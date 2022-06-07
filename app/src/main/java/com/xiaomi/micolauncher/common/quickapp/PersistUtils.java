package com.xiaomi.micolauncher.common.quickapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.xiaomi.micolauncher.common.L;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PersistUtils {
    private static Map<String, a> a = new HashMap();

    public static void persist(Context context, String str, String str2) {
        if (a.get(a(str, str2)) == null) {
            L.quickapp.i("PersistUtils persist");
            a aVar = new a(context, str, str2);
            aVar.b();
            a.put(a(str, str2), aVar);
        }
    }

    public static void unpersist(String str, String str2) {
        a remove = a.remove(a(str, str2));
        if (remove != null) {
            L.quickapp.i("PersistUtils unpersist");
            remove.c();
        }
    }

    private static String a(String str, String str2) {
        return str + "|" + str2;
    }

    /* loaded from: classes3.dex */
    private static class a {
        Context a;
        String b;
        String c;
        IBinder.DeathRecipient d;
        ServiceConnection e;
        IBinder f;
        long g;
        int h;
        Handler i;
        long j;

        private a(Context context, String str, String str2) {
            this.h = 3;
            this.a = context;
            this.b = str;
            this.c = str2;
            this.d = new IBinder.DeathRecipient() { // from class: com.xiaomi.micolauncher.common.quickapp.PersistUtils.a.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    a.this.b();
                }
            };
            this.e = new ServiceConnection() { // from class: com.xiaomi.micolauncher.common.quickapp.PersistUtils.a.2
                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                }

                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    a.this.i.removeMessages(0);
                    a aVar = a.this;
                    aVar.f = iBinder;
                    try {
                        iBinder.linkToDeath(aVar.d, 0);
                    } catch (RemoteException e) {
                        L.quickapp.i("PersistUtils", "link to death failed.", e);
                    }
                }
            };
            this.i = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.common.quickapp.PersistUtils.a.3
                @Override // android.os.Handler
                public void handleMessage(@NotNull Message message) {
                    a.this.b();
                }
            };
        }

        private void a() {
            if (Math.abs(System.currentTimeMillis() - this.g) > 300000) {
                this.h = 3;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            if (this.h > 0 || Math.abs(System.currentTimeMillis() - this.j) > 60000) {
                a();
                int i = this.h;
                if (i <= 0) {
                    c();
                    L.quickapp.i("PersistUtils", "start: delay 300000ms");
                    this.i.sendEmptyMessageDelayed(0, 300000L);
                    return;
                }
                if (i == 3) {
                    this.j = System.currentTimeMillis();
                }
                this.h--;
                this.g = System.currentTimeMillis();
                try {
                    Intent intent = new Intent();
                    intent.setPackage(this.b);
                    intent.setComponent(new ComponentName(this.b, this.c));
                    this.a.bindService(intent, this.e, 1);
                    this.i.sendEmptyMessageDelayed(0, 10000L);
                } catch (Exception e) {
                    L.quickapp.i("PersistUtils", "bind service failed", e);
                }
            } else {
                c();
                L.quickapp.i("PersistUtils", "start: delay 43200000ms");
                this.i.sendEmptyMessageDelayed(0, 43200000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            try {
                if (this.f != null) {
                    this.f.unlinkToDeath(this.d, 0);
                }
                this.a.unbindService(this.e);
            } catch (Exception e) {
                L.quickapp.e("PersistUtils", "stop: ", e);
            }
        }
    }
}
