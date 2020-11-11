package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cranesmart.Adapter.addresslistmenuadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.address.Addaddress;
import com.example.cranesmart.pojo.address.Addaddresslist;
import com.example.cranesmart.pojo.address.Addresslistdatum;
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

public class AdressmenuActivity extends AppCompatActivity implements Adapterinterface {
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
    RelativeLayout relative1;
    RecyclerView recyleaddress;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Addresslistdatum> product;
    TextInputLayout prov,prov1,prov2,prov3,prov4,prov5,prov6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adressmenu);
        fullname = findViewById(R.id.fullname);
        prov = findViewById(R.id.prov);
        product= new ArrayList<>();
        recyleaddress=findViewById(R.id.recyleaddress);
        relative1=findViewById(R.id.relative1);
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
        Intent intent = getIntent();
        final int op = intent.getIntExtra("status", 0);
        Log.d("!!pos", "onCreate: " + intent.getIntExtra("postion", 0));
//     Update Address
        if (intent != null) {
            postion = intent.getIntExtra("postion", 123456789);
            if (!(postion == 123456789)) {

                Log.d("!!pos1", "onCreate: " + postion);
                Sizee();
            } else {

            }
        }
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdressmenuActivity.this, AddresslistmenuActivity.class);
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
                    if (!(postion == 123456789)) {

                        Log.d("!!pos1", "onCreate: " + postion);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdressmenuActivity.this);
                        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Your Address Updated Sucessfully</font>"));
                        alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color='#000'>OK</font>"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        edit();
                                        Intent i = new Intent(AdressmenuActivity.this, AddresslistmenuActivity.class);
                                        startActivity(i);
                                        Sizee();
                                        Log.d("Addtocart", "Cart");
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        alertDialog.show();

                    } else if (op == 1) {

                        Log.d("!!pos1", "onCreate: " + postion);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdressmenuActivity.this);
                        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Your Address updated  sucessfully</font>"));
                        alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color='#000'>OK</font>"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        edit();
                                        Intent i = new Intent(AdressmenuActivity.this, AddresslistmenuActivity.class);
                                        startActivity(i);
                                        Sizee();
                                        Log.d("Addtocart", "Cart");
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawableResource(R.color.white);
                        alertDialog.show();
                    }
                    else {
                        Add();
                        Intent i= new Intent(AdressmenuActivity.this,AddresslistmenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }
//    save  Address
    private void Add() {
        final ProgressDialog progressDialog = ProgressDialog.show(AdressmenuActivity.this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final String name = fullname.getText().toString().trim();
        String number = phone.getText().toString().trim();
        String add = address.getText().toString().trim();
        String district = city.getText().toString().trim();
        String code = postalcode.getText().toString().trim();
        SharedPreferences sh = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = sh.getString(Sharedprefence.UserID, "0");
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

                SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
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
//    Country list
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
                            if(!(sipner.getText().toString().isEmpty())){
                                state.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                            else {
                                state.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(AdressmenuActivity.this);
                            builderInner.setMessage(arrayAdapter.getItem(which));
                            sipner.setText(arrayAdapter.getItem(which).toString().trim());
                            SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("country_id", response.body().getData().get(which).getCountryCode().trim().toString());
                            editor.commit();
                            editor.apply();
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
//StateList
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
                        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("State_id", response.body().getData().get(which).getStateID().trim().toString());
                        editor.commit();
                                editor.apply();
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
//     Update Address
    private void edit() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref = this.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = pref.getString(Sharedprefence.AddId, "0");
        String value1 = pref.getString(Sharedprefence.UserID, "0");
        String value2 = pref.getString("country_id", "0");
        String value3 = pref.getString("State_id", "0");
        String name = fullname.getText().toString();
        String phoe = phone.getText().toString();
        String addres = address.getText().toString();
        String city1 = city.getText().toString();
        String postalcod = postalcode.getText().toString();
        String State = state.getText().toString();
        String country = sipner.getText().toString();
        Call<Editaddresspojo> call = service.edit(value1, value, name, phoe, addres, city1, value2, value3, postalcod);
        call.enqueue(new Callback<Editaddresspojo>() {
            @Override
            public void onResponse(Call<Editaddresspojo> call, Response<Editaddresspojo> response) {
                progressDialog.dismiss();
                String TAG = "@@addreses";
                state.setText(state.getText().toString());
                sipner.setText(sipner.getText().toString());

            }

            @Override
            public void onFailure(Call<Editaddresspojo> call, Throwable t) {

            }


        });

    }
// Adapter
    private void Addresslist(){
        layoutManager = new LinearLayoutManager(AdressmenuActivity.this);
        recyleaddress.setLayoutManager(layoutManager);
        final addresslistmenuadapter adapter = new addresslistmenuadapter(product,this);
        recyleaddress.setHasFixedSize(true);
        recyleaddress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
//    Address list
    private void Sizee() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Addaddresslist> call = service.addresslist(value);
        call.enqueue(new Callback<Addaddresslist>() {
            @Override
            public void onResponse(Call<Addaddresslist> call, Response<Addaddresslist> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getData()!=null) {
                        for (i = 0; i < response.body().getData().size(); i++) {
                            SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(Sharedprefence.AddId, response.body().getData().get(i).getAddID().trim());
                            editor.putString("CountryId11", response.body().getData().get(i).getCountryId().trim());
                            editor.putString("StateId11", response.body().getData().get(i).getStateId().trim());
                            editor.commit();
                            editor.apply();
                            product.addAll(Arrays.asList(response.body().getData().get(i)));
                        }
                        Addresslist();
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Addaddresslist> call, Throwable t) {

            }


        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(AdressmenuActivity.this,AddresslistmenuActivity.class);
        startActivity(i);
        finish();
    }
//interFace Method
    @Override
    public void onMethodCallback() {

    }
}

