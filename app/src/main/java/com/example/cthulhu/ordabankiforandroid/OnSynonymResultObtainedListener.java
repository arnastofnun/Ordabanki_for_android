package com.example.cthulhu.ordabankiforandroid;
/**
 * Listener interface for rest client
 * Created by Bill on 26/10/14.
 */
public interface OnSynonymResultObtainedListener {
    public void onSynonymResultObtained(SynonymResult[] SynResult);
    public void onSynonymResultFailure(int statusCode);
}
