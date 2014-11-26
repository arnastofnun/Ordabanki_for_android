package com.example.cthulhu.ordabankiforandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.activities.AboutGlossaryActivity;
import com.example.cthulhu.ordabankiforandroid.Glossary;
import com.example.cthulhu.ordabankiforandroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * <h1>Glossary adapter</h1>
 * <p>An adapter that implements most of the functions for the
 * glossary list view</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014.
 */
public class GlossaryAdapter extends ArrayAdapter<Glossary> implements SectionIndexer {
    HashMap<String,Integer> alphaIndexer; //Indexer for the alphabet sorting
    String[] sections; //Sections for the alphabet sorting
    private static ArrayList<Glossary> glossaryList; //a list containing glossaries
    private static Context context; //current context

    /**
     * Invoke the overwritten methods in superclass
     * -----------------------------------------------
     * Written by Karl Ásgeir
     * @param context the current context
     * @param listViewResourceId the resource Id of the list view that the adapter is being added to
     * @param glossaryList the glossary list that is to be added to the list view
     */
    public GlossaryAdapter(Context context, int listViewResourceId, ArrayList<Glossary> glossaryList){
        super(context,listViewResourceId,glossaryList);
        //Sets up the alphabetical scrolling
        setUpAlphabeticalScrolling(glossaryList);
        //Set the glossary
        GlossaryAdapter.glossaryList = new ArrayList<Glossary>();
        GlossaryAdapter.glossaryList = glossaryList;
        //Set the context
        GlossaryAdapter.context = context;
    }

    /**
     * Sets up the alphabetical scrolling for the
     * list
     * -------------------------------------------
     * Written by Karl Ásgeir
     * @since 22.11.14
     */
    private void setUpAlphabeticalScrolling(ArrayList<Glossary> glossaryList){
        //Add sections to the alpha indexer
        alphaIndexer = addSections(glossaryList);
        //Set up the section list
        sections = setupSections(alphaIndexer);
    }

    /**
     * This method iterates adds the sections needed to the alphaIndexer
     * @param list the list that should be alphabetically indexed
     */
    private HashMap<String, Integer> addSections(ArrayList<Glossary> list){
        HashMap<String,Integer> alphaIndexer = new HashMap<String, Integer>();
        //Iterate through the list
        for(int i = 0;i<list.size();i++){
            //Get the name of the glossary
            String name = list.get(i).getName();
            //If it's not already in the indexer, put it there
            if(name != null && !name.isEmpty()){
                String firstLetter = name.substring(0,1).toUpperCase();
                if(!alphaIndexer.containsKey(firstLetter)){
                    alphaIndexer.put(firstLetter,i);
                }
            }
        }
        return alphaIndexer;
    }

    /**
     * This method sets up the section list
     * @param alphaIndexer the alpha indexer
     * @return the section list
     */
    private String[] setupSections(HashMap<String, Integer> alphaIndexer){
        //Create an arraylist from the alpha indexer keys
        ArrayList<String> sectionList = new ArrayList<String>( alphaIndexer.keySet());
        //sort the list
        Collections.sort(sectionList);
        //Return the section list
        return sectionList.toArray(new String[sectionList.size()]);
    }



    /**
     * A simple class to hold the checkbox
     * and text view of each item in the
     * glossary list
     * ------------------------------------
     * Written by Karl Ásgeir
     * @since 14.10.2014
     */
    private class ViewHolder{
        TextView glossaryName;
        ImageButton link;
        ImageView tick;
    }


    /**
     * Gets the view of the current item in the list view
     * @param position The position of the item in the list
     * @param convertView The view of the item in the list
     * @param parent The parent view
     * @return convertView is the view of the current item
     */
    public View getView(int position, View convertView, ViewGroup parent){
        //Initialize
        ViewHolder holder;
        //If view has not been inflated
        if(convertView== null){
            //inflate the layout
            convertView = inflateView(parent);
            //Set the view holder
            holder = setupViewHolder(convertView);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Set the current glossary name, tick status and link in the viewholder
        Glossary glossary = glossaryList.get(position);
        setViewHolder(holder,glossary);
        return convertView;
    }

    /**
     * Sets values to the view holder
     * @param holder the view holder that the values should be bound to
     * @param glossary the glossary that the values come from
     */
    private void setViewHolder(ViewHolder holder,Glossary glossary){
        holder.link.setTag(glossary);
        if(glossary.getUrl().isEmpty()){
            holder.link.setImageResource(android.R.color.transparent);
        }
        else{
            holder.link.setImageResource(R.drawable.ic_action_web_site);
        }
        holder.glossaryName.setText(glossary.getName());
        View row = (View) holder.glossaryName.getParent();
        if(glossary.isSelected()){
            row.setBackgroundResource(R.color.glossary_selected);
            holder.tick.setVisibility(View.VISIBLE);
        }
        else{
            row.setBackgroundResource(R.color.glossary_notselected);
            holder.tick.setVisibility(View.INVISIBLE);
        }
        holder.glossaryName.setTag(glossary);
    }




    /**
     * A method to inflate a view
     * @param parent The viewgroup that the inflated view is appended to
     * @return the inflated view
     */
    private View inflateView(ViewGroup parent){
        LayoutInflater vi = LayoutInflater.from(getContext());
        return  vi.inflate(R.layout.glossary_list,parent, false);
    }

    /**
     * A method that set's up a new viewholder
     * @param view the view that the view holder is set up from
     * @return the vie holder
     */
    private ViewHolder setupViewHolder(View view){
        //Set the views to the view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.glossaryName = (TextView) view.findViewById(R.id.GlossaryName);
        viewHolder.link = (ImageButton) view.findViewById(R.id.GlossaryLink);
        viewHolder.tick = (ImageView) view.findViewById(R.id.checked_image);
        view.setTag(viewHolder);

        //Set an on click listener to the tick that does the same thing as the one below

        //Set an on click listener to the tick
        viewHolder.tick.setOnClickListener(new View.OnClickListener() {
            //if the checkbox is clicked set the selected glossary to selected
            @Override
            public void onClick(View view) {toggleSelect(view);}
        });


        //Set an on click listener to the glossary
        viewHolder.glossaryName.setOnClickListener(new View.OnClickListener() {
            //if the name is clicked set the selected glossary to selected
            @Override
            public void onClick(View view) {toggleSelect(view);}
        });

        //Set the on click listener on the link
        viewHolder.link.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openLink(view);
            }
        });

        return viewHolder;
    }

    /**
     * A method that toogles the selected state of
     * the glossary on click
     * @param view the view that was clicked
     */
    private void toggleSelect(View view){
        //Get the view of the checked image
        View row = (View) view.getParent();
        ImageView tick = (ImageView) row.findViewById(R.id.checked_image);
        TextView glossname = (TextView) row.findViewById(R.id.GlossaryName);
        Glossary glossary = (Glossary) glossname.getTag();


        //If glossary is selected, deselect it and hide the tick
        if(glossary.isSelected()) {
            row.setBackgroundResource(R.color.glossary_notselected);
            tick.setVisibility(View.INVISIBLE);
            glossary.setSelected(false);
        }
        //If glossary is note selected, select it and show the tick
        else{
            row.setBackgroundResource(R.color.glossary_selected);
            tick.setVisibility(View.VISIBLE);
            glossary.setSelected(true);

        }
    }

    /**
     * A method that opens a link if a
     * link was pressed in the glossary list
     * @param view the view that was clicked
     */
    private void openLink(View view){
        //Get the glossary that was clicked on
        Glossary glossary = (Glossary) view.getTag();
        if(!glossary.getUrl().isEmpty()) {
            //Open the link
            Intent intent = new Intent(context, AboutGlossaryActivity.class).putExtra("url_string",glossary.getUrl());
            context.startActivity(intent);
        }
        else{
            Toast.makeText(view.getContext(),view.getResources().getString(R.string.link_unaccessable),Toast.LENGTH_LONG).show();
        }
    }



    /**
     * Gets the position for the correct section
     */
    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.get(sections[section]);
    }

    /**
     * Gets the section for a specific positon
     * @param position the position
     * @return the section
     */
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    /**
     * gets all the sections
     * @return the sections
     */
    @Override
    public Object[] getSections() {
        return sections;
    }

}
