package com.example.cthulhu.ordabankiforandroid;

import android.content.SearchRecentSuggestionsProvider;

/**
 * @author Trausti
 * @since 24.10.2014.
 */
public class SearchAutoComplete extends SearchRecentSuggestionsProvider {
    
    public final static String AUTHORITY = "com.example.cthulhu.ordabankiforandroid.SearchAutoComplete";
    public final static int MODE= DATABASE_MODE_QUERIES;

    public SearchAutoComplete(){
        setupSuggestions(AUTHORITY,MODE);
    }


}
