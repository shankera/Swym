package com.swym.app.views.main;

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

import com.swym.app.R;
import com.swym.app.data.DataSource;
import com.swym.app.data.Deposit;
import com.swym.app.data.Strings;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;
import com.swym.app.viewmodels.main.BudgetViewModel;
import com.swym.app.views.EditTransactionActivity;
import com.swym.app.views.SetBudgetActivity;

import java.text.NumberFormat;

public class BudgetFragment extends Fragment {
    private final int withdrawalRequestCode = 309;
    private final int depositRequestCode = 515;
    private final int budgetRequestCode = 801;
    private TextView bs;
    private TextView fs;
    private SharedPreferences myPrefs;
    private BudgetViewModel viewModel;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        myPrefs = getActivity().getApplicationContext().getSharedPreferences("com.swym.app", Activity.MODE_PRIVATE);
        viewModel = new BudgetViewModel(myPrefs.getFloat(Strings.budgetKey, 0.00f),
                myPrefs.getFloat(Strings.balanceKey, 0.00f),
                myPrefs.getFloat(Strings.budgetGoalKey, 0.00f),
                DataSource.getInstance());
        myPrefs.edit().putFloat(Strings.budgetKey, (float) viewModel.budget).apply();
        myPrefs.edit().putFloat(Strings.balanceKey, (float) viewModel.balance).apply();
        myPrefs.edit().putFloat(Strings.budgetGoalKey, (float) viewModel.getBudgetGoal()).apply();

        //OnClickListener for the add purchase button
        view.findViewById(R.id.addPurchase).setOnClickListener(v -> {
            Intent addWithdrawal = new Intent(getActivity(), EditTransactionActivity.class);
            addWithdrawal.putExtra("TRANSACTION_TYPE", TransactionType.WITHDRAWAL);
            startActivityForResult(addWithdrawal, withdrawalRequestCode);
        });

        //OnClickListener for the add funds button
        view.findViewById(R.id.addFunds).setOnClickListener(v -> {
            Intent addDeposit = new Intent(getActivity(), EditTransactionActivity.class);
            addDeposit.putExtra("TRANSACTION_TYPE", TransactionType.DEPOSIT);
            startActivityForResult(addDeposit, depositRequestCode);

        });

        view.findViewById(R.id.budgetshow).setOnClickListener(v -> {
            Intent setBudget = new Intent(getActivity(), SetBudgetActivity.class);
            startActivityForResult(setBudget, budgetRequestCode);
        });

        bs = view.findViewById(R.id.budgetshow);
        fs = view.findViewById(R.id.balanceshow);

        updateTextView(NumberFormat.getCurrencyInstance());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        SharedPreferences.Editor edit = myPrefs.edit();
        switch (requestCode) {
            case (withdrawalRequestCode):
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.addWithdrawal((Withdrawal) intent.getSerializableExtra("TRANSACTION_TYPE"));
                    updateTextView(NumberFormat.getCurrencyInstance());
                    myPrefs.edit().putFloat(Strings.budgetKey, (float) viewModel.budget).apply();
                    myPrefs.edit().putFloat(Strings.balanceKey, (float) viewModel.balance).apply();
                }
                break;
            case (depositRequestCode):
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.addDeposit((Deposit) intent.getSerializableExtra("TRANSACTION_TYPE"));
                    myPrefs.edit().putFloat(Strings.balanceKey, (float) viewModel.balance).apply();
                    fs.setText(fmt.format(viewModel.balance));
                }
                break;
            case (budgetRequestCode):
                if (resultCode == Activity.RESULT_OK) {
                    double newBudgetGoal = intent.getDoubleExtra(Strings.budgetGoalKey, 0.00);
                    viewModel.updateBudget(newBudgetGoal);
                    edit.putFloat(Strings.budgetGoalKey, (float) viewModel.getBudgetGoal()).apply();
                    updateTextView(fmt);
                }
                edit.putFloat(Strings.budgetGoalKey, Float.parseFloat(String.valueOf(viewModel.getBudgetGoal()))).apply();
                edit.putFloat(Strings.budgetKey, Float.parseFloat(String.valueOf(viewModel.budget))).apply();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.updateBudget(DataSource.getInstance().getBudgetGoal());
        viewModel.balance = DataSource.getInstance().getBalance();
        myPrefs.edit().putFloat(Strings.budgetKey, (float) viewModel.budget).apply();
        myPrefs.edit().putFloat(Strings.balanceKey, (float) viewModel.balance).apply();
        myPrefs.edit().putFloat(Strings.budgetGoalKey, (float) viewModel.getBudgetGoal()).apply();
        updateTextView(NumberFormat.getCurrencyInstance());
    }

    //calculates any expenditures and deducts from the budget then updates the TextView
    private void updateTextView(NumberFormat fmt) {
        bs.setTextColor(viewModel.getColor(viewModel.budget));
        bs.setText(fmt.format(viewModel.budget));
        fs.setText(fmt.format(viewModel.balance));
    }
}
