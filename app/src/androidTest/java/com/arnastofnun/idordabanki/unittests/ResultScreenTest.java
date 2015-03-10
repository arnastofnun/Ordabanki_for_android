package com.arnastofnun.idordabanki.unittests;


import android.test.ActivityInstrumentationTestCase2;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.robotium.solo.Solo;

/**
 * @author Karl Ásgeir Geirsson
 * @since 03.08.2015
 */
public class ResultScreenTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;

    public ResultScreenTest() {
        super(SplashActivity.class);
    }

    /**
     * method  sets up activity and solo before testing can begin
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();

        //TODO initial setup

    }

    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }


    public void testAmountOfResults() {
        assertTrue("Wrong amount of results",solo.searchText("15 results for bla?"));
    }

    public void testWordResult(){

    }


}
