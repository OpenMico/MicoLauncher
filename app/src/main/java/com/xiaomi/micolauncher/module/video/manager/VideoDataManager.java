package com.xiaomi.micolauncher.module.video.manager;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.db.VideoRealmHelper;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class VideoDataManager {
    public static final String ORIGIN_BILI = "bilibili";
    public static final String ORIGIN_CHILD = "child";
    public static final String ORIGIN_IQIYI = "iqiyi";
    public static final String ORIGIN_MI_TV = "mitv";
    public static final String ORIGIN_YOUKU = "youku";
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private Map<Long, Video.Block> d = new HashMap();
    private Map<String, List<Video.Category>> b = new HashMap();
    private Map<String, Long> c = new HashMap();
    private boolean e = ChildModeManager.getManager().isChildMode();

    /* loaded from: classes3.dex */
    public static class a {
        private static VideoDataManager a = new VideoDataManager();
    }

    public static VideoDataManager getManager() {
        return a.a;
    }

    public boolean isChildMode() {
        return this.e;
    }

    public void refreshChildModeCache() {
        this.d.clear();
        this.b.clear();
        this.e = ChildModeManager.getManager().isChildMode() || SystemSetting.getKeyIsTempChild();
    }

    private List<Video.Category> a(String str) {
        return this.b.get(str);
    }

    private void a(String str, List<Video.Category> list) {
        this.b.put(str, list);
    }

    public Observable<List<Video.Category>> loadCategoryList() {
        return loadCategoryList("iqiyi");
    }

    public Observable<List<Video.Category>> loadCategoryList(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$aLhjLW3-65F2k3XH6SPY7Z0h3Qw
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                VideoDataManager.this.b(str, observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(String str, ObservableEmitter observableEmitter) throws Exception {
        List<Video.Category> a2 = a(str);
        if (a2 == null || a2.size() <= 0 || !b(str)) {
            a(observableEmitter, str);
            return;
        }
        observableEmitter.onNext(a2);
        observableEmitter.onComplete();
    }

    private void a(final Emitter emitter, final String str) {
        if (this.e) {
            ApiManager.displayService.getChildVideoCategoryList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$9EGk5TNu0ry6doxs6ElyBjbVr0E
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.this.b(str, emitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$H2Z3dk3HPFSvkClpNDFBUlMiNio
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.d(Emitter.this, (Throwable) obj);
                }
            });
        } else {
            ApiManager.displayService.getVideoCategoryList(Hardware.current(MicoApplication.getGlobalContext()).getName(), str).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$F8_ldMNJ5NHKBUuoCuEjtdWADlA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.this.a(str, emitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$r3X_7x3y9IpGYG5c6XB2gNX0qyg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.c(Emitter.this, (Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void b(String str, Emitter emitter, List list) throws Exception {
        a(str, list);
        this.c.put(str, Long.valueOf(System.currentTimeMillis()));
        this.d.clear();
        emitter.onNext(list);
        emitter.onComplete();
    }

    public static /* synthetic */ void d(Emitter emitter, Throwable th) throws Exception {
        emitter.onError(th);
        emitter.onComplete();
    }

    public /* synthetic */ void a(String str, Emitter emitter, List list) throws Exception {
        a(str, list);
        this.c.put(str, Long.valueOf(System.currentTimeMillis()));
        this.d.clear();
        emitter.onNext(list);
        emitter.onComplete();
    }

    public static /* synthetic */ void c(Emitter emitter, Throwable th) throws Exception {
        emitter.onError(th);
        emitter.onComplete();
    }

    public Observable<Video.Block> loadCategoryContent(final long j, final String str, final String str2) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$lHwA7p34B7LHIsD3Xrxwn1OzH1U
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                VideoDataManager.this.a(j, str, str2, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(long j, String str, String str2, ObservableEmitter observableEmitter) throws Exception {
        if (this.d.getOrDefault(Long.valueOf(j), null) != null) {
            observableEmitter.onNext(this.d.get(Long.valueOf(j)));
            observableEmitter.onComplete();
            return;
        }
        loadCategoryContentFromRemote(observableEmitter, j, str, str2);
    }

    public Observable<Video.Block> loadCategoryContent2(final long j, final String str, final String str2) {
        Video.Block block = this.d.get(Long.valueOf(j));
        if (block != null && ContainerUtil.hasData(block.itemList)) {
            return Observable.just(block);
        }
        if (this.e) {
            return ApiManager.displayService.getChildVideoCategoryContent(str, 0, 50).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$QQvEzY0Fq4_QJtC0BlhfvO-SMXc
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource b;
                    b = VideoDataManager.this.b(j, str, str2, (List) obj);
                    return b;
                }
            });
        }
        return ApiManager.displayService.getVideoCategoryContent(j, 0, 50, Hardware.current(MicoApplication.getGlobalContext()).getName()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$20VPbQJEKUZhM99tIaund9aTA64
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = VideoDataManager.this.a(j, str, str2, (List) obj);
                return a2;
            }
        });
    }

    public /* synthetic */ ObservableSource b(long j, String str, String str2, List list) throws Exception {
        Video.Block block = new Video.Block(j, str, str2, new ArrayList(list));
        this.d.put(Long.valueOf(j), block);
        return Observable.just(block);
    }

    public /* synthetic */ ObservableSource a(long j, String str, String str2, List list) throws Exception {
        Video.Block block = new Video.Block(j, str, str2, new ArrayList(list));
        this.d.put(Long.valueOf(j), block);
        return Observable.just(block);
    }

    public void loadCategoryContentFromRemote(final Emitter emitter, final long j, final String str, final String str2) {
        if (this.e) {
            ApiManager.displayService.getChildVideoCategoryContent(str, 0, 50).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$UOl7hyAn9Xtsyy7guN4Hate4r5Y
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.this.b(j, str, str2, emitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$SrYzzJyWq0uTUZvkuhBRfcNr1qM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.b(Emitter.this, (Throwable) obj);
                }
            });
        } else {
            ApiManager.displayService.getVideoCategoryContent(j, 0, 50, Hardware.current(MicoApplication.getGlobalContext()).getName()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$BneclXQlqBfdl_SkJBTOzSTyFOw
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.this.a(j, str, str2, emitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$VZyhzIGVHXu3jwFcbw2JmPIA0dA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoDataManager.a(Emitter.this, (Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void b(long j, String str, String str2, Emitter emitter, List list) throws Exception {
        Video.Block block = new Video.Block(j, str, str2, new ArrayList(list));
        this.d.put(Long.valueOf(j), block);
        emitter.onNext(block);
        emitter.onComplete();
    }

    public static /* synthetic */ void b(Emitter emitter, Throwable th) throws Exception {
        emitter.onError(th);
        emitter.onComplete();
    }

    public /* synthetic */ void a(long j, String str, String str2, Emitter emitter, List list) throws Exception {
        Video.Block block = new Video.Block(j, str, str2, new ArrayList(list));
        this.d.put(Long.valueOf(j), block);
        emitter.onNext(block);
        emitter.onComplete();
    }

    public static /* synthetic */ void a(Emitter emitter, Throwable th) throws Exception {
        emitter.onError(th);
        emitter.onComplete();
    }

    public Observable<List<VideoItem>> loadVideoListFromDB(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$hMbXPfNeE6kel7MhPkh3MVaWZz8
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                VideoDataManager.a(str, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void a(String str, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(VideoRealmHelper.getInstance().findAll(str));
        observableEmitter.onComplete();
    }

    public Observable<List<VideoItem>> loadVideoListFromDBInSevenDays() {
        return Observable.create($$Lambda$VideoDataManager$rlFnG9i8ENw_s1uW3ZBFHlMY3Q.INSTANCE);
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(VideoRealmHelper.getInstance().findInSevenDays());
        observableEmitter.onComplete();
    }

    public Observable<Long> loadCategoryIdByName(@NotNull final String str) {
        return loadCategoryList().subscribeOn(MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.video.manager.-$$Lambda$VideoDataManager$uoaLuNf7FUND2VguQmNAXINQ208
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = VideoDataManager.b(str, (List) obj);
                return b;
            }
        });
    }

    public static /* synthetic */ ObservableSource b(@NotNull String str, List list) throws Exception {
        Iterator it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            Video.Category category = (Video.Category) it.next();
            if (str.equals(category.type)) {
                j = category.id;
            }
        }
        return Observable.fromArray(Long.valueOf(j));
    }

    private boolean b(String str) {
        return Math.abs(System.currentTimeMillis() - this.c.get(str).longValue()) <= a;
    }
}
