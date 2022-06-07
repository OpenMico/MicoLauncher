package io.netty.util.collection;

import io.netty.util.collection.ByteObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ByteCollections {
    private static final ByteObjectMap<Object> a = new a();

    private ByteCollections() {
    }

    public static <V> ByteObjectMap<V> emptyMap() {
        return (ByteObjectMap<V>) a;
    }

    public static <V> ByteObjectMap<V> unmodifiableMap(ByteObjectMap<V> byteObjectMap) {
        return new b(byteObjectMap);
    }

    /* loaded from: classes4.dex */
    private static final class a implements ByteObjectMap<Object> {
        @Override // java.util.Map
        public void clear() {
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public boolean containsKey(byte b) {
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

        @Override // io.netty.util.collection.ByteObjectMap
        public Object get(byte b) {
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

        @Override // io.netty.util.collection.ByteObjectMap
        public Object remove(byte b) {
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

        @Override // io.netty.util.collection.ByteObjectMap
        public Object put(byte b, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public Set<Byte> keySet() {
            return Collections.emptySet();
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public Iterable<ByteObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        /* renamed from: a */
        public Object put(Byte b, Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Byte, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override // java.util.Map
        public Set<Map.Entry<Byte, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<V> implements ByteObjectMap<V> {
        private final ByteObjectMap<V> a;
        private Set<Byte> b;
        private Set<Map.Entry<Byte, V>> c;
        private Collection<V> d;
        private Iterable<ByteObjectMap.PrimitiveEntry<V>> e;

        b(ByteObjectMap<V> byteObjectMap) {
            this.a = byteObjectMap;
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public V get(byte b) {
            return this.a.get(b);
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public V put(byte b, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public V remove(byte b) {
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

        @Override // io.netty.util.collection.ByteObjectMap
        public boolean containsKey(byte b) {
            return this.a.containsKey(b);
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
        public V put(Byte b, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Byte, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override // io.netty.util.collection.ByteObjectMap
        public Iterable<ByteObjectMap.PrimitiveEntry<V>> entries() {
            if (this.e == null) {
                this.e = new Iterable<ByteObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.ByteCollections.b.1
                    @Override // java.lang.Iterable
                    public Iterator<ByteObjectMap.PrimitiveEntry<V>> iterator() {
                        b bVar = b.this;
                        return new C0205b(bVar.a.entries().iterator());
                    }
                };
            }
            return this.e;
        }

        @Override // java.util.Map
        public Set<Byte> keySet() {
            if (this.b == null) {
                this.b = Collections.unmodifiableSet(this.a.keySet());
            }
            return this.b;
        }

        @Override // java.util.Map
        public Set<Map.Entry<Byte, V>> entrySet() {
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

        /* renamed from: io.netty.util.collection.ByteCollections$b$b  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private class C0205b implements Iterator<ByteObjectMap.PrimitiveEntry<V>> {
            final Iterator<ByteObjectMap.PrimitiveEntry<V>> a;

            C0205b(Iterator<ByteObjectMap.PrimitiveEntry<V>> it) {
                this.a = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public ByteObjectMap.PrimitiveEntry<V> next() {
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
        public class a implements ByteObjectMap.PrimitiveEntry<V> {
            private final ByteObjectMap.PrimitiveEntry<V> b;

            a(ByteObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.b = primitiveEntry;
            }

            @Override // io.netty.util.collection.ByteObjectMap.PrimitiveEntry
            public byte key() {
                return this.b.key();
            }

            @Override // io.netty.util.collection.ByteObjectMap.PrimitiveEntry
            public V value() {
                return this.b.value();
            }

            @Override // io.netty.util.collection.ByteObjectMap.PrimitiveEntry
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }
    }
}
