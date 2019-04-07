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
public class Details extends Fragment {


    public Details() {
        // Required empty public constructor
    }
    TextView one_low,one_high,one_day,condition,
            two_low,two_high,two_day,condition_two,
            three_low,three_high,three_day,condition_three,
            four_low,four_high,four_day,condition_four,
            five_low,five_high,five_day,condition_five,
            six_low,six_high,six_day,condition_six;
    ImageView one_image,two_image,three_image,four_image,five_image,six_image;
       View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        one_low = view.findViewById(R.id.one_low);
        //Toast.makeText(MainActivity.this, ""+one_low, Toast.LENGTH_SHORT).show();
        //one_low.setText(data.getOne_temp_low());
        one_high = view.findViewById(R.id.one_high);
        one_day = view.findViewById(R.id.one_day);
        condition = view.findViewById(R.id.condition_one);

        two_day = view.findViewById(R.id.two_day);
        two_high = view.findViewById(R.id.two_high);
        two_low = view.findViewById(R.id.two_low);
        condition_two = view.findViewById(R.id.condition_two);

        three_day = view.findViewById(R.id.three_day);
        three_high = view.findViewById(R.id.three_high);
        three_low = view.findViewById(R.id.three_low);
        condition_three = view.findViewById(R.id.condition_three);

        four_day = view.findViewById(R.id.four_day);
        four_high = view.findViewById(R.id.four_high);
        four_low = view.findViewById(R.id.four_low);
        condition_four = view.findViewById(R.id.condition_four);

        five_day = view.findViewById(R.id.five_day);
        five_high = view.findViewById(R.id.five_high);
        five_low = view.findViewById(R.id.five_low);
        condition_five = view.findViewById(R.id.condition_five);

        six_day = view.findViewById(R.id.six_day);
        six_high = view.findViewById(R.id.six_high);
        six_low = view.findViewById(R.id.six_low);
        condition_six = view.findViewById(R.id.condition_six);

        one_image = view.findViewById(R.id.one_image);
        two_image = view.findViewById(R.id.two_image);
        three_image = view.findViewById(R.id.three_image);
        four_image = view.findViewById(R.id.four_image);
        five_image = view.findViewById(R.id.five_image);
        six_image = view.findViewById(R.id.six_image);
        return view;
    }

    public void execute() {
        Data value = ((MainActivity)getActivity()).POSITION_data;
        if(value!=null) {
            one_low.setText("Min Temp="+value.getOne_temp_low());
            one_day.setText(value.getOne_day());
            one_high.setText("Max_Temp="+value.getOne_temp_high());
            condition.setText(value.getOne_text());
            Toast.makeText(getContext(), value.getOne_code(), Toast.LENGTH_SHORT).show();

            two_low.setText("Min Temp="+value.getTwo_temp_low());
            two_day.setText(value.getTwo_day());
            two_high.setText("Max_Temp="+value.getTwo_temp_high());
            condition.setText(value.getTwo_text());

            three_low.setText("Min Temp="+value.getThree_temp_low());
            three_day.setText(value.getThree_day());
            three_high.setText("Max_Temp="+value.getThree_temp_high());
            condition.setText(value.getThree_text());

            four_low.setText("Min Temp="+value.getFour_temp_low());
            four_day.setText(value.getFour_day());
            four_high.setText("Max_Temp="+value.getFour_temp_high());
            condition.setText(value.getFour_text());

            five_low.setText("Min Temp="+value.getFive_temp_low());
            five_day.setText(value.getFive_day());
            five_high.setText("Max_Temp="+value.getFive_temp_high());
            condition.setText(value.getFive_text());

            six_low.setText("Min Temp="+value.getSix_temp_low());
            six_day.setText(value.getSix_day());
            six_high.setText("Max_Temp="+value.getSix_temp_high());
            condition.setText(value.getSix_text());

    }}
    @Override
    public void onStart() {
        super.onStart();
        execute();

        }
    }

