package com.example.cranesmart.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.cranesmart.R;
import com.example.cranesmart.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;
    private int[] tabIcons = {
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_shopping_basket_24,
            R.drawable.ic_baseline_account_balance_wallet_24,
            R.drawable.ic_baseline_person_24,
            R.drawable.ic_baseline_settings_24
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_home, container, false);
       // toolbar = view.findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar)
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new DashFragment(), "Home");
        adapter.addFragment(new OneFragment(), "Shop");
        adapter.addFragment(new DashFragment(), "Wallet");
        adapter.addFragment(new OneFragment(), "Profile");
        adapter.addFragment(new DashFragment(), "Settings");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    } private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }
}