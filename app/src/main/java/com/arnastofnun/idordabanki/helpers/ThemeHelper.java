package com.arnastofnun.idordabanki.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.R;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * This is a helper class to help with
 * theme changes, and other things connected
 * to themes
 * @karlasgeir
 * @since 3/23/15.
 */
public class ThemeHelper {
    Context context;

    /**
     * Simple constructor
     * @param context
     */
    public ThemeHelper(Context context){
        this.context= context;
    }
    /**
     * Converts an int color to hex value
     * @param color - the integer value for the color
     * @return - the hex value for the color
     */
    private String convertColorToHex(int color){
        return String.format("#%06X", (0xFFFFFF & color));
    }

    /**
     * Returns the color from an attribute
     * @param attrID the attirbute ID
     * @return the color integer
     */
    public int getAttrColor(int attrID){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrID,typedValue,true);
        return typedValue.data;
    }

    /**
     * Returns a drawable ID from
     * an attribute
     * @param attrID the attribute ID
     * @return the drawable ID
     */
    public int getAttrDrawable(int attrID){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrID,typedValue,true);
        return typedValue.resourceId;
    }

    /**
     * Returns hex color from attr ID for a color
     * @param attrID the attibute ID
     * @return the hex color
     */
    public String getHexColorFromAttr(int attrID){
        return convertColorToHex(getAttrColor(attrID));
    }

    /**
     * Sets the no action bar theme for the current theme
     * @param act the activity that should have no action bar
     */
    public static void setCurrentNoActionBar(Activity act){
        BiMap<Integer,Integer> themes = HashBiMap.create();
        themes.put(R.style.AppTheme_Girly,R.style.AppTheme_Girly_NoActionBar_FullScreen);
        themes.put(R.style.AppTheme_Light,R.style.AppTheme_Light_NoActionBar_FullScreen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
        if(sharedPreferences.contains("currentTheme")){
            int currentTheme = sharedPreferences.getInt("currentTheme",0);
            act.setTheme(themes.get(currentTheme));
        }
    }

    /**
     * Sets the current theme to an activity
     * @param act the activity that should get the theme
     */
    public static void setCurrentTheme(Activity act){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
        if(sharedPreferences.contains("currentTheme")){
            int currentTheme = sharedPreferences.getInt("currentTheme",0);
            act.setTheme(currentTheme);
        }

    }

    /**
     * Changes theme
     * @param act - the activity that is calling the theme change
     * @param themeID the theme ID
     */
    public void setTheme(Activity act, int themeID){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Save the theme
        editor.putInt("currentTheme", themeID);
        editor.apply();
        act.setTheme(themeID);
        act.finish();
        //Create an intent and restart the activity
        Intent intent = new Intent(context,context.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }

}
