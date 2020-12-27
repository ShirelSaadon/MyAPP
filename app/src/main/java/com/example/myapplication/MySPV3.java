package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


public class MySPV3 {

    private int count;
    public interface KEYS {
        public static final String KEY_USER_USER_NAME = "KEY_USER_USER_NAME";
        public static final String KEY_USER_THEME = "KEY_USER_THEME";
        public static final String TOP_TEN_LATITUDE = "TOP_TEN_LATITUDE";
        public static final String TOP_TEN_LONGITUDS = "TOP_TEN_LONGITUDS";



    }
    private static MySPV3 instance;
    private SharedPreferences prefs;

    public static MySPV3 getInstance() {

        return instance;
    }

    public MySPV3(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences("MY_SP", Context.MODE_PRIVATE);
        count = prefs.getAll().size();
    }
    public void counter(){
        setCount(getCount()+4);
    }

    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MySPV3(context);
        }
    }

//    public static void init(Fragment_Map context) {
//        if (instance == null) {
//            instance = new MySPV3(context);
//        }
//    }


    //// ---------------------------------------------------------- ////

    public void putlatitude(String key, double latitude) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, Double.doubleToRawLongBits(latitude));
        editor.apply();
    }
    public void putlongituds(String key, double longituds) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, Double.doubleToRawLongBits(longituds));
        editor.apply();


    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();

    }


    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public double getlatitude(String key,int def){
        return Double.longBitsToDouble(prefs.getLong(key, def));}

    public double getlongituds(String key,int def){
        return Double.longBitsToDouble(prefs.getLong(key, def));}








    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }
    public int getInt(String key ,int def){
        return prefs.getInt(key,def);

    }


    public void removeKey(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).apply();

    }
    public void clearCache(){
        SharedPreferences.Editor editor = prefs.edit();
        setCount(0);
        editor.clear();
        editor.commit();
    }









}