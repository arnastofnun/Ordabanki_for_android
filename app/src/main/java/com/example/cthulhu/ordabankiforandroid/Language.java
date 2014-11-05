package com.example.cthulhu.ordabankiforandroid;


/**
 * Created by cthulhu on 01/11/14.
 */
public class Language {
    String code;
    Info[] info;
        public class Info{
            String lang_code;
            String lang_name;
            public Info(){
                //noargs constructor
            }
        }
    public String getLangCode(){return code;}
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

}
