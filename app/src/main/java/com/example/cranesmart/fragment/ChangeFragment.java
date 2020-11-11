package com.example.cranesmart.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.changepassword.Changepassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeFragment extends Fragment {
    EditText oldpass,pass,pass2;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change, container, false);
        pass2=view.findViewById(R.id.pass2);
        oldpass=view.findViewById(R.id.oldpass);
        pass=view.findViewById(R.id.pass);
        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldpass.getText().toString().isEmpty()){
                    oldpass.setError("Enter Your correct password");
                }
                else if(pass.getText().toString().isEmpty()){
                    pass.setError("Enter Your  password");
                }
                else if(pass2.getText().toString().isEmpty()){
                    pass2.setError(" Your password is Not matched");
                }
                else if(!(pass2.getText().toString().trim()).equals(pass.getText().toString().trim())){
                    pass2.setError(" Your password is Not matched");
                }
                else{
                 resetp();
                }
            }
        });
        return view;
    }
    private void resetp() {

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String pass1 = oldpass.getText().toString().trim();
        String passn = pass.getText().toString().trim();
        String email1 = pass2.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        SharedPreferences sh=getActivity().getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Changepassword>call = service.change(value, pass1,passn, email1);
        call.enqueue(new Callback<Changepassword>() {
            @Override
            public void onResponse(Call<Changepassword> call, Response<Changepassword> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1)
                {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Changepassword> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}