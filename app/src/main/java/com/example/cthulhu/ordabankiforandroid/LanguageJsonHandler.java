package com.example.cthulhu.ordabankiforandroid;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Bill
 * @since 01/11/14.
 */
public class LanguageJsonHandler extends JsonHttpResponseHandler{

        SplashActivity activity;

        /**
         * initialisation
         * @param activity parent activity
         */
        public LanguageJsonHandler(SplashActivity activity)
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
            Log.v("SUCCESS","HERE");
            Language[] languages =  gson.fromJson(response.toString(), Language[].class);
            activity.onLanguagesObtained(languages);
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
            activity.onLanguagesFailure(statusCode);
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
            activity.onLanguagesFailure(statusCode);
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
            activity.onLanguagesFailure(statusCode);
        }
}

