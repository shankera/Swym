package com.swym.app.popups;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.swym.app.MainActivity;
import com.swym.app.data.Purchase;
import com.swym.app.R;
import com.swym.app.data.TransactionDataSource;

import java.io.Serializable;

public class AddPurchase extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
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
                EditText purchaseField = (EditText) findViewById(R.id.enterPurchase);
                EditText costField = (EditText) findViewById(R.id.enterCost);
                EditText descField = (EditText) findViewById(R.id.enterDescription);
                if(!purchaseField.getText().toString().equals("") && !costField.getText().toString().equals("")){
                    if(!descField.getText().toString().equals("")){
                        p.setDescription(descField.getText().toString());
                    }
                    p.setCost(Double.parseDouble(costField.getText().toString()));
                    p.setName(purchaseField.getText().toString());
                    data.putExtra("Purchase", (Serializable) p);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }else{
                    Toast t = Toast.makeText(getApplication(), "Purchase and Cost cannot be empty", Toast.LENGTH_SHORT);
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
