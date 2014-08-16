package com.swym.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 8/16/2014.
 */
public class TransactionDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_TYPE, MySQLiteHelper.COLUMN_COST,
            MySQLiteHelper.COLUMN_DESCRIPTION};

    public TransactionDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteException{
        database = dbHelper.getReadableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Transaction createTransaction(String name, double cost, String type, String desc){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_COST, cost);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, desc);
        values.put(MySQLiteHelper.COLUMN_TYPE, type);
        long insertId = database.insert(MySQLiteHelper.TABLE_TRANSACTIONS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRANSACTIONS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Transaction t = cursorToTransaction(cursor);
        cursor.close();
        return t;
    }

    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction t = null;
        if(cursor.getString(4).equals("Purchase")){
            t = new Purchase();
        }else if(cursor.getString(4).equals("Fund")){
            t = new Fund();
        }
        t.setId(cursor.getLong(0));
        t.setName(cursor.getString(1));
        t.setCost(cursor.getDouble(2));
        t.setDescription(cursor.getString(3));
        return t;
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions = new ArrayList<Transaction>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRANSACTIONS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Transaction t = cursorToTransaction(cursor);
            transactions.add(t);
            cursor.moveToNext();
        }
        cursor.close();
        return transactions;
    }
}
