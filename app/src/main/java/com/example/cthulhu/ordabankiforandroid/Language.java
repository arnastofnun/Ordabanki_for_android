package com.example.cthulhu.ordabankiforandroid;

import static com.example.cthulhu.ordabankiforandroid.Language.LangName.*;

/**
 * Created by cthulhu on 01/11/14.
 */
public class Language {
    String LangCode;
    LangName[] langNames;
        public static class LangName{
            static String localeCode;
            static String locLangName;
            public LangName(){
                //noargs constructor
            }
        }
    public String getLangCode(){return LangCode;}
    public String getLangName (){
        String lName = null;
        for (LangName ignored : langNames) {
            if (localeCode.equals(LocaleSettings.getLanguage())) {
                lName = locLangName;
                break;
            }
        }
        return lName;
        }
}
