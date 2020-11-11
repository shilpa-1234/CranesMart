package com.example.cranesmart.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.ProductDetailFragment;
import com.example.cranesmart.pojo.Search.Search;

import java.util.ArrayList;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>{
    public SearchListAdapter(ArrayList<Search> datalist) {
        this.datalist = datalist;
    }
    private ArrayList<Search> datalist;
Context context;
    String proID;
     String Status="1";
    public ImageView image,wishlist;


    // RecyclerView recyclerView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.searchrow, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        context=parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(datalist.get(position).getName());
        holder.price.setText("₹"+" "+datalist.get(position).getPrice());
        Glide.with(context).load(datalist.get(position).getImg().trim()).placeholder(R.drawable.noimage).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tagl", "onClick: error");
                SharedPreferences preferences=context.getSharedPreferences(Sharedprefence.SharedprefenceName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("status1",Status);
                editor.commit();
                editor.apply();
                proID=datalist.get(position).getProID().trim();
                Fragment frag = new ProductDetailFragment();
                final FragmentTransaction fragmentTransactionProfile = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragment_transaction1(fragmentTransactionProfile,frag);

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

//
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,price;
        ImageView image,wishlist;


        public ViewHolder(View itemView) {
            super(itemView);
            this.wishlist=itemView.findViewById(R.id.wishlist);
            this.image= itemView.findViewById(R.id.a2);
            this.textView = (TextView) itemView.findViewById(R.id.tv_name);
            this.price=itemView.findViewById(R.id.price);

        }
    }

    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment)
    {

        Bundle arguments = new Bundle();
        arguments.putString("proID", proID);
        changeFragment.setArguments(arguments);
        fragmentTransactionChange =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.replace(R.id.frame,changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        ((AppCompatActivity) context).getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransactionChange.commit();


    }

}