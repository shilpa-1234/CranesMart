package com.example.cranesmart.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Adapter.ImageAdapter;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.dashboard.re;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {
    Context context;
    ViewPager mViewPager;
    int page_position = 0;
    private TextView[] dots;
    private static int currentPage = 0;
    private static int NUM_PAGES = 5;
    ArrayList<String> itemList;
    private LinearLayout ll_dots;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mViewPager = view.findViewById(R.id.viewPage);
        itemList = new ArrayList<String>();
        Search();
       SharedPreferences preferences=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("statusdash","0");
                editor.commit();
                editor.apply();
       return view;

    }
    private void Search() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //getting the user values


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);

        //defining the call
        Call<re> call = service.listRepos();
        call.enqueue(new Callback<re>() {
            @Override
            public void onResponse(Call<re> call, Response<re> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage1());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage2());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage3());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage4());
                itemList.add(response.body().getFiveBanner().get(0).getBannerImage5());
                if (Integer.valueOf(response.body().getStatus().toString().trim()) == 1) {
                    ImageAdapter adapterView = new ImageAdapter(context, itemList);
                    mViewPager.setAdapter(adapterView);
                }
            }

            @Override
            public void onFailure(Call<re> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}

