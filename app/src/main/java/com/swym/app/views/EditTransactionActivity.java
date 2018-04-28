package com.swym.app.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swym.app.R;
import com.swym.app.data.Deposit;
import com.swym.app.data.Transaction;
import com.swym.app.data.TransactionType;
import com.swym.app.data.Withdrawal;
import com.swym.app.viewmodels.CustomViewModelFactory;
import com.swym.app.viewmodels.EditTransactionViewModel;

import java.text.NumberFormat;
import java.util.Calendar;

public class EditTransactionActivity extends AppCompatActivity {
    private String dateString;
    private EditTransactionViewModel viewModel;
    private TransactionType transactionType;

    private EditText source;
    private EditText amount;
    private EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        this.transactionType = (TransactionType) this.getIntent().getSerializableExtra("TRANSACTION_TYPE");
        this.source = findViewById(R.id.source_value);
        this.amount = findViewById(R.id.amount_value);
        this.description = findViewById(R.id.description_value);
        this.viewModel = ViewModelProviders.of(this, new CustomViewModelFactory<>(this, this.transactionType))
                .get(EditTransactionViewModel.class);
        this.setViewData();
        TextView moneySign = findViewById(R.id.money_sign);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        moneySign.setText(String.format("%s", fmt.format(0.00).charAt(0)));
        findViewById(R.id.cancelButton).setOnClickListener(v -> finish());
        findViewById(R.id.pick_date_button).setOnClickListener(v -> {
            Calendar myCalendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                this.dateString = monthOfYear + "/" + dayOfMonth + "/" + year;

                TextView dateView = findViewById(R.id.deposit_date);
                dateView.setText(this.dateString);
            };
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        findViewById(R.id.saveButton).setOnClickListener(view -> {
            Intent data = new Intent();

            if (!source.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
                Transaction transaction = null;
                if (this.transactionType == TransactionType.DEPOSIT) {
                    transaction = new Deposit();
                } else if (this.transactionType == TransactionType.WITHDRAWAL) {
                    transaction = new Withdrawal();
                }
                if (transaction != null) {
                    if (!description.getText().toString().isEmpty()) {
                        transaction.setDescription(description.getText().toString());
                    }
                    transaction.setCost(Double.parseDouble(amount.getText().toString()));
                    transaction.setName(source.getText().toString());
                    transaction.setRealDate(this.dateString);
                }
                data.putExtra("TRANSACTION_TYPE", transaction);
                setResult(Activity.RESULT_OK, data);
                finish();
            } else {
                Toast t = Toast.makeText(getApplication(), getString(R.string.funds_toast), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    private void setViewData() {
        this.viewModel.title.observe(this, this::setTitle);
        this.viewModel.sourceHint.observe(this, this.source::setHint);
        this.viewModel.amountHint.observe(this, this.amount::setHint);
        this.viewModel.descriptionHint.observe(this, this.description::setHint);
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
