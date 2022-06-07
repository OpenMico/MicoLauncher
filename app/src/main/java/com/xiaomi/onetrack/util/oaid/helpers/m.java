package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.oaid.a.g;
import com.xiaomi.onetrack.util.p;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class m {
    private static final String d = "ZTEDeviceIDHelper";
    String a = "com.mdid.msa";
    public final LinkedBlockingQueue<IBinder> b = new LinkedBlockingQueue<>(1);
    ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.ZTEDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                m.this.b.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                p.a("ZTEDeviceIDHelper", e.getMessage());
            }
        }
    };

    private void a(String str, Context context) {
        Intent intent = new Intent();
        intent.setClassName(this.a, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            if (context.startService(intent) == null) {
            }
        } catch (Exception e) {
            p.a(d, e.getMessage());
        }
    }

    public String a(Context context) {
        IBinder poll;
        try {
            context.getPackageManager().getPackageInfo(this.a, 0);
        } catch (Exception e) {
            p.a(d, e.getMessage());
        }
        String packageName = context.getPackageName();
        a(packageName, context);
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", packageName);
        String str = "";
        try {
            try {
            } catch (Exception e2) {
                String message = e2.getMessage();
                p.a(d, message);
                context = message;
            }
            if (context.bindService(intent, this.c, 1)) {
                try {
                    poll = this.b.poll(1L, TimeUnit.SECONDS);
                } catch (Exception e3) {
                    p.a(d, e3.getMessage());
                    context.unbindService(this.c);
                    context = context;
                }
                if (poll == null) {
                    try {
                        context.unbindService(this.c);
                    } catch (Exception e4) {
                        p.a(d, e4.getMessage());
                    }
                    return str;
                }
                str = new g.a.C0183a(poll).b();
                context.unbindService(this.c);
                context = context;
            }
            return str;
        } catch (Throwable th) {
            try {
                context.unbindService(this.c);
            } catch (Exception e5) {
                p.a(d, e5.getMessage());
            }
            throw th;
        }
    }
}
