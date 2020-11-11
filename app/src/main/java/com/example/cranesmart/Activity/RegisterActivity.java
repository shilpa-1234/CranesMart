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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.registerlogin.register;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout line;
    Button button;
    EditText username1,mail1,password1,mobile,referral;
    TextInputLayout prov,prov0,prov1,prov2,prov3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        line=findViewById(R.id.line);
        button=findViewById(R.id.button);
        username1=findViewById(R.id.username1);
        mail1=findViewById(R.id.mail1);
        password1=findViewById(R.id.password1);
        mobile=findViewById(R.id.mobile);
        referral=findViewById(R.id.referral);
        prov0=findViewById(R.id.prov0);
        prov1=findViewById(R.id.prov1);
        prov2=findViewById(R.id.prov2);
        prov3=findViewById(R.id.prov3);
        prov=findViewById(R.id.prov);




        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(i);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username1.getText().toString().isEmpty()) {
                    prov.setError("Field Required");
                }
                else if (mail1.getText().toString().isEmpty()) {
                    prov0.setError("Field Required");
                    prov.setErrorEnabled(false);
                    prov1.setErrorEnabled(false);
                    prov2.setErrorEnabled(false);
                    prov3.setErrorEnabled(false);
                }
                else if (password1.getText().toString().isEmpty()) {
                    prov1.setError("Field Required");
                    prov0.setErrorEnabled(false);
                    prov.setErrorEnabled(false);
                    prov2.setErrorEnabled(false);
                    prov3.setErrorEnabled(false);
                }
                else if (mobile.getText().toString().isEmpty()) {
                    prov2.setError("Field Required");
                    prov0.setErrorEnabled(false);
                    prov1.setErrorEnabled(false);
                    prov.setErrorEnabled(false);
                    prov3.setErrorEnabled(false);
                }
                else if (referral.getText().toString().isEmpty()) {
                    prov3.setError("Field Required");
                    prov0.setErrorEnabled(false);
                    prov1.setErrorEnabled(false);
                    prov2.setErrorEnabled(false);
                    prov.setErrorEnabled(false);
                }
                else {
                   prov.setErrorEnabled(false);
                   prov0.setErrorEnabled(false);
                   prov1.setErrorEnabled(false);
                   prov2.setErrorEnabled(false);
                   prov3.setErrorEnabled(false);
                    userSignUp();

                }

            }

        });
    }
//    Regester Api
    private void userSignUp() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String user1 = username1.getText().toString().trim();
        String pass1 = password1.getText().toString().trim();
        String email1 = mail1.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String referral1=referral.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<register> call = service.createUser(
                user1,
                email1,
                pass1,
                mobile1,
                referral1
        );

        //calling the api
        call.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("name",username1.getText().toString());
                editor.putString("mail",mail1.getText().toString());
                editor.putString("mobile",mobile.getText().toString());
                editor.putString("password",password1.getText().toString());
                editor.commit();
                editor.apply();
                if(Integer.parseInt(response.body().getStatus().toString())==1)
                {
                    Intent intent=new Intent(RegisterActivity.this, VerificationActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getStatus()==0) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<register> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
