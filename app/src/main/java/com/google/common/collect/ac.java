package com.google.common.collect;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.Immutable;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DenseImmutableTable.java */
@Immutable(containerOf = {"R", HomePageRecordHelper.AREA_C, ExifInterface.GPS_MEASUREMENT_INTERRUPTED})
@GwtCompatible
/* loaded from: classes2.dex */
public final class ac<R, C, V> extends bz<R, C, V> {
    private final int[] cellColumnIndices;
    private final int[] cellRowIndices;
    private final int[] columnCounts;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableMap<C, ImmutableMap<R, V>> columnMap;
    private final int[] rowCounts;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<R, ImmutableMap<C, V>> rowMap;
    private final V[][] values;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ac(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        this.values = (V[][]) ((Object[][]) Array.newInstance(Object.class, immutableSet.size(), immutableSet2.size()));
        this.rowKeyToIndex = Maps.a((Collection) immutableSet);
        this.columnKeyToIndex = Maps.a((Collection) immutableSet2);
        this.rowCounts = new int[this.rowKeyToIndex.size()];
        this.columnCounts = new int[this.columnKeyToIndex.size()];
        int[] iArr = new int[immutableList.size()];
        int[] iArr2 = new int[immutableList.size()];
        for (int i = 0; i < immutableList.size(); i++) {
            Table.Cell<R, C, V> cell = immutableList.get(i);
            R rowKey = cell.getRowKey();
            C columnKey = cell.getColumnKey();
            int intValue = this.rowKeyToIndex.get(rowKey).intValue();
            int intValue2 = this.columnKeyToIndex.get(columnKey).intValue();
            Preconditions.checkArgument(this.values[intValue][intValue2] == null, "duplicate key: (%s, %s)", rowKey, columnKey);
            this.values[intValue][intValue2] = cell.getValue();
            int[] iArr3 = this.rowCounts;
            iArr3[intValue] = iArr3[intValue] + 1;
            int[] iArr4 = this.columnCounts;
            iArr4[intValue2] = iArr4[intValue2] + 1;
            iArr[i] = intValue;
            iArr2[i] = intValue2;
        }
        this.cellRowIndices = iArr;
        this.cellColumnIndices = iArr2;
        this.rowMap = new e();
        this.columnMap = new b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DenseImmutableTable.java */
    /* loaded from: classes2.dex */
    public static abstract class c<K, V> extends ImmutableMap.a<K, V> {
        private final int size;

        @NullableDecl
        abstract V a(int i);

        abstract ImmutableMap<K, Integer> c_();

        c(int i) {
            this.size = i;
        }

        private boolean g() {
            return this.size == c_().size();
        }

        K c(int i) {
            return c_().keySet().asList().get(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap.a, com.google.common.collect.ImmutableMap
        public ImmutableSet<K> c() {
            return g() ? c_().keySet() : super.c();
        }

        @Override // java.util.Map
        public int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            Integer num = c_().get(obj);
            if (num == null) {
                return null;
            }
            return a(num.intValue());
        }

        @Override // com.google.common.collect.ImmutableMap.a
        UnmodifiableIterator<Map.Entry<K, V>> d_() {
            return new AbstractIterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.ac.c.1
                private int b = -1;
                private final int c;

                {
                    this.c = c.this.c_().size();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<K, V> computeNext() {
                    int i = this.b;
                    while (true) {
                        this.b = i + 1;
                        int i2 = this.b;
                        if (i2 >= this.c) {
                            return endOfData();
                        }
                        Object a = c.this.a(i2);
                        if (a != null) {
                            return Maps.immutableEntry(c.this.c(this.b), a);
                        }
                        i = this.b;
                    }
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DenseImmutableTable.java */
    /* loaded from: classes2.dex */
    public final class d extends c<C, V> {
        private final int rowIndex;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean b() {
            return true;
        }

        d(int i) {
            super(ac.this.rowCounts[i]);
            this.rowIndex = i;
        }

        @Override // com.google.common.collect.ac.c
        ImmutableMap<C, Integer> c_() {
            return ac.this.columnKeyToIndex;
        }

        @Override // com.google.common.collect.ac.c
        V a(int i) {
            return (V) ac.this.values[this.rowIndex][i];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DenseImmutableTable.java */
    /* loaded from: classes2.dex */
    public final class a extends c<R, V> {
        private final int columnIndex;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean b() {
            return true;
        }

        a(int i) {
            super(ac.this.columnCounts[i]);
            this.columnIndex = i;
        }

        @Override // com.google.common.collect.ac.c
        ImmutableMap<R, Integer> c_() {
            return ac.this.rowKeyToIndex;
        }

        @Override // com.google.common.collect.ac.c
        V a(int i) {
            return (V) ac.this.values[i][this.columnIndex];
        }
    }

    /* compiled from: DenseImmutableTable.java */
    /* loaded from: classes2.dex */
    private final class e extends c<R, ImmutableMap<C, V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean b() {
            return false;
        }

        private e() {
            super(ac.this.rowCounts.length);
        }

        @Override // com.google.common.collect.ac.c
        ImmutableMap<R, Integer> c_() {
            return ac.this.rowKeyToIndex;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public ImmutableMap<C, V> a(int i) {
            return new d(i);
        }
    }

    /* compiled from: DenseImmutableTable.java */
    /* loaded from: classes2.dex */
    private final class b extends c<C, ImmutableMap<R, V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean b() {
            return false;
        }

        private b() {
            super(ac.this.columnCounts.length);
        }

        @Override // com.google.common.collect.ac.c
        ImmutableMap<C, Integer> c_() {
            return ac.this.columnKeyToIndex;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public ImmutableMap<R, V> a(int i) {
            return new a(i);
        }
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.Table
    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.copyOf(this.columnMap);
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.Table
    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.copyOf(this.rowMap);
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.o, com.google.common.collect.Table
    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Integer num = this.rowKeyToIndex.get(obj);
        Integer num2 = this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return this.values[num.intValue()][num2.intValue()];
    }

    @Override // com.google.common.collect.Table
    public int size() {
        return this.cellRowIndices.length;
    }

    @Override // com.google.common.collect.bz
    Table.Cell<R, C, V> a(int i) {
        int i2 = this.cellRowIndices[i];
        int i3 = this.cellColumnIndices[i];
        return a(rowKeySet().asList().get(i2), columnKeySet().asList().get(i3), this.values[i2][i3]);
    }

    @Override // com.google.common.collect.bz
    V b(int i) {
        return this.values[this.cellRowIndices[i]][this.cellColumnIndices[i]];
    }

    @Override // com.google.common.collect.ImmutableTable
    ImmutableTable.a e() {
        return ImmutableTable.a.a(this, this.cellRowIndices, this.cellColumnIndices);
    }
}
