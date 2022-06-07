package io.realm;

import io.realm.internal.ColumnIndices;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: ImmutableRealmSchema.java */
/* loaded from: classes5.dex */
class g extends RealmSchema {
    /* JADX INFO: Access modifiers changed from: package-private */
    public g(BaseRealm baseRealm, ColumnIndices columnIndices) {
        super(baseRealm, columnIndices);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema get(String str) {
        a(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!this.a.c().hasTable(tableNameForClass)) {
            return null;
        }
        return new f(this.a, this, this.a.c().getTable(tableNameForClass), getColumnInfo(str));
    }

    @Override // io.realm.RealmSchema
    public Set<RealmObjectSchema> getAll() {
        RealmProxyMediator schemaMediator = this.a.getConfiguration().getSchemaMediator();
        Set<Class<? extends RealmModel>> modelClasses = schemaMediator.getModelClasses();
        LinkedHashSet linkedHashSet = new LinkedHashSet(modelClasses.size());
        for (Class<? extends RealmModel> cls : modelClasses) {
            linkedHashSet.add(get(schemaMediator.getSimpleClassName(cls)));
        }
        return linkedHashSet;
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema create(String str) {
        throw new UnsupportedOperationException("This 'RealmSchema' is immutable. Please use 'DynamicRealm.getSchema() to get a mutable instance.");
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        throw new UnsupportedOperationException("This 'RealmSchema' is immutable. Please use 'DynamicRealm.getSchema() to get a mutable instance.");
    }

    @Override // io.realm.RealmSchema
    public void remove(String str) {
        throw new UnsupportedOperationException("This 'RealmSchema' is immutable. Please use 'DynamicRealm.getSchema() to get a mutable instance.");
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema rename(String str, String str2) {
        throw new UnsupportedOperationException("This 'RealmSchema' is immutable. Please use 'DynamicRealm.getSchema() to get a mutable instance.");
    }
}
