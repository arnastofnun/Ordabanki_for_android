package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.models.Result;

/**
 * Listener interface for rest client
 * @author Bill
 * @since 26.10.2014
 */
public interface OnResultObtainedListener {
    //TODO: javadocs
    void onResultObtained(Result[] result);
    //TODO: javadocs
    void onResultFailure(int statusCode);
}
