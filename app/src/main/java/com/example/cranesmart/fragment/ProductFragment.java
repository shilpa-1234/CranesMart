package com.example.cranesmart.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Adapter.ProductListAdapter;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.productlist.ProductPojo;
import com.example.cranesmart.pojo.productlist.Productpojoretro;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {
    RecyclerView recyclerviewsubcategory1;
    String category;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ProductPojo> datalist;
    Integer i;
    TextView textview1;
    ImageView noproduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_fragment, container, false);
        recyclerviewsubcategory1=view.findViewById(R.id.recyclerviewsubcategory1);
        noproduct=view.findViewById(R.id.noproduct);
         textview1=view.findViewById(R.id.textview1);
        SharedPreferences preferences= getContext().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String text=preferences.getString("textview","0");
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        textview1.setText(text);
        datalist=new ArrayList<ProductPojo>();
        Bundle arguments = getArguments();
        category = arguments.getString("catID");
        showcategory();
        return view;
    }

    private void showcategory(){

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        Call<Productpojoretro> call = service.product(category);
        call.enqueue(new Callback<Productpojoretro>() {
            @Override
            public void onResponse(Call<Productpojoretro> call, Response<Productpojoretro> response) {

                progressDialog.dismiss();
                recyclerviewsubcategory1.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                recyclerviewsubcategory1.setLayoutManager(layoutManager);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                recyclerviewsubcategory1.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    ProductListAdapter mAdapter = new ProductListAdapter(datalist);
                recyclerviewsubcategory1.setAdapter(mAdapter);

                   if(response.body().getData().size()>0) {
                       for (i = 0; i < response.body().getData().size(); i++) {
                           datalist.addAll(Arrays.asList(response.body().getData().get(i)));

                       }

                       mAdapter.notifyDataSetChanged();
                   }
                   else{
                       noproduct.setVisibility(View.VISIBLE);

                   }
            }




            @Override
            public void onFailure(Call<Productpojoretro> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
