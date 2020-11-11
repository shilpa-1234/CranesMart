package com.example.cranesmart.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Backinterface;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.fragment.EditprofileFragment;
import com.example.cranesmart.R;
import com.example.cranesmart.fragment.DashFragment;
import com.example.cranesmart.fragment.SettingFragment;
import com.example.cranesmart.fragment.WalletFragment;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.example.cranesmart.pojo.cart.Addcartlist;
import com.example.cranesmart.pojo.cart.CartList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.getActivity;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class DashboardActivity extends AppCompatActivity implements  Backinterface {
    DrawerLayout drawer;
    NavigationView navigationView;

    ImageView menu, shopingcart, imageprof;
    ArrayList<CartList> listdata;
    TextView logout, t_name,t_mobile;
    LinearLayout l_homework, l_holiday, l_answersheet, l_feereport, l_location, l_locatio, l_help, l_moment, l_logout,wallet;
    private int backpress;
    TextView textCartItemCount, cartitem;
    int mCartItemCount = 10;
    boolean exitstatus = false;
    boolean isBackPressed  = false;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    String stat="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        isInternetOn();
        checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
        Fragment frag = new DashFragment();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        final BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentTransaction fragmentTransactionProfile = DashboardActivity.this.getSupportFragmentManager().beginTransaction();
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
        fragment_transaction1(fragmentTransactionProfile, frag, "fragmet");
        shopingcart = findViewById(R.id.shopingcart);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        l_homework = findViewById(R.id.l_homework);
        cartitem = findViewById(R.id.cartitem);
        listdata = new ArrayList<CartList>();
        l_holiday = findViewById(R.id.l_holiday);
        l_answersheet = findViewById(R.id.l_answersheet);
        wallet = findViewById(R.id.wallet);
        l_feereport = findViewById(R.id.l_feereport);
        l_location = findViewById(R.id.l_location);
        l_locatio = findViewById(R.id.l_locatio);
        l_help = findViewById(R.id.l_help);
        l_moment = findViewById(R.id.l_moment);
        t_name = findViewById(R.id.t_name);
        t_mobile = findViewById(R.id.t_mobile);
        imageprof = findViewById(R.id.imageprof);
        mBottomNavigationView.getMenu().findItem(R.id.Home).setChecked(true);
        BottomNavigationBar();
        cartlist();

        l_logout = findViewById(R.id.l_logout);
// Navigation Bar item On Click Listner Start
        navigationView = findViewById(R.id.nav_view);
        menu = findViewById(R.id.menu1);
        menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                drawer.openDrawer(Gravity.START);
                userdetail();
            }
        });

        shopingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        l_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itent= new Intent(DashboardActivity.this,RechargeActivity.class);
                startActivity(itent);
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new WalletFragment();

                fragment_transaction1(fragmentTransactionProfile, frag, "holiday");
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        l_holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new EditprofileFragment() ;

                fragment_transaction1(fragmentTransactionProfile, frag, "holiday");
                drawer.closeDrawer(GravityCompat.START);
            }

        });

        l_answersheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent itent= new Intent(DashboardActivity.this, AddresslistmenuActivity.class);
                itent.putExtra("status","1");
                startActivity(itent);
        }
        });
        l_feereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent itent = new Intent(DashboardActivity.this, CartActivity.class);
                startActivity(itent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        l_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();

            }
        });
        l_locatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
//                Intent itent= new Intent(DashboardActivity.this,MainActivity.class);
//                startActivity(itent);
            }
        });
        l_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
//                Intent itent= new Intent(DashboardActivity.this,MainActivity.class);
//                startActivity(itent);
            }
        });
        l_moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
//                Intent itent= new Intent(DashboardActivity.this,MainActivity.class);
//                startActivity(itent);
            }
        });
        l_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Do you want to Log out this application ?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString(Sharedprefence.logStatus,"0");
                                editor.apply();
                                editor.commit();

                                Intent i=  new Intent(DashboardActivity.this, LoginActivity.class);
                                startActivity(i);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alert.show();

                drawer.closeDrawer(GravityCompat.START);
            }
        });
//        navigation Bar item listner are Endeded here....
    }
//Bottom Navigation Method Start From Here....
    public void BottomNavigationBar() {
        final BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentTransaction fragmentTransactionProfile = DashboardActivity.this.getSupportFragmentManager().beginTransaction();
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Home:
                        exitstatus=true;

                        Fragment frag = new DashFragment();
                        fragment_transaction1(fragmentTransactionProfile, frag, "dash");
                        mBottomNavigationView.getMenu().findItem(R.id.Home).setChecked(true);
                        return true;
                    case R.id.Wallet:
                        exitstatus=false;
                        Fragment frag1 = new WalletFragment();
                        fragment_transaction1(fragmentTransactionProfile, frag1, "wallet");
                        mBottomNavigationView.getMenu().findItem(R.id.Wallet).setChecked(true);
                        return true;
                    case R.id.Profile:
                        exitstatus=false;
                        Fragment frag2 = new EditprofileFragment();
                        fragment_transaction1(fragmentTransactionProfile, frag2, "profile");
                        mBottomNavigationView.getMenu().findItem(R.id.Profile).setChecked(true);
                        return true;
                    case R.id.Shop:
                        exitstatus=false;
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Coming Soon</font>"));
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        alertDialog.show();
//                        Fragment frag3 = new ShopFragment();
//                        fragment_transaction1(fragmentTransactionProfile, frag3, "Shop");
//                        mBottomNavigationView.getMenu().findItem(R.id.Shop).setChecked(true);
                        return true;
                    case R.id.Setting:
                        exitstatus=false;
                        Fragment frag4 = new SettingFragment();
                        fragment_transaction1(fragmentTransactionProfile, frag4, "setting");
                        mBottomNavigationView.getMenu().findItem(R.id.Setting).setChecked(true);
                        return true;


                }
                return false;
            }
        };

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
// End Here....

    }



    public void fragment_transaction1(FragmentTransaction fragmentTransactionChange, Fragment changeFragment, String tag) {

        fragmentTransactionChange= getSupportFragmentManager().beginTransaction();
        fragmentTransactionChange.replace(R.id.frame, changeFragment,"fragment");
        fragmentTransactionChange.addToBackStack(null);
        fragmentTransactionChange.commit();

    }
// Cart list Api
    private void cartlist() {

        listdata.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        WifiManager wifiManager = (WifiManager) DashboardActivity.this.getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        SharedPreferences sh = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = sh.getString(Sharedprefence.UserID, "0");

        Call<Addcartlist> call = service.cartlist(value, ipAddress);
        call.enqueue(new Callback<Addcartlist>() {
            @Override
            public void onResponse(Call<Addcartlist> call, Response<Addcartlist> response) {


                if (response.body().getCartList()==null||response.body().getCartList().isEmpty()) {
                    cartitem.setVisibility(View.GONE);

                } else {
                    cartitem.setText(String.valueOf(response.body().getCartList().size()));
                    cartitem.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Addcartlist> call, Throwable t) {

            }


        });
    }

// On Back Press Method
    @Override
    public void onBackPressed() {
        drawer.closeDrawer(GravityCompat.START);
        final BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.getMenu().findItem(R.id.Home).setChecked(true);
            int count = getSupportFragmentManager().getBackStackEntryCount();
        SharedPreferences preferences=getSharedPreferences(Sharedprefence.SharedprefenceName,Context.MODE_PRIVATE);
         stat=preferences.getString("status1","0");
         String Statusdash=preferences.getString(Sharedprefence.SearchStatus,"0");
            if (Statusdash.equals("1")||!(Statusdash.equals("0"))) {
                drawer.closeDrawer(GravityCompat.START);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000'>Are you sure you want to exit?</font>"));
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DashboardActivity.this.finishAffinity();
                            }
                        });
                 alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                     }
                 });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
                SharedPreferences preferences1=getSharedPreferences(Sharedprefence.SharedprefenceName,MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences1.edit();
                editor.putString(Sharedprefence.SearchStatus,"0");
                editor.apply();
                editor.commit();

            }

      else  if(stat.equals("1")||!(stat.equals("0"))){
            FragmentTransaction fragmentTransactionChange;
            Fragment changeFragment=new DashFragment();
            fragmentTransactionChange= getSupportFragmentManager().beginTransaction();
            fragmentTransactionChange.add(R.id.frame, changeFragment);
            fragmentTransactionChange.addToBackStack(null);
            fragmentTransactionChange.commit();
            SharedPreferences preferences1=getSharedPreferences(Sharedprefence.SharedprefenceName,MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences1.edit();
            editor.putString("status1","0");
            editor.apply();
            editor.commit();

        }

                else {
                    getSupportFragmentManager().popBackStack();
                }



        }
// Getting User Detail
    private void userdetail() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0"); assert value != null;



        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
//                progressDialog.dismiss();

                if(response.body().getUserDetail().getProfilePhoto()!=null||response.body().getUserDetail().getName()!=null){
                Glide.with(DashboardActivity.this).load(APIUrl.IMG_URL+response.body().getUserDetail().getProfilePhoto()).placeholder(R.drawable.circleimg1).into(imageprof);
                String nameurl=response.body().getUserDetail().getName().toString().trim();
                t_name.setText(nameurl);
                    t_mobile.setText("+91"+response.body().getUserDetail().getMobile().toString().trim());
                    SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString(Sharedprefence.Name,nameurl);
                    editor.apply();
                    editor.commit();
               }
                else{
                    Toast.makeText(DashboardActivity.this, "null pointer expecption is there", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
// Check Internet Connection
    @Override
    public void onName(String name) {
        exitstatus=false;
    }
    public final boolean isInternetOn() {
        AlertDialog.Builder builder;

        final ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {

            builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setMessage(Html.fromHtml("<font color='#000'>Please Check the internet connection</font>") ).setTitle("<font color='#000'>No Internet Connection</font>");

            builder.setMessage(Html.fromHtml("<font color='#000'>Please Check the internet connection</font>"))
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                           startActivity(intent);
                        }
//                            Toast.makeText(SplashScreenActivity.this,"you choose no action for alertbox",
//                                    Toast.LENGTH_SHORT).show();

                    });
            AlertDialog alert = builder.create();
            alert.setTitle(Html.fromHtml("<font color='#000'>No Internet Connection</font>"));
            alert.show();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        }

        Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
        return false;
    }
//    Getting Permission
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(DashboardActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(DashboardActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DashboardActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(DashboardActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DashboardActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(DashboardActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}

