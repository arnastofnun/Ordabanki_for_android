package com.example.cthulhu.ordabankiforandroid;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26/10/14.
 */
public interface OnResultObtainedListener {
    //TODO: javadocs
    public void onResultObtained(Result[] result);
    //TODO: javadocs
    public void onResultFailure(int statusCode);
}
