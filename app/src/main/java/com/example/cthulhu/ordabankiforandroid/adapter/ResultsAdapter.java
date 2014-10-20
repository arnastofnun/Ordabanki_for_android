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
import java.util.List;
import java.util.Arrays;
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
    private ArrayList<Result> resultsList;

    /**
     * Invoke the overwritten methods in superclass
     * -----------------------------------------------
     * Written by Karl Ásgeir
     * @param context the current context
     * @param listViewResourceId the resource Id of the list view that the adapter is being added to
     * @param resultsList the glossary list that is to be added to the list view
     */
    public ResultsAdapter(Context context, int listViewResourceId, ArrayList<Result> resultsList){
        super(context,listViewResourceId,resultsList);
        this.resultsList = new ArrayList<Result>();
        this.resultsList.addAll(resultsList);
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
            if(resultsList.get(position).getLexical_category()!=null){
                holder.lexical_category = (TextView) view.findViewById(R.id.resultLexical_category);
                hasLex = true;
            }
            if(resultsList.get(position).getSynonyms()!=null){
                holder.synonyms = (TextView) view.findViewById(R.id.resultSynonyms);
                hasSyn = true;
            }
            if(resultsList.get(position).getDefinition()!=null){
                holder.definition = (TextView) view.findViewById(R.id.resultDefinition);
                hasDef = true;
            }
            if(resultsList.get(position).getExample()!=null){
                holder.example = (TextView) view.findViewById(R.id.resultExample);
                hasEx = true;
            }
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        //Set the current glossary name and checkbox status
        Result result = resultsList.get(position);
        holder.term.setText(result.getWord());
        holder.language.setText("(" + result.getLanguage_name()+")");
        holder.glossary.setText("["+result.getTerminology_dictionary()+"]");
        if(hasLex){
            holder.lexical_category.setText("Lexical category: " + result.getLexical_category());
        }
        if(hasSyn){
            holder.synonyms.setText("Synonyms:\n");
            List<Result.Synonym> synonymList = Arrays.asList(result.getSynonyms());
            Iterator it = synonymList.iterator();
            while(it.hasNext()){
                holder.synonyms.setText(holder.synonyms.getText() + (String)it.next() + "\n");
            }
        }
        if(hasDef){
            holder.definition.setText("Definition: " + result.getDefinition());
        }
        if(hasEx){
            holder.example.setText("Example: " + result.getExample());
        }
        return view;
    }

}
