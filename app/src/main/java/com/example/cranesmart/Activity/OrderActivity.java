package com.example.cranesmart.Activity;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cranesmart.Adapter.OrderAdapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.Orderlistpojo.OrderDatum;
import com.example.cranesmart.pojo.Orderlistpojo.Orderlist;

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
    ArrayList<OrderDatum> list;
    ImageView menu;
    int i;
    SwipeRefreshLayout swipetorefresh;
    TextView text,order;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_order, container, false);
        recycleorders=view.findViewById(R.id.recycleorders);
        text=view.findViewById(R.id.text);
        order=view.findViewById(R.id.order);
        swipetorefresh =view. findViewById(R.id.swipetorefresh);
        OrderList();
        list = new ArrayList<>();
        return view;
    }
//    Adapter
    private void Oredrlist(){
        layoutManager = new LinearLayoutManager(getContext());
        recycleorders.setLayoutManager(layoutManager);
        final OrderAdapter adapter = new OrderAdapter(list,this);
        recycleorders.setHasFixedSize(true);
        recycleorders.setAdapter(adapter);
        swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                adapter.notifyDataSetChanged();
                OrderList();
                swipetorefresh.setRefreshing(false);
            }
        });
        swipetorefresh.setColorSchemeResources(R.color.blue);
        adapter.notifyDataSetChanged();
    }
//    Order List Api
        private void OrderList() {
            final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sh=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Orderlist> call = service.listorder(value);
        call.enqueue(new Callback<Orderlist>() {
            @Override
            public void onResponse(Call<Orderlist> call, Response<Orderlist> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getOrderData()!=null&&!(response.body().getOrderData().isEmpty())&&!(response.body().getOrderData().equals(" "))) {
                        list.clear();
                        for (i = 0; i < response.body().getOrderData().size(); i++) {
                            if( response.body().getOrderData().get(i).getProductInfo().size()>0) {
                                if (response.body().getOrderData().get(i).getProductInfo().get(0) != null && !(response.body().getOrderData().get(i).getProductInfo().get(0).equals(" "))) {
                                    list.addAll(Arrays.asList(response.body().getOrderData().get(i)));
                                    order.setVisibility(View.VISIBLE);
                                    text.setVisibility(View.GONE);
                                }
                                else {
                                    text.setVisibility(View.VISIBLE);
                                }
                            }
                            else{
                                order.setVisibility(View.GONE);
                                text.setVisibility(View.VISIBLE);
                            }
                        }
                        Oredrlist();
                    }
                    else {
                        text.setVisibility(View.VISIBLE);
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
//    interface Method
    @Override
    public void onMethodCallback() {
        OrderList();
    }
}