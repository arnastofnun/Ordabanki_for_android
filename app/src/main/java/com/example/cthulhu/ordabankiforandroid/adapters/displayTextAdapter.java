package com.example.cthulhu.ordabankiforandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.R;

/**
 * <h1>Help adapter</h1>
 * <p>An does most of the work for the help screen</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 21.11.2014.
 */
public class displayTextAdapter extends ArrayAdapter<String>{
    private String[] titles;
    private Context context;

    public displayTextAdapter(Context context, int listViewResourceID, String[] titles, String[] helpDescriptions){
        super(context,listViewResourceID,helpDescriptions);
        this.titles = titles;
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
            ImageView img = (ImageView) convertView.findViewById(R.id.help_image);



            if (title != null && titles.length > position && !titles[position].equals("!skip")){
                title.setVisibility(View.VISIBLE);
                title.setText(titles[position]);
            }
            else if(title!= null){
                title.setVisibility(View.GONE);
            }
            if(img!= null && content !=null && currentHelp.startsWith("image:")){
                content.setVisibility(View.GONE);
                String uri = "drawable/" + currentHelp.substring(6);
                int resourceID = context.getResources().getIdentifier(uri,null,context.getPackageName());
                img.setImageResource(resourceID);
                img.setVisibility(View.VISIBLE);

            }
            else if(content != null && img != null){
                img.setVisibility(View.GONE);
                content.setText(currentHelp);
                content.setVisibility(View.VISIBLE);
            }

        }

        return convertView;
    }

}


