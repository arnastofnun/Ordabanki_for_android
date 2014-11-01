package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 01/11/14.
 */
public class Dictionary {
    String code;
    DName[] dNames;
        public static class DName{
            String localeCode;
            String name;
            public DName(){
                //noargs constructor
            }
            public String getLocaleCode(){return localeCode;}
            public String getName(){return name;}
        }
    public String getCode(){return code;}
    public DName[]getDNames(){return dNames;}
}
