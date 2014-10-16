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
 * Created by cthulhu on 13/10/14.
 */

class OrdabankiRestClientActions {
    //holders for JSON results to work around enforced void return typing of onSuccess
    private int resultType = 0;
    private JSONArray resultsJSONArray = new JSONArray();
    private JSONObject resultsJSONObject = new JSONObject();

    public void setResultsJSON(String relURL, RequestParams params) throws JSONException {
        OrdabankiRESTClient.get(relURL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                resultsJSONObject = response;
                resultType = 1;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                resultsJSONArray = response;
                resultType=2;
            }
        });
    }
    //parse resultsJSON with GSON and return
    private ResultFields parseResults() {
        Gson gson = new Gson();
        ResultFields resultsJava = new ResultFields();
        switch(resultType) {
            case 0:
                //throw exception?
                break;
            case 1:
                //parse resultsJSONObject with GSON and return
                //resultsJava = gson.fromJson();
                resultType = 0;
                break;
            case 2:
                //parse resultsJSONArray with GSON and return
                //reset result type
                //resultsJava = gson.fromJson(resultsJSONArray, ResultFields.class);
                resultType = 0;
                break;
        }
        return resultsJava;
    }
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
            while(iterator.hasNext()&&!iterator.next().equals(selectedGlossaries.get(selectedGlossaries.size()-1))){
                relURL= relURL+ iterator.next()+delim;
            }
            relURL = relURL+selectedGlossaries.get(selectedGlossaries.size()-1)+".json";
        }
        return relURL;
    }
    public ResultFields getSearchResult(String sTerm, String sLang, String tLang) throws JSONException {

        String relURL = createURL(sTerm, sLang, tLang);
        RequestParams params = new RequestParams();
        setResultsJSON(relURL,params);
        ResultFields resultsJava = parseResults();
        return resultsJava;
    }
}




