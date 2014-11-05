package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cthulhu.ordabankiforandroid.AboutGlossaryActivity;
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
        this.glossaryList = new ArrayList<Glossary>();
        this.glossaryList = glossaryList;
        this.context = context;
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
            holder.glossaryName = (TextView) view.findViewById(R.id.GlossaryName);
            holder.link = (ImageButton) view.findViewById(R.id.GlossaryLink);
            holder.tick = (ImageView) view.findViewById(R.id.checked_image);
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



            holder.glossaryName.setOnClickListener(new View.OnClickListener() {
                //if the checkbox is clicked set the selected glossary to selected
                @Override
                public void onClick(View v) {
                    TextView glossaryName = (TextView) v;
                    Glossary glossary = (Glossary) glossaryName.getTag();
                    View row = (View) v.getParent();
                    ImageView tick = (ImageView) row.findViewById(R.id.checked_image);
                    if(glossary.isSelected()) {
                        row.setBackgroundResource(R.color.glossary_notselected);
                        tick.setVisibility(View.INVISIBLE);
                        glossary.setSelected(false);
                    }
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




        //Set the current glossary name and checkbox status
        Glossary glossary = glossaryList.get(position);
        holder.link.setTag(glossary);
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


}
