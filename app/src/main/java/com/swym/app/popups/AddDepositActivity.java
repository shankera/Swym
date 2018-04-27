package com.swym.app.popups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swym.app.R;
import com.swym.app.data.Deposit;
import com.swym.app.data.TransactionType;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDepositActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);
        TextView moneySign = findViewById(R.id.moneySign);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        moneySign.setText(String.format("%s", fmt.format(0.00).charAt(0)));
        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
        findViewById(R.id.saveButton).setOnClickListener(view -> {
            Intent data = new Intent();
            Deposit deposit = new Deposit();
            EditText purchaseField = (EditText) findViewById(R.id.enterPurchase);
            EditText costField = (EditText) findViewById(R.id.enterCost);
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            EditText descField = (EditText) findViewById(R.id.enterDescription);

            if (!purchaseField.getText().toString().isEmpty() && !costField.getText().toString().isEmpty()) {
                if (!descField.getText().toString().isEmpty()) {
                    deposit.setDescription(descField.getText().toString());
                }
                deposit.setCost(Double.parseDouble(costField.getText().toString()));
                deposit.setName(purchaseField.getText().toString());
                Date now = new Date();
                String date = new SimpleDateFormat("yyyymm").format(now);
                deposit.setDate(Integer.parseInt(date));
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                deposit.setRealDate(month + "/" + day + "/" + year);
                data.putExtra(TransactionType.DEPOSIT.toString(), deposit);
                setResult(Activity.RESULT_OK, data);
                finish();
            } else {
                Toast t = Toast.makeText(getApplication(), getString(R.string.funds_toast), Toast.LENGTH_SHORT);
                t.show();
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
