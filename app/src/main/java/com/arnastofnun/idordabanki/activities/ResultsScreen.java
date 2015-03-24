package com.arnastofnun.idordabanki.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.arnastofnun.idordabanki.dialogs.HelpDialog;
import com.arnastofnun.idordabanki.models.Result;
import com.arnastofnun.idordabanki.models.SynonymResult;
import com.arnastofnun.idordabanki.preferences.LocaleSettings;
import com.arnastofnun.idordabanki.preferences.Settings;
import com.arnastofnun.idordabanki.sync.OrdabankiURLGen;
import com.arnastofnun.idordabanki.sync.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.adapters.ResultListAdapter;
import com.arnastofnun.idordabanki.dialogs.FilterDialog;
import com.arnastofnun.idordabanki.filters.GlossaryFilter;
import com.arnastofnun.idordabanki.filters.TargetLangFilter;
import com.arnastofnun.idordabanki.interfaces.OnResultObtainedListener;
import com.arnastofnun.idordabanki.interfaces.OnSynonymResultObtainedListener;
import com.arnastofnun.idordabanki.sync.jsonHandlers.OrdabankiJsonHandler;
import com.arnastofnun.idordabanki.sync.jsonHandlers.SynonymResultJsonHandler;
import com.google.common.collect.Collections2;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class implements functions for the results screen
 * ------------------------------------------------------
 * @author Trausti
 * @since 08.10.2014
 */
public class ResultsScreen extends Activity implements OnResultObtainedListener, OnSynonymResultObtainedListener {
    private Globals global = (Globals) Globals.getContext(); //Get a reference to the globals
    private String searchQuery; //The search query
    private ExpandableListView listView; //The list view
    private ArrayList<Result> resultList; //The list of results
    private HashMap<String, ArrayList<Result>> resultMap; //A hash map combining results with the same word
    private ArrayList<String> words; //List of words (headers for the group list items)
    private ArrayList<SynonymResult> synonymResultList; //List containing the synonym results
    //Booleans that help with synchronization
    private boolean wordDone = false;
    private boolean synonymDone = false;
    private boolean wordError = false;
    private boolean synonymError = false;
    //Holds the search mode
    private String searchString;
    /**
     * Takes search term from intent and passes to Rest client
     * Bill
     * @param savedInstanceState saved instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setCurrentTheme(this);
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_results_screen);
        setContentView(R.layout.activity_results_screen);
        LocaleSettings localeSettings = new LocaleSettings(this);
        localeSettings.setCurrLocaleFromPrefs();

        resultList = global.getOriginalResults(); //Get the result list from globals
        //Get the data from the intent

        Bundle data = getIntent().getExtras();
        boolean newSearch = false;
        if(data != null && data.containsKey("newSearch")){
            newSearch = data.getBoolean("newSearch");
        }
        //If it doesn't exist this is a new search
        if(resultList == null || newSearch){
            if(data != null && data.containsKey("searchQuery")) {
                searchQuery = data.getString("searchQuery");
            }
            global.setSTerm(searchQuery);
            //If the searchQuery is an integer we go straight to term search
            if(isInteger(searchQuery)){
                doTermIdSearch(searchQuery);
            }else{
                //Search in the right search mode
                switch(global.getSearchMode()) {
                    case 0:
                        doWordSearch(searchQuery);
                        doSynonymSearch(searchQuery);
                        break;

                    case 1:
                        doWordSearch(searchQuery);
                        synonymDone = true;
                        synonymResultList=new ArrayList<>();
                        break;

                    case 2:
                        doSynonymSearch(searchQuery);
                        wordDone = true;
                        resultList = new ArrayList<>();
                        break;
                }
            }
        }else{
            //We display the results that we had already gotten
            displayListView();
        }

        waitForResults();
    }

    /**
     * A method that runs on restart of the activity
     */
    @Override
    protected void onRestart(){
        super.onRestart();
        searchQuery = global.getSTerm();
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
        resultList = new ArrayList<>(Arrays.asList(resultArr));
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
        synonymResultList = new ArrayList<>(Arrays.asList(sResult));
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
             * both synonym and word search is done
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
                        setHashMap();
                        displayListView();
                    }
                });
            }
        };
        //Start a new thread with the runnable
        Thread timingThread = new Thread(runnable);
        timingThread.start();
    }

    @Override
    protected void onPause() {
        Log.v("sterm",searchQuery);
        global.setSTerm(searchQuery);
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(global.getSTerm() != null) {
            searchQuery = global.getSTerm();
            Log.v("sterm", searchQuery);
        }
        super.onResume();
    }

    /**
     * A method that displays the list view
     */
    public void displayListView(){
        String tLangCode = global.getTLangCode();
        String glossCode = global.getGlossCode();
        filterResults(glossCode,tLangCode);
        setHashMap();
        //Display the number of results
        String searchPreTerm = getResources().getString(R.string.searchpreterm);
        TextView textView = (TextView) findViewById(R.id.resultText);
        int resultsCount = resultList.size();
        if(searchQuery!=null){
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchQuery);
        }else {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null && bundle.containsKey("searchString")) {
                searchString = getIntent().getExtras().getString("searchString");
            } else{
                searchString = global.getSTerm();
            }
            textView.setText(resultsCount + " " + searchPreTerm + " " + searchString);
        }


        //Find the list view
        listView = (ExpandableListView) findViewById(R.id.resultsList);

        //Create and add the adapter to the list view
        final ExpandableListAdapter listAdapter = new ResultListAdapter(this,resultMap,words);
        listView.setAdapter(listAdapter);

        listView.setGroupIndicator(null); //Hide the automatic indicator

        //An on click listener on each child
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            /**
             * A method that goes to the results info screen on child click
             * @param parent the parent view
             * @param v the view of the current child
             * @param groupPosition the position of the group
             * @param childPosition the position of the child
             * @param id the id of the child
             * @return true
             */
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                global.setResults(resultList);
                Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                intent.putExtra("selectedResult", id);
                startActivity(intent);
                return true;
            }
        });

        //An on click listener on each group
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            /**
             * A method that either goes to the results info screen, if the group only
             * contains one glossary, else it displays the sublist
             * @param parent the parent view
             * @param v the view of the group
             * @param groupPosition the group position
             * @param id the id of the group
             * @return true
             */
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //If sublist only has one item
                if(resultMap.get(words.get(groupPosition)).size() == 1){
                    //We open the results info screen
                    global.setResults(resultList);
                    Intent intent = new Intent(ResultsScreen.this, ResultInfo.class);
                    intent.putExtra("selectedResult",id);
                    if(searchQuery != null){
                        intent.putExtra("searchQuery",searchQuery);
                    }else{
                        intent.putExtra("searchQuery",searchString);
                    }
                    startActivity(intent);
                }
                else{
                    //We expand/collapse the group
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
            resultList = removeDuplicates(resultList);
            FilterDialog.setFilterPossibilities(resultList);
            global.setOriginalResults(resultList);
        }
    }

    /**
     * A method to remove duplicates from the result list
     * @param list - the list that should remove duplicates
     * @return the list without duplicates
     */
    public ArrayList<Result> removeDuplicates(ArrayList<Result> list){
        ArrayList<Result> result = new ArrayList<>();
        for(int i=1;i<list.size();i++){
            if(!list.get(i-1).equals(list.get(i))) {
                result.add(list.get(i-1));
            }
        }
        if(!list.get(list.size()-1).equals(list.get(list.size()-2))){
            result.add(list.get(list.size()-1));
        }
        return result;

    }


    /**
     * A method that groups items with the same word,
     * but different glossaries into a hash map
     * with the word as key, and a list of glossaries as
     * item
     */
    private void setHashMap(){
        //initialize
        words = new ArrayList<>();
        resultMap = new HashMap<>();
        //Go through the result list
        for(Result item : resultList){
            //If the hash map doesn't contain the word, we need to add it
            if (!resultMap.containsKey(item.getWord())) {
                ArrayList<Result> tempList = new ArrayList<>();
                tempList.add(item);
                resultMap.put(item.getWord(), tempList);
                words.add(item.getWord());
            } else {
                //Add to the list
                resultMap.get(item.getWord()).add(item);
            }
        }
    }


    private void filterResults(String glossaryCode, String languageCode){
        ArrayList<Result> rList = global.getOriginalResults();
        String allLangString = getResources().getString(R.string.all_languages);
        if(rList!=null) {
            if (glossaryCode != null &&!glossaryCode.equals(allLangString)) {
                GlossaryFilter glossaryFilter = new GlossaryFilter(glossaryCode);
                rList = new ArrayList<>(Collections2.filter(rList, glossaryFilter));
            }

            if (languageCode != null && !languageCode.equals(allLangString)) {
                TargetLangFilter langFilter = new TargetLangFilter(languageCode);
                rList = new ArrayList<>(Collections2.filter(rList, langFilter));
            }
            resultList = rList;
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
     * Handle the back button press
     */
    @Override
    public void onBackPressed() {
        handleBackPress();
    }

    private void handleBackPress(){
        global.setResults(null);
        global.setOriginalResults(null);
        Intent intent = new Intent(this, SearchScreen.class);
        this.startActivity(intent);
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

            case R.id.action_filter:
                FilterDialog filterDialog = new FilterDialog(this,this.getLayoutInflater());
                filterDialog.show();
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
                handleBackPress();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
