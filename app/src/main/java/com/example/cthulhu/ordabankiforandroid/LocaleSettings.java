package com.example.cthulhu.ordabankiforandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * This class contains methods to get language locale
 * and set language locale
 * --------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 28.10.2014.
 */
public class LocaleSettings{
    /*
        data invariants
        ---------------
        sharedpref is the default shared preferences
        context is the current context
        status is true if there is a language in shared preferences, else false
        language contains the language in shared preferences, else null
     */
    SharedPreferences sharedpref;
    Context context;
    boolean status;
    static String language;

    /**
     * use: Locale settings localeSettings = new LocaleSettings(context)
     * pre:context is of type Context
     * post:A new LocaleSettings object has been created
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param context is the current context
     */
    public LocaleSettings(Context context) {
        super();
        //Set variables
        this.context = context;
        this.sharedpref = PreferenceManager.getDefaultSharedPreferences(context);
        this.status = sharedpref.contains("lang");
        this.language = getLanguageFromPref();
    }

    /**
     * use: String language = getLanguage()
     * pre: nothing
     * post: language is now the current language, or
     *       null if there is no language in shared preferences
     * -----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @return language, the current language in shared preferences, or null
     */
    public static String getLanguage(){
        return language;
    }


    /**
     * use: String language = getLanguageFromPref()
     * pre: nothing
     * post: language is now the current language, or
     *       null if there is no language in shared preferences
     * -----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @return language, the current language in shared preferences, or null
     */
    public String getLanguageFromPref(){
        //if language is set in shared prefs
        if(status){
            //get the language string
            return sharedpref.getString("lang","DEFAULT");
        }
        else{
            return null;
        }
    }

    /**
     * use: setLanguage(lang,class)
     * pre: lang is of type String, class is of type Class
     * post: the language has been set to lang, and the activity
     *       class has been started
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param lang is the language that should be set
     * @param cl is the activity that should be started
     */
    public void setLanguage(String lang,Class cl){
        //Edit the shared preferences
        SharedPreferences.Editor editor = sharedpref.edit();
        //Set lang as the language and apply changes
        editor.putString("lang",lang);
        editor.apply();
        //Set the locale settings and start the activity
        setLocale(lang,cl);
    }


    /**
     * use: setLanguageFromPref(class)
     * pre: class is of type Class
     * post: starts the activity cl if language is set in preferences, else returns false
     * ----------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param cl is the activity to start after language has been set
     * @return starts the activity cl if language is set in preferences, else returns false
     */
    public boolean setLanguageFromPref(Class cl){
        if(status){
            //set the language and start the activity
            setLocale(language,cl);
            return true;
        }
        else return false;
    }


    /**
     * use: getLocaleStatus()
     * pre: nothing
     * post: returns true if language is set in shared preferences, else false
     * ------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @return true if language is set in shared preferences, else false
     */
    public boolean getLocaleStatus(){
        return status;
    }


    public void setCurrLocaleFromPrefs(){
        String lang = getLanguageFromPref();
        Locale myLocale = new Locale(lang);
        //Get the configurations
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        //Set the locale settings to thew new locale
        conf.locale = myLocale;
        //update settings
        res.updateConfiguration(conf,dm);
    }

    /**
     * pre:lang is of type String
     * pre:cl is of type class
     * post:starts an activity in the language lang
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param lang is the language locale string ("en", "is" ...)
     * @param cl is the class for the activity that should start
     */
    private void setLocale(String lang, Class cl){
        //create new locale
        Locale myLocale = new Locale(lang);
        //Get the configurations
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        //Set the locale settings to thew new locale
        conf.locale = myLocale;
        //update settings
        res.updateConfiguration(conf,dm);
        //Start the intent
        Intent intent = new Intent(context,cl);
        context.startActivity(intent);
    }

}
