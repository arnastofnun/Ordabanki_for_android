package com.arnastofnun.idordabanki.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arnastofnun.idordabanki.fragments.ChooseLanguagesFragment;
import com.arnastofnun.idordabanki.fragments.PickGlossaryFragment;
import com.arnastofnun.idordabanki.fragments.SearchScreenFragment;

import java.util.ArrayList;

/**
 * This class contains a Tabs-Pager adapter that enables
 * tabs to switch between fragments
 * -------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 9.10.2014
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    /**
     * Invoke the overwritten methods in superclass
     * --------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param fm the fragment manager for the fragments
     */
    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    //Count of tabs
    private final int count = 3;

    //Tab titles
    private ArrayList<String> tabtitles = new ArrayList<>();


    //Gets the fragment at a specific index

    /**
     * This method returns a fragment based on a specific index
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param index the index to get the
     * @return fragment, the fragment based on the index (null if no fragment has that index)
     */
    @Override
    public Fragment getItem(int index){
        //Simply select a fragment based on the index
        switch(index){
            case 0:
                //returns the search screen fragment
                return new SearchScreenFragment();
            case 1:
                //returns the pick glossary fragment
                return new PickGlossaryFragment();
            case 2:
                return new ChooseLanguagesFragment();
            default: return null;
        }
    }

    /**
     * Simple way to get the total number of tabs
     * ------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @return count the number of tabs
     */
    @Override
    public int getCount(){
        return count;
    }

    /**
     * Sets the title of the tabs from an array of strings
     * Written by Karl Ásgeir Geirsson
     * @param tabs an array of title strings for the tabs
     */
    public void setTabTitles(ArrayList<String> tabs){
        tabtitles = tabs;
    }

    /**
     * This is a method to get the title of the page from
     * a specific position
     * @param position the position of the tab
     * @return returns the title of the tab
     */
    @Override
    public CharSequence getPageTitle(int position){
        return tabtitles.get(position);
    }

}
