package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_permissions_PermissionRealmProxy.java */
/* loaded from: classes5.dex */
final class t extends ColumnInfo {
    long a;
    long b;
    long c;
    long d;
    long e;
    long f;
    long g;
    long h;

    t(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new t(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        t tVar = (t) columnInfo;
        t tVar2 = (t) columnInfo2;
        tVar2.a = tVar.a;
        tVar2.b = tVar.b;
        tVar2.c = tVar.c;
        tVar2.d = tVar.d;
        tVar2.e = tVar.e;
        tVar2.f = tVar.f;
        tVar2.g = tVar.g;
        tVar2.h = tVar.h;
    }
}
