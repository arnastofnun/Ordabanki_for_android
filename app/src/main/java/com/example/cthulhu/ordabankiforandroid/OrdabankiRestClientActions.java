package com.example.cthulhu.ordabankiforandroid;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
class OrdabankiRestClientActions {

    //holder for JSON results to work around enforced void return typing of onSuccess
    private static Result[] result;
//    private static Result resultObj;

    //use: setResultsJSON(relURL,params);
    //pre: relUrl is a String, params is a RequestParams
    //post: fills resultArr with results for search query if connection is successful,

    public static void setResults(String relURL, RequestParams params) throws JSONException {
        OrdabankiRESTClient.get(relURL, params, new JsonHttpResponseHandler() {

/*            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                resultObj = gson.fromJson(response.toString(), Result.class);
            }*/

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                result = gson.fromJson(response.toString(), Result[].class);
            }
        });
    }


    private static String createWordOnlyURL(String sTerm){
            //takes base URL and appends search constraints
            final String baseURL = "http://apiordabanki.arnastofnun.is/request.php?word=";
            return baseURL+ sTerm;
    }
    //use: createURL(sTerm,sLang,tLang)
    //pre: sTerm,sLang,tLang are strings, they form the search query
    //post: returns the url for search results, a string
    private static String createURL(String sTerm, String sLang, String tLang) {
        //takes base URL and appends search constraints
        final String delim = ";"; //change when find out right delimiter
        final String baseURL = "http://apiordabanki.arnastofnun.is/request.php?word=";
        String relURL = baseURL+ sTerm;
        relURL = relURL + "&sLang="+sLang;
        //change when see right syntax for request, format is right
        relURL = relURL + "&tLang=" + tLang;
        relURL = relURL + "&gloss=";
        ArrayList<String> selectedGlossaries = PickGlossaryFragment.getSelectedGlossaries();
        if (selectedGlossaries.size()==1) {relURL=relURL+selectedGlossaries.get(0);}
        else {
            ListIterator<String> it = selectedGlossaries.listIterator();
            //iterate until last but one member,
            while(it.hasNext()&& it.nextIndex()!=selectedGlossaries.size()-1){
                relURL= relURL+ it.next()+delim;
            }
            relURL = relURL+selectedGlossaries.get(selectedGlossaries.size()-1);
        }
        return relURL;
    }

    //use: setSearchResult(sTerm,sLang,tLang)
    //pre: sTerm,sLang,tLang are strings, they form the search query
    //post: returns an ArrayList that contain the search results
    public static void setSearchResult(String sTerm, String sLang, String tLang) throws JSONException {

        String relURL = createURL(sTerm, sLang, tLang);
        RequestParams params = new RequestParams();
        setResults(relURL, params);

    }

    public static void setSearchResultWordOnly(String sTerm) throws JSONException {

        String relURL = createWordOnlyURL(sTerm);
        RequestParams params = new RequestParams();
        setResults(relURL, params);

    }
    public static Result[] getResult(){
        return result;
    }
}



