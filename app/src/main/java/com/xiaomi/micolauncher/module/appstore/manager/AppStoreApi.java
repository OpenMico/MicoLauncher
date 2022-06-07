package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.mico.appstore.IAppStoreServiceCallback;
import com.xiaomi.mico.appstore.IAppstoreService;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AppStoreClearEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.FileSizeUtil;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
public class AppStoreApi {
    public static final String APPSTORE_BIND_ACTION = "com.xiaomi.mico.appstore.action.BIND";
    public static final String APPSTORE_PKG_NAME = "com.xiaomi.mico.appstore";
    public static final int DOWNLOAD_STATE_CHECKING = -1;
    public static final int DOWNLOAD_STATE_NOT_ENOUGH_SPACE = -2;

    /* loaded from: classes3.dex */
    public static class a {
        ServiceConnection a;
        Context b;
        private boolean c;
        private CountDownLatch d = new CountDownLatch(1);

        public a(Context context) {
            this.b = context;
        }

        void a(ServiceConnection serviceConnection, boolean z) {
            this.a = serviceConnection;
            this.c = z;
            try {
                this.d.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void a() {
            this.d.countDown();
        }

        void b() {
            Threads.getHeavyWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$a$HEV49q0AdifbEQuNZFB9alwC7C8
                @Override // java.lang.Runnable
                public final void run() {
                    AppStoreApi.a.this.c();
                }
            });
        }

        public /* synthetic */ void c() {
            if (this.a != null && this.c) {
                L.storage.d("%s unbind AppStore Service", "AppStoreApi");
                this.b.unbindService(this.a);
                this.a = null;
                this.c = false;
            }
        }
    }

    static boolean a(Context context, String str, AppInfo appInfo) {
        int installedPackageVersionCode = AppInstallManager.getInstalledPackageVersionCode(context, str);
        L.storage.i("%s pkgName=%s pkgVersion=%s storePkg=%s storeVersion=%s", "AppStoreApi", str, Integer.valueOf(installedPackageVersionCode), appInfo.getPackageName(), Long.valueOf(appInfo.getVersionCode()));
        if (installedPackageVersionCode < appInfo.getVersionCode()) {
            return !appInfo.isOnline() && installedPackageVersionCode > 0;
        }
        return true;
    }

    public static boolean handleAppWithPkgName(Context context, String str, Runnable runnable) {
        return handleAppWithPkgName(context, str, true, runnable);
    }

    public static boolean handleAppWithPkgName(final Context context, String str, boolean z, final Runnable runnable) {
        final AppInfo appinfoByPackageName = AppStoreManager.getManager().getAppinfoByPackageName(str);
        if (appinfoByPackageName != null) {
            if (a(context, str, appinfoByPackageName)) {
                a(context, str, z, runnable);
            } else if (appinfoByPackageName.isOnline()) {
                if (AppStoreManager.getManager().isDisableInstallApp()) {
                    L.storage.i("%s pkgName=%s not installed for NoDownloadExternalApp", "AppStoreApi", str);
                    return true;
                } else if (!CommonUtils.isPackageInstalled(context, str) && !appinfoByPackageName.isOnline()) {
                    SpeechManager.getInstance().ttsRequest(context.getString(R.string.local_tts_not_found_app));
                    return false;
                } else if (!z || !Hardware.isBigScreen() || !LockSetting.getHasLock(context, str)) {
                    b(context, appinfoByPackageName, runnable);
                } else {
                    ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$rYGnLd_T2cfm9-6VUzq1scU89E0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppStoreApi.c(context, appinfoByPackageName, runnable);
                        }
                    });
                }
            }
            return true;
        } else if (!CommonUtils.isPackageInstalled(context, str)) {
            return false;
        } else {
            a(context, str, z, runnable);
            return true;
        }
    }

    public static /* synthetic */ void c(final Context context, final AppInfo appInfo, final Runnable runnable) {
        new LockAppDialog(context, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$6XLoEKzj1z1EVjocoDldEeQYkC0
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                AppStoreApi.b(context, appInfo, runnable);
            }
        }).show();
    }

    private static void a(final Context context, String str, boolean z, final Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (!z || !Hardware.isBigScreen() || !LockSetting.getHasLock(context, str)) {
            runnable.run();
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$4fkmHxbNGJ43i-9SyOxUoU2ARh8
                @Override // java.lang.Runnable
                public final void run() {
                    AppStoreApi.a(context, runnable);
                }
            });
        }
    }

    public static /* synthetic */ void a(Context context, final Runnable runnable) {
        runnable.getClass();
        new LockAppDialog(context, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$tuOdmdVhq36nuKLWdu3ncZ7mHGQ
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                runnable.run();
            }
        }).show();
    }

    private static void a(Context context, final AppInfo appInfo, final Runnable runnable) {
        final a aVar = new a(context);
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi.1
            IAppstoreService a;

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                this.a = IAppstoreService.Stub.asInterface(iBinder);
                try {
                    aVar.a();
                    L.storage.d("%s service start installApp package=%s", "AppStoreApi", appInfo.getPackageName());
                    this.a.installApp(appInfo.toJson(), new IAppStoreServiceCallback.Stub() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi.1.1
                        @Override // com.xiaomi.mico.appstore.IAppStoreServiceCallback
                        public void callback(int i, String str) {
                            if (i == 2) {
                                AppStoreApi.notifyFreeSpaceNotEnough();
                            } else if (i == 0) {
                                L.storage.d("%s service installApp complete package=%s", "AppStoreApi", appInfo.getPackageName());
                                if (runnable != null) {
                                    ThirdPartyAppProxy.getInstance().setOnLauncherResumeRunnable(runnable);
                                }
                            }
                        }
                    });
                    aVar.b();
                } catch (RemoteException e) {
                    L.storage.e("AppStoreApi handleAppWithPkgName failed to installApp", e);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.a = null;
                L.storage.e("%s installApp onServiceDisconnected", "AppStoreApi");
            }
        };
        Intent intent = new Intent();
        intent.setAction(APPSTORE_BIND_ACTION);
        intent.setPackage(APPSTORE_PKG_NAME);
        boolean bindService = context.bindService(intent, serviceConnection, 1);
        L.storage.d("%s bind appstore service connected=%s", "AppStoreApi", Boolean.valueOf(bindService));
        aVar.a(serviceConnection, bindService);
    }

    public static void b(final Context context, final AppInfo appInfo, final Runnable runnable) {
        if (!NetworkMonitor.getInstance().isWifiConnected(true)) {
            ToastUtil.showToast((int) R.string.activity_app_download_status_error_hint);
        } else {
            Observable.fromCallable(new Callable() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$LYBfLOVzi1SvrgKnYCEVBqGsUiQ
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Long a2;
                    a2 = AppStoreApi.a(context);
                    return a2;
                }
            }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreApi$SGrsFijs6tY8uyXLTNF5Aki9vmY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppStoreApi.a(context, appInfo, runnable, (Long) obj);
                }
            }, $$Lambda$AppStoreApi$gmJY2GNxPPMZBSezohR3e75v69U.INSTANCE);
        }
    }

    public static /* synthetic */ Long a(Context context) throws Exception {
        return Long.valueOf(FileSizeUtil.getStorageFreeSizeSync(context));
    }

    public static /* synthetic */ void a(Context context, AppInfo appInfo, Runnable runnable, Long l) throws Exception {
        if (l.longValue() < 256000000) {
            notifyFreeSpaceNotEnough();
        } else {
            a(context, appInfo, runnable);
        }
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.storage.e("AppStoreApi checkStorageAndInstall failed to installApp", th);
    }

    public static Observable<String> deepClearApps(Context context, String str) {
        return AppStoreUtil.deepClearApps(context, str);
    }

    public static void notifyFreeSpaceNotEnough() {
        EventBusRegistry.getEventBus().post(new AppStoreClearEvent(false));
    }
}
