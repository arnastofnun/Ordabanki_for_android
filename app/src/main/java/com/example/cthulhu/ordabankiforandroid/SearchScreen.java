package com.example.cthulhu.ordabankiforandroid;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.TabsPagerAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains functions for the search screen
 * tabbed view.
 * It allows you to swipe and tab between fragments
 * Created by karlasgeir on 9.10.2014.
 */
public class SearchScreen extends FragmentActivity implements ActionBar.TabListener {
    /*
    *   Data invariants:
    *   viewPager: ViewPager object
    *   mAdaptar: Adapter for tabs pager
    *   sourceLangSpinner: source language drop down menu items
    *   targetLangSpinner: target language drop down menu items    
    *   resultList: list of search results
    */
    private ViewPager viewPager;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        //Tab titles
        List<String> tabs = new ArrayList<String>();
        tabs.add(this.getString(R.string.search_tab));
        tabs.add(this.getString(R.string.pick_glossary_tab));
        tabs.add(this.getString(R.string.languages_tab_name));

        //Initilize
        viewPager = (ViewPager) findViewById(R.id.searchscreen);
        actionBar = getActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        //Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }


        //Change the tabs when swiping between fragments
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // when page is selected also select correct tab
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
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

        if (allowsearch) {
            intent.putExtra("searchQuery", searchQuery);
            this.startActivity(intent);
        }
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        View focus = getCurrentFocus();
        if (focus != null) {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 0) {
            View focus = getCurrentFocus();
            if (focus != null) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(focus, 0);
            }
        }
    }

    //Make sure the viewPager changes fragments when tab is pressed
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
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

                //Todo set text for help  based on active fragment
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
                createOptionsPopupMenu(v);
                return true;



        }
        return super.onOptionsItemSelected(item);

    }


    private void createOptionsPopupMenu(View v){
        PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings_change_language:
                        AlertDialog.Builder languageBuilder = new AlertDialog.Builder(SearchScreen.this);
                        languageBuilder
                                .setTitle(R.string.change_language)
                                //Todo set default value for select language settings
                                .setSingleChoiceItems(R.array.language_array, -1, new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                    //Todo implement on click functions for select language settings
                                    }
                                });
                        //Todo implement accept button
                        languageBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        AlertDialog languageDialog = languageBuilder.create();
                        languageDialog.show();
                        return true;

                    case R.id.settings_about:
                        Intent intent = new Intent(SearchScreen.this, AboutActivity.class);
                        SearchScreen.this.startActivity(intent);
                        return true;
                    case R.id.settings_contact:
                        Intent email = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto",getResources().getString(R.string.contact_email),null));
                        //Todo doesn't seem to put the email to address through
                        try{
                            startActivity(Intent.createChooser(email,getResources().getString(R.string.choose_email_client)));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(SearchScreen.this,getResources().getString(R.string.error_no_email_client), Toast.LENGTH_LONG).show();
                        }
                        return true;
                }
                return true;
            }
        });
        inflater.inflate(R.menu.settings_menu,popup.getMenu());
        popup.show();
    }
}
