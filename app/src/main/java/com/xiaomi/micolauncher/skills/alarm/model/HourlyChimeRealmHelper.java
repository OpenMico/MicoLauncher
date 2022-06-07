package com.xiaomi.micolauncher.skills.alarm.model;

import androidx.annotation.NonNull;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.DataBaseLogHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy;
import java.util.List;

/* loaded from: classes3.dex */
public class HourlyChimeRealmHelper {
    public static final String DB_NAME = "hourlyChime.realm";
    public static final String ID = "id";
    private Realm a;
    private RealmMigration b = $$Lambda$HourlyChimeRealmHelper$XhsxoudyRsilBnngjFFklXiths.INSTANCE;

    public static /* synthetic */ void a(DynamicRealm dynamicRealm, long j, long j2) {
        RealmSchema schema = dynamicRealm.getSchema();
        if (j == 0) {
            schema.get(com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("content", String.class, new FieldAttribute[0]).addField("status", Boolean.class, new FieldAttribute[0]).addField("position", Integer.class, new FieldAttribute[0]);
        }
    }

    public HourlyChimeRealmHelper() {
        try {
            this.a = Realm.getInstance(new RealmConfiguration.Builder().name(DB_NAME).schemaVersion(1L).migration(this.b).modules(new HourlyChimeModule(), new Object[0]).build());
        } catch (Throwable th) {
            L.database.e("%s constructor HourlyChimeRealmHelper() catched the throwable %s", "[HourlyChimeRealmHelper]:", th);
        }
    }

    public void addHourse(final HourlyChimeObject hourlyChimeObject) {
        if (this.a != null) {
            DataBaseLogHelper.printProcess();
            this.a.executeTransactionAsync(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeRealmHelper$GBg7-KqM3Uj1NRzflF94_UI20bA
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    HourlyChimeRealmHelper.b(HourlyChimeObject.this, realm);
                }
            });
        }
    }

    public static /* synthetic */ void b(HourlyChimeObject hourlyChimeObject, Realm realm) {
        HourlyChimeObject hourlyChimeObject2 = (HourlyChimeObject) realm.copyToRealmOrUpdate((Realm) hourlyChimeObject, new ImportFlag[0]);
    }

    public void updateHours(@NonNull final HourlyChimeObject hourlyChimeObject) {
        L.hourlychime.d("HourlyChimeRealmHelper updateHours hourlyChimeObject :%s", hourlyChimeObject);
        if (this.a != null) {
            DataBaseLogHelper.printProcess();
            this.a.executeTransactionAsync(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeRealmHelper$EKXqA8t-3Z3UFZ_oed1IGJ-5ZwU
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    HourlyChimeRealmHelper.a(HourlyChimeObject.this, realm);
                }
            });
        }
    }

    public static /* synthetic */ void a(@NonNull HourlyChimeObject hourlyChimeObject, Realm realm) {
        HourlyChimeObject hourlyChimeObject2 = (HourlyChimeObject) realm.copyToRealmOrUpdate((Realm) hourlyChimeObject, new ImportFlag[0]);
    }

    public List<HourlyChimeObject> queryAllHours() {
        if (this.a == null) {
            return null;
        }
        DataBaseLogHelper.printProcess();
        RealmResults findAll = this.a.where(HourlyChimeObject.class).findAll();
        if (ContainerUtil.isEmpty(findAll)) {
            return findAll;
        }
        return this.a.copyFromRealm(findAll.sort("id"));
    }

    public List<HourlyChimeObject> querySetHourlyChime(boolean z) {
        L.hourlychime.d("HourlyChimeRealmHelper querySetHourlyChime");
        if (this.a == null) {
            return null;
        }
        DataBaseLogHelper.printProcess();
        RealmResults findAll = this.a.where(HourlyChimeObject.class).equalTo("status", Boolean.valueOf(z)).findAll();
        if (ContainerUtil.isEmpty(findAll)) {
            return findAll;
        }
        return this.a.copyFromRealm(findAll.sort("id"));
    }

    public int queryAllHourseNum() {
        Realm realm = this.a;
        if (realm == null) {
            return 0;
        }
        RealmResults findAll = realm.where(HourlyChimeObject.class).findAll();
        if (!ContainerUtil.isEmpty(findAll)) {
            return findAll.size();
        }
        return 0;
    }

    public void close() {
        Realm realm = this.a;
        if (realm != null) {
            try {
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
