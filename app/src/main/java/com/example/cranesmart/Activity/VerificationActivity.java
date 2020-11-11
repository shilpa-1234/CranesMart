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
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.verfiyforget.Verfiy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificationActivity extends AppCompatActivity {
    PinView firstPinView;
    TextView mobile;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        firstPinView=findViewById(R.id.firstPinView);
        mobile=findViewById(R.id.mobile);
        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String mobil=pref.getString("mobile","0");
        mobile.setText(mobil);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstPinView.getText().toString().isEmpty())
                {
                    firstPinView.setError("Enter Otp");
                }
                else   if(firstPinView.getText().toString().isEmpty())
                {
                    firstPinView.setError("Enter Otp");
                }
                else if(firstPinView.getText().toString().isEmpty())
                {
                    firstPinView.setError("Enter Otp");
                }
                else if(firstPinView.getText().toString().isEmpty())
                {
                    firstPinView.setError("Enter Otp");
                }
                else {
                    firstPinView.setError(null);
                    verify();
                }
            }
        });
    }
//    Verfication Api
//    it's used for Regestraion  Time
    private void verify() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String user1 = firstPinView.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<Verfiy> call = service.verify(
                user1

        );
        call.enqueue(new Callback<Verfiy>() {
            @Override
            public void onResponse(Call<Verfiy> call, Response<Verfiy> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1){
                    Intent intent= new Intent(VerificationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(VerificationActivity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();

                }
              Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Verfiy> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

