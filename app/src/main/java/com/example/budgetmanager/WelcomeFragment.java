package com.example.budgetmanager;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // מעבר אוטומטי אחרי 4.5 שניות עם בדיקת קיום fragment
        new Handler().postDelayed(() -> {
            if (isAdded()) { // בודק שה־Fragment מחובר
                NavController navController = NavHostFragment.findNavController(WelcomeFragment.this);
                navController.navigate(R.id.incomeFragment);
            }
        }, 2500);
    }



}
