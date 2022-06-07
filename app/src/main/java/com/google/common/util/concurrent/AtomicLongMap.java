package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class AtomicLongMap<K> implements Serializable {
    @MonotonicNonNullDecl
    private transient Map<K, Long> a;
    private final ConcurrentHashMap<K, AtomicLong> map;

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> concurrentHashMap) {
        this.map = (ConcurrentHashMap) Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap<>(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<K> create = create();
        create.putAll(map);
        return create;
    }

    public long get(K k) {
        AtomicLong atomicLong = this.map.get(k);
        if (atomicLong == null) {
            return 0L;
        }
        return atomicLong.get();
    }

    @CanIgnoreReturnValue
    public long incrementAndGet(K k) {
        return addAndGet(k, 1L);
    }

    @CanIgnoreReturnValue
    public long decrementAndGet(K k) {
        return addAndGet(k, -1L);
    }

    @CanIgnoreReturnValue
    public long addAndGet(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(k);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(k, new AtomicLong(j))) == null) {
                return j;
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 == 0) {
                    break;
                }
                long j3 = j2 + j;
                if (atomicLong.compareAndSet(j2, j3)) {
                    return j3;
                }
            }
        } while (!this.map.replace(k, atomicLong, new AtomicLong(j)));
        return j;
    }

    @CanIgnoreReturnValue
    public long getAndIncrement(K k) {
        return getAndAdd(k, 1L);
    }

    @CanIgnoreReturnValue
    public long getAndDecrement(K k) {
        return getAndAdd(k, -1L);
    }

    @CanIgnoreReturnValue
    public long getAndAdd(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(k);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(k, new AtomicLong(j))) == null) {
                return 0L;
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 == 0) {
                    break;
                } else if (atomicLong.compareAndSet(j2, j2 + j)) {
                    return j2;
                }
            }
        } while (!this.map.replace(k, atomicLong, new AtomicLong(j)));
        return 0L;
    }

    @CanIgnoreReturnValue
    public long put(K k, long j) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(k);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(k, new AtomicLong(j))) == null) {
                return 0L;
            }
            while (true) {
                long j2 = atomicLong.get();
                if (j2 == 0) {
                    break;
                } else if (atomicLong.compareAndSet(j2, j)) {
                    return j2;
                }
            }
        } while (!this.map.replace(k, atomicLong, new AtomicLong(j)));
        return 0L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(Map<? extends K, ? extends Long> map) {
        for (Map.Entry<? extends K, ? extends Long> entry : map.entrySet()) {
            put(entry.getKey(), ((Long) entry.getValue()).longValue());
        }
    }

    @CanIgnoreReturnValue
    public long remove(K k) {
        long j;
        AtomicLong atomicLong = this.map.get(k);
        if (atomicLong == null) {
            return 0L;
        }
        do {
            j = atomicLong.get();
            if (j == 0) {
                break;
            }
        } while (!atomicLong.compareAndSet(j, 0L));
        this.map.remove(k, atomicLong);
        return j;
    }

    boolean a(K k, long j) {
        AtomicLong atomicLong = this.map.get(k);
        if (atomicLong == null) {
            return false;
        }
        long j2 = atomicLong.get();
        if (j2 != j) {
            return false;
        }
        if (j2 != 0 && !atomicLong.compareAndSet(j2, 0L)) {
            return false;
        }
        this.map.remove(k, atomicLong);
        return true;
    }

    @CanIgnoreReturnValue
    @Beta
    public boolean removeIfZero(K k) {
        return a(k, 0L);
    }

    public void removeAllZeros() {
        Iterator<Map.Entry<K, AtomicLong>> it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            AtomicLong value = it.next().getValue();
            if (value != null && value.get() == 0) {
                it.remove();
            }
        }
    }

    public long sum() {
        long j = 0;
        for (AtomicLong atomicLong : this.map.values()) {
            j += atomicLong.get();
        }
        return j;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.a;
        if (map != null) {
            return map;
        }
        Map<K, Long> a = a();
        this.a = a;
        return a;
    }

    private Map<K, Long> a() {
        return Collections.unmodifiableMap(Maps.transformValues(this.map, new Function<AtomicLong, Long>() { // from class: com.google.common.util.concurrent.AtomicLongMap.1
            /* renamed from: a */
            public Long apply(AtomicLong atomicLong) {
                return Long.valueOf(atomicLong.get());
            }
        }));
    }

    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }
}
