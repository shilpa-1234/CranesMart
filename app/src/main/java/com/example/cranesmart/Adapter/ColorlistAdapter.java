package com.example.cranesmart.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.inrefaceforsizeandcolor.CustomItemClickListener;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.productdetail.productcartcolor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.graphics.Color.*;
// Adapter is used for Color in Product Detail Fragment
public class ColorlistAdapter  extends RecyclerView.Adapter<ColorlistAdapter.ViewHolder>{
    ArrayList<productcartcolor>backgroundcolor;
    boolean clicked = false;
    private int lastCheckedPosition = -1;
    Context context;
    CustomItemClickListener listner;
    public ColorlistAdapter(ArrayList<productcartcolor> backgroundcolor,CustomItemClickListener listner) {
        this.backgroundcolor=backgroundcolor;
        this.listner=listner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.colorlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(backgroundcolor.get(position)!=null) {
            if(backgroundcolor.get(position).getDescription().equals("")||backgroundcolor.get(position).getDescription().isEmpty()||backgroundcolor.get(position).getDescription()==null){
                holder.color.getBackground().setColorFilter(parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            }
            else  if(backgroundcolor.get(position).getDescription()!=null){
            holder.color.getBackground().setColorFilter(parseColor(backgroundcolor.get(position).getDescription()), PorterDuff.Mode.SRC_ATOP);}
            holder.color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(!clicked) {

                       holder.linear.getBackground().setColorFilter(parseColor(backgroundcolor.get(position).getDescription()), PorterDuff.Mode.SRC_ATOP);
                       holder.color.getBackground().setColorFilter(parseColor(backgroundcolor.get(position).getDescription()), PorterDuff.Mode.SRC_ATOP);
                       listner.onItemClick(backgroundcolor.get(position).getAttributeValue(),1);

                               clicked=true;
                   }
                   else {
                       holder.linear.getBackground().setColorFilter(parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
                       holder.color.getBackground().setColorFilter(parseColor(backgroundcolor.get(position).getDescription()), PorterDuff.Mode.SRC_ATOP);
                       clicked=false;

                   }
                    notifyDataSetChanged();
                }
            });
        }
 }


    @Override
    public int getItemCount() {
        return backgroundcolor.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView color,color1;
        public TextView textView;
        LinearLayout linear;
        public ViewHolder(View itemView) {
            super(itemView);
            this.color =  itemView.findViewById(R.id.color);
            this.linear =  itemView.findViewById(R.id.linear);
            this.textView= itemView.findViewById(R.id.textview);
        }
    }
}
