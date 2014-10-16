package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


 //Created by karlasgeir on 9.10.2014.

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
    //Data invarians:
    //sourceSpinner and targetSpinner are UI components;drop down menu list
    //sourceSpinner contains source language selections
    //targetSpinner contains target language selections
    
    private Spinner sourceSpinner;
    private Spinner targetSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_search_screen,container,false);

        //Get the source language spinner
        sourceSpinner = (Spinner) rootView.findViewById(R.id.sourceSpinner);
        //Create a list to place the source languages in
        List<String> listSource = new ArrayList<String>();

        //Making "All" the first item
        listSource.add(getResources().getString(R.string.all_languages));
        //Adding some placeholder values until we get the API
        //todo add source languages from api
        listSource.add("example");
        listSource.add("spinner");

        //Create an array adapter to put the source list into the spinner
        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item,listSource);
        dataAdapterSource.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the source language spinner
        sourceSpinner.setAdapter(dataAdapterSource);

        //Add items to target language dropdown spinner
        targetSpinner = (Spinner) rootView.findViewById(R.id.targetSpinner);
        //Create a list to place the target languages in
        List<String> listTarget = new ArrayList<String>();
        //Making "All" the first item
        listTarget.add(getResources().getString(R.string.all_languages));
        //Adding some placeholder values until we get the API
        //todo add target languages from api
        listTarget.add("example");
        listTarget.add("spinner");

        //Create an array adapter to put the target list into the spinner
        ArrayAdapter<String> dataAdapterTarget = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item,listTarget);
        dataAdapterTarget.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the target language spinner
        targetSpinner.setAdapter(dataAdapterTarget);



        return rootView;
    }




}
