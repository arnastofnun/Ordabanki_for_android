package com.arnastofnun.idordabanki.activities;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.dialogs.ConnectionDialogueFragment;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.arnastofnun.idordabanki.adapters.ResultPagerAdapter;
import com.arnastofnun.idordabanki.dialogs.HelpDialog;
import com.arnastofnun.idordabanki.models.Result;
import com.arnastofnun.idordabanki.preferences.Settings;
import com.arnastofnun.idordabanki.sync.ConnectionDetector;

import java.util.List;

/**
 * ResultInfo displays more info when result has been selected
 *
 * When a specific result is clicked, it opens a new activity
 * with more information about the result and translations
 *
 * @author Trausti
 * @since 05.11.2014
 */

public class ResultInfo extends FragmentActivity implements ConnectionDialogueFragment.ConnectionDialogueListener {
    int resultIndex;
    static String termID;
    String wID;
    private ConnectionDetector connectionDetector;




    /**
     * display activity with info about selected term in results screen
     * <p> After all info on results have been fetched the data is parsed, HTML is
     *   generated inside the method and interpreted and displayed on the screen </p>
     *  written by Trausti
     * 
     * @param  savedInstanceState saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setCurrentTheme(this);
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_result_info);
        setContentView(R.layout.activity_result_info);
        PagerAdapter resultPagerAdapter;
        ViewPager resultPager;
        connectionDetector = new ConnectionDetector(this);
        Bundle extras = getIntent().getExtras();
        resultPager = (ViewPager) findViewById(R.id.result_pager);
        resultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());
        if(extras.containsKey("selectedResult")) {
            wID = Long.toString(extras.getLong("selectedResult"));
            resultPager.setAdapter(resultPagerAdapter);
            Globals globals = (Globals) Globals.getContext();
            for(Result item: globals.getResults()){
                if(item.getId_term().equals(wID)){
                    resultIndex = globals.getResults().indexOf(item);
                }
                if(item.getId_word() != null && item.getId_word().equals(wID)){
                    resultIndex = globals.getResults().indexOf(item);
                }
            }
            resultPager.setCurrentItem(resultIndex);
        }
        else if(extras.containsKey("termID")){
            termID = extras.getString("termID");
            resultPager.setAdapter(resultPagerAdapter);
            resultPager.setCurrentItem(0);
        }
    }

    /**
     * a method to get the termID
     * @return the term ID
     */
    public static String getTermId(){
        return termID;
    }

    /**
     * A method to get the total count of pages
     * in the page viewer
     * @return the amount of pages
     */
    public static int getPageCount(){
        if(getTermId() != null){
            return 1;
        }
        else{
            Globals globals = (Globals) Globals.getContext();
            List<Result> resultList = globals.getResults();
            return resultList.size();
        }
    }

    /**
     * Inflates the options menu
     * @param menu the options menu
     * @return true or false
     * TODO inflate the correct options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_info, menu);
        //Get the search view and the search manager
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Set up the search view
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    /**
     * Handle the back button press
     */
    @Override
    public void onBackPressed() {
        chooseBackPath();
    }

    /**
     * A method that chooses where to go back
     */
    private void chooseBackPath(){
        super.onBackPressed();
        //If we are doing term search
        if(termID != null){
            //We go back to the search screen
            Intent intent = new Intent(this, SearchScreen.class);
            this.startActivity(intent);
        }
        else{
            //We go back to the results
            Globals global = (Globals) Globals.getContext();
            Intent intent = new Intent(this, ResultsScreen.class);
            intent.putExtra("searchQuery",global.getSTerm());
            intent.putExtra("newSearch",true);
            this.startActivity(intent);
        }
    }

    /**
     * A method to get the selected value
     * @return the selected value
     */
    public Long getSelected(){
        return Long.valueOf(wID);
    }

    /**
     * handles actions when an item in the options menu is clicked
     * Written by Karl Ásgeir Geirsson
     * @param item the clicked item
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.resultinfo);
        */
        switch(item.getItemId()){
            //If the help button is pressed
            case R.id.action_help:
                //Get the correct list of helps for this activity
                String[] titleList = getResources().getStringArray(R.array.help_result_info_screen_titles);
                String[] helpList = getResources().getStringArray(R.array.help_result_info_screen);
                //Create a new help dialog
                HelpDialog helpDialog = new HelpDialog(this,this.getLayoutInflater(),titleList,helpList);
                //Show the help dialog
                helpDialog.show();

                return true;
            //If the settings button is pressed
            case R.id.action_settings:
                //Find the view for the settings button
                View v = findViewById(R.id.action_settings);
                //Create a new settings instance
                Settings settings = new Settings(this);
                //Create a popup menu with settings, that pops from the action button
                settings.createOptionsPopupMenu(v);
                return true;

            case R.id.action_declension:
                Globals globals = (Globals) Globals.getContext();
                String uri = "http://bin.arnastofnun.is/leit/?q=" + globals.getSTerm();
                Intent intent  = new Intent(this, Beygingar.class).putExtra("url_string",uri);
                this.startActivity(intent);
                return true;

            case android.R.id.home:
                chooseBackPath();
                return true;
        }

            return super.onOptionsItemSelected(item);
    }

    /**
     * A method so the fragment can get the connections
     * detector
     */
    public ConnectionDetector getConnectionsDetector(){
        return this.connectionDetector;
    }


    /**
     * A method that's run when the connection
     * dialog positive button is clicked
     * @param dialog the dialog
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        connectionDetector.retry();
    }

    /**
     * A method that's run when the connection
     * dialog negative button is clicked
     * @param dialog the dialog
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finish();
    }
}
