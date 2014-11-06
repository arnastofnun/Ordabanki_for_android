package com.example.cthulhu.ordabankiforandroid;


/**
 * Java object for parsing of Dictionary Jsons
 * @author Bill
 * @since 01/11/14.
 */
public class Dictionary {
    String dict_code; //code of the glossary
    String fjoldi; //total number of glossaries
    Info[] info; //Info class containing the glossary names in different languages

    /**
     * Info class that contains language code and
     * dictionary name in that language
     * @author Bill
     * @since 01/11/14
     */
    public  class Info{
            String lang_code;
            String dict_name;

        /**
         * creates a new instance of the Info class
         * Written by Bill
         */
        public Info(){
            //noargs constructor
        }

        }

    /**
     * Written by Bill
     * @return dict_code is the glossary code
     */
    public String getDictCode(){return dict_code;}

    /**
     * Written by Bill
     * @return fjoldi is the total number of glossaries
     */
    public String getFjoldi(){return fjoldi;}

    /**
     * Written by Bill
     * @return dictionary name in language of app
     */
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
            if (anInfo.lang_code.equals(LocaleSettings.returnLanguage())) {
                locName = anInfo.dict_name;
                break;
            }
        }
        if(locName == null){
            if(defaultEN == null) {
                locName = defaultIS;
            }else{
                locName=defaultEN;
            }
        }
        return locName;
    }
}
