package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import java.util.Date;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class Table implements NativeObject {
    public static final long INFINITE = -1;
    public static final int MAX_BINARY_SIZE = 16777200;
    public static final int MAX_STRING_SIZE = 16777199;
    public static final boolean NOT_NULLABLE = false;
    public static final int NO_MATCH = -1;
    public static final boolean NULLABLE = true;
    private final long c;
    private final NativeContext d;
    private final OsSharedRealm e;
    private static final String a = Util.getTablePrefix();
    public static final int CLASS_NAME_MAX_LENGTH = 63 - a.length();
    private static final long b = nativeGetFinalizerPtr();

    private native long nativeAddColumn(long j, int i, String str, boolean z);

    private native long nativeAddColumnLink(long j, int i, String str, long j2);

    private native long nativeAddPrimitiveListColumn(long j, int i, String str, boolean z);

    private native void nativeAddSearchIndex(long j, long j2);

    private native void nativeClear(long j, boolean z);

    private native void nativeConvertColumnToNotNullable(long j, long j2, boolean z);

    private native void nativeConvertColumnToNullable(long j, long j2, boolean z);

    private native long nativeCountDouble(long j, long j2, double d);

    private native long nativeCountFloat(long j, long j2, float f);

    private native long nativeCountLong(long j, long j2, long j3);

    private native long nativeCountString(long j, long j2, String str);

    private native long nativeFindFirstBool(long j, long j2, boolean z);

    private native long nativeFindFirstDouble(long j, long j2, double d);

    private native long nativeFindFirstFloat(long j, long j2, float f);

    public static native long nativeFindFirstInt(long j, long j2, long j3);

    public static native long nativeFindFirstNull(long j, long j2);

    public static native long nativeFindFirstString(long j, long j2, String str);

    private native long nativeFindFirstTimestamp(long j, long j2, long j3);

    private static native long nativeFreeze(long j, long j2);

    private native boolean nativeGetBoolean(long j, long j2, long j3);

    private native byte[] nativeGetByteArray(long j, long j2, long j3);

    private native long nativeGetColumnCount(long j);

    private native long nativeGetColumnKey(long j, String str);

    private native String nativeGetColumnName(long j, long j2);

    private native String[] nativeGetColumnNames(long j);

    private native int nativeGetColumnType(long j, long j2);

    private native double nativeGetDouble(long j, long j2, long j3);

    private static native long nativeGetFinalizerPtr();

    private native float nativeGetFloat(long j, long j2, long j3);

    private native long nativeGetLink(long j, long j2, long j3);

    private native long nativeGetLinkTarget(long j, long j2);

    private native long nativeGetLong(long j, long j2, long j3);

    private native String nativeGetName(long j);

    private native String nativeGetString(long j, long j2, long j3);

    private native long nativeGetTimestamp(long j, long j2, long j3);

    private native boolean nativeHasSameSchema(long j, long j2);

    private native boolean nativeHasSearchIndex(long j, long j2);

    public static native void nativeIncrementLong(long j, long j2, long j3, long j4);

    private native boolean nativeIsColumnNullable(long j, long j2);

    private native boolean nativeIsNullLink(long j, long j2, long j3);

    private native boolean nativeIsValid(long j);

    private native void nativeMoveLastOver(long j, long j2);

    public static native void nativeNullifyLink(long j, long j2, long j3);

    private native void nativeRemoveColumn(long j, long j2);

    private native void nativeRemoveSearchIndex(long j, long j2);

    private native void nativeRenameColumn(long j, long j2, String str);

    public static native void nativeSetBoolean(long j, long j2, long j3, boolean z, boolean z2);

    public static native void nativeSetByteArray(long j, long j2, long j3, byte[] bArr, boolean z);

    public static native void nativeSetDouble(long j, long j2, long j3, double d, boolean z);

    public static native void nativeSetFloat(long j, long j2, long j3, float f, boolean z);

    public static native void nativeSetLink(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetLong(long j, long j2, long j3, long j4, boolean z);

    public static native void nativeSetNull(long j, long j2, long j3, boolean z);

    public static native void nativeSetString(long j, long j2, long j3, String str, boolean z);

    public static native void nativeSetTimestamp(long j, long j2, long j3, long j4, boolean z);

    private native long nativeSize(long j);

    private native long nativeWhere(long j);

    public Table getTable() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public native long nativeGetRowPtr(long j, long j2);

    public Table(OsSharedRealm osSharedRealm, long j) {
        this.d = osSharedRealm.context;
        this.e = osSharedRealm;
        this.c = j;
        this.d.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.c;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return b;
    }

    public boolean isValid() {
        long j = this.c;
        return j != 0 && nativeIsValid(j);
    }

    private void a(String str) {
        if (str.length() > 63) {
            throw new IllegalArgumentException("Column names are currently limited to max 63 characters.");
        }
    }

    public long addColumn(RealmFieldType realmFieldType, String str, boolean z) {
        a(str);
        switch (realmFieldType) {
            case INTEGER:
            case BOOLEAN:
            case STRING:
            case BINARY:
            case DATE:
            case FLOAT:
            case DOUBLE:
                return nativeAddColumn(this.c, realmFieldType.getNativeValue(), str, z);
            case INTEGER_LIST:
            case BOOLEAN_LIST:
            case STRING_LIST:
            case BINARY_LIST:
            case DATE_LIST:
            case FLOAT_LIST:
            case DOUBLE_LIST:
                return nativeAddPrimitiveListColumn(this.c, realmFieldType.getNativeValue() - 128, str, z);
            default:
                throw new IllegalArgumentException("Unsupported type: " + realmFieldType);
        }
    }

    public long addColumn(RealmFieldType realmFieldType, String str) {
        return addColumn(realmFieldType, str, false);
    }

    public long addColumnLink(RealmFieldType realmFieldType, String str, Table table) {
        a(str);
        return nativeAddColumnLink(this.c, realmFieldType.getNativeValue(), str, table.c);
    }

    public void removeColumn(long j) {
        String className = getClassName();
        String columnName = getColumnName(j);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.e, getClassName());
        nativeRemoveColumn(this.c, j);
        if (columnName.equals(primaryKeyForObject)) {
            OsObjectStore.setPrimaryKeyForObject(this.e, className, null);
        }
    }

    public void renameColumn(long j, String str) {
        a(str);
        String nativeGetColumnName = nativeGetColumnName(this.c, j);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.e, getClassName());
        nativeRenameColumn(this.c, j, str);
        if (nativeGetColumnName.equals(primaryKeyForObject)) {
            try {
                OsObjectStore.setPrimaryKeyForObject(this.e, getClassName(), str);
            } catch (Exception e) {
                nativeRenameColumn(this.c, j, nativeGetColumnName);
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isColumnNullable(long j) {
        return nativeIsColumnNullable(this.c, j);
    }

    public void convertColumnToNullable(long j) {
        if (!this.e.isSyncRealm()) {
            nativeConvertColumnToNullable(this.c, j, a(j));
            return;
        }
        throw new IllegalStateException("This method is only available for non-synchronized Realms");
    }

    public void convertColumnToNotNullable(long j) {
        if (!this.e.isSyncRealm()) {
            nativeConvertColumnToNotNullable(this.c, j, a(j));
            return;
        }
        throw new IllegalStateException("This method is only available for non-synchronized Realms");
    }

    public long size() {
        return nativeSize(this.c);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear(boolean z) {
        b();
        nativeClear(this.c, z);
    }

    public long getColumnCount() {
        return nativeGetColumnCount(this.c);
    }

    public String getColumnName(long j) {
        return nativeGetColumnName(this.c, j);
    }

    public String[] getColumnNames() {
        return nativeGetColumnNames(this.c);
    }

    public long getColumnKey(String str) {
        if (str != null) {
            return nativeGetColumnKey(this.c, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    public RealmFieldType getColumnType(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.c, j));
    }

    public void moveLastOver(long j) {
        b();
        nativeMoveLastOver(this.c, j);
    }

    private boolean a(long j) {
        return getColumnName(j).equals(OsObjectStore.getPrimaryKeyForObject(this.e, getClassName()));
    }

    public static void throwDuplicatePrimaryKeyException(Object obj) {
        throw new RealmPrimaryKeyConstraintException("Value already exists: " + obj);
    }

    public OsSharedRealm getSharedRealm() {
        return this.e;
    }

    public long getLong(long j, long j2) {
        return nativeGetLong(this.c, j, j2);
    }

    public boolean getBoolean(long j, long j2) {
        return nativeGetBoolean(this.c, j, j2);
    }

    public float getFloat(long j, long j2) {
        return nativeGetFloat(this.c, j, j2);
    }

    public double getDouble(long j, long j2) {
        return nativeGetDouble(this.c, j, j2);
    }

    public Date getDate(long j, long j2) {
        return new Date(nativeGetTimestamp(this.c, j, j2));
    }

    public String getString(long j, long j2) {
        return nativeGetString(this.c, j, j2);
    }

    public byte[] getBinaryByteArray(long j, long j2) {
        return nativeGetByteArray(this.c, j, j2);
    }

    public long getLink(long j, long j2) {
        return nativeGetLink(this.c, j, j2);
    }

    public Table getLinkTarget(long j) {
        return new Table(this.e, nativeGetLinkTarget(this.c, j));
    }

    public UncheckedRow getUncheckedRow(long j) {
        return UncheckedRow.a(this.d, this, j);
    }

    public UncheckedRow getUncheckedRowByPointer(long j) {
        return UncheckedRow.b(this.d, this, j);
    }

    public CheckedRow getCheckedRow(long j) {
        return CheckedRow.get(this.d, this, j);
    }

    public void setLong(long j, long j2, long j3, boolean z) {
        b();
        nativeSetLong(this.c, j, j2, j3, z);
    }

    public void incrementLong(long j, long j2, long j3) {
        b();
        nativeIncrementLong(this.c, j, j2, j3);
    }

    public void setBoolean(long j, long j2, boolean z, boolean z2) {
        b();
        nativeSetBoolean(this.c, j, j2, z, z2);
    }

    public void setFloat(long j, long j2, float f, boolean z) {
        b();
        nativeSetFloat(this.c, j, j2, f, z);
    }

    public void setDouble(long j, long j2, double d, boolean z) {
        b();
        nativeSetDouble(this.c, j, j2, d, z);
    }

    public void setDate(long j, long j2, Date date, boolean z) {
        if (date != null) {
            b();
            nativeSetTimestamp(this.c, j, j2, date.getTime(), z);
            return;
        }
        throw new IllegalArgumentException("Null Date is not allowed.");
    }

    public void setString(long j, long j2, @Nullable String str, boolean z) {
        b();
        if (str == null) {
            nativeSetNull(this.c, j, j2, z);
        } else {
            nativeSetString(this.c, j, j2, str, z);
        }
    }

    public void setBinaryByteArray(long j, long j2, byte[] bArr, boolean z) {
        b();
        nativeSetByteArray(this.c, j, j2, bArr, z);
    }

    public void setLink(long j, long j2, long j3, boolean z) {
        b();
        nativeSetLink(this.c, j, j2, j3, z);
    }

    public void setNull(long j, long j2, boolean z) {
        b();
        nativeSetNull(this.c, j, j2, z);
    }

    public void addSearchIndex(long j) {
        b();
        nativeAddSearchIndex(this.c, j);
    }

    public void removeSearchIndex(long j) {
        b();
        nativeRemoveSearchIndex(this.c, j);
    }

    public boolean hasSearchIndex(long j) {
        return nativeHasSearchIndex(this.c, j);
    }

    public boolean isNullLink(long j, long j2) {
        return nativeIsNullLink(this.c, j, j2);
    }

    public void nullifyLink(long j, long j2) {
        nativeNullifyLink(this.c, j, j2);
    }

    public boolean a() {
        OsSharedRealm osSharedRealm = this.e;
        return osSharedRealm != null && !osSharedRealm.isInTransaction();
    }

    public void b() {
        if (a()) {
            c();
        }
    }

    public long count(long j, long j2) {
        return nativeCountLong(this.c, j, j2);
    }

    public long count(long j, float f) {
        return nativeCountFloat(this.c, j, f);
    }

    public long count(long j, double d) {
        return nativeCountDouble(this.c, j, d);
    }

    public long count(long j, String str) {
        return nativeCountString(this.c, j, str);
    }

    public TableQuery where() {
        return new TableQuery(this.d, this, nativeWhere(this.c));
    }

    public long findFirstLong(long j, long j2) {
        return nativeFindFirstInt(this.c, j, j2);
    }

    public long findFirstBoolean(long j, boolean z) {
        return nativeFindFirstBool(this.c, j, z);
    }

    public long findFirstFloat(long j, float f) {
        return nativeFindFirstFloat(this.c, j, f);
    }

    public long findFirstDouble(long j, double d) {
        return nativeFindFirstDouble(this.c, j, d);
    }

    public long findFirstDate(long j, Date date) {
        if (date != null) {
            return nativeFindFirstTimestamp(this.c, j, date.getTime());
        }
        throw new IllegalArgumentException("null is not supported");
    }

    public long findFirstString(long j, String str) {
        if (str != null) {
            return nativeFindFirstString(this.c, j, str);
        }
        throw new IllegalArgumentException("null is not supported");
    }

    public long findFirstNull(long j) {
        return nativeFindFirstNull(this.c, j);
    }

    @Nullable
    public String getName() {
        return nativeGetName(this.c);
    }

    public String getClassName() {
        String classNameForTable = getClassNameForTable(getName());
        if (!Util.isEmptyString(classNameForTable)) {
            return classNameForTable;
        }
        throw new IllegalStateException("This object class is no longer part of the schema for the Realm file. It is therefor not possible to access the schema name.");
    }

    public String toString() {
        long columnCount = getColumnCount();
        String name = getName();
        StringBuilder sb = new StringBuilder("The Table ");
        if (name != null && !name.isEmpty()) {
            sb.append(getName());
            sb.append(StringUtils.SPACE);
        }
        sb.append("contains ");
        sb.append(columnCount);
        sb.append(" columns: ");
        String[] columnNames = getColumnNames();
        int length = columnNames.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            String str = columnNames[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(str);
            i++;
            z = false;
        }
        sb.append(".");
        sb.append(" And ");
        sb.append(size());
        sb.append(" rows.");
        return sb.toString();
    }

    private static void c() {
        throw new IllegalStateException("Cannot modify managed objects outside of a write transaction.");
    }

    public boolean hasSameSchema(Table table) {
        if (table != null) {
            return nativeHasSameSchema(this.c, table.c);
        }
        throw new IllegalArgumentException("The argument cannot be null");
    }

    public Table freeze(OsSharedRealm osSharedRealm) {
        if (osSharedRealm.isFrozen()) {
            return new Table(osSharedRealm, nativeFreeze(osSharedRealm.getNativePtr(), this.c));
        }
        throw new IllegalArgumentException("Frozen Realm required");
    }

    @Nullable
    public static String getClassNameForTable(@Nullable String str) {
        if (str == null) {
            return null;
        }
        return !str.startsWith(a) ? str : str.substring(a.length());
    }

    public static String getTableNameForClass(String str) {
        if (str == null) {
            return null;
        }
        return a + str;
    }
}
