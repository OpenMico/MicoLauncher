package io.realm;

import io.realm.internal.ColumnInfo;

/* compiled from: io_realm_sync_SubscriptionRealmProxy.java */
/* loaded from: classes5.dex */
final class q extends ColumnInfo {
    long a;
    long b;
    long c;
    long d;
    long e;
    long f;
    long g;
    long h;
    long i;
    long j;

    q(ColumnInfo columnInfo, boolean z) {
        super(columnInfo, z);
        copy(columnInfo, this);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final ColumnInfo copy(boolean z) {
        return new q(this, z);
    }

    @Override // io.realm.internal.ColumnInfo
    protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
        q qVar = (q) columnInfo;
        q qVar2 = (q) columnInfo2;
        qVar2.a = qVar.a;
        qVar2.b = qVar.b;
        qVar2.c = qVar.c;
        qVar2.d = qVar.d;
        qVar2.e = qVar.e;
        qVar2.f = qVar.f;
        qVar2.g = qVar.g;
        qVar2.h = qVar.h;
        qVar2.i = qVar.i;
        qVar2.j = qVar.j;
    }
}
