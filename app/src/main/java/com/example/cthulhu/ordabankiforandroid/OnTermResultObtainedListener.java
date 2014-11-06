package com.example.cthulhu.ordabankiforandroid;

/**
 * Listener interface for rest client
 * Created by Bill on 26/10/14.
 */
public interface OnTermResultObtainedListener {
    public void onTermResultObtained(TermResult[] tResult);
    public void onTermResultFailure(int statusCode);
}
