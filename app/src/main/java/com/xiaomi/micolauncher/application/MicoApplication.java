package com.xiaomi.micolauncher.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import androidx.multidex.MultiDexApplication;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.bumptech.glide.Glide;
import com.elvishew.xlog.Logger;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.milink.kit.MiLinkKitComponentConfig$ComponentConfiguration;
import com.umeng.commonsdk.UMConfigure;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.mico.settingslib.core.MicoSettingsManager;
import com.xiaomi.micolauncher.application.setup.LogSetup;
import com.xiaomi.micolauncher.application.setup.UMengSetup;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.AppComponentProxy;
import com.xiaomi.micolauncher.common.player.MiplaySetupUtil;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenOpenManager;
import com.xiaomi.micolauncher.module.multicp.HookThirdPartyAppsService;
import com.xiaomi.micolauncher.module.multicp.MultiCpUtils;
import com.xiaomi.miot.support.MicoSupConstants;
import miuix.arch.component.AppComponentManagerConfig;

@AppComponentManagerConfig(domain = "com.xiaomi.micolauncher", subComponentManagerConfig = {MiLinkKitComponentConfig$ComponentConfiguration.class})
/* loaded from: classes3.dex */
public class MicoApplication extends MultiDexApplication {
    @Deprecated
    private static MicoApplication a;
    private final SetupManager b = SetupManager.getInstance();
    private boolean c = false;

    @Deprecated
    public static Activity getTopActivity() {
        return a().getTopActivityOrLauncher();
    }

    @Deprecated
    public static <T extends Activity> boolean hasActivityOnStack(Class<T> cls) {
        return a().hasActivityOnStack(cls);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        Log.d("MICO.startUpProfile", " Application attachBaseContext");
        Proxies.Instance.setApp(context);
        a(context);
        super.attachBaseContext(context);
        this.c = ProcessChecker.isMainProcess(context);
        if (this.c) {
            AppComponentProxy.attachBaseContext(this);
        }
    }

    @Deprecated
    public static Context getGlobalContext() {
        return a;
    }

    public static MicoApplication getApp() {
        return a;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Log.e(MicoSupConstants.TAG_LAU, "Info: micoLauncher Application onCreate!");
        a = this;
        TypefaceUtil.init(this);
        Settings.System.putLong(getContentResolver(), "screen_off_timeout", 2147483647L);
        if (((PowerManager) getSystemService("power")).isInteractive()) {
            ScreenUtil.turnScreenOn(this);
        }
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            L.startUpProfile.d("MicoApplication onCreate in PhoenixProcess");
            return;
        }
        String currentProcessName = ProcessChecker.getCurrentProcessName(this);
        if (ProcessChecker.isUnknownProcess(currentProcessName)) {
            Logger logger = L.startUpProfile;
            logger.d("MicoApplication onCreate in unknown process: " + currentProcessName);
            ProcessChecker.quitProcessOnUnknownProcess();
            return;
        }
        if (this.c) {
            AppComponentProxy.onCreate();
            Logger logger2 = L.startUpProfile;
            logger2.d("MicoApplication onCreate in " + currentProcessName);
            MiplaySetupUtil.setup(this);
        }
        if (!ProcessChecker.isMainProcess(this, currentProcessName)) {
            Logger logger3 = L.startUpProfile;
            logger3.d("MicoApplication onCreate in " + currentProcessName);
            return;
        }
        L.startUpProfile.d("MICO.startUpProfile", "[main]--------MicoApplication Start--------");
        Glide.with(a);
        if (!DebugHelper.isDebugVersion()) {
            UMConfigure.preInit(this, UMengSetup.APP_KEY, "XiaoMi");
        }
        this.b.setup(this);
        a().a((Application) this);
        BGASwipeBackHelper.init(this, null);
        if (!MultiCpUtils.isAccessibilitySettingsOn(this, HookThirdPartyAppsService.class)) {
            MultiCpUtils.enableAccessibilityService();
        }
        Logger logger4 = L.startUpProfile;
        logger4.d("--------MicoApplication onCreate finish, cost:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
    }

    private void a(Context context) {
        LogSetup instance = LogSetup.getInstance();
        if (!instance.isSetup()) {
            instance.setup(context);
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        this.b.destroy();
        L.base.w("onTerminate : app gets killed");
        super.onTerminate();
        if (this.c) {
            AppComponentProxy.onTerminate();
        }
        MicoSettingsManager.getInstance().unRegister(this);
    }

    public static boolean isVideoInForeground() {
        return ActivityLifeCycleManager.getInstance().isVideoInForeground();
    }

    private static ActivityLifeCycleManager a() {
        return ActivityLifeCycleManager.getInstance();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Log.w(LogSetup.TAG, "onLowMemory");
        Glide.get(getApplicationContext()).onLowMemory();
        GlideUtils.clearCache(a);
        LockScreenOpenManager.unbindScreenSaverKeepAliveService(this);
        if (this.c) {
            AppComponentProxy.onLowMemory();
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        Log.w(LogSetup.TAG, "onTrimMemory " + i);
        if (this.c) {
            AppComponentProxy.onTrimMemory(i);
            Glide.get(getApplicationContext()).onTrimMemory(i);
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.c) {
            AppComponentProxy.onConfigurationChanged(configuration);
        }
    }
}
