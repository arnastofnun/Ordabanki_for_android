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
import java.util.Iterator;
/**
 * This class contains functions for the
 * pick glossary tab of the search screen
 * --------------------------------------
 * @author  Karl Ásgeir Geirsson
 * @date 9.10.2014
 */
public class PickGlossaryFragment extends Fragment {
    //Initialize
    private ArrayList<Glossary> glossaryList = new ArrayList<Glossary>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Load the .xml file for the pick glossary fragment
        View rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);
        //Display the pick glossary list
        displayListView(rootView);

        return rootView;
    }
    /**
     * This function is supposed to loop through the glossaries and add them to the glossary list.
     * For now it just puts some test glossaries in.
     * It also sets an on click listener that displays a toast for now.
     * It should open a link to the url of the glossary later
     * -------------------------------------------------------------------------------------------
     * @autor Karl Ásgeir Geirsson
     * @date 09.10.2014
     */
    private void displayListView(View rootView){
        //todo add url to the glossary list
        //todo get glossaries from API and put into glossary list
        //List of glossaries
        Glossary glossary = new Glossary("MED","Medicine",true, "http://www.ismal.hi.is/ob/uppl/laekn.html");
        glossaryList.add(glossary);
        glossary = new Glossary("PHYS","Physics",true, "http://www.ismal.hi.is/ob/uppl/edlisfr.html");
        glossaryList.add(glossary);

        //Creating a new glossary adapter
        GlossaryAdapter glossaryAdapter = new GlossaryAdapter(this.getActivity(), R.layout.glossary_list, glossaryList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        ListView listView = (ListView) rootView.findViewById(R.id.GlossaryList);
        listView.setAdapter(glossaryAdapter);

        //Setting the on item click listener to be ready for later use
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glossary glossary = (Glossary) parent.getItemAtPosition(position);
                //For now just display a toast for testing
                Toast.makeText(getActivity().getApplicationContext(), "Clicked on: " + glossary.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * This function returns an array of all selected glossaries from the glossary list
     * ---------------------------------------------------------------------------------
     * @autor Bill
     * @date 14.10.2014
     * @param glossaryList
     * @return selectedGlossaries
     */
    public ArrayList<String> getSelectedGlossaries(ArrayList<Glossary> glossaryList){
        Iterator<Glossary> iterator = glossaryList.iterator();
        ArrayList<String> selectedGlossaries = new ArrayList<String>();
        while(iterator.hasNext()){
            if (iterator.next().isSelected()){
                selectedGlossaries.add(iterator.next().getName());
            }
        }
        return selectedGlossaries;
    }

}
