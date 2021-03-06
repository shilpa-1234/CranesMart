package com.example.cranesmart.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cranesmart.Activity.AddressActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.address.Addresslistdatum;
import com.example.cranesmart.pojo.address.Editaddresspojo;
import com.example.cranesmart.pojo.deletecart.Deletepojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// It Similar to Addresslist Adapter
//and Used in addresslistmenu Activity
public class addresslistmenuadapter extends RecyclerView.Adapter<addresslistmenuadapter.ViewHolder> {
    private ArrayList<Addresslistdatum> product;
    Context context;
    private Adapterinterface madapterinterface;
    public addresslistmenuadapter(ArrayList<Addresslistdatum> product, Adapterinterface madapterinterface) {
        this.product = product;
        this.context=context;
        this.madapterinterface=madapterinterface;
    }




    @Override
    public addresslistmenuadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresslist, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final addresslistmenuadapter.ViewHolder viewHolder, final int i) {
        viewHolder.buttoncontinue.setVisibility(View.GONE);
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
    private void delete() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading...");
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
        String value=pref.getString("addID","0");
        Log.d("@@shared@@111",value);
        String value1=pref.getString(Sharedprefence.UserID,"0");
        Log.d("@@shared@@12",value1);

        Call<Deletepojo> call = service.delete(value1,value);
        call.enqueue(new Callback<Deletepojo>() {
            @Override
            public void onResponse(Call<Deletepojo> call, Response<Deletepojo> response) {
                //hiding progress dialog
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                madapterinterface.onMethodCallback();
                Log.d(">>adapter", "onResponse: "+response.body().getMessage());

            }

            @Override
            public void onFailure(Call<Deletepojo> call, Throwable t) {

            }


        });

    }
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
        String value=pref.getString("addID","0");
        Log.d("@@shared@@111",value);
        String value1=pref.getString(Sharedprefence.UserID,"0");
        Log.d("@@shared@@12",value1);
        String name=pref.getString("name","0");
        String phone=pref.getString("phone","0");
        String address=pref.getString("add","0");
        String city=pref.getString("city","0");
        String postalcode=pref.getString("code","0");
        String State=pref.getString("State_id","0");
        Log.d("@@address", "edit: "+State);
        String country=pref.getString("country_id","0");
        Log.d("@@address", "edit: "+country);
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

}