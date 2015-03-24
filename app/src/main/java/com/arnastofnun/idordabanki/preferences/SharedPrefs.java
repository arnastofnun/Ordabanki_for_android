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

    /**
     * A method that checks if shared preferences contain
     * a key
     * @param key - the key
     * @return - true if it contains the key, else false
     */
    public static boolean contains(String key){
        return sharedPrefs.contains(key);
    }

    /**
     * A method to get the string from a key
     * @param key - the key
     * @return - the string corresponding to the key
     */
    public static String getString(String key){
        return sharedPrefs.getString(key, null);
    }

    /**
     * A method to get the int from a key
     * @param key - the key
     * @return - the integer corresponding to the key
     */
    public static int getInt(String key){
        return sharedPrefs.getInt(key, 0);
    }

    /**
     * A method to get a parceable array from a key
     * @param key  the key
     * @param type the type of array list
     * @param <T> the type of Parcelable
     * @return an array list of a parcelable object
     */
    public static <T extends Parcelable> ArrayList<T> getParcelableArray(String key,Type type){
        String json = sharedPrefs.getString(key, null);
        return new Gson().fromJson(json, type);
    }

    /**
     * A method to get a biMap of strings from shared preferences
     * @param key the key
     * @return a biMap of strings corresponding to the key
     */
    public static BiMap<String,String> getStringBiMap(String key){
        String json = sharedPrefs.getString(key, null);
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        HashMap<String,String> hashMap = new Gson().fromJson(json,type);
        if(hashMap != null) {
            return HashBiMap.create(hashMap);
        }
        return null;
    }

    /**
     * @param key the key
     * @param value the value
     */
    public static void putString(String key,String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * @param key the key
     * @param value the value
     */
    public static void putInt(String key,int value){
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * @param key - the key
     * @param parcelableArrayList the array list of parcelable objects
     */
    public static void putParcelableArray(String key,ArrayList<? extends Parcelable> parcelableArrayList){
        String json = new Gson().toJson(parcelableArrayList);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * @param key the key
     * @param biMap the biMap of strings
     */
    public static void putStringBiMap(String key, BiMap<String,String> biMap){
        String json = new Gson().toJson(biMap);
        editor.putString(key,json);
        editor.apply();
    }

    /**
     * @return the shared preferences editor
     */
    public static SharedPreferences.Editor getEditor(){
        return editor;
    }

    /**
     * @return the shared preferences
     */
    public static SharedPreferences getSharedPrefs(){
        return sharedPrefs;
    }
}
