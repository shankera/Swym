package com.swym.app.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.swym.app.R;
import com.swym.app.data.TransactionType;
import com.swym.app.utilities.ResourceProvider;

public class EditTransactionViewModel extends ViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> sourceHint = new MutableLiveData<>();
    public MutableLiveData<String> amountHint = new MutableLiveData<>();
    public MutableLiveData<String> descriptionHint = new MutableLiveData<>();
    private ResourceProvider resourceProvider;

    public EditTransactionViewModel(Context context, TransactionType transactionType) {
        this.resourceProvider = new ResourceProvider(context);

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
}
