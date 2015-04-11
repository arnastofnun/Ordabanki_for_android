package com.arnastofnun.idordabanki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.adapters.GlossaryAdapter;
import com.arnastofnun.idordabanki.models.Glossary;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.gson.reflect.TypeToken;
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
    private ArrayList<Glossary> glossaryList;
    private static ListView listView;
    private static GlossaryAdapter glossaryAdapter;
    private View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_pick_glossary,container,false);
        //Display the pick glossary list
        displayListView();
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
        SharedPrefs.putParcelableArray("glossary_state", glossaryAdapter.getGlossaryList());
    }

    /**
     * Written by Bill
     * sets the glossary state when resumed
     * Post: glossary state has been set
     */
    @Override
    public void onResume(){
        super.onResume();
        if(SharedPrefs.contains("glossary_state")){
            glossaryList = SharedPrefs.getParcelableArray("glossary_state", new TypeToken<ArrayList<Glossary>>() {
            }.getType());
        }
    }

    /**use:displayListView()
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
     * @since 09.10.2014
     */
    private void displayListView(){

        //List of glossaries
        if(SharedPrefs.contains("glossary_state")){
            glossaryList = SharedPrefs.getParcelableArray("glossary_state",new TypeToken<ArrayList<Glossary>>(){}.getType());
        } else {
            glossaryList = SharedPrefs.getParcelableArray("dictionaries", new TypeToken<ArrayList<Glossary>>() {
            }.getType());

        }




        //Creating a new glossary adapter
        glossaryAdapter = new GlossaryAdapter(this.getActivity(), R.layout.glossary_list, glossaryList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        listView = (ListView) rootView.findViewById(R.id.GlossaryList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(glossaryAdapter);

        if (!SharedPrefs.contains("glossary_state")) {
            checkAllGlossaries();
        }

        //Button to check all glossaries
        final Button checkallbutton = (Button) rootView.findViewById(R.id.select_all_glossaries);

        //Button to uncheck all glossaries
        final Button decheckallbutton = (Button) rootView.findViewById(R.id.deselect_all_glossaries);
        if(areAllSelected()){
            checkallbutton.setVisibility(View.GONE);
            decheckallbutton.setVisibility(View.VISIBLE);
        } else{
            decheckallbutton.setVisibility(View.GONE);
            checkallbutton.setVisibility(View.VISIBLE);
        }
        //On click listener to select all glossaries
        checkallbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
               checkAllGlossaries();
               checkallbutton.setVisibility(View.GONE);
               decheckallbutton.setVisibility(View.VISIBLE);
            }
        });

        //On click listener to deselect all glossaries
        decheckallbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                decheckAllGlossaries();
                decheckallbutton.setVisibility(View.GONE);
                checkallbutton.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * This method checks all glossaries
     * Written by Karl Ásgeir Geirsson
     */
    private void checkAllGlossaries(){
        for(int i=0;i<glossaryAdapter.getCount();i++){
            listView.setItemChecked(i, true);
            glossaryAdapter.setChecked(i,true);
        }
        glossaryAdapter.notifyDataSetChanged();
    }


    /**
     * This method unchecks all glossaries
     * Written by Karl Ásgeir Geirsson
     */
    private void decheckAllGlossaries(){
        for(int i=0;i<glossaryAdapter.getCount();i++){
            listView.setItemChecked(i,false);
            glossaryAdapter.setChecked(i,false);
        }
        glossaryAdapter.notifyDataSetChanged();
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

        ArrayList<String> selectedGlossaries = new ArrayList<>();
        for(Glossary glossary : glossaryAdapter.getGlossaryList()) {
            if(glossary.isSelected()){
                selectedGlossaries.add(glossary.getDictCode());
            }
        }
        return selectedGlossaries;
    }

    /**
     * Written by Bill
     * use: boolean selected = areAllSelected();
     * @return true if all glossaries are selected, else false
     */
    public static boolean areAllSelected(){
        return (listView.getCheckedItemCount()==glossaryAdapter.getCount());}
}
