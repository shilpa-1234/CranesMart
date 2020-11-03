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
import com.example.cranesmart.pojo.PremiumWallethistory.Wallethistoryadapter;
import com.example.cranesmart.pojo.cranemartpoint.Cranemartpoint;

import java.util.ArrayList;

public class Cranesmartpointhistoryadapter extends RecyclerView.Adapter<Cranesmartpointhistoryadapter.ViewHolder> {
    private ArrayList<Cranemartpoint> wallet;
    Context context;
  String  TAG ="@@walletadapter";
    private Adapterinterface madapterinterface;
    public Cranesmartpointhistoryadapter(ArrayList<Cranemartpoint> wallet, Adapterinterface madapterinterface) {
        this.wallet = wallet;
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
        Log.d(TAG, "onBindViewHolder: "+wallet.get(i).getDescription().toString());
        Log.d(TAG, "onBindViewHolder: "+wallet.get(i).getPoints().toString());
        Log.d(TAG, "onBindViewHolder: "+wallet.get(i).getDatetime().toString());
       viewHolder.description.setText(wallet.get(i).getDescription().toString());
        if(wallet.get(i).getType().equals("1")){
            viewHolder.rupees.setTextColor(Color.parseColor("#0DAC8F"));
            viewHolder.rupees.setText("P."+" "+wallet.get(i).getPoints().toString());
        }
        else if(wallet.get(i).getType().equals("2")){
            viewHolder.rupees.setTextColor(Color.RED);
            viewHolder.rupees.setText("P."+" "+wallet.get(i).getPoints().toString());
        }

       viewHolder.time.setText(wallet.get(i).getDatetime().toString());
       if(wallet.get(i).getType().equals("1")){
            viewHolder.Premium.setText("Credited To");
        }
       else if(wallet.get(i).getType().equals("2")){
           viewHolder.Premium.setText("Deducted From");
       }
    }

    @Override
    public int getItemCount() {
        return wallet.size();
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