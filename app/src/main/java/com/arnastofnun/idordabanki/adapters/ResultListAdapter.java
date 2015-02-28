package com.arnastofnun.idordabanki.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.Result;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by karlasgeir on 2/27/15.
 */
public class ResultListAdapter extends BaseExpandableListAdapter{
    private Activity context;
    private HashMap<String,ArrayList<Result>> resultMap;
    private ArrayList<String> words;
    //Get global values
    Globals g = (Globals) Globals.getContext();

    public ResultListAdapter(Activity context, HashMap<String, ArrayList<Result>> resultMap, ArrayList<String> words){
        this.context = context;
        this.resultMap = resultMap;
        this.words = words;
    }

    public Object getChild(int groupPosition, int childPosition){
        return resultMap.get(words.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }


    /**
     * A simple class to hold the values
     * for the text views in each list item
     * ------------------------------------
     * @author Karl Ásgeir Geirsson
     * @since 27.02.2014
     */
    private class ViewHolder{
        TextView language;
        TextView glossary;
    }


    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        //Initialize
        final ViewHolder holder;
        final Result result = (Result) getChild(groupPosition, childPosition);
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

    public int getChildrenCount(int groupPosition){
        return resultMap.get(words.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition){
        return words.get(groupPosition);
    }

    public int getGroupCount(){
        return words.size();
    }

    public long getGroupId(int groupPosition){
        return groupPosition;
    }



    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String termWord = (String) getGroup(groupPosition);
        ArrayList<Result> resultlist = resultMap.get(words.get(groupPosition));
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_list_item, null);
        }


        TextView termView = (TextView) convertView.findViewById(R.id.word);
        TextView langView = (TextView) convertView.findViewById(R.id.resultLanguage);
        TextView glossView = (TextView) convertView.findViewById(R.id.resultGlossary);
        ImageView ind = (ImageView) convertView.findViewById(R.id.indicator);

        if(resultlist.size() == 1){
            ind.setVisibility(View.GONE);
            termView.setTypeface(null, Typeface.NORMAL);
            glossView.setText(getGlossaryName(resultlist.get(0)));
            langView.setText(getLanguageName(resultlist.get(0)));
            glossView.setVisibility(View.VISIBLE);
            langView.setVisibility(View.VISIBLE);
        }else{
            termView.setTypeface(null, Typeface.BOLD);
            int imageResourceId = isExpanded ? R.drawable.ic_action_collapse : R.drawable.ic_action_expand;
            ind.setImageResource(imageResourceId);

            ind.setVisibility(View.VISIBLE);
            langView.setVisibility(View.GONE);
            glossView.setVisibility(View.GONE);
        }

        termView.setText(termWord);


        return convertView;
    }



    public boolean hasStableIds(){
        return true;
    }

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


}
