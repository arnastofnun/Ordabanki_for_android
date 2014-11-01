package com.example.cthulhu.ordabankiforandroid;

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
            public static String getLocaleCode(){return localeCode;}
            public static String getLocLangName(){return locLangName;}

        }
    public String getLangCode(){return LangCode;}
    public String getLangName (){
        String name = null;
        for (LangName langName : langNames) {
            if (LangName.getLocaleCode().equals(LocaleSettings.getLanguage())) {
                name = LangName.getLocLangName();
            }
        }
        return name;
        }
}
