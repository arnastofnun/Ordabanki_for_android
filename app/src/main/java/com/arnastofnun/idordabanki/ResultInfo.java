package com.arnastofnun.idordabanki;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.arnastofnun.idordabanki.adapters.ResultPagerAdapter;

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

public class ResultInfo extends FragmentActivity {
    int resultIndex;
    static String termID;




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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_info);
        PagerAdapter resultPagerAdapter;
        ViewPager resultPager;
        Bundle extras = getIntent().getExtras();
        resultPager = (ViewPager) findViewById(R.id.result_pager);
        resultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());
        if(extras.containsKey("selectedResultIndex")) {
            resultIndex = extras.getInt("selectedResultIndex");
            resultPager.setAdapter(resultPagerAdapter);
            resultPager.setCurrentItem(resultIndex);
        }
        else if(extras.containsKey("termID")){
            termID = extras.getString("termID");
            resultPager.setAdapter(resultPagerAdapter);
            resultPager.setCurrentItem(0);
        }
    }

    public static String getTermId(){
        return termID;
    }

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
            Intent intent = new Intent(this, ResultsScreen.class);
            this.startActivity(intent);
        }
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

            case android.R.id.home:
                chooseBackPath();
                return true;
        }

            return super.onOptionsItemSelected(item);
    }
}
