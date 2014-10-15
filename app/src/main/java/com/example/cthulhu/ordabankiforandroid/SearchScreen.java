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

import com.example.cthulhu.ordabankiforandroid.adapter.TabsPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains functions for the search screen
 * tabbed view.
 * It allows you to swipe and tab between fragments
 * Created by karlasgeir on 9.10.2014.
 */
public class SearchScreen extends FragmentActivity implements ActionBar.TabListener{
    //Variables
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
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
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Add items to source language dropdown spinner


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





    /* Handle the button on the search screen fragment */
    public void search(View view){
        Intent intent = new Intent(this, ResultsScreen.class);
        EditText editText =(EditText) findViewById(R.id.searchView);
        String searchQuery = editText.getText().toString();
        intent.putExtra("searchQuery", searchQuery);
        this.startActivity(intent);
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){
        return;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){
        return;
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
