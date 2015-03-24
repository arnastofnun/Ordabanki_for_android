package com.arnastofnun.idordabanki.models;


import android.support.annotation.NonNull;

import com.arnastofnun.idordabanki.preferences.LocaleSettings;

/**
 * Java object for parsing of language Jsons
 * @author Bill
 */
public class Language implements Comparable<Language>{
    String code; //The language code
    Info[] info; //List of language codes and name of language in that language code

    /**
     * Info class that holds the
     * language code and name of languages in different languages
     * @author Bill
     */
    public class Info{
        String lang_code;
        String lang_name;

        /**
         * Creates a new instance of the Info class
         * Written by Bill
         */
        public Info(){
            //noarg
        }
    }

    /**
     * Written by Bill
     * usage: String lang_code = language.getLangCode();
     * @return the language code
     */
    public String getLangCode(){return code;}

    /**
     * Written by Bill
     * usage: String lang_name = language.getLangName();
     * @return language name in language of app
     */
    public String getLangName(){
        String locName = null;
        String defaultEN= null;
        String defaultIS = null;
        for (Info anInfo : info) {
            if (anInfo.lang_code.equals("IS")){
                defaultIS = anInfo.lang_name;
            }
            else if (anInfo.lang_code.equals("EN")){
                defaultEN = anInfo.lang_name;
            }
            if (anInfo.lang_code.equals(LocaleSettings.returnLanguage())) {
                locName = anInfo.lang_name;
                break;
            }
        }
        if(locName==null){
            if(defaultEN==null) {
                locName = defaultIS;
            }else{
                locName=defaultEN;
            }
        }
        return locName;
    }

    /**
     * Written by Karl Ásgeir Geirsson
     * usage: int cmp = lang.compareTo(language2);
     * @param l is the language that this language should be compared to
     * @return returns an integer the alphabetical order of the languages
     */
    public int compareTo(@NonNull Language l){
            return getLangName().compareTo(l.getLangName());
    }

}
