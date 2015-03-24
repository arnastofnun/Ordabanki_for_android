package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.models.Dictionary;

/**
 * Interface for dictionary http handler
 *  @author Bill
 * @since 01.11.2014
 */
public interface OnDictionariesObtainedListener {
    //TODO: javadocs
    void onDictionariesObtained(Dictionary[] dictionaries);
    //TODO: javadocs
    void onDictionariesFailure(int statusCode);
}
