package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_permissions_ClassPermissionsRealmProxy.java */
/* loaded from: classes5.dex */
final class s extends ColumnInfo {
    long a;
    long b;

    s(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new s(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        s sVar = (s) columnInfo;
        s sVar2 = (s) columnInfo2;
        sVar2.a = sVar.a;
        sVar2.b = sVar.b;
    }
}
