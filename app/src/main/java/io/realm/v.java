package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_permissions_RealmPermissionsRealmProxy.java */
/* loaded from: classes5.dex */
final class v extends ColumnInfo {
    long a;
    long b;

    v(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new v(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        v vVar = (v) columnInfo;
        v vVar2 = (v) columnInfo2;
        vVar2.a = vVar.a;
        vVar2.b = vVar.b;
    }
}
