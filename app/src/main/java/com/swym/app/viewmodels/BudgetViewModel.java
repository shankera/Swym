package com.swym.app.viewmodels;

import com.swym.app.data.DataSource;
import com.swym.app.data.Fund;
import com.swym.app.data.IDataSource;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;

import java.util.List;

public class BudgetViewModel {
    private IDataSource dataSource;
    public boolean firstTimeRun;
    public boolean hasMoney = false;
    private final double NO_MONEY = 0.00;
    public double budget = 0.00;
    public double balance = 0.00;

    private List<Transaction> transactions;
    public BudgetViewModel(double budget, double balance, double budgetGoal, IDataSource dataSource){
        this.dataSource = dataSource;
        this.dataSource.setBudgetGoal(budgetGoal);
        this.transactions = dataSource.getAllTransactions();
        hasMoney = this.transactions != null;

        this.budget = budget;
        this.balance = balance;
        if(this.dataSource.getBudgetGoal() == NO_MONEY) {
            this.dataSource.setBudgetGoal(budget);
        }
        if(balance == NO_MONEY && hasMoney) {
            updateEverything();
        }
    }
    public double getBudgetGoal() {
        return this.dataSource.getBudgetGoal();
    }
    public void updateEverything(){
        this.transactions = dataSource.getAllTransactions();
        double newBudget = this.dataSource.getBudgetGoal();
        this.balance = 0.00;
        for(Transaction transaction: this.transactions){
            if(transaction instanceof Purchase){
                newBudget -= transaction.getCost();
                this.balance -= transaction.getCost();
            } else if (transaction instanceof Fund){
                this.balance += transaction.getCost();
            }
        }
        this.budget = newBudget;
    }

    public void addPurchase(Purchase purchase) {
        transactions.add(purchase);
        balance -= purchase.getCost();
        budget -= purchase.getCost();
        dataSource.createTransaction(purchase.getName(), purchase.getCost(), purchase.getDescription(), purchase.getDate(),"Purchase", purchase.getRealDate());
    }

    public void addFund(Fund fund) {
        transactions.add(fund);
        balance += fund.getCost();
        dataSource.createTransaction(fund.getName(), fund.getCost(), fund.getDescription(), fund.getDate(),"Fund", fund.getRealDate());
    }

    public double updateBudget(double budgetGoal) {
        this.dataSource.setBudgetGoal(budgetGoal);
        double updatedBudget = budgetGoal;
        for(Transaction d: transactions) {
            if(d instanceof Purchase) {
                updatedBudget = updatedBudget - d.getCost();
            }
        }
        this.budget = updatedBudget;
        return updatedBudget;
    }
}
