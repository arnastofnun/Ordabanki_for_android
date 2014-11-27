package com.arnastofnun.idordabanki.jsonHandlers;

import com.arnastofnun.idordabanki.ResultsInfoFragment;
import com.arnastofnun.idordabanki.TermResult;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handler to parse results from numerical term search or return status code on error/no result.
 * @author Bill
 */
public class TermResultJsonHandler extends JsonHttpResponseHandler {
    /**
     * AnActivity is a placeholder to avoid errors until this is ready to be integrated.
     */    
    ResultsInfoFragment fragment;

    /**
     * initialisation
     * @param fragment the fragment that calls the handler
     */
    public TermResultJsonHandler(ResultsInfoFragment fragment)
    {
        this.fragment = fragment;
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
        TermResult[] tResult =  gson.fromJson(response.toString(), TermResult[].class);
        fragment.onTermResultObtained(tResult);
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
        fragment.onTermResultFailure(statusCode);
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
        fragment.onTermResultFailure(statusCode);
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
        fragment.onTermResultFailure(statusCode);
    }
}
