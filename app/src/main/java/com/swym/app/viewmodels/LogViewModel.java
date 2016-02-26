package com.swym.app.viewmodels;

import com.swym.app.data.DataSource;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;
import java.text.NumberFormat;
import java.util.ArrayList;

import rx.Observable;

public class LogViewModel {
    private DataSource dataSource;
    public ArrayList<String> vms;
    public LogViewModel() {
        this.dataSource = DataSource.getInstance();
        vms = new ArrayList<>();
        dataSource.getAllTransactions();
        Observable.from(dataSource.getAllTransactions())
                .map(t -> {
                    createViewModel(t);
                    return t;
                });
    }
    private void createViewModel(Transaction transaction){
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String outputString;
        if(transaction instanceof Purchase)
            outputString = "-";
        else
            outputString = "+";
        outputString+=fmt.format(transaction.getCost()) + " - " + transaction.getName();
        vms.add(0, outputString);
    }
    public void deleteTransaction(Transaction transaction) {
        dataSource.deleteTransaction(transaction);
    }
}
