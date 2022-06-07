package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.Date;

/* loaded from: classes5.dex */
public enum InvalidRow implements Row {
    INSTANCE;

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        return false;
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        throw a();
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        throw a();
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        throw a();
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        throw a();
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public boolean isNullLink(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public OsList getModelList(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setString(long j, String str) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, byte[] bArr) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public boolean isNull(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void setNull(long j) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        throw a();
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        throw a();
    }

    @Override // io.realm.internal.Row
    public Row freeze(OsSharedRealm osSharedRealm) {
        return INSTANCE;
    }

    private RuntimeException a() {
        return new IllegalStateException("Object is no longer managed by Realm. Has it been deleted?");
    }
}
