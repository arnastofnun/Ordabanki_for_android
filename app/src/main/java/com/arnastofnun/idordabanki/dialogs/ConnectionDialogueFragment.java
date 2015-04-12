package com.arnastofnun.idordabanki.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;

/**
 * Creates a dialogue box when no internet connection present
 * @author Bill
 */
public class ConnectionDialogueFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
   * implement this interface in order to receive event callbacks.
   * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ConnectionDialogueListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ConnectionDialogueListener mListener;

    /** Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
     * @param activity the activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ConnectionDialogueListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ConnectionDialogueListener");
        }
    }

    /**
     * Builds the dialog and set up the button click handlers
     * @param savedInstanceState the saved instance state
     * @return Alert dialogue the alert dialogue
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.connection_dialog, null);

        builder.setView(view);
            setCancelable(false);
            builder.setPositiveButton(R.string.Retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(ConnectionDialogueFragment.this);
                    }
                }).setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Send the negative button event back to the host activity
                    mListener.onDialogNegativeClick(ConnectionDialogueFragment.this);
                }
            });
        return builder.create();
    }

    /**
     * A method that runs when the dialogue has started
     */
    @Override
    public void onStart(){
        super.onStart();
        //Set the button colors
        changeButtonColor(((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE));
        changeButtonColor(((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE));
    }

    /**
     * This method changes the color of a button
     * Written by: Karl Ásgeir Geirsson
     * @param button the button to change color
     */
    public void changeButtonColor(Button button){
        if(button != null){
            ThemeHelper themeHelper = new ThemeHelper(getActivity());
            button.setBackgroundColor(themeHelper.getAttrColor(R.attr.buttonBackground));
            button.setTextColor(themeHelper.getAttrColor(R.attr.buttonText));
        }
    }

}





