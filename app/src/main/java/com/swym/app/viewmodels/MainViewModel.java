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
}
