package com.example.harpreet.counter;

import android.app.Dialog;
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
    private SimpleLocation location;//this library is added to get the gps or current location of the User
    public static final int PLACERESULT = 722;
    public static String PACKAGE_NAME;
    //for adapter
    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
    public FirebaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TODO
        1. Change Custom Layout to hold more data
        2. Redundant data is been added to the firebase like mon is been added twice and there is no wed check the cloud function
        3. Handle deleting events
          */

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
       // fragments.add(City_Fragment);
        fragments.add(detail_fragment);
        com.example.harpreet.counter.PagerAdapter adapter1 = new com.example.harpreet.counter.PagerAdapter(getSupportFragmentManager(),fragments);
        viewPager = findViewById(R.id.main_container);
        viewPager.setAdapter(adapter1);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
             if(i==1)
             {
                 if(POSITION_data==null)
                     function();
             }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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
            firebaseFirestore=FirebaseFirestore.getInstance();//to access database
            firebaseFirestore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                           // startActivity(new Intent(MainActivity.this,setup.class));
                        }
                            setupRecyclerView();
                            adapter.startListening();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        function();
                    }
                }
            });
        }
    }
//this function is only for on start so that null is not been set as POSITION_data
    public void function() {
        int size=adapter.getSnapshots().size();
        if(size>0){
            POSITION_data=adapter.getItem(0);
        }else{
            POSITION_data = null;
        }
        change_Data();
    }
    public void change_Data(){
        detail_fragment.execute();
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
                startActivity(new Intent(MainActivity.this,setup.class));
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
        //this will be handling the swiping events for deleting items
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
               change_Data();
           }
       });

    }

}