package com.xiaomi.micolauncher.api.cache;

import android.text.TextUtils;
import com.elvishew.xlog.XLog;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.DataBaseLogHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.event.VideoQueryEvent;
import com.xiaomi.micolauncher.module.video.db.VideoRealmHelper;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class ApiRealmHelper {
    static final /* synthetic */ boolean a = !ApiRealmHelper.class.desiredAssertionStatus();
    private final RealmConfiguration b;
    private RealmMigration c;

    /* loaded from: classes3.dex */
    public static class a {
        public static ApiRealmHelper a = new ApiRealmHelper();
    }

    private ApiRealmHelper() {
        this.c = $$Lambda$ApiRealmHelper$9JSHzvcViIYhVUt9kHEquHmAgxs.INSTANCE;
        this.b = new RealmConfiguration.Builder().name("mico_launcher_api.realm").schemaVersion(1L).modules(new ApiRealmModule(), new Object[0]).migration(this.c).build();
    }

    public static /* synthetic */ void a(DynamicRealm dynamicRealm, long j, long j2) {
        L.course.i("api realm oldversion %d  newversion  %d", Long.valueOf(j), Long.valueOf(j2));
        RealmSchema schema = dynamicRealm.getSchema();
        if (j == 0 && j2 == 1) {
            schema.get(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("pCode", String.class, new FieldAttribute[0]);
        }
    }

    public static ApiRealmHelper getInstance() {
        return a.a;
    }

    public boolean update(String str, String str2) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.b);
            try {
                final ApiRealmObject apiRealmObject = new ApiRealmObject();
                apiRealmObject.realmSet$url(str);
                apiRealmObject.realmSet$content(str2);
                apiRealmObject.realmSet$lastUpdateTime(System.currentTimeMillis());
                instance.executeTransaction(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$OcUPBlcA3Rh65V0cM9pbWdaIJjw
                    @Override // io.realm.Realm.Transaction
                    public final void execute(Realm realm) {
                        realm.insertOrUpdate(ApiRealmObject.this);
                    }
                });
                if (instance != null) {
                    instance.close();
                }
                return true;
            } catch (Exception e) {
                XLog.e(e);
                if (instance != null) {
                    instance.close();
                }
                return false;
            }
        } catch (Throwable th) {
            L.database.e("%s update() catch the throwable %s", "[ApiRealmHelper]:", th);
            return false;
        }
    }

    public boolean updateAsync(String str, String str2) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.b);
            try {
                final ApiRealmObject apiRealmObject = new ApiRealmObject();
                apiRealmObject.realmSet$url(str);
                apiRealmObject.realmSet$content(str2);
                apiRealmObject.realmSet$lastUpdateTime(System.currentTimeMillis());
                instance.executeTransactionAsync(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$WqQXz9pdiwroCHR-m4GBp_CZivo
                    @Override // io.realm.Realm.Transaction
                    public final void execute(Realm realm) {
                        realm.insertOrUpdate(ApiRealmObject.this);
                    }
                });
                if (instance != null) {
                    instance.close();
                }
                return true;
            } catch (Exception e) {
                XLog.e(e);
                if (instance != null) {
                    instance.close();
                }
                return false;
            }
        } catch (Throwable th) {
            L.database.e("%s updateAsync() catch the throwable %s ", "[ApiRealmHelper]:", th);
            return false;
        }
    }

    public void insertVideoData(final VideoData videoData) {
        if (videoData.isLongVideo()) {
            try {
                Realm instance = Realm.getInstance(this.b);
                instance.executeTransactionAsync(new Realm.Transaction() { // from class: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$fr0WmaQ-tnJRx71Wwi-gV3cB2qQ
                    @Override // io.realm.Realm.Transaction
                    public final void execute(Realm realm) {
                        ApiRealmHelper.a(VideoData.this, realm);
                    }
                });
                if (instance != null) {
                    instance.close();
                }
            } catch (Throwable th) {
                L.database.e("%s insertVideoData() catch the throwable %s", "[ApiRealmHelper]:", th);
            }
        }
    }

    public static /* synthetic */ void a(VideoData videoData, Realm realm) {
        videoData.setLastUpdateTime(System.currentTimeMillis());
        realm.copyToRealmOrUpdate((Realm) videoData, new ImportFlag[0]);
        if (realm.where(VideoData.class).count() > 50) {
            VideoData videoData2 = (VideoData) realm.where(VideoData.class).findAll().sort("lastUpdateTime").first();
            if (a || videoData2 != null) {
                videoData2.deleteFromRealm();
                return;
            }
            throw new AssertionError();
        }
    }

    public void queryAllVideoDatas() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.api.cache.-$$Lambda$ApiRealmHelper$Zsm0IkMnrSspLDgECsMNMdgkAkE
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ApiRealmHelper.this.a(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe();
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        Realm instance = Realm.getInstance(this.b);
        Throwable th = null;
        try {
            List<VideoData> copyFromRealm = instance.copyFromRealm(instance.where(VideoData.class).findAll());
            HashMap hashMap = new HashMap();
            if (ContainerUtil.isEmpty(copyFromRealm)) {
                copyFromRealm = new ArrayList();
            } else {
                for (VideoData videoData : copyFromRealm) {
                    hashMap.put(videoData.getName(), videoData);
                }
            }
            for (VideoItem videoItem : VideoRealmHelper.getInstance().findAllVideoItems()) {
                if (hashMap.keySet().contains(videoItem.getTitle())) {
                    VideoData videoData2 = (VideoData) hashMap.get(videoItem.getTitle());
                    if (videoData2 != null && videoData2.getLastUpdateTime() < videoItem.getLastUpdateTime()) {
                        videoData2.setLastUpdateTime(videoItem.getLastUpdateTime());
                    }
                } else {
                    VideoData videoData3 = new VideoData();
                    videoData3.setCp(videoItem.getCp());
                    videoData3.setCpId(videoItem.getMediaId());
                    if (ContainerUtil.hasData(videoItem.getRating())) {
                        videoData3.setRating(Double.parseDouble(videoItem.getRating()));
                    }
                    videoData3.setLastUpdateTime(videoItem.getLastUpdateTime());
                    videoData3.setImage(videoItem.getRecommendVerticalImage());
                    videoData3.setName(videoItem.getTitle());
                    videoData3.setTitleText(videoItem.getTitle());
                    videoData3.setVip(videoItem.isVip());
                    videoData3.setPCode(videoItem.getpCode());
                    copyFromRealm.add(videoData3);
                }
            }
            String find = getInstance().find("mina/display/main_screen/recent_play");
            if (!TextUtils.isEmpty(find)) {
                MainPage.LongVideo longVideo = (MainPage.LongVideo) Gsons.getGson().fromJson(find, new TypeToken<MainPage.LongVideo>() { // from class: com.xiaomi.micolauncher.api.cache.ApiRealmHelper.1
                }.getType());
                VideoData videoData4 = new VideoData();
                videoData4.setName(longVideo.getTitle());
                videoData4.setTitleText(longVideo.getTitle());
                videoData4.setImage(longVideo.getImageURL());
                videoData4.setDesc(longVideo.getDescription());
                videoData4.setCpId(longVideo.getCpId());
                copyFromRealm.add(videoData4);
            }
            Collections.sort(copyFromRealm, $$Lambda$ApiRealmHelper$UQ73_xh4Gu7eh2O5HKH8PQDno.INSTANCE);
            EventBusRegistry.getEventBus().post(new VideoQueryEvent(copyFromRealm));
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

    public static /* synthetic */ int a(VideoData videoData, VideoData videoData2) {
        return Long.compare(videoData2.getLastUpdateTime(), videoData.getLastUpdateTime());
    }

    public String find(String str) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.b);
            ApiRealmObject apiRealmObject = (ApiRealmObject) instance.where(ApiRealmObject.class).equalTo("url", str).findFirst();
            if (apiRealmObject == null) {
                if (instance != null) {
                    instance.close();
                }
                return null;
            }
            String realmGet$content = apiRealmObject.realmGet$content();
            if (instance != null) {
                instance.close();
            }
            return realmGet$content;
        } catch (Throwable th) {
            L.database.e("ApiRealmHelper#find() catch the throwable %s", th);
            return "";
        }
    }

    public String find2(String str) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.b);
            ApiRealmObject apiRealmObject = (ApiRealmObject) instance.where(ApiRealmObject.class).equalTo("url", str).findFirst();
            if (apiRealmObject == null) {
                if (instance != null) {
                    instance.close();
                }
                return null;
            }
            String realmGet$content = apiRealmObject.realmGet$content();
            if (instance != null) {
                instance.close();
            }
            return realmGet$content;
        } catch (Throwable th) {
            L.database.e("ApiRealmHelper#find() catch the throwable %s", th);
            return "Exception";
        }
    }

    public long lastUpdateTime(String str) {
        DataBaseLogHelper.printProcess();
        try {
            Realm instance = Realm.getInstance(this.b);
            ApiRealmObject apiRealmObject = (ApiRealmObject) instance.where(ApiRealmObject.class).equalTo("url", str).findFirst();
            if (apiRealmObject == null) {
                if (instance != null) {
                    instance.close();
                }
                return 0L;
            }
            long realmGet$lastUpdateTime = apiRealmObject.realmGet$lastUpdateTime();
            if (instance != null) {
                instance.close();
            }
            return realmGet$lastUpdateTime;
        } catch (Throwable th) {
            L.database.e("%s lastUpdateTime() catch the throwable %s", "[ApiRealmHelper]:", th);
            return 0L;
        }
    }
}
