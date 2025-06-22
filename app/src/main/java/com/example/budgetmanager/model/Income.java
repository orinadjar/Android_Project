package com.example.budgetmanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "income_table")
public class Income {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private double amount;

    // Constructor
    public Income(String title, double amount) {
        this.title = title;
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
