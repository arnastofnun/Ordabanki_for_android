package com.arnastofnun.idordabanki.filters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.Result;
import com.arnastofnun.idordabanki.ResultsScreen;
import com.arnastofnun.idordabanki.ThemeHelper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This class creates a filter dilaog for the
 * result list
 * @author karlasgeir
 * @since 3/19/15.
 */
public class FilterDialog {
    ResultsScreen activity; //The context that calls the dialog
    LayoutInflater layoutInflater; //The layout inflater
    Globals globals = (Globals) Globals.getContext(); //Global variables

    public FilterDialog(ResultsScreen activity, LayoutInflater layoutInflater){
        super();
        //Set variables
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    /**
     * A method that builds the custom dialog
     * Written by Karl Ásgeir Geirsson
     * @return returns the built dialog
     */
    private AlertDialog buildDialog() {

        //Construct the builder
        AlertDialog.Builder filterDialog = new AlertDialog.Builder(activity);
        //Inflate the view
        View view = layoutInflater.inflate(R.layout.filter_dialog, null);
        filterDialog.setView(view);

        //Get the spinners
        final Spinner glossSpinner = (Spinner) view.findViewById(R.id.filterGlossarySpinner);
        final Spinner langSpinner = (Spinner) view.findViewById(R.id.filterLanguageSpinner);
        //Get the lists from globals
        final BiMap<String,String> glossList = globals.getFilterGlossaries();
        final BiMap<String,String> langList = globals.getFilterLanguages();

        //Create new lists for the spinners
        ArrayList<String> glossNameList = new ArrayList<>();
        ArrayList<String> langNameList =  new ArrayList<>();
        //Create temporary lists
        ArrayList<String> glossaryNames = new ArrayList<>(glossList.values());
        ArrayList<String> languageNames = new ArrayList<>(langList.values());
        //Add all on top of the lists
        glossNameList.add(activity.getResources().getString(R.string.all_languages));
        langNameList.add(activity.getResources().getString(R.string.all_languages));
        //Put icelandic and english as the top two languages
        if(langList.containsKey("IS")) {
            langNameList.add(langList.get("IS"));
            languageNames.remove(langList.get("IS"));
        }
        if(langList.containsKey("EN")){
            langNameList.add(langList.get("EN"));
            languageNames.remove(langList.get("EN"));
        }

        //Sort the lists
        Collections.sort(glossaryNames);
        Collections.sort(languageNames);
        //Add the lists to the spinner lists
        glossNameList.addAll(glossaryNames);
        langNameList.addAll(languageNames);

        //Create an array adapter to put the lists into the spinners
        final ArrayAdapter<String> glossaryAdapter = new ArrayAdapter<>(activity, R.layout.spinner_item, glossNameList);
        final ArrayAdapter<String> langAdapter = new ArrayAdapter<>(activity, R.layout.spinner_item, langNameList);
        glossaryAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to spinners
        glossSpinner.setAdapter(glossaryAdapter);
        langSpinner.setAdapter(langAdapter);

        //Set the correct position of the spinner based on previously selected values
        String allLangString = activity.getResources().getString(R.string.all_languages);
        String glossCode = globals.getGlossCode();
        String langCode = globals.getTLangCode();
        if(glossCode != null && !glossCode.equals(allLangString)){
            int glossPos = glossaryAdapter.getPosition(glossList.get(glossCode));
            glossSpinner.setSelection(glossPos);
        }
        if(langCode != null && !langCode.equals(allLangString)){
            int langPos = langAdapter.getPosition(langList.get(langCode));
            langSpinner.setSelection(langPos);
        }


        //Set cancel button
        filterDialog.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        //set accept button
        filterDialog.setPositiveButton(R.string.settings_changelanguage_confirm,new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int id){
               //Get the lists
               BiMap<String,String> glossaries = globals.getLoc_dictionaries();
               BiMap<String,String> languages = globals.getLanguages();
               //Get the selected items from the spinners
               String glossCode = glossSpinner.getSelectedItem().toString();
               String langCode = langSpinner.getSelectedItem().toString();
               //Set to globals
               globals.setGlossCode(glossaries.inverse().get(glossCode));
               globals.setTLangCode(languages.inverse().get(langCode));
               //Display the filtered result list
               activity.displayListView();
           }
        });

        //Create and show the dialog
        return filterDialog.create();
    }


    /**
     *  This method shows the dialog
     *  Written by: Karl Ásgeir Geirsson
     */
    public void show(){
        //build and show the dialog
        AlertDialog dialog = buildDialog();
        dialog.show();
        //Setup buttons
        Button dismissButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        //change the colors to fit the theme
        changeButtonColor(dismissButton);
        changeButtonColor(positiveButton);
    }

    /**
     * This method changes the color of a button
     * Written by: Karl Ásgeir Geirsson
     * @param button the button to change color
     */
    public void changeButtonColor(Button button){
        if(button != null){
            ThemeHelper themeHelper = new ThemeHelper(activity);
            if(button != null){
                button.setBackgroundColor(themeHelper.getAttrColor(R.attr.secondaryBackgroundColor));
                button.setTextColor(themeHelper.getAttrColor(R.attr.secondaryTextColor));
            }
        }
    }


    /**
     * A static method to set up the
     * lists of possible filters
     * @param resultList - the result
     */
    public static void setFilterPossibilities(ArrayList<Result> resultList){
        //Get the lists from globals
        Globals globals = (Globals) Globals.getContext();
        BiMap<String,String> langList = globals.getLanguages();
        BiMap<String,String> glossList = globals.getLoc_dictionaries();

        //Create new maps
        BiMap<String,String> filtLangList = HashBiMap.create();
        BiMap<String,String> filtGlossList = HashBiMap.create();

        //Add only languages and glossaries that are in the result
        for(Result item:resultList){
            if(!filtLangList.containsKey(item.getLanguage_code())){
                filtLangList.put(item.getLanguage_code(),langList.get(item.getLanguage_code()));
            }
            if(!filtGlossList.containsKey(item.getDictionary_code())){
                filtGlossList.put(item.getDictionary_code(),glossList.get(item.getDictionary_code()));
            }
        }
        //Set to globals
        globals.setFilterGlossaries(filtGlossList);
        globals.setFilterLanguages(filtLangList);
    }

}
