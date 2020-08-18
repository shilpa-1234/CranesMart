package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.APIService;
import com.example.cranesmart.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.ex;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
LinearLayout line;
Button button;
EditText username,password;
TextView fgtpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        button=findViewById(R.id.button);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        fgtpass=findViewById(R.id.fgtpass);
        line=findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(i);

            }
        });
        fgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.onEditorAction(EditorInfo.IME_ACTION_DONE);
                password.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Your password is not valid", Toast.LENGTH_SHORT).show();
                } else {
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

        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();


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
        Call<ex> call = service.userLogin(
                email,
                pass
        );

        //calling the api
        call.enqueue(new Callback<ex>() {
            @Override
            public void onResponse(Call<ex> call, Response<ex> response) {
                //hiding progress dialog
                progressDialog.dismiss();
             if(response.body().getStatus().equals("1")){
                 Log.e("@@",response.body().getUserDetail().getEmail());
                 Log.e("ss",response.body().getUserDetail().getMobile());
                 SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                 SharedPreferences.Editor editor = pref.edit();
                 editor.putString("userID",response.body().getUserDetail().getUserID());
                 editor.putString("name",response.body().getUserDetail().getName());

         editor.putString("email",response.body().getUserDetail().getEmail());
                 editor.putString("mobile",response.body().getUserDetail().getMobile());
                 Intent i=new Intent(MainActivity.this, DashboardActivity.class);
                 startActivity(i);
             }
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            }
      @Override
            public void onFailure(Call<ex> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}