package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.event.AppStoreListChange;
import com.xiaomi.micolauncher.common.event.LoginResultEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppStoreManager {
    public static final String KEY_APP_STORAGE_LAST_ALARM_TIME = "KEY_APP_STORAGE_LAST_ALARM_TIME";
    public static final String MESH_GATEWAY_PKGNAME = "com.xiaomi.mesh.gateway";
    private static final long a = TimeUnit.DAYS.toMillis(1);
    private static String b = Environment.getExternalStorageDirectory() + File.separator + "NoDownloadExternalApp";
    private static final long c = TimeUnit.MINUTES.toMillis(15);
    private static final long d = TimeUnit.MINUTES.toMillis(5);
    private List<AppInfo> e;
    private volatile Context f;
    private volatile boolean g;
    private volatile long h;
    private volatile boolean i;
    private boolean j;
    private Handler k;

    private AppStoreManager() {
        this.g = false;
        this.h = 0L;
        this.i = false;
        this.j = false;
        this.k = new MicoHandler(ThreadUtil.getWorkHandler().getLooper()) { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager.2
            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public String getLogTag() {
                return "AppStoreManager";
            }

            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public void processMessage(Message message) {
                switch (message.what) {
                    case 0:
                        AppStoreManager.this.b();
                        return;
                    case 1:
                        AppStoreManager appStoreManager = AppStoreManager.this;
                        appStoreManager.init(appStoreManager.f);
                        return;
                    case 2:
                        if (AppStoreManager.this.i && !AppStoreManager.this.j) {
                            AppStoreUtil.silentUpgradeApps(AppStoreManager.this.f, "com.xiaomi.mesh.gateway:com.miui.hybrid.soundbox");
                            AppStoreManager.this.i = false;
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final AppStoreManager a = new AppStoreManager();
    }

    public static AppStoreManager getManager() {
        return a.a;
    }

    public void initBeforeLogin(Context context) {
        this.f = context;
        if (new File(b).exists()) {
            this.j = true;
        }
        EventBusRegistry.getEventBus().register(this);
        if (SystemSetting.isInitialized(context)) {
            this.k.sendEmptyMessageDelayed(0, d);
        } else {
            d();
        }
    }

    private void d() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction() != null && "android.provision.action.PROVISION_COMPLETE".equals(intent.getAction())) {
                    AppStoreManager.this.k.sendEmptyMessageDelayed(0, AppStoreManager.d);
                    context.unregisterReceiver(this);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provision.action.PROVISION_COMPLETE");
        this.f.registerReceiver(broadcastReceiver, intentFilter);
    }

    public boolean isDisableInstallApp() {
        return this.j;
    }

    public void init(Context context) {
        getAppInfoList().subscribeOn(MicoSchedulers.io()).subscribe();
        AppStoreOtaUtils.getInstance().setCurrentVer(AppStoreOtaUtils.getInstance().getVersionName(this.f, "com.xiaomi.mesh.gateway"));
    }

    public Observable<List<AppInfo>> getAppInfoList() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreManager$yFDN5ywnLiX8TbtZHzYb3h4zbuo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppStoreManager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        if (this.e == null) {
            L.storage.i("AppStoreManager get applist data from db");
            String find = ApiRealmHelper.getInstance().find(a());
            if (!TextUtils.isEmpty(find)) {
                this.e = (List) Gsons.getGson().fromJson(find, new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager.3
                }.getType());
                e();
            } else {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.f.getResources().openRawResource(R.raw.appstore_default_list)));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                        sb.append("\n");
                    }
                    this.e = (List) Gsons.getGson().fromJson(sb.toString(), new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager.4
                    }.getType());
                    e();
                    bufferedReader.close();
                } catch (IOException e) {
                    L.storage.e("load app store default list : ", e);
                }
                setNeedUpgradeApps(true);
            }
        }
        checkIfGetAppListFromServer();
        observableEmitter.onNext(this.e);
        observableEmitter.onComplete();
    }

    String a() {
        return ApiConstants.isProductionEnv() ? "apk_management" : "apk_management_preview";
    }

    public Observable<List<AppInfo>> getAppInfoListFromServer() {
        final String a2 = a();
        if (ApiManager.isInited()) {
            return ApiManager.minaService.loadConfig(a2).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreManager$QkIdYs1N8JvvYls9MgjWKptKrpk
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppStoreManager.this.a(a2, (String) obj);
                }
            }).map(new Function() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreManager$qddgBjJjMEW2RDqmrV43_xgoGwQ
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    List a3;
                    a3 = AppStoreManager.this.a((String) obj);
                    return a3;
                }
            }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreManager$GD92UMFlCOR0R9fPOQzBpaVTggU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppStoreManager.this.a((List) obj);
                }
            });
        }
        return null;
    }

    public /* synthetic */ void a(String str, String str2) throws Exception {
        this.g = true;
        L.storage.i("AppStoreManager got applist fromServer from server and updatedb");
        if (TextUtils.isEmpty(ApiRealmHelper.getInstance().find2(str))) {
            setNeedUpgradeApps(true);
        }
        ApiRealmHelper.getInstance().updateAsync(str, str2);
        this.h = System.currentTimeMillis();
    }

    public /* synthetic */ List a(String str) throws Exception {
        return (List) Gsons.getGson().fromJson(str, new TypeToken<CopyOnWriteArrayList<AppInfo>>() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager.5
        }.getType());
    }

    public /* synthetic */ void a(List list) throws Exception {
        this.e = list;
        e();
        EventBusRegistry.getEventBus().post(new AppStoreListChange());
        AppDownloadManager.getAppDownloadManager().inalidateHistoryState();
        this.k.sendEmptyMessage(2);
    }

    public AppInfo getAppinfoByPackageName(String str) {
        List<AppInfo> list = this.e;
        if (list == null) {
            return null;
        }
        for (AppInfo appInfo : list) {
            if (TextUtils.equals(str, appInfo.getPackageName())) {
                return appInfo;
            }
        }
        return null;
    }

    public void checkIfGetAppListFromServer() {
        Observable<List<AppInfo>> appInfoListFromServer;
        String a2 = a();
        if (this.h == 0) {
            this.h = ApiRealmHelper.getInstance().lastUpdateTime(a2);
            L.storage.i("AppStoreManager checkIfGetAppListFromServer lastCacheAppListTime=%s", Long.valueOf(this.h));
        }
        if (this.f != null) {
            if ((!this.g || System.currentTimeMillis() - this.h >= a) && NetworkMonitor.getInstance().isWifiConnected() && (appInfoListFromServer = getAppInfoListFromServer()) != null) {
                appInfoListFromServer.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.computation()).subscribe();
            }
        }
    }

    private void e() {
        String str = SystemProperties.get("ro.mi.sw_channel");
        for (AppInfo appInfo : this.e) {
            if (!isHideApp(appInfo)) {
                if (!appInfo.matchChannel("mico") && !appInfo.matchChannel(str)) {
                    this.e.remove(appInfo);
                } else if (!appInfo.matchHardware(Hardware.current(this.f).getName()) || !appInfo.matchHardwareVersion(Hardware.current(this.f).getName(), RomUpdateAdapter.getInstance().getVersion().toString())) {
                    this.e.remove(appInfo);
                }
            }
        }
    }

    public static boolean isHideApp(AppInfo appInfo) {
        if (!TextUtils.equals(appInfo.getAppName(), "米家") || !SkillPath.MIOT.equals(appInfo.getMicoAction())) {
            return TextUtils.equals(appInfo.getAppName(), "通话") && SkillPath.CONTACTS.equals(appInfo.getMicoAction());
        }
        return true;
    }

    void b() {
        L.storage.i("AppStoreManager callAppStoreTimer");
        this.k.sendEmptyMessageDelayed(0, c);
        if (VoipModel.getInstance().isVoipActive() || PlayerApi.isPlaying() || MicoApplication.isVideoInForeground() || ThirdPartyAppProxy.getInstance().isThirdPartyAppInForeground(this.f)) {
            L.storage.w("%s ignore runAppStoreTimer because the device is busying now", "AppStoreManager");
        } else {
            AppStoreUtil.runAppStoreTimer(this.f, true ^ this.j);
        }
    }

    public void setNeedUpgradeApps(boolean z) {
        this.i = z;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginResult(LoginResultEvent loginResultEvent) {
        L.storage.i("AppStoreManager onLoginResult %s", Boolean.valueOf(loginResultEvent.getResult()));
        if (loginResultEvent.getResult()) {
            setNeedUpgradeApps(true);
        }
    }
}
