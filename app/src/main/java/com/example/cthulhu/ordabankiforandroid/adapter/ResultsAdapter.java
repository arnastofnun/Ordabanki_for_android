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
import java.util.Iterator;
import java.util.ListIterator;

/**
 * <h1>Results Adapter</h1>
 * <p>An adapter that implements most of the functions for the
 * results list view</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 15.10.2014.
 */
public class ResultsAdapter extends ArrayAdapter<Result> {
    //Initialize a list for the results
    private Result[] resultsList;
    private Context context;

    /**
     * Invoke the overwritten methods in superclass
     * -----------------------------------------------
     * Written by Karl Ásgeir
     * @param context the current context
     * @param listViewResourceId the resource Id of the list view that the adapter is being added to
     * @param resultsList the glossary list that is to be added to the list view
     */
    public ResultsAdapter(Context context, int listViewResourceId, Result[] resultsList){
        super(context,listViewResourceId,resultsList);
        this.resultsList = resultsList;
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
        View view = convertView;
        ViewHolder holder;
        boolean hasLex = false;
        boolean hasSyn = false;
        boolean hasDef = false;
        boolean hasEx = false;
        Result result = resultsList[position];

        if(view == null){
            //inflate the layout
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.results_list,parent,false);

            //Set the view holder

            holder = new ViewHolder();
            holder.term = (TextView) view.findViewById(R.id.resultTerm);
            holder.language = (TextView) view.findViewById(R.id.resultLanguage);
            holder.glossary = (TextView) view.findViewById(R.id.resultGlossary);
            TextView lexicalCategory= (TextView) view.findViewById(R.id.resultLexical_category);
            TextView synonymsView = (TextView) view.findViewById(R.id.resultSynonyms);
            TextView resDefinitions = (TextView) view.findViewById(R.id.resultDefinition);
            TextView resExamples = (TextView) view.findViewById(R.id.resultExample);

            if(result.getLexical_category()!=null){
                lexicalCategory.setVisibility(View.VISIBLE);
                holder.lexical_category = lexicalCategory;
                hasLex = true;
            }
            else{
               lexicalCategory.setVisibility(View.GONE);
               hasLex=false;
            }
            if(result.getSynonyms().get(0) != null){
                synonymsView.setVisibility(View.VISIBLE);
                holder.synonyms = synonymsView;
                hasSyn = true;
            }
            else{
                synonymsView.setVisibility(View.GONE);
                hasSyn=false;
            }
            if(!result.getDefinition().equals("")){
                resDefinitions.setVisibility(View.VISIBLE);
                holder.definition = resDefinitions;
                hasDef = true;
            }
            else{
                resDefinitions.setVisibility(View.GONE);
                hasDef = false;
            }
            if(!result.getExample().equals("")){
                resExamples.setVisibility(View.VISIBLE);
                holder.example = resExamples;
                hasEx = true;
            }
            else{
                resExamples.setVisibility(View.GONE);
                hasEx = false;
            }
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        //Set the current glossary name and checkbox status


        holder.term.setText(result.getWord());
        holder.language.setText("(" + result.getLanguage_name()+")");
        holder.glossary.setText("["+result.getTerminology_dictionary()+"]");
        if(hasLex){
            holder.lexical_category.setText(context.getString(R.string.lexical_category)+ result.getLexical_category());
        }
        if(hasSyn){
            holder.synonyms.setText(context.getString(R.string.synonyms)+"\n");
            ArrayList<Result.Synonym> synonymList = result.getSynonyms();
            ListIterator<Result.Synonym> it = synonymList.listIterator();
            while(it.hasNext()){
                holder.synonyms.setText(holder.synonyms.getText() + it.next().getSynonym() + "\n");
            }
        }
        if(hasDef){
            holder.definition.setText(context.getString(R.string.definition) + result.getDefinition());
        }
        if(hasEx){
            holder.example.setText(context.getString(R.string.example) + result.getExample());
        }
        return view;
    }

}
