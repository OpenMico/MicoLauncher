package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class IntCollections {
    private static final IntObjectMap<Object> a = new a();

    private IntCollections() {
    }

    public static <V> IntObjectMap<V> emptyMap() {
        return (IntObjectMap<V>) a;
    }

    public static <V> IntObjectMap<V> unmodifiableMap(IntObjectMap<V> intObjectMap) {
        return new b(intObjectMap);
    }

    /* loaded from: classes4.dex */
    private static final class a implements IntObjectMap<Object> {
        @Override // java.util.Map
        public void clear() {
        }

        @Override // io.netty.util.collection.IntObjectMap
        public boolean containsKey(int i) {
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

        @Override // io.netty.util.collection.IntObjectMap
        public Object get(int i) {
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

        @Override // io.netty.util.collection.IntObjectMap
        public Object remove(int i) {
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

        @Override // io.netty.util.collection.IntObjectMap
        public Object put(int i, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public Set<Integer> keySet() {
            return Collections.emptySet();
        }

        @Override // io.netty.util.collection.IntObjectMap
        public Iterable<IntObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        /* renamed from: a */
        public Object put(Integer num, Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override // java.util.Map
        public Set<Map.Entry<Integer, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<V> implements IntObjectMap<V> {
        private final IntObjectMap<V> a;
        private Set<Integer> b;
        private Set<Map.Entry<Integer, V>> c;
        private Collection<V> d;
        private Iterable<IntObjectMap.PrimitiveEntry<V>> e;

        b(IntObjectMap<V> intObjectMap) {
            this.a = intObjectMap;
        }

        @Override // io.netty.util.collection.IntObjectMap
        public V get(int i) {
            return this.a.get(i);
        }

        @Override // io.netty.util.collection.IntObjectMap
        public V put(int i, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // io.netty.util.collection.IntObjectMap
        public V remove(int i) {
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

        @Override // io.netty.util.collection.IntObjectMap
        public boolean containsKey(int i) {
            return this.a.containsKey(i);
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
        public V put(Integer num, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override // io.netty.util.collection.IntObjectMap
        public Iterable<IntObjectMap.PrimitiveEntry<V>> entries() {
            if (this.e == null) {
                this.e = new Iterable<IntObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.IntCollections.b.1
                    @Override // java.lang.Iterable
                    public Iterator<IntObjectMap.PrimitiveEntry<V>> iterator() {
                        b bVar = b.this;
                        return new C0207b(bVar.a.entries().iterator());
                    }
                };
            }
            return this.e;
        }

        @Override // java.util.Map
        public Set<Integer> keySet() {
            if (this.b == null) {
                this.b = Collections.unmodifiableSet(this.a.keySet());
            }
            return this.b;
        }

        @Override // java.util.Map
        public Set<Map.Entry<Integer, V>> entrySet() {
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

        /* renamed from: io.netty.util.collection.IntCollections$b$b  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private class C0207b implements Iterator<IntObjectMap.PrimitiveEntry<V>> {
            final Iterator<IntObjectMap.PrimitiveEntry<V>> a;

            C0207b(Iterator<IntObjectMap.PrimitiveEntry<V>> it) {
                this.a = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public IntObjectMap.PrimitiveEntry<V> next() {
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
        public class a implements IntObjectMap.PrimitiveEntry<V> {
            private final IntObjectMap.PrimitiveEntry<V> b;

            a(IntObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.b = primitiveEntry;
            }

            @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
            public int key() {
                return this.b.key();
            }

            @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
            public V value() {
                return this.b.value();
            }

            @Override // io.netty.util.collection.IntObjectMap.PrimitiveEntry
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }
    }
}
