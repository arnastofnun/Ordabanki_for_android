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
        }
    public String getLangCode(){return LangCode;}
    public String getLangName (){
        String name = null;
        for (LangName ignored : langNames) {
            if (LangName.localeCode.equals(LocaleSettings.getLanguage())) {
                name = LangName.locLangName;
            }
        }
        return name;
        }
}
