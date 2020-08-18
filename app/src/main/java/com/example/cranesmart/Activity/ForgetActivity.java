package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.forget;

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
forget();
            }
        });
    }private void forget() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //getting the user values

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
                    Intent intent=new Intent(ForgetActivity.this, ForgetotpActivity.class);
                    startActivity(intent);
                }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<forget> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}