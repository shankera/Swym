package com.swym.app.popups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swym.app.MainActivity;
import com.swym.app.data.Fund;
import com.swym.app.R;
import com.swym.app.data.TransactionDataSource;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arjun on 8/4/2014.
 */
public class AddFunds extends ActionBarActivity {
    private final double moneySignFormatValue = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);
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
                Fund f = new Fund();
                EditText purchaseField = (EditText) findViewById(R.id.enterPurchase);
                EditText costField = (EditText) findViewById(R.id.enterCost);
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                EditText descField = (EditText) findViewById(R.id.enterDescription);
                if(!purchaseField.getText().toString().equals("") && !costField.getText().toString().equals("")){
                    if(!descField.getText().toString().equals("")){
                        f.setDescription(descField.getText().toString());
                    }
                    f.setCost(Double.parseDouble(costField.getText().toString()));
                    f.setName(purchaseField.getText().toString());
                    Date now = new Date();
                    String date = new SimpleDateFormat("yyyymm").format(now);
                    f.setDate(Integer.parseInt(date));
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    f.setRealDate(month + "/" + day+ "/"+year);
                    data.putExtra("Fund", (Serializable) f);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }else{
                    Toast t = Toast.makeText(getApplication(), "Income and Cost cannot be empty", Toast.LENGTH_SHORT);
                    t.show();
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
