package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;

/* compiled from: WakeLockManager.java */
/* loaded from: classes.dex */
final class i {
    @Nullable
    private final PowerManager a;
    @Nullable
    private PowerManager.WakeLock b;
    private boolean c;
    private boolean d;

    public i(Context context) {
        this.a = (PowerManager) context.getApplicationContext().getSystemService("power");
    }

    public void a(boolean z) {
        if (z && this.b == null) {
            PowerManager powerManager = this.a;
            if (powerManager == null) {
                Log.w("WakeLockManager", "PowerManager is null, therefore not creating the WakeLock.");
                return;
            } else {
                this.b = powerManager.newWakeLock(1, "ExoPlayer:WakeLockManager");
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

    @SuppressLint({"WakelockTimeout"})
    private void a() {
        PowerManager.WakeLock wakeLock = this.b;
        if (wakeLock != null) {
            if (!this.c || !this.d) {
                this.b.release();
            } else {
                wakeLock.acquire();
            }
        }
    }
}
