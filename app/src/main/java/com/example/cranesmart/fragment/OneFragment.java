package com.example.cranesmart.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.cranesmart.R;

public class OneFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v= inflater.inflate(R.layout.fragment_one, container, false);
            TextView textView=v.findViewById(R.id.text);
            textView.setText("First Fragment");
            return v;
        }
    }