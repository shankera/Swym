package mocks;

import android.content.Context;

import com.swym.app.data.Deposit;
import com.swym.app.data.IDataSource;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionDataSource;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;

import java.util.List;

public class DataSourceMock implements IDataSource {
    private double budgetGoal = 0.00;

    private List<Transaction> dataSource;

    public double getBudgetGoal() {
        return budgetGoal;
    }
    public void setBudgetGoal(double budgetGoal) {
        this.budgetGoal = budgetGoal;
    }

    public void createTransaction(String name, double cost, String desc, int date, TransactionType type, String realDate){
        switch (type){
            case WITHDRAWAL:
                dataSource.add(new Withdrawal(name, cost, desc,date,type,realDate));
                break;
            case DEPOSIT:
                dataSource.add(new Deposit(name, cost, desc,date,type,realDate));
                break;
        }
    }
    public void deleteTransaction(Transaction t){
        dataSource.remove(t);
    }
    public List<Transaction> getAllTransactions(){
        return dataSource;
    }
}