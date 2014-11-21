package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.R;

/**
 * <h1>Help adapter</h1>
 * <p>An does most of the work for the help screen</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 21.11.2014.
 */
public class HelpAdapter extends ArrayAdapter<String>{
    private String[] titles;
    private String[] helpDescriptions;
    private Context context;

    public HelpAdapter(Context context, int listViewResourceID, String[] titles, String[] helpDescriptions){
        super(context,listViewResourceID,helpDescriptions);
        this.titles = titles;
        this.helpDescriptions = helpDescriptions;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.help_list,null);
        }

        String currentHelp = getItem(position);

        if(currentHelp != null) {
            TextView title = (TextView) convertView.findViewById(R.id.help_item_title);
            TextView content = (TextView) convertView.findViewById(R.id.help_content);

            if (title != null && titles.length > position && !titles[position].equals("")){
                title.setVisibility(View.VISIBLE);
                title.setText(titles[position]);
            }
            else if(title!= null){
                title.setVisibility(View.GONE);
            }
            if(content != null){
                content.setText(currentHelp);
            }

        }

        return convertView;
    }

}


