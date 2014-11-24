package com.example.cthulhu.ordabankiforandroid.interfaces;

//TODO: javadocs

import com.example.cthulhu.ordabankiforandroid.TermResult;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26/10/14.
 */
public interface OnTermResultObtainedListener {
    //TODO: javadocs
    public void onTermResultObtained(TermResult[] tResult);
    //TODO: javadocs
    public void onTermResultFailure(int statusCode);
}
