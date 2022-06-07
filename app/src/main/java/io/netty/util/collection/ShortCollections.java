package io.netty.util.collection;

import io.netty.util.collection.ShortObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ShortCollections {
    private static final ShortObjectMap<Object> a = new a();

    private ShortCollections() {
    }

    public static <V> ShortObjectMap<V> emptyMap() {
        return (ShortObjectMap<V>) a;
    }

    public static <V> ShortObjectMap<V> unmodifiableMap(ShortObjectMap<V> shortObjectMap) {
        return new b(shortObjectMap);
    }

    /* loaded from: classes4.dex */
    private static final class a implements ShortObjectMap<Object> {
        @Override // java.util.Map
        public void clear() {
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return false;
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public boolean containsKey(short s) {
            return false;
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return false;
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            return null;
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public Object get(short s) {
            return null;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            return null;
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public Object remove(short s) {
            return null;
        }

        @Override // java.util.Map
        public int size() {
            return 0;
        }

        private a() {
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public Object put(short s, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public Set<Short> keySet() {
            return Collections.emptySet();
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public Iterable<ShortObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        /* renamed from: a */
        public Object put(Short sh, Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Short, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override // java.util.Map
        public Set<Map.Entry<Short, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<V> implements ShortObjectMap<V> {
        private final ShortObjectMap<V> a;
        private Set<Short> b;
        private Set<Map.Entry<Short, V>> c;
        private Collection<V> d;
        private Iterable<ShortObjectMap.PrimitiveEntry<V>> e;

        b(ShortObjectMap<V> shortObjectMap) {
            this.a = shortObjectMap;
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public V get(short s) {
            return this.a.get(s);
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public V put(short s, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public V remove(short s) {
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

        @Override // io.netty.util.collection.ShortObjectMap
        public boolean containsKey(short s) {
            return this.a.containsKey(s);
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
        public V put(Short sh, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Short, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override // io.netty.util.collection.ShortObjectMap
        public Iterable<ShortObjectMap.PrimitiveEntry<V>> entries() {
            if (this.e == null) {
                this.e = new Iterable<ShortObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.ShortCollections.b.1
                    @Override // java.lang.Iterable
                    public Iterator<ShortObjectMap.PrimitiveEntry<V>> iterator() {
                        b bVar = b.this;
                        return new C0209b(bVar.a.entries().iterator());
                    }
                };
            }
            return this.e;
        }

        @Override // java.util.Map
        public Set<Short> keySet() {
            if (this.b == null) {
                this.b = Collections.unmodifiableSet(this.a.keySet());
            }
            return this.b;
        }

        @Override // java.util.Map
        public Set<Map.Entry<Short, V>> entrySet() {
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

        /* renamed from: io.netty.util.collection.ShortCollections$b$b  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private class C0209b implements Iterator<ShortObjectMap.PrimitiveEntry<V>> {
            final Iterator<ShortObjectMap.PrimitiveEntry<V>> a;

            C0209b(Iterator<ShortObjectMap.PrimitiveEntry<V>> it) {
                this.a = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public ShortObjectMap.PrimitiveEntry<V> next() {
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
        public class a implements ShortObjectMap.PrimitiveEntry<V> {
            private final ShortObjectMap.PrimitiveEntry<V> b;

            a(ShortObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.b = primitiveEntry;
            }

            @Override // io.netty.util.collection.ShortObjectMap.PrimitiveEntry
            public short key() {
                return this.b.key();
            }

            @Override // io.netty.util.collection.ShortObjectMap.PrimitiveEntry
            public V value() {
                return this.b.value();
            }

            @Override // io.netty.util.collection.ShortObjectMap.PrimitiveEntry
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }
    }
}
