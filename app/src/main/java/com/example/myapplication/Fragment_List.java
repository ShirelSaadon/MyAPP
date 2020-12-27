package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import Objects.Records;
import Objects.TopTen;

public class Fragment_List extends Fragment {
    private static final String TAG = "sss";
    private TextView list_LBL_name;
    private ArrayList<Records> rec ;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,container,false);
        findViews(view);
        PrintTopTen(rec);
        return view;
    }



    public Fragment_List(ArrayList<Records> rec) {

        this.rec =rec;
    }


    private void findViews(View view) {

        list_LBL_name=view.findViewById(R.id.listfragment_LBL_TopTen);
    }

    public void PrintTopTen(ArrayList<Records> tt) {

        if ( tt.isEmpty()){
            list_LBL_name.setText("TOP 1O IS EMPTY!");
        }
        int j=0;
        for (Records rec : tt) {
            j++;
            list_LBL_name.setText(list_LBL_name.getText()+" "+ (j)+"."+rec.getName()+ " "+ rec.getScore()+ " \n" + "["+rec.getLocation().getLat() +" ," + rec.getLocation().getLon()+ " ]" + "\n");
            //Log.d(TAG, "sort :" + rec.getName() + rec.getScore() +  rec.getLocation()+"\n");
        }


    }


}
