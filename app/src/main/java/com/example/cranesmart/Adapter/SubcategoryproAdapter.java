package com.example.cranesmart.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.SubcategoryActivity;
import com.example.cranesmart.pojo.subcategory.Datum;

import java.util.ArrayList;


public class SubcategoryproAdapter extends RecyclerView.Adapter<SubcategoryproAdapter.ViewHolder> {
    public SubcategoryproAdapter(ArrayList<Datum> listdata) {
        this.listdata = listdata;
    }
    private ArrayList<Datum> listdata;
    Context context;
    public ImageView Sub;

String catID;
    // RecyclerView recyclerView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.subcategory, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        context=parent.getContext();
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText(listdata.get(position).getTitle());

        Glide.with(context).load(String.valueOf(listdata.get(position).getCategoryIcon())).placeholder(R.drawable.kid).into(holder.Sub);
//        Log.e("@@1",listdata.get(position).getTitle());
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                catID=listdata.get(position).getCatID().trim().toString();
                Fragment frag = new SubcategoryActivity();
                final FragmentTransaction fragmentTransactionProfile = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragment_transaction1(fragmentTransactionProfile,frag);
                SharedPreferences preferences= context.getSharedPreferences(Sharedprefence.SharedprefenceName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("textview2",listdata.get(position).getTitle());
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
        public ImageView Sub;
        LinearLayout linear;
        public ViewHolder(View itemView) {
            super(itemView);
            this.Sub=itemView.findViewById(R.id.Sub);
            this.linear=itemView.findViewById(R.id.linear);
            this.textView = (TextView) itemView.findViewById(R.id.textview1);

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