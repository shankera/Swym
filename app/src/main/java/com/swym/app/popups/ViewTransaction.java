package com.swym.app.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.swym.app.R;
import com.swym.app.data.Purchase;
import com.swym.app.data.Transaction;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * Created by Arjun on 8/18/2014.
 */
public class ViewTransaction extends ActionBarActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        Intent intent = getIntent();
        Transaction t = (Transaction) intent.getSerializableExtra("Transaction");
        TextView type = (TextView) findViewById(R.id.viewType);
        TextView name = (TextView) findViewById(R.id.viewName);
        TextView valType = (TextView) findViewById(R.id.viewValType);
        TextView value = (TextView) findViewById(R.id.viewValue);
        TextView desc = (TextView) findViewById(R.id.viewDescription);
        TextView realDate = (TextView) findViewById(R.id.viewRealDate);
        if(t instanceof Purchase) {
            type.setText("Purchase");
        }else{
            type.setText("Income");
        }
        name.setText(t.getName());
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        valType.setText("Amount");
        value.setText(fmt.format(t.getCost()));
        desc.setText(t.getDescription());
        realDate.setText(t.getRealDate());

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewTransaction.this);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent data = new Intent();
                        Intent intent = getIntent();
                        data.putExtra("Delete", intent.getSerializableExtra("Transaction"));
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No",null);
                alertDialog.setMessage("Are you sure you want to delete this transaction?");
                alertDialog.setTitle("Confirm Deletion");
                alertDialog.show();
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
