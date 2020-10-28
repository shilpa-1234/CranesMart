package com.example.cranesmart.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Activity.DashboardActivity;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class EditprofileFragment extends Fragment {
    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/encoded_image";
    Uri myPicture = null;
    ImageView profileimg, viewImage;
    RelativeLayout relative1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        profileimg = view.findViewById(R.id.profileimg);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        userdetail();
        relative1=view.findViewById(R.id.relative1);
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction fragmentTransactionProfile =getActivity().getSupportFragmentManager().beginTransaction();
                Fragment frag1 = new MyprofileFragment();
                fragment_transaction1(fragmentTransactionProfile, frag1, "profile");
            }
        });
        return view;
    }
    private void userdetail() {
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
        SharedPreferences sh=getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;



//        base64.setText(encodedImage);
        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
                progressDialog.dismiss();
                Glide.with(getContext()).load(APIUrl.IMG_URL+response.body().getUserDetail().getProfilePhoto()).placeholder(R.drawable.circleimg1).into(profileimg);
                Log.d("@@userimageedit", "onResponse: "+response.body().getUserDetail().getProfilePhoto());
            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                progressDialog.dismiss();
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