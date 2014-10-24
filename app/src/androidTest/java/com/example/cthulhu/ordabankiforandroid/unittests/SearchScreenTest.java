package com.example.cthulhu.ordabankiforandroid.unittests;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.ChooseLanguagesFragment;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.SearchScreen;

/**
 * @author Trausti
 * @since 23.10.2014.
 */
public class SearchScreenTest extends ActivityInstrumentationTestCase2<SearchScreen> {

    //instance variable
    private SearchScreen activity;

    public SearchScreenTest() {
        super(SearchScreen.class);
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
