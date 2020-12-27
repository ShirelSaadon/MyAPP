package com.example.myapplication;

//



import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.*;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import Objects.Records;
import Objects.TopTen;

public class MainActivity extends AppCompatActivity {

    MediaPlayer ring;
    private static final String TAG = "sss";
    private ImageView curCard1;
    private ImageView curCard2;
    private Card card1;
    private Card card2;

    private TextView num_of_wins_player1;
    private TextView num_of_wins_player2;
    private Deck deck = new Deck();

    private int player1_count_wins = 0;
    private int player2_count_wins = 0;
    private int winner;


     ProgressBar progressBar;
     int progressStatus = 0;
     private Handler handlerBar = new Handler();



    private ImageButton play_BTN;
    TopTen topTen;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:creating ");
        curCard1 = findViewById(R.id.IMG_card1);
        curCard2 = findViewById(R.id.IMG_card2);
        num_of_wins_player1 = findViewById(R.id.game_LBL_leftScore);
        num_of_wins_player2 = findViewById(R.id.game_LBL_rightScore);
        ring= MediaPlayer.create(MainActivity.this,R.raw.bensoundukulele);
        fetchLsatLocation();
        startTimer();

    }




    //game bar progress
    private void startProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            public void run() {
                while (isForeground) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handlerBar.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    private void  updateWinner() {
        Records r = winner1();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d(TAG, "winner :" + r.getName() + r.getScore());
        Log.d(TAG, "count :" + MySPV3.getInstance().getCount() + "");
        if (currentLocation != null) {
            //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            MyLocation temp = new MyLocation();
            temp.setLat(currentLocation.getLatitude());
            temp.setLon(currentLocation.getLongitude());
            r.setLocation(temp);
            Log.d(TAG, "location :" + currentLocation.toString() );
        }

        else{
            MyLocation temp =new MyLocation();
            temp.setLat(0d);
            temp.setLon(0d);
            r.setLocation(temp);
            Log.d(TAG, "location is null :" + r.getLocation().getLon() + " ," + r.getLocation().getLat());
        }

        MySPV3.getInstance().putString(MySPV3.KEYS.KEY_USER_USER_NAME + MySPV3.getInstance().getCount() / 4, r.getName());
        MySPV3.getInstance().putInt(MySPV3.KEYS.KEY_USER_THEME + MySPV3.getInstance().getCount() / 4, r.getScore());
        MySPV3.getInstance().putlatitude(MySPV3.KEYS.TOP_TEN_LATITUDE + ((MySPV3.getInstance().getCount()) / 4),r.getLocation().getLat());
        MySPV3.getInstance().putlongituds(MySPV3.KEYS.TOP_TEN_LONGITUDS + ((MySPV3.getInstance().getCount()) / 4) ,r.getLocation().getLon());
        Log.d(TAG, "key: " + MySPV3.KEYS.KEY_USER_USER_NAME + MySPV3.getInstance().getCount() / 4);



        topTen = generateMokeData();
        Gson gson = new Gson();
        String ttJson = gson.toJson(topTen);
        TopTen tt2 = gson.fromJson(ttJson, TopTen.class);
        upDateTopTen(tt2);
        MySPV3.getInstance().counter();
        recordActivity1(tt2.getRecords());


    }


    private void upDateTopTen(TopTen tt2) {
        Log.d(TAG, "checkTopTen: ");

        if (tt2.getRecords().size()>10){
            Collections.sort(tt2.getRecords());
            tt2.getRecords().subList(9, MySPV3.getInstance().getCount()/4).clear();
        }
        else {
            Collections.sort(tt2.getRecords());
        }

    }


    public static TopTen generateMokeData() {
        TopTen topTen = new TopTen();
        for(int i=0; i<=MySPV3.getInstance().getCount()/4 ;i++) {
            Records r = new Records();
            r.setName(MySPV3.getInstance().getString(MySPV3.KEYS.KEY_USER_USER_NAME+i, "NONE"));
            r.setScore(MySPV3.getInstance().getInt(MySPV3.KEYS.KEY_USER_THEME+i, -1));
            MyLocation temp = new MyLocation();
            temp.setLat(MySPV3.getInstance().getlatitude(MySPV3.KEYS.TOP_TEN_LATITUDE+i,-1));
            temp.setLon(MySPV3.getInstance().getlongituds(MySPV3.KEYS.TOP_TEN_LONGITUDS+i,-1));
            r.setLocation(temp);
            Log.d(TAG, r.toString());

            topTen.getRecords().add(r);
        }

            return topTen;

    }

    public void recordActivity1(ArrayList<Records> rec)
    {
        Log.d(TAG, "recordActivity1: ");

        Intent i= new Intent(this,RecordActivity.class);
        Gson gson = new Gson();
        String str = gson.toJson(rec);
        i.putExtra("arr",str);
        startActivity(i);
        finish();
        


    }


    public Records winner1()

    {
           Records records = new Records();
            if(player1_count_wins>player2_count_wins) {
                records.setName("Player1");
                records.setScore(player1_count_wins);
            }
            else if(player1_count_wins<player2_count_wins){
                records.setName("Player2");
                records.setScore(player2_count_wins);
            }
            else {//defaulf -player one winner
                records.setName("Player1");
                records.setScore(player1_count_wins);
            }

            return records;

    }









    boolean isForeground=true;

    final Handler handler= new Handler();


    private void startTimer(){
        isForeground=true;
        final int delay =1000;
        startProgressBar();
        handler.postDelayed(new Runnable() {

            public void run() {
                handler.postDelayed(this, delay);
                if(deck.getCards().isEmpty()){
                    handler.removeCallbacks( this);
                    ring.stop();
                    isForeground=false;
                    updateWinner();
                }
                else
                    winner = startGame(deck.getCards());
                    setWinner();
            }
        },delay);
    }



    //ask for permission to get location
    private void fetchLsatLocation() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

    }
    //print text if didnt give permission to take the place
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else {
            Toast.makeText(getApplicationContext(),"Premission denied.",Toast.LENGTH_SHORT).show();
        }
    }
    //get the current location
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE) ;
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                currentLocation = location;
            }
        }else {
            Log.d(TAG, "getCurrentLocation: ");
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }






    public void setWinner(){
        if(winner==1) {
            player1_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
        }else if(winner==2) {
            player2_count_wins++;
            num_of_wins_player2.setText(" "+player2_count_wins);
        }else{
            player1_count_wins++;
            player2_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
            num_of_wins_player2.setText(" "+player2_count_wins);

        }
    }
    public int startGame(ArrayList<Card> deck){
        //get a random 2 cards from the deck that left while we have a cards in the deck
        //remove from the deck the card we already play .
        //the function return the number of the winner.
        //startProgressBar();

        ring.start();

        int winner;
        card1 = getRandomCard(deck);
        deck.remove(card1);
        card2 = getRandomCard(deck);
        deck.remove(card2);
        //set the random cards to the viewImage
        curCard1.setImageResource(card1.getCard_img_id());
        curCard2.setImageResource(card2.getCard_img_id());

        winner=compareCard(card1,card2);
        return winner;
    }
    public int compareCard(Card card1,Card card2){
        //compare between two cards by rank
        //return: 0 if equals,1 if card1 bigger,2 if card2 bigger
        if(card1.getRank()==card2.getRank())
            return 0;
        else if(card1.getRank().ordinal()>card2.getRank().ordinal())
            return 1;
        else
            return 2;
    }

    public Card getRandomCard(ArrayList<Card>curDeck){
        int rnd = new Random().nextInt(curDeck.size());
        return curDeck.get(rnd);
    }

    @Override
    protected void onStart() {
        Log.d("sss", "onStart");
        super.onStart();
    }
    @Override
    protected void onResume() {
        Log.d("sss", "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d("sss", "onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        Log.d("sss", "onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d("sss", "onDestory");
        super.onDestroy();
    }



}