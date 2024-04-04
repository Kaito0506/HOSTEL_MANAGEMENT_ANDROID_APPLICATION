package com.java.hostel_management;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.java.hostel_management.model.ModelRoom;
import com.java.hostel_management.model.ModelService;

import java.time.LocalDateTime;
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

        String createProduct = "CREATE TABLE SERVICE ( " +
                "id INTEGER primary key autoincrement," +
                "name TEXT not null," +
                "price MONEY not null)";

        String createBill = "CREATE TABLE BILL ( " +
                "id INTEGER primary key autoincrement," +
                "room_id INTEGER ," +
                "cus_name TEXT not null," +
                "checkIn DATETIME not null," +
                "checkOut DATETIME," +
                "total MONEY," +
                "FOREIGN KEY (room_id) REFERENCES ROOM(id))";

        String createBillDetail = "CREATE TABLE BILLDETAIL ( " +
                "id INTEGER primary key autoincrement," +
                "bill_id INTEGER," +
                "service_id INTEGER," +
                "FOREIGN KEY (bill_id) REFERENCES BILL(id)," +
                "FOREIGN KEY (service_id) REFERENCES SERVICE(id))";

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
        db.execSQL("DROP TABLE IF EXISTS SERVICE");
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

    public ArrayList<ModelRoom> viewAvailableRoom(int status){
        ArrayList<ModelRoom> rooms = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorRooms = db.rawQuery("select * from ROOM where status=" + status, null);
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
    /////////////////////
    public boolean addService(String n, double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Check if the room with the same name already exists
        Cursor cursor = db.rawQuery("SELECT * FROM SERVICE WHERE name=?", new String[]{n});

        if (cursor.getCount() > 0) {
            // Room with the same name already exists, handle the situation (e.g., show a message)
            Log.d(TAG, "addService: rvice with the same name already exists");
            cursor.close();
            return false;
        } else {
            // Room with the given name doesn't exist, proceed with insertion
            try {
                values.put("name", n);
                values.put("price", price);
                db.insert("SERVICE", null, values);
                Log.d(TAG, "addService: success!!!!!!!!!!!!!!!!!!!!!");
                cursor.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "addService: failed!!!!!!!!!!!!!!!!!!!!!");
                cursor.close();
                return false;
            }
        }
    }

    ///////////
    public ArrayList<ModelService> viewAllService(){
        ArrayList<ModelService> services = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorServices = db.rawQuery("select * from SERVICE", null);
        // move to first item
        if(cursorServices.moveToFirst()){
            do{
                services.add(new ModelService(
                        cursorServices.getInt(0),
                        cursorServices.getString(1),
                        cursorServices.getDouble(2)
                ));
            }while(cursorServices.moveToNext());
        }
        cursorServices.close();
        return services;
    }
    /////////////////////////////////
    public boolean deleteService(int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete("SERVICE", "id=?", new String[]{String.valueOf(id)});
            Log.d(TAG, "deleteService: success");
            return true;
        }catch (Exception e){
            Log.d(TAG, "deleteService: failded");
            return false;
        }
    }
    ////////////////////////////////////
    public boolean UpdateService(int id, String n,Double p){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("name", n);
            values.put("price", p);
            int rowsAffect = db.update("SERVICE", values, "id=?", new String[]{String.valueOf(id)});
            Log.d(TAG, "UpdateServie: success");
            db.close();
            return rowsAffect>0;
        }catch (Exception e){
            Log.d(TAG, "UpdateService: failed");
            return false;
        }
    }
    /////////////////////////
    public boolean addBill(int room_id, String cus_name, String checkIn, String checkOut){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues valueUpdate = new ContentValues();
        try {
            values.put("room_id", room_id);
            values.put("cus_name", cus_name);
            values.put("total", 0);
            values.put("checkin", checkIn);
            // Only put checkOut in ContentValues if it's not null
            if (checkOut != null) {
                values.put("checkout", checkOut);
            }
            db.insert("BILL", null, values);
            // update room status
            valueUpdate.put("status", 1);
            db.update("ROOM", valueUpdate, "id=?", new String[]{String.valueOf(room_id)});
            return true;
        } catch (Exception e) {
            return false;
        }

    };

}
