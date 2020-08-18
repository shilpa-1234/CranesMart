package com.example.cranesmart.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.Adapter.ImageAdapter;
import com.example.cranesmart.Adapter.ProductAdapter;
import com.example.cranesmart.R;
import com.example.cranesmart.Adapter.RecyclerAdapter;
import com.example.cranesmart.pojo.CategoryDatum;
import com.example.cranesmart.pojo.CategoryList;
import com.example.cranesmart.pojo.Product;
import com.example.cranesmart.pojo.re;

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
    ArrayList<Product> product;
    ViewPager mViewPager;
    Context context;
    private RecyclerView.Adapter mAdapter;
    int page_position = 0;
    private LinearLayout ll_dots;
    private TextView[] dots;
    private static int currentPage = 0;
    private static int NUM_PAGES = 5;
    ImageView a1;
int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        mViewPager = view.findViewById(R.id.viewPage);
        itemList = new ArrayList<String>();
        listdata=new ArrayList<CategoryDatum>();
        recyclerview = view.findViewById(R.id.recyclerview);
        recycleview1=view.findViewById(R.id.recycleview1);
        a1=view.findViewById(R.id.a1);
        ll_dots = view.findViewById(R.id.ll_dots);
        userSignUp();
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new SearchFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        addBottomDots(0);
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




        return view;

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[itemList.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }
    private void showcategory(){
        recycleview1.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recycleview1.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5);
        recycleview1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        RecyclerAdapter mAdapter = new RecyclerAdapter(listdata);
        recycleview1.setAdapter(mAdapter);
        recycleview1.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();

    }
    private void initViews(){
        ProductAdapter adapter= new ProductAdapter(product);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
         //        recyclerview.setNestedScrollingEnabled(HorizontalScrollView);
    }
    private void userSignUp() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading...");
        progressDialog.show();

        //getting the user values

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);

        //defining the call
        Call<re> call = service.listRepos();
        call.enqueue(new Callback<re>() {
            @Override
            public void onResponse(Call<re> call, Response<re> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                Log.e("@@", String.valueOf(response.body().getStatus()));
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
//                    Log.e("@@41",response.body().getCategoryList().get(0).getCategory().toString());


                    ImageAdapter adapterView = new ImageAdapter(context, itemList);
                    mViewPager.setAdapter(adapterView);

                    for (i=1 ; i<response.body().getCategoryData().size(); i++){
                        listdata.addAll(Arrays.asList(response.body().getCategoryData().get(i)));
                    }
                    showcategory();
//                    for(i=1;i<response.body().getCategoryList().size();i++){
//                  //   product.addAll(Arrays.asList(response.body().get);
//                    }

//                    Log.e("@@list",response.body().getFiveBanner().get()..toString());


      /*              RecyclerAdapter adapter = new RecyclerAdapter(itemList);
                    adapter.notifyDataSetChanged();
                    recyclerview.setHasFixedSize(true);
                    recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                }
                //displaying the message from the response as toast
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<re> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}