package com.swym.app.viewmodels;

import com.swym.app.data.DataSource;
import com.swym.app.data.Fund;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;

import java.util.List;

public class BudgetViewModel {
    private DataSource dataSource;
    public boolean firstTimeRun;
    public boolean hasMoney = false;
    private final double NO_MONEY = 0.00;
    public double budgetGoal = 0.00;
    public double budget = 0.00;
    public double balance = 0.00;

    private List<Transaction> transactions;
    public BudgetViewModel(double budget, double balance){
        this.dataSource = DataSource.getInstance();
        this.transactions = dataSource.getAllTransactions();
        hasMoney = this.transactions != null;
        double newBudget = budget;
        budgetGoal = budget;
        this.budget = budget;
        if(balance == NO_MONEY && hasMoney) {
            for(Transaction transaction: transactions){
                if(transaction instanceof Purchase){
                    newBudget -= transaction.getCost();
                    this.balance -= transaction.getCost();
                } else if (transaction instanceof Fund){
                    this.balance += transaction.getCost();
                }
            }
            this.budget = newBudget;
        }
    }

    public void addPurchase(Purchase purchase) {
        transactions.add(purchase);
        balance -= purchase.getCost();
        dataSource.createTransaction(purchase.getName(), purchase.getCost(), purchase.getDescription(), purchase.getDate(),"Purchase", purchase.getRealDate());
    }

    public void addFund(Fund fund) {
        transactions.add(fund);
        balance += fund.getCost();
        dataSource.createTransaction(fund.getName(), fund.getCost(), fund.getDescription(), fund.getDate(),"Fund", fund.getRealDate());
    }

    public double updateBudget(double budgetGoal) {
        this.budgetGoal = budgetGoal;
        double updatedBudget = budgetGoal;
        transactions = dataSource.getAllTransactions();
        for(Transaction d: transactions) {
            if(d instanceof Purchase) {
                updatedBudget = updatedBudget - d.getCost();
            }
        }
        this.budget = updatedBudget;
        return updatedBudget;
    }
}
