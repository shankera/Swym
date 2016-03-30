package mocks;

import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;

public class WithdrawalStub extends Withdrawal {
    public WithdrawalStub(String name, double amount){
        super(name, amount, "", 0, TransactionType.WITHDRAWAL, "");
    }
}
