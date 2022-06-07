package com.xiaomi.micolauncher.skills.voip.model;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import java.io.Closeable;
import java.util.List;

/* loaded from: classes3.dex */
public class CallBlackListRealmHelper implements Closeable {
    private RealmMigration b = $$Lambda$CallBlackListRealmHelper$cI1ZiBq7LB5AbDKvuCrSUuD7WQ8.INSTANCE;
    private Realm a = Realm.getInstance(new RealmConfiguration.Builder().name("blacklist.realm").schemaVersion(2).modules(new CallBlackListModule(), new Object[0]).migration(this.b).build());

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DynamicRealm dynamicRealm, long j, long j2) {
        RealmSchema schema = dynamicRealm.getSchema();
        if (j == 1 && j2 == 2) {
            schema.get(CallBlackListRealmObject.class.getSimpleName()).removeField("id").addPrimaryKey("blackListNum");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        try {
            this.a.beginTransaction();
            this.a.deleteAll();
            this.a.commitTransaction();
            this.a.close();
            L.micoVoip.i("delete call black list realm");
            Realm.deleteRealm(this.a.getConfiguration());
        } catch (RealmMigrationNeededException | IllegalStateException e) {
            e.printStackTrace();
            L.micoVoip.e("deleteCallBlackListDb exception:%s", e);
            this.a.cancelTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<CallBlackListRealmObject> b() {
        RealmResults findAll = this.a.where(CallBlackListRealmObject.class).findAll();
        return ContainerUtil.isEmpty(findAll) ? findAll : this.a.copyFromRealm(findAll);
    }

    public Realm getRealm() {
        return this.a;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        L.voip.i("CallBlackListRealmHelper close");
        Realm realm = this.a;
        if (realm != null) {
            realm.close();
        }
    }
}
