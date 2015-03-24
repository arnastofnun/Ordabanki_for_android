package com.arnastofnun.idordabanki.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.common.collect.BiMap;

import java.util.ArrayList;
import java.util.Collections;


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
    private static Spinner targetSpinner;
    private Globals g = (Globals) Globals.getContext();



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
        targetSpinner = (Spinner) rootView.findViewById(R.id.targetSpinner);
        //Create a list to place the source languages in
        ArrayList<String> listSource = new ArrayList<>();
        ArrayList<String> listTarg  = new ArrayList<>();

        //Making "All" the first item
        listSource.add(getResources().getString(R.string.all_languages));
        listTarg.add(getResources().getString(R.string.all_languages));
        BiMap<String,String> langList = SharedPrefs.getStringBiMap("languages");
        ArrayList<String>  allLanguages = new ArrayList<>();
        if(langList != null) {
            allLanguages.addAll(langList.values());
        }
        //Make sure icelandic and english are the top languags
        listTarg.add(langList.get("IS"));
        listTarg.add(langList.get("EN"));
        listSource.add(langList.get("IS"));
        listSource.add(langList.get("EN"));
        allLanguages.remove(langList.get("IS"));
        allLanguages.remove(langList.get("EN"));
        //Sort the languages list
        Collections.sort(allLanguages);
        //Add to spinner lists
        listTarg.addAll(allLanguages);
        listSource.addAll(allLanguages);

        //Create an array adapter to put the lists into the spinners
        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<>
                (this.getActivity(), R.layout.spinner_item,listSource);
        dataAdapterSource.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        //Set the adapters to the spinners
        sourceSpinner.setAdapter(dataAdapterSource);

        ArrayAdapter<String> dataAdapterTarget = new ArrayAdapter<> (this.getActivity(),R.layout.spinner_item,listTarg);
        dataAdapterTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpinner.setAdapter(dataAdapterTarget);

        return rootView;
    }

    /**
     * Saves state to globals when fragment loses focus
     * Written by Bill
     */
    @Override
    public void onPause(){
        super.onPause();
        //Get from globals
        BiMap<String,String> languages = SharedPrefs.getStringBiMap("languages");
        //Get the items from the spinners
        String tLang = targetSpinner.getSelectedItem().toString();
        String sLang = sourceSpinner.getSelectedItem().toString();
        //Check for all selected, and send go globals
        String allLangString = getResources().getString(R.string.all_languages);
        if(!tLang.equals(allLangString)) {
            g.setTLangCode(languages.inverse().get(tLang));
        } else{
            g.setTLangCode(allLangString);
        }
        if(!sLang.equals(allLangString)) {
            g.setSLangCode(languages.inverse().get(sLang));
        } else{
            g.setSLangCode(allLangString);
        }
    }

    /**
     * restores state from globals when fragment returns to focus
     * Written by Bill
     */
    @Override
    public void onResume(){
        super.onResume();

        //Get from globals
        BiMap<String,String> languages = SharedPrefs.getStringBiMap("languages");

        String allLangString = getResources().getString(R.string.all_languages);

        //Get the language codes
        String tLangCode = g.getTLangCode();
        String sLangCode = g.getSLangCode();

        //Set the correct position of the target language spinner
        if(tLangCode != null && !g.getTLangCode().equals(allLangString)) {
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> tAdapter = (ArrayAdapter<String>) targetSpinner.getAdapter();
            int tLangPos = tAdapter.getPosition(languages.get(g.getTLangCode()));
            targetSpinner.setSelection(tLangPos);
        }
        //Set the correct position of the source language spinner
        if(sLangCode != null && !g.getSLangCode().equals(allLangString)){
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> sAdapter = (ArrayAdapter<String>) sourceSpinner.getAdapter();
            int sLangPos = sAdapter.getPosition(languages.get(g.getSLangCode()));
            sourceSpinner.setSelection(sLangPos);
        }
    }

    /**
     * Written by Bill
     * @return language code for source language
     */
    public static String getSourceLanguage(){
        Globals globals = (Globals) Globals.getContext();
        if(sourceSpinner == null || sourceSpinner.getSelectedItemPosition() == 0) {return "ALL";}
        else {
            return SharedPrefs.getStringBiMap("languages").inverse().get(sourceSpinner.getSelectedItem().toString());
        }
    }

}
