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

import com.example.cranesmart.Adapter.Cranesmartpointhistoryadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.cranemartpoint.Cranemartpoint;
import com.example.cranesmart.pojo.cranemartpoint.cranemartpointpojo;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CranesmartpointActivity extends AppCompatActivity implements Adapterinterface {
RecyclerView recyclehistory;
    ArrayList<Cranemartpoint> wallet;
    RecyclerView.LayoutManager layoutManager;
    ImageView menu1;
    int i;
    TextView text;
    SwipeRefreshLayout swipetorefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cranesmartpoint);
        final FragmentTransaction fragmentTransactionProfile = CranesmartpointActivity.this.getSupportFragmentManager().beginTransaction();
        menu1=findViewById(R.id.menu1);
        swipetorefresh=findViewById(R.id.swipetorefresh);
        text=findViewById(R.id.text);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new WalletFragment();

                fragment_transaction1(fragmentTransactionProfile, frag);
            }
        });
        recyclehistory=findViewById(R.id.recyclehistory);
        wallet=new ArrayList<>();
        cranespointhistory();
    }
//    Adapter
    private void cranespointlist(){
        layoutManager = new LinearLayoutManager(CranesmartpointActivity.this);
        recyclehistory.setLayoutManager(layoutManager);
        final Cranesmartpointhistoryadapter adapter = new Cranesmartpointhistoryadapter(wallet,CranesmartpointActivity.this);
        recyclehistory.setHasFixedSize(true);
        recyclehistory.setAdapter(adapter);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wallet.clear();
                cranespointhistory();
                adapter.notifyDataSetChanged();
                swipetorefresh.setRefreshing(false);
            }
        });
        swipetorefresh.setColorSchemeResources(R.color.blue);
        adapter.notifyDataSetChanged();
    }
//    Point History Api
    private void cranespointhistory() {
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
        Call<cranemartpointpojo> call = service.cranes(value);
        call.enqueue(new Callback<cranemartpointpojo>() {
            @Override
            public void onResponse(Call<cranemartpointpojo> call, Response<cranemartpointpojo> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getData()!=null&&!(response.body().getData().isEmpty())&&!(response.body().getData().equals(""))) {
                        wallet.clear();
                        for (i = 0; i < response.body().getData().size(); i++) {
                            wallet.addAll(Arrays.asList(response.body().getData().get(i)));
                            text.setVisibility(View.GONE);
                        }
                        cranespointlist();
                    }
                    else {
                        text.setVisibility(View.GONE);
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<cranemartpointpojo> call, Throwable t) {

            }


        });

    }

//    interface method
    @Override
    public void onMethodCallback() {
        cranespointhistory();
    }
//    Fragment Transaction method
    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment) {

        fragmentTransactionChange= getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }
}
