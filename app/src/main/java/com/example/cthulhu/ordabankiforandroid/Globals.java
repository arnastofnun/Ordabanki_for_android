package com.example.cthulhu.ordabankiforandroid;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by cthulhu on 04/11/14.
 */
public class Globals extends Application{
    private ArrayList<ArrayList<String>> languages;
    private ArrayList<Glossary> dictionaries;

    public void setLanguages(ArrayList<ArrayList<String>> langs){
     this.languages=langs;
    }
    public void setDictionaries(ArrayList<Glossary> dicts){
        this.dictionaries=dicts;
    }
    public ArrayList<ArrayList<String>>getLanguages(){return languages;}
    public ArrayList<Glossary>getDictionaries(){return dictionaries;}
}
