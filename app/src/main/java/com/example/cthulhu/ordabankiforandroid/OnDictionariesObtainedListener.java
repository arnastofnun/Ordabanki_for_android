package com.example.cthulhu.ordabankiforandroid;

/**
 * @author Bill
 * @since 01/11/14.
 */
public interface OnDictionariesObtainedListener {
    public void onDictionariesObtained(Dictionary[] dictionaries);
    public void onDictionariesFailure(int statusCode);
}
