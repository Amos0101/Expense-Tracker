package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        auth = FirebaseAuth.getInstance();
    }

    public void goToAddExpense(View view) {
        startActivity(new Intent(this, AddExpenseActivity.class));
    }

    public void goToViewExpenses(View view) {
        startActivity(new Intent(this, ViewExpensesActivity.class));
    }

    public void logoutUser(View view) {
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}