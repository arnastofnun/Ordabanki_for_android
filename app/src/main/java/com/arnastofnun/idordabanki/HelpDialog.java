package com.arnastofnun.idordabanki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.arnastofnun.idordabanki.adapters.displayTextAdapter;

/**
 * This class creates a custom dialog
 * for the help.
 * @author Karl Ásgeir Geirsson
 * @since 21.11.2014.
 */
public class HelpDialog {
    Context context; //The context that calls the dialog
    String[] titleList; //List of titles in the dialog
    String[] helpList; //List of contents in the dialog
    LayoutInflater layoutInflater; //The layout inflater

    /**
     * Constructor for the dialog
     * Written by Karl Ásgeir Geirsso
     * @param context The context that calls the dialog
     * @param layoutInflater The layout inflater
     * @param titleList List of titles in the dialog
     * @param helpList List of contents in the dialog
     */
    public HelpDialog(Context context, LayoutInflater layoutInflater,  String[] titleList, String[] helpList){
        super();
        //Set variables
        this.context = context;
        this.titleList = titleList;
        this.helpList = helpList;
        this.layoutInflater = layoutInflater;
    }

    /**
     * A method that builds the custom dialog
     * Written by karl Ásgeir Geirsson
     * @return returns the built dialog
     */
    private AlertDialog buildDialog(){
        //Construct the builder
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
        //Inflate the view
        View view = layoutInflater.inflate(R.layout.help_dialog,null);
        helpBuilder.setView(view);

        //Construct a new displayTextAdapter (my custom adapter)
        displayTextAdapter displayTextAdapter = new displayTextAdapter(context, R.layout.help_list, titleList,helpList);

        //Setting the adapter to the list view
        ListView listView = (ListView) view.findViewById(R.id.help_list_view);
        listView.setAdapter(displayTextAdapter);

        //Set cancel button
        helpBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

            }
        });

        //Create and show the dialog
        return helpBuilder.create();
    }

    /**
     *  This method shows the dialog
     *  Written by: Karl Ásgeir Geirsson
     */
    public void show(){
        AlertDialog dialog = buildDialog();
        dialog.show();
        Button dismissButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        changeButtonColor(dismissButton);
    }

    /**
     * This method changes the color of a button
     * Written by: Karl Ásgeir Geirsson
     * @param button the button to change color
     */
    public void changeButtonColor(Button button){
        if(button != null){
            button.setBackgroundColor(context.getResources().getColor(R.color.darkgrey));
            button.setTextColor(context.getResources().getColor(android.R.color.primary_text_dark));
        }
    }

}





