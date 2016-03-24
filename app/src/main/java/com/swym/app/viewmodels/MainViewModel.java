package com.swym.app.viewmodels;

import android.content.Context;

import com.swym.app.data.DataSource;

public class MainViewModel {

    private static DataSource dataSource;

    public MainViewModel(Context context){
        dataSource = DataSource.getInstance(context);
    }
}
