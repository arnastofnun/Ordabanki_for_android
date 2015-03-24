package com.arnastofnun.idordabanki.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.arnastofnun.idordabanki.LocaleSettings;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.ThemeHelper;

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


    /**
     * Runs when the activity is created
     * Does all the setup work for the activity
     * @param savedInstanceState the saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setCurrentNoActionBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        //initTypeFace();


        //Get the image buttons
        ImageButton enButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_english);
        ImageButton isButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_icelandic);
        ImageButton daButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_danish);
        ImageButton svButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_swedish);
        ImageButton noButton = (ImageButton) findViewById(R.id.SelectLanguageActivity_norwegian);

        //create new locale settings object
        final LocaleSettings localeSettings = new LocaleSettings(this);

        //Create on click listeners for the buttons
        languageOnClickListener(enButton);
        languageOnClickListener(isButton);
        languageOnClickListener(daButton);
        languageOnClickListener(svButton);
        languageOnClickListener(noButton);


    }

    /*
    private void initTypeFace() {
        TextView textView = (TextView)findViewById(R.id.welcometext);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Walkway_SemiBold.ttf");
        textView.setTypeface(font);
    }
    */


    /**
     * use: languageOnClickListener(imageButton, localeSettings)
     * pre: imageButton is of type ImageButton, localeSettings is of type LocaleSettings
     * post: the language has been set to the language that the image button defines
     * -----------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param imageButton an image button that is supposed to change language
     */
    private void languageOnClickListener(ImageButton imageButton){
        final String lang;
        //Switch to choose language from the buttons
        switch(imageButton.getId()){
            case R.id.SelectLanguageActivity_english:
                lang="EN";
                break;
            case R.id.SelectLanguageActivity_icelandic:
                lang="IS";
                break;
            case R.id.SelectLanguageActivity_danish:
                lang="DA";
                break;
            case R.id.SelectLanguageActivity_swedish:
                lang="SV";
                break;
            case R.id.SelectLanguageActivity_norwegian:
                lang="NO";
                break;
            default:
                lang="";
                break;
        }

        //create on click listener for the button
        imageButton.setOnClickListener(new View.OnClickListener(){

            /**
             * handles action when image button is clicked
             * @param v the view of the clicked image button
             */
            @Override
            public void onClick(View v){
                //start the activity in the correct language
                LocaleSettings lSettings = new LocaleSettings(getBaseContext());
                lSettings.setLanguageInit(lang);
                Intent intent = new Intent(SelectLanguageActivity.this,SplashActivity.class);
                SelectLanguageActivity.this.startActivity(intent);
                SelectLanguageActivity.this.finish();
            }
        });
    }



}
