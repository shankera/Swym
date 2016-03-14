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
import com.swym.app.popups.AddFundsActivity;
import com.swym.app.popups.AddPurchaseActivity;
import com.swym.app.popups.SetBudgetActivity;
import com.swym.app.viewmodels.BudgetViewModel;

import java.text.NumberFormat;

public class BudgetFragment extends Fragment {
    private final int purchaseRequestCode = 309;
    private final int fundsRequestCode = 515;
    private final int budgetRequestCode = 801;
    private View view;
    private TextView bs;
    private TextView fs;
    private SharedPreferences myPrefs;
    private BudgetViewModel viewModel;
    private final String budgetKey = "Budget";
    private final String budgetGoalKey = "BudgetGoal";
    private final String balanceKey = "Balance";
    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        this.view = view;
        myPrefs = getActivity().getApplicationContext().getSharedPreferences("com.swym.app", Activity.MODE_PRIVATE);
        viewModel = new BudgetViewModel(myPrefs.getFloat(budgetKey, 0.00f), myPrefs.getFloat("Balance", 0.00f));
        myPrefs.edit().putFloat(budgetKey, (float) viewModel.budget).apply();
        myPrefs.edit().putFloat(balanceKey, (float) viewModel.balance).apply();
        myPrefs.edit().putFloat(budgetGoalKey, (float) (viewModel.budgetGoal)).apply();

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

        viewModel.firstTimeRun = myPrefs.getBoolean("FirstTime", true);

        if(viewModel.firstTimeRun){
            viewModel.firstTimeRun = false;
            SharedPreferences.Editor edit = myPrefs.edit();
            edit.putBoolean("FirstTime", false);
            edit.apply();
            Intent setBudget = new Intent(getActivity(), SetBudgetActivity.class);
            startActivityForResult(setBudget,budgetRequestCode);
        }

        bs = (TextView) view.findViewById(R.id.budgetshow);
        fs = (TextView) view.findViewById(R.id.balanceshow);

        updateTextView(NumberFormat.getCurrencyInstance());
        return view;
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
                    edit.apply();
                }
                break;
            case(fundsRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    viewModel.addFund((Fund) intent.getExtras().getSerializable("Fund"));
                    double fundsBudget = viewModel.balance;
                    edit.putFloat("Fund", Float.parseFloat(String.valueOf(fundsBudget))).apply();
                    fs.setText(fmt.format(fundsBudget));
                }
                break;
            case(budgetRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    viewModel.updateBudget(intent.getExtras().getDouble(budgetKey));
                    updateTextView(fmt);
                }
                edit.putFloat(budgetGoalKey, Float.parseFloat(String.valueOf(viewModel.budgetGoal))).apply();
                edit.putFloat(budgetKey, Float.parseFloat(String.valueOf(viewModel.budget))).apply();
                edit.apply();
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        viewModel.budget = myPrefs.getFloat(budgetKey, 0.00f);
        viewModel.balance = myPrefs.getFloat(balanceKey, 0.00f);
        updateTextView(NumberFormat.getCurrencyInstance());
    }
    //calculates any expenditures and deducts from the budget then updates the textview
    private void updateTextView(NumberFormat fmt){
        double balance = viewModel.balance;
        double budget = viewModel.budget;

        if(budget<=0.00){
            bs.setTextColor(Color.RED);
        }else if(budget < (.25 * viewModel.budgetGoal)){
            bs.setTextColor(Color.YELLOW);
        }
        else{
            bs.setTextColor(Color.GREEN);
        }
        bs.setText(fmt.format(viewModel.budget));
        fs.setText(fmt.format(viewModel.balance));
    }

}
