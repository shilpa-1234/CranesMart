package com.example.cranesmart.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.AddressActivity;
import com.example.cranesmart.Activity.RegisterActivity;
import com.example.cranesmart.Activity.Verification1;
import com.example.cranesmart.Activity.VerificationActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelist;
import com.example.cranesmart.pojo.operatorlist.Commonliststate.Circlelistforall;
import com.example.cranesmart.pojo.operatorlist.Prepaid.Operatorlist;
import com.example.cranesmart.pojo.operatorlist.Prepaid.operatordata;
import com.example.cranesmart.pojo.operatorlist.postpaid.operatordatapost;
import com.example.cranesmart.pojo.operatorlist.postpaid.operlistpostpaid;
import com.example.cranesmart.pojo.recharge.Rechargepojo;
import com.example.cranesmart.pojo.recharge.Rechargepostpojo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MobilerechargeFragment extends Fragment {
    TextInputLayout mobile,acnmner,oper,circle,amounm;
         RadioButton radioprepaid,radiopostpaid;
         EditText mobilenumber,Amount,accnumer;
         CardView cardprepaid;
    TextView opreator,code;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<operatordata> sites;
    ArrayAdapter<String> arrayAdapter1;
    ArrayList<operatordatapost> sites1;
    ArrayAdapter<String> arrayAdapter2;
    ArrayList<Circlelistforall> sites2;
    int i;
    String Operator,operator;
    Button proceed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobilerecharge, container, false);
        radioprepaid=view.findViewById(R.id.radioprepaid);
        acnmner=view.findViewById(R.id.acnmner);
        mobile=view.findViewById(R.id.mobile);
        accnumer=view.findViewById(R.id.accnumer);
        oper=view.findViewById(R.id.oper);
        circle=view.findViewById(R.id.circle);
        amounm=view.findViewById(R.id.amounm);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        arrayAdapter= new ArrayAdapter<String>(getContext(),R.layout.selectalert);
        arrayAdapter2= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        radiopostpaid=view.findViewById(R.id.radiopostpaid);
        mobilenumber=view.findViewById(R.id.mobilenumber);
        sites=new ArrayList<operatordata>();
        arrayAdapter1= new ArrayAdapter<String>(getContext(), R.layout.selectalert);
        sites1=new ArrayList<operatordatapost>();
        sites2=new ArrayList<Circlelistforall>();
        Amount=view.findViewById(R.id.Amount);
        opreator=view.findViewById(R.id.opreator);
        proceed=view.findViewById(R.id.proceed);
        code=view.findViewById(R.id.code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circlelist();
            }
        });
        cardprepaid=view.findViewById(R.id.cardprepaid);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (mobilenumber.getText().toString().length()<=9) {
                        mobile.setError("Please enter Your mobile number");

                    }
                    else if (opreator.getText().toString().isEmpty()) {
                        oper.setError("Please select your opreator");
                        mobile.setError(null);
                        acnmner.setError(null);
                    }
                    else if (code.getText().toString().isEmpty()) {
                        circle.setError("Please select your circle");
                        mobile.setError(null);
                        oper.setError(null);
                        acnmner.setError(null);
                    } else if (Amount.getText().toString().isEmpty()) {
                        amounm.setError("Please enter Your amount");
                        mobile.setError(null);
                        acnmner.setError(null);
                        oper.setError(null);
                        circle.setError(null);
                    } else if (radiopostpaid.isChecked()) {
                        if (accnumer.getText().toString().isEmpty()) {
                            acnmner.setError("Please enter Your acc number");
                            mobile.setError(null);

                        }
                        else {
                            acnmner.setVisibility(View.VISIBLE);
                            mobile.setError(null);
                            oper.setError(null);
                            circle.setError(null);
                            amounm.setError(null);
                            acnmner.setError(null);
                            radiopostpaid.setChecked(true);
                            radioprepaid.setChecked(false);
                            rechargepostpaid();
                        }
                    }
                    else if (radioprepaid.isChecked()) {
                        acnmner.setVisibility(View.GONE);
                        mobile.setError(null);
                        oper.setError(null);
                        circle.setError(null);
                        amounm.setError(null);
                        radioprepaid.setChecked(true);
                        radiopostpaid.setChecked(false);
                        recharge();

                    }

                    }




        });
        radiopostpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acnmner.setVisibility(View.VISIBLE);
            }
        });
        radioprepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acnmner.setVisibility(View.GONE);
            }
        });
        opreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioprepaid.isChecked()) {
                    withMultiChoiceItems();
                }
                else if(radiopostpaid.isChecked()) {
                    withMultiChoice();
                }
            }
        });
        return view;
    }
    public void withMultiChoiceItems() {
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
        Call<Operatorlist> call = service.list("Prepaid");
        call.enqueue(new Callback<Operatorlist>() {
            @Override
            public void onResponse(Call<Operatorlist> call, final Response<Operatorlist> response) {
                progressDialog.dismiss();
                for(i=0;i<response.body().getData().size();i++) {
                    arrayAdapter.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                    sites.addAll(Arrays.asList(response.body().getData().get(i)));
                }
                TextView textView = new TextView(getContext());
                textView.setText("Select Your Operator:-");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);
                builderSingle.setIcon(R.drawable.ic_baseline_format_list_bulleted_24);
                builderSingle.setCancelable(false);
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
                        opreator.setText(arrayAdapter.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("operatorcode",response.body().getData().get(which).getCode().trim().toString());
                        editor.commit();
                        editor.apply();
                        Operator=sites.get(which).getName().toString();

                        operator=sites.get(which).getCode().toString();
                        Log.d("operatorprepaidcode", operator);
                        Log.d("@@sipner",arrayAdapter.getItem(which).toString().trim());
//
                    }
                });
                AlertDialog alertDialog = builderSingle.create();
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_button);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

                alertDialog.show();





            }

            @Override
            public void onFailure(Call<Operatorlist> call, Throwable t) {

            }


        });
    }
    public void withMultiChoice() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
arrayAdapter1.clear();
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        Call<operlistpostpaid> call = service.list1("Postpaid");
        call.enqueue(new Callback<operlistpostpaid>() {
            @Override
            public void onResponse(Call<operlistpostpaid> call, final Response<operlistpostpaid> response) {
                progressDialog.dismiss();
                for(i=0;i<response.body().getData().size();i++) {
                    arrayAdapter1.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                    sites1.addAll(Arrays.asList(response.body().getData().get(i)));
                }

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                builderSingle.setCancelable(false);
                builderSingle.setIcon(R.drawable.ic_baseline_account_balance_wallet_24);
                TextView textView = new TextView(getContext());
                textView.setText("Select Your Operator:-");
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

                builderSingle.setAdapter( arrayAdapter1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            String strName = sites.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);
                        builderInner.setMessage(arrayAdapter1.getItem(which));
                        opreator.setText(arrayAdapter1.getItem(which).toString().trim());
                        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("operatorpostpaidcode",response.body().getData().get(which).getCode().trim().toString());
                        editor.commit();
                        editor.apply();
                        Operator=sites1.get(which).getName().toString();

                        operator=sites1.get(which).getCode().toString();
                        Log.d("@@sipner",arrayAdapter1.getItem(which).toString().trim());
//
                    }
                });
                AlertDialog alertDialog = builderSingle.create();
//                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_button);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;                alertDialog.show();



            }

            @Override
            public void onFailure(Call<operlistpostpaid> call, Throwable t) {

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

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);
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
        String mobile=mobilenumber.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Log.d("!!", "onResponse: "+operator.toString());
        Call<Rechargepojo> call = service.recharge(userid,"1",mobile,"1",operator,circlecode,amount);
        Log.d("rechargeuserid", "recharge: "+userid);
        call.enqueue(new Callback<Rechargepojo>() {
            @Override
            public void onResponse(Call<Rechargepojo> call, final Response<Rechargepojo> response) {
                progressDialog.dismiss();
                Log.d("!!", "onResponse: "+response.toString());
                Log.d("!!", "onResponse: "+response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("otp", response.body().getOtp());
                        Log.d("getotp", response.body().getOtp() + "_");
                        editor.commit();
                        editor.apply();
                        Intent intent = new Intent(getContext(), Verification1.class);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getStatus()==0) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Rechargepojo> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });

    }
    public void rechargepostpaid(){
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
        String operator=pref.getString("operatorpostpaidcode","0");
        String circlecode=pref.getString("circle","0");
        String mobile=mobilenumber.getText().toString();        //Defining retrofit api service
        String amount=Amount.getText().toString();        //Defining retrofit api service
        String Acnumer=accnumer.getText().toString();        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Log.d("!!", "onResponse: "+operator.toString());
        Call<Rechargepostpojo> call = service.rechargepost(userid,"1",mobile,"2",operator,circlecode,amount,Acnumer);
        call.enqueue(new Callback<Rechargepostpojo>() {
            @Override
            public void onResponse(Call<Rechargepostpojo> call, final Response<Rechargepostpojo> response) {
                progressDialog.dismiss();
                Log.d("!!", "onResponse: "+response.toString());
                Log.d("!!", "onResponse: "+response.body().toString());
                Log.d("!!status", "onResponse: "+response.body().getStatus());
                Log.d("!!", "onResponse: "+response.body().getMessage().toString());
                SharedPreferences preferences=getActivity().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("otp",response.body().getOtp());
                Log.d("getotp", response.body().getOtp()+"_");
                editor.commit();
                editor.apply();
                Intent intent=new Intent(getContext(), Verification1.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Enter OTP", Toast.LENGTH_SHORT).show();


//                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Rechargepostpojo> call, Throwable t) {

            }


        });

    }

}