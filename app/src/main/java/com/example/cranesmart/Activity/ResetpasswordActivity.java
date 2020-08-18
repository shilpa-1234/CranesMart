package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.reset;

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
                    pass.setError("Enter your password");
                }
                else if (pass2.getText().toString().isEmpty()) {
                    pass2.setError("Enter your confirm password");
                }
                else if(pass!=pass2){
                    Toast.makeText(ResetpasswordActivity.this, "your password is not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    resetp();
                }
            }
        });
    }
    private void resetp() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reset password");
        progressDialog.show();

        //getting the user values

        String pass1 = pass.getText().toString().trim();
        String email1 = pass2.getText().toString().trim();



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
        Call<reset> call = service.reset(
                "",
               pass1,
                email1
        );

        //calling the api
        call.enqueue(new Callback<reset>() {
            @Override
            public void onResponse(Call<reset> call, Response<reset> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                Log.d("@@",response.body().getMessage().toString());
                int d = Log.d("@@", response.body().getStatus().toString());
                if(Integer.valueOf(response.body().getStatus().toString())==1)
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ResetpasswordActivity.this);
                    String userID = preferences.getString("userID", "_");
                    String name = preferences.getString("name", "_");
                    String email = preferences.getString("email", "_");
                    String mobile = preferences.getString("mobile", "_");

                    Toast.makeText(ResetpasswordActivity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ResetpasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<reset> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

