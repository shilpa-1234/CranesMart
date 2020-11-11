package com.example.cranesmart.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.CranesmartpointActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Activity.PremiumHistoryFragment;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WalletFragment extends Fragment {
TextView rupees,rupees1,rupees2;
    TextView premiumHistory,cmHistory,upgrademember;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wallet, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        rupees=view.findViewById(R.id.rupees);
        rupees1=view.findViewById(R.id.rupees1);
        rupees2=view.findViewById(R.id.rupees2);
        premiumHistory=view.findViewById(R.id.premiumHistory);
        cmHistory=view.findViewById(R.id.cmHistory);
        upgrademember=view.findViewById(R.id.upgrademember);
        premiumHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),PremiumHistoryFragment.class);
                startActivity(i);
            }
        });
        cmHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), CranesmartpointActivity.class);
                startActivity(i);
            }
        });
        userdetail();
        return  view;

    }
    private void userdetail() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        SharedPreferences sh=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0"); assert value != null;



        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
                rupees.setText("₹"+" "+response.body().getUserDetail().getPremiumWalletBalance().trim());
                rupees1.setText("P."+" "+response.body().getUserDetail().getCmPoints().trim());

            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
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