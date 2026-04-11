package com.example.expensetracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAmount, tvCategory, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Expense expense = expenseList.get(position);

        holder.tvTitle.setText(expense.getTitle());
        holder.tvAmount.setText("Amount: " + expense.getAmount());
        holder.tvCategory.setText("Category: " + expense.getCategory());
        holder.tvDate.setText("Date: " + expense.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditExpense.class);

            intent.putExtra("id", expense.getId());
            intent.putExtra("title", expense.getTitle());
            intent.putExtra("amount", expense.getAmount());
            intent.putExtra("category", expense.getCategory());
            intent.putExtra("date", expense.getDate());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

}