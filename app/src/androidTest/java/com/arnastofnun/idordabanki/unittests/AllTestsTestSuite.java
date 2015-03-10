package com.arnastofnun.idordabanki.unittests;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A test suite to enable us to run all tests in one click
 * @author karlasgeir
 * @since 3/10/15.
 */
public class AllTestsTestSuite {
    public static Test suite(){
        ArrayList<Class> testClasses = new ArrayList<>();
        testClasses.addAll(Arrays.asList(ResultScreenTest.class, SearchScreenTest.class,
                SearchTest.class, SplashScreenTests.class, TermIDTests.class, TermIDTests2.class));

        TestSuite suite = new TestSuite();
        Iterator iterator = testClasses.iterator();
        while(iterator.hasNext()) {
            Class item = (Class) iterator.next();
            suite.addTestSuite(item);
        }
        return suite;
    }
}
