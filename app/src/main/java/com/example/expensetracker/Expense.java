package com.example.expensetracker;

public class Expense {

    private String id;
    private String title;
    private String amount;
    private String category;
    private String date;

    public Expense() {} // REQUIRED

    public Expense(String id, String title, String amount, String category, String date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
}