package io.realm;

import io.realm.RealmObjectSchema;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/* compiled from: MutableRealmSchema.java */
/* loaded from: classes5.dex */
class k extends RealmSchema {
    /* JADX INFO: Access modifiers changed from: package-private */
    public k(BaseRealm baseRealm) {
        super(baseRealm, null);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema get(String str) {
        a(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!this.a.c().hasTable(tableNameForClass)) {
            return null;
        }
        return new j(this.a, this, this.a.c().getTable(tableNameForClass));
    }

    @Override // io.realm.RealmSchema
    public Set<RealmObjectSchema> getAll() {
        String[] tablesNames = this.a.c().getTablesNames();
        int length = tablesNames.length;
        LinkedHashSet linkedHashSet = new LinkedHashSet(length);
        for (String str : tablesNames) {
            RealmObjectSchema realmObjectSchema = get(Table.getClassNameForTable(str));
            if (realmObjectSchema != null) {
                linkedHashSet.add(realmObjectSchema);
            }
        }
        return linkedHashSet;
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema create(String str) {
        a(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (str.length() <= Table.CLASS_NAME_MAX_LENGTH) {
            return new j(this.a, this, this.a.c().createTable(tableNameForClass));
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), Integer.valueOf(str.length())));
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        a(str, "Null or empty class names are not allowed");
        RealmObjectSchema.b(str2);
        String d = d(str);
        RealmObjectSchema.b bVar = RealmObjectSchema.b().get(cls);
        boolean z = true;
        if (bVar == null || !(bVar.a == RealmFieldType.STRING || bVar.a == RealmFieldType.INTEGER)) {
            throw new IllegalArgumentException(String.format("Realm doesn't support primary key field type '%s'.", cls));
        }
        if (bVar.a != RealmFieldType.STRING) {
            z = false;
        }
        boolean z2 = bVar.c;
        if (j.a(fieldAttributeArr, FieldAttribute.REQUIRED)) {
            z2 = false;
        }
        return new j(this.a, this, this.a.c().createTableWithPrimaryKey(d, str2, z, z2));
    }

    @Override // io.realm.RealmSchema
    public void remove(String str) {
        this.a.a();
        a(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (OsObjectStore.deleteTableForObject(this.a.c(), str)) {
            c(tableNameForClass);
            return;
        }
        throw new IllegalArgumentException("Cannot remove class because it is not in this Realm: " + str);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema rename(String str, String str2) {
        this.a.a();
        a(str, "Class names cannot be empty or null");
        a(str2, "Class names cannot be empty or null");
        String tableNameForClass = Table.getTableNameForClass(str);
        String tableNameForClass2 = Table.getTableNameForClass(str2);
        b(str, "Cannot rename class because it doesn't exist in this Realm: " + str);
        if (!this.a.c().hasTable(tableNameForClass2)) {
            this.a.c().renameTable(tableNameForClass, tableNameForClass2);
            Table table = this.a.c().getTable(tableNameForClass2);
            RealmObjectSchema c = c(tableNameForClass);
            if (c == null || !c.a().isValid() || !c.getClassName().equals(str2)) {
                c = new j(this.a, this, table);
            }
            a(tableNameForClass2, c);
            return c;
        }
        throw new IllegalArgumentException(str + " cannot be renamed because the new class already exists: " + str2);
    }

    private String d(String str) {
        if (str.length() <= Table.CLASS_NAME_MAX_LENGTH) {
            return Table.getTableNameForClass(str);
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), Integer.valueOf(str.length())));
    }
}
