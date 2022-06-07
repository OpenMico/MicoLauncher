package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public class HashBasedTable<R, C, V> extends cm<R, C, V> {
    private static final long serialVersionUID = 0;

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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Map row(Object obj) {
        return super.row(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Set rowKeySet() {
        return super.rowKeySet();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public /* bridge */ /* synthetic */ Map rowMap() {
        return super.rowMap();
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a<C, V> implements Supplier<Map<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        final int expectedSize;

        a(int i) {
            this.expectedSize = i;
        }

        /* renamed from: a */
        public Map<C, V> get() {
            return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
        }
    }

    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable<>(new LinkedHashMap(), new a(0));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(int i, int i2) {
        t.a(i2, "expectedCellsPerRow");
        return new HashBasedTable<>(Maps.newLinkedHashMapWithExpectedSize(i), new a(i2));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
        HashBasedTable<R, C, V> create = create();
        create.putAll(table);
        return create;
    }

    HashBasedTable(Map<R, Map<C, V>> map, a<C, V> aVar) {
        super(map, aVar);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.contains(obj, obj2);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsColumn(@NullableDecl Object obj) {
        return super.containsColumn(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsRow(@NullableDecl Object obj) {
        return super.containsRow(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return (V) super.get(obj, obj2);
    }

    @Override // com.google.common.collect.o, com.google.common.collect.Table
    public boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return (V) super.remove(obj, obj2);
    }
}
