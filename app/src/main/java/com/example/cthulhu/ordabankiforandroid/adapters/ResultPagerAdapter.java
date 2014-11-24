package com.example.cthulhu.ordabankiforandroid.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.cthulhu.ordabankiforandroid.Globals;
import com.example.cthulhu.ordabankiforandroid.Result;
import com.example.cthulhu.ordabankiforandroid.ResultsInfoFragment;

import java.util.List;

/**
 * This adapter handles the switching between
 * the fragments in the results-info screen
 * @author Karl Ásgier Geirsson
 * @since 23.11.2014.
 */
public class ResultPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Constructor for the adapter
     * @param fm the fragment manager
     */
    public ResultPagerAdapter(FragmentManager fm){
        super(fm);
    }

    /**
     * This method gets the correct fragment
     * for the given position
     * @param position the position
     * @return the fragment for the position
     */
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //Create a new results info fragment
        ResultsInfoFragment resultsInfoFragment = new ResultsInfoFragment();
        //Pass the position argument
        Bundle args = new Bundle();
        args.putInt("resultIndex",position);
        resultsInfoFragment.setArguments(args);
        return resultsInfoFragment;
    }

    /**
     * This method gets the total fragment count
     * @return the fragment count
     */
    @Override
    public int getCount() {
        Globals globals = (Globals) Globals.getContext();
        List<Result> resultList = globals.getResults();
        return resultList.size();
    }
}
