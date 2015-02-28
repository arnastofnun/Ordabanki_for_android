package com.arnastofnun.idordabanki;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.adapters.ResultListAdapter;
import com.arnastofnun.idordabanki.interfaces.OnResultObtainedListener;
import com.arnastofnun.idordabanki.interfaces.OnSynonymResultObtainedListener;
import com.arnastofnun.idordabanki.jsonHandlers.OrdabankiJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.SynonymResultJsonHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
    private ExpandableListView listView;
    private ArrayList<Result> resultList;
    private HashMap<String, ArrayList<Result>> resultMap;
    private ArrayList<String> words;
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
        setTitle(R.string.title_activity_results_screen);
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
            setHashMap(resultList);
            displayListView();
        }


        waitForResults();
    }

    @Override
    protected void onRestart(){
        displayListView();
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
                        setHashMap(resultList);
                        displayListView();
                    }
                });
            }
        };
        //Start a new thread with the runnable
        Thread timingThread = new Thread(runnable);
        timingThread.start();
    }



    private void displayListView(){
        String searchPreTerm = getResources().getString(R.string.searchpreterm);
        TextView textView = (TextView) findViewById(R.id.resultText);
        int resultsCount = resultList.size();
        if(searchQuery!=null){
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);
        }else{
            String topResultWord = resultList.get(0).getWord();
            textView.setText(resultsCount + " " + searchPreTerm + " " + topResultWord);
        }

        listView = (ExpandableListView) findViewById(R.id.resultsList);
        final ExpandableListAdapter listAdapter = new ResultListAdapter(this,resultMap,words);
        listView.setAdapter(listAdapter);

        //setGroupIndicatorToRight(listView);
        listView.setGroupIndicator(null);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                global.setResults(resultList);
                Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                intent.putExtra("selectedResultIndex",parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition)));
                startActivity(intent);
                return true;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(resultMap.get(words.get(groupPosition)).size() == 1){
                    global.setResults(resultList);
                    Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                    intent.putExtra("selectedResultIndex",parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition)));
                    startActivity(intent);
                }
                else{
                    if(listView.isGroupExpanded(groupPosition)){
                        listView.collapseGroup(groupPosition);
                    }
                    else{
                        listView.expandGroup(groupPosition);
                    }
                }

                return true;
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

    private void setHashMap(ArrayList<Result> resultlist){
        /**
         * My attempt to group together the results
         */
        resultMap = new HashMap<String, ArrayList<Result>>();
        words = new ArrayList<String>();
        for(int i=0;i<resultList.size();i++) {
            Result item = resultList.get(i);

            if (!resultMap.containsKey(item.getWord())) {
                ArrayList<Result> tempList = new ArrayList<>();
                tempList.add(item);
                resultMap.put(item.getWord(), tempList);
                words.add(item.getWord());
            } else {
                resultMap.get(item.getWord()).add(item);
            }
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
