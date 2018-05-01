package com.swym.app.viewmodels.main;

import com.swym.app.data.Deposit;
import com.swym.app.data.IDataSource;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;

import java.util.List;

public class BudgetViewModel {
    private final double NO_MONEY = 0.00;
    public double budget = 0.00;
    public double balance = 0.00;
    private IDataSource dataSource;
    private List<Transaction> transactions;

    //If balance and budget are passed through, do not re-calculate
    public BudgetViewModel(double budget, double balance, double budgetGoal, IDataSource dataSource) {
        this.dataSource = dataSource;
        this.dataSource.setBudgetGoal(budgetGoal);
        this.transactions = dataSource.getAllTransactions();

        this.budget = budget;
        this.balance = balance;
        this.dataSource.setBalance(balance);
        if (this.dataSource.getBudgetGoal() == NO_MONEY) {
            this.dataSource.setBudgetGoal(budget);
        }
    }

    public double getBudgetGoal() {
        return this.dataSource.getBudgetGoal();
    }

    public void addWithdrawal(Withdrawal withdrawal) {
        transactions.add(withdrawal);
        balance -= withdrawal.getCost();
        budget -= withdrawal.getCost();
        dataSource.createTransaction(withdrawal, TransactionType.WITHDRAWAL);
        this.dataSource.setBalance(balance);
    }

    public void addDeposit(Deposit deposit) {
        transactions.add(deposit);
        balance += deposit.getCost();
        dataSource.createTransaction(deposit, TransactionType.DEPOSIT);
        this.dataSource.setBalance(balance);
    }

    public double updateBudget(double newBudgetGoal) {
        double budgetDifference = this.dataSource.getBudgetGoal() - this.budget;
        this.dataSource.setBudgetGoal(newBudgetGoal);
        this.budget = newBudgetGoal - budgetDifference;
        return this.budget;
    }
}
