package com.example.cthulhu.ordabankiforandroid;

import org.json.JSONException;

/**
 * Created by cthulhu on 03/11/14.
 */
public class DictionaryRestClientUsage {
    public void getDictionaries(String dictURL, DictionaryJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(dictURL, null, jsonHandler);
    }
}
