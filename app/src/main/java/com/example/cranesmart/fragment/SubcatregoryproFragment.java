package com.example.cranesmart.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Adapter.SubcategoryAdapter;
import com.example.cranesmart.Adapter.SubcategoryproAdapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.subcategory.Datum;
import com.example.cranesmart.pojo.subcategory.Subcategoryretr;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class SubcatregoryproFragment extends Fragment {


    Integer i;
    ImageView back;
    RecyclerView recyclerviewsubcategory;
    String ss;
    TextView textview1;
//        Bundle arguments = getArguments();
//        String desired_string = arguments.getString("catID");

    //    int catID = 1;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Datum> listdata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_subcatregorypro, container, false);
        recyclerviewsubcategory=view.findViewById(R.id.recyclerviewsubcategory);
        textview1=view.findViewById(R.id.textview1);
        SharedPreferences preferences= getContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String text=preferences.getString("textview1","0");
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        isInternetOn();
        editor.apply();
        textview1.setText(text);
        listdata=new ArrayList<Datum>();
        Bundle arguments = getArguments();
        ss = arguments.getString("catID");
        Log.d("####", ss);

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

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);

        //defining the call
        Call<Subcategoryretr> call = service.sub(ss);
        call.enqueue(new Callback<Subcategoryretr>() {
            @Override
            public void onResponse(Call<Subcategoryretr> call, Response<Subcategoryretr> response) {
                //hiding progress dialog

                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Log.d("%%%%%", "onResponse: SUCCESFULL ");
                    Log.d("%%%%%",response.body().getMessage().toString().trim());
                    Log.d("%%%%%",response.body().getData().toString().trim());
                    Log.d("%%%%%", response.body().getStatus().toString().trim());
                    recyclerviewsubcategory.setHasFixedSize(true);
                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerviewsubcategory.setLayoutManager(layoutManager);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
                    recyclerviewsubcategory.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    SubcategoryproAdapter mAdapter = new SubcategoryproAdapter(listdata);
                    recyclerviewsubcategory.setAdapter(mAdapter);
                    for (i=0;i<response.body().getData().size();i++) {
                        listdata.addAll(Arrays.asList(response.body().getData().get(i)));

                    }

                    mAdapter.notifyDataSetChanged();}

//                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Subcategoryretr> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public final boolean isInternetOn() {
        AlertDialog.Builder builder;

        final ConnectivityManager connec =
                (ConnectivityManager)getActivity().getSystemService(CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {

            builder = new AlertDialog.Builder(getContext());
            builder.setMessage(Html.fromHtml("<font color='#000'>Please Check the internet connection</font>") ).setTitle("<font color='#000'>No Internet Connection</font>");

            builder.setMessage(Html.fromHtml("<font color='#000'>Please Check the internet connection</font>"))
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                            startActivity(intent);
                        }
//                            Toast.makeText(SplashScreenActivity.this,"you choose no action for alertbox",
//                                    Toast.LENGTH_SHORT).show();

                    });
            AlertDialog alert = builder.create();
            alert.setTitle(Html.fromHtml("<font color='#000'>No Internet Connection</font>"));
            alert.show();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        }

        Toast.makeText(getContext(), " Not Connected ", Toast.LENGTH_LONG).show();
        return false;
    }


}