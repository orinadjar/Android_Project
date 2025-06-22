package com.example.budgetmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanager.model.Expense;
import com.example.budgetmanager.viewmodel.ExpenseViewModel;
import com.example.budgetmanager.ui_expense.ExpenseAdapter;

public class ExpenseFragment extends Fragment {

    private ExpenseViewModel expenseViewModel;

    public ExpenseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        EditText expenseName = view.findViewById(R.id.editTextExpenseName);
        EditText expenseAmount = view.findViewById(R.id.editTextExpenseAmount);
        Button saveButton = view.findViewById(R.id.buttonSaveExpense);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewExpenses);

        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnExpenseDeleteListener(expense -> {
            expenseViewModel.deleteExpense(expense);
        });

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        saveButton.setOnClickListener(v -> {
            
            String name = expenseName.getText().toString().trim();
            String amountStr = expenseAmount.getText().toString().trim();

            if (name.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            Expense expense = new Expense(name, amount);
            expenseViewModel.insertExpense(expense);

            Toast.makeText(getActivity(), "Expense saved!", Toast.LENGTH_SHORT).show();
            expenseName.setText("");
            expenseAmount.setText("");
        });

        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), adapter::setExpenseList);

        return view;
    }
}
