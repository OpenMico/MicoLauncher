package com.xiaomi.micolauncher.application;

import android.app.job.JobScheduler;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.mico.utils.LogCallback;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.utils.UtilsConfig;
import com.xiaomi.micolauncher.application.setup.AppStoreSetupBeforeLogin;
import com.xiaomi.micolauncher.application.setup.BatteryMonitorSetup;
import com.xiaomi.micolauncher.application.setup.BluetoothAudioRelaySetup;
import com.xiaomi.micolauncher.application.setup.BluetoothSettingSetup;
import com.xiaomi.micolauncher.application.setup.BrainSetup;
import com.xiaomi.micolauncher.application.setup.CacheSetup;
import com.xiaomi.micolauncher.application.setup.Camera2VisualRecognitionSetup;
import com.xiaomi.micolauncher.application.setup.ChildModeSetUp;
import com.xiaomi.micolauncher.application.setup.DebugConfigSetup;
import com.xiaomi.micolauncher.application.setup.DrawableSetup;
import com.xiaomi.micolauncher.application.setup.EarlySetups;
import com.xiaomi.micolauncher.application.setup.EventBusSetup;
import com.xiaomi.micolauncher.application.setup.FlowPeakCacheJobSetup;
import com.xiaomi.micolauncher.application.setup.HourlyChimeSetup;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.application.setup.IqiyiModelSetup;
import com.xiaomi.micolauncher.application.setup.LoadAdSetup;
import com.xiaomi.micolauncher.application.setup.LoginAndApiSetup;
import com.xiaomi.micolauncher.application.setup.MiotSetup;
import com.xiaomi.micolauncher.application.setup.OthersSetup;
import com.xiaomi.micolauncher.application.setup.PushSetup;
import com.xiaomi.micolauncher.application.setup.RealmSetup;
import com.xiaomi.micolauncher.application.setup.ReceiversManager;
import com.xiaomi.micolauncher.application.setup.ScreenOnOffSetup;
import com.xiaomi.micolauncher.application.setup.SettingsConfigSetup;
import com.xiaomi.micolauncher.application.setup.StatSetup;
import com.xiaomi.micolauncher.application.setup.StrictModeSetup;
import com.xiaomi.micolauncher.application.setup.SystemUIStateUpdateSetup;
import com.xiaomi.micolauncher.application.setup.TrackLogSetup;
import com.xiaomi.micolauncher.application.setup.UMengSetup;
import com.xiaomi.micolauncher.application.setup.UncaughtExceptionHandlerSetup;
import com.xiaomi.micolauncher.application.setup.UpdateSetup;
import com.xiaomi.micolauncher.application.setup.ViewHoldSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.AppStoreSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.AsynclyHomeModelsSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.DlnaSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.MediaSessionControllerSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.MiTvSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.MicoVoipModelSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.QuickAppSetup;
import com.xiaomi.micolauncher.application.setup.afterlogin.ThirdPartyAppsSetup;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class SetupManager {
    public static final String TAG = "SetupManager";
    private static final SetupManager a = new SetupManager();
    private static final long b = TimeUnit.SECONDS.toMillis(5);
    private static final int c = (Runtime.getRuntime().availableProcessors() - 1) * 2;
    private volatile boolean d;
    private ISetupRule[] e = {new LoginAndApiSetup(), new MiotSetup(), new EarlySetups(), new StrictModeSetup(), new CacheSetup(), new RealmSetup(), new UncaughtExceptionHandlerSetup(), new StatSetup(), new ReceiversManager(), new FlowPeakCacheJobSetup(), new AppStoreSetupBeforeLogin(), new ViewHoldSetup()};
    private a f = a.a(this.e);
    private a g = a.a(new ISetupRule[]{new BrainSetup()});
    private a h = a.a(new ISetupRule[]{new SettingsConfigSetup(), new DebugConfigSetup(), new ChildModeSetUp(), new OthersSetup(), new EventBusSetup(), new DrawableSetup(), new ScreenOnOffSetup(), new SystemUIStateUpdateSetup(), new BatteryMonitorSetup(), new MicoVoipModelSetup(), new UpdateSetup(), new PushSetup(), new BluetoothSettingSetup(), new MediaSessionControllerSetup(), new AsynclyHomeModelsSetup(), new MiTvSetup(), new DlnaSetup(), new IqiyiModelSetup(), new Camera2VisualRecognitionSetup(), ThirdPartyAppsSetup.getInstance(), new QuickAppSetup(), new LoadAdSetup(), new AppStoreSetup(), new BluetoothAudioRelaySetup(), new UMengSetup(), new TrackLogSetup(), new HourlyChimeSetup()});
    private a[] i = {this.f, this.g, this.h};
    private final AtomicInteger j = new AtomicInteger();
    private final AtomicInteger k = new AtomicInteger();
    private volatile boolean l = false;
    private ThreadPoolExecutor m = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public boolean isAllSetUpDone() {
        return this.d;
    }

    private SetupManager() {
    }

    public static SetupManager getInstance() {
        return a;
    }

    public void setup(@NonNull final Context context) {
        ((JobScheduler) context.getSystemService(JobScheduler.class)).cancelAll();
        a();
        Log.i(TAG, "entry setup " + context + " thread " + Thread.currentThread().getName());
        a(context, this.f);
        if (SystemSetting.isInitialized(context)) {
            Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$SetupManager$5iVtN4g_DGoV52qYFlCscp7_nrs
                @Override // java.lang.Runnable
                public final void run() {
                    SetupManager.this.a(context);
                }
            }, b);
        }
    }

    public /* synthetic */ void a(@NonNull Context context) {
        setupAfterLoginEagerStage(context);
        setupAfterLoginNotEagerStage(context);
    }

    public void setupAfterLoginEagerStage(Context context) {
        if (!SystemSetting.isInitialized(context)) {
            L.init.e("setupAfterLoginEagerStage : call setupIfNeed before initialized");
        } else {
            a(context, this.g);
        }
    }

    public void setupAfterLoginNotEagerStage(Context context) {
        if (!SystemSetting.isInitialized(context)) {
            L.init.e("setupAfterLoginNotEagerStage : call setupIfNeed before initialized");
            return;
        }
        a(context, this.h);
        this.l = true;
    }

    private void a(Context context, a aVar) {
        if (aVar.a()) {
            a(context, aVar.a);
        }
    }

    private void a(final Context context, ISetupRule[] iSetupRuleArr) {
        for (final ISetupRule iSetupRule : iSetupRuleArr) {
            a("set up %s", iSetupRule);
            if (iSetupRule.mustBeSync(context)) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                String simpleName = iSetupRule.getClass().getSimpleName();
                long beginTiming = DebugHelper.beginTiming("SyncSetup %s", simpleName);
                iSetupRule.setup(context);
                DebugHelper.endTiming(beginTiming, "SyncSetup %s complete", simpleName);
                L.startUpProfile.d("setup " + iSetupRule.getClass().getSimpleName() + " cost " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
            } else {
                this.j.incrementAndGet();
                this.m.submit(new Runnable() { // from class: com.xiaomi.micolauncher.application.SetupManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int incrementAndGet = SetupManager.this.k.incrementAndGet();
                        String simpleName2 = iSetupRule.getClass().getSimpleName();
                        L.startUpProfile.d("开始执行第[%d]个AsyncSetup: %s", Integer.valueOf(incrementAndGet), simpleName2);
                        long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        iSetupRule.setup(context);
                        L.startUpProfile.d("已成功执行第[%d]个AsyncSetup: <%s> ,cost %s ms", Integer.valueOf(incrementAndGet), simpleName2, Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime2));
                        if (SetupManager.this.k.get() == SetupManager.this.j.get() && SetupManager.this.l) {
                            SetupManager.this.d = true;
                            L.startUpProfile.i("异步Setup全部成功执行了!");
                            SetupManager.this.m.shutdownNow();
                            SetupManager.this.m = null;
                        }
                    }

                    public String toString() {
                        return iSetupRule.toString();
                    }
                });
            }
        }
        L.startUpProfile.d("async setup 一共" + this.j.get() + " 个");
    }

    private void a(String str, Object... objArr) {
        L.init.i(str, objArr);
    }

    private void a() {
        UtilsConfig.setIoThreadsCount(c);
        UtilsConfig.setComputationThreadsCount(2);
        UtilsConfig.setCrashOnBugDetected(false);
        UtilsConfig.setEnableTracing(false);
        UtilsConfig.setThreadPriority(0);
        UtilsConfig.setLogCallback(new LogCallback() { // from class: com.xiaomi.micolauncher.application.SetupManager.2
            @Override // com.xiaomi.mico.utils.LogCallback
            public void v(String str, Object... objArr) {
                L.util.v(str, objArr);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void d(String str, Object... objArr) {
                L.util.d(str, objArr);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void i(String str, Object... objArr) {
                L.util.i(str, objArr);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void w(String str, Object... objArr) {
                L.util.w(str, objArr);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void e(String str, Object... objArr) {
                L.util.e(str, objArr);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void v(String str, Throwable th) {
                L.util.v(str, th);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void d(String str, Throwable th) {
                L.util.d(str, th);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void i(String str, Throwable th) {
                L.util.i(str, th);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void w(String str, Throwable th) {
                L.util.w(str, th);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void e(String str, Throwable th) {
                L.util.e(str, th);
            }

            @Override // com.xiaomi.mico.utils.LogCallback
            public void printStackTrace(String str) {
                DebugHelper.printStackTrace(str, L.util);
            }
        });
    }

    public void destroy() {
        this.f.b();
        this.g.b();
        this.h.b();
    }

    public void a(ISetupRule[] iSetupRuleArr) {
        for (ISetupRule iSetupRule : iSetupRuleArr) {
            iSetupRule.onDestroy();
        }
    }

    public <T> T findSetup(Class<T> cls) {
        for (a aVar : this.i) {
            T t = (T) a(aVar, cls);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    private <T> T a(a aVar, Class<T> cls) {
        if (aVar == null) {
            return null;
        }
        for (ISetupRule iSetupRule : aVar.a) {
            T t = (T) iSetupRule;
            if (t.getClass() == cls) {
                return t;
            }
        }
        return null;
    }

    public <T> T getRegisteredEventBusSubscriber(Class<T> cls) {
        return (T) ((EventBusSetup) findSetup(EventBusSetup.class)).getRegistered(cls);
    }

    /* loaded from: classes3.dex */
    public static class a {
        private ISetupRule[] a = null;
        private AtomicBoolean b = new AtomicBoolean(false);

        private a() {
        }

        static a a(ISetupRule[] iSetupRuleArr) {
            a aVar = new a();
            aVar.a = iSetupRuleArr;
            return aVar;
        }

        public boolean a() {
            return this.b.compareAndSet(false, true);
        }

        void b() {
            if (this.a != null) {
                SetupManager.getInstance().a(this.a);
                this.b.set(false);
            }
        }
    }
}

        void b() {
            if (this.a != null) {
                SetupManager.getInstance().a(this.a);
                this.b.set(false);
            }
        }
    }
}
