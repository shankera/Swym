package com.swym.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Arjun on 8/16/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_DESCRIPTION = "description";
    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_TRANSACTIONS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_TYPE + " text not null, "
            + COLUMN_COST + " double not null, "
            + COLUMN_DESCRIPTION + " text);";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newer) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + old + " to "
                        + newer + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(sqLiteDatabase);
    }
}
