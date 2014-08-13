package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private double budgetVal = 0.00;
    public BudgetFragment() {
    }

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
        v = view;
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        TextView bs = (TextView) v.findViewById(R.id.budgetshow);
        switch(requestCode){
            case(purchaseRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    Purchase p = (Purchase) intent.getExtras().getSerializable("Purchase");
                    data.add(p);
                    double updatedBudget = budgetVal;
                    for(Datum d: data){
                        updatedBudget = updatedBudget - d.getCost();
                    }
                    NumberFormat fmt = NumberFormat.getCurrencyInstance();

                    String str = fmt.format(updatedBudget);
                    bs.setText(str);
                }
            case(fundsRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    Fund f = (Fund) intent.getExtras().getSerializable("Fund");

                }
            case(budgetRequestCode):
                if(resultCode == Activity.RESULT_OK){
                    budgetVal = intent.getExtras().getDouble("Budget");

                    NumberFormat fmt = NumberFormat.getCurrencyInstance();
                    bs.setText(fmt.format(budgetVal));
                }
        }
    }


}
