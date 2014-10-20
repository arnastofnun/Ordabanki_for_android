package com.example.cthulhu.ordabankiforandroid;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

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
public class SearchScreen extends FragmentActivity implements ActionBar.TabListener{
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

        //Initilize
        viewPager = (ViewPager) findViewById(R.id.searchscreen);
        actionBar = getActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);




        //Adding Tabs
        for(String tab_name : tabs){
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





    // Handle the button on the search screen fragment

    /**
     * This method handles the search. It gets the search query,
     * the source and target languages, sends a request to the API and gets an
     * array of results.
     * The results are then sent to the ResultsScreen, and the
     * ResultScreen activity is started
     * ----------------------------------------------------------------------
     * Written by Bill
     * @param view the current view
     * @throws JSONException
     */
    public void search(View view) throws JSONException {
        //Create the Intent
        Intent intent = new Intent(this, ResultsScreen.class);
        //Get the search query
        EditText searchView =(EditText) findViewById(R.id.searchView);
        String searchQuery = searchView.getText().toString();
        if(!searchQuery.equals("")) {
            //Get the currently selected language from the source language spinner
            Spinner sourceLangSpinner = (Spinner) findViewById(R.id.sourceSpinner);
            String sLang = sourceLangSpinner.getSelectedItem().toString();

            //Get the currently selected language from the target language spinner
            Spinner targetLangSpinner = (Spinner) findViewById(R.id.targetSpinner);
            String tLang = targetLangSpinner.getSelectedItem().toString();

            //Get the search results
            OrdabankiRestClientActions.setSearchResult(searchQuery, sLang, tLang);
            ArrayList<Result> resultList = new ArrayList<Result>();
            //For now, just put in some placeholer values
            Result result = new Result("lobe", "english", "Medicine");
            resultList.add(result);
            result = new Result("blade", "english", "Metallurgy");
            resultList.add(result);
            //resultList = OrdabankiRestClientActions.getResultArray();
            //languages will be handled elsewhere when this is ready to go

            //For now we just put the search query through
            intent.putExtra("searchQuery", searchQuery);

            //Put the result list through the intent
            intent.putExtra("resultList", resultList);
            //Start the results screen
            this.startActivity(intent);
        }
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){
    }

    //Make sure the viewPager changes fragments when tab is pressed
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
            return rootView;
        }
    }
}
