package com.example.cthulhu.ordabankiforandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cthulhu.ordabankiforandroid.SearchScreenFragment;
import com.example.cthulhu.ordabankiforandroid.PickGlossaryFragment;
/**
 * This class contains a Tabs-Pager adapter that provides
 * fragment views to tabs
 * Created by karlasgeir on 9.10.2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int index){
        switch(index){
            case 0:
                //returns the search screen fragment
                return new SearchScreenFragment();
            case 1:
                //returns the pick glossary fragment
                return new PickGlossaryFragment();
        }
        return null;
    }

    @Override
    public int getCount(){
        return 2;
    }

}
