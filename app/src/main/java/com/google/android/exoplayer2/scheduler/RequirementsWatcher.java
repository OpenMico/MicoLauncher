package com.google.android.exoplayer2.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class RequirementsWatcher {
    private final Context a;
    private final Listener b;
    private final Requirements c;
    private final Handler d = Util.createHandlerForCurrentOrMainLooper();
    @Nullable
    private a e;
    private int f;
    @Nullable
    private b g;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i);
    }

    public RequirementsWatcher(Context context, Listener listener, Requirements requirements) {
        this.a = context.getApplicationContext();
        this.b = listener;
        this.c = requirements;
    }

    public int start() {
        this.f = this.c.getNotMetRequirements(this.a);
        IntentFilter intentFilter = new IntentFilter();
        if (this.c.isNetworkRequired()) {
            if (Util.SDK_INT >= 24) {
                a();
            } else {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
        }
        if (this.c.isChargingRequired()) {
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        if (this.c.isIdleRequired()) {
            if (Util.SDK_INT >= 23) {
                intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            } else {
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
            }
        }
        if (this.c.isStorageNotLowRequired()) {
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        }
        this.e = new a();
        this.a.registerReceiver(this.e, intentFilter, null, this.d);
        return this.f;
    }

    public void stop() {
        this.a.unregisterReceiver((BroadcastReceiver) Assertions.checkNotNull(this.e));
        this.e = null;
        if (Util.SDK_INT >= 24 && this.g != null) {
            b();
        }
    }

    public Requirements getRequirements() {
        return this.c;
    }

    @RequiresApi(24)
    private void a() {
        this.g = new b();
        ((ConnectivityManager) Assertions.checkNotNull((ConnectivityManager) this.a.getSystemService("connectivity"))).registerDefaultNetworkCallback(this.g);
    }

    @RequiresApi(24)
    private void b() {
        ((ConnectivityManager) Assertions.checkNotNull((ConnectivityManager) this.a.getSystemService("connectivity"))).unregisterNetworkCallback((ConnectivityManager.NetworkCallback) Assertions.checkNotNull(this.g));
        this.g = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        int notMetRequirements = this.c.getNotMetRequirements(this.a);
        if (this.f != notMetRequirements) {
            this.f = notMetRequirements;
            this.b.onRequirementsStateChanged(this, notMetRequirements);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if ((this.f & 3) != 0) {
            c();
        }
    }

    /* loaded from: classes2.dex */
    private class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                RequirementsWatcher.this.c();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    /* loaded from: classes2.dex */
    public final class b extends ConnectivityManager.NetworkCallback {
        private boolean b;
        private boolean c;

        private b() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            a();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            a();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            boolean hasCapability = networkCapabilities.hasCapability(16);
            if (!this.b || this.c != hasCapability) {
                this.b = true;
                this.c = hasCapability;
                a();
            } else if (hasCapability) {
                b();
            }
        }

        private void a() {
            RequirementsWatcher.this.d.post(new Runnable() { // from class: com.google.android.exoplayer2.scheduler.-$$Lambda$RequirementsWatcher$b$hpIIrUfk0ZX5b-k59dcnrjmtHEo
                @Override // java.lang.Runnable
                public final void run() {
                    RequirementsWatcher.b.this.d();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d() {
            if (RequirementsWatcher.this.g != null) {
                RequirementsWatcher.this.c();
            }
        }

        private void b() {
            RequirementsWatcher.this.d.post(new Runnable() { // from class: com.google.android.exoplayer2.scheduler.-$$Lambda$RequirementsWatcher$b$SfyWMSHdfrF0qeO3rFSR5t3TQ0Y
                @Override // java.lang.Runnable
                public final void run() {
                    RequirementsWatcher.b.this.c();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c() {
            if (RequirementsWatcher.this.g != null) {
                RequirementsWatcher.this.d();
            }
        }
    }
}
