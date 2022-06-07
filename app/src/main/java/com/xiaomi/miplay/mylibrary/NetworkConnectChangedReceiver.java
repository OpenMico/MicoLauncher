package com.xiaomi.miplay.mylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;

/* loaded from: classes4.dex */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String d = "NetworkConnectChangedReceiver";
    private MiPlayAudioService a;
    private DeviceManager b;
    private Handler c;

    public NetworkConnectChangedReceiver(MiPlayAudioService miPlayAudioService, DeviceManager deviceManager, Handler handler) {
        this.a = miPlayAudioService;
        this.b = deviceManager;
        this.c = handler;
    }

    @Override // android.content.BroadcastReceiver
    @RequiresApi(api = 23)
    public void onReceive(Context context, Intent intent) {
        Logger.d(d, "onReceive action" + intent.getAction(), new Object[0]);
        boolean z = true;
        if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
            if (parcelableExtra != null) {
                if (((NetworkInfo) parcelableExtra).getState() != NetworkInfo.State.CONNECTED) {
                    z = false;
                }
                Logger.d(d, "isConnected:" + z, new Object[0]);
                if (!z) {
                    int localPlayState = this.a.getLocalPlayState();
                    Logger.d(d, "state_changed connectedmidevicelist:" + this.b.getConnectedMiDeviceList().size(), new Object[0]);
                    if (localPlayState == 3 && this.b.getConnectedMiDeviceList().size() > 0) {
                        this.a.LocalPause();
                    }
                }
            }
        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            Logger.d(d, "CONNECTIVITY_ACTION", new Object[0]);
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                Logger.d(d, "activeNetwork is null", new Object[0]);
                this.c.obtainMessage(47).sendToTarget();
            } else if (!activeNetworkInfo.isConnected()) {
                Logger.d(d, "current  no Internet available", new Object[0]);
                this.c.obtainMessage(47).sendToTarget();
                int localPlayState2 = this.a.getLocalPlayState();
                Logger.d(d, "connectivity connectedmidevicelist:" + this.b.getConnectedMiDeviceList().size(), new Object[0]);
                if (localPlayState2 == 3 && this.b.getConnectedMiDeviceList().size() > 0) {
                    this.a.LocalPause();
                }
            } else if (activeNetworkInfo.getType() == 1) {
                Log.e(d, "connected to wifi");
                this.a.highFrequencyDiscovery();
            } else if (activeNetworkInfo.getType() == 0) {
                Log.e(d, " connected to mobile");
            }
        }
    }
}
