package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 01/11/14.
 */
public interface OnDictionariesObtainedListener {
    public void onDictionariesObtained(Dictionary[] dictionaries);
    public void onDictionariesFailure(int statusCode);
}
