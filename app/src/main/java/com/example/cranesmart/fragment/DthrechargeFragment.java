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
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelist;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelistforall;
import com.example.cranesmart.pojo.operatorlist.DTH.DTHlist;
import com.example.cranesmart.pojo.operatorlist.DTH.Dth;
import com.example.cranesmart.pojo.recharge.Rechargepojo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DthrechargeFragment extends Fragment {
    LinearLayout lisitingopen;
    TextView provider,code;
    String Operator,operator;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;
    ArrayList<Dth> sites;
    ArrayList<Circlelistforall> sites2;
    EditText customerid,Amount;
    Button confirm;
    TextInputLayout oper4,oper1,oper2,oper3;
    int i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dthrecharge, container, false);
        lisitingopen=view.findViewById(R.id.lisitingopen);
        SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        provider=view.findViewById(R.id.provider);
        code=view.findViewById(R.id.code);
        Amount=view.findViewById(R.id.Amount);
        customerid=view.findViewById(R.id.customerid);
        oper1=view.findViewById(R.id.oper1);
        oper2=view.findViewById(R.id.oper2);
        oper3=view.findViewById(R.id.oper3);
        oper4=view.findViewById(R.id.oper4);
        confirm=view.findViewById(R.id.confirm);
        sites=new ArrayList<Dth>();
        sites2=new ArrayList<Circlelistforall>();
        arrayAdapter= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        arrayAdapter2= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!(provider.getText().toString().isEmpty())) {
//                    lisitingopen.setVisibility(View.VISIBLE);
//                }
                setProvider();

            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circlelist();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisitingopen.setVisibility(View.VISIBLE);
                 if (provider.getText().toString().isEmpty()) {
                     oper1.setError("Select your provider");
                }
                 else if (customerid.getText().toString().isEmpty()) {
                     oper2.setError("Enter your customer card number");
                 }
                 else if (code.getText().toString().isEmpty()) {
                     oper3.setError("Select your operator");
                 }else if (Amount.getText().toString().isEmpty()) {
                     oper4.setError("Enter your amount");
                 }
                 else {
                     oper1.setErrorEnabled(false);
                     oper2.setErrorEnabled(false);
                     oper3.setErrorEnabled(false);
                     oper4.setErrorEnabled(false);
                recharge();
            }
            }
        });
        return view;
    }
    public void setProvider() {
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
        Call<DTHlist> call = service.list2("DTH");
        call.enqueue(new Callback<DTHlist>() {
            @Override
            public void onResponse(Call<DTHlist> call, final Response<DTHlist> response) {
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
                        if(provider.getText().toString().isEmpty()) {
                            lisitingopen.setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                        }
                    }
                });
                builderSingle.setAdapter( arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter.getItem(which));
                        provider.setText(arrayAdapter.getItem(which).toString().trim());
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
            public void onFailure(Call<DTHlist> call, Throwable t) {
             progressDialog.dismiss();
            }


        });
    }
    public void circlelist() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrayAdapter2.clear();
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Circlelist> call = service.circleList();
        call.enqueue(new Callback<Circlelist>() {
            @Override
            public void onResponse(Call<Circlelist> call, final Response<Circlelist> response) {
                progressDialog.dismiss();
                for(i=0;i<response.body().getData().size();i++) {
                    arrayAdapter2.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                    sites2.addAll(Arrays.asList(response.body().getData().get(i)));
                }

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                builderSingle.setCancelable(false);
                builderSingle.setIcon(R.drawable.ic_baseline_account_balance_wallet_24);
                TextView textView = new TextView(getContext());
                textView.setText("Select Your Circle:-");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                builderSingle.setCustomTitle(textView);
                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter( arrayAdapter2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter2.getItem(which));
                        code.setText(arrayAdapter2.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("circle",response.body().getData().get(which).getCode().trim().toString());
                        editor.commit();
                        editor.apply();
                        Operator=sites2.get(which).getName().toString();
                        operator=sites2.get(which).getCode().toString();
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
            public void onFailure(Call<Circlelist> call, Throwable t) {
              progressDialog.dismiss();
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
        String circlecode=pref.getString("circle","0");
        String amount=Amount.getText().toString();        //Defining retrofit api service
        String cardNumber=customerid.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Rechargepojo> call = service.rechargepos(userid,Sharedprefence.Dthrecharge,operator,circlecode,amount,cardNumber);
        call.enqueue(new Callback<Rechargepojo>() {
            @Override
            public void onResponse(Call<Rechargepojo> call, final Response<Rechargepojo> response) {
                progressDialog.dismiss();
                SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("otp",response.body().getOtp());
                editor.commit();
                editor.apply();
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Rechargepojo> call, Throwable t) {
             progressDialog.dismiss();
            }


        });

    }
}