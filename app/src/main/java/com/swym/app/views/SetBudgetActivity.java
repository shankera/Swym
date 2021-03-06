package com.swym.app.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.swym.app.R;
import com.swym.app.data.Strings;

import java.text.NumberFormat;

public class SetBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);

        TextView moneySign = findViewById(R.id.moneySign);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        moneySign.setText(String.format("%s", fmt.format(0.00).charAt(0)));

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());

        findViewById(R.id.saveButton).setOnClickListener(view -> {
            Intent data = new Intent();
            EditText budgetField = findViewById(R.id.enterBudget);
            if (!budgetField.getText().toString().equals("")) {
                data.putExtra(Strings.budgetGoalKey, Double.parseDouble(budgetField.getText().toString()));
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
