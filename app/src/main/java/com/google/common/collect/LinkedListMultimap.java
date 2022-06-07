package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public class LinkedListMultimap<K, V> extends g<K, V> implements ListMultimap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    @NullableDecl
    private transient f<K, V> a;
    @NullableDecl
    private transient f<K, V> b;
    private transient Map<K, e<K, V>> c;
    private transient int d;
    private transient int e;

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
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

    @Override // com.google.common.collect.g
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    /* loaded from: classes2.dex */
    public static final class f<K, V> extends f<K, V> {
        @NullableDecl
        final K a;
        @NullableDecl
        V b;
        @NullableDecl
        f<K, V> c;
        @NullableDecl
        f<K, V> d;
        @NullableDecl
        f<K, V> e;
        @NullableDecl
        f<K, V> f;

        f(@NullableDecl K k, @NullableDecl V v) {
            this.a = k;
            this.b = v;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public K getKey() {
            return this.a;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V setValue(@NullableDecl V v) {
            V v2 = this.b;
            this.b = v;
            return v2;
        }
    }

    /* loaded from: classes2.dex */
    public static class e<K, V> {
        f<K, V> a;
        f<K, V> b;
        int c = 1;

        e(f<K, V> fVar) {
            this.a = fVar;
            this.b = fVar;
            fVar.f = null;
            fVar.e = null;
        }
    }

    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<>();
    }

    public static <K, V> LinkedListMultimap<K, V> create(int i) {
        return new LinkedListMultimap<>(i);
    }

    public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new LinkedListMultimap<>(multimap);
    }

    LinkedListMultimap() {
        this(12);
    }

    private LinkedListMultimap(int i) {
        this.c = bp.a(i);
    }

    private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size());
        putAll(multimap);
    }

    @CanIgnoreReturnValue
    public f<K, V> a(@NullableDecl K k, @NullableDecl V v, @NullableDecl f<K, V> fVar) {
        f<K, V> fVar2 = new f<>(k, v);
        if (this.a == null) {
            this.b = fVar2;
            this.a = fVar2;
            this.c.put(k, new e<>(fVar2));
            this.e++;
        } else if (fVar == null) {
            f<K, V> fVar3 = this.b;
            fVar3.c = fVar2;
            fVar2.d = fVar3;
            this.b = fVar2;
            e<K, V> eVar = this.c.get(k);
            if (eVar == null) {
                this.c.put(k, new e<>(fVar2));
                this.e++;
            } else {
                eVar.c++;
                f<K, V> fVar4 = eVar.b;
                fVar4.e = fVar2;
                fVar2.f = fVar4;
                eVar.b = fVar2;
            }
        } else {
            this.c.get(k).c++;
            fVar2.d = fVar.d;
            fVar2.f = fVar.f;
            fVar2.c = fVar;
            fVar2.e = fVar;
            if (fVar.f == null) {
                this.c.get(k).a = fVar2;
            } else {
                fVar.f.e = fVar2;
            }
            if (fVar.d == null) {
                this.a = fVar2;
            } else {
                fVar.d.c = fVar2;
            }
            fVar.d = fVar2;
            fVar.f = fVar2;
        }
        this.d++;
        return fVar2;
    }

    public void a(f<K, V> fVar) {
        if (fVar.d != null) {
            fVar.d.c = fVar.c;
        } else {
            this.a = fVar.c;
        }
        if (fVar.c != null) {
            fVar.c.d = fVar.d;
        } else {
            this.b = fVar.d;
        }
        if (fVar.f == null && fVar.e == null) {
            this.c.remove(fVar.a).c = 0;
            this.e++;
        } else {
            e<K, V> eVar = this.c.get(fVar.a);
            eVar.c--;
            if (fVar.f == null) {
                eVar.a = fVar.e;
            } else {
                fVar.f.e = fVar.e;
            }
            if (fVar.e == null) {
                eVar.b = fVar.f;
            } else {
                fVar.e.f = fVar.f;
            }
        }
        this.d--;
    }

    public void b(@NullableDecl Object obj) {
        Iterators.b(new h(obj));
    }

    public static void c(@NullableDecl Object obj) {
        if (obj == null) {
            throw new NoSuchElementException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class g implements ListIterator<Map.Entry<K, V>> {
        int a;
        @NullableDecl
        f<K, V> b;
        @NullableDecl
        f<K, V> c;
        @NullableDecl
        f<K, V> d;
        int e;

        @Override // java.util.ListIterator
        public /* synthetic */ void set(Object obj) {
            a((Map.Entry) ((Map.Entry) obj));
        }

        g(int i) {
            LinkedListMultimap.this = r3;
            this.e = LinkedListMultimap.this.e;
            int size = r3.size();
            Preconditions.checkPositionIndex(i, size);
            if (i < size / 2) {
                this.b = r3.a;
                while (true) {
                    i--;
                    if (i <= 0) {
                        break;
                    }
                    next();
                }
            } else {
                this.d = r3.b;
                this.a = size;
                while (true) {
                    i++;
                    if (i >= size) {
                        break;
                    }
                    previous();
                }
            }
            this.c = null;
        }

        private void c() {
            if (LinkedListMultimap.this.e != this.e) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            c();
            return this.b != null;
        }

        @CanIgnoreReturnValue
        /* renamed from: a */
        public f<K, V> next() {
            c();
            LinkedListMultimap.c(this.b);
            f<K, V> fVar = this.b;
            this.c = fVar;
            this.d = fVar;
            this.b = fVar.c;
            this.a++;
            return this.c;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            c();
            t.a(this.c != null);
            f<K, V> fVar = this.c;
            if (fVar != this.b) {
                this.d = fVar.d;
                this.a--;
            } else {
                this.b = fVar.c;
            }
            LinkedListMultimap.this.a((f) this.c);
            this.c = null;
            this.e = LinkedListMultimap.this.e;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            c();
            return this.d != null;
        }

        @CanIgnoreReturnValue
        /* renamed from: b */
        public f<K, V> previous() {
            c();
            LinkedListMultimap.c(this.d);
            f<K, V> fVar = this.d;
            this.c = fVar;
            this.b = fVar;
            this.d = fVar.d;
            this.a--;
            return this.c;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.a;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.a - 1;
        }

        public void a(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        /* renamed from: b */
        public void add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        void a(V v) {
            Preconditions.checkState(this.c != null);
            this.c.b = v;
        }
    }

    /* loaded from: classes2.dex */
    private class d implements Iterator<K> {
        final Set<K> a;
        f<K, V> b;
        @NullableDecl
        f<K, V> c;
        int d;

        private d() {
            LinkedListMultimap.this = r1;
            this.a = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.b = LinkedListMultimap.this.a;
            this.d = LinkedListMultimap.this.e;
        }

        private void a() {
            if (LinkedListMultimap.this.e != this.d) {
                throw new ConcurrentModificationException();
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            a();
            return this.b != null;
        }

        @Override // java.util.Iterator
        public K next() {
            f<K, V> fVar;
            a();
            LinkedListMultimap.c(this.b);
            this.c = this.b;
            this.a.add(this.c.a);
            do {
                this.b = this.b.c;
                fVar = this.b;
                if (fVar == null) {
                    break;
                }
            } while (!this.a.add(fVar.a));
            return this.c.a;
        }

        @Override // java.util.Iterator
        public void remove() {
            a();
            t.a(this.c != null);
            LinkedListMultimap.this.b(this.c.a);
            this.c = null;
            this.d = LinkedListMultimap.this.e;
        }
    }

    /* loaded from: classes2.dex */
    public class h implements ListIterator<V> {
        @NullableDecl
        final Object a;
        int b;
        @NullableDecl
        f<K, V> c;
        @NullableDecl
        f<K, V> d;
        @NullableDecl
        f<K, V> e;

        h(Object obj) {
            LinkedListMultimap.this = r1;
            this.a = obj;
            e eVar = (e) r1.c.get(obj);
            this.c = eVar == null ? null : eVar.a;
        }

        public h(Object obj, @NullableDecl int i) {
            LinkedListMultimap.this = r4;
            e eVar = (e) r4.c.get(obj);
            int i2 = eVar == null ? 0 : eVar.c;
            Preconditions.checkPositionIndex(i, i2);
            if (i < i2 / 2) {
                this.c = eVar == null ? null : eVar.a;
                while (true) {
                    i--;
                    if (i <= 0) {
                        break;
                    }
                    next();
                }
            } else {
                this.e = eVar == null ? null : eVar.b;
                this.b = i2;
                while (true) {
                    i++;
                    if (i >= i2) {
                        break;
                    }
                    previous();
                }
            }
            this.a = obj;
            this.d = null;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.c != null;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        @CanIgnoreReturnValue
        public V next() {
            LinkedListMultimap.c(this.c);
            f<K, V> fVar = this.c;
            this.d = fVar;
            this.e = fVar;
            this.c = fVar.e;
            this.b++;
            return this.d.b;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.e != null;
        }

        @Override // java.util.ListIterator
        @CanIgnoreReturnValue
        public V previous() {
            LinkedListMultimap.c(this.e);
            f<K, V> fVar = this.e;
            this.d = fVar;
            this.c = fVar;
            this.e = fVar.f;
            this.b--;
            return this.d.b;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.b;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.b - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            t.a(this.d != null);
            f<K, V> fVar = this.d;
            if (fVar != this.c) {
                this.e = fVar.f;
                this.b--;
            } else {
                this.c = fVar.e;
            }
            LinkedListMultimap.this.a((f) this.d);
            this.d = null;
        }

        @Override // java.util.ListIterator
        public void set(V v) {
            Preconditions.checkState(this.d != null);
            this.d.b = v;
        }

        @Override // java.util.ListIterator
        public void add(V v) {
            this.e = LinkedListMultimap.this.a(this.a, v, this.c);
            this.b++;
            this.d = null;
        }
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return this.d;
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public boolean isEmpty() {
        return this.a == null;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        return this.c.containsKey(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public boolean containsValue(@NullableDecl Object obj) {
        return values().contains(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        a(k, v, null);
        return true;
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        List<V> d2 = d(k);
        h hVar = new h(k);
        Iterator<? extends V> it = iterable.iterator();
        while (hVar.hasNext() && it.hasNext()) {
            hVar.next();
            hVar.set(it.next());
        }
        while (hVar.hasNext()) {
            hVar.next();
            hVar.remove();
        }
        while (it.hasNext()) {
            hVar.add(it.next());
        }
        return d2;
    }

    private List<V> d(@NullableDecl Object obj) {
        return Collections.unmodifiableList(Lists.newArrayList(new h(obj)));
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public List<V> removeAll(@NullableDecl Object obj) {
        List<V> d2 = d(obj);
        b(obj);
        return d2;
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        this.a = null;
        this.b = null;
        this.c.clear();
        this.d = 0;
        this.e++;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public List<V> get(@NullableDecl final K k) {
        return new AbstractSequentialList<V>() { // from class: com.google.common.collect.LinkedListMultimap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                e eVar = (e) LinkedListMultimap.this.c.get(k);
                if (eVar == null) {
                    return 0;
                }
                return eVar.c;
            }

            @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
            public ListIterator<V> listIterator(int i) {
                return new h(k, i);
            }
        };
    }

    /* loaded from: classes2.dex */
    class b extends Sets.f<K> {
        b() {
            LinkedListMultimap.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return LinkedListMultimap.this.c.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return LinkedListMultimap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return !LinkedListMultimap.this.removeAll(obj).isEmpty();
        }
    }

    @Override // com.google.common.collect.g
    Set<K> f() {
        return new b();
    }

    @Override // com.google.common.collect.g
    Multiset<K> j() {
        return new Multimaps.g(this);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public List<V> values() {
        return (List) super.values();
    }

    /* loaded from: classes2.dex */
    public class c extends AbstractSequentialList<V> {
        c() {
            LinkedListMultimap.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return LinkedListMultimap.this.d;
        }

        @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
        public ListIterator<V> listIterator(int i) {
            final g gVar = new g(i);
            return new cq<Map.Entry<K, V>, V>(gVar) { // from class: com.google.common.collect.LinkedListMultimap.c.1
                @Override // com.google.common.collect.cp
                /* bridge */ /* synthetic */ Object a(Object obj) {
                    return a((Map.Entry<K, Object>) obj);
                }

                V a(Map.Entry<K, V> entry) {
                    return entry.getValue();
                }

                @Override // com.google.common.collect.cq, java.util.ListIterator
                public void set(V v) {
                    gVar.a((g) v);
                }
            };
        }
    }

    /* renamed from: a */
    public List<V> h() {
        return new c();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public List<Map.Entry<K, V>> entries() {
        return (List) super.entries();
    }

    /* loaded from: classes2.dex */
    public class a extends AbstractSequentialList<Map.Entry<K, V>> {
        a() {
            LinkedListMultimap.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return LinkedListMultimap.this.d;
        }

        @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
        public ListIterator<Map.Entry<K, V>> listIterator(int i) {
            return new g(i);
        }
    }

    /* renamed from: b */
    public List<Map.Entry<K, V>> k() {
        return new a();
    }

    @Override // com.google.common.collect.g
    Iterator<Map.Entry<K, V>> l() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.g
    Map<K, Collection<V>> m() {
        return new Multimaps.a(this);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
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
        this.c = w.i();
        int readInt = objectInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }
}
