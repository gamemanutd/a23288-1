package com.egco428.a23288;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_DATA = "comment";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USR = "user";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_LAT = "lati";
    public static final String COLUMN_LONG = "longti";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DATA + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_USR + " text not null, " +
            COLUMN_PASS + " text not null, " +
            COLUMN_LAT + " text not null, " +
            COLUMN_LONG + " text not null);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("Pong tai","pup");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }
}
