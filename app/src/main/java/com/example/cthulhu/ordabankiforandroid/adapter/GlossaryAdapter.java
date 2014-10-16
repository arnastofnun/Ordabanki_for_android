package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.Glossary;
import com.example.cthulhu.ordabankiforandroid.R;

import java.util.ArrayList;

/**
 * <h1>Glossary adapter</h1>
 * <p>An adapter that implements most of the functions for the
 * glossary list view</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014.
 */
public class GlossaryAdapter extends ArrayAdapter<Glossary> {

    //Initialize a list for the glossaries
    private ArrayList<Glossary> glossaryList;

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
        this.glossaryList = new ArrayList<Glossary>();
        this.glossaryList.addAll(glossaryList);
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
        TextView name;
        CheckBox checkBox;
    }


    /**
     * This function implements the glossary list layout and
     * sets an on click listener to set the selected glossary to selected.
     * It also sets the name of the glossary in the glossary list
     * --------------------------------------------------------------------
     * Written by Karl Ásgeir
     * @param position position of item in list
     * @param convertView the list view to convert
     * @param parent the parent view group
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Initialize
        View view = convertView;
        ViewHolder holder;


        if(view == null){
            //inflate the layout
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.glossary_list,parent,false);

            //Set the view holder
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.GlossaryText);
            holder.checkBox = (CheckBox) view.findViewById(R.id.GlossaryCheckbox);
            view.setTag(holder);

            //Set the on click listener on the checkbox
            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                //if the checkbox is clicked set the selected glossary to selected
                public void onClick(View v) {
                    CheckBox checkbox = (CheckBox) v ;
                    Glossary glossary = (Glossary) checkbox.getTag();
                    /*
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + checkbox.getText() +
                                    " is " + checkbox.isChecked(),
                            Toast.LENGTH_LONG).show();
                     */
                    glossary.setSelected(checkbox.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        //Set the current glossary name and checkbox status
        Glossary glossary = glossaryList.get(position);
        holder.name.setText(glossary.getName());
        holder.checkBox.setChecked(glossary.isSelected());
        holder.checkBox.setTag(glossary);

        return view;
    }


}
