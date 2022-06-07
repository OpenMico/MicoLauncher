package io.netty.util.collection;

import io.netty.util.collection.LongObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class LongCollections {
    private static final LongObjectMap<Object> a = new a();

    private LongCollections() {
    }

    public static <V> LongObjectMap<V> emptyMap() {
        return (LongObjectMap<V>) a;
    }

    public static <V> LongObjectMap<V> unmodifiableMap(LongObjectMap<V> longObjectMap) {
        return new b(longObjectMap);
    }

    /* loaded from: classes4.dex */
    private static final class a implements LongObjectMap<Object> {
        @Override // java.util.Map
        public void clear() {
        }

        @Override // io.netty.util.collection.LongObjectMap
        public boolean containsKey(long j) {
            return false;
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return false;
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // io.netty.util.collection.LongObjectMap
        public Object get(long j) {
            return null;
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            return null;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // io.netty.util.collection.LongObjectMap
        public Object remove(long j) {
            return null;
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            return null;
        }

        @Override // java.util.Map
        public int size() {
            return 0;
        }

        private a() {
        }

        @Override // io.netty.util.collection.LongObjectMap
        public Object put(long j, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public Set<Long> keySet() {
            return Collections.emptySet();
        }

        @Override // io.netty.util.collection.LongObjectMap
        public Iterable<LongObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        /* renamed from: a */
        public Object put(Long l, Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Long, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override // java.util.Map
        public Set<Map.Entry<Long, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<V> implements LongObjectMap<V> {
        private final LongObjectMap<V> a;
        private Set<Long> b;
        private Set<Map.Entry<Long, V>> c;
        private Collection<V> d;
        private Iterable<LongObjectMap.PrimitiveEntry<V>> e;

        b(LongObjectMap<V> longObjectMap) {
            this.a = longObjectMap;
        }

        @Override // io.netty.util.collection.LongObjectMap
        public V get(long j) {
            return this.a.get(j);
        }

        @Override // io.netty.util.collection.LongObjectMap
        public V put(long j, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // io.netty.util.collection.LongObjectMap
        public V remove(long j) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public int size() {
            return this.a.size();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // java.util.Map
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override // io.netty.util.collection.LongObjectMap
        public boolean containsKey(long j) {
            return this.a.containsKey(j);
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return this.a.containsValue(obj);
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.a.get(obj);
        }

        /* renamed from: a */
        public V put(Long l, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Long, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override // io.netty.util.collection.LongObjectMap
        public Iterable<LongObjectMap.PrimitiveEntry<V>> entries() {
            if (this.e == null) {
                this.e = new Iterable<LongObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.LongCollections.b.1
                    @Override // java.lang.Iterable
                    public Iterator<LongObjectMap.PrimitiveEntry<V>> iterator() {
                        b bVar = b.this;
                        return new C0208b(bVar.a.entries().iterator());
                    }
                };
            }
            return this.e;
        }

        @Override // java.util.Map
        public Set<Long> keySet() {
            if (this.b == null) {
                this.b = Collections.unmodifiableSet(this.a.keySet());
            }
            return this.b;
        }

        @Override // java.util.Map
        public Set<Map.Entry<Long, V>> entrySet() {
            if (this.c == null) {
                this.c = Collections.unmodifiableSet(this.a.entrySet());
            }
            return this.c;
        }

        @Override // java.util.Map
        public Collection<V> values() {
            if (this.d == null) {
                this.d = Collections.unmodifiableCollection(this.a.values());
            }
            return this.d;
        }

        /* renamed from: io.netty.util.collection.LongCollections$b$b  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private class C0208b implements Iterator<LongObjectMap.PrimitiveEntry<V>> {
            final Iterator<LongObjectMap.PrimitiveEntry<V>> a;

            C0208b(Iterator<LongObjectMap.PrimitiveEntry<V>> it) {
                this.a = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public LongObjectMap.PrimitiveEntry<V> next() {
                if (hasNext()) {
                    return new a(this.a.next());
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes4.dex */
        public class a implements LongObjectMap.PrimitiveEntry<V> {
            private final LongObjectMap.PrimitiveEntry<V> b;

            a(LongObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.b = primitiveEntry;
            }

            @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
            public long key() {
                return this.b.key();
            }

            @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
            public V value() {
                return this.b.value();
            }

            @Override // io.netty.util.collection.LongObjectMap.PrimitiveEntry
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }
    }
}
