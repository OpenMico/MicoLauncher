package com.ouyang.network.monitor;

import android.content.Context;
import android.os.PowerManager;

/* loaded from: classes2.dex */
public class NetworkWakeLock {
    private Context a;
    private PowerManager.WakeLock b = null;
    private String c;

    public NetworkWakeLock(Context context, String str) {
        this.a = null;
        this.c = null;
        this.a = context;
        this.c = str == null ? "NetworkWakeLock" : str;
    }

    public void acquireWakeLock() {
        synchronized (this) {
            if (this.b == null) {
                this.b = ((PowerManager) this.a.getSystemService("power")).newWakeLock(536870913, this.c);
                if (this.b != null) {
                    this.b.acquire();
                }
            }
        }
    }

    public void releaseWakeLock() {
        synchronized (this) {
            if (this.b != null) {
                this.b.release();
                this.b = null;
            }
        }
    }
}
