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


public class ChooseLanguagesFragment extends Fragment {
    //Data invarians:
    //sourceSpinner and targetSpinner are UI components;drop down menu list
    //sourceSpinner contains source language selections
    //targetSpinner contains target language selections

    private static Spinner sourceSpinner;
    private static Spinner targetSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_choose_languages, container, false);

        //Get the source language spinner
        sourceSpinner = (Spinner) rootView.findViewById(R.id.sourceSpinner);
        //Create a list to place the source languages in
        List<String> listSource = new ArrayList<String>();

        //Making "All" the first item
        listSource.add(getResources().getString(R.string.all_languages));
        //Adding some placeholder values until we get the API
        //todo add source languages from api
        listSource.add("Icelandic");
        listSource.add("English");

        //Create an array adapter to put the source list into the spinner
        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<String>
                (this.getActivity(), R.layout.spinner_item,listSource);
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
        listTarget.add("Icelandic");
        listTarget.add("English");


        //Create an array adapter to put the target list into the spinner
        ArrayAdapter<String> dataAdapterTarget = new ArrayAdapter<String>
                (this.getActivity(), R.layout.spinner_item,listTarget);
        dataAdapterTarget.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the target language spinner
        targetSpinner.setAdapter(dataAdapterTarget);


        return rootView;
    }


    public static String getSourceLanguage(){
        if(sourceSpinner == null) {return "ALL";}
        else {return sourceSpinner.getSelectedItem().toString();}
    }

    public static String getTargetLanguage(){
        if(targetSpinner == null) {return "ALL";}
        else {return targetSpinner.getSelectedItem().toString();}
    }





}
