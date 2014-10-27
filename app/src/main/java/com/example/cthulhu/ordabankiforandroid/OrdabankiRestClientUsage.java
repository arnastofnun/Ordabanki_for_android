package com.example.cthulhu.ordabankiforandroid;

import org.json.JSONException;

/**
 * Created by cthulhu on 26/10/14.
 */
public class OrdabankiRestClientUsage {
    public void setResults(String relURL, OrdabankiJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(relURL, null, jsonHandler);


    }
}
