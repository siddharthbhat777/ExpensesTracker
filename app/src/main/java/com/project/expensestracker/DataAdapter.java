package com.project.expensestracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;//wht is this notify ?wait plzz
import java.util.Date;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    Context context;
    ArrayList<ModelClass> modelList; // it will be better if u give a name related to list to this variable for better understanding ok

    public DataAdapter(Context context, ArrayList<ModelClass> modelList) {
        this.context = context;
        this.modelList = modelList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_data_view, parent, false);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataViewHolder holder, int position) {

        ModelClass modelClass = modelList.get(position);

        holder.descTV.setText(modelClass.getDesc());

        long yourmilliseconds =modelClass.getdAndT();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date resultdate = new Date(yourmilliseconds);

        holder.dateTV.setText(sdf.format(resultdate));

        holder.priceTV.setText(String.valueOf(modelClass.getAmt()));

        holder.deleteCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog customDialog = new DeleteDialog((Activity) context,modelClass.getDesc());
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView descTV, dateTV, priceTV;
        MaterialCardView deleteCV;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            descTV = itemView.findViewById(R.id.single_data);
            dateTV = itemView.findViewById(R.id.single_date);
            priceTV = itemView.findViewById(R.id.single_amount);
            deleteCV = itemView.findViewById(R.id.deleteDataCardView);
        }
    }
}
