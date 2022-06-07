package java8.util.concurrent;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import sun.misc.Unsafe;

/* compiled from: TLRandom.java */
/* loaded from: classes5.dex */
final class c {
    private static final Unsafe a = d.a;
    private static final boolean b;
    private static final boolean c;
    private static final long d;
    private static final long e;
    private static final long f;
    private static final long g;
    private static final ThreadLocal<a> h;
    private static final AtomicInteger i;
    private static final AtomicLong j;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(long j2) {
        long j3 = (j2 ^ (j2 >>> 33)) * (-49064778989728563L);
        long j4 = (j3 ^ (j3 >>> 33)) * (-4265267296055464877L);
        return j4 ^ (j4 >>> 33);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(long j2) {
        long j3 = (j2 ^ (j2 >>> 33)) * (-49064778989728563L);
        return (int) (((j3 ^ (j3 >>> 33)) * (-4265267296055464877L)) >>> 32);
    }

    private c() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void a() {
        int addAndGet = i.addAndGet(-1640531527);
        if (addAndGet == 0) {
            addAndGet = 1;
        }
        c(a(j.getAndAdd(-4942790177534073029L)));
        b(addAndGet);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final long b() {
        long e2 = e() - 7046029254386353131L;
        c(e2);
        return e2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int c() {
        return f();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int a(int i2) {
        int i3 = i2 ^ (i2 << 13);
        int i4 = i3 ^ (i3 >>> 17);
        int i5 = i4 ^ (i4 << 5);
        b(i5);
        return i5;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int d() {
        int i2;
        int g2 = g();
        if (g2 != 0) {
            int i3 = g2 ^ (g2 << 13);
            int i4 = i3 ^ (i3 >>> 17);
            i2 = i4 ^ (i4 << 5);
        } else {
            i2 = b(j.getAndAdd(-4942790177534073029L));
            if (i2 == 0) {
                i2 = 1;
            }
        }
        c(i2);
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TLRandom.java */
    /* loaded from: classes5.dex */
    public static final class a {
        long a;
        int b;
        int c;

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long e() {
        return h.get().a;
    }

    private static void c(long j2) {
        h.get().a = j2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int f() {
        return h.get().b;
    }

    private static void b(int i2) {
        h.get().b = i2;
    }

    private static int g() {
        return h.get().c;
    }

    private static void c(int i2) {
        h.get().c = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void a(Thread thread) {
        if (!c) {
            a.putObject(thread, d, (Object) null);
            a.putObject(thread, e, (Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void a(Thread thread, AccessControlContext accessControlContext) {
        if (!c) {
            a.putOrderedObject(thread, f, accessControlContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void a(Thread thread, ClassLoader classLoader) {
        if (!c) {
            a.putObject(thread, g, classLoader);
        }
    }

    private static boolean h() {
        if (a("android.util.DisplayMetrics")) {
            return true;
        }
        return a("org.robovm.rt.bro.Bro");
    }

    private static boolean i() {
        String property;
        return a("com.ibm.misc.JarVersion") && (property = System.getProperty("java.class.version", "45")) != null && property.length() >= 2 && "52".compareTo(property.substring(0, 2)) > 0;
    }

    private static boolean a(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str, false, c.class.getClassLoader());
        } catch (Throwable unused) {
            cls = null;
        }
        return cls != null;
    }

    static {
        try {
            b = i();
            c = h();
            if (!c) {
                d = a.objectFieldOffset(Thread.class.getDeclaredField("threadLocals"));
                e = a.objectFieldOffset(Thread.class.getDeclaredField("inheritableThreadLocals"));
                f = a.objectFieldOffset(Thread.class.getDeclaredField(b ? "accessControlContext" : "inheritedAccessControlContext"));
                g = a.objectFieldOffset(Thread.class.getDeclaredField("contextClassLoader"));
            } else {
                d = 0L;
                e = 0L;
                f = 0L;
                g = 0L;
            }
            h = new ThreadLocal<a>() { // from class: java8.util.concurrent.c.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public a initialValue() {
                    return new a();
                }
            };
            i = new AtomicInteger();
            j = new AtomicLong(a(System.currentTimeMillis()) ^ a(System.nanoTime()));
            if (((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() { // from class: java8.util.concurrent.c.2
                /* renamed from: a */
                public Boolean run() {
                    return Boolean.valueOf(Boolean.getBoolean("java.util.secureRandomSeed"));
                }
            })).booleanValue()) {
                byte[] seed = SecureRandom.getSeed(8);
                long j2 = seed[0] & 255;
                for (int i2 = 1; i2 < 8; i2++) {
                    j2 = (j2 << 8) | (seed[i2] & 255);
                }
                j.set(j2);
            }
        } catch (Exception e2) {
            throw new ExceptionInInitializerError(e2);
        }
    }
}
