package com.example.cranesmart.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Activity.DashboardActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WalletFragment extends Fragment {
TextView rupees,rupees1,rupees2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wallet, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        rupees=view.findViewById(R.id.rupees);
        rupees1=view.findViewById(R.id.rupees1);
        rupees2=view.findViewById(R.id.rupees2);
        editor.commit();
        editor.apply();
        userdetail();
        return  view;

    }
    private void userdetail() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        SharedPreferences sh=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;



        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
                rupees.setText("₹"+response.body().getUserDetail().getPremiumWalletBalance().trim());
                rupees1.setText("₹"+response.body().getUserDetail().getCmPoints().trim());

            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}