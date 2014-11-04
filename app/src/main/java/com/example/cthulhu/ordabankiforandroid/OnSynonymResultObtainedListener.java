package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 04/11/14.
 */
public interface OnSynonymResultObtainedListener {
    public void onSynonymResultObtained(SynonymResult[] SynResult);
    public void onSynonymResultFailure(int statusCode);
}
