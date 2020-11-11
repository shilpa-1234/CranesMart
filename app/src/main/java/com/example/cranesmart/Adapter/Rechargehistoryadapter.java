package com.example.cranesmart.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.RechargeHistory.rechargehistory;
import com.example.cranesmart.pojo.cranemartpoint.Cranemartpoint;

import java.util.ArrayList;

public class Rechargehistoryadapter extends RecyclerView.Adapter<Rechargehistoryadapter.ViewHolder> {
    private ArrayList<rechargehistory> history;
    Context context;
  String  TAG ="@@walletadapter";
    private Adapterinterface madapterinterface;
    public Rechargehistoryadapter(ArrayList<rechargehistory> history, Adapterinterface madapterinterface) {
        this.history = history;
        this.context=context;
        this.madapterinterface=madapterinterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.premiumhistory, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
       viewHolder.description.setText(history.get(i).getDescription().toString());
        if(history.get(i).getStatus().equals("1")){
            viewHolder.rupees.setTextColor(Color.parseColor("#FFE609"));
            viewHolder.rupees.setText("₹"+" "+history.get(i).getAmount().toString());
        }
        else if(history.get(i).getStatus().equals("2")){
            viewHolder.rupees.setTextColor(Color.parseColor("#0DAC8F"));
            viewHolder.rupees.setText("₹"+" "+history.get(i).getAmount().toString());
        }
        else if(history.get(i).getStatus().equals("3")){
            viewHolder.rupees.setTextColor(Color.RED);
            viewHolder.rupees.setText("₹"+" "+history.get(i).getAmount().toString());
        }

       viewHolder.time.setText(history.get(i).getDatetime().toString());
        if(history.get(i).getStatus().equals("1")){
            viewHolder.Premium.setText("Pending");
        }
       else if(history.get(i).getStatus().equals("2")){
            viewHolder.Premium.setText("Sucessfully Recharged");
       }
       else if(history.get(i).getStatus().equals("3")){
            viewHolder.Premium.setText("Failed");
       }
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView description,rupees,time,Premium;
        public ViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.text1);
            rupees = view.findViewById(R.id.rupees);
            time = view.findViewById(R.id.time);
            Premium = view.findViewById(R.id.Premium);


        }
    }



}