package com.example.cthulhu.ordabankiforandroid;

/**
 * Interface for dictionary http handler
 *  @author Bill
 * @since 01/11/14.
 */
public interface OnDictionariesObtainedListener {
    //TODO: javadocs
    public void onDictionariesObtained(Dictionary[] dictionaries);
    //TODO: javadocs
    public void onDictionariesFailure(int statusCode);
}
