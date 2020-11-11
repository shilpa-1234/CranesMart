package com.example.cranesmart.Adapter;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.inrefaceforsizeandcolor.CustomItemClickListener;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.productdetail.ProductcartSize;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class SizelistAdapter  extends RecyclerView.Adapter<SizelistAdapter.ViewHolder>{
    ArrayList<ProductcartSize> product=new ArrayList<>();
    boolean clicked = false;
    CustomItemClickListener listner;

    public SizelistAdapter(ArrayList<ProductcartSize> product, CustomItemClickListener listner) {
        this.product=product;
        this.listner=listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.size, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(product.get(position).getDescription());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked) {
                    holder.linear.getBackground().setColorFilter(parseColor("#2CA6CF"), PorterDuff.Mode.SRC_ATOP);
                    clicked=true;
                    listner.onItemClick(product.get(position).getAttributeValue(),0);
                }
                else {
                    holder.linear.getBackground().setColorFilter(parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
                    clicked=false;

                }
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        LinearLayout linear;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.size);
            this.linear =  itemView.findViewById(R.id.linear);
        }
    }
}
