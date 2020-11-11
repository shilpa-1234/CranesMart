package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.verfiyforget.forget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetActivity extends AppCompatActivity {
EditText forget1;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        forget1=findViewById(R.id.forget1);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forget1.getText().toString().isEmpty()){
                 forget1.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                }
                else{
                    forget1.getBackground().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                    forget();
                    Intent intent=new Intent(ForgetActivity.this, ForgetotpActivity.class);
                    startActivity(intent);}
            }
        });
    }
//    Forget api Call here..
    private void forget() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String user1 = forget1.getText().toString().trim();




        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);

        //defining the call
        Call<forget> call = service.pass(
                user1

        );

        //calling the api
        call.enqueue(new Callback<forget>() {
            @Override
            public void onResponse(Call<forget> call, Response<forget> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(Integer.valueOf(response.body().getStatus().toString())==1)
                {
                    Toast.makeText(ForgetActivity.this, "Enter your otp", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<forget> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}