package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> a = new Function<Map<Object, Object>, Map<Object, Object>>() { // from class: com.google.common.collect.Tables.1
        /* renamed from: a */
        public Map<Object, Object> apply(Map<Object, Object> map) {
            return Collections.unmodifiableMap(map);
        }
    };

    private Tables() {
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@NullableDecl R r, @NullableDecl C c2, @NullableDecl V v) {
        return new b(r, c2, v);
    }

    /* loaded from: classes2.dex */
    public static final class b<R, C, V> extends a<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        private final C columnKey;
        @NullableDecl
        private final R rowKey;
        @NullableDecl
        private final V value;

        b(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            this.rowKey = r;
            this.columnKey = c;
            this.value = v;
        }

        @Override // com.google.common.collect.Table.Cell
        public R getRowKey() {
            return this.rowKey;
        }

        @Override // com.google.common.collect.Table.Cell
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override // com.google.common.collect.Table.Cell
        public V getValue() {
            return this.value;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class a<R, C, V> implements Table.Cell<R, C, V> {
        @Override // com.google.common.collect.Table.Cell
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            return Objects.equal(getRowKey(), cell.getRowKey()) && Objects.equal(getColumnKey(), cell.getColumnKey()) && Objects.equal(getValue(), cell.getValue());
        }

        @Override // com.google.common.collect.Table.Cell
        public int hashCode() {
            return Objects.hashCode(getRowKey(), getColumnKey(), getValue());
        }

        public String toString() {
            return "(" + getRowKey() + Constants.ACCEPT_TIME_SEPARATOR_SP + getColumnKey() + ")=" + getValue();
        }
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof d ? (Table<R, C, V>) ((d) table).a : new d(table);
    }

    /* loaded from: classes2.dex */
    public static class d<C, R, V> extends o<C, R, V> {
        private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> b = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>() { // from class: com.google.common.collect.Tables.d.1
            /* renamed from: a */
            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        };
        final Table<R, C, V> a;

        d(Table<R, C, V> table) {
            this.a = (Table) Preconditions.checkNotNull(table);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public void clear() {
            this.a.clear();
        }

        @Override // com.google.common.collect.Table
        public Map<C, V> column(R r) {
            return this.a.row(r);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public Set<R> columnKeySet() {
            return this.a.rowKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V>> columnMap() {
            return this.a.rowMap();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
            return this.a.contains(obj2, obj);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public boolean containsColumn(@NullableDecl Object obj) {
            return this.a.containsRow(obj);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public boolean containsRow(@NullableDecl Object obj) {
            return this.a.containsColumn(obj);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public boolean containsValue(@NullableDecl Object obj) {
            return this.a.containsValue(obj);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
            return this.a.get(obj2, obj);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V put(C c, R r, V v) {
            return this.a.put(r, c, v);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.a.putAll(Tables.transpose(table));
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
            return this.a.remove(obj2, obj);
        }

        @Override // com.google.common.collect.Table
        public Map<R, V> row(C c) {
            return this.a.column(c);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public Set<C> rowKeySet() {
            return this.a.columnKeySet();
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V>> rowMap() {
            return this.a.columnMap();
        }

        @Override // com.google.common.collect.Table
        public int size() {
            return this.a.size();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public Collection<V> values() {
            return this.a.values();
        }

        @Override // com.google.common.collect.o
        Iterator<Table.Cell<C, R, V>> b() {
            return Iterators.transform(this.a.cellSet().iterator(), b);
        }
    }

    @Beta
    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new cm(map, supplier);
    }

    @Beta
    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> table, Function<? super V1, V2> function) {
        return new c(table, function);
    }

    /* loaded from: classes2.dex */
    public static class c<R, C, V1, V2> extends o<R, C, V2> {
        final Table<R, C, V1> a;
        final Function<? super V1, V2> b;

        c(Table<R, C, V1> table, Function<? super V1, V2> function) {
            this.a = (Table) Preconditions.checkNotNull(table);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public boolean contains(Object obj, Object obj2) {
            return this.a.contains(obj, obj2);
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V2 get(Object obj, Object obj2) {
            if (contains(obj, obj2)) {
                return this.b.apply((V1) this.a.get(obj, obj2));
            }
            return null;
        }

        @Override // com.google.common.collect.Table
        public int size() {
            return this.a.size();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public void clear() {
            this.a.clear();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V2 put(R r, C c, V2 v2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public V2 remove(Object obj, Object obj2) {
            if (contains(obj, obj2)) {
                return this.b.apply((V1) this.a.remove(obj, obj2));
            }
            return null;
        }

        @Override // com.google.common.collect.Table
        public Map<C, V2> row(R r) {
            return Maps.transformValues(this.a.row(r), this.b);
        }

        @Override // com.google.common.collect.Table
        public Map<R, V2> column(C c) {
            return Maps.transformValues(this.a.column(c), this.b);
        }

        Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> e() {
            return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() { // from class: com.google.common.collect.Tables.c.1
                /* renamed from: a */
                public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), c.this.b.apply(cell.getValue()));
                }
            };
        }

        @Override // com.google.common.collect.o
        Iterator<Table.Cell<R, C, V2>> b() {
            return Iterators.transform(this.a.cellSet().iterator(), e());
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public Set<R> rowKeySet() {
            return this.a.rowKeySet();
        }

        @Override // com.google.common.collect.o, com.google.common.collect.Table
        public Set<C> columnKeySet() {
            return this.a.columnKeySet();
        }

        @Override // com.google.common.collect.o
        Collection<V2> c() {
            return Collections2.transform(this.a.values(), this.b);
        }

        @Override // com.google.common.collect.Table
        public Map<R, Map<C, V2>> rowMap() {
            return Maps.transformValues(this.a.rowMap(), new Function<Map<C, V1>, Map<C, V2>>() { // from class: com.google.common.collect.Tables.c.2
                /* renamed from: a */
                public Map<C, V2> apply(Map<C, V1> map) {
                    return Maps.transformValues(map, c.this.b);
                }
            });
        }

        @Override // com.google.common.collect.Table
        public Map<C, Map<R, V2>> columnMap() {
            return Maps.transformValues(this.a.columnMap(), new Function<Map<R, V1>, Map<R, V2>>() { // from class: com.google.common.collect.Tables.c.3
                /* renamed from: a */
                public Map<R, V2> apply(Map<R, V1> map) {
                    return Maps.transformValues(map, c.this.b);
                }
            });
        }
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new f(table);
    }

    /* loaded from: classes2.dex */
    public static class f<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Table<? extends R, ? extends C, ? extends V> delegate;

        f(Table<? extends R, ? extends C, ? extends V> table) {
            this.delegate = (Table) Preconditions.checkNotNull(table);
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.ForwardingObject
        public Table<R, C, V> delegate() {
            return (Table<? extends R, ? extends C, ? extends V>) this.delegate;
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<R, V> column(@NullableDecl C c) {
            return Collections.unmodifiableMap(super.column(c));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<C, Map<R, V>> columnMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), Tables.b()));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public V put(@NullableDecl R r, @NullableDecl C c, @NullableDecl V v) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<C, V> row(@NullableDecl R r) {
            return Collections.unmodifiableMap(super.row(r));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Map<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), Tables.b()));
        }

        @Override // com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }
    }

    @Beta
    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
        return new e(rowSortedTable);
    }

    /* loaded from: classes2.dex */
    static final class e<R, C, V> extends f<R, C, V> implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0;

        public e(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
            super(rowSortedTable);
        }

        /* renamed from: a */
        public RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable) super.delegate();
        }

        @Override // com.google.common.collect.Tables.f, com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public SortedMap<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableSortedMap(Maps.transformValues((SortedMap) delegate().rowMap(), Tables.b()));
        }

        @Override // com.google.common.collect.Tables.f, com.google.common.collect.ForwardingTable, com.google.common.collect.Table
        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(delegate().rowKeySet());
        }
    }

    public static <K, V> Function<Map<K, V>, Map<K, V>> b() {
        return (Function<Map<K, V>, Map<K, V>>) a;
    }

    public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> table) {
        return cn.a(table, (Object) null);
    }

    public static boolean a(Table<?, ?, ?> table, @NullableDecl Object obj) {
        if (obj == table) {
            return true;
        }
        if (obj instanceof Table) {
            return table.cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }
}
