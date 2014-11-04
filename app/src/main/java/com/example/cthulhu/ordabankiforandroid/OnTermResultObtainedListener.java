package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 04/11/14.
 */
public interface OnTermResultObtainedListener {
    public void onTermResultObtained(TermResult[] tResult);
    public void onTermResultFailure(int statusCode);
}
