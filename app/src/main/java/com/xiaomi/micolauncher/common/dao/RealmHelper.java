package com.xiaomi.micolauncher.common.dao;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.exceptions.RealmMigrationNeededException;
import java.io.FileNotFoundException;

/* loaded from: classes3.dex */
public class RealmHelper {
    public static Realm getInstance(RealmConfiguration realmConfiguration) {
        try {
            try {
                return Realm.getInstance(realmConfiguration);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } catch (RealmMigrationNeededException unused) {
            RealmMigration migration = realmConfiguration.getMigration();
            if (migration != null) {
                Realm.migrateRealm(realmConfiguration, migration);
            } else {
                Realm.migrateRealm(realmConfiguration, new RealmMigration() { // from class: com.xiaomi.micolauncher.common.dao.RealmHelper.1
                    @Override // io.realm.RealmMigration
                    public void migrate(DynamicRealm dynamicRealm, long j, long j2) {
                    }
                });
            }
            return null;
        }
    }
}
