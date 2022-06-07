package io.netty.util.internal.chmv8;

import com.xiaomi.onetrack.api.b;
import io.realm.BuildConfig;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import sun.misc.Unsafe;

/* compiled from: Striped64.java */
/* loaded from: classes4.dex */
abstract class a extends Number {
    static final ThreadLocal<int[]> a = new ThreadLocal<>();
    static final Random b = new Random();
    static final int c = Runtime.getRuntime().availableProcessors();
    private static final Unsafe g;
    private static final long h;
    private static final long i;
    volatile transient C0210a[] d;
    volatile transient long e;
    volatile transient int f;

    abstract long a(long j, long j2);

    /* compiled from: Striped64.java */
    /* renamed from: io.netty.util.internal.chmv8.a$a  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    static final class C0210a {
        private static final Unsafe b;
        private static final long c;
        volatile long a;

        C0210a(long j) {
            this.a = j;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final boolean a(long j, long j2) {
            return b.compareAndSwapLong(this, c, j, j2);
        }

        static {
            try {
                b = a.c();
                c = b.objectFieldOffset(C0210a.class.getDeclaredField(b.p));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    static {
        try {
            g = c();
            h = g.objectFieldOffset(a.class.getDeclaredField(BuildConfig.FLAVOR));
            i = g.objectFieldOffset(a.class.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    final boolean b(long j, long j2) {
        return g.compareAndSwapLong(this, h, j, j2);
    }

    final boolean a() {
        return g.compareAndSwapInt(this, i, 0, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0022 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00ee A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void a(long r17, int[] r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.a.a(long, int[], boolean):void");
    }

    final void a(long j) {
        C0210a[] aVarArr = this.d;
        this.e = j;
        if (aVarArr != null) {
            for (C0210a aVar : aVarArr) {
                if (aVar != null) {
                    aVar.a = j;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe c() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: io.netty.util.internal.chmv8.a.1
                /* renamed from: a */
                public Unsafe run() throws Exception {
                    Field[] declaredFields = Unsafe.class.getDeclaredFields();
                    for (Field field : declaredFields) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            return (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    throw new NoSuchFieldError("the Unsafe");
                }
            });
        }
    }
}
