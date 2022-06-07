package io.realm;

import io.realm.internal.ColumnIndices;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public abstract class RealmSchema {
    final BaseRealm a;
    private final Map<String, Table> b = new HashMap();
    private final Map<Class<? extends RealmModel>, Table> c = new HashMap();
    private final Map<Class<? extends RealmModel>, RealmObjectSchema> d = new HashMap();
    private final Map<String, RealmObjectSchema> e = new HashMap();
    private final ColumnIndices f;

    public abstract RealmObjectSchema create(String str);

    public abstract RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr);

    @Nullable
    public abstract RealmObjectSchema get(String str);

    public abstract Set<RealmObjectSchema> getAll();

    public abstract void remove(String str);

    public abstract RealmObjectSchema rename(String str, String str2);

    public RealmSchema(BaseRealm baseRealm, @Nullable ColumnIndices columnIndices) {
        this.a = baseRealm;
        this.f = columnIndices;
    }

    public boolean contains(String str) {
        return this.a.c().hasTable(Table.getTableNameForClass(str));
    }

    void a(String str, String str2) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(str2);
        }
    }

    void b(String str, String str2) {
        if (!this.a.c().hasTable(Table.getTableNameForClass(str))) {
            throw new IllegalArgumentException(str2);
        }
    }

    public Table a(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        Table table = this.b.get(tableNameForClass);
        if (table != null) {
            return table;
        }
        Table table2 = this.a.c().getTable(tableNameForClass);
        this.b.put(tableNameForClass, table2);
        return table2;
    }

    public Table a(Class<? extends RealmModel> cls) {
        Table table = this.c.get(cls);
        if (table != null) {
            return table;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (a(originalModelClass, cls)) {
            table = this.c.get(originalModelClass);
        }
        if (table == null) {
            table = this.a.c().getTable(Table.getTableNameForClass(this.a.getConfiguration().getSchemaMediator().getSimpleClassName(originalModelClass)));
            this.c.put(originalModelClass, table);
        }
        if (a(originalModelClass, cls)) {
            this.c.put(cls, table);
        }
        return table;
    }

    public RealmObjectSchema b(Class<? extends RealmModel> cls) {
        RealmObjectSchema realmObjectSchema = this.d.get(cls);
        if (realmObjectSchema != null) {
            return realmObjectSchema;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (a(originalModelClass, cls)) {
            realmObjectSchema = this.d.get(originalModelClass);
        }
        if (realmObjectSchema == null) {
            f fVar = new f(this.a, this, a(cls), c(originalModelClass));
            this.d.put(originalModelClass, fVar);
            realmObjectSchema = fVar;
        }
        if (a(originalModelClass, cls)) {
            this.d.put(cls, realmObjectSchema);
        }
        return realmObjectSchema;
    }

    public RealmObjectSchema b(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        RealmObjectSchema realmObjectSchema = this.e.get(tableNameForClass);
        if (realmObjectSchema != null && realmObjectSchema.a().isValid() && realmObjectSchema.getClassName().equals(str)) {
            return realmObjectSchema;
        }
        if (this.a.c().hasTable(tableNameForClass)) {
            BaseRealm baseRealm = this.a;
            f fVar = new f(baseRealm, this, baseRealm.c().getTable(tableNameForClass));
            this.e.put(tableNameForClass, fVar);
            return fVar;
        }
        throw new IllegalArgumentException("The class " + str + " doesn't exist in this Realm.");
    }

    private boolean a(Class<? extends RealmModel> cls, Class<? extends RealmModel> cls2) {
        return cls.equals(cls2);
    }

    public final boolean a() {
        return this.f != null;
    }

    public final ColumnInfo c(Class<? extends RealmModel> cls) {
        c();
        return this.f.getColumnInfo(cls);
    }

    public final ColumnInfo getColumnInfo(String str) {
        c();
        return this.f.getColumnInfo(str);
    }

    final void a(String str, RealmObjectSchema realmObjectSchema) {
        this.e.put(str, realmObjectSchema);
    }

    final RealmObjectSchema c(String str) {
        return this.e.remove(str);
    }

    private void c() {
        if (!a()) {
            throw new IllegalStateException("Attempt to use column key before set.");
        }
    }

    public void b() {
        ColumnIndices columnIndices = this.f;
        if (columnIndices != null) {
            columnIndices.refresh();
        }
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.e.clear();
    }
}
