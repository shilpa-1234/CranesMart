package com.example.cranesmart.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.BlankFragment;
import com.example.cranesmart.pojo.dashboard.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Demo_Adapter extends RecyclerView.Adapter<Demo_Adapter.ViewHolder>{
    private ArrayList<Product>data;
    Context context;
    String proID;
    public Demo_Adapter(ArrayList<Product>data) {
        this.data = data;
    }

    public ImageView a1;

    // RecyclerView recyclerView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.card_row, parent, false);
        context=parent.getContext();
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.textView.setText(data.get(position).getProductName());

        Glide.with(context).load(String.valueOf("http://cranesmart.codunite.in/"+data.get(position).getProductImg())).override(500,500).into(holder.a2);
        if(data.get(position).getSpecialPriceStatus()==0)
        {
             holder.price.setText("₹"+" "+data.get(position).getPrice().trim());
        }
        else{
            holder.price.setPaintFlags( holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price.setText("₹"+" "+data.get(position).getSpecialPrice().trim());
        }
        holder.a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            proID=data.get(position).getProductId().trim();
//
               Log.d("frag333", proID);



                Fragment frag = new BlankFragment();
                FragmentTransaction fragmentTransactionChange = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                fragment_transaction1(fragmentTransactionProfile,frag);

                Bundle arguments = new Bundle();
                arguments.putString("proID", proID);
                frag.setArguments(arguments);
//                Bundle arguments = new Bundle();
//                arguments.putString("proID", proID);
//                frag.setArguments(arguments);
                fragmentTransactionChange =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransactionChange.replace(R.id.frame,frag);
                fragmentTransactionChange.addToBackStack(null);
                fragmentTransactionChange.commit();




            }
        });
        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View view) {

                if(!clicked) {
                    holder. wishlist.setBackgroundResource(R.drawable.wishlistt);
                    clicked=true;
                }
                else{
                    holder.  wishlist.setBackgroundResource(R.mipmap.wishlist);
                    clicked=false;
                }
//                notifyDataSetChanged();

            }
        });
        Log.e("@@data",data.get(position).getProductName());
        Log.d("@@img","http://cranesmart.codunite.in/"+data.get(position).getProductImg());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,price;
        public ImageView a2,wishlist;
        public ViewHolder(View itemView) {
            super(itemView);
            this.wishlist=itemView.findViewById(R.id.wishlist);
            this.price=itemView.findViewById(R.id.price);
            this.a2=itemView.findViewById(R.id.a2);
            this.textView = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment)
    {

        Bundle arguments = new Bundle();
        arguments.putString("proID", "10");
        changeFragment.setArguments(arguments);
        fragmentTransactionChange =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.replace(R.id.content_frame,changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();


    }

}