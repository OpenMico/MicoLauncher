package io.netty.util.internal.chmv8;

import com.xiaomi.infra.galaxy.fds.Constants;
import io.netty.util.internal.StringUtil;
import io.realm.internal.OsCollectionChangeSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
public class ConcurrentHashMapV8<K, V> implements Serializable, ConcurrentMap<K, V> {
    private static final Unsafe n;
    private static final long o;
    private static final long p;
    private static final long q;
    private static final long r;
    private static final long s;
    private static final long serialVersionUID = 7249069246763182397L;
    private static final long t;
    private static final long u;
    private static final int v;
    volatile transient ai<K, V>[] b;
    private volatile transient ai<K, V>[] d;
    private volatile transient long e;
    private volatile transient int f;
    private volatile transient int g;
    private volatile transient int h;
    private volatile transient int i;
    private volatile transient d[] j;
    private transient KeySetView<K, V> k;
    private transient aw<K, V> l;
    private transient f<K, V> m;
    static final int a = Runtime.getRuntime().availableProcessors();
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("segments", ar[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};
    static final AtomicInteger c = new AtomicInteger();

    /* loaded from: classes4.dex */
    public interface Action<A> {
        void apply(A a);
    }

    /* loaded from: classes4.dex */
    public interface BiAction<A, B> {
        void apply(A a, B b);
    }

    /* loaded from: classes4.dex */
    public interface BiFun<A, B, T> {
        T apply(A a, B b);
    }

    /* loaded from: classes4.dex */
    public interface ConcurrentHashMapSpliterator<T> {
        long estimateSize();

        void forEachRemaining(Action<? super T> action);

        boolean tryAdvance(Action<? super T> action);

        ConcurrentHashMapSpliterator<T> trySplit();
    }

    /* loaded from: classes4.dex */
    public interface DoubleByDoubleToDouble {
        double apply(double d, double d2);
    }

    /* loaded from: classes4.dex */
    public interface Fun<A, T> {
        T apply(A a);
    }

    /* loaded from: classes4.dex */
    public interface IntByIntToInt {
        int apply(int i, int i2);
    }

    /* loaded from: classes4.dex */
    public interface LongByLongToLong {
        long apply(long j, long j2);
    }

    /* loaded from: classes4.dex */
    public interface ObjectByObjectToDouble<A, B> {
        double apply(A a, B b);
    }

    /* loaded from: classes4.dex */
    public interface ObjectByObjectToInt<A, B> {
        int apply(A a, B b);
    }

    /* loaded from: classes4.dex */
    public interface ObjectByObjectToLong<A, B> {
        long apply(A a, B b);
    }

    /* loaded from: classes4.dex */
    public interface ObjectToDouble<A> {
        double apply(A a);
    }

    /* loaded from: classes4.dex */
    public interface ObjectToInt<A> {
        int apply(A a);
    }

    /* loaded from: classes4.dex */
    public interface ObjectToLong<A> {
        long apply(A a);
    }

    static final int a(int i2) {
        return (i2 ^ (i2 >>> 16)) & Integer.MAX_VALUE;
    }

    private static final int b(int i2) {
        int i3 = i2 - 1;
        int i4 = i3 | (i3 >>> 1);
        int i5 = i4 | (i4 >>> 2);
        int i6 = i5 | (i5 >>> 4);
        int i7 = i6 | (i6 >>> 8);
        int i8 = i7 | (i7 >>> 16);
        if (i8 < 0) {
            return 1;
        }
        if (i8 >= 1073741824) {
            return 1073741824;
        }
        return 1 + i8;
    }

    static {
        try {
            n = d();
            o = n.objectFieldOffset(ConcurrentHashMapV8.class.getDeclaredField("sizeCtl"));
            p = n.objectFieldOffset(ConcurrentHashMapV8.class.getDeclaredField("transferIndex"));
            q = n.objectFieldOffset(ConcurrentHashMapV8.class.getDeclaredField("transferOrigin"));
            r = n.objectFieldOffset(ConcurrentHashMapV8.class.getDeclaredField("baseCount"));
            s = n.objectFieldOffset(ConcurrentHashMapV8.class.getDeclaredField("cellsBusy"));
            t = n.objectFieldOffset(d.class.getDeclaredField(com.xiaomi.onetrack.api.b.p));
            u = n.arrayBaseOffset(ai[].class);
            int arrayIndexScale = n.arrayIndexScale(ai[].class);
            if (((arrayIndexScale - 1) & arrayIndexScale) == 0) {
                v = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
                return;
            }
            throw new Error("data type scale not a power of two");
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class ai<K, V> implements Map.Entry<K, V> {
        final int b;
        final K c;
        volatile V d;
        volatile ai<K, V> e;

        ai(int i, K k, V v, ai<K, V> aiVar) {
            this.b = i;
            this.c = k;
            this.d = v;
            this.e = aiVar;
        }

        @Override // java.util.Map.Entry
        public final K getKey() {
            return this.c;
        }

        @Override // java.util.Map.Entry
        public final V getValue() {
            return this.d;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            return this.c.hashCode() ^ this.d.hashCode();
        }

        public final String toString() {
            return this.c + "=" + this.d;
        }

        @Override // java.util.Map.Entry
        public final V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            K k;
            V v;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && (key == (k = this.c) || key.equals(k)) && (value == (v = this.d) || value.equals(v));
        }

        ai<K, V> a(int i, Object obj) {
            K k;
            if (obj == null) {
                return null;
            }
            ai<K, V> aiVar = this;
            do {
                if (aiVar.b == i && ((k = aiVar.c) == obj || (k != null && obj.equals(k)))) {
                    return aiVar;
                }
                aiVar = aiVar.e;
            } while (aiVar != null);
            return null;
        }
    }

    static Class<?> a(Object obj) {
        Type[] actualTypeArguments;
        if (!(obj instanceof Comparable)) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if (cls == String.class) {
            return cls;
        }
        Type[] genericInterfaces = cls.getGenericInterfaces();
        if (genericInterfaces == null) {
            return null;
        }
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getRawType() == Comparable.class && (actualTypeArguments = parameterizedType.getActualTypeArguments()) != null && actualTypeArguments.length == 1 && actualTypeArguments[0] == cls) {
                    return cls;
                }
            }
        }
        return null;
    }

    static int a(Class<?> cls, Object obj, Object obj2) {
        if (obj2 == null || obj2.getClass() != cls) {
            return 0;
        }
        return ((Comparable) obj).compareTo(obj2);
    }

    static final <K, V> ai<K, V> a(ai<K, V>[] aiVarArr, int i2) {
        return (ai) n.getObjectVolatile(aiVarArr, (i2 << v) + u);
    }

    static final <K, V> boolean a(ai<K, V>[] aiVarArr, int i2, ai<K, V> aiVar, ai<K, V> aiVar2) {
        return n.compareAndSwapObject(aiVarArr, (i2 << v) + u, aiVar, aiVar2);
    }

    static final <K, V> void a(ai<K, V>[] aiVarArr, int i2, ai<K, V> aiVar) {
        n.putObjectVolatile(aiVarArr, (i2 << v) + u, aiVar);
    }

    public ConcurrentHashMapV8() {
    }

    public ConcurrentHashMapV8(int i2) {
        if (i2 >= 0) {
            this.f = i2 >= 536870912 ? 1073741824 : b(i2 + (i2 >>> 1) + 1);
            return;
        }
        throw new IllegalArgumentException();
    }

    public ConcurrentHashMapV8(Map<? extends K, ? extends V> map) {
        this.f = 16;
        putAll(map);
    }

    public ConcurrentHashMapV8(int i2, float f2) {
        this(i2, f2, 1);
    }

    public ConcurrentHashMapV8(int i2, float f2, int i3) {
        if (f2 <= 0.0f || i2 < 0 || i3 <= 0) {
            throw new IllegalArgumentException();
        }
        long j2 = (long) (((i2 < i3 ? i3 : i2) / f2) + 1.0d);
        this.f = j2 >= Constants.DEFAULT_SPACE_LIMIT ? 1073741824 : b((int) j2);
    }

    @Override // java.util.Map
    public int size() {
        long a2 = a();
        if (a2 < 0) {
            return 0;
        }
        if (a2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) a2;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return a() <= 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x004d, code lost:
        return r1.d;
     */
    @Override // java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public V get(java.lang.Object r5) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            int r0 = a(r0)
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r1 = r4.b
            r2 = 0
            if (r1 == 0) goto L_0x004e
            int r3 = r1.length
            if (r3 <= 0) goto L_0x004e
            int r3 = r3 + (-1)
            r3 = r3 & r0
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r1 = a(r1, r3)
            if (r1 == 0) goto L_0x004e
            int r3 = r1.b
            if (r3 != r0) goto L_0x002c
            K r3 = r1.c
            if (r3 == r5) goto L_0x0029
            if (r3 == 0) goto L_0x0037
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0037
        L_0x0029:
            V r5 = r1.d
            return r5
        L_0x002c:
            if (r3 >= 0) goto L_0x0037
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r5 = r1.a(r0, r5)
            if (r5 == 0) goto L_0x0036
            V r2 = r5.d
        L_0x0036:
            return r2
        L_0x0037:
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V> r1 = r1.e
            if (r1 == 0) goto L_0x004e
            int r3 = r1.b
            if (r3 != r0) goto L_0x0037
            K r3 = r1.c
            if (r3 == r5) goto L_0x004b
            if (r3 == 0) goto L_0x0037
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0037
        L_0x004b:
            V r5 = r1.d
            return r5
        L_0x004e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        if (obj != null) {
            ai<K, V>[] aiVarArr = this.b;
            if (aiVarArr != null) {
                as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
                while (true) {
                    ai<K, V> b2 = asVar.b();
                    if (b2 == null) {
                        break;
                    }
                    V v2 = b2.d;
                    if (v2 == obj) {
                        return true;
                    }
                    if (v2 != null && obj.equals(v2)) {
                        return true;
                    }
                }
            }
            return false;
        }
        throw new NullPointerException();
    }

    @Override // java.util.Map
    public V put(K k2, V v2) {
        return a((ConcurrentHashMapV8<K, V>) k2, (K) v2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0053, code lost:
        r7 = r1.d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
        if (r11 != false) goto L_0x0066;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0057, code lost:
        r1.d = r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final V a(K r9, V r10, boolean r11) {
        /*
            r8 = this;
            if (r9 == 0) goto L_0x00a0
            if (r10 == 0) goto L_0x00a0
            int r0 = r9.hashCode()
            int r0 = a(r0)
            r1 = 0
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r2 = r8.b
        L_0x000f:
            if (r2 == 0) goto L_0x009a
            int r3 = r2.length
            if (r3 != 0) goto L_0x0016
            goto L_0x009a
        L_0x0016:
            int r3 = r3 + (-1)
            r3 = r3 & r0
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r4 = a(r2, r3)
            r5 = 0
            if (r4 != 0) goto L_0x002d
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r4 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai
            r4.<init>(r0, r9, r10, r5)
            boolean r3 = a(r2, r3, r5, r4)
            if (r3 == 0) goto L_0x000f
            goto L_0x0091
        L_0x002d:
            int r6 = r4.b
            r7 = -1
            if (r6 != r7) goto L_0x0037
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai[] r2 = r8.a(r2, r4)
            goto L_0x000f
        L_0x0037:
            monitor-enter(r4)
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r7 = a(r2, r3)     // Catch: all -> 0x0097
            if (r7 != r4) goto L_0x0083
            if (r6 < 0) goto L_0x006c
            r1 = 1
            r6 = r1
            r1 = r4
        L_0x0043:
            int r7 = r1.b     // Catch: all -> 0x0097
            if (r7 != r0) goto L_0x005a
            K r7 = r1.c     // Catch: all -> 0x0097
            if (r7 == r9) goto L_0x0053
            if (r7 == 0) goto L_0x005a
            boolean r7 = r9.equals(r7)     // Catch: all -> 0x0097
            if (r7 == 0) goto L_0x005a
        L_0x0053:
            V r7 = r1.d     // Catch: all -> 0x0097
            if (r11 != 0) goto L_0x0066
            r1.d = r10     // Catch: all -> 0x0097
            goto L_0x0066
        L_0x005a:
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V> r7 = r1.e     // Catch: all -> 0x0097
            if (r7 != 0) goto L_0x0068
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r7 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai     // Catch: all -> 0x0097
            r7.<init>(r0, r9, r10, r5)     // Catch: all -> 0x0097
            r1.e = r7     // Catch: all -> 0x0097
            r7 = r5
        L_0x0066:
            r1 = r6
            goto L_0x0084
        L_0x0068:
            int r6 = r6 + 1
            r1 = r7
            goto L_0x0043
        L_0x006c:
            boolean r6 = r4 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.at     // Catch: all -> 0x0097
            if (r6 == 0) goto L_0x0083
            r1 = 2
            r6 = r4
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$at r6 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.at) r6     // Catch: all -> 0x0097
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$au r6 = r6.a(r0, r9, r10)     // Catch: all -> 0x0097
            if (r6 == 0) goto L_0x0081
            V r7 = r6.d     // Catch: all -> 0x0097
            if (r11 != 0) goto L_0x0084
            r6.d = r10     // Catch: all -> 0x0097
            goto L_0x0084
        L_0x0081:
            r7 = r5
            goto L_0x0084
        L_0x0083:
            r7 = r5
        L_0x0084:
            monitor-exit(r4)     // Catch: all -> 0x0097
            if (r1 == 0) goto L_0x000f
            r9 = 8
            if (r1 < r9) goto L_0x008e
            r8.b(r2, r3)
        L_0x008e:
            if (r7 == 0) goto L_0x0091
            return r7
        L_0x0091:
            r9 = 1
            r8.a(r9, r1)
            return r5
        L_0x0097:
            r9 = move-exception
            monitor-exit(r4)     // Catch: all -> 0x0097
            throw r9
        L_0x009a:
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai[] r2 = r8.c()
            goto L_0x000f
        L_0x00a0:
            java.lang.NullPointerException r9 = new java.lang.NullPointerException
            r9.<init>()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        c(map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            a((ConcurrentHashMapV8<K, V>) entry.getKey(), entry.getValue(), false);
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return a(obj, (Object) null, (Object) null);
    }

    final V a(Object obj, V v2, Object obj2) {
        int length;
        int i2;
        ai<K, V> a2;
        V v3;
        au<K, V> a3;
        K k2;
        int a4 = a(obj.hashCode());
        ai<K, V>[] aiVarArr = this.b;
        while (true) {
            if (aiVarArr == null || (length = aiVarArr.length) == 0 || (a2 = a(aiVarArr, (i2 = (length - 1) & a4))) == null) {
                break;
            }
            int i3 = a2.b;
            if (i3 == -1) {
                aiVarArr = a(aiVarArr, a2);
            } else {
                boolean z2 = false;
                synchronized (a2) {
                    z2 = true;
                    if (a(aiVarArr, i2) == a2) {
                        if (i3 >= 0) {
                            ai<K, V> aiVar = null;
                            ai<K, V> aiVar2 = a2;
                            while (true) {
                                if (aiVar2.b != a4 || ((k2 = aiVar2.c) != obj && (k2 == null || !obj.equals(k2)))) {
                                    ai<K, V> aiVar3 = aiVar2.e;
                                    if (aiVar3 == null) {
                                        break;
                                    }
                                    aiVar = aiVar2;
                                    aiVar2 = aiVar3;
                                }
                            }
                            v3 = aiVar2.d;
                            if (obj2 == null || obj2 == v3 || (v3 != null && obj2.equals(v3))) {
                                if (v2 != null) {
                                    aiVar2.d = v2;
                                } else if (aiVar != null) {
                                    aiVar.e = aiVar2.e;
                                } else {
                                    a(aiVarArr, i2, aiVar2.e);
                                }
                            }
                            v3 = null;
                        } else if (a2 instanceof at) {
                            at atVar = (at) a2;
                            au<K, V> auVar = atVar.a;
                            if (!(auVar == null || (a3 = auVar.a(a4, obj, null)) == null)) {
                                v3 = (V) a3.d;
                                if (obj2 == null || obj2 == v3 || (v3 != null && obj2.equals(v3))) {
                                    if (v2 != null) {
                                        a3.d = v2;
                                    } else if (atVar.a(a3)) {
                                        a(aiVarArr, i2, a((ai) atVar.f));
                                    }
                                }
                            }
                            v3 = null;
                        }
                    }
                    v3 = null;
                }
                if (z2) {
                    if (v3 != null) {
                        if (v2 == null) {
                            a(-1L, -1);
                        }
                        return v3;
                    }
                }
            }
        }
        return null;
    }

    @Override // java.util.Map
    public void clear() {
        ai<K, V> aiVar;
        ai<K, V>[] aiVarArr = this.b;
        int i2 = 0;
        long j2 = 0;
        while (aiVarArr != null && i2 < aiVarArr.length) {
            ai<K, V> a2 = a(aiVarArr, i2);
            if (a2 == null) {
                i2++;
            } else {
                int i3 = a2.b;
                if (i3 == -1) {
                    aiVarArr = a(aiVarArr, a2);
                    i2 = 0;
                } else {
                    synchronized (a2) {
                        if (a(aiVarArr, i2) == a2) {
                            if (i3 >= 0) {
                                aiVar = a2;
                            } else {
                                aiVar = a2 instanceof at ? ((at) a2).f : null;
                            }
                            while (aiVar != null) {
                                j2--;
                                aiVar = aiVar.e;
                            }
                            i2++;
                            a(aiVarArr, i2, (ai) null);
                        }
                    }
                }
            }
        }
        if (j2 != 0) {
            a(j2, -1);
        }
    }

    @Override // java.util.Map
    public KeySetView<K, V> keySet() {
        KeySetView<K, V> keySetView = this.k;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView<K, V> keySetView2 = new KeySetView<>(this, null);
        this.k = keySetView2;
        return keySetView2;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        aw<K, V> awVar = this.l;
        if (awVar != null) {
            return awVar;
        }
        aw<K, V> awVar2 = new aw<>(this);
        this.l = awVar2;
        return awVar2;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        f<K, V> fVar = this.m;
        if (fVar != null) {
            return fVar;
        }
        f<K, V> fVar2 = new f<>(this);
        this.m = fVar2;
        return fVar2;
    }

    @Override // java.util.Map
    public int hashCode() {
        ai<K, V>[] aiVarArr = this.b;
        int i2 = 0;
        if (aiVarArr != null) {
            as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
            while (true) {
                ai<K, V> b2 = asVar.b();
                if (b2 == null) {
                    break;
                }
                i2 += b2.d.hashCode() ^ b2.c.hashCode();
            }
        }
        return i2;
    }

    public String toString() {
        ai<K, V>[] aiVarArr = this.b;
        int length = aiVarArr == null ? 0 : aiVarArr.length;
        as asVar = new as(aiVarArr, length, 0, length);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        ai<K, V> b2 = asVar.b();
        if (b2 != null) {
            while (true) {
                Object obj = b2.c;
                Object obj2 = b2.d;
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                sb.append('=');
                if (obj2 == this) {
                    obj2 = "(this Map)";
                }
                sb.append(obj2);
                b2 = asVar.b();
                if (b2 == null) {
                    break;
                }
                sb.append(StringUtil.COMMA);
                sb.append(' ');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        V value;
        V v2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        ai<K, V>[] aiVarArr = this.b;
        int length = aiVarArr == null ? 0 : aiVarArr.length;
        as asVar = new as(aiVarArr, length, 0, length);
        while (true) {
            ai<K, V> b2 = asVar.b();
            if (b2 != null) {
                V v3 = b2.d;
                Object obj2 = map.get(b2.c);
                if (obj2 == null || (obj2 != v3 && !obj2.equals(v3))) {
                    break;
                }
            } else {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    K key = entry.getKey();
                    if (key == null || (value = entry.getValue()) == null || (v2 = get(key)) == null || (value != v2 && !value.equals(v2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes4.dex */
    static class ar<K, V> extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;

        ar(float f) {
            this.loadFactor = f;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int i2 = 0;
        int i3 = 1;
        while (i3 < 16) {
            i2++;
            i3 <<= 1;
        }
        int i4 = 32 - i2;
        int i5 = i3 - 1;
        ar[] arVarArr = new ar[16];
        for (int i6 = 0; i6 < arVarArr.length; i6++) {
            arVarArr[i6] = new ar(0.75f);
        }
        objectOutputStream.putFields().put("segments", arVarArr);
        objectOutputStream.putFields().put("segmentShift", i4);
        objectOutputStream.putFields().put("segmentMask", i5);
        objectOutputStream.writeFields();
        ai<K, V>[] aiVarArr = this.b;
        if (aiVarArr != null) {
            as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
            while (true) {
                ai<K, V> b2 = asVar.b();
                if (b2 == null) {
                    break;
                }
                objectOutputStream.writeObject(b2.c);
                objectOutputStream.writeObject(b2.d);
            }
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        long j2;
        int i2;
        K k2;
        this.f = -1;
        objectInputStream.defaultReadObject();
        long j3 = 0;
        long j4 = 0;
        ai<K, V> aiVar = null;
        while (true) {
            Object readObject = objectInputStream.readObject();
            Object readObject2 = objectInputStream.readObject();
            j2 = 1;
            if (readObject == null || readObject2 == null) {
                break;
            }
            j4++;
            aiVar = new ai<>(a(readObject.hashCode()), readObject, readObject2, aiVar);
        }
        if (j4 == 0) {
            this.f = 0;
            return;
        }
        boolean z2 = true;
        if (j4 >= 536870912) {
            i2 = 1073741824;
        } else {
            int i3 = (int) j4;
            i2 = b(i3 + (i3 >>> 1) + 1);
        }
        ai<K, V>[] aiVarArr = new ai[i2];
        int i4 = i2 - 1;
        while (aiVar != null) {
            ai<K, V> aiVar2 = aiVar.e;
            int i5 = aiVar.b;
            int i6 = i5 & i4;
            ai<K, V> a2 = a(aiVarArr, i6);
            if (a2 != null) {
                K k3 = aiVar.c;
                if (a2.b < 0) {
                    if (((at) a2).a(i5, k3, aiVar.d) == null) {
                        j3 += j2;
                    }
                    z2 = false;
                } else {
                    int i7 = 0;
                    for (ai<K, V> aiVar3 = a2; aiVar3 != null; aiVar3 = aiVar3.e) {
                        if (aiVar3.b == i5 && ((k2 = aiVar3.c) == k3 || (k2 != null && k3.equals(k2)))) {
                            z2 = false;
                            break;
                        } else {
                            i7++;
                        }
                    }
                    z2 = true;
                    if (z2 && i7 >= 8) {
                        j3++;
                        aiVar.e = a2;
                        ai<K, V> aiVar4 = aiVar;
                        au<K, V> auVar = null;
                        au<K, V> auVar2 = null;
                        while (aiVar4 != null) {
                            au<K, V> auVar3 = new au<>(aiVar4.b, aiVar4.c, aiVar4.d, null, null);
                            auVar3.h = auVar2;
                            if (auVar2 == null) {
                                auVar = auVar3;
                            } else {
                                auVar2.e = auVar3;
                            }
                            aiVar4 = aiVar4.e;
                            auVar2 = auVar3;
                            j3 = j3;
                        }
                        a(aiVarArr, i6, new at(auVar));
                        z2 = false;
                    }
                }
            }
            if (z2) {
                j2 = 1;
                j3++;
                aiVar.e = a2;
                a(aiVarArr, i6, aiVar);
            } else {
                j2 = 1;
            }
            aiVar = aiVar2;
            z2 = true;
        }
        this.b = aiVarArr;
        this.f = i2 - (i2 >>> 2);
        this.e = j3;
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V putIfAbsent(K k2, V v2) {
        return a((ConcurrentHashMapV8<K, V>) k2, (K) v2, true);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean remove(Object obj, Object obj2) {
        if (obj != null) {
            return (obj2 == null || a(obj, (Object) null, obj2) == null) ? false : true;
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public boolean replace(K k2, V v2, V v3) {
        if (k2 != null && v2 != null && v3 != null) {
            return a((Object) k2, (K) v3, (Object) v2) != null;
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V replace(K k2, V v2) {
        if (k2 != null && v2 != null) {
            return a((Object) k2, (K) v2, (Object) null);
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    public V getOrDefault(Object obj, V v2) {
        V v3 = get(obj);
        return v3 == null ? v2 : v3;
    }

    public void forEach(BiAction<? super K, ? super V> biAction) {
        if (biAction != null) {
            ai<K, V>[] aiVarArr = this.b;
            if (aiVarArr != null) {
                as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
                while (true) {
                    ai<K, V> b2 = asVar.b();
                    if (b2 != null) {
                        biAction.apply((K) b2.c, (V) b2.d);
                    } else {
                        return;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void replaceAll(BiFun<? super K, ? super V, ? extends V> biFun) {
        if (biFun != null) {
            ai<K, V>[] aiVarArr = this.b;
            if (aiVarArr != null) {
                as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
                while (true) {
                    ai<K, V> b2 = asVar.b();
                    if (b2 != null) {
                        V v2 = b2.d;
                        Object obj = (K) b2.c;
                        do {
                            Object apply = biFun.apply(obj, v2);
                            if (apply == null) {
                                throw new NullPointerException();
                            } else if (a(obj, apply, v2) == null) {
                                v2 = (Object) get(obj);
                            }
                        } while (v2 != null);
                    } else {
                        return;
                    }
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
        r8 = false;
        r9 = r4.d;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public V computeIfAbsent(K r12, io.netty.util.internal.chmv8.ConcurrentHashMapV8.Fun<? super K, ? extends V> r13) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.computeIfAbsent(java.lang.Object, io.netty.util.internal.chmv8.ConcurrentHashMapV8$Fun):java.lang.Object");
    }

    public V computeIfPresent(K k2, BiFun<? super K, ? super V, ? extends V> biFun) {
        au<K, V> a2;
        K k3;
        if (k2 == null || biFun == null) {
            throw new NullPointerException();
        }
        int a3 = a(k2.hashCode());
        ai<K, V>[] aiVarArr = this.b;
        int i2 = 0;
        int i3 = 0;
        V v2 = null;
        while (true) {
            if (aiVarArr != null) {
                int length = aiVarArr.length;
                if (length != 0) {
                    int i4 = (length - 1) & a3;
                    ai<K, V> a4 = a(aiVarArr, i4);
                    if (a4 == null) {
                        break;
                    }
                    int i5 = a4.b;
                    if (i5 == -1) {
                        aiVarArr = a(aiVarArr, a4);
                    } else {
                        synchronized (a4) {
                            if (a(aiVarArr, i4) == a4) {
                                if (i5 >= 0) {
                                    int i6 = 1;
                                    ai<K, V> aiVar = null;
                                    ai<K, V> aiVar2 = a4;
                                    while (true) {
                                        if (aiVar2.b != a3 || ((k3 = aiVar2.c) != k2 && (k3 == null || !k2.equals(k3)))) {
                                            ai<K, V> aiVar3 = aiVar2.e;
                                            if (aiVar3 == null) {
                                                break;
                                            }
                                            i6++;
                                            aiVar = aiVar2;
                                            aiVar2 = aiVar3;
                                        }
                                    }
                                    v2 = (V) biFun.apply(k2, (V) aiVar2.d);
                                    if (v2 != null) {
                                        aiVar2.d = v2;
                                    } else {
                                        ai<K, V> aiVar4 = aiVar2.e;
                                        if (aiVar != null) {
                                            aiVar.e = aiVar4;
                                        } else {
                                            a(aiVarArr, i4, aiVar4);
                                        }
                                        i2 = -1;
                                    }
                                    i3 = i6;
                                } else if (a4 instanceof at) {
                                    i3 = 2;
                                    at atVar = (at) a4;
                                    au<K, V> auVar = atVar.a;
                                    if (!(auVar == null || (a2 = auVar.a(a3, k2, null)) == null)) {
                                        v2 = (V) biFun.apply(k2, (Object) a2.d);
                                        if (v2 != null) {
                                            a2.d = v2;
                                        } else {
                                            if (atVar.a(a2)) {
                                                a(aiVarArr, i4, a((ai) atVar.f));
                                            }
                                            i2 = -1;
                                        }
                                    }
                                }
                            }
                        }
                        if (i3 != 0) {
                            break;
                        }
                    }
                }
            }
            aiVarArr = c();
        }
        if (i2 != 0) {
            a(i2, i3);
        }
        return v2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0075, code lost:
        r8 = (V) r15.apply(r14, (V) r2.d);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007b, code lost:
        if (r8 == null) goto L_0x0080;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x007d, code lost:
        r2.d = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0080, code lost:
        r2 = r2.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0082, code lost:
        if (r9 == null) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0084, code lost:
        r9.e = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0087, code lost:
        a(r1, r6, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x008a, code lost:
        r4 = -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public V compute(K r14, io.netty.util.internal.chmv8.ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends V> r15) {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.compute(java.lang.Object, io.netty.util.internal.chmv8.ConcurrentHashMapV8$BiFun):java.lang.Object");
    }

    public V merge(K k2, V v2, BiFun<? super V, ? super V, ? extends V> biFun) {
        V v3;
        K k3;
        if (k2 == null || v2 == null || biFun == null) {
            throw new NullPointerException();
        }
        int a2 = a(k2.hashCode());
        ai<K, V>[] aiVarArr = this.b;
        int i2 = 0;
        int i3 = 0;
        V v4 = null;
        while (true) {
            if (aiVarArr != null) {
                int length = aiVarArr.length;
                if (length != 0) {
                    int i4 = (length - 1) & a2;
                    ai<K, V> a3 = a(aiVarArr, i4);
                    i3 = 1;
                    if (a3 != null) {
                        int i5 = a3.b;
                        if (i5 == -1) {
                            aiVarArr = a(aiVarArr, a3);
                        } else {
                            synchronized (a3) {
                                if (a(aiVarArr, i4) == a3) {
                                    if (i5 >= 0) {
                                        ai<K, V> aiVar = null;
                                        ai<K, V> aiVar2 = a3;
                                        int i6 = 1;
                                        while (true) {
                                            if (aiVar2.b != a2 || ((k3 = aiVar2.c) != k2 && (k3 == null || !k2.equals(k3)))) {
                                                ai<K, V> aiVar3 = aiVar2.e;
                                                if (aiVar3 == null) {
                                                    aiVar2.e = new ai<>(a2, k2, v2, null);
                                                    i3 = 1;
                                                    v3 = v2;
                                                    break;
                                                }
                                                i6++;
                                                aiVar = aiVar2;
                                                aiVar2 = aiVar3;
                                            }
                                        }
                                        v3 = (V) biFun.apply((V) aiVar2.d, v2);
                                        if (v3 != null) {
                                            aiVar2.d = v3;
                                        } else {
                                            ai<K, V> aiVar4 = aiVar2.e;
                                            if (aiVar != null) {
                                                aiVar.e = aiVar4;
                                            } else {
                                                a(aiVarArr, i4, aiVar4);
                                            }
                                            i3 = -1;
                                        }
                                        i2 = i6;
                                    } else if (a3 instanceof at) {
                                        i2 = 2;
                                        at atVar = (at) a3;
                                        au<K, V> auVar = atVar.a;
                                        au<K, V> a4 = auVar == null ? null : auVar.a(a2, k2, null);
                                        v3 = a4 == null ? v2 : (V) biFun.apply((Object) a4.d, v2);
                                        if (v3 == null) {
                                            if (a4 != null) {
                                                if (atVar.a(a4)) {
                                                    a(aiVarArr, i4, a((ai) atVar.f));
                                                }
                                                i3 = -1;
                                            }
                                            i3 = i3;
                                        } else if (a4 != null) {
                                            a4.d = v3;
                                            i3 = i3;
                                        } else {
                                            atVar.a(a2, k2, v3);
                                        }
                                    }
                                }
                                v3 = v4;
                                i3 = i3;
                            }
                            if (i2 == 0) {
                                i3 = i3;
                                v4 = v3;
                            } else if (i2 >= 8) {
                                b(aiVarArr, i4);
                            }
                        }
                    } else if (a(aiVarArr, i4, (ai) null, new ai(a2, k2, v2, null))) {
                        v3 = v2;
                        break;
                    }
                }
            }
            aiVarArr = c();
        }
        if (i3 != 0) {
            a(i3, i2);
        }
        return v3;
    }

    @Deprecated
    public boolean contains(Object obj) {
        return containsValue(obj);
    }

    public Enumeration<K> keys() {
        ai<K, V>[] aiVarArr = this.b;
        int length = aiVarArr == null ? 0 : aiVarArr.length;
        return new p(aiVarArr, length, 0, length, this);
    }

    public Enumeration<V> elements() {
        ai<K, V>[] aiVarArr = this.b;
        int length = aiVarArr == null ? 0 : aiVarArr.length;
        return new av(aiVarArr, length, 0, length, this);
    }

    public long mappingCount() {
        long a2 = a();
        if (a2 < 0) {
            return 0L;
        }
        return a2;
    }

    public static <K> KeySetView<K, Boolean> newKeySet() {
        return new KeySetView<>(new ConcurrentHashMapV8(), Boolean.TRUE);
    }

    public static <K> KeySetView<K, Boolean> newKeySet(int i2) {
        return new KeySetView<>(new ConcurrentHashMapV8(i2), Boolean.TRUE);
    }

    public KeySetView<K, V> keySet(V v2) {
        if (v2 != null) {
            return new KeySetView<>(this, v2);
        }
        throw new NullPointerException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class o<K, V> extends ai<K, V> {
        final ai<K, V>[] a;

        o(ai<K, V>[] aiVarArr) {
            super(-1, null, null, null);
            this.a = aiVarArr;
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0029, code lost:
            if ((r0 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.o) == false) goto L_0x0030;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x002b, code lost:
            r0 = ((io.netty.util.internal.chmv8.ConcurrentHashMapV8.o) r0).a;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0034, code lost:
            return r0.a(r5, r6);
         */
        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ai
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        io.netty.util.internal.chmv8.ConcurrentHashMapV8.ai<K, V> a(int r5, java.lang.Object r6) {
            /*
                r4 = this;
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r0 = r4.a
            L_0x0002:
                r1 = 0
                if (r6 == 0) goto L_0x003a
                if (r0 == 0) goto L_0x003a
                int r2 = r0.length
                if (r2 == 0) goto L_0x003a
                int r2 = r2 + (-1)
                r2 = r2 & r5
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r0 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(r0, r2)
                if (r0 != 0) goto L_0x0014
                goto L_0x003a
            L_0x0014:
                int r2 = r0.b
                if (r2 != r5) goto L_0x0025
                K r3 = r0.c
                if (r3 == r6) goto L_0x0024
                if (r3 == 0) goto L_0x0025
                boolean r3 = r6.equals(r3)
                if (r3 == 0) goto L_0x0025
            L_0x0024:
                return r0
            L_0x0025:
                if (r2 >= 0) goto L_0x0035
                boolean r1 = r0 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.o
                if (r1 == 0) goto L_0x0030
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$o r0 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.o) r0
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r0 = r0.a
                goto L_0x0002
            L_0x0030:
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai r5 = r0.a(r5, r6)
                return r5
            L_0x0035:
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V> r0 = r0.e
                if (r0 != 0) goto L_0x0014
                return r1
            L_0x003a:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.o.a(int, java.lang.Object):io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai");
        }
    }

    /* loaded from: classes4.dex */
    static final class am<K, V> extends ai<K, V> {
        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ai
        ai<K, V> a(int i, Object obj) {
            return null;
        }

        am() {
            super(-3, null, null, null);
        }
    }

    private final ai<K, V>[] c() {
        while (true) {
            ai<K, V>[] aiVarArr = this.b;
            if (aiVarArr != null && aiVarArr.length != 0) {
                return aiVarArr;
            }
            int i2 = this.f;
            if (i2 < 0) {
                Thread.yield();
            } else if (n.compareAndSwapInt(this, o, i2, -1)) {
                try {
                    ai<K, V>[] aiVarArr2 = this.b;
                    if (aiVarArr2 == null || aiVarArr2.length == 0) {
                        int i3 = i2 > 0 ? i2 : 16;
                        ai<K, V>[] aiVarArr3 = new ai[i3];
                        this.b = aiVarArr3;
                        i2 = i3 - (i3 >>> 2);
                        aiVarArr2 = aiVarArr3;
                    }
                    return aiVarArr2;
                } finally {
                    this.f = i2;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0017, code lost:
        if (r0.compareAndSwapLong(r19, r2, r4, r11) == false) goto L_0x0019;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a(long r20, int r22) {
        /*
            r19 = this;
            r8 = r19
            r9 = r22
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$d[] r10 = r8.j
            if (r10 != 0) goto L_0x0019
            sun.misc.Unsafe r0 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.n
            long r2 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.r
            long r4 = r8.e
            long r11 = r4 + r20
            r1 = r19
            r6 = r11
            boolean r0 = r0.compareAndSwapLong(r1, r2, r4, r6)
            if (r0 != 0) goto L_0x0049
        L_0x0019:
            io.netty.util.internal.InternalThreadLocalMap r1 = io.netty.util.internal.InternalThreadLocalMap.get()
            io.netty.util.internal.IntegerHolder r4 = r1.counterHashCode()
            r0 = 1
            if (r4 == 0) goto L_0x0094
            if (r10 == 0) goto L_0x0094
            int r2 = r10.length
            int r2 = r2 - r0
            if (r2 < 0) goto L_0x0094
            int r3 = r4.value
            r2 = r2 & r3
            r12 = r10[r2]
            if (r12 == 0) goto L_0x0094
            sun.misc.Unsafe r11 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.n
            long r13 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.t
            long r2 = r12.a
            long r17 = r2 + r20
            r15 = r2
            boolean r2 = r11.compareAndSwapLong(r12, r13, r15, r17)
            if (r2 != 0) goto L_0x0042
            r5 = r2
            goto L_0x0095
        L_0x0042:
            if (r9 > r0) goto L_0x0045
            return
        L_0x0045:
            long r11 = r19.a()
        L_0x0049:
            if (r9 < 0) goto L_0x0093
        L_0x004b:
            int r4 = r8.f
            long r0 = (long) r4
            int r0 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x0093
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r6 = r8.b
            if (r6 == 0) goto L_0x0093
            int r0 = r6.length
            r1 = 1073741824(0x40000000, float:2.0)
            if (r0 >= r1) goto L_0x0093
            if (r4 >= 0) goto L_0x007d
            r0 = -1
            if (r4 == r0) goto L_0x0093
            int r0 = r8.g
            int r1 = r8.h
            if (r0 <= r1) goto L_0x0093
            io.netty.util.internal.chmv8.ConcurrentHashMapV8$ai<K, V>[] r7 = r8.d
            if (r7 != 0) goto L_0x006b
            goto L_0x0093
        L_0x006b:
            sun.misc.Unsafe r0 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.n
            long r2 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.o
            int r5 = r4 + (-1)
            r1 = r19
            boolean r0 = r0.compareAndSwapInt(r1, r2, r4, r5)
            if (r0 == 0) goto L_0x008e
            r8.a(r6, r7)
            goto L_0x008e
        L_0x007d:
            sun.misc.Unsafe r0 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.n
            long r2 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.o
            r5 = -2
            r1 = r19
            boolean r0 = r0.compareAndSwapInt(r1, r2, r4, r5)
            if (r0 == 0) goto L_0x008e
            r0 = 0
            r8.a(r6, r0)
        L_0x008e:
            long r11 = r19.a()
            goto L_0x004b
        L_0x0093:
            return
        L_0x0094:
            r5 = r0
        L_0x0095:
            r0 = r19
            r2 = r20
            r0.a(r1, r2, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(long, int):void");
    }

    final ai<K, V>[] a(ai<K, V>[] aiVarArr, ai<K, V> aiVar) {
        ai<K, V>[] aiVarArr2;
        int i2;
        if (!(aiVar instanceof o) || (aiVarArr2 = ((o) aiVar).a) == null) {
            return this.b;
        }
        if (aiVarArr2 == this.d && aiVarArr == this.b && this.g > this.h && (i2 = this.f) < -1 && n.compareAndSwapInt(this, o, i2, i2 - 1)) {
            a(aiVarArr, aiVarArr2);
        }
        return aiVarArr2;
    }

    private final void c(int i2) {
        int length;
        int b2 = i2 >= 536870912 ? 1073741824 : b(i2 + (i2 >>> 1) + 1);
        while (true) {
            int i3 = this.f;
            if (i3 >= 0) {
                ai<K, V>[] aiVarArr = this.b;
                if (aiVarArr == null || (length = aiVarArr.length) == 0) {
                    int i4 = i3 > b2 ? i3 : b2;
                    if (n.compareAndSwapInt(this, o, i3, -1)) {
                        try {
                            if (this.b == aiVarArr) {
                                this.b = new ai[i4];
                                i3 = i4 - (i4 >>> 2);
                            }
                        } finally {
                            this.f = i3;
                        }
                    } else {
                        continue;
                    }
                } else if (b2 > i3 && length < 1073741824) {
                    if (aiVarArr == this.b && n.compareAndSwapInt(this, o, i3, -2)) {
                        a(aiVarArr, (ai[]) null);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final void a(ai<K, V>[] aiVarArr, ai<K, V>[] aiVarArr2) {
        ai<K, V>[] aiVarArr3;
        Unsafe unsafe;
        long j2;
        int i2;
        int i3;
        int i4;
        ai aiVar;
        ai aiVar2;
        ai<K, V> aiVar3;
        ConcurrentHashMapV8<K, V> concurrentHashMapV8 = this;
        ai<K, V>[] aiVarArr4 = aiVarArr;
        int length = aiVarArr4.length;
        int i5 = a;
        boolean z2 = true;
        int i6 = i5 > 1 ? (length >>> 3) / i5 : length;
        int i7 = i6 < 16 ? 16 : i6;
        if (aiVarArr2 == null) {
            try {
                ai<K, V>[] aiVarArr5 = new ai[length << 1];
                concurrentHashMapV8.d = aiVarArr5;
                concurrentHashMapV8.h = length;
                concurrentHashMapV8.g = length;
                o oVar = new o(aiVarArr4);
                int i8 = length;
                while (i8 > 0) {
                    int i9 = i8 > i7 ? i8 - i7 : 0;
                    for (int i10 = i9; i10 < i8; i10++) {
                        aiVarArr5[i10] = oVar;
                    }
                    for (int i11 = length + i9; i11 < length + i8; i11++) {
                        aiVarArr5[i11] = oVar;
                    }
                    n.putOrderedInt(concurrentHashMapV8, q, i9);
                    i8 = i9;
                }
                aiVarArr3 = aiVarArr5;
            } catch (Throwable unused) {
                concurrentHashMapV8.f = Integer.MAX_VALUE;
                return;
            }
        } else {
            aiVarArr3 = aiVarArr2;
        }
        int length2 = aiVarArr3.length;
        o oVar2 = new o(aiVarArr3);
        int i12 = -1;
        boolean z3 = true;
        int i13 = 0;
        int i14 = 0;
        boolean z4 = false;
        while (true) {
            if (!z3) {
                ai<K, V> aiVar4 = null;
                if (i13 < 0 || i13 >= length || (i4 = i13 + length) >= length2) {
                    i7 = i7;
                    length2 = length2;
                    oVar2 = oVar2;
                    if (z4) {
                        this.d = null;
                        this.b = aiVarArr3;
                        this.f = (length << 1) - (length >>> 1);
                        return;
                    }
                    concurrentHashMapV8 = this;
                    z2 = true;
                    do {
                        unsafe = n;
                        j2 = o;
                        i2 = concurrentHashMapV8.f;
                        i3 = i2 + 1;
                    } while (!unsafe.compareAndSwapInt(this, j2, i2, i3));
                    i12 = -1;
                    if (i3 == -1) {
                        i13 = length;
                        z3 = true;
                        z4 = true;
                    } else {
                        return;
                    }
                } else {
                    ai<K, V> a2 = a(aiVarArr4, i13);
                    if (a2 != null) {
                        int i15 = a2.b;
                        if (i15 == i12) {
                            i13 = i13;
                            z3 = z2;
                            i7 = i7;
                            length2 = length2;
                            i12 = i12;
                            z2 = z3;
                            concurrentHashMapV8 = concurrentHashMapV8;
                            oVar2 = oVar2;
                        } else {
                            synchronized (a2) {
                                if (a(aiVarArr4, i13) != a2) {
                                    i7 = i7;
                                    length2 = length2;
                                    oVar2 = oVar2;
                                } else if (i15 >= 0) {
                                    int i16 = i15 & length;
                                    ai<K, V> aiVar5 = a2;
                                    for (ai<K, V> aiVar6 = a2.e; aiVar6 != null; aiVar6 = aiVar6.e) {
                                        int i17 = aiVar6.b & length;
                                        if (i17 != i16) {
                                            aiVar5 = aiVar6;
                                            i16 = i17;
                                        }
                                    }
                                    if (i16 == 0) {
                                        aiVar3 = null;
                                        aiVar4 = aiVar5;
                                    } else {
                                        aiVar3 = aiVar5;
                                    }
                                    ai<K, V> aiVar7 = a2;
                                    while (aiVar7 != aiVar5) {
                                        int i18 = aiVar7.b;
                                        K k2 = aiVar7.c;
                                        V v2 = aiVar7.d;
                                        if ((i18 & length) == 0) {
                                            aiVar5 = aiVar5;
                                            aiVar4 = new ai<>(i18, k2, v2, aiVar4);
                                        } else {
                                            aiVar5 = aiVar5;
                                            aiVar3 = new ai<>(i18, k2, v2, aiVar3);
                                        }
                                        aiVar7 = aiVar7.e;
                                        i7 = i7;
                                    }
                                    i7 = i7;
                                    a(aiVarArr3, i13, aiVar4);
                                    a(aiVarArr3, i4, aiVar3);
                                    a(aiVarArr4, i13, oVar2);
                                    length2 = length2;
                                    oVar2 = oVar2;
                                    z3 = true;
                                } else {
                                    i7 = i7;
                                    if (a2 instanceof at) {
                                        at atVar = (at) a2;
                                        ai aiVar8 = atVar.f;
                                        au<K, V> auVar = null;
                                        au<K, V> auVar2 = null;
                                        au<K, V> auVar3 = null;
                                        int i19 = 0;
                                        au<K, V> auVar4 = null;
                                        int i20 = 0;
                                        while (aiVar8 != null) {
                                            int i21 = aiVar8.b;
                                            au<K, V> auVar5 = new au<>(i21, aiVar8.c, aiVar8.d, null, null);
                                            if ((i21 & length) == 0) {
                                                auVar5.h = auVar4;
                                                if (auVar4 == null) {
                                                    auVar = auVar5;
                                                } else {
                                                    auVar4.e = auVar5;
                                                }
                                                i20++;
                                                auVar4 = auVar5;
                                            } else {
                                                auVar5.h = auVar3;
                                                if (auVar3 == null) {
                                                    auVar2 = auVar5;
                                                } else {
                                                    auVar3.e = auVar5;
                                                }
                                                i19++;
                                                auVar3 = auVar5;
                                            }
                                            aiVar8 = aiVar8.e;
                                            length2 = length2;
                                            oVar2 = oVar2;
                                        }
                                        length2 = length2;
                                        if (i20 <= 6) {
                                            aiVar = a((ai) auVar);
                                        } else {
                                            aiVar = i19 != 0 ? new at(auVar) : atVar;
                                        }
                                        if (i19 <= 6) {
                                            aiVar2 = a((ai) auVar2);
                                        } else {
                                            aiVar2 = i20 != 0 ? new at(auVar2) : atVar;
                                        }
                                        a(aiVarArr3, i13, aiVar);
                                        a(aiVarArr3, i4, aiVar2);
                                        oVar2 = oVar2;
                                        aiVarArr4 = aiVarArr;
                                        a(aiVarArr4, i13, oVar2);
                                        z3 = true;
                                    } else {
                                        length2 = length2;
                                        oVar2 = oVar2;
                                    }
                                }
                            }
                            i13 = i13;
                            i12 = -1;
                            concurrentHashMapV8 = this;
                            z2 = true;
                        }
                    } else if (a(aiVarArr4, i13, (ai) null, oVar2)) {
                        a(aiVarArr3, i13, (ai) null);
                        a(aiVarArr3, i4, (ai) null);
                        i13 = i13;
                        z3 = z2;
                        i7 = i7;
                        length2 = length2;
                        i12 = i12;
                        z2 = z3;
                        concurrentHashMapV8 = concurrentHashMapV8;
                        oVar2 = oVar2;
                    } else {
                        i13 = i13;
                        i7 = i7;
                        length2 = length2;
                        i12 = i12;
                        z2 = z2;
                        concurrentHashMapV8 = concurrentHashMapV8;
                        oVar2 = oVar2;
                    }
                }
                i14 = i14;
            } else {
                int i22 = i13 - 1;
                if (i22 >= i14 || z4) {
                    i13 = i22;
                    i14 = i14;
                    z3 = false;
                } else {
                    int i23 = concurrentHashMapV8.g;
                    if (i23 <= concurrentHashMapV8.h) {
                        i13 = i12;
                        z3 = false;
                    } else {
                        Unsafe unsafe2 = n;
                        long j3 = p;
                        int i24 = i23 > i7 ? i23 - i7 : 0;
                        if (unsafe2.compareAndSwapInt(this, j3, i23, i24)) {
                            i13 = i23 - 1;
                            i14 = i24;
                            z3 = false;
                        } else {
                            i13 = i22;
                            i14 = i14;
                        }
                    }
                }
            }
        }
    }

    private final void b(ai<K, V>[] aiVarArr, int i2) {
        int i3;
        if (aiVarArr != null) {
            au<K, V> auVar = null;
            if (aiVarArr.length >= 64) {
                ai<K, V> a2 = a(aiVarArr, i2);
                if (a2 != null && a2.b >= 0) {
                    synchronized (a2) {
                        if (a(aiVarArr, i2) == a2) {
                            ai<K, V> aiVar = a2;
                            au<K, V> auVar2 = null;
                            while (aiVar != null) {
                                au<K, V> auVar3 = new au<>(aiVar.b, aiVar.c, aiVar.d, null, null);
                                auVar3.h = auVar2;
                                if (auVar2 == null) {
                                    auVar = auVar3;
                                } else {
                                    auVar2.e = auVar3;
                                }
                                aiVar = aiVar.e;
                                auVar2 = auVar3;
                            }
                            a(aiVarArr, i2, new at(auVar));
                        }
                    }
                }
            } else if (aiVarArr == this.b && (i3 = this.f) >= 0 && n.compareAndSwapInt(this, o, i3, -2)) {
                a(aiVarArr, (ai[]) null);
            }
        }
    }

    static <K, V> ai<K, V> a(ai<K, V> aiVar) {
        ai<K, V> aiVar2 = null;
        ai<K, V> aiVar3 = null;
        while (aiVar != null) {
            ai<K, V> aiVar4 = new ai<>(aiVar.b, aiVar.c, aiVar.d, null);
            if (aiVar3 == null) {
                aiVar2 = aiVar4;
            } else {
                aiVar3.e = aiVar4;
            }
            aiVar = aiVar.e;
            aiVar3 = aiVar4;
        }
        return aiVar2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class au<K, V> extends ai<K, V> {
        au<K, V> a;
        au<K, V> f;
        au<K, V> g;
        au<K, V> h;
        boolean i;

        au(int i, K k, V v, ai<K, V> aiVar, au<K, V> auVar) {
            super(i, k, v, aiVar);
            this.a = auVar;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ai
        ai<K, V> a(int i, Object obj) {
            return a(i, obj, null);
        }

        final au<K, V> a(int i, Object obj, Class<?> cls) {
            au<K, V> a;
            int a2;
            if (obj == null) {
                return null;
            }
            Class<?> cls2 = cls;
            au<K, V> auVar = this;
            do {
                auVar = auVar.f;
                au<K, V> auVar2 = auVar.g;
                int i2 = auVar.b;
                if (i2 > i) {
                    auVar = auVar;
                    continue;
                } else if (i2 < i) {
                    auVar = auVar2;
                    continue;
                } else {
                    Object obj2 = auVar.c;
                    if (obj2 == obj || (obj2 != null && obj.equals(obj2))) {
                        return auVar;
                    }
                    if (auVar == null && auVar2 == null) {
                        return null;
                    }
                    if ((cls2 != null || (cls2 = ConcurrentHashMapV8.a(obj)) != null) && (a2 = ConcurrentHashMapV8.a(cls2, obj, obj2)) != 0) {
                        if (a2 >= 0) {
                            auVar = auVar2;
                        }
                        continue;
                    } else if (auVar == null) {
                        auVar = auVar2;
                        continue;
                    } else if (auVar2 != null && (a = auVar2.a(i, obj, cls2)) != null) {
                        return a;
                    } else {
                        auVar = auVar;
                        continue;
                    }
                }
            } while (auVar != null);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class at<K, V> extends ai<K, V> {
        static final /* synthetic */ boolean i = !ConcurrentHashMapV8.class.desiredAssertionStatus();
        private static final Unsafe j;
        private static final long k;
        au<K, V> a;
        volatile au<K, V> f;
        volatile Thread g;
        volatile int h;

        static {
            try {
                j = ConcurrentHashMapV8.d();
                k = j.objectFieldOffset(at.class.getDeclaredField("lockState"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        at(au<K, V> auVar) {
            super(-2, null, null, null);
            int i2;
            this.f = auVar;
            auVar = null;
            while (auVar != null) {
                auVar = (au) auVar.e;
                auVar.g = null;
                auVar.f = null;
                if (auVar == null) {
                    auVar.a = null;
                    auVar.i = false;
                } else {
                    Object obj = auVar.c;
                    int i3 = auVar.b;
                    Class<?> cls = null;
                    au<K, V> auVar2 = auVar;
                    while (true) {
                        int i4 = auVar2.b;
                        if (i4 > i3) {
                            i2 = -1;
                        } else if (i4 < i3) {
                            i2 = 1;
                        } else {
                            i2 = (cls == null && (cls = ConcurrentHashMapV8.a(obj)) == null) ? 0 : ConcurrentHashMapV8.a(cls, obj, auVar2.c);
                        }
                        au<K, V> auVar3 = i2 <= 0 ? auVar2.f : auVar2.g;
                        if (auVar3 == null) {
                            break;
                        }
                        auVar2 = auVar3;
                    }
                    auVar.a = auVar2;
                    if (i2 <= 0) {
                        auVar2.f = auVar;
                    } else {
                        auVar2.g = auVar;
                    }
                    auVar = c(auVar, auVar);
                }
            }
            this.a = auVar;
        }

        private final void a() {
            if (!j.compareAndSwapInt(this, k, 0, 1)) {
                c();
            }
        }

        private final void b() {
            this.h = 0;
        }

        private final void c() {
            boolean z = false;
            while (true) {
                int i2 = this.h;
                if ((i2 & 1) == 0) {
                    if (j.compareAndSwapInt(this, k, i2, 1)) {
                        break;
                    }
                } else if ((i2 & 2) == 0) {
                    if (j.compareAndSwapInt(this, k, i2, i2 | 2)) {
                        this.g = Thread.currentThread();
                        z = true;
                    }
                } else if (z) {
                    LockSupport.park(this);
                }
            }
            if (z) {
                this.g = null;
            }
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ai
        final ai<K, V> a(int i2, Object obj) {
            Unsafe unsafe;
            long j2;
            int i3;
            Thread thread;
            K k2;
            au<K, V> auVar = null;
            if (obj != null) {
                for (ai<K, V> aiVar = this.f; aiVar != null; aiVar = aiVar.e) {
                    int i4 = this.h;
                    if ((i4 & 3) != 0) {
                        if (aiVar.b == i2 && ((k2 = aiVar.c) == obj || (k2 != null && obj.equals(k2)))) {
                            return aiVar;
                        }
                    } else if (j.compareAndSwapInt(this, k, i4, i4 + 4)) {
                        try {
                            au<K, V> auVar2 = this.a;
                            if (auVar2 != null) {
                                auVar = auVar2.a(i2, obj, null);
                            }
                            return auVar;
                        } finally {
                            do {
                                unsafe = j;
                                j2 = k;
                                i3 = this.h;
                            } while (!unsafe.compareAndSwapInt(this, j2, i3, i3 - 4));
                            if (i3 == 6 && (thread = this.g) != null) {
                                LockSupport.unpark(thread);
                            }
                        }
                    }
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x008e, code lost:
            if (io.netty.util.internal.chmv8.ConcurrentHashMapV8.at.i != false) goto L_0x009f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0096, code lost:
            if (b(r12.a) == false) goto L_0x0099;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x009e, code lost:
            throw new java.lang.AssertionError();
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x009f, code lost:
            return null;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final io.netty.util.internal.chmv8.ConcurrentHashMapV8.au<K, V> a(int r13, K r14, V r15) {
            /*
                r12 = this;
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r0 = r12.a
                r1 = 0
                r2 = r1
            L_0x0004:
                if (r0 != 0) goto L_0x0017
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au r0 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$au
                r7 = 0
                r8 = 0
                r3 = r0
                r4 = r13
                r5 = r14
                r6 = r15
                r3.<init>(r4, r5, r6, r7, r8)
                r12.a = r0
                r12.f = r0
                goto L_0x008c
            L_0x0017:
                int r3 = r0.b
                r4 = -1
                r8 = 1
                if (r3 <= r13) goto L_0x001f
                r9 = r4
                goto L_0x0054
            L_0x001f:
                if (r3 >= r13) goto L_0x0023
                r9 = r8
                goto L_0x0054
            L_0x0023:
                java.lang.Object r3 = r0.c
                if (r3 == r14) goto L_0x00a8
                if (r3 == 0) goto L_0x0031
                boolean r5 = r14.equals(r3)
                if (r5 == 0) goto L_0x0031
                goto L_0x00a8
            L_0x0031:
                if (r2 != 0) goto L_0x0039
                java.lang.Class r2 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(r14)
                if (r2 == 0) goto L_0x003f
            L_0x0039:
                int r3 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(r2, r14, r3)
                if (r3 != 0) goto L_0x0053
            L_0x003f:
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r3 = r0.f
                if (r3 != 0) goto L_0x0045
                r9 = r8
                goto L_0x0054
            L_0x0045:
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r3 = r0.g
                if (r3 == 0) goto L_0x0051
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au r3 = r3.a(r13, r14, r2)
                if (r3 != 0) goto L_0x0050
                goto L_0x0051
            L_0x0050:
                return r3
            L_0x0051:
                r9 = r4
                goto L_0x0054
            L_0x0053:
                r9 = r3
            L_0x0054:
                if (r9 >= 0) goto L_0x0059
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r3 = r0.f
                goto L_0x005b
            L_0x0059:
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r3 = r0.g
            L_0x005b:
                if (r3 != 0) goto L_0x00a5
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r10 = r12.f
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au r11 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$au
                r2 = r11
                r3 = r13
                r4 = r14
                r5 = r15
                r6 = r10
                r7 = r0
                r2.<init>(r3, r4, r5, r6, r7)
                r12.f = r11
                if (r10 == 0) goto L_0x0070
                r10.h = r11
            L_0x0070:
                if (r9 >= 0) goto L_0x0075
                r0.f = r11
                goto L_0x0077
            L_0x0075:
                r0.g = r11
            L_0x0077:
                boolean r13 = r0.i
                if (r13 != 0) goto L_0x007e
                r11.i = r8
                goto L_0x008c
            L_0x007e:
                r12.a()
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r13 = r12.a     // Catch: all -> 0x00a0
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au r13 = c(r13, r11)     // Catch: all -> 0x00a0
                r12.a = r13     // Catch: all -> 0x00a0
                r12.b()
            L_0x008c:
                boolean r13 = io.netty.util.internal.chmv8.ConcurrentHashMapV8.at.i
                if (r13 != 0) goto L_0x009f
                io.netty.util.internal.chmv8.ConcurrentHashMapV8$au<K, V> r13 = r12.a
                boolean r13 = b(r13)
                if (r13 == 0) goto L_0x0099
                goto L_0x009f
            L_0x0099:
                java.lang.AssertionError r13 = new java.lang.AssertionError
                r13.<init>()
                throw r13
            L_0x009f:
                return r1
            L_0x00a0:
                r13 = move-exception
                r12.b()
                throw r13
            L_0x00a5:
                r0 = r3
                goto L_0x0004
            L_0x00a8:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.at.a(int, java.lang.Object, java.lang.Object):io.netty.util.internal.chmv8.ConcurrentHashMapV8$au");
        }

        /* JADX WARN: Finally extract failed */
        final boolean a(au<K, V> auVar) {
            au<K, V> auVar2;
            au<K, V> auVar3;
            au<K, V> auVar4 = (au) auVar.e;
            au<K, V> auVar5 = auVar.h;
            if (auVar5 == null) {
                this.f = auVar4;
            } else {
                auVar5.e = auVar4;
            }
            if (auVar4 != null) {
                auVar4.h = auVar5;
            }
            if (this.f == null) {
                this.a = null;
                return true;
            }
            au<K, V> auVar6 = this.a;
            if (auVar6 == null || auVar6.g == null || (auVar2 = auVar6.f) == null || auVar2.f == null) {
                return true;
            }
            a();
            try {
                au<K, V> auVar7 = auVar.f;
                au<K, V> auVar8 = auVar.g;
                if (auVar7 != null && auVar8 != null) {
                    au<K, V> auVar9 = auVar8;
                    while (true) {
                        au<K, V> auVar10 = auVar9.f;
                        if (auVar10 == null) {
                            break;
                        }
                        auVar9 = auVar10;
                    }
                    boolean z = auVar9.i;
                    auVar9.i = auVar.i;
                    auVar.i = z;
                    auVar7 = auVar9.g;
                    au<K, V> auVar11 = auVar.a;
                    if (auVar9 == auVar8) {
                        auVar.a = auVar9;
                        auVar9.g = auVar;
                    } else {
                        au<K, V> auVar12 = auVar9.a;
                        auVar.a = auVar12;
                        if (auVar12 != null) {
                            if (auVar9 == auVar12.f) {
                                auVar12.f = auVar;
                            } else {
                                auVar12.g = auVar;
                            }
                        }
                        auVar9.g = auVar8;
                        auVar8.a = auVar9;
                    }
                    auVar.f = null;
                    auVar9.f = auVar7;
                    auVar7.a = auVar9;
                    auVar.g = auVar7;
                    if (auVar7 != null) {
                        auVar7.a = auVar;
                    }
                    auVar9.a = auVar11;
                    if (auVar11 == null) {
                        auVar6 = auVar9;
                    } else if (auVar == auVar11.f) {
                        auVar11.f = auVar9;
                    } else {
                        auVar11.g = auVar9;
                    }
                    if (auVar7 == null) {
                        auVar7 = auVar;
                    }
                } else if (auVar7 == null) {
                    auVar7 = auVar8 != null ? auVar8 : auVar;
                }
                if (auVar7 != auVar) {
                    au<K, V> auVar13 = auVar.a;
                    auVar7.a = auVar13;
                    if (auVar13 == null) {
                        auVar6 = auVar7;
                    } else if (auVar == auVar13.f) {
                        auVar13.f = auVar7;
                    } else {
                        auVar13.g = auVar7;
                    }
                    auVar.a = null;
                    auVar.g = null;
                    auVar.f = null;
                }
                if (!auVar.i) {
                    auVar6 = d(auVar6, auVar7);
                }
                this.a = auVar6;
                if (auVar == auVar7 && (auVar3 = auVar.a) != null) {
                    if (auVar == auVar3.f) {
                        auVar3.f = null;
                    } else if (auVar == auVar3.g) {
                        auVar3.g = null;
                    }
                    auVar.a = null;
                }
                b();
                if (i || b(this.a)) {
                    return false;
                }
                throw new AssertionError();
            } catch (Throwable th) {
                b();
                throw th;
            }
        }

        static <K, V> au<K, V> a(au<K, V> auVar, au<K, V> auVar2) {
            au<K, V> auVar3;
            if (!(auVar2 == null || (auVar3 = auVar2.g) == null)) {
                au<K, V> auVar4 = auVar3.f;
                auVar2.g = auVar4;
                if (auVar4 != null) {
                    auVar4.a = auVar2;
                }
                au<K, V> auVar5 = auVar2.a;
                auVar3.a = auVar5;
                if (auVar5 == null) {
                    auVar3.i = false;
                    auVar = auVar3;
                } else if (auVar5.f == auVar2) {
                    auVar5.f = auVar3;
                } else {
                    auVar5.g = auVar3;
                }
                auVar3.f = auVar2;
                auVar2.a = auVar3;
            }
            return auVar;
        }

        static <K, V> au<K, V> b(au<K, V> auVar, au<K, V> auVar2) {
            au<K, V> auVar3;
            if (!(auVar2 == null || (auVar3 = auVar2.f) == null)) {
                au<K, V> auVar4 = auVar3.g;
                auVar2.f = auVar4;
                if (auVar4 != null) {
                    auVar4.a = auVar2;
                }
                au<K, V> auVar5 = auVar2.a;
                auVar3.a = auVar5;
                if (auVar5 == null) {
                    auVar3.i = false;
                    auVar = auVar3;
                } else if (auVar5.g == auVar2) {
                    auVar5.g = auVar3;
                } else {
                    auVar5.f = auVar3;
                }
                auVar3.g = auVar2;
                auVar2.a = auVar3;
            }
            return auVar;
        }

        static <K, V> au<K, V> c(au<K, V> auVar, au<K, V> auVar2) {
            au<K, V> auVar3;
            au<K, V> auVar4;
            au<K, V> auVar5;
            auVar2.i = true;
            while (true) {
                auVar2 = auVar2.a;
                if (auVar2 == null) {
                    auVar2.i = false;
                    return auVar2;
                } else if (!auVar2.i || (auVar3 = auVar2.a) == null) {
                    break;
                } else {
                    au<K, V> auVar6 = auVar3.f;
                    if (auVar2 == auVar6) {
                        au<K, V> auVar7 = auVar3.g;
                        if (auVar7 == null || !auVar7.i) {
                            if (auVar2 == auVar2.g) {
                                auVar = a(auVar, auVar2);
                                auVar4 = auVar2.a;
                                auVar3 = auVar4 == null ? null : auVar4.a;
                            } else {
                                auVar2 = auVar2;
                                auVar4 = auVar2;
                            }
                            if (auVar4 != null) {
                                auVar4.i = false;
                                if (auVar3 != null) {
                                    auVar3.i = true;
                                    auVar = b(auVar, auVar3);
                                    auVar2 = auVar2;
                                }
                            }
                        } else {
                            auVar7.i = false;
                            auVar2.i = false;
                            auVar3.i = true;
                            auVar2 = auVar3;
                        }
                    } else if (auVar6 == null || !auVar6.i) {
                        if (auVar2 == auVar2.f) {
                            auVar = b(auVar, auVar2);
                            auVar5 = auVar2.a;
                            auVar3 = auVar5 == null ? null : auVar5.a;
                        } else {
                            auVar2 = auVar2;
                            auVar5 = auVar2;
                        }
                        if (auVar5 != null) {
                            auVar5.i = false;
                            if (auVar3 != null) {
                                auVar3.i = true;
                                auVar = a(auVar, auVar3);
                                auVar2 = auVar2;
                            }
                        }
                    } else {
                        auVar6.i = false;
                        auVar2.i = false;
                        auVar3.i = true;
                        auVar2 = auVar3;
                    }
                }
            }
            return auVar;
        }

        static <K, V> au<K, V> d(au<K, V> auVar, au<K, V> auVar2) {
            while (auVar2 != null && auVar2 != auVar) {
                au<K, V> auVar3 = auVar2.a;
                if (auVar3 == null) {
                    auVar2.i = false;
                    return auVar2;
                } else if (auVar2.i) {
                    auVar2.i = false;
                    return auVar;
                } else {
                    au<K, V> auVar4 = auVar3.f;
                    auVar4 = null;
                    if (auVar4 == auVar2) {
                        auVar4 = auVar3.g;
                        if (auVar4 != null && auVar4.i) {
                            auVar4.i = false;
                            auVar3.i = true;
                            auVar = a(auVar, auVar3);
                            auVar3 = auVar2.a;
                            auVar4 = auVar3 == null ? null : auVar3.g;
                        }
                        if (auVar4 == null) {
                            auVar2 = auVar3;
                        } else {
                            au<K, V> auVar5 = auVar4.f;
                            au<K, V> auVar6 = auVar4.g;
                            if ((auVar6 == null || !auVar6.i) && (auVar5 == null || !auVar5.i)) {
                                auVar4.i = true;
                                auVar2 = auVar3;
                            } else {
                                if (auVar6 == null || !auVar6.i) {
                                    if (auVar5 != null) {
                                        auVar5.i = false;
                                    }
                                    auVar4.i = true;
                                    auVar = b(auVar, auVar4);
                                    auVar3 = auVar2.a;
                                    if (auVar3 != null) {
                                        auVar4 = auVar3.g;
                                    }
                                }
                                if (auVar4 != null) {
                                    auVar4.i = auVar3 == null ? false : auVar3.i;
                                    au<K, V> auVar7 = auVar4.g;
                                    if (auVar7 != null) {
                                        auVar7.i = false;
                                    }
                                }
                                if (auVar3 != null) {
                                    auVar3.i = false;
                                    auVar = a(auVar, auVar3);
                                }
                                auVar2 = auVar;
                            }
                        }
                    } else {
                        if (auVar4 != null && auVar4.i) {
                            auVar4.i = false;
                            auVar3.i = true;
                            auVar = b(auVar, auVar3);
                            auVar3 = auVar2.a;
                            auVar4 = auVar3 == null ? null : auVar3.f;
                        }
                        if (auVar4 == null) {
                            auVar2 = auVar3;
                        } else {
                            au<K, V> auVar8 = auVar4.f;
                            au<K, V> auVar9 = auVar4.g;
                            if ((auVar8 == null || !auVar8.i) && (auVar9 == null || !auVar9.i)) {
                                auVar4.i = true;
                                auVar2 = auVar3;
                            } else {
                                if (auVar8 == null || !auVar8.i) {
                                    if (auVar9 != null) {
                                        auVar9.i = false;
                                    }
                                    auVar4.i = true;
                                    auVar = a(auVar, auVar4);
                                    auVar3 = auVar2.a;
                                    if (auVar3 != null) {
                                        auVar4 = auVar3.f;
                                    }
                                }
                                if (auVar4 != null) {
                                    auVar4.i = auVar3 == null ? false : auVar3.i;
                                    au<K, V> auVar10 = auVar4.f;
                                    if (auVar10 != null) {
                                        auVar10.i = false;
                                    }
                                }
                                if (auVar3 != null) {
                                    auVar3.i = false;
                                    auVar = b(auVar, auVar3);
                                }
                                auVar2 = auVar;
                            }
                        }
                    }
                }
            }
            return auVar;
        }

        static <K, V> boolean b(au<K, V> auVar) {
            au<K, V> auVar2 = auVar.a;
            au<K, V> auVar3 = auVar.f;
            au<K, V> auVar4 = auVar.g;
            au<K, V> auVar5 = auVar.h;
            au auVar6 = (au) auVar.e;
            if (auVar5 != null && auVar5.e != auVar) {
                return false;
            }
            if (auVar6 != null && auVar6.h != auVar) {
                return false;
            }
            if (auVar2 != null && auVar != auVar2.f && auVar != auVar2.g) {
                return false;
            }
            if (auVar3 != null && (auVar3.a != auVar || auVar3.b > auVar.b)) {
                return false;
            }
            if (auVar4 != null && (auVar4.a != auVar || auVar4.b < auVar.b)) {
                return false;
            }
            if (auVar.i && auVar3 != null && auVar3.i && auVar4 != null && auVar4.i) {
                return false;
            }
            if (auVar3 == null || b(auVar3)) {
                return auVar4 == null || b(auVar4);
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class as<K, V> {
        ai<K, V>[] c;
        ai<K, V> d = null;
        int e;
        int f;
        int g;
        final int h;

        as(ai<K, V>[] aiVarArr, int i, int i2, int i3) {
            this.c = aiVarArr;
            this.h = i;
            this.e = i2;
            this.f = i2;
            this.g = i3;
        }

        final ai<K, V> b() {
            ai<K, V>[] aiVarArr;
            int length;
            int i;
            ai<K, V> aiVar = this.d;
            if (aiVar != null) {
                aiVar = aiVar.e;
            }
            while (aiVar == null) {
                if (this.f >= this.g || (aiVarArr = this.c) == null || (length = aiVarArr.length) <= (i = this.e) || i < 0) {
                    this.d = null;
                    return null;
                }
                aiVar = ConcurrentHashMapV8.a(aiVarArr, i);
                if (aiVar != null && aiVar.b < 0) {
                    if (aiVar instanceof o) {
                        this.c = ((o) aiVar).a;
                        aiVar = null;
                    } else {
                        aiVar = aiVar instanceof at ? ((at) aiVar).f : null;
                    }
                }
                int i2 = this.e + this.h;
                this.e = i2;
                if (i2 >= length) {
                    int i3 = this.f + 1;
                    this.f = i3;
                    this.e = i3;
                }
            }
            this.d = aiVar;
            return aiVar;
        }
    }

    /* loaded from: classes4.dex */
    static class a<K, V> extends as<K, V> {
        final ConcurrentHashMapV8<K, V> a;
        ai<K, V> b;

        a(ai<K, V>[] aiVarArr, int i, int i2, int i3, ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(aiVarArr, i, i2, i3);
            this.a = concurrentHashMapV8;
            b();
        }

        public final boolean hasNext() {
            return this.d != null;
        }

        public final boolean hasMoreElements() {
            return this.d != null;
        }

        public final void remove() {
            ai<K, V> aiVar = this.b;
            if (aiVar != null) {
                this.b = null;
                this.a.a(aiVar.c, (K) null, (Object) null);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class p<K, V> extends a<K, V> implements Enumeration<K>, Iterator<K> {
        p(ai<K, V>[] aiVarArr, int i, int i2, int i3, ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(aiVarArr, i, i2, i3, concurrentHashMapV8);
        }

        @Override // java.util.Iterator
        public final K next() {
            ai aiVar = this.d;
            if (aiVar != null) {
                K k = aiVar.c;
                this.b = aiVar;
                b();
                return k;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Enumeration
        public final K nextElement() {
            return next();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class av<K, V> extends a<K, V> implements Enumeration<V>, Iterator<V> {
        av(ai<K, V>[] aiVarArr, int i, int i2, int i3, ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(aiVarArr, i, i2, i3, concurrentHashMapV8);
        }

        @Override // java.util.Iterator
        public final V next() {
            ai aiVar = this.d;
            if (aiVar != null) {
                V v = aiVar.d;
                this.b = aiVar;
                b();
                return v;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Enumeration
        public final V nextElement() {
            return next();
        }
    }

    /* loaded from: classes4.dex */
    static final class e<K, V> extends a<K, V> implements Iterator<Map.Entry<K, V>> {
        e(ai<K, V>[] aiVarArr, int i, int i2, int i3, ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(aiVarArr, i, i2, i3, concurrentHashMapV8);
        }

        /* renamed from: a */
        public final Map.Entry<K, V> next() {
            ai aiVar = this.d;
            if (aiVar != null) {
                K k = aiVar.c;
                V v = aiVar.d;
                this.b = aiVar;
                b();
                return new r(k, v, this.a);
            }
            throw new NoSuchElementException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class r<K, V> implements Map.Entry<K, V> {
        final K a;
        V b;
        final ConcurrentHashMapV8<K, V> c;

        r(K k, V v, ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            this.a = k;
            this.b = v;
            this.c = concurrentHashMapV8;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public String toString() {
            return this.a + "=" + this.b;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            K k;
            V v;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && (key == (k = this.a) || key.equals(k)) && (value == (v = this.b) || value.equals(v));
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (v != null) {
                V v2 = this.b;
                this.b = v;
                this.c.put(this.a, v);
                return v2;
            }
            throw new NullPointerException();
        }
    }

    /* loaded from: classes4.dex */
    static final class q<K, V> extends as<K, V> implements ConcurrentHashMapSpliterator<K> {
        long a;

        q(ai<K, V>[] aiVarArr, int i, int i2, int i3, long j) {
            super(aiVarArr, i, i2, i3);
            this.a = j;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ConcurrentHashMapSpliterator
        public ConcurrentHashMapSpliterator<K> trySplit() {
            int i = this.f;
            int i2 = this.g;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            ai[] aiVarArr = this.c;
            int i4 = this.h;
            this.g = i3;
            long j = this.a >>> 1;
            this.a = j;
            return new q(aiVarArr, i4, i3, i2, j);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ConcurrentHashMapSpliterator
        public void forEachRemaining(Action<? super K> action) {
            if (action != null) {
                while (true) {
                    ai<K, V> b = b();
                    if (b != null) {
                        action.apply((K) b.c);
                    } else {
                        return;
                    }
                }
            } else {
                throw new NullPointerException();
            }
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ConcurrentHashMapSpliterator
        public boolean tryAdvance(Action<? super K> action) {
            if (action != null) {
                ai<K, V> b = b();
                if (b == null) {
                    return false;
                }
                action.apply((K) b.c);
                return true;
            }
            throw new NullPointerException();
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.ConcurrentHashMapSpliterator
        public long estimateSize() {
            return this.a;
        }
    }

    final int a(long j2) {
        if (j2 == Long.MAX_VALUE) {
            return 0;
        }
        long a2 = a();
        if (a2 <= 1 || a2 < j2) {
            return 0;
        }
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism() << 2;
        if (j2 <= 0) {
            return commonPoolParallelism;
        }
        long j3 = a2 / j2;
        return j3 >= ((long) commonPoolParallelism) ? commonPoolParallelism : (int) j3;
    }

    public void forEach(long j2, BiAction<? super K, ? super V> biAction) {
        if (biAction != null) {
            new i(null, a(j2), 0, 0, this.b, biAction).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEach(long j2, BiFun<? super K, ? super V, ? extends U> biFun, Action<? super U> action) {
        if (biFun == null || action == null) {
            throw new NullPointerException();
        }
        new l(null, a(j2), 0, 0, this.b, biFun, action).invoke();
    }

    public <U> U search(long j2, BiFun<? super K, ? super V, ? extends U> biFun) {
        if (biFun != null) {
            return new ap(null, a(j2), 0, 0, this.b, biFun, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduce(long j2, BiFun<? super K, ? super V, ? extends U> biFun, BiFun<? super U, ? super U, ? extends U> biFun2) {
        if (biFun != null && biFun2 != null) {
            return new aa(null, a(j2), 0, 0, this.b, null, biFun, biFun2).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceToDouble(long j2, ObjectByObjectToDouble<? super K, ? super V> objectByObjectToDouble, double d2, DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectByObjectToDouble != null && doubleByDoubleToDouble != null) {
            return new ab(null, a(j2), 0, 0, this.b, null, objectByObjectToDouble, d2, doubleByDoubleToDouble).invoke().doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceToLong(long j2, ObjectByObjectToLong<? super K, ? super V> objectByObjectToLong, long j3, LongByLongToLong longByLongToLong) {
        if (objectByObjectToLong != null && longByLongToLong != null) {
            return new ad(null, a(j2), 0, 0, this.b, null, objectByObjectToLong, j3, longByLongToLong).invoke().longValue();
        }
        throw new NullPointerException();
    }

    public int reduceToInt(long j2, ObjectByObjectToInt<? super K, ? super V> objectByObjectToInt, int i2, IntByIntToInt intByIntToInt) {
        if (objectByObjectToInt != null && intByIntToInt != null) {
            return new ac(null, a(j2), 0, 0, this.b, null, objectByObjectToInt, i2, intByIntToInt).invoke().intValue();
        }
        throw new NullPointerException();
    }

    public void forEachKey(long j2, Action<? super K> action) {
        if (action != null) {
            new h(null, a(j2), 0, 0, this.b, action).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachKey(long j2, Fun<? super K, ? extends U> fun, Action<? super U> action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new k(null, a(j2), 0, 0, this.b, fun, action).invoke();
    }

    public <U> U searchKeys(long j2, Fun<? super K, ? extends U> fun) {
        if (fun != null) {
            return new ao(null, a(j2), 0, 0, this.b, fun, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public K reduceKeys(long j2, BiFun<? super K, ? super K, ? extends K> biFun) {
        if (biFun != null) {
            return new ak(null, a(j2), 0, 0, this.b, null, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceKeys(long j2, Fun<? super K, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
        if (fun != null && biFun != null) {
            return new w(null, a(j2), 0, 0, this.b, null, fun, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceKeysToDouble(long j2, ObjectToDouble<? super K> objectToDouble, double d2, DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble != null && doubleByDoubleToDouble != null) {
            return new x(null, a(j2), 0, 0, this.b, null, objectToDouble, d2, doubleByDoubleToDouble).invoke().doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceKeysToLong(long j2, ObjectToLong<? super K> objectToLong, long j3, LongByLongToLong longByLongToLong) {
        if (objectToLong != null && longByLongToLong != null) {
            return new z(null, a(j2), 0, 0, this.b, null, objectToLong, j3, longByLongToLong).invoke().longValue();
        }
        throw new NullPointerException();
    }

    public int reduceKeysToInt(long j2, ObjectToInt<? super K> objectToInt, int i2, IntByIntToInt intByIntToInt) {
        if (objectToInt != null && intByIntToInt != null) {
            return new y(null, a(j2), 0, 0, this.b, null, objectToInt, i2, intByIntToInt).invoke().intValue();
        }
        throw new NullPointerException();
    }

    public void forEachValue(long j2, Action<? super V> action) {
        if (action != null) {
            new n(null, a(j2), 0, 0, this.b, action).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachValue(long j2, Fun<? super V, ? extends U> fun, Action<? super U> action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new m(null, a(j2), 0, 0, this.b, fun, action).invoke();
    }

    public <U> U searchValues(long j2, Fun<? super V, ? extends U> fun) {
        if (fun != null) {
            return new aq(null, a(j2), 0, 0, this.b, fun, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public V reduceValues(long j2, BiFun<? super V, ? super V, ? extends V> biFun) {
        if (biFun != null) {
            return new al(null, a(j2), 0, 0, this.b, null, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceValues(long j2, Fun<? super V, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
        if (fun != null && biFun != null) {
            return new ae(null, a(j2), 0, 0, this.b, null, fun, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceValuesToDouble(long j2, ObjectToDouble<? super V> objectToDouble, double d2, DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble != null && doubleByDoubleToDouble != null) {
            return new af(null, a(j2), 0, 0, this.b, null, objectToDouble, d2, doubleByDoubleToDouble).invoke().doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceValuesToLong(long j2, ObjectToLong<? super V> objectToLong, long j3, LongByLongToLong longByLongToLong) {
        if (objectToLong != null && longByLongToLong != null) {
            return new ah(null, a(j2), 0, 0, this.b, null, objectToLong, j3, longByLongToLong).invoke().longValue();
        }
        throw new NullPointerException();
    }

    public int reduceValuesToInt(long j2, ObjectToInt<? super V> objectToInt, int i2, IntByIntToInt intByIntToInt) {
        if (objectToInt != null && intByIntToInt != null) {
            return new ag(null, a(j2), 0, 0, this.b, null, objectToInt, i2, intByIntToInt).invoke().intValue();
        }
        throw new NullPointerException();
    }

    public void forEachEntry(long j2, Action<? super Map.Entry<K, V>> action) {
        if (action != null) {
            new g(null, a(j2), 0, 0, this.b, action).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachEntry(long j2, Fun<Map.Entry<K, V>, ? extends U> fun, Action<? super U> action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new j(null, a(j2), 0, 0, this.b, fun, action).invoke();
    }

    public <U> U searchEntries(long j2, Fun<Map.Entry<K, V>, ? extends U> fun) {
        if (fun != null) {
            return new an(null, a(j2), 0, 0, this.b, fun, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public Map.Entry<K, V> reduceEntries(long j2, BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFun) {
        if (biFun != null) {
            return new aj(null, a(j2), 0, 0, this.b, null, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceEntries(long j2, Fun<Map.Entry<K, V>, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
        if (fun != null && biFun != null) {
            return new s(null, a(j2), 0, 0, this.b, null, fun, biFun).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceEntriesToDouble(long j2, ObjectToDouble<Map.Entry<K, V>> objectToDouble, double d2, DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble != null && doubleByDoubleToDouble != null) {
            return new t(null, a(j2), 0, 0, this.b, null, objectToDouble, d2, doubleByDoubleToDouble).invoke().doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceEntriesToLong(long j2, ObjectToLong<Map.Entry<K, V>> objectToLong, long j3, LongByLongToLong longByLongToLong) {
        if (objectToLong != null && longByLongToLong != null) {
            return new v(null, a(j2), 0, 0, this.b, null, objectToLong, j3, longByLongToLong).invoke().longValue();
        }
        throw new NullPointerException();
    }

    public int reduceEntriesToInt(long j2, ObjectToInt<Map.Entry<K, V>> objectToInt, int i2, IntByIntToInt intByIntToInt) {
        if (objectToInt != null && intByIntToInt != null) {
            return new u(null, a(j2), 0, 0, this.b, null, objectToInt, i2, intByIntToInt).invoke().intValue();
        }
        throw new NullPointerException();
    }

    /* loaded from: classes4.dex */
    static abstract class c<K, V, E> implements Serializable, Collection<E> {
        private static final long serialVersionUID = 7249069246763182397L;
        final ConcurrentHashMapV8<K, V> map;

        public abstract boolean contains(Object obj);

        public abstract Iterator<E> iterator();

        public abstract boolean remove(Object obj);

        c(ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            this.map = concurrentHashMapV8;
        }

        public ConcurrentHashMapV8<K, V> getMap() {
            return this.map;
        }

        @Override // java.util.Collection
        public final void clear() {
            this.map.clear();
        }

        @Override // java.util.Collection
        public final int size() {
            return this.map.size();
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            long mappingCount = this.map.mappingCount();
            if (mappingCount <= 2147483639) {
                int i = (int) mappingCount;
                Object[] objArr = new Object[i];
                int i2 = 0;
                Iterator<E> it = iterator();
                while (it.hasNext()) {
                    E next = it.next();
                    if (i2 == i) {
                        int i3 = OsCollectionChangeSet.MAX_ARRAY_LENGTH;
                        if (i < 2147483639) {
                            if (i < 1073741819) {
                                i3 = (i >>> 1) + 1 + i;
                            }
                            objArr = Arrays.copyOf(objArr, i3);
                            i = i3;
                        } else {
                            throw new OutOfMemoryError("Required array size too large");
                        }
                    }
                    i2++;
                    objArr[i2] = next;
                }
                return i2 == i ? objArr : Arrays.copyOf(objArr, i2);
            }
            throw new OutOfMemoryError("Required array size too large");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            long mappingCount = this.map.mappingCount();
            if (mappingCount <= 2147483639) {
                int i = (int) mappingCount;
                T[] tArr2 = tArr.length >= i ? tArr : (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
                int length = tArr2.length;
                int i2 = 0;
                Iterator<E> it = iterator();
                while (it.hasNext()) {
                    E next = it.next();
                    if (i2 == length) {
                        int i3 = OsCollectionChangeSet.MAX_ARRAY_LENGTH;
                        if (length < 2147483639) {
                            if (length < 1073741819) {
                                i3 = (length >>> 1) + 1 + length;
                            }
                            tArr2 = (T[]) Arrays.copyOf(tArr2, i3);
                            length = i3;
                        } else {
                            throw new OutOfMemoryError("Required array size too large");
                        }
                    }
                    i2++;
                    tArr2[i2] = next;
                }
                if (tArr != tArr2 || i2 >= length) {
                    return i2 == length ? tArr2 : (T[]) Arrays.copyOf(tArr2, i2);
                }
                tArr2[i2] = null;
                return tArr2;
            }
            throw new OutOfMemoryError("Required array size too large");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Iterator<E> it = iterator();
            if (it.hasNext()) {
                while (true) {
                    Object next = it.next();
                    if (next == this) {
                        next = "(this Collection)";
                    }
                    sb.append(next);
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(StringUtil.COMMA);
                    sb.append(' ');
                }
            }
            sb.append(']');
            return sb.toString();
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x000c  */
        @Override // java.util.Collection
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean containsAll(java.util.Collection<?> r2) {
            /*
                r1 = this;
                if (r2 == r1) goto L_0x001a
                java.util.Iterator r2 = r2.iterator()
            L_0x0006:
                boolean r0 = r2.hasNext()
                if (r0 == 0) goto L_0x001a
                java.lang.Object r0 = r2.next()
                if (r0 == 0) goto L_0x0018
                boolean r0 = r1.contains(r0)
                if (r0 != 0) goto L_0x0006
            L_0x0018:
                r2 = 0
                return r2
            L_0x001a:
                r2 = 1
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.c.containsAll(java.util.Collection):boolean");
        }

        @Override // java.util.Collection
        public final boolean removeAll(Collection<?> collection) {
            Iterator<E> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection
        public final boolean retainAll(Collection<?> collection) {
            Iterator<E> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
    }

    /* loaded from: classes4.dex */
    public static class KeySetView<K, V> extends c<K, V, K> implements Serializable, Set<K> {
        private static final long serialVersionUID = 7249069246763182397L;
        private final V value;

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c
        public /* bridge */ /* synthetic */ ConcurrentHashMapV8 getMap() {
            return super.getMap();
        }

        KeySetView(ConcurrentHashMapV8<K, V> concurrentHashMapV8, V v) {
            super(concurrentHashMapV8);
            this.value = v;
        }

        public V getMappedValue() {
            return this.value;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return this.map.remove(obj) != null;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            ConcurrentHashMapV8 concurrentHashMapV8 = this.map;
            ai<K, V>[] aiVarArr = concurrentHashMapV8.b;
            int length = aiVarArr == null ? 0 : aiVarArr.length;
            return new p(aiVarArr, length, 0, length, concurrentHashMapV8);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean add(K k) {
            V v = this.value;
            if (v != null) {
                return this.map.a((ConcurrentHashMapV8) k, (K) v, true) == null;
            }
            throw new UnsupportedOperationException();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends K> collection) {
            V v = this.value;
            if (v != null) {
                Iterator<? extends K> it = collection.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (this.map.a((ConcurrentHashMapV8) it.next(), (Object) v, true) == null) {
                        z = true;
                    }
                }
                return z;
            }
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            Iterator<K> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                i += it.next().hashCode();
            }
            return i;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            Set set;
            return (obj instanceof Set) && ((set = (Set) obj) == this || (containsAll(set) && set.containsAll(this)));
        }

        public ConcurrentHashMapSpliterator<K> spliterator166() {
            ConcurrentHashMapV8 concurrentHashMapV8 = this.map;
            long a = concurrentHashMapV8.a();
            ai<K, V>[] aiVarArr = concurrentHashMapV8.b;
            int length = aiVarArr == null ? 0 : aiVarArr.length;
            long j = 0;
            if (a >= 0) {
                j = a;
            }
            return new q(aiVarArr, length, 0, length, j);
        }

        public void forEach(Action<? super K> action) {
            if (action != null) {
                ai<K, V>[] aiVarArr = this.map.b;
                if (aiVarArr != null) {
                    as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
                    while (true) {
                        ai<K, V> b = asVar.b();
                        if (b != null) {
                            action.apply((K) b.c);
                        } else {
                            return;
                        }
                    }
                }
            } else {
                throw new NullPointerException();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class aw<K, V> extends c<K, V, V> implements Serializable, Collection<V> {
        private static final long serialVersionUID = 2249069246763182397L;

        aw(ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(concurrentHashMapV8);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (obj == null) {
                return false;
            }
            Iterator<V> it = iterator();
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator<V> iterator() {
            ConcurrentHashMapV8 concurrentHashMapV8 = this.map;
            ai<K, V>[] aiVarArr = concurrentHashMapV8.b;
            int length = aiVarArr == null ? 0 : aiVarArr.length;
            return new av(aiVarArr, length, 0, length, concurrentHashMapV8);
        }

        @Override // java.util.Collection
        public final boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes4.dex */
    static final class f<K, V> extends c<K, V, Map.Entry<K, V>> implements Serializable, Set<Map.Entry<K, V>> {
        private static final long serialVersionUID = 2249069246763182397L;

        f(ConcurrentHashMapV8<K, V> concurrentHashMapV8) {
            super(concurrentHashMapV8);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            Object value;
            return (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (obj2 = this.map.get(key)) == null || (value = entry.getValue()) == null || (value != obj2 && !value.equals(obj2))) ? false : true;
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && this.map.remove(key, value);
        }

        @Override // io.netty.util.internal.chmv8.ConcurrentHashMapV8.c, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            ConcurrentHashMapV8 concurrentHashMapV8 = this.map;
            ai<K, V>[] aiVarArr = concurrentHashMapV8.b;
            int length = aiVarArr == null ? 0 : aiVarArr.length;
            return new e(aiVarArr, length, 0, length, concurrentHashMapV8);
        }

        /* renamed from: a */
        public boolean add(Map.Entry<K, V> entry) {
            return this.map.a((ConcurrentHashMapV8) entry.getKey(), (K) entry.getValue(), false) == null;
        }

        @Override // java.util.Collection, java.util.Set
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            boolean z = false;
            for (Map.Entry<K, V> entry : collection) {
                if (add(entry)) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            ai<K, V>[] aiVarArr = this.map.b;
            int i = 0;
            if (aiVarArr != null) {
                as asVar = new as(aiVarArr, aiVarArr.length, 0, aiVarArr.length);
                while (true) {
                    ai<K, V> b = asVar.b();
                    if (b == null) {
                        break;
                    }
                    i += b.hashCode();
                }
            }
            return i;
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            Set set;
            return (obj instanceof Set) && ((set = (Set) obj) == this || (containsAll(set) && set.containsAll(this)));
        }
    }

    /* loaded from: classes4.dex */
    static abstract class b<K, V, R> extends CountedCompleter<R> {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int batch;
        int index;
        ai<K, V> next;
        ai<K, V>[] tab;

        b(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr) {
            super(bVar);
            this.batch = i;
            this.baseIndex = i2;
            this.index = i2;
            this.tab = aiVarArr;
            if (aiVarArr == null) {
                this.baseLimit = 0;
                this.baseSize = 0;
            } else if (bVar == null) {
                int length = aiVarArr.length;
                this.baseLimit = length;
                this.baseSize = length;
            } else {
                this.baseLimit = i3;
                this.baseSize = bVar.baseSize;
            }
        }

        final ai<K, V> a() {
            ai<K, V>[] aiVarArr;
            int length;
            int i;
            ai<K, V> aiVar = this.next;
            if (aiVar != null) {
                aiVar = aiVar.e;
            }
            while (aiVar == null) {
                if (this.baseIndex >= this.baseLimit || (aiVarArr = this.tab) == null || (length = aiVarArr.length) <= (i = this.index) || i < 0) {
                    this.next = null;
                    return null;
                }
                aiVar = ConcurrentHashMapV8.a(aiVarArr, i);
                if (aiVar != null && aiVar.b < 0) {
                    if (aiVar instanceof o) {
                        this.tab = ((o) aiVar).a;
                        aiVar = null;
                    } else {
                        aiVar = aiVar instanceof at ? ((at) aiVar).f : null;
                    }
                }
                int i2 = this.index + this.baseSize;
                this.index = i2;
                if (i2 >= length) {
                    int i3 = this.baseIndex + 1;
                    this.baseIndex = i3;
                    this.index = i3;
                }
            }
            this.next = aiVar;
            return aiVar;
        }
    }

    /* loaded from: classes4.dex */
    static final class h<K, V> extends b<K, V, Void> {
        final Action<? super K> action;

        h(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Action<? super K> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super K> action = this.action;
            if (action != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new h(this, i4, i3, i2, this.tab, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        action.apply((K) a.c);
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class n<K, V> extends b<K, V, Void> {
        final Action<? super V> action;

        n(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Action<? super V> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super V> action = this.action;
            if (action != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new n(this, i4, i3, i2, this.tab, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        action.apply((V) a.d);
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class g<K, V> extends b<K, V, Void> {
        final Action<? super Map.Entry<K, V>> action;

        g(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Action<? super Map.Entry<K, V>> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super Map.Entry<K, V>> action = this.action;
            if (action != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new g(this, i4, i3, i2, this.tab, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        action.apply(a);
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class i<K, V> extends b<K, V, Void> {
        final BiAction<? super K, ? super V> action;

        i(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, BiAction<? super K, ? super V> biAction) {
            super(bVar, i, i2, i3, aiVarArr);
            this.action = biAction;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiAction<? super K, ? super V> biAction = this.action;
            if (biAction != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new i(this, i4, i3, i2, this.tab, biAction).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        biAction.apply((K) a.c, (V) a.d);
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class k<K, V, U> extends b<K, V, Void> {
        final Action<? super U> action;
        final Fun<? super K, ? extends U> transformer;

        k(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<? super K, ? extends U> fun, Action<? super U> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.transformer = fun;
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super U> action;
            Fun<? super K, ? extends U> fun = this.transformer;
            if (fun != null && (action = this.action) != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new k(this, i4, i3, i2, this.tab, fun, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        Object obj = (Object) fun.apply((K) a.c);
                        if (obj != 0) {
                            action.apply(obj);
                        }
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class m<K, V, U> extends b<K, V, Void> {
        final Action<? super U> action;
        final Fun<? super V, ? extends U> transformer;

        m(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<? super V, ? extends U> fun, Action<? super U> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.transformer = fun;
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super U> action;
            Fun<? super V, ? extends U> fun = this.transformer;
            if (fun != null && (action = this.action) != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new m(this, i4, i3, i2, this.tab, fun, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        Object obj = (Object) fun.apply((V) a.d);
                        if (obj != 0) {
                            action.apply(obj);
                        }
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class j<K, V, U> extends b<K, V, Void> {
        final Action<? super U> action;
        final Fun<Map.Entry<K, V>, ? extends U> transformer;

        j(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<Map.Entry<K, V>, ? extends U> fun, Action<? super U> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.transformer = fun;
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super U> action;
            Fun<Map.Entry<K, V>, ? extends U> fun = this.transformer;
            if (fun != null && (action = this.action) != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new j(this, i4, i3, i2, this.tab, fun, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        Object obj = (Object) fun.apply(a);
                        if (obj != 0) {
                            action.apply(obj);
                        }
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class l<K, V, U> extends b<K, V, Void> {
        final Action<? super U> action;
        final BiFun<? super K, ? super V, ? extends U> transformer;

        l(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, BiFun<? super K, ? super V, ? extends U> biFun, Action<? super U> action) {
            super(bVar, i, i2, i3, aiVarArr);
            this.transformer = biFun;
            this.action = action;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            Action<? super U> action;
            BiFun<? super K, ? super V, ? extends U> biFun = this.transformer;
            if (biFun != null && (action = this.action) != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    new l(this, i4, i3, i2, this.tab, biFun, action).fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a != null) {
                        Object obj = (Object) biFun.apply((K) a.c, (V) a.d);
                        if (obj != 0) {
                            action.apply(obj);
                        }
                    } else {
                        propagateCompletion();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ao<K, V, U> extends b<K, V, U> {
        final AtomicReference<U> result;
        final Fun<? super K, ? extends U> searchFunction;

        ao(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<? super K, ? extends U> fun, AtomicReference<U> atomicReference) {
            super(bVar, i, i2, i3, aiVarArr);
            this.searchFunction = fun;
            this.result = atomicReference;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            AtomicReference<U> atomicReference;
            Fun<? super K, ? extends U> fun = this.searchFunction;
            if (fun != null && (atomicReference = this.result) != 0) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    } else if (atomicReference.get() == null) {
                        addToPendingCount(1);
                        int i4 = this.batch >>> 1;
                        this.batch = i4;
                        this.baseLimit = i3;
                        new ao(this, i4, i3, i2, this.tab, fun, atomicReference).fork();
                    } else {
                        return;
                    }
                }
                while (atomicReference.get() == null) {
                    ai<K, V> a = a();
                    if (a == null) {
                        propagateCompletion();
                        return;
                    }
                    Object apply = fun.apply((K) a.c);
                    if (apply != null) {
                        if (atomicReference.compareAndSet(null, apply)) {
                            quietlyCompleteRoot();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class aq<K, V, U> extends b<K, V, U> {
        final AtomicReference<U> result;
        final Fun<? super V, ? extends U> searchFunction;

        aq(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<? super V, ? extends U> fun, AtomicReference<U> atomicReference) {
            super(bVar, i, i2, i3, aiVarArr);
            this.searchFunction = fun;
            this.result = atomicReference;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            AtomicReference<U> atomicReference;
            Fun<? super V, ? extends U> fun = this.searchFunction;
            if (fun != null && (atomicReference = this.result) != 0) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    } else if (atomicReference.get() == null) {
                        addToPendingCount(1);
                        int i4 = this.batch >>> 1;
                        this.batch = i4;
                        this.baseLimit = i3;
                        new aq(this, i4, i3, i2, this.tab, fun, atomicReference).fork();
                    } else {
                        return;
                    }
                }
                while (atomicReference.get() == null) {
                    ai<K, V> a = a();
                    if (a == null) {
                        propagateCompletion();
                        return;
                    }
                    Object apply = fun.apply((V) a.d);
                    if (apply != null) {
                        if (atomicReference.compareAndSet(null, apply)) {
                            quietlyCompleteRoot();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class an<K, V, U> extends b<K, V, U> {
        final AtomicReference<U> result;
        final Fun<Map.Entry<K, V>, ? extends U> searchFunction;

        an(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, Fun<Map.Entry<K, V>, ? extends U> fun, AtomicReference<U> atomicReference) {
            super(bVar, i, i2, i3, aiVarArr);
            this.searchFunction = fun;
            this.result = atomicReference;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            AtomicReference<U> atomicReference;
            Fun<Map.Entry<K, V>, ? extends U> fun = this.searchFunction;
            if (fun != null && (atomicReference = this.result) != 0) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    } else if (atomicReference.get() == null) {
                        addToPendingCount(1);
                        int i4 = this.batch >>> 1;
                        this.batch = i4;
                        this.baseLimit = i3;
                        new an(this, i4, i3, i2, this.tab, fun, atomicReference).fork();
                    } else {
                        return;
                    }
                }
                while (atomicReference.get() == null) {
                    ai<K, V> a = a();
                    if (a == null) {
                        propagateCompletion();
                        return;
                    }
                    Object apply = fun.apply(a);
                    if (apply != null) {
                        if (atomicReference.compareAndSet(null, apply)) {
                            quietlyCompleteRoot();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ap<K, V, U> extends b<K, V, U> {
        final AtomicReference<U> result;
        final BiFun<? super K, ? super V, ? extends U> searchFunction;

        ap(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, BiFun<? super K, ? super V, ? extends U> biFun, AtomicReference<U> atomicReference) {
            super(bVar, i, i2, i3, aiVarArr);
            this.searchFunction = biFun;
            this.result = atomicReference;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            AtomicReference<U> atomicReference;
            BiFun<? super K, ? super V, ? extends U> biFun = this.searchFunction;
            if (biFun != null && (atomicReference = this.result) != 0) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    } else if (atomicReference.get() == null) {
                        addToPendingCount(1);
                        int i4 = this.batch >>> 1;
                        this.batch = i4;
                        this.baseLimit = i3;
                        new ap(this, i4, i3, i2, this.tab, biFun, atomicReference).fork();
                    } else {
                        return;
                    }
                }
                while (atomicReference.get() == null) {
                    ai<K, V> a = a();
                    if (a == null) {
                        propagateCompletion();
                        return;
                    }
                    Object apply = biFun.apply((K) a.c, (V) a.d);
                    if (apply != null) {
                        if (atomicReference.compareAndSet(null, apply)) {
                            quietlyCompleteRoot();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ak<K, V> extends b<K, V, K> {
        ak<K, V> nextRight;
        final BiFun<? super K, ? super K, ? extends K> reducer;
        K result;
        ak<K, V> rights;

        ak(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ak<K, V> akVar, BiFun<? super K, ? super K, ? extends K> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = akVar;
            this.reducer = biFun;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final K getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super K, ? super K, ? extends K> biFun = this.reducer;
            if (biFun != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ak<K, V> akVar = new ak<>(this, i4, i3, i2, this.tab, this.rights, biFun);
                    this.rights = akVar;
                    akVar.fork();
                }
                Object obj = (K) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    K k = a.c;
                    if (obj == null) {
                        obj = k;
                    } else if (k != 0) {
                        obj = (K) biFun.apply(obj, k);
                    }
                }
                this.result = (K) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ak akVar2 = (ak) firstComplete;
                    ak<K, V> akVar3 = akVar2.rights;
                    while (akVar3 != null) {
                        K k2 = akVar3.result;
                        if (k2 != null) {
                            Object obj2 = (K) akVar2.result;
                            if (obj2 != null) {
                                k2 = (K) biFun.apply(obj2, k2);
                            }
                            akVar2.result = (K) k2;
                        }
                        akVar3 = akVar3.nextRight;
                        akVar2.rights = akVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class al<K, V> extends b<K, V, V> {
        al<K, V> nextRight;
        final BiFun<? super V, ? super V, ? extends V> reducer;
        V result;
        al<K, V> rights;

        al(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, al<K, V> alVar, BiFun<? super V, ? super V, ? extends V> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = alVar;
            this.reducer = biFun;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final V getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super V, ? super V, ? extends V> biFun = this.reducer;
            if (biFun != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    al<K, V> alVar = new al<>(this, i4, i3, i2, this.tab, this.rights, biFun);
                    this.rights = alVar;
                    alVar.fork();
                }
                Object obj = (V) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    V v = a.d;
                    obj = obj == null ? v : (V) biFun.apply(obj, v);
                }
                this.result = (V) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    al alVar2 = (al) firstComplete;
                    al<K, V> alVar3 = alVar2.rights;
                    while (alVar3 != null) {
                        V v2 = alVar3.result;
                        if (v2 != null) {
                            Object obj2 = (V) alVar2.result;
                            if (obj2 != null) {
                                v2 = (V) biFun.apply(obj2, v2);
                            }
                            alVar2.result = (V) v2;
                        }
                        alVar3 = alVar3.nextRight;
                        alVar2.rights = alVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class aj<K, V> extends b<K, V, Map.Entry<K, V>> {
        aj<K, V> nextRight;
        final BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
        Map.Entry<K, V> result;
        aj<K, V> rights;

        aj(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, aj<K, V> ajVar, BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = ajVar;
            this.reducer = biFun;
        }

        /* renamed from: b */
        public final Map.Entry<K, V> getRawResult() {
            return this.result;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFun = this.reducer;
            if (biFun != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    aj<K, V> ajVar = new aj<>(this, i4, i3, i2, this.tab, this.rights, biFun);
                    this.rights = ajVar;
                    ajVar.fork();
                }
                Map.Entry<K, V> entry = null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    entry = entry == null ? a : (Map.Entry) biFun.apply(entry, a);
                }
                this.result = entry;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    aj ajVar2 = (aj) firstComplete;
                    aj<K, V> ajVar3 = ajVar2.rights;
                    while (ajVar3 != null) {
                        Map.Entry<K, V> entry2 = ajVar3.result;
                        if (entry2 != null) {
                            Map.Entry<K, V> entry3 = ajVar2.result;
                            if (entry3 != null) {
                                entry2 = (Map.Entry) biFun.apply(entry3, entry2);
                            }
                            ajVar2.result = entry2;
                        }
                        ajVar3 = ajVar3.nextRight;
                        ajVar2.rights = ajVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class w<K, V, U> extends b<K, V, U> {
        w<K, V, U> nextRight;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        w<K, V, U> rights;
        final Fun<? super K, ? extends U> transformer;

        w(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, w<K, V, U> wVar, Fun<? super K, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = wVar;
            this.transformer = fun;
            this.reducer = biFun;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super U, ? super U, ? extends U> biFun;
            Fun<? super K, ? extends U> fun = this.transformer;
            if (!(fun == null || (biFun = this.reducer) == null)) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    w<K, V, U> wVar = new w<>(this, i4, i3, i2, this.tab, this.rights, fun, biFun);
                    this.rights = wVar;
                    wVar.fork();
                }
                Object obj = (U) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    obj = (Object) fun.apply((K) a.c);
                    if (obj != null) {
                        if (obj != null) {
                            obj = (U) biFun.apply(obj, obj);
                        }
                    }
                }
                this.result = (U) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    w wVar2 = (w) firstComplete;
                    w<K, V, U> wVar3 = wVar2.rights;
                    while (wVar3 != null) {
                        U u = wVar3.result;
                        if (u != null) {
                            Object obj2 = (U) wVar2.result;
                            if (obj2 != null) {
                                u = (U) biFun.apply(obj2, u);
                            }
                            wVar2.result = (U) u;
                        }
                        wVar3 = wVar3.nextRight;
                        wVar2.rights = wVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ae<K, V, U> extends b<K, V, U> {
        ae<K, V, U> nextRight;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        ae<K, V, U> rights;
        final Fun<? super V, ? extends U> transformer;

        ae(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ae<K, V, U> aeVar, Fun<? super V, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = aeVar;
            this.transformer = fun;
            this.reducer = biFun;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super U, ? super U, ? extends U> biFun;
            Fun<? super V, ? extends U> fun = this.transformer;
            if (!(fun == null || (biFun = this.reducer) == null)) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ae<K, V, U> aeVar = new ae<>(this, i4, i3, i2, this.tab, this.rights, fun, biFun);
                    this.rights = aeVar;
                    aeVar.fork();
                }
                Object obj = (U) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    obj = (Object) fun.apply((V) a.d);
                    if (obj != null) {
                        if (obj != null) {
                            obj = (U) biFun.apply(obj, obj);
                        }
                    }
                }
                this.result = (U) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ae aeVar2 = (ae) firstComplete;
                    ae<K, V, U> aeVar3 = aeVar2.rights;
                    while (aeVar3 != null) {
                        U u = aeVar3.result;
                        if (u != null) {
                            Object obj2 = (U) aeVar2.result;
                            if (obj2 != null) {
                                u = (U) biFun.apply(obj2, u);
                            }
                            aeVar2.result = (U) u;
                        }
                        aeVar3 = aeVar3.nextRight;
                        aeVar2.rights = aeVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class s<K, V, U> extends b<K, V, U> {
        s<K, V, U> nextRight;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        s<K, V, U> rights;
        final Fun<Map.Entry<K, V>, ? extends U> transformer;

        s(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, s<K, V, U> sVar, Fun<Map.Entry<K, V>, ? extends U> fun, BiFun<? super U, ? super U, ? extends U> biFun) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = sVar;
            this.transformer = fun;
            this.reducer = biFun;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super U, ? super U, ? extends U> biFun;
            Fun<Map.Entry<K, V>, ? extends U> fun = this.transformer;
            if (!(fun == null || (biFun = this.reducer) == null)) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    s<K, V, U> sVar = new s<>(this, i4, i3, i2, this.tab, this.rights, fun, biFun);
                    this.rights = sVar;
                    sVar.fork();
                }
                Object obj = (U) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    obj = (Object) fun.apply(a);
                    if (obj != null) {
                        if (obj != null) {
                            obj = (U) biFun.apply(obj, obj);
                        }
                    }
                }
                this.result = (U) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    s sVar2 = (s) firstComplete;
                    s<K, V, U> sVar3 = sVar2.rights;
                    while (sVar3 != null) {
                        U u = sVar3.result;
                        if (u != null) {
                            Object obj2 = (U) sVar2.result;
                            if (obj2 != null) {
                                u = (U) biFun.apply(obj2, u);
                            }
                            sVar2.result = (U) u;
                        }
                        sVar3 = sVar3.nextRight;
                        sVar2.rights = sVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class aa<K, V, U> extends b<K, V, U> {
        aa<K, V, U> nextRight;
        final BiFun<? super U, ? super U, ? extends U> reducer;
        U result;
        aa<K, V, U> rights;
        final BiFun<? super K, ? super V, ? extends U> transformer;

        aa(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, aa<K, V, U> aaVar, BiFun<? super K, ? super V, ? extends U> biFun, BiFun<? super U, ? super U, ? extends U> biFun2) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = aaVar;
            this.transformer = biFun;
            this.reducer = biFun2;
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter, io.netty.util.internal.chmv8.ForkJoinTask
        public final U getRawResult() {
            return this.result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            BiFun<? super U, ? super U, ? extends U> biFun;
            BiFun<? super K, ? super V, ? extends U> biFun2 = this.transformer;
            if (!(biFun2 == null || (biFun = this.reducer) == null)) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    aa<K, V, U> aaVar = new aa<>(this, i4, i3, i2, this.tab, this.rights, biFun2, biFun);
                    this.rights = aaVar;
                    aaVar.fork();
                }
                Object obj = (U) null;
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    obj = (Object) biFun2.apply((K) a.c, (V) a.d);
                    if (obj != null) {
                        if (obj != null) {
                            obj = (U) biFun.apply(obj, obj);
                        }
                    }
                }
                this.result = (U) obj;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    aa aaVar2 = (aa) firstComplete;
                    aa<K, V, U> aaVar3 = aaVar2.rights;
                    while (aaVar3 != null) {
                        U u = aaVar3.result;
                        if (u != null) {
                            Object obj2 = (U) aaVar2.result;
                            if (obj2 != null) {
                                u = (U) biFun.apply(obj2, u);
                            }
                            aaVar2.result = (U) u;
                        }
                        aaVar3 = aaVar3.nextRight;
                        aaVar2.rights = aaVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class x<K, V> extends b<K, V, Double> {
        final double basis;
        x<K, V> nextRight;
        final DoubleByDoubleToDouble reducer;
        double result;
        x<K, V> rights;
        final ObjectToDouble<? super K> transformer;

        x(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, x<K, V> xVar, ObjectToDouble<? super K> objectToDouble, double d, DoubleByDoubleToDouble doubleByDoubleToDouble) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = xVar;
            this.transformer = objectToDouble;
            this.basis = d;
            this.reducer = doubleByDoubleToDouble;
        }

        /* renamed from: b */
        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            DoubleByDoubleToDouble doubleByDoubleToDouble;
            ObjectToDouble<? super K> objectToDouble = this.transformer;
            if (!(objectToDouble == null || (doubleByDoubleToDouble = this.reducer) == null)) {
                double d = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    x<K, V> xVar = new x<>(this, i4, i3, i2, this.tab, this.rights, objectToDouble, d, doubleByDoubleToDouble);
                    this.rights = xVar;
                    xVar.fork();
                    objectToDouble = objectToDouble;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    d = doubleByDoubleToDouble.apply(d, objectToDouble.apply((K) a.c));
                }
                this.result = d;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    x xVar2 = (x) firstComplete;
                    x<K, V> xVar3 = xVar2.rights;
                    while (xVar3 != null) {
                        xVar2.result = doubleByDoubleToDouble.apply(xVar2.result, xVar3.result);
                        xVar3 = xVar3.nextRight;
                        xVar2.rights = xVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class af<K, V> extends b<K, V, Double> {
        final double basis;
        af<K, V> nextRight;
        final DoubleByDoubleToDouble reducer;
        double result;
        af<K, V> rights;
        final ObjectToDouble<? super V> transformer;

        af(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, af<K, V> afVar, ObjectToDouble<? super V> objectToDouble, double d, DoubleByDoubleToDouble doubleByDoubleToDouble) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = afVar;
            this.transformer = objectToDouble;
            this.basis = d;
            this.reducer = doubleByDoubleToDouble;
        }

        /* renamed from: b */
        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            DoubleByDoubleToDouble doubleByDoubleToDouble;
            ObjectToDouble<? super V> objectToDouble = this.transformer;
            if (!(objectToDouble == null || (doubleByDoubleToDouble = this.reducer) == null)) {
                double d = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    af<K, V> afVar = new af<>(this, i4, i3, i2, this.tab, this.rights, objectToDouble, d, doubleByDoubleToDouble);
                    this.rights = afVar;
                    afVar.fork();
                    objectToDouble = objectToDouble;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    d = doubleByDoubleToDouble.apply(d, objectToDouble.apply((V) a.d));
                }
                this.result = d;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    af afVar2 = (af) firstComplete;
                    af<K, V> afVar3 = afVar2.rights;
                    while (afVar3 != null) {
                        afVar2.result = doubleByDoubleToDouble.apply(afVar2.result, afVar3.result);
                        afVar3 = afVar3.nextRight;
                        afVar2.rights = afVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class t<K, V> extends b<K, V, Double> {
        final double basis;
        t<K, V> nextRight;
        final DoubleByDoubleToDouble reducer;
        double result;
        t<K, V> rights;
        final ObjectToDouble<Map.Entry<K, V>> transformer;

        t(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, t<K, V> tVar, ObjectToDouble<Map.Entry<K, V>> objectToDouble, double d, DoubleByDoubleToDouble doubleByDoubleToDouble) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = tVar;
            this.transformer = objectToDouble;
            this.basis = d;
            this.reducer = doubleByDoubleToDouble;
        }

        /* renamed from: b */
        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            DoubleByDoubleToDouble doubleByDoubleToDouble;
            ObjectToDouble<Map.Entry<K, V>> objectToDouble = this.transformer;
            if (!(objectToDouble == null || (doubleByDoubleToDouble = this.reducer) == null)) {
                double d = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    t<K, V> tVar = new t<>(this, i4, i3, i2, this.tab, this.rights, objectToDouble, d, doubleByDoubleToDouble);
                    this.rights = tVar;
                    tVar.fork();
                    objectToDouble = objectToDouble;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    d = doubleByDoubleToDouble.apply(d, objectToDouble.apply(a));
                }
                this.result = d;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    t tVar2 = (t) firstComplete;
                    t<K, V> tVar3 = tVar2.rights;
                    while (tVar3 != null) {
                        tVar2.result = doubleByDoubleToDouble.apply(tVar2.result, tVar3.result);
                        tVar3 = tVar3.nextRight;
                        tVar2.rights = tVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ab<K, V> extends b<K, V, Double> {
        final double basis;
        ab<K, V> nextRight;
        final DoubleByDoubleToDouble reducer;
        double result;
        ab<K, V> rights;
        final ObjectByObjectToDouble<? super K, ? super V> transformer;

        ab(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ab<K, V> abVar, ObjectByObjectToDouble<? super K, ? super V> objectByObjectToDouble, double d, DoubleByDoubleToDouble doubleByDoubleToDouble) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = abVar;
            this.transformer = objectByObjectToDouble;
            this.basis = d;
            this.reducer = doubleByDoubleToDouble;
        }

        /* renamed from: b */
        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            DoubleByDoubleToDouble doubleByDoubleToDouble;
            ObjectByObjectToDouble<? super K, ? super V> objectByObjectToDouble = this.transformer;
            if (!(objectByObjectToDouble == null || (doubleByDoubleToDouble = this.reducer) == null)) {
                double d = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ab<K, V> abVar = new ab<>(this, i4, i3, i2, this.tab, this.rights, objectByObjectToDouble, d, doubleByDoubleToDouble);
                    this.rights = abVar;
                    abVar.fork();
                    objectByObjectToDouble = objectByObjectToDouble;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    d = doubleByDoubleToDouble.apply(d, objectByObjectToDouble.apply((K) a.c, (V) a.d));
                }
                this.result = d;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ab abVar2 = (ab) firstComplete;
                    ab<K, V> abVar3 = abVar2.rights;
                    while (abVar3 != null) {
                        abVar2.result = doubleByDoubleToDouble.apply(abVar2.result, abVar3.result);
                        abVar3 = abVar3.nextRight;
                        abVar2.rights = abVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class z<K, V> extends b<K, V, Long> {
        final long basis;
        z<K, V> nextRight;
        final LongByLongToLong reducer;
        long result;
        z<K, V> rights;
        final ObjectToLong<? super K> transformer;

        z(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, z<K, V> zVar, ObjectToLong<? super K> objectToLong, long j, LongByLongToLong longByLongToLong) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = zVar;
            this.transformer = objectToLong;
            this.basis = j;
            this.reducer = longByLongToLong;
        }

        /* renamed from: b */
        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            LongByLongToLong longByLongToLong;
            ObjectToLong<? super K> objectToLong = this.transformer;
            if (!(objectToLong == null || (longByLongToLong = this.reducer) == null)) {
                long j = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    z<K, V> zVar = new z<>(this, i4, i3, i2, this.tab, this.rights, objectToLong, j, longByLongToLong);
                    this.rights = zVar;
                    zVar.fork();
                    objectToLong = objectToLong;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    j = longByLongToLong.apply(j, objectToLong.apply((K) a.c));
                }
                this.result = j;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    z zVar2 = (z) firstComplete;
                    z<K, V> zVar3 = zVar2.rights;
                    while (zVar3 != null) {
                        zVar2.result = longByLongToLong.apply(zVar2.result, zVar3.result);
                        zVar3 = zVar3.nextRight;
                        zVar2.rights = zVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ah<K, V> extends b<K, V, Long> {
        final long basis;
        ah<K, V> nextRight;
        final LongByLongToLong reducer;
        long result;
        ah<K, V> rights;
        final ObjectToLong<? super V> transformer;

        ah(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ah<K, V> ahVar, ObjectToLong<? super V> objectToLong, long j, LongByLongToLong longByLongToLong) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = ahVar;
            this.transformer = objectToLong;
            this.basis = j;
            this.reducer = longByLongToLong;
        }

        /* renamed from: b */
        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            LongByLongToLong longByLongToLong;
            ObjectToLong<? super V> objectToLong = this.transformer;
            if (!(objectToLong == null || (longByLongToLong = this.reducer) == null)) {
                long j = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ah<K, V> ahVar = new ah<>(this, i4, i3, i2, this.tab, this.rights, objectToLong, j, longByLongToLong);
                    this.rights = ahVar;
                    ahVar.fork();
                    objectToLong = objectToLong;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    j = longByLongToLong.apply(j, objectToLong.apply((V) a.d));
                }
                this.result = j;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ah ahVar2 = (ah) firstComplete;
                    ah<K, V> ahVar3 = ahVar2.rights;
                    while (ahVar3 != null) {
                        ahVar2.result = longByLongToLong.apply(ahVar2.result, ahVar3.result);
                        ahVar3 = ahVar3.nextRight;
                        ahVar2.rights = ahVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class v<K, V> extends b<K, V, Long> {
        final long basis;
        v<K, V> nextRight;
        final LongByLongToLong reducer;
        long result;
        v<K, V> rights;
        final ObjectToLong<Map.Entry<K, V>> transformer;

        v(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, v<K, V> vVar, ObjectToLong<Map.Entry<K, V>> objectToLong, long j, LongByLongToLong longByLongToLong) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = vVar;
            this.transformer = objectToLong;
            this.basis = j;
            this.reducer = longByLongToLong;
        }

        /* renamed from: b */
        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            LongByLongToLong longByLongToLong;
            ObjectToLong<Map.Entry<K, V>> objectToLong = this.transformer;
            if (!(objectToLong == null || (longByLongToLong = this.reducer) == null)) {
                long j = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    v<K, V> vVar = new v<>(this, i4, i3, i2, this.tab, this.rights, objectToLong, j, longByLongToLong);
                    this.rights = vVar;
                    vVar.fork();
                    objectToLong = objectToLong;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    j = longByLongToLong.apply(j, objectToLong.apply(a));
                }
                this.result = j;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    v vVar2 = (v) firstComplete;
                    v<K, V> vVar3 = vVar2.rights;
                    while (vVar3 != null) {
                        vVar2.result = longByLongToLong.apply(vVar2.result, vVar3.result);
                        vVar3 = vVar3.nextRight;
                        vVar2.rights = vVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ad<K, V> extends b<K, V, Long> {
        final long basis;
        ad<K, V> nextRight;
        final LongByLongToLong reducer;
        long result;
        ad<K, V> rights;
        final ObjectByObjectToLong<? super K, ? super V> transformer;

        ad(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ad<K, V> adVar, ObjectByObjectToLong<? super K, ? super V> objectByObjectToLong, long j, LongByLongToLong longByLongToLong) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = adVar;
            this.transformer = objectByObjectToLong;
            this.basis = j;
            this.reducer = longByLongToLong;
        }

        /* renamed from: b */
        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            LongByLongToLong longByLongToLong;
            ObjectByObjectToLong<? super K, ? super V> objectByObjectToLong = this.transformer;
            if (!(objectByObjectToLong == null || (longByLongToLong = this.reducer) == null)) {
                long j = this.basis;
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ad<K, V> adVar = new ad<>(this, i4, i3, i2, this.tab, this.rights, objectByObjectToLong, j, longByLongToLong);
                    this.rights = adVar;
                    adVar.fork();
                    objectByObjectToLong = objectByObjectToLong;
                    i = i;
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    j = longByLongToLong.apply(j, objectByObjectToLong.apply((K) a.c, (V) a.d));
                }
                this.result = j;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ad adVar2 = (ad) firstComplete;
                    ad<K, V> adVar3 = adVar2.rights;
                    while (adVar3 != null) {
                        adVar2.result = longByLongToLong.apply(adVar2.result, adVar3.result);
                        adVar3 = adVar3.nextRight;
                        adVar2.rights = adVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class y<K, V> extends b<K, V, Integer> {
        final int basis;
        y<K, V> nextRight;
        final IntByIntToInt reducer;
        int result;
        y<K, V> rights;
        final ObjectToInt<? super K> transformer;

        y(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, y<K, V> yVar, ObjectToInt<? super K> objectToInt, int i4, IntByIntToInt intByIntToInt) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = yVar;
            this.transformer = objectToInt;
            this.basis = i4;
            this.reducer = intByIntToInt;
        }

        /* renamed from: b */
        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            IntByIntToInt intByIntToInt;
            ObjectToInt<? super K> objectToInt = this.transformer;
            if (!(objectToInt == null || (intByIntToInt = this.reducer) == null)) {
                int i = this.basis;
                int i2 = this.baseIndex;
                while (this.batch > 0) {
                    int i3 = this.baseLimit;
                    int i4 = (i3 + i2) >>> 1;
                    if (i4 <= i2) {
                        break;
                    }
                    addToPendingCount(1);
                    int i5 = this.batch >>> 1;
                    this.batch = i5;
                    this.baseLimit = i4;
                    y<K, V> yVar = new y<>(this, i5, i4, i3, this.tab, this.rights, objectToInt, i, intByIntToInt);
                    this.rights = yVar;
                    yVar.fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    i = intByIntToInt.apply(i, objectToInt.apply((K) a.c));
                }
                this.result = i;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    y yVar2 = (y) firstComplete;
                    y<K, V> yVar3 = yVar2.rights;
                    while (yVar3 != null) {
                        yVar2.result = intByIntToInt.apply(yVar2.result, yVar3.result);
                        yVar3 = yVar3.nextRight;
                        yVar2.rights = yVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ag<K, V> extends b<K, V, Integer> {
        final int basis;
        ag<K, V> nextRight;
        final IntByIntToInt reducer;
        int result;
        ag<K, V> rights;
        final ObjectToInt<? super V> transformer;

        ag(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ag<K, V> agVar, ObjectToInt<? super V> objectToInt, int i4, IntByIntToInt intByIntToInt) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = agVar;
            this.transformer = objectToInt;
            this.basis = i4;
            this.reducer = intByIntToInt;
        }

        /* renamed from: b */
        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            IntByIntToInt intByIntToInt;
            ObjectToInt<? super V> objectToInt = this.transformer;
            if (!(objectToInt == null || (intByIntToInt = this.reducer) == null)) {
                int i = this.basis;
                int i2 = this.baseIndex;
                while (this.batch > 0) {
                    int i3 = this.baseLimit;
                    int i4 = (i3 + i2) >>> 1;
                    if (i4 <= i2) {
                        break;
                    }
                    addToPendingCount(1);
                    int i5 = this.batch >>> 1;
                    this.batch = i5;
                    this.baseLimit = i4;
                    ag<K, V> agVar = new ag<>(this, i5, i4, i3, this.tab, this.rights, objectToInt, i, intByIntToInt);
                    this.rights = agVar;
                    agVar.fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    i = intByIntToInt.apply(i, objectToInt.apply((V) a.d));
                }
                this.result = i;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ag agVar2 = (ag) firstComplete;
                    ag<K, V> agVar3 = agVar2.rights;
                    while (agVar3 != null) {
                        agVar2.result = intByIntToInt.apply(agVar2.result, agVar3.result);
                        agVar3 = agVar3.nextRight;
                        agVar2.rights = agVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class u<K, V> extends b<K, V, Integer> {
        final int basis;
        u<K, V> nextRight;
        final IntByIntToInt reducer;
        int result;
        u<K, V> rights;
        final ObjectToInt<Map.Entry<K, V>> transformer;

        u(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, u<K, V> uVar, ObjectToInt<Map.Entry<K, V>> objectToInt, int i4, IntByIntToInt intByIntToInt) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = uVar;
            this.transformer = objectToInt;
            this.basis = i4;
            this.reducer = intByIntToInt;
        }

        /* renamed from: b */
        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            IntByIntToInt intByIntToInt;
            ObjectToInt<Map.Entry<K, V>> objectToInt = this.transformer;
            if (!(objectToInt == null || (intByIntToInt = this.reducer) == null)) {
                int i = this.basis;
                int i2 = this.baseIndex;
                while (this.batch > 0) {
                    int i3 = this.baseLimit;
                    int i4 = (i3 + i2) >>> 1;
                    if (i4 <= i2) {
                        break;
                    }
                    addToPendingCount(1);
                    int i5 = this.batch >>> 1;
                    this.batch = i5;
                    this.baseLimit = i4;
                    u<K, V> uVar = new u<>(this, i5, i4, i3, this.tab, this.rights, objectToInt, i, intByIntToInt);
                    this.rights = uVar;
                    uVar.fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    i = intByIntToInt.apply(i, objectToInt.apply(a));
                }
                this.result = i;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    u uVar2 = (u) firstComplete;
                    u<K, V> uVar3 = uVar2.rights;
                    while (uVar3 != null) {
                        uVar2.result = intByIntToInt.apply(uVar2.result, uVar3.result);
                        uVar3 = uVar3.nextRight;
                        uVar2.rights = uVar3;
                    }
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class ac<K, V> extends b<K, V, Integer> {
        final int basis;
        ac<K, V> nextRight;
        final IntByIntToInt reducer;
        int result;
        ac<K, V> rights;
        final ObjectByObjectToInt<? super K, ? super V> transformer;

        ac(b<K, V, ?> bVar, int i, int i2, int i3, ai<K, V>[] aiVarArr, ac<K, V> acVar, ObjectByObjectToInt<? super K, ? super V> objectByObjectToInt, int i4, IntByIntToInt intByIntToInt) {
            super(bVar, i, i2, i3, aiVarArr);
            this.nextRight = acVar;
            this.transformer = objectByObjectToInt;
            this.basis = i4;
            this.reducer = intByIntToInt;
        }

        /* renamed from: b */
        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        @Override // io.netty.util.internal.chmv8.CountedCompleter
        public final void compute() {
            IntByIntToInt intByIntToInt;
            ObjectByObjectToInt<? super K, ? super V> objectByObjectToInt = this.transformer;
            if (!(objectByObjectToInt == null || (intByIntToInt = this.reducer) == null)) {
                int i = this.basis;
                int i2 = this.baseIndex;
                while (this.batch > 0) {
                    int i3 = this.baseLimit;
                    int i4 = (i3 + i2) >>> 1;
                    if (i4 <= i2) {
                        break;
                    }
                    addToPendingCount(1);
                    int i5 = this.batch >>> 1;
                    this.batch = i5;
                    this.baseLimit = i4;
                    ac<K, V> acVar = new ac<>(this, i5, i4, i3, this.tab, this.rights, objectByObjectToInt, i, intByIntToInt);
                    this.rights = acVar;
                    acVar.fork();
                }
                while (true) {
                    ai<K, V> a = a();
                    if (a == null) {
                        break;
                    }
                    i = intByIntToInt.apply(i, objectByObjectToInt.apply((K) a.c, (V) a.d));
                }
                this.result = i;
                for (CountedCompleter<?> firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ac acVar2 = (ac) firstComplete;
                    ac<K, V> acVar3 = acVar2.rights;
                    while (acVar3 != null) {
                        acVar2.result = intByIntToInt.apply(acVar2.result, acVar3.result);
                        acVar3 = acVar3.nextRight;
                        acVar2.rights = acVar3;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class d {
        volatile long a;

        d(long j) {
            this.a = j;
        }
    }

    final long a() {
        d[] dVarArr = this.j;
        long j2 = this.e;
        if (dVarArr != null) {
            for (d dVar : dVarArr) {
                if (dVar != null) {
                    j2 += dVar.a;
                }
            }
        }
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x002a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011b A[EDGE_INSN: B:90:0x011b->B:81:0x011b ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a(io.netty.util.internal.InternalThreadLocalMap r26, long r27, io.netty.util.internal.IntegerHolder r29, boolean r30) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.a(io.netty.util.internal.InternalThreadLocalMap, long, io.netty.util.internal.IntegerHolder, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe d() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: io.netty.util.internal.chmv8.ConcurrentHashMapV8.1
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
