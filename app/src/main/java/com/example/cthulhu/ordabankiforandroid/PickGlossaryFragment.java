package com.example.cthulhu.ordabankiforandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.adapter.GlossaryAdapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Load the .xml file for the pick glossary fragment

        View rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);

            //Display the pick glossary list
            displayListView(rootView);
        return rootView;
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
     * todo get glossaries from API and put into glossary list
     * -------------------------------------------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param rootView the root view
     * @since 09.10.2014
     */
    private void displayListView(View rootView){
        //List of glossaries
        glossaryList = new ArrayList<Glossary>();
        Globals g = (Globals)this.getActivity().getApplication();
        glossaryList.addAll(g.getDictionaries());



        //Creating a new glossary adapter
        GlossaryAdapter glossaryAdapter = new GlossaryAdapter(this.getActivity(), R.layout.glossary_list, glossaryList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        final ListView listView = (ListView) rootView.findViewById(R.id.GlossaryList);
        listView.setAdapter(glossaryAdapter);

        //Button to check all glossaries
        Button checkallbutton = (Button) rootView.findViewById(R.id.select_all_glossaries);
        //Button to uncheck all glossaries
        Button decheckallbutton = (Button) rootView.findViewById(R.id.deselect_all_glossaries);
        //On click listener to select all glossaries
        checkallbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                        listitemview =  listView.getChildAt(index);
                        ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                        listitemview.setBackgroundResource(R.color.glossary_selected);
                        tick.setImageResource(R.drawable.ic_action_accept);
                    }
                    index++;
                }

            }
        });

        //On click listener to deselect all glossaries
        decheckallbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                        ImageView tick = (ImageView) listitemview.findViewById(R.id.checked_image);
                        listitemview.setBackgroundResource(R.color.glossary_notselected);
                        tick.setImageResource(0);

                    }
                    index++;
                }

            }
        });

        //Setting the on item click listener to be ready for later use
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glossary glossary = (Glossary) parent.getItemAtPosition(position);
                 //For now just display a toast for testing
                Toast.makeText(getActivity().getApplicationContext(), "Clicked on: " + glossary.getName() + " checked: " + glossary.isSelected(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * use: ArrayList<Glossary> glossaryList = getGlossaryList();
     * pre: nothing
     * post: This function returns an array list of all glossaries from the glossary list
     * ---------------------------------------------------------------------------------
     * Written by Bill
     * @since 14.10.2014
     * @return glossaryList
     */
    /*public static ArrayList<Glossary> getGlossaryList(){
            return glossaryList;
    }
*/
    /**
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
                Log.v("selected glossary: ", glossary.getDictCode());
                selectedGlossaries.add(glossary.getDictCode());
            }
            else {
                allSelected = false;
            }
        }

        return selectedGlossaries;
    }
    public static boolean areAllSelected(){return allSelected;}
}
