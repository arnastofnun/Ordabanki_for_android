package com.example.cthulhu.ordabankiforandroid;

import android.content.SearchRecentSuggestionsProvider;

/**
 * This class holds information necessary to get the search suggestion working
 * @author Karl Ásgeir Geirsson
 * @since 24.10.2014.
 */
public class SearchAutoComplete extends SearchRecentSuggestionsProvider {
    //Setup variables
    public final static String AUTHORITY = "com.example.cthulhu.ordabankiforandroid.SearchAutoComplete";
    public final static int MODE= DATABASE_MODE_QUERIES;

    /**
     * Sets up suggestions with the correct settings
     */
    public SearchAutoComplete(){
        setupSuggestions(AUTHORITY,MODE);
    }


}
