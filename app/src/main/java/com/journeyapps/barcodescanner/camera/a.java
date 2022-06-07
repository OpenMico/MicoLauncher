package com.journeyapps.barcodescanner.camera;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CameraThread.java */
/* loaded from: classes2.dex */
public class a {
    private static final String a = "a";
    private static a b;
    private Handler c;
    private HandlerThread d;
    private int e = 0;
    private final Object f = new Object();

    public static a a() {
        if (b == null) {
            b = new a();
        }
        return b;
    }

    private a() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(Runnable runnable) {
        synchronized (this.f) {
            c();
            this.c.post(runnable);
        }
    }

    private void c() {
        synchronized (this.f) {
            if (this.c == null) {
                if (this.e > 0) {
                    this.d = new HandlerThread("CameraThread");
                    this.d.start();
                    this.c = new Handler(this.d.getLooper());
                } else {
                    throw new IllegalStateException("CameraThread is not open");
                }
            }
        }
    }

    private void d() {
        synchronized (this.f) {
            this.d.quit();
            this.d = null;
            this.c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void b() {
        synchronized (this.f) {
            this.e--;
            if (this.e == 0) {
                d();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void b(Runnable runnable) {
        synchronized (this.f) {
            this.e++;
            a(runnable);
        }
    }
}
