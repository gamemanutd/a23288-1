package com.egco428.a23288;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


public class UserDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USR, DatabaseHelper.COLUMN_PASS, DatabaseHelper.COLUMN_LAT, DatabaseHelper.COLUMN_LONG};

    public UserDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public UserData createData(String user, String pass, Double lati, Double longti){
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_USR, user);
        values.put(DatabaseHelper.COLUMN_PASS, pass);
        values.put(DatabaseHelper.COLUMN_LAT, lati);
        values.put(DatabaseHelper.COLUMN_LONG, longti);

        long insertID = database.insert(DatabaseHelper.TABLE_DATA, null,values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_DATA,allColumns, DatabaseHelper.COLUMN_ID+ " = "+insertID, null, null, null, null);
        cursor.moveToFirst();
        UserData newUserData = cursorToUserData(cursor);
        cursor.close();
        return newUserData;
    }

    public void deleteUserData(UserData userData){
        long id = userData.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(DatabaseHelper.TABLE_DATA, DatabaseHelper.COLUMN_ID+" = "+id, null);

    }

    public String checkPassword(String user){
        String queryPass = "SELECT pass FROM comment WHERE " + DatabaseHelper.COLUMN_USR + "=" + "'" + user +"'";
        Cursor cursor = database.rawQuery(queryPass,null);
        if (cursor.getCount()==0){
            cursor.close();
            return null;
        }
        else if(cursor.getCount()>0){
            cursor.moveToFirst();
            if (cursor.moveToFirst()){
                String getPass = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASS));
                return getPass;
            }
        }
        cursor.close();
        return null;
    }

    public List<UserData> getAllUserData(){
        List<UserData> fortunes = new ArrayList<UserData>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_DATA, allColumns,null,null, null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            UserData userData = cursorToUserData(cursor);
            fortunes.add(userData);
            cursor.moveToNext();
        }
        cursor.close();
        return fortunes;
    }

    public UserData cursorToUserData(Cursor cursor){
        UserData userData= new UserData();
        userData.setId(cursor.getLong(0));
        userData.setUser(cursor.getString(1));
        userData.setPasss(cursor.getString(2));
        userData.setLat(cursor.getString(3));
        userData.setLong(cursor.getString(4));
        return userData;
    }

}
