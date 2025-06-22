package com.example.budgetmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetmanager.data_local.AppDatabase;
import com.example.budgetmanager.data_local.IncomeDao;
import com.example.budgetmanager.model.Income;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IncomeViewModel extends AndroidViewModel {

    private final IncomeDao incomeDao;
    private final ExecutorService executorService;

    private final LiveData<List<Income>> allIncomes;


    public IncomeViewModel(@NonNull Application application) { // Contractor
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        incomeDao = db.incomeDao();
        executorService = Executors.newSingleThreadExecutor();
        allIncomes = incomeDao.getAllIncomes();

    }

    public LiveData<List<Income>> getAllIncomes() { // Getter
        return allIncomes;
    }

    public void insertIncome(Income income) {
        executorService.execute(() -> incomeDao.insert(income));
    }

    public void deleteIncome(Income income) {
        executorService.execute(() -> incomeDao.delete(income));
    }

}
