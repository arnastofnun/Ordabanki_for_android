package com.arnastofnun.idordabanki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by karlasgeir on 3/23/15.
 */
public class ThemeHelper {
    Context context;

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

    public int getAttrDrawable(int attrID){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrID,typedValue,true);
        return typedValue.resourceId;
    }

    public String getHexColorFromAttr(int attrID){
        return convertColorToHex(getAttrColor(attrID));
    }

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

    public static void setCurrentTheme(Activity act){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
        if(sharedPreferences.contains("currentTheme")){
            int currentTheme = sharedPreferences.getInt("currentTheme",0);
            act.setTheme(currentTheme);
        }

    }

    public void setTheme(Activity act, int themeID){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Globals.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Set lang as the language and apply changes
        editor.putInt("currentTheme", themeID);
        editor.apply();
        act.setTheme(themeID);
        act.finish();
        Intent intent = new Intent(context,context.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }

}
