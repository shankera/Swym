package com.swym.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class BudgetFragment extends Fragment {
    private final int purchaseRequestCode = 309;
    private final int fundsRequestCode = 515;
    private final int budgetRequestCode = 801;
    private ArrayList<Datum> data = new ArrayList<Datum>();
    private View v;
    private double budgetVal;
    private double fundsVal;
    private TextView bs;
    private TextView fs;
    private SharedPreferences myPrefs;
    private boolean bool = false;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        view.findViewById(R.id.addPurchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), AddPurchase.class);
                startActivityForResult(addIntent, purchaseRequestCode);
            }
        });
        view.findViewById(R.id.addFunds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFunds = new Intent(getActivity(), AddFunds.class);
                startActivityForResult(addFunds,fundsRequestCode);
            }
        });
        view.findViewById(R.id.budgetshow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setBudget = new Intent(getActivity(), SetBudget.class);
                startActivityForResult(setBudget,budgetRequestCode);
            }
        });
        myPrefs = getActivity().getApplicationContext().getSharedPreferences("com.swym.app", Activity.MODE_PRIVATE);

        v = view;

        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        bs = (TextView) v.findViewById(R.id.budgetshow);
        budgetVal = myPrefs.getFloat("Budget",0.00f);
        fundsVal = myPrefs.getFloat("Fund",0.00f);

        if(bs != null)
            bs.setText(fmt.format(budgetVal));
        fs = (TextView) v.findViewById(R.id.balanceshow);

        if(fs != null)
            fs.setText(fmt.format(fundsVal));
        bool = true;
        Log.e(bool + "", budgetVal + "");
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(bool){

            NumberFormat fmt = NumberFormat.getCurrencyInstance();
            budgetVal = myPrefs.getFloat("Budget", 0.00f);
            double updatedBudget = budgetVal;
            for(Datum d: data){
                if(d instanceof Purchase)
                    updatedBudget = updatedBudget - d.getCost();
            }
            bs.setText(fmt.format(updatedBudget));

            fundsVal = myPrefs.getFloat("Fund", 0.00f);
            fs.setText(fmt.format(fundsVal));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        SharedPreferences.Editor edit = myPrefs.edit();
        switch(requestCode){
            case(purchaseRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    Purchase p = (Purchase) intent.getExtras().getSerializable("Purchase");
                    data.add(p);
                    double updatedBudget = budgetVal;
                    for(Datum d: data){
                        if(d instanceof Purchase)
                            updatedBudget = updatedBudget - d.getCost();
                    }
                    fundsVal -=p.getCost();
                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsVal)));
                    edit.commit();
                    String str = fmt.format(updatedBudget);
                    bs.setText(str);
                    fs.setText(fmt.format(fundsVal));
                }
                break;
            case(fundsRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    Fund f = (Fund) intent.getExtras().getSerializable("Fund");
                    data.add(f);
                    fundsVal += f.getCost();
                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsVal)));
                    edit.commit();
                    fs.setText(fmt.format(fundsVal));

                }
                break;
            case(budgetRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    this.budgetVal = intent.getExtras().getDouble("Budget");
                    edit.putFloat("Budget", Float.parseFloat(String.valueOf(budgetVal)));
                    edit.commit();
                    bs.setText(fmt.format(budgetVal));

                    Log.e(bool + "", budgetVal + "");
                }
                break;
        }
    }

}
