package com.xiaomi.micolauncher.api.cache;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs implements RealmMigration {
    public static final /* synthetic */ $$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs INSTANCE = new $$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs();

    private /* synthetic */ $$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs() {
    }

    @Override // io.realm.RealmMigration
    public final void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        ApiRealmHelper.a(dynamicRealm, j, j2);
    }
}
