package com.arnastofnun.idordabanki.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;


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


        searchModeListener(rootView);
        return rootView;
    }


    /**
     * A listener that changes the search mode when
     * the radio buttons are pressed
     * @param rootView - the view of the fragment
     */
    public void searchModeListener(View rootView){
        RadioGroup optionsGroup = (RadioGroup) rootView.findViewById(R.id.optionsGroup);

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup radioGroup,int id){
                radioGroup.check(id);
                int searchMode;
                // Check which radio button is active
                switch(id) {
                    case R.id.radioButton:
                        //terms and syns
                        searchMode = 0;
                        break;
                    case R.id.radioButton2:
                        //terms only
                        searchMode = 1;
                        break;
                    case R.id.radioButton3:
                        //synonyms only
                        searchMode = 2;
                        break;
                    case R.id.radioButton4:
                        //declensions
                        searchMode = 3;
                        break;
                    default:
                        searchMode = 0;
                        break;
                }

                Globals globals = (Globals) Globals.getContext();
                globals.setSearchMode(searchMode);
            }
        });
    }







}
