package com.example.budgetmanager.data_local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetmanager.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Query("SELECT * FROM expense_table ORDER BY id DESC")
    LiveData<List<Expense>> getAllExpenses();

    @Delete
    void delete(Expense expense);

}
