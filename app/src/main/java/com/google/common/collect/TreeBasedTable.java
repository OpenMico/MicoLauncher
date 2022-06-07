package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public class TreeBasedTable<R, C, V> extends cl<R, C, V> {
    private static final long serialVersionUID = 0;
    private final Comparator<? super C> columnComparator;

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Set cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Map column(Object obj) {
        return super.column(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Set columnKeySet() {
        return super.columnKeySet();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Map columnMap() {
        return super.columnMap();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.contains(obj, obj2);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean containsColumn(@NullableDecl Object obj) {
        return super.containsColumn(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean containsRow(@NullableDecl Object obj) {
        return super.containsRow(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Object get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.get(obj, obj2);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2, Object obj3) {
        return super.put(obj, obj2, obj3);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ void putAll(Table table) {
        super.putAll(table);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.google.common.collect.o
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    /* loaded from: classes2.dex */
    private static class a<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        final Comparator<? super C> comparator;

        a(Comparator<? super C> comparator) {
            this.comparator = comparator;
        }

        /* renamed from: a */
        public TreeMap<C, V> get() {
            return new TreeMap<>(this.comparator);
        }
    }

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable<>(Ordering.natural(), Ordering.natural());
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable<>(comparator, comparator2);
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> treeBasedTable) {
        TreeBasedTable<R, C, V> treeBasedTable2 = new TreeBasedTable<>(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll(treeBasedTable);
        return treeBasedTable2;
    }

    TreeBasedTable(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        super(new TreeMap(comparator), new a(comparator2));
        this.columnComparator = comparator2;
    }

    @Deprecated
    public Comparator<? super R> rowComparator() {
        return rowKeySet().comparator();
    }

    @Deprecated
    public Comparator<? super C> columnComparator() {
        return this.columnComparator;
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public SortedMap<C, V> row(R r) {
        return new b(this, r);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class b extends cm<R, C, V>.f implements SortedMap<C, V> {
        @NullableDecl
        final C d;
        @NullableDecl
        final C e;
        @NullableDecl
        transient SortedMap<C, V> f;

        b(TreeBasedTable treeBasedTable, R r) {
            this(r, null, null);
        }

        b(R r, C c, @NullableDecl C c2) {
            super(r);
            this.d = c;
            this.e = c2;
            Preconditions.checkArgument(c == null || c2 == null || a(c, c2) <= 0);
        }

        /* renamed from: e */
        public SortedSet<C> keySet() {
            return new Maps.s(this);
        }

        @Override // java.util.SortedMap
        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }

        int a(Object obj, Object obj2) {
            return comparator().compare(obj, obj2);
        }

        boolean a(@NullableDecl Object obj) {
            C c;
            C c2;
            return obj != null && ((c = this.d) == null || a(c, obj) <= 0) && ((c2 = this.e) == null || a(c2, obj) > 0);
        }

        @Override // java.util.SortedMap
        public SortedMap<C, V> subMap(C c, C c2) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)) && a(Preconditions.checkNotNull(c2)));
            return new b(this.a, c, c2);
        }

        @Override // java.util.SortedMap
        public SortedMap<C, V> headMap(C c) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return new b(this.a, this.d, c);
        }

        @Override // java.util.SortedMap
        public SortedMap<C, V> tailMap(C c) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return new b(this.a, c, this.e);
        }

        @Override // java.util.SortedMap
        public C firstKey() {
            if (a() != null) {
                return a().firstKey();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.SortedMap
        public C lastKey() {
            if (a() != null) {
                return a().lastKey();
            }
            throw new NoSuchElementException();
        }

        SortedMap<C, V> f() {
            SortedMap<C, V> sortedMap = this.f;
            if (sortedMap == null || (sortedMap.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.a))) {
                this.f = (SortedMap) TreeBasedTable.this.backingMap.get(this.a);
            }
            return this.f;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: g */
        public SortedMap<C, V> a() {
            return (SortedMap) super.a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: h */
        public SortedMap<C, V> c() {
            SortedMap<C, V> f = f();
            if (f == null) {
                return null;
            }
            C c = this.d;
            if (c != null) {
                f = f.tailMap(c);
            }
            C c2 = this.e;
            return c2 != null ? f.headMap(c2) : f;
        }

        @Override // com.google.common.collect.cm.f
        void d() {
            if (f() != null && this.f.isEmpty()) {
                TreeBasedTable.this.backingMap.remove(this.a);
                this.f = null;
                this.b = null;
            }
        }

        @Override // com.google.common.collect.cm.f, java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return a(obj) && super.containsKey(obj);
        }

        @Override // com.google.common.collect.cm.f, java.util.AbstractMap, java.util.Map
        public V put(C c, V v) {
            Preconditions.checkArgument(a(Preconditions.checkNotNull(c)));
            return (V) super.put(c, v);
        }
    }

    @Override // com.google.common.collect.cl, com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    @Override // com.google.common.collect.cl, com.google.common.collect.cm, com.google.common.collect.Table
    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    @Override // com.google.common.collect.cm
    Iterator<C> g() {
        final Comparator<? super C> columnComparator = columnComparator();
        final UnmodifiableIterator mergeSorted = Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>() { // from class: com.google.common.collect.TreeBasedTable.1
            /* renamed from: a */
            public Iterator<C> apply(Map<C, V> map) {
                return map.keySet().iterator();
            }
        }), columnComparator);
        return new AbstractIterator<C>() { // from class: com.google.common.collect.TreeBasedTable.2
            @NullableDecl
            C a;

            @Override // com.google.common.collect.AbstractIterator
            protected C computeNext() {
                boolean z;
                while (mergeSorted.hasNext()) {
                    C c = (C) mergeSorted.next();
                    C c2 = this.a;
                    if (c2 == null || columnComparator.compare(c, c2) != 0) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (!z) {
                        this.a = c;
                        return this.a;
                    }
                }
                this.a = null;
                return endOfData();
            }
        };
    }
}
