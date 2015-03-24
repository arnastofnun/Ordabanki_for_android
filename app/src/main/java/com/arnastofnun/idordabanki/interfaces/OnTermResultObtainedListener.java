package com.arnastofnun.idordabanki.interfaces;

//TODO: javadocs

import com.arnastofnun.idordabanki.models.TermResult;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26.10.2014
 */
public interface OnTermResultObtainedListener {
    //TODO: javadocs
    void onTermResultObtained(TermResult[] tResult);
    //TODO: javadocs
    void onTermResultFailure(int statusCode);
}
