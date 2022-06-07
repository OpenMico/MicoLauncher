package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: RegularImmutableTable.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class bz<R, C, V> extends ImmutableTable<R, C, V> {
    abstract Table.Cell<R, C, V> a(int i);

    abstract V b(int i);

    @Override // com.google.common.collect.ImmutableTable
    /* renamed from: f */
    public final ImmutableSet<Table.Cell<R, C, V>> a() {
        return isEmpty() ? ImmutableSet.of() : new a();
    }

    /* compiled from: RegularImmutableTable.java */
    /* loaded from: classes2.dex */
    public final class a extends bg<Table.Cell<R, C, V>> {
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return false;
        }

        private a() {
            bz.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return bz.this.size();
        }

        /* renamed from: b */
        public Table.Cell<R, C, V> a(int i) {
            return bz.this.a(i);
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Object obj2 = bz.this.get(cell.getRowKey(), cell.getColumnKey());
            return obj2 != null && obj2.equals(cell.getValue());
        }
    }

    @Override // com.google.common.collect.ImmutableTable
    /* renamed from: h */
    public final ImmutableCollection<V> c() {
        return isEmpty() ? ImmutableList.of() : new b();
    }

    /* compiled from: RegularImmutableTable.java */
    /* loaded from: classes2.dex */
    public final class b extends ImmutableList<V> {
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        private b() {
            bz.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return bz.this.size();
        }

        @Override // java.util.List
        public V get(int i) {
            return (V) bz.this.b(i);
        }
    }

    public static <R, C, V> bz<R, C, V> a(List<Table.Cell<R, C, V>> list, @NullableDecl final Comparator<? super R> comparator, @NullableDecl final Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(list);
        if (!(comparator == null && comparator2 == null)) {
            Collections.sort(list, new Comparator<Table.Cell<R, C, V>>() { // from class: com.google.common.collect.bz.1
                /* renamed from: a */
                public int compare(Table.Cell<R, C, V> cell, Table.Cell<R, C, V> cell2) {
                    Comparator comparator3 = comparator;
                    int compare = comparator3 == null ? 0 : comparator3.compare(cell.getRowKey(), cell2.getRowKey());
                    if (compare != 0) {
                        return compare;
                    }
                    Comparator comparator4 = comparator2;
                    if (comparator4 == null) {
                        return 0;
                    }
                    return comparator4.compare(cell.getColumnKey(), cell2.getColumnKey());
                }
            });
        }
        return a((Iterable) list, (Comparator) comparator, (Comparator) comparator2);
    }

    private static <R, C, V> bz<R, C, V> a(Iterable<Table.Cell<R, C, V>> iterable, @NullableDecl Comparator<? super R> comparator, @NullableDecl Comparator<? super C> comparator2) {
        ImmutableSet immutableSet;
        ImmutableSet immutableSet2;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ImmutableList copyOf = ImmutableList.copyOf(iterable);
        for (Table.Cell<R, C, V> cell : iterable) {
            linkedHashSet.add(cell.getRowKey());
            linkedHashSet2.add(cell.getColumnKey());
        }
        if (comparator == null) {
            immutableSet = ImmutableSet.copyOf((Collection) linkedHashSet);
        } else {
            immutableSet = ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(comparator, linkedHashSet));
        }
        if (comparator2 == null) {
            immutableSet2 = ImmutableSet.copyOf((Collection) linkedHashSet2);
        } else {
            immutableSet2 = ImmutableSet.copyOf((Collection) ImmutableList.sortedCopyOf(comparator2, linkedHashSet2));
        }
        return a(copyOf, immutableSet, immutableSet2);
    }

    public static <R, C, V> bz<R, C, V> a(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        return ((long) immutableList.size()) > (((long) immutableSet.size()) * ((long) immutableSet2.size())) / 2 ? new ac(immutableList, immutableSet, immutableSet2) : new ck(immutableList, immutableSet, immutableSet2);
    }
}
