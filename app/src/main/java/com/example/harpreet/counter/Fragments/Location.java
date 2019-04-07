package com.example.harpreet.counter.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.harpreet.counter.MainActivity;
import com.example.harpreet.counter.R;
import com.example.harpreet.counter.Utils.Adding;
import com.example.harpreet.counter.search_places;

import static android.app.Activity.RESULT_OK;
import static com.example.harpreet.counter.MainActivity.PLACERESULT;

public class Location extends Fragment {

    public Adding creatingItem;
    public Location() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_location, container, false);
        FloatingActionButton floatingActionButton=view.findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_New_City();
            }
        });
      //  ((MainActivity)getActivity()).setupRecyclerView();
        return view;
    }
    public void Add_New_City()
    {
        startActivityForResult(new Intent(getContext(),search_places.class),PLACERESULT);
        //make different ID for this app to use google places
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==PLACERESULT&&resultCode==RESULT_OK)
        {

            String city_name = data.getStringExtra("city");
            String latitute = data.getStringExtra("latitude");

            String ans=latitute.substring(10,latitute.length()-2);
            // Toast.makeText(this, ans, Toast.LENGTH_LONG).show();
            String lat_longarr[]=ans.split(",");
           // Toast.makeText(getContext(), city_name, Toast.LENGTH_SHORT).show();
            creatingItem = new Adding(lat_longarr,getContext());
            ((MainActivity)getActivity()).function();//added
        }
    }

}
