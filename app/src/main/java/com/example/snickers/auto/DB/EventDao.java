package com.example.snickers.auto.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class EventDao {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public EventDao (View v){
        dbHelper = new DBHelper(v.getContext());


    }
    public ArrayList<EventModel> select (){
        ArrayList<EventModel> contacts = new ArrayList<>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_EVENTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            int idDate = cursor.getColumnIndex(dbHelper.KEY_DATE);
            int idDistance = cursor.getColumnIndex(dbHelper.KEY_DATE);
            int idDescription = cursor.getColumnIndex(dbHelper.KEY_DESCRIPTION);

            EventModel item;
            do {
                try {
                    item = new EventModel();
                    item.set_id(cursor.getInt(idIndex));
                    item.setDate(cursor.getString(idDate));
                    item.setDistance(cursor.getInt(idDistance));
                    item.setDescription(cursor.getString(idDescription));

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
                         String description
                         ){
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(DBHelper.KEY_DATE, date);
        contentValues.put(DBHelper.KEY_DISTANCE, distance);
        contentValues.put(DBHelper.KEY_DESCRIPTION, description);

        long values = database.insert(DBHelper.TABLE_EVENTS, null, contentValues);
        database.close();
        if (values != -1)
            return true;
        else
            return false;

    }



    public boolean delete(int id) {

        database = dbHelper.getWritableDatabase();

        int values = database.delete(DBHelper.TABLE_EVENTS, DBHelper.KEY_ID + " = " + id, null);
        database.close();
        if (values != 0)
            return true;
        else
            return false;
    }

    public boolean update(int id, String date,
                          int distance,
                          String description) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_DATE, date);
        contentValues.put(DBHelper.KEY_DISTANCE, distance);
        contentValues.put(DBHelper.KEY_DESCRIPTION, description);
        // обновляем по id
        int updCount = database.update(DBHelper.TABLE_EVENTS, contentValues, DBHelper.KEY_ID + " = " + id, null);


        return true;
    }
}
