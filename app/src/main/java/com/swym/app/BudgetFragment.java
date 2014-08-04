package com.swym.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BudgetFragment extends Fragment {
    private final int requestCode = 309;
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
                startActivityForResult(addIntent, requestCode);
            }
        });
        view.findViewById(R.id.addFunds).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addFunds = new Intent(getActivity(), AddFunds.class);
                startActivityForResult(addFunds,requestCode);
            }
        });
        return view;
    }
}
