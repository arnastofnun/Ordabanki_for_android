package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.adapter.ResultsAdapter;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class implements functions for the results screen
 * ------------------------------------------------------
 * @author Trausti
 * @since 08.10.2014
 */
public class ResultsScreen extends Activity implements OnResultObtainedListener{
    OrdabankiJsonHandler jsonHandler;
    private String searchQuery;
    private ListView listView;
    /**
     * Takes search term from intent and passes to Rest client
     * Bill
     * @param savedInstanceState saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();

        jsonHandler = new OrdabankiJsonHandler(this);
        Bundle data = getIntent().getExtras();
        searchQuery = data.getString("searchQuery");
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();
            try {
                client.setResults(OrdabankiURLGen.createWordURL(searchQuery), jsonHandler);
                //when glossaries and languages implemented in api use:
                //client.setResults(OrdabankiRestClientActions.createURL(searchQuery), jsonHandler);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    /**
     * Passes result to list view on successful connection.
     * Bill
     * @param resultArr Result array
     */
    @Override
    public void onResultObtained(Result[] resultArr){
        List<Result> resultList = Arrays.asList(resultArr);
        Collections.sort(resultList);

        String searchPreTerm = getResources().getString(R.string.searchpreterm);
        TextView textView = (TextView) findViewById(R.id.resultText);
        if(resultArr == null){
            String databaseError = getResources().getString(R.string.database_error);
            textView.setText(databaseError);
        }
        else {
            int resultsCount = resultList.size();
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);
            displayListView(resultList);
        }
    }

    /**
     *shows no result screen if connection established and no result or connection error and HTTP
     *error code if connection not established
     *  Bill
     *  @param statusCode HTTP status code
     */
    @Override
    public void onResultFailure(int statusCode) {
        if (statusCode==200) {
            String searchPreTerm = getResources().getString(R.string.searchpreterm);
            TextView textView = (TextView) findViewById(R.id.resultText);
            String noResult = getResources().getString(R.string.no_result);
            textView.setText(noResult + " " + searchPreTerm + " " + searchQuery);
        }
        else{
            TextView textView = (TextView) findViewById(R.id.resultText);
            String connectionError = getResources().getString(R.string.connection_error) +": "+ statusCode;
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
    private void displayListView(List<Result> resultList){
        //Placeholder values for the glossaries
        //Result result = new Result("lobe", "english", "Medicine");
        //resultList.add(result);
        //result = new Result("blade", "english", "Metallurgy");
        //resultList.add(result);

        //Creating a new glossary adapter
        ResultsAdapter resultsAdapter = new ResultsAdapter(this, R.layout.results_list, resultList);
        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        listView = (ListView) findViewById(R.id.resultsList);
        listView.setAdapter(resultsAdapter);


        //Setting the on item click listener to be ready for later use
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Result result = (Result) parent.getItemAtPosition(position);
                Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                intent.putExtra("idTerm",result.getId_term());
                intent.putExtra("idWord",result.getWord());
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Clicked on: " + result.getWord(), Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * Runs when options menu is created
     * basically just inflates the options menu
     * @param menu the options menu
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results_screen, menu);
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
        switch(item.getItemId()){
            //If the help button is pressed
            case R.id.action_help:
                //Build a dialog
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                helpBuilder
                        .setTitle(R.string.help_title)
                        .setMessage(getResources().getString(R.string.help_result_screen));
                //Cancel action
                helpBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Do nothing on cancel
                    }
                });
                //Create and show the dialog
                AlertDialog helpDialog = helpBuilder.create();
                helpDialog.show();
                return true;
            //If the settings button is pressed
            case R.id.action_settings:
                //Find the view for the settings button
                View v = findViewById(R.id.action_settings);
                //Create a new settings instance
                Settings settings = new Settings(this);
                //Create a popup menu with settings, that pops from the action button
                settings.createOptionsPopupMenu(v, SearchScreen.class);
                return true;



        }
        return super.onOptionsItemSelected(item);

    }
}
