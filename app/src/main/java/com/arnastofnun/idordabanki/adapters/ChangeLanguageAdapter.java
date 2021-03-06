package com.arnastofnun.idordabanki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.arnastofnun.idordabanki.R;

import java.util.ArrayList;

/**
 * This is an adapter that inflates the
 * views for the language change dialog
 * and toggles the radio buttons
 * @author Karl Ásgeir Geirsson
 * @since 21.11.2014
 */
public class ChangeLanguageAdapter extends ArrayAdapter<String> {
    int selectedIndex = -1; //The default index of the selected item

    /**
     * Constructor for the change language adapter
     * @param context the context of the activity calling the adapter
     * @param listViewResourceID The resource id of the list view
     * @param languages The languages to be listed
     */
    public ChangeLanguageAdapter(Context context, int listViewResourceID, ArrayList<String> languages) {
        super(context, listViewResourceID, languages);
    }

    /**
     * A public setter to change the selected item
     * @param index the index of the selected item
     */
    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    /**
     * A function that is run once a list item gets into the visible view
     * @param position The position of the list item
     * @param convertView The view of the list item
     * @param parent The parent view of the list item
     * @return The view for the list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //If the view hasn't been inflated
        if (convertView == null) { view = inflateView(parent);}
        //If the item exists
        if (getItem(position) != null) {
            updateItem(view,position);
        }
        //Return the converted view
        return view;
    }


    /**
     * A method to inflate a view
     * @param parent The viewgroup that the inflated view is appended to
     * @return the inflated view
     */
    private View inflateView(ViewGroup parent){
        LayoutInflater vi = LayoutInflater.from(getContext());
        return  vi.inflate(R.layout.change_language_list,parent, false);
    }

    /**
     * A method that updates the state of the current
     * item
     * @param view the view of the current item
     * @param position the position of the current item
     */
    private void updateItem(View view, int position){
        //Get the views for the radio button and language view
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.change_language_radio);
        TextView language = (TextView) view.findViewById(R.id.change_language_language);
        setLanguage(language, position);
        toggleRadioButton(radioButton, position);
    }

    /**
     * A method that sets the language text view
     * @param languageView the language text view
     * @param position the position of the view in the list
     */
    private void setLanguage(TextView languageView, int position){
        if(languageView != null){languageView.setText(getItem(position));}
    }

    /**
     * A method to toggle the selected state of the radio buttons
     * @param button The radio button to toggle
     * @param position the position of the radio button in the list
     */
    private void toggleRadioButton(RadioButton button, int position){
        button.setChecked(selectedIndex == position);
    }

}
