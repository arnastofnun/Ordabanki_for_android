package com.example.cthulhu.ordabankiforandroid;



import com.google.gson.Gson;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by cthulhu on 26/10/14.
 */
public class OrdabankiJsonHandler extends JsonHttpResponseHandler {

    ResultsScreen activity;

    public OrdabankiJsonHandler(ResultsScreen activity)
    {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response){
        Gson gson = new Gson();
        Result[] result =  gson.fromJson(response.toString(), Result[].class);
        activity.onResultObtained(result);
    }
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
        activity.onResultFailure(statusCode);
    }

}
