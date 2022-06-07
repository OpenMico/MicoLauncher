package com.google.android.exoplayer2;

import android.content.Context;
import android.net.wifi.WifiManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;
import com.xiaomi.smarthome.library.common.network.Network;

/* compiled from: WifiLockManager.java */
/* loaded from: classes.dex */
final class j {
    @Nullable
    private final WifiManager a;
    @Nullable
    private WifiManager.WifiLock b;
    private boolean c;
    private boolean d;

    public j(Context context) {
        this.a = (WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
    }

    public void a(boolean z) {
        if (z && this.b == null) {
            WifiManager wifiManager = this.a;
            if (wifiManager == null) {
                Log.w("WifiLockManager", "WifiManager is null, therefore not creating the WifiLock.");
                return;
            } else {
                this.b = wifiManager.createWifiLock(3, "ExoPlayer:WifiLockManager");
                this.b.setReferenceCounted(false);
            }
        }
        this.c = z;
        a();
    }

    public void b(boolean z) {
        this.d = z;
        a();
    }

    private void a() {
        WifiManager.WifiLock wifiLock = this.b;
        if (wifiLock != null) {
            if (!this.c || !this.d) {
                this.b.release();
            } else {
                wifiLock.acquire();
            }
        }
    }
}
