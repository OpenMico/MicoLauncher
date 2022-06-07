package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.onetrack.util.oaid.a.a;
import com.xiaomi.onetrack.util.p;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class a {
    private static final String c = "ASUSDeviceIDHelper";
    public final LinkedBlockingQueue<IBinder> a = new LinkedBlockingQueue<>(1);
    ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.onetrack.util.oaid.helpers.ASUSDeviceIDHelper$1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                a.this.a.offer(iBinder, 1L, TimeUnit.SECONDS);
            } catch (Exception e) {
                p.a("ASUSDeviceIDHelper", e.getMessage());
            }
        }
    };

    public String a(Context context) {
        IBinder poll;
        Intent intent = new Intent();
        intent.setAction("com.asus.msa.action.ACCESS_DID");
        intent.setComponent(new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService"));
        String str = "";
        try {
        } catch (Exception e) {
            p.a(c, e.getMessage());
        }
        if (context.bindService(intent, this.b, 1)) {
            try {
                try {
                    poll = this.a.poll(1L, TimeUnit.SECONDS);
                } catch (Exception e2) {
                    p.a(c, e2.getMessage());
                    context.unbindService(this.b);
                }
                if (poll == null) {
                    try {
                        context.unbindService(this.b);
                    } catch (Exception e3) {
                        p.a(c, e3.getMessage());
                    }
                    return str;
                }
                str = new a.C0179a(poll).a();
                context.unbindService(this.b);
            } catch (Throwable th) {
                try {
                    context.unbindService(this.b);
                } catch (Exception e4) {
                    p.a(c, e4.getMessage());
                }
                throw th;
            }
        }
        return str;
    }
}
