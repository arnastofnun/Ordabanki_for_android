package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
public class SplashActivity extends Activity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final LocaleSettings localeSettings = new LocaleSettings(this);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(localeSettings.getLocaleStatus()){
                    localeSettings.setLanguageFromPref(SearchScreen.class);
                }
                else {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
