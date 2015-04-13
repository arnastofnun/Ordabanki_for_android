package com.arnastofnun.idordabanki.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.arnastofnun.idordabanki.models.Result;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.common.collect.BiMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that takes care of laying out the correct values
 * in the result list view
 * Created by karlasgeir
 * @since 27.02.2015
 */
public class ResultListAdapter extends BaseExpandableListAdapter{
    private Activity context; //A reference to the activity
    private HashMap<String,ArrayList<Result>> resultMap; //A hashmap to hold the sublist items
    private ArrayList<String> words; //An array containing titles for the sublists
    //Get global values
    Globals g = (Globals) Globals.getContext(); //Get the global values

    /**
     * A constructor for the result list adapter
     * @param context - A reference to the activity
     * @param resultMap - A hashmap to hold the sublist items
     * @param words - A string of titles for the sublists
     */
    public ResultListAdapter(Activity context, HashMap<String, ArrayList<Result>> resultMap, ArrayList<String> words){
        this.context = context;
        this.resultMap = resultMap;
        this.words = words;
    }

    /**
     * Returns the child list item at a certain
     * position
     * @param groupPosition - the position of the list group (parent list item)
     * @param childPosition - the position of the child (position in the sublist of the parent)
     * @return The child item
     */
    public Object getChild(int groupPosition, int childPosition){
        return resultMap.get(words.get(groupPosition)).get(childPosition);
    }

    /**
     * Returns the id of the child at a certain position
     * @param groupPosition - the position of the list group (parent list item)
     * @param childPosition - the position of the child (position in the sublist of the parent)
     * @return the child position
     */
    public long getChildId(int groupPosition, int childPosition){
        Result result = resultMap.get(words.get(groupPosition)).get(childPosition);
        return Long.valueOf(result.getId_word());
    }


    /**
     * A simple class to hold the values
     * for the text views in each list item
     * ------------------------------------
     * @author Karl Ásgeir Geirsson
     * @since 27.02.2015
     */
    private class ViewHolder{
        TextView language;
        TextView glossary;
    }

    /**
     * A method that returns the child view at a certain position
     * @param groupPosition - the position of the list group (parent list item)
     * @param childPosition - the position of the child (position in the sublist of the parent)
     * @param isLastChild - true if it's the last child
     * @param convertView - the view
     * @param parent - the parent
     * @return The child view
     */
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        //Initialize
        final ViewHolder holder;
        final Result result = (Result) getChild(groupPosition, childPosition);
        //If the view hasn't been initialized
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
     * A method to get the number of children for
     * a certain group
     * @param groupPosition - the position of the list group (parent list item)
     * @return the number of children under that group
     */
    public int getChildrenCount(int groupPosition){
        return resultMap.get(words.get(groupPosition)).size();
    }

    /**
     * A method to get the group at
     * a certain position
     * @param groupPosition - the position of the list group (parent list item)
     * @return the group
     */
    public Object getGroup(int groupPosition){
        return words.get(groupPosition);
    }

    /**
     * Get the total number of groups
     * @return the total number of groups
     */
    public int getGroupCount(){
        return words.size();
    }

    /**
     * Get the id of a group at a certain position
     * @param groupPosition - the position of the list group (parent list item)
     * @return the ID of the group
     */
    public long getGroupId(int groupPosition){
        Result result = resultMap.get(words.get(groupPosition)).get(0);
        if(result.getId_word() == null){
            return Long.valueOf(result.getId_term());
        }

        return Long.valueOf(result.getId_word());
    }


    /**
     * A method to get the view of the group
     * @param groupPosition - the position of the list group (parent list item)
     * @param isExpanded - true if group shows sublists, else false
     * @param convertView - the view
     * @param parent - - the parent
     * @return the group view
     */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String termWord = (String) getGroup(groupPosition); //The title of the group
        ArrayList<Result> resultlist = resultMap.get(words.get(groupPosition)); //The list of sublist items

        //If the group view hasn't been initialized
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_list_item, null);
        }

        //Find the views, that will be changed
        TextView termView = (TextView) convertView.findViewById(R.id.word);
        TextView langView = (TextView) convertView.findViewById(R.id.resultLanguage);
        TextView glossView = (TextView) convertView.findViewById(R.id.resultGlossary);
        ImageView ind = (ImageView) convertView.findViewById(R.id.indicator);

        //If there is just one item in the sublist
        if(resultlist.size() == 1){
            //Hide the dropdown/pullup indicator
            ind.setVisibility(View.GONE);
            //Don't use bold text
            termView.setTypeface(null, Typeface.NORMAL);
            //Set text to the glossary and language name
            glossView.setText(getGlossaryName(resultlist.get(0)));
            langView.setText(getLanguageName(resultlist.get(0)));
            //Make the glossary and language views visible
            glossView.setVisibility(View.VISIBLE);
            if(resultlist.get(0).getLanguage_code().equals("")){
                langView.setVisibility(View.GONE);
            } else {
                langView.setVisibility(View.VISIBLE);
            }

        }else{
            //We want the header to be bold
            termView.setTypeface(null, Typeface.BOLD);
            //Make sure the dropdown/pullup indicator is visible, and correct
            int imageResourceId = isExpanded ? R.attr.collapseIcon : R.attr.expandIcon;
            ThemeHelper themeHelper = new ThemeHelper(context);
            ind.setImageResource(themeHelper.getAttrDrawable(imageResourceId));
            //Show the indicator
            ind.setVisibility(View.VISIBLE);
            //Hide the language and glossary views
            langView.setVisibility(View.GONE);
            glossView.setVisibility(View.GONE);
        }
        //Set the term word
        termView.setText(termWord);

        return convertView;
    }


    /**
     * @return true if the Ids are stable
     */
    public boolean hasStableIds(){
        return true;
    }

    /**
     * A method that desides if a child is selectable
     * @param groupPosition - the position of the list group (parent list item)
     * @param childPosition - the position of the child (position in the sublist of the parent)
     * @return true if the child is selectable
     */
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

    /**
     * A method to inflate a view
     * @param parent The viewgroup that the inflated view is appended to
     * @return the inflated view
     */
    private View inflateView(ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        return  inflater.inflate(R.layout.result_list_child_item,parent, false);
    }

    /**
     * A method that sets up a new viewholder
     * @param view the view that the view holder is set up from
     * @return the view holder
     */
    private ViewHolder setupViewHolder(View view){
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.language = (TextView) view.findViewById(R.id.resultLanguage);
        viewHolder.glossary = (TextView) view.findViewById(R.id.resultGlossary);
        return viewHolder;
    }

    /**
     * Sets the view holder to the correct values
     * @param viewHolder the view holder to be set
     * @param result the result that contains the values that
     *               should be set to the viewholder
     */
    private void setHolder(ViewHolder viewHolder, Result result){
        if(!result.getLanguage_code().equals("")) {
            viewHolder.language.setText(getLanguageName(result));
            viewHolder.language.setVisibility(View.VISIBLE);
        } else{
            viewHolder.language.setVisibility(View.GONE);
        }
        viewHolder.glossary.setText(getGlossaryName(result));
    }

    /**
     * Gets the language name in the correct language
     * @param result the result
     * @return the language name of the result
     */
    private String getLanguageName(Result result){
        BiMap<String,String> languages = SharedPrefs.getStringBiMap("languages");
        return languages.get(result.getLanguage_code());
    }

    /**
     * Gets the glossary name in the correct language
     * @param result the result
     * @return the glossary name of the result
     */
    private String getGlossaryName(Result result){
        BiMap<String,String> dictionaries = SharedPrefs.getStringBiMap("loc_dictionaries");
        return dictionaries.get(result.getDictionary_code());
    }

}
