package com.example.cthulhu.ordabankiforandroid;

import com.loopj.android.http.*;
import org.apache.http.Header;
import org.json.*;
import com.google.gson.*;
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
        ResultFields resultsJava = new ResultFields();
        switch(resultType) {
            case 0:
                //throw exception?
                break;
            case 1:
                //parse resultsJSONObject with GSON and return
                resultType = 0;
                break;
            case 2:
                //parse resultsJSONArray with GSON and return
                //reset result type
                resultType = 0;
                break;
        }
        return resultsJava;
    }

    public ResultFields getSearchResult(String sTerm, int sLangInt, int[] tLangInt, int[] glossInt) throws JSONException {

        String relURL = URLgen.createURL(sTerm, sLangInt, tLangInt,glossInt);
        RequestParams params = new RequestParams();
        setResultsJSON(relURL,params);
        ResultFields resultsJava = parseResults();
        return resultsJava;
    }
}




