package com.arnastofnun.idordabanki.REST;

import com.arnastofnun.idordabanki.jsonHandlers.DictionaryJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.LanguageJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.OrdabankiJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.SynonymResultJsonHandler;
import com.arnastofnun.idordabanki.jsonHandlers.TermResultJsonHandler;

import org.json.JSONException;

/**
 * Creates instances of rest client for different database calls
 * @author Bill 
 * @since 26/10/14
 */
public class OrdabankiRestClientUsage {
    /**
     * creates instance of rest client for word search
     * @param relURL relative word URL from URLGen to be searched
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void setResults(String relURL, OrdabankiJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(relURL, null, jsonHandler);
    }

    /**
     * creates instance of rest client for call to dictionary list
     * @param dictURL dictionary list URL
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void getDictionaries(String dictURL, DictionaryJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(dictURL, null, jsonHandler);
    }

    /**
     * creates instance of rest client for call to language list
     * @param langURL language list URL
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void getLanguages(String langURL, LanguageJsonHandler jsonHandler) throws JSONException {
        OrdabankiRESTClient.get(langURL,null,jsonHandler);
    }

    /**
     * creates instance of rest client for numerical term search
     * @param termURL numerical term search URL from URLGen
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void setTermResults(String termURL, TermResultJsonHandler jsonHandler) throws JSONException{
        OrdabankiRESTClient.get(termURL, null, jsonHandler);
    }

    /**
     * creates instance of rest client for synonym search
     * @param synURL synonym URL from URLGen
     * @param jsonHandler json handler instance
     * @throws JSONException
     */
    public void setSynonymResults(String synURL, SynonymResultJsonHandler jsonHandler) throws JSONException{
        OrdabankiRESTClient.get(synURL, null, jsonHandler);
    }

}
