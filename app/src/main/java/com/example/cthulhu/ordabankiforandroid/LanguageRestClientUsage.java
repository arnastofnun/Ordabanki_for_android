package com.example.cthulhu.ordabankiforandroid;

import org.json.JSONException;

/**
 * Created by cthulhu on 03/11/14.
 */
public class LanguageRestClientUsage {
    public void getLanguages(String langURL, LanguageJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(langURL,null,jsonHandler);
    }
}
