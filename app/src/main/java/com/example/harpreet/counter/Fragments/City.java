package com.example.harpreet.counter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.counter.MainActivity;
import com.example.harpreet.counter.R;
import com.example.harpreet.counter.Utils.Data;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class City extends Fragment {


    public City() {
        // Required empty public constructor
    }

    private View view;
    public TextView current_temp,current_humidity,wind,current_condition;
    ImageView current_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_city, container, false);
        current_temp = view.findViewById(R.id.current_temp);
        wind = view.findViewById(R.id.wind);
        current_humidity=view.findViewById(R.id.humidity);
        current_condition = view.findViewById(R.id.condition);
        current_image=view.findViewById(R.id.icon);
        return view;
    }
    String limit(String str)
    {
        int limit;String s;
        s="";
        String ss=str;
        limit=str.length();
        if(limit>5)
        {
            for(int i=0;i<5;i++)
                s=s+str.charAt(i);
        }
        else
            s=ss;
        return s;
    }
     public void execute() {
         Data value = null;
         try {
             value = ((MainActivity) getActivity()).POSITION_data;
             if (value != null) {
                 current_condition.setText(limit(value.getCurrent_condition()));
                 current_humidity.setText(limit(value.getCurrent_humidity()));
                 current_temp.setText(limit(value.getCurrent_temp()));
                 wind.setText(limit(value.getWind()));
                 int resId = getActivity().getResources().getIdentifier(
                         "a"+value.getCurrent_image(),
                         "drawable",
                         getActivity().getPackageName()
                 );
                 current_image.setImageResource(resId);
             }
         } catch (NullPointerException e) {
             Log.d(TAG, "Null POSITION_Data");
         }
         if (value != null) {
          current_condition.setText(limit(value.getCurrent_condition()));
          current_humidity.setText(limit(value.getCurrent_humidity()));
          current_temp.setText(limit(value.getCurrent_temp()));
          wind.setText(limit(value.getWind()));
             int resId = getActivity().getResources().getIdentifier(
                     "a"+value.getCurrent_image(),
                     "drawable",
                     getActivity().getPackageName()
             );
             current_image.setImageResource(resId);
         }
     }

    @Override
    public void onStart() {
        super.onStart();
      //  execute();
    }
}
