package com.xiaomi.micolauncher.module.skill.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;
import com.xiaomi.micolauncher.module.homepage.bean.AppPageData;
import com.xiaomi.micolauncher.module.homepage.bean.CategoryTab;
import com.xiaomi.micolauncher.module.homepage.bean.Explore;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.cache.AppRealmHelper;
import com.xiaomi.micolauncher.module.homepage.event.AppInstalledEvent;
import com.xiaomi.micolauncher.module.homepage.event.AppsDataLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.LoadExploreDataSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadSkillDataSuccessEvent;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class SkillDataManager {
    public static final String APP_ALARM = "闹钟";
    public static final String APP_GALLERY = "家庭相册";
    public static final String APP_WEATHER = "天气";
    public static final String TAB_KEY_MINE = "mine";
    public static final String TAB_KEY_REC = "rec";
    private static final long a = TimeUnit.HOURS.toMillis(8);
    private static final long b = TimeUnit.SECONDS.toMillis(5);
    private static final long c = TimeUnit.HOURS.toMillis(24);
    private int A;
    private long d;
    private int e;
    private int f;
    private final Handler g;
    private final HashMap<Long, AppInfo> h;
    private final HashMap<String, AppInfo> i;
    private final HashMap<String, AppInfo> j;
    private final Object k;
    private final List<AppInfo> l;
    private HashMap<String, List<AppInfo>> m;
    private final HashMap<String, List<RecommendCard>> n;
    private final Set<Long> o;
    private final Set<String> p;
    private final Set<String> q;
    private final List<Long> r;
    private final List<String> s;
    private final List<String> t;
    private List<Skill.SkillBean> u;
    private List<Skill.SkillInfo> v;
    private List<Skill.SkillType> w;
    private List<List<SkillApp>> x;
    private int y;
    private AppPageData z;

    /* loaded from: classes3.dex */
    public static class b {
        private static final SkillDataManager a = new SkillDataManager();
    }

    public static SkillDataManager getManager() {
        return b.a;
    }

    private SkillDataManager() {
        this.h = new HashMap<>();
        this.i = new HashMap<>();
        this.j = new HashMap<>();
        this.k = new Object();
        this.l = new ArrayList();
        this.m = new HashMap<>();
        this.n = new HashMap<>();
        this.o = new HashSet();
        this.p = new HashSet();
        this.q = new HashSet();
        this.r = new ArrayList();
        this.s = new ArrayList();
        this.t = new ArrayList();
        this.u = new ArrayList();
        this.w = new ArrayList();
        this.v = new ArrayList();
        this.g = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$QWSFiTw0sRG82Yan3c8vz_QKJUE
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = SkillDataManager.this.a(message);
                return a2;
            }
        });
    }

    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case 1000:
                loadSkillData();
                return false;
            case 1001:
                loadExploreData();
                return false;
            case 1002:
                loadAppsData((Context) message.obj);
                return false;
            default:
                return false;
        }
    }

    public void setThirdApps(List<List<SkillApp>> list) {
        this.x = list;
    }

    public List<List<SkillApp>> getThirdApps() {
        return this.x;
    }

    public void setCurrentPosition(int i) {
        this.y = i;
    }

    public int getCurrentPosition() {
        return this.y;
    }

    @SuppressLint({"CheckResult"})
    public Observable<List<Skill.SkillBean>> loadSkillList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$0xhL-JYrHYp5MO7iMRkwMal4Bcs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.e(observableEmitter);
            }
        });
    }

    public /* synthetic */ void e(final ObservableEmitter observableEmitter) throws Exception {
        List<Skill.SkillBean> list = this.u;
        if (list != null && list.size() > 0 && e()) {
            observableEmitter.onNext(this.u);
            observableEmitter.onComplete();
        } else if (ApiManager.isInited()) {
            ApiManager.displayService.getSkillList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$eE-thn6FEe7ZqaOEFRsMFDxGSCk
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.c(observableEmitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$FWgr_6s7WoMJ5yp10QKUbXnnLx0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.e(ObservableEmitter.this, (Throwable) obj);
                }
            });
        } else {
            L.base.w("ApiManager is not inited!");
        }
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter, List list) throws Exception {
        this.u = list;
        this.v.clear();
        this.d = System.currentTimeMillis();
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void e(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private boolean a(boolean z, String str) {
        if ("GENERAL".equals(str) || ContainerUtil.isEmpty(str)) {
            return true;
        }
        if (z) {
            return "CHILD".equals(str);
        }
        return "ADULT".equals(str);
    }

    private void a(final String str) {
        if (ThreadUtil.isMainThread()) {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$2x5vBjC_02vIEBBo0-ZzSCn98XY
                @Override // java.lang.Runnable
                public final void run() {
                    SkillDataManager.g(str);
                }
            });
        } else {
            ApiRealmHelper.getInstance().updateAsync("mina/display/main_screen/apps", str);
        }
    }

    public static /* synthetic */ void g(String str) {
        ApiRealmHelper.getInstance().updateAsync("mina/display/main_screen/apps", str);
    }

    public AppPageData loadLocalAppData() {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/apps");
        if (TextUtils.isEmpty(find)) {
            return null;
        }
        AppPageData appPageData = (AppPageData) Gsons.getGson().fromJson(find, new TypeToken<AppPageData>() { // from class: com.xiaomi.micolauncher.module.skill.manager.SkillDataManager.1
        }.getType());
        L.homepage.w("AppPageData cache Data parse end");
        return appPageData;
    }

    private void a(Context context, AppPageData appPageData) {
        boolean isChildMode = ChildModeManager.getManager().isChildMode();
        a();
        ArrayList arrayList = new ArrayList();
        List<AppInfo> queryAllAppInfos = AppRealmHelper.getInstance().queryAllAppInfos();
        if (ContainerUtil.hasData(queryAllAppInfos)) {
            for (AppInfo appInfo : queryAllAppInfos) {
                if (a(isChildMode, appInfo.getModeType()) && !AppStoreManager.isHideApp(appInfo)) {
                    arrayList.add(appInfo);
                }
            }
        }
        List<AppInfo> appList = appPageData.getAppList();
        if (ContainerUtil.hasData(appList)) {
            synchronized (this.k) {
                this.l.clear();
            }
            for (AppInfo appInfo2 : appList) {
                if (!appInfo2.isHidden() && appInfo2.matchChannel("mico") && appInfo2.matchHardware(Hardware.current(context).getName()) && appInfo2.matchHardwareVersion(Hardware.current(context).getName(), RomUpdateAdapter.getInstance().getVersion().toString()) && !AppStoreManager.isHideApp(appInfo2) && a(isChildMode, appInfo2.getModeType())) {
                    synchronized (this.k) {
                        this.l.add(appInfo2);
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (CategoryTab categoryTab : appPageData.getTabList()) {
            if (a(isChildMode, categoryTab.getModeType())) {
                arrayList2.add(categoryTab);
            }
        }
        appPageData.setTabList(arrayList2);
        for (CategoryTab categoryTab2 : arrayList2) {
            ArrayList arrayList3 = new ArrayList();
            for (RecommendCard recommendCard : appPageData.getRecommendCardList()) {
                if (recommendCard.getAppTabKey().equals(categoryTab2.getAppTabKey())) {
                    arrayList3.add(recommendCard);
                }
            }
            if (ContainerUtil.hasData(arrayList3)) {
                this.n.put(categoryTab2.getAppTabKey(), arrayList3);
            }
            if (categoryTab2.isConfigurable() || "all".equals(categoryTab2.getAppTabKey())) {
                this.m.put("all", this.l);
            } else {
                if (TAB_KEY_MINE.equals(categoryTab2.getAppTabKey()) && ContainerUtil.hasData(arrayList)) {
                    for (AppInfo appInfo3 : arrayList) {
                        c(appInfo3);
                        d(appInfo3);
                    }
                }
                List<AppInfo> arrayList4 = new ArrayList<>();
                for (AppInfo appInfo4 : this.l) {
                    this.h.put(Long.valueOf(appInfo4.getAppKey()), appInfo4);
                    this.i.put(appInfo4.getPackageName(), appInfo4);
                    this.j.put(appInfo4.getMicoAction(), appInfo4);
                    if (TAB_KEY_MINE.equals(categoryTab2.getAppTabKey()) && a(isChildMode, appInfo4.getModeType())) {
                        a(arrayList4, arrayList, appInfo4);
                    }
                    if (appInfo4.getAppCategory().equals(categoryTab2.getTag())) {
                        arrayList4.add(appInfo4);
                    }
                }
                if (TAB_KEY_MINE.equals(categoryTab2.getAppTabKey())) {
                    if (ContainerUtil.hasData(arrayList)) {
                        arrayList4 = arrayList;
                    }
                    if (ContainerUtil.hasData(arrayList4)) {
                        arrayList4 = (List) arrayList4.stream().map(new Function() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$m3ges-kcrq3uBJP_mXy71xjrkTQ
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                SkillDataManager.a a2;
                                a2 = SkillDataManager.this.a((AppInfo) obj);
                                return a2;
                            }
                        }).sorted().map($$Lambda$SkillDataManager$6GHxUbJXVuPgmDHnhgBo4nAoiKY.INSTANCE).collect(Collectors.toList());
                    }
                    AppRealmHelper.getInstance().insert(arrayList4);
                }
                String tag = categoryTab2.getTag();
                if (ContainerUtil.isEmpty(tag)) {
                    tag = categoryTab2.getAppTabKey();
                }
                this.m.put(tag, arrayList4);
            }
        }
    }

    @NonNull
    public a a(AppInfo appInfo) {
        char c2;
        String appName = appInfo.getAppName();
        int hashCode = appName.hashCode();
        int i = -1;
        if (hashCode == 735243) {
            if (appName.equals(APP_WEATHER)) {
                c2 = 0;
            }
            c2 = 65535;
        } else if (hashCode != 1228230) {
            if (hashCode == 723689867 && appName.equals(APP_GALLERY)) {
                c2 = 1;
            }
            c2 = 65535;
        } else {
            if (appName.equals(APP_ALARM)) {
                c2 = 2;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                i = -3;
                break;
            case 1:
                i = -2;
                break;
            case 2:
                break;
            default:
                i = 0;
                break;
        }
        return new a(i, appInfo);
    }

    @SuppressLint({"CheckResult"})
    public void loadAppsData(final Context context) {
        this.A++;
        if (!ApiManager.isInited()) {
            L.homepage.w("loadAppsData when ApiManager init is failed");
            loadAppsDataFromCache(context);
            return;
        }
        L.homepage.w("loadAppsData start");
        ApiManager.displayService.loadAppsData().map(new io.reactivex.functions.Function() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$qJLrZE0awH62oDk7_gyC-HApGT0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                AppPageData f;
                f = SkillDataManager.this.f((String) obj);
                return f;
            }
        }).map(new io.reactivex.functions.Function() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$mMrPhRpXYcn_rRL2MOSfTUd1vdY
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                AppPageData c2;
                c2 = SkillDataManager.this.c(context, (AppPageData) obj);
                return c2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$EUSOy9OmF7G3ixZskIfcnDPc6GQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.this.b(context, (AppPageData) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$rkX14w54HDn2RoU0IQnh5Ipp5Uo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.this.a(context, (Throwable) obj);
            }
        });
    }

    public /* synthetic */ AppPageData f(String str) throws Exception {
        a(str);
        return (AppPageData) Gsons.getGson().fromJson(str, (Class<Object>) AppPageData.class);
    }

    public /* synthetic */ AppPageData c(Context context, AppPageData appPageData) throws Exception {
        a(context, appPageData);
        return appPageData;
    }

    public /* synthetic */ void b(Context context, AppPageData appPageData) throws Exception {
        a(appPageData, context);
        L.homepage.w("loadAppsData end");
    }

    public /* synthetic */ void a(Context context, Throwable th) throws Exception {
        loadAppsDataFromCache(context);
    }

    private void a(Context context) {
        if (this.A < 16) {
            Message obtain = Message.obtain();
            obtain.obj = context;
            obtain.what = 1002;
            this.g.removeMessages(1002);
            this.g.sendMessageDelayed(obtain, RandomTimeUtils.getExponentRandomTimeInMills(this.A, b));
        }
    }

    public void loadAppsDataFromCache(final Context context) {
        Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$cpIej-cJ1cNm2-mP070ECGhpQzw
            @Override // java.lang.Runnable
            public final void run() {
                SkillDataManager.this.b(context);
            }
        });
    }

    public /* synthetic */ void b(Context context) {
        AppPageData loadLocalAppData = loadLocalAppData();
        if (loadLocalAppData != null) {
            a(context, loadLocalAppData);
            a(loadLocalAppData, context);
            L.homepage.w("loadAppsData from server is fail,read cache ");
        } else {
            L.homepage.w("loadAppsData from server is fail ,cache is empty");
        }
        a(context);
    }

    public AppPageData getAppPageData() {
        return this.z;
    }

    public void setAppPageData(AppPageData appPageData) {
        this.z = appPageData;
    }

    private void a(AppPageData appPageData, Context context) {
        this.A = 0;
        setAppPageData(appPageData);
        Message obtain = Message.obtain();
        obtain.obj = context;
        obtain.what = 1002;
        this.g.removeMessages(1002);
        this.g.sendMessageDelayed(obtain, c);
        EventBusRegistry.getEventBus().post(new AppsDataLoadSuccess());
    }

    private void a(List<AppInfo> list, List<AppInfo> list2, AppInfo appInfo) {
        if (!ContainerUtil.isEmpty(list2)) {
            int b2 = b(appInfo);
            if (b2 == -1) {
                if (!appInfo.isRemovable()) {
                    list2.add(appInfo);
                    c(appInfo);
                }
            } else if (b2 < list2.size()) {
                list2.set(b2, appInfo);
            }
        } else if (!appInfo.isRemovable()) {
            list.add(appInfo);
            c(appInfo);
        }
    }

    private int b(AppInfo appInfo) {
        long appKey = appInfo.getAppKey();
        if (appKey != 0 && this.r.contains(Long.valueOf(appKey))) {
            return this.r.indexOf(Long.valueOf(appKey));
        }
        String packageName = appInfo.getPackageName();
        if (ContainerUtil.hasData(packageName) && this.s.contains(packageName)) {
            return this.s.indexOf(packageName);
        }
        String micoAction = appInfo.getMicoAction();
        if (!ContainerUtil.hasData(micoAction) || !this.t.contains(micoAction)) {
            return -1;
        }
        return this.t.indexOf(micoAction);
    }

    private void a() {
        this.o.clear();
        this.p.clear();
        this.q.clear();
        this.r.clear();
        this.t.clear();
        this.s.clear();
    }

    private void c(AppInfo appInfo) {
        if (appInfo.getAppKey() != 0) {
            this.o.add(Long.valueOf(appInfo.getAppKey()));
        }
        if (ContainerUtil.hasData(appInfo.getPackageName())) {
            this.p.add(appInfo.getPackageName());
        }
        if (ContainerUtil.hasData(appInfo.getMicoAction())) {
            this.q.add(appInfo.getMicoAction());
        }
    }

    private void d(AppInfo appInfo) {
        this.r.add(Long.valueOf(appInfo.getAppKey()));
        this.s.add(appInfo.getPackageName());
        this.t.add(appInfo.getMicoAction());
    }

    public List<RecommendCard> getRecommendCards(String str) {
        return this.n.get(str);
    }

    public List<AppInfo> getAppInfosByTabKey(String str) {
        return this.m.get(str);
    }

    public List<AppInfo> getAllAppInfos() {
        ArrayList arrayList;
        synchronized (this.k) {
            arrayList = new ArrayList(this.l);
        }
        return arrayList;
    }

    public AppInfo getAppInfoByAppKey(long j) {
        return this.h.get(Long.valueOf(j));
    }

    public HashMap<Long, AppInfo> getAppMap() {
        return this.h;
    }

    public AppInfo getAppInfoByPkg(String str) {
        return this.i.get(str);
    }

    public void addRecord(AppInfo appInfo) {
        if (appInfo == null) {
            L.skillpage.i("addRecord appinfo is null ");
            return;
        }
        long appKey = appInfo.getAppKey();
        String packageName = appInfo.getPackageName();
        String micoAction = appInfo.getMicoAction();
        if (this.o.contains(Long.valueOf(appKey))) {
            L.skillpage.i("addRecord contains appKey : %s", Long.valueOf(appKey));
        } else if (this.p.contains(packageName)) {
            L.skillpage.i("addRecord contains pkg : %s", packageName);
        } else if (this.q.contains(micoAction)) {
            L.skillpage.i("addRecord contains action : %s", micoAction);
        } else {
            L.skillpage.i("addRecord : %s", appInfo.getAppName());
            c(appInfo);
            d(appInfo);
            getAppInfosByTabKey(TAB_KEY_MINE).add(appInfo);
            AppRealmHelper.getInstance().insert(appInfo);
            EventBusRegistry.getEventBus().post(new AppInstalledEvent(appInfo));
        }
    }

    public void addRecordByPkg(String str) {
        L.skillpage.i("addRecordByPkg : %s", str);
        addRecord(this.i.get(str));
    }

    public void addRecordByAction(String str) {
        L.skillpage.i("addRecordByAction : %s", str);
        addRecord(this.j.get(str));
    }

    public void deleteRecord(AppInfo appInfo) {
        this.o.remove(Long.valueOf(appInfo.getAppKey()));
        this.p.remove(appInfo.getPackageName());
        this.q.remove(appInfo.getMicoAction());
        int b2 = b(appInfo);
        if (b2 != -1) {
            this.r.remove(b2);
            this.s.remove(b2);
            this.t.remove(b2);
        }
        getAppInfosByTabKey(TAB_KEY_MINE).remove(appInfo);
        AppRealmHelper.getInstance().delete(appInfo);
        AppRealmHelper.getInstance().insert(getManager().getAppInfosByTabKey(TAB_KEY_MINE));
    }

    @SuppressLint({"CheckResult"})
    public void loadExploreData() {
        if (ApiManager.isInited()) {
            ApiManager.minaService.loadConfig("skill_tip_recommendations").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$WP3nw0KmBTnlwqKb0G1DUbIdiYM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.e((String) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$EHvhGZikopZsDYxRyxHnYzae8i0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.b((Throwable) obj);
                }
            });
        } else {
            L.base.w("ApiManager is not inited!");
        }
    }

    public /* synthetic */ void e(String str) throws Exception {
        final Explore explore = (Explore) Gsons.getGson().fromJson(str, new TypeToken<Explore>() { // from class: com.xiaomi.micolauncher.module.skill.manager.SkillDataManager.2
        }.getType());
        if (explore == null || !ContainerUtil.hasData(explore.getExplores())) {
            b();
            return;
        }
        Threads.getIoThreadPool().execute(new Runnable() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$2NjLtv7ie1WklwIChMnq5EUQooE
            @Override // java.lang.Runnable
            public final void run() {
                SkillDataManager.b(Explore.this);
            }
        });
        EventBusRegistry.getEventBus().post(new LoadExploreDataSuccessEvent(explore));
        this.g.sendEmptyMessageDelayed(1001, c);
    }

    public static /* synthetic */ void b(Explore explore) {
        ApiRealmHelper.getInstance().updateAsync("mina/display/main_screen/explore", Gsons.getGson().toJson(explore));
    }

    public /* synthetic */ void b(Throwable th) throws Exception {
        L.skillpage.d("load explore data : ", th);
        b();
    }

    private void b() {
        if (this.f == 0) {
            c();
        }
        int i = this.f;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, b);
            Logger logger = L.launcher;
            logger.i(" refresh explore data after " + exponentRandomTimeInMills + " exponent " + this.f);
            this.g.sendEmptyMessageDelayed(1001, exponentRandomTimeInMills);
            this.f = this.f + 1;
        }
    }

    @SuppressLint({"CheckResult"})
    private void c() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$328WHcN7z9-21oZJYlIkdQUyQww
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.d(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$SkillDataManager$uSyLWJ8Udz7mPLJBo63SN2hJSd4.INSTANCE);
    }

    public /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/explore");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (Explore) Gsons.getGson().fromJson(find, new TypeToken<Explore>() { // from class: com.xiaomi.micolauncher.module.skill.manager.SkillDataManager.3
        }.getType()) : null);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void a(Explore explore) throws Exception {
        if (explore != null && ContainerUtil.hasData(explore.getExplores())) {
            EventBusRegistry.getEventBus().post(new LoadExploreDataSuccessEvent(explore));
        }
    }

    @SuppressLint({"CheckResult"})
    public void loadSkillData() {
        Observable<List<Skill.SkillBean>> loadSkillListForBigScreen = loadSkillListForBigScreen();
        if (loadSkillListForBigScreen != null) {
            loadSkillListForBigScreen.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$ecICHD4QX_VX5v7iAiwg51FfUUQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.c((List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$HI1vpQlOCT5I_27rbso45orBTuw
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.a((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void c(final List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            Threads.getIoThreadPool().execute(new Runnable() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$9TgHVZxCcGBxZIwgQCYnB8sB0QA
                @Override // java.lang.Runnable
                public final void run() {
                    SkillDataManager.d(list);
                }
            });
            EventBusRegistry.getEventBus().post(new LoadSkillDataSuccessEvent(list));
            this.g.sendEmptyMessageDelayed(1000, c);
            return;
        }
        d();
    }

    public static /* synthetic */ void d(List list) {
        ApiRealmHelper.getInstance().updateAsync("mina/display/main_screen/skills", Gsons.getGson().toJson(list));
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        L.skillpage.e("load skill data error : ", th);
        d();
    }

    private void d() {
        if (this.e == 0) {
            loadSkillFromDB();
        }
        int i = this.e;
        if (i < 16) {
            long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, b);
            Logger logger = L.launcher;
            logger.i(" refresh skill data after " + exponentRandomTimeInMills + " exponent " + this.e);
            this.g.sendEmptyMessageDelayed(1000, exponentRandomTimeInMills);
            this.e = this.e + 1;
        }
    }

    @SuppressLint({"CheckResult"})
    public void loadSkillFromDB() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$JvVERP1bA_5lfEImfSBitRu36sg
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.c(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$SkillDataManager$Bo4vY4KNFFsNSl3IZ_5qMf_jkLE.INSTANCE);
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("mina/display/main_screen/skills");
        List arrayList = new ArrayList();
        if (!TextUtils.isEmpty(find)) {
            arrayList = (List) Gsons.getGson().fromJson(find, new TypeToken<List<Skill.SkillBean>>() { // from class: com.xiaomi.micolauncher.module.skill.manager.SkillDataManager.4
            }.getType());
        }
        observableEmitter.onNext(arrayList);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void b(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            EventBusRegistry.getEventBus().post(new LoadSkillDataSuccessEvent(list));
        }
    }

    public Observable<List<Skill.SkillBean>> loadSkillListForBigScreen() {
        if (ApiManager.isInited()) {
            return ApiManager.minaService.loadConfig("skill_info_list_hd").map(new io.reactivex.functions.Function() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$ehxkGWfAZi_ErYLZvC3yUPOlQyc
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    List d;
                    d = SkillDataManager.this.d((String) obj);
                    return d;
                }
            });
        }
        return null;
    }

    public /* synthetic */ List d(String str) throws Exception {
        return (List) Gsons.getGson().fromJson(str, new TypeToken<List<Skill.SkillBean>>() { // from class: com.xiaomi.micolauncher.module.skill.manager.SkillDataManager.5
        }.getType());
    }

    @SuppressLint({"CheckResult"})
    public Observable<List<Skill.SkillInfo>> loadSkillInfoList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$wHU80rD61-H85Ww239T4oJXu_LA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.b(observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(final ObservableEmitter observableEmitter) throws Exception {
        List<Skill.SkillInfo> list = this.v;
        if (list == null || list.size() <= 0) {
            Observable<List<Skill.SkillBean>> loadSkillListForBigScreen = Hardware.isBigScreen() ? loadSkillListForBigScreen() : loadSkillList();
            if (loadSkillListForBigScreen != null) {
                loadSkillListForBigScreen.flatMap($$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM.INSTANCE).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$Vkn-ZyoOH_WmmkvyCYv60lPACEk
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SkillDataManager.this.b(observableEmitter, (List) obj);
                    }
                }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$q3hzoLCT8Z4DIWN06fJW5txo3fg
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SkillDataManager.d(ObservableEmitter.this, (Throwable) obj);
                    }
                });
                return;
            }
            return;
        }
        observableEmitter.onNext(this.v);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ ObservableSource a(List list) throws Exception {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Skill.SkillBean) it.next()).id);
        }
        return getManager().loadSkillInfo(arrayList);
    }

    public /* synthetic */ void b(ObservableEmitter observableEmitter, List list) throws Exception {
        this.v = list;
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void d(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    @SuppressLint({"CheckResult"})
    public Observable<Skill.SkillInfo> loadSkillInfo(@NotNull final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$yxMAPjkp4nnnXvK4tVJJNCdCHvU
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.b(str, observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(@NotNull final String str, final ObservableEmitter observableEmitter) throws Exception {
        Skill.SkillInfo b2 = b(str);
        if (b2 != null) {
            observableEmitter.onNext(b2);
            observableEmitter.onComplete();
            return;
        }
        loadSkillInfoList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$U54CfEUBiWqywXI3tWsq8BId4YE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.this.a(observableEmitter, str, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$Ja4HC0Ghpp6DtADXfnSNFbz5g4I
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.c(ObservableEmitter.this, (Throwable) obj);
            }
        });
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, @NotNull String str, List list) throws Exception {
        observableEmitter.onNext(b(str));
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void c(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private Skill.SkillInfo b(@NotNull String str) {
        List<Skill.SkillInfo> list = this.v;
        if (list == null) {
            return null;
        }
        for (Skill.SkillInfo skillInfo : list) {
            if (skillInfo != null && str.equals(skillInfo.id)) {
                return skillInfo;
            }
        }
        return null;
    }

    public Observable<List<Skill.SkillInfo>> loadSkillInfo(List<String> list) {
        return ApiManager.displayService.getSkillInfo(Gsons.getGson().toJson(list));
    }

    @SuppressLint({"CheckResult"})
    public Observable<List<Skill.SkillType>> loadSkillTypeList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$rdNlPOydtZ6QSrCvW_dM4VxQtqI
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final ObservableEmitter observableEmitter) throws Exception {
        List<Skill.SkillType> list = this.w;
        if (list == null || list.size() <= 0) {
            ApiManager.displayService.getSkillTypeList().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$9IybWSf0JVMBsnrAqHQBwTSwgnk
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.this.a(observableEmitter, (List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$JDJc0eBYcztTh6fGdz7YaQpi8Mg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SkillDataManager.b(ObservableEmitter.this, (Throwable) obj);
                }
            });
            return;
        }
        observableEmitter.onNext(this.w);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, List list) throws Exception {
        this.w = list;
        observableEmitter.onNext(list);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void b(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    @SuppressLint({"CheckResult"})
    public Observable<Skill.SkillType> loadSkillTypeById(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$S35Hrc-dpmr2eoFmqIPp7kRiKcU
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillDataManager.this.a(str, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(String str, final ObservableEmitter observableEmitter) throws Exception {
        Skill.SkillType c2 = c(str);
        if (c2 != null) {
            observableEmitter.onNext(c2);
            observableEmitter.onComplete();
            return;
        }
        ApiManager.displayService.getSkillTypeById(str).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$-gAID-wyki5fC965NBgBdtdgeko
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.a(ObservableEmitter.this, (Skill.SkillType) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$HeB5E5r4a6Ut5CaJ9oaSKCSDhgc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDataManager.a(ObservableEmitter.this, (Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter, Skill.SkillType skillType) throws Exception {
        observableEmitter.onNext(skillType);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private Skill.SkillType c(String str) {
        List<Skill.SkillType> list = this.w;
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (Skill.SkillType skillType : this.w) {
            if (str.equals(skillType.id)) {
                return skillType;
            }
        }
        return null;
    }

    public Observable<List<SkillStore.SkillV2>> loadCategorySkills(String str, int i, int i2, String str2) {
        return ApiManager.skillstoreService.getCategorySkills(str, i, i2, str2);
    }

    public Observable<SkillStore.SkillDetailV2> getSkillDetail(String str, String str2) {
        return ApiManager.skillstoreService.getSkillDetail(str, str2);
    }

    private boolean e() {
        return Math.abs(System.currentTimeMillis() - this.d) <= a;
    }

    /* loaded from: classes3.dex */
    public static class a implements Comparable<a> {
        public int a;
        public AppInfo b;

        public a(int i, AppInfo appInfo) {
            this.a = i;
            this.b = appInfo;
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            return Integer.compare(this.a, aVar.a);
        }
    }
}
