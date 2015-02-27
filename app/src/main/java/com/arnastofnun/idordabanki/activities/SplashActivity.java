package com.arnastofnun.idordabanki.activities;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;

import com.arnastofnun.idordabanki.ConnectionDetector;
import com.arnastofnun.idordabanki.ConnectionDialogueFragment;
import com.arnastofnun.idordabanki.Dictionary;
import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.Glossary;
import com.arnastofnun.idordabanki.Language;
import com.arnastofnun.idordabanki.LocaleSettings;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.SearchScreen;
import com.arnastofnun.idordabanki.interfaces.OnDictionariesObtainedListener;
import com.arnastofnun.idordabanki.interfaces.OnLanguagesObtainedListener;
import com.arnastofnun.idordabanki.jsonHandlers.DictionaryJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.LanguageJsonHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Basically just waits for two seconds and then starts the next activity

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
public class SplashActivity extends FragmentActivity implements OnDictionariesObtainedListener, OnLanguagesObtainedListener, ConnectionDialogueFragment.ConnectionDialogueListener{

    /**
     * localisedLangs is a list of strings that are names of languages
     */
    public ArrayList<ArrayList<String>> localisedLangs;
    /**
     * localisedLangs is list of strings that are dictionary names
     */    
    public ArrayList<ArrayList<String>> localisedDicts;
    /**
     * localisedLangs is a list of glossaries
     */
    public ArrayList<Glossary> glossaries;
    /**
     * dObtained is true if dictionary values have been obtained
     */
    private boolean dObtained;
    /**
     * lObtained is true if language values have been obtained
     */
    private boolean lObtained;
    /**
     * error is true if an error appears while the app is running
     */
    private boolean error;
    /**
     * startTime is the current time im milliseconds
     */
    long startTime;
    /**
     * dJsonHandler is handles json files that contain dictionary values
     */
    DictionaryJsonHandler dJsonHandler;
    /**
     * when this activity is started a logo is displayed while initialization takes place
     * @param savedInstanceState the saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Starts the timer
        startTime = System.currentTimeMillis();
        boolean connected = confirmConnection();
        if (connected) {
            //check if locale is set
            isLocaleSet();

            //Initialize values values
            dObtained = false;
            lObtained = false;
            error =false;

            //Get the languages
            getLocalisedLangs();
            //Get the dictionaries
            getLocalisedDicts();


            checkTiming();

        }
    }

    /**
     * Written by Karl Ásgeir Geirsson
     * Checks if locale is manually set or if this is the first startup of the app
     * Post: If locale is set it does nothing
     *       If locale is not set it starts the select language activity
     */
    private boolean confirmConnection(){

            boolean connected = checkConnection();
            if(!connected){
                DialogFragment restartQuit = new ConnectionDialogueFragment();
                restartQuit.show(getFragmentManager(), "NoInternetConnection");
            }
        return connected;
    }
    public boolean checkConnection(){
        //check for internet connection
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        return cd.isConnectingToInternet();
    }
    private void retry(){
        finish();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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

    /**
     * Run when dictionaries have been obtained
     * Written by Bill and Karl
     * @param dictionaries the localized dictionaries
     * post: glossaries have been set
     *       glossaries have been sorted in alphabetical order
     *       localisedDicts has been set
     *       dObtained = true
     */
    @Override
    public void onDictionariesObtained (Dictionary[]dictionaries){
        //Create new glossaries and localizedDicts array lists
        glossaries = new ArrayList<Glossary>();
        localisedDicts = new ArrayList<ArrayList<String>>();
        int index = 0;
        //Variables for the for loop
        Glossary glossary;
        ArrayList<String> codeList = new ArrayList<String>();
        ArrayList<String> dictList = new ArrayList<String>();
        //Toast.makeText(getApplicationContext(), "dLoop", Toast.LENGTH_SHORT).show();
        /*
            For each dictionary class add its code
            to the codeList, its name to the dictList and
            creates a new instance of the Glossary class with the dict code and name,
            which is added to the glossaries array list
         */
        for (Dictionary dict : dictionaries) {
            if(dict.getDictName() != null && !dict.getDictName().isEmpty()) {
                codeList.add(index, dict.getDictCode());
                dictList.add(index, dict.getDictName());
                glossary = new Glossary(dict.getDictCode(), dict.getDictName());
                glossaries.add(index, glossary);
                index++;
            }
        }

        //Add the code list to the 0 index
        localisedDicts.add(0, codeList);
        //Add the list of dictionary names to the 1 index
        localisedDicts.add(1, dictList);
        //Sort the glossaries in alphabetical order
        Collections.sort(glossaries);
        dObtained=true;
    }

    /**
     * Written by Bill
     * Handles error if we can't get dictionaries
     * @param statusCode the status code of the error
     */
    @Override
    public void onDictionariesFailure (int statusCode){
        error = true;
        //checkConnection();
        //Toast.makeText(getApplicationContext(), "dictionary error", Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }

    /**
     * Written by Bill and Karl
     * Runs when languages are obtained
     * @param languages the obtained languages
     */
    @Override
    public void onLanguagesObtained (Language[]languages){
        //Initialize array list
        localisedLangs = new ArrayList<ArrayList<String>>();
        //We start on index 2 to save space for the Icelandic and English
        int index = 2;
        //Initialize the code and name list and add 0 and 1 index to them
        ArrayList<String> codeList = new ArrayList<String>();
        codeList.add(0,null);
        codeList.add(1,null);
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add(0,null);
        nameList.add(1,null);
        //Convert the languages array to a list
        List<Language> langarray = Arrays.asList(languages);
        //Sort the languages array
        Collections.sort(langarray);
        /**
         *   For each language put the language code into the codeList
         *   put the language name into the nameList
         *   Put Icelandic first and English second
         */
        for (Language lang : langarray) {
            if(lang.getLangCode().equals("IS")){
                codeList.set(0,lang.getLangCode());
                nameList.set(0,lang.getLangName());
            }
            else if(lang.getLangCode().equals("EN")){
                codeList.set(1,lang.getLangCode());
                nameList.set(1,lang.getLangName());
            }
            else {
                codeList.add(index, lang.getLangCode());
                nameList.add(index, lang.getLangName());
                index++;
            }

        }
        //Add the codes list to index 0
        localisedLangs.add(0,codeList);
        //Add the names list to index 1
        localisedLangs.add(1,nameList);

        lObtained=true;
    }

    /**
     * Written by Bill
     * Handles errors if we can't get the languages
     * @param statusCode the status code of the error
     */
    @Override
    public void onLanguagesFailure (int statusCode){
        error= true;
        //checkConnection();
        //Toast.makeText(getApplicationContext(), "Database Error: Status Code "+ statusCode, Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }


    /**
     * Written by Bill
     * use: getLocalisedLangs()
     * post: Request sent to get localised languages
     */
    private void getLocalisedLangs(){
        //calls rest client to populate languages array
        final String langURL = "http://api.arnastofnun.is/ordabanki.php?list=langs&agent=ordabankaapp";
        LanguageJsonHandler lJsonHandler = new LanguageJsonHandler(this);
        OrdabankiRestClientUsage langClient = new OrdabankiRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting languages", Toast.LENGTH_SHORT).show();
            langClient.getLanguages(langURL, lJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Written by Bill
     * use: getLocalisedDicts()
     * post: Request sent to get localised dictionaries
     */
    private void getLocalisedDicts(){
        //calls rest client to populate dictionaries array
        final String dictURL = "http://api.arnastofnun.is/ordabanki.php?list=dicts&agent=ordabankaapp";
        dJsonHandler = new DictionaryJsonHandler(this);
        OrdabankiRestClientUsage dictClient = new OrdabankiRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting dictionaries", Toast.LENGTH_SHORT).show();
            dictClient.getDictionaries(dictURL, dJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * A class that checks how long the screen has been running
     * It runs for the time that it takes to get the glossaries and languages or for two seconds
     * whichever is faster
     * Written by Bill and Karl
     * post: If dictionaries and languages were obtained
     *          Globals for languages, glossaries, and localiseddictionaries have been set
     *          The search screen has been opened in the correct language
     *       else it crashes
     * //TODO Handle dictionary and languages errors
     */
    private void checkTiming(){
        //Get the globals
        final Globals globals = (Globals) getApplicationContext();
        //Create a new runnable to run in a new thread
        Runnable runnable = new Runnable() {
            /**
             * Written by Bill and Karl
             * Checks if languages and dictionaries have been obtained
             * if so it sets them
             */
            public void run() {
                Looper.prepare();
                //Decide end time
                long endTime = startTime + 2000;
                long delay = 0;
                //While we don't get an error
                while (!error) {
                    //If dictionaries and languages are obtained
                    if (dObtained && lObtained) {
                        //We calculate the remaining delay
                        long now = System.currentTimeMillis();
                        if (now < endTime) {
                            delay = endTime - now;
                        }
                        break;
                    }
                }

                //Set globals
                globals.setLanguages(localisedLangs);
                globals.setDictionaries(glossaries);
                globals.setLocalizedDictionaries(localisedDicts);
                //Create a new handler to run after the delay in the main thread
                Handler mainHandler = new Handler(SplashActivity.this.getMainLooper());
                mainHandler.postDelayed(new Runnable() {
                    /**
                     * Written by Karl Ásgeir Geirsson
                     * post: The search screen has been opened in the correct language
                     */
                    @Override
                    public void run() {
                        LocaleSettings localeSettings = new LocaleSettings(SplashActivity.this);
                        localeSettings.setLanguageFromPref(SearchScreen.class);
                    }
                }, delay);
            }
        };
        //Start a new thread with the runnable
        Thread timingThread = new Thread(runnable);
        timingThread.start();

    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        retry();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finish();
    }


}

