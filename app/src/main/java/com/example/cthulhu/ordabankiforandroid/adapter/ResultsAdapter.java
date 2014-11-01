package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.Result;


import static com.example.cthulhu.ordabankiforandroid.Result.Synonym;


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
        boolean hasLex = true;
        boolean hasSyn = true;
        boolean hasDef = true;
        boolean hasEx = true;
        Result resultObj = resultsList[position];

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

            if(resultObj.getLexical_category()!=null){
                lexicalCategory.setVisibility(View.VISIBLE);
                holder.lexical_category = lexicalCategory;
                hasLex = true;
            }
            else{
               lexicalCategory.setVisibility(View.GONE);
               hasLex=false;
            }
            if(resultObj.getSynonyms()[0] != null){
                synonymsView.setVisibility(View.VISIBLE);
                holder.synonyms = synonymsView;
                hasSyn = true;
            }
            else{
                synonymsView.setVisibility(View.GONE);
                hasSyn=false;
            }
            if(!resultObj.getDefinition().equals("")){
                resDefinitions.setVisibility(View.VISIBLE);
                holder.definition = resDefinitions;
                hasDef = true;
            }
            else{
                resDefinitions.setVisibility(View.GONE);
                hasDef = false;
            }
            if(!resultObj.getExample().equals("")){
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



        //adds all non-null fields from result object to result view
        holder.term.setText(resultObj.getWord());
        //language and glossary will return a code later. need to parse it to locale
        holder.language.setText("(" + resultObj.getLanguage_name()+")");
        holder.glossary.setText("["+resultObj.getTerminology_dictionary()+"]");
        if(hasLex){
            holder.lexical_category.setText(context.getString(R.string.lexical_category)+ resultObj.getLexical_category());
        }
        //loops through synonym strings in synonym object and adds to newline delimited list
        if(hasSyn){
            holder.synonyms.setText(context.getString(R.string.synonyms)+"\n");
            Synonym[] synonymList;
            synonymList = resultObj.getSynonyms();
            for (Synonym aSynonymList : synonymList) {
                holder.synonyms.setText(holder.synonyms.getText() + aSynonymList.getSynonym() + "\n");
            }
        }
        if(hasDef){
            String def = resultObj.getDefinition().replaceAll("<[^>]*>", "");
            holder.definition.setText(context.getString(R.string.definition) + def);
        }
        if(hasEx){
            holder.example.setText(context.getString(R.string.example) + resultObj.getExample());
        }
        return view;
    }

}
