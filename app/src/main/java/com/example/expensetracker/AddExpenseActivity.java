package com.example.expensetracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenseActivity extends AppCompatActivity {
    EditText etTitle, etAmount, etCategory, etDate;

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);

        auth = FirebaseAuth.getInstance();

// Reference to Expenses node
        databaseReference = FirebaseDatabase.getInstance().getReference("Expenses");
    }

    public void saveExpense(View view) {

        String title = etTitle.getText().toString().trim();
        String amount = etAmount.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        // VALIDATION

        if (title.isEmpty()) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return;
        }

        if (amount.isEmpty()) {
            etAmount.setError("Amount is required");
            etAmount.requestFocus();
            return;
        }

        if (category.isEmpty()) {
            etCategory.setError("Category is required");
            etCategory.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            etDate.setError("Date is required");
            etDate.requestFocus();
            return;
        }

        // Get current user
        String userId = auth.getCurrentUser().getUid();

        // Generate unique ID
        String expenseId = databaseReference.child(userId).push().getKey();

        // Create object
        Expense expense = new Expense(expenseId, title, amount, category, date);

        // Save to Firebase
        databaseReference.child(userId).child(expenseId)
                .setValue(expense)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();

                        // Clear form
                        etTitle.setText("");
                        etAmount.setText("");
                        etCategory.setText("");
                        etDate.setText("");

                    } else {
                        Toast.makeText(this, "Failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}