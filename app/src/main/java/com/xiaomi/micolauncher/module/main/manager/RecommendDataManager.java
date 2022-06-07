package com.xiaomi.micolauncher.module.main.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.manager.HomeMusicDataManager;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class RecommendDataManager {
    private static final long a = TimeUnit.SECONDS.toMillis(5);
    private static final long b = TimeUnit.HOURS.toMillis(3);
    private int A;
    private int B;
    private int C;
    private int D;
    private String E;
    private Context F;
    private HomeDataContainer G;
    private Handler c;
    private CompositeDisposable d;
    private List<MainPage.Music> e;
    private List<MainPage.Video> f;
    private List<MainPage.News> g;
    private List<MainPage.Station> h;
    private List<Station.Item> i;
    private List<Video.ShortVideo> j;
    private List<MainPage.MainPageVideo> k;
    private List<PatchWall.Item> l;
    private List<MainPage.SkillApp> m;
    private List<MainPage.LongVideo> n;
    private List<MainPage.LongVideo> o;
    private PatchWall.Item p;
    private MainPage.LongVideo q;
    private boolean r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    /* loaded from: classes3.dex */
    public static class HomeDataContainer {
        List<MainPage.News> a;
        List<MainPage.Station> b;
        List<Station.Item> c;
        List<Video.ShortVideo> d;
        List<MainPage.LongVideo> e;
        List<MainPage.LongVideo> f;
        PatchWall.Item g;
        MainPage.LongVideo h;
        List<MainPage.SkillApp> i;
        List<MainPage.Music> j;
        List<PatchWall.Item> k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static RecommendDataManager a = new RecommendDataManager();
    }

    public static RecommendDataManager getManager() {
        return a.a;
    }

    private RecommendDataManager() {
        this.n = new ArrayList();
        this.o = new ArrayList();
        this.c = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$AtV5uYNsXh-3rTTNoWe27sG7wT4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = RecommendDataManager.this.a(message);
                return a2;
            }
        });
        this.d = new CompositeDisposable();
    }

    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case 1:
                s();
                return false;
            case 2:
                w();
                return false;
            case 3:
                y();
                return false;
            case 4:
                u();
                return false;
            case 5:
                F();
                return false;
            case 6:
                a(this.F);
                return false;
            case 7:
                p();
                return false;
            case 8:
                n();
                return false;
            case 9:
            default:
                return false;
            case 10:
                f();
                return false;
            case 11:
                h();
                return false;
            case 12:
                loadMusicSource(this.F);
                return false;
            case 13:
                j();
                return false;
        }
    }

    public void clear() {
        this.d.dispose();
    }

    public void loadData(Context context) {
        L.homepage.d("RecommendDataManager load data ");
        if (!Hardware.isBigScreen()) {
            this.F = context;
            t();
            s();
            u();
            w();
            y();
            F();
        }
    }

    public void loadHomeData(Context context) {
        L.homepage.d("RecommendDataManager load data ");
        this.F = context;
        this.G = new HomeDataContainer();
        loadDataFromDB();
        f();
        h();
        s();
        a(context);
        loadMusicSource(context);
        j();
        n();
    }

    public void loadDataFromDB() {
        L.homepage.d("RecommendDataManager loadDataFromDB ");
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$JmxjeFQRR0zr0tCpnt2d_Eb2jSo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.g(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$cOOSZSfoW3y6rnLnfbZ-gTjpPG4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.g((RecommendDataManager.HomeDataContainer) obj);
            }
        }, $$Lambda$RecommendDataManager$GB7DVOwK0aFm9r6T3gpfQ5ITTmk.INSTANCE));
    }

    public /* synthetic */ void g(ObservableEmitter observableEmitter) throws Exception {
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/recent_play");
        if (!TextUtils.isEmpty(find)) {
            homeDataContainer.h = (MainPage.LongVideo) Gsons.getGson().fromJson(find, new TypeToken<MainPage.LongVideo>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.1
            }.getType());
        }
        String find2 = ApiRealmHelper.getInstance().find("mina/display/main_screen/guess_like");
        if (!TextUtils.isEmpty(find2)) {
            homeDataContainer.g = (PatchWall.Item) Gsons.getGson().fromJson(find2, new TypeToken<PatchWall.Item>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.12
            }.getType());
        }
        String find3 = ApiRealmHelper.getInstance().find("mina/display/main_screen/long_banner");
        if (!TextUtils.isEmpty(find3)) {
            homeDataContainer.e = (List) Gsons.getGson().fromJson(find3, new TypeToken<List<MainPage.LongVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.22
            }.getType());
        }
        String find4 = ApiRealmHelper.getInstance().find("mina/display/main_screen/short_banner");
        if (!TextUtils.isEmpty(find4)) {
            homeDataContainer.f = (List) Gsons.getGson().fromJson(find4, new TypeToken<List<MainPage.LongVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.23
            }.getType());
        }
        String find5 = ApiRealmHelper.getInstance().find("mina/display/main_screen/news");
        if (!TextUtils.isEmpty(find5)) {
            homeDataContainer.a = (List) Gsons.getGson().fromJson(find5, new TypeToken<List<MainPage.News>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.24
            }.getType());
        }
        String find6 = ApiRealmHelper.getInstance().find("mina/display/main_screen/short_video");
        if (!TextUtils.isEmpty(find6)) {
            homeDataContainer.d = (List) Gsons.getGson().fromJson(find6, new TypeToken<List<Video.ShortVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.25
            }.getType());
        }
        String find7 = ApiRealmHelper.getInstance().find("mina/display/main_screen/music");
        if (!TextUtils.isEmpty(find7)) {
            homeDataContainer.j = (List) Gsons.getGson().fromJson(find7, new TypeToken<List<MainPage.Music>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.26
            }.getType());
        }
        String find8 = ApiRealmHelper.getInstance().find("mina/display/m  ain_screen/music_mv");
        if (!TextUtils.isEmpty(find8)) {
            homeDataContainer.k = (List) Gsons.getGson().fromJson(find8, new TypeToken<List<PatchWall.Item>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.27
            }.getType());
        }
        String find9 = ApiRealmHelper.getInstance().find("mina/display/main_screen/home_station");
        if (!TextUtils.isEmpty(find9)) {
            homeDataContainer.c = (List) Gsons.getGson().fromJson(find9, new TypeToken<List<Station.Item>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.28
            }.getType());
        }
        String find10 = ApiRealmHelper.getInstance().find("mina/display/main_screen/station");
        if (!TextUtils.isEmpty(find10)) {
            homeDataContainer.b = (List) Gsons.getGson().fromJson(find10, new TypeToken<List<MainPage.Station>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.2
            }.getType());
        }
        String find11 = ApiRealmHelper.getInstance().find("mina/display/main_screen/skill_app");
        if (!TextUtils.isEmpty(find11)) {
            homeDataContainer.i = (List) Gsons.getGson().fromJson(find11, new TypeToken<List<MainPage.SkillApp>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.3
            }.getType());
        }
        observableEmitter.onNext(homeDataContainer);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void g(HomeDataContainer homeDataContainer) throws Exception {
        boolean z = a(this.n, homeDataContainer.e) || a(this.o, homeDataContainer.f) || a(this.g, homeDataContainer.a) || a(this.j, homeDataContainer.d);
        if (ContainerUtil.isEmpty(this.e)) {
            this.e = homeDataContainer.j;
        }
        if (ContainerUtil.isEmpty(this.g)) {
            this.g = homeDataContainer.a;
        }
        if (ContainerUtil.isEmpty(this.h)) {
            this.h = homeDataContainer.b;
        }
        if (ContainerUtil.isEmpty(this.i)) {
            this.i = homeDataContainer.c;
        }
        if (ContainerUtil.isEmpty(this.j)) {
            this.j = homeDataContainer.d;
        }
        if (ContainerUtil.isEmpty(this.l)) {
            this.l = homeDataContainer.k;
        }
        if (ContainerUtil.isEmpty(this.m)) {
            this.m = homeDataContainer.i;
        }
        if (ContainerUtil.isEmpty(this.n)) {
            this.n = homeDataContainer.e;
        }
        if (ContainerUtil.isEmpty(this.o)) {
            this.o = homeDataContainer.f;
        }
        if (this.p == null) {
            this.p = homeDataContainer.g;
        }
        if (this.q == null) {
            this.q = homeDataContainer.h;
        }
        if (z) {
            L.homepage.d("RecommendDataManager load cache data");
            a(new RecommendEvent.HomeFirstScreenDataChanged());
        }
    }

    public static /* synthetic */ void r(Throwable th) throws Exception {
        L.homepage.d("RecommendDataManager load cache data : ", th);
    }

    private <T> boolean a(List<T> list, List<T> list2) {
        if (ContainerUtil.isEmpty(list2)) {
            return false;
        }
        return !ContainerUtil.equal(list, list2);
    }

    private void a() {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$Y06laBvrdploFsY8OqE3xl73ylA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.f(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$402ly_m2M0tih6ZKnIMlgq9gvRg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.f((RecommendDataManager.HomeDataContainer) obj);
            }
        }, $$Lambda$RecommendDataManager$NLWjnC9tfUZIZV4uzEZGcWuHTI.INSTANCE));
    }

    public /* synthetic */ void f(ObservableEmitter observableEmitter) throws Exception {
        long beginTiming = DebugHelper.beginTiming();
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/long_banner");
        if (!TextUtils.isEmpty(find)) {
            homeDataContainer.e = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.LongVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.4
            }.getType());
        }
        String find2 = ApiRealmHelper.getInstance().find("mina/display/main_screen/short_banner");
        if (!TextUtils.isEmpty(find2)) {
            homeDataContainer.f = (List) Gsons.getGson().fromJson(find2, new TypeToken<List<MainPage.LongVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.5
            }.getType());
        }
        String find3 = ApiRealmHelper.getInstance().find("mina/display/main_screen/guess_like");
        if (!TextUtils.isEmpty(find3)) {
            homeDataContainer.g = (PatchWall.Item) Gsons.getGson().fromJson(find3, new TypeToken<PatchWall.Item>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.6
            }.getType());
        }
        String find4 = ApiRealmHelper.getInstance().find("mina/display/main_screen/recent_play");
        if (!TextUtils.isEmpty(find4)) {
            homeDataContainer.h = (MainPage.LongVideo) Gsons.getGson().fromJson(find4, new TypeToken<MainPage.LongVideo>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.7
            }.getType());
        }
        DebugHelper.endTiming(beginTiming, "RecommendDataManager loadFirstScreenDataFromDb", new Object[0]);
        observableEmitter.onNext(homeDataContainer);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void f(HomeDataContainer homeDataContainer) throws Exception {
        boolean z = a(this.n, homeDataContainer.e) || a(this.o, homeDataContainer.f);
        this.n = homeDataContainer.e;
        this.o = homeDataContainer.f;
        this.p = homeDataContainer.g;
        this.q = homeDataContainer.h;
        if (z) {
            L.homepage.d("RecommendDataManager loadFirstScreenDataFromDb");
            a(new RecommendEvent.HomeFirstScreenDataChanged());
        }
    }

    public static /* synthetic */ void q(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager loadFirstScreenDataFromDb : ", th);
    }

    private void b() {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$JtSoe01bigpQfw8g4RwdhyPqLZA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.e(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$i4BySP40y5581iQLri-Ootzemy8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.e((RecommendDataManager.HomeDataContainer) obj);
            }
        }, $$Lambda$RecommendDataManager$V7dEiBs9iEAJFobHrUxCszTvkk.INSTANCE));
    }

    public /* synthetic */ void e(ObservableEmitter observableEmitter) throws Exception {
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/news");
        if (!TextUtils.isEmpty(find)) {
            homeDataContainer.a = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.News>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.8
            }.getType());
        }
        String find2 = ApiRealmHelper.getInstance().find("mina/display/main_screen/short_video");
        if (!TextUtils.isEmpty(find2)) {
            homeDataContainer.d = (List) Gsons.getGson().fromJson(find2, new TypeToken<List<Video.ShortVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.9
            }.getType());
        }
        observableEmitter.onNext(homeDataContainer);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void e(HomeDataContainer homeDataContainer) throws Exception {
        boolean z = a(this.g, homeDataContainer.a) || a(this.j, homeDataContainer.d);
        this.g = homeDataContainer.a;
        this.j = homeDataContainer.d;
        if (z) {
            a(new RecommendEvent.NewsAndShortVideoDataChanged());
        }
    }

    public static /* synthetic */ void p(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager loadNewsShortVideoDataFromDb : ", th);
    }

    private void c() {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$4WWPCi5gxJ0zT1QHxCpegSm35Ik
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.d(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$47rH1SNH_Kb6g9n1JDmO6JfSYQQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.d((RecommendDataManager.HomeDataContainer) obj);
            }
        }, $$Lambda$RecommendDataManager$bozh461_1ocCl5UWSd4L_i3DiYA.INSTANCE));
    }

    public /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/home_station");
        if (!TextUtils.isEmpty(find)) {
            homeDataContainer.c = (List) Gsons.getGson().fromJson(find, new TypeToken<List<Station.Item>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.10
            }.getType());
        }
        String find2 = ApiRealmHelper.getInstance().find("mina/display/main_screen/station");
        if (!TextUtils.isEmpty(find2)) {
            homeDataContainer.b = (List) Gsons.getGson().fromJson(find2, new TypeToken<List<MainPage.Station>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.11
            }.getType());
        }
        observableEmitter.onNext(homeDataContainer);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void d(HomeDataContainer homeDataContainer) throws Exception {
        boolean z = a(this.h, homeDataContainer.b) || a(this.i, homeDataContainer.c);
        this.i = homeDataContainer.c;
        this.h = homeDataContainer.b;
        if (z) {
            a(new RecommendEvent.StationDataUpdated());
        }
    }

    public static /* synthetic */ void o(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager loadStationsFromDb : ", th);
    }

    private void d() {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$R_J20V3Sp587kGNEUh8xAavwAgM
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.c(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$oo77SEik7qCWbsHjzN_z43Grs08
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.m((List) obj);
            }
        }, $$Lambda$RecommendDataManager$6piTAe2VbOiRHyJD4WICYjNpXk4.INSTANCE));
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/skill_app");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.SkillApp>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.13
        }.getType()) : null);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void m(List list) throws Exception {
        boolean a2 = a(this.m, list);
        this.m = list;
        if (a2) {
            a(new RecommendEvent.SkillAppDataUpdated());
        }
    }

    public static /* synthetic */ void n(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager loadSkillAppFromDb : ", th);
    }

    private void e() {
        this.d.add(Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$zkjD_ZqnwaVgvCgeaLTM8K-TQpE
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                RecommendDataManager.this.b(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$SswsCLwbB_q9D8zhjC4-lHJh_XM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.l((List) obj);
            }
        }, $$Lambda$RecommendDataManager$oMM7XWrbUvmp3OcTLKX985_6Nu4.INSTANCE));
    }

    public /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        List list;
        String find = ApiRealmHelper.getInstance().find("mina/display/m  ain_screen/music_mv");
        if (!TextUtils.isEmpty(find)) {
            list = (List) Gsons.getGson().fromJson(find, new TypeToken<List<PatchWall.Item>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.14
            }.getType());
        } else {
            a(new RecommendEvent.MusicDataUpdated());
            list = null;
        }
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void l(List list) throws Exception {
        boolean a2 = a(this.l, list);
        this.l = list;
        if (a2) {
            a(new RecommendEvent.MusicDataUpdated());
        }
    }

    public static /* synthetic */ void m(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager loadMusicMvFromDb : ", th);
    }

    public boolean isEmpty() {
        return ContainerUtil.isEmpty(this.g) || ContainerUtil.isEmpty(this.h) || ContainerUtil.isEmpty(this.i) || ContainerUtil.isEmpty(this.j) || ContainerUtil.isEmpty(this.m) || ContainerUtil.isEmpty(this.n) || ContainerUtil.isEmpty(this.o);
    }

    private void a(Object obj) {
        if (!isEmpty()) {
            EventBusRegistry.getEventBus().post(obj);
        } else {
            L.homepage.i("RecommendDataManager empty data, do not postEvent %s", obj);
        }
    }

    private void a(final String str, final Object obj) {
        long beginTiming = DebugHelper.beginTiming();
        if (ThreadUtil.isMainThread()) {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$p6h1nqDliDdTLFzRBghVIHHiqhc
                @Override // java.lang.Runnable
                public final void run() {
                    RecommendDataManager.b(str, obj);
                }
            });
        } else {
            ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
        }
        DebugHelper.endTiming(beginTiming, "load home data : " + str, new Object[0]);
    }

    public static /* synthetic */ void b(String str, Object obj) {
        ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
    }

    private void f() {
        this.c.removeMessages(10);
        this.d.add(Observable.zip(ApiManager.displayService.getRecommendLongBanner(Hardware.current(this.F).getName()), ApiManager.displayService.getRecommendShortBanner(Hardware.current(this.F).getName()), HomeMusicDataManager.getManager().loadGuessYouLikeListen().onErrorResumeNext($$Lambda$RecommendDataManager$Pup1bzro23ExS19llcB2auRRjZs.INSTANCE), new Function3() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$3_uueEY2jOTl0ii2j5L50DDf1uY
            @Override // io.reactivex.functions.Function3
            public final Object apply(Object obj, Object obj2, Object obj3) {
                RecommendDataManager.HomeDataContainer a2;
                a2 = RecommendDataManager.this.a((String) obj, (String) obj2, (PatchWall) obj3);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$U5TBcvPHpZlio52xV_4eIR7yt-U
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.c((RecommendDataManager.HomeDataContainer) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$Bt8Q0MIV-Da1_2KIOY-d0lrq3FA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.k((Throwable) obj);
            }
        }));
    }

    public static /* synthetic */ ObservableSource l(Throwable th) throws Exception {
        return Observable.create($$Lambda$RecommendDataManager$kdCTDLN9kVgj_JEvbaqtonv2w8.INSTANCE);
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(new PatchWall());
        observableEmitter.onComplete();
    }

    public /* synthetic */ HomeDataContainer a(String str, String str2, PatchWall patchWall) throws Exception {
        PatchWall.Block block;
        List<MainPage.LongVideo> a2 = a(str, true);
        a("mina/display/main_screen/long_banner", a2);
        List<MainPage.LongVideo> a3 = a(str2, false);
        a("mina/display/main_screen/short_banner", a3);
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        homeDataContainer.e = a2;
        homeDataContainer.f = a3;
        if (patchWall != null && ContainerUtil.hasData(patchWall.blocks) && (block = patchWall.blocks.get(0)) != null && ContainerUtil.hasData(block.items)) {
            homeDataContainer.g = block.items.get(0);
            a("mina/display/main_screen/guess_like", this.p);
        }
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/recent_play");
        if (!TextUtils.isEmpty(find)) {
            homeDataContainer.h = (MainPage.LongVideo) Gsons.getGson().fromJson(find, new TypeToken<MainPage.LongVideo>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.15
            }.getType());
        }
        return homeDataContainer;
    }

    public /* synthetic */ void c(HomeDataContainer homeDataContainer) throws Exception {
        if (ContainerUtil.isEmpty(homeDataContainer.e) || ContainerUtil.isEmpty(homeDataContainer.f)) {
            g();
            return;
        }
        boolean z = a(this.n, homeDataContainer.e) || a(this.o, homeDataContainer.f);
        this.n = homeDataContainer.e;
        this.o = homeDataContainer.f;
        this.p = homeDataContainer.g;
        this.q = homeDataContainer.h;
        if (z) {
            L.homepage.d(" RecommendDataManager load first screen data!");
            a(new RecommendEvent.HomeFirstScreenDataChanged());
        }
        this.c.sendEmptyMessageDelayed(10, I());
    }

    public /* synthetic */ void k(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager load first screen data error: ", th);
        g();
    }

    private void g() {
        if (this.B == 0) {
            a();
        }
        int i = this.B;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh first home screen data after " + exponentRandomTimeInMills + " exponent " + this.B);
            this.c.sendEmptyMessageDelayed(10, exponentRandomTimeInMills);
            this.B = this.B + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(10, I());
    }

    private void h() {
        this.c.removeMessages(11);
        this.d.add(Observable.zip(ApiManager.displayService.getRecommendNewsList(Hardware.current(this.F).getName(), true), ApiManager.displayService.recommendShortVideo(), $$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$Q_WGSvxBLWqGipwE9K1RB5vHleY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.b((RecommendDataManager.HomeDataContainer) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$TCEsBJBHhWzYtikF21O8wlnEuwQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.j((Throwable) obj);
            }
        }));
    }

    public static /* synthetic */ HomeDataContainer c(List list, List list2) throws Exception {
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        homeDataContainer.a = list;
        homeDataContainer.d = list2;
        return homeDataContainer;
    }

    public /* synthetic */ void b(HomeDataContainer homeDataContainer) throws Exception {
        if (ContainerUtil.isEmpty(homeDataContainer.a) || ContainerUtil.isEmpty(homeDataContainer.d)) {
            i();
            return;
        }
        boolean z = a(this.g, homeDataContainer.a) || a(this.j, homeDataContainer.d);
        a(homeDataContainer.a);
        b(homeDataContainer.d);
        L.homepage.d("RecommendDataManager load news short video");
        if (z) {
            a(new RecommendEvent.NewsAndShortVideoDataChanged());
        }
        this.c.sendEmptyMessageDelayed(11, I());
    }

    public /* synthetic */ void j(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager load news short video error : ", th);
        i();
    }

    private void i() {
        if (this.C == 0) {
            b();
        }
        int i = this.C;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh news and short video data after " + exponentRandomTimeInMills + " exponent " + this.C);
            this.c.sendEmptyMessageDelayed(11, exponentRandomTimeInMills);
            this.C = this.C + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(11, I());
    }

    private void a(List<MainPage.News> list) {
        if (ContainerUtil.hasData(list)) {
            this.g = list;
            a("mina/display/main_screen/news", list);
        }
    }

    private void b(List<Video.ShortVideo> list) {
        if (ContainerUtil.hasData(list)) {
            this.w = 0;
            this.j = list;
            E();
            a("mina/display/main_screen/short_video", list);
        }
    }

    private void j() {
        this.c.removeMessages(13);
        this.d.add(Observable.zip(ApiManager.displayService.getRecommendHomeStation().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$6HE3Xndu1g4Ti6AURG-V9cIFhxE
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource k;
                k = RecommendDataManager.this.k((List) obj);
                return k;
            }
        }), ApiManager.displayService.getRecommendStationList(), $$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$HXbW1vCFGt85OzjSwP4YqdWUocY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.a((RecommendDataManager.HomeDataContainer) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$9buLfw-JgoFDTeZWbfLDhUhIQCI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.i((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ ObservableSource k(List list) throws Exception {
        return Observable.zip(a((Station.HomeStation) list.get(0)), a((Station.HomeStation) list.get(1)), $$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2NulIvZwSY.INSTANCE);
    }

    public static /* synthetic */ List a(Station.Sound sound, Station.Sound sound2) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (ContainerUtil.hasData(sound.soundList)) {
            arrayList.add(sound.soundList.get(0));
        }
        if (ContainerUtil.hasData(sound2.soundList)) {
            arrayList.add(sound2.soundList.get(0));
        }
        return arrayList;
    }

    public static /* synthetic */ HomeDataContainer b(List list, List list2) throws Exception {
        HomeDataContainer homeDataContainer = new HomeDataContainer();
        homeDataContainer.c = list;
        homeDataContainer.b = list2;
        return homeDataContainer;
    }

    public /* synthetic */ void a(HomeDataContainer homeDataContainer) throws Exception {
        if (ContainerUtil.isEmpty(homeDataContainer.c) || ContainerUtil.isEmpty(homeDataContainer.b)) {
            k();
            return;
        }
        boolean z = a(this.h, homeDataContainer.b) || a(this.i, homeDataContainer.c);
        c(homeDataContainer.c);
        d(homeDataContainer.b);
        L.homepage.d("RecommendDataManager load stations");
        if (z) {
            a(new RecommendEvent.StationDataUpdated());
        }
        this.c.sendEmptyMessageDelayed(13, I());
    }

    public /* synthetic */ void i(Throwable th) throws Exception {
        L.homepage.e("RecommendDataManager load stations error : ", th);
        k();
    }

    private void k() {
        if (this.D == 0) {
            c();
        }
        int i = this.D;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh stations data after " + exponentRandomTimeInMills + " exponent " + this.D);
            this.c.sendEmptyMessageDelayed(13, exponentRandomTimeInMills);
            this.D = this.D + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(13, I());
    }

    private void c(List<Station.Item> list) {
        if (ContainerUtil.hasData(list)) {
            this.i = list;
            ImageSwatchCacheManager.getManager().clearCache();
            a("mina/display/main_screen/home_station", list);
        }
    }

    private void d(List<MainPage.Station> list) {
        if (ContainerUtil.hasData(list)) {
            this.u = 0;
            this.h = list;
            ImageSwatchCacheManager.getManager().clearCache();
            a("mina/display/main_screen/station", list);
        }
    }

    public List<MainPage.LongVideo> getShortBanners() {
        return this.o;
    }

    public List<MainPage.LongVideo> getLongBanners() {
        return this.n;
    }

    private List<MainPage.LongVideo> a(String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject != null) {
                    String optString = jSONObject.optString("title");
                    String optString2 = jSONObject.optString("description");
                    String optString3 = jSONObject.optString(TraceConstants.Result.CP);
                    String optString4 = jSONObject.optString("cpId");
                    String optString5 = jSONObject.optString("actionURL");
                    String optString6 = jSONObject.optString("imageURL");
                    String optString7 = jSONObject.optString("bannerType");
                    String str2 = "";
                    if (z) {
                        str2 = jSONObject.optString("trackKey");
                    }
                    arrayList.add(new MainPage.LongVideo(optString, optString3, optString4, optString6, optString2, optString5, optString7, str2));
                }
            }
        } catch (JSONException e) {
            L.homepage.e("parse exception is ", e);
        }
        return arrayList;
    }

    private void a(final Context context) {
        this.d.add(MusicDataManager.getManager().checkAuthStatus().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$vEOGaZ3KGIvwP4TgxfM47K3qkFM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.a(context, (MIBrain.CPBindStatus) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$2BrhwjNIpZFLQEVFiGwVAYdOD1I
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.h((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void a(Context context, MIBrain.CPBindStatus cPBindStatus) throws Exception {
        PreferenceUtils.setSettingBoolean(context, HomePageUtils.QQ_MUSIC_BIND, cPBindStatus.isValid());
        this.c.sendEmptyMessageDelayed(6, I());
    }

    public /* synthetic */ void h(Throwable th) throws Exception {
        l();
    }

    private void l() {
        int i = this.x;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh check auth status after " + exponentRandomTimeInMills + " exponent " + this.x);
            this.c.sendEmptyMessageDelayed(6, exponentRandomTimeInMills);
            this.x = this.x + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(6, I());
    }

    public void loadMusicSource(final Context context) {
        if (ApiManager.userProfileService == null) {
            L.launcher.d("loadMusicSource userProfileService is null");
        } else {
            ApiManager.userProfileService.getMusicSource().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.16
                /* renamed from: a */
                public void onSuccess(String str) {
                    RecommendDataManager.this.E = SystemSetting.MusicSuorce.value(str).getSource();
                    HomePageUtils.setMusicSource(context, RecommendDataManager.this.E);
                }

                @Override // com.xiaomi.micolauncher.api.DefaultObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    RecommendDataManager.this.m();
                }
            });
        }
    }

    public void m() {
        int i = this.y;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh music source after " + exponentRandomTimeInMills + " exponent " + this.y);
            this.c.sendEmptyMessageDelayed(12, exponentRandomTimeInMills);
            this.y = this.y + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(12, I());
    }

    private void n() {
        this.c.removeMessages(8);
        this.d.add(ApiManager.displayService.getRecommendSkillApp().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$xxGSS8G9hZvyQ7R2xEcYzID6ql4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.j((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$guvx7Xwb12hfBjDdEjIDi4g05X4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.g((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void j(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            this.A = 0;
            boolean a2 = a(this.m, list);
            this.m = list;
            L.homepage.d("RecommendDataManager load skill app");
            if (a2) {
                a(new RecommendEvent.SkillAppDataUpdated());
            }
            this.c.sendEmptyMessageDelayed(8, I());
            a("mina/display/main_screen/skill_app", list);
            return;
        }
        o();
    }

    public /* synthetic */ void g(Throwable th) throws Exception {
        o();
    }

    private void o() {
        if (this.A == 0) {
            d();
        }
        int i = this.A;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh skill app data after " + exponentRandomTimeInMills + " exponent " + this.A);
            this.c.sendEmptyMessageDelayed(8, exponentRandomTimeInMills);
            this.A = this.A + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(8, I());
    }

    private void p() {
        this.d.add(ApiManager.displayService.getQQPatchWallMv().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$Bzell1bKG0YcYgSXd-e6FZTJDBg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.a((PatchWall.Block) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$Ix6RQc64kq3crlx82zBN3N6ZBFg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.f((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void a(PatchWall.Block block) throws Exception {
        if (ContainerUtil.hasData(block.items)) {
            this.z = 0;
            boolean a2 = a(this.l, block.items);
            this.l = block.items;
            if (a2) {
                a(new RecommendEvent.MusicDataUpdated());
            }
            this.c.sendEmptyMessageDelayed(7, I());
            L.homepage.d("load music mv");
            a("mina/display/m  ain_screen/music_mv", this.l);
            return;
        }
        q();
    }

    public /* synthetic */ void f(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadMusicMv : ", th);
        q();
    }

    private void q() {
        if (this.z == 0) {
            e();
        }
        int i = this.z;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh video data after " + exponentRandomTimeInMills + " exponent " + this.z);
            this.c.sendEmptyMessageDelayed(7, exponentRandomTimeInMills);
            this.z = this.z + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(7, I());
    }

    private void r() {
        if (ContainerUtil.isEmpty(this.e)) {
            s();
        }
        if (ContainerUtil.isEmpty(this.f)) {
            u();
        }
        if (ContainerUtil.isEmpty(this.g)) {
            w();
        }
        if (ContainerUtil.isEmpty(this.h)) {
            y();
        }
        if (ContainerUtil.isEmpty(this.j)) {
            F();
        }
    }

    public void refreshQQMusic() {
        this.c.sendEmptyMessage(9);
    }

    private void s() {
        L.launcher.d("RecommendDataManager start load music data");
        this.c.removeMessages(1);
        this.d.add(ApiManager.displayService.getRecommendMusicList(Hardware.current(this.F).getName()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$yrD0sKFnzZDtOwySbVYItW4cssc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.i((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$FusPSsHA5pBdnOSEYlQ6BEfKRCs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.e((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void i(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            this.s = 0;
            boolean a2 = a(this.e, list);
            this.e = list;
            if (!Hardware.isBigScreen()) {
                EventBusRegistry.getEventBus().post(new RecommendEvent.MusicDataUpdated());
            } else if (a2) {
                a(new RecommendEvent.MusicDataUpdated());
            }
            ImageSwatchCacheManager.getManager().clearCache();
            this.c.sendEmptyMessageDelayed(1, I());
            a("mina/display/main_screen/music", list);
        } else {
            A();
        }
        L.homepage.d("RecommendDataManager recommendata load music data success");
    }

    public /* synthetic */ void e(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadMusicData : ", th);
        A();
    }

    private void t() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$N7EiXStuGjTbBpRo3UAvReXP5t8
            @Override // java.lang.Runnable
            public final void run() {
                RecommendDataManager.this.N();
            }
        });
    }

    public /* synthetic */ void N() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/music");
        if (!TextUtils.isEmpty(find)) {
            List<MainPage.Music> list = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.Music>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.17
            }.getType());
            boolean a2 = a(this.e, list);
            this.e = list;
            if (!Hardware.isBigScreen()) {
                EventBusRegistry.getEventBus().post(new RecommendEvent.MusicDataUpdated());
            } else if (a2) {
                a(new RecommendEvent.MusicDataUpdated());
            }
        } else {
            EventBusRegistry.getEventBus().post(new RecommendEvent.MusicDataUpdated());
        }
    }

    private void u() {
        L.launcher.d(" start load video data");
        this.c.removeMessages(4);
        this.d.add(ApiManager.displayService.getRecommendVideoList().subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$SObodkdi31T9Cj9luctHd4zn1jY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.h((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$r85UBtts0lJ1yvjWg15OtIS3nNc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.d((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void h(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            this.v = 0;
            this.f = list;
            EventBusRegistry.getEventBus().post(new RecommendEvent.VideoDataUpdated());
            this.c.sendEmptyMessageDelayed(4, I());
            a("mina/display/main_screen/video", list);
            return;
        }
        B();
    }

    public /* synthetic */ void d(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadVideoData", th);
        B();
    }

    private void v() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$fn9MAco4BUssfltJbi54MJ_Kw2g
            @Override // java.lang.Runnable
            public final void run() {
                RecommendDataManager.this.M();
            }
        });
    }

    public /* synthetic */ void M() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/video");
        if (!TextUtils.isEmpty(find)) {
            this.f = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.Video>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.18
            }.getType());
            EventBusRegistry.getEventBus().post(new RecommendEvent.LongVideoDataUpdated());
        }
    }

    private void w() {
        L.launcher.d(" start load news data");
        DebugHelper.beginTiming();
        this.c.removeMessages(2);
        this.d.add(ApiManager.displayService.getRecommendNewsList(Hardware.current(this.F).getName(), true).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$ET1QUBuG5Fd7hw9HhomrpcLn4U8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.g((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$g__ODVZfJVnQV2Ixj2ZGS13z_f4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.c((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void g(List list) throws Exception {
        updateRecommendNewsList(list);
        L.launcher.d("RecommendDataManager load news data success");
    }

    public /* synthetic */ void c(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadNewsData : ", th);
        C();
    }

    public void updateRecommendNewsList(List<MainPage.News> list) {
        if (ContainerUtil.hasData(list)) {
            this.t = 0;
            this.g = list;
            EventBusRegistry.getEventBus().post(new RecommendEvent.NewsDataUpdated());
            this.c.sendEmptyMessageDelayed(2, I());
            a("mina/display/main_screen/news", list);
            return;
        }
        C();
    }

    private void x() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$-_79ItXdyJJ1y5-qpSKFnH14DtY
            @Override // java.lang.Runnable
            public final void run() {
                RecommendDataManager.this.L();
            }
        });
    }

    public /* synthetic */ void L() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/news");
        if (!TextUtils.isEmpty(find)) {
            this.g = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.News>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.19
            }.getType());
            EventBusRegistry.getEventBus().post(new RecommendEvent.NewsDataUpdated());
        }
    }

    private Observable<Station.Sound> a(Station.HomeStation homeStation) {
        return ApiManager.soundService.getRecommendSoundList(homeStation.getCpId(), homeStation.getCp(), 0, 20);
    }

    private void y() {
        L.launcher.d(" start load station data");
        this.c.removeMessages(3);
        this.d.add(ApiManager.displayService.getRecommendStationList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$ZKRvqiow8Z_ZdcSYuJrvKjps4I4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.f((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$6bpywqhBx3_eFxkj1A793KHiUZw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.b((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void f(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            this.u = 0;
            this.h = list;
            EventBusRegistry.getEventBus().post(new RecommendEvent.StationDataUpdated());
            ImageSwatchCacheManager.getManager().clearCache();
            this.c.sendEmptyMessageDelayed(3, I());
            L.launcher.d("RecommendDataManager loadStationData success");
            a("mina/display/main_screen/station", list);
            return;
        }
        D();
    }

    public /* synthetic */ void b(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadStationData : ", th);
        D();
    }

    private void z() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$rFHAZO9BoiaxWO_w53j4w_1XgH0
            @Override // java.lang.Runnable
            public final void run() {
                RecommendDataManager.this.K();
            }
        });
    }

    public /* synthetic */ void K() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/station");
        if (!TextUtils.isEmpty(find)) {
            this.h = (List) Gsons.getGson().fromJson(find, new TypeToken<List<MainPage.Station>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.20
            }.getType());
            EventBusRegistry.getEventBus().post(new RecommendEvent.StationDataUpdated());
        }
    }

    private void A() {
        if (this.s == 0 && Hardware.isBigScreen()) {
            t();
        }
        int i = this.s;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh music data after " + exponentRandomTimeInMills + " exponent " + this.s);
            this.c.sendEmptyMessageDelayed(1, exponentRandomTimeInMills);
            this.s = this.s + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(1, I());
    }

    private void B() {
        if (this.v == 0) {
            v();
        }
        int i = this.v;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh video data after " + exponentRandomTimeInMills + " exponent " + this.v);
            this.c.sendEmptyMessageDelayed(4, exponentRandomTimeInMills);
            this.v = this.v + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(4, I());
    }

    private void C() {
        if (this.t == 0) {
            x();
        }
        int i = this.t;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh news data after " + exponentRandomTimeInMills + " exponent " + this.t);
            this.c.sendEmptyMessageDelayed(2, exponentRandomTimeInMills);
            this.t = this.t + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(2, I());
    }

    private void D() {
        if (this.u == 0) {
            z();
        }
        int i = this.u;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh station data after " + exponentRandomTimeInMills + " exponent " + this.u);
            this.c.sendEmptyMessageDelayed(3, exponentRandomTimeInMills);
            this.u = this.u + 1;
            return;
        }
        this.c.sendEmptyMessageDelayed(3, I());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.isConnectedStablely()) {
            L.launcher.d(" status bar event, wifi connected");
            r();
        }
    }

    public void refreshNewsList() {
        this.c.sendEmptyMessage(2);
    }

    public void refreshMusicList() {
        this.c.sendEmptyMessage(1);
    }

    public String getMusicResource() {
        return this.E;
    }

    public void refreshMusicList(Context context, String str) {
        this.F = context;
        if (Hardware.isBigScreen()) {
            this.E = str;
            RecommendEvent.MusicSourceChanged musicSourceChanged = new RecommendEvent.MusicSourceChanged();
            musicSourceChanged.source = str;
            EventBusRegistry.getEventBus().post(musicSourceChanged);
            if (HomePageUtils.isBindQQ(context)) {
                this.c.sendEmptyMessage(7);
            }
        }
        this.c.sendEmptyMessage(1);
    }

    public void refreshShortVideoList() {
        this.c.sendEmptyMessage(5);
    }

    public List<PatchWall.Item> getRecommendMusicMvs() {
        return this.l;
    }

    public List<MainPage.SkillApp> getRecommendSkillApps() {
        return this.m;
    }

    public PatchWall.Item getRecommendGuessLike() {
        return this.p;
    }

    public MainPage.LongVideo getRecommendRecentPlayVideo() {
        return this.q;
    }

    public boolean isRecentPlayVideoUpdated() {
        return this.r;
    }

    public void setRecentPlayVideoUpdated(boolean z) {
        this.r = z;
    }

    public void updateRecentPlayVideo(MainPage.LongVideo longVideo) {
        this.q = longVideo;
        setRecentPlayVideoUpdated(true);
        a("mina/display/main_screen/recent_play", longVideo);
    }

    public List getRecommendMusicList() {
        return this.e;
    }

    public void clearQQMusicData() {
        this.e = null;
        this.l = null;
    }

    public List<MainPage.News> getRecommendNewsList() {
        return this.g;
    }

    public List<MainPage.Station> getRecommendStationList() {
        return this.h;
    }

    public List<Station.Item> getRecommendHomeStationList() {
        return this.i;
    }

    public List<? extends MainPage.MainPageVideo> getRecommendVideos() {
        if (this.k == null) {
            if (!ContainerUtil.isEmpty(this.f)) {
                return this.f;
            }
            if (!ContainerUtil.isEmpty(this.j)) {
                return this.j;
            }
        }
        return this.k;
    }

    public ArrayList<VideoItem> getRecommendShortVideos() {
        if (ContainerUtil.isEmpty(this.j)) {
            return null;
        }
        ArrayList<VideoItem> arrayList = new ArrayList<>();
        for (int i = 0; i < this.j.size(); i++) {
            arrayList.add(this.j.get(i).transform());
        }
        return arrayList;
    }

    public int getRecommendShortVideoIndex(VideoItem videoItem) {
        if (!ContainerUtil.isEmpty(this.j)) {
            for (int i = 0; i < this.j.size(); i++) {
                if (!TextUtils.isEmpty(videoItem.getMediaId()) && videoItem.getMediaId().equals(this.j.get(i).cpId)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private synchronized void E() {
        if (!(this.j == null || this.f == null)) {
            this.k = new ArrayList();
            int max = Math.max(this.j.size(), this.f.size());
            for (int i = 0; i < max; i++) {
                if (i < this.f.size()) {
                    this.k.add(this.f.get(i));
                }
                if (i < this.j.size()) {
                    this.k.add(this.j.get(i));
                }
            }
        }
    }

    private void F() {
        L.launcher.d("start load short video data");
        this.d.add(ApiManager.displayService.recommendShortVideo().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$PuU9B-czNfHFbxMVDLU0pfk7YLk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.e((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$H2-uGSl3A8-_3KZXmD9mizrqBRw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RecommendDataManager.this.a((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void e(List list) throws Exception {
        updateShortVideoData(list);
        L.launcher.d("RecommendDataManager load short video data success");
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        L.launcher.e("RecommendDataManager loadShortVideoData : ", th);
        G();
    }

    public void updateShortVideoData(List<Video.ShortVideo> list) {
        if (ContainerUtil.hasData(list)) {
            this.w = 0;
            this.j = list;
            E();
            EventBusRegistry.getEventBus().post(new RecommendEvent.VideoDataUpdated());
            this.c.sendEmptyMessageDelayed(5, I());
            a("mina/display/main_screen/short_video", list);
            return;
        }
        G();
    }

    private void G() {
        if (this.w == 0) {
            H();
        }
        int i = this.w;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            L.launcher.d(" refresh short video data after %s exponent %s", Long.valueOf(exponentRandomTimeInMills), Integer.valueOf(this.w));
            this.c.sendEmptyMessageDelayed(5, exponentRandomTimeInMills);
            this.w++;
            return;
        }
        this.c.sendEmptyMessageDelayed(5, I());
    }

    private void H() {
        MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$XilkDh0YcaDXG-nmtffbdFhJZeY
            @Override // java.lang.Runnable
            public final void run() {
                RecommendDataManager.this.J();
            }
        });
    }

    public /* synthetic */ void J() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/short_video");
        if (!TextUtils.isEmpty(find)) {
            this.j = (List) Gsons.getGson().fromJson(find, new TypeToken<List<Video.ShortVideo>>() { // from class: com.xiaomi.micolauncher.module.main.manager.RecommendDataManager.21
            }.getType());
            E();
            EventBusRegistry.getEventBus().post(new RecommendEvent.VideoDataUpdated());
        }
    }

    private long I() {
        long randomMillisInNextMin = b + RandomTimeUtils.getRandomMillisInNextMin(60);
        L.homepage.i("refreshTimePeriod=%s", Long.valueOf(randomMillisInNextMin));
        return randomMillisInNextMin;
    }
}
