package com.arnastofnun.idordabanki;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Holds global variables.
 * @author Bill
 */
public class Globals extends Application{
    private ArrayList<ArrayList<String>> languages; //List of all languages in the current language and their codes
    private ArrayList<Glossary> dictionaries; //List of all glossaries, set in the current language
    private ArrayList<ArrayList<String>> loc_dictionaries; //List of all dictionary names in the current language and their codes
    private ArrayList<Glossary> glossaryState; //Keeps the selected glossary state between searches
    private int tLangPos = -1; //Keeps the position of the selected target language between searches
    private int sLangPos = -1; //Keeps the position of the selected source language between searches
    private ArrayList<Result> results;
    private int searchMode = 0;


    public static Context context;

    /**
     * A method to get the context
     * in a easy way
     */
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    public void setResults(ArrayList<Result> results){
        this.results = results;
    }

    public void setSearchMode(int i){
        this.searchMode = i;
    }

    public int getSearchMode(){
        return searchMode;
    }

    /**
     * Sets the source and target languages to the current language
     * Written by Bill
     * @param langs List of all languages in the current language and their codes
     */
    public void setLanguages(ArrayList<ArrayList<String>> langs){
     this.languages=langs;
    }

    /**
     * Sets the dictionaries to the current language
     * Written by Bill
     * @param dicts List of all glossaries, set in the current language
     */
    public void setDictionaries(ArrayList<Glossary> dicts){
        this.dictionaries=dicts;
    }

    /**
     * Sets the loc_dictionaries to the current language
     * Written by Karl Ásgeir Geirsson
     * @param dicts List of all dictionary names in the current language and their codes
     */
    public void setLocalizedDictionaries(ArrayList<ArrayList<String>> dicts){this.loc_dictionaries=dicts;}

    /**
     * Sets the glossary state
     * Written by Bill
     * @param glossaryList the current glossary list
     */
    public void setGlossaryState(ArrayList<Glossary> glossaryList){
        this.glossaryState=glossaryList;
    }

    /**
     * Sets the current position of the chosen target language
     * Written by Bill
     * @param pos the position of the chosen target language
     */
    public void setTLangPos(int pos){
        this.tLangPos=pos;
    }

    /**
     * Sets the current position of the chosen source language
     * Written by Bill
     * @param pos the position of the chosen source language
     */
    public void setSLangPos(int pos){
        this.sLangPos=pos;
    }

    /**
     * Written by Bill
     * @return languages
     */
    public ArrayList<ArrayList<String>>getLanguages(){return languages;}

    /**
     * Written by Bill
     * @return loc_dictionaries
     */
    public ArrayList<ArrayList<String>> getLoc_dictionaries(){return loc_dictionaries;}

    /**
     * Written by Bill
     * @return dictionaries
     */
    public ArrayList<Glossary>getDictionaries(){return dictionaries;}

    /**
     * Written by Bill
     * @return glossaryState
     */
    public ArrayList<Glossary>getGlossaryState(){return glossaryState;}


    /**
     *Written by Karl Ásgeir Geirsson
     * @return results
     */
    public ArrayList<Result> getResults(){
        return results;
    }


    /**
     * Written by Bill
     * @return tLangPos
     */
    public int getTLangPos(){return tLangPos;}

    /**
     * Written by Bill
     * @return sLangPos
     */
    public int getSLangPos(){return sLangPos;}
}
