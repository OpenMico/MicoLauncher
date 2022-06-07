package com.xiaomi.micolauncher.skills.alarm.model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklX-iths  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklXiths implements RealmMigration {
    public static final /* synthetic */ $$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklXiths INSTANCE = new $$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklXiths();

    private /* synthetic */ $$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklXiths() {
    }

    @Override // io.realm.RealmMigration
    public final void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        HourlyChimeRealmHelper.a(dynamicRealm, j, j2);
    }
}
