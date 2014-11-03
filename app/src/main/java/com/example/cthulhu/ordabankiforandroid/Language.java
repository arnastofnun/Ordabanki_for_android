package com.example.cthulhu.ordabankiforandroid;



/**
 * Created by cthulhu on 01/11/14.
 */
public class Language {
    String code;
    Info[] info;
        public static class Info{
            static String lang_code;
            static String lang_name;
            public Info(){
                //noargs constructor
            }
        }
    public String getLangCode(){return code;}
    public String getLangName(){
        String locName = "";
        String defaultEN= "";
        String defaultIS = "";
        for (Info ignored : info) {
            if (Info.lang_code.equals("IS")){
                defaultIS = Info.lang_name;
            }
            else if (Info.lang_code.equals("EN")){
                defaultEN = Info.lang_name;
            }
            else if (Info.lang_code.equals(LocaleSettings.getLanguage())) {
                locName = Info.lang_name;
                break;
            }
        }
        if(locName.equals("")){
            if(defaultEN.equals("")) {
                locName = defaultIS;
            }else{
                locName=defaultEN;
            }
        }
        return locName;
    }
}
