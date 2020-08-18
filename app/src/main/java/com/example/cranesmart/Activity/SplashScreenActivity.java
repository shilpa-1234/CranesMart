package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cranesmart.R;
import com.sandeep.AndroidDialog.AndroidDialog;

public class SplashScreenActivity extends AppCompatActivity {
  //  private static int SPLASH_SCREEN_TIME_OUT = 2000;
    Button angry_btn,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        angry_btn=findViewById(R.id.angry_btn);
        angry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SplashScreenActivity.this,
                        RegisterActivity.class);
                startActivity(i);

            }
        });
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SplashScreenActivity.this,
                        MainActivity.class);
                startActivity(i);

            }
        });
    }

}




