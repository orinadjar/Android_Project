package com.example.budgetmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetmanager.data_local.AppDatabase;
import com.example.budgetmanager.data_local.ExpenseDao;
import com.example.budgetmanager.model.Expense;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseViewModel extends AndroidViewModel {

    private final ExpenseDao expenseDao;
    private final ExecutorService executorService;
    private final LiveData<List<Expense>> allExpenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        expenseDao = db.expenseDao();
        executorService = Executors.newSingleThreadExecutor();
        allExpenses = expenseDao.getAllExpenses();
    }

    public void insertExpense(Expense expense) {
        executorService.execute(() -> expenseDao.insert(expense));
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public void deleteExpense(Expense expense) {
        executorService.execute(() -> expenseDao.delete(expense));
    }

}
