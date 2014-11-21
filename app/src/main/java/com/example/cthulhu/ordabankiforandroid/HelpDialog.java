package com.example.cthulhu.ordabankiforandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.cthulhu.ordabankiforandroid.adapter.HelpAdapter;

/**
 * Created by karlasgeir on 21.11.2014.
 */
public class HelpDialog {
    Context context;
    String[] titleList;
    String[] helpList;
    LayoutInflater layoutInflater;

    public HelpDialog(Context context, LayoutInflater layoutInflater,  String[] titleList, String[] helpList){
        super();
        this.context = context;
        this.titleList = titleList;
        this.helpList = helpList;
        this.layoutInflater = layoutInflater;
    }

    private AlertDialog buildDialog(){
        //Build the dialog
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
//Get the index of the current fragment
        View view = layoutInflater.inflate(R.layout.help_dialog,null);
        helpBuilder.setView(view);


        //Creating a new glossary adapter
        HelpAdapter helpAdapter = new HelpAdapter(context, R.layout.help_list, titleList,helpList);

        //Getting the glossary list and setting it's adapter to my custom glossary adapter
        ListView listView = (ListView) view.findViewById(R.id.help_list_view);
        listView.setAdapter(helpAdapter);

        //Set cancel button
        helpBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

            }
        });

        //Create and show the dialog
        return helpBuilder.create();
    }

    public void show(){
        AlertDialog dialog = buildDialog();
        dialog.show();
        Button dismissButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(dismissButton != null){
            dismissButton.setBackgroundColor(context.getResources().getColor(R.color.darkgrey));
            dismissButton.setTextColor(context.getResources().getColor(android.R.color.primary_text_dark));
        }
    }

}





