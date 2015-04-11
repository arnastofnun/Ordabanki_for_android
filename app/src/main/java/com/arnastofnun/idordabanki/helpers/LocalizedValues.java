package com.arnastofnun.idordabanki.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.arnastofnun.idordabanki.activities.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.arnastofnun.idordabanki.models.Dictionary;
import com.arnastofnun.idordabanki.models.Glossary;
import com.arnastofnun.idordabanki.models.Language;
import com.arnastofnun.idordabanki.preferences.LocaleSettings;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class was created to help with getting localized
 * values from the api and setting them in globals
 * Created by karlasgeir on 3/24/15.
 */
public class LocalizedValues extends AsyncTask<Void,Integer,Boolean> {
    private boolean error = false;
    private boolean dObtained = false;
    private boolean lObtained = false;
    private SplashActivity activity;
    /**
     * localisedLangs is a list of strings that are names of languages
     */
    public BiMap<String,String> localisedLangs;
    /**
     * localisedLangs is list of strings that are dictionary names
     */
    public BiMap<String,String> localisedDicts;

    /**
     * glossaries is a list of glossaries
     */
    public ArrayList<Glossary> glossaries;

    /**
     * Simple constructor
     * @param activity - the calling activity
     */
    public LocalizedValues(SplashActivity activity){
        this.activity = activity;
    }

    /**
     * A method that runs in another thread, not blocking the UI
     * @param voids - void
     * @return true
     */
    @Override
    protected Boolean doInBackground(Void ... voids){
        //While we don't get an error
        while (!error) {
            //If dictionaries and languages are obtained
            if (dObtained && lObtained) {
                break;
            }
        }
        return true;
    }

    /**
     * A method that is executed before
     * the background proces begins
     */
    @Override
    protected void onPreExecute(){
        //Initialize values values
        dObtained = false;
        lObtained = false;
        error =false;

        activity.getLocalisedValues();
    }

    /**
     * A process that updates the progress
     * of the background progress
     * @param progress the progress
     */
    protected void onProgressUpdate(Integer... progress) {

    }

    /**
     * A method that executes after the background process
     * is done
     * @param bool true
     */
    @Override
    protected void onPostExecute(Boolean bool) {
        //Set globals
        SharedPrefs.getEditor().remove("glossary_state");
        SharedPrefs.putStringBiMap("languages",localisedLangs);
        SharedPrefs.putParcelableArray("dictionaries", glossaries);
        SharedPrefs.putStringBiMap("loc_dictionaries",localisedDicts);
        LocaleSettings localeSettings = new LocaleSettings(activity);
        localeSettings.setLanguageFromPref(SearchScreen.class);
    }

    /**
     * a method that takes in error codes
     * @param statusCode
     */
    public void throwError(int statusCode){
        this.error = true;
    }

    /**
     * A method that is called when dictionaries are
     * obtained
     * @param dictionaries - the dictionaries
     */
    public void dictsObtained(Dictionary[]dictionaries){
        parseGlossaries(dictionaries);
        this.dObtained = true;
    }

    /**
     * A method that is called when languages are obtained
     * @param languages - the languages
     */
    public void langsObtained(Language[]languages){
        parseLanguages(languages);
        this.lObtained = true;
    }

    /**
     * A method to parse a list of dictionaries, and
     * save them to shared preferences
     * @param dictionaries - the dictionaries
     */
    private void parseGlossaries(Dictionary[]dictionaries) {
        //Create new glossaries and localizedDicts array lists
        glossaries = new ArrayList<>();
        localisedDicts = HashBiMap.create();
        int index = 0;
        //Variables for the for loop
        Glossary glossary;
            /*
                For each dictionary class add its code
                to the codeList, its name to the dictList and
                creates a new instance of the Glossary class with the dict code and name,
                which is added to the glossaries array list
             */
        for (Dictionary dict : dictionaries) {
            if (dict.getDictName() != null && !dict.getDictName().isEmpty() && !localisedDicts.containsKey(dict.getDictCode()) && !localisedDicts.inverse().containsKey(dict.getDictName())) {
                localisedDicts.put(dict.getDictCode(), dict.getDictName());
                glossary = new Glossary(dict.getDictCode(), dict.getDictName());
                glossaries.add(index, glossary);
                index++;
            }
        }
        //Sort the glossaries in alphabetical order
        Collections.sort(glossaries);
    }

    /**
     * A method to parse the languages and save them to dictionaries
     * @param languages - the languages
     */
    private void parseLanguages(Language[]languages){
        localisedLangs = HashBiMap.create();
        //Put the language code and name into the BiMap
        for (Language lang : languages) {
            localisedLangs.put(lang.getLangCode(),lang.getLangName());
        }
    }
}
