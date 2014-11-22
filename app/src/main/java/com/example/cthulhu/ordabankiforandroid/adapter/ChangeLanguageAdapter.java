package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.R;

/**
 * Created by karlasgeir on 21.11.2014.
 */
public class ChangeLanguageAdapter extends ArrayAdapter<String> {

    int selectedIndex = -1;

    public ChangeLanguageAdapter(Context context, int listViewResourceID, String[] languages) {
        super(context, listViewResourceID, languages);

    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.change_language_list,parent, false);
        }

        String currentItem = getItem(position);

        if (currentItem != null) {
            final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.change_language_radio);
            TextView language = (TextView) convertView.findViewById(R.id.change_language_language);

            if(language != null){
                language.setText(currentItem);
            }
            if(selectedIndex == position){
                radioButton.setChecked(true);
            }
            else{
                radioButton.setChecked(false);
            }
        }
        return convertView;
    }





}
