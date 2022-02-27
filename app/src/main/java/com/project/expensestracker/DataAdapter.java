package com.project.expensestracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    Context context;
    ArrayList<ModelClass> model;

    public DataAdapter(Context context, ArrayList<ModelClass> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_data_view, parent, false);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataViewHolder holder, int position) {

        ModelClass modelClass = model.get(position);

        holder.descTV.setText(modelClass.getDesc());
        holder.dateTV.setText(modelClass.getdAndT());
        holder.priceTV.setText(String.valueOf(modelClass.getAmt()));

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView descTV, dateTV, priceTV;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            descTV = itemView.findViewById(R.id.single_data);
            dateTV = itemView.findViewById(R.id.single_date);
            priceTV = itemView.findViewById(R.id.single_amount);
        }
    }
}
