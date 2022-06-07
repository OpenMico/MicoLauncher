package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractTable.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class o<R, C, V> implements Table<R, C, V> {
    @MonotonicNonNullDecl
    private transient Set<Table.Cell<R, C, V>> a;
    @MonotonicNonNullDecl
    private transient Collection<V> b;

    abstract Iterator<Table.Cell<R, C, V>> b();

    @Override // com.google.common.collect.Table
    public boolean containsRow(@NullableDecl Object obj) {
        return Maps.b((Map<?, ?>) rowMap(), obj);
    }

    @Override // com.google.common.collect.Table
    public boolean containsColumn(@NullableDecl Object obj) {
        return Maps.b((Map<?, ?>) columnMap(), obj);
    }

    @Override // com.google.common.collect.Table
    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    @Override // com.google.common.collect.Table
    public Set<C> columnKeySet() {
        return columnMap().keySet();
    }

    @Override // com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        for (Map<C, V> map : rowMap().values()) {
            if (map.containsValue(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Map map = (Map) Maps.a((Map<?, Object>) rowMap(), obj);
        return map != null && Maps.b(map, obj2);
    }

    @Override // com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Map map = (Map) Maps.a((Map<?, Object>) rowMap(), obj);
        if (map == null) {
            return null;
        }
        return (V) Maps.a(map, obj2);
    }

    @Override // com.google.common.collect.Table
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // com.google.common.collect.Table
    public void clear() {
        Iterators.b(cellSet().iterator());
    }

    @Override // com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Map map = (Map) Maps.a((Map<?, Object>) rowMap(), obj);
        if (map == null) {
            return null;
        }
        return (V) Maps.c(map, obj2);
    }

    @Override // com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V put(R r, C c, V v) {
        return row(r).put(c, v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.Table
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
            put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    @Override // com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> set = this.a;
        if (set != null) {
            return set;
        }
        Set<Table.Cell<R, C, V>> a2 = a();
        this.a = a2;
        return a2;
    }

    Set<Table.Cell<R, C, V>> a() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractTable.java */
    /* loaded from: classes2.dex */
    public class a extends AbstractSet<Table.Cell<R, C, V>> {
        a() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Map map = (Map) Maps.a((Map<?, Object>) o.this.rowMap(), cell.getRowKey());
            return map != null && Collections2.a(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@NullableDecl Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Map map = (Map) Maps.a((Map<?, Object>) o.this.rowMap(), cell.getRowKey());
            return map != null && Collections2.b(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            o.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Table.Cell<R, C, V>> iterator() {
            return o.this.b();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return o.this.size();
        }
    }

    @Override // com.google.common.collect.Table
    public Collection<V> values() {
        Collection<V> collection = this.b;
        if (collection != null) {
            return collection;
        }
        Collection<V> c = c();
        this.b = c;
        return c;
    }

    Collection<V> c() {
        return new b();
    }

    Iterator<V> d() {
        return new cp<Table.Cell<R, C, V>, V>(cellSet().iterator()) { // from class: com.google.common.collect.o.1
            @Override // com.google.common.collect.cp
            /* bridge */ /* synthetic */ Object a(Object obj) {
                return a((Table.Cell<R, C, Object>) obj);
            }

            V a(Table.Cell<R, C, V> cell) {
                return cell.getValue();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractTable.java */
    /* loaded from: classes2.dex */
    public class b extends AbstractCollection<V> {
        b() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return o.this.d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return o.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            o.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return o.this.size();
        }
    }

    @Override // com.google.common.collect.Table
    public boolean equals(@NullableDecl Object obj) {
        return Tables.a(this, obj);
    }

    @Override // com.google.common.collect.Table
    public int hashCode() {
        return cellSet().hashCode();
    }

    public String toString() {
        return rowMap().toString();
    }
}
