package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionDataSource;
import com.swym.app.popups.ViewTransactionActivity;
import com.swym.app.viewmodels.LogViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogFragment extends android.support.v4.app.ListFragment {
    private LogViewModel viewModel;
    private final int viewCode = 425;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.viewModel = new LogViewModel();
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent viewIntent = new Intent(getActivity(), ViewTransactionActivity.class);
        viewIntent.putExtra("Transaction", viewModel.vms.get(viewModel.vms.size()-1-position));
        startActivityForResult(viewIntent, viewCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            viewModel.deleteTransaction((Transaction) intent.getSerializableExtra("Delete"));
        }
    }
    public void onResume(){
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, viewModel.vms);
        setListAdapter(adapter);
    }
}
