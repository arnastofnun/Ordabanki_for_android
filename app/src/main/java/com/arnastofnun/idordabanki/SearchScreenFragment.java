package com.arnastofnun.idordabanki;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


/**
 * <h1>Search Screen fragment</h1>
 * <p>This class contains the methods for the search screen fragment</p>
 * <p>It is supposed to get the source and target languages from the API and
 * display them in the source and target language spinners</p>
 * <p>For now it just puts placeholder values on the spinners</p>
 * -------------------------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 09.10.2014
 */
public class SearchScreenFragment extends Fragment {

    /**
     * Runs when created
     * @param savedInstanceState saved instances
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Runs when view is created
     * @param inflater the layout inflater
     * @param container the ViewGroup
     * @param savedInstanceState saved instances
     * @return the view that was created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Inflate the layout
        View rootView = inflater.inflate(R.layout.fragment_search_screen,container,false);

        //Get the search view and the search manager
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) rootView.findViewById(R.id.searchView);

        //Set up the search view
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);


        return rootView;
    }




}
