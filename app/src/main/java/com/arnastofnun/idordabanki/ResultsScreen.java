package com.arnastofnun.idordabanki;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.adapters.ResultsAdapter;
import com.arnastofnun.idordabanki.interfaces.OnResultObtainedListener;
import com.arnastofnun.idordabanki.interfaces.OnSynonymResultObtainedListener;
import com.arnastofnun.idordabanki.jsonHandlers.OrdabankiJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.SynonymResultJsonHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class implements functions for the results screen
 * ------------------------------------------------------
 * @author Trausti
 * @since 08.10.2014
 */
public class ResultsScreen extends Activity implements OnResultObtainedListener, OnSynonymResultObtainedListener {
    private Globals global = (Globals) Globals.getContext();

    private String searchQuery;
    private ListView listView;
    private ArrayList<Result> resultList;
    private List<SynonymResult> synonymResultList;
    private boolean wordDone = false;
    private boolean synonymDone = false;
    private boolean wordError = false;
    private boolean synonymError = false;

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


        resultList = global.getResults();
        if(resultList == null){
            Bundle data = getIntent().getExtras();
            searchQuery = data.getString("searchQuery");
            if(isInteger(searchQuery)){
                doTermIdSearch(searchQuery);
            }
            else{
                doWordSearch(searchQuery);
                doSynonymSearch(searchQuery);
            }

        }
        else{
            displayListView();
        }

        waitForResults();


    }

    /**
     * This method initiates a term ID search
     * @param searchQuery the search query to search for
     */
    public void doTermIdSearch(String searchQuery){
        Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
        intent.putExtra("termID", searchQuery);
        startActivity(intent);
    }

    /**
     * Searches for word
     * @param searchQuery the search query
     */
    public void doWordSearch(String searchQuery){
        OrdabankiJsonHandler jsonHandler;
        jsonHandler = new OrdabankiJsonHandler(this);
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
        ArrayList<Result> rList = new ArrayList<Result>(Arrays.asList(resultArr));
        Collections.sort(rList);
        resultList = rList;
        wordDone = true;
    }


    //TODO handle errors correctly
    /**
     *shows no result screen if connection established and no result or connection error and HTTP
     *error code if connection not established
     *  Bill
     *  @param statusCode HTTP status code. No result on 200, otherwise error.
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
            wordError = true;
        }
    }


    /**
     * A method that searches for synonyms
     * @param searchQuery the search query
     */
    public void doSynonymSearch(String searchQuery){
        SynonymResultJsonHandler sJsonHandler = new SynonymResultJsonHandler(ResultsScreen.this);
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();
        try {
            client.setSynonymResults(OrdabankiURLGen.createSynonymURL(searchQuery), sJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * A method that is run when the synonym request is finished
     * @param sResult the synonym result from the api
     */
    @Override
    public void onSynonymResultObtained(SynonymResult[] sResult){
        List<SynonymResult> sList = Arrays.asList(sResult);
        Collections.sort(sList);
        synonymResultList = sList;
        synonymDone = true;
    }

    /**
     * A method that is run if the synonym request fails
     * @param statusCode HTTP status code. No result on 200, otherwise error.
     */
    @Override
    public void onSynonymResultFailure(int statusCode) {
        if(statusCode == 200){
            synonymDone = true;
        }
        else {
            synonymError = true;
        }
    }
    /**
     * A method that waits for the result of the search
     * Written by Karl Ásgeir Geirsson
     */
    public void waitForResults(){
        Runnable runnable = new Runnable() {
            /**
             * Written by Bill and Karl
             * Checks if languages and dictionaries have been obtained
             * if so it sets them
             */
            public void run() {
                Looper.prepare();
                //Decide end time
                //While we don't get an error
                while (!(synonymError && wordError)) {
                    //If dictionaries and languages are obtained
                    if (wordDone && synonymDone) {
                        break;
                    }
                }

                //Create a new handler to run after the delay in the main thread
                Handler mainHandler = new Handler(ResultsScreen.this.getMainLooper());
                mainHandler.post(new Runnable() {
                    /**
                     * Written by Karl Ásgeir Geirsson
                     * post: The search screen has been opened in the correct language
                     */
                    @Override
                    public void run() {
                        combineResults();
                        displayListView();
                    }
                });
            }
        };
        //Start a new thread with the runnable
        Thread timingThread = new Thread(runnable);
        timingThread.start();
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
    private void displayListView(){
        //Placeholder values for the glossaries
        //Result result = new Result("lobe", "english", "Medicine");
        //resultList.add(result);
        //result = new Result("blade", "english", "Metallurgy");
        //resultList.add(result);
        String searchPreTerm = getResources().getString(R.string.searchpreterm);
        TextView textView = (TextView) findViewById(R.id.resultText);
        int resultsCount = resultList.size();
        if(searchQuery!=null){
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);
        }else{
            String topResultWord = resultList.get(0).getWord();
            textView.setText(resultsCount + " " + searchPreTerm + " " + topResultWord);
        }

        //Creating a new glossary adapter
        ResultsAdapter resultsAdapter = new ResultsAdapter(this, R.layout.results_list, resultList);
        final ArrayList<Result> rList = resultList;
        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        listView = (ListView) findViewById(R.id.resultsList);
        listView.setAdapter(resultsAdapter);



        //Setting the on item click listener to be ready for later use
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                global.setResults(rList);
                Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                intent.putExtra("selectedResultIndex",position);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Clicked on: " + result.getWord(), Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * A method that combines the results into
     * one array
     */
    private void combineResults(){
        if(this.synonymResultList!=null && this.synonymResultList.size()>0) {
            for (SynonymResult synonymResult : synonymResultList) {
                Result result = new Result();
                result.setWord(synonymResult.getSynonym() + " → " + synonymResult.getWord());
                result.setId_term(synonymResult.getTerm_id());
                result.setDictionary_code(synonymResult.getDict_code());
                //TODO: set language from API
                //Just to keep it from crashing, I could remove the language view for it though
                result.setLanguage_code("IS");
                resultList.add(result);
            }
            Collections.sort(resultList);
        }
    }

    /**
     * A method that checks if a string is numerical
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
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
        //Get the search view and the search manager
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Set up the search view
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
                String[] titleList = getResources().getStringArray(R.array.help_result_screen_titles);
                String[] helpList = getResources().getStringArray(R.array.help_result_screen);
                HelpDialog helpDialog = new HelpDialog(this,this.getLayoutInflater(),titleList,helpList);
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
        }
        return super.onOptionsItemSelected(item);

    }
}
