package com.example.cthulhu.ordabankiforandroid.interfaces;

import com.example.cthulhu.ordabankiforandroid.Language;

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
