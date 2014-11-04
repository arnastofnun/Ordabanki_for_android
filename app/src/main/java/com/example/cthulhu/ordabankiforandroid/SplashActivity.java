package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import org.json.JSONException;

//Basically just waits for two secons and then starts the next activity

/**
 * <h1>Splash screen</h1>
 * <p>This class is supposed to handle things that need
 * to be hidden in the splash screen</p>
 * <p>It should get the Locale settings and select language based on them</p>
 * <p>It should start the select language screen if language has not been selected before</p>
 * <p>It should start the SearchScreen if language has already been selected</p>
 * <p></p>
 * ------------------------------------------------------------------------------------------
 * @author Karl Ásgeir Geirsson edited 3/11/14 by Bill to implement languages and dictionaries
 */
public class SplashActivity extends Activity implements OnDictionariesObtainedListener, OnLanguagesObtainedListener {

    public String[][] localisedLangs;
    public String[][] localisedDicts;
    private boolean dObtained;
    private boolean lObtained;
    private boolean error;
    long startTime;
    DictionaryJsonHandler dJsonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart(){
        super.onStart();
        startTime = System.currentTimeMillis();
        isLocaleSet();
        dObtained = false;
        lObtained = false;
        error =false;
        getLocalisedLangs();
        getLocalisedDicts();
        checkTiming();
    }

    private void isLocaleSet(){
        final LocaleSettings localeSettings = new LocaleSettings(this);
        //if no language set in locale go to select language
        if (!localeSettings.getLocaleStatus()) {
            Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

    }
    @Override
    public void onDictionariesObtained (Dictionary[]dictionaries){
        localisedDicts = new String[dictionaries.length][2];
        int index = 0;
        //Toast.makeText(getApplicationContext(), "dLoop", Toast.LENGTH_SHORT).show();
        for (Dictionary dict : dictionaries) {
            localisedDicts[index][0] = dict.getDictCode();
            localisedDicts[index][1] = dict.getDictName();
            index++;
        }
        dObtained=true;
    }
    @Override
    public void onDictionariesFailure (int statusCode){
        error = true;
        Toast.makeText(getApplicationContext(), "dictionary error", Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }
    @Override
    public void onLanguagesObtained (Language[]languages){
        localisedLangs = new String[languages.length][2];
        int index = 0;
        for (Language lang : languages) {
            localisedLangs[index][0] = lang.getLangCode();
            localisedLangs[index][1] = lang.getLangName();
            index++;
        }
        lObtained=true;
    }
    @Override
    public void onLanguagesFailure (int statusCode){
        error= true;
        Toast.makeText(getApplicationContext(), "languages error", Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }

    private void getLocalisedLangs(){
        //calls rest client to populate languages array
        final String langURL = "http://api.arnastofnun.is/ordabanki.php?list=dicts&agent=ordabankaapp";
        LanguageJsonHandler lJsonHandler = new LanguageJsonHandler(this);
        LanguageRestClientUsage langClient = new LanguageRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting languages", Toast.LENGTH_SHORT).show();
            langClient.getLanguages(langURL, lJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getLocalisedDicts(){
        //calls rest client to populate dictionaries array
        final String dictURL = "http://api.arnastofnun.is/ordabanki.php?list=langs&agent=ordabankaapp";
        dJsonHandler = new DictionaryJsonHandler(this);
        DictionaryRestClientUsage dictClient = new DictionaryRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting dictionaries", Toast.LENGTH_SHORT).show();
            dictClient.getDictionaries(dictURL, dJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void checkTiming(){
        //checks if splash screen has been up for a minimum of 2 seconds then moves to search screen
        final LocaleSettings localeSettings = new LocaleSettings(this);
        Runnable runnable = new Runnable() {
            public void run() {
                long endTime = startTime + 2000;
                while (!error) {
                    if (dObtained && lObtained) {
                        long delay = endTime-System.currentTimeMillis();
                        if (delay>0) {
                            Handler timer = new Handler();
                            timer.postDelayed(new Runnable() {
                                public void run() {
                                    localeSettings.setLanguageFromPref(SearchScreen.class);

                                }
                            }, delay);
                        }
                        else{localeSettings.setLanguageFromPref(SearchScreen.class);}
                    }
                }
                //error
            }
        };
        Thread timingThread = new Thread(runnable);
        timingThread.start();

    }


}

