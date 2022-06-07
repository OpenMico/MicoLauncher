package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    /* loaded from: classes2.dex */
    public interface StatsCounter {
        void recordEviction();

        void recordHits(int i);

        void recordLoadException(long j);

        void recordLoadSuccess(long j);

        void recordMisses(int i);

        CacheStats snapshot();
    }

    @Override // com.google.common.cache.Cache
    public void cleanUp() {
    }

    @Override // com.google.common.cache.Cache
    public V get(K k, Callable<? extends V> callable) throws ExecutionException {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.cache.Cache
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        V ifPresent;
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        for (Object obj : iterable) {
            if (!newLinkedHashMap.containsKey(obj) && (ifPresent = getIfPresent(obj)) != null) {
                newLinkedHashMap.put(obj, ifPresent);
            }
        }
        return ImmutableMap.copyOf(newLinkedHashMap);
    }

    @Override // com.google.common.cache.Cache
    public void put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.cache.Cache
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.common.cache.Cache
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.cache.Cache
    public void invalidate(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.cache.Cache
    public void invalidateAll(Iterable<?> iterable) {
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            invalidate(it.next());
        }
    }

    @Override // com.google.common.cache.Cache
    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.cache.Cache
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.cache.Cache
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }

    /* loaded from: classes2.dex */
    public static final class SimpleStatsCounter implements StatsCounter {
        private final b a = c.a();
        private final b b = c.a();
        private final b c = c.a();
        private final b d = c.a();
        private final b e = c.a();
        private final b f = c.a();

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordHits(int i) {
            this.a.a(i);
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordMisses(int i) {
            this.b.a(i);
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadSuccess(long j) {
            this.c.a();
            this.e.a(j);
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadException(long j) {
            this.d.a();
            this.e.a(j);
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordEviction() {
            this.f.a();
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public CacheStats snapshot() {
            return new CacheStats(this.a.b(), this.b.b(), this.c.b(), this.d.b(), this.e.b(), this.f.b());
        }

        public void incrementBy(StatsCounter statsCounter) {
            CacheStats snapshot = statsCounter.snapshot();
            this.a.a(snapshot.hitCount());
            this.b.a(snapshot.missCount());
            this.c.a(snapshot.loadSuccessCount());
            this.d.a(snapshot.loadExceptionCount());
            this.e.a(snapshot.totalLoadTime());
            this.f.a(snapshot.evictionCount());
        }
    }
}
