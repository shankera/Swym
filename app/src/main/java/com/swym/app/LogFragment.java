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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arjun on 8/18/2014.
 */
public class LogFragment extends android.support.v4.app.ListFragment {
    private TransactionDataSource dataSource;
    private List<String> values;
    private final int viewCode = 425;

    private List<Transaction> data;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        dataSource = MainActivity.getDatasource();
        dataSource.open();

        doIt();
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent viewIntent = new Intent(getActivity(), ViewTransactionActivity.class);
        int indexes = data.size()-1;

        viewIntent.putExtra("Transaction", data.get(indexes - position));
        startActivityForResult(viewIntent, viewCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == Activity.RESULT_OK) {
            dataSource.deleteTransaction((Transaction) intent.getSerializableExtra("Delete"));
        }
    }
    public void onResume(){
        super.onResume();
        doIt();
    }
    private void doIt(){
        data = dataSource.getAllTransactions();
        values = new ArrayList<String>();
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        for(Transaction d : data){
            if(d instanceof Purchase)
                values.add("-"+fmt.format(d.getCost()) + " - " + d.getName());
            else
                values.add("+"+fmt.format(d.getCost()) + " - " + d.getName());
        }
        Collections.reverse(values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
