package xyz.davidng.puzzle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 1/9/2016.
 */
public class Utils {

    public static boolean isOnline(Context context) {
        try {
            NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (localNetworkInfo != null) {
                boolean bool = localNetworkInfo.isConnectedOrConnecting();
                if (bool) {
                    return true;
                }
            }
        } catch (Exception ex) {}
        return false;
    }

}
