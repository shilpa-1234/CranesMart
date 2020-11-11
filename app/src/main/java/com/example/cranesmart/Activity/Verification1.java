package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.operatorlist.otprecharge.Otpmobilerecharge;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Verification1 extends AppCompatActivity {
    PinView firstPinView;
    TextView mobile;
    Button button;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication1);
        firstPinView=findViewById(R.id.firstPinView);
        mobile=findViewById(R.id.mobile);
        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String mobil=pref.getString(Sharedprefence.Mobile,"0");
        mobile.setText(mobil);
        button=findViewById(R.id.button);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Verification1.this,RechargeActivity.class);
                startActivity(i);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstPinView.getText().toString().isEmpty())
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
//    Verification Api
//    It used for Recharge
    private void verify() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String userid=pref.getString(Sharedprefence.UserID,"0");
        String otp=pref.getString("otp","0");
        String firstPinVie=firstPinView.getText().toString();
        Call<Otpmobilerecharge> call = service.verifyotp(
                userid,firstPinVie

        );
        call.enqueue(new Callback<Otpmobilerecharge>() {
            private Activity layoutView;

            @Override
            public void onResponse(Call<Otpmobilerecharge> call, Response<Otpmobilerecharge> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Verification1.this);
                        LayoutInflater inflater = (Verification1.this).getLayoutInflater();

                        builder.setCancelable(false);

                        View dialogView= inflater.inflate(R.layout.dialogbox, null);
                        builder.setView(dialogView);

                        Button btnDialog1 = (Button)dialogView.findViewById(R.id.btnDialog1);
                        TextView message = dialogView.findViewById(R.id.message);
                        message.setText(response.body().getMessage());
                        btnDialog1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Verification1.this, DashboardActivity.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.cb_rectangle_round);
                        alertDialog.show();                    }

                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Verification1.this);
                        LayoutInflater inflater = (Verification1.this).getLayoutInflater();
                        builder.setCancelable(false);

                        View dialogView= inflater.inflate(R.layout.failedbox, null);
                        builder.setView(dialogView);

                        Button button = (Button)dialogView.findViewById(R.id.btnDialog);
                        TextView textView2 = dialogView.findViewById(R.id.textView2);
                        textView2.setText(response.body().getMessage());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Verification1.this, Verification1.class);
                                startActivity(intent);
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                }
                else {
                    Toast.makeText(Verification1.this, "Bad response ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Otpmobilerecharge> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Verification1.this, RechargeActivity.class);
        startActivity(intent);
        finish();
    }
}

