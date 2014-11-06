package com.example.cthulhu.ordabankiforandroid;

/**
 * Interface for language http calls
 * @author Bill
 */
public interface OnLanguagesObtainedListener {
    //TODO: javadocs
    public void onLanguagesObtained(Language[] languages);
    //TODO: javadocs
    public void onLanguagesFailure(int statusCode);
}
