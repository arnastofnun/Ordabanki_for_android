package com.arnastofnun.idordabanki;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arnastofnun.idordabanki.adapters.TabsPagerAdapter;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * This class contains functions for the search screen
 * tabbed view.
 * It allows you to swipe and tab between fragments
 * @author Karl Ásgeir Geirsson
 * @since 9.10.2014
 */
public class SearchScreen extends FragmentActivity {
    /*
    *   Data invariants:
    *   viewPager: ViewPager object
    *   mAdapter: Adapter for tabs pager
    *   sourceLangSpinner: source language drop down menu items
    *   targetLangSpinner: target language drop down menu items    
    *   resultList: list of search results
    */
    private ViewPager viewPager;

    /**
     * @param newConfig sets newconfigurations
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

    }

    /**
     * @param outState the state to be saved
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    /**
     * @param savedState the state that was saved
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);
    }




    /**
     * runs when the activity is created
     * does all of the setup for the application
     * Written by Karl Ásgeir Geirsson
     * @param savedInstanceState the saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Resets the kept results array so we can search again
        Globals globals = (Globals) Globals.getContext();
        globals.setResults(null);
        //Set the content view
        setTitle(R.string.search_tab);
        setContentView(R.layout.activity_search_screen);
        //Get the current locale
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();


        Intent intent = getIntent();
        checkSearchQuery(intent);

        //Tab titles
        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add(this.getString(R.string.search_tab));
        tabs.add(this.getString(R.string.pick_glossary_tab));
        tabs.add(this.getString(R.string.languages_tab_name));

        //Setup the viewPager
        setupViewPager(tabs);


        //Disable the homebutton on the action bar
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
        }


    }

    /**
     * This method sets up the viewpager with the correct tabs
     * @param tabs the tabs of the viewpager
     */
    private void setupViewPager(ArrayList<String> tabs){
        //Initialize the viewpager
        viewPager = (ViewPager) findViewById(R.id.searchscreen);
        //Create a tabs pager adapter
        TabsPagerAdapter tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        //Add the tab titles to the tabs pager adapter
        tabsAdapter.setTabTitles(tabs);
        //set the adapter to the
        viewPager.setAdapter(tabsAdapter);

        //Change the keyboard state on swipe
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * Runs when a new page is selected
             * Written by Karl Ásgeir Geirsson
             * @param position the position of the new page
             */
            @Override
            public void onPageSelected(int position) {
                //If its the search screen
                if(position == 0){
                    toggleKeyboard(true);
                }
            }

            /**
             * Runs when the page is scrolled
             * @param position The position of the active page
             * @param positionOffset the offset from the active page
             * @param positionOffsetPixels the offset from the active page in pixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //If there is movement and we are in the search fragment
                if(positionOffset > 0 && position == 0){
                    toggleKeyboard(false);
                }

            }

            /**
             * Runs when the page scroll state is changed
             * @param position the position of the active page
             */
            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent){
        checkSearchQuery(intent);
    }

    /**
     * Checks if a search has happened
     * and tries to search
     */
    private void checkSearchQuery(Intent intent){
        //Check for search query

        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            //Get the search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Save it to search suggestions
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
            suggestions.saveRecentQuery(query,null);
            //Try searching
            try {
                search(query);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * A method that toggles the keyboard
     * visibility based on a boolean value
     * @param on turns keyboard on if true, else off
     */
    private void toggleKeyboard(boolean on) {
        View focus = getCurrentFocus();
        if (focus != null) {
            //We turn on the keyboard
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(on) keyboard.showSoftInput(focus, 0);
            else keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

    /**
     * This disables the back button
     */
    @Override
    public void onBackPressed() {
    }


    /**
     * This method handles the search. It gets the search query,
     * the source and target languages, sends a request to the API and gets an
     * array of results.
     * The results are then sent to the ResultsScreen, and the
     * ResultScreen activity is started
     * ----------------------------------------------------------------------
     * Written by Bill
     *
     * @throws JSONException
     */
    public void search(String searchQuery) throws JSONException {
        Boolean allowsearch = true;
        //Create the Intent
        //todo make intent go to term results screen if search term is numeric

        Intent intent = new Intent(this, ResultsScreen.class);

        if (searchQuery.equals("")) {
            allowsearch = false;
            Toast.makeText(this, this.getString(R.string.no_search_term), Toast.LENGTH_LONG).show();
        }


        if (PickGlossaryFragment.getSelectedGlossaries().isEmpty()) {
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


    /**
     * Runs when the options menu is created
     * @param menu the menu
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_screen, menu);
        return true;
    }

    /**
     * runs when an options menu item is clicked
     * @param item the item that is clicked
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            //If clicked on help
            case R.id.action_help:
                //Find the current fragment
                int currFragment = viewPager.getCurrentItem();
                //List of help titles and contents
                String[] titleList;
                String[] helpList;

                //Switch to determine the text of the help
                switch(currFragment){
                    //If we are in the search fragment
                    case 0:
                        titleList = getResources().getStringArray(R.array.help_search_fragment_titles);
                        helpList = getResources().getStringArray(R.array.help_search_fragment);
                        break;
                    //If we are in the pick glossary fragment
                    case 1:
                        titleList = getResources().getStringArray(R.array.help_glossary_fragment_titles);
                        helpList = getResources().getStringArray(R.array.help_glossary_fragment);
                        break;
                    //If we are in the pick language fragment
                    case 2:
                        titleList = getResources().getStringArray(R.array.help_language_fragment_titles);
                        helpList = getResources().getStringArray(R.array.help_language_fragment);
                        break;
                    default:
                        titleList = getResources().getStringArray(R.array.help_search_fragment_titles);
                        helpList = getResources().getStringArray(R.array.help_search_fragment);
                }
                //Create the help dialog
                HelpDialog helpDialog = new HelpDialog(this,this.getLayoutInflater(),titleList,helpList);


                //Create and show the dialog
                helpDialog.show();
                return true;
            //If the settings button is pressed
            case R.id.action_settings:
                //Get the view of the settings button
                View v = findViewById(R.id.action_settings);
                //Create a new instance of Settings
                Settings settings = new Settings(this);
                //Create a popup settings menu that pops up below the settings button
                settings.createOptionsPopupMenu(v);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
