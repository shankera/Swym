package mocks;

import com.swym.app.data.Deposit;
import com.swym.app.data.IDataSource;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;

import java.util.ArrayList;
import java.util.List;

public class DataSourceMock implements IDataSource {
    private double budgetGoal = 0.00;

    private List<Transaction> dataSource;

    public DataSourceMock(){
        dataSource = new ArrayList<>();
    }
    public double getBudgetGoal() {
        return budgetGoal;
    }
    public void setBudgetGoal(double budgetGoal) {
        this.budgetGoal = budgetGoal;
    }

    @Override
    public double getBalance() {
        return 0;
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

    @Override
    public void createTransaction(Transaction transaction, TransactionType type) {

    }

    public void deleteTransaction(Transaction t){
        dataSource.remove(t);
    }
    public List<Transaction> getAllTransactions(){
        return dataSource;
    }
}