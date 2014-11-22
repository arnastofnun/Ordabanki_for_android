package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.AboutGlossaryActivity;
import com.example.cthulhu.ordabankiforandroid.Glossary;
import com.example.cthulhu.ordabankiforandroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * <h1>Glossary adapter</h1>
 * <p>An adapter that implements most of the functions for the
 * glossary list view</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014.
 */
public class GlossaryAdapter extends ArrayAdapter<Glossary> implements SectionIndexer {
    //Indexer for the alphabet sorting
    HashMap<String,Integer> alphaIndexer;
    //Sections for the alphabet sorting
    String[] sections;

    /**
    *   a list containing glossaries
    */    
    private static ArrayList<Glossary> glossaryList;
    /**
    *   current context
    */       
    private static Context context;

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


        this.glossaryList = new ArrayList<Glossary>();
        this.glossaryList = glossaryList;
        this.context = context;
    }

    /**
     * Sets up the alphabetical scrolling for the
     * list
     * -------------------------------------------
     * Written by Karl Ásgeir
     * @since 22.11.14
     */
    private void setUpAlphabeticalScrolling(ArrayList<Glossary> glossaryList){
        //Initialize the indexer
        alphaIndexer = new HashMap<String, Integer>();
        //Get the glossary size
        int size = glossaryList.size();
        //Go through the glossary list and add a section for each
        //different first character
        for(int x = 0;x<size;x++){
            //Get the name of the glossary
            String name = glossaryList.get(x).getName();
            //make sure it exists and isn't an empty string
            if(name != null) {
                if(!name.equals("")) {
                    //Get the first character
                    String ch = name.substring(0, 1).toUpperCase();
                    //If it's not in the indexer, put it there
                    if (!alphaIndexer.containsKey(ch)) {
                        alphaIndexer.put(ch, x);
                    }
                }
            }

        }
        //Set up the section list
        Set <String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sections = sectionList.toArray(sections);
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
        View view = convertView;
        ViewHolder holder;

        //If view has not been inflated
        if(view == null){
            //inflate the layout
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.glossary_list,parent,false);

            //Set the view holder
            holder = new ViewHolder();
            holder.glossaryName = (TextView) view.findViewById(R.id.GlossaryName);
            holder.link = (ImageButton) view.findViewById(R.id.GlossaryLink);
            holder.tick = (ImageView) view.findViewById(R.id.checked_image);
            view.setTag(holder);

            //Set an on click listener to the glossary
            holder.glossaryName.setOnClickListener(new View.OnClickListener() {
                //if the checkbox is clicked set the selected glossary to selected
                @Override
                public void onClick(View v) {
                    //Get the view of the checked image
                    TextView glossaryName = (TextView) v;
                    Glossary glossary = (Glossary) glossaryName.getTag();
                    View row = (View) v.getParent();
                    ImageView tick = (ImageView) row.findViewById(R.id.checked_image);
                    //If glossary is selected, deselect it and hide the tick
                    if(glossary.isSelected()) {
                        row.setBackgroundResource(R.color.glossary_notselected);
                        tick.setVisibility(View.INVISIBLE);
                        glossary.setSelected(false);
                    }
                    //If glossary is note selected, select it and show the tick
                   else{
                        row.setBackgroundResource(R.color.glossary_selected);
                        tick.setVisibility(View.VISIBLE);;
                        glossary.setSelected(true);

                    }

                }
            });


            //Set the on click listener on the link
            holder.link.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Glossary glossary = (Glossary) v.getTag();
                    if(!glossary.getUrl().equals("")) {
                        Log.v("glossary link",glossary.getUrl());
                        Intent intent = new Intent(context, AboutGlossaryActivity.class).putExtra("url_string",glossary.getUrl());
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(v.getContext(),v.getResources().getString(R.string.link_unaccessable),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) view.getTag();
        }




        //Set the current glossary name, tick status and link
        Glossary glossary = glossaryList.get(position);
        holder.link.setTag(glossary);
        //TODO: hide empty urls, this doesn't work any more
        if(glossary.getUrl().equals("")){
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

        return view;
    }


    /**
     * Gets the position for the correct section
     */
    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

}
