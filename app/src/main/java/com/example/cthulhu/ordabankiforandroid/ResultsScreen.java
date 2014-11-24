package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.adapter.ResultsAdapter;

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
public class ResultsScreen extends Activity implements OnResultObtainedListener, OnSynonymResultObtainedListener{
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
        synonymResultList = global.getSynonymResults();
        if(resultList == null && synonymResultList == null){
            Bundle data = getIntent().getExtras();
            searchQuery = data.getString("searchQuery");
            doWordSearch(searchQuery);
            doSynonymSearch(searchQuery);

        }
        else{
            displayListView();
        }

        waitForResults();


    }


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

        TextView textView = (TextView) findViewById(R.id.resultText);
        if(resultArr == null){
            String databaseError = getResources().getString(R.string.database_error);
            textView.setText(databaseError);
        }
        else {
            resultList = rList;
            wordDone = true;
        }



    }


    //TODO handle errors correctly
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
            wordError = true;
        }
    }



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
     * real javadoc will be in final class-see ResultScreen
     * @param sResult
     */
    @Override
    public void onSynonymResultObtained(SynonymResult[] sResult){
        List<SynonymResult> sList = Arrays.asList(sResult);
        Collections.sort(sList);

        TextView textView = (TextView) findViewById(R.id.resultText);
        if(sResult == null){
            String databaseError = getResources().getString(R.string.database_error);
            textView.setText(databaseError);
        }
        else {
            synonymResultList = sList;
            synonymDone = true;
        }
    }

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param statusCode HTTP status code. No result on 200, otherwise error.
     */
    @Override
    public void onSynonymResultFailure(int statusCode){
        synonymError = true;
    }


    public void waitForResults(){
        Runnable runnable = new Runnable() {
            /**
             * Written by Bill and Karl
             * Checks if languages and dictionaries have been obtained
             * if so it sets them
             */
            public void run() {
                Looper.prepare();
                //Deside end time
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
        textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);

        //Creating a new glossary adapter
        ResultsAdapter resultsAdapter = new ResultsAdapter(this, R.layout.results_list, resultList, synonymResultList);
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

    private void combineResults(){
        if(this.synonymResultList!=null && this.synonymResultList.size()>0) {
            for (SynonymResult synonymResult : synonymResultList) {
                Log.v("Synonym", synonymResult.getWord());
                Result result = new Result();
                result.setWord(synonymResult.getSynonym() + " → " + synonymResult.getWord());
                result.setId_term(synonymResult.getTerm_id());
                result.setDictionary_code(synonymResult.getDict_code());
                //TODO: set language from API
                //Just to keep it from crashing, I could remove the language view for it though
                result.setLanguage_code("IS");
                Log.v("RESULT", result.getWord());
                Log.v("Before add", resultList.get(resultList.size() - 1).getWord());
                resultList.add(result);
                Log.v("AFTER Add", resultList.get(resultList.size() - 1).getWord());
            }
            Collections.sort(resultList);
        }
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
