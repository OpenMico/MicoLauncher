package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StandardTable.java */
@GwtCompatible
/* loaded from: classes2.dex */
public class cm<R, C, V> extends o<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @MonotonicNonNullDecl
    private transient Set<C> a;
    @MonotonicNonNullDecl
    private transient Map<R, Map<C, V>> b;
    final Map<R, Map<C, V>> backingMap;
    @MonotonicNonNullDecl
    private transient cm<R, C, V>.e c;
    final Supplier<? extends Map<C, V>> factory;

    /* JADX INFO: Access modifiers changed from: package-private */
    public cm(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return (obj == null || obj2 == null || !super.contains(obj, obj2)) ? false : true;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsColumn(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        for (Map<C, V> map : this.backingMap.values()) {
            if (Maps.b((Map<?, ?>) map, obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsRow(@NullableDecl Object obj) {
        return obj != null && Maps.b((Map<?, ?>) this.backingMap, obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        return obj != null && super.containsValue(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return (V) super.get(obj, obj2);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override // com.google.common.collect.Table
    public int size() {
        int i = 0;
        for (Map<C, V> map : this.backingMap.values()) {
            i += map.size();
        }
        return i;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public void clear() {
        this.backingMap.clear();
    }

    private Map<C, V> a(R r) {
        Map<C, V> map = this.backingMap.get(r);
        if (map != null) {
            return map;
        }
        Map<C, V> map2 = (Map) this.factory.get();
        this.backingMap.put(r, map2);
        return map2;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V put(R r, C c2, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c2);
        Preconditions.checkNotNull(v);
        return a(r).put(c2, v);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Map map;
        if (obj == null || obj2 == null || (map = (Map) Maps.a((Map<?, Object>) this.backingMap, obj)) == null) {
            return null;
        }
        V v = (V) map.remove(obj2);
        if (map.isEmpty()) {
            this.backingMap.remove(obj);
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Map<R, V> b(Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<Map.Entry<R, Map<C, V>>> it = this.backingMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<R, Map<C, V>> next = it.next();
            V remove = next.getValue().remove(obj);
            if (remove != null) {
                linkedHashMap.put(next.getKey(), remove);
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Object obj, Object obj2, Object obj3) {
        return obj3 != null && obj3.equals(get(obj, obj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(Object obj, Object obj2, Object obj3) {
        if (!a(obj, obj2, obj3)) {
            return false;
        }
        remove(obj, obj2);
        return true;
    }

    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    private abstract class h<T> extends Sets.f<T> {
        private h() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return cm.this.backingMap.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            cm.this.backingMap.clear();
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.o
    Iterator<Table.Cell<R, C, V>> b() {
        return new a();
    }

    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    private class a implements Iterator<Table.Cell<R, C, V>> {
        final Iterator<Map.Entry<R, Map<C, V>>> a;
        @NullableDecl
        Map.Entry<R, Map<C, V>> b;
        Iterator<Map.Entry<C, V>> c;

        private a() {
            this.a = cm.this.backingMap.entrySet().iterator();
            this.c = Iterators.c();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a.hasNext() || this.c.hasNext();
        }

        /* renamed from: a */
        public Table.Cell<R, C, V> next() {
            if (!this.c.hasNext()) {
                this.b = this.a.next();
                this.c = this.b.getValue().entrySet().iterator();
            }
            Map.Entry<C, V> next = this.c.next();
            return Tables.immutableCell(this.b.getKey(), next.getKey(), next.getValue());
        }

        @Override // java.util.Iterator
        public void remove() {
            this.c.remove();
            if (this.b.getValue().isEmpty()) {
                this.a.remove();
                this.b = null;
            }
        }
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r) {
        return new f(r);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class f extends Maps.m<C, V> {
        final R a;
        @NullableDecl
        Map<C, V> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public f(R r) {
            this.a = (R) Preconditions.checkNotNull(r);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Map<C, V> a() {
            Map<C, V> map = this.b;
            if (map != null && (!map.isEmpty() || !cm.this.backingMap.containsKey(this.a))) {
                return this.b;
            }
            Map<C, V> c = c();
            this.b = c;
            return c;
        }

        Map<C, V> c() {
            return cm.this.backingMap.get(this.a);
        }

        void d() {
            if (a() != null && this.b.isEmpty()) {
                cm.this.backingMap.remove(this.a);
                this.b = null;
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            Map<C, V> a = a();
            return (obj == null || a == null || !Maps.b((Map<?, ?>) a, obj)) ? false : true;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            Map<C, V> a = a();
            if (obj == null || a == null) {
                return null;
            }
            return (V) Maps.a((Map<?, Object>) a, obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(C c, V v) {
            Preconditions.checkNotNull(c);
            Preconditions.checkNotNull(v);
            Map<C, V> map = this.b;
            if (map == null || map.isEmpty()) {
                return (V) cm.this.put(this.a, c, v);
            }
            return this.b.put(c, v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            Map<C, V> a = a();
            if (a == null) {
                return null;
            }
            V v = (V) Maps.c(a, obj);
            d();
            return v;
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map
        public void clear() {
            Map<C, V> a = a();
            if (a != null) {
                a.clear();
            }
            d();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            Map<C, V> a = a();
            if (a == null) {
                return 0;
            }
            return a.size();
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<C, V>> b() {
            Map<C, V> a = a();
            if (a == null) {
                return Iterators.c();
            }
            final Iterator<Map.Entry<C, V>> it = a.entrySet().iterator();
            return new Iterator<Map.Entry<C, V>>() { // from class: com.google.common.collect.cm.f.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                /* renamed from: a */
                public Map.Entry<C, V> next() {
                    return f.this.a((Map.Entry) it.next());
                }

                @Override // java.util.Iterator
                public void remove() {
                    it.remove();
                    f.this.d();
                }
            };
        }

        Map.Entry<C, V> a(final Map.Entry<C, V> entry) {
            return new ForwardingMapEntry<C, V>() { // from class: com.google.common.collect.cm.f.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                public Map.Entry<C, V> delegate() {
                    return entry;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                public V setValue(V v) {
                    return (V) super.setValue(Preconditions.checkNotNull(v));
                }

                @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                public boolean equals(Object obj) {
                    return standardEquals(obj);
                }
            };
        }
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c2) {
        return new b(c2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class b extends Maps.ad<R, V> {
        final C a;

        b(C c2) {
            this.a = (C) Preconditions.checkNotNull(c2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(R r, V v) {
            return (V) cm.this.put(r, this.a, v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            return (V) cm.this.get(obj, this.a);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return cm.this.contains(obj, this.a);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            return (V) cm.this.remove(obj, this.a);
        }

        @CanIgnoreReturnValue
        boolean a(Predicate<? super Map.Entry<R, V>> predicate) {
            Iterator<Map.Entry<R, Map<C, V>>> it = cm.this.backingMap.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<R, Map<C, V>> next = it.next();
                Map<C, V> value = next.getValue();
                V v = value.get(this.a);
                if (v != null && predicate.apply(Maps.immutableEntry(next.getKey(), v))) {
                    value.remove(this.a);
                    z = true;
                    if (value.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Maps.ad
        Set<Map.Entry<R, V>> a() {
            return new a();
        }

        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        private class a extends Sets.f<Map.Entry<R, V>> {
            private a() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, V>> iterator() {
                return new C0101b();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i = 0;
                for (Map<C, V> map : cm.this.backingMap.values()) {
                    if (map.containsKey(b.this.a)) {
                        i++;
                    }
                }
                return i;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return !cm.this.containsColumn(b.this.a);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                b.this.a(Predicates.alwaysTrue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return cm.this.a(entry.getKey(), b.this.a, entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return cm.this.b(entry.getKey(), b.this.a, entry.getValue());
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return b.this.a(Predicates.not(Predicates.in(collection)));
            }
        }

        /* compiled from: StandardTable.java */
        /* renamed from: com.google.common.collect.cm$b$b  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private class C0101b extends AbstractIterator<Map.Entry<R, V>> {
            final Iterator<Map.Entry<R, Map<C, V>>> a;

            private C0101b() {
                this.a = cm.this.backingMap.entrySet().iterator();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public Map.Entry<R, V> computeNext() {
                while (this.a.hasNext()) {
                    Map.Entry<R, Map<C, V>> next = this.a.next();
                    if (next.getValue().containsKey(b.this.a)) {
                        return new a(next);
                    }
                }
                return endOfData();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* compiled from: StandardTable.java */
            /* renamed from: com.google.common.collect.cm$b$b$a */
            /* loaded from: classes2.dex */
            public class a extends f<R, V> {
                final /* synthetic */ Map.Entry a;

                a(Map.Entry entry) {
                    this.a = entry;
                }

                @Override // com.google.common.collect.f, java.util.Map.Entry
                public R getKey() {
                    return (R) this.a.getKey();
                }

                @Override // com.google.common.collect.f, java.util.Map.Entry
                public V getValue() {
                    return (V) ((Map) this.a.getValue()).get(b.this.a);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.f, java.util.Map.Entry
                public V setValue(V v) {
                    return (V) ((Map) this.a.getValue()).put(b.this.a, Preconditions.checkNotNull(v));
                }
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Set<R> h() {
            return new c();
        }

        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        private class c extends Maps.n<R, V> {
            c() {
                super(b.this);
            }

            @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return cm.this.contains(obj, b.this.a);
            }

            @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                return cm.this.remove(obj, b.this.a) != null;
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return b.this.a(Maps.a(Predicates.not(Predicates.in(collection))));
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Collection<V> b() {
            return new d();
        }

        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        private class d extends Maps.ac<R, V> {
            d() {
                super(b.this);
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean remove(Object obj) {
                return obj != null && b.this.a(Maps.b(Predicates.equalTo(obj)));
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                return b.this.a(Maps.b(Predicates.in(collection)));
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                return b.this.a(Maps.b(Predicates.not(Predicates.in(collection))));
            }
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Set<C> columnKeySet() {
        Set<C> set = this.a;
        if (set != null) {
            return set;
        }
        d dVar = new d();
        this.a = dVar;
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class d extends cm<R, C, V>.h {
        private d() {
            super();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<C> iterator() {
            return cm.this.g();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Iterators.size(iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            boolean z = false;
            if (obj == null) {
                return false;
            }
            Iterator<Map<C, V>> it = cm.this.backingMap.values().iterator();
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().remove(obj)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = cm.this.backingMap.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (Iterators.removeAll(next.keySet().iterator(), collection)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Iterator<Map<C, V>> it = cm.this.backingMap.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map<C, V> next = it.next();
                if (next.keySet().retainAll(collection)) {
                    z = true;
                    if (next.isEmpty()) {
                        it.remove();
                    }
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return cm.this.containsColumn(obj);
        }
    }

    Iterator<C> g() {
        return new c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class c extends AbstractIterator<C> {
        final Map<C, V> a;
        final Iterator<Map<C, V>> b;
        Iterator<Map.Entry<C, V>> c;

        private c() {
            this.a = (Map) cm.this.factory.get();
            this.b = cm.this.backingMap.values().iterator();
            this.c = Iterators.a();
        }

        @Override // com.google.common.collect.AbstractIterator
        protected C computeNext() {
            while (true) {
                if (this.c.hasNext()) {
                    Map.Entry<C, V> next = this.c.next();
                    if (!this.a.containsKey(next.getKey())) {
                        this.a.put(next.getKey(), next.getValue());
                        return next.getKey();
                    }
                } else if (!this.b.hasNext()) {
                    return endOfData();
                } else {
                    this.c = this.b.next().entrySet().iterator();
                }
            }
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.b;
        if (map != null) {
            return map;
        }
        Map<R, Map<C, V>> f2 = f();
        this.b = f2;
        return f2;
    }

    Map<R, Map<C, V>> f() {
        return new g();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class g extends Maps.ad<R, Map<C, V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public g() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return cm.this.containsRow(obj);
        }

        /* renamed from: a */
        public Map<C, V> get(Object obj) {
            if (cm.this.containsRow(obj)) {
                return cm.this.row(obj);
            }
            return null;
        }

        /* renamed from: b */
        public Map<C, V> remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return cm.this.backingMap.remove(obj);
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<R, Map<C, V>>> a() {
            return new a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        public class a extends cm<R, C, V>.h {
            a() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return Maps.a((Set) cm.this.backingMap.keySet(), (Function) new Function<R, Map<C, V>>() { // from class: com.google.common.collect.cm.g.a.1
                    /* renamed from: a */
                    public Map<C, V> apply(R r) {
                        return cm.this.row(r);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return cm.this.backingMap.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && Collections2.a(cm.this.backingMap.entrySet(), entry);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return entry.getKey() != null && (entry.getValue() instanceof Map) && cm.this.backingMap.entrySet().remove(entry);
            }
        }
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        cm<R, C, V>.e eVar = this.c;
        if (eVar != null) {
            return eVar;
        }
        cm<R, C, V>.e eVar2 = new e();
        this.c = eVar2;
        return eVar2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: StandardTable.java */
    /* loaded from: classes2.dex */
    public class e extends Maps.ad<C, Map<R, V>> {
        private e() {
        }

        /* renamed from: a */
        public Map<R, V> get(Object obj) {
            if (cm.this.containsColumn(obj)) {
                return cm.this.column(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return cm.this.containsColumn(obj);
        }

        /* renamed from: b */
        public Map<R, V> remove(Object obj) {
            if (cm.this.containsColumn(obj)) {
                return cm.this.b(obj);
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.ad
        public Set<Map.Entry<C, Map<R, V>>> a() {
            return new a();
        }

        @Override // com.google.common.collect.Maps.ad, java.util.AbstractMap, java.util.Map
        public Set<C> keySet() {
            return cm.this.columnKeySet();
        }

        @Override // com.google.common.collect.Maps.ad
        Collection<Map<R, V>> b() {
            return new b();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        public class a extends cm<R, C, V>.h {
            a() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                return Maps.a((Set) cm.this.columnKeySet(), (Function) new Function<C, Map<R, V>>() { // from class: com.google.common.collect.cm.e.a.1
                    /* renamed from: a */
                    public Map<R, V> apply(C c) {
                        return cm.this.column(c);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return cm.this.columnKeySet().size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (!cm.this.containsColumn(entry.getKey())) {
                    return false;
                }
                return e.this.get(entry.getKey()).equals(entry.getValue());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                cm.this.b(((Map.Entry) obj).getKey());
                return true;
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.a((Set<?>) this, collection.iterator());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(cm.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(Maps.immutableEntry(next, cm.this.column(next)))) {
                        cm.this.b(next);
                        z = true;
                    }
                }
                return z;
            }
        }

        /* compiled from: StandardTable.java */
        /* loaded from: classes2.dex */
        private class b extends Maps.ac<C, Map<R, V>> {
            b() {
                super(e.this);
            }

            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean remove(Object obj) {
                for (Map.Entry<C, Map<R, V>> entry : e.this.entrySet()) {
                    if (entry.getValue().equals(obj)) {
                        cm.this.b(entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(cm.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (collection.contains(cm.this.column(next))) {
                        cm.this.b(next);
                        z = true;
                    }
                }
                return z;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                Iterator it = Lists.newArrayList(cm.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!collection.contains(cm.this.column(next))) {
                        cm.this.b(next);
                        z = true;
                    }
                }
                return z;
            }
        }
    }
}
