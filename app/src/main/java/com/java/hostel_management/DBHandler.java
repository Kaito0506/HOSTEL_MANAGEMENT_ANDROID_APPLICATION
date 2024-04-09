package com.java.hostel_management;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.java.hostel_management.model.ModelBill;
import com.java.hostel_management.model.ModelBillDetail;
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
                "total MONEY default 0," +
                "isPaid int default 0," +
                "FOREIGN KEY (room_id) REFERENCES ROOM(id))";

        String createBillDetail = "CREATE TABLE BILLDETAIL ( " +
                "id INTEGER primary key autoincrement," +
                "bill_id INTEGER," +
                "service_id INTEGER," +
                "quantity int not null," +
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

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorService = db.rawQuery("SELECT service_id FROM BILLDETAIL where bill_id=?", new String[]{String.valueOf(id)});
        if(cursorService.getCount()>0){
            Log.d(TAG, "deleteService: service is in unpaid bill");
                return false;
        }
        else {
            db.delete("SERVICE", "id=?", new String[]{String.valueOf(id)});
            Log.d(TAG, "deleteService: success");
            return true;
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
    public boolean addBill(int room_id, String cus_name, String checkIn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues valueUpdate = new ContentValues();
        try {
            values.put("room_id", room_id);
            values.put("cus_name", cus_name);
            values.put("total", 0);
            values.put("checkin", checkIn);
            // Only put checkOut in ContentValues if it's not null

            db.insert("BILL", null, values);
            // update room status
            valueUpdate.put("status", 1);
            db.update("ROOM", valueUpdate, "id=?", new String[]{String.valueOf(room_id)});
            return true;
        } catch (Exception e) {
            return false;
        }

    };

    //////////
    public boolean addBillDetail(int bill_id, int service_id,  int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues valueUpdate = new ContentValues();
        try {
            values.put("bill_id", bill_id);
            values.put("service_id", service_id);
            values.put("quantity", quantity);
            db.insert("BILLDETAIL", null, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    public ModelBill getUncheckOutBill(int room_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorBill = db.rawQuery("SELECT * FROM BILL WHERE isPaid=0 and  room_id=?", new String[]{String.valueOf(room_id)});

        // Check if cursor has data
        if(cursorBill.moveToFirst()) {
            ModelBill bill = new ModelBill(
                    cursorBill.getInt(0),
                    cursorBill.getInt(1),
                    cursorBill.getString(2),
                    cursorBill.getString(3),
                    cursorBill.getString(4),
                    cursorBill.getDouble(5),
                    cursorBill.getInt(6)
            );
            cursorBill.close();
            return bill;
        } else {
            // No data found, return null or handle the situation accordingly
            cursorBill.close();
            return null;
        }
    }
    ////////////////////////
    public ModelService getService(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SERVICE WHERE id=?", new String[]{String.valueOf(id)});

        // Check if cursor has data
        if(cursor.moveToFirst()) {
            ModelService bill = new ModelService(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2)
            );
            cursor.close();
            return bill;
        } else {
            // No data found, return null or handle the situation accordingly
            cursor.close();
            return null;
        }
    }
    //////////////////////////
    public ArrayList<ModelBillDetail> viewAllBillDetail(int bill_id){
        ArrayList<ModelBillDetail> details = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from BILLDETAIL WHERE bill_id=?", new String[]{String.valueOf(bill_id)});
        // move to first item
        if(cursor.moveToFirst()){
            do{
                details.add(new ModelBillDetail(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                ));
            }while(cursor.moveToNext());
            Log.d(TAG, "viewAllBillDetail: !!!!!!!!!!!!!");
        }
        cursor.close();
        return details;
    }

    public boolean checkout(int room_id, int bill_id, Double finalTotal) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues roomValues = new ContentValues();
            roomValues.put("status", 0); // 0 lets this room be available
            int roomRow = db.update("ROOM", roomValues, "id=?", new String[]{String.valueOf(room_id)});

            // Change bill status
            ContentValues billValues = new ContentValues();
            billValues.put("isPaid", 1); // it means paid
            billValues.put("total", finalTotal);
            int billRow = db.update("BILL", billValues, "id=?", new String[]{String.valueOf(bill_id)});

            db.close();
            // Return true if both roomRow and billRow are greater than 0, indicating successful updates
            return (roomRow > 0 && billRow > 0);
        } catch (Exception e) {
            Log.d(TAG, "Checkout: failed");
            return false;
        }
    }



}
