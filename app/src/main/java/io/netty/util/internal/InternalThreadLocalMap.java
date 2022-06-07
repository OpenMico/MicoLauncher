package io.netty.util.internal;

import io.netty.util.concurrent.FastThreadLocalThread;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public final class InternalThreadLocalMap extends x {
    public static final Object UNSET = new Object();
    public long rp1;
    public long rp2;
    public long rp3;
    public long rp4;
    public long rp5;
    public long rp6;
    public long rp7;
    public long rp8;
    public long rp9;

    public static InternalThreadLocalMap getIfSet() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof FastThreadLocalThread) {
            return ((FastThreadLocalThread) currentThread).threadLocalMap();
        }
        return (InternalThreadLocalMap) a.get();
    }

    public static InternalThreadLocalMap get() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof FastThreadLocalThread) {
            return a((FastThreadLocalThread) currentThread);
        }
        return a();
    }

    private static InternalThreadLocalMap a(FastThreadLocalThread fastThreadLocalThread) {
        InternalThreadLocalMap threadLocalMap = fastThreadLocalThread.threadLocalMap();
        if (threadLocalMap != null) {
            return threadLocalMap;
        }
        InternalThreadLocalMap internalThreadLocalMap = new InternalThreadLocalMap();
        fastThreadLocalThread.setThreadLocalMap(internalThreadLocalMap);
        return internalThreadLocalMap;
    }

    private static InternalThreadLocalMap a() {
        ThreadLocal<InternalThreadLocalMap> threadLocal = x.a;
        InternalThreadLocalMap internalThreadLocalMap = threadLocal.get();
        if (internalThreadLocalMap != null) {
            return internalThreadLocalMap;
        }
        InternalThreadLocalMap internalThreadLocalMap2 = new InternalThreadLocalMap();
        threadLocal.set(internalThreadLocalMap2);
        return internalThreadLocalMap2;
    }

    public static void remove() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof FastThreadLocalThread) {
            ((FastThreadLocalThread) currentThread).setThreadLocalMap(null);
        } else {
            a.remove();
        }
    }

    public static void destroy() {
        a.remove();
    }

    public static int nextVariableIndex() {
        int andIncrement = b.getAndIncrement();
        if (andIncrement >= 0) {
            return andIncrement;
        }
        b.decrementAndGet();
        throw new IllegalStateException("too many thread-local indexed variables");
    }

    public static int lastVariableIndex() {
        return b.get() - 1;
    }

    private InternalThreadLocalMap() {
        super(b());
    }

    private static Object[] b() {
        Object[] objArr = new Object[32];
        Arrays.fill(objArr, UNSET);
        return objArr;
    }

    public int size() {
        int i = this.d != 0 ? 1 : 0;
        if (this.e != 0) {
            i++;
        }
        if (this.f != null) {
            i++;
        }
        if (this.g != null) {
            i++;
        }
        if (this.h != null) {
            i++;
        }
        if (this.i != null) {
            i++;
        }
        if (this.j != null) {
            i++;
        }
        if (this.k != null) {
            i++;
        }
        if (this.l != null) {
            i++;
        }
        if (this.m != null) {
            i++;
        }
        if (this.n != null) {
            i++;
        }
        for (Object obj : this.c) {
            if (obj != UNSET) {
                i++;
            }
        }
        return i - 1;
    }

    public StringBuilder stringBuilder() {
        StringBuilder sb = this.k;
        if (sb == null) {
            StringBuilder sb2 = new StringBuilder(512);
            this.k = sb2;
            return sb2;
        }
        sb.setLength(0);
        return sb;
    }

    public Map<Charset, CharsetEncoder> charsetEncoderCache() {
        Map<Charset, CharsetEncoder> map = this.l;
        if (map != null) {
            return map;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.l = identityHashMap;
        return identityHashMap;
    }

    public Map<Charset, CharsetDecoder> charsetDecoderCache() {
        Map<Charset, CharsetDecoder> map = this.m;
        if (map != null) {
            return map;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.m = identityHashMap;
        return identityHashMap;
    }

    public <E> ArrayList<E> arrayList() {
        return arrayList(8);
    }

    public <E> ArrayList<E> arrayList(int i) {
        ArrayList<E> arrayList = this.n;
        if (arrayList == null) {
            return new ArrayList<>(i);
        }
        arrayList.clear();
        arrayList.ensureCapacity(i);
        return arrayList;
    }

    public int futureListenerStackDepth() {
        return this.d;
    }

    public void setFutureListenerStackDepth(int i) {
        this.d = i;
    }

    public ThreadLocalRandom random() {
        ThreadLocalRandom threadLocalRandom = this.h;
        if (threadLocalRandom != null) {
            return threadLocalRandom;
        }
        ThreadLocalRandom threadLocalRandom2 = new ThreadLocalRandom();
        this.h = threadLocalRandom2;
        return threadLocalRandom2;
    }

    public Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache() {
        Map<Class<?>, TypeParameterMatcher> map = this.i;
        if (map != null) {
            return map;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.i = identityHashMap;
        return identityHashMap;
    }

    public Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache() {
        Map<Class<?>, Map<String, TypeParameterMatcher>> map = this.j;
        if (map != null) {
            return map;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.j = identityHashMap;
        return identityHashMap;
    }

    public IntegerHolder counterHashCode() {
        return this.g;
    }

    public void setCounterHashCode(IntegerHolder integerHolder) {
        this.g = integerHolder;
    }

    public Map<Class<?>, Boolean> handlerSharableCache() {
        Map<Class<?>, Boolean> map = this.f;
        if (map != null) {
            return map;
        }
        WeakHashMap weakHashMap = new WeakHashMap(4);
        this.f = weakHashMap;
        return weakHashMap;
    }

    public int localChannelReaderStackDepth() {
        return this.e;
    }

    public void setLocalChannelReaderStackDepth(int i) {
        this.e = i;
    }

    public Object indexedVariable(int i) {
        Object[] objArr = this.c;
        return i < objArr.length ? objArr[i] : UNSET;
    }

    public boolean setIndexedVariable(int i, Object obj) {
        Object[] objArr = this.c;
        if (i < objArr.length) {
            Object obj2 = objArr[i];
            objArr[i] = obj;
            return obj2 == UNSET;
        }
        a(i, obj);
        return true;
    }

    private void a(int i, Object obj) {
        Object[] objArr = this.c;
        int length = objArr.length;
        int i2 = (i >>> 1) | i;
        int i3 = i2 | (i2 >>> 2);
        int i4 = i3 | (i3 >>> 4);
        int i5 = i4 | (i4 >>> 8);
        Object[] copyOf = Arrays.copyOf(objArr, (i5 | (i5 >>> 16)) + 1);
        Arrays.fill(copyOf, length, copyOf.length, UNSET);
        copyOf[i] = obj;
        this.c = copyOf;
    }

    public Object removeIndexedVariable(int i) {
        Object[] objArr = this.c;
        if (i >= objArr.length) {
            return UNSET;
        }
        Object obj = objArr[i];
        objArr[i] = UNSET;
        return obj;
    }

    public boolean isIndexedVariableSet(int i) {
        Object[] objArr = this.c;
        return i < objArr.length && objArr[i] != UNSET;
    }
}
