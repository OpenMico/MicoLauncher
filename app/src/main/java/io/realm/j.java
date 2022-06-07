package io.realm;

import io.realm.RealmObjectSchema;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsResults;
import io.realm.internal.Table;
import io.realm.internal.core.DescriptorOrdering;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MutableRealmObjectSchema.java */
/* loaded from: classes5.dex */
public class j extends RealmObjectSchema {
    /* JADX INFO: Access modifiers changed from: package-private */
    public j(BaseRealm baseRealm, RealmSchema realmSchema, Table table) {
        super(baseRealm, realmSchema, table, new RealmObjectSchema.a(table));
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setClassName(String str) {
        this.d.a();
        e(str);
        String tableNameForClass = Table.getTableNameForClass(str);
        if (str.length() > Table.CLASS_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: '%2$s' (%3$d)", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), str, Integer.valueOf(str.length())));
        } else if (!this.d.sharedRealm.hasTable(tableNameForClass)) {
            String name = this.e.getName();
            String className = this.e.getClassName();
            String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, className);
            if (primaryKeyForObject != null) {
                OsObjectStore.setPrimaryKeyForObject(this.d.sharedRealm, className, null);
            }
            this.d.sharedRealm.renameTable(name, tableNameForClass);
            if (primaryKeyForObject != null) {
                try {
                    OsObjectStore.setPrimaryKeyForObject(this.d.sharedRealm, str, primaryKeyForObject);
                } catch (Exception e) {
                    this.d.sharedRealm.renameTable(this.e.getName(), name);
                    throw e;
                }
            }
            return this;
        } else {
            throw new IllegalArgumentException("Class already exists: " + str);
        }
    }

    private void e(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Null or empty class names are not allowed");
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        RealmObjectSchema.b bVar = (RealmObjectSchema.b) a.get(cls);
        if (bVar != null) {
            if (a(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
                c();
            }
            f(str);
            boolean z = bVar.c;
            if (a(fieldAttributeArr, FieldAttribute.REQUIRED)) {
                z = false;
            }
            long addColumn = this.e.addColumn(bVar.a, str, z);
            try {
                a(str, fieldAttributeArr);
                return this;
            } catch (Exception e) {
                this.e.removeColumn(addColumn);
                throw e;
            }
        } else if (b.containsKey(cls)) {
            throw new IllegalArgumentException("Use addRealmObjectField() instead to add fields that link to other RealmObjects: " + str);
        } else if (RealmModel.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Use 'addRealmObjectField()' instead to add fields that link to other RealmObjects: %s(%s)", str, cls));
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "Realm doesn't support this field type: %s(%s)", str, cls));
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema) {
        b(str);
        g(str);
        this.e.addColumnLink(RealmFieldType.OBJECT, str, this.d.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema) {
        b(str);
        g(str);
        this.e.addColumnLink(RealmFieldType.LIST, str, this.d.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, Class<?> cls) {
        b(str);
        g(str);
        RealmObjectSchema.b bVar = (RealmObjectSchema.b) a.get(cls);
        if (bVar != null) {
            this.e.addColumn(bVar.b, str, bVar.c);
            return this;
        } else if (cls.equals(RealmObjectSchema.class) || RealmModel.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Use 'addRealmListField(String name, RealmObjectSchema schema)' instead to add lists that link to other RealmObjects: " + str);
        } else {
            throw new IllegalArgumentException(String.format(Locale.US, "RealmList does not support lists with this type: %s(%s)", str, cls));
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeField(String str) {
        this.d.a();
        b(str);
        if (hasField(str)) {
            long d = d(str);
            String className = getClassName();
            if (str.equals(OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, className))) {
                OsObjectStore.setPrimaryKeyForObject(this.d.sharedRealm, className, str);
            }
            this.e.removeColumn(d);
            return this;
        }
        throw new IllegalStateException(str + " does not exist.");
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema renameField(String str, String str2) {
        this.d.a();
        b(str);
        c(str);
        b(str2);
        g(str2);
        this.e.renameColumn(d(str), str2);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addIndex(String str) {
        b(str);
        c(str);
        long d = d(str);
        if (!this.e.hasSearchIndex(d)) {
            this.e.addSearchIndex(d);
            return this;
        }
        throw new IllegalStateException(str + " already has an index.");
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeIndex(String str) {
        this.d.a();
        b(str);
        c(str);
        long d = d(str);
        if (this.e.hasSearchIndex(d)) {
            this.e.removeSearchIndex(d);
            return this;
        }
        throw new IllegalStateException("Field is not indexed: " + str);
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addPrimaryKey(String str) {
        c();
        b(str);
        c(str);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, getClassName());
        if (primaryKeyForObject == null) {
            long d = d(str);
            if (getFieldType(str) != RealmFieldType.STRING && !this.e.hasSearchIndex(d)) {
                this.e.addSearchIndex(d);
            }
            OsObjectStore.setPrimaryKeyForObject(this.d.sharedRealm, getClassName(), str);
            return this;
        }
        throw new IllegalStateException(String.format(Locale.ENGLISH, "Field '%s' has been already defined as primary key.", primaryKeyForObject));
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removePrimaryKey() {
        this.d.a();
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.d.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            long columnKey = this.e.getColumnKey(primaryKeyForObject);
            if (this.e.hasSearchIndex(columnKey)) {
                this.e.removeSearchIndex(columnKey);
            }
            OsObjectStore.setPrimaryKeyForObject(this.d.sharedRealm, getClassName(), null);
            return this;
        }
        throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setRequired(String str, boolean z) {
        long columnKey = this.e.getColumnKey(str);
        boolean isRequired = isRequired(str);
        RealmFieldType columnType = this.e.getColumnType(columnKey);
        if (columnType == RealmFieldType.OBJECT) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmObject references: " + str);
        } else if (columnType == RealmFieldType.LIST) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmList references: " + str);
        } else if (z && isRequired) {
            throw new IllegalStateException("Field is already required: " + str);
        } else if (z || isRequired) {
            if (z) {
                try {
                    this.e.convertColumnToNotNullable(columnKey);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("Attempted to insert null into non-nullable column")) {
                        throw new IllegalStateException(String.format("The primary key field '%s' has 'null' values stored.", str));
                    }
                    throw e;
                }
            } else {
                this.e.convertColumnToNullable(columnKey);
            }
            return this;
        } else {
            throw new IllegalStateException("Field is already nullable: " + str);
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setNullable(String str, boolean z) {
        setRequired(str, !z);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema transform(RealmObjectSchema.Function function) {
        if (function != null) {
            OsResults createSnapshot = OsResults.createFromQuery(this.d.sharedRealm, this.e.where(), new DescriptorOrdering()).createSnapshot();
            long size = createSnapshot.size();
            if (size <= 2147483647L) {
                int size2 = (int) createSnapshot.size();
                for (int i = 0; i < size2; i++) {
                    DynamicRealmObject dynamicRealmObject = new DynamicRealmObject(this.d, new CheckedRow(createSnapshot.getUncheckedRow(i)));
                    if (dynamicRealmObject.isValid()) {
                        function.apply(dynamicRealmObject);
                    }
                }
            } else {
                throw new UnsupportedOperationException("Too many results to iterate: " + size);
            }
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.realm.RealmObjectSchema
    public FieldDescriptor a(String str, RealmFieldType... realmFieldTypeArr) {
        return FieldDescriptor.createStandardFieldDescriptor(getSchemaConnector(), a(), str, realmFieldTypeArr);
    }

    private void a(String str, FieldAttribute[] fieldAttributeArr) {
        if (fieldAttributeArr != null) {
            boolean z = false;
            try {
                if (fieldAttributeArr.length > 0) {
                    if (a(fieldAttributeArr, FieldAttribute.INDEXED)) {
                        addIndex(str);
                        z = true;
                    }
                    if (a(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
                        addPrimaryKey(str);
                    }
                }
            } catch (Exception e) {
                long d = d(str);
                if (z) {
                    this.e.removeSearchIndex(d);
                }
                throw ((RuntimeException) e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(FieldAttribute[] fieldAttributeArr, FieldAttribute fieldAttribute) {
        if (fieldAttributeArr == null || fieldAttributeArr.length == 0) {
            return false;
        }
        for (FieldAttribute fieldAttribute2 : fieldAttributeArr) {
            if (fieldAttribute2 == fieldAttribute) {
                return true;
            }
        }
        return false;
    }

    private void f(String str) {
        b(str);
        g(str);
    }

    private void g(String str) {
        if (this.e.getColumnKey(str) != -1) {
            throw new IllegalArgumentException("Field already exists in '" + getClassName() + "': " + str);
        }
    }

    private void c() {
        if (this.d.configuration.f()) {
            throw new UnsupportedOperationException("'addPrimaryKey' is not supported by synced Realms.");
        }
    }
}
