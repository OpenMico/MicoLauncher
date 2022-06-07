package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class ArrayTable<R, C, V> extends o<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V>.c a;
    private final V[][] array;
    @MonotonicNonNullDecl
    private transient ArrayTable<R, C, V>.e b;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableList<C> columnList;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableList<R> rowList;

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.o
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<>(iterable, iterable2);
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        return table instanceof ArrayTable ? new ArrayTable<>((ArrayTable) table) : new ArrayTable<>(table);
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        this.rowList = ImmutableList.copyOf(iterable);
        this.columnList = ImmutableList.copyOf(iterable2);
        Preconditions.checkArgument(this.rowList.isEmpty() == this.columnList.isEmpty());
        this.rowKeyToIndex = Maps.a(this.rowList);
        this.columnKeyToIndex = Maps.a(this.columnList);
        this.array = (V[][]) ((Object[][]) Array.newInstance(Object.class, this.rowList.size(), this.columnList.size()));
        eraseAll();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ArrayTable(Table<R, C, V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        this.rowList = arrayTable.rowList;
        this.columnList = arrayTable.columnList;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance(Object.class, this.rowList.size(), this.columnList.size()));
        this.array = vArr;
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr2 = arrayTable.array;
            System.arraycopy(vArr2[i], 0, vArr[i], 0, vArr2[i].length);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class a<K, V> extends Maps.m<K, V> {
        private final ImmutableMap<K, Integer> a;

        @NullableDecl
        abstract V a(int i, V v);

        abstract String a();

        @NullableDecl
        abstract V b(int i);

        private a(ImmutableMap<K, Integer> immutableMap) {
            this.a = immutableMap;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.a.keySet();
        }

        K a(int i) {
            return this.a.keySet().asList().get(i);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.a.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        Map.Entry<K, V> c(final int i) {
            Preconditions.checkElementIndex(i, size());
            return new f<K, V>() { // from class: com.google.common.collect.ArrayTable.a.1
                @Override // com.google.common.collect.f, java.util.Map.Entry
                public K getKey() {
                    return (K) a.this.a(i);
                }

                @Override // com.google.common.collect.f, java.util.Map.Entry
                public V getValue() {
                    return (V) a.this.b(i);
                }

                @Override // com.google.common.collect.f, java.util.Map.Entry
                public V setValue(V v) {
                    return (V) a.this.a(i, v);
                }
            };
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<K, V>> b() {
            return new b<Map.Entry<K, V>>(size()) { // from class: com.google.common.collect.ArrayTable.a.2
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: b */
                public Map.Entry<K, V> a(int i) {
                    return a.this.c(i);
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            Integer num = this.a.get(obj);
            if (num == null) {
                return null;
            }
            return b(num.intValue());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            Integer num = this.a.get(k);
            if (num != null) {
                return a(num.intValue(), v);
            }
            throw new IllegalArgumentException(a() + StringUtils.SPACE + k + " not in " + this.a.keySet());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    public V at(int i, int i2) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        return this.array[i][i2];
    }

    @CanIgnoreReturnValue
    public V set(int i, int i2, @NullableDecl V v) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        V[][] vArr = this.array;
        V v2 = vArr[i][i2];
        vArr[i][i2] = v;
        return v2;
    }

    @GwtIncompatible
    public V[][] toArray(Class<V> cls) {
        V[][] vArr = (V[][]) ((Object[][]) Array.newInstance((Class<?>) cls, this.rowList.size(), this.columnList.size()));
        for (int i = 0; i < this.rowList.size(); i++) {
            V[][] vArr2 = this.array;
            System.arraycopy(vArr2[i], 0, vArr[i], 0, vArr2[i].length);
        }
        return vArr;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void eraseAll() {
        for (V[] vArr : this.array) {
            Arrays.fill(vArr, (Object) null);
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsColumn(@NullableDecl Object obj) {
        return this.columnKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsRow(@NullableDecl Object obj) {
        return this.rowKeyToIndex.containsKey(obj);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        V[][] vArr = this.array;
        for (V[] vArr2 : vArr) {
            for (V v : vArr2) {
                if (Objects.equal(obj, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return at(num.intValue(), num2.intValue());
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean isEmpty() {
        return this.rowList.isEmpty() || this.columnList.isEmpty();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V put(R r, C c2, @NullableDecl V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c2);
        Integer num = this.rowKeyToIndex.get(r);
        boolean z = true;
        Preconditions.checkArgument(num != null, "Row %s not in %s", r, this.rowList);
        Integer num2 = this.columnKeyToIndex.get(c2);
        if (num2 == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "Column %s not in %s", c2, this.columnList);
        return set(num.intValue(), num2.intValue(), v);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    @Deprecated
    public V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public V erase(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), null);
    }

    @Override // com.google.common.collect.Table
    public int size() {
        return this.rowList.size() * this.columnList.size();
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Override // com.google.common.collect.o
    Iterator<Table.Cell<R, C, V>> b() {
        return new b<Table.Cell<R, C, V>>(size()) { // from class: com.google.common.collect.ArrayTable.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: b */
            public Table.Cell<R, C, V> a(int i) {
                return ArrayTable.this.a(i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Table.Cell<R, C, V> a(final int i) {
        return new Tables.a<R, C, V>() { // from class: com.google.common.collect.ArrayTable.2
            final int a;
            final int b;

            {
                this.a = i / ArrayTable.this.columnList.size();
                this.b = i % ArrayTable.this.columnList.size();
            }

            @Override // com.google.common.collect.Table.Cell
            public R getRowKey() {
                return (R) ArrayTable.this.rowList.get(this.a);
            }

            @Override // com.google.common.collect.Table.Cell
            public C getColumnKey() {
                return (C) ArrayTable.this.columnList.get(this.b);
            }

            @Override // com.google.common.collect.Table.Cell
            public V getValue() {
                return (V) ArrayTable.this.at(this.a, this.b);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public V b(int i) {
        return at(i / this.columnList.size(), i % this.columnList.size());
    }

    @Override // com.google.common.collect.Table
    public Map<R, V> column(C c2) {
        Preconditions.checkNotNull(c2);
        Integer num = this.columnKeyToIndex.get(c2);
        return num == null ? ImmutableMap.of() : new b(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class b extends a<R, V> {
        final int a;

        @Override // com.google.common.collect.ArrayTable.a
        String a() {
            return "Row";
        }

        b(int i) {
            super(ArrayTable.this.rowKeyToIndex);
            this.a = i;
        }

        @Override // com.google.common.collect.ArrayTable.a
        V b(int i) {
            return (V) ArrayTable.this.at(i, this.a);
        }

        @Override // com.google.common.collect.ArrayTable.a
        V a(int i, V v) {
            return (V) ArrayTable.this.set(i, this.a, v);
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    @Override // com.google.common.collect.Table
    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V>.c cVar = this.a;
        if (cVar != null) {
            return cVar;
        }
        ArrayTable<R, C, V>.c cVar2 = new c();
        this.a = cVar2;
        return cVar2;
    }

    /* loaded from: classes2.dex */
    private class c extends a<C, Map<R, V>> {
        @Override // com.google.common.collect.ArrayTable.a
        String a() {
            return "Column";
        }

        @Override // com.google.common.collect.ArrayTable.a
        /* bridge */ /* synthetic */ Object a(int i, Object obj) {
            return a(i, (Map) ((Map) obj));
        }

        @Override // com.google.common.collect.ArrayTable.a, java.util.AbstractMap, java.util.Map
        public /* synthetic */ Object put(Object obj, Object obj2) {
            return a((c) obj, (Map) ((Map) obj2));
        }

        private c() {
            super(ArrayTable.this.columnKeyToIndex);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public Map<R, V> b(int i) {
            return new b(i);
        }

        Map<R, V> a(int i, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> a(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.google.common.collect.Table
    public Map<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        Integer num = this.rowKeyToIndex.get(r);
        return num == null ? ImmutableMap.of() : new d(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class d extends a<C, V> {
        final int a;

        @Override // com.google.common.collect.ArrayTable.a
        String a() {
            return "Column";
        }

        d(int i) {
            super(ArrayTable.this.columnKeyToIndex);
            this.a = i;
        }

        @Override // com.google.common.collect.ArrayTable.a
        V b(int i) {
            return (V) ArrayTable.this.at(this.a, i);
        }

        @Override // com.google.common.collect.ArrayTable.a
        V a(int i, V v) {
            return (V) ArrayTable.this.set(this.a, i, v);
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    @Override // com.google.common.collect.Table
    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V>.e eVar = this.b;
        if (eVar != null) {
            return eVar;
        }
        ArrayTable<R, C, V>.e eVar2 = new e();
        this.b = eVar2;
        return eVar2;
    }

    /* loaded from: classes2.dex */
    private class e extends a<R, Map<C, V>> {
        @Override // com.google.common.collect.ArrayTable.a
        String a() {
            return "Row";
        }

        @Override // com.google.common.collect.ArrayTable.a
        /* bridge */ /* synthetic */ Object a(int i, Object obj) {
            return a(i, (Map) ((Map) obj));
        }

        @Override // com.google.common.collect.ArrayTable.a, java.util.AbstractMap, java.util.Map
        public /* synthetic */ Object put(Object obj, Object obj2) {
            return a((e) obj, (Map) ((Map) obj2));
        }

        private e() {
            super(ArrayTable.this.rowKeyToIndex);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public Map<C, V> b(int i) {
            return new d(i);
        }

        Map<C, V> a(int i, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> a(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.google.common.collect.o
    Iterator<V> d() {
        return new b<V>(size()) { // from class: com.google.common.collect.ArrayTable.3
            @Override // com.google.common.collect.b
            protected V a(int i) {
                return (V) ArrayTable.this.b(i);
            }
        };
    }
}
