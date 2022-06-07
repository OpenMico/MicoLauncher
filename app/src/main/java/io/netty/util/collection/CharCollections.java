package io.netty.util.collection;

import io.netty.util.collection.CharObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class CharCollections {
    private static final CharObjectMap<Object> a = new a();

    private CharCollections() {
    }

    public static <V> CharObjectMap<V> emptyMap() {
        return (CharObjectMap<V>) a;
    }

    public static <V> CharObjectMap<V> unmodifiableMap(CharObjectMap<V> charObjectMap) {
        return new b(charObjectMap);
    }

    /* loaded from: classes4.dex */
    private static final class a implements CharObjectMap<Object> {
        @Override // java.util.Map
        public void clear() {
        }

        @Override // io.netty.util.collection.CharObjectMap
        public boolean containsKey(char c) {
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

        @Override // io.netty.util.collection.CharObjectMap
        public Object get(char c) {
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

        @Override // io.netty.util.collection.CharObjectMap
        public Object remove(char c) {
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

        @Override // io.netty.util.collection.CharObjectMap
        public Object put(char c, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public Set<Character> keySet() {
            return Collections.emptySet();
        }

        @Override // io.netty.util.collection.CharObjectMap
        public Iterable<CharObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        /* renamed from: a */
        public Object put(Character ch, Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Character, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override // java.util.Map
        public Set<Map.Entry<Character, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b<V> implements CharObjectMap<V> {
        private final CharObjectMap<V> a;
        private Set<Character> b;
        private Set<Map.Entry<Character, V>> c;
        private Collection<V> d;
        private Iterable<CharObjectMap.PrimitiveEntry<V>> e;

        b(CharObjectMap<V> charObjectMap) {
            this.a = charObjectMap;
        }

        @Override // io.netty.util.collection.CharObjectMap
        public V get(char c) {
            return this.a.get(c);
        }

        @Override // io.netty.util.collection.CharObjectMap
        public V put(char c, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // io.netty.util.collection.CharObjectMap
        public V remove(char c) {
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

        @Override // io.netty.util.collection.CharObjectMap
        public boolean containsKey(char c) {
            return this.a.containsKey(c);
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
        public V put(Character ch, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException("remove");
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Character, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override // io.netty.util.collection.CharObjectMap
        public Iterable<CharObjectMap.PrimitiveEntry<V>> entries() {
            if (this.e == null) {
                this.e = new Iterable<CharObjectMap.PrimitiveEntry<V>>() { // from class: io.netty.util.collection.CharCollections.b.1
                    @Override // java.lang.Iterable
                    public Iterator<CharObjectMap.PrimitiveEntry<V>> iterator() {
                        b bVar = b.this;
                        return new C0206b(bVar.a.entries().iterator());
                    }
                };
            }
            return this.e;
        }

        @Override // java.util.Map
        public Set<Character> keySet() {
            if (this.b == null) {
                this.b = Collections.unmodifiableSet(this.a.keySet());
            }
            return this.b;
        }

        @Override // java.util.Map
        public Set<Map.Entry<Character, V>> entrySet() {
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

        /* renamed from: io.netty.util.collection.CharCollections$b$b  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private class C0206b implements Iterator<CharObjectMap.PrimitiveEntry<V>> {
            final Iterator<CharObjectMap.PrimitiveEntry<V>> a;

            C0206b(Iterator<CharObjectMap.PrimitiveEntry<V>> it) {
                this.a = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext();
            }

            /* renamed from: a */
            public CharObjectMap.PrimitiveEntry<V> next() {
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
        public class a implements CharObjectMap.PrimitiveEntry<V> {
            private final CharObjectMap.PrimitiveEntry<V> b;

            a(CharObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.b = primitiveEntry;
            }

            @Override // io.netty.util.collection.CharObjectMap.PrimitiveEntry
            public char key() {
                return this.b.key();
            }

            @Override // io.netty.util.collection.CharObjectMap.PrimitiveEntry
            public V value() {
                return this.b.value();
            }

            @Override // io.netty.util.collection.CharObjectMap.PrimitiveEntry
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }
    }
}
