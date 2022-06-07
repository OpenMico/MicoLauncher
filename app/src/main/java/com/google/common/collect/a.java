package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractBiMap.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class a<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    @RetainedWith
    @MonotonicNonNullDecl
    transient a<V, K> a;
    @MonotonicNonNullDecl
    private transient Map<K, V> b;
    @MonotonicNonNullDecl
    private transient Set<K> c;
    @MonotonicNonNullDecl
    private transient Set<V> d;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> e;

    @CanIgnoreReturnValue
    K a(@NullableDecl K k) {
        return k;
    }

    @CanIgnoreReturnValue
    V b(@NullableDecl V v) {
        return v;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Map<K, V> map, Map<V, K> map2) {
        a(map, map2);
    }

    private a(Map<K, V> map, a<V, K> aVar) {
        this.b = map;
        this.a = aVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public Map<K, V> delegate() {
        return this.b;
    }

    void a(Map<K, V> map, Map<V, K> map2) {
        boolean z = true;
        Preconditions.checkState(this.b == null);
        Preconditions.checkState(this.a == null);
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(map2.isEmpty());
        if (map == map2) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.b = map;
        this.a = a((Map) map2);
    }

    a<V, K> a(Map<V, K> map) {
        return new c(map, this);
    }

    void a(a<V, K> aVar) {
        this.a = aVar;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V put(@NullableDecl K k, @NullableDecl V v) {
        return a(k, v, false);
    }

    @CanIgnoreReturnValue
    public V forcePut(@NullableDecl K k, @NullableDecl V v) {
        return a(k, v, true);
    }

    private V a(@NullableDecl K k, @NullableDecl V v, boolean z) {
        a((a<K, V>) k);
        b((a<K, V>) v);
        boolean containsKey = containsKey(k);
        if (containsKey && Objects.equal(v, get(k))) {
            return v;
        }
        if (z) {
            inverse().remove(v);
        } else {
            Preconditions.checkArgument(!containsValue(v), "value already present: %s", v);
        }
        V put = this.b.put(k, v);
        a(k, containsKey, put, v);
        return put;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(K k, boolean z, V v, V v2) {
        if (z) {
            d(v);
        }
        this.a.b.put(v2, k);
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj) {
        if (containsKey(obj)) {
            return c(obj);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V c(Object obj) {
        V remove = this.b.remove(obj);
        d(remove);
        return remove;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(V v) {
        this.a.b.remove(v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public void clear() {
        this.b.clear();
        this.a.b.clear();
    }

    public BiMap<V, K> inverse() {
        return this.a;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.c;
        if (set != null) {
            return set;
        }
        d dVar = new d();
        this.c = dVar;
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractBiMap.java */
    /* loaded from: classes2.dex */
    public class d extends ForwardingSet<K> {
        private d() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<K> delegate() {
            return a.this.b.keySet();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public void clear() {
            a.this.clear();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            a.this.c(obj);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return Maps.a(a.this.entrySet().iterator());
        }
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public Set<V> values() {
        Set<V> set = this.d;
        if (set != null) {
            return set;
        }
        e eVar = new e();
        this.d = eVar;
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractBiMap.java */
    /* loaded from: classes2.dex */
    public class e extends ForwardingSet<V> {
        final Set<V> a;

        private e() {
            this.a = a.this.a.keySet();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<V> delegate() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return Maps.b(a.this.entrySet().iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }

        @Override // com.google.common.collect.ForwardingObject
        public String toString() {
            return standardToString();
        }
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.e;
        if (set != null) {
            return set;
        }
        b bVar = new b();
        this.e = bVar;
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractBiMap.java */
    /* renamed from: com.google.common.collect.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public class C0098a extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> b;

        C0098a(Map.Entry<K, V> entry) {
            this.b = entry;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
        public Map.Entry<K, V> delegate() {
            return this.b;
        }

        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
        public V setValue(V v) {
            a.this.b((a) v);
            Preconditions.checkState(a.this.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v, getValue())) {
                return v;
            }
            Preconditions.checkArgument(!a.this.containsValue(v), "value already present: %s", v);
            V value = this.b.setValue(v);
            Preconditions.checkState(Objects.equal(v, a.this.get(getKey())), "entry no longer in map");
            a.this.a(getKey(), true, value, v);
            return value;
        }
    }

    Iterator<Map.Entry<K, V>> a() {
        final Iterator<Map.Entry<K, V>> it = this.b.entrySet().iterator();
        return new Iterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.a.1
            @NullableDecl
            Map.Entry<K, V> a;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                this.a = (Map.Entry) it.next();
                return new C0098a(this.a);
            }

            @Override // java.util.Iterator
            public void remove() {
                t.a(this.a != null);
                V value = this.a.getValue();
                it.remove();
                a.this.d(value);
                this.a = null;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractBiMap.java */
    /* loaded from: classes2.dex */
    public class b extends ForwardingSet<Map.Entry<K, V>> {
        final Set<Map.Entry<K, V>> a;

        private b() {
            this.a = a.this.b.entrySet();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<Map.Entry<K, V>> delegate() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public void clear() {
            a.this.clear();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!this.a.contains(obj)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            ((a) a.this.a).b.remove(entry.getValue());
            this.a.remove(entry);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return a.this.a();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Maps.a(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractBiMap.java */
    /* loaded from: classes2.dex */
    public static class c<K, V> extends a<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        protected /* bridge */ /* synthetic */ Object delegate() {
            return a.super.delegate();
        }

        @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
        public /* bridge */ /* synthetic */ Collection values() {
            return a.super.values();
        }

        c(Map<K, V> map, a<V, K> aVar) {
            super(map, aVar);
        }

        @Override // com.google.common.collect.a
        K a(K k) {
            return (K) this.a.b((a) k);
        }

        @Override // com.google.common.collect.a
        V b(V v) {
            return (V) this.a.a((a) v);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(inverse());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            a((a) ((a) objectInputStream.readObject()));
        }

        @GwtIncompatible
        Object readResolve() {
            return inverse().inverse();
        }
    }
}
