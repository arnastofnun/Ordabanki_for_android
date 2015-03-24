package com.arnastofnun.idordabanki.unittests;


import android.test.ActivityInstrumentationTestCase2;

import com.arnastofnun.idordabanki.Globals;

import com.arnastofnun.idordabanki.activities.SearchScreen;
import com.arnastofnun.idordabanki.activities.SplashActivity;

import com.arnastofnun.idordabanki.models.Glossary;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.gson.reflect.TypeToken;
import com.robotium.solo.Solo;

import java.util.ArrayList;


/**
 * A test class intended to test the functionality of the
 * splash activity class
 * @author Karl Ásgeir Geirsson
 * @since 09.03.2015.
 */
public class SplashScreenTests extends ActivityInstrumentationTestCase2<SplashActivity> {
    /**
     * solo is used to do access functionalities while app is running
     */
    private Solo solo;
    private Globals globals;

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

        globals = (Globals) Globals.getContext();

        //Make sure the globals are reset
        SharedPrefs.getEditor().remove("dictionaries");
        SharedPrefs.getEditor().remove("languages");
        SharedPrefs.getEditor().remove("loc_dictionaries");
    }

    /**
     * Makes sure that everything is cleared before
     * the next test starts
     * @throws Exception
     */
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * A test to check if there is connection
     * to the database
     */
    public void testCheckConnection(){
        //TODO move to check connection test
       //assertTrue("No connection", getActivity().checkConnection());
    }

    /**
     * A method to test if everything is initialized correctly
     */
    public void testInitialize() throws Exception{
        getActivity();
        solo.waitForActivity(SearchScreen.class);
        solo.assertCurrentActivity("Wrong activity",SearchScreen.class);
        Globals globals = (Globals) Globals.getContext();
        assertNotNull("Dictionaries not initialized", SharedPrefs.getParcelableArray("dictionaries", new TypeToken<ArrayList<Glossary>>() {
        }.getType()));
        assertNotNull("Languages not initialized",SharedPrefs.getStringBiMap("languages"));
        assertNotNull("Localized languages not initialized",SharedPrefs.getStringBiMap("loc_dictionaries"));
    }




}
