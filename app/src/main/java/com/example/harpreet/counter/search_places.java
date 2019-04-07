package com.example.harpreet.counter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class search_places extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String placeName = place.getName().toString();
                String lat = place.getLatLng().toString();
                String code=place.getId();
               // Toast.makeText(search_places.this, code, Toast.LENGTH_LONG).show();

                Intent intent = getIntent();
                intent.putExtra("city", placeName);
                intent.putExtra("latitude",lat);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Status status) {

            }
        });

    }
}
