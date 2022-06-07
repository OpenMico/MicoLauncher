package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_permissions_RoleRealmProxy.java */
/* loaded from: classes5.dex */
final class w extends ColumnInfo {
    long a;
    long b;

    w(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new w(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        w wVar = (w) columnInfo;
        w wVar2 = (w) columnInfo2;
        wVar2.a = wVar.a;
        wVar2.b = wVar.b;
    }
}
