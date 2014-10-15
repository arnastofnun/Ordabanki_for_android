package com.example.cthulhu.ordabankiforandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cthulhu.ordabankiforandroid.SearchScreenFragment;
import com.example.cthulhu.ordabankiforandroid.PickGlossaryFragment;
/**
 * This class contains a Tabs-Pager adapter that enables
 * tabs to switch between fragments
 * -------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @date 9.10.2014
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    //Count of tabs
    private final int count = 2;

    //Gets the fragment at a specific index
    @Override
    public Fragment getItem(int index){
        Fragment fragment = null;
        //Simply select a fragment based on the index
        switch(index){
            case 0:
                //returns the search screen fragment
                return new SearchScreenFragment();
            case 1:
                //returns the pick glossary fragment
                return new PickGlossaryFragment();
        }
        return fragment;
    }

    //Return the total count of tabs
    @Override
    public int getCount(){
        return count;
    }

}
