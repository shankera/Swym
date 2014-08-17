package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class BudgetFragment extends Fragment {
    private TransactionDataSource datasource;
    private final int purchaseRequestCode = 309;
    private final int fundsRequestCode = 515;
    private final int budgetRequestCode = 801;
    private List<Transaction> transactions;
    private View v;
    private double budgetVal;
    private double fundsVal;
    private TextView bs;
    private TextView fs;
    private SharedPreferences myPrefs;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        //OnClickListener for the add purchase button
        view.findViewById(R.id.addPurchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), AddPurchase.class);
                startActivityForResult(addIntent, purchaseRequestCode);
            }
        });

        //OnClickListener for the add funds button
        view.findViewById(R.id.addFunds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFunds = new Intent(getActivity(), AddFunds.class);
                startActivityForResult(addFunds,fundsRequestCode);
            }
        });

        //OnClickListener for the budget label
        view.findViewById(R.id.budgetshow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setBudget = new Intent(getActivity(), SetBudget.class);
                startActivityForResult(setBudget,budgetRequestCode);
            }
        });
        myPrefs = getActivity().getApplicationContext().getSharedPreferences("com.swym.app", Activity.MODE_PRIVATE);
        datasource = MainActivity.getDatasource();
        datasource.open();

        //copies all transactions from the datasource
        transactions = datasource.getAllTransactions();
        v = view;

        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        bs = (TextView) v.findViewById(R.id.budgetshow);
        fs = (TextView) v.findViewById(R.id.balanceshow);
        budgetVal = myPrefs.getFloat("Budget", 0.00f);
        fundsVal = myPrefs.getFloat("Fund", 0.00f);

        double updatedBudget = budgetVal;

        if(transactions != null) {
            //calculates any expenditures and deducts from the budget
            for (Transaction d : transactions) {
                if (d instanceof Purchase) {
                    updatedBudget = updatedBudget - d.getCost();
                }
            }

            bs.setText(fmt.format(updatedBudget));
            fs.setText(fmt.format(fundsVal));
        }
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
                    transactions.add(p);
                    datasource.createTransaction(p.getName(), p.getCost(), p.getDescription(), "Purchase");
                    double updatedBudget = budgetVal;
                    double fundsBudget = 0.00;
                    for(Transaction d: transactions){
                        if(d instanceof Purchase) {
                            updatedBudget = updatedBudget - d.getCost();
                            fundsBudget -= d.getCost();
                        }
                        else{
                            fundsBudget += d.getCost();
                        }
                    }
                    String str = fmt.format(updatedBudget);
                    bs.setText(str);

                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsBudget)));
                    edit.commit();
                    fs.setText(fmt.format(fundsBudget));
                }
                break;
            case(fundsRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    Fund f = (Fund) intent.getExtras().getSerializable("Fund");
                    transactions.add(f);
                    datasource.createTransaction(f.getName(), f.getCost(), f.getDescription(), "Fund");
                    double fundsBudget = 0.00;
                    for(Transaction d: transactions){
                        if(d instanceof Purchase) {
                            fundsBudget -= d.getCost();
                        }
                        else{
                            fundsBudget += d.getCost();
                        }
                    }
                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsBudget)));
                    edit.commit();
                    fs.setText(fmt.format(fundsBudget));
                }
                break;
            case(budgetRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    this.budgetVal = intent.getExtras().getDouble("Budget");
                    double updatedBudget = budgetVal;
                    for(Transaction d: transactions){
                        if(d instanceof Purchase) {
                            updatedBudget = updatedBudget - d.getCost();
                    }
                    edit.putFloat("Budget", Float.parseFloat(String.valueOf(budgetVal)));
                    edit.commit();
                    bs.setText(fmt.format(updatedBudget));
                }
                break;
            }
        }
    }
}
