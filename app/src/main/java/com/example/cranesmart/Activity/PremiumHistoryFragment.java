package com.example.cranesmart.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cranesmart.Adapter.Walletpreiumadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.PremiumWallethistory.Wallethistory;
import com.example.cranesmart.pojo.PremiumWallethistory.Wallethistoryadapter;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PremiumHistoryFragment extends AppCompatActivity implements Adapterinterface {
    RecyclerView recyclehistory;
    ArrayList<Wallethistoryadapter> wallet;
    RecyclerView.LayoutManager layoutManager;
    ImageView menu;
    TextView edit;
    SwipeRefreshLayout swipetorefresh;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentTransaction fragmentTransactionProfile = PremiumHistoryFragment.this.getSupportFragmentManager().beginTransaction();
        setContentView(R.layout.fragment_premium_history);
        recyclehistory=findViewById(R.id.recyclehistory);
        swipetorefresh=findViewById(R.id.swipetorefresh);
        menu=findViewById(R.id.menu);
        edit=findViewById(R.id.edit);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new WalletFragment();

                fragment_transaction1(fragmentTransactionProfile, frag);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(PremiumHistoryFragment.this,AddwalletActivity.class);
               startActivity(intent);
            }
        });
        wallet= new ArrayList<>();
        wallethistory();
    }
//    Adapter
    private void Walletlist(){
        layoutManager = new LinearLayoutManager(PremiumHistoryFragment.this);
        recyclehistory.setLayoutManager(layoutManager);
        final Walletpreiumadapter adapter = new Walletpreiumadapter(wallet,this);
        recyclehistory.setHasFixedSize(true);
        recyclehistory.setAdapter(adapter);

        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wallet.clear();
                wallethistory();
                adapter.notifyDataSetChanged();
                swipetorefresh.setRefreshing(false);
            }
        });
        swipetorefresh.setColorSchemeResources(R.color.blue);
        adapter.notifyDataSetChanged();
    }
//    Wallet History Api
    private void wallethistory() {

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
        Call<Wallethistory> call = service.wallet(value);
        call.enqueue(new Callback<Wallethistory>() {
            @Override
            public void onResponse(Call<Wallethistory> call, Response<Wallethistory> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {

                    if (response.body().getData()!=null) {
                        wallet.clear();
                        for (i = 0; i < response.body().getData().size(); i++) {
                            wallet.addAll(Arrays.asList(response.body().getData().get(i)));
                        }
                        Walletlist();
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Wallethistory> call, Throwable t) {

            }


        });

    }
// Interface method
    @Override
    public void onMethodCallback() {
        wallethistory();
    }
//    Fragment Transaction
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment) {

        fragmentTransactionChange= getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }

}