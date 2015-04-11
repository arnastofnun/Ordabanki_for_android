package com.arnastofnun.idordabanki.tests.unittests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityUnitTestCase;

import com.arnastofnun.idordabanki.activities.SelectLanguageActivity;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.arnastofnun.idordabanki.preferences.LocaleSettings;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A method to unit test methods in the
 * SplashActivity
 * @author karlasgeir
 * @since 4/11/15.
 */
public class SplashActivityUnitTests extends ActivityUnitTestCase<SplashActivity> {
    SharedPreferences.Editor editor;
    LocaleSettings localeSettings;


    public SplashActivityUnitTests(){
        super(SplashActivity.class);
    }

    /**
     * A method to set up the tests
     * It's run before every test
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        editor = SharedPrefs.getEditor();
        editor.putString("lang", "EN").apply();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),SplashActivity.class);
        startActivity(intent, null, null);
        localeSettings = new LocaleSettings(getActivity());
    }

    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }


    /**
     * A test that tests the isLocaleSet method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void testIsLocaleSet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        editor.remove("lang").apply();
        assertFalse("Locale settings should have been cleared", localeSettings.getLocaleStatus());
        Method isLocaleSet = SplashActivity.class.getDeclaredMethod("isLocaleSet", null);
        isLocaleSet.setAccessible(true);
        isLocaleSet.invoke(getActivity(), null);
        Intent intent = getStartedActivityIntent();
        assertEquals("Should have gone to language selection", intent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName(), SelectLanguageActivity.class.getName());
    }

    /**
     * A test that tests the isLocaleSet method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void testIsLocaleSet2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertTrue("Locale settings should not have been cleared", localeSettings.getLocaleStatus());
        Method isLocaleSet = SplashActivity.class.getDeclaredMethod("isLocaleSet", null);
        isLocaleSet.setAccessible(true);
        isLocaleSet.invoke(getActivity(), null);
        assertFalse("Activity should not be finished yet", isFinishCalled());
    }

    /**
     * A test that tests the initialize method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void testInitialize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException {
        assertTrue("Locale settings should not have been cleared", localeSettings.getLocaleStatus());
        Method isLocaleSet = SplashActivity.class.getDeclaredMethod("initialize", null);
        isLocaleSet.setAccessible(true);
        isLocaleSet.invoke(getActivity(), null);
        assertFalse("Activity should not be finished yet", isFinishCalled());
    }




}
