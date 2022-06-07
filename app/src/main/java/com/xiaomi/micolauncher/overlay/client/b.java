package com.xiaomi.micolauncher.overlay.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/* compiled from: LauncherOverlayConnection.java */
/* loaded from: classes3.dex */
class b implements ServiceConnection {
    private final Context a;
    private final int b;
    private final String c;
    private boolean d;

    public b(Context context, int i, String str) {
        this.a = context;
        this.c = str;
        this.b = i;
    }

    public final boolean a() {
        if (!this.d) {
            Context context = this.a;
            this.d = context.bindService(LauncherClient.getServerIntent(context, this.c), this, this.b);
            Log.d("LauncherClient.Connection", "bind overlay service of " + this.c + ", result:" + this.d);
        } else {
            Log.d("LauncherClient.Connection", "overlay service already connected for" + this.c);
        }
        return this.d;
    }

    public final void b() {
        if (this.d) {
            this.a.unbindService(this);
            this.d = false;
            Log.d("LauncherClient.Connection", "unbind overlay service of " + this.c);
            return;
        }
        Log.d("LauncherClient.Connection", "overlay service already disconnected for" + this.c);
    }

    public final String c() {
        return this.c;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("LauncherClient.Connection", "onServiceConnected:" + componentName);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("LauncherClient.Connection", "onServiceDisconnected:" + componentName);
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        Log.d("LauncherClient.Connection", "onBindingDied:" + componentName);
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        Log.d("LauncherClient.Connection", "onNullBinding:" + componentName);
    }
}
