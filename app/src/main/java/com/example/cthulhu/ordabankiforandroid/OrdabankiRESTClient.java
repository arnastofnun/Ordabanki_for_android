package com.example.cthulhu.ordabankiforandroid;
import com.loopj.android.http.*;

import org.json.JSONException;
//13/10/14.


/**
 * <h1>Orðabanki REST Client</h1>
 * <p><A simple REST client to get and post to the orðabanki API/p>
 * ----------------------------------------------------------------
 * @author Bill
 * @since 13.10.2014
 */
public class OrdabankiRESTClient {
    //initialize
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * send a get request
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(url, params, responseHandler);
    }


    /**
     * send a post request
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }


}
