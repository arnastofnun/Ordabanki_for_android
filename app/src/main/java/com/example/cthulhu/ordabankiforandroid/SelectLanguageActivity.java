package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import java.util.Locale;

/*
*   Holds the functions that are implemented in
*   the select language screen
*
*/

/**
 * This class holds the functions that are implemented
 * in the select language screen
 * It handles actions for the choose language buttons
 * ----------------------------------------------------
 * @author Karl Ásgeir Geirsson
 */
public class SelectLanguageActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        //Get the image buttons
        ImageButton enButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_english);
        ImageButton isButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_icelandic);
        ImageButton daButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_danish);
        ImageButton svButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_swedish);

        enButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setLocale("en");
            }
        });

        isButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setLocale("is");
            }
        });
        daButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setLocale("da");
            }
        });

        svButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setLocale("sv");
            }
        });


    }

    /**
     * pre:lang is of type String
     * post:starts the activity that sets the language of the UI
     * todo move this into a LocaleSettings class
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param lang is the language locale string ("en" or "is")
     */
    public void setLocale(String lang){
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf,dm);
        Intent intent = new Intent(this,SearchScreen.class);
        startActivity(intent);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
