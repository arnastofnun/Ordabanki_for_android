package com.arnastofnun.idordabanki;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.arnastofnun.idordabanki.adapters.GlossaryAdapter;

import java.util.ArrayList;
/**
 * This class contains functions for the
 * pick glossary tab of the search screen
 * --------------------------------------
 * @author  Karl Ásgeir Geirsson
 * @since 9.10.2014
 */
public class PickGlossaryFragment extends Fragment {
   /*
    * Data invariants:
    *   glossaryList is a list that contains all the glossaries to be used
    *   in the Orðabanki app
    */
    private static ArrayList<Glossary> glossaryList;
    private static boolean allSelected = true;
    private static ListView listView;

    /**
     * Written by Karl Ásgeir Geirsson
     * Run when the view is created
     * @param inflater the layout inflater
     * @param container the ViewGroup
     * @param savedInstanceState the saved instances
     * @return returns the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Load the .xml file for the pick glossary fragment

        View rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);
            //Display the pick glossary list
            displayListView(rootView);
        return rootView;
    }

    /**
     * Written by Bill
     * Saves the glossary state when it looses focus
     * Post: Glossary state has been saved
     */
    @Override
    public void onPause(){
        super.onPause();
        Globals g= (Globals) this.getActivity().getApplication();
        if(glossaryList == null){
            Log.v("GlossaryList","null");
        }
        g.setGlossaryState(glossaryList);
    }

    /**
     * Written by Bill
     * sets the glossary state when resumed
     * Post: glossary state has been set
     */
    @Override
    public void onResume(){
        super.onResume();
        Globals g= (Globals) this.getActivity().getApplication();
        if(g==null){
           Log.v("Globals","null");
        }
        else if(g.getGlossaryState() == null){
            Log.v("GlossaryState","null");
        }
        else {
            resumeGlossaryState(g);
        }

    }

    public void resumeGlossaryState(Globals globals){
        glossaryList = globals.getGlossaryState();
        int index = 0;
        View listitemview;
        for (Glossary glossary : glossaryList) {
            listitemview = listView.getChildAt(index);
            if (listitemview != null) {
                if (glossary.isSelected()) {
                    ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                    listitemview.setBackgroundResource(R.color.glossary_selected);
                    tick.setVisibility(View.VISIBLE);
                } else {
                    ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                    listitemview.setBackgroundResource(R.color.glossary_notselected);
                    tick.setVisibility(View.INVISIBLE);
                }
            }
            index++;
        }
    }


    /**use:displayListView(rootView)
     * pre: rootView is of type View
     * This function is supposed to loop through the glossaries and add them to the glossary list.
     * For now it just puts some test glossaries in.
     * It also sets an on click listener that displays a toast for now.
     * It should open a link to the url of the glossary later
     * This function is supposed to loop through the glossaries and add them to the glossary list.
     * For now it just puts some test glossaries in.
     * It also sets an on click listener that displays a toast for now.
     * -------------------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param rootView the root view
     * @since 09.10.2014
     */
    private void displayListView(View rootView){
        //List of glossaries
            Globals g= (Globals) this.getActivity().getApplication();
            glossaryList = new ArrayList<Glossary>();
            //g = (Globals) this.getActivity().getApplication();
            glossaryList.addAll(g.getDictionaries());

        //Creating a new glossary adapter
        GlossaryAdapter glossaryAdapter = new GlossaryAdapter(this.getActivity(), R.layout.glossary_list, glossaryList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        listView = (ListView) rootView.findViewById(R.id.GlossaryList);
        listView.setAdapter(glossaryAdapter);

        //Button to check all glossaries
        Button checkallbutton = (Button) rootView.findViewById(R.id.select_all_glossaries);
        //Button to uncheck all glossaries
        Button decheckallbutton = (Button) rootView.findViewById(R.id.deselect_all_glossaries);
        //On click listener to select all glossaries
        checkallbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               checkAllGlossaries();

            }
        });

        //On click listener to deselect all glossaries
        decheckallbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                decheckAllGlossaries();
            }
        });

    }

    /**
     * This method checks all glossaries
     * Written by Karl Ásgeir Geirsson
     */
    private void checkAllGlossaries(){
        //variables
        int index=0;
        View listitemview;
        //Go through the glossary list
        for(Glossary glossary : glossaryList){
            //If glossary is checked
            if(!glossary.isSelected()){
                //Set the selected status of the glossary
                glossary.setSelected(true);
                //Get the correct item in the list
                listitemview = listView.getChildAt(index);
                if(listitemview != null) {
                    ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                    listitemview.setBackgroundResource(R.color.glossary_selected);
                    tick.setVisibility(View.VISIBLE);
                }
            }
            index++;
        }
    }

    /**
     * This method dechecks all glossaries
     * Written by Karl Ásgeir Geirsson
     */
    private void decheckAllGlossaries(){
        //variables
        int index=0;
        View listitemview;
        //Go through the glossary list
        for(Glossary glossary : glossaryList){
            //If glossary is checked
            if(glossary.isSelected()){
                //Set the selected status of the glossary
                glossary.setSelected(false);
                //Get the correct item in the list
                listitemview =  listView.getChildAt(index);
                if(listitemview != null) {
                    ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                    listitemview.setBackgroundResource(R.color.glossary_notselected);
                    tick.setVisibility(View.INVISIBLE);
                }

            }
            index++;
        }
    }

    /**
     * Written by Bill
     * use: ArrayList<Glossary> glossaryList = getSelectedGlossaries();
     * pre: nothing
     * post: his function returns an array list of all selected glossaries from the glossary list
     * @return selectedGlossaries
     */
    public static ArrayList<String> getSelectedGlossaries(){
        //Go through the glossaries and add the selected ones to selectedGlossaries
        ArrayList<String> selectedGlossaries = new ArrayList<String>();
        for(Glossary glossary : glossaryList) {

            if(glossary.isSelected()){
                selectedGlossaries.add(glossary.getDictCode());
            }
            else {
                allSelected = false;
            }
        }

        return selectedGlossaries;
    }

    /**
     * Written by Bill
     * use: boolean selected = areAllSelected();
     * @return true if all glossaries are selected, else false
     */
    public static boolean areAllSelected(){return allSelected;}
}
