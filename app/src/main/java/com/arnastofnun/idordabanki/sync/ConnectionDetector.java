package com.arnastofnun.idordabanki.sync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * This class detectes if there is a
 * conncection to the internet
 * @cthulhu
 * @since 2/16/15.
 */
public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

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
