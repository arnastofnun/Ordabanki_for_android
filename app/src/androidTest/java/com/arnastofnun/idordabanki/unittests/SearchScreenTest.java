package com.arnastofnun.idordabanki.unittests;


import android.provider.SearchRecentSuggestions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.LocaleSettings;
import com.arnastofnun.idordabanki.PickGlossaryFragment;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.ResultInfo;
import com.arnastofnun.idordabanki.ResultsScreen;
import com.arnastofnun.idordabanki.SearchAutoComplete;
import com.arnastofnun.idordabanki.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;
import junit.framework.Assert;
import java.util.Arrays;
import java.util.HashSet;

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

        //Make the app start up in english
        LocaleSettings localeSettings = new LocaleSettings(Globals.getContext());
        localeSettings.setLanguageInit("EN");

        getActivity();
        //Clear the search history
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(getActivity().getApplicationContext(), SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
        suggestions.clearHistory();

        solo.waitForActivity(SearchScreen.class);
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
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
        //Select terms only search while the synonyms aren't restricted
        solo.clickOnRadioButton(1);
        //Click on Glossary tab
        solo.scrollToSide(Solo.RIGHT);
        // Waits for Tab to load
        solo.sleep(500);
        // Deselects all glossaries
        solo.clickOnText(solo.getString(R.string.deselect_all));

        //These are the glossaries that we are going to select
        HashSet<String> expectedGlossaries = new HashSet<>();
        expectedGlossaries.addAll(Arrays.asList("Archeology", "Architecture", "Arts", "Astronomy"));

        //Select them
        solo.clickInList(4);
        solo.clickInList(5);
        solo.clickInList(8);
        solo.clickInList(9);

        //Scroll to the search fragment
        solo.scrollToSide(Solo.LEFT);
        solo.sleep(500);

        //Search for sta*
        solo.clickOnEditText(0);
        solo.enterText(0, "sta*");
        solo.pressSoftKeyboardSearchButton();

        //Wait until results are displayed
        solo.waitForText("67 results for sta*");

        //Get the list view, and adapter
        ExpandableListView listView = (ExpandableListView) solo.getView(R.id.resultsList);
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();

        //Loop through the groups
        for(int i=0; i<listAdapter.getGroupCount(); i++){
            View groupView = listAdapter.getGroupView(i,true,null,listView);
            int groupSize = listAdapter.getChildrenCount(i);
            //If the sublist is empty
            if(groupSize == 1){
                TextView glossaryView = (TextView) groupView.findViewById(R.id.resultGlossary);
                String glossary = glossaryView.getText().toString();
                assertTrue("Wrong glossary",expectedGlossaries.contains(glossary));

            }
            else{
                //Loop through sublists
                for(int j=0;i<groupSize;i++) {
                    View view = listAdapter.getChildView(i,j,true,null,listView);
                    TextView glossaryView = (TextView) view.findViewById(R.id.resultGlossary);
                    String glossary = glossaryView.getText().toString();
                    assertTrue("Wrong glossary",expectedGlossaries.contains(glossary));
                }
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

        //click on help icon in search screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(500); // give it time to change activity
        assertNotNull("Didn't open help",solo.getButton("Close",true));
        solo.clickOnButton("Close");

        solo.clickOnEditText(0);
        solo.enterText(0, "stock");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class);

        //click on help icon in results screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(500);
        assertNotNull("Didn't open help", solo.getButton("Close", true));
        solo.clickOnButton("Close");

        solo.clickInList(2);
        solo.waitForActivity(ResultInfo.class);

        //click on help icon in results info screen
        solo.clickOnActionBarItem(R.id.action_help);
        solo.sleep(500);
        assertNotNull("Didn't open help", solo.getButton("Close", true));
        solo.clickOnButton("Close");
    }


    /**
     * This method tests if spinner values stay the same after
     * having exited and revisited the activity that contains them
     * Written by Trausti
     */
    public void testSourceLanguageSpinner() {
        //Get the view for the languages tab
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);
        //Press spinner item 0 (src lang) and language 2 (English)
        solo.pressSpinnerItem(0,2);

        //Go to the search tab
        solo.scrollToSide(Solo.LEFT);
        solo.scrollToSide(Solo.LEFT);

        //Go back to the languages tab
        //Get the view for the languages tab
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);

        //check if spinner items still have the same values
        assertTrue("Spinner changed languages on swipe",solo.isSpinnerTextSelected("English"));
    }

    public void testSourceLanguageFunctionality(){
        //Select terms only search while the synonyms aren't restricted
        solo.clickOnRadioButton(1);

        //Get the view for the languages tab
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);

        solo.pressSpinnerItem(0,2);

        //Go to the search tab
        solo.scrollToSide(Solo.LEFT);
        solo.scrollToSide(Solo.LEFT);

        solo.enterText(0,"hor*");
        solo.pressSoftKeyboardSearchButton();

       String expectedLang = "English";

        //Wait until results are displayed
        solo.waitForText("219 results for hor*");

        //Get the list view, and adapter
        ExpandableListView listView = (ExpandableListView) solo.getView(R.id.resultsList);
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();

        //Loop through the groups
        for(int i=0; i<listAdapter.getGroupCount(); i++){
            View groupView = listAdapter.getGroupView(i,true,null,listView);
            int groupSize = listAdapter.getChildrenCount(i);
            //If the sublist is empty
            if(groupSize == 1){
                TextView languageView = (TextView) groupView.findViewById(R.id.resultLanguage);
                String lang = languageView.getText().toString();
                assertTrue("Wrong language",expectedLang.equals(lang));

            }
            else{
                //Loop through sublists
                for(int j=0;i<groupSize;i++) {
                    View view = listAdapter.getChildView(i,j,true,null,listView);
                    TextView glossaryView = (TextView) view.findViewById(R.id.resultLanguage);
                    String lang = glossaryView.getText().toString();
                    assertTrue("Wrong language", expectedLang.equals(lang));
                }
            }
        }
    }

    /**
     * This method tests if a search string can be entered and sent
     * from the search screen, and if the ResultsScreen receives them
     * Written by Kristján
     */
    public void testEditText() {
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
        solo.pressSoftKeyboardSearchButton();
        //Waits for the Results to load
        solo.waitForActivity(ResultsScreen.class, 1000);
        // Verifies the search term has passed into the ResultsScreen
        Assert.assertTrue(solo.searchText("Autodefenestration"));
    }

    /**
     *
     * This method tests the suggestion feature
     * Note that the word "Autodefenestration" is not in the database
     * and will not return a full search result
     * written by Kristján
     */
    public void testSuggestion() {
        //Enters a word
        solo.enterText(0, "Autodefenestration");
        //Clear it
        solo.clearEditText(0);
        //Enter a partial mach
        solo.enterText(0,"Auto");
        //Check if the suggestion comes up
        Assert.assertFalse(solo.waitForText("Autodefenestration",1,1000));
        //Enter the rest of the word
        solo.enterText(0,"defenestration");
        //Searches, to save the search term in search history
        solo.pressSoftKeyboardSearchButton();
        //Waits for results
        solo.waitForActivity(ResultsScreen.class);
        // Goes back to search screen
        solo.goBack();
        //Enters a part of the word
        solo.enterText(0, "Auto");
        //Check if the suggestion comes up
        Assert.assertTrue(solo.waitForText("Autodefenestration",1,1000));
        //Clears the text
        solo.clearEditText(0);
        //Enters another non matching word
        solo.enterText(0,"YOLO");
        //Check it the suggestion is gone
        Assert.assertFalse(solo.waitForText("Autodefenestration",1,1000));
    }


    /**
     * This method tests the functionality of the Glossary tab
     * Written by Kristján
     */
    public void testGlossarySelection () {
        //Click on Glossary tab
        solo.scrollToSide(Solo.RIGHT);
        // Deselects all
        solo.clickOnText(solo.getString(R.string.deselect_all));
        solo.sleep(1000);
        assertEquals("All glossaries weren't deselected",0,PickGlossaryFragment.getSelectedGlossaries().size());

        Globals globals = (Globals) Globals.getContext();
        // Selects All
        solo.clickOnText(solo.getString(R.string.select_all));
        solo.sleep(1000);
        assertEquals("All glossaries weren't reselected",globals.getDictionaries().size(),PickGlossaryFragment.getSelectedGlossaries().size());

        solo.clickOnText(solo.getString(R.string.deselect_all));

        solo.sleep(1000);

        solo.clickInList(2);
        solo.clickInList(4);
        solo.clickInList(6);
        solo.clickInList(8);

        solo.sleep(1000);

        assertEquals("Wrong number of glossaries",4,PickGlossaryFragment.getSelectedGlossaries().size());

        solo.clickOnText(solo.getString(R.string.select_all));
        solo.clickOnText(solo.getString(R.string.deselect_all));
        solo.scrollToSide(Solo.LEFT);
        solo.enterText(0, "Asf");
        solo.pressSoftKeyboardSearchButton();
        solo.sleep(1000);
        solo.assertCurrentActivity("Shouldn't have been able to search when all glossaries are deselected",SearchScreen.class);
    }



    /**
     * This method tests whether or not search is allowed with 0 letters or less than two with *
     * Written by Trausti
     */

    public void testSearchLessThanTwoLetters() {
        solo.enterText(0,"");
        solo.pressSoftKeyboardSearchButton();
        //user should not be able to search as he has entered 0 letters
        solo.assertCurrentActivity("wrong activity", SearchScreen.class);
        solo.clearEditText(0);
        //Enter *
        solo.enterText(0,"*");
        solo.pressSoftKeyboardSearchButton();
        //user should not be able to search as he has entered * alone
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
        solo.clearEditText(0);
        //Enter a*
        solo.enterText(0,"a*");
        solo.pressSoftKeyboardSearchButton();
        //user should not be able to search as he has entered less than two characters along with *
        solo.assertCurrentActivity("wrong activity",SearchScreen.class);
        solo.clearEditText(0);
        //Enter aa*
        solo.enterText(0,"aa*");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(ResultsScreen.class,1000);
        solo.assertCurrentActivity("* search not working",ResultsScreen.class);
    }

    /**
     * This method tests the language select dialog, accessed through the on the action bar
     * Written by Kristján
     * TODO: find another way to test this, it won't work since the test crashes when the app restarts
     */
    public void testLanguageSelection() {
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
        //Makes sure search is empty
        solo.clearEditText(0);
        //Enters search term
        solo.enterText(0, "Polystyrene");
        //Searches, to save the search term in the search history
        solo.pressSoftKeyboardSearchButton();
        //Waits for results
        solo.waitForActivity(ResultsScreen.class);
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
        Assert.assertFalse("suggestion was not cleared", solo.searchText("Polystyrene",2000));
    }

}
