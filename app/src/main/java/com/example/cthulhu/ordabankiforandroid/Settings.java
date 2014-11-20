package com.example.cthulhu.ordabankiforandroid;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.SearchRecentSuggestions;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * @author Karl Ásgeir Geirsson
 * @since 29.10.2014.
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

    /**
     * Creates an options popup menu with various options
     * Written by Karl Ásgeir Geirsson
     * @param v the view where the menu should pop down from
     * @param cl the class of the activity to be started on language change
     */
    public void createOptionsPopupMenu(View v, final Class cl){
        //Create a new popup menu
        PopupMenu popup = new PopupMenu(context,v);
        MenuInflater inflater = popup.getMenuInflater();
        //Set on click listener for the menu items
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //If change language button is pressed
                    case R.id.settings_change_language:
                        //Get the current language from the locale settings
                        final LocaleSettings localeSettings = new LocaleSettings(context);
                        String lang = localeSettings.getLanguage();
                        //Get the position of the current language
                        int langPos = getLanguagePos(lang);
                        //Set up a single choice dialog with the languages
                        final AlertDialog.Builder languageBuilder = new AlertDialog.Builder(context);
                        languageBuilder
                                .setTitle(R.string.change_language)
                                .setSingleChoiceItems(R.array.language_array, langPos, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        //Set cancel button
                        languageBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        //set accept button that sets the language to the selected language
                        languageBuilder.setPositiveButton(R.string.settings_changelanguage_confirm,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                //Get the selected position
                                ListView lw = ((AlertDialog)dialog).getListView();
                                int pos = lw.getCheckedItemPosition();
                                //Set the language and return to splash screen to load
                                localeSettings.setLanguage(getLanguageFromPos(pos),SplashActivity.class);
                            }
                        });
                        //Create and show the dialog
                        AlertDialog languageDialog = languageBuilder.create();
                        languageDialog.show();
                        return true;
                    //If the abut button is pressed
                    case R.id.settings_about:
                        //Start the about activity
                        Intent intent = new Intent(context, AboutActivity.class);
                        context.startActivity(intent);
                        return true;
                    //If the contact us button is pressed
                    case R.id.settings_contact:
                        //Open a mail client with mailto set
                        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getResources().getString(R.string.contact_email), null));
                        try{
                            context.startActivity(Intent.createChooser(email,context.getResources().getString(R.string.choose_email_client)));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, context.getResources().getString(R.string.error_no_email_client), Toast.LENGTH_LONG).show();
                        }
                        return true;
                    //If clear search history is pressed
                    case R.id.settings_clear_search:
                        //Clear the search histry
                        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(context, SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
                        suggestions.clearHistory();
                }
                return true;
            }
        });
        //Inflate the layout for the popup menu
        inflater.inflate(R.menu.settings_menu,popup.getMenu());
        //Show the popup menu
        popup.show();
    }


    /**
     * @param lang the language
     * @return the position of lang
     */
    private int getLanguagePos(String lang){

        if(lang.equals("IS")){
            return 0;
        }
        else if(lang.equals("EN")){
            return 1;
        }
        else if(lang.equals("SV")){
            return 2;
        }
        else if(lang.equals("DA")) {
            return 3;
        }
        else if(lang.equals("NO")){
                return 4;
        }
        else{
            return -1;
        }
    }

    /**
     * @param pos the position of the lang
     * @return the language in that position
     */
    private String getLanguageFromPos(int pos){
        switch(pos){
            case 0:
                return "IS";
            case 1:
                return "EN";
            case 2:
                return "SV";
            case 3:
                return "DA";
            case 4:
                return "NO";
            default:
                return "";
        }
    }



}
