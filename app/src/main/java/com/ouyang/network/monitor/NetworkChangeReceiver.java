package com.ouyang.network.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/* loaded from: classes2.dex */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private int a = -1;
    private NetworkChangeListener b;

    private String c(int i) {
        if (i == 4) {
            return "TYPE_MOBILE_DUN";
        }
        if (i == 17) {
            return "TYPE_VPN";
        }
        switch (i) {
            case 0:
                return "TYPE_MOBILE";
            case 1:
                return "TYPE_WIFI";
            default:
                switch (i) {
                    case 6:
                        return "TYPE_WIMAX";
                    case 7:
                        return "TYPE_BLUETOOTH";
                    case 8:
                        return "TYPE_DUMMY";
                    case 9:
                        return "TYPE_ETHERNET";
                    default:
                        return "UNKNOWN";
                }
        }
    }

    public NetworkChangeReceiver(NetworkChangeListener networkChangeListener) {
        this.b = networkChangeListener;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d("NetworkChangeReceiver", "onReceive: " + intent.getAction());
        if (intent.getBooleanExtra("noConnectivity", false)) {
            Log.d("NetworkChangeReceiver", "NO_CONNECTIVITY");
            int i = this.a;
            if (i != -1) {
                a(i);
                this.a = -1;
            }
        } else {
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("otherNetwork");
            if (networkInfo != null) {
                Log.d("NetworkChangeReceiver", "EXTRA_OTHER_NETWORK_INFO: " + c(networkInfo.getType()) + " state: " + networkInfo.getState());
                if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                    b(networkInfo);
                }
            }
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            a(activeNetworkInfo);
        }
    }

    private void a(NetworkInfo networkInfo) {
        Log.d("NetworkChangeReceiver", "NetworkType: " + c(networkInfo.getType()));
        int i = this.a;
        if (!(i == -1 || i == networkInfo.getType())) {
            a(this.a);
        }
        b(networkInfo);
    }

    private void b(NetworkInfo networkInfo) {
        Log.d("NetworkChangeReceiver", "NetworkState: " + networkInfo.getState());
        if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
            b(networkInfo.getType());
            this.a = networkInfo.getType();
        } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED && this.a != -1) {
            a(networkInfo.getType());
            this.a = -1;
        }
    }

    private void a(int i) {
        if (i != 9) {
            switch (i) {
                case 0:
                    break;
                default:
                    return;
                case 1:
                    this.b.onWifiDisconnected();
                    break;
            }
            this.b.onMobileDisconnected();
            return;
        }
        this.b.onEthernetDisconnected();
    }

    private void b(int i) {
        if (i != 9) {
            switch (i) {
                case 0:
                    break;
                default:
                    return;
                case 1:
                    this.b.onWifiConnected();
                    break;
            }
            this.b.onMobileConnected();
            return;
        }
        this.b.onEthernetConnected();
    }
}
