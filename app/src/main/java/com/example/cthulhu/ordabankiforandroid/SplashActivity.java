package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

//Basically just waits for two secons and then starts the next activity

/**
 * <h1>Splash screen</h1>
 * <p>This class is supposed to handle things that need
 * to be hidden in the splash screen</p>
 * <p>It should get the Locale settings and select language based on them</p>
 * <p>It should start the select language screen if language has not been selected before</p>
 * <p>It should start the SearchScreen if language has already been selected</p>
 * <p>For now it just waits and then starts the select language screen</p>
 * todo get locale settings and set for rest of the app
 * todo start the select language screen if language only if language has not been selected
 * ------------------------------------------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 */
public class SplashActivity extends Activity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    SharedPreferences sharedpref;
    String lang="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(sharedpref.contains("lang")){
                    lang=sharedpref.getString("lang","DEFAULT");
                    setLocale(lang);
                }
                else {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                /*Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();*/
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


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
        getMenuInflater().inflate(R.menu.splash, menu);
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
