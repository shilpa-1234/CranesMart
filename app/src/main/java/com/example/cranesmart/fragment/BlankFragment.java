package com.example.cranesmart.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cranesmart.Activity.CartActivity;
import com.example.cranesmart.Adapter.ColorlistAdapter;
import com.example.cranesmart.Adapter.ImageproAdapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Adapter.SizelistAdapter;
import com.example.cranesmart.inrefaceforsizeandcolor.CustomItemClickListener;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.cart.Addcartlist;
import com.example.cranesmart.pojo.cart.CartList;
import com.example.cranesmart.pojo.cart.Cartpojo;
import com.example.cranesmart.pojo.productdetail.Detailpojo;
import com.example.cranesmart.pojo.productdetail.ProductcartSize;
import com.example.cranesmart.pojo.productdetail.productcartcolor;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.WIFI_SERVICE;
import static java.lang.String.valueOf;

public class BlankFragment extends Fragment implements CustomItemClickListener {
    ImageView imageview,backward,forward;
    TextView Dressname;
    TextView Description;
    TextView Oldprice,count;
    TextView Currentprice,rs1,cartitem;
    TextView Reviews;
    ListView listviewsize;
    RadioButton stock,free;
    LinearLayout Rating,layout,linearsize;
    RecyclerView recyclerView,listviewcolor;
    Button buy,cart;
    int i;
    ImageView rating1,rating2,rating3,rating4,rating5;
    AlertDialog.Builder builder;
    Button search;
    EditText search1;
    ArrayList<productcartcolor>backgroundcolor;
    ArrayList<ProductcartSize> product;
    ArrayList<CartList> listdata;
    String proID,prodID,status;
    ViewPager mViewPager;
    int page_position = 0;
    private LinearLayout ll_dots;
    private TextView[] dots;
    private static int currentPage = 0;
    private static int NUM_PAGES = 5;
    ArrayList<String> itemList;
    DotsIndicator dotsIndicator;
    String selectsize,Sizeid;
    String selectcolor,Colorid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_product_detail, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        product=new ArrayList<ProductcartSize>();
        backgroundcolor=new ArrayList<productcartcolor>();

        dotsIndicator = (DotsIndicator) view.findViewById(R.id.dots_indicator);
        mViewPager = view.findViewById(R.id.viewPage);
        itemList=new ArrayList<String>();
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position ==itemList.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                mViewPager.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);
        Rating=view.findViewById(R.id.rating);
        rating1=view.findViewById(R.id.rating1);
        rating2=view.findViewById(R.id.rating2);
        rating3=view.findViewById(R.id.rating3);
        rating4=view.findViewById(R.id.rating4);
        rating5=view.findViewById(R.id.rating5);
        recyclerView   = view.findViewById(R.id.lvItems);
        backward=view.findViewById(R.id.backward);
        listdata= new ArrayList<CartList>() ;
        forward=view.findViewById(R.id.forward);
        count=view.findViewById(R.id.count);
        Dressname=view.findViewById(R.id.dressname);
        builder = new AlertDialog.Builder(getContext());
        Description=view.findViewById(R.id.description);
        rs1=view.findViewById(R.id.rs1);
        cartitem=getActivity().findViewById(R.id.cartitem);
        Oldprice=view.findViewById(R.id.oldprice);
        Currentprice=view.findViewById(R.id.currentprice);
        Reviews=view.findViewById(R.id.reviews);
        listviewcolor=view.findViewById(R.id.listviewcolor);
        layout=view.findViewById(R.id.layout);
        linearsize=view.findViewById(R.id.size);
        buy=view.findViewById(R.id.buy);
        cart=view.findViewById(R.id.cart);
        Bundle arguments = getArguments();
        assert arguments != null;
        proID = arguments.getString("proID");
        proID=arguments.getString("proID");


        Log.d("PRODCTID", proID);

rating1.setOnClickListener(new View.OnClickListener() {
    boolean clicked = false;
    @Override
    public void onClick(View view) {
        if(!clicked) {
            rating1.setBackgroundResource(R.drawable.rating);
            clicked=true;
        }
        else{
            rating1.setBackgroundResource(R.drawable.rating1);
            rating2.setBackgroundResource(R.drawable.rating1);
            rating3.setBackgroundResource(R.drawable.rating1);
            rating4.setBackgroundResource(R.drawable.rating1);
            rating5.setBackgroundResource(R.drawable.rating1);
            clicked=false;
        }
    }
});
        rating2.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View view) {
                if(!clicked) {
                    rating1.setBackgroundResource(R.drawable.rating);
                    rating2.setBackgroundResource(R.drawable.rating);
                    clicked=true;
                }
                else{
                    rating2.setBackgroundResource(R.drawable.rating1);
                    rating3.setBackgroundResource(R.drawable.rating1);
                    rating4.setBackgroundResource(R.drawable.rating1);
                    rating5.setBackgroundResource(R.drawable.rating1);
                    clicked=false;
                }
            }
        });
        rating3.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View view) {
                if(!clicked) {
                    rating1.setBackgroundResource(R.drawable.rating);
                    rating2.setBackgroundResource(R.drawable.rating);
                    rating3.setBackgroundResource(R.drawable.rating);
                    clicked=true;
                }
                else{
                    rating3.setBackgroundResource(R.drawable.rating1);
                    rating4.setBackgroundResource(R.drawable.rating1);
                    rating5.setBackgroundResource(R.drawable.rating1);
                    clicked=false;
                }
            }
        });
        rating4.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View view) {
                if(!clicked) {
                    rating1.setBackgroundResource(R.drawable.rating);
                    rating2.setBackgroundResource(R.drawable.rating);
                    rating3.setBackgroundResource(R.drawable.rating);
                    rating4.setBackgroundResource(R.drawable.rating);
                    clicked=true;
                }
                else{
                    rating4.setBackgroundResource(R.drawable.rating1);
                    rating5.setBackgroundResource(R.drawable.rating1);
                    clicked=false;
                }
            }
        });
        rating5.setOnClickListener(new View.OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View view) {
                if(!clicked) {
                    rating1.setBackgroundResource(R.drawable.rating);
                    rating2.setBackgroundResource(R.drawable.rating);
                    rating3.setBackgroundResource(R.drawable.rating);
                    rating4.setBackgroundResource(R.drawable.rating);
                    rating5.setBackgroundResource(R.drawable.rating);
                    clicked=true;
                }
                else{
                    rating5.setBackgroundResource(R.drawable.rating1);
                    clicked=false;
                }
            }
        });


        Sizee();
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i<10){
               i++;
                }
                count.setText(String.valueOf(i));
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

     if(i<=1)
     {

         count.setText(String.valueOf(1));
         i=1;
     }
     else {
         i--;
         count.setText(String.valueOf(i));
     }
    }
});
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), CartActivity.class);
                startActivity(i);
                cart();

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>You added product in cart sucessfully</font>"));
                        alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color='#000'>OK</font>"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(getContext(),"Added Sucessfully",Toast.LENGTH_LONG).show();
                                        cart();
                                        Log.d("Addtocart","Cart");
                                    }
                                });



                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();


            }
        });

        return view;
    }


    private void size(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        SizelistAdapter adapter = new SizelistAdapter(product,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
    }
    private void color(){
        listviewcolor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        ColorlistAdapter adapter = new ColorlistAdapter(backgroundcolor,this);
        listviewcolor.setHasFixedSize(true);
        listviewcolor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        listviewcolor.setAdapter(adapter);
    }
private  void cart(){
    final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
    progressDialog.setContentView(R.layout.custom_loader);
    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    //getting the user values

    //building retrofit object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Defining retrofit api service
    APIService service = retrofit.create(APIService.class);
    WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
    String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    SharedPreferences sh=getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    Log.d("Ipaddress", ipAddress);
    String value=sh.getString("userID","0"); assert value != null;
    Log.d("@@shared@@",value);
    JSONObject postData = new JSONObject();
    try {
        if(Colorid!=null&&selectcolor!=null||Sizeid!=null&&selectsize!=null){
            if(Sizeid==null&&selectsize==null){
                postData.put(Colorid, selectcolor.toString());
                postData.put("0","0");
//                selectsize="0";
            }
            else if(Colorid==null&&selectcolor==null){
                postData.put(Sizeid, selectsize.toString());
                Colorid="0";
                selectcolor="0";
            }
            else{
                postData.put(Colorid, selectcolor.toString());
                postData.put(Sizeid, selectsize.toString());
                Log.d("jsonobjectprint", "cart: " + postData);
            }

        }
        else if(Sizeid!=null&&selectsize!=null){
            postData.put(Sizeid, selectsize.toString());
            Log.d("jsonobjectprint", "cart: " + postData);
        }
     else if(Colorid==null&&selectcolor==null){
        Colorid="0";
        selectcolor="0";
    }
        else if(Sizeid==null&&selectsize==null){
            Sizeid="0";
            selectsize="0";
        }
        else {
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
    Call<Cartpojo> call = service.cart(value,ipAddress,proID,count.getText().toString(), String.valueOf(postData));
    Log.d("Quantity",count.getText().toString());
    call.enqueue(new Callback<Cartpojo>() {
        @Override
        public void onResponse(Call<Cartpojo> call, Response<Cartpojo> response) {
            progressDialog.dismiss();
            cartlist();
            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Call<Cartpojo> call, Throwable t) {
             progressDialog.dismiss();
            Toast.makeText(getContext(),"Bad response"+t.getMessage(),Toast.LENGTH_LONG).show();

        }


    });


        }
    private void cartlist() {
        listdata.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Log.d("@@methodip", "cartlist: "+ipAddress);
        SharedPreferences sh = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value = sh.getString("userID", "0");
        Log.d("@@shared@@123", value);
        Call<Addcartlist> call = service.cartlist(value, ipAddress);
        call.enqueue(new Callback<Addcartlist>() {
            private static final String TAG = "BlankFragment";
            @Override
            public void onResponse(Call<Addcartlist> call, Response<Addcartlist> response) {
                Log.d("Addcartlistresponse", response.body().toString());
                Log.d("Addcartlist", response.body().getStatus().toString());
                if (response.body().getCartList() != null||!(response.body().getCartList().isEmpty())) {
                    String item= String.valueOf(response.body().getCartList().size());
                    Log.d(TAG, "onResponse: "+item);
                    cartitem.setText(item);
                    cartitem.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(getContext(), "CartApi not work", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Addcartlist> call, Throwable t) {
                Log.d("Successsf", "onResponse:Blankfragment Failurethrow "+t.getMessage());
                Toast.makeText(getContext(),"Bad response"+t.getMessage(),Toast.LENGTH_LONG).show();

            }


        });
    }

    private void Sizee() {
itemList.clear();
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        backgroundcolor.clear();
        APIService service = retrofit.create(APIService.class);

        Call<Detailpojo> call = service.detail(proID);
        call.enqueue(new Callback<Detailpojo>() {
            @Override
            public void onResponse(Call<Detailpojo> call, Response<Detailpojo> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    String TAG="@@image";
                    Log.d("$$$%", response.body().toString());
                    Log.d("$$$&", response.body().getStatus().toString());
                    Log.d("$$$*", response.body().getMessage().toString());
                        for (int image=0;image < response.body().getImg().size();image++) {
                            Log.d(TAG, "onResponse: "+response.body().getImg().size());
                            itemList.add(response.body().getImg().get(image));
                        }
                    if (response.body().getStatus() == 1||response.body().getImg()!=null) {
                        ImageproAdapter adapterView = new ImageproAdapter(getContext(), itemList);
                        mViewPager.setAdapter(adapterView);
                        dotsIndicator.setViewPager(mViewPager);

                    }

                        response.body().getName();
                        Dressname.setText(response.body().getName());
                        response.body().getPrice();
                        Oldprice.setText(response.body().getPrice());
                        if (response.body().getIsSpecialPrice() == 1) {
                            Log.d("getspecialprice", "onResponse: ");
                            response.body().getSpecialPrice();
                            Currentprice.setText(response.body().getSpecialPrice());
                            Oldprice.setPaintFlags(Oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        } else {
                            Currentprice.setVisibility(View.GONE);
                            rs1.setVisibility(View.GONE);
                            Oldprice.setPaintFlags(Oldprice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        }


                    if (response.body().getDescription() != null) {
                        response.body().getDescription();
                        Description.setText(response.body().getDescription());
                    } else {
                        Description.setVisibility(View.GONE);
                    }
                    assert response.body().getAttributeList() != null;
                    if (response.body().getAttributeList() == null || response.body().getAttributeList().equals("") || response.body().getAttributeList().isEmpty()) {
                        Log.d("Visibilty", "onResponse: ");
                        layout.setVisibility(View.GONE);
                        linearsize.setVisibility(View.GONE);
                    } else if (response.body().getAttributeList() != null || !(response.body().getAttributeList().equals("0"))) {

                        if (Integer.valueOf(response.body().getAttributeList().get(0).getColor().size()) == null) {
                            layout.setVisibility(View.GONE);
                        } else {
                            Log.d("TEST", response.body().getAttributeList().get(0).getColor().get(0).getLabel());
                            Log.d("TEST", String.valueOf(response.body().getAttributeList().get(0).getColor().size()));
                            Log.d("Stringggg", "answer print or not");
                            for (i = 0; i < response.body().getAttributeList().get(0).getColor().size(); i++) {
                                if(response.body().getAttributeList().get(0).getColor().get(i).getDescription().equals("")){
                                    layout.setVisibility(View.GONE);
                                }
                               else if(response.body().getAttributeList().get(0).getColor().get(i).getDescription()!=null){
                                backgroundcolor.add(response.body().getAttributeList().get(0).getColor().get(i));
                                Colorid=response.body().getAttributeList().get(0).getId();
                                }

                                Log.d("TAGlabel", response.body().getAttributeList().get(0).getColor().get(i).getLabel().toString());
                                Log.d("TAGcolor", response.body().getAttributeList().get(0).getColor().get(i).getDescription().toString());

                            }
                            color();


                        }
                        if (response.body().getAttributeList().get(1).getSize() == null) {
                            linearsize.setVisibility(View.GONE);
                        } else {
                            for (i = 0; i < response.body().getAttributeList().get(1).getSize().size(); i++) {
                                if(response.body().getAttributeList().get(1).getSize().get(i).getDescription()!=null&&!(response.body().getAttributeList().get(1).getSize().get(i).getDescription().isEmpty())&&!(response.body().getAttributeList().get(1).getSize().get(i).getDescription().equals(""))){
                                    product.add(response.body().getAttributeList().get(1).getSize().get(i));
                                    Sizeid=response.body().getAttributeList().get(1).getId();
                                }
                                else{
                                    linearsize.setVisibility(View.GONE);
                                }
                            }
                            size();


                        }

                    } else {
                        Log.d("Visibilty", "onResponse:1 ");
                        layout.setVisibility(View.GONE);
                        linearsize.setVisibility(View.GONE);
                    }
                }
                else {
                    Log.d("Visibilty", "onResponse:1 ");
                    layout.setVisibility(View.GONE);
                    linearsize.setVisibility(View.GONE);
                }
                }
            @Override
            public void onFailure(Call<Detailpojo> call, Throwable t) {
                progressDialog.dismiss();
            }

            });


}


    @Override
    public void onItemClick(String position,int Status) {
        if (Status==0){
        Log.d("@@positionsize", "onItemClick: "+position);
        selectsize=position;
        }
        else if(Status==1){
            Log.d("@@positioncolor", "onItemClick: "+position);
            selectcolor=position;
        }
    }
}