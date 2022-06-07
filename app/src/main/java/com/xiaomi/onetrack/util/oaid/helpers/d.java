package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.oaid.a.b;
import com.xiaomi.onetrack.util.p;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class d {
    private static final String c = "HWDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.HWDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                d.this.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                p.a("HWDeviceIDHelper", e.getMessage());
            }
        }
    };

    public String a(Context context) {
        IBinder poll;
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        String str = "";
        try {
            try {
            } catch (Throwable th) {
                try {
                    context.unbindService(this.b);
                } catch (Exception e) {
                    p.a(c, e.getMessage());
                }
                throw th;
            }
        } catch (Exception e2) {
            String message = e2.getMessage();
            p.a(c, message);
            context = message;
        }
        if (context.bindService(intent, this.b, 1)) {
            try {
                poll = this.a.poll(1L, TimeUnit.SECONDS);
            } catch (Exception e3) {
                p.a(c, e3.getMessage());
                context.unbindService(this.b);
                context = context;
            }
            if (poll == null) {
                try {
                    context.unbindService(this.b);
                } catch (Exception e4) {
                    p.a(c, e4.getMessage());
                }
                return str;
            }
            str = new b.a(poll).a();
            context.unbindService(this.b);
            context = context;
        }
        return str;
    }
}
