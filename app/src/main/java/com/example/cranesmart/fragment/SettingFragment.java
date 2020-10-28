package com.example.cranesmart.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cranesmart.Activity.DashboardActivity;
import com.example.cranesmart.Activity.MainActivity;
import com.example.cranesmart.R;


public class SettingFragment extends Fragment {
 SharedPreferences.Editor log;
    TextView Logout;
    RelativeLayout relative1,relative2,relative3,relative4,relative5,relative6,relative7;
    String val="0";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        relative1=view.findViewById(R.id.relative1);
        relative2=view.findViewById(R.id.relative2);
        relative3=view.findViewById(R.id.relative3);
        relative4=view.findViewById(R.id.relative4);
        relative5=view.findViewById(R.id.relative5);
        relative6=view.findViewById(R.id.relative6);
        relative7=view.findViewById(R.id.relative7);
        relative1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

            }
        });
        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

            }
        });
        relative3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();


            }
        });
        relative4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

            }
        });
        relative5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();


            }
        }); relative6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Log.d("Addtocart","Cart");
                            }
                        });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();


            }
        }); relative7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

             /*   SharedPreferences sh= getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                String value=sh.getString("userID","0");
                sh.edit().remove(value);
                sh.edit().commit();
                sh.edit().clear();*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Do you want to Log out this application ?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("log_status","0");
                                editor.apply();
                                editor.commit();

                                Intent i=  new Intent(getContext(),MainActivity.class);
                                startActivity(i);


                                //                            Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                //                                    Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                //                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                //                                    Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //Setting the title manually
                alert.show();


            }
        });
        return view;
    }
}