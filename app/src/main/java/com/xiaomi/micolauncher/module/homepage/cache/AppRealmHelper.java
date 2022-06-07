package com.xiaomi.micolauncher.module.homepage.cache;

import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class AppRealmHelper {
    private final RealmConfiguration a;

    private AppRealmHelper() {
        this.a = new RealmConfiguration.Builder().name("mico_launcher_app.realm").modules(new AppRealmModule(), new Object[0]).build();
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final AppRealmHelper a = new AppRealmHelper();
    }

    public static AppRealmHelper getInstance() {
        return a.a;
    }

    public void insert(AppInfo appInfo) {
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            instance.beginTransaction();
            instance.insertOrUpdate(new AppRealmObject(appInfo, instance.where(AppRealmObject.class).findAll().size()));
            instance.commitTransaction();
            if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    public void delete(final AppInfo appInfo) {
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            instance.executeTransactionAsync(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.module.homepage.cache.-$$Lambda$AppRealmHelper$TQrkgH9sHbI1ONJoM_H8QIEX_L0
                @Override // io.realm.Realm.Transaction
                public final void execute(Realm realm) {
                    AppRealmHelper.a(AppInfo.this, realm);
                }
            });
            if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    public static /* synthetic */ void a(AppInfo appInfo, Realm realm) {
        AppRealmObject appRealmObject = (AppRealmObject) realm.where(AppRealmObject.class).equalTo("appName", appInfo.getAppName()).findFirst();
        if (appRealmObject != null) {
            appRealmObject.deleteFromRealm();
        }
    }

    public List<AppInfo> queryAllAppInfos() {
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            RealmResults sort = instance.where(AppRealmObject.class).findAll().sort("position", Sort.ASCENDING);
            if (ContainerUtil.isEmpty(sort)) {
                if (instance != null) {
                    instance.close();
                }
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = sort.iterator();
            while (it.hasNext()) {
                arrayList.add(((AppRealmObject) it.next()).convert2AppInfo());
            }
            if (instance != null) {
                instance.close();
            }
            return arrayList;
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    public void insert(List<AppInfo> list) {
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            instance.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                instance.insertOrUpdate(new AppRealmObject(list.get(i), i));
            }
            instance.commitTransaction();
            if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }

    public void update(List<AppInfo> list) {
        Realm instance = Realm.getInstance(this.a);
        Throwable th = null;
        try {
            instance.beginTransaction();
            RealmResults findAll = instance.where(AppRealmObject.class).findAll();
            if (!ContainerUtil.isEmpty(findAll)) {
                findAll.deleteAllFromRealm();
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(list);
            for (int i = 0; i < arrayList2.size(); i++) {
                arrayList.add(new AppRealmObject((AppInfo) arrayList2.get(i), i));
            }
            instance.insertOrUpdate(arrayList);
            instance.commitTransaction();
            if (instance != null) {
                instance.close();
            }
        } catch (Throwable th2) {
            if (instance != null) {
                if (0 != 0) {
                    try {
                        instance.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    instance.close();
                }
            }
            throw th2;
        }
    }
}
