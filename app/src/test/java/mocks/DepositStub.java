package mocks;

import com.swym.app.data.Deposit;
import com.swym.app.data.TransactionType;

public class DepositStub extends Deposit {
    public DepositStub(String name, double amount){
        super(name, amount, "", 0, TransactionType.DEPOSIT, "");
    }
}
