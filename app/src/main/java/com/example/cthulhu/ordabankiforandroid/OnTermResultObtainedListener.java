package com.example.cthulhu.ordabankiforandroid;

//TODO: javadocs
/**
 * Listener interface for rest client
 * Created by Bill on 26/10/14.
 */
public interface OnTermResultObtainedListener {
    //TODO: javadocs
    public void onTermResultObtained(TermResult[] tResult);
    //TODO: javadocs
    public void onTermResultFailure(int statusCode);
}
