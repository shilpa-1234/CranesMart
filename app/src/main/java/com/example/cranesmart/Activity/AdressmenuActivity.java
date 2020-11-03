package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.address.Addaddress;
import com.example.cranesmart.pojo.address.Addaddresslist;
import com.example.cranesmart.pojo.address.Editaddresspojo;
import com.example.cranesmart.pojo.countrystatelist.Countryadapterpojo;
import com.example.cranesmart.pojo.countrystatelist.Countrypojo;
import com.example.cranesmart.pojo.countrystatelist.Stateadapterlistpojo;
import com.example.cranesmart.pojo.countrystatelist.Statelist;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdressmenuActivity extends AppCompatActivity {
    EditText fullname,phone,address,city,postalcode;
    TextView sipner,state;
    ImageView menu;
    boolean[] checkedSites = new boolean[]{false,false,false,false,false};
    ArrayList<Countryadapterpojo>sites;
    ArrayList<Stateadapterlistpojo>stat;
    int i;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapte;
    Button buttoncontinue;
    String Country,State,country;
    int postion;
    TextInputLayout prov,prov1,prov2,prov3,prov4,prov5,prov6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adressmenu);
        fullname = findViewById(R.id.fullname);
        prov = findViewById(R.id.prov);
        prov1 = findViewById(R.id.prov1);
        prov2 = findViewById(R.id.prov2);
        prov3 = findViewById(R.id.prov3);
        prov4 = findViewById(R.id.prov4);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.adrress);
        menu = findViewById(R.id.menu);
        sites = new ArrayList<Countryadapterpojo>();
        stat = new ArrayList<Stateadapterlistpojo>();
        arrayAdapter = new ArrayAdapter<String>(AdressmenuActivity.this, R.layout.selectalert);
        arrayAdapte = new ArrayAdapter<String>(AdressmenuActivity.this, R.layout.selectalert);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        postalcode = findViewById(R.id.postalcode);
        sipner = findViewById(R.id.sipner);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdressmenuActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiChoiceItems();
            }
        });
        sipner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withMultiChoiceItems();
            }
        });
        buttoncontinue = findViewById(R.id.buttoncontinue);

        buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullname.getText().toString().isEmpty()) {
                    prov.setError("Field Required");
                } else if (phone.getText().toString().length() <= 9) {
                    prov1.setError("Field Required");
                } else if (address.getText().toString().isEmpty()) {
                    prov2.setError("Field Required");
                } else if (city.getText().toString().isEmpty()) {
                    prov3.setError("Field Required");
                } else if (state.getText().toString().isEmpty()) {
                    state.setError("Field Required");
                } else if (postalcode.getText().toString().length() <= 5) {
                    prov4.setError("Field Required");
                } else if (sipner.getText().toString().isEmpty()) {
                    sipner.setError("Field Required");

                } else {
                    state.setError(null);
                    sipner.setError(null);
                    prov.setErrorEnabled(false);
                    prov1.setErrorEnabled(false);
                    prov2.setErrorEnabled(false);
                    prov3.setErrorEnabled(false);
                    prov4.setErrorEnabled(false);
                    Add();
                    Intent i = new Intent(AdressmenuActivity.this, DashboardActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    private void Add() {
        final ProgressDialog progressDialog = ProgressDialog.show(AdressmenuActivity.this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final String name = fullname.getText().toString().trim();
        String number = phone.getText().toString().trim();
        String add = address.getText().toString().trim();
        String district = city.getText().toString().trim();
        String code = postalcode.getText().toString().trim();
        SharedPreferences sh = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value = sh.getString("userID", "0");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Addaddress> call = service.add(
                name, add, "-", district, code, number, State, Country, value
        );

        call.enqueue(new Callback<Addaddress>() {
            @Override
            public void onResponse(Call<Addaddress> call, Response<Addaddress> response) {
                Log.d("Sharedpre", String.valueOf(response.body().getStatus()));
                Log.d("Sharedpre", String.valueOf(response.body().getMessage()));
                progressDialog.dismiss();

                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("name", fullname.getText().toString());
                editor.putString("phone", phone.getText().toString());
                editor.putString("add", address.getText().toString());
                editor.putString("city", city.getText().toString());
                editor.putString("stat", state.getText().toString());
                editor.putString("code", postalcode.getText().toString());
                editor.apply();
                editor.commit();
                Log.d("Sharedprefences", String.valueOf(editor));

            }

            @Override
            public void onFailure(Call<Addaddress> call, Throwable t) {

            }


        });
    }
    private void withMultiChoiceItems() {
            final ProgressDialog progressDialog = ProgressDialog.show(AdressmenuActivity.this, null, null, true);
            progressDialog.setContentView(R.layout.custom_loader);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<Countrypojo> call = service.country();
            call.enqueue(new Callback<Countrypojo>() {
                @Override
                public void onResponse(Call<Countrypojo> call, final Response<Countrypojo> response) {
                    progressDialog.dismiss();
                    for (i = 0; i < response.body().getData().size(); i++) {
                        arrayAdapter.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                        sites.addAll(Arrays.asList(response.body().getData().get(i)));
                    }

                    TextView textView = new TextView(AdressmenuActivity.this);
                    textView.setText("Select Your Country");
                    textView.setPadding(20, 30, 20, 30);
                    textView.setTextSize(20F);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setTextColor(Color.BLACK);
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(AdressmenuActivity.this);
                    builderSingle.setCancelable(false);
                    builderSingle.setIcon(R.drawable.ic_baseline_account_balance_wallet_24);
                    builderSingle.setCustomTitle(textView);


                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            state.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(AdressmenuActivity.this);
                            builderInner.setMessage(arrayAdapter.getItem(which));
                            sipner.setText(arrayAdapter.getItem(which).toString().trim());
                            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("country_id", response.body().getData().get(which).getCountryCode().trim().toString());
                            Country = sites.get(which).getCountryID().toString();

                            country = sites.get(which).getCountryCode().toString();
                            Log.d("@@sipner", arrayAdapter.getItem(which).toString().trim());

                        }
                    });
                    AlertDialog alertDialog = builderSingle.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                    alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    alertDialog.show();


                    state.setVisibility(View.VISIBLE);


                }

                @Override
                public void onFailure(Call<Countrypojo> call, Throwable t) {

                }


            });
        }

    private void MultiChoiceItems() {
        final ProgressDialog progressDialog = ProgressDialog.show(AdressmenuActivity.this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Statelist> call = service.state(country);
        call.enqueue(new Callback<Statelist>() {
            @Override
            public void onResponse(Call<Statelist> call, final Response<Statelist> response) {
                progressDialog.dismiss();
                for (i = 0; i < response.body().getData().size(); i++) {
                    arrayAdapte.addAll(Arrays.asList(response.body().getData().get(i).getName()));
                    stat.addAll(Arrays.asList(response.body().getData().get(i)));
                }
                TextView textView = new TextView(AdressmenuActivity.this);
                textView.setText("Select Your State");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(AdressmenuActivity.this);
                builderSingle.setCancelable(false);
                builderSingle.setIcon(R.drawable.ic_baseline_account_balance_wallet_24);
                builderSingle.setCustomTitle(textView);


                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapte, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(AdressmenuActivity.this);

                        builderInner.setMessage(arrayAdapte.getItem(which));
                        state.setText(arrayAdapte.getItem(which).toString().trim());
                        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("State_id", response.body().getData().get(which).getStateID().trim().toString());
                        State = stat.get(which).getStateID().toString();
                        Log.d("Stateid", State);
                        Log.d("@@sipner", arrayAdapte.getItem(which).toString().trim());

                    }
                });
                AlertDialog alertDialog = builderSingle.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.show();


            }

            @Override
            public void onFailure(Call<Statelist> call, Throwable t) {

            }


        });

    }
}

