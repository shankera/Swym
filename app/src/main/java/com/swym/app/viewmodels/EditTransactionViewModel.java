package com.swym.app.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.swym.app.R;
import com.swym.app.data.Deposit;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;
import com.swym.app.utilities.ResourceProvider;

public class EditTransactionViewModel extends ViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> sourceHint = new MutableLiveData<>();
    public MutableLiveData<String> amountHint = new MutableLiveData<>();
    public MutableLiveData<String> descriptionHint = new MutableLiveData<>();
    private ResourceProvider resourceProvider;
    private TransactionType transactionType;

    public EditTransactionViewModel(Context context, TransactionType transactionType) {
        this.resourceProvider = new ResourceProvider(context);
        this.transactionType = transactionType;

        if (transactionType == TransactionType.WITHDRAWAL) {
            this.title.postValue(this.resourceProvider.getString(R.string.title_activity_withdrawal));
            this.sourceHint.postValue(this.resourceProvider.getString(R.string.WithdrawalSourceHint));
            this.amountHint.postValue(this.resourceProvider.getString(R.string.WithdrawalAmountHint));
            this.descriptionHint.postValue(this.resourceProvider.getString(R.string.WithdrawalDescriptionHint));
        } else if (transactionType == TransactionType.DEPOSIT) {
            this.title.postValue(this.resourceProvider.getString(R.string.title_activity_deposit));
            this.sourceHint.postValue(this.resourceProvider.getString(R.string.DepositSourceHint));
            this.amountHint.postValue(this.resourceProvider.getString(R.string.DepositAmountHint));
            this.descriptionHint.postValue(this.resourceProvider.getString(R.string.DepositDescriptionHint));

        }

    }

    public Transaction getTransaction() {
        Transaction transaction = null;
        if (this.transactionType == TransactionType.DEPOSIT) {
            transaction = new Deposit();
        } else if (this.transactionType == TransactionType.WITHDRAWAL) {
            transaction = new Withdrawal();
        }
        return transaction;
    }
}
