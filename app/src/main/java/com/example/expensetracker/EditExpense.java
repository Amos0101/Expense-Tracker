package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditExpense extends AppCompatActivity {

    EditText etTitle, etAmount, etCategory, etDate;

    DatabaseReference databaseReference;
    FirebaseAuth auth;

    String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);

        auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Expenses")
                .child(userId);

        // RECEIVE DATA
        expenseId = getIntent().getStringExtra("id");

        etTitle.setText(getIntent().getStringExtra("title"));
        etAmount.setText(getIntent().getStringExtra("amount"));
        etCategory.setText(getIntent().getStringExtra("category"));
        etDate.setText(getIntent().getStringExtra("date"));
    }

    public void updateExpense(View view) {

        String title = etTitle.getText().toString().trim();
        String amount = etAmount.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        if (title.isEmpty() || amount.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Expense updatedExpense = new Expense(expenseId, title, amount, category, date);

        databaseReference.child(expenseId)
                .setValue(updatedExpense)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    public void deleteExpense(View view) {

        databaseReference.child(expenseId)
                .removeValue()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (d, i) -> deleteExpense(null))
                .setNegativeButton("No", null)
                .show();
    }
}