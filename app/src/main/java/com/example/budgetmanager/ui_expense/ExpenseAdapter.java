package com.example.budgetmanager.ui_expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanager.R;
import com.example.budgetmanager.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList = new ArrayList<>();
    private OnExpenseDeleteListener deleteListener;

    public void setExpenseList(List<Expense> list) {
        this.expenseList = list;
        notifyDataSetChanged();
    }

    public void setOnExpenseDeleteListener(OnExpenseDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.title.setText(expense.getTitle());
        holder.amount.setText("₪" + expense.getAmount());

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(expense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount;
        ImageButton deleteButton;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewExpenseTitle);
            amount = itemView.findViewById(R.id.textViewExpenseAmount);
            deleteButton = itemView.findViewById(R.id.buttonDeleteExpense);
        }
    }

    public interface OnExpenseDeleteListener {
        void onDelete(Expense expense);
    }
}
