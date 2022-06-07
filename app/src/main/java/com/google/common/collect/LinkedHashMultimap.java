package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class LinkedHashMultimap<K, V> extends bi<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    @VisibleForTesting
    transient int a;
    private transient a<K, V> b = new a<>(null, null, 0, null);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface c<K, V> {
        c<K, V> a();

        void a(c<K, V> cVar);

        c<K, V> b();

        void b(c<K, V> cVar);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsKey(@NullableDecl Object obj) {
        return super.containsKey(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Set get(@NullableDecl Object obj) {
        return super.get(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean put(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.put(obj, obj2);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(@NullableDecl Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Set removeAll(@NullableDecl Object obj) {
        return super.removeAll(obj);
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.google.common.collect.g
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<>(16, 2);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int i, int i2) {
        return new LinkedHashMultimap<>(Maps.a(i), Maps.a(i2));
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> create = create(multimap.keySet().size(), 2);
        create.putAll(multimap);
        return create;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> void b(c<K, V> cVar, c<K, V> cVar2) {
        cVar.b(cVar2);
        cVar2.a(cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> void b(a<K, V> aVar, a<K, V> aVar2) {
        aVar.a((a) aVar2);
        aVar2.b((a) aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> void b(c<K, V> cVar) {
        b(cVar.a(), cVar.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <K, V> void b(a<K, V> aVar) {
        b((a) aVar.c(), (a) aVar.d());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class a<K, V> extends aw<K, V> implements c<K, V> {
        @NullableDecl
        a<K, V> nextInValueBucket;
        @NullableDecl
        a<K, V> predecessorInMultimap;
        @NullableDecl
        c<K, V> predecessorInValueSet;
        final int smearedValueHash;
        @NullableDecl
        a<K, V> successorInMultimap;
        @NullableDecl
        c<K, V> successorInValueSet;

        a(@NullableDecl K k, @NullableDecl V v, int i, @NullableDecl a<K, V> aVar) {
            super(k, v);
            this.smearedValueHash = i;
            this.nextInValueBucket = aVar;
        }

        boolean a(@NullableDecl Object obj, int i) {
            return this.smearedValueHash == i && Objects.equal(getValue(), obj);
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public c<K, V> a() {
            return this.predecessorInValueSet;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public c<K, V> b() {
            return this.successorInValueSet;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public void a(c<K, V> cVar) {
            this.predecessorInValueSet = cVar;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public void b(c<K, V> cVar) {
            this.successorInValueSet = cVar;
        }

        public a<K, V> c() {
            return this.predecessorInMultimap;
        }

        public a<K, V> d() {
            return this.successorInMultimap;
        }

        public void a(a<K, V> aVar) {
            this.successorInMultimap = aVar;
        }

        public void b(a<K, V> aVar) {
            this.predecessorInMultimap = aVar;
        }
    }

    private LinkedHashMultimap(int i, int i2) {
        super(bp.b(i));
        this.a = 2;
        t.a(i2, "expectedValuesPerKey");
        this.a = i2;
        a<K, V> aVar = this.b;
        b((a) aVar, (a) aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.k
    /* renamed from: a */
    public Set<V> c() {
        return bp.d(this.a);
    }

    @Override // com.google.common.collect.d
    Collection<V> a(K k) {
        return new b(k, this.a);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public Set<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return super.replaceValues((Object) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.k, com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    public Set<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g, com.google.common.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public final class b extends Sets.f<V> implements c<K, V> {
        @VisibleForTesting
        a<K, V>[] a;
        private final K c;
        private int d = 0;
        private int e = 0;
        private c<K, V> f = this;
        private c<K, V> g = this;

        b(K k, int i) {
            this.c = k;
            this.a = new a[au.a(i, 1.0d)];
        }

        private int c() {
            return this.a.length - 1;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public c<K, V> a() {
            return this.g;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public c<K, V> b() {
            return this.f;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public void a(c<K, V> cVar) {
            this.g = cVar;
        }

        @Override // com.google.common.collect.LinkedHashMultimap.c
        public void b(c<K, V> cVar) {
            this.f = cVar;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<V> iterator() {
            return new Iterator<V>() { // from class: com.google.common.collect.LinkedHashMultimap.b.1
                c<K, V> a;
                @NullableDecl
                a<K, V> b;
                int c;

                {
                    this.a = b.this.f;
                    this.c = b.this.e;
                }

                private void a() {
                    if (b.this.e != this.c) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    a();
                    return this.a != b.this;
                }

                @Override // java.util.Iterator
                public V next() {
                    if (hasNext()) {
                        a<K, V> aVar = (a) this.a;
                        V value = aVar.getValue();
                        this.b = aVar;
                        this.a = aVar.b();
                        return value;
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    a();
                    t.a(this.b != null);
                    b.this.remove(this.b.getValue());
                    this.c = b.this.e;
                    this.b = null;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.d;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@NullableDecl Object obj) {
            int a = au.a(obj);
            for (a<K, V> aVar = this.a[c() & a]; aVar != null; aVar = aVar.nextInValueBucket) {
                if (aVar.a(obj, a)) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(@NullableDecl V v) {
            int a = au.a(v);
            int c = c() & a;
            a<K, V> aVar = this.a[c];
            for (a<K, V> aVar2 = aVar; aVar2 != null; aVar2 = aVar2.nextInValueBucket) {
                if (aVar2.a(v, a)) {
                    return false;
                }
            }
            a<K, V> aVar3 = new a<>(this.c, v, a, aVar);
            LinkedHashMultimap.b(this.g, aVar3);
            LinkedHashMultimap.b(aVar3, this);
            LinkedHashMultimap.b((a) LinkedHashMultimap.this.b.c(), (a) aVar3);
            LinkedHashMultimap.b((a) aVar3, LinkedHashMultimap.this.b);
            this.a[c] = aVar3;
            this.d++;
            this.e++;
            d();
            return true;
        }

        private void d() {
            if (au.a(this.d, this.a.length, 1.0d)) {
                a<K, V>[] aVarArr = new a[this.a.length * 2];
                this.a = aVarArr;
                int length = aVarArr.length - 1;
                for (c<K, V> cVar = this.f; cVar != this; cVar = cVar.b()) {
                    a<K, V> aVar = (a) cVar;
                    int i = aVar.smearedValueHash & length;
                    aVar.nextInValueBucket = aVarArr[i];
                    aVarArr[i] = aVar;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @CanIgnoreReturnValue
        public boolean remove(@NullableDecl Object obj) {
            int a = au.a(obj);
            int c = c() & a;
            a<K, V> aVar = this.a[c];
            a<K, V> aVar2 = null;
            while (aVar != null) {
                if (aVar.a(obj, a)) {
                    if (aVar2 == null) {
                        this.a[c] = aVar.nextInValueBucket;
                    } else {
                        aVar2.nextInValueBucket = aVar.nextInValueBucket;
                    }
                    LinkedHashMultimap.b((c) aVar);
                    LinkedHashMultimap.b((a) aVar);
                    this.d--;
                    this.e++;
                    return true;
                }
                aVar = aVar.nextInValueBucket;
                aVar2 = aVar;
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Arrays.fill(this.a, (Object) null);
            this.d = 0;
            for (c<K, V> cVar = this.f; cVar != this; cVar = cVar.b()) {
                LinkedHashMultimap.b((a) cVar);
            }
            LinkedHashMultimap.b(this, this);
            this.e++;
        }
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g
    Iterator<Map.Entry<K, V>> l() {
        return new Iterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.LinkedHashMultimap.1
            a<K, V> a;
            @NullableDecl
            a<K, V> b;

            {
                this.a = LinkedHashMultimap.this.b.successorInMultimap;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a != LinkedHashMultimap.this.b;
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                if (hasNext()) {
                    a<K, V> aVar = this.a;
                    this.b = aVar;
                    this.a = aVar.successorInMultimap;
                    return aVar;
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                t.a(this.b != null);
                LinkedHashMultimap.this.remove(this.b.getKey(), this.b.getValue());
                this.b = null;
            }
        };
    }

    @Override // com.google.common.collect.d, com.google.common.collect.g
    Iterator<V> i() {
        return Maps.b(l());
    }

    @Override // com.google.common.collect.d, com.google.common.collect.Multimap
    public void clear() {
        super.clear();
        a<K, V> aVar = this.b;
        b((a) aVar, (a) aVar);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(keySet().size());
        for (K k : keySet()) {
            objectOutputStream.writeObject(k);
        }
        objectOutputStream.writeInt(size());
        for (Map.Entry<K, V> entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.b = new a<>(null, null, 0, null);
        a<K, V> aVar = this.b;
        b((a) aVar, (a) aVar);
        this.a = 2;
        int readInt = objectInputStream.readInt();
        Map b2 = bp.b(12);
        for (int i = 0; i < readInt; i++) {
            Object readObject = objectInputStream.readObject();
            b2.put(readObject, a((LinkedHashMultimap<K, V>) readObject));
        }
        int readInt2 = objectInputStream.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            ((Collection) b2.get(objectInputStream.readObject())).add(objectInputStream.readObject());
        }
        a(b2);
    }
}
