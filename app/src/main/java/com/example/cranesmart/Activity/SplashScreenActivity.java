package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;

public class SplashScreenActivity extends AppCompatActivity {
    //  private static int SPLASH_SCREEN_TIME_OUT = 2000;
    Button angry_btn, login;
    TextView Create;
    Boolean isFirstTime;
    String val = "0";
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        angry_btn = findViewById(R.id.angry_btn);
        Create = findViewById(R.id.Create);
        builder = new AlertDialog.Builder(this);
        login = findViewById(R.id.login);
        isInternetOn();
        SharedPreferences sh = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value1 = sh.getString(Sharedprefence.logStatus, "0");
        if (!value1.equals("0")) {

            Intent i = new Intent(SplashScreenActivity.this,DashboardActivity.class);
            startActivity(i);
        } else {

            Create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isInternetOn()) {

                        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
            angry_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isInternetOn()) {
                        Intent i = new Intent(SplashScreenActivity.this,
                                RegisterActivity.class);
                        startActivity(i);
                    }
                }
            });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isInternetOn()) {
                        Intent i = new Intent(SplashScreenActivity.this,
                                LoginActivity.class);
                        startActivity(i);
                    }

                }
            });



        }

    }

//    Check For Internet Connection

    public final boolean isInternetOn() {
        final ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {

            builder = new AlertDialog.Builder(SplashScreenActivity.this);
            builder.setMessage("Please Check the internet connection") .setTitle("No Internet Connection");

            builder.setMessage("Please Check the internet connection")
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
            alert.setTitle("No Internet Connection");
            alert.show();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        }

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }


}




