package com.efs.sdk.base.a.e;

import android.content.Context;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.a.h.g;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

/* loaded from: classes.dex */
public final class f {
    static FileLock b;
    volatile int a;

    /* synthetic */ f(byte b2) {
        this();
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final f a = new f((byte) 0);

        public static /* synthetic */ f a() {
            return a;
        }
    }

    private f() {
        this.a = 0;
        a(com.efs.sdk.base.a.d.a.a().c);
    }

    public final boolean a() {
        if (this.a == 2) {
            return true;
        }
        if (this.a != 0) {
            return false;
        }
        a(com.efs.sdk.base.a.d.a.a().c);
        return false;
    }

    private synchronized void a(final Context context) {
        d.a("efs.send_log", "tryFileLock start! ", null);
        this.a = 1;
        new Thread(new Runnable() { // from class: com.efs.sdk.base.a.e.f.1
            {
                f.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FileLock lock;
                try {
                    File a2 = com.efs.sdk.base.a.h.a.a(context);
                    if (!a2.exists()) {
                        a2.mkdirs();
                    }
                    File file = new File(a2.getPath() + File.separator + "sendlock");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    do {
                        lock = new FileOutputStream(file).getChannel().lock();
                        f.b = lock;
                    } while (!lock.isValid());
                    d.a("efs.send_log", "tryFileLock sendlock sucess! processname: " + g.b(), null);
                    f.this.a = 2;
                } catch (Exception e) {
                    d.a("efs.send_log", "tryFileLock fail! " + e.getMessage(), null);
                    f.this.a = 0;
                }
            }
        }).start();
    }
}
