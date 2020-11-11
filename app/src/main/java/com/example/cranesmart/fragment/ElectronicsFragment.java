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

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.Verification1;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.operatorlist.Electricity.Electricity;
import com.example.cranesmart.pojo.operatorlist.Electricity.Electricitylist;
import com.example.cranesmart.pojo.recharge.Landlinepojo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ElectronicsFragment extends Fragment {
             LinearLayout lisitingopen;
             TextView Billers;
             EditText Boardname,name,reference_id,Amount;
             Button confirm;
    String Operator,operator;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Electricitylist> sites;
    TextInputLayout ele,elec,elect,electr,electri;
    int i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_electronics, container, false);
        lisitingopen=view.findViewById(R.id.lisitingopen);
        SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        Boardname=view.findViewById(R.id.Boardname);
        name=view.findViewById(R.id.name);
        ele=view.findViewById(R.id.ele);
        elec=view.findViewById(R.id.elec);
        elect=view.findViewById(R.id.elect);
        electr=view.findViewById(R.id.electr);
        electri=view.findViewById(R.id.electri);
        reference_id=view.findViewById(R.id.reference_id);
        Amount=view.findViewById(R.id.Amount);
        confirm=view.findViewById(R.id.confirm1);
        sites=new ArrayList<Electricitylist>();
        arrayAdapter= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        Billers=view.findViewById(R.id.Billers);
        Billers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circlelist();
//                if(!(Billers.getText().toString().isEmpty())){
//                    lisitingopen.setVisibility(View.VISIBLE);
//                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisitingopen.setVisibility(View.VISIBLE);
             if (Billers.getText().toString().isEmpty()) {
                 ele.setError("Select your operator");  }
             else if (Boardname.getText().toString().isEmpty()) {
                 elec.setError("Enter your Account number");}
             else if (name.getText().toString().isEmpty()) {
                 elect.setError("Enter your Customer name");}
             else if (reference_id.getText().toString().isEmpty()) {
                 electr.setError("Enter your reference_id");}
                 else if (Amount.getText().toString().isEmpty()) {
                 electri.setError("Enter your Amount");}
                 else {
                     ele.setErrorEnabled(false);
                 elec.setErrorEnabled(false);
                 elect.setErrorEnabled(false);
                 electr.setErrorEnabled(false);
                 electri.setErrorEnabled(false);
                     recharge();
             }
            }

        });
        return view;
    }
    public void circlelist() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrayAdapter.clear();
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Electricity> call = service.list5("Electricity");
        call.enqueue(new Callback<Electricity>() {
            @Override
            public void onResponse(Call<Electricity> call, final Response<Electricity> response) {
                progressDialog.dismiss();
                for(i=0;i<response.body().getData().size();i++) {
                    arrayAdapter.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                    sites.addAll(Arrays.asList(response.body().getData().get(i)));
                }

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                builderSingle.setCancelable(false);
                builderSingle.setIcon(R.drawable.ic_baseline_account_balance_wallet_24);
                TextView textView = new TextView(getContext());
                textView.setText("Select Your Provider:-");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                builderSingle.setCustomTitle(textView);
                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Billers.getText().toString().isEmpty()) {
                            lisitingopen.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    }
                });
                builderSingle.setAdapter( arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter.getItem(which));
                        Billers.setText(arrayAdapter.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("operator code",response.body().getData().get(which).getCode().trim().toString());
                        Operator=sites.get(which).getName().toString();
                        operator=sites.get(which).getCode().toString();
                    }
                });
                AlertDialog alertDialog = builderSingle.create();
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_button);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.show();

                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Electricity> call, Throwable t) {

            }


        });
    }
    public void recharge(){
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SharedPreferences pref = getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String userid=pref.getString(Sharedprefence.UserID,"0");
        String operator=pref.getString("operatorcode","0");
        String Account=Boardname.getText().toString();        //Defining retrofit api service
        String customername=name.getText().toString();        //Defining retrofit api service
        String referenc_id=reference_id.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Landlinepojo> call = service.electronics(userid,Sharedprefence.ElectricityBill,operator,Account,customername,referenc_id,amount);
        call.enqueue(new Callback<Landlinepojo>() {
            @Override
            public void onResponse(Call<Landlinepojo> call, final Response<Landlinepojo> response) {
                progressDialog.dismiss();
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Landlinepojo> call, Throwable t) {
                   progressDialog.dismiss();
            }


        });

    }


}