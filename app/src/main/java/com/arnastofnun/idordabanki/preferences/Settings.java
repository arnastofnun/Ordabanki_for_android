package com.arnastofnun.idordabanki.preferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.SearchRecentSuggestions;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.helpers.SearchAutoComplete;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.arnastofnun.idordabanki.activities.AboutActivity;
import com.arnastofnun.idordabanki.activities.SplashActivity;
import com.arnastofnun.idordabanki.adapters.ChangeLanguageAdapter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Karl Ásgeir Geirsson
 * @since 29.10.2014
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

    Activity context;
    private BiMap<Integer,String> themes;


    ArrayList<String> themeNames;



    /**
     * use: Locale settings localeSettings = new LocaleSettings(context)
     * pre:context is of type Context
     * post:A new LocaleSettings object has been created
     * ----------------------------------------------------------
     * Written by Karl Ásgeir Geirsson
     * @param context is the current context
     */
    public Settings(Activity context) {
        super();
        //Set variables
        this.context = context;
        themes = HashBiMap.create();
        themes.put(R.style.AppTheme_Girly,"Girly");
        themes.put(R.style.AppTheme_Light,"Normal");
        themeNames= new ArrayList<>(themes.values());

    }



    /**
     * Creates an options popup menu with various options
     * Written by Karl Ásgeir Geirsson
     * @param v the view where the menu should pop down from
     */
    public void createOptionsPopupMenu(View v){
        //Create a new popup menu
        Context wrapper = new ContextThemeWrapper(context,R.style.PopupStyle);
        PopupMenu popup = new PopupMenu(wrapper,v);
        MenuInflater inflater = popup.getMenuInflater();
        //Set on click listener for the menu items
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //If change language button is pressed
                    case R.id.settings_change_language:
                        changeLanguageClicked();
                        return true;
                    case R.id.settings_change_theme:
                        changeThemeClicked();
                        return true;
                    //If the about button is pressed
                    case R.id.settings_about:
                        //Start the about activity
                        Intent intent = new Intent(context, AboutActivity.class);
                        context.startActivity(intent);
                        return true;
                    //If the contact us button is pressed
                    case R.id.settings_contact:
                        sendEmail();
                        return true;
                    //If clear search history is pressed
                    case R.id.settings_clear_search:
                        clearSearchSuggestions();
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
     * A method to clear the search suggestions
     */
    private void clearSearchSuggestions(){
        //Clear the search history
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(context, SearchAutoComplete.AUTHORITY, SearchAutoComplete.MODE);
        suggestions.clearHistory();
    }

    /**
     * A method to send email to the contact person
     */
    private void sendEmail(){
        //Open a mail client with mailto set
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getResources().getString(R.string.contact_email), null));
        try{
            context.startActivity(Intent.createChooser(email,context.getResources().getString(R.string.choose_email_client)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getResources().getString(R.string.error_no_email_client), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method handles the action when the
     * change language option is selected
     */
    private void changeLanguageClicked(){
        //Create the dialog builder
        AlertDialog.Builder clangBuilder = new AlertDialog.Builder(context);
        //Layout and set the view
        View view = LayoutInflater.from(context).inflate(R.layout.change_language_dialog, null);
        clangBuilder.setView(view);

        final ListView listView = setupListView(view);

        //Set cancel button (just dismisses the dialog)
        clangBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        //Set positive button (accepts the chosen language)
        clangBuilder.setPositiveButton(R.string.settings_changelanguage_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Get the selected position
                int pos = listView.getCheckedItemPosition();
                //If the language isn't already selected
                if(pos != getCurrentLanguagePosition()) {
                    //Set the language and return to splash screen to load
                    LocaleSettings localeSettings = new LocaleSettings(context);
                    localeSettings.setLanguage(getLanguageFromPos(pos), SplashActivity.class, true);
                }
            }
        });

        //Create and show the dialog
        AlertDialog chLangDialog = clangBuilder.create();
        chLangDialog.show();

        //Change the color of the buttons
        Button dismissButton = chLangDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button confirmButton = chLangDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        changeButtonColor(dismissButton);
        changeButtonColor(confirmButton);
    }

    private void changeThemeClicked(){
        //Create the dialog builder
        AlertDialog.Builder clangBuilder = new AlertDialog.Builder(context);
        //Layout and set the view
        View view = LayoutInflater.from(context).inflate(R.layout.change_language_dialog, null);
        clangBuilder.setView(view);
        TextView title = (TextView) view.findViewById(R.id.change_language_titlebar);
        title.setText(R.string.settings_theme);

        final ListView listView = setupThemeListView(view);

        //Set cancel button (just dismisses the dialog)
        clangBuilder.setNegativeButton(R.string.close_help, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        //Set positive button (accepts the chosen language)
        clangBuilder.setPositiveButton(R.string.settings_changelanguage_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Get the selected position
                int pos = listView.getCheckedItemPosition();
                TypedValue typedValue = new TypedValue();

                context.getTheme().resolveAttribute(R.attr.themeName, typedValue, true);
                //If the language isn't already selectes
                if(pos != themeNames.indexOf(typedValue.string)) {
                    ThemeHelper themeHelper = new ThemeHelper(context);
                    themeHelper.setTheme(context,themes.inverse().get(themeNames.get(pos)));
                }
            }
        });

        //Create and show the dialog
        AlertDialog chLangDialog = clangBuilder.create();
        chLangDialog.show();

        //Change the color of the buttons
        Button dismissButton = chLangDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button confirmButton = chLangDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        changeButtonColor(dismissButton);
        changeButtonColor(confirmButton);
    }


    /**
     * A method to get the current language
     * position from locale settings
     * @return the current language position
     */
    private int getCurrentLanguagePosition(){
        LocaleSettings localeSettings = new LocaleSettings(context);
        return getLanguagePos(localeSettings.getLanguage());
    }

    /**
     * A method to setup the list view
     * @param view the view to attach the listview to
     * @return the list view
     */
    private ListView setupListView(View view){
        //Creating a new custom adapter and appending the languages to it
        ArrayList<String> languages = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.language_array)));
        final ChangeLanguageAdapter changeLanguageAdapter = new ChangeLanguageAdapter(context, R.layout.change_language_list, languages);
        //Finding the list view and setting the adapter
        final ListView listView = (ListView) view.findViewById(R.id.change_language_list_view);
        listView.setAdapter(changeLanguageAdapter);
        //Setting the correct selected language
        changeLanguageAdapter.setSelectedIndex(getCurrentLanguagePosition());
        //On item click listener for the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Change the selected item
                changeLanguageAdapter.setSelectedIndex(position);
                listView.setItemChecked(position, true);
                changeLanguageAdapter.notifyDataSetChanged();
            }
        });

        return listView;
    }

    private ListView setupThemeListView(View view){

        BiMap<Integer,String> themes =HashBiMap.create();
        themes.put(R.style.AppTheme_Girly,"Girly");
        themes.put(R.style.AppTheme_Light,"Normal");

        ArrayList<String> themeNames = new ArrayList<>(themes.values());

        final ChangeLanguageAdapter themeAdapter = new ChangeLanguageAdapter(context,R.layout.change_language_list,themeNames);

        final ListView listView = (ListView) view.findViewById(R.id.change_language_list_view);
        listView.setAdapter(themeAdapter);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeName,typedValue,true);
        themeAdapter.setSelectedIndex(themeNames.indexOf(typedValue.string));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                themeAdapter.setSelectedIndex(position);
                listView.setItemChecked(position,true);
                themeAdapter.notifyDataSetChanged();
            }
        });

        return listView;
    }

    /**
     * A method to change the color of a button
     * @param button the button that should change color
     */
    private void changeButtonColor(Button button){
        ThemeHelper themeHelper = new ThemeHelper(context);
        if(button != null){
            button.setBackgroundColor(themeHelper.getAttrColor(R.attr.secondaryBackgroundColor));
            button.setTextColor(themeHelper.getAttrColor(R.attr.secondaryTextColor));
        }
    }

    /**
     * @param lang the language
     * @return the position of lang
     */
    private int getLanguagePos(String lang){

        switch (lang){
            case "IS":
                return 0;
            case "EN":
                return 1;
            case "SV":
                return 2;
            case "DA":
                return 3;
            case "NO":
                return 4;
            default:
                return -1;
        }
    }

    /**
     * @param pos the position of the lang
     * @return the language in that position
     */
    public static String getLanguageFromPos(int pos){
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
