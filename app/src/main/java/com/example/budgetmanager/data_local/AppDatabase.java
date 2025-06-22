package com.example.budgetmanager.data_local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.budgetmanager.model.Expense;
import com.example.budgetmanager.model.Income;

@Database(entities = {Income.class, Expense.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract IncomeDao incomeDao();

    public abstract ExpenseDao expenseDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "budget_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
