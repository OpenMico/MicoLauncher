package com.uc.crashsdk.a;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.HashMap;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class f {
    private static volatile HandlerThread b;
    private static volatile HandlerThread c;
    private static volatile HandlerThread d;
    private static Handler e;
    private static Handler f;
    private static Handler g;
    private static Handler h;
    static final /* synthetic */ boolean a = !f.class.desiredAssertionStatus();
    private static final HashMap<Object, Object[]> i = new HashMap<>();

    public static Handler a(int i2) {
        switch (i2) {
            case 0:
                if (b == null) {
                    a();
                }
                return e;
            case 1:
                if (c == null) {
                    b();
                }
                return f;
            case 2:
                if (g == null) {
                    g = new Handler(Looper.getMainLooper());
                }
                return g;
            case 3:
                if (h == null) {
                    c();
                }
                return h;
            default:
                throw new RuntimeException("unknown thread type: " + i2);
        }
    }

    public static boolean a(int i2, Runnable runnable, long j) {
        Handler a2;
        if (runnable == null || (a2 = a(i2)) == null) {
            return false;
        }
        e eVar = new e(10, new Object[]{runnable});
        synchronized (i) {
            i.put(runnable, new Object[]{eVar, Integer.valueOf(i2)});
        }
        return a2.postDelayed(eVar, j);
    }

    public static void a(int i2, Object[] objArr) {
        if (i2 != 10) {
            if (!a) {
                throw new AssertionError();
            }
        } else if (a || objArr != null) {
            Runnable runnable = (Runnable) objArr[0];
            synchronized (i) {
                if (i.get(runnable) != null) {
                    i.remove(runnable);
                }
            }
            runnable.run();
        } else {
            throw new AssertionError();
        }
    }

    public static boolean a(int i2, Runnable runnable) {
        return a(i2, runnable, 0L);
    }

    public static void a(Runnable runnable) {
        Object[] objArr;
        if (runnable != null) {
            synchronized (i) {
                objArr = i.get(runnable);
            }
            if (objArr != null) {
                Handler handler = null;
                switch (((Integer) objArr[1]).intValue()) {
                    case 0:
                        handler = e;
                        break;
                    case 1:
                        handler = f;
                        break;
                    case 2:
                        handler = g;
                        break;
                }
                if (handler != null) {
                    handler.removeCallbacks((Runnable) objArr[0]);
                }
                synchronized (i) {
                    if (i.get(runnable) != null) {
                        i.remove(runnable);
                    }
                }
            }
        }
    }

    public static boolean b(Runnable runnable) {
        Object[] objArr;
        if (runnable == null) {
            return false;
        }
        synchronized (i) {
            objArr = i.get(runnable);
        }
        return objArr != null;
    }

    private static synchronized void a() {
        synchronized (f.class) {
            if (b == null) {
                HandlerThread handlerThread = new HandlerThread("CrashSDKBkgdHandler", 10);
                b = handlerThread;
                handlerThread.start();
                e = new Handler(b.getLooper());
            }
        }
    }

    private static synchronized void b() {
        synchronized (f.class) {
            if (c == null) {
                HandlerThread handlerThread = new HandlerThread("CrashSDKNormalHandler", 0);
                c = handlerThread;
                handlerThread.start();
                f = new Handler(c.getLooper());
            }
        }
    }

    private static synchronized void c() {
        synchronized (f.class) {
            if (d == null) {
                HandlerThread handlerThread = new HandlerThread("CrashSDKAnrHandler", 0);
                d = handlerThread;
                handlerThread.start();
                h = new Handler(d.getLooper());
            }
        }
    }
}
