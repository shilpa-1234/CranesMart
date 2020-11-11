package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.registerlogin.ex;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
LinearLayout line;
Button button;
EditText username,password;
TextView fgtpass;
TextInputLayout prov,prov2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        button=findViewById(R.id.button);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        fgtpass=findViewById(R.id.fgtpass);
        line=findViewById(R.id.line);
        prov=findViewById(R.id.prov);
        prov2=findViewById(R.id.prov2);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(i);

            }
        });
        fgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.onEditorAction(EditorInfo.IME_ACTION_DONE);
                password.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (username.getText().toString().equals("")) {
                    prov2.setError("Field is required");
                } else if (password.getText().toString().equals("")) {
                    prov.setError("Field is required");
                }
                else {
                    prov.setErrorEnabled(false);
                    prov2.setErrorEnabled(false);
                    userSignUp();

                }
            }
        });

    }
//    Login Auth Api
    private void userSignUp() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ex> call = service.userLogin(
                email,
                pass
        );
        call.enqueue(new Callback<ex>() {
            @Override
            public void onResponse(Call<ex> call, Response<ex> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()==1) {
                        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                            editor.putString(Sharedprefence.UserID, response.body().getUserDetail().getUserID());
                            editor.putString("name", response.body().getUserDetail().getName());
                            editor.putString("email", response.body().getUserDetail().getEmail());
                            editor.putString("mobile", response.body().getUserDetail().getMobile());
                            editor.putString("photo", APIUrl.IMG_URL+response.body().getUserDetail().getProfilePhoto());
                            editor.putString(Sharedprefence.logStatus,"1");
                            editor.apply();
                            editor.commit();
                            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(i);
                        }

                        else{
                        Toast.makeText(LoginActivity.this, "Please Enter Valid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Bad response call on this number 7239962886", Toast.LENGTH_SHORT).show();

                }
            }

      @Override
            public void onFailure(Call<ex> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
          Toast.makeText(LoginActivity.this, "Bad response call on this number 7239962886", Toast.LENGTH_SHORT).show();
            }
        });
    }
// on BAck  Pressed method
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginActivity.this.finishAffinity();
    }
}