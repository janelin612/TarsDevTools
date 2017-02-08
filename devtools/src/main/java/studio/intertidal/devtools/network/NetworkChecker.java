package studio.intertidal.devtools.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {
    /**
     * 連線狀態
     */
    public enum Status {
        /**
         * 連線中
         */
        CONNECTED,
        /**
         * 網路關閉
         */
        DISCONNECTED,
        /**
         * 網路有開 但不能用
         */
        UNAVAILABLE
    }

    public static Status getStatus(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {
            return Status.DISCONNECTED;
        } else {
            if (mNetworkInfo.isConnected()) {
                return Status.CONNECTED;
            } else {
                return Status.UNAVAILABLE;
            }
        }
    }
}
