package com.example.cthulhu.ordabankiforandroid;

/**
 * Interface for language http calls
 * @author Bill
 */
public interface OnLanguagesObtainedListener {
    public void onLanguagesObtained(Language[] languages);
    public void onLanguagesFailure(int statusCode);
}
