package com.example.cthulhu.ordabankiforandroid;
import android.app.Application;
/**
 * Created by cthulhu on 04/11/14.
 */
public class Globals extends Application{
    private String[][] languages;
    private String[][] dictionaries;

    public void setLanguages(String[][] langs){
     this.languages=langs;
    }
    public void setDictionaries(String[][]dicts){
        this.dictionaries=dicts;
    }
    public String[][]getLanguages(){return languages;}
    public String[][]getDictionaries(){return dictionaries;}
}
