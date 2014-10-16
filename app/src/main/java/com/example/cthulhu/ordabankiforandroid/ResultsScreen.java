package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.ResultsAdapter;

import java.util.ArrayList;

/**
 * This class implements functions for the results screen
 * ------------------------------------------------------
 * @author Trausti
 * @since 08.10.2014
 */
public class ResultsScreen extends Activity {
    //Initialize
    private ArrayList<Result> resultList = new ArrayList<Result>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);

        /*Just so we have something going through the intent*/
        Intent intent = getIntent();
        //This will be used to get the results from the intent
        //ArrayList <Result> resultList = getIntent().getStringArrayListExtra("resultList")

        //This is just for now untill we get the API
        String searchquery = intent.getStringExtra("searchQuery");
        String searchpreterm = getResources().getString(R.string.searchpreterm);
        int resultscount = 5;
        TextView textview = (TextView) findViewById(R.id.resultText);
        textview.setText(resultscount + " " + searchpreterm + " " + searchquery);

       displayListView(findViewById(android.R.id.content));
    }


    /**
     * This function is supposed to loop through the glossaries and add them to the glossary list.
     * For now it just puts some test glossaries in.
     * It also sets an on click listener that displays a toast for now.
     * It should open a link to the url of the glossary later
     * todo add url to the glossary list
     * todo get glossaries from API and put into glossary list
     * -------------------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @since 09.10.2014
     * @param rootView the root view
     */
    private void displayListView(View rootView){
        //Placeholder values for the glossaries
        Result result = new Result("lobe", "english", "Medicine");
        resultList.add(result);
        result = new Result("blade", "english", "Metallurgy");
        resultList.add(result);

        //Creating a new glossary adapter
        ResultsAdapter resultsAdapter = new ResultsAdapter(this, R.layout.results_list, resultList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        ListView listView = (ListView) findViewById(R.id.resultsList);
        listView.setAdapter(resultsAdapter);

        //Setting the on item click listener to be ready for later use
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result result = (Result) parent.getItemAtPosition(position);
                //For now just display a toast for testing
                Toast.makeText(getApplicationContext(), "Clicked on: " + result.getTerm(), Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results_screen, menu);
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
}
