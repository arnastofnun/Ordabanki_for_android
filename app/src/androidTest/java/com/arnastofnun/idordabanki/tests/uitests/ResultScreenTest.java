package com.arnastofnun.idordabanki.tests.uitests;


import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.test.ActivityInstrumentationTestCase2;
import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.preferences.LocaleSettings;
import com.arnastofnun.idordabanki.activities.ResultInfo;
import com.arnastofnun.idordabanki.activities.ResultsScreen;
import com.arnastofnun.idordabanki.helpers.SearchAutoComplete;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.robotium.solo.Solo;


/**
 * A test that tests if the ResultScreen is functioning correctly
 * @author karlasgeir
 * @since 08/03/2015
 */
public class ResultScreenTest extends ActivityInstrumentationTestCase2<com.arnastofnun.idordabanki.activities.SplashActivity> {

    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;

    /**
     * Basic constructor
     */
    public ResultScreenTest() {
        super(com.arnastofnun.idordabanki.activities.SplashActivity.class);
    }

    /**
     * method  sets up activity and solo before testing can begin
     */
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());

        SharedPrefs.getEditor().putString("lang","EN");

        //Clear the search history
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(getActivity().getApplicationContext(), SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
        suggestions.clearHistory();
        getActivity();

        //Search for hest*
        solo.enterText(0, "hest*");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);


    }

    /**
     * Makes sure everything is cleared before next
     * test
     * @throws Exception
     */
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * A test that tests if the correct
     * amount of results comes up
     */
    public void testAmountOfResults() {
        assertTrue("Wrong amount of results",solo.waitForText("57 results for"));
    }

    /**
     * A test that tests if the sublists open and close correctly
     */
    public void testSublistsOpenClose(){
        assertFalse("sublist initially open",solo.waitForText("Aviation"));
        solo.scrollListToTop(0);
        solo.clickInList(3);
        solo.sleep(500);
        assertTrue("sublist did not open",solo.waitForText("Aviation"));
    }

    /**
     * A test that tests if the clicking on sublist items
     * is functioning correctly
     */
    public void testClickOnSubItem(){
        solo.clickInList(3);
        solo.clickOnText("Aviation");
        solo.waitForActivity(ResultInfo.class);
        solo.assertCurrentActivity("Didn't get to results info",ResultInfo.class);
    }

    /**
     * A test that test if clicking on group items is working
     * correctly (group items with one item)
     */
    public void testClickOnGroupItem(){
        solo.clickInList(1);
        solo.waitForActivity(ResultInfo.class);
        solo.assertCurrentActivity("Didn't get to results info",ResultInfo.class);
    }


}
