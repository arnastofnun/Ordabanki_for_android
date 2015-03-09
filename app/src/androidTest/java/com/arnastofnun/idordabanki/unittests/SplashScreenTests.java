package com.arnastofnun.idordabanki.unittests;


import android.test.ActivityUnitTestCase;

import com.arnastofnun.idordabanki.Globals;

import com.arnastofnun.idordabanki.activities.SplashActivity;

import com.robotium.solo.Solo;


/**
 * A test class intended to test the functionality of the
 * splash activity class
 * Created by karlasgeir
 * @since 09.03.2015.
 */
public class SplashScreenTests extends ActivityUnitTestCase<SplashActivity> {
    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;
    private SplashActivity splashActivity;

    /**
     * A constructor for the test class
     */
    public SplashScreenTests(){
        super(SplashActivity.class);
    }

    /**
     * method  sets up activity and solo before testing can begin
     */
    public void setUp() throws Exception {
        /*This way the testPreconditions will work*/
        solo = new Solo(getInstrumentation());
        splashActivity = new SplashActivity();
    }

    /**
     * A test to check if there is connection
     * to the database
     */
    public void testCheckConnection(){
        assertTrue("No connection",splashActivity.checkConnection());
    }

    /**
     * A method to test the initialize() method
     */
    public void testInitialize() throws Exception{
        splashActivity.initialize();

        Globals globals = (Globals) Globals.getContext();
        assertNotNull("Dictionaries not initialized",globals.getDictionaries());
        assertNotNull("Languages not initialized",globals.getLanguages());
        assertNotNull("Localized languages not initialized",globals.getLoc_dictionaries());
    }
}
