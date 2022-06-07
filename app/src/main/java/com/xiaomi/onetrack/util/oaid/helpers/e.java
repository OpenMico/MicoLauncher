package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.oaid.a.c;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class e {
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.LenovoDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                e.this.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public String a(Context context) {
        IBinder poll;
        context.getPackageName();
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        String str = "";
        if (context.bindService(intent, this.b, 1)) {
            try {
                try {
                    poll = this.a.poll(1L, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (poll == null) {
                    try {
                        context.unbindService(this.b);
                    } catch (Exception unused) {
                    }
                    return str;
                }
                str = new c.a.C0180a(poll).a();
                try {
                    context.unbindService(this.b);
                } catch (Exception unused2) {
                }
            } catch (Throwable th) {
                try {
                    context.unbindService(this.b);
                } catch (Exception unused3) {
                }
                throw th;
            }
        }
        return str;
    }
}
