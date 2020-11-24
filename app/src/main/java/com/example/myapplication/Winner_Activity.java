package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Winner_Activity extends AppCompatActivity {
    private TextView winner_LBL;
    public static final String EXTRA_COUNT = "EXTRA_COUNT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        winner_LBL = findViewById(R.id.winPlayer_LBL);
        String count = getIntent().getStringExtra(EXTRA_COUNT);
        winner_LBL.setText("" + count);


    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}