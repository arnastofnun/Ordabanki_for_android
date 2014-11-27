package com.arnastofnun.idordabanki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.arnastofnun.idordabanki.LocaleSettings;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.Settings;
import com.arnastofnun.idordabanki.adapters.displayTextAdapter;

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

        String[] titleList = getResources().getStringArray(R.array.about_titles);
        String[] aboutList = getResources().getStringArray(R.array.about_contents);
        //Creating a new help adapter
        displayTextAdapter adapter = new displayTextAdapter(this, R.layout.about_list, titleList,aboutList);
        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        ListView listView = (ListView) this.findViewById(R.id.about_list_view);
        listView.setAdapter(adapter);


    }


    @Override
    protected void  onStart(){
        super.onStart();
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
            settings.createOptionsPopupMenu(v);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
