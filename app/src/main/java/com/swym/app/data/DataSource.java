package com.swym.app.data;

import android.content.Context;

import java.util.List;

public class DataSource {
    private static TransactionDataSource dataSource;
    private static DataSource instance;
    public static DataSource getInstance(Context context) {
        return instance == null ? instance = new DataSource(context) : instance;
    }
    public static DataSource getInstance() {
        return instance;
    }

    private DataSource(Context context){
        dataSource = new TransactionDataSource(context);
        dataSource.open();

    }
    public void createTransaction(String name, double cost, String desc, int date, String type, String realDate){
        dataSource.createTransaction(name, cost, desc, date, type, realDate);
    }
    public void deleteTransaction(Transaction t){
        dataSource.deleteTransaction(t);
    }
    public List<Transaction> getAllTransactions(){
        return dataSource.getAllTransactions();
    }
}