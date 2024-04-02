package com.java.hostel_management;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String dbName = "HOSTEL_MANAGEMENT";
    private static final int dbVersion = 1;
    public static  boolean isSuccess;
    public DBHandler(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRoom = "CREATE TABLE ROOM ( " +
                "id INTEGER primary key autoincrement," +
                "name TEXT not null unique," +
                "status INTEGER not null," +
                "type TEXT not null,"  +
                "price MONEY not null)";

        String createProduct = "CREATE TABLE PRODUCT ( " +
                "id INTEGER primary key autoincrement," +
                "nane TEXT not null," +
                "price MONEY not null)";

        String createBill = "CREATE TABLE BILL ( " +
                "id INTEGER primary key autoincrement," +
                "room_id INTEGER ," +
                "nane TEXT not null," +
                "checkIn DATETIME not null," +
                "checkOut DATETIME," +
                "total MONEY," +
                "FOREIGN KEY (room_id) REFERENCES ROOM(id))";

        String createBillDetail = "CREATE TABLE BILLDETAIL ( " +
                "id INTEGER primary key autoincrement," +
                "bill_id INTEGER," +
                "product_id INTEGER," +
                "FOREIGN KEY (bill_id) REFERENCES BILL(id)," +
                "FOREIGN KEY (product_id) REFERENCES PRODUCT(id))";

        try{
            db.execSQL(createRoom);
            db.execSQL(createProduct);
            db.execSQL(createBill);
            db.execSQL(createBillDetail);
            Log.d(TAG, "onCreate: success!!!!!!!!!!!!!!!!!!!!!");
            isSuccess = true;

        }catch(Exception e){
            Log.d(TAG, "onCreate: failed!!!!!!!!!!!!!!!!!!!!!");
            isSuccess = false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BILLDETAIL");
        db.execSQL("DROP TABLE IF EXISTS BILL");
        db.execSQL("DROP TABLE IF EXISTS ROOM");
        db.execSQL("DROP TABLE IF EXISTS PRODUCT");
        onCreate(db);
    }

    public boolean addRoom(String n, String type, double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Check if the room with the same name already exists
        Cursor cursor = db.rawQuery("SELECT * FROM ROOM WHERE name=?", new String[]{n});

        if (cursor.getCount() > 0) {
            // Room with the same name already exists, handle the situation (e.g., show a message)
            Log.d(TAG, "addRoom: Room with the same name already exists");
            cursor.close();
            return false;
        } else {
            // Room with the given name doesn't exist, proceed with insertion
            try {
                values.put("name", n);
                values.put("price", price);
                values.put("status", 0);
                values.put("type", type);
                db.insert("ROOM", null, values);
                Log.d(TAG, "addRoom: success!!!!!!!!!!!!!!!!!!!!!");
                cursor.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "addRoom: failed!!!!!!!!!!!!!!!!!!!!!");
                cursor.close();
                return false;
            }
        }
    }

    public ArrayList<ModelRoom> viewAllRoom(){
        ArrayList<ModelRoom> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorRooms = db.rawQuery("select * from ROOM", null);
        // move to first item
        if(cursorRooms.moveToFirst()){
            do{
                rooms.add(new ModelRoom(
                   cursorRooms.getInt(0),
                   cursorRooms.getString(1),
                   cursorRooms.getInt(2),
                   cursorRooms.getString(3),
                   cursorRooms.getDouble(4)
                ));
            }while(cursorRooms.moveToNext());
        }
        cursorRooms.close();
        return rooms;
    }

    public boolean deleteRoom(int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete("ROOM", "id=?", new String[]{String.valueOf(id)});
            Log.d(TAG, "deleteRoom: success");
            return true;
        }catch (Exception e){
            Log.d(TAG, "deleteRoom: failded");
            return false;
        }
    }

    public boolean UpdateRoom(int id, String n, String t, Double p){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("name", n);
            values.put("price", p);
            values.put("type", t);
            int rowsAffect = db.update("ROOM", values, "id=?", new String[]{String.valueOf(id)});
            Log.d(TAG, "UpdateRoom: success");
            db.close();
            return rowsAffect>0;
        }catch (Exception e){
            Log.d(TAG, "UpdateRoom: failed");
            return false;
        }
    }


}
