package io.netty.util.internal;

import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
import io.netty.util.internal.chmv8.LongAdderV8;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Deque;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class PlatformDependent {
    public static final boolean BIG_ENDIAN_NATIVE_ORDER;
    private static volatile Boolean f;
    private static final boolean j;
    private static final boolean k;
    private static final long l;
    private static final long m;
    private static final boolean n;
    private static final File o;
    private static final int p;
    private static final int q;
    private static final boolean r;
    private static final AtomicLong s;
    private static final long t;
    static final /* synthetic */ boolean a = !PlatformDependent.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(PlatformDependent.class);
    private static final Pattern c = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
    private static final boolean d = a();
    private static final boolean e = b();
    private static final int g = d();
    private static final boolean h = !isAndroid();
    private static final boolean i = e();

    static {
        j = i && g < 8;
        k = i && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false);
        l = f();
        m = w.e();
        n = g();
        o = h();
        p = i();
        q = j();
        BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        if (b.isDebugEnabled()) {
            b.debug("-Dio.netty.noPreferDirect: {}", Boolean.valueOf(!k));
        }
        if (!hasUnsafe() && !isAndroid()) {
            b.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system unstability.");
        }
        long j2 = SystemPropertyUtil.getLong("io.netty.maxDirectMemory", -1L);
        int i2 = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
        if (i2 == 0 || !hasUnsafe() || !w.d()) {
            r = false;
            s = null;
        } else {
            r = true;
            if (i2 < 0) {
                j2 = f();
                if (j2 <= 0) {
                    s = null;
                } else {
                    s = new AtomicLong();
                }
            } else {
                s = new AtomicLong();
            }
        }
        t = j2;
        b.debug("io.netty.maxDirectMemory: {} bytes", Long.valueOf(j2));
    }

    public static boolean isAndroid() {
        return d;
    }

    public static boolean isWindows() {
        return e;
    }

    public static boolean isRoot() {
        if (f == null) {
            synchronized (PlatformDependent.class) {
                if (f == null) {
                    f = Boolean.valueOf(c());
                }
            }
        }
        return f.booleanValue();
    }

    public static int javaVersion() {
        return g;
    }

    public static boolean canEnableTcpNoDelayByDefault() {
        return h;
    }

    public static boolean hasUnsafe() {
        return i;
    }

    public static boolean isUnaligned() {
        return w.a();
    }

    public static boolean directBufferPreferred() {
        return k;
    }

    public static long maxDirectMemory() {
        return l;
    }

    public static boolean hasJavassist() {
        return n;
    }

    public static File tmpdir() {
        return o;
    }

    public static int bitMode() {
        return p;
    }

    public static int addressSize() {
        return q;
    }

    public static long allocateMemory(long j2) {
        return w.e(j2);
    }

    public static void freeMemory(long j2) {
        w.f(j2);
    }

    public static void throwException(Throwable th) {
        if (hasUnsafe()) {
            w.a(th);
        } else {
            a(th);
        }
    }

    private static <E extends Throwable> void a(Throwable th) throws Throwable {
        throw th;
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        if (j) {
            return new ConcurrentHashMapV8();
        }
        return new ConcurrentHashMap();
    }

    public static LongCounter newLongCounter() {
        if (i) {
            return new LongAdderV8();
        }
        return new a();
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i2) {
        if (j) {
            return new ConcurrentHashMapV8(i2);
        }
        return new ConcurrentHashMap(i2);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i2, float f2) {
        if (j) {
            return new ConcurrentHashMapV8(i2, f2);
        }
        return new ConcurrentHashMap(i2, f2);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int i2, float f2, int i3) {
        if (j) {
            return new ConcurrentHashMapV8(i2, f2, i3);
        }
        return new ConcurrentHashMap(i2, f2, i3);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
        if (j) {
            return new ConcurrentHashMapV8(map);
        }
        return new ConcurrentHashMap(map);
    }

    public static void freeDirectBuffer(ByteBuffer byteBuffer) {
        if (hasUnsafe() && !isAndroid()) {
            w.a(byteBuffer);
        }
    }

    public static long directBufferAddress(ByteBuffer byteBuffer) {
        return w.b(byteBuffer);
    }

    public static Object getObject(Object obj, long j2) {
        return w.a(obj, j2);
    }

    public static Object getObjectVolatile(Object obj, long j2) {
        return w.b(obj, j2);
    }

    public static int getInt(Object obj, long j2) {
        return w.c(obj, j2);
    }

    public static long objectFieldOffset(Field field) {
        return w.a(field);
    }

    public static byte getByte(long j2) {
        return w.a(j2);
    }

    public static short getShort(long j2) {
        return w.b(j2);
    }

    public static int getInt(long j2) {
        return w.c(j2);
    }

    public static long getLong(long j2) {
        return w.d(j2);
    }

    public static byte getByte(byte[] bArr, int i2) {
        return w.a(bArr, i2);
    }

    public static short getShort(byte[] bArr, int i2) {
        return w.b(bArr, i2);
    }

    public static int getInt(byte[] bArr, int i2) {
        return w.c(bArr, i2);
    }

    public static long getLong(byte[] bArr, int i2) {
        return w.d(bArr, i2);
    }

    private static long a(byte[] bArr, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (bArr[i2 + 7] & 255) | (bArr[i2] << 56) | ((bArr[i2 + 1] & 255) << 48) | ((bArr[i2 + 2] & 255) << 40) | ((bArr[i2 + 3] & 255) << 32) | ((bArr[i2 + 4] & 255) << 24) | ((bArr[i2 + 5] & 255) << 16) | ((bArr[i2 + 6] & 255) << 8);
        }
        long j2 = (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8);
        return ((bArr[i2 + 7] & 255) << 56) | ((bArr[i2 + 2] & 255) << 16) | j2 | ((bArr[i2 + 3] & 255) << 24) | ((bArr[i2 + 4] & 255) << 32) | ((bArr[i2 + 5] & 255) << 40) | ((bArr[i2 + 6] & 255) << 48);
    }

    private static long a(CharSequence charSequence, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (charSequence.charAt(i2 + 7) & 255) | (charSequence.charAt(i2) << 56) | ((charSequence.charAt(i2 + 1) & 255) << 48) | ((charSequence.charAt(i2 + 2) & 255) << 40) | ((charSequence.charAt(i2 + 3) & 255) << 32) | ((charSequence.charAt(i2 + 4) & 255) << 24) | ((charSequence.charAt(i2 + 5) & 255) << 16) | ((charSequence.charAt(i2 + 6) & 255) << 8);
        }
        long charAt = (charSequence.charAt(i2) & 255) | ((charSequence.charAt(i2 + 1) & 255) << 8);
        return ((charSequence.charAt(i2 + 7) & 255) << 56) | ((charSequence.charAt(i2 + 2) & 255) << 16) | charAt | ((charSequence.charAt(i2 + 3) & 255) << 24) | ((charSequence.charAt(i2 + 4) & 255) << 32) | ((charSequence.charAt(i2 + 5) & 255) << 40) | ((charSequence.charAt(i2 + 6) & 255) << 48);
    }

    private static int b(byte[] bArr, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (bArr[i2 + 3] & 255) | (bArr[i2] << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
        }
        return (bArr[i2 + 3] << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    private static int b(CharSequence charSequence, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (charSequence.charAt(i2 + 3) & 255) | (charSequence.charAt(i2) << 24) | ((charSequence.charAt(i2 + 1) & 255) << 16) | ((charSequence.charAt(i2 + 2) & 255) << 8);
        }
        return (charSequence.charAt(i2 + 3) << 24) | (charSequence.charAt(i2) & 255) | ((charSequence.charAt(i2 + 1) & 255) << 8) | ((charSequence.charAt(i2 + 2) & 255) << 16);
    }

    private static short c(byte[] bArr, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (short) ((bArr[i2 + 1] & 255) | (bArr[i2] << 8));
        }
        return (short) ((bArr[i2 + 1] << 8) | (bArr[i2] & 255));
    }

    private static short c(CharSequence charSequence, int i2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (short) ((charSequence.charAt(i2 + 1) & 255) | (charSequence.charAt(i2) << '\b'));
        }
        return (short) ((charSequence.charAt(i2 + 1) << '\b') | (charSequence.charAt(i2) & 255));
    }

    public static void putOrderedObject(Object obj, long j2, Object obj2) {
        w.a(obj, j2, obj2);
    }

    public static void putByte(long j2, byte b2) {
        w.a(j2, b2);
    }

    public static void putShort(long j2, short s2) {
        w.a(j2, s2);
    }

    public static void putInt(long j2, int i2) {
        w.a(j2, i2);
    }

    public static void putLong(long j2, long j3) {
        w.a(j2, j3);
    }

    public static void putByte(byte[] bArr, int i2, byte b2) {
        w.a(bArr, i2, b2);
    }

    public static void putShort(byte[] bArr, int i2, short s2) {
        w.a(bArr, i2, s2);
    }

    public static void putInt(byte[] bArr, int i2, int i3) {
        w.a(bArr, i2, i3);
    }

    public static void putLong(byte[] bArr, int i2, long j2) {
        w.a(bArr, i2, j2);
    }

    public static void copyMemory(long j2, long j3, long j4) {
        w.a(j2, j3, j4);
    }

    public static void copyMemory(byte[] bArr, int i2, long j2, long j3) {
        w.a(bArr, m + i2, (Object) null, j2, j3);
    }

    public static void copyMemory(long j2, byte[] bArr, int i2, long j3) {
        w.a((Object) null, j2, bArr, m + i2, j3);
    }

    public static void setMemory(byte[] bArr, int i2, long j2, byte b2) {
        w.a(bArr, m + i2, j2, b2);
    }

    public static void setMemory(long j2, long j3, byte b2) {
        w.a(j2, j3, b2);
    }

    public static ByteBuffer allocateDirectNoCleaner(int i2) {
        if (a || r) {
            a(i2);
            try {
                return w.a(i2);
            } catch (Throwable th) {
                b(i2);
                throwException(th);
                return null;
            }
        } else {
            throw new AssertionError();
        }
    }

    public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer byteBuffer, int i2) {
        if (a || r) {
            int capacity = i2 - byteBuffer.capacity();
            a(capacity);
            try {
                return w.a(byteBuffer, i2);
            } catch (Throwable th) {
                b(capacity);
                throwException(th);
                return null;
            }
        } else {
            throw new AssertionError();
        }
    }

    public static void freeDirectNoCleaner(ByteBuffer byteBuffer) {
        if (a || r) {
            int capacity = byteBuffer.capacity();
            w.f(w.b(byteBuffer));
            b(capacity);
            return;
        }
        throw new AssertionError();
    }

    private static void a(int i2) {
        long j2;
        long j3;
        if (s != null) {
            do {
                j2 = s.get();
                j3 = i2 + j2;
                if (j3 > t) {
                    throw new OutOfDirectMemoryError("failed to allocate " + i2 + " byte(s) of direct memory (used: " + j2 + ", max: " + t + ')');
                }
            } while (!s.compareAndSet(j2, j3));
        }
    }

    private static void b(int i2) {
        AtomicLong atomicLong = s;
        if (atomicLong != null) {
            long addAndGet = atomicLong.addAndGet(-i2);
            if (!a && addAndGet < 0) {
                throw new AssertionError();
            }
        }
    }

    public static boolean useDirectBufferNoCleaner() {
        return r;
    }

    public static boolean equals(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        if (!hasUnsafe() || !w.c()) {
            return a(bArr, i2, bArr2, i3, i4);
        }
        return w.a(bArr, i2, bArr2, i3, i4);
    }

    public static int hashCodeAscii(byte[] bArr, int i2, int i3) {
        if (!hasUnsafe() || !w.c()) {
            return a(bArr, i2, i3);
        }
        return w.b(bArr, i2, i3);
    }

    public static int hashCodeAscii(CharSequence charSequence) {
        if (!hasUnsafe() || !w.c()) {
            return a(charSequence);
        }
        if (w.a(charSequence)) {
            return w.a(w.c(charSequence));
        }
        if (w.b(charSequence)) {
            return w.a(w.d(charSequence));
        }
        return a(charSequence);
    }

    public static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<? super U> cls, String str) {
        if (!hasUnsafe()) {
            return null;
        }
        try {
            return w.a(cls, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<? super T> cls, String str) {
        if (!hasUnsafe()) {
            return null;
        }
        try {
            return w.b(cls, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<? super T> cls, String str) {
        if (!hasUnsafe()) {
            return null;
        }
        try {
            return w.c(cls, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static <T> Queue<T> newMpscQueue() {
        return new r();
    }

    public static <T> Queue<T> newSpscQueue() {
        if (hasUnsafe()) {
            return new SpscLinkedQueue();
        }
        return new SpscLinkedAtomicQueue();
    }

    public static <T> Queue<T> newFixedMpscQueue(int i2) {
        if (hasUnsafe()) {
            return new k(i2);
        }
        return new LinkedBlockingQueue(i2);
    }

    public static ClassLoader getClassLoader(Class<?> cls) {
        return w.a(cls);
    }

    public static ClassLoader getContextClassLoader() {
        return w.f();
    }

    public static ClassLoader getSystemClassLoader() {
        return w.g();
    }

    public static <C> Deque<C> newConcurrentDeque() {
        if (javaVersion() < 7) {
            return new LinkedBlockingDeque();
        }
        return new ConcurrentLinkedDeque();
    }

    private static boolean a() {
        boolean z = false;
        try {
            Class.forName("android.app.Application", false, getSystemClassLoader());
            z = true;
        } catch (Throwable unused) {
        }
        if (z) {
            b.debug("Platform: Android");
        }
        return z;
    }

    private static boolean b() {
        boolean contains = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
        if (contains) {
            b.debug("Platform: Windows");
        }
        return contains;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
        if (r5 == null) goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0057, code lost:
        r5.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0076, code lost:
        if (r5 == null) goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0079, code lost:
        if (r6 == null) goto L_0x0093;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
        io.netty.util.internal.PlatformDependent.b.debug("UID: {}", r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0092, code lost:
        return "0".equals(r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean c() {
        /*
            Method dump skipped, instructions count: 264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.PlatformDependent.c():boolean");
    }

    private static int d() {
        int i2 = 6;
        if (!isAndroid()) {
            try {
                try {
                    Class.forName("java.time.Clock", false, getClassLoader(Object.class));
                    i2 = 8;
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                Class.forName("java.util.concurrent.LinkedTransferQueue", false, getClassLoader(BlockingQueue.class));
                i2 = 7;
            }
        }
        if (b.isDebugEnabled()) {
            b.debug("Java version: {}", Integer.valueOf(i2));
        }
        return i2;
    }

    private static boolean e() {
        boolean z;
        boolean z2 = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        b.debug("-Dio.netty.noUnsafe: {}", Boolean.valueOf(z2));
        if (isAndroid()) {
            b.debug("sun.misc.Unsafe: unavailable (Android)");
            return false;
        } else if (z2) {
            b.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            return false;
        } else {
            if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
                z = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
            } else {
                z = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
            }
            if (!z) {
                b.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
                return false;
            }
            try {
                boolean b2 = w.b();
                b.debug("sun.misc.Unsafe: {}", b2 ? "available" : "unavailable");
                return b2;
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0078, code lost:
        r5 = java.lang.Long.parseLong(r8.group(1));
        r0 = r8.group(2).charAt(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x008b, code lost:
        if (r0 == 'G') goto L_0x00ab;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x008f, code lost:
        if (r0 == 'K') goto L_0x00a7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0093, code lost:
        if (r0 == 'M') goto L_0x00a2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0097, code lost:
        if (r0 == 'g') goto L_0x00ab;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009b, code lost:
        if (r0 == 'k') goto L_0x00a7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x009f, code lost:
        if (r0 == 'm') goto L_0x00a2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a2, code lost:
        r5 = r5 * 1048576;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a7, code lost:
        r5 = r5 * android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ab, code lost:
        r5 = r5 * com.xiaomi.infra.galaxy.fds.Constants.DEFAULT_SPACE_LIMIT;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static long f() {
        /*
            r0 = 0
            r1 = 0
            r3 = 1
            r4 = 0
            java.lang.String r5 = "sun.misc.VM"
            java.lang.ClassLoader r6 = getSystemClassLoader()     // Catch: Throwable -> 0x0024
            java.lang.Class r5 = java.lang.Class.forName(r5, r3, r6)     // Catch: Throwable -> 0x0024
            java.lang.String r6 = "maxDirectMemory"
            java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch: Throwable -> 0x0024
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r6, r7)     // Catch: Throwable -> 0x0024
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch: Throwable -> 0x0024
            java.lang.Object r5 = r5.invoke(r0, r6)     // Catch: Throwable -> 0x0024
            java.lang.Number r5 = (java.lang.Number) r5     // Catch: Throwable -> 0x0024
            long r5 = r5.longValue()     // Catch: Throwable -> 0x0024
            goto L_0x0025
        L_0x0024:
            r5 = r1
        L_0x0025:
            int r7 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r7 <= 0) goto L_0x002a
            return r5
        L_0x002a:
            java.lang.String r7 = "java.lang.management.ManagementFactory"
            java.lang.ClassLoader r8 = getSystemClassLoader()     // Catch: Throwable -> 0x00af
            java.lang.Class r7 = java.lang.Class.forName(r7, r3, r8)     // Catch: Throwable -> 0x00af
            java.lang.String r8 = "java.lang.management.RuntimeMXBean"
            java.lang.ClassLoader r9 = getSystemClassLoader()     // Catch: Throwable -> 0x00af
            java.lang.Class r8 = java.lang.Class.forName(r8, r3, r9)     // Catch: Throwable -> 0x00af
            java.lang.String r9 = "getRuntimeMXBean"
            java.lang.Class[] r10 = new java.lang.Class[r4]     // Catch: Throwable -> 0x00af
            java.lang.reflect.Method r7 = r7.getDeclaredMethod(r9, r10)     // Catch: Throwable -> 0x00af
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch: Throwable -> 0x00af
            java.lang.Object r0 = r7.invoke(r0, r9)     // Catch: Throwable -> 0x00af
            java.lang.String r7 = "getInputArguments"
            java.lang.Class[] r9 = new java.lang.Class[r4]     // Catch: Throwable -> 0x00af
            java.lang.reflect.Method r7 = r8.getDeclaredMethod(r7, r9)     // Catch: Throwable -> 0x00af
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch: Throwable -> 0x00af
            java.lang.Object r0 = r7.invoke(r0, r8)     // Catch: Throwable -> 0x00af
            java.util.List r0 = (java.util.List) r0     // Catch: Throwable -> 0x00af
            int r7 = r0.size()     // Catch: Throwable -> 0x00af
            int r7 = r7 - r3
        L_0x0061:
            if (r7 < 0) goto L_0x00af
            java.util.regex.Pattern r8 = io.netty.util.internal.PlatformDependent.c     // Catch: Throwable -> 0x00af
            java.lang.Object r9 = r0.get(r7)     // Catch: Throwable -> 0x00af
            java.lang.CharSequence r9 = (java.lang.CharSequence) r9     // Catch: Throwable -> 0x00af
            java.util.regex.Matcher r8 = r8.matcher(r9)     // Catch: Throwable -> 0x00af
            boolean r9 = r8.matches()     // Catch: Throwable -> 0x00af
            if (r9 != 0) goto L_0x0078
            int r7 = r7 + (-1)
            goto L_0x0061
        L_0x0078:
            java.lang.String r0 = r8.group(r3)     // Catch: Throwable -> 0x00af
            long r5 = java.lang.Long.parseLong(r0)     // Catch: Throwable -> 0x00af
            r0 = 2
            java.lang.String r0 = r8.group(r0)     // Catch: Throwable -> 0x00af
            char r0 = r0.charAt(r4)     // Catch: Throwable -> 0x00af
            r3 = 71
            if (r0 == r3) goto L_0x00ab
            r3 = 75
            if (r0 == r3) goto L_0x00a7
            r3 = 77
            if (r0 == r3) goto L_0x00a2
            r3 = 103(0x67, float:1.44E-43)
            if (r0 == r3) goto L_0x00ab
            r3 = 107(0x6b, float:1.5E-43)
            if (r0 == r3) goto L_0x00a7
            r3 = 109(0x6d, float:1.53E-43)
            if (r0 == r3) goto L_0x00a2
            goto L_0x00af
        L_0x00a2:
            r3 = 1048576(0x100000, double:5.180654E-318)
            long r5 = r5 * r3
            goto L_0x00af
        L_0x00a7:
            r3 = 1024(0x400, double:5.06E-321)
            long r5 = r5 * r3
            goto L_0x00af
        L_0x00ab:
            r3 = 1073741824(0x40000000, double:5.304989477E-315)
            long r5 = r5 * r3
        L_0x00af:
            int r0 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r0 > 0) goto L_0x00c7
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()
            long r5 = r0.maxMemory()
            io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.internal.PlatformDependent.b
            java.lang.String r1 = "maxDirectMemory: {} bytes (maybe)"
            java.lang.Long r2 = java.lang.Long.valueOf(r5)
            r0.debug(r1, r2)
            goto L_0x00d2
        L_0x00c7:
            io.netty.util.internal.logging.InternalLogger r0 = io.netty.util.internal.PlatformDependent.b
            java.lang.String r1 = "maxDirectMemory: {} bytes"
            java.lang.Long r2 = java.lang.Long.valueOf(r5)
            r0.debug(r1, r2)
        L_0x00d2:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.PlatformDependent.f():long");
    }

    private static boolean g() {
        if (isAndroid()) {
            return false;
        }
        boolean z = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
        b.debug("-Dio.netty.noJavassist: {}", Boolean.valueOf(z));
        if (z) {
            b.debug("Javassist: unavailable (io.netty.noJavassist)");
            return false;
        }
        try {
            JavassistTypeParameterMatcherGenerator.generate(Object.class, getClassLoader(PlatformDependent.class));
            b.debug("Javassist: available");
            return true;
        } catch (Throwable unused) {
            b.debug("Javassist: unavailable");
            b.debug("You don't have Javassist in your class path or you don't have enough permission to load dynamically generated classes.  Please check the configuration for better performance.");
            return false;
        }
    }

    private static File h() {
        File file;
        File a2;
        try {
            a2 = a(SystemPropertyUtil.get("io.netty.tmpdir"));
        } catch (Throwable unused) {
        }
        if (a2 != null) {
            b.debug("-Dio.netty.tmpdir: {}", a2);
            return a2;
        }
        File a3 = a(SystemPropertyUtil.get("java.io.tmpdir"));
        if (a3 != null) {
            b.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", a3);
            return a3;
        }
        if (isWindows()) {
            File a4 = a(System.getenv("TEMP"));
            if (a4 != null) {
                b.debug("-Dio.netty.tmpdir: {} (%TEMP%)", a4);
                return a4;
            }
            String str = System.getenv("USERPROFILE");
            if (str != null) {
                File a5 = a(str + "\\AppData\\Local\\Temp");
                if (a5 != null) {
                    b.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", a5);
                    return a5;
                }
                File a6 = a(str + "\\Local Settings\\Temp");
                if (a6 != null) {
                    b.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", a6);
                    return a6;
                }
            }
        } else {
            File a7 = a(System.getenv("TMPDIR"));
            if (a7 != null) {
                b.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", a7);
                return a7;
            }
        }
        if (isWindows()) {
            file = new File("C:\\Windows\\Temp");
        } else {
            file = new File("/tmp");
        }
        b.warn("Failed to get the temporary directory; falling back to: {}", file);
        return file;
    }

    private static File a(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        try {
            return file.getAbsoluteFile();
        } catch (Exception unused) {
            return file;
        }
    }

    private static int i() {
        int i2 = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
        if (i2 > 0) {
            b.debug("-Dio.netty.bitMode: {}", Integer.valueOf(i2));
            return i2;
        }
        int i3 = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
        if (i3 > 0) {
            b.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", Integer.valueOf(i3));
            return i3;
        }
        int i4 = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
        if (i4 > 0) {
            b.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", Integer.valueOf(i4));
            return i4;
        }
        String trim = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
        if ("amd64".equals(trim) || "x86_64".equals(trim)) {
            i4 = 64;
        } else if ("i386".equals(trim) || "i486".equals(trim) || "i586".equals(trim) || "i686".equals(trim)) {
            i4 = 32;
        }
        if (i4 > 0) {
            b.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(i4), trim);
        }
        Matcher matcher = Pattern.compile("([1-9][0-9]+)-?bit").matcher(SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 64;
    }

    private static int j() {
        if (!hasUnsafe()) {
            return -1;
        }
        return w.h();
    }

    private static boolean a(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        int i5 = i4 + i2;
        while (i2 < i5) {
            if (bArr[i2] != bArr2[i3]) {
                return false;
            }
            i2++;
            i3++;
        }
        return true;
    }

    static int a(byte[] bArr, int i2, int i3) {
        int i4 = i3 & 7;
        int i5 = i2 + i4;
        int i6 = -1028477387;
        for (int i7 = (i2 - 8) + i3; i7 >= i5; i7 -= 8) {
            i6 = w.b(a(bArr, i7), i6);
        }
        switch (i4) {
            case 1:
                return (i6 * 31) + w.a(bArr[i2]);
            case 2:
                return (i6 * 31) + w.a(c(bArr, i2));
            case 3:
                return (((i6 * 31) + w.a(c(bArr, i2 + 1))) * 31) + w.a(bArr[i2]);
            case 4:
                return (i6 * 31) + w.b(b(bArr, i2));
            case 5:
                return (((i6 * 31) + Integer.rotateLeft(w.b(b(bArr, i2 + 1)), 13)) * 31) + w.a(bArr[i2]);
            case 6:
                return (((i6 * 31) + Integer.rotateLeft(w.b(b(bArr, i2 + 2)), 13)) * 31) + w.a(c(bArr, i2));
            case 7:
                return (((((i6 * 31) + Integer.rotateLeft(w.b(b(bArr, i2 + 3)), 13)) * 31) + w.a(c(bArr, i2 + 1))) * 31) + w.a(bArr[i2]);
            default:
                return i6;
        }
    }

    static int a(CharSequence charSequence) {
        int length = charSequence.length() & 7;
        int i2 = -1028477387;
        for (int length2 = charSequence.length() - 8; length2 >= length; length2 -= 8) {
            i2 = w.b(a(charSequence, length2), i2);
        }
        switch (length) {
            case 1:
                return (i2 * 31) + w.a(charSequence.charAt(0));
            case 2:
                return (i2 * 31) + w.a(c(charSequence, 0));
            case 3:
                return (((i2 * 31) + w.a(c(charSequence, 1))) * 31) + w.a(charSequence.charAt(0));
            case 4:
                return (i2 * 31) + w.b(b(charSequence, 0));
            case 5:
                return (((i2 * 31) + Integer.rotateLeft(w.b(b(charSequence, 1)), 13)) * 31) + w.a(charSequence.charAt(0));
            case 6:
                return (((i2 * 31) + Integer.rotateLeft(w.b(b(charSequence, 2)), 13)) * 31) + w.a(c(charSequence, 0));
            case 7:
                return (((((i2 * 31) + Integer.rotateLeft(w.b(b(charSequence, 3)), 13)) * 31) + w.a(c(charSequence, 1))) * 31) + w.a(charSequence.charAt(0));
            default:
                return i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a extends AtomicLong implements LongCounter {
        private static final long serialVersionUID = 4074772784610639305L;

        private a() {
        }

        @Override // io.netty.util.internal.LongCounter
        public void add(long j) {
            addAndGet(j);
        }

        @Override // io.netty.util.internal.LongCounter
        public void increment() {
            incrementAndGet();
        }

        @Override // io.netty.util.internal.LongCounter
        public void decrement() {
            decrementAndGet();
        }

        @Override // io.netty.util.internal.LongCounter
        public long value() {
            return get();
        }
    }

    private PlatformDependent() {
    }
}
