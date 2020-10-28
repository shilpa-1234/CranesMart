package com.example.cranesmart.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.R;
import com.example.cranesmart.pojo.dashboard.CategoryDatum;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<CategoryDatum> product;
    Context context;
    public ProductAdapter(ArrayList<CategoryDatum > product,Context context) {
        this.product = product;
        this.context=context;
    }



    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int i) {

  //      viewHolder.tv_name.setText(android.get(i).getProductId());
/*        viewHolder.price.setText(product.get(i).getProductName());
        Picasso.with(context).load(String.valueOf(product.get(i).getProductImg()));
        viewHolder.tv_name.setText(product.get(i).getProductName());*/
        Log.d("--!",product.get(i).getCategoryIcon().toString());
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,price;
        private ImageView a2;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            price = (TextView)view.findViewById(R.id.price);
            a2= view.findViewById(R.id.a2);

        }
    }

}