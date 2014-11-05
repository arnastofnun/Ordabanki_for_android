package com.example.cthulhu.ordabankiforandroid;


import java.util.ArrayList;
import java.util.ListIterator;

/**

 * This class creates URLs to be passed to the client from paramters within the app
 *
 * Created by cthulhu on 13/10/14.
 * <h1>OrðabankiURLGen</h1>
 * <p>Creates URLs to be passed into the rest client</p>
 * @author Bill
 * @since 13.10.14

 */
class OrdabankiURLGen {
    /**
     * creates url with only search term as a parameter. Default later maybe?
     * @param sTerm search term
     * @return relative url
     */
    final static String baseURL = "http://api.arnastofnun.is/ordabanki.php?";

    public static String createWordOnlyURL(String sTerm){
        //takes base URL and appends search term
        final String baseURL = "http://api.arnastofnun.is/ordabanki.php?word=";
        return baseURL+ sTerm;
    }
    //use: createURL(sTerm,sLang,tLang)
    //pre: sTerm,sLang,tLang are strings, they form the search query
    //post: returns the url for search results, a string

    /**
     * creates URL with all parameters
     * @param sTerm search term
     * @return relative url
     */
    public static String createWordURL(String sTerm) {
        //takes base URL and appends search term
        return appendParams(baseURL + "word="+sTerm);
    }

    public static String createTermURL(String sTerm){
        return appendParams(baseURL + "term="+sTerm);
    }

    public static String createSynonymURL(String sTerm){
        return appendParams(baseURL +"synonym="+sTerm);
    }

    public static String appendParams(String relURL){
        final String delim = ",";
        if (!ChooseLanguagesFragment.getSourceLanguage().equals("ALL"))
            relURL = relURL + "&slang="+ChooseLanguagesFragment.getSourceLanguage();
            /* commented until target language implemented in api
        if(!ChooseLanguagesFragment.getTargetLanguage().equals("ALL"))
            relURL = relURL + "&tlang=" + ChooseLanguagesFragment.getTargetLanguage();
             */
        if(!PickGlossaryFragment.areAllSelected()) {
                relURL = relURL + "&dicts=";
                ArrayList<String> selectedGlossaries = PickGlossaryFragment.getSelectedGlossaries();
        if (selectedGlossaries.size() == 1) {
            relURL = relURL + selectedGlossaries.get(0);
        } else {
            ListIterator<String> it = selectedGlossaries.listIterator();
            //iterate until last but one member,
            while (it.hasNext() && it.nextIndex() != selectedGlossaries.size() - 1) {
                relURL = relURL + it.next() + delim;
            }
            relURL = relURL + selectedGlossaries.get(selectedGlossaries.size() - 1);
        }
    }
    return relURL+"&agent=ordabankaapp";
    }
}



