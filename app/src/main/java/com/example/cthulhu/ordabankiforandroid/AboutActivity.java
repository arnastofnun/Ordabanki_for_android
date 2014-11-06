package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * This class handles the about activity screen
 * The screen includes information about the app
 * and the history of the Orðabanki
 * This is initiated by the about button in settings in the action bar
 * @author Karl Ásgeir Geirrson
 * @since 24.10.2014.
 */
public class AboutActivity extends Activity {

    /**
     * Runs when activity is created
     * @param savedInstanceState the saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        //Make sure it is set in the right language
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();

    }

    /**
     * Runs when menu is created
     * just inflates the menu
     * @param menu the menu
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    /**
     * Runs when an item in the action bar is cliced
     * @param item the menu item that was clicked
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Handle the settings button
        if(id == R.id.action_settings){
            View v = findViewById(R.id.action_settings);
            //Open up the popup options menu
            Settings settings = new Settings(this);
            settings.createOptionsPopupMenu(v,AboutActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
