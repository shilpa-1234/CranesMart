package com.example.cranesmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cranesmart.Adapter.MyAdapter;
import com.example.cranesmart.R;
import com.google.android.material.tabs.TabLayout;

public class RechargeActivity extends AppCompatActivity {
  TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    ImageView backward;
    int poss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        backward=findViewById(R.id.backward);
        tabLayout.setTabTextColors(getResources().getColor(R.color.opaque_black), getResources().getColor(R.color.blue));
isInternetOn();
        poss = getIntent().getIntExtra("poss", 0);

        tabLayout.addTab(tabLayout.newTab().setText("Mobile"));
        tabLayout.addTab(tabLayout.newTab().setText("Dth"));
        tabLayout.addTab(tabLayout.newTab().setText("Datacard"));
        tabLayout.addTab(tabLayout.newTab().setText("Electricity"));
//        tabLayout.addTab(tabLayout.newTab().setText("Gas"));
//        tabLayout.addTab(tabLayout.newTab().setText("Broadband"));
        tabLayout.addTab(tabLayout.newTab().setText("Landline"));
//        tabLayout.addTab(tabLayout.newTab().setText("Water"));
//        tabLayout.addTab(tabLayout.newTab().setText("Flight"));
//        tabLayout.addTab(tabLayout.newTab().setText("Hotel"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount(),poss);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(poss);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.select();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RechargeActivity.this,DashboardActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(RechargeActivity.this,DashboardActivity.class);
        startActivity(i);
        super.onBackPressed();
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

            builder = new AlertDialog.Builder(RechargeActivity.this);
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

}
