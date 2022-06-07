package com.google.zxing.client.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* loaded from: classes2.dex */
public final class InactivityTimer {
    private static final String a = "InactivityTimer";
    private final Context b;
    private Runnable f;
    private boolean g;
    private boolean d = false;
    private final BroadcastReceiver c = new a();
    private Handler e = new Handler();

    public InactivityTimer(Context context, Runnable runnable) {
        this.b = context;
        this.f = runnable;
    }

    public void activity() {
        c();
        if (this.g) {
            this.e.postDelayed(this.f, 300000L);
        }
    }

    public void start() {
        b();
        activity();
    }

    public void cancel() {
        c();
        a();
    }

    private void a() {
        if (this.d) {
            this.b.unregisterReceiver(this.c);
            this.d = false;
        }
    }

    private void b() {
        if (!this.d) {
            this.b.registerReceiver(this.c, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.d = true;
        }
    }

    private void c() {
        this.e.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        this.g = z;
        if (this.d) {
            activity();
        }
    }

    /* loaded from: classes2.dex */
    private final class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                final boolean z = intent.getIntExtra("plugged", -1) <= 0;
                InactivityTimer.this.e.post(new Runnable() { // from class: com.google.zxing.client.android.InactivityTimer.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InactivityTimer.this.a(z);
                    }
                });
            }
        }
    }
}
