package com.example.cthulhu.ordabankiforandroid;

/**
 * Listener interface for rest client
 * Created by Bill on 26/10/14.
 */
public interface OnResultObtainedListener {
    public void onResultObtained(Result[] result);
    public void onResultFailure(int statusCode);
}
