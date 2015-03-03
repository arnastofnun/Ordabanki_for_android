package com.arnastofnun.idordabanki;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A fragment that allows you to choose source and target language
 * @author Trausti
 * @since  ?
 */
public class ChooseLanguagesFragment extends Fragment {
    //Data invariants:
    //sourceSpinner and targetSpinner are UI components;drop down menu list
    //sourceSpinner contains source language selections
    //targetSpinner contains target language selections

    private static Spinner sourceSpinner;
    //private static Spinner targetSpinner;
    private static ArrayList<String> codeRef= new ArrayList<String>();



    /**
     * Runs when view is created
     * Written by Trausti and Karl
     * @param inflater the layout inflater
     * @param container the containing viewgroup
     * @param savedInstanceState the saved instance
     * @return the view that has been created
     */
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

        //Create an array adapter to put the source list into the spinner
        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<String>
                (this.getActivity(), R.layout.spinner_item,listSource);
        dataAdapterSource.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the source language spinner
        sourceSpinner.setAdapter(dataAdapterSource);
        /*
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
        */

        return rootView;
    }

    /**
     * Saves state to globals when fragment loses focus
     * Written by Bill
     */
    @Override
    public void onPause(){
        super.onPause();
        Globals g = (Globals) this.getActivity().getApplication();
        //g.setTLangPos(targetSpinner.getSelectedItemPosition());
        g.setSLangPos(sourceSpinner.getSelectedItemPosition());
    }

    /**
     * restores state from globals when fragment returns to focus
     * Written by Bill
     */
    @Override
    public void onResume(){
        super.onResume();
        Globals g = (Globals) this.getActivity().getApplication();
        /*
        if(g.getTLangPos() != -1) {
            targetSpinner.setSelection(g.getTLangPos());
        }
        */
        if(g.getSLangPos() != -1){
            sourceSpinner.setSelection(g.getSLangPos());
        }
    }

    /**
     * Written by Bill
     * @return language code for source language
     */
    public static String getSourceLanguage(){
        if(sourceSpinner == null || sourceSpinner.getSelectedItemPosition() == 0) {return "ALL";}
        else {
            int index =sourceSpinner.getSelectedItemPosition()-1;
            return codeRef.get(index);
        }
    }

    /**
     * Written by Bill
     * @return language code for target language
     */
   /*
    public static String getTargetLanguage(){
        if(targetSpinner == null || targetSpinner.getSelectedItemPosition() == 0) {return "ALL";}
        else {
            int index =targetSpinner.getSelectedItemPosition()-1;
            return codeRef.get(index);
        }
    }
    */





}