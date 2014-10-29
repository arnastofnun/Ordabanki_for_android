package com.example.cthulhu.ordabankiforandroid;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.TabsPagerAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;


/**
 * This class contains functions for the search screen
 * tabbed view.
 * It allows you to swipe and tab between fragments
 * Created by karlasgeir on 9.10.2014.
 */
public class SearchScreen extends FragmentActivity {
    /*
    *   Data invariants:
    *   viewPager: ViewPager object
    *   mAdaptar: Adapter for tabs pager
    *   sourceLangSpinner: source language drop down menu items
    *   targetLangSpinner: target language drop down menu items    
    *   resultList: list of search results
    */
    private ViewPager viewPager;
    private Locale locale = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();

        //Tab titles
        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add(this.getString(R.string.search_tab));
        tabs.add(this.getString(R.string.pick_glossary_tab));
        tabs.add(this.getString(R.string.languages_tab_name));

        //Initilize
        viewPager = (ViewPager) findViewById(R.id.searchscreen);
        ActionBar actionBar = getActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mAdapter.setTabTitles(tabs);
        viewPager.setAdapter(mAdapter);
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
        }


        //Change the tabs when swiping between fragments
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    View focus = getCurrentFocus();
                    if (focus != null) {
                        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.showSoftInput(focus, 0);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffset > 0 && position == 0){
                    View focus = getCurrentFocus();
                    if (focus != null) {
                        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });

    }

    //Disable the back button
    @Override
    public void onBackPressed() {
    }



    // Handle the button on the search screen fragment

    /**
     * This method handles the search. It gets the search query,
     * the source and target languages, sends a request to the API and gets an
     * array of results.
     * The results are then sent to the ResultsScreen, and the
     * ResultScreen activity is started
     * ----------------------------------------------------------------------
     * Written by Bill
     *
     * @param view the current view
     * @throws JSONException
     */
    public void search(View view) throws JSONException {
        Boolean allowsearch = true;
        //Create the Intent
        Intent intent = new Intent(this, ResultsScreen.class);
        //Get the search query
        EditText searchView = (EditText) findViewById(R.id.searchView);
        String searchQuery = searchView.getText().toString();

        if (searchQuery.equals("")) {
            allowsearch = false;
            Toast.makeText(this, this.getString(R.string.no_search_term), Toast.LENGTH_LONG).show();
        }


        if (PickGlossaryFragment.getSelectedGlossaries().isEmpty()) {
            Log.v("selected glossaries", "null");
            allowsearch = false;
            Toast.makeText(this, this.getString(R.string.no_glossary_picked), Toast.LENGTH_LONG).show();
        }
        if(searchQuery.contains("*")) {
            int count = 0;
            for(char ch: searchQuery.toCharArray()){
                if(ch != '*'){
                    count ++;
                }
                if(count >= 2){
                    break;
                }
            }
            if(count < 2) {
                allowsearch = false;
                Toast.makeText(this, R.string.ast_char_limit, Toast.LENGTH_LONG).show();
            }
        }

        if (allowsearch) {
            intent.putExtra("searchQuery", searchQuery);
            this.startActivity(intent);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_help:

                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                int currFragment = viewPager.getCurrentItem();

                helpBuilder.setTitle(R.string.help_title);
                switch(currFragment){
                    case 0:
                        helpBuilder.setMessage(getResources().getString(R.string.help_search_fragment));
                        break;
                    case 1:
                        helpBuilder.setMessage(getResources().getString(R.string.help_glossary_fragment));
                        break;
                    case 2:
                        helpBuilder.setMessage(getResources().getString(R.string.help_language_fragment));
                        break;
                }
                helpBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                });
                AlertDialog helpDialog = helpBuilder.create();
                helpDialog.show();

                return true;
            case R.id.action_settings:
                View v = findViewById(R.id.action_settings);
                Settings settings = new Settings(this);
                settings.createOptionsPopupMenu(v,SearchScreen.class);
                return true;



        }
        return super.onOptionsItemSelected(item);

    }

}
