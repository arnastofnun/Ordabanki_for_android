package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class ChooseLanguagesFragment extends Fragment {
    //Data invarians:
    //sourceSpinner and targetSpinner are UI components;drop down menu list
    //sourceSpinner contains source language selections
    //targetSpinner contains target language selections

    private static Spinner sourceSpinner;
    private static Spinner targetSpinner;
    private static ArrayList<String> codeRef= new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_choose_languages, container, false);

        //Get the source language spinner
        sourceSpinner = (Spinner) rootView.findViewById(R.id.sourceSpinner);
        //Create a list to place the source languages in
        ArrayList<String> listSource = new ArrayList<String>();

        //Making "All" the first item
        listSource.add(getResources().getString(R.string.all_languages));
        Globals g = (Globals)this.getActivity().getApplication();
        listSource.addAll(g.getLanguages().get(1));
        codeRef.addAll(g.getLanguages().get(0));


        //Adding some placeholder values until we get the API

        //Source languages
        //String[] src_langs = getString(R.string.src_languages).split("-");

        //Target languages
        //String[] target_langs = getString(R.string.target_languages).split("-");

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
        ArrayList<String> listTarget = new ArrayList<String>();
        //Making "All" the first item
        listTarget.add(getResources().getString(R.string.all_languages));
        listTarget.addAll(g.getLanguages().get(1));
        //Adding some placeholder values until we get the API


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
        else {
            int index =targetSpinner.getSelectedItemPosition()-1;
            return codeRef.get(index);
        }
    }

    public static String getTargetLanguage(){
        if(targetSpinner == null) {return "ALL";}
        else {
            int index =targetSpinner.getSelectedItemPosition()-1;
            return codeRef.get(index);
        }
    }





}
