package com.arnastofnun.idordabanki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Checks if an internet connection is present
 * @author Bill
 */
public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    /**
     *
     * @return true is connection present, false if not
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for(NetworkInfo item:info){
                    if(item.getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
        }
        return false;
    }
}
