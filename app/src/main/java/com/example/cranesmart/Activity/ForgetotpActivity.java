package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.fgotp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetotpActivity extends AppCompatActivity {
    PinView firstPinView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetotp);
        firstPinView=findViewById(R.id.firstPinView);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp();
            }
        });
    }
    private void otp() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //getting the user values

        String user1 = firstPinView.getText().toString().trim();




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
        Call<fgotp> call = service.otp(
                user1

        );

        //calling the api
        call.enqueue(new Callback<fgotp>() {
            @Override
            public void onResponse(Call<fgotp> call, Response<fgotp> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if(response.body().getStatus().equals("1")){
                    Toast.makeText(ForgetotpActivity.this, "Reset your password", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgetotpActivity.this, ResetpasswordActivity.class);
                    startActivity(intent);
                }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<fgotp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

