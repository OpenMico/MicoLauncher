package com.xiaomi.micolauncher.overlay.client;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.overlay.ILauncherOverlay;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* compiled from: PersistentLauncherOverlayConnection.java */
/* loaded from: classes3.dex */
class c extends b {
    private static final HashMap<String, c> b = new HashMap<>(2);
    @Nullable
    public ILauncherOverlay a;
    @Nullable
    private WeakReference<LauncherClient> c;
    private boolean d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized c a(Context context, String str) {
        c cVar;
        synchronized (c.class) {
            cVar = b.get(str);
            if (cVar == null) {
                cVar = new c(context, str);
                b.put(str, cVar);
            }
        }
        return cVar;
    }

    private c(Context context, String str) {
        super(context, 33, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized ILauncherOverlay a(LauncherClient launcherClient) {
        this.c = new WeakReference<>(launcherClient);
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void a(LauncherClient launcherClient, boolean z) {
        LauncherClient d = d();
        if (d != null && d.equals(launcherClient)) {
            this.c = null;
            if (z) {
                b();
                b.remove(c());
            }
        }
    }

    @Nullable
    private LauncherClient d() {
        WeakReference<LauncherClient> weakReference = this.c;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // com.xiaomi.micolauncher.overlay.client.b, android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        super.onServiceConnected(componentName, iBinder);
        a(ILauncherOverlay.Stub.asInterface(iBinder));
    }

    @Override // com.xiaomi.micolauncher.overlay.client.b, android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        super.onServiceDisconnected(componentName);
        a((ILauncherOverlay) null);
        e();
    }

    private void a(ILauncherOverlay iLauncherOverlay) {
        this.a = iLauncherOverlay;
        LauncherClient d = d();
        if (d != null) {
            d.a(this.a);
        }
    }

    public final void a(boolean z) {
        this.d = z;
        e();
    }

    private void e() {
        if (this.d && this.a == null) {
            b();
        }
    }
}
