package com.example.cranesmart.Adapter;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cranesmart.Activity.AddressActivity;
import com.example.cranesmart.Activity.CartActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.Paymentgatewaywallet.PaymentShopingActivity;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.Checkoutpojo.Checkoutapi;
import com.example.cranesmart.pojo.cranesmartpojo.Cranesmartwallet;
import com.example.cranesmart.pojo.address.Addresslistdatum;
import com.example.cranesmart.pojo.deletecart.Deletepojo;
import com.example.cranesmart.pojo.address.Editaddresspojo;
import com.example.cranesmart.pojo.paymentparametrekey.parametrekey;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addresslistadapter extends RecyclerView.Adapter<addresslistadapter.ViewHolder> {
    private ArrayList<Addresslistdatum> product;
    Context context;
    private Adapterinterface madapterinterface;
    public addresslistadapter(ArrayList<Addresslistdatum> product,Adapterinterface madapterinterface) {
        this.product = product;
        this.context=context;
        this.madapterinterface=madapterinterface;
    }




    @Override
    public addresslistadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresslist, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final addresslistadapter.ViewHolder viewHolder, final int i) {

        viewHolder.personname.setText(product.get(i).getName());
        Log.d("personname", product.get(i).getName());
        viewHolder.address.setText(product.get(i).getAddress1()+" "+product.get(i).getZipCode());
        viewHolder.mobile.setText(product.get(i).getPhoneNo());
        viewHolder.city.setText(product.get(i).getCity());
        viewHolder.State.setText(product.get(i).getState());
        viewHolder.country.setText(product.get(i).getCountry());
        SharedPreferences pref = context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        final SharedPreferences.Editor editor=pref.edit();
        editor.putString("address",viewHolder.address.toString());
        editor.commit();
        editor.apply();
        Log.d("personaddress", product.get(i).getAddress1());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, product.size());
                delete();
            }
        });
        viewHolder.buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
                final boolean clicked=false;
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView= inflater.inflate(R.layout.walletpayoptions, null);
                builder.setView(dialogView);
                final RadioButton radio = dialogView.findViewById(R.id.radio);
                final RadioButton radio1 = dialogView.findViewById(R.id.radio1);
                Button proceed=dialogView.findViewById(R.id.proceed);
                final AlertDialog alertDialog = builder.create();
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(radio1.isChecked()){
                            radio.setChecked(false);
                            paymentkey();
                            Intent i = new Intent(context, PaymentShopingActivity.class);
                            context.startActivity(i);
                            alertDialog.dismiss();

                        }
                        else if(radio.isChecked()){
                            radio1.setChecked(false);
                            Cranesmartwallet();
                            alertDialog.dismiss();
                        }
                    }
                });

//                AlertDialog alertDialog = builder.create();
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.cb_rectangle_round);
                alertDialog.show();


            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(context,AddressActivity.class);
                in.putExtra("postion",i);
                context.startActivity(in);
                edit();


            }
        });




    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView personname,address,mobile,city,State,country;
        Button buttoncontinue,edit,delete;
        SwipeRefreshLayout mSwipeRefreshLayout;
        public ViewHolder(View view) {
            super(view);
            personname = view.findViewById(R.id.personname);
            mobile = view.findViewById(R.id.mobile);
            city = view.findViewById(R.id.city);
            State = view.findViewById(R.id.State);
            country = view.findViewById(R.id.country);
            address = view.findViewById(R.id.address);
            buttoncontinue= view.findViewById(R.id.buttoncontinue);
            edit= view.findViewById(R.id.edit);
            delete= view.findViewById(R.id.delete);

        }
    }
//    Delete address From Database
    private void delete() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Html.fromHtml("<font color='#000'>loading...</font>"));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref =context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=pref.getString(Sharedprefence.AddId,"0");
        String value1=pref.getString(Sharedprefence.UserID,"0");

        Call<Deletepojo> call = service.delete(value1,value);
        call.enqueue(new Callback<Deletepojo>() {
            @Override
            public void onResponse(Call<Deletepojo> call, Response<Deletepojo> response) {
                //hiding progress dialog
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                madapterinterface.onMethodCallback();

            }

            @Override
            public void onFailure(Call<Deletepojo> call, Throwable t) {

            }


        });

    }
//    Update Address
    private void edit() {

        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref =context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=pref.getString(Sharedprefence.AddId,"0");
        String value1=pref.getString(Sharedprefence.UserID,"0");
        String name=pref.getString("name","0");
        String phone=pref.getString("phone","0");
        String address=pref.getString("add","0");
        String city=pref.getString("city","0");
        String postalcode=pref.getString("code","0");
        String State=pref.getString("State_id","0");
        String country=pref.getString("country_id","0");
        Call<Editaddresspojo> call = service.edit(value1,value,name,phone,address,city,country,State,postalcode);
        call.enqueue(new Callback<Editaddresspojo>() {
            @Override
            public void onResponse(Call<Editaddresspojo> call, Response<Editaddresspojo> response) {
                //hiding progress dialog
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<Editaddresspojo> call, Throwable t) {

            }


        });

    }
// Getting Payment Parameter Key
    private void paymentkey() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<parametrekey> call = service.key(
        );

        call.enqueue(new Callback<parametrekey>() {
            @Override
            public void onResponse(Call<parametrekey> call, Response<parametrekey> response) {
                progressDialog.dismiss();

                SharedPreferences pref = context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Sharedprefence.Merchant_Id, response.body().getData().getPayuMerchantId());
                editor.putString(Sharedprefence.Merchant_Key, response.body().getData().getPayuMerchantKey());
                editor.putString(Sharedprefence.Salt, response.body().getData().getPayuMerchantSalt());
                editor.apply();
                editor.commit();

            }

            @Override
            public void onFailure(Call<parametrekey> call, Throwable t) {

            }


        });
    }

//    Order Check out api

    private void checkout() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SharedPreferences sh = context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = sh.getString(Sharedprefence.UserID, "0");
        String value1 = sh.getString(Sharedprefence.AddId, "0");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Checkoutapi> call = service.order(
                value, value1
        );

        call.enqueue(new Callback<Checkoutapi>() {
            @Override
            public void onResponse(Call<Checkoutapi> call, Response<Checkoutapi> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    SharedPreferences pref = context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Sharedprefence.Amountshop, response.body().getData().getAmount().toString());
                    editor.putString(Sharedprefence.Taxnid, response.body().getData().getTxnid().toString());
                    editor.putString(Sharedprefence.Encoded_id, response.body().getData().getEncodedOrderId().toString());
                    editor.putString(Sharedprefence.HashKeys, response.body().getData().getHash().toString());
                    editor.commit();
                    editor.apply();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Checkoutapi> call, Throwable t) {

            }


        });
    }
//    Payment From Cranes Mart wallet
    private void Cranesmartwallet(){
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SharedPreferences sh = context.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = sh.getString(Sharedprefence.UserID, "0");
        String value1 = sh.getString(Sharedprefence.Encoded_id, "0");
        String amount = sh.getString(Sharedprefence.Amountshop, "0");
        String txnid = sh.getString(Sharedprefence.Taxnid, "0");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Cranesmartwallet> call = service.cranesmartwallet(
                value, value1,"sucess",amount,txnid
        );

        call.enqueue(new Callback<Cranesmartwallet>() {
            @Override
            public void onResponse(Call<Cranesmartwallet> call, Response<Cranesmartwallet> response) {
                progressDialog.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.MyAlertDialogTheme);
                alertDialogBuilder.setMessage(response.body().getMessage());
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent i= new Intent(context, CartActivity.class);
                                context.startActivity(i);

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

        }

            @Override
            public void onFailure(Call<Cranesmartwallet> call, Throwable t) {

            }


        });
    }
}