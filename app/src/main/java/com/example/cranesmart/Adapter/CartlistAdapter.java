package com.example.cranesmart.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Activity.CartActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.cart.Addcartlist;
import com.example.cranesmart.pojo.cart.CartList;
import com.example.cranesmart.pojo.cart.Cartpojo;
import com.example.cranesmart.pojo.deletecart.Deletecartpojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.WIFI_SERVICE;
import static android.graphics.Color.parseColor;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class CartlistAdapter extends RecyclerView.Adapter<CartlistAdapter.ViewHolder>{
    private ArrayList<CartList>listdata;
    private Adapterinterface madapterinterface;
    private boolean clickable = true;
    int total;
String proID;

    Context context;
int i;
    // RecyclerView recyclerView;
    public CartlistAdapter(ArrayList<CartList>listdata,Adapterinterface madapterinterface) {
        this.listdata = listdata;
        this.madapterinterface=madapterinterface;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        context=parent.getContext();
        View listItem= layoutInflater.inflate(R.layout.cartlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("bindviewholderrrr", "onBindViewHolder: "+listdata.get(position).getAttribute().toString());
//       final CartList CartList = listdata.get(position);
        holder.number.setText(listdata.get(position).getQty());
        holder.textView.setText(listdata.get(position).getProductName());
        if(listdata.get(position).getAttribute().isEmpty()&&listdata.get(position).getAttribute()==null&&listdata.get(position).getAttribute().equals("")
        &&listdata.get(position).getAttribute().size()<2){

        }
        else {
            try {
                if (listdata.get(position).getAttribute().get(0) != null) {
                    holder.colorcode.getBackground().setColorFilter(Color.parseColor(listdata.get(position).getAttribute().get(0).getAttributeDescription()), PorterDuff.Mode.SRC_ATOP);
                    holder.color.setText(listdata.get(position).getAttribute().get(0).getAttributeValueLabel());
                } else {
                    holder.color.setVisibility(View.GONE);
                    holder.colorcode.setVisibility(View.GONE);
                }
                if (listdata.get(position).getAttribute().get(1).equals(null) && listdata.get(position).getAttribute().get(1).equals(0) && listdata.get(position).getAttribute().get(1).equals("")) {
                    holder.size.setVisibility(View.GONE);
                } else {
                    holder.size.setText("Size" + ":" + listdata.get(position).getAttribute().get(1).getAttributeValueLabel());
                }
            } catch (Exception e){
                Log.d("@@cartactiviy", "onBindViewHolder: "+e.getMessage());
            }
        }
            if(listdata.get(position).getIsSpecialPrice()==1){
            Intent intent = new Intent();
            intent.putExtra("pos","1"); 
            holder.price.setText("₹"+" "+listdata.get(position).getSpecialPrice());
       }
        else{
            Intent intent = new Intent();
            intent.putExtra("pos1","0");
            holder.price.setText("₹"+" "+listdata.get(position).getPrice());
        }
        Glide.with(context).load(listdata.get(position).getProductImg().trim()).into(holder.imageView);

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(Integer.parseInt(holder.number.getText().toString().trim())<10){
                   if(clickable){
                       holder.plus.setEnabled(false);
                cart(listdata.get(position).getId().trim(), String.valueOf(1));
                cartlist();
                   }}
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Integer.parseInt(holder.number.getText().toString().trim())<=1)
                {
                    holder.minus.setEnabled(false);
                    i=1;
                }
                else{
                    if(Integer.parseInt(holder.number.getText().toString().trim())>0)
                    {
                        if(clickable){
                        holder.minus.setEnabled(false);
                        cart(listdata.get(position).getId().trim().trim(), String.valueOf(-1));}
                    }
                    else {
                    }

                }

            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletecart();
                listdata.clear();
                holder.invisible.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,price,number,color,size;
        ImageView imageView,plus,minus,close,colorcode;
        CardView invisible;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.wallet3);
            this.textView = (TextView) itemView.findViewById(R.id.Smart);
            this.plus =  itemView.findViewById(R.id.plus);
            this.invisible=itemView.findViewById(R.id.invisible);
            this.minus =  itemView.findViewById(R.id.minus);
            this.close =  itemView.findViewById(R.id.close);
            this.color =  itemView.findViewById(R.id.color);
            this.size =  itemView.findViewById(R.id.size);
            this.colorcode =  itemView.findViewById(R.id.colorcode);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.price = (TextView) itemView.findViewById(R.id.price);

        }
    }
    private  void cart(String proID,String count){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        SharedPreferences sh=context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;
        Log.d("@@shared@@",value);


        Call<Cartpojo> call = service.cart(value,ipAddress,proID,count,"1");
        Log.d("@@method1", value);
        Log.d("@@method2", ipAddress);
        Log.d("@@method3", proID);
        Log.d("@@method4", count);
        Log.d("Quantity",count);
        Log.d("ProductID",proID);
        call.enqueue(new Callback<Cartpojo>() {
            @Override
            public void onResponse(Call<Cartpojo> call, Response<Cartpojo> response) {

                madapterinterface.onMethodCallback();

            }

            @Override
            public void onFailure(Call<Cartpojo> call, Throwable t) {

            }
            //displaying the message from the response as toast


        });


    }
    private void deletecart() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref =context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value1=pref.getString("userID","0");
        String temp_id= pref.getString("temp_id","0");
        String pro_ID= pref.getString("pro_ID","0");
        Log.d("@@shared@@12",value1);

        Call<Deletecartpojo> call = service.cartremove(temp_id,value1,pro_ID);
        Log.d("@@adapteid2", pro_ID);
        Log.d("@@adapteid3", temp_id);
        Log.d("@@adapteid4", value1);
        call.enqueue(new Callback<Deletecartpojo>() {
            @Override
            public void onResponse(Call<Deletecartpojo> call, Response<Deletecartpojo> response) {
                //hiding progress dialog
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                madapterinterface.onMethodCallback();

            }

            @Override
            public void onFailure(Call<Deletecartpojo> call, Throwable t) {

            }


        });

    }
    private  void cartlist(){
        total=0;
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        listdata.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        SharedPreferences sh=context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0");
        Call<Addcartlist> call = service.cartlist(value,ipAddress);
        call.enqueue(new Callback<Addcartlist>() {
            @Override
            public void onResponse(Call<Addcartlist> call, Response<Addcartlist> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    if (response.body().getCartList()!=null) {
                            if (response.body().getCartList().get(i).getIsSpecialPrice() == 0) {
                                total = total + parseInt(response.body().getCartList().get(i).getQty()) * parseInt(response.body().getCartList().get(i).getPrice());
                            } else {
                                total = parseInt(response.body().getCartList().get(i).getQty()) * parseInt(response.body().getCartList().get(i).getSpecialPrice());

                            }


                        }


                        Log.d("Addedcart", "why we get error");
                    }
                }



            @Override
            public void onFailure(Call<Addcartlist> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("@@failure", "onFailure: "+t.getMessage());

            }


        });


    }


}



