package com.swym.app.data;

import android.content.Context;

import java.util.List;

public class DataSource implements IDataSource {
    private static TransactionDataSource dataSource;
    private static DataSource instance;
    private double balance;
    private double budgetGoal;

    private DataSource(Context context) {
        dataSource = new TransactionDataSource(context);
        dataSource.open();

    }

    public static DataSource getInstance(Context context) {
        return instance == null ? instance = new DataSource(context) : instance;
    }

    public static DataSource getInstance() {
        if (instance == null) {
            throw new NullPointerException("DataSource has not been initialized");
        }
        return instance;
    }

    public double getBudgetGoal() {
        return budgetGoal;
    }

    public void setBudgetGoal(double budgetGoal) {
        this.budgetGoal = budgetGoal;
    }

    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void createTransaction(String name, double cost, String desc, int date, TransactionType type, String realDate) {
        dataSource.createTransaction(name, cost, desc, date, type, realDate);
    }

    public void createTransaction(Transaction transaction, TransactionType type) {
        dataSource.createTransaction(transaction.getName(), transaction.getCost(), transaction.getDescription(), transaction.getDate(), type, transaction.getRealDate());
    }

    public void deleteTransaction(Transaction t) {
        dataSource.deleteTransaction(t);
    }

    public List<Transaction> getAllTransactions() {
        return dataSource.getAllTransactions();
    }
}