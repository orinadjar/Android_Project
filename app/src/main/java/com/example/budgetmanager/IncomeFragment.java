package com.example.budgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetmanager.model.Income;
import com.example.budgetmanager.viewmodel.IncomeViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.budgetmanager.ui_income.IncomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IncomeFragment extends Fragment {

    private IncomeViewModel incomeViewModel;

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_income, container, false);

        EditText incomeName = view.findViewById(R.id.editTextIncomeName);
        EditText incomeAmount = view.findViewById(R.id.editTextIncomeAmount);
        Button saveButton = view.findViewById(R.id.buttonSaveIncome);
        Button logoutButton = view.findViewById(R.id.logoutButton);

        TextView emailTextView = view.findViewById(R.id.loggedInEmailTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailTextView.setText(user.getEmail());
        }


        // יצירת ViewModel
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // ניתוק המשתמש
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish(); // סוגר את הפעילות הנוכחית
        });


        saveButton.setOnClickListener(v -> {
            String name = incomeName.getText().toString().trim();
            String amountStr = incomeAmount.getText().toString().trim();

            if (name.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            Income income = new Income(name, amount);

            incomeViewModel.insertIncome(income);

            Toast.makeText(getActivity(), "Income saved!", Toast.LENGTH_SHORT).show();

            incomeName.setText("");
            incomeAmount.setText("");
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewIncomes);
        IncomeAdapter adapter = new IncomeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnIncomeDeleteListener(income -> {
            incomeViewModel.deleteIncome(income);
        });

        // הצגת נתונים מתוך ה-ViewModel
        incomeViewModel.getAllIncomes().observe(getViewLifecycleOwner(), adapter::setIncomeList);


        return view;
    }
}
