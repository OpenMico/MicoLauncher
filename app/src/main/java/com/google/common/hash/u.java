package com.google.common.hash;

import com.google.common.annotations.GwtIncompatible;
import com.xiaomi.onetrack.api.b;
import io.realm.BuildConfig;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

/* compiled from: Striped64.java */
@GwtIncompatible
/* loaded from: classes2.dex */
abstract class u extends Number {
    static final ThreadLocal<int[]> a = new ThreadLocal<>();
    static final Random b = new Random();
    static final int c = Runtime.getRuntime().availableProcessors();
    private static final Unsafe g;
    private static final long h;
    private static final long i;
    @NullableDecl
    volatile transient a[] d;
    volatile transient long e;
    volatile transient int f;

    abstract long a(long j, long j2);

    /* compiled from: Striped64.java */
    /* loaded from: classes2.dex */
    static final class a {
        private static final Unsafe b;
        private static final long c;
        volatile long a;

        a(long j) {
            this.a = j;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final boolean a(long j, long j2) {
            return b.compareAndSwapLong(this, c, j, j2);
        }

        static {
            try {
                b = u.a();
                c = b.objectFieldOffset(a.class.getDeclaredField(b.p));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    static {
        try {
            g = a();
            h = g.objectFieldOffset(u.class.getDeclaredField(BuildConfig.FLAVOR));
            i = g.objectFieldOffset(u.class.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    final boolean b(long j, long j2) {
        return g.compareAndSwapLong(this, h, j, j2);
    }

    final boolean c() {
        return g.compareAndSwapInt(this, i, 0, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0022 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00ee A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void a(long r17, @org.checkerframework.checker.nullness.compatqual.NullableDecl int[] r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.u.a(long, int[], boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe a() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.hash.u.1
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
