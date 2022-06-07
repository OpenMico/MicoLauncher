package com.google.common.hash;

import com.google.common.primitives.Longs;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

/* compiled from: LittleEndianByteArray.java */
/* loaded from: classes2.dex */
final class l {
    static final /* synthetic */ boolean a = !l.class.desiredAssertionStatus();
    private static final b b;

    /* compiled from: LittleEndianByteArray.java */
    /* loaded from: classes2.dex */
    private enum a implements b {
        INSTANCE {
            @Override // com.google.common.hash.l.b
            public long a(byte[] bArr, int i) {
                return Longs.fromBytes(bArr[i + 7], bArr[i + 6], bArr[i + 5], bArr[i + 4], bArr[i + 3], bArr[i + 2], bArr[i + 1], bArr[i]);
            }
        }
    }

    /* compiled from: LittleEndianByteArray.java */
    /* loaded from: classes2.dex */
    private interface b {
        long a(byte[] bArr, int i);
    }

    static {
        b bVar = a.INSTANCE;
        try {
            if ("amd64".equals(System.getProperty("os.arch"))) {
                bVar = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? c.UNSAFE_LITTLE_ENDIAN : c.UNSAFE_BIG_ENDIAN;
            }
        } catch (Throwable unused) {
        }
        b = bVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(byte[] bArr, int i) {
        if (a || bArr.length >= i + 8) {
            return b.a(bArr, i);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    /* compiled from: LittleEndianByteArray.java */
    /* loaded from: classes2.dex */
    private enum c implements b {
        UNSAFE_LITTLE_ENDIAN {
            @Override // com.google.common.hash.l.b
            public long a(byte[] bArr, int i) {
                return c.c.getLong(bArr, i + c.d);
            }
        },
        UNSAFE_BIG_ENDIAN {
            @Override // com.google.common.hash.l.b
            public long a(byte[] bArr, int i) {
                return Long.reverseBytes(c.c.getLong(bArr, i + c.d));
            }
        };
        
        private static final Unsafe c = c();
        private static final int d = c.arrayBaseOffset(byte[].class);

        static {
            if (c.arrayIndexScale(byte[].class) != 1) {
                throw new AssertionError();
            }
        }

        private static Unsafe c() {
            try {
                try {
                    return Unsafe.getUnsafe();
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            } catch (SecurityException unused) {
                return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.hash.l.c.3
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

    private l() {
    }
}
