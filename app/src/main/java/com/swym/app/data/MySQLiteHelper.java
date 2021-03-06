package com.swym.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    static final String TABLE_TRANSACTIONS = "transactions";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_TYPE = "type";
    static final String COLUMN_COST = "cost";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_REAL_DATE = "realdate";
    static final String COLUMN_DESCRIPTION = "description";
    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_TRANSACTIONS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_TYPE + " text not null, "
            + COLUMN_COST + " double not null, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_DATE + " integer not null, "
            + COLUMN_REAL_DATE + " text);";

    MySQLiteHelper(Context context) {
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
