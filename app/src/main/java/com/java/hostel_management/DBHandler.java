package com.java.hostel_management;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
                "name TEXT not null," +
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

    public void addRoom(String n, String type, double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", n);
        values.put("price", price);
        values.put("status", 0);
        values.put("type", type);
        db.insert("ROOM", null, values);
        //db.close();

    }
}
