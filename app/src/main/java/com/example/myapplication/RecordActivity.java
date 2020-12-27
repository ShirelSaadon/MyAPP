package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Objects.Records;
import Objects.TopTen;

public class RecordActivity extends AppCompatActivity {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;
    private static final String TAG = "arr";
    private ArrayList <Records> topTen= new ArrayList<>();

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        String str = getIntent().getStringExtra("arr");
        Log.d(TAG, "onCreate: " + str);
        Gson gson= new Gson();
        Type scoreType = new TypeToken<ArrayList<Records>>() {
        }.getType();
        ArrayList <Records> r =gson.fromJson(str,scoreType);
        Log.d(TAG, "onCreate: "+r.toString());
        fragment_list = new Fragment_List(r);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_LAY_list, fragment_list);
        ft.commit();
        fragment_map = new Fragment_Map(r);
        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.main_LAY_map, fragment_map);
        ft2.commit();






    }
















}
