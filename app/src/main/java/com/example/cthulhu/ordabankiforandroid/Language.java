package com.example.cthulhu.ordabankiforandroid;


/**
 * Created by cthulhu on 01/11/14.
 */
public class Language {
    String code;
    Info[] info;
        public static class Info{
            String lang_code;
            String lang_name;
            public Info(){
                //noargs constructor
            }
            public String getLangCode(){return lang_code;}
            public String getLangName(){return lang_name;}
        }
    public String getCode(){return code;}
    public Info[] getInfo(){return info;}

}
