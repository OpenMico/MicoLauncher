package com.xiaomi.miot.host.lan.impl.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes2.dex */
public class WifiBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "WifiBroadcastReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
        if ("android.net.wifi.STATE_CHANGE".equals(action)) {
            Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
            WifiInfo wifiInfo = (WifiInfo) intent.getParcelableExtra("wifiInfo");
            intent.getStringExtra("bssid");
            if (parcelableExtra != null) {
                NetworkInfo.State state = ((NetworkInfo) parcelableExtra).getState();
                String str = TAG;
                Log.d(str, "wifi状态改变：" + state.toString());
                if (state == NetworkInfo.State.DISCONNECTED) {
                    Log.d(TAG, "wifi状态断开");
                } else if (state == NetworkInfo.State.CONNECTED) {
                    Log.d(TAG, "wifi状态连接");
                    MiotLogUtil.e("WifiBroadcastReceiver", "connectHideAp success" + wifiManager.getConnectionInfo().toString());
                }
            }
        }
    }
}
