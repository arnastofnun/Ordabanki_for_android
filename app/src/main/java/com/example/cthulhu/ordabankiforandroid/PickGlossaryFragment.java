package com.example.cthulhu.ordabankiforandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class contains functions for the
 * pick glossary tab of the search screen
 * Created by karlasgeir on 9.10.2014.
 */
public class PickGlossaryFragment extends Fragment {

    //Load the .xml file for the pick glossary fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);
        return rootView;
    }

}
