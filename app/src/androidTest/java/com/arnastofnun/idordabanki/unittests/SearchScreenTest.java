package com.arnastofnun.idordabanki.unittests;

import android.provider.SearchRecentSuggestions;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.ResultsScreen;
import com.arnastofnun.idordabanki.SearchAutoComplete;
import com.arnastofnun.idordabanki.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Tests SearchScreenActivity with robotium library
 * 
 * @author Trausti
 * @since 23.10.2014.
 */
public class SearchScreenTest extends ActivityInstrumentationTestCase2<SplashActivity> {
    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;

    public SearchScreenTest() {
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
     * Checks if we are testing the current activity
     */
    public void testPreconditions() {
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * USER STORY TEST:
     *
     * <p>
     *     user story: As a teacher I want to be able to choose one or
     *     more glossaries to restrict the results to my field
     *
     *     story point: 1,
     *     MSCW: SH,
     *     planned in sprint: 1,
     *     finished in sprint: 2
     * </p>
     *
     * Written by Trausti
     */
    public void testUserStoryGlossary(){
        solo.sleep(3000);

        //Click on Glossary tab
        solo.clickOnText(solo.getString(R.string.pick_glossary_tab));
        // Waits for Tab to load
        solo.sleep(1000);
        // Deselects all
        solo.clickOnText(solo.getString(R.string.deselect_all));

        // Select entries 2,4,7 and 8
        solo.clickInList(8);
        solo.clickInList(10);
        solo.clickInList(12);
        solo.clickInList(15);

        solo.scrollToSide(Solo.LEFT);
        solo.sleep(1000);
        solo.clickOnEditText(0);
        solo.enterText(0, "sta*");
        solo.sleep(1000);
        solo.sendKey(Solo.ENTER);
        solo.sleep(10000);

        TextView resultText = (TextView) solo.getView(R.id.resultText);
        String[] resultTextArr = resultText.getText().toString().split(" ");
        int resultsLength = Integer.parseInt(resultTextArr[0]);

        solo.clickInList(1);
        solo.sleep(5000);

        String[] expectedGlossaries = {"Byggingarlist", "Bílorð", "Bókband", "Efnafræði"};

        for(int i=0; i<resultsLength; i++){
            //if correctGlossary is true then we have the correct glossary
            solo.sleep(2000);

            boolean correctGlossary = false;
            TextView glossaryName = (TextView) solo.getView(R.id.termGlossaryView);
            TextView termName = (TextView) solo.getView(R.id.termWordView);

            String glossary = glossaryName.getText().toString();
            String term = termName.getText().toString();

            //check if term is a synonym, if it is restrictions don't apply
            if(term.contains("→")){
                solo.scrollToSide(Solo.RIGHT);
            }else{
                for(String expectedGlossary: expectedGlossaries){
                    if(expectedGlossary.equals(glossary)){
                        correctGlossary = true;
                    }
                }
                assertTrue(correctGlossary);
                solo.scrollToSide(Solo.RIGHT);
            }

        }
    }


    /**
     * USER STORY TEST:
     *
     * <p>
     *     user story: As a non-technical person I want to be able to get
     *     help so I can effectively use the app
     *
     *     story point: 3,
     *     MSCW: SH,
     *     planned in sprint: 2,
     *     finished in sprint: 2
     * </p>
     *
     * Written by Trausti
     */
    public void testUserStoryHelp(){
        solo.sleep(3000);

        //click on help icon in search screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(2000); // give it time to change activity
        solo.clickOnText(solo.getString(R.string.close_help));

        solo.clickOnEditText(0);
        solo.enterText(0, "stock");
        solo.sendKey(Solo.ENTER);
        solo.sleep(3000);

        //click on help icon in results screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(500);
        solo.clickOnText(solo.getString(R.string.close_help));

        solo.clickInList(1);
        solo.sleep(3000);

        //click on help icon in results info screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(500);
        solo.clickOnText(solo.getString(R.string.close_help));

    }


    /**
     * This method tests if spinner values stay the same after
     * having exited and revisited the activity that contains them
     * Written by Trausti
     */

    public void testSpinner() {
        solo.sleep(5000);

        //TODO This test no longer functions
        //Got get the view for the languages tab
        solo.clickOnText(solo.getString(R.string.pick_glossary_tab));
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
        //assertTrue(solo.isSpinnerTextSelected("English"));//spinner item 0
       //     assertTrue(solo.isSpinnerTextSelected("Icelandic"));//spinner item 1
    }

    /**
     * This method tests if a search string can be entered and sent
     * from the search screen, and if the ResultsScreen receives them
     * Written by Kristján
     */


    public void testEditText() {
        solo.sleep(5000);

        // Validates the Search Bar starts Empty
        Assert.assertTrue("search bar is not empty at initialization",solo.searchText(""));
        // Enters a word into the Search Bar
        solo.enterText(0, "Autodefenestration");
        // Asserts the word has been entered
        Assert.assertTrue("word was not entered into search bar",solo.searchText("Autodefenestration"));
        // Clears text
        solo.clearEditText(0);
        // Verifies text has been cleared
        Assert.assertTrue("Search bar does not clear properly",solo.searchText(""));
        // Re-enters text
        solo.enterText(0, "Autodefenestration");
        //Clicks the Search button
        solo.sendKey(Solo.ENTER);
        //Waits for the Results to load
        solo.sleep(5000);
        solo.assertCurrentActivity("wrong activity",ResultsScreen.class);
        // Verifies the search term has passed into the ResultsScreen
        /*This tests if the result list is empty, but that wasn't the point of the test
        ListView list = solo.getCurrentViews(ListView.class).get(0);
        assertTrue("The result is not empty", list.getChildCount() == 0);
        */
        Assert.assertTrue(solo.searchText("Autodefenestration"));
        // Returns to SearchScreen
        solo.goBack();
    }

    /**
     *
     * This method tests the suggestion feature
     * Note that the word "Autodefenestration" is not in the database
     * and will not return a full search result
     * written by Kristján
     */

    public void testSuggestion() {
        solo.sleep(5000);


        /*Karl Ásgeir was trying to do a test here*/
        //Enters a word
        solo.enterText(0, "Autodefenestration");
        //Clear it
        solo.clearEditText(0);
        //Enter a partial mach
        solo.enterText(0,"Auto");
        //Check if the suggestion comes up
        Assert.assertFalse(solo.waitForText("Autodefenestration"));
        //Enter the rest of the word
        solo.enterText(0,"defenestration");
        //Searches, to save the search term in search history
        solo.sendKey(Solo.ENTER);
        //Waits for results
        // Goes back to search screen
        solo.goBack();
        //Enters a part of the word
        solo.enterText(0, "Auto");
        //Check if the suggestion comes up
        Assert.assertTrue(solo.waitForText("Autodefenestration"));
        //Clears the text
        solo.clearEditText(0);
        //Enters another non matching word
        solo.enterText(0,"Poly");
        //Check it the suggestion is gone
        Assert.assertFalse(solo.waitForText("Autodefenestration"));


        /*
        //Enters the first word
        solo.enterText(0, "Autodefenestration");
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
        */


    }


    /**
     * This method tests the functionality of the Glossary tab
     * Written by Kristján
     */
    public void testGlossarySelection () {
        solo.sleep(5000);

        //TODO Expand tests and add Assertions
        //Click on Glossary tab
        solo.clickOnText(solo.getString(R.string.pick_glossary_tab));
        // Waits for Tab to load
        solo.sleep(1000);
        // Deselects all
        solo.clickOnText(solo.getString(R.string.deselect_all));
        // Selects All
        solo.clickOnText(solo.getString(R.string.select_all));
        // Selects First entry in list
        solo.clickInList(1);
        // Selects Deselects First entry in list
        solo.clickInList(1);
        // Selects Third entry in list
        solo.clickInList(3);
        // Deselects Third entry in list
        solo.clickInList(3);

        // Deselects all
        solo.clickOnText(solo.getString(R.string.deselect_all));
        solo.clickOnText(solo.getString(R.string.search_tab));
        solo.enterText(0, "Asf");
        solo.sendKey(Solo.ENTER);
        //user should not be able to search as he has deselected all tabs
        //solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }



    /**
     * This method tests whether or not search is allowed with 0 letters or less than two with *
     * Written by Trausti
     */

    public void testSearchLessThanTwoLetters() {
        solo.sleep(5000);
        solo.enterText(0,"");
        solo.sendKey(Solo.ENTER);
        //user should not be able to search as he has entered 0 letters
        solo.assertCurrentActivity("wrong activity", SearchScreen.class);
        solo.clearEditText(0);
        //Enter *
        solo.enterText(0,"*");
        solo.sendKey(Solo.ENTER);
        //user should not be able to search as he has entered * alone
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
        solo.clearEditText(0);
        //Enter a*
        solo.enterText(0,"a*");
        solo.sendKey(Solo.ENTER);
        //user should not be able to search as he has entered less than two characters along with *
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
    }

    /**
     * This method tests the language select dialog, accessed through the on the action bar
     * Written by Kristján
     */

    public void testLanguageSelection() {
        solo.sleep(5000);
        //solo.goBack();
        // Selects the action bar menu
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects Icelandic
        solo.clickInList(1);
        // Confirms selection
        solo.clickOnText(solo.getString(R.string.settings_changelanguage_confirm));
        //waits for new language to load
        solo.sleep(2000);
        // Asserts Icelandic is selected
        Assert.assertTrue(solo.searchText("Leita"));
        // Repeats, selects the English language but cancels the selection
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects English
        solo.clickInList(0);
        // Cancels selection
        solo.clickOnText(solo.getString(R.string.close_help));
        //waits for new language to load
        solo.sleep(2000);
        // Asserts Icelandic is still selected
        Assert.assertTrue(solo.searchText("Leita"));
        // Repeats, but this time confirms English selection
        solo.clickOnActionBarItem(R.id.action_settings);
        // Clicks on the Language select option
        solo.clickInList(0);
        // Selects English
        solo.clickInList(0);
        // Confirms selection
        solo.clickOnText(solo.getString(R.string.settings_changelanguage_confirm));
        //waits for new language to load
        solo.sleep(2000);
        //Asserts English is selected
        //Assert.assertTrue(solo.searchText("Search"));
    }

    /*
    * This method test if search history can be cleared
    * written by Kristján
     */

    public void testClearHistory(){
        solo.sleep(5000);
        //Makes sure search is empty
        solo.clearEditText(0);
        //Enters search term
        solo.enterText(0, "Polystyrene");
        //Searches, to save the search term in the search history
        solo.sendKey(Solo.ENTER);
        //Waits for results
        //Goes back to search screen
        solo.goBack();
        //Makes sure search is empty
        solo.clearEditText(0);
        //Enters partial match
        solo.enterText(0, "Poly");
        //Confirms suggestion is made
        Assert.assertTrue("Suggestion was not made initially",solo.searchText("Polystyrene"));
        //Makes sure search is empty
        solo.clearEditText(0);
        //Clears the search
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickInList(4);
        //Re-enters partial match
        solo.enterText(0, "Poly");
        //Confirms suggestion is no longer made
        Assert.assertFalse("suggestion was not cleared", solo.searchText("Polystyrene"));
    }


    /**
     * tearDown exits all opened activities
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
