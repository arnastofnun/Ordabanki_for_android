package com.arnastofnun.idordabanki.interfaces;

//TODO: javadocs

import com.arnastofnun.idordabanki.TermResult;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26.10.2014
 */
public interface OnTermResultObtainedListener {
    //TODO: javadocs
    public void onTermResultObtained(TermResult[] tResult);
    //TODO: javadocs
    public void onTermResultFailure(int statusCode);
}
