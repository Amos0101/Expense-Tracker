package com.example.expensetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ViewExpensesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Expense> expenseList;
    ExpenseAdapter adapter;

    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseList = new ArrayList<>();
        adapter = new ExpenseAdapter(expenseList);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Expenses")
                .child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                expenseList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Expense expense = dataSnapshot.getValue(Expense.class);
                    expenseList.add(expense);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}