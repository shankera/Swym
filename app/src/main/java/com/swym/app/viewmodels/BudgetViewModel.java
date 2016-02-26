package com.swym.app.viewmodels;

import android.graphics.Color;

import com.swym.app.data.DataSource;
import com.swym.app.data.Fund;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;

import java.text.NumberFormat;
import java.util.List;
import rx.Observable;

public class BudgetViewModel {
    private DataSource dataSource;
    public boolean firstTimeRun;
    public boolean hasMoney = false;
    private final double NO_MONEY = 0.00;
    public int budgetColor = Color.BLACK;
    public double budget = 0.00;
    public double balance = 0.00;

    public String budgetText;

    private List<Transaction> transactions;
    public BudgetViewModel(double budget){
        this.dataSource = DataSource.getInstance();
        this.transactions = dataSource.getAllTransactions();
        hasMoney = this.transactions != null;
        double newBudget = budget;
        if(hasMoney) {
            for(Transaction transaction: transactions){
                if(transaction instanceof Purchase){
                    newBudget -= transaction.getCost();
                } else if (transaction instanceof Fund){
                    newBudget += transaction.getCost();
                }
            }
            this.budget = newBudget;

        } else {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            this.budgetText = formatter.format(NO_MONEY);
        }
    }

    public void updateBudget(){
        double newBudget = NO_MONEY;

        if(newBudget<=0.00){
            budgetColor = Color.RED;
        } else if(newBudget < (.25*budget)) {
            budgetColor = Color.YELLOW;
        } else {
            budgetColor = Color.GREEN;
        }
    }

}
