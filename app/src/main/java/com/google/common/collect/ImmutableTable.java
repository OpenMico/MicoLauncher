package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xiaomi.onetrack.api.b;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ImmutableTable<R, C, V> extends o<R, C, V> implements Serializable {
    @Override // com.google.common.collect.Table
    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    abstract a e();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: f */
    public abstract ImmutableSet<Table.Cell<R, C, V>> a();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: h */
    public abstract ImmutableCollection<V> c();

    @Override // com.google.common.collect.Table
    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean containsColumn(@NullableDecl Object obj) {
        return super.containsColumn(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean containsRow(@NullableDecl Object obj) {
        return super.containsRow(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Object get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.get(obj, obj2);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.o
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return (ImmutableTable<R, C, V>) ck.a;
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R r, C c, V v) {
        return new ce(r, c, v);
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable) table;
        }
        return a(table.cellSet());
    }

    private static <R, C, V> ImmutableTable<R, C, V> a(Iterable<? extends Table.Cell<? extends R, ? extends C, ? extends V>> iterable) {
        Builder builder = builder();
        for (Table.Cell<? extends R, ? extends C, ? extends V> cell : iterable) {
            builder.put(cell);
        }
        return builder.build();
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder<>();
    }

    static <R, C, V> Table.Cell<R, C, V> a(R r, C c, V v) {
        return Tables.immutableCell(Preconditions.checkNotNull(r, "rowKey"), Preconditions.checkNotNull(c, "columnKey"), Preconditions.checkNotNull(v, b.p));
    }

    /* loaded from: classes2.dex */
    public static final class Builder<R, C, V> {
        private final List<Table.Cell<R, C, V>> a = Lists.newArrayList();
        @MonotonicNonNullDecl
        private Comparator<? super R> b;
        @MonotonicNonNullDecl
        private Comparator<? super C> c;

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderRowsBy(Comparator<? super R> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator, "rowComparator");
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> comparator) {
            this.c = (Comparator) Preconditions.checkNotNull(comparator, "columnComparator");
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> put(R r, C c, V v) {
            this.a.add(ImmutableTable.a(r, c, v));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof Tables.b) {
                Preconditions.checkNotNull(cell.getRowKey(), "row");
                Preconditions.checkNotNull(cell.getColumnKey(), "column");
                Preconditions.checkNotNull(cell.getValue(), b.p);
                this.a.add(cell);
            } else {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
                put(cell);
            }
            return this;
        }

        public ImmutableTable<R, C, V> build() {
            switch (this.a.size()) {
                case 0:
                    return ImmutableTable.of();
                case 1:
                    return new ce((Table.Cell) Iterables.getOnlyElement(this.a));
                default:
                    return bz.a((List) this.a, (Comparator) this.b, (Comparator) this.c);
            }
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableSet<Table.Cell<R, C, V>> cellSet() {
        return (ImmutableSet) super.cellSet();
    }

    /* renamed from: g */
    public final UnmodifiableIterator<Table.Cell<R, C, V>> b() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    @Override // com.google.common.collect.o
    final Iterator<V> d() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.Table
    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c, "columnKey");
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) columnMap().get(c), ImmutableMap.of());
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableSet<C> columnKeySet() {
        return columnMap().keySet();
    }

    @Override // com.google.common.collect.Table
    public ImmutableMap<C, V> row(R r) {
        Preconditions.checkNotNull(r, "rowKey");
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) rowMap().get(r), ImmutableMap.of());
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableSet<R> rowKeySet() {
        return rowMap().keySet();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return get(obj, obj2) != null;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        return values().contains(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @Deprecated
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @Deprecated
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    /* loaded from: classes2.dex */
    static final class a implements Serializable {
        private static final long serialVersionUID = 0;
        private final int[] cellColumnIndices;
        private final int[] cellRowIndices;
        private final Object[] cellValues;
        private final Object[] columnKeys;
        private final Object[] rowKeys;

        private a(Object[] objArr, Object[] objArr2, Object[] objArr3, int[] iArr, int[] iArr2) {
            this.rowKeys = objArr;
            this.columnKeys = objArr2;
            this.cellValues = objArr3;
            this.cellRowIndices = iArr;
            this.cellColumnIndices = iArr2;
        }

        public static a a(ImmutableTable<?, ?, ?> immutableTable, int[] iArr, int[] iArr2) {
            return new a(immutableTable.rowKeySet().toArray(), immutableTable.columnKeySet().toArray(), immutableTable.values().toArray(), iArr, iArr2);
        }

        Object readResolve() {
            Object[] objArr = this.cellValues;
            if (objArr.length == 0) {
                return ImmutableTable.of();
            }
            int i = 0;
            if (objArr.length == 1) {
                return ImmutableTable.of(this.rowKeys[0], this.columnKeys[0], objArr[0]);
            }
            ImmutableList.Builder builder = new ImmutableList.Builder(objArr.length);
            while (true) {
                Object[] objArr2 = this.cellValues;
                if (i >= objArr2.length) {
                    return bz.a(builder.build(), ImmutableSet.copyOf(this.rowKeys), ImmutableSet.copyOf(this.columnKeys));
                }
                builder.add((ImmutableList.Builder) ImmutableTable.a(this.rowKeys[this.cellRowIndices[i]], this.columnKeys[this.cellColumnIndices[i]], objArr2[i]));
                i++;
            }
        }
    }

    final Object writeReplace() {
        return e();
    }
}
