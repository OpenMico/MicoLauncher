package io.netty.util.internal;

import com.google.common.base.Ascii;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: PlatformDependent0.java */
/* loaded from: classes4.dex */
public final class w {
    static final Unsafe a;
    private static final InternalLogger b = InternalLoggerFactory.getInstance(w.class);
    private static final long c;
    private static final long d;
    private static final long e;
    private static final long f;
    private static final long g;
    private static final long h;
    private static final Constructor<?> i;
    private static final boolean j;

    public static int a(byte b2) {
        return b2 & Ascii.US;
    }

    public static int a(char c2) {
        return c2 & 31;
    }

    public static int a(short s) {
        return s & 7967;
    }

    public static int b(int i2) {
        return i2 & 522133279;
    }

    private static int b(short s) {
        return s & 31;
    }

    private static int c(int i2) {
        return (i2 & 31) | ((2031616 & i2) >>> 8);
    }

    private static int g(long j2) {
        return (int) ((j2 & 31) | ((8725724278030336L & j2) >>> 24) | ((133143986176L & j2) >>> 16) | ((2031616 & j2) >>> 8));
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01ee  */
    static {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.w.<clinit>():void");
    }

    public static boolean a() {
        return j;
    }

    public static boolean b() {
        return a != null;
    }

    public static boolean c() {
        return j;
    }

    public static void a(Throwable th) {
        a.throwException((Throwable) ObjectUtil.checkNotNull(th, "cause"));
    }

    public static boolean d() {
        return i != null;
    }

    public static ByteBuffer a(ByteBuffer byteBuffer, int i2) {
        return c(a.reallocateMemory(b(byteBuffer), i2), i2);
    }

    public static ByteBuffer a(int i2) {
        return c(a.allocateMemory(i2), i2);
    }

    private static ByteBuffer c(long j2, int i2) {
        try {
            return (ByteBuffer) i.newInstance(Long.valueOf(j2), Integer.valueOf(i2));
        } catch (Throwable th) {
            if (th instanceof Error) {
                throw ((Error) th);
            }
            throw new Error(th);
        }
    }

    public static void a(ByteBuffer byteBuffer) {
        g.a(byteBuffer);
    }

    public static long b(ByteBuffer byteBuffer) {
        return d(byteBuffer, c);
    }

    public static long e() {
        return d;
    }

    public static Object a(Object obj, long j2) {
        return a.getObject(obj, j2);
    }

    public static Object b(Object obj, long j2) {
        return a.getObjectVolatile(obj, j2);
    }

    public static int c(Object obj, long j2) {
        return a.getInt(obj, j2);
    }

    private static long d(Object obj, long j2) {
        return a.getLong(obj, j2);
    }

    public static long a(Field field) {
        return a.objectFieldOffset(field);
    }

    public static byte a(long j2) {
        return a.getByte(j2);
    }

    public static short b(long j2) {
        return a.getShort(j2);
    }

    public static int c(long j2) {
        return a.getInt(j2);
    }

    public static long d(long j2) {
        return a.getLong(j2);
    }

    public static byte a(byte[] bArr, int i2) {
        return a.getByte(bArr, d + i2);
    }

    public static short b(byte[] bArr, int i2) {
        return a.getShort(bArr, d + i2);
    }

    public static int c(byte[] bArr, int i2) {
        return a.getInt(bArr, d + i2);
    }

    public static long d(byte[] bArr, int i2) {
        return a.getLong(bArr, d + i2);
    }

    public static void a(Object obj, long j2, Object obj2) {
        a.putOrderedObject(obj, j2, obj2);
    }

    public static void a(long j2, byte b2) {
        a.putByte(j2, b2);
    }

    public static void a(long j2, short s) {
        a.putShort(j2, s);
    }

    public static void a(long j2, int i2) {
        a.putInt(j2, i2);
    }

    public static void a(long j2, long j3) {
        a.putLong(j2, j3);
    }

    public static void a(byte[] bArr, int i2, byte b2) {
        a.putByte(bArr, d + i2, b2);
    }

    public static void a(byte[] bArr, int i2, short s) {
        a.putShort(bArr, d + i2, s);
    }

    public static void a(byte[] bArr, int i2, int i3) {
        a.putInt(bArr, d + i2, i3);
    }

    public static void a(byte[] bArr, int i2, long j2) {
        a.putLong(bArr, d + i2, j2);
    }

    public static void a(long j2, long j3, long j4) {
        while (j4 > 0) {
            long min = Math.min(j4, 1048576L);
            a.copyMemory(j2, j3, min);
            j4 -= min;
            j2 += min;
            j3 += min;
        }
    }

    public static void a(Object obj, long j2, Object obj2, long j3, long j4) {
        long j5 = j2;
        long j6 = j3;
        long j7 = j4;
        while (j7 > 0) {
            long min = Math.min(j7, 1048576L);
            a.copyMemory(obj, j5, obj2, j6, min);
            j7 -= min;
            j5 += min;
            j6 += min;
        }
    }

    public static void a(long j2, long j3, byte b2) {
        a.setMemory(j2, j3, b2);
    }

    public static void a(Object obj, long j2, long j3, byte b2) {
        a.setMemory(obj, j2, j3, b2);
    }

    public static boolean a(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        if (i4 == 0) {
            return true;
        }
        long j2 = d;
        long j3 = i2 + j2;
        long j4 = j2 + i3;
        int i5 = i4 & 7;
        long j5 = i5 + j3;
        long j6 = i4;
        long j7 = (j3 - 8) + j6;
        long j8 = (j4 - 8) + j6;
        while (j7 >= j5) {
            if (a.getLong(bArr, j7) != a.getLong(bArr2, j8)) {
                return false;
            }
            j7 -= 8;
            j8 -= 8;
        }
        switch (i5) {
            case 1:
                return a.getByte(bArr, j3) == a.getByte(bArr2, j4);
            case 2:
                return a.getChar(bArr, j3) == a.getChar(bArr2, j4);
            case 3:
                return a.getChar(bArr, j3 + 1) == a.getChar(bArr2, 1 + j4) && a.getByte(bArr, j3) == a.getByte(bArr2, j4);
            case 4:
                return a.getInt(bArr, j3) == a.getInt(bArr2, j4);
            case 5:
                return a.getInt(bArr, j3 + 1) == a.getInt(bArr2, 1 + j4) && a.getByte(bArr, j3) == a.getByte(bArr2, j4);
            case 6:
                return a.getInt(bArr, j3 + 2) == a.getInt(bArr2, 2 + j4) && a.getChar(bArr, j3) == a.getChar(bArr2, j4);
            case 7:
                return a.getInt(bArr, j3 + 3) == a.getInt(bArr2, 3 + j4) && a.getChar(bArr, j3 + 1) == a.getChar(bArr2, 1 + j4) && a.getByte(bArr, j3) == a.getByte(bArr2, j4);
            default:
                return true;
        }
    }

    public static int a(byte[] bArr) {
        return b(bArr, 0, bArr.length);
    }

    public static int b(byte[] bArr, int i2, int i3) {
        long j2 = d + i2;
        int i4 = i3 & 7;
        int i5 = -1028477387;
        if (i3 > 7) {
            long j3 = i4 + j2;
            for (long j4 = (j2 - 8) + i3; j4 >= j3; j4 -= 8) {
                i5 = b(a.getLong(bArr, j4), i5);
            }
        }
        switch (i4) {
            case 1:
                return (i5 * 31) + a(a.getByte(bArr, j2));
            case 2:
                return (i5 * 31) + a(a.getShort(bArr, j2));
            case 3:
                return (((i5 * 31) + a(a.getShort(bArr, 1 + j2))) * 31) + a(a.getByte(bArr, j2));
            case 4:
                return (i5 * 31) + b(a.getInt(bArr, j2));
            case 5:
                return (((i5 * 31) + Integer.rotateLeft(b(a.getInt(bArr, 1 + j2)), 13)) * 31) + a(a.getByte(bArr, j2));
            case 6:
                return (((i5 * 31) + Integer.rotateLeft(b(a.getInt(bArr, 2 + j2)), 13)) * 31) + a(a.getShort(bArr, j2));
            case 7:
                return (((((i5 * 31) + Integer.rotateLeft(b(a.getInt(bArr, 3 + j2)), 13)) * 31) + a(a.getShort(bArr, 1 + j2))) * 31) + a(a.getByte(bArr, j2));
            default:
                return i5;
        }
    }

    public static int a(char[] cArr) {
        int length = cArr.length & 7;
        int i2 = -1028477387;
        for (int length2 = cArr.length - 8; length2 >= length; length2 -= 8) {
            i2 = a(a.getLong(cArr, e + (length2 * f)), a.getLong(cArr, e + ((length2 + 4) * f)), i2);
        }
        switch (length) {
            case 1:
                return (i2 * 31) + b(a.getShort(cArr, e));
            case 2:
                return (i2 * 31) + c(a.getInt(cArr, e));
            case 3:
                return (((i2 * 31) + c(a.getInt(cArr, e + f))) * 31) + b(a.getShort(cArr, e));
            case 4:
                return (i2 * 31) + g(a.getLong(cArr, e));
            case 5:
                return (((i2 * 31) + Integer.rotateLeft(g(a.getLong(cArr, e + f)), 13)) * 31) + b(a.getShort(cArr, e));
            case 6:
                return (((i2 * 31) + Integer.rotateLeft(g(a.getLong(cArr, e + (f * 2))), 13)) * 31) + c(a.getInt(cArr, e));
            case 7:
                return (((((i2 * 31) + Integer.rotateLeft(g(a.getLong(cArr, e + (f * 3))), 13)) * 31) + c(a.getInt(cArr, e + f))) * 31) + b(a.getShort(cArr, e));
            default:
                return i2;
        }
    }

    public static boolean a(CharSequence charSequence) {
        return g != -1 && charSequence.getClass() == String.class;
    }

    public static boolean b(CharSequence charSequence) {
        return h != -1 && charSequence.getClass() == String.class;
    }

    public static char[] c(CharSequence charSequence) {
        return (char[]) a.getObject(charSequence, g);
    }

    public static byte[] d(CharSequence charSequence) {
        return (byte[]) a.getObject(charSequence, h);
    }

    public static int b(long j2, int i2) {
        return (((i2 * 31) + ((int) ((2242545357458243584L & j2) >>> 32))) * 31) + b((int) j2);
    }

    static int a(long j2, long j3, int i2) {
        return (((i2 * 31) + g(j3)) * 31) + g(j2);
    }

    public static <U, W> AtomicReferenceFieldUpdater<U, W> a(Class<? super U> cls, String str) throws Exception {
        return new aa(a, cls, str);
    }

    public static <T> AtomicIntegerFieldUpdater<T> b(Class<? super T> cls, String str) throws Exception {
        return new y(a, cls, str);
    }

    public static <T> AtomicLongFieldUpdater<T> c(Class<? super T> cls, String str) throws Exception {
        return new z(a, cls, str);
    }

    public static ClassLoader a(final Class<?> cls) {
        if (System.getSecurityManager() == null) {
            return cls.getClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: io.netty.util.internal.w.2
            /* renamed from: a */
            public ClassLoader run() {
                return cls.getClassLoader();
            }
        });
    }

    public static ClassLoader f() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: io.netty.util.internal.w.3
            /* renamed from: a */
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    public static ClassLoader g() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        }
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: io.netty.util.internal.w.4
            /* renamed from: a */
            public ClassLoader run() {
                return ClassLoader.getSystemClassLoader();
            }
        });
    }

    public static int h() {
        return a.addressSize();
    }

    public static long e(long j2) {
        return a.allocateMemory(j2);
    }

    public static void f(long j2) {
        a.freeMemory(j2);
    }

    private w() {
    }
}
