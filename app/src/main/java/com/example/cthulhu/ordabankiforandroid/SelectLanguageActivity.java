package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.widget.TextView;

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
        initTypeFace();


        //Get the image buttons
        ImageButton enButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_english);
        ImageButton isButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_icelandic);
        ImageButton daButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_danish);
        ImageButton svButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_swedish);

        //create new locale settings object
        final LocaleSettings localeSettings = new LocaleSettings(this);

        //Create on click listeners for the buttons
        languageOnClickListener(enButton,localeSettings);
        languageOnClickListener(isButton,localeSettings);
        languageOnClickListener(daButton,localeSettings);
        languageOnClickListener(svButton,localeSettings);


    }

    private void initTypeFace() {
        /*TextView textView = (TextView)findViewById(R.id.welcometext);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Walkway_SemiBold.ttf");
        textView.setTypeface(font);*/
    }


    /**
     * use: languageOnClickListener(imageButton, localeSettings)
     * pre: imageButton is of type ImageButton, localeSettings is of type LocaleSettings
     * post: the language has been set to the language that the image button defines
     * -----------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param imageButton an image button that is supposed to change language
     * @param localeSettings a locale settings object
     */
    private void languageOnClickListener(ImageButton imageButton, final LocaleSettings localeSettings){
        final String lang;
        //Switch to choose language from the buttons
        switch(imageButton.getId()){
            case R.id.SelectLanguageActivity_english:
                lang="en";
                break;
            case R.id.SelectLanguageActivity_icelandic:
                lang="is";
                break;
            default:
                lang="";
                break;
        }

        //create on click listener for the button
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start the activity in the correct language
                localeSettings.setLanguage(lang,SearchScreen.class);
            }
        });
    }



}
