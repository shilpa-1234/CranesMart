package com.example.cranesmart.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cranesmart.Adapter.SizelistAdapter;
import com.example.cranesmart.R;

import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

public class ProductDetailFragment extends Fragment {
    ImageView Imageview,backward,forward;
    TextView Dressname,Description,Oldprice,Currentprice,Reviews,count;
    ListView listviewcolor,listviewsize;
    RadioButton stock,free;
    LinearLayout Rating;
    Button buy,cart;
    ListView lvTest;
    ArrayList<String>product;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        product=new ArrayList<String>();
        Rating=view.findViewById(R.id.rating);
        backward=view.findViewById(R.id.backward);
        forward=view.findViewById(R.id.forward);
        count=view.findViewById(R.id.count);
        Dressname=view.findViewById(R.id.dressname);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        Description=view.findViewById(R.id.description);
        Oldprice=view.findViewById(R.id.oldprice);
        Currentprice=view.findViewById(R.id.currentprice);
        Reviews=view.findViewById(R.id.reviews);
        listviewcolor=view.findViewById(R.id.listviewcolor);
//        listviewsize=view.findViewById(R.id.listviewsize);
//        stock=view.findViewById(R.id.stock);
//        free=view.findViewById(R.id.free);
        buy=view.findViewById(R.id.buy);
        cart=view.findViewById(R.id.cart);

        ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
//        Detail(product);

        ArrayAdapter Items = new ArrayAdapter(getContext(), R.layout.size, items);
         lvTest = view.findViewById(R.id.lvItems);
        lvTest.setAdapter(Items);
        return view;
    }
//    public void Detail (ArrayList<String> product){
//        Activity context = new Activity() ;
//        SizelistAdapter adapter=new SizelistAdapter(context,product);
//        listviewsize.setAdapter(adapter);
//        listviewsize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//    }
}