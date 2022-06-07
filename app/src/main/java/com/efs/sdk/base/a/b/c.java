package com.efs.sdk.base.a.b;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.h.b;
import com.efs.sdk.base.a.h.d;
import java.io.File;

/* loaded from: classes.dex */
public final class c extends Handler implements Runnable {
    public boolean a;

    /* synthetic */ c(byte b) {
        this();
    }

    private c() {
        super(com.efs.sdk.base.a.h.a.a.a.getLooper());
        this.a = true;
        sendEmptyMessageDelayed(2, 60000L);
    }

    /* loaded from: classes.dex */
    static class a {
        private static final c a = new c((byte) 0);
    }

    public static c a() {
        return a.a;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what != 2) {
            d.a("efs.cache", "disk listener not support command: " + message.what, null);
            return;
        }
        com.efs.sdk.base.a.h.a.d.a(this);
    }

    @Override // java.lang.Runnable
    public final void run() {
        a unused;
        unused = a.b.a;
        File f = com.efs.sdk.base.a.h.a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a);
        if (f.exists()) {
            for (File file : b.d(f)) {
                if (a.a(file.getName())) {
                    a.c(file);
                }
            }
        }
        com.efs.sdk.base.a.c.a.c a2 = com.efs.sdk.base.a.c.a.c.a();
        String str = a2.d.e.containsKey("disk_bytes") ? a2.d.e.get("disk_bytes") : "4194304";
        if (TextUtils.isEmpty(str)) {
            str = "4194304";
        }
        long parseLong = Long.parseLong(str);
        long c = b.c(com.efs.sdk.base.a.h.a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a)) + b.c(com.efs.sdk.base.a.h.a.d(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a));
        this.a = c < parseLong;
        if (!this.a) {
            d.a("efs.cache", "Cache Limited! curr " + c + "byte, max " + parseLong + " byte.", null);
        }
        sendEmptyMessageDelayed(2, 600000L);
    }
}
