package com.example.cranesmart.Adapter;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.SubcatregoryproFragment;
import com.example.cranesmart.pojo.dashboard.CategoryDatum;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    public RecyclerAdapter(ArrayList<CategoryDatum> listdata) {
        this.listdata = listdata;
    }
    private ArrayList<CategoryDatum> listdata;
    Context context;
    public ImageView a1;
    String catID;
    EditText search;
    // RecyclerView recyclerView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText(listdata.get(position).getTitle());
        Glide.with(context).load(listdata.get(position).getCategoryIcon().trim()).placeholder(R.drawable.kid).into(holder.a1);
        Log.d("ImageUrl",APIUrl.IMG_URL+listdata.get(position).getCategoryIcon());

//        Log.e("@@1",listdata.get(position).getTitle());
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                catID=listdata.get(position).getCatID().trim().toString();

                Fragment frag = new SubcatregoryproFragment();
                final FragmentTransaction fragmentTransactionProfile = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragment_transaction1(fragmentTransactionProfile,frag);
                SharedPreferences preferences= context.getSharedPreferences("Mypref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("textview1",listdata.get(position).getTitle());
                editor.commit();



            }
//
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        LinearLayout linear;
        public ImageView a1;
        public ViewHolder(View itemView) {
            super(itemView);
            this.a1= itemView.findViewById(R.id.a1);
            this.linear=itemView.findViewById(R.id.linear);
            this.textView = (TextView) itemView.findViewById(R.id.textview);


        }
    }
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment)
    {

        Bundle arguments = new Bundle();
        arguments.putString("catID", catID);
        changeFragment.setArguments(arguments);
        fragmentTransactionChange =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.replace(R.id.frame,changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();


    }

   }