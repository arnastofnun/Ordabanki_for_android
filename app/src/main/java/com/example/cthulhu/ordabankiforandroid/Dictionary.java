package com.example.cthulhu.ordabankiforandroid;


/**
 * @author Bill
 * @since 01/11/14.
 */
public class Dictionary {
    String dict_code;
    String fjoldi;
    Info[] info;
        public static class Info{
            static String lang_code;
            static String dict_name;
            public Info(){
                //noargs constructor
            }

        }
    public String getDictCode(){return dict_code;}
    public String getFjoldi(){return fjoldi;}
    public String getDictName(){
        String locName = null;
        String defaultEN= null;
        String defaultIS = null;
        for (Info anInfo : info) {
            if (anInfo.lang_code.equals("IS")){
                defaultIS = anInfo.dict_name;
            }
            else if (anInfo.lang_code.equals("EN")){
                defaultEN =anInfo.dict_name;
            }
            else if (anInfo.lang_code.equals(LocaleSettings.getLanguage())) {
                locName = anInfo.dict_name;
                break;
            }
        }
        if(locName.equals(null)){
            if(defaultEN.equals(null)) {
                locName = defaultIS;
            }else{
                locName=defaultEN;
            }
        }
        return locName;
    }
}
