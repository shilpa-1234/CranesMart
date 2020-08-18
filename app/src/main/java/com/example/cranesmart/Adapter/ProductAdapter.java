package com.example.cranesmart.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.R;
import com.example.cranesmart.pojo.CategoryList;
import com.example.cranesmart.pojo.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product>product;
Context context;
    public ProductAdapter(ArrayList<Product> android) {
        this.product = android;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder viewHolder, int i) {

  //      viewHolder.tv_name.setText(android.get(i).getProductId());
        viewHolder.price.setText(product.get(i).getPrice());
        Picasso.with(context).load(String.valueOf(product.get(i).getProductImg()));
        viewHolder.tv_name.setText(product.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,price;
        private ImageView a1;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            price = (TextView)view.findViewById(R.id.price);
            a1= view.findViewById(R.id.a2);

        }
    }

}