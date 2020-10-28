package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.verfiyforget.fgotp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetotpActivity extends AppCompatActivity {
    PinView firstPinView;
    TextView mobile;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetotp);
        firstPinView=findViewById(R.id.firstPinView);
        button=findViewById(R.id.button);
        mobile=findViewById(R.id.mobile);
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
        String mobil=pref.getString("mobile","0");
        mobile.setText(mobil);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstPinView.getText().toString().isEmpty()) {
                    firstPinView.setError("Enter Otp");
                }
                if(firstPinView.getText().toString().isEmpty()) {
                    firstPinView.setError("Enter Otp");
                }
                if(firstPinView.getText().toString().isEmpty()) {
                    firstPinView.setError("Enter Otp");
                }
                if(firstPinView.getText().toString().isEmpty()) {
                    firstPinView.setError("Enter Otp");
                }
                else {
                    firstPinView.setError(null);
                    otp();
                }
            }
        });
    }
    private void otp() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String user1 = firstPinView.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);

        Call<fgotp> call = service.otp(
                user1

        );

        call.enqueue(new Callback<fgotp>() {
            @Override
            public void onResponse(Call<fgotp> call, Response<fgotp> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1){

                    SharedPreferences preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userid",response.body().getUserID());
                    Log.d("useridforgrt", "onResponse: "+response.body().getUserID());
                    editor.apply();
                    editor.commit();
                    Intent intent=new Intent(ForgetotpActivity.this, ResetpasswordActivity.class);
                    startActivity(intent);
                    Toast.makeText(ForgetotpActivity.this, "Reset your password", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<fgotp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

