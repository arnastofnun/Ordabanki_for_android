package com.example.cthulhu.ordabankiforandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.GlossaryAdapter;

import java.util.ArrayList;

/**
 * This class contains functions for the
 * pick glossary tab of the search screen
 * Created by karlasgeir on 9.10.2014.
 */
public class PickGlossaryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Load the .xml file for the pick glossary fragment
        View rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);


        displayListView(rootView);

        return rootView;
    }


    private void displayListView(View rootView){
        //List of glossaries
        ArrayList<Glossary> glossaryList = new ArrayList<Glossary>();
        Glossary glossary = new Glossary("MATH","Mathematics",true);
        glossaryList.add(glossary);
        glossary = new Glossary("PHYS","Physics",true);
        glossaryList.add(glossary);

        //Creating a new glossary adapter
        GlossaryAdapter glossaryAdapter = new GlossaryAdapter(this.getActivity(), R.layout.glossary_list, glossaryList);

        ListView listView = (ListView) rootView.findViewById(R.id.GlossaryList);
        listView.setAdapter(glossaryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glossary glossary = (Glossary) parent.getItemAtPosition(position);
                //For now just display a toast for testing
                Toast.makeText(getActivity().getApplicationContext(), "Clicked on: " + glossary.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
