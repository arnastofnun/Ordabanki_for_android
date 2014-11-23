package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.Globals;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.Result;
import com.example.cthulhu.ordabankiforandroid.SynonymResult;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Results Adapter</h1>
 * <p>An adapter that implements most of the functions for the
 * results list view</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 15.10.2014.
 */
public class ResultsAdapter extends ArrayAdapter<Result> {
    //Get global values
    Globals g = (Globals) Globals.getContext();
    //Initialize a list for the results
    private ArrayList<Result> resultsList;
    private Context context;

    /**
     * Invoke the overwritten methods in superclass
     * -----------------------------------------------
     * Written by Karl Ásgeir
     * @param context the current context
     * @param listViewResourceId the resource Id of the list view that the adapter is being added to
     * @param resultsList the glossary list that is to be added to the list view
     */
    public ResultsAdapter(Context context, int listViewResourceId, List<Result> resultsList, List<SynonymResult> synonymResultList){
        super(context,listViewResourceId,resultsList);
        this.resultsList = new ArrayList<Result>(resultsList);

        this.context = context;
    }

    /**
     * A simple class to hold the values
     * for the text views in each list item
     * ------------------------------------
     * @author Karl Ásgeir Geirsson
     * @since 14.10.2014
     */
    private class ViewHolder{
        TextView term;
        TextView language;
        TextView glossary;
        TextView lexical_category;
        TextView synonyms;
        TextView definition;
        TextView example;

    }

    /**
     * This function implements the results list layout and
     * sets the correct values for the results in the results
     * list
     * -------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param position position of item in list
      *@param convertView the list view to convert
     * @param parent the parent view group
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Initialize
        final ViewHolder holder;

        Result result = resultsList.get(position);


        if(convertView == null){
            //inflate the layout
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.results_list,null);

            //Set the view holder

            holder = new ViewHolder();

            holder.term = (TextView) convertView.findViewById(R.id.resultTerm);
            holder.language = (TextView) convertView.findViewById(R.id.resultLanguage);
            holder.glossary = (TextView) convertView.findViewById(R.id.resultGlossary);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }




        //Get the languages and dictionaries from global values
        ArrayList<ArrayList<String>> languages = g.getLanguages();
        ArrayList<ArrayList<String>> dictionaries = g.getLoc_dictionaries();

        /*
            Find the index if the language and dictionary code set by the current result
            This is done so we can provide the correct language
         */
        int lang_index = languages.get(0).indexOf(result.getLanguage_code());
        int gloss_index = dictionaries.get(0).indexOf(result.getDictionary_code());

        //Set holder values
        holder.term.setText(result.getWord());
        holder.language.setText(languages.get(1).get(lang_index));
        holder.glossary.setText(dictionaries.get(1).get(gloss_index));


        return convertView;
    }




}
