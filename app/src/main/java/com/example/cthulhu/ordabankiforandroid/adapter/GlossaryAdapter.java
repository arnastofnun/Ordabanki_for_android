package com.example.cthulhu.ordabankiforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.Glossary;
import com.example.cthulhu.ordabankiforandroid.R;

import java.util.ArrayList;

/**
 * Created by karlasgeir on 14.10.2014.
 */
public class GlossaryAdapter extends ArrayAdapter<Glossary> {

    private ArrayList<Glossary> glossaryList;

    public GlossaryAdapter(Context context, int textViewResourceID){
        super(context, textViewResourceID);
    }

    public GlossaryAdapter(Context context, int textViewResourceId, ArrayList<Glossary> glossaryList){
        super(context,textViewResourceId,glossaryList);
        this.glossaryList = new ArrayList<Glossary>();
        this.glossaryList.addAll(glossaryList);
    }

    private class ViewHolder{
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        ViewHolder holder = null;

        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.glossary_list,null);

            holder = new ViewHolder();
            holder.code = (TextView) view.findViewById(R.id.GlossaryText);
            holder.name = (CheckBox) view.findViewById(R.id.GlossaryCheckbox);
            view.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox checkbox = (CheckBox) v ;
                    Glossary glossary = (Glossary) checkbox.getTag();
                    /*
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + checkbox.getText() +
                                    " is " + checkbox.isChecked(),
                            Toast.LENGTH_LONG).show();
                     */
                    glossary.setSelected(checkbox.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Glossary glossary = glossaryList.get(position);
        holder.code.setText(" (" +  glossary.getCode() + ")");
        holder.name.setText(glossary.getName());
        holder.name.setChecked(glossary.isSelected());
        holder.name.setTag(glossary);
        return view;
    }


}
