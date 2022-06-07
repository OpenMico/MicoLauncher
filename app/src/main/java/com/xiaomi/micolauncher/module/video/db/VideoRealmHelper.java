package com.xiaomi.micolauncher.module.video.db;

import android.text.TextUtils;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.common.DataBaseLogHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy;

/* loaded from: classes3.dex */
public class VideoRealmHelper {
    private RealmConfiguration a;
    private RealmMigration b;

    private VideoRealmHelper() {
        this.b = $$Lambda$VideoRealmHelper$4T5fmiQ1CRQPkD0vCe47BXjp1w.INSTANCE;
        this.a = new RealmConfiguration.Builder().name("mico_launcher_video.realm").schemaVersion(3L).modules(new VideoRealmModule(), new Object[0]).migration(this.b).build();
    }

    public static /* synthetic */ void a(DynamicRealm dynamicRealm, long j, long j2) {
        L.course.i("oldversion %d  newversion  %d", Long.valueOf(j), Long.valueOf(j2));
        RealmSchema schema = dynamicRealm.getSchema();
        if (j == 0 && j2 == 1) {
            schema.get(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("metaId", String.class, new FieldAttribute[0]);
            j++;
        }
        if (j == 1 && j2 == 2) {
            schema.get(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("ci", Integer.TYPE, new FieldAttribute[0]);
            j++;
        }
        if (j == 2 && j2 == 3) {
            schema.get(com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("pCode", String.class, new FieldAttribute[0]);
        }
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static VideoRealmHelper a = new VideoRealmHelper();
    }

    public static VideoRealmHelper getInstance() {
        return a.a;
    }

    public void insert(VideoItem videoItem) {
        DataBaseLogHelper.printProcess();
        if (TextUtils.isEmpty(videoItem.getCp())) {
            L.base.d("insert cp is empty");
        } else if ("mitv".equals(videoItem.getCp()) || "LONG".equals(videoItem.lengthType)) {
            try {
                Realm instance = Realm.getInstance(this.a);
                try {
                    instance.beginTransaction();
                    VideoRealmObject videoRealmObject = new VideoRealmObject(videoItem);
                    videoRealmObject.setLastUpdateTime(System.currentTimeMillis());
                    instance.insertOrUpdate(videoRealmObject);
                    if (instance.where(VideoRealmObject.class).count() > 50) {
                        ((VideoRealmObject) instance.where(VideoRealmObject.class).findAll().sort("lastUpdateTime").first()).deleteFromRealm();
                    }
                    instance.commitTransaction();
                } catch (Exception e) {
                    XLog.e(e);
                }
                if (instance != null) {
                    instance.close();
                }
            } catch (Throwable th) {
                L.database.e("%s insert catch the throwable %s", "[VideoRealmHelper]:", th);
            }
        } else {
            L.base.d("insert video is short");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.xiaomi.micolauncher.skills.video.model.VideoItem> findAll(java.lang.String r11) {
        /*
            r10 = this;
            com.xiaomi.micolauncher.common.DataBaseLogHelper.printProcess()
            r0 = 0
            r1 = 0
            io.realm.RealmConfiguration r2 = r10.a     // Catch: Throwable -> 0x00bb
            io.realm.Realm r2 = io.realm.Realm.getInstance(r2)     // Catch: Throwable -> 0x00bb
            java.lang.Class<com.xiaomi.micolauncher.module.video.db.VideoRealmObject> r3 = com.xiaomi.micolauncher.module.video.db.VideoRealmObject.class
            io.realm.RealmQuery r3 = r2.where(r3)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            io.realm.RealmResults r3 = r3.findAll()     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.lang.String r4 = "lastUpdateTime"
            io.realm.Sort r5 = io.realm.Sort.DESCENDING     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            io.realm.RealmResults r3 = r3.sort(r4, r5)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r4.<init>()     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r5 = r0
        L_0x0023:
            int r6 = r3.size()     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r5 >= r6) goto L_0x0090
            java.lang.Object r6 = r3.get(r5)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            com.xiaomi.micolauncher.module.video.db.VideoRealmObject r6 = (com.xiaomi.micolauncher.module.video.db.VideoRealmObject) r6     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.lang.String r7 = r6.getCpType()     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.lang.String r8 = "bilibili"
            boolean r8 = r8.equals(r11)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 == 0) goto L_0x004c
            java.lang.String r8 = "bilibili"
            boolean r8 = r8.equals(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 == 0) goto L_0x004c
            com.xiaomi.micolauncher.skills.video.model.VideoItem r7 = new com.xiaomi.micolauncher.skills.video.model.VideoItem     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r7.<init>(r6)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r4.add(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            goto L_0x008d
        L_0x004c:
            java.lang.String r8 = "iqiyi"
            boolean r8 = r8.equals(r11)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 == 0) goto L_0x0070
            java.lang.String r8 = "iqiyi"
            boolean r8 = r8.equals(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 != 0) goto L_0x0062
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 == 0) goto L_0x0070
        L_0x0062:
            com.xiaomi.micolauncher.skills.video.model.VideoItem r7 = new com.xiaomi.micolauncher.skills.video.model.VideoItem     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r7.<init>(r6)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.lang.String r6 = "iqiyi"
            r7.setCp(r6)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r4.add(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            goto L_0x008d
        L_0x0070:
            java.lang.String r8 = "mitv"
            boolean r8 = r8.equals(r11)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r8 == 0) goto L_0x008d
            java.lang.String r8 = "mitv"
            boolean r7 = r8.equals(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            if (r7 == 0) goto L_0x008d
            com.xiaomi.micolauncher.skills.video.model.VideoItem r7 = new com.xiaomi.micolauncher.skills.video.model.VideoItem     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r7.<init>(r6)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            java.lang.String r6 = "mitv"
            r7.setCp(r6)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
            r4.add(r7)     // Catch: Exception -> 0x009b, Throwable -> 0x0099, all -> 0x0096
        L_0x008d:
            int r5 = r5 + 1
            goto L_0x0023
        L_0x0090:
            if (r2 == 0) goto L_0x0095
            r2.close()     // Catch: Throwable -> 0x00bb
        L_0x0095:
            return r4
        L_0x0096:
            r11 = move-exception
            r3 = r1
            goto L_0x00aa
        L_0x0099:
            r11 = move-exception
            goto L_0x00a5
        L_0x009b:
            r11 = move-exception
            com.elvishew.xlog.XLog.e(r11)     // Catch: Throwable -> 0x0099, all -> 0x0096
            if (r2 == 0) goto L_0x00a4
            r2.close()     // Catch: Throwable -> 0x00bb
        L_0x00a4:
            return r1
        L_0x00a5:
            throw r11     // Catch: all -> 0x00a6
        L_0x00a6:
            r3 = move-exception
            r9 = r3
            r3 = r11
            r11 = r9
        L_0x00aa:
            if (r2 == 0) goto L_0x00ba
            if (r3 == 0) goto L_0x00b7
            r2.close()     // Catch: Throwable -> 0x00b2
            goto L_0x00ba
        L_0x00b2:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch: Throwable -> 0x00bb
            goto L_0x00ba
        L_0x00b7:
            r2.close()     // Catch: Throwable -> 0x00bb
        L_0x00ba:
            throw r11     // Catch: Throwable -> 0x00bb
        L_0x00bb:
            r11 = move-exception
            com.elvishew.xlog.Logger r2 = com.xiaomi.micolauncher.common.L.database
            java.lang.String r3 = "%s findAll() catch the throwable %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r5 = "[VideoRealmHelper]:"
            r4[r0] = r5
            r0 = 1
            r4[r0] = r11
            r2.e(r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.video.db.VideoRealmHelper.findAll(java.lang.String):java.util.List");
    }

    public VideoItem findVideoItem(String str, String str2) {
        DataBaseLogHelper.printProcess();
        for (VideoItem videoItem : findAll(str)) {
            if (videoItem.getMediaId().equals(str2)) {
                return videoItem;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.xiaomi.micolauncher.skills.video.model.VideoItem> findInSevenDays() {
        /*
            r8 = this;
            com.xiaomi.micolauncher.common.DataBaseLogHelper.printProcess()
            r0 = 0
            io.realm.RealmConfiguration r1 = r8.a     // Catch: Throwable -> 0x0064
            io.realm.Realm r1 = io.realm.Realm.getInstance(r1)     // Catch: Throwable -> 0x0064
            java.lang.Class<com.xiaomi.micolauncher.module.video.db.VideoRealmObject> r2 = com.xiaomi.micolauncher.module.video.db.VideoRealmObject.class
            io.realm.RealmQuery r2 = r1.where(r2)     // Catch: Throwable -> 0x004d, all -> 0x004a
            java.lang.String r3 = "lastUpdateTime"
            long r4 = com.xiaomi.micolauncher.module.video.childmode.ChildModeRecentPlayHelper.getEarliestTime()     // Catch: Throwable -> 0x004d, all -> 0x004a
            io.realm.RealmQuery r2 = r2.greaterThan(r3, r4)     // Catch: Throwable -> 0x004d, all -> 0x004a
            io.realm.RealmResults r2 = r2.findAll()     // Catch: Throwable -> 0x004d, all -> 0x004a
            java.lang.String r3 = "lastUpdateTime"
            io.realm.Sort r4 = io.realm.Sort.DESCENDING     // Catch: Throwable -> 0x004d, all -> 0x004a
            io.realm.RealmResults r2 = r2.sort(r3, r4)     // Catch: Throwable -> 0x004d, all -> 0x004a
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: Throwable -> 0x004d, all -> 0x004a
            r3.<init>()     // Catch: Throwable -> 0x004d, all -> 0x004a
            java.util.Iterator r2 = r2.iterator()     // Catch: Throwable -> 0x004d, all -> 0x004a
        L_0x002f:
            boolean r4 = r2.hasNext()     // Catch: Throwable -> 0x004d, all -> 0x004a
            if (r4 == 0) goto L_0x0044
            java.lang.Object r4 = r2.next()     // Catch: Throwable -> 0x004d, all -> 0x004a
            com.xiaomi.micolauncher.module.video.db.VideoRealmObject r4 = (com.xiaomi.micolauncher.module.video.db.VideoRealmObject) r4     // Catch: Throwable -> 0x004d, all -> 0x004a
            com.xiaomi.micolauncher.skills.video.model.VideoItem r5 = new com.xiaomi.micolauncher.skills.video.model.VideoItem     // Catch: Throwable -> 0x004d, all -> 0x004a
            r5.<init>(r4)     // Catch: Throwable -> 0x004d, all -> 0x004a
            r3.add(r5)     // Catch: Throwable -> 0x004d, all -> 0x004a
            goto L_0x002f
        L_0x0044:
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch: Throwable -> 0x0064
        L_0x0049:
            return r3
        L_0x004a:
            r2 = move-exception
            r3 = r0
            goto L_0x0053
        L_0x004d:
            r2 = move-exception
            throw r2     // Catch: all -> 0x004f
        L_0x004f:
            r3 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L_0x0053:
            if (r1 == 0) goto L_0x0063
            if (r3 == 0) goto L_0x0060
            r1.close()     // Catch: Throwable -> 0x005b
            goto L_0x0063
        L_0x005b:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch: Throwable -> 0x0064
            goto L_0x0063
        L_0x0060:
            r1.close()     // Catch: Throwable -> 0x0064
        L_0x0063:
            throw r2     // Catch: Throwable -> 0x0064
        L_0x0064:
            r1 = move-exception
            com.elvishew.xlog.Logger r2 = com.xiaomi.micolauncher.common.L.database
            java.lang.String r3 = "%s findInSevenDays() catch the throwable %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 0
            java.lang.String r6 = "[VideoRealmHelper]:"
            r4[r5] = r6
            r5 = 1
            r4[r5] = r1
            r2.e(r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.video.db.VideoRealmHelper.findInSevenDays():java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.xiaomi.micolauncher.skills.video.model.VideoItem> findAllVideoItems() {
        /*
            r8 = this;
            r0 = 0
            io.realm.RealmConfiguration r1 = r8.a     // Catch: Throwable -> 0x0057
            io.realm.Realm r1 = io.realm.Realm.getInstance(r1)     // Catch: Throwable -> 0x0057
            java.lang.Class<com.xiaomi.micolauncher.module.video.db.VideoRealmObject> r2 = com.xiaomi.micolauncher.module.video.db.VideoRealmObject.class
            io.realm.RealmQuery r2 = r1.where(r2)     // Catch: Throwable -> 0x0040, all -> 0x003d
            io.realm.RealmResults r2 = r2.findAll()     // Catch: Throwable -> 0x0040, all -> 0x003d
            java.lang.String r3 = "lastUpdateTime"
            io.realm.Sort r4 = io.realm.Sort.DESCENDING     // Catch: Throwable -> 0x0040, all -> 0x003d
            io.realm.RealmResults r2 = r2.sort(r3, r4)     // Catch: Throwable -> 0x0040, all -> 0x003d
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: Throwable -> 0x0040, all -> 0x003d
            r3.<init>()     // Catch: Throwable -> 0x0040, all -> 0x003d
            java.util.Iterator r2 = r2.iterator()     // Catch: Throwable -> 0x0040, all -> 0x003d
        L_0x0022:
            boolean r4 = r2.hasNext()     // Catch: Throwable -> 0x0040, all -> 0x003d
            if (r4 == 0) goto L_0x0037
            java.lang.Object r4 = r2.next()     // Catch: Throwable -> 0x0040, all -> 0x003d
            com.xiaomi.micolauncher.module.video.db.VideoRealmObject r4 = (com.xiaomi.micolauncher.module.video.db.VideoRealmObject) r4     // Catch: Throwable -> 0x0040, all -> 0x003d
            com.xiaomi.micolauncher.skills.video.model.VideoItem r5 = new com.xiaomi.micolauncher.skills.video.model.VideoItem     // Catch: Throwable -> 0x0040, all -> 0x003d
            r5.<init>(r4)     // Catch: Throwable -> 0x0040, all -> 0x003d
            r3.add(r5)     // Catch: Throwable -> 0x0040, all -> 0x003d
            goto L_0x0022
        L_0x0037:
            if (r1 == 0) goto L_0x003c
            r1.close()     // Catch: Throwable -> 0x0057
        L_0x003c:
            return r3
        L_0x003d:
            r2 = move-exception
            r3 = r0
            goto L_0x0046
        L_0x0040:
            r2 = move-exception
            throw r2     // Catch: all -> 0x0042
        L_0x0042:
            r3 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L_0x0046:
            if (r1 == 0) goto L_0x0056
            if (r3 == 0) goto L_0x0053
            r1.close()     // Catch: Throwable -> 0x004e
            goto L_0x0056
        L_0x004e:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch: Throwable -> 0x0057
            goto L_0x0056
        L_0x0053:
            r1.close()     // Catch: Throwable -> 0x0057
        L_0x0056:
            throw r2     // Catch: Throwable -> 0x0057
        L_0x0057:
            r1 = move-exception
            com.elvishew.xlog.Logger r2 = com.xiaomi.micolauncher.common.L.database
            java.lang.String r3 = "%s findAll() catch the throwable %s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r5 = 0
            java.lang.String r6 = "[VideoRealmHelper]:"
            r4[r5] = r6
            r5 = 1
            r4[r5] = r1
            r2.e(r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.video.db.VideoRealmHelper.findAllVideoItems():java.util.List");
    }
}
