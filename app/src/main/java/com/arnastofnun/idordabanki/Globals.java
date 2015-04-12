package com.arnastofnun.idordabanki;

import android.app.Application;
import android.content.Context;

import com.arnastofnun.idordabanki.models.Glossary;
import com.arnastofnun.idordabanki.models.Result;
import com.google.common.collect.BiMap;

import java.util.ArrayList;

/**
 * Holds global variables.
 * @author Bill
 */
public class Globals extends Application{
    private ArrayList<Glossary> glossaryState; //Keeps the selected glossary state between searches
    private String tLangCode = null; //Keeps the position of the selected target language between searches
    private String glossCode = null; //Keeps the position of the selected glossary filter
    private String sLangCode = null; //Keeps the position of the selected source language between searches
    private ArrayList<Result> results;
    private ArrayList<Result> originalResults;
    private int searchMode = 0;
    private BiMap<String,String> filterGlossaries;
    private BiMap<String,String> filterLanguages;
    private String sTerm;
    private Boolean dec;

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

    /**
     * @return the context
     */
    public static Context getContext(){
        return context;
    }

    /**
     * @param results Sets the results
     */
    public void setResults(ArrayList<Result> results){
        this.results = results;
    }

    /**
     * @param results sets the original results
     */
    public void setOriginalResults(ArrayList<Result> results){this.originalResults = results;}

    /**
     * @param i - the search mode
     */
    public void setSearchMode(int i){
        this.searchMode = i;
    }

    /**
     * @return the search mode
     */
    public int getSearchMode(){
        return searchMode;
    }

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
     * @param code the position of the chosen target language
     */
    public void setTLangCode(String code){
        this.tLangCode=code;
    }

    /**
     * Sets the current position of the chosen source language
     * Written by Bill
     * @param pos the position of the chosen source language
     */
    public void setSLangCode(String pos){
        this.sLangCode=pos;
    }

    /**
     * Sets the glossary position of the glossary that should be filtered
     * @param code - the position of the glossary
     */
    public void setGlossCode(String code){
        this.glossCode=code;
    }

    /**
     * @param term the search term
     */
    public void setSTerm(String term) {this.sTerm = term;}



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
     * @return originalResults
     */
    public ArrayList<Result> getOriginalResults(){return originalResults;}


    /**
     * Written by Bill
     * @return tLangPos
     */
    public String getTLangCode(){return tLangCode;}

    /**
     * Written by Bill
     * @return sLangPos
     */
    public String getSLangCode(){return sLangCode;}

    /**
     * @return the glossary position for filtering
     */
    public String getGlossCode(){
        return glossCode;
    }

    /**
     * @param filterGlossaries - the glossaries that can be filtered by
     */
    public void setFilterGlossaries(BiMap<String,String> filterGlossaries){
        this.filterGlossaries = filterGlossaries;
    }

    /**
     * @param filterLanguages the languages that can be filtered by
     */
    public void setFilterLanguages (BiMap<String,String> filterLanguages){
        this.filterLanguages = filterLanguages;
    }

    /**
     * @return the glossaries that can be filtered by
     */
    public BiMap<String,String> getFilterGlossaries(){
        return this.filterGlossaries;
    }

    /**
     * @return the languages that can be filtered by
     */
    public BiMap<String,String> getFilterLanguages(){
        return this.filterLanguages;
    }


    /**
     * @return the search term
     */
    public String getSTerm() {return this.sTerm;}
}
