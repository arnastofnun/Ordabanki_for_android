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
 * An adapter that implements most of the functions for the
 * glossary list view
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @date 14.10.2014.
 */
public class GlossaryAdapter extends ArrayAdapter<Glossary> {

    //Initialize a list for the glossaries
    private ArrayList<Glossary> glossaryList;


    public GlossaryAdapter(Context context, int textViewResourceID){
        super(context, textViewResourceID);
    }

    public GlossaryAdapter(Context context, int textViewResourceId, ArrayList<Glossary> glossaryList){
        super(context,textViewResourceId,glossaryList);
        this.glossaryList = new ArrayList<Glossary>();
        this.glossaryList.addAll(glossaryList);
    }

    /**
     * A simple class to hold the checkbox
     * and text view of each item in the
     * glossary list
     * ------------------------------------
     * @author Karl Ásgeir Geirsson
     * @date 14.10.2014
     */
    private class ViewHolder{
        TextView name;
        CheckBox checkBox;
    }


    /**
     * This function implements the glossary list layout and
     * sets an on click listener to set the selected glossary to selected.
     * It also sets the name of the glossary in the glossary list
     * -------------------------------
     * @author Karl Ásgeir Geirsson
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Initialize
        View view = convertView;
        ViewHolder holder = null;


        if(view == null){
            //inflate the layout
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.glossary_list,null);

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
