package com.example.cthulhu.ordabankiforandroid;

import android.content.SearchRecentSuggestionsProvider;

/**
 * @author Trausti
 * @since 24.10.2014.
 */
public class SearchAutoComplete extends SearchRecentSuggestionsProvider {
   /*
   todo create methods for autocomplete for search that will use AutoCompleteTextview.
   REFERENCE:
    http://developer.android.com/reference/android/widget/AutoCompleteTextView.html
   EXAMPLES:
    http://www.tutorialspoint.com/android/android_autocompletetextview_control.htm
    */
    public final static String AUTHORITY = "com.example.cthulhu.ordabankiforandroid.SearchAutoComplete";
    public final static int MODE= DATABASE_MODE_QUERIES;

    public SearchAutoComplete(){
        setupSuggestions(AUTHORITY,MODE);
    }


}
