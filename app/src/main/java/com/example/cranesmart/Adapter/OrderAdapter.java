package com.example.cranesmart.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.Orderlistpojo.OrderDatum;
import com.example.cranesmart.pojo.Orderlistpojo.ProductInfo;
import com.example.cranesmart.pojo.PremiumWallethistory.Wallethistoryadapter;
import com.example.cranesmart.pojo.cranemartpoint.Cranemartpoint;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<ProductInfo> list;
    Context context;
    String  TAG ="@@walletadapter";
    private Adapterinterface madapterinterface;
    public OrderAdapter(ArrayList<ProductInfo> list, Adapterinterface madapterinterface) {
        this.list = list;
        this.context=context;
        this.madapterinterface=madapterinterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderlayout, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Glide.with(context).load(APIUrl.IMG_URL+list.get(i).getProductImg()).placeholder(R.drawable.noimage).into(viewHolder.txt_Amount);
        viewHolder.txt_id.setText(list.get(i).getProductName());
        viewHolder.txt_description.setText("₹"+" "+list.get(i).getProductTotalPrice()+" "+"Qty:"+list.get(i).getProductQty());
        viewHolder.txt.setText(list.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_id,txt_description,txt;
        CircleImageView txt_Amount;
        public ViewHolder(View view) {
            super(view);
            txt_id = view.findViewById(R.id.txt_id);
            txt_description = view.findViewById(R.id.txt_description);
            txt = view.findViewById(R.id.txt);
            txt_Amount = view.findViewById(R.id.txt_Amount);


        }
    }



}