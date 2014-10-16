package com.example.cthulhu.ordabankiforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * This class sets creates the search screen UI
 * Created by karlasgeir on 9.10.2014.
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

        sourceSpinner = (Spinner) rootView.findViewById(R.id.sourceSpinner);
        List<String> listSource = new ArrayList<String>();
        listSource.add("All");
        listSource.add("example");
        listSource.add("spinner");

        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item,listSource);

        dataAdapterSource.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);


        sourceSpinner.setAdapter(dataAdapterSource);

        //Add items to target language dropdown spinner
        targetSpinner = (Spinner) rootView.findViewById(R.id.targetSpinner);
        List<String> listTarget = new ArrayList<String>();
        listTarget.add("All");
        listTarget.add("example");
        listTarget.add("spinner");

        ArrayAdapter<String> dataAdapterTarget = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item,listTarget);

        dataAdapterTarget.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        targetSpinner.setAdapter(dataAdapterTarget);



        return rootView;
    }




}
