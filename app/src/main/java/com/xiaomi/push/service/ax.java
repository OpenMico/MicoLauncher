package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ac;
import com.xiaomi.push.l;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ax {
    private static ax a;
    private static String e;
    private Context b;
    private boolean d;
    private Messenger h;
    private List<Message> f = new ArrayList();
    private boolean g = false;
    private Messenger c = new Messenger(new v(this, Looper.getMainLooper()));

    private ax(Context context) {
        this.d = false;
        this.b = context.getApplicationContext();
        if (a()) {
            b.c("use miui push service");
            this.d = true;
        }
    }

    public static ax a(Context context) {
        if (a == null) {
            a = new ax(context);
        }
        return a;
    }

    private boolean a() {
        if (ac.e) {
            return false;
        }
        try {
            PackageInfo packageInfo = this.b.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 104;
        } catch (Exception unused) {
            return false;
        }
    }

    private synchronized void b(Intent intent) {
        if (this.g) {
            Message c = c(intent);
            if (this.f.size() >= 50) {
                this.f.remove(0);
            }
            this.f.add(c);
            return;
        }
        if (this.h == null) {
            Context context = this.b;
            w wVar = new w(this);
            Context context2 = this.b;
            context.bindService(intent, wVar, 1);
            this.g = true;
            this.f.clear();
            this.f.add(c(intent));
        } else {
            try {
                this.h.send(c(intent));
            } catch (RemoteException unused) {
                this.h = null;
                this.g = false;
            }
        }
    }

    private Message c(Intent intent) {
        Message obtain = Message.obtain();
        obtain.what = 17;
        obtain.obj = intent;
        return obtain;
    }

    public boolean a(Intent intent) {
        try {
            if (l.m1113a() || Build.VERSION.SDK_INT < 26) {
                this.b.startService(intent);
                return true;
            }
            b(intent);
            return true;
        } catch (Exception e2) {
            b.a(e2);
            return false;
        }
    }
}
