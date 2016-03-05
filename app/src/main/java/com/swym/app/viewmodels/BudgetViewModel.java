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
    public void addPurchase(Purchase purchase) {
        transactions.add(purchase);
        dataSource.createTransaction(purchase.getName(), purchase.getCost(), purchase.getDescription(), purchase.getDate(),"Purchase", purchase.getRealDate());
    }
    public void addFund(Fund fund) {
        transactions.add(fund);
        dataSource.createTransaction(fund.getName(), fund.getCost(), fund.getDescription(), fund.getDate(),"Fund", fund.getRealDate());
    }
    //TODO THIS FUCKING SUCKS
    public double getFunds(){
        double fundsBudget = 0.00;
        for(Transaction d: transactions){
            if(d instanceof Purchase) {
                fundsBudget -= d.getCost();
            }
            else{
                fundsBudget += d.getCost();
            }
        }
        return fundsBudget;
    }
    public void fuckingAwfulFunction(NumberFormat fmt) {
        transactions = dataSource.getAllTransactions();
        for(Transaction d: transactions) {
            if(d instanceof Purchase) {
                updatedBudget = updatedBudget - d.getCost();
                fundsBudget -= d.getCost();
            }
            else{
                fundsBudget += d.getCost();
            }
        }

        if(updatedBudget<=0.00){
            bs.setTextColor(Color.RED);
        }else if(updatedBudget < (.25*budgetVal)){
            bs.setTextColor(Color.YELLOW);
        }
        else{
            bs.setTextColor(Color.GREEN);
        }
        bs.setText(fmt.format(updatedBudget));
        fs.setText(fmt.format(fundsBudget));

    }
}
