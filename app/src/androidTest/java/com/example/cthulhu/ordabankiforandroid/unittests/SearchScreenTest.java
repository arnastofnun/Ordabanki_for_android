package com.example.cthulhu.ordabankiforandroid.unittests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.ChooseLanguagesFragment;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.SearchScreen;
import com.robotium.solo.Solo;
import junit.framework.Assert;

/**
 * @author Trausti
 * @since 23.10.2014.
 */
public class SearchScreenTest extends ActivityInstrumentationTestCase2<SearchScreen> {

    private Solo solo;
   // private SearchScreen activity;
   // private EditText searchView;

    public SearchScreenTest() {
        super(SearchScreen.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());


        /* The following commented-out code is a workaround used by
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

    /**
     * This method tests if a search string can be entered and sent
     * from the search screen
     * Written by Kristján
     */

    public void testEditText() /* throws Exception */ {
        // Validates the Search Bar starts Empty
        Assert.assertTrue(solo.searchText(""));
        // Enters a word into the Search Bar
        solo.enterText(0, "Autodefenestration");
        // Asserts the word has been entered
        Assert.assertTrue(solo.searchText("Autodefenestration"));
        // TODO confirm the search query has been passed onwards
        // Clicks the search button
        // solo.clickOnButton(solo.getString(R.id.searchView));
        solo.goBack();

        //

    }







    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
