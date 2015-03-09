package com.arnastofnun.idordabanki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnastofnun.idordabanki.R;

/**
 * <h1>Help adapter</h1>
 * <p>An does most of the work for the help screen</p>
 * --------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 21.11.2014
 */
public class displayTextAdapter extends ArrayAdapter<String>{
    private String[] titles;
    private Context context;

    /**
     * A constructor for the displayTextAdapter
     * @param context - a reference to the context
     * @param listViewResourceID - the resource ID of the list view
     * @param titles - a list of titles
     * @param helpDescriptions - a list helps
     */
    public displayTextAdapter(Context context, int listViewResourceID, String[] titles, String[] helpDescriptions){
        super(context,listViewResourceID,helpDescriptions);
        this.titles = titles;
        this.context = context;
    }

    /**
     * A method to get the view at a certain position
     * @param position - the position in the list
     * @param convertView - the view
     * @param parent - the parent
     * @return the view of the list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //If the view hasn't been initialized
        if(convertView == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.help_list,null);
        }

        String currentHelp = getItem(position); //Get the item at this position


        if(currentHelp != null) {
            //Find the views that will be changed
            TextView title = (TextView) convertView.findViewById(R.id.help_item_title);
            TextView content = (TextView) convertView.findViewById(R.id.help_content);
            ImageView img = (ImageView) convertView.findViewById(R.id.help_image);


            //If there is a title, and it's not the codeword !skip
            if (title != null && titles.length > position && !titles[position].equals("!skip")){
                title.setVisibility(View.VISIBLE); //Show the title view
                title.setText(titles[position]); //Set the text of the title
            }
            else if(title!= null){
                title.setVisibility(View.GONE); //Hide the title
            }
            //If the contents are an image
            if(img!= null && content !=null && currentHelp.startsWith("image:")){
                //Hide the contents view
                content.setVisibility(View.GONE);
                //Set the image
                String uri = "drawable/" + currentHelp.substring(6);
                int resourceID = context.getResources().getIdentifier(uri,null,context.getPackageName());
                img.setImageResource(resourceID);
                //Show the image
                img.setVisibility(View.VISIBLE);

            }
            //If the contents are plain text
            else if(content != null && img != null){
                //Hide the image view
                img.setVisibility(View.GONE);
                //Set the text
                content.setText(currentHelp);
                //Show the contents
                content.setVisibility(View.VISIBLE);
            }

        }

        return convertView;
    }

}


