package com.example.cthulhu.ordabankiforandroid;


import java.util.ArrayList;
import java.util.ListIterator;

/**

 * This class handles queries and parses data received after search query has been made to DB.
 *
 * Created by cthulhu on 13/10/14.
 * <h1>Orðabanki Rest Client Actions</h1>
 * <p>This class parses data received after a search query has been made to the Ordabanki DB.</p>
 * @author Bill
 * @since 13.10.14

 */
class OrdabankiURLGen {


//    private static Result resultObj;

    //use: setResultsJSON(relURL,params);
    //pre: relUrl is a String, params is a RequestParams
    //post: fills resultArr with results for search query if connection is successful,

/*    public static void setResults(String relURL) throws JSONException {
        OrdabankiRESTClient.get(relURL, null, OrdabankiJsonHandler jsonHandler);


    }*/


    public static String createWordOnlyURL(String sTerm){
        //takes base URL and appends search constraints
        final String baseURL = "http://api.arnastofnun.is/ordabanki.php?word=";
        sTerm = sTerm.replaceAll("\\*", "%25");
        sTerm = sTerm.replaceAll("\\?", "%5F");
        return baseURL+ sTerm;
    }
    //use: createURL(sTerm,sLang,tLang)
    //pre: sTerm,sLang,tLang are strings, they form the search query
    //post: returns the url for search results, a string
    public static String createURL(String sTerm) {
        //takes base URL and appends search constraints
        final String delim = "&"; //change when find out right delimiter
        final String baseURL = "http://api.arnastofnun.is/ordabanki.php?word=";
        sTerm = sTerm.replaceAll("\\*", "%");
        sTerm = sTerm.replaceAll("\\?", "_");
        String relURL = baseURL+ sTerm;
        relURL = relURL + "&sLang="+ChooseLanguagesFragment.getSourceLanguage();
        //change when see right syntax for request, format is right
        relURL = relURL + "&tLang=" + ChooseLanguagesFragment.getTargetLanguage();
        relURL = relURL + "&gloss=";
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

        return relURL;
    }
/*
    //use: setSearchResult(sTerm,sLang,tLang)
    //pre: sTerm,sLang,tLang are strings, they form the search query
    //post: returns an ArrayList that contain the search results
    public static void setSearchResult(String sTerm, OrdabankiJsonHandler jsonHandler) throws JSONException {

        String relURL = createURL(sTerm);
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();
        //RequestParams params = new RequestParams();
        client.setResults(relURL, jsonHandler);

    }

    public static void setSearchResultWordOnly(String sTerm, OrdabankiJsonHandler jsonHandler) throws JSONException {

        String relURL = createWordOnlyURL(sTerm);
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();
        client.setResults(relURL, jsonHandler);

    }

    public static void setSearchResultTestPage(OrdabankiJsonHandler jsonHandler) throws JSONException {


        //RequestParams params = new RequestParams();
        OrdabankiRestClientUsage client = new OrdabankiRestClientUsage();


    }
*/
}



