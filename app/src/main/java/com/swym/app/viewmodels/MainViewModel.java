package com.swym.app.viewmodels;

import android.content.Context;

import com.swym.app.data.DataSource;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;

public class MainViewModel {

    private static DataSource dataSource;

    public MainViewModel(Context context){
        dataSource = DataSource.getInstance(context);
    }

    public float updateBudget(double budget) {
        double updatedBudget = budget;
        for(Transaction d: dataSource.getAllTransactions()){
            if(d instanceof Purchase) {
                updatedBudget = updatedBudget - d.getCost();
            }
        }
        return Float.parseFloat(String.valueOf(budget));
    }
}
