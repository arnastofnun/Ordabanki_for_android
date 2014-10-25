package com.example.cthulhu.ordabankiforandroid.unittests;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cthulhu.ordabankiforandroid.ChooseLanguagesFragment;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.SearchScreen;
import com.robotium.solo.Solo;

/**
 * @author Trausti
 * @since 23.10.2014.
 */
public class SearchScreenTest extends ActivityInstrumentationTestCase2<SearchScreen> {

    private Solo solo;
    private SearchScreen activity;

    public SearchScreenTest() {
        super(SearchScreen.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }

    //Are we testing the correct activity?
    public void testPreconditions(){
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * This method tests if spinner values stay the same after
     * having exited and revisited the activity that contains them
     * Written by Trausti
     * @return      nothing
     */
    public void testSpinner(){
        //Click on Languages tab
        solo.clickOnText(solo.getString(R.string.languages_tab_name));
        //Press spinner item 0 (src lang) and language 2 (English)
        solo.pressSpinnerItem(0,2);
        //Press spinner item 1 (trg lang) and language 1 (Icelandic)
        solo.pressSpinnerItem(1,1);
        //Click on search tab
        solo.clickOnText(solo.getString(R.string.search_tab));
        //Click on Languages tab again
        solo.clickOnText(solo.getString(R.string.languages_tab_name));
        //check if spinner items still have the same values
        assertTrue(solo.isSpinnerTextSelected("English"));//spinner item 0
        assertTrue(solo.isSpinnerTextSelected("Icelandic"));//spinner item 1
    }





    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
