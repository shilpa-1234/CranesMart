package com.example.cranesmart.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.DashboardActivity;
import com.example.cranesmart.Activity.RechargeActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Adapter.Demo_Adapter;
import com.example.cranesmart.Adapter.ImageAdapter;
import com.example.cranesmart.R;
import com.example.cranesmart.Adapter.RecyclerAdapter;
import com.example.cranesmart.pojo.dashboard.CategoryDatum;
import com.example.cranesmart.pojo.dashboard.Product;
import com.example.cranesmart.pojo.dashboard.re;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashFragment extends Fragment {
    RecyclerView recyclerview,recycleview1;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> itemList;
    ArrayList<CategoryDatum> listdata;
    ArrayList<Product>data;
    ViewPager mViewPager;
    Context context;
    LinearLayout broadband,mobile,Dth,Datacard,Electronics,Gas,Ladline,water,flight,hotel;

    private RecyclerView.Adapter mAdapter;
    int page_position = 0;
    private LinearLayout ll_dots;
    private TextView[] dots;
    private static int currentPage = 0;
    private static int NUM_PAGES = 5;
    ImageView a1;
int i;
Button search;
DotsIndicator dotsIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        mViewPager = view.findViewById(R.id.viewPage);
        context = container.getContext();
        itemList = new ArrayList<String>();
        listdata=new ArrayList<CategoryDatum>();
        data=new ArrayList<Product>();
        recyclerview = view.findViewById(R.id.recyclerview);
        recycleview1=view.findViewById(R.id.recycleview1);
        mobile=view.findViewById(R.id.mobile);
        Dth=view.findViewById(R.id.Dth);
        Datacard=view.findViewById(R.id.Datacard);
        Electronics=view.findViewById(R.id.Electronics);
        Ladline=view.findViewById(R.id.ladline);
        water=view.findViewById(R.id.water);
        Gas=view.findViewById(R.id.Gas);
        search=view.findViewById(R.id.search);
        broadband=view.findViewById(R.id.broadband);
        flight=view.findViewById(R.id.flight);
        hotel=view.findViewById(R.id.hotel);
        a1=view.findViewById(R.id.a1);
        dotsIndicator = (DotsIndicator) view.findViewById(R.id.dots_indicator);

        userSignUp();
        final FragmentTransaction fragmentTransactionProfile = getActivity().getSupportFragmentManager().beginTransaction();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment changeFragment=new SearchproductFragment();
                FragmentTransaction fragmentTransactionChange = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransactionChange.add(R.id.frame, changeFragment,"fragment");
                fragmentTransactionChange.addToBackStack(null);
                fragmentTransactionChange.commit();

            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RechargeActivity.class);
                intent.putExtra("poss",0);
                startActivity(intent);
            }
        });

        Dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RechargeActivity.class);
                intent.putExtra("poss",1);
                startActivity(intent);
            }
        });
        Datacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RechargeActivity.class);
                intent.putExtra("poss",2);
                startActivity(intent);
            }
        });
        Electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RechargeActivity.class);
                intent.putExtra("poss",3);
                startActivity(intent);
            }
        });
        Ladline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RechargeActivity.class);
                intent.putExtra("poss",4);
                startActivity(intent);
            }
        });
        Gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
            }
        });
        broadband.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
            }
        });
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

            }
        });

//        addBottomDots(0);
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position ==itemList.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                mViewPager.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);


        SharedPreferences preferences=context.getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","1");
        editor.commit();
        editor.apply();

        return view;

    }


    private void showcategory(){
        recycleview1.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recycleview1.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recycleview1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recycleview1.setLayoutManager(layoutManager);
        RecyclerAdapter mAdapter = new RecyclerAdapter(listdata);
        recycleview1.setAdapter(mAdapter);
//        recycleview1.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();

    }
    private void initViews(){
        Demo_Adapter adapter= new Demo_Adapter(data);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerview.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);


    }

    private void userSignUp() {

//
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<re> call = service.listRepos();
        call.enqueue(new Callback<re>() {
            @Override
            public void onResponse(Call<re> call, Response<re> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                Log.e("@@", String.valueOf(response.toString()));
                Log.e("@@", String.valueOf(response.body().getFiveBanner().get(0).getBannerImage1()));
                Log.e("@@", String.valueOf(response.body().getFiveBanner().get(0).getBannerImage2()));
                Log.e("@@", String.valueOf(response.body().getFiveBanner().get(0).getBannerImage3()));
                Log.e("@@", String.valueOf(response.body().getFiveBanner().get(0).getBannerImage4()));
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage1());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage2());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage3());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage4());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage5());
                if (Integer.valueOf(response.body().getStatus().toString()) == 1) {

                    ImageAdapter adapterView = new ImageAdapter(context, itemList);
                    mViewPager.setAdapter(adapterView);
                    dotsIndicator.setViewPager(mViewPager);

                    for (i=0 ; i<response.body().getCategoryData().size(); i++){
                        listdata.addAll(Arrays.asList(response.body().getCategoryData().get(i)));
                    }
                    showcategory();
                    Log.e("@@1",response.body().getCategoryList().get(0).getProduct().get(0).getProductId());
                    Log.e("@@1",response.body().getCategoryList().get(0).getProduct().get(0).getProductImg());
                    Log.e("@@1",response.body().getCategoryList().get(0).getProduct().get(0).getPrice());
                    Log.e("@@1",response.body().getCategoryList().get(0).getProduct().get(0).getPrice());


                    for (i=1 ; i<response.body().getCategoryList().get(0).getProduct().size();i++){
                        Log.e("@@!@",response.body().getCategoryList().get(0).getProduct().get(i).getProductImg());
                        Log.d("@@!@",response.body().getCategoryList().get(0).getProduct().get(i).getProductId());
                        Log.d("@@!@",response.body().getCategoryList().get(0).getProduct().get(i).getPrice());
                        Log.d("@@!@",response.body().getCategoryList().get(0).getProduct().get(i).getProductName());
                        data.addAll(Arrays.asList((response.body().getCategoryList().get(0).getProduct().get(i))));
                    }
                          initViews();


                }
            }

            @Override
            public void onFailure(Call<re> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment, String tag) {

        fragmentTransactionChange= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.add(R.id.frame, changeFragment,"fragment");
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }
}