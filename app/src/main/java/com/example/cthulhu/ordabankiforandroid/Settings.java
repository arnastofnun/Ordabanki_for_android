package com.example.cthulhu.ordabankiforandroid;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * Created by karlasgeir on 29.10.2014.
 */
public class Settings {
    /*
        data invariants
        ---------------
        sharedpref is the default shared preferences
        context is the current context
        status is true if there is a language in shared preferences, else false
        language contains the language in shared preferences, else null
     */
    Context context;


    /**
     * use: Locale settings localeSettings = new LocaleSettings(context)
     * pre:context is of type Context
     * post:A new LocaleSettings object has been created
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param context is the current context
     */
    public Settings(Context context) {
        super();
        //Set variables
        this.context = context;

    }


    public void createOptionsPopupMenu(View v, final Class cl){
        PopupMenu popup = new PopupMenu(context,v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.settings_change_language:
                        final LocaleSettings localeSettings = new LocaleSettings(context);
                        String lang = localeSettings.getLanguage();
                        int langPos = getLanguagePos(lang);
                        final AlertDialog.Builder languageBuilder = new AlertDialog.Builder(context);
                        languageBuilder
                                .setTitle(R.string.change_language)
                                        //Todo set default value for select language settings
                                .setSingleChoiceItems(R.array.language_array, langPos, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Todo implement on click functions for select language settings
                                    }
                                });
                        //Todo implement accept button
                        languageBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        languageBuilder.setPositiveButton(R.string.settings_changelanguage_confirm,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                ListView lw = ((AlertDialog)dialog).getListView();
                                int pos = lw.getCheckedItemPosition();
                                Log.v("id", String.valueOf(pos));
                                Log.v("lang",getLanguageFromPos(pos));
                                localeSettings.setLanguage(getLanguageFromPos(pos),cl);
                            }
                        });
                        AlertDialog languageDialog = languageBuilder.create();
                        languageDialog.show();
                        return true;

                    case R.id.settings_about:
                        Intent intent = new Intent(context, AboutActivity.class);
                        context.startActivity(intent);
                        return true;
                    case R.id.settings_contact:
                        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getResources().getString(R.string.contact_email), null));
                        //Todo doesn't seem to put the email to address through
                        try{
                            context.startActivity(Intent.createChooser(email,context.getResources().getString(R.string.choose_email_client)));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, context.getResources().getString(R.string.error_no_email_client), Toast.LENGTH_LONG).show();
                        }
                        return true;
                }
                return true;
            }
        });
        inflater.inflate(R.menu.settings_menu,popup.getMenu());
        popup.show();
    }



    private int getLanguagePos(String lang){

        if(lang.equals("is")){
            return 0;
        }
        else if(lang.equals("en")){
            return 1;
        }
        else if(lang.equals("sv")){
            return 2;
        }
        else if(lang.equals("da")){
            return 3;
        }
        else{
            return -1;
        }
    }

    private String getLanguageFromPos(int pos){
        switch(pos){
            case 0:
                return "is";
            case 1:
                return "en";
            case 2:
                return "sv";
            case 3:
                return "da";
            default:
                return "";
        }
    }



}
