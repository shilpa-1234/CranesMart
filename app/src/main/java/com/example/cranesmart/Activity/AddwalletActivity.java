package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Paymentgatewaywallet.PaymentActivity;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.example.cranesmart.pojo.paymentparametrekey.parametrekey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddwalletActivity extends AppCompatActivity {
    ImageView menu;
    Button button;
    EditText searchlist;
    TextView currentbalancd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwallet);
        menu=findViewById(R.id.menu);
        searchlist=findViewById(R.id.searchlist);
        currentbalancd=findViewById(R.id.currentbalancd);
        searchlist.getText().toString();
        userdetail();
        button=findViewById(R.id.button);
        final FragmentTransaction fragmentTransactionProfile = AddwalletActivity.this.getSupportFragmentManager().beginTransaction();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new WalletFragment();

                fragment_transaction1(fragmentTransactionProfile, frag);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchlist.getText().toString().isEmpty()){
                    searchlist.setError("Please Enter Amount");
                }
                else {
                    paymentkey();
                    SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("addwallet", searchlist.getText().toString());
                    editor.commit();
                    editor.apply();
                    Intent i = new Intent(AddwalletActivity.this, PaymentActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment) {

        fragmentTransactionChange= getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }
    private void paymentkey() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
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
                Log.d("Sharedpre", String.valueOf(response.body().getStatus()));
                Log.d("Sharedpre", String.valueOf(response.body().getMessage()));
                progressDialog.dismiss();

                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("merchant_Id", response.body().getData().getPayuMerchantId());
                editor.putString("merchant_Key", response.body().getData().getPayuMerchantKey());
                editor.putString("salt", response.body().getData().getPayuMerchantSalt());
                editor.apply();
                editor.commit();
                Log.d("Sharedprefences", String.valueOf(editor));

            }

            @Override
            public void onFailure(Call<parametrekey> call, Throwable t) {

            }


        });
    }
    private void userdetail() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        SharedPreferences sh=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;



        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
                currentbalancd.setText("₹"+" "+response.body().getUserDetail().getPremiumWalletBalance().trim());

            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                Toast.makeText(AddwalletActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}