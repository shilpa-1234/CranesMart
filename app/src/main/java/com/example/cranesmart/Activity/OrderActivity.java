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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cranesmart.Adapter.OrderAdapter;
import com.example.cranesmart.Adapter.Rechargehistoryadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.EditprofileFragment;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.Orderlistpojo.OrderDatum;
import com.example.cranesmart.pojo.Orderlistpojo.Orderlist;
import com.example.cranesmart.pojo.Orderlistpojo.ProductInfo;
import com.example.cranesmart.pojo.RechargeHistory.Rechargehistorypojo;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends Fragment implements Adapterinterface {
    RecyclerView recycleorders;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ProductInfo> list;
    ImageView menu;
    int i;
    SwipeRefreshLayout swipetorefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_order, container, false);
        recycleorders=view.findViewById(R.id.recycleorders);
        swipetorefresh =view. findViewById(R.id.swipetorefresh);
        final FragmentTransaction fragmentTransactionProfile = getActivity().getSupportFragmentManager().beginTransaction();
//        menu = view.findViewById(R.id.menu);
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment frag = new EditprofileFragment();
//
//                fragment_transaction1(fragmentTransactionProfile, frag);
//            }
//        });
        OrderList();
        list = new ArrayList<>();
        return view;
    }
    private void Oredrlist(){
        layoutManager = new LinearLayoutManager(getContext());
        recycleorders.setLayoutManager(layoutManager);
        OrderAdapter adapter = new OrderAdapter(list,this);
        recycleorders.setHasFixedSize(true);
        recycleorders.setAdapter(adapter);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                OrderList();
                swipetorefresh.setRefreshing(false);
            }
        });
        swipetorefresh.setColorSchemeResources(R.color.blue);
        adapter.notifyDataSetChanged();
    }
        private void OrderList() {

            final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sh=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0");
            Log.d("@@userid", "OrderList: "+value);
        Call<Orderlist> call = service.listorder(value);
        call.enqueue(new Callback<Orderlist>() {
            @Override
            public void onResponse(Call<Orderlist> call, Response<Orderlist> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getOrderData()!=null) {
                        list.clear();
                        for (i = 0; i < response.body().getOrderData().size(); i++) {
                            if( response.body().getOrderData().get(i).getProductInfo().size()>0) {
                                Log.d("@@@ordersize!!", "onResponse: "+response.body().getOrderData().get(i).getProductInfo().size());
                                if (response.body().getOrderData().get(i).getProductInfo().get(0) != null && !(response.body().getOrderData().get(i).getProductInfo().get(0).equals(" "))) {
                                    Log.d("@@@ordersize!!", "onResponse: " + response.body().getOrderData().get(i).getProductInfo().size());
                                    list.addAll(Arrays.asList(response.body().getOrderData().get(i).getProductInfo().get(0)));
                                    Log.d("@@@order!!", "onResponse: " + response.body().getOrderData().get(i).getProductInfo().get(0));
                                }
                            }
                            else{
//                                Toast.makeText(getContext(), "No Order Placed By You", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Oredrlist();
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Orderlist> call, Throwable t) {
                progressDialog.dismiss();
            }


        });

    }

    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment) {

        fragmentTransactionChange = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }

    @Override
    public void onMethodCallback() {
        OrderList();
    }
}