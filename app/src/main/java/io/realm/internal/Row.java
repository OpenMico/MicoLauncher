package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.Date;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public interface Row {
    void checkIfAttached();

    Row freeze(OsSharedRealm osSharedRealm);

    byte[] getBinaryByteArray(long j);

    boolean getBoolean(long j);

    long getColumnCount();

    long getColumnKey(String str);

    String[] getColumnNames();

    RealmFieldType getColumnType(long j);

    Date getDate(long j);

    double getDouble(long j);

    float getFloat(long j);

    long getLink(long j);

    long getLong(long j);

    OsList getModelList(long j);

    long getObjectKey();

    String getString(long j);

    Table getTable();

    OsList getValueList(long j, RealmFieldType realmFieldType);

    boolean hasColumn(String str);

    boolean isLoaded();

    boolean isNull(long j);

    boolean isNullLink(long j);

    boolean isValid();

    void nullifyLink(long j);

    void setBinaryByteArray(long j, @Nullable byte[] bArr);

    void setBoolean(long j, boolean z);

    void setDate(long j, Date date);

    void setDouble(long j, double d);

    void setFloat(long j, float f);

    void setLink(long j, long j2);

    void setLong(long j, long j2);

    void setNull(long j);

    void setString(long j, @Nullable String str);
}
