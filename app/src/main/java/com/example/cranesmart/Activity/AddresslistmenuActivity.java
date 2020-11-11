package com.example.cranesmart.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.Adapter.addresslistmenuadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.address.Addaddresslist;
import com.example.cranesmart.pojo.address.Addresslistdatum;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddresslistmenuActivity extends AppCompatActivity implements Adapterinterface {
RelativeLayout relative1;
RecyclerView recyleaddress;
    RecyclerView.LayoutManager layoutManager;
ArrayList<Addresslistdatum> product;
ImageView menu;
LinearLayout li;
int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        product= new ArrayList<>();
        recyleaddress=findViewById(R.id.recyleaddress);
        relative1=findViewById(R.id.relative1);
        li=findViewById(R.id.li);
        li.setVisibility(View.GONE);
       Sizee();
        menu=findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddresslistmenuActivity.this,DashboardActivity.class);
                startActivity(i);
            }
        });
// Add New Address
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddresslistmenuActivity.this,AdressmenuActivity.class);
                i.putExtra("postion","123456789");
                startActivity(i);
            }
        });
    }
//    Adapter
    private void Addresslist(){
        layoutManager = new LinearLayoutManager(AddresslistmenuActivity.this);
        recyleaddress.setLayoutManager(layoutManager);
        final addresslistmenuadapter adapter = new addresslistmenuadapter(product,this);
        recyleaddress.setHasFixedSize(true);
        recyleaddress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
//    Address List
    private void Sizee() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Addaddresslist> call = service.addresslist(value);
        call.enqueue(new Callback<Addaddresslist>() {
            @Override
            public void onResponse(Call<Addaddresslist> call, Response<Addaddresslist> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    product.clear();
                    if (response.body().getData()!=null) {
                        for (i = 0; i < response.body().getData().size(); i++) {
                            SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(Sharedprefence.AddId, response.body().getData().get(i).getAddID().trim());
                            editor.putString("CountryId11", response.body().getData().get(i).getCountryId().trim());
                            editor.putString("StateId11", response.body().getData().get(i).getStateId().trim());
                            editor.commit();
                            editor.apply();
                            product.addAll(Arrays.asList(response.body().getData().get(i)));
                        }
                        Addresslist();
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Addaddresslist> call, Throwable t) {

            }


        });

    }

    @Override
    public void onMethodCallback() {
        Sizee();
        product.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(AddresslistmenuActivity.this,DashboardActivity.class);
        startActivity(i);
        finish();
    }
}