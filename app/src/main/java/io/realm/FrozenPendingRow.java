package io.realm;

import io.realm.internal.InvalidRow;
import io.realm.internal.OsList;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.Date;

/* loaded from: classes5.dex */
public enum FrozenPendingRow implements Row {
    INSTANCE;

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return false;
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        return false;
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public boolean isNullLink(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public OsList getModelList(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setString(long j, String str) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, byte[] bArr) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public boolean isNull(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void setNull(long j) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        throw new IllegalStateException("This object was frozen while a query for it was still running.");
    }

    @Override // io.realm.internal.Row
    public Row freeze(OsSharedRealm osSharedRealm) {
        return InvalidRow.INSTANCE;
    }
}
