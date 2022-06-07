package com.xiaomi.micolauncher.module.homepage.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoTabLoadSuccess;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class HomeVideoDataManager {
    public static final String TAB_AUDIO_BOOK = "AUDIO_BOOK";
    public static final String TAB_KEY_HISTORY = "HISTORY";
    public static final String TAB_KEY_KID = "KID";
    public static final String TAB_KEY_REC = "REC";
    public static final String TAB_MUSIC = "MUSIC";
    private static final long a = TimeUnit.SECONDS.toMillis(5);
    private final HashMap<String, List<VideoData>> b;
    private final Handler c;
    private int d;
    private int e;
    private int f;
    private final CompositeDisposable g;
    private final HashMap<String, Layout> h;
    private VideoTabInfo.RecommendCategory i;
    private VideoTabInfo.RecommendAppCard j;
    private HomeVideoTabLoadSuccess k;
    private HomeVideoLoadSuccess l;

    public VideoTabInfo.RecommendAppCard getRecommendAppCard() {
        return this.j;
    }

    public VideoTabInfo.RecommendCategory getRecommendCategory() {
        return this.i;
    }

    private HomeVideoDataManager() {
        this.b = new HashMap<>();
        this.h = new HashMap<>();
        this.c = new Handler(ThreadUtil.getWorkHandler().getLooper(), $$Lambda$HomeVideoDataManager$gZB5T6Emgl5ecKnaiiafDGdiUE.INSTANCE);
        this.g = new CompositeDisposable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Message message) {
        int i = message.what;
        if (i == 10000) {
            EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshHomeData());
            return false;
        } else if (i == 20000) {
            EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshVideoData((String) message.obj));
            return false;
        } else if (i != 30000) {
            return false;
        } else {
            EventBusRegistry.getEventBus().post(new RecommendEvent.RefreshVideoTabData());
            return false;
        }
    }

    /* loaded from: classes3.dex */
    private static class a {
        private static final HomeVideoDataManager a = new HomeVideoDataManager();
    }

    public static HomeVideoDataManager getManager() {
        return a.a;
    }

    private void a() {
        cancelRequest();
        this.c.removeMessages(10000);
        this.c.removeMessages(20000);
        this.c.removeMessages(30000);
    }

    @SuppressLint({"CheckResult"})
    public void loadVideoTabList(Context context) {
        a();
        L.homepage.w("loadVideoTabList from server start");
        this.g.add(ApiManager.displayService.getHomeVideoTabs().map(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$0oZCecA6lc_67HguB_odpxAOfuM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                VideoTabInfo a2;
                a2 = HomeVideoDataManager.this.a((VideoTabInfo) obj);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$6gQb682pDb8uCBDtdLtn1BGceXU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeVideoDataManager.this.b((VideoTabInfo) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$xToikD-OzGzff5BQT3zEY3rsYj0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeVideoDataManager.this.b((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(VideoTabInfo videoTabInfo) throws Exception {
        if (videoTabInfo != null) {
            a("mina/display/main_screen/tab2/", videoTabInfo);
            HomeVideoTabLoadSuccess homeVideoTabLoadSuccess = new HomeVideoTabLoadSuccess(videoTabInfo.getTabDataList());
            this.i = new VideoTabInfo.RecommendCategory(videoTabInfo.getCategoryList());
            this.j = videoTabInfo.getRecommendCard();
            setHomeVideoTabDataFromLocalDB(homeVideoTabLoadSuccess);
            EventBusRegistry.getEventBus().post(homeVideoTabLoadSuccess);
            L.homepage.w("loadVideoTabList from server end");
            return;
        }
        b();
        L.homepage.e("load video tab data is empty.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        b();
        L.homepage.e("load video tab data error : ", th);
    }

    public HomeVideoTabLoadSuccess getHomeVideoTabDataFromLocalDB() {
        return this.k;
    }

    public void setHomeVideoTabDataFromLocalDB(HomeVideoTabLoadSuccess homeVideoTabLoadSuccess) {
        this.k = homeVideoTabLoadSuccess;
    }

    @SuppressLint({"CheckResult"})
    public void loadVideoTabListFromDB() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$ctzIbHZTwsi4bNrWWGACGujlFXM
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeVideoDataManager.this.b(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        L.homepage.i("EntertainFragment loadVideoTabListFromDB");
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/tab2/");
        if (ContainerUtil.hasData(find)) {
            VideoTabInfo videoTabInfo = (VideoTabInfo) Gsons.getGson().fromJson(find, new TypeToken<VideoTabInfo>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager.1
            }.getType());
            HomeVideoTabLoadSuccess homeVideoTabLoadSuccess = new HomeVideoTabLoadSuccess(videoTabInfo.getTabDataList());
            this.i = new VideoTabInfo.RecommendCategory(videoTabInfo.getCategoryList());
            this.j = videoTabInfo.getRecommendCard();
            setHomeVideoTabDataFromLocalDB(homeVideoTabLoadSuccess);
            EventBusRegistry.getEventBus().post(homeVideoTabLoadSuccess);
        }
    }

    public void loadHomeData(final Context context) {
        L.homepage.w("loadHomeData from server start");
        this.g.add(ApiManager.displayService.getHomeVideos(Hardware.current(context).getName(), TAB_KEY_REC, ChildModeManager.getManager().isChildMode(), 0, 20, d()).map(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$mKL4JFGZkkVCpqqoW4QcOPsUDCw
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                HomeVideoData b;
                b = HomeVideoDataManager.this.b(context, (HomeVideoData) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$Iw0mPRJdAQ8_lsIRhYRBpqVLKEE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeVideoDataManager.this.a((HomeVideoData) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$ePfNUpoQhVSFIzxgYfu0qsb6dkA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeVideoDataManager.this.a((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(HomeVideoData homeVideoData) throws Exception {
        if (homeVideoData == null || !ContainerUtil.hasData(homeVideoData.getVideoList())) {
            L.homepage.e("VideoFragment load home video empty");
            c();
            return;
        }
        this.d = 0;
        a("mina/display/main_screen/tab/list", (Object) homeVideoData);
        EventBusRegistry.getEventBus().post(new HomeVideoLoadSuccess(null, homeVideoData, TAB_KEY_REC));
        processShortVideo(TAB_KEY_REC, homeVideoData.getVideoList());
        L.homepage.i("VideoFragment load home video data success");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        c();
        L.homepage.e("VideoFragment load home video error : ", th);
    }

    private void a(final String str, final Object obj) {
        Logger logger = L.homepage;
        logger.i("VideoFragment updateAsync url=" + str);
        if (ThreadUtil.isMainThread()) {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$0t6g-JNNB9H0lbjl4eKwctQXI8M
                @Override // java.lang.Runnable
                public final void run() {
                    HomeVideoDataManager.b(str, obj);
                }
            });
        } else {
            ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str, Object obj) {
        ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
    }

    public HomeVideoLoadSuccess getHomeVideoLoadSuccess() {
        return this.l;
    }

    public void setHomeVideoLoadSuccess(HomeVideoLoadSuccess homeVideoLoadSuccess) {
        this.l = homeVideoLoadSuccess;
    }

    public void loadFirstScreenDataFromDb(Context context) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$m_ptrujITt1TTLaSJeqGvMCJXGQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HomeVideoDataManager.this.a(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        HomeVideoData homeVideoData;
        L.homepage.i("VideoFragment loadData loadFirstScreenDataFromDb");
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/tab/list");
        if (!TextUtils.isEmpty(find)) {
            homeVideoData = (HomeVideoData) Gsons.getGson().fromJson(find, new TypeToken<HomeVideoData>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager.2
            }.getType());
            if (homeVideoData != null) {
                homeVideoData.setHasMore(false);
            } else {
                return;
            }
        } else {
            L.homepage.i("VideoFragment local data is empty");
            homeVideoData = null;
        }
        HomeVideoLoadSuccess homeVideoLoadSuccess = new HomeVideoLoadSuccess(null, homeVideoData, TAB_KEY_REC);
        homeVideoLoadSuccess.isLocalData = true;
        setHomeVideoLoadSuccess(homeVideoLoadSuccess);
        EventBusRegistry.getEventBus().post(homeVideoLoadSuccess);
    }

    private void b() {
        int i = this.f;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh video tab data after " + exponentRandomTimeInMills + " exponent " + this.f);
            this.c.sendEmptyMessageDelayed(30000, exponentRandomTimeInMills);
            this.f = this.f + 1;
        }
    }

    private void c() {
        int i = this.d;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh first home screen data after " + exponentRandomTimeInMills + " exponent " + this.d);
            this.c.sendEmptyMessageDelayed(10000, exponentRandomTimeInMills);
            this.d = this.d + 1;
        }
    }

    private String d() {
        return UUID.randomUUID().toString();
    }

    public void loadVideoData(final Context context, final String str, int i) {
        a();
        if (ApiManager.isInited()) {
            this.g.add(ApiManager.displayService.getHomeVideos(Hardware.current(context).getName(), str, ChildModeManager.getManager().isChildMode(), i, 20, d()).map(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$IM1gNsbdp8v6_b5fU6_Ukc1qtTo
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    HomeVideoData a2;
                    a2 = HomeVideoDataManager.this.a(context, (HomeVideoData) obj);
                    return a2;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$tV28HBwNqJZwT689lXJtn1gbFF4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    HomeVideoDataManager.this.a(str, (HomeVideoData) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$nffysiZlRLIRzXv_1pSinoe-9a0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    HomeVideoDataManager.this.a(str, (Throwable) obj);
                }
            }));
            return;
        }
        L.base.w("ApiManager is not inited!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, HomeVideoData homeVideoData) throws Exception {
        EventBusRegistry.getEventBus().post(new HomeVideoLoadSuccess(null, homeVideoData, str));
        processShortVideo(str, homeVideoData.getVideoList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, Throwable th) throws Exception {
        L.homepage.e("load more video data error : ", th);
        a(str);
    }

    public void cancelRequest() {
        CompositeDisposable compositeDisposable = this.g;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    private void a(String str) {
        int i = this.e;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, a);
            Logger logger = L.launcher;
            logger.d(" refresh other screen data after " + exponentRandomTimeInMills + " exponent " + this.e);
            this.c.sendMessageDelayed(this.c.obtainMessage(20000, str), exponentRandomTimeInMills);
            this.e = this.e + 1;
        }
    }

    public Layout getLayout(String str) {
        if (ContainerUtil.isEmpty(str)) {
            str = "key_empty";
        }
        return this.h.get(str);
    }

    public void putLayout(String str, Layout layout) {
        if (ContainerUtil.isEmpty(str)) {
            str = "key_empty";
        }
        this.h.put(str, layout);
    }

    public void clearLayout() {
        this.h.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public HomeVideoData b(HomeVideoData homeVideoData, Context context) {
        L.homepage.i("map video data execute on thread");
        if (homeVideoData == null || ContainerUtil.isEmpty(homeVideoData.getVideoList())) {
            return homeVideoData;
        }
        long beginTiming = DebugHelper.beginTiming();
        for (VideoData videoData : homeVideoData.getVideoList()) {
            videoData.setText(a(videoData, context));
            a(videoData);
            if (videoData.getRating() != 0.0d) {
                videoData.setRatingText(Double.toString(videoData.getRating()));
            }
        }
        DebugHelper.endTiming(beginTiming, "build layout", new Object[0]);
        return homeVideoData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VideoTabInfo a(VideoTabInfo videoTabInfo) {
        if (videoTabInfo == null) {
            return null;
        }
        VideoTabInfo.RecommendAppCard recommendCard = videoTabInfo.getRecommendCard();
        List<AppInfo> appList = videoTabInfo.getAppList();
        if (recommendCard != null && !ContainerUtil.isEmpty(appList)) {
            List<VideoTabInfo.RecommendAppInfo> itemList = recommendCard.getItemList();
            if (!ContainerUtil.isEmpty(itemList)) {
                for (VideoTabInfo.RecommendAppInfo recommendAppInfo : itemList) {
                    AppInfo a2 = a(recommendAppInfo.getAppKey(), appList);
                    if (a2 != null) {
                        recommendAppInfo.setAppInfo(a2);
                    }
                }
            }
        }
        return videoTabInfo;
    }

    private static AppInfo a(long j, List<AppInfo> list) {
        for (AppInfo appInfo : list) {
            if (appInfo != null && j == appInfo.getAppKey()) {
                return appInfo;
            }
        }
        return null;
    }

    private String a(VideoData videoData) {
        int length = videoData.getName().length();
        String name = videoData.getName();
        if (videoData.isHotPlay() || videoData.getRating() > 0.0d) {
            if (length > 12) {
                name = videoData.getName().substring(0, 12);
            }
        } else if (length > 28) {
            name = videoData.getName().substring(0, 28);
        }
        videoData.setTitleText(name);
        return name;
    }

    @NotNull
    private String a(VideoData videoData, Context context) {
        if (videoData.getTotalEpisode() > 1) {
            if (videoData.getLatestEpisode() <= 10000000 && videoData.getLatestEpisode() <= videoData.getTotalEpisode()) {
                return videoData.getLatestEpisode() == videoData.getTotalEpisode() ? context.getResources().getString(R.string.total_episode_text, Integer.valueOf(videoData.getTotalEpisode())) : context.getResources().getString(R.string.latest_episode_text, Integer.valueOf(videoData.getLatestEpisode()));
            }
            return context.getResources().getString(R.string.latest_episode_zongyi, Integer.valueOf(videoData.getLatestEpisode()));
        } else if (videoData.getDuration() != 0) {
            return a(videoData.getDuration());
        } else {
            return "";
        }
    }

    private static String a(long j) {
        return TimeCalculator.completedString(j / 60) + Constants.COLON_SEPARATOR + TimeCalculator.completedString(j % 60);
    }

    public void processShortVideo(final String str, final List<VideoData> list) {
        if (!ContainerUtil.isEmpty(list)) {
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeVideoDataManager$mPxWMlpVO5sGRjPnLeSQFV7kHNA
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    HomeVideoDataManager.this.a(str, list, observableEmitter);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, List list, ObservableEmitter observableEmitter) throws Exception {
        List<VideoData> list2 = this.b.get(str);
        if (ContainerUtil.isEmpty(list2)) {
            list2 = new ArrayList<>();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            VideoData videoData = (VideoData) it.next();
            if (videoData.isShort() && !list2.contains(videoData)) {
                list2.add(videoData);
            }
        }
        this.b.put(str, list2);
    }

    public HashMap<String, List<VideoData>> getShortVideoMaps() {
        return this.b;
    }
}
