package com.xiaomi.ai.android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.api.Network;
import com.xiaomi.ai.b.c;
import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public final class NetworkUtils {

    /* loaded from: classes2.dex */
    public static class a extends BroadcastReceiver {
        private final d a;
        private volatile boolean b = true;

        public a(d dVar) {
            this.a = dVar;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("networkType", -1);
                Logger.a("NetworkUtils", "onReceive: CONNECTIVITY_ACTION, type=" + intExtra, false);
                c.a.submit(new Runnable() { // from class: com.xiaomi.ai.android.utils.NetworkUtils.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                        boolean z = false;
                        if (connectivityManager == null) {
                            Logger.c("NetworkUtils", "onReceive: ConnectivityManager is null", false);
                            return;
                        }
                        NetworkInfo networkInfo = null;
                        try {
                            networkInfo = connectivityManager.getActiveNetworkInfo();
                        } catch (Exception e) {
                            Logger.c("NetworkUtils", Logger.throwableToString(e), false);
                        }
                        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) {
                            Logger.c("NetworkUtils", "onReceive: activeInfo=" + networkInfo, false);
                        } else {
                            z = true;
                            if (!a.this.b) {
                                a.this.a.h().b(!a.this.a.c().hasMessages(3));
                            }
                        }
                        a.this.b = z;
                    }
                });
            }
        }
    }

    public static String a(String str) {
        return (str == null || "".equals(str)) ? "unknown" : str;
    }

    public static boolean a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            Logger.c("NetworkUtils", Logger.throwableToString(e), false);
        }
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static Network.NetworkType b(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return Network.NetworkType.UNKNOWN;
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            Logger.c("NetworkUtils", Logger.throwableToString(e), false);
        }
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            if (networkInfo.getType() == 1) {
                return Network.NetworkType.WIFI;
            }
            if (networkInfo.getType() == 0) {
                return Network.NetworkType.DATA;
            }
        }
        return Network.NetworkType.UNKNOWN;
    }

    public static String c(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = null;
        if (connectivityManager == null) {
            return null;
        }
        try {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            Logger.c("NetworkUtils", Logger.throwableToString(e), false);
        }
        boolean z = true;
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) {
            if (Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 0) {
                z = false;
            }
            return z ? "none-airplane" : "none";
        } else if (networkInfo.getType() == 1) {
            return "WIFI";
        } else {
            if (networkInfo.getType() != 0) {
                return networkInfo.getTypeName();
            }
            int subtype = networkInfo.getSubtype();
            if (subtype == 20) {
                return "5G";
            }
            switch (subtype) {
                case 1:
                    return "GPRS";
                case 2:
                case 4:
                case 7:
                case 11:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    String subtypeName = networkInfo.getSubtypeName();
                    return (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ? "3G" : subtypeName;
            }
        }
    }

    public static int d(Context context) {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) context.getSystemService(com.xiaomi.smarthome.library.common.network.Network.NETWORK_TYPE_WIFI);
        if (!(wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
            try {
                return WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
            } catch (Exception e) {
                Logger.c("NetworkUtils", Logger.throwableToString(e), false);
            }
        }
        return 0;
    }

    public static String e(Context context) {
        TelephonyManager telephonyManager;
        return (Build.VERSION.SDK_INT < 22 || context == null || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) ? "unknown" : a(telephonyManager.getSimOperator());
    }

    public static native synchronized boolean ipv6Available();
}
