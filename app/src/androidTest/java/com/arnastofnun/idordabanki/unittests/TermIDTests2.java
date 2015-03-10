package com.arnastofnun.idordabanki.unittests;

import android.test.ActivityInstrumentationTestCase2;

import com.arnastofnun.idordabanki.ResultInfo;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * A test that tests the Term ID search
 * @author Karl Ásgeir Geirsson
 * @since 26.11.2014
 */
public class TermIDTests2 extends ActivityInstrumentationTestCase2<SplashActivity> {

    public TermIDTests2(){
        super(SplashActivity.class);
    }

    private Solo solo;
    private String validTermID = "507285";



    /**
     * method which sets up activity and solo before testing can begin
     */
    public void setUp() throws Exception {
        /*This way the testPreconditions will work*/
        solo = new Solo(getInstrumentation());
        getActivity();
        // Validates the Search Bar starts Empty
        Assert.assertTrue("search bar is not empty at initialization", solo.searchText(""));
        // Enters a word into the Search Bar

        //Enters a word
        solo.enterText(0, validTermID);
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultInfo.class);
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }


    /**
     * test if the getPageCount()
     * method is functioning correctly
     */
    public void testGetPageCount(){
        assertEquals(ResultInfo.getPageCount(), 1);
    }


    /**
     * Test if the search comes through
     */
    public void testTermIdSearch(){
        assertTrue("Correct term not found", solo.waitForText("initial"));
    }

}
