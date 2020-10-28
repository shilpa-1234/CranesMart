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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.updatepassword.reset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetpasswordActivity extends AppCompatActivity {
Button button;
EditText pass,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        button=findViewById(R.id.button);
        pass=findViewById(R.id.pass);
        pass2=findViewById(R.id.pass2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().isEmpty()) {
                    pass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }
                else if (pass2.getText().toString().isEmpty()) {
                    pass2.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }
                else if(!(pass2.getText().toString().trim()).equals(pass.getText().toString().trim())){
                    pass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    pass2.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(ResetpasswordActivity.this, "your password is not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    pass.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                    pass2.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                    resetp();
                }
            }
        });
    }
    private void resetp() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String pass1 = pass.getText().toString().trim();
        String email1 = pass2.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        SharedPreferences preferences=getSharedPreferences("Mypref",MODE_PRIVATE);
        String value=preferences.getString("userid","0");
        Call<reset> call = service.reset(
                value,
               pass1,
                email1
        );

        //calling the api
        call.enqueue(new Callback<reset>() {
            @Override
            public void onResponse(Call<reset> call, Response<reset> response) {
                progressDialog.dismiss();
                if(Integer.parseInt(response.body().getStatus().toString())==1)
                {
                    Intent intent=new Intent(ResetpasswordActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<reset> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

