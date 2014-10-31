package com.example.cthulhu.ordabankiforandroid.unittests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.example.cthulhu.ordabankiforandroid.ChooseLanguagesFragment;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.ResultsScreen;
import com.example.cthulhu.ordabankiforandroid.SearchScreen;
import com.robotium.solo.Solo;
import junit.framework.Assert;

/**
 * @author Trausti
 * @since 23.10.2014.
 */
public class SearchScreenTest extends ActivityInstrumentationTestCase2<SearchScreen> {

    private Solo solo;

    public SearchScreenTest() {
        super(SearchScreen.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

/*
         The following commented-out code is a workaround used by
        / Kristján for unit tests to work in his system.

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            }
        });
*/

    }

    //Are we testing the correct activity?
    public void testPreconditions(){
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * This method tests if spinner values stay the same after
     * having exited and revisited the activity that contains them
     * Written by Trausti
     * @return      nothing */

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

    /**
     * This method tests if a search string can be entered and sent
     * from the search screen, and if the ResultsScreen recives them
     * Written by Kristján
     */


    public void testEditText() {
        // Validates the Search Bar starts Empty
        Assert.assertTrue(solo.searchText(""));
        // Enters a word into the Search Bar
        solo.enterText(0, "Autodefenestration");
        // Asserts the word has been entered
        Assert.assertTrue(solo.searchText("Autodefenestration"));
        // Clears text
        solo.clearEditText(0);
        // Verifies text has been cleared
        Assert.assertTrue(solo.searchText(""));
        // Re-enters text
        solo.enterText(0, "Autodefenestration");
        // Clicks the search button
        solo.clickOnButton("Leita");
        //Waits for the Results to load
        solo.waitForActivity(ResultsScreen.class);
        // Asserts that the current screen is the results
        solo.assertCurrentActivity("wrong activity",ResultsScreen.class);
        // Verifies the search term has passed into the ResultsScreen
        // TODO find a less stupid way to do this
        Assert.assertTrue(solo.searchText("Engar niðurstöður fyrir Autodefenestration"));
        // Returns to SearchScreen
        solo.goBack();
    }



    /**
     * This method tests the functionality of the Glossary tab
     * Written by Kristján
     */


    public void testGlossarySelection() {
        //TODO Expand tests and add Assertions
        //Click on Glossary tab
        solo.clickOnText(solo.getString(R.string.pick_glossary_tab));
        // Waits for Tab to load
        solo.sleep(1000);
        // Deselects all
        solo.clickOnButton(2);
        // Selects All
        solo.clickOnButton(1);
        // Selects First entry in list
        solo.clickInList(1);
        // Selects Deselects First entry in list
        solo.clickInList(1);
        // Selects Third entry in list
        solo.clickInList(3);
        // Deselects Third entry in list
        solo.clickInList(3);


    }




    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
