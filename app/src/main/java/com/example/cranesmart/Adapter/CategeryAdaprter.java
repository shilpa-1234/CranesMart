package com.example.cranesmart.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategeryAdaprter extends PagerAdapter {
    Context mContext;
ArrayList sliderImageId;
    CategeryAdaprter(Context context, ArrayList<String> arr) {
        this.mContext = context;
        this.sliderImageId=arr;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

/*    private int[] sliderImageId = new int[]{
            R.drawable.banner, R.drawable.banner, R.drawable.banner,R.drawable.banner, R.drawable.banner,
    };*/

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(sliderImageId[position]);
        Picasso.with(mContext).load(sliderImageId.get(position).toString()).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return sliderImageId.size();
    }
}