package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.Dictionary;

/**
 * Interface for dictionary http handler
 *  @author Bill
 * @since 01.11.2014
 */
public interface OnDictionariesObtainedListener {
    //TODO: javadocs
    public void onDictionariesObtained(Dictionary[] dictionaries);
    //TODO: javadocs
    public void onDictionariesFailure(int statusCode);
}
