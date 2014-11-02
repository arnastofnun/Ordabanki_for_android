package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;


public class AnActivity extends Activity implements OnDictionariesObtainedListener, OnLanguagesObtainedListener{
    LanguageJsonHandler lJsonHandler;
    DictionaryJsonHandler dJsonHandler;
    final String langURL="http://api...";
    final String dictURL="http://api...";
    String[][] localisedLangs;
    String[][] localisedDicts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an);
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();
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
    }
    @Override
    public void onDictionariesObtained(Dictionary[] dictionaries){
        localisedDicts=new String[dictionaries.length][2];
        int index = 0;
        for (Dictionary dict: dictionaries){
            localisedDicts[index][1]= dict.getCode();
            localisedDicts[index][2]= dict.getDName();
            index++;
        }
    }
    @Override
    public void onDictionariesFailure(int statusCode){
        //handle failure
    }
    @Override
    public void onLanguagesObtained(Language[] languages){
        localisedLangs=new String[languages.length][2];
        int index = 0;
        for (Language lang: languages){
            localisedDicts[index][1]= lang.getLangCode();
            localisedDicts[index][2]= lang.getLangName();
            index++;
        }
    }
    @Override
    public void onLanguagesFailure(int statusCode){
        //handle failure
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_an, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
