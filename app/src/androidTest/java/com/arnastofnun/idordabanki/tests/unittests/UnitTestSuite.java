package com.arnastofnun.idordabanki.tests.unittests;

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
public class UnitTestSuite {
    public static Test suite(){
        ArrayList<Class> testClasses = new ArrayList<>();
        testClasses.addAll(Arrays.asList(SplashActivityUnitTests2.class,SplashActivityUnitTests.class));

        TestSuite suite = new TestSuite();
        Iterator iterator = testClasses.iterator();
        while(iterator.hasNext()) {
            Class item = (Class) iterator.next();
            suite.addTestSuite(item);
        }
        return suite;
    }
}
