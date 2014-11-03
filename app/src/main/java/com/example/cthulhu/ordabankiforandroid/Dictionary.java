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
        String locName = "";
        String defaultEN= "";
        String defaultIS = "";
        for (Info ignored : info) {
            if (Info.lang_code.equals("IS")){
                defaultIS = Info.dict_name;
            }
            else if (Info.lang_code.equals("EN")){
                defaultEN =Info.dict_name;
            }
            else if (Info.lang_code.equals(LocaleSettings.getLanguage())) {
                locName = Info.dict_name;
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
