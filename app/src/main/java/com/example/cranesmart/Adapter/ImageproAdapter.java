package com.example.cranesmart.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cranesmart.R;
//import com.jsibbold.zoomage.ZoomageView;

import java.util.ArrayList;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;

public class ImageproAdapter extends PagerAdapter {
    Context mContext;
ArrayList sliderImageId;
    public ImageproAdapter(Context context, ArrayList<String> arr) {
        this.mContext = context;
        this.sliderImageId=arr;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageViewZoom) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageViewZoom imageView = new ImageViewZoom(mContext);
        imageView.setScaleType(ImageViewZoom.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(sliderImageId.get(position)).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageViewZoom) object);
    }

    @Override
    public int getCount() {
        return sliderImageId.size();
    }
}