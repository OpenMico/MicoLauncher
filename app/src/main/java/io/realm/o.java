package io.realm;

import io.realm.internal.ColumnInfo;
import io.realm.internal.fields.FieldDescriptor;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SchemaConnector.java */
/* loaded from: classes5.dex */
public class o implements FieldDescriptor.SchemaProxy {
    private final RealmSchema a;

    public o(RealmSchema realmSchema) {
        this.a = realmSchema;
    }

    @Override // io.realm.internal.fields.FieldDescriptor.SchemaProxy
    public boolean hasCache() {
        return this.a.a();
    }

    @Override // io.realm.internal.fields.FieldDescriptor.SchemaProxy
    public ColumnInfo getColumnInfo(String str) {
        return this.a.getColumnInfo(str);
    }

    @Override // io.realm.internal.fields.FieldDescriptor.SchemaProxy
    public long getNativeTablePtr(String str) {
        return this.a.a(str).getNativePtr();
    }
}
