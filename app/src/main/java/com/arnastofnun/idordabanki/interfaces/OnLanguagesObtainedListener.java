package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.models.Language;

/**
 * Interface for language http calls
 * @author Bill
 */
public interface OnLanguagesObtainedListener {
    //TODO: javadocs
    void onLanguagesObtained(Language[] languages);
    //TODO: javadocs
    void onLanguagesFailure(int statusCode);
}
