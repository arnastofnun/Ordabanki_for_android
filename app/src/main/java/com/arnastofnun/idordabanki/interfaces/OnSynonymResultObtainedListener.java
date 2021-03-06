package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.models.SynonymResult;

/**
 * Listener interface for rest client
 * returns a SynonymResult array if results are obtained or an HTTP status code otherwise
 * @author Bill
 * @since 26.10.2014
 */
public interface OnSynonymResultObtainedListener {
    /**
     * Called on successful HTTP call
     * @param SynResult results array
     */
    void onSynonymResultObtained(SynonymResult[] SynResult);

    /**
     * Called on failure
     * @param statusCode HTTP status code. No result on 200, otherwise error.
     */
    void onSynonymResultFailure(int statusCode);
}
