package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cthulhu.ordabankiforandroid.adapter.ResultPagerAdapter;

/**
 * ResultInfo displays more info when result has been selected
 *
 * When a specific result is clicked, it opens a new activity
 * with more information about the result and translations
 *
 * @author Trausti
 * @since 05/11/14
 *
 */

public class ResultInfo extends FragmentActivity {
    int resultIndex;




    /**
     * display activity with info about selected term in results screen
     * <p> After all info on results has been fetched the data is parsed, HTML is
     *   generated inside the method and interpeted and displayed on the screen </p>
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
        resultIndex = extras.getInt("selectedResultIndex");
        resultPager = (ViewPager) findViewById(R.id.result_pager);
        resultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());
        resultPager.setAdapter(resultPagerAdapter);
        resultPager.setCurrentItem(resultIndex);




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
        return true;
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
                //Build a dialog

                String[] titleList = getResources().getStringArray(R.array.help_result_info_screen_titles);
                String[] helpList = getResources().getStringArray(R.array.help_result_info_screen);

                HelpDialog helpDialog = new HelpDialog(this,this.getLayoutInflater(),titleList,helpList);
                helpDialog.show();
                /*
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                helpBuilder
                        .setTitle(R.string.help_title)
                        .setMessage(getResources().getString(R.string.help_result_info_screen))
                        .setView(iv);
                //Cancel action
                helpBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Do nothing on cancel
                    }
                });
                //Create and show the dialog
                AlertDialog helpDialog = helpBuilder.create();

                */
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
        }

            return super.onOptionsItemSelected(item);
    }
}
