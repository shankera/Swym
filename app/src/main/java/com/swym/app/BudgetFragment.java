package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swym.app.data.Fund;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionDataSource;
import com.swym.app.popups.AddFundsActivity;
import com.swym.app.popups.AddPurchaseActivity;
import com.swym.app.popups.SetBudgetActivity;
import com.swym.app.viewmodels.BudgetViewModel;

import java.text.NumberFormat;
import java.util.List;

public class BudgetFragment extends Fragment {
    private final int purchaseRequestCode = 309;
    private final int fundsRequestCode = 515;
    private final int budgetRequestCode = 801;

    private View v;
    private TextView bs;
    private TextView fs;
    private SharedPreferences myPrefs;
    private BudgetViewModel viewModel;
    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        viewModel = new BudgetViewModel(myPrefs.getFloat("Budget", 0.00f));
        //OnClickListener for the add purchase button
        view.findViewById(R.id.addPurchase).setOnClickListener(v -> {
            Intent addIntent = new Intent(getActivity(), AddPurchaseActivity.class);
            startActivityForResult(addIntent, purchaseRequestCode);
        });

        //OnClickListener for the add funds button
        view.findViewById(R.id.addFunds).setOnClickListener(v -> {
            Intent addFunds = new Intent(getActivity(), AddFundsActivity.class);
            startActivityForResult(addFunds,fundsRequestCode);

        });

        myPrefs = getActivity().getApplicationContext().getSharedPreferences("com.swym.app", Activity.MODE_PRIVATE);

        viewModel.firstTimeRun = myPrefs.getBoolean("FirstTime", true);

        if(viewModel.firstTimeRun){
            viewModel.firstTimeRun = false;
            SharedPreferences.Editor edit = myPrefs.edit();
            edit.putBoolean("FirstTime", false);
            edit.commit();
            Intent setBudget = new Intent(getActivity(), SetBudgetActivity.class);
            startActivityForResult(setBudget,budgetRequestCode);
        }

        //copies all transactions from the datasource

        transactions = datasource.getAllTransactions();
        v = view;

        bs = (TextView) v.findViewById(R.id.budgetshow);
        fs = (TextView) v.findViewById(R.id.balanceshow);

        double updatedBudget = budgetVal;

            if(transactions != null) {
            updateTextView(fmt);
            fs.setText(fmt.format(nomoney));
        }
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        SharedPreferences.Editor edit = myPrefs.edit();
        switch(requestCode){
            case(purchaseRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    viewModel.addPurchase((Purchase) intent.getExtras().getSerializable("Purchase"));
                    updateTextView(NumberFormat.getCurrencyInstance());
                    edit.commit();
                }
                break;
            case(fundsRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    viewModel.addFund((Fund) intent.getExtras().getSerializable("Fund"));
                    double fundsBudget = viewModel.getFunds();
                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsBudget)));
                    edit.commit();
                    fs.setText(fmt.format(fundsBudget));
                }
                break;
            case(budgetRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    this.budgetVal = intent.getExtras().getDouble("Budget");
                    updateTextView(fmt);
                }
                edit.putFloat("Budget", Float.parseFloat(String.valueOf(budgetVal)));
                edit.commit();
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        viewModel.budget = myPrefs.getFloat("Budget", 0.00f);
        updateTextView(NumberFormat.getCurrencyInstance());
    }
    //calculates any expenditures and deducts from the budget then updates the textview
    private void updateTextView(NumberFormat fmt){
        double fundsBudget = 0.00;
        transactions = datasource.getAllTransactions();
        for(Transaction d: transactions) {
            if(d instanceof Purchase) {
                updatedBudget = updatedBudget - d.getCost();
                fundsBudget -= d.getCost();
            }
            else{
                fundsBudget += d.getCost();
            }
        }

        if(updatedBudget<=0.00){
            bs.setTextColor(Color.RED);
        }else if(updatedBudget < (.25*budgetVal)){
            bs.setTextColor(Color.YELLOW);
        }
        else{
            bs.setTextColor(Color.GREEN);
        }
        bs.setText(fmt.format(updatedBudget));
        fs.setText(fmt.format(fundsBudget));

    }

}
