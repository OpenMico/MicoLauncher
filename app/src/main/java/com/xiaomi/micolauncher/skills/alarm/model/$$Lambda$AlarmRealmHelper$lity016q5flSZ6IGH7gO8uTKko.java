package com.xiaomi.micolauncher.skills.alarm.model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmRealmHelper$lit-y016q5flSZ6IGH7gO8uTKko  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AlarmRealmHelper$lity016q5flSZ6IGH7gO8uTKko implements RealmMigration {
    public static final /* synthetic */ $$Lambda$AlarmRealmHelper$lity016q5flSZ6IGH7gO8uTKko INSTANCE = new $$Lambda$AlarmRealmHelper$lity016q5flSZ6IGH7gO8uTKko();

    private /* synthetic */ $$Lambda$AlarmRealmHelper$lity016q5flSZ6IGH7gO8uTKko() {
    }

    @Override // io.realm.RealmMigration
    public final void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        AlarmRealmHelper.a(dynamicRealm, j, j2);
    }
}
