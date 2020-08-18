package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout line;
    Button button;
    EditText username1,mail1,password1,mobile,referral;

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





        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,
                        MainActivity.class);
                startActivity(i);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username1.getText().toString().isEmpty()) {
                    username1.setError("name");
                }
                else if (mail1.getText().toString().isEmpty()) {
                    mail1.setError("valid mail please");
                }
                else if (password1.getText().toString().isEmpty()) {
                    password1.setError("write password");
                }
                else if (mobile.getText().toString().isEmpty()) {
                    mobile.setError("Enter mobile number");
                }
                else if (referral.getText().toString().isEmpty()) {
                    referral.setError("Enter referral id");
                }
                else {
                    userSignUp();

                }

            }

        });
    }
    private void userSignUp() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        //getting the user values

        String user1 = username1.getText().toString().trim();
        String pass1 = password1.getText().toString().trim();
        String email1 = mail1.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String referral1=referral.getText().toString().trim();



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
                Log.d("@@",response.body().getMessage().toString());
                int d = Log.d("@@", response.body().getStatus().toString());
                if(Integer.valueOf(response.body().getStatus().toString())==1)
                {

                    Toast.makeText(RegisterActivity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, VerificationActivity.class);
                    startActivity(intent);
                }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<register> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
