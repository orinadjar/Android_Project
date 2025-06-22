package com.example.budgetmanager.ui_income;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanager.R;
import com.example.budgetmanager.model.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private List<Income> incomeList = new ArrayList<>();
    private OnIncomeDeleteListener deleteListener;

    public void setIncomeList(List<Income> list) {
        this.incomeList = list;
        notifyDataSetChanged();
    }

    public void setOnIncomeDeleteListener(OnIncomeDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_income, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        Income income = incomeList.get(position);
        holder.title.setText(income.getTitle());
        holder.amount.setText("₪" + income.getAmount());

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(income);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount;
        ImageButton deleteButton;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewIncomeTitle);
            amount = itemView.findViewById(R.id.textViewIncomeAmount);
            deleteButton = itemView.findViewById(R.id.buttonDeleteIncome);
        }
    }

    public interface OnIncomeDeleteListener {
        void onDelete(Income income);
    }
}
