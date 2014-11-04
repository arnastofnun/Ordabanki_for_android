package com.example.cthulhu.ordabankiforandroid;


/**
 * @author Bill
 * @since 01/11/14.
 */
public class Dictionary {
    String dict_code;
    int fjoldi;
    Info[] info;
        public static class Info{
            static String lang_code;
            static String dict_name;
            public Info(){
                //noargs constructor
            }
        public  String getLangCode(){return lang_code;}
        public  String getDictName(){return dict_name;}
        }
    public String getDictCode(){return dict_code;}
    public int getFjoldi(){return fjoldi;}
    public Info[] getInfo(){return info;}
}
