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

import com.swym.app.data.Purchase;
import com.swym.app.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPurchaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);


        TextView moneySign = (TextView) findViewById(R.id.moneySign);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        double moneySignFormatValue = 0.00;
        moneySign.setText(String.format("%s", fmt.format(moneySignFormatValue).charAt(0)));

        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
        findViewById(R.id.saveButton).setOnClickListener(view -> {
            Intent data = new Intent();
            Purchase p = new Purchase();
            EditText purchaseField = (EditText) findViewById(R.id.enterPurchase);
            EditText costField = (EditText) findViewById(R.id.enterCost);
            EditText descField = (EditText) findViewById(R.id.enterDescription);
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            if(!purchaseField.getText().toString().equals("") && !costField.getText().toString().equals("")){
                if(!descField.getText().toString().equals("")){
                    p.setDescription(descField.getText().toString());
                }
                p.setCost(Double.parseDouble(costField.getText().toString()));
                p.setName(purchaseField.getText().toString());

                Date now = new Date();
                String date = new SimpleDateFormat("yyyymm").format(now);
                p.setDate(Integer.parseInt(date));
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                p.setRealDate(month + "/" + day+ "/"+year);
                data.putExtra("Purchase", p);
                setResult(Activity.RESULT_OK, data);
                finish();
            }else{
                Toast t = Toast.makeText(getApplication(), getString(R.string.purchase_toast), Toast.LENGTH_SHORT);
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
