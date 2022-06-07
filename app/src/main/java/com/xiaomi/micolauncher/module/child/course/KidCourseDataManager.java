package com.xiaomi.micolauncher.module.child.course;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.CourseTab;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.GenerateOpaqueUtil;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class KidCourseDataManager {
    private static final long a = TimeUnit.DAYS.toMillis(1);
    private List<CourseTab> b;
    private CourseTab c;
    private String d;
    private int e;
    private List<ChildVideo.ItemsBean> f;
    private Map<String, ChildVideo.BlocksBean> g;
    private Map<String, Long> h;
    private Map<String, ChildVideo> i;
    private Map<String, List<ChildVideo.ItemsBean>> j;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final KidCourseDataManager a = new KidCourseDataManager();
    }

    public static KidCourseDataManager getManager() {
        return a.a;
    }

    private KidCourseDataManager() {
        this.b = new ArrayList();
        this.g = new HashMap();
        this.h = new HashMap();
        this.i = new HashMap();
        this.j = new HashMap();
    }

    public void addChildVideoCache(String str, ChildVideo childVideo) {
        if (this.i == null) {
            this.i = new HashMap();
        }
        this.i.put(str, childVideo);
    }

    public void addBlockCache(String str, ChildVideo.BlocksBean blocksBean) {
        if (this.g == null) {
            this.g = new HashMap();
        }
        this.g.put(str, blocksBean);
    }

    @SuppressLint({"CheckResult"})
    public Observable<List<CourseTab>> getCourseTabList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$6BkqdGF6mzGunNImTPlI4wBZzNU
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                KidCourseDataManager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final ObservableEmitter observableEmitter) throws Exception {
        if (ContainerUtil.hasData(this.b)) {
            observableEmitter.onNext(this.b);
            observableEmitter.onComplete();
        }
        ApiManager.minaService.loadConfig("kid_course_tab").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$cw1ZArd_rUHShqV6SIDzNjlCAqs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                KidCourseDataManager.this.a(observableEmitter, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$J8CMdKEWC7MOacbMivQ6DwGcTWo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                KidCourseDataManager.this.a(observableEmitter, (Throwable) obj);
            }
        });
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, String str) throws Exception {
        this.b = (List) Gsons.getGson().fromJson(str, new TypeToken<List<CourseTab>>() { // from class: com.xiaomi.micolauncher.module.child.course.KidCourseDataManager.1
        }.getType());
        if (ContainerUtil.hasData(this.b)) {
            for (int i = 1; i < ContainerUtil.getSize(this.b) + 1; i++) {
                this.b.get(i - 1).setId(String.valueOf(i));
            }
            observableEmitter.onNext(this.b);
            L.course.i("courseTabList size %d", Integer.valueOf(ContainerUtil.getSize(this.b)));
            ApiRealmHelper.getInstance().updateAsync("key_course_tab", Gsons.getGson().toJson(this.b));
            return;
        }
        L.course.e("getCourseTabList error , data is null");
        a((Emitter<List<CourseTab>>) observableEmitter);
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        L.course.e("getCourseTabList error ", th);
        a((Emitter<List<CourseTab>>) observableEmitter);
    }

    private void a(Emitter<List<CourseTab>> emitter) {
        String find = ApiRealmHelper.getInstance().find("key_course_tab");
        if (!TextUtils.isEmpty(find)) {
            List<CourseTab> list = (List) Gsons.getGson().fromJson(find, new TypeToken<List<CourseTab>>() { // from class: com.xiaomi.micolauncher.module.child.course.KidCourseDataManager.2
            }.getType());
            if (ContainerUtil.hasData(list)) {
                emitter.onNext(list);
                L.course.i("courseTabList cache size %d", Integer.valueOf(ContainerUtil.getSize(list)));
            }
        }
    }

    @SuppressLint({"CheckResult"})
    public Observable<List<ChildVideo.ItemsBean>> getVideoTypeFromRemote(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$Sd3-3zJGXgiO9aHpSafJJC-pRuA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                KidCourseDataManager.this.a(str, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final String str, final ObservableEmitter observableEmitter) throws Exception {
        this.f = new ArrayList();
        String str2 = ChildVideoDataManager.MI_TV_URL_PREFIX + str;
        ApiManager.childVideoService.getChildVideo(str2, ChildVideoDataManager.PTF, 21, ChildVideoDataManager.CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString(str2)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$J5Y2QyEmxX0oYrP-eFlF7GIX0xI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                KidCourseDataManager.this.b(str, observableEmitter, (ChildVideo) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$EE_ShJEchOFm-BRhP_Y_AL3Li0Y
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                KidCourseDataManager.this.a(observableEmitter, str, (Throwable) obj);
            }
        });
    }

    public /* synthetic */ void b(String str, ObservableEmitter observableEmitter, ChildVideo childVideo) throws Exception {
        for (ChildVideo.BlocksBean blocksBean : childVideo.getBlocks()) {
            for (ChildVideo.ItemsBean itemsBean : blocksBean.getItems()) {
                this.f.add(itemsBean);
                L.course.i("save filter types  %s", itemsBean.getItemTitle());
            }
        }
        this.j.put(str, this.f);
        observableEmitter.onNext(this.f);
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, String str, Throwable th) throws Exception {
        if (ContainerUtil.hasData(this.j)) {
            observableEmitter.onNext(this.j.get(str));
        }
    }

    public Observable<ChildVideo.BlocksBean> loadCategoryContent(final String str, final boolean z) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$yY0zZE5toBJbH5xQVjpkLXGDPr0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                KidCourseDataManager.this.b(str, z, observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(String str, boolean z, ObservableEmitter observableEmitter) throws Exception {
        ChildVideo.BlocksBean blocksBean = this.g.get(str);
        if (!z || blocksBean == null || !a(str)) {
            a((Emitter<ChildVideo.BlocksBean>) observableEmitter, str);
            return;
        }
        L.course.i("load filter cache data  %s", str);
        observableEmitter.onNext(blocksBean);
    }

    @SuppressLint({"CheckResult"})
    void a(final Emitter<ChildVideo.BlocksBean> emitter, final String str) {
        ApiManager.childVideoService.getChildVideo(str, ChildVideoDataManager.PTF, 21, ChildVideoDataManager.CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString(str)).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$x2g6xYDse9acjDe7ksxvQTxUEFE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                KidCourseDataManager.this.a(str, emitter, (ChildVideo) obj);
            }
        });
    }

    public /* synthetic */ void a(String str, Emitter emitter, ChildVideo childVideo) throws Exception {
        this.h.put(str, Long.valueOf(System.currentTimeMillis()));
        ChildVideo.BlocksBean blocksBean = childVideo.getBlocks().get(0);
        blocksBean.setMeta(childVideo.getMeta());
        emitter.onNext(blocksBean);
        emitter.onComplete();
    }

    @SuppressLint({"CheckResult"})
    public Observable<ChildVideo> getRecommendData(final String str, final boolean z) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$TGTWl9Ue5SGjsOA38A4wnS6y75c
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                KidCourseDataManager.this.a(str, z, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final String str, boolean z, final ObservableEmitter observableEmitter) throws Exception {
        ChildVideo childVideo = this.i.get(str);
        if (!z || childVideo == null || !a(str)) {
            ApiManager.childVideoService.getChildVideo(str, ChildVideoDataManager.PTF, 21, ChildVideoDataManager.CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString(str)).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$KidCourseDataManager$_Koorv0pZYl2dYJq7VzXrQIC-Cg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    KidCourseDataManager.this.a(str, observableEmitter, (ChildVideo) obj);
                }
            });
        } else {
            observableEmitter.onNext(childVideo);
        }
    }

    public /* synthetic */ void a(String str, ObservableEmitter observableEmitter, ChildVideo childVideo) throws Exception {
        L.course.i("KidCourseDataManager load data from remote  size  %d", Integer.valueOf(ContainerUtil.getSize(childVideo.getBlocks())));
        this.h.put(str, Long.valueOf(System.currentTimeMillis()));
        this.i.put(str, childVideo);
        observableEmitter.onNext(childVideo);
    }

    public List<CourseTab> getCourseTabs() {
        return this.b;
    }

    private boolean a(String str) {
        Long l;
        Map<String, Long> map = this.h;
        return (map == null || (l = map.get(str)) == null || Math.abs(System.currentTimeMillis() - l.longValue()) > a) ? false : true;
    }

    public void clearCache() {
        L.course.i("clear kid course cache");
        if (ContainerUtil.hasData(this.g)) {
            this.g.clear();
        }
        if (ContainerUtil.hasData(this.i)) {
            this.i.clear();
        }
        if (ContainerUtil.hasData(this.h)) {
            this.h.clear();
        }
        if (ContainerUtil.hasData(this.g)) {
            this.g.clear();
        }
    }

    public CourseTab getCurrentTab() {
        if (this.c == null) {
            this.c = new CourseTab();
        }
        return this.c;
    }

    public void setCurrentTab(CourseTab courseTab) {
        this.c = courseTab;
    }

    public int getSubTabOffset() {
        return this.e;
    }

    public void setSubTabOffset(int i) {
        this.e = i;
    }

    public String getSubTab() {
        return this.d;
    }

    public void setSubTab(String str) {
        this.d = str;
    }
}
