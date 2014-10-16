package com.example.cthulhu.ordabankiforandroid;
import com.loopj.android.http.*;
/**
 * This class makes an Aynchronous connection to the Orðabanki DB with the REST protocol
 * Created by cthulhu on 13/10/14.
 */
public class OrdabankiRESTClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }
}
