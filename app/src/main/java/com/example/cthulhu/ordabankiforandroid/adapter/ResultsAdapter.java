package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.Result;

import java.util.ArrayList;

/**
 * An adapter that implements most of the functions for the
 * results list view
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @date 15.10.2014.
 */
public class ResultsAdapter extends ArrayAdapter<Result> {
    //Initialize a list for the results
    private ArrayList<Result> resultsList;

    public ResultsAdapter(Context context, int textViewResourceID){
        super(context, textViewResourceID);
    }

    public ResultsAdapter(Context context, int textViewResourceId, ArrayList<Result> resultsList){
        super(context,textViewResourceId,resultsList);
        this.resultsList = new ArrayList<Result>();
        this.resultsList.addAll(resultsList);
    }

    /**
     * A simple class to hold the values
     * for the text views in each list item
     * ------------------------------------
     * @author Karl Ásgeir Geirsson
     * @date 14.10.2014
     */
    private class ViewHolder{
        TextView term;
        TextView language;
        TextView glossary;
    }

    /**
     * This function implements the results list layout and
     * sets the correct values for the results in the results
     * list
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
            view = vi.inflate(R.layout.results_list,null);

            //Set the view holder
            holder = new ViewHolder();
            holder.term = (TextView) view.findViewById(R.id.resultTerm);
            holder.language = (TextView) view.findViewById(R.id.resultLanguage);
            holder.glossary = (TextView) view.findViewById(R.id.resultGlossary);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        //Set the current glossary name and checkbox status
        Result result = resultsList.get(position);
        holder.term.setText(result.getTerm());
        holder.language.setText("(" + result.getLanguage()+")");
        holder.glossary.setText("["+result.getGlossary()+"]");

        return view;
    }

}
