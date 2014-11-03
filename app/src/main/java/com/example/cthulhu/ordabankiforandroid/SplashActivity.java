package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import org.json.JSONException;

//Basically just waits for two secons and then starts the next activity

/**
 * <h1>Splash screen</h1>
 * <p>This class is supposed to handle things that need
 * to be hidden in the splash screen</p>
 * <p>It should get the Locale settings and select language based on them</p>
 * <p>It should start the select language screen if language has not been selected before</p>
 * <p>It should start the SearchScreen if language has already been selected</p>
 * <p>For now it just waits and then starts the select language screen</p>
 * ------------------------------------------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 */
public class SplashActivity extends Activity implements OnDictionariesObtainedListener, OnLanguagesObtainedListener {
    /**
     * Duration of wait *
     */
    private final long MIN_SPLASH_DISPLAY_LENGTH = 2000;
    LanguageJsonHandler lJsonHandler;
    DictionaryJsonHandler dJsonHandler;
    final String langURL = "http://api.arnastofnun.is/ordabanki.php?list=dicts&agent=ordabankaapp";
    final String dictURL = "http://api.arnastofnun.is/ordabanki.php?list=langs&agent=ordabankaapp";
    String[][] localisedLangs;
    String[][] localisedDicts;
    boolean dObtained = false;
    boolean lObtained = false;
    boolean error =false;
    final LocaleSettings localeSettings = new LocaleSettings(this);
    long startTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!localeSettings.getLocaleStatus()) {

            Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();

        }
        setContentView(R.layout.activity_splash);

        lJsonHandler = new LanguageJsonHandler(this);
        dJsonHandler = new DictionaryJsonHandler(this);

        OrdabankiRestClientUsage langClient = new OrdabankiRestClientUsage();
        try {
            langClient.getLanguages(langURL, lJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OrdabankiRestClientUsage dictClient = new OrdabankiRestClientUsage();
        try {
            dictClient.getDictionaries(dictURL, dJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Runnable runnable = new Runnable() {
            public void run() {
                long endTime = startTime+MIN_SPLASH_DISPLAY_LENGTH;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            Thread.sleep(endTime-System.currentTimeMillis());
                        } catch (Exception e) {e.printStackTrace();}
                    }
                }
                localeSettings.setLanguageFromPref(SearchScreen.class);
            }
        };
        Thread timingThread = new Thread(runnable);

        while(!error){
            if(dObtained&&lObtained){
                timingThread.start();
            }
        }


    }
    @Override
    public void onDictionariesObtained (Dictionary[]dictionaries){
        localisedDicts = new String[dictionaries.length][2];
        int index = 0;
        for (Dictionary dict : dictionaries) {
            localisedDicts[index][1] = dict.getDictCode();
            localisedDicts[index][2] = dict.getDictName();
            index++;
        }
        dObtained=true;
    }
    @Override
    public void onDictionariesFailure ( int statusCode){
        error = true;
        //todo handle failure: error message, restart quit options
    }
    @Override
    public void onLanguagesObtained (Language[]languages){
        localisedLangs = new String[languages.length][2];
        int index = 0;
        for (Language lang : languages) {
            localisedDicts[index][1] = lang.getLangCode();
            localisedDicts[index][2] = lang.getLangName();
            index++;
        }
        lObtained=true;
    }
    @Override
    public void onLanguagesFailure ( int statusCode){
       error= true;
        //todo handle failure: error message, restart quit options
    }


}

