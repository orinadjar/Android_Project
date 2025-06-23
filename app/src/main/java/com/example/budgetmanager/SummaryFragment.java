package com.example.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetmanager.viewmodel.IncomeViewModel;
import com.example.budgetmanager.viewmodel.ExpenseViewModel;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class SummaryFragment extends Fragment {

    private IncomeViewModel incomeViewModel;
    private ExpenseViewModel expenseViewModel;

    private double totalIncome = 0.0;
    private double totalExpense = 0.0;

    public SummaryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView incomeText = view.findViewById(R.id.textTotalIncome);
        TextView expenseText = view.findViewById(R.id.textTotalExpense);
        TextView balanceText = view.findViewById(R.id.textBalance);
        BarChart barChart = view.findViewById(R.id.barChart);

        Button logoutButton = view.findViewById(R.id.logoutButton);

        TextView emailTextView = view.findViewById(R.id.loggedInEmailTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailTextView.setText(user.getEmail());
        }

        TextView textFeedback = view.findViewById(R.id.textFeedback);
        CardView cardFeedback = view.findViewById(R.id.cardFeedback);


        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // ניתוק המשתמש
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish(); // סוגר את הפעילות הנוכחית
        });

        incomeViewModel.getAllIncomes().observe(getViewLifecycleOwner(), incomes -> {
            totalIncome = 0;
            for (var income : incomes)
                totalIncome += income.getAmount();

            incomeText.setText("₪ " + totalIncome);
            balanceText.setText("₪ " + (totalIncome - totalExpense));
        });

        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
            totalExpense = 0;
            for (var expense : expenses)
                totalExpense += expense.getAmount();

            expenseText.setText("₪ " + totalExpense);
            balanceText.setText("₪ " + (totalIncome - totalExpense));
        });

        // Graph
        incomeViewModel.getAllIncomes().observe(getViewLifecycleOwner(), incomes -> {
            totalIncome = 0;
            for (var income : incomes)
                totalIncome += income.getAmount();
            incomeText.setText("₪ " + totalIncome);
            updateBarChart(barChart);

            updateFeedbackCard(textFeedback);

        });

        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
            totalExpense = 0;
            for (var expense : expenses)
                totalExpense += expense.getAmount();
            expenseText.setText("₪ " + totalExpense);
            updateBarChart(barChart);


            updateFeedbackCard(textFeedback);
        });


    }

    private void updateBarChart(BarChart barChart) {
        double balance = totalIncome - totalExpense;

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, (float) totalIncome));
        entries.add(new BarEntry(1, (float) totalExpense));
        entries.add(new BarEntry(2, (float) balance));

        BarDataSet dataSet = new BarDataSet(entries, "Budget Overview");
        dataSet.setColors(new int[]{R.color.green_700, R.color.red_700, R.color.black}, getContext());

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);

        String[] labels = {"Income", "Expense", "Balance"};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate(); // רענון
    }

    private void updateFeedbackCard(TextView textFeedback) {
        double balance = totalIncome - totalExpense;

        if (totalIncome == 0 && totalExpense == 0) {
            textFeedback.setText("No data yet. Start tracking your budget today!");
            return;
        }

        if (totalIncome == 0) {
            textFeedback.setText("No income data. Add your earnings to see your progress.");
            return;
        }

        double percent = (totalExpense / totalIncome) * 100;

        if (balance > 0) {
            textFeedback.setText("Great job! You saved ₪" + balance + " this period.\n"
                    + "Your expenses are only " + (int) percent + "% of your income.");
        } else if (balance == 0) {
            textFeedback.setText("You're breaking even. Spend carefully!");
        } else {
            textFeedback.setText("Warning: You overspent ₪" + Math.abs(balance) + "!\n"
                    + "Try to reduce expenses next time.");
        }
    }

}
