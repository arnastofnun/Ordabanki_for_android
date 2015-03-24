package com.arnastofnun.idordabanki.activities;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.arnastofnun.idordabanki.helpers.LocalizedValues;
import com.arnastofnun.idordabanki.sync.ConnectionDetector;
import com.arnastofnun.idordabanki.dialogs.ConnectionDialogueFragment;
import com.arnastofnun.idordabanki.models.Dictionary;
import com.arnastofnun.idordabanki.models.Language;
import com.arnastofnun.idordabanki.preferences.LocaleSettings;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.sync.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.arnastofnun.idordabanki.interfaces.OnDictionariesObtainedListener;
import com.arnastofnun.idordabanki.interfaces.OnLanguagesObtainedListener;
import com.arnastofnun.idordabanki.sync.jsonHandlers.DictionaryJsonHandler;
import com.arnastofnun.idordabanki.sync.jsonHandlers.LanguageJsonHandler;

import org.json.JSONException;


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
     * startTime is the current time im milliseconds
     */
    long startTime;
    private LocalizedValues localizedValues;
    private ConnectionDetector connectionDetector;
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
        ThemeHelper.setCurrentNoActionBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        connectionDetector = new ConnectionDetector(this);
        //Starts the timer
        startTime = System.currentTimeMillis();
        boolean connected = connectionDetector.confirmConnection();
        if (connected) {
            initialize();
        }
    }

    /**
     * A method to start the functions
     */
    private void initialize(){
        //check if locale is set
        isLocaleSet();
        //Get the localized languages and dictionaries
        localizedValues = new LocalizedValues(this);
        localizedValues.execute();
    }


    /**
     * A method to check if locale is set
     */
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
        localizedValues.dictsObtained(dictionaries);
    }

    /**
     * Written by Bill
     * Handles error if we can't get dictionaries
     * @param statusCode the status code of the error
     */
    @Override
    public void onDictionariesFailure (int statusCode){
        localizedValues.throwError(statusCode);
    }

    /**
     * Written by Bill and Karl
     * Runs when languages are obtained
     * @param languages the obtained languages
     */
    @Override
    public void onLanguagesObtained (Language[]languages){
        localizedValues.langsObtained(languages);
    }

    /**
     * Written by Bill
     * Handles errors if we can't get the languages
     * @param statusCode the status code of the error
     */
    @Override
    public void onLanguagesFailure (int statusCode){
        localizedValues.throwError(statusCode);
    }

    /**
     * A method that starts getting the
     * localised values
     */
    public void getLocalisedValues(){
        this.getLocalisedDicts();
        this.getLocalisedLangs();
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
     * A method that's run when the connection
     * dialog positive button is clicked
     * @param dialog the dialog
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        connectionDetector.retry();
    }

    /**
     * A method that's run when the connection
     * dialog negative button is clicked
     * @param dialog the dialog
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finish();
    }


}

