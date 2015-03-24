package com.arnastofnun.idordabanki.sync;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.arnastofnun.idordabanki.dialogs.ConnectionDialogueFragment;

/**
 * Checks if an internet connection is present
 * @author Bill
 */
public class ConnectionDetector {

    private Activity _activity;

    /**
     * Simple constructor
     * @param activity - the calling activity
     */
    public ConnectionDetector(Activity activity){
        this._activity = activity;
    }

    /**
     *
     * @return true is connection present, false if not
     */
    private boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    /**
     * Written by Bill
     * Checks if locale is manually set or if this is the first startup of the app
     * Post: If locale is set it does nothing
     *       If locale is not set it starts the select language activity
     */
    public boolean confirmConnection(){
        boolean connected = isConnectingToInternet();
        if(!connected){
            DialogFragment restartQuit = new ConnectionDialogueFragment();
            restartQuit.show(_activity.getFragmentManager(), "NoInternetConnection");
        }
        return connected;
    }

    /**
     * A method that tries to connect again
     */
    public void retry(){
        _activity.finish();
        Intent i = _activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(_activity.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _activity.startActivity(i);
    }
}
