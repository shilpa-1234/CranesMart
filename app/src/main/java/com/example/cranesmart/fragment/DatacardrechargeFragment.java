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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.Verification1;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelist;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelistforall;
import com.example.cranesmart.pojo.operatorlist.Datacard.Datacard;
import com.example.cranesmart.pojo.operatorlist.Datacard.Datacardlist;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatacardrechargeFragment extends Fragment {
    String Operator,operator;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Datacardlist> sites;
    ArrayAdapter<String> arrayAdapter2;
    ArrayList<Circlelistforall> sites2;
    EditText mobilenumber,Amount;
    TextView provider,code;
    Button buttonpaybill;
    RadioButton radioprepaid,radiopostpaid;
    LinearLayout lisitingopen;
    TextInputLayout prov,prov1,prov2,prov3;
     int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_datacardrecharge, container, false);
        final SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        provider=view.findViewById(R.id.provider);
        prov=view.findViewById(R.id.prov);
        prov1=view.findViewById(R.id.prov1);
        prov3=view.findViewById(R.id.prov3);
        prov2=view.findViewById(R.id.prov2);
        code=view.findViewById(R.id.code);
        mobilenumber=view.findViewById(R.id.mobilenumber);
        Amount=view.findViewById(R.id.Amount);
        radioprepaid=view.findViewById(R.id.radioprepaid);
        radiopostpaid=view.findViewById(R.id.radiopostpaid);
        buttonpaybill=view.findViewById(R.id.buttonpaybill);
        lisitingopen=view.findViewById(R.id.lisitingopen);
        sites=new ArrayList<Datacardlist>();
        sites2=new ArrayList<Circlelistforall>();
        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circlelist();
//                if(!(provider.getText().toString().isEmpty())){
//                    lisitingopen.setVisibility(View.VISIBLE);
//                }
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statelist();
            }
        });
        buttonpaybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisitingopen.setVisibility(View.VISIBLE);
            if(mobilenumber.getText().toString().isEmpty())
                {
                    prov1.setError("Enter your Datacard number");
                }
                else if(provider.getText().toString().isEmpty())
                {
                    prov.setError("Select your provider ");
                }
                else if(Amount.getText().toString().isEmpty())
                {
                    prov2.setError("Enter your Amount ");
                }
                else if(code.getText().toString().isEmpty())
                {
                    prov3.setError("Select your Circle");
                }
               else if(radioprepaid.isChecked()){
                   prov.setErrorEnabled(false);
                   prov1.setErrorEnabled(false);
                   prov2.setErrorEnabled(false);
                   prov3.setErrorEnabled(false);
                    recharge();
                    radioprepaid.setChecked(true);
                    radiopostpaid.setChecked(false);
                }
                else if(radiopostpaid.isChecked()){
                prov.setErrorEnabled(false);
                prov1.setErrorEnabled(false);
                prov2.setErrorEnabled(false);
                prov3.setErrorEnabled(false);
                    radioprepaid.setChecked(false);
                    radiopostpaid.setChecked(true);
                    rechargepost();
                }


            }
        });
        arrayAdapter= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        arrayAdapter2= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
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
        Call<Datacard> call = service.list3("Datacard");
        call.enqueue(new Callback<Datacard>() {
            @Override
            public void onResponse(Call<Datacard> call, final Response<Datacard> response) {
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


//

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
//                            String strName = sites.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter.getItem(which));
                        provider.setText(arrayAdapter.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("operator code",response.body().getData().get(which).getCode().trim().toString());
                        Operator=sites.get(which).getName().toString();

                        operator=sites.get(which).getCode().toString();
//
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
            public void onFailure(Call<Datacard> call, Throwable t) {

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
        String mobile=mobilenumber.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Datacard> call = service.Datacard(userid,Sharedprefence.Datacardrecharge,operator,circlecode,amount,mobile,Sharedprefence.Prepaid);
        call.enqueue(new Callback<Datacard>() {
            @Override
            public void onResponse(Call<Datacard> call, final Response<Datacard> response) {
                progressDialog.dismiss();
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Datacard> call, Throwable t) {
                progressDialog.dismiss();
            }


        });

    }
    public void rechargepost(){
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
        String mobile=mobilenumber.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<Datacard> call = service.Datacard(userid,Sharedprefence.Datacardrecharge,operator,circlecode,amount,mobile,Sharedprefence.Postpaid);
        call.enqueue(new Callback<Datacard>() {
            @Override
            public void onResponse(Call<Datacard> call, final Response<Datacard> response) {
                progressDialog.dismiss();
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Datacard> call, Throwable t) {
              progressDialog.dismiss();
            }


        });

    }
    public void statelist() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
arrayAdapter2.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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


//

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


}