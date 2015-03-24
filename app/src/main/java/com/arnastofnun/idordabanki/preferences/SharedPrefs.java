package com.arnastofnun.idordabanki.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import com.arnastofnun.idordabanki.Globals;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A helper class to make access to shared preferences easier
 * @author karlasgeir
 * @since 3/24/15.
 */
public class SharedPrefs {
    private static SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
    //Edit the shared preferences
    private static SharedPreferences.Editor editor = sharedPrefs.edit();


    public static boolean contains(String key){
        return sharedPrefs.contains(key);
    }

    public static String getString(String key){
        return sharedPrefs.getString(key, null);
    }

    public static int getInt(String key){
        return sharedPrefs.getInt(key, 0);
    }

    public static <T extends Parcelable> ArrayList<T> getParcelableArray(String key,Type type){
        String json = sharedPrefs.getString(key, null);
        return new Gson().fromJson(json, type);
    }

    public static BiMap<String,String> getStringBiMap(String key){
        String json = sharedPrefs.getString(key, null);
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        HashMap<String,String> hashMap = new Gson().fromJson(json,type);
        if(hashMap != null) {
            return HashBiMap.create(hashMap);
        }
        return null;
    }

    public static void putString(String key,String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void putInt(String key,int value){
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putParcelableArray(String key,ArrayList<? extends Parcelable> parcelableArrayList){
        String json = new Gson().toJson(parcelableArrayList);
        editor.putString(key, json);
        editor.apply();
    }

    public static void putStringBiMap(String key, BiMap<String,String> biMap){
        String json = new Gson().toJson(biMap);
        editor.putString(key,json);
        editor.apply();
    }


    public static SharedPreferences.Editor getEditor(){
        return editor;
    }

    public static SharedPreferences getSharedPrefs(){
        return sharedPrefs;
    }
}
