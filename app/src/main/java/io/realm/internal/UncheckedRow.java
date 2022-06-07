package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.Date;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class UncheckedRow implements NativeObject, Row {
    private static final long a = nativeGetFinalizerPtr();
    private final long b;
    protected final NativeContext context;
    protected final Table parent;

    private static native long nativeGetFinalizerPtr();

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return true;
    }

    protected native long nativeFreeze(long j, long j2);

    protected native boolean nativeGetBoolean(long j, long j2);

    protected native byte[] nativeGetByteArray(long j, long j2);

    protected native long nativeGetColumnCount(long j);

    protected native long nativeGetColumnKey(long j, String str);

    protected native String[] nativeGetColumnNames(long j);

    protected native int nativeGetColumnType(long j, long j2);

    protected native double nativeGetDouble(long j, long j2);

    protected native float nativeGetFloat(long j, long j2);

    protected native long nativeGetLink(long j, long j2);

    protected native long nativeGetLong(long j, long j2);

    protected native long nativeGetObjectKey(long j);

    protected native String nativeGetString(long j, long j2);

    protected native long nativeGetTimestamp(long j, long j2);

    protected native boolean nativeHasColumn(long j, String str);

    protected native boolean nativeIsNull(long j, long j2);

    protected native boolean nativeIsNullLink(long j, long j2);

    protected native boolean nativeIsValid(long j);

    protected native void nativeNullifyLink(long j, long j2);

    protected native void nativeSetBoolean(long j, long j2, boolean z);

    protected native void nativeSetByteArray(long j, long j2, @Nullable byte[] bArr);

    protected native void nativeSetDouble(long j, long j2, double d);

    protected native void nativeSetFloat(long j, long j2, float f);

    protected native void nativeSetLink(long j, long j2, long j3);

    protected native void nativeSetLong(long j, long j2, long j3);

    protected native void nativeSetNull(long j, long j2);

    protected native void nativeSetString(long j, long j2, String str);

    protected native void nativeSetTimestamp(long j, long j2, long j3);

    public UncheckedRow(NativeContext nativeContext, Table table, long j) {
        this.context = nativeContext;
        this.parent = table;
        this.b = j;
        nativeContext.addReference(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UncheckedRow(UncheckedRow uncheckedRow) {
        this.context = uncheckedRow.context;
        this.parent = uncheckedRow.parent;
        this.b = uncheckedRow.b;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.b;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static UncheckedRow a(NativeContext nativeContext, Table table, long j) {
        return new UncheckedRow(nativeContext, table, table.nativeGetRowPtr(table.getNativePtr(), j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static UncheckedRow b(NativeContext nativeContext, Table table, long j) {
        return new UncheckedRow(nativeContext, table, j);
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        return nativeGetColumnCount(this.b);
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        return nativeGetColumnNames(this.b);
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        if (str != null) {
            return nativeGetColumnKey(this.b, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.b, j));
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        return this.parent;
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        return nativeGetObjectKey(this.b);
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        return nativeGetLong(this.b, j);
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        return nativeGetBoolean(this.b, j);
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        return nativeGetFloat(this.b, j);
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        return nativeGetDouble(this.b, j);
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        return new Date(nativeGetTimestamp(this.b, j));
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        return nativeGetString(this.b, j);
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        return nativeGetByteArray(this.b, j);
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        return nativeGetLink(this.b, j);
    }

    public boolean isNullLink(long j) {
        return nativeIsNullLink(this.b, j);
    }

    public OsList getModelList(long j) {
        return new OsList(this, j);
    }

    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        return new OsList(this, j);
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        this.parent.b();
        nativeSetLong(this.b, j, j2);
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        this.parent.b();
        nativeSetBoolean(this.b, j, z);
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        this.parent.b();
        nativeSetFloat(this.b, j, f);
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        this.parent.b();
        nativeSetDouble(this.b, j, d);
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        this.parent.b();
        if (date != null) {
            nativeSetTimestamp(this.b, j, date.getTime());
            return;
        }
        throw new IllegalArgumentException("Null Date is not allowed.");
    }

    @Override // io.realm.internal.Row
    public void setString(long j, @Nullable String str) {
        this.parent.b();
        if (str == null) {
            nativeSetNull(this.b, j);
        } else {
            nativeSetString(this.b, j, str);
        }
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, @Nullable byte[] bArr) {
        this.parent.b();
        nativeSetByteArray(this.b, j, bArr);
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        this.parent.b();
        nativeSetLink(this.b, j, j2);
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        this.parent.b();
        nativeNullifyLink(this.b, j);
    }

    public boolean isNull(long j) {
        return nativeIsNull(this.b, j);
    }

    public void setNull(long j) {
        this.parent.b();
        nativeSetNull(this.b, j);
    }

    public CheckedRow convertToChecked() {
        return CheckedRow.getFromRow(this);
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        long j = this.b;
        return j != 0 && nativeIsValid(j);
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        if (!isValid()) {
            throw new IllegalStateException("Object is no longer managed by Realm. Has it been deleted?");
        }
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        return nativeHasColumn(this.b, str);
    }

    public Row freeze(OsSharedRealm osSharedRealm) {
        if (!isValid()) {
            return InvalidRow.INSTANCE;
        }
        return new UncheckedRow(this.context, this.parent.freeze(osSharedRealm), nativeFreeze(this.b, osSharedRealm.getNativePtr()));
    }
}
