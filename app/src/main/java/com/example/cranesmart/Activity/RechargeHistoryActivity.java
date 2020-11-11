package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cranesmart.Adapter.Rechargehistoryadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.RechargeHistory.Rechargehistorypojo;
import com.example.cranesmart.pojo.RechargeHistory.rechargehistory;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RechargeHistoryActivity extends AppCompatActivity implements Adapterinterface {
    RecyclerView recyclehistory;
    ArrayList<rechargehistory> history;
    RecyclerView.LayoutManager layoutManager;
    ImageView menu;
    int i;
    TextView text;
    SwipeRefreshLayout swipetorefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentTransaction fragmentTransactionProfile = RechargeHistoryActivity.this.getSupportFragmentManager().beginTransaction();
        setContentView(R.layout.activity_recharge_history);
        recyclehistory = findViewById(R.id.recyclehistory);
        text = findViewById(R.id.text);
        swipetorefresh = findViewById(R.id.swipetorefresh);
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new WalletFragment();

                fragment_transaction1(fragmentTransactionProfile, frag);
            }
        });
        history = new ArrayList<>();
        Rechargehistory();
    }
//    Adapter
    private void Walletlist(){
        layoutManager = new LinearLayoutManager(RechargeHistoryActivity.this);
        recyclehistory.setLayoutManager(layoutManager);
        final Rechargehistoryadapter adapter = new Rechargehistoryadapter(history,this);
        recyclehistory.setHasFixedSize(true);
        recyclehistory.setAdapter(adapter);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                history.clear();
                Rechargehistory();
                adapter.notifyDataSetChanged();
                swipetorefresh.setRefreshing(false);
            }
        });
        swipetorefresh.setColorSchemeResources(R.color.blue);
        adapter.notifyDataSetChanged();
    }
//    Recharge History Wallet
    private void Rechargehistory() {
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
        Call<Rechargehistorypojo> call = service.historyrecharge(value);
        call.enqueue(new Callback<Rechargehistorypojo>() {
            @Override
            public void onResponse(Call<Rechargehistorypojo> call, Response<Rechargehistorypojo> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getData()!=null&&!(response.body().getData().isEmpty())&&!(response.body().getData().equals(" "))) {
                        history.clear();
                        for (i = 0; i < response.body().getData().size(); i++) {
                            history.addAll(Arrays.asList(response.body().getData().get(i)));
                            text.setVisibility(View.GONE);
                        }
                        Walletlist();
                    }
                    else{
                        text.setVisibility(View.VISIBLE);
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Rechargehistorypojo> call, Throwable t) {

            }


        });

    }
// Interface Method
    @Override
    public void onMethodCallback() {
        Rechargehistory();
    }
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment) {

        fragmentTransactionChange = getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }
}