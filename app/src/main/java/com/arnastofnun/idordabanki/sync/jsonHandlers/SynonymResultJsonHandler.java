package com.arnastofnun.idordabanki.sync.jsonHandlers;

import com.arnastofnun.idordabanki.activities.ResultsScreen;
import com.arnastofnun.idordabanki.models.SynonymResult;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handler to parse results from synonym search or return status code on error/no result.
 * @author Bill
 * @since 04.11.2014
 */
public class SynonymResultJsonHandler extends JsonHttpResponseHandler{
    ResultsScreen activity;

    /**
     * initialisation
     * @param activity parent activity
     */
    public SynonymResultJsonHandler(ResultsScreen activity)
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
        SynonymResult[] sResult =  gson.fromJson(response.toString(), SynonymResult[].class);
        activity.onSynonymResultObtained(sResult);
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
        if(throwable.getMessage().contains("Unterminated object")){
            activity.onSynonymResultFailure(0);
        }
        else {
            activity.onSynonymResultFailure(statusCode);
        }
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
        if(throwable.getMessage().contains("Unterminated object")){
            activity.onSynonymResultFailure(0);
        }
        else {
            activity.onSynonymResultFailure(statusCode);
        }
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
        if(throwable.getMessage().contains("Unterminated object")){
            activity.onSynonymResultFailure(0);
        }
        else {
            activity.onSynonymResultFailure(statusCode);
        }
    }

}
