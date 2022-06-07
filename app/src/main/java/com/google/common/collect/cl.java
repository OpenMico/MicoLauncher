package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

/* compiled from: StandardRowSortedTable.java */
@GwtCompatible
/* loaded from: classes2.dex */
class cl<R, C, V> extends cm<R, C, V> implements RowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public cl(SortedMap<R, Map<C, V>> sortedMap, Supplier<? extends Map<C, V>> supplier) {
        super(sortedMap, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SortedMap<R, Map<C, V>> h() {
        return (SortedMap) this.backingMap;
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.o, com.google.common.collect.Table
    public SortedSet<R> rowKeySet() {
        return (SortedSet) rowMap().keySet();
    }

    @Override // com.google.common.collect.cm, com.google.common.collect.Table
    public SortedMap<R, Map<C, V>> rowMap() {
        return (SortedMap) super.rowMap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public SortedMap<R, Map<C, V>> f() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: StandardRowSortedTable.java */
    /* loaded from: classes2.dex */
    public class a extends cm<R, C, V>.g implements SortedMap<R, Map<C, V>> {
        private a() {
            super();
        }

        /* renamed from: c */
        public SortedSet<R> keySet() {
            return (SortedSet) super.keySet();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: d */
        public SortedSet<R> h() {
            return new Maps.s(this);
        }

        @Override // java.util.SortedMap
        public Comparator<? super R> comparator() {
            return cl.this.h().comparator();
        }

        @Override // java.util.SortedMap
        public R firstKey() {
            return (R) cl.this.h().firstKey();
        }

        @Override // java.util.SortedMap
        public R lastKey() {
            return (R) cl.this.h().lastKey();
        }

        @Override // java.util.SortedMap
        public SortedMap<R, Map<C, V>> headMap(R r) {
            Preconditions.checkNotNull(r);
            return new cl(cl.this.h().headMap(r), cl.this.factory).rowMap();
        }

        @Override // java.util.SortedMap
        public SortedMap<R, Map<C, V>> subMap(R r, R r2) {
            Preconditions.checkNotNull(r);
            Preconditions.checkNotNull(r2);
            return new cl(cl.this.h().subMap(r, r2), cl.this.factory).rowMap();
        }

        @Override // java.util.SortedMap
        public SortedMap<R, Map<C, V>> tailMap(R r) {
            Preconditions.checkNotNull(r);
            return new cl(cl.this.h().tailMap(r), cl.this.factory).rowMap();
        }
    }
}
