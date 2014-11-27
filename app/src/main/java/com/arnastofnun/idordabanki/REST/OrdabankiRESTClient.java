package com.arnastofnun.idordabanki.REST;
import com.loopj.android.http.*;

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
     * @param url url to connect to
     * @param params parameters object
     * @param responseHandler response handler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(url, params, responseHandler);
    }


    /**
     * send a post request
     * @param url url to connect to
     * @param params parameters object
     * @param responseHandler response handler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }


}
