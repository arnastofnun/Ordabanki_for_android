package com.arnastofnun.idordabanki.jsonHandlers;



import com.arnastofnun.idordabanki.Result;
import com.arnastofnun.idordabanki.ResultsScreen;
import com.google.gson.Gson;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handles raw Json data or returns HTTP status code if there is an error
 * @author Bill
 * @since 26/10/14.
 */
public class OrdabankiJsonHandler extends JsonHttpResponseHandler {

    ResultsScreen activity;

    /**
     * initialisation
     * @param activity parent activity
     */
    public OrdabankiJsonHandler(ResultsScreen activity)
    {
        this.activity = activity;
    }

    /**
     * Parses raw Json result and returns to activity
     * @param statusCode HTTP status code
     * @param headers headers array
     * @param response raw Json response
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response){
        Gson gson = new Gson();
        Result[] result =  gson.fromJson(response.toString(), Result[].class);
        activity.onResultObtained(result);
    }

    /**
     * All onFailure methods only return HTTP status code to activity
     * @param statusCode HTTP status code
     * @param headers headers array
     * @param throwable thrown exception
     * @param errorResponse Json array response (error)
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
        activity.onResultFailure(statusCode);
    }

    /**
     *
     * @param statusCode HTTP status code
     * @param headers headers array
     * @param responseString string response (error)
     * @param throwable thrown exception
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
        activity.onResultFailure(statusCode);
    }
/*    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable){
        activity.onResultFailure(statusCode);
    }*/

    /**
     *
     * @param statusCode HTTP status code
     * @param headers headers array
     * @param throwable thrown exception
     * @param errorResponse Json object response (error)
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
        activity.onResultFailure(statusCode);
    }
}
