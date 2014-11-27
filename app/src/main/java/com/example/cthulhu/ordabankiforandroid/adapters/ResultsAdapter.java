package com.example.cthulhu.ordabankiforandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.Globals;
import com.example.cthulhu.ordabankiforandroid.R;
import com.example.cthulhu.ordabankiforandroid.Result;

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

    /**
     * Invoke the overwritten methods in superclass
     * -----------------------------------------------
     * Written by Karl Ásgeir
     * @param context the current context
     * @param listViewResourceId the resource Id of the list view that the adapter is being added to
     * @param resultsList the glossary list that is to be added to the list view
     */
    public ResultsAdapter(Context context, int listViewResourceId, List<Result> resultsList){
        super(context,listViewResourceId,resultsList);
        this.resultsList = new ArrayList<Result>(resultsList);
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
        //Inflate a new view
        if(convertView == null){
            //inflate the layout
            convertView = inflateView(parent);
            //Set the view holder
            holder = setupViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Set holder values
        setHolder(holder,result);
        return convertView;
    }


    /**
     * Sets the view holder to the correct values
     * @param viewHolder the view holder to be set
     * @param result the result that contains the values that
     *               should be set to the viewholder
     */
    private void setHolder(ViewHolder viewHolder, Result result){
        viewHolder.term.setText(result.getWord());
        viewHolder.language.setText(getLanguageName(result));
        viewHolder.glossary.setText(getGlossaryName(result));
    }

    /**
     * Gets the language name in the correct language
     * @param result the result
     * @return the language name of the result
     */
    private String getLanguageName(Result result){
        ArrayList<ArrayList<String>> languages = g.getLanguages();
        int lang_index = languages.get(0).indexOf(result.getLanguage_code());
        return languages.get(1).get(lang_index);
    }

    /**
     * Gets the glossary name in the correct language
     * @param result the result
     * @return the glossary name of the result
     */
    private String getGlossaryName(Result result){
        ArrayList<ArrayList<String>> dictionaries = g.getLoc_dictionaries();
        int gloss_index = dictionaries.get(0).indexOf(result.getDictionary_code());
        return dictionaries.get(1).get(gloss_index);
    }


    /**
     * A method to inflate a view
     * @param parent The viewgroup that the inflated view is appended to
     * @return the inflated view
     */
    private View inflateView(ViewGroup parent){
        LayoutInflater vi = LayoutInflater.from(getContext());
        return  vi.inflate(R.layout.results_list,parent, false);
    }



    /**
     * A method that set's up a new viewholder
     * @param view the view that the view holder is set up from
     * @return the vie holder
     */
    private ViewHolder setupViewHolder(View view){
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.term = (TextView) view.findViewById(R.id.resultTerm);
        viewHolder.language = (TextView) view.findViewById(R.id.resultLanguage);
        viewHolder.glossary = (TextView) view.findViewById(R.id.resultGlossary);
        return viewHolder;
    }


}
