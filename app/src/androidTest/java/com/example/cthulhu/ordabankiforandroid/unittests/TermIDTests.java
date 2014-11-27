package com.example.cthulhu.ordabankiforandroid.unittests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.example.cthulhu.ordabankiforandroid.ResultsScreen;
import com.robotium.solo.Solo;

/**
 * Created by karlasgeir on 26.11.2014.
 */
public class TermIDTests extends ActivityUnitTestCase<ResultsScreen> {

    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;


    public TermIDTests() {
        super(ResultsScreen.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());

        //Put up a fake intent
        Intent intent = new Intent(getInstrumentation().getTargetContext(),ResultsScreen.class);
        //Put in a valid Term ID
        intent.putExtra("searchQuery", "507286");

        startActivity(intent,null,null);

    }


    /**
     * Checks if the itInteger method is working
     */
    public void testIsInteger() {
        solo.sleep(5000);
        //Put in a valid term ID
        String validTermID = "507285";
        String invalidTermID1 = "a402045";
        String invalidTermID2 = "slkdjfa";
        boolean passTest = getActivity().isInteger(validTermID)
                && !getActivity().isInteger(invalidTermID1)
                &&!getActivity().isInteger(invalidTermID2);
        assertEquals(passTest,true);

    }

    /**
     * Check if TermId is detected in search query
     */
    public void testDoTermSearch(){
        solo.sleep(5000);
        //Put in a valid term ID
        String validTermID = "507285";
        getActivity().doTermIdSearch(validTermID);
        Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertEquals(launchIntent.getComponent().getShortClassName(),".ResultInfo");
    }





}
