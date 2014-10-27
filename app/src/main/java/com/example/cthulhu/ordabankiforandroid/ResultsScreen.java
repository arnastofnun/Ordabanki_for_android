package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.ResultsAdapter;

import org.json.JSONException;

/**
 * This class implements functions for the results screen
 * ------------------------------------------------------
 * @author Trausti
 * @since 08.10.2014
 */
public class ResultsScreen extends Activity implements OnResultObtainedListener{
    OrdabankiJsonHandler jsonHandler;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);
        jsonHandler = new OrdabankiJsonHandler(this);
        Bundle data = getIntent().getExtras();
        searchQuery = data.getString("searchQuery");
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();
            try {
                client.setResults(OrdabankiURLGen.createWordOnlyURL(searchQuery), jsonHandler);
                //when glossaries and languages implemented in api use:
                //client.setResults(OrdabankiRestClientActions.createURL(searchQuery), jsonHandler);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onResultObtained(Result[] result){
        String searchPreTerm = getResources().getString(R.string.searchpreterm);
        TextView textView = (TextView) findViewById(R.id.resultText);
        if(result == null){
            String databaseError = getResources().getString(R.string.database_error);
            textView.setText(databaseError);
        }
        else {
            int resultsCount = result.length;
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);
            displayListView(result);
        }
    }
    @Override
    public void onResultFailure(int statusCode) {
        if (statusCode==404) {
            String searchPreTerm = getResources().getString(R.string.searchpreterm);
            TextView textView = (TextView) findViewById(R.id.resultText);
            String noResult = getResources().getString(R.string.no_result);
            textView.setText(noResult + " " + searchPreTerm + " " + searchQuery);
        }
        else{
            TextView textView = (TextView) findViewById(R.id.resultText);
            String connectionError = getResources().getString(R.string.connection_error);
            textView.setText(connectionError);
        }
    }
    /**
     * This function is supposed to loop through the glossaries and add them to the glossary list.
     * For now it just puts some test glossaries in.
     * It also sets an on click listener that displays a toast for now.
     * It should open a link to the url of the glossary later
     *
     * -------------------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @since 09.10.2014
     */
    private void displayListView(Result[] resultList){
        //Placeholder values for the glossaries
        //Result result = new Result("lobe", "english", "Medicine");
        //resultList.add(result);
        //result = new Result("blade", "english", "Metallurgy");
        //resultList.add(result);

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
                Toast.makeText(getApplicationContext(), "Clicked on: " + result.getWord(), Toast.LENGTH_LONG).show();
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
