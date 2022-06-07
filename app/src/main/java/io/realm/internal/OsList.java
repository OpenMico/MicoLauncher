package io.realm.internal;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.internal.ObservableCollection;
import java.util.Date;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class OsList implements NativeObject, ObservableCollection {
    private static final long d = nativeGetFinalizerPtr();
    private final long a;
    private final NativeContext b;
    private final Table c;
    private final ObserverPairList<ObservableCollection.CollectionObserverPair> e = new ObserverPairList<>();

    private static native void nativeAddBinary(long j, @Nullable byte[] bArr);

    private static native void nativeAddBoolean(long j, boolean z);

    private static native void nativeAddDate(long j, long j2);

    private static native void nativeAddDouble(long j, double d2);

    private static native void nativeAddFloat(long j, float f);

    private static native void nativeAddLong(long j, long j2);

    private static native void nativeAddNull(long j);

    private static native void nativeAddRow(long j, long j2);

    private static native void nativeAddString(long j, @Nullable String str);

    private static native long[] nativeCreate(long j, long j2, long j3);

    private static native void nativeDelete(long j, long j2);

    private static native void nativeDeleteAll(long j);

    private static native long nativeFreeze(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetQuery(long j);

    private static native long nativeGetRow(long j, long j2);

    private static native Object nativeGetValue(long j, long j2);

    private static native void nativeInsertBinary(long j, long j2, @Nullable byte[] bArr);

    private static native void nativeInsertBoolean(long j, long j2, boolean z);

    private static native void nativeInsertDate(long j, long j2, long j3);

    private static native void nativeInsertDouble(long j, long j2, double d2);

    private static native void nativeInsertFloat(long j, long j2, float f);

    private static native void nativeInsertLong(long j, long j2, long j3);

    private static native void nativeInsertNull(long j, long j2);

    private static native void nativeInsertRow(long j, long j2, long j3);

    private static native void nativeInsertString(long j, long j2, @Nullable String str);

    private static native boolean nativeIsValid(long j);

    private static native void nativeMove(long j, long j2, long j3);

    private static native void nativeRemove(long j, long j2);

    private static native void nativeRemoveAll(long j);

    private static native void nativeSetBinary(long j, long j2, @Nullable byte[] bArr);

    private static native void nativeSetBoolean(long j, long j2, boolean z);

    private static native void nativeSetDate(long j, long j2, long j3);

    private static native void nativeSetDouble(long j, long j2, double d2);

    private static native void nativeSetFloat(long j, long j2, float f);

    private static native void nativeSetLong(long j, long j2, long j3);

    private static native void nativeSetNull(long j, long j2);

    private static native void nativeSetRow(long j, long j2, long j3);

    private static native void nativeSetString(long j, long j2, @Nullable String str);

    private static native long nativeSize(long j);

    private native void nativeStartListening(long j);

    private native void nativeStopListening(long j);

    public OsList(UncheckedRow uncheckedRow, long j) {
        OsSharedRealm sharedRealm = uncheckedRow.getTable().getSharedRealm();
        long[] nativeCreate = nativeCreate(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), j);
        this.a = nativeCreate[0];
        this.b = sharedRealm.context;
        this.b.addReference(this);
        if (nativeCreate[1] != 0) {
            this.c = new Table(sharedRealm, nativeCreate[1]);
        } else {
            this.c = null;
        }
    }

    private OsList(OsSharedRealm osSharedRealm, long j, @Nullable Table table) {
        this.a = j;
        this.c = table;
        this.b = osSharedRealm.context;
        this.b.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.a;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return d;
    }

    public UncheckedRow getUncheckedRow(long j) {
        return this.c.getUncheckedRowByPointer(nativeGetRow(this.a, j));
    }

    public void addRow(long j) {
        nativeAddRow(this.a, j);
    }

    public void insertRow(long j, long j2) {
        nativeInsertRow(this.a, j, j2);
    }

    public void setRow(long j, long j2) {
        nativeSetRow(this.a, j, j2);
    }

    public void addNull() {
        nativeAddNull(this.a);
    }

    public void insertNull(long j) {
        nativeInsertNull(this.a, j);
    }

    public void setNull(long j) {
        nativeSetNull(this.a, j);
    }

    public void addLong(long j) {
        nativeAddLong(this.a, j);
    }

    public void insertLong(long j, long j2) {
        nativeInsertLong(this.a, j, j2);
    }

    public void setLong(long j, long j2) {
        nativeSetLong(this.a, j, j2);
    }

    public void addDouble(double d2) {
        nativeAddDouble(this.a, d2);
    }

    public void insertDouble(long j, double d2) {
        nativeInsertDouble(this.a, j, d2);
    }

    public void setDouble(long j, double d2) {
        nativeSetDouble(this.a, j, d2);
    }

    public void addFloat(float f) {
        nativeAddFloat(this.a, f);
    }

    public void insertFloat(long j, float f) {
        nativeInsertFloat(this.a, j, f);
    }

    public void setFloat(long j, float f) {
        nativeSetFloat(this.a, j, f);
    }

    public void addBoolean(boolean z) {
        nativeAddBoolean(this.a, z);
    }

    public void insertBoolean(long j, boolean z) {
        nativeInsertBoolean(this.a, j, z);
    }

    public void setBoolean(long j, boolean z) {
        nativeSetBoolean(this.a, j, z);
    }

    public void addBinary(@Nullable byte[] bArr) {
        nativeAddBinary(this.a, bArr);
    }

    public void insertBinary(long j, @Nullable byte[] bArr) {
        nativeInsertBinary(this.a, j, bArr);
    }

    public void setBinary(long j, @Nullable byte[] bArr) {
        nativeSetBinary(this.a, j, bArr);
    }

    public void addString(@Nullable String str) {
        nativeAddString(this.a, str);
    }

    public void insertString(long j, @Nullable String str) {
        nativeInsertString(this.a, j, str);
    }

    public void setString(long j, @Nullable String str) {
        nativeSetString(this.a, j, str);
    }

    public void addDate(@Nullable Date date) {
        if (date == null) {
            nativeAddNull(this.a);
        } else {
            nativeAddDate(this.a, date.getTime());
        }
    }

    public void insertDate(long j, @Nullable Date date) {
        if (date == null) {
            nativeInsertNull(this.a, j);
        } else {
            nativeInsertDate(this.a, j, date.getTime());
        }
    }

    public void setDate(long j, @Nullable Date date) {
        if (date == null) {
            nativeSetNull(this.a, j);
        } else {
            nativeSetDate(this.a, j, date.getTime());
        }
    }

    @Nullable
    public Object getValue(long j) {
        return nativeGetValue(this.a, j);
    }

    public void move(long j, long j2) {
        nativeMove(this.a, j, j2);
    }

    public void remove(long j) {
        nativeRemove(this.a, j);
    }

    public void removeAll() {
        nativeRemoveAll(this.a);
    }

    public long size() {
        return nativeSize(this.a);
    }

    public boolean isEmpty() {
        return nativeSize(this.a) <= 0;
    }

    public TableQuery getQuery() {
        return new TableQuery(this.b, this.c, nativeGetQuery(this.a));
    }

    public boolean isValid() {
        return nativeIsValid(this.a);
    }

    public void delete(long j) {
        nativeDelete(this.a, j);
    }

    public void deleteAll() {
        nativeDeleteAll(this.a);
    }

    public Table getTargetTable() {
        return this.c;
    }

    public <T> void addListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        if (this.e.isEmpty()) {
            nativeStartListening(this.a);
        }
        this.e.add(new ObservableCollection.CollectionObserverPair(t, orderedRealmCollectionChangeListener));
    }

    public <T> void addListener(T t, RealmChangeListener<T> realmChangeListener) {
        addListener((OsList) t, (OrderedRealmCollectionChangeListener<OsList>) new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public <T> void removeListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        this.e.remove(t, orderedRealmCollectionChangeListener);
        if (this.e.isEmpty()) {
            nativeStopListening(this.a);
        }
    }

    public <T> void removeListener(T t, RealmChangeListener<T> realmChangeListener) {
        removeListener((OsList) t, (OrderedRealmCollectionChangeListener<OsList>) new ObservableCollection.RealmChangeListenerWrapper(realmChangeListener));
    }

    public void removeAllListeners() {
        this.e.clear();
        nativeStopListening(this.a);
    }

    @Override // io.realm.internal.ObservableCollection
    public void notifyChangeListeners(long j) {
        OsCollectionChangeSet osCollectionChangeSet = new OsCollectionChangeSet(j, false);
        if (!osCollectionChangeSet.isEmpty()) {
            this.e.foreach(new ObservableCollection.Callback(osCollectionChangeSet));
        }
    }

    public OsList freeze(OsSharedRealm osSharedRealm) {
        long nativeFreeze = nativeFreeze(this.a, osSharedRealm.getNativePtr());
        Table table = this.c;
        return new OsList(osSharedRealm, nativeFreeze, table != null ? table.freeze(osSharedRealm) : null);
    }
}
