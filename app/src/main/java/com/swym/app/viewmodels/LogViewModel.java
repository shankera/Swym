package com.swym.app.viewmodels;

import com.swym.app.data.DataSource;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

public class LogViewModel {
    private DataSource dataSource;
    public ArrayList<String> vms;
    public List<Transaction> transactions;
    public LogViewModel() {
        this.dataSource = DataSource.getInstance();
        vms = new ArrayList<>();
        this.transactions = dataSource.getAllTransactions();
        Collections.reverse(transactions);
        Observable.from(this.transactions)
                .map(t -> {
                    createViewModel(t);
                    return t;
                }).subscribe();
    }
    private void createViewModel(Transaction transaction){
        vms.add(viewModelString(transaction));
    }
    private String viewModelString(Transaction transaction) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String outputString;
        if(transaction instanceof Purchase)
            outputString = "-";
        else
            outputString = "+";
        outputString+=fmt.format(transaction.getCost()) + " - " + transaction.getName();
        return outputString;
    }

    public void deleteTransaction(Transaction transaction) {
        int index = vms.indexOf(this.viewModelString(transaction));
        vms.remove(index);
        transactions.remove(index);
        dataSource.deleteTransaction(transaction);
    }
}
