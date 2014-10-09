package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 09/10/14.
 */
public class URLgen {
    //todo add unit testing
    private String[] languages = {"all", "foo", "bar"};
    private String[] glossaries = {"all", "foo", "bar"};

    private String parse_sLang(int sLangInt) {
        //parse int to str from array of strings for source
        //Placeholder, maybe not necessary
        return languages[sLangInt];

    }

    private String[] parse_tLang(int[] tLangInt) {
        //as source language but for an array of values, 0 in first member
        // of tLangInt -> all
        String[] tLangStr = new String[tLangInt.length];
        for (int i = 0; i < tLangInt.length; i++) {
            tLangStr[i] = languages[tLangInt[i]];
        }
        return tLangStr;
    }

    private String[] parseGloss(int[] glossInt) {
        //as target language but for glossaries
        String[] glossStr = new String[glossInt.length];
        for (int i = 0; i < glossInt.length; i++) {
            glossStr[i] = glossaries[glossInt[i]];
        }
        return glossStr;
    }

    public String createURL(String sTerm, int sLangInt, int[] tLangInt, int[] glossInt) {
        //takes base URL and appends search constraints
        String delim = ";"; //change when find out right delimiter
        String URL = "http://apiordabanki.arnastofnun.is/request.php?word=" + sTerm;
        URL = URL + "/sLang=" + parse_sLang(sLangInt);
        //probably change when see right syntax for request
        URL = URL + "/tLang=";
        String[] tLangStr = parse_tLang(tLangInt);
        for (int i = 0; i < tLangStr.length; i++) {
            if (i < tLangStr.length - 1)
                URL = URL + tLangStr[i] + delim;
            else
                URL = URL + tLangStr[i];
            URL = URL + "/gloss=";
            String[] glossStr = parseGloss(glossInt);
            for (int j = 0; j < glossStr.length; j++) {
                if (i < glossStr.length - 1)
                    URL = URL + glossStr[j] + delim;
                else
                    URL = URL + glossStr[j];

            }

        }
    return URL;
    }
}