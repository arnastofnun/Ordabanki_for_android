package com.example.cthulhu.ordabankiforandroid.unittests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;

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
*/}

    //Are we testing the correct activity?
    public void testPreconditions() {
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * This method tests if spinner values stay the same after
     * having exited and revisited the activity that contains them
     * Written by Trausti
     * @return      nothing */

    public void testSpinner() {
        //TODO This test no longer functions
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
        //Clicks the Search button
        solo.clickOnView(solo.getView(R.id.searchscreen));
        //Waits for the Results to load
        solo.assertCurrentActivity("wrong activity",ResultsScreen.class);
        // Verifies the search term has passed into the ResultsScreen
        // TODO find a less stupid way to do this
        Assert.assertTrue(solo.searchText("Engar niðurstöður fyrir Autodefenestration"));
        // Returns to SearchScreen
        solo.goBack();
    }

    /**
     *
     * This method tests the suggestion feature
     * Must be run after the EditText test for first word to be in memory
     * written by Kristján
     */

    public void testSuggestion() {
        //Enters the first word
        solo.enterText(0, "Autodefenestration");
        //clears the first word
        solo.clearEditText(0);
        //enters the second word
        solo.enterText(0, "Polyester");
        //Checks that the first word is no longer on the screen
        Assert.assertFalse(solo.searchText("Autodefenestration"));
        //clears the text
        solo.clearEditText(0);
        //checks that suggestion is made (This Assertion may cause problems further into development)
        Assert.assertTrue(solo.searchText("Autodefenestration"));
        //Enters partial match
        solo.enterText(0, "Auto");
        //checks that suggestion is made
        Assert.assertTrue(solo.searchText("Autodefenestration"));
        //enters something that doesn't match
        solo.enterText(0, "AutoMobile");
        //Checks that suggestion is no longer made
        Assert.assertFalse(solo.searchText("Autodefenestration"));



    }


    /**
     * This method tests the functionality of the Glossary tab
     * Written by Kristján
     */


    public void testGlossarySelection () {
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

        // Deselects all
        solo.clickOnButton(2);
        solo.clickOnText(solo.getString(R.string.search_tab));
        solo.enterText(0, "Asf");
        solo.clickOnButton(R.string.search_button);
        //user should not be able to search as he has deselected all tabs
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }



    /**
     * This method tests whether or not search is allowed with 0-2 letters
     * Written by Trausti
     */

    public void testSearchLessThanTwoLetters() {
        EditText search = (EditText)solo.getView(R.id.searchView);
        solo.enterText(search,"");
        solo.clickOnText(solo.getString(R.string.search_button));
        //user should not be able to search as he has entered 0 letters
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
        solo.clearEditText(search);
        solo.enterText(search,"a");
        solo.clickOnText(solo.getString(R.string.search_button));

        //user should not be able to search as he has entered 1 letter
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * This method tests the langauge select option on the action bar
     * This test may give a false negative if previous test doesn't end on SearchScreenActivity
     * Written by Kristján
     */

    public void testLanguageSelection() {
        //solo.goBack();
        // Selects the action bar menu
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects Icelandic
        solo.clickInList(1);
        // Confirms selection
        solo.clickOnButton(1);
        //waits for new language to load
        solo.sleep(5000);
        // Asserts Icelandic is selected
        Assert.assertTrue(solo.searchText("Leita"));
        // Repeats, selects the English language but cancels the selection
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects English
        solo.clickInList(0);
        // Cancels selection
        solo.clickOnButton(0);
        //waits for new language to load
        solo.sleep(5000);
        // Asserts Icelandic is still selected
        Assert.assertTrue(solo.searchText("Leita"));
        // Repeats, but this time confirms English selection
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects English
        solo.clickInList(0);
        // Confirms selection
        solo.clickOnButton(1);
        //waits for new language to load
        solo.sleep(5000);
        // Asserts English is selected
        Assert.assertTrue(solo.searchText("Search"));
    }

    /*
    * This method test if search history can be cleared
    * written by Kristján
     */


    public void testClearHistory(){
        //Makes sure search is empty
        solo.clearEditText(0);
        //Enters search term
        solo.enterText(0, "Polystyrene");
        //Searches, to save the search term in autocomplete
        solo.clickOnView(solo.getView(R.id.searchscreen));
        //Waits for results
        solo.sleep(50000);
        // Goes back to search screen
        solo.goBack();
        //Makes sure search is empty
        solo.clearEditText(0);
        //Enters partial match
        solo.enterText(0, "Poly");
        //Confirms suggestion is made
        Assert.assertTrue(solo.searchText("Polystyrene"));
        //Clears the search
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickInList(4);
        //Re-enters partial match
        solo.enterText(0, "Poly");
        //Confirms suggestion is no longer made
        Assert.assertTrue(solo.searchText("Polystyrene"));
    }



    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
