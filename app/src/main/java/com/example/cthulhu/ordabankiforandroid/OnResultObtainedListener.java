package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 26/10/14.
 */
public interface OnResultObtainedListener {
    public void onResultObtained(Result[] result);
    public void onResultFailure(int statusCode);
}
