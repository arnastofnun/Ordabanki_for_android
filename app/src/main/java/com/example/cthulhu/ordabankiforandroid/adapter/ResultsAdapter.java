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
        final ViewHolder holder;
        boolean hasLex = false;
        boolean hasSyn = false;
        boolean hasDef = false;
        boolean hasEx = false;
        Result result = resultsList[position];


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
            holder.lexical_category= (TextView) convertView.findViewById(R.id.resultLexical_category);
            holder.synonyms = (TextView) convertView.findViewById(R.id.resultSynonyms);
            holder.definition = (TextView) convertView.findViewById(R.id.resultDefinition);
            holder.example = (TextView) convertView.findViewById(R.id.resultExample);

            if(result.getLexical_category()!=null){
                hasLex = true;
            }
            else{
               hasLex=false;
            }
            if(result.getSynonyms()[0] != null){
                hasSyn = true;
            }
            else{
                hasSyn=false;
            }
            if(!result.getDefinition().equals("")){
                hasDef = true;
            }
            else{
                hasDef = false;
            }
            if(!result.getExample().equals("")){
                hasEx = true;
            }
            else{
                hasEx = false;
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }



        //adds all non-null fields from result object to result view
        holder.term.setText(result.getWord());
        holder.language.setText("(" + result.getLanguage_name()+")");
        holder.glossary.setText("["+result.getTerminology_dictionary()+"]");
        if(hasLex){
            convertView.findViewById(R.id.resultLexical_category).setVisibility(View.VISIBLE);
            holder.lexical_category.setText(context.getString(R.string.lexical_category)+ result.getLexical_category());
        }
        else{
            convertView.findViewById(R.id.resultLexical_category).setVisibility(View.GONE);
        }
        //loops through synonym strings in synonym object and adds to newline delimited list
        if(hasSyn){
            convertView.findViewById(R.id.resultSynonyms).setVisibility(View.VISIBLE);
            holder.synonyms.setText(context.getString(R.string.synonyms)+"\n");
            Synonym[] synonymList;
            synonymList = result.getSynonyms();
            for (Synonym aSynonymList : synonymList) {
                holder.synonyms.setText(holder.synonyms.getText() + aSynonymList.getSynonym() + "\n");
            }
        }
        else{
            convertView.findViewById(R.id.resultSynonyms).setVisibility(View.GONE);
        }
        if(hasDef){
            convertView.findViewById(R.id.resultDefinition).setVisibility(View.VISIBLE);
            String def = result.getDefinition().replaceAll("<[^>]*>", "");
            holder.definition.setText(context.getString(R.string.definition) + def);
        }
        else{
            convertView.findViewById(R.id.resultDefinition).setVisibility(View.GONE);
        }
        if(hasEx){
            convertView.findViewById(R.id.resultExample).setVisibility(View.VISIBLE);
            holder.example.setText(context.getString(R.string.example) + result.getExample());
        }
        else{
            convertView.findViewById(R.id.resultExample
            ).setVisibility(View.GONE);
        }

        return convertView;
    }

}
