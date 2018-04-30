package com.swym.app.viewmodels.main;

import com.swym.app.data.Deposit;
import com.swym.app.data.IDataSource;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;

import java.util.List;

public class BudgetViewModel {
    private final double NO_MONEY = 0.00;
    private boolean hasMoney = false;
    public double budget = 0.00;
    public double balance = 0.00;
    private IDataSource dataSource;
    private List<Transaction> transactions;

    //If balance and budget are passed through, do not re-calculate
    public BudgetViewModel(double budget, double balance, double budgetGoal, IDataSource dataSource) {
        this.dataSource = dataSource;
        this.dataSource.setBudgetGoal(budgetGoal);
        this.transactions = dataSource.getAllTransactions();
        hasMoney = this.transactions != null;

        this.budget = budget;
        this.balance = balance;
        if (this.dataSource.getBudgetGoal() == NO_MONEY) {
            this.dataSource.setBudgetGoal(budget);
        }
        if (balance == NO_MONEY && hasMoney) {
//            updateEverything();
        }
    }

    public double getBudgetGoal() {
        return this.dataSource.getBudgetGoal();
    }

    public void updateEverything() {
        this.transactions = dataSource.getAllTransactions();
        double newBudget = this.dataSource.getBudgetGoal();
//        this.balance = 0.00;
//        for (Transaction transaction : this.transactions) {
//            if (transaction instanceof Withdrawal) {
//                newBudget -= transaction.getCost();
//                this.balance -= transaction.getCost();
//            } else if (transaction instanceof Deposit) {
//                this.balance += transaction.getCost();
//            }
//        }
        this.budget = newBudget;
    }

    public void addWithdrawal(Withdrawal withdrawal) {
        transactions.add(withdrawal);
        balance -= withdrawal.getCost();
        budget -= withdrawal.getCost();
        dataSource.createTransaction(withdrawal.getName(), withdrawal.getCost(), withdrawal.getDescription(), withdrawal.getDate(), TransactionType.WITHDRAWAL, withdrawal.getRealDate());
    }

    public void addDeposit(Deposit deposit) {
        transactions.add(deposit);
        balance += deposit.getCost();
        dataSource.createTransaction(deposit.getName(), deposit.getCost(), deposit.getDescription(), deposit.getDate(), TransactionType.DEPOSIT, deposit.getRealDate());
    }

    public double updateBudget(double newBudgetGoal) {
        double budgetDifference = this.getBudgetGoal() - this.budget;
        this.dataSource.setBudgetGoal(newBudgetGoal);
        this.budget = newBudgetGoal - budgetDifference;
        return this.budget;
    }
}
