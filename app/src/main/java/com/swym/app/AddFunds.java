package com.swym.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Arjun on 8/4/2014.
 */
public class AddFunds extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);
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
                EditText descField = (EditText) findViewById(R.id.enterDescription);
                if(!purchaseField.getText().toString().equals("") && !costField.getText().toString().equals("")){
                    if(!descField.getText().toString().equals("")){
                        f.setDescription(descField.getText().toString());
                    }
                    f.setCost(Double.parseDouble(costField.getText().toString()));
                    f.setName(purchaseField.getText().toString());
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

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_purchase, menu);
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
