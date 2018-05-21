package com.example.snickers.auto.DB.contacts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.snickers.auto.DB.DBHelper;

import java.util.ArrayList;

public class ContactDao {
    private SQLiteDatabase database;
   private DBHelper dbHelper;

  public ContactDao (View v){
       dbHelper = new DBHelper(v.getContext());


   }
 public ArrayList<ContactModel> select (){
  ArrayList<ContactModel> contacts = new ArrayList<>();
     database = dbHelper.getWritableDatabase();

     Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
     if (cursor.moveToFirst()) {
         int idIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
         int idVolume = cursor.getColumnIndex(dbHelper.KEY_VOLUME);
         int idPrice = cursor.getColumnIndex(dbHelper.KEY_PRICE);
         int idType = cursor.getColumnIndex(dbHelper.KEY_TYPE);
         int idTogether = cursor.getColumnIndex(dbHelper.KEY_TOGETHER);
         int idDistance = cursor.getColumnIndex(dbHelper.KEY_DISTANCE);
         int idDate = cursor.getColumnIndex(dbHelper.KEY_DATE);


         ContactModel item;
         do {
             try {
                 item = new ContactModel();
                 item.set_id(cursor.getInt(idIndex));
                 item.setVolume(cursor.getDouble(idVolume));
                 item.setPrice(cursor.getDouble(idPrice));
                 item.setTogether(cursor.getDouble(idTogether));
                 item.setType(cursor.getString(idType));
                 item.setDate(cursor.getString(idDate));
                 item.setDistance(cursor.getInt(idDistance));

                 contacts.add(item);
             } catch (Exception e) {
                 break;
             }
         } while (cursor.moveToNext());
     } else {
         Log.d("mLog", "0 row");
     }
     cursor.close();
     database.close();

  return contacts;
 }
 public Boolean add ( String date,
          int distance,
          double volume,
          double price,
          double together,
          String type){
     database = dbHelper.getWritableDatabase();
     ContentValues contentValues = new ContentValues();

     contentValues.put(DBHelper.KEY_VOLUME, volume);
     contentValues.put(DBHelper.KEY_TOGETHER, together);
     contentValues.put(DBHelper.KEY_TYPE, type);
     contentValues.put(DBHelper.KEY_PRICE, price);
     contentValues.put(DBHelper.KEY_DATE, date);
     contentValues.put(DBHelper.KEY_DISTANCE, distance);

     long values = database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
     database.close();
     if (values != -1)
         return true;
     else
         return false;

 }



    public boolean delete(int id) {

        database = dbHelper.getWritableDatabase();

        int values = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + " = " + id, null);
        database.close();
        if (values != 0)
            return true;
        else
            return false;
    }

    public boolean update(int id, String date,
                          int distance,
                          double volume,
                          double price,
                          double together,
                          String type) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_VOLUME, volume);
        contentValues.put(DBHelper.KEY_TOGETHER, together);
        contentValues.put(DBHelper.KEY_TYPE, type);
        contentValues.put(DBHelper.KEY_PRICE, price);
        contentValues.put(DBHelper.KEY_DATE, date);
        contentValues.put(DBHelper.KEY_DISTANCE, distance);
        // обновляем по id
        int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + " = " + id, null);


      return true;
    }


}
