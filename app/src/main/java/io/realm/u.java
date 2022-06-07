package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_permissions_PermissionUserRealmProxy.java */
/* loaded from: classes5.dex */
final class u extends ColumnInfo {
    long a;
    long b;

    u(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new u(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        u uVar = (u) columnInfo;
        u uVar2 = (u) columnInfo2;
        uVar2.a = uVar.a;
        uVar2.b = uVar.b;
    }
}
