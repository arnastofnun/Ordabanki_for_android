package com.example.cthulhu.ordabankiforandroid.unittests;

import android.app.Fragment;
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;

import com.example.cthulhu.ordabankiforandroid.R;

/**
 * Created by tka on 24.10.2014.
 */
public class ChooseLanguagesTest extends ActivityInstrumentationTestCase2<TestFragmentActivity> {

    /*
    * TRYING TO GET THIS TO WORK: http://blog.denevell.org/android-testing-fragments.html
    * WOULD LIKE TO TEST SPINNERS
    * */
    private TestFragmentActivity mActivity;

    private Spinner spinner;
    public static final int TEST_STATE_DESTROY_POSITION = 2;
    public static final String TEST_STATE_DESTROY_SELECTION = "English";

    public ChooseLanguagesTest() {
        super(TestFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        mActivity = getActivity();


    }


    private Fragment startFragment(Fragment fragment) {
        FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        transaction.add(R.id.activity_test_fragment_linearlayout, fragment, "tag");
        transaction.commit();
        getInstrumentation().waitForIdleSync();
        Fragment frag = mActivity.getFragmentManager().findFragmentByTag("tag");
        return frag;
    }
    public void testFragment() {
        ChooseLanguagesTest fragment = new ChooseLanguagesTest() {
            //Override methods and add assertations here.

        };
        //Fragment frag = startFragment(fragment);  <-- THIS GIVES AN ERROR

    }

}
