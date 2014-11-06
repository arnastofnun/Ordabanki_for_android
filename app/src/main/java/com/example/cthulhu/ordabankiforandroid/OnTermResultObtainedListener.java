package com.example.cthulhu.ordabankiforandroid;

//TODO: javadocs
/**
 * Created by cthulhu on 04/11/14.
 */
public interface OnTermResultObtainedListener {
    //TODO: javadocs
    public void onTermResultObtained(TermResult[] tResult);
    //TODO: javadocs
    public void onTermResultFailure(int statusCode);
}
