package com.swym.app.popups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.swym.app.data.DataSource;
import com.swym.app.data.Purchase;
import com.swym.app.R;

import java.text.NumberFormat;

/**
 * Created by Arjun on 8/12/2014.
 */
public class SetBudgetActivity extends ActionBarActivity {
    private final double moneySignFormatValue = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);


        TextView moneySign = (TextView) findViewById(R.id.moneySign);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        moneySign.setText(fmt.format(moneySignFormatValue).charAt(0)+"");

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                Purchase p = new Purchase();
                EditText budgetField = (EditText) findViewById(R.id.enterBudget);
                if (!budgetField.getText().toString().equals("")) {
                    DataSource.getInstance().budgetGoal = Double.parseDouble(budgetField.getText().toString());
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
