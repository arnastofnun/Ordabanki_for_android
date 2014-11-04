package com.example.cthulhu.ordabankiforandroid;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by cthulhu on 04/11/14.
 */
public class Globals extends Application{
    private ArrayList<ArrayList<String>> languages;
    private ArrayList<Glossary> dictionaries;
    private ArrayList<Glossary> glossaryState;
    private int tLangPos;
    private int sLangPos;

    public void setLanguages(ArrayList<ArrayList<String>> langs){
     this.languages=langs;
    }
    public void setDictionaries(ArrayList<Glossary> dicts){
        this.dictionaries=dicts;
    }
    public void setGlossaryState(ArrayList<Glossary> glossaryList){
        this.glossaryState=glossaryList;
    }
    public void setTLangPos(int pos){
        this.tLangPos=pos;
    }
    public void setSLangPos(int pos){
        this.sLangPos=pos;
    }
    public ArrayList<ArrayList<String>>getLanguages(){return languages;}
    public ArrayList<Glossary>getDictionaries(){return dictionaries;}
    public ArrayList<Glossary>getGlossaryState(){return glossaryState;}
    public int getTLangPos(){return tLangPos;}
    public int getSLangPos(){return sLangPos;}
}
