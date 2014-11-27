package com.arnastofnun.idordabanki.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.arnastofnun.idordabanki.ResultInfo;
import com.arnastofnun.idordabanki.ResultsInfoFragment;

/**
 * This adapter handles the switching between
 * the fragments in the results-info screen
 * @author Karl Ásgeir Geirsson
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
        Log.v("position",String.valueOf(position));
        //Create a new results info fragment
        ResultsInfoFragment resultsInfoFragment = new ResultsInfoFragment();
        //Pass the position argument
        Bundle args = new Bundle();
        if(ResultInfo.getTermId() != null){
            args.putString("TermID",ResultInfo.getTermId());
        }
        else{
            args.putInt("resultIndex",position);
        }
        resultsInfoFragment.setArguments(args);
        return resultsInfoFragment;
    }





    /**
     * This method gets the total fragment count
     * @return the fragment count
     */
    @Override
    public int getCount() {
        return ResultInfo.getPageCount();
    }
}
