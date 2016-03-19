package com.swym.app.data;

import java.util.List;

public interface IDataSource {
    double getBudgetGoal();
    void setBudgetGoal(double budgetGoal);
    void createTransaction(String name, double cost, String desc, int date, String type, String realDate);
    void deleteTransaction(Transaction t);
    List<Transaction> getAllTransactions();
}