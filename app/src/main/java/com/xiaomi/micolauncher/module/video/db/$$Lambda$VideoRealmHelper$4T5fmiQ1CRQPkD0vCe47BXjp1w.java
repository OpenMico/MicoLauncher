package com.xiaomi.micolauncher.module.video.db;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.video.db.-$$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47-BXjp1w  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47BXjp1w implements RealmMigration {
    public static final /* synthetic */ $$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47BXjp1w INSTANCE = new $$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47BXjp1w();

    private /* synthetic */ $$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47BXjp1w() {
    }

    @Override // io.realm.RealmMigration
    public final void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        VideoRealmHelper.a(dynamicRealm, j, j2);
    }
}
