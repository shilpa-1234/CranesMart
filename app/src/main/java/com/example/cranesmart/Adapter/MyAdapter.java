package com.example.cranesmart.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cranesmart.fragment.BroadbandFragment;
import com.example.cranesmart.fragment.DatacardrechargeFragment;
import com.example.cranesmart.fragment.ElectronicsFragment;
import com.example.cranesmart.fragment.GasFragment;
import com.example.cranesmart.fragment.LandlineFragment;
import com.example.cranesmart.fragment.WaterFragment;
import com.example.cranesmart.fragment.DthrechargeFragment;
import com.example.cranesmart.fragment.MobilerechargeFragment;

public class MyAdapter extends FragmentPagerAdapter {
    int   Poss;
    Context context;
        int totalTabs;
        Boolean status=true;
        public MyAdapter(Context c, FragmentManager fm, int totalTabs,int Poss) {
            super(fm);
            context = c;
            this.totalTabs = totalTabs;
            this.Poss=Poss;
        }
        @Override
        public Fragment getItem(int position) {
            if(status)
            {
                position=Poss;
                status=false;
            }
            switch (position) {
                case 0:
                    MobilerechargeFragment fragment = new MobilerechargeFragment();
                     return fragment;
                case 1:
                    DthrechargeFragment dthFragment = new DthrechargeFragment();
                    return dthFragment;
                case 2:
                    DatacardrechargeFragment Fragment = new DatacardrechargeFragment();
                    return Fragment;
                case 3:
                    ElectronicsFragment Fragment1 = new ElectronicsFragment();
                    return Fragment1;
//                    case 4:
//                    GasFragment Fragment2 = new GasFragment();
//                    return Fragment2;
//                    case 5:
//                    BroadbandFragment Fragment3 = new BroadbandFragment();
//                    return Fragment3;
                    case 4:
                    LandlineFragment Fragment2 = new LandlineFragment();
                    return Fragment2;
//                    case 7:
//                    WaterFragment Fragment5 = new WaterFragment();
//                    return Fragment5;
//                    case 8:
//                  FlightFragment Fragment6 = new FlightFragment();
//                    return Fragment6;
//                    case 9:
//                    HotelFragment Fragment7 = new HotelFragment();
//                    return Fragment7;
                default:
                    return null;
            }
        }
        public void setfragment(int position)
        {
            switch (position) {
                case 0:
                    MobilerechargeFragment fragment = new MobilerechargeFragment();

                case 1:
                    DthrechargeFragment dthFragment = new DthrechargeFragment();

                case 2:
                    DatacardrechargeFragment Fragment = new DatacardrechargeFragment();

                case 3:
                    ElectronicsFragment Fragment1 = new ElectronicsFragment();

                case 4:
                    GasFragment Fragment2 = new GasFragment();
                case 5:
                    BroadbandFragment Fragment3 = new BroadbandFragment();

                case 6:
                    LandlineFragment Fragment4 = new LandlineFragment();

                case 7:
                    WaterFragment Fragment5 = new WaterFragment();

//                    case 8:
//                  FlightFragment Fragment6 = new FlightFragment();
//                    return Fragment6;
//                    case 9:
//                    HotelFragment Fragment7 = new HotelFragment();
//                    return Fragment7;
                default:

            }

        }
        @Override
        public int getCount() {
            return totalTabs;
        }
    }

