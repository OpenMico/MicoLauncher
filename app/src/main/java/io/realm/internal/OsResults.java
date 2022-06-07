package io.realm.internal;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.internal.ObservableCollection;
import io.realm.internal.core.DescriptorOrdering;
import io.realm.internal.core.QueryDescriptor;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class OsResults implements NativeObject, ObservableCollection {
    public static final byte AGGREGATE_FUNCTION_AVERAGE = 3;
    public static final byte AGGREGATE_FUNCTION_MAXIMUM = 2;
    public static final byte AGGREGATE_FUNCTION_MINIMUM = 1;
    public static final byte AGGREGATE_FUNCTION_SUM = 4;
    public static final byte MODE_EMPTY = 0;
    public static final byte MODE_LINK_LIST = 4;
    public static final byte MODE_LIST = 2;
    public static final byte MODE_QUERY = 3;
    public static final byte MODE_TABLE = 1;
    public static final byte MODE_TABLEVIEW = 5;
    private static final long b = nativeGetFinalizerPtr();
    private final long a;
    private final OsSharedRealm c;
    private final NativeContext d;
    private final Table e;
    protected boolean loaded;
    private boolean f = false;
    protected final ObserverPairList<ObservableCollection.CollectionObserverPair> observerPairs = new ObserverPairList<>();

    /* loaded from: classes5.dex */
    public interface a<T> {
        void a(OsObjectBuilder osObjectBuilder, RealmList<T> realmList);
    }

    private static native Object nativeAggregate(long j, long j2, byte b2);

    private static native void nativeClear(long j);

    private static native boolean nativeContains(long j, long j2);

    protected static native long nativeCreateResults(long j, long j2, long j3);

    private static native long nativeCreateResultsFromBacklinks(long j, long j2, long j3, long j4);

    private static native long nativeCreateSnapshot(long j);

    private static native void nativeDelete(long j, long j2);

    private static native boolean nativeDeleteFirst(long j);

    private static native boolean nativeDeleteLast(long j);

    private static native long nativeDistinct(long j, QueryDescriptor queryDescriptor);

    private static native void nativeEvaluateQueryIfNeeded(long j, boolean z);

    private static native long nativeFirstRow(long j);

    private static native long nativeFreeze(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private static native byte nativeGetMode(long j);

    private static native long nativeGetRow(long j, int i);

    private static native long nativeIndexOf(long j, long j2);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLastRow(long j);

    private static native void nativeSetBinary(long j, String str, @Nullable byte[] bArr);

    private static native void nativeSetBoolean(long j, String str, boolean z);

    private static native void nativeSetDouble(long j, String str, double d);

    private static native void nativeSetFloat(long j, String str, float f);

    private static native void nativeSetInt(long j, String str, long j2);

    private static native void nativeSetList(long j, String str, long j2);

    private static native void nativeSetNull(long j, String str);

    private static native void nativeSetObject(long j, String str, long j2);

    private static native void nativeSetString(long j, String str, @Nullable String str2);

    private static native void nativeSetTimestamp(long j, String str, long j2);

    private static native long nativeSize(long j);

    private static native long nativeSort(long j, QueryDescriptor queryDescriptor);

    private native void nativeStartListening(long j);

    private native void nativeStopListening(long j);

    private static native long nativeWhere(long j);

    private static native String toJSON(long j, int i);

    /* loaded from: classes5.dex */
    public static abstract class Iterator<T> implements java.util.Iterator<T> {
        OsResults b;
        protected int pos = -1;

        protected abstract T convertRowToObject(UncheckedRow uncheckedRow);

        public Iterator(OsResults osResults) {
            if (!osResults.c.isClosed()) {
                this.b = osResults;
                if (!osResults.f) {
                    if (osResults.c.isInTransaction()) {
                        a();
                    } else {
                        this.b.c.addIterator(this);
                    }
                }
            } else {
                throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            c();
            return ((long) (this.pos + 1)) < this.b.size();
        }

        @Override // java.util.Iterator
        @Nullable
        public T next() {
            c();
            this.pos++;
            if (this.pos < this.b.size()) {
                return a(this.pos);
            }
            throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + this.b.size() + ". Remember to check hasNext() before using next().");
        }

        @Override // java.util.Iterator
        @Deprecated
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by RealmResults iterators.");
        }

        public void a() {
            this.b = this.b.createSnapshot();
        }

        public void b() {
            this.b = null;
        }

        void c() {
            if (this.b == null) {
                throw new ConcurrentModificationException("No outside changes to a Realm is allowed while iterating a living Realm collection.");
            }
        }

        @Nullable
        T a(int i) {
            return convertRowToObject(this.b.getUncheckedRow(i));
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class ListIterator<T> extends Iterator<T> implements java.util.ListIterator<T> {
        public ListIterator(OsResults osResults, int i) {
            super(osResults);
            if (i < 0 || i > this.b.size()) {
                throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (this.b.size() - 1) + "]. Yours was " + i);
            }
            this.pos = i - 1;
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void add(@Nullable T t) {
            throw new UnsupportedOperationException("Adding an element is not supported. Use Realm.createObject() instead.");
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            c();
            return this.pos >= 0;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            c();
            return this.pos + 1;
        }

        @Override // java.util.ListIterator
        @Nullable
        public T previous() {
            c();
            try {
                this.pos--;
                return a(this.pos);
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException("Cannot access index less than zero. This was " + this.pos + ". Remember to check hasPrevious() before using previous().");
            }
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            c();
            return this.pos;
        }

        @Override // java.util.ListIterator
        @Deprecated
        public void set(@Nullable T t) {
            throw new UnsupportedOperationException("Replacing an element is not supported.");
        }
    }

    /* loaded from: classes5.dex */
    public enum Aggregate {
        MINIMUM((byte) 1),
        MAXIMUM((byte) 2),
        AVERAGE((byte) 3),
        SUM((byte) 4);
        
        private final byte value;

        Aggregate(byte b) {
            this.value = b;
        }

        public byte getValue() {
            return this.value;
        }
    }

    /* loaded from: classes5.dex */
    public enum Mode {
        EMPTY,
        TABLE,
        PRIMITIVE_LIST,
        QUERY,
        LINK_LIST,
        TABLEVIEW;

        static Mode a(byte b) {
            switch (b) {
                case 0:
                    return EMPTY;
                case 1:
                    return TABLE;
                case 2:
                    return PRIMITIVE_LIST;
                case 3:
                    return QUERY;
                case 4:
                    return LINK_LIST;
                case 5:
                    return TABLEVIEW;
                default:
                    throw new IllegalArgumentException("Invalid value: " + ((int) b));
            }
        }
    }

    public static OsResults createForBacklinks(OsSharedRealm osSharedRealm, UncheckedRow uncheckedRow, Table table, String str) {
        return new OsResults(osSharedRealm, table, nativeCreateResultsFromBacklinks(osSharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), table.getNativePtr(), table.getColumnKey(str)));
    }

    public static OsResults createFromQuery(OsSharedRealm osSharedRealm, TableQuery tableQuery, DescriptorOrdering descriptorOrdering) {
        tableQuery.a();
        return new OsResults(osSharedRealm, tableQuery.getTable(), nativeCreateResults(osSharedRealm.getNativePtr(), tableQuery.getNativePtr(), descriptorOrdering.getNativePtr()));
    }

    public static OsResults createFromQuery(OsSharedRealm osSharedRealm, TableQuery tableQuery) {
        return createFromQuery(osSharedRealm, tableQuery, new DescriptorOrdering());
    }

    public OsResults(OsSharedRealm osSharedRealm, Table table, long j) {
        boolean z = false;
        this.c = osSharedRealm;
        this.d = osSharedRealm.context;
        this.e = table;
        this.a = j;
        this.d.addReference(this);
        this.loaded = getMode() != Mode.QUERY ? true : z;
    }

    public OsResults createSnapshot() {
        if (this.f) {
            return this;
        }
        OsResults osResults = new OsResults(this.c, this.e, nativeCreateSnapshot(this.a));
        osResults.f = true;
        return osResults;
    }

    public OsResults freeze(OsSharedRealm osSharedRealm) {
        OsResults osResults = new OsResults(osSharedRealm, this.e.freeze(osSharedRealm), nativeFreeze(this.a, osSharedRealm.getNativePtr()));
        if (isLoaded()) {
            osResults.load();
        }
        return osResults;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.a;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return b;
    }

    public UncheckedRow getUncheckedRow(int i) {
        return this.e.getUncheckedRowByPointer(nativeGetRow(this.a, i));
    }

    public UncheckedRow firstUncheckedRow() {
        long nativeFirstRow = nativeFirstRow(this.a);
        if (nativeFirstRow != 0) {
            return this.e.getUncheckedRowByPointer(nativeFirstRow);
        }
        return null;
    }

    public UncheckedRow lastUncheckedRow() {
        long nativeLastRow = nativeLastRow(this.a);
        if (nativeLastRow != 0) {
            return this.e.getUncheckedRowByPointer(nativeLastRow);
        }
        return null;
    }

    public Table getTable() {
        return this.e;
    }

    public TableQuery where() {
        return new TableQuery(this.d, this.e, nativeWhere(this.a));
    }

    public String toJSON(int i) {
        return toJSON(this.a, i);
    }

    public Number aggregateNumber(Aggregate aggregate, long j) {
        return (Number) nativeAggregate(this.a, j, aggregate.getValue());
    }

    public Date aggregateDate(Aggregate aggregate, long j) {
        return (Date) nativeAggregate(this.a, j, aggregate.getValue());
    }

    public long size() {
        return nativeSize(this.a);
    }

    public void clear() {
        nativeClear(this.a);
    }

    public OsResults sort(QueryDescriptor queryDescriptor) {
        return new OsResults(this.c, this.e, nativeSort(this.a, queryDescriptor));
    }

    public OsResults distinct(QueryDescriptor queryDescriptor) {
        return new OsResults(this.c, this.e, nativeDistinct(this.a, queryDescriptor));
    }

    public boolean contains(UncheckedRow uncheckedRow) {
        return nativeContains(this.a, uncheckedRow.getNativePtr());
    }

    public int indexOf(UncheckedRow uncheckedRow) {
        long nativeIndexOf = nativeIndexOf(this.a, uncheckedRow.getNativePtr());
        if (nativeIndexOf > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) nativeIndexOf;
    }

    public void delete(long j) {
        nativeDelete(this.a, j);
    }

    public boolean deleteFirst() {
        return nativeDeleteFirst(this.a);
    }

    public boolean deleteLast() {
        return nativeDeleteLast(this.a);
    }

    public void setNull(String str) {
        nativeSetNull(this.a, str);
    }

    public void setBoolean(String str, boolean z) {
        nativeSetBoolean(this.a, str, z);
    }

    public void setInt(String str, long j) {
        nativeSetInt(this.a, str, j);
    }

    public void setFloat(String str, float f) {
        nativeSetFloat(this.a, str, f);
    }

    public void setDouble(String str, double d) {
        nativeSetDouble(this.a, str, d);
    }

    public void setString(String str, @Nullable String str2) {
        nativeSetString(this.a, str, str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        nativeSetBinary(this.a, str, bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        if (date == null) {
            nativeSetNull(this.a, str);
        } else {
            nativeSetTimestamp(this.a, str, date.getTime());
        }
    }

    public void setObject(String str, @Nullable Row row) {
        long j;
        if (row == null) {
            setNull(str);
            return;
        }
        if (row instanceof UncheckedRow) {
            j = ((UncheckedRow) row).getNativePtr();
        } else if (row instanceof CheckedRow) {
            j = ((CheckedRow) row).getNativePtr();
        } else {
            throw new UnsupportedOperationException("Unsupported Row type: " + row.getClass().getCanonicalName());
        }
        nativeSetObject(this.a, str, j);
    }

    private <T> void a(String str, RealmList<T> realmList, a<T> aVar) {
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(getTable(), Collections.EMPTY_SET);
        aVar.a(osObjectBuilder, realmList);
        try {
            nativeSetList(this.a, str, osObjectBuilder.getNativePtr());
        } finally {
            osObjectBuilder.close();
        }
    }

    public void setStringList(String str, RealmList<String> realmList) {
        a(str, realmList, new a<String>() { // from class: io.realm.internal.OsResults.1
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<String> realmList2) {
                osObjectBuilder.addStringList(0L, realmList2);
            }
        });
    }

    public void setByteList(String str, RealmList<Byte> realmList) {
        a(str, realmList, new a<Byte>() { // from class: io.realm.internal.OsResults.4
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Byte> realmList2) {
                osObjectBuilder.addByteList(0L, realmList2);
            }
        });
    }

    public void setShortList(String str, RealmList<Short> realmList) {
        a(str, realmList, new a<Short>() { // from class: io.realm.internal.OsResults.5
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Short> realmList2) {
                osObjectBuilder.addShortList(0L, realmList2);
            }
        });
    }

    public void setIntegerList(String str, RealmList<Integer> realmList) {
        a(str, realmList, new a<Integer>() { // from class: io.realm.internal.OsResults.6
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Integer> realmList2) {
                osObjectBuilder.addIntegerList(0L, realmList2);
            }
        });
    }

    public void setLongList(String str, RealmList<Long> realmList) {
        a(str, realmList, new a<Long>() { // from class: io.realm.internal.OsResults.7
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Long> realmList2) {
                osObjectBuilder.addLongList(0L, realmList2);
            }
        });
    }

    public void setBooleanList(String str, RealmList<Boolean> realmList) {
        a(str, realmList, new a<Boolean>() { // from class: io.realm.internal.OsResults.8
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Boolean> realmList2) {
                osObjectBuilder.addBooleanList(0L, realmList2);
            }
        });
    }

    public void setByteArrayList(String str, RealmList<byte[]> realmList) {
        a(str, realmList, new a<byte[]>() { // from class: io.realm.internal.OsResults.9
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<byte[]> realmList2) {
                osObjectBuilder.addByteArrayList(0L, realmList2);
            }
        });
    }

    public void setDateList(String str, RealmList<Date> realmList) {
        a(str, realmList, new a<Date>() { // from class: io.realm.internal.OsResults.10
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Date> realmList2) {
                osObjectBuilder.addDateList(0L, realmList2);
            }
        });
    }

    public void setFloatList(String str, RealmList<Float> realmList) {
        a(str, realmList, new a<Float>() { // from class: io.realm.internal.OsResults.11
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Float> realmList2) {
                osObjectBuilder.addFloatList(0L, realmList2);
            }
        });
    }

    public void setDoubleList(String str, RealmList<Double> realmList) {
        a(str, realmList, new a<Double>() { // from class: io.realm.internal.OsResults.2
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<Double> realmList2) {
                osObjectBuilder.addDoubleList(0L, realmList2);
            }
        });
    }

    public void setModelList(String str, RealmList<RealmModel> realmList) {
        a(str, realmList, new a<RealmModel>() { // from class: io.realm.internal.OsResults.3
            @Override // io.realm.internal.OsResults.a
            public void a(OsObjectBuilder osObjectBuilder, RealmList<RealmModel> realmList2) {
                osObjectBuilder.addObjectList(0L, realmList2);
            }
        });
    }

    public <T> void addListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        if (this.observerPairs.isEmpty()) {
            nativeStartListening(this.a);
        }
        this.observerPairs.add(new ObservableCollection.CollectionObserverPair(t, orderedRealmCollectionChangeListener));
    }

    public <T> void addListener(T t, RealmChangeListener<T> realmChangeListener) {
        addListener((OsResults) t, (OrderedRealmCollectionChangeListener<OsResults>) new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public <T> void removeListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        this.observerPairs.remove(t, orderedRealmCollectionChangeListener);
        if (this.observerPairs.isEmpty()) {
            nativeStopListening(this.a);
        }
    }

    public <T> void removeListener(T t, RealmChangeListener<T> realmChangeListener) {
        removeListener((OsResults) t, (OrderedRealmCollectionChangeListener<OsResults>) new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public void removeAllListeners() {
        this.observerPairs.clear();
        nativeStopListening(this.a);
    }

    public boolean isValid() {
        return nativeIsValid(this.a);
    }

    @Override // io.realm.internal.ObservableCollection
    public void notifyChangeListeners(long j) {
        OsCollectionChangeSet osCollectionChangeSet;
        if (j == 0) {
            osCollectionChangeSet = new EmptyLoadChangeSet(null, this.c.isPartial());
        } else {
            osCollectionChangeSet = new OsCollectionChangeSet(j, !isLoaded(), null, this.c.isPartial());
        }
        if (!osCollectionChangeSet.isEmpty() || !isLoaded()) {
            this.loaded = true;
            this.observerPairs.foreach(new ObservableCollection.Callback(osCollectionChangeSet));
        }
    }

    public Mode getMode() {
        return Mode.a(nativeGetMode(this.a));
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void load() {
        if (!this.loaded) {
            nativeEvaluateQueryIfNeeded(this.a, false);
            notifyChangeListeners(0L);
        }
    }
}
