package io.realm;

import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public abstract class RealmObjectSchema {
    static final Map<Class<?>, b> a;
    static final Map<Class<?>, b> b;
    final RealmSchema c;
    final BaseRealm d;
    final Table e;
    private final ColumnInfo f;

    /* loaded from: classes5.dex */
    public interface Function {
        void apply(DynamicRealmObject dynamicRealmObject);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract FieldDescriptor a(String str, RealmFieldType... realmFieldTypeArr);

    public abstract RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr);

    public abstract RealmObjectSchema addIndex(String str);

    public abstract RealmObjectSchema addPrimaryKey(String str);

    public abstract RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmListField(String str, Class<?> cls);

    public abstract RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema removeField(String str);

    public abstract RealmObjectSchema removeIndex(String str);

    public abstract RealmObjectSchema removePrimaryKey();

    public abstract RealmObjectSchema renameField(String str, String str2);

    public abstract RealmObjectSchema setClassName(String str);

    public abstract RealmObjectSchema setNullable(String str, boolean z);

    public abstract RealmObjectSchema setRequired(String str, boolean z);

    public abstract RealmObjectSchema transform(Function function);

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(String.class, new b(RealmFieldType.STRING, RealmFieldType.STRING_LIST, true));
        hashMap.put(Short.TYPE, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        hashMap.put(Short.class, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        hashMap.put(Integer.TYPE, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        hashMap.put(Integer.class, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        hashMap.put(Long.TYPE, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        hashMap.put(Long.class, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        hashMap.put(Float.TYPE, new b(RealmFieldType.FLOAT, RealmFieldType.FLOAT_LIST, false));
        hashMap.put(Float.class, new b(RealmFieldType.FLOAT, RealmFieldType.FLOAT_LIST, true));
        hashMap.put(Double.TYPE, new b(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_LIST, false));
        hashMap.put(Double.class, new b(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_LIST, true));
        hashMap.put(Boolean.TYPE, new b(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_LIST, false));
        hashMap.put(Boolean.class, new b(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_LIST, true));
        hashMap.put(Byte.TYPE, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        hashMap.put(Byte.class, new b(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        hashMap.put(byte[].class, new b(RealmFieldType.BINARY, RealmFieldType.BINARY_LIST, true));
        hashMap.put(Date.class, new b(RealmFieldType.DATE, RealmFieldType.DATE_LIST, true));
        a = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(RealmObject.class, new b(RealmFieldType.OBJECT, null, false));
        hashMap2.put(RealmList.class, new b(RealmFieldType.LIST, null, false));
        b = Collections.unmodifiableMap(hashMap2);
    }

    public RealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table, ColumnInfo columnInfo) {
        this.c = realmSchema;
        this.d = baseRealm;
        this.e = table;
        this.f = columnInfo;
    }

    public String getClassName() {
        return this.e.getClassName();
    }

    public boolean hasField(String str) {
        return this.e.getColumnKey(str) != -1;
    }

    public boolean hasIndex(String str) {
        b(str);
        c(str);
        Table table = this.e;
        return table.hasSearchIndex(table.getColumnKey(str));
    }

    public boolean isRequired(String str) {
        return !this.e.isColumnNullable(d(str));
    }

    public boolean isNullable(String str) {
        return this.e.isColumnNullable(d(str));
    }

    public boolean isPrimaryKey(String str) {
        c(str);
        return str.equals(OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, getClassName()));
    }

    public boolean hasPrimaryKey() {
        return OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, getClassName()) != null;
    }

    public String getPrimaryKey() {
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            return primaryKeyForObject;
        }
        throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
    }

    public Set<String> getFieldNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet((int) this.e.getColumnCount());
        for (String str : this.e.getColumnNames()) {
            linkedHashSet.add(str);
        }
        return linkedHashSet;
    }

    public RealmFieldType getFieldType(String str) {
        return this.e.getColumnType(d(str));
    }

    public long a(String str) {
        long columnKey = this.f.getColumnKey(str);
        if (columnKey >= 0) {
            return columnKey;
        }
        throw new IllegalArgumentException("Field does not exist: " + str);
    }

    public Table a() {
        return this.e;
    }

    public static final Map<Class<?>, b> b() {
        return a;
    }

    protected final o getSchemaConnector() {
        return new o(this.c);
    }

    public static void b(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Field name can not be null or empty");
        } else if (str.contains(".")) {
            throw new IllegalArgumentException("Field name can not contain '.'");
        } else if (str.length() > 63) {
            throw new IllegalArgumentException("Field name is currently limited to max 63 characters.");
        }
    }

    void c(String str) {
        if (this.e.getColumnKey(str) == -1) {
            throw new IllegalArgumentException("Field name doesn't exist on object '" + getClassName() + "': " + str);
        }
    }

    long d(String str) {
        long columnKey = this.e.getColumnKey(str);
        if (columnKey != -1) {
            return columnKey;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field name '%s' does not exist on schema for '%s'", str, getClassName()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ColumnInfo {
        private final Table a;

        public a(Table table) {
            super((ColumnInfo) null, false);
            this.a = table;
        }

        @Override // io.realm.internal.ColumnInfo
        public long getColumnKey(String str) {
            return this.a.getColumnKey(str);
        }

        @Override // io.realm.internal.ColumnInfo
        public ColumnInfo.ColumnDetails getColumnDetails(String str) {
            throw new UnsupportedOperationException("DynamicColumnIndices do not support 'getColumnDetails'");
        }

        @Override // io.realm.internal.ColumnInfo
        public void copyFrom(ColumnInfo columnInfo) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        protected ColumnInfo copy(boolean z) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        protected void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot copy");
        }
    }

    /* loaded from: classes5.dex */
    public static final class b {
        final RealmFieldType a;
        final RealmFieldType b;
        final boolean c;

        b(RealmFieldType realmFieldType, @Nullable RealmFieldType realmFieldType2, boolean z) {
            this.a = realmFieldType;
            this.b = realmFieldType2;
            this.c = z;
        }
    }
}
