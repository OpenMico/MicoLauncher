package com.xiaomi.micolauncher.skills.voip.model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8 implements RealmMigration {
    public static final /* synthetic */ $$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8 INSTANCE = new $$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8();

    private /* synthetic */ $$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8() {
    }

    @Override // io.realm.RealmMigration
    public final void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        CallBlackListRealmHelper.a(dynamicRealm, j, j2);
    }
}
