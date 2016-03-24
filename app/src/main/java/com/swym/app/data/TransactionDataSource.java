package com.swym.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransactionDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_COST, MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_TYPE, MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_REAL_DATE};

    public TransactionDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteException{
        database = dbHelper.getReadableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public synchronized Transaction createTransaction(String name, double cost, String desc, int date, TransactionType type, String realDate){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_COST, cost);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, desc);
        values.put(MySQLiteHelper.COLUMN_TYPE, type.toString());
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        values.put(MySQLiteHelper.COLUMN_REAL_DATE, realDate);
        long insertId = database.insert(MySQLiteHelper.TABLE_TRANSACTIONS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRANSACTIONS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Transaction t = cursorToTransaction(cursor);
        cursor.close();
        return t;
    }
    public void deleteTransaction(Transaction t){
        long id = t.getId();
        database.delete(MySQLiteHelper.TABLE_TRANSACTIONS,
                MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }
    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction t = null;
        if(cursor.getString(4).equals(TransactionType.WITHDRAWAL.toString())){
            t = new Withdrawal();
        }else if(cursor.getString(4).equals(TransactionType.DEPOSIT.toString())){
            t = new Deposit();
        }
        if(t != null) {
            t.setId(cursor.getLong(0));
            t.setName(cursor.getString(1));
            t.setCost(cursor.getDouble(2));
            t.setDescription(cursor.getString(3));
            t.setDate(cursor.getInt(5));
            t.setRealDate(cursor.getString(6));
        }
        return t;
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> temp = new ArrayList<Transaction>();
        List<Transaction> transactions = new ArrayList<Transaction>();

        Date now = new Date();
        String date = new SimpleDateFormat("yyyymm").format(now);
        for(Transaction t : temp){
            if(t.getDate() == Integer.parseInt(date)){
                transactions.add(t);
            }
        }
       // Collections.reverse(transactions);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRANSACTIONS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction t = cursorToTransaction(cursor);
            transactions.add(t);
            cursor.moveToNext();
        }
        cursor.close();

        return transactions;
    }
}
