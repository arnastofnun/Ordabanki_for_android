package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 01/11/14.
 */
public class Language {
    String LangCode;
    LangName[] langNames;
        public static class LangName{
            String localeCode;
            String locLangName;
            public LangName(){
                //noargs constructor
            }
            public String getLocaleCode(){return localeCode;}
            public String getLocLangName(){return locLangName;}

        }
    public String getLangCode(){return LangCode;}
    public LangName[]getLangNames(){return langNames;}
}
