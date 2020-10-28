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
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelist;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelistforall;
import com.example.cranesmart.pojo.operatorlist.Datacard.Datacard;
import com.example.cranesmart.pojo.operatorlist.Datacard.Datacardlist;
import com.example.cranesmart.pojo.operatorlist.Landline.Landline;
import com.example.cranesmart.pojo.operatorlist.Landline.Landlinelist;
import com.example.cranesmart.pojo.recharge.Landlinepojo;
import com.example.cranesmart.pojo.recharge.Rechargepojo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandlineFragment extends Fragment {
    String Operator,operator;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Landlinelist> sites;
    EditText landlinenumber,Amount;
    LinearLayout lisitingopen;
    TextView provider,code;
    Button getbill;
    ArrayAdapter<String> arrayAdapter2;
    ArrayList<Circlelistforall> sites2;
    int i;
    TextInputLayout oper4,oper1,oper2,oper3;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_landline, container, false);
        provider=view.findViewById(R.id.provider);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        code=view.findViewById(R.id.code);
        oper1=view.findViewById(R.id.oper1);
        oper2=view.findViewById(R.id.oper2);
        oper3=view.findViewById(R.id.oper3);
        oper4=view.findViewById(R.id.oper4);
        landlinenumber=view.findViewById(R.id.landlinenumber);
        Amount=view.findViewById(R.id.Amount);
        getbill=view.findViewById(R.id.getbill);
        lisitingopen=view.findViewById(R.id.lisitingopen);
        sites2=new ArrayList<Circlelistforall>();
        arrayAdapter2= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        getbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(provider.getText().toString().isEmpty()){
                    oper1.setError("Select Your Provider");
                }
                else if(code.getText().toString().isEmpty()){
                    oper2.setError("Select Your Circle");
                }
                else if(Amount.getText().toString().isEmpty()){
                    oper3.setError("Enter Your Mobile Number");
                }
                else if(landlinenumber.getText().toString().length()>10){
                    oper4.setError("Enter Your Landline Number");
                }
                else{
                    oper1.setErrorEnabled(false);
                    oper2.setErrorEnabled(false);
                    oper3.setErrorEnabled(false);
                    oper4.setErrorEnabled(false);
                             recharge();
                }
            }
        });
        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisitingopen.setVisibility(View.VISIBLE);
                operatorlist();
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circlelist();
            }
        });
        sites=new ArrayList<Landlinelist>();
        arrayAdapter= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        return view;
    }
    public void operatorlist() {
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
        Call<Landline> call = service.list4("Landline");
        call.enqueue(new Callback<Landline>() {
            @Override
            public void onResponse(Call<Landline> call, final Response<Landline> response) {
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
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter( arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            String strName = sites.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter.getItem(which));
                        provider.setText(arrayAdapter.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("operator code",response.body().getData().get(which).getCode().trim().toString());
                        Operator=sites.get(which).getName().toString();

                        operator=sites.get(which).getCode().toString();
                        Log.d("@@sipner",arrayAdapter.getItem(which).toString().trim());
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
            public void onFailure(Call<Landline> call, Throwable t) {

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
//                            String strName = sites.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage(arrayAdapter2.getItem(which));
                        code.setText(arrayAdapter2.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("circle",response.body().getData().get(which).getCode().trim().toString());
                        editor.commit();
                        editor.apply();
                        Operator=sites2.get(which).getName().toString();

                        operator=sites2.get(which).getCode().toString();
                        Log.d("operatorprepaidcode1", operator);
                        Log.d("@@sipner",arrayAdapter2.getItem(which).toString().trim());
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
            public void onFailure(Call<Circlelist> call, Throwable t) {

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
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
        String userid=pref.getString("userID","0");
        String operator=pref.getString("operatorcode","0");
        String circlecode=pref.getString("circle","0");
        String mobile=landlinenumber.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Log.d("!!", "onResponse: "+operator.toString());
        Call<Landlinepojo> call = service.landline(userid,"5",operator,circlecode,mobile,amount);
        call.enqueue(new Callback<Landlinepojo>() {
            @Override
            public void onResponse(Call<Landlinepojo> call, final Response<Landlinepojo> response) {
                progressDialog.dismiss();
                Log.d("!!", "onResponse: "+response.toString());
                Log.d("!!", "onResponse: "+response.body().toString());
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();


//                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Landlinepojo> call, Throwable t) {

            }


        });

    }

}