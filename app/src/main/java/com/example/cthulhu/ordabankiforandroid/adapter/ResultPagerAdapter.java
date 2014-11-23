package com.example.cthulhu.ordabankiforandroid.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.cthulhu.ordabankiforandroid.Globals;
import com.example.cthulhu.ordabankiforandroid.Result;
import com.example.cthulhu.ordabankiforandroid.ResultsInfoFragment;

import java.util.List;

/**
 * Created by karlasgeir on 23.11.2014.
 */
public class ResultPagerAdapter extends FragmentStatePagerAdapter {
    public ResultPagerAdapter(FragmentManager fm){
        super(fm);
    }

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


    @Override
    public int getCount() {
        Globals globals = (Globals) Globals.getContext();
        List<Result> resultList = globals.getResults();
        return resultList.size();
    }
}
