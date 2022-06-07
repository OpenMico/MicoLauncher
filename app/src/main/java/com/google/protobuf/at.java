package com.google.protobuf;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnsafeUtil.java */
/* loaded from: classes2.dex */
public final class at {
    static final boolean b;
    private static final Logger c = Logger.getLogger(at.class.getName());
    private static final Unsafe d = c();
    private static final Class<?> e = b.b();
    private static final boolean f = d(Long.TYPE);
    private static final boolean g = d(Integer.TYPE);
    private static final d h = d();
    private static final boolean i = f();
    private static final boolean j = e();
    static final long a = b(byte[].class);
    private static final long k = b(boolean[].class);
    private static final long l = c(boolean[].class);
    private static final long m = b(int[].class);
    private static final long n = c(int[].class);
    private static final long o = b(long[].class);
    private static final long p = c(long[].class);
    private static final long q = b(float[].class);
    private static final long r = c(float[].class);
    private static final long s = b(double[].class);
    private static final long t = c(double[].class);
    private static final long u = b(Object[].class);
    private static final long v = c(Object[].class);
    private static final long w = b(g());
    private static final int x = (int) (a & 7);

    static {
        b = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private at() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        return j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b() {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T a(Class<T> cls) {
        try {
            return (T) d.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(Field field) {
        return h.a(field);
    }

    private static int b(Class<?> cls) {
        if (j) {
            return h.a(cls);
        }
        return -1;
    }

    private static int c(Class<?> cls) {
        if (j) {
            return h.b(cls);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(Object obj, long j2) {
        return h.e(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, int i2) {
        h.a(obj, j2, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long b(Object obj, long j2) {
        return h.f(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, long j3) {
        h.a(obj, j2, j3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean c(Object obj, long j2) {
        return h.b(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, boolean z) {
        h.a(obj, j2, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static float d(Object obj, long j2) {
        return h.c(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, float f2) {
        h.a(obj, j2, f2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double e(Object obj, long j2) {
        return h.d(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, double d2) {
        h.a(obj, j2, d2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object f(Object obj, long j2) {
        return h.g(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Object obj, long j2, Object obj2) {
        h.a(obj, j2, obj2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte a(byte[] bArr, long j2) {
        return h.a(bArr, a + j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte[] bArr, long j2, byte b2) {
        h.a((Object) bArr, a + j2, b2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte[] bArr, long j2, long j3, long j4) {
        h.a(bArr, j2, j3, j4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(long j2, byte[] bArr, long j3, long j4) {
        h.a(j2, bArr, j3, j4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte a(long j2) {
        return h.a(j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(long j2, byte b2) {
        h.a(j2, b2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long b(long j2) {
        return h.b(j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(ByteBuffer byteBuffer) {
        return h.f(byteBuffer, w);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Unsafe c() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.protobuf.at.1
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
                    return null;
                }
            });
        } catch (Throwable unused) {
            return null;
        }
    }

    private static d d() {
        if (d == null) {
            return null;
        }
        if (!b.a()) {
            return new c(d);
        }
        if (f) {
            return new b(d);
        }
        if (g) {
            return new a(d);
        }
        return null;
    }

    private static boolean e() {
        Unsafe unsafe = d;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (b.a()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger = c;
            Level level = Level.WARNING;
            logger.log(level, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    private static boolean f() {
        Unsafe unsafe = d;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (g() == null) {
                return false;
            }
            if (b.a()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger = c;
            Level level = Level.WARNING;
            logger.log(level, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    private static boolean d(Class<?> cls) {
        if (!b.a()) {
            return false;
        }
        try {
            Class<?> cls2 = e;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Field g() {
        Field a2;
        if (b.a() && (a2 = a(Buffer.class, "effectiveDirectAddress")) != null) {
            return a2;
        }
        Field a3 = a(Buffer.class, "address");
        if (a3 == null || a3.getType() != Long.TYPE) {
            return null;
        }
        return a3;
    }

    private static long b(Field field) {
        d dVar;
        if (field == null || (dVar = h) == null) {
            return -1L;
        }
        return dVar.a(field);
    }

    private static Field a(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UnsafeUtil.java */
    /* loaded from: classes2.dex */
    public static abstract class d {
        Unsafe a;

        public abstract byte a(long j);

        public abstract byte a(Object obj, long j);

        public abstract void a(long j, byte b);

        public abstract void a(long j, byte[] bArr, long j2, long j3);

        public abstract void a(Object obj, long j, byte b);

        public abstract void a(Object obj, long j, double d);

        public abstract void a(Object obj, long j, float f);

        public abstract void a(Object obj, long j, boolean z);

        public abstract void a(byte[] bArr, long j, long j2, long j3);

        public abstract long b(long j);

        public abstract boolean b(Object obj, long j);

        public abstract float c(Object obj, long j);

        public abstract double d(Object obj, long j);

        d(Unsafe unsafe) {
            this.a = unsafe;
        }

        public final long a(Field field) {
            return this.a.objectFieldOffset(field);
        }

        public final int e(Object obj, long j) {
            return this.a.getInt(obj, j);
        }

        public final void a(Object obj, long j, int i) {
            this.a.putInt(obj, j, i);
        }

        public final long f(Object obj, long j) {
            return this.a.getLong(obj, j);
        }

        public final void a(Object obj, long j, long j2) {
            this.a.putLong(obj, j, j2);
        }

        public final Object g(Object obj, long j) {
            return this.a.getObject(obj, j);
        }

        public final void a(Object obj, long j, Object obj2) {
            this.a.putObject(obj, j, obj2);
        }

        public final int a(Class<?> cls) {
            return this.a.arrayBaseOffset(cls);
        }

        public final int b(Class<?> cls) {
            return this.a.arrayIndexScale(cls);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UnsafeUtil.java */
    /* loaded from: classes2.dex */
    public static final class c extends d {
        c(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.at.d
        public byte a(long j) {
            return this.a.getByte(j);
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte b) {
            this.a.putByte(j, b);
        }

        @Override // com.google.protobuf.at.d
        public long b(long j) {
            return this.a.getLong(j);
        }

        @Override // com.google.protobuf.at.d
        public byte a(Object obj, long j) {
            return this.a.getByte(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, byte b) {
            this.a.putByte(obj, j, b);
        }

        @Override // com.google.protobuf.at.d
        public boolean b(Object obj, long j) {
            return this.a.getBoolean(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, boolean z) {
            this.a.putBoolean(obj, j, z);
        }

        @Override // com.google.protobuf.at.d
        public float c(Object obj, long j) {
            return this.a.getFloat(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, float f) {
            this.a.putFloat(obj, j, f);
        }

        @Override // com.google.protobuf.at.d
        public double d(Object obj, long j) {
            return this.a.getDouble(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, double d) {
            this.a.putDouble(obj, j, d);
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte[] bArr, long j2, long j3) {
            this.a.copyMemory((Object) null, j, bArr, at.a + j2, j3);
        }

        @Override // com.google.protobuf.at.d
        public void a(byte[] bArr, long j, long j2, long j3) {
            this.a.copyMemory(bArr, at.a + j, (Object) null, j2, j3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UnsafeUtil.java */
    /* loaded from: classes2.dex */
    public static final class b extends d {
        b(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.at.d
        public byte a(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte b) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public long b(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public byte a(Object obj, long j) {
            return at.b ? at.k(obj, j) : at.l(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, byte b) {
            if (at.b) {
                at.c(obj, j, b);
            } else {
                at.d(obj, j, b);
            }
        }

        @Override // com.google.protobuf.at.d
        public boolean b(Object obj, long j) {
            return at.b ? at.m(obj, j) : at.n(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, boolean z) {
            if (at.b) {
                at.d(obj, j, z);
            } else {
                at.e(obj, j, z);
            }
        }

        @Override // com.google.protobuf.at.d
        public float c(Object obj, long j) {
            return Float.intBitsToFloat(e(obj, j));
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, float f) {
            a(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.protobuf.at.d
        public double d(Object obj, long j) {
            return Double.longBitsToDouble(f(obj, j));
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, double d) {
            a(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte[] bArr, long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public void a(byte[] bArr, long j, long j2, long j3) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UnsafeUtil.java */
    /* loaded from: classes2.dex */
    public static final class a extends d {
        a(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.at.d
        public byte a(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte b) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public long b(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public byte a(Object obj, long j) {
            return at.b ? at.k(obj, j) : at.l(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, byte b) {
            if (at.b) {
                at.c(obj, j, b);
            } else {
                at.d(obj, j, b);
            }
        }

        @Override // com.google.protobuf.at.d
        public boolean b(Object obj, long j) {
            return at.b ? at.m(obj, j) : at.n(obj, j);
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, boolean z) {
            if (at.b) {
                at.d(obj, j, z);
            } else {
                at.e(obj, j, z);
            }
        }

        @Override // com.google.protobuf.at.d
        public float c(Object obj, long j) {
            return Float.intBitsToFloat(e(obj, j));
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, float f) {
            a(obj, j, Float.floatToIntBits(f));
        }

        @Override // com.google.protobuf.at.d
        public double d(Object obj, long j) {
            return Double.longBitsToDouble(f(obj, j));
        }

        @Override // com.google.protobuf.at.d
        public void a(Object obj, long j, double d) {
            a(obj, j, Double.doubleToLongBits(d));
        }

        @Override // com.google.protobuf.at.d
        public void a(long j, byte[] bArr, long j2, long j3) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.protobuf.at.d
        public void a(byte[] bArr, long j, long j2, long j3) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte k(Object obj, long j2) {
        return (byte) ((a(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte l(Object obj, long j2) {
        return (byte) ((a(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int a2 = a(obj, j3);
        int i2 = ((~((int) j2)) & 3) << 3;
        a(obj, j3, ((255 & b2) << i2) | (a2 & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Object obj, long j2, byte b2) {
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        a(obj, j3, ((255 & b2) << i2) | (a(obj, j3) & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean m(Object obj, long j2) {
        return k(obj, j2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean n(Object obj, long j2) {
        return l(obj, j2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Object obj, long j2, boolean z) {
        c(obj, j2, z ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(Object obj, long j2, boolean z) {
        d(obj, j2, z ? (byte) 1 : (byte) 0);
    }
}
