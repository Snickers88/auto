package com.example.snickers.auto.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public  static  final int DATABASE_VERSION = 3;
    public  static  final String DATABASE_NAME = "contactDb";
    public  static  final String TABLE_CONTACTS = "contacts";
    public  static  final String TABLE_EVENTS= "events";


    public  static  final String KEY_ID = "_id";
    public  static  final String KEY_DATE = "date";
    public  static  final String KEY_DISTANCE = "distance";
    public  static  final String KEY_VOLUME = "volume";
    public  static  final String KEY_PRICE = "price";
    public  static  final String KEY_TOGETHER = "together";
    public  static  final String KEY_TYPE = "type";
    public  static  final String KEY_DESCRIPTION = "description";






    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table " + TABLE_CONTACTS + "("
              + KEY_ID + " integer primary key,"
              + KEY_DATE + " text,"
              + KEY_DISTANCE + " integer,"
              + KEY_VOLUME + " real,"
              + KEY_PRICE + " real,"
              + KEY_TOGETHER + " real,"
              + KEY_TYPE + " text" + ")");
      db.execSQL("create table " + TABLE_EVENTS + "("
                + KEY_ID + " integer primary key,"
                + KEY_DATE + " text,"
                + KEY_DISTANCE + " integer,"
                + KEY_DESCRIPTION + " text" + ")");
    }








    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("drop table if exists " + TABLE_CONTACTS);
        db.execSQL("drop table if exists " + TABLE_EVENTS);

       onCreate(db);
    }










}
