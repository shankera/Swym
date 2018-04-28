package com.swym.app.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.swym.app.data.TransactionType;
import com.swym.app.popups.EditTransactionViewModel;


public class CustomViewModelFactory<S> implements ViewModelProvider.Factory {
    private S value;
    private Context context;
    public CustomViewModelFactory(Context context, S value) {
        this.value = value;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return modelClass.cast(new EditTransactionViewModel(context, (TransactionType) this.value));
    }
}