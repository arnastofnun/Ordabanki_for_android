package com.example.cthulhu.ordabankiforandroid;

import org.json.JSONException;

/**
 * Creates instance of rest client
 * Created by Bill on 26/10/14
 */
public class OrdabankiRestClientUsage {
    /**
     * creates instance of rest client
     * @param relURL relative URL to be searched
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void setResults(String relURL, OrdabankiJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(relURL, null, jsonHandler);
    }
    public void getLanguages(String langURL, LanguageJsonHandler jsonHandler) throws JSONException{
        OrdabankiRESTClient.get(langURL,null,jsonHandler);
    }
    public void getDictionaries(String dictURL, DictionaryJsonHandler jsonHandler) throws JSONException{
        OrdabankiRESTClient.get(dictURL, null, jsonHandler);
    }
}
