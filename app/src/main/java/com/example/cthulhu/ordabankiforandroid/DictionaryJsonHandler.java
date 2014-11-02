package com.example.cthulhu.ordabankiforandroid;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by cthulhu on 01/11/14.
 */
public class DictionaryJsonHandler extends JsonHttpResponseHandler{

    AnActivity activity;

    /**
     * initialisation
     * @param activity parent activity
     */
    public DictionaryJsonHandler(AnActivity activity)
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
        Dictionary[] dictionaries =  gson.fromJson(response.toString(), Dictionary[].class);
        activity.onDictionariesObtained(dictionaries);
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
        activity.onDictionariesFailure(statusCode);
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
        activity.onDictionariesFailure(statusCode);
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
        activity.onDictionariesFailure(statusCode);
    }
}

