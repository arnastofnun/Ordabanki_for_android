package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.Result;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26.10.2014
 */
public interface OnResultObtainedListener {
    //TODO: javadocs
    public void onResultObtained(Result[] result);
    //TODO: javadocs
    public void onResultFailure(int statusCode);
}
