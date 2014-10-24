package com.example.cthulhu.ordabankiforandroid.unittests;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.cthulhu.ordabankiforandroid.R;

/**
 * Created by tka on 24.10.2014.
 */
public class TestFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_for_tests);
    }
}