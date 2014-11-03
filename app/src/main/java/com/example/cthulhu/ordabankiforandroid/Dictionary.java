package com.example.cthulhu.ordabankiforandroid;

import static com.example.cthulhu.ordabankiforandroid.Dictionary.DName.localeCode;

/**
 * @author Bill
 * @since 01/11/14.
 */
public class Dictionary {
    String code;
    DName[] dNames;
        public static class DName{
            static String localeCode;
            static String dName;
            public DName(){
                //noargs constructor
            }

        }
    public String getCode(){return code;}
    public String getDName(){
        String locName = null;
        for (DName ignored : dNames) {
            if (localeCode.equals(LocaleSettings.getLanguage())) {
                locName = DName.dName;
                break;
            }
        }
        return locName;
    }
}
