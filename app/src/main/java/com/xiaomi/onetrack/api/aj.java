package com.xiaomi.onetrack.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.miui.analytics.ITrack;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.util.p;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class aj {
    public static final String a = "com.miui.analytics";
    public static final String b = "com.miui.analytics.onetrack.OneTrackService";
    private static final String c = "ServiceConnectManager";
    private static final int i = 1;
    private ITrack d;
    private AtomicBoolean e;
    private AtomicBoolean f;
    private final Object g;
    private Context h;
    private c j;
    private ServiceConnection k;
    private CopyOnWriteArrayList<b> l;

    /* loaded from: classes4.dex */
    public interface b {
        void a();
    }

    public static aj a() {
        return a.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a {
        private static aj a = new aj();

        private a() {
        }
    }

    private aj() {
        this.e = new AtomicBoolean(false);
        this.f = new AtomicBoolean(false);
        this.g = new Object();
        this.k = new ServiceConnection() { // from class: com.xiaomi.onetrack.api.ServiceConnectionManager$1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Object obj;
                AtomicBoolean atomicBoolean;
                int i2;
                AtomicBoolean atomicBoolean2;
                AtomicBoolean atomicBoolean3;
                ITrack iTrack;
                try {
                    obj = aj.this.g;
                    synchronized (obj) {
                        atomicBoolean = aj.this.e;
                        i2 = 0;
                        atomicBoolean.set(false);
                        atomicBoolean2 = aj.this.f;
                        atomicBoolean2.set(true);
                        aj.this.d = ITrack.Stub.asInterface(iBinder);
                    }
                    aj.this.e();
                    StringBuilder sb = new StringBuilder();
                    sb.append("onServiceConnected  mConnecting ");
                    atomicBoolean3 = aj.this.e;
                    sb.append(atomicBoolean3);
                    sb.append(" mIOneTrackService ");
                    iTrack = aj.this.d;
                    if (iTrack != null) {
                        i2 = 1;
                    }
                    sb.append(i2);
                    sb.append(" pid:");
                    sb.append(Process.myPid());
                    sb.append(" tid:");
                    sb.append(Process.myTid());
                    p.a("ServiceConnectManager", sb.toString());
                } catch (Throwable th) {
                    p.a("ServiceConnectManager", "onServiceConnected throwable:" + th.getMessage());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Object obj;
                AtomicBoolean atomicBoolean;
                int i2;
                AtomicBoolean atomicBoolean2;
                AtomicBoolean atomicBoolean3;
                ITrack iTrack;
                try {
                    obj = aj.this.g;
                    synchronized (obj) {
                        aj.this.d = null;
                        atomicBoolean = aj.this.e;
                        i2 = 0;
                        atomicBoolean.set(false);
                        atomicBoolean2 = aj.this.f;
                        atomicBoolean2.set(false);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("onServiceDisconnected:  mConnecting ");
                    atomicBoolean3 = aj.this.e;
                    sb.append(atomicBoolean3);
                    sb.append(" mIOneTrackService ");
                    iTrack = aj.this.d;
                    if (iTrack != null) {
                        i2 = 1;
                    }
                    sb.append(i2);
                    p.a("ServiceConnectManager", sb.toString());
                } catch (Throwable th) {
                    p.a("ServiceConnectManager", "onServiceDisconnected throwable:" + th.getMessage());
                }
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                Object obj;
                AtomicBoolean atomicBoolean;
                AtomicBoolean atomicBoolean2;
                Context context;
                ServiceConnection serviceConnection;
                try {
                    obj = aj.this.g;
                    synchronized (obj) {
                        aj.this.d = null;
                        atomicBoolean = aj.this.e;
                        atomicBoolean.set(false);
                        atomicBoolean2 = aj.this.f;
                        atomicBoolean2.set(false);
                    }
                    try {
                        context = aj.this.h;
                        serviceConnection = aj.this.k;
                        context.unbindService(serviceConnection);
                    } catch (Exception e) {
                        p.a("ServiceConnectManager", "onBindingDied: " + e.toString());
                    }
                } catch (Throwable th) {
                    p.a("ServiceConnectManager", "onBindingDied throwable:" + th.getMessage());
                }
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Object obj;
                AtomicBoolean atomicBoolean;
                AtomicBoolean atomicBoolean2;
                try {
                    obj = aj.this.g;
                    synchronized (obj) {
                        aj.this.d = null;
                        atomicBoolean = aj.this.e;
                        atomicBoolean.set(false);
                        atomicBoolean2 = aj.this.f;
                        atomicBoolean2.set(false);
                    }
                } catch (Throwable th) {
                    p.a("ServiceConnectManager", "onNullBinding throwable:" + th.getMessage());
                }
            }
        };
        this.l = new CopyOnWriteArrayList<>();
        this.h = com.xiaomi.onetrack.e.a.a();
        this.j = new c(Looper.getMainLooper());
        b();
    }

    public boolean a(String str, String str2, Configuration configuration) {
        boolean z;
        synchronized (this.g) {
            b();
            z = false;
            if (this.d != null) {
                try {
                    this.d.trackEvent(configuration.getAppId(), com.xiaomi.onetrack.e.a.d(), str, str2);
                    z = true;
                } catch (RemoteException e) {
                    d();
                    this.e.set(false);
                    this.f.set(false);
                    this.d = null;
                    p.a(c, "track: " + e.toString());
                } catch (NullPointerException unused) {
                }
            }
        }
        return z;
    }

    public void b(String str, String str2, Configuration configuration) {
        try {
            synchronized (this.g) {
                this.d.trackEvent(configuration.getAppId(), com.xiaomi.onetrack.e.a.d(), str, str2);
            }
        } catch (Exception e) {
            p.b(c, "trackCacheData error:" + e.toString());
        }
    }

    private void b() {
        if (this.e.get() || (this.f.get() && this.d != null)) {
            StringBuilder sb = new StringBuilder();
            sb.append("ensureService mConnecting: ");
            sb.append(this.e.get());
            sb.append(" mIsBindSuccess:");
            sb.append(this.f.get());
            sb.append(" mAnalytics: ");
            sb.append(this.d == null ? 0 : 1);
            p.a(c, sb.toString());
            return;
        }
        c();
    }

    private void c() {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.miui.analytics", b);
            boolean bindService = this.h.bindService(intent, this.k, 1);
            if (bindService) {
                this.e.set(true);
            } else {
                this.e.set(false);
                this.h.unbindService(this.k);
            }
            p.a(c, "bindService:  mConnecting: " + this.e + " bindResult:" + bindService);
        } catch (Exception e) {
            try {
                this.e.set(false);
                this.h.unbindService(this.k);
            } catch (Exception e2) {
                Log.d(c, "bindService e1: " + e2.toString());
            }
            p.b(c, "bindService e: " + e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        try {
            if (this.f.get()) {
                this.h.unbindService(this.k);
                this.f.set(false);
                p.a(c, "unBindService  mIsBindSuccess:" + this.f.get());
            }
        } catch (Exception e) {
            p.a(c, "unBindService: " + e.toString());
        }
    }

    public void a(b bVar) {
        if (!this.l.contains(bVar)) {
            this.l.add(bVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        Iterator<b> it = this.l.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public void a(int i2) {
        if (i2 == 2) {
            this.j.sendEmptyMessageDelayed(1, 5000L);
        } else if (this.j.hasMessages(1)) {
            this.j.removeMessages(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                aj.this.d();
            }
        }
    }
}
