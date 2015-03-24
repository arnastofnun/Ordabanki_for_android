package com.arnastofnun.idordabanki.unittests;

import android.provider.SearchRecentSuggestions;
import android.test.ActivityInstrumentationTestCase2;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.activities.ResultInfo;
import com.arnastofnun.idordabanki.activities.ResultsScreen;
import com.arnastofnun.idordabanki.helpers.SearchAutoComplete;
import com.arnastofnun.idordabanki.activities.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;
import junit.framework.Assert;

/**
 * This test should test the search from the action bar
 * @author karlasgeir
 * @since 2/27/15.
 */
public class SearchTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;

    /**
     * A basic constructor
     */
    public SearchTest() {
        super(SplashActivity.class);
    }

    /**
     * method  sets up activity and solo before testing can begin
     */
    public void setUp() throws Exception {
        /*This way the testPreconditions will work*/
        solo = new Solo(getInstrumentation());
        getActivity();
        //Clear the search history
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(getActivity().getApplicationContext(), SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
        suggestions.clearHistory();
        super.setUp();
    }

    /**
     * Makes sure everything is cleared
     * before next test
     * @throws Exception
     */
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Test if the search is correct from the result list
     */
    public void testSearchFromResultList() throws InterruptedException{
        solo.assertCurrentActivity("wrong starting activity",SearchScreen.class);
        solo.enterText(0, "bla?");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);
        solo.clickOnActionBarItem(R.id.search);
        solo.enterText(0, "leit");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForText("7 niðurstöður fyrir leit",1,1000);
        Assert.assertTrue("could not search", solo.searchText("7 niðurstöður fyrir leit"));
    }

    /**
     * Test if the search is correct from the result list
     */
    public void testSearchFromResultInfoList() {
        solo.assertCurrentActivity("wrong starting activity",SearchScreen.class);
        solo.enterText(0, "bla?");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);
        solo.clickInList(0);
        solo.clickOnText("danska");
        solo.waitForActivity(ResultInfo.class);
        solo.clickOnActionBarItem(R.id.search);
        solo.enterText(0,"leit");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForText("niðurstöður");
        Assert.assertTrue("could not search",solo.searchText("7 niðurstöður fyrir leit"));
    }


}
