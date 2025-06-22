package com.example.budgetmanager.data_local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetmanager.model.Income;

import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Query("SELECT * FROM income_table ORDER BY id DESC")
    LiveData<List<Income>> getAllIncomes();

    @Delete
    void delete(Income income);


}
