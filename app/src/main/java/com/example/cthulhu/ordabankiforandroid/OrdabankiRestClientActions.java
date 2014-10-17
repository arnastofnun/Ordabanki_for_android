package com.example.cthulhu.ordabankiforandroid;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class parses data received after a search query has been made to the Ordabanki DB.
 *
 * Created by cthulhu on 13/10/14. not default any more, go away last yellow block
 */

class OrdabankiRestClientActions {

    //holder for JSON results to work around enforced void return typing of onSuccess
    private static ArrayList<Result> resultArr = new ArrayList<Result>();

    
     /**
     * use: setResultsJSON(relURL,params);
     * @param relUrl is of type String
     * @param params is of type RequestParams
     * @return nothing, fills resultArr with results for search query if connection is successful,
     */
    public static void setResultsJSON(String relURL, RequestParams params) throws JSONException {
        OrdabankiRESTClient.get(relURL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                String jsonStr = response.toString();
                Result resultObj = gson.fromJson(jsonStr, Result.class);
                ArrayList<Result> tempArr = new ArrayList<Result>();
                tempArr.add(resultObj);
                resultArr = tempArr;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //more to do in this one need to see format of json arrays

            }
        });
    }


    /**
    * use: createURL(sTerm,sLang,tLang)
    * @param sTerm is a string, part of the search query
    * @param sLang is a string, part of the search query
    * @param tLang is a string, part of the search query
    * @return url for search results, a string
    */
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
        if (selectedGlossaries.size()==1) {relURL=relURL+selectedGlossaries.get(0)+".json";}
        else {
            Iterator<String> iterator = selectedGlossaries.iterator();
            //iterate until last but one member, There is probably a less verbose way to do this
            while(iterator.hasNext()&&!iterator.next().equals(selectedGlossaries.get(selectedGlossaries.size()-1))){
                relURL= relURL+ iterator.next()+delim;
            }
            relURL = relURL+selectedGlossaries.get(selectedGlossaries.size()-1)+".json";
        }
        return relURL;
    }
    
    /**
    * use: setSearchResult(sTerm,sLang,tLang)
    * @param sTerm is a string, part of the search query
    * @param sLang is a string, part of the search query
    * @param tLang is a string, part of the search query
    * @return  an ArrayList that contains the search results
    */
    public static void setSearchResult(String sTerm, String sLang, String tLang) throws JSONException {

        String relURL = createURL(sTerm, sLang, tLang);
        RequestParams params = new RequestParams();
        setResultsJSON(relURL,params);

    }
    public static ArrayList<Result> getResultArray(){
        return resultArr;
    }
}



