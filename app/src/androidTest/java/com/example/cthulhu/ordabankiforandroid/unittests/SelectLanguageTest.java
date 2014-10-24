package com.example.cthulhu.ordabankiforandroid.unittests;


import android.test.ActivityInstrumentationTestCase2;

import com.example.cthulhu.ordabankiforandroid.SelectLanguageActivity;

/**
 * @author Trausti
 * @since 23.10.2014.
 */
public class SelectLanguageTest extends ActivityInstrumentationTestCase2<SelectLanguageActivity> {

    //instance variable
    private SelectLanguageActivity activity;

    public SelectLanguageTest() {
        super(SelectLanguageActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    public void testPreConditions() {
    } // end of testPreConditions() method definition

    public void testStateDestroy() {
    }
    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

}
