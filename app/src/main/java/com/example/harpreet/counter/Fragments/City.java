package com.example.harpreet.counter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.counter.MainActivity;
import com.example.harpreet.counter.R;
import com.example.harpreet.counter.Utils.Data;


/**
 * A simple {@link Fragment} subclass.
 */
public class City extends Fragment {


    public City() {
        // Required empty public constructor
    }

    private View view;
    public TextView current_temp,current_humidity,wind,current_condition;ImageView current_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_city, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        current_temp = view.findViewById(R.id.current_temp);
        wind = view.findViewById(R.id.wind);
        current_humidity=view.findViewById(R.id.humidity);
        current_condition = view.findViewById(R.id.condition);
        current_image=view.findViewById(R.id.icon);
        Data selected=((MainActivity)getActivity()).POSITION_data;
        Toast.makeText(getContext(), "here_city", Toast.LENGTH_SHORT).show();
        if(selected!=null){
            Toast.makeText(getContext(), selected.getCurrent_condition(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getContext(), selected.getCurrent_condition(), Toast.LENGTH_SHORT).show();

    }
}
