package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

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
    private static ArrayList<Glossary> glossaryList;


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
        this.glossaryList = glossaryList;
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
        CheckBox checkBox;
        ImageButton link;
    }





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
            holder.checkBox = (CheckBox) view.findViewById(R.id.GlossaryCheckbox);
            holder.link = (ImageButton) view.findViewById(R.id.GlossaryLink);
            view.setTag(holder);

            //Set the on click listener on the checkbox
            /*
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView == null) {
                        Log.v("buttonView", "null");
                    } else {
                        Log.v("buttonView", "OK");
                    }
                    Glossary glossary = (Glossary) buttonView.getTag();
                    if (glossary == null) {
                        Log.v("glossary", "null");
                    } else {
                        Log.v("glossary", "OK");
                    }
                    glossary.setSelected(isChecked);
                }
            });
            */



            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                //if the checkbox is clicked set the selected glossary to selected
                @Override
                public void onClick(View v) {
                    CheckBox checkbox = (CheckBox) v;
                    Glossary glossary = (Glossary) checkbox.getTag();
                    if (glossary == null) {
                        Log.v("glossary", "null");
                    } else {
                        Log.v("glossary", "OK");
                    }
                    glossary.setSelected(checkbox.isChecked());
                }
            });


            //Set the on click listener on the link
            holder.link.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Glossary glossary = (Glossary) v.getTag();
                    if(!glossary.getUrl().equals("")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(glossary.getUrl()));
                        v.getContext().startActivity(intent);
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




        //Set the current glossary name and checkbox status
        Glossary glossary = glossaryList.get(position);
        holder.link.setTag(glossary);
        holder.checkBox.setText(glossary.getName());
        holder.checkBox.setChecked(glossary.isSelected());
        holder.checkBox.setTag(glossary);

        return view;
    }


}
