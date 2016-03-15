package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.swym.app.data.Transaction;
import com.swym.app.popups.ViewTransactionActivity;
import com.swym.app.viewmodels.LogViewModel;

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
        viewIntent.putExtra("Transaction", viewModel.transactions.get(position));
        startActivityForResult(viewIntent, viewCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            Transaction t = (Transaction) intent.getSerializableExtra("Delete");
            viewModel.deleteTransaction(t);
        }
    }
    public void onResume(){
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, viewModel.vms);
        setListAdapter(adapter);
    }
}
