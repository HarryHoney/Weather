package com.example.harpreet.counter.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.harpreet.counter.Fragments.City;
import com.example.harpreet.counter.Fragments.Details;
import com.example.harpreet.counter.Fragments.Location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Math.floor;

public class Adding {

    private Location location;
    private City city;
    private Details details;
    private String latitute="",longitude="";
    Context context;
    public String Url = "https://weatherserviceapp.herokuapp.com/data?";
    FirebaseFirestore firebaseFirestore;
    public Adding(Location location,City city,Details details)
    {
        this.city=city;
        this.details=details;
        this.location=location;
    }
    public Adding(String arr[],Context context)
    {
        this.context=context;
        latitute=arr[0];
        longitude=arr[1];
        //ask the server for JSON Data
        String callingurl=Url+"lat="+latitute+"&lon="+longitude;
        new JSONTask().execute(callingurl);
    }







    public class JSONTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String city = jsonObject.getString("city");
                String current_temp = jsonObject.getString("current_temp");
                String date = jsonObject.getString("date");
                String wind = jsonObject.getString("wind");
                String current_condition = jsonObject.getString("current_condition");
                String current_humidity = jsonObject.getString("current_humidity");
                String current_image = jsonObject.getString("current_image");
                String one_day = jsonObject.getString("one_day");
                String one_temp_low = jsonObject.getString("one_temp_low");
                String one_temp_high = jsonObject.getString("one_temp_high");
                String one_text = jsonObject.getString("one_text");
                String one_code = jsonObject.getString("one_code");
                String two_day = jsonObject.getString("two_day");
                String two_temp_low = jsonObject.getString("two_temp_low");
                String two_temp_high = jsonObject.getString("two_temp_high");
                String two_text = jsonObject.getString("two_text");
                String two_code = jsonObject.getString("two_code");
                String three_day = jsonObject.getString("three_day");
                String three_temp_low = jsonObject.getString("three_temp_low");
                String three_temp_high = jsonObject.getString("three_temp_high");
                String three_text = jsonObject.getString("three_text");
                String three_code = jsonObject.getString("three_code");
                String four_day = jsonObject.getString("two_day");
                String four_temp_low = jsonObject.getString("two_temp_low");
                String four_temp_high = jsonObject.getString("two_temp_high");
                String four_text = jsonObject.getString("two_text");
                String four_code = jsonObject.getString("two_code");
                String five_day = jsonObject.getString("two_day");
                String five_temp_low = jsonObject.getString("two_temp_low");
                String five_temp_high = jsonObject.getString("two_temp_high");
                String five_text = jsonObject.getString("two_text");
                String five_code = jsonObject.getString("two_code");
                String six_day = jsonObject.getString("two_day");
                String six_temp_low = jsonObject.getString("two_temp_low");
                String six_temp_high = jsonObject.getString("two_temp_high");
                String six_text = jsonObject.getString("two_text");
                String six_code = jsonObject.getString("two_code");

                Double value;
                Double temp=Double.parseDouble(current_temp);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                current_temp=temp+"";
                Long time=Long.parseLong(date);
                Timestamp timestamp =new Timestamp(time);
                Date dd = new Date(timestamp.getTime());
                date=dd.toString();
                HashMap<String ,String> map= new HashMap<>();
                map.put("city",city);
                map.put("current_temp",current_temp);
                map.put("date",date);
                map.put("wind",wind);
                map.put("current_condition",current_condition);
                map.put("current_humidity",current_humidity);
                map.put("current_image",current_image);

                temp=Double.parseDouble(one_temp_low);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                one_temp_low=temp+"";
                temp=Double.parseDouble(one_temp_high);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                one_temp_high=temp+"";
                map.put("one_day",one_day);
                map.put("one_temp_low",one_temp_low);
                map.put("one_temp_high",one_temp_high);
                map.put("one_text",one_text);
                map.put("one_code",one_code);

                temp=Double.parseDouble(two_temp_low);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                two_temp_low=temp+"";
                temp=Double.parseDouble(two_temp_high);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                two_temp_high=temp+"";
                map.put("two_day",two_day);
                map.put("two_temp_low",two_temp_low);
                map.put("two_temp_high",two_temp_high);
                map.put("two_text",two_text);
                map.put("two_code",two_code);

                temp=Double.parseDouble(three_temp_low);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                three_temp_low=temp+"";
                temp=Double.parseDouble(three_temp_high);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                three_temp_high=temp+"";
                map.put("three_day",three_day);
                map.put("three_temp_low",three_temp_low);
                map.put("three_temp_high",three_temp_high);
                map.put("three_text",three_text);
                map.put("three_code",three_code);

                temp=Double.parseDouble(four_temp_low);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                four_temp_low=temp+"";
                temp=Double.parseDouble(four_temp_high);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                four_temp_high=temp+"";
                map.put("four_day",four_day);
                map.put("four_temp_low",four_temp_low);
                map.put("four_temp_high",four_temp_high);
                map.put("four_text",four_text);
                map.put("four_code",four_code);

                temp=Double.parseDouble(five_temp_low);
                temp=((temp-32)*5)/9;
                five_temp_low=temp+"";
                temp=Double.parseDouble(five_temp_high);
                temp=((temp-32)*5)/9;
                five_temp_high=temp+"";
                map.put("five_day",five_day);
                map.put("five_temp_low",five_temp_low);
                map.put("five_temp_high",five_temp_high);
                map.put("five_text",five_text);
                map.put("five_code",five_code);

                temp=Double.parseDouble(six_temp_low);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                six_temp_low=temp+"";
                temp=Double.parseDouble(six_temp_high);
                temp=((temp-32)*5)/9;
                temp=temp*100;value=temp-floor(temp);temp=temp-value;temp=temp/100;
                six_temp_high=temp+"";
                map.put("six_day",six_day);
                map.put("six_temp_low",six_temp_low);
                map.put("six_temp_high",six_temp_high);
                map.put("six_text",six_text);
                map.put("six_code",six_code);

                //object is ready. Now all I need to do is put it to the firebase
                FirebaseAuth mauth=FirebaseAuth.getInstance();
                String Uid=mauth.getUid();
                firebaseFirestore = FirebaseFirestore.getInstance();

                firebaseFirestore.collection(Uid).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });


            } catch(JSONException e){
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader input = null;
            StringBuffer buffer;
            String Url = strings[0];
            try {
                URL url = new URL(Url);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int response = connection.getResponseCode();

                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                buffer = new StringBuffer();
                String line = "";
                while ((line = input.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    if (input != null)
                        input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }





}
