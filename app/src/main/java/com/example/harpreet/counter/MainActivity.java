package com.example.harpreet.counter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.counter.Account.login;
import com.example.harpreet.counter.Account.setup;
import com.example.harpreet.counter.Fragments.City;
import com.example.harpreet.counter.Fragments.Details;
import com.example.harpreet.counter.Fragments.Location;
import com.example.harpreet.counter.Utils.Data;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    public Data POSITION_data;
    android.support.v7.widget.Toolbar main_toolbar;
    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    Location Location_fragment;
    City City_Fragment;
    Details detail_fragment;
    private SimpleLocation location;//this libraray is added to get the gps or current location of the User
    public static final int PLACERESULT = 722;
    public static String PACKAGE_NAME;
    //for adapter
    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
    public FirebaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = new SimpleLocation(this);
        mauth=FirebaseAuth.getInstance();
        main_toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setTitle("Weather");
        PACKAGE_NAME  =getApplicationContext().getPackageName();

        if(mauth.getCurrentUser()==null) {
          startActivity(new Intent(MainActivity.this,login.class));
        }
        Location_fragment = new Location();
        City_Fragment = new City();
        detail_fragment = new Details();
        //fragments are been set in the view pager
        fragments = new ArrayList<>();
        fragments.add(Location_fragment);
        fragments.add(City_Fragment);
        fragments.add(detail_fragment);
        com.example.harpreet.counter.PagerAdapter adapter1 = new com.example.harpreet.counter.PagerAdapter(getSupportFragmentManager(),fragments);
        viewPager = findViewById(R.id.main_container);
        viewPager.setAdapter(adapter1);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            //go to sign in page
            sendtoLogin();
        }
        else
        {
            String userID;
            userID=mauth.getCurrentUser().getUid();
            FirebaseFirestore firebaseFirestore;
            firebaseFirestore=FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                           // startActivity(new Intent(MainActivity.this,setup.class));
                        }
                            setupRecyclerView();
                            adapter.startListening();
                            if(adapter.getSnapshots().size()==0){
                                POSITION_data=null;
                            }else{
                                POSITION_data=adapter.getItem(1);
                            }
                    }
                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
 //       adapter.stopListening();
    }

    private void sendtoLogin()
    {
        Intent intent=new Intent(MainActivity.this,login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case (R.id.refresh):
                Toast.makeText(MainActivity.this,"refresh Selected",Toast.LENGTH_LONG).show();
                return true;
            case (R.id.location) :
                // if we can't access the location yet
                if (!location.hasLocationEnabled()) {
                    // ask the user to enable location access
                    SimpleLocation.openSettings(this);
                }
                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                String lat_lon=latitude+" "+longitude;
                Toast.makeText(MainActivity.this,lat_lon,Toast.LENGTH_LONG).show();
                return true;
            case(R.id.settings):
                Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT).show();
                //do something
                return true;
            case (R.id.about_us):
                //do something
                Toast.makeText(MainActivity.this,"About us Selected",Toast.LENGTH_LONG).show();

                return true;
            case (R.id.sign_out):
            {
                mauth.signOut();
                startActivity(new Intent(this,login.class));
            }
            default:
                return false;
        }

    }

    public void setupRecyclerView(){

        FirebaseAuth auth=FirebaseAuth.getInstance();
        String userid=auth.getCurrentUser().getUid();
        CollectionReference reference = firebaseFirestore.collection("/"+userid);
        Query query = reference
                .limit(50);
        FirestoreRecyclerOptions<Data> options = new FirestoreRecyclerOptions.Builder<Data>().
                setQuery(query,Data.class)
                .build();
        adapter = new FirebaseAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //this will be handling the swiping events
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
             adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

       adapter.setOnItemClickListener(new FirebaseAdapter.OnitemClickListener() {
           @Override
           public void OnitemClick(DocumentSnapshot snapshot, int position) {
               Data data = snapshot.toObject(Data.class);
               POSITION_data=data;
               /*
               TextView one_low,one_high,one_day,condition,
                       two_low,two_high,two_day,condition_two,
                       three_low,three_high,three_day,condition_three,
                       four_low,four_high,four_day,condition_four,
                       five_low,five_high,five_day,condition_five,
                       six_low,six_high,six_day,condition_six;
               one_low = findViewById(R.id.one_low);
               Toast.makeText(MainActivity.this, ""+one_low, Toast.LENGTH_SHORT).show();
               //one_low.setText(data.getOne_temp_low());
               one_high = findViewById(R.id.one_high);
               one_day = findViewById(R.id.one_day);
               condition = findViewById(R.id.condition_one);

               two_day = findViewById(R.id.two_day);
               two_high = findViewById(R.id.two_high);
               two_low = findViewById(R.id.two_low);
               condition_two = findViewById(R.id.condition_two);

               three_day = findViewById(R.id.three_day);
               three_high = findViewById(R.id.three_high);
               three_low = findViewById(R.id.three_low);
               condition_three = findViewById(R.id.condition_three);

               four_day = findViewById(R.id.four_day);
               four_high = findViewById(R.id.four_high);
               four_low = findViewById(R.id.four_low);
               condition_four = findViewById(R.id.condition_four);

               five_day = findViewById(R.id.five_day);
               five_high = findViewById(R.id.five_high);
               five_low = findViewById(R.id.five_low);
               condition_five = findViewById(R.id.condition_five);

               six_day = findViewById(R.id.six_day);
               six_high = findViewById(R.id.six_high);
               six_low = findViewById(R.id.six_low);
               condition_six = findViewById(R.id.condition_six);
               */
           }
       });

    }

}