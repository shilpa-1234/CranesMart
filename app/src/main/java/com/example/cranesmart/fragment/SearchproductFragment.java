package com.example.cranesmart.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.DashboardActivity;
import com.example.cranesmart.Adapter.ProductListAdapter;
import com.example.cranesmart.Adapter.SearchListAdapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.DashFragment;
import com.example.cranesmart.pojo.Search.Search;
import com.example.cranesmart.pojo.Search.Searchpojo;
import com.example.cranesmart.pojo.productlist.ProductPojo;
import com.example.cranesmart.pojo.productlist.Productpojoretro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchproductFragment extends Fragment {
    RecyclerView recyclerviewsubcategory1;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Search> datalist;
    Integer i;
    ImageView noproduct;

    EditText  search1;
    SearchListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_searchproduct, container, false);

        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        recyclerviewsubcategory1=view.findViewById(R.id.recyclerviewsubcategory1);
        noproduct=view.findViewById(R.id.noproduct);
        search1=view.findViewById(R.id.searchlist);
        datalist=new ArrayList<Search>();

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search1.getText().toString().isEmpty()){
                    search1.getBackground().setColorFilter(getResources().getColor(R.color.cb_errorRed), PorterDuff.Mode.SRC_ATOP);
                }
                else {
                    search1.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    showcategory();
                }

            }
        });
//      search1.setOnTouchListener(this);
        return view;
    }

    private void showcategory(){
//
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datalist.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        String serch=search1.getText().toString();
        Call<Searchpojo> call = service.search(serch);
        call.enqueue(new Callback<Searchpojo>() {
            @Override
            public void onResponse(Call<Searchpojo> call, Response<Searchpojo> response) {

                progressDialog.dismiss();
                 recyclerviewsubcategory1.setHasFixedSize(true);
                 // use a linear layout manager
                 layoutManager = new LinearLayoutManager(getContext());
                 recyclerviewsubcategory1.setLayoutManager(layoutManager);
                 GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                 recyclerviewsubcategory1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                  mAdapter = new SearchListAdapter(datalist);
                 recyclerviewsubcategory1.setAdapter(mAdapter);
                if(response.body().getStatus().toString().trim().equals("1")){

             if (response.body().getData().size() > 0) {
                     for (i = 1; i < response.body().getData().size(); i++) {
                         datalist.addAll(Arrays.asList(response.body().getData().get(i)));

                     }

                     mAdapter.notifyDataSetChanged();

                 }
             else{
                 noproduct.setVisibility(View.VISIBLE);

             }
             }
                else{
                    noproduct.setVisibility(View.VISIBLE);

                }
            }




            @Override
            public void onFailure(Call<Searchpojo> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment)
    {
        fragmentTransactionChange = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.replace(R.id.frame,changeFragment);
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();


    }
    @Override
    public void onResume() {
        super.onResume();
        search1.post(new Runnable() {
            @Override
            public void run() {
                search1.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(search1, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

}