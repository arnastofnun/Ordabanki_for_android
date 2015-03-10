package com.arnastofnun.idordabanki.unittests;

import android.provider.SearchRecentSuggestions;
import android.test.ActivityInstrumentationTestCase2;

import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.ResultInfo;
import com.arnastofnun.idordabanki.ResultsInfoFragment;
import com.arnastofnun.idordabanki.ResultsScreen;
import com.arnastofnun.idordabanki.SearchAutoComplete;
import com.arnastofnun.idordabanki.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Test search related functionality
 *
 *  @author Karl Ásgeir Geirsson
 *  @since 02.27.2015.
 */
public class SearchTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;

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
        /*This makes sure the settings are cleared before each test, but it makes it start up in the select language screen
        Context context = getInstrumentation().getTargetContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
        */
    }

    /**
     * Test if the search is correct from the result list
     */
    public void testSearchFromResultList() {
        solo.assertCurrentActivity("wrong starting activity",SearchScreen.class);
        solo.enterText(0, "bla?");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);
        solo.clickOnActionBarItem(R.id.search);
        solo.enterText(0, "leit");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForText("niðurstöður");
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
        solo.clickOnText("blað", 1);
        solo.waitForActivity(ResultInfo.class);
        solo.clickOnActionBarItem(R.id.search);
        solo.enterText(0,"leit");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForText("niðurstöður");
        Assert.assertTrue("could not search",solo.searchText("7 niðurstöður fyrir leit"));
    }

    /**
     * Test if a correct search is done when buttons are clicked on in result info
     */
    public void testButtonsInResultInfo(){
        solo.assertCurrentActivity("wrong starting activity",SearchScreen.class);
        solo.enterText(0, "bla?");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);
        solo.clickOnText("blad", 1);
        solo.waitForActivity(ResultInfo.class);
        solo.clickOnText("Bogen", 1);
        Assert.assertTrue("not correct term/button",solo.searchText("Bogen"));
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);
        solo.clickOnText("Bogen", 1);
        solo.waitForActivity(ResultInfo.class);
        solo.clickOnText("Blatt", 1);
        Assert.assertTrue("not correct term/button",solo.searchText("Blatt"));
        solo.pressSoftKeyboardSearchButton();
    }




}
