package com.arnastofnun.idordabanki.tests.unittests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.test.ActivityUnitTestCase;

import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.arnastofnun.idordabanki.helpers.LocalizedValues;
import com.arnastofnun.idordabanki.models.Dictionary;
import com.arnastofnun.idordabanki.models.Glossary;
import com.arnastofnun.idordabanki.models.Language;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.common.collect.BiMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A method to unit test methods in the
 * SplashActivity
 * @author karlasgeir
 * @since 4/11/15.
 */
public class SplashActivityUnitTests2 extends ActivityUnitTestCase<SplashActivity> {
    Gson gson = new Gson();
    Language[] langMocks = gson.fromJson("[{\"code\":\"SANSKRIT\",\"info\":[{\"lang_code\": \"IS\", \"lang_name\": \"sanskrít\"}]}]", Language[].class);
    Dictionary[] dictMocks = gson.fromJson("[{\"dict_code\":\"LAEKN\",\"fjoldi\":33435,\"info\":[{\"lang_code\": \"IS\", \"dict_name\": \"Læknisfræði\"},{\"lang_code\": \"EN\", \"dict_name\": \"Medicine\"}]}]", Dictionary[].class);
    SharedPreferences.Editor editor;

    public SplashActivityUnitTests2(){
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
        editor.remove("dictionaries").remove("languages").remove("loc_dictionaries").putString("lang", "EN").apply();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),SplashActivity.class);
        startActivity(intent, null, null);
    }

    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }


    /**
     * A method to test the onDictionariesObtained method
     * THIS TEST WILL FAIL IF RUN WITH THE OTHERS, BUT WORKS IN ISOLATION
     * @throws JSONException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void testOnDictionariesObtained() throws JSONException, NoSuchFieldException, IllegalAccessException {
        assertFalse("There should be no dictionaries in sharedPrefs", SharedPrefs.contains("dicts"));
        Field privateField = SplashActivity.class.getDeclaredField("localizedValues");
        privateField.setAccessible(true);
        LocalizedValues localizedValues = (LocalizedValues) privateField.get(getActivity());


        getActivity().onDictionariesObtained(dictMocks);
        getActivity().onLanguagesObtained(langMocks);


        while(localizedValues.getStatus() != AsyncTask.Status.FINISHED){
            //Wait for the taks to finish
        }
        ArrayList<Glossary> glossaries = SharedPrefs.getParcelableArray("dictionaries", new TypeToken<ArrayList<Glossary>>() {
        }.getType());
        assertEquals("Should be equal", "LAEKN", glossaries.get(0).getDictCode());
        BiMap<String,String> languages = SharedPrefs.getStringBiMap("loc_dictionaries");

        assertEquals("Should be equal", "Medicine", languages.get("LAEKN"));
    }


    /**
     * A method to test the onLanguagesObtained method
     * THIS TEST WILL FAIL IF RUN WITH THE OTHERS, BUT WORKS IN ISOLATION
     * @throws JSONException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void testOnLanguagesObtained() throws JSONException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        assertFalse("There should be no dictionaries in sharedPrefs", SharedPrefs.contains("dicts"));
        Field privateField = SplashActivity.class.getDeclaredField("localizedValues");
        privateField.setAccessible(true);
        LocalizedValues localizedValues = (LocalizedValues) privateField.get(getActivity());


        getActivity().onDictionariesObtained(dictMocks);
        getActivity().onLanguagesObtained(langMocks);

        while(localizedValues.getStatus() != AsyncTask.Status.FINISHED){
            //Wait
        }

        BiMap<String,String> languages = SharedPrefs.getStringBiMap("languages");

        assertEquals("Should be equal", "sanskrít", languages.get("SANSKRIT"));

    }
}
