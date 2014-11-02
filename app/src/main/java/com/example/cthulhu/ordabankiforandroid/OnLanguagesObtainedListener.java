package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 01/11/14.
 */
public interface OnLanguagesObtainedListener {
    public void onLanguagesObtained(Language[] languages);
    public void onLanguagesFailure(int statusCode);
}
