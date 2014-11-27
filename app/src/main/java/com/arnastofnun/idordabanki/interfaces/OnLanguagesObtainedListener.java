package com.arnastofnun.idordabanki.interfaces;

import com.arnastofnun.idordabanki.Language;

/**
 * Interface for language http calls
 * @author Bill
 */
public interface OnLanguagesObtainedListener {
    //TODO: javadocs
    public void onLanguagesObtained(Language[] languages);
    //TODO: javadocs
    public void onLanguagesFailure(int statusCode);
}
