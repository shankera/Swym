package com.swym.app.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.swym.app.R;
import com.swym.app.data.Transaction;
import com.swym.app.data.Withdrawal;

import java.text.NumberFormat;

public class ViewTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        Intent intent = getIntent();
        Transaction t = (Transaction) intent.getSerializableExtra("Transaction");
        TextView type = findViewById(R.id.viewType);
        TextView name = findViewById(R.id.viewName);
        TextView valType = findViewById(R.id.viewValType);
        TextView value = findViewById(R.id.viewValue);
        TextView desc = findViewById(R.id.viewDescription);
        TextView realDate = findViewById(R.id.viewRealDate);
        if (t instanceof Withdrawal) {
            type.setText(getString(R.string.Purchase));
        } else {
            type.setText(getString(R.string.Source));
        }
        name.setText(t.getName());
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        valType.setText(getString(R.string.Cost));
        value.setText(fmt.format(t.getCost()));
        desc.setText(t.getDescription());
        realDate.setText(t.getRealDate());

        findViewById(R.id.closeButton).setOnClickListener(v -> finish());
        findViewById(R.id.deleteButton).setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setPositiveButton(getString(R.string.deleteButtonText), (dialogInterface, i) -> {
                Intent data = new Intent();
                Intent intent1 = getIntent();
                data.putExtra("Delete", intent1.getSerializableExtra("Transaction"));
                setResult(Activity.RESULT_OK, data);
                finish();
            });
            alertDialog.setNegativeButton(getString(R.string.cancelButtonText), null);
            alertDialog.setMessage(getString(R.string.delete_prompt));
            alertDialog.setTitle(getString(R.string.delete_confirm));
            alertDialog.show();
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
