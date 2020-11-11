package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Adapter.CartlistAdapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.cart.Addcartlist;
import com.example.cranesmart.pojo.cart.CartList;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class CartActivity extends AppCompatActivity implements Adapterinterface {
    TextView addtocart,total1,price,price1,bill,totall;
    RecyclerView Cartrecyclerview;
    ImageView menu,noproduct;
    ArrayList<CartList> listdata;
    RecyclerView.LayoutManager layoutManager;
    Button button;
    RelativeLayout re;
    Integer total;
    int  postion,pos;
       Double  calculate;
    CartlistAdapter mAdapter;
    int i;
    int in=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addtocart=findViewById(R.id.addtocart);
        menu=findViewById(R.id.menu);
        total1=findViewById(R.id.total1);
        price1=findViewById(R.id.price1);
        totall=findViewById(R.id.totall);
        noproduct=findViewById(R.id.noproduct);
        Intent intent = getIntent();
        if(intent!=null) {
            postion = intent.getIntExtra("pos", 0);
            pos = intent.getIntExtra("pos1", 0);
        }
        re=findViewById(R.id.re);
        bill=findViewById(R.id.bill);
        price=findViewById(R.id.price);
        listdata= new ArrayList<CartList>();
        Cartrecyclerview=findViewById(R.id.cartrecyclerview);
        button=findViewById(R.id.button);
        mAdapter = new CartlistAdapter(listdata,this);
        cartlist();


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listdata!= null){
                    Intent intent = new Intent(CartActivity.this, AddresslistActivity.class);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(CartActivity.this, "Add product in cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
// cArtlist Api
    private  void cartlist(){
        total=0;
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        listdata.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        WifiManager wifiManager = (WifiManager) CartActivity.this.getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Addcartlist> call = service.cartlist(value,ipAddress);
        call.enqueue(new Callback<Addcartlist>() {
            @Override
            public void onResponse(Call<Addcartlist> call, Response<Addcartlist> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {

                    if (response.body().getCartList()!=null) {
                        re.setVisibility(View.VISIBLE);
                        listdata.clear();
//  Calculation of PPurchasing Ruppes and Adding Cart List
                        for (i = 0; i <response.body().getCartList().size(); i++) {
                            listdata.addAll(Arrays.asList(response.body().getCartList().get(i)));
                            if(response.body().getCartList().get(i).getIsSpecialPrice()==0) {
                                total = total+  parseInt(response.body().getCartList().get(i).getQty()) * parseInt(String.valueOf(response.body().getCartList().get(i).getFinalPrice()));
                            }

                            else {
                                total = parseInt(response.body().getCartList().get(i).getQty()) * parseInt(String.valueOf(response.body().getCartList().get(i).getFinalPrice()));

                            }
                            SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("temp_id",listdata.get(i).getTempId().trim());
                            editor.putString("pro_ID",listdata.get(i).getId().trim());
                            editor.putString("amount", String.valueOf(total));
                            editor.commit();
                            editor.apply();

                        }

                        price.setText(String.valueOf(total));
                        int num1 = Integer.parseInt(price.getText().toString());
                        int num2 = 0;
                        price1.setText(String.valueOf(num2));
                        int sum = num1 + num2;
                        bill.setText(String.valueOf(sum));

                        int size = listdata.size();
                        total1.setText("(" + valueOf(size) + "items):");
//                        set adapter
                        mAdapter.notifyDataSetChanged();
                        Cartrecyclerview.setAdapter(mAdapter);
                        Cartrecyclerview.setHasFixedSize(true);
                        mAdapter.notifyDataSetChanged();
                        layoutManager = new LinearLayoutManager(CartActivity.this);
                        Cartrecyclerview.setLayoutManager(layoutManager);
                    }
                    else {
                        listdata.clear();
                        re.setVisibility(View.GONE);
                        noproduct.setVisibility(View.VISIBLE);
                        Toast.makeText(CartActivity.this, "No product add in cart", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    listdata.clear();
                    noproduct.setVisibility(View.VISIBLE);
                    re.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this, "No product add in cart", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Addcartlist> call, Throwable t) {
                progressDialog.dismiss();
            }


        });


    }
//interface method
    @Override
    public void onMethodCallback() {
        listdata.clear();

cartlist();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(CartActivity.this,DashboardActivity.class);
        startActivity(i);
        finish();
        }
}