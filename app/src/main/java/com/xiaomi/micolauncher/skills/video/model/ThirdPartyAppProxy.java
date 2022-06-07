package com.xiaomi.micolauncher.skills.video.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.contentprovider.MicoPassportInfoProvider;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.utils.ApkTool;
import com.xiaomi.micolauncher.module.cameradetection.event.SupportGestureDialogEvent;
import com.xiaomi.micolauncher.module.commandsdk.MiSoundBoxCommandSdkService;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.db.VideoRealmHelper;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BiliAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.FullFeaturedSdkBasedAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.GKidAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.KaraoketvCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MicoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.QiyiAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TalkKidAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TencentVideoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TestAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TiktokAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.YYAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.YoukuAppCommandProcessor;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ThirdPartyAppProxy {
    public static final int SYNC_PACKAGE_DELAY_SECS = 3;
    private static final List<String> a = new ArrayList<String>() { // from class: com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.1
        {
            add("iqiyi");
            add("bilibili");
            add(VideoItem.CP_VIPKID);
            add("youku");
            add("mitv");
            add("tencent");
            add("mangotv");
        }
    };
    private static final long b = TimeUnit.SECONDS.toMillis(2);
    private volatile IAppCommandProcessor c;
    private boolean d;
    private List<String> e;
    private volatile String f;
    private final Map<String, IAppCommandProcessor> g;
    private OnStartListener h;
    private b i;
    private boolean j;
    private StartArgs k;
    private Context l;
    private Runnable m;
    private final Runnable n;

    /* loaded from: classes3.dex */
    public interface OnCustomStartVideoCallback {
        Intent createIntent();
    }

    /* loaded from: classes3.dex */
    public interface OnFillIntentExtras {
        Intent fillExtra(Intent intent);
    }

    @VisibleForTesting
    /* loaded from: classes3.dex */
    public interface OnStartListener {
        void onPackageAndProcessorSynced();
    }

    /* loaded from: classes3.dex */
    public interface a {
        void onAllowed();
    }

    public /* synthetic */ void g() {
        stop(this.l);
        quit(this.l);
        EventBusRegistry.getEventBus().post(new VideoPlayerEvent(VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_LIST_COMPLETE));
    }

    /* loaded from: classes3.dex */
    public static class c {
        private static final ThirdPartyAppProxy a = new ThirdPartyAppProxy();
    }

    private ThirdPartyAppProxy() {
        this.f = null;
        this.g = new ConcurrentHashMap();
        this.n = new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$K9A-6KyjFOdgPZ9SXgn6VlZll_k
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartyAppProxy.this.g();
            }
        };
    }

    public static ThirdPartyAppProxy getInstance() {
        return c.a;
    }

    public boolean isEverStarted() {
        return this.d;
    }

    public void initialize(Context context) {
        this.l = context;
        if (!ContainerUtil.hasData(this.e)) {
            updateThirdPartyPackagesAndLauncherAsync(context);
        }
    }

    public void updateThirdPartyPackagesAndLauncherAsync(final Context context) {
        L.video.i("updateThirdPartyPackagesAndLauncher");
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$GOytEisM0WTeXarxb3aaRhxXyNw
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartyAppProxy.this.h(context);
            }
        });
    }

    public /* synthetic */ void h(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.video_app_packages_integrated_sdk);
        List<String> localInstallAppPackages = ApkTool.getLocalInstallAppPackages(context.getPackageManager());
        ArrayList arrayList = new ArrayList(Arrays.asList(stringArray));
        arrayList.removeAll(localInstallAppPackages);
        arrayList.addAll(localInstallAppPackages);
        this.e = new CopyOnWriteArrayList(arrayList);
        this.e.remove(GKidAppCommandProcessor.PACKAGE_NAME_GKID_SMALLSCREEN);
        for (String str : this.e) {
            IAppCommandProcessor c2 = c(str);
            if (c2 != null && TextUtils.equals(c2.getPackage(), str)) {
                this.g.put(str, c2);
            }
        }
    }

    public boolean isThirdPartyAppInForeground(Context context) {
        return a(context, false);
    }

    public boolean isTimedThirdPartyAppInForeground(Context context) {
        return a(context, true);
    }

    private boolean a(Context context, boolean z) {
        List<String> list;
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (ActivityUtil.isLauncherInForeground(context) && (list = this.e) != null) {
            for (String str : list) {
                if (ActivityUtil.isPackageInForeground(context, str)) {
                    L.video.i("package is in foreground %s", str);
                    return true;
                }
            }
        }
        boolean z2 = e() && !isMicoProcessor(iAppCommandProcessor) && (!z || (iAppCommandProcessor != null && iAppCommandProcessor.shouldCountTimeOnChildMode()));
        L.video.i("processor is %s, return %s for timed-only %b", this.c, Boolean.valueOf(z2), Boolean.valueOf(z));
        return z2;
    }

    public void setTargetPackage(Context context, String str) {
        setTargetPackage(context, str, true);
    }

    public void setTargetPackage(final Context context, String str, boolean z) {
        if (!TextUtils.equals(this.f, str)) {
            L.video.i("old package is %s, %s, new package is %s", this.f, this.c, str);
            this.f = str;
            final IAppCommandProcessor iAppCommandProcessor = this.c;
            Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$kTE6DyaCnD0o4JjKafJwkmxdKc0
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartyAppProxy.this.a(iAppCommandProcessor, context);
                }
            });
        }
        this.f = str;
        L.video.i("set target package to %s", str);
    }

    public /* synthetic */ void a(IAppCommandProcessor iAppCommandProcessor, Context context) {
        if (iAppCommandProcessor != null && !isMicoProcessor(iAppCommandProcessor)) {
            L.video.i("stop and quit %s", iAppCommandProcessor);
            iAppCommandProcessor.stop(context);
            iAppCommandProcessor.quit(context);
        }
    }

    public String getTargetPackage() {
        return this.f;
    }

    public void startApp(Context context, String str) {
        startApp(context, str, null);
    }

    public void startApp(final Context context, final String str, final OnFillIntentExtras onFillIntentExtras) {
        final IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null && !TextUtils.equals(iAppCommandProcessor.getPackage(), str)) {
            Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$e_XBWHI7zBtfMe5hs7d0a7i8wTc
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartyAppProxy.a(IAppCommandProcessor.this, str, context);
                }
            });
        }
        IAppCommandProcessor c2 = c(str);
        if (c2 != null) {
            setTargetPackage(context, str, c2.supportCommandSdk());
        }
        L.video.i("startApp start begin");
        a(context, str, new a() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$uGEG006iUt4EJaunMye-Z86Xjdc
            @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.a
            public final void onAllowed() {
                ThirdPartyAppProxy.this.b(context, str, onFillIntentExtras);
            }
        });
    }

    public static /* synthetic */ void a(IAppCommandProcessor iAppCommandProcessor, String str, Context context) {
        L.video.d("stop last app %s => %s -> %s ", iAppCommandProcessor, iAppCommandProcessor.getPackage(), str);
        iAppCommandProcessor.stop(context);
        iAppCommandProcessor.quit(context);
    }

    public /* synthetic */ void b(Context context, String str, OnFillIntentExtras onFillIntentExtras) {
        a(context);
        g(context);
        IAppCommandProcessor currentProcessor = getCurrentProcessor();
        if (currentProcessor != null && currentProcessor.needCamera()) {
            setCurrentAppNeedCamera(true);
        }
        a(context, str, onFillIntentExtras);
        L.video.i("startApp start done");
        this.d = true;
    }

    private long a() {
        return SystemClock.uptimeMillis();
    }

    private void a(Context context, String str, OnFillIntentExtras onFillIntentExtras) {
        L.video.i("%s start app %s, processor %s", "ThirdPartyAppProxy", str, this.c);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null && iAppCommandProcessor.supportStartApp()) {
            L.video.i("%s start app supported start %s", "ThirdPartyAppProxy", str);
            b();
            iAppCommandProcessor.startApp(context, onFillIntentExtras);
        } else if (iAppCommandProcessor != null) {
            L.video.e("%s %s do not support start app, processor %s", "ThirdPartyAppProxy", str, iAppCommandProcessor);
        }
    }

    private void b() {
        L.video.i("%s package %s, processor = %s", "ThirdPartyAppProxy", this.f, this.c);
    }

    private void a(Context context) {
        b(context, b(context));
    }

    private String b(Context context) {
        if (this.f == null) {
            return c(context);
        }
        L.video.i("current package %s", this.f);
        return this.f;
    }

    private String c(Context context) {
        String str = this.f == null ? "com.xiaomi.micolauncher" : this.f;
        if (ContainerUtil.isEmpty(this.e)) {
            String a2 = a(str);
            L.video.i("empty third party, current package %s", a2);
            return a2;
        }
        for (String str2 : this.e) {
            if (ActivityUtil.isPackageInForeground(context, str2)) {
                L.video.i("found by package manager, current package %s", str2);
                return str2;
            }
        }
        String a3 = a(str);
        L.video.i("current package by default %s", a3);
        return a3;
    }

    private String a(String str) {
        Activity topActivity;
        return (str == "com.xiaomi.micolauncher" || (topActivity = ActivityLifeCycleManager.getInstance().getTopActivity()) == null || !(topActivity instanceof BaseActivity) || !((BaseActivity) topActivity).isMicoActivityResumed()) ? str : "com.xiaomi.micolauncher";
    }

    public void syncProcessorByForegroundApp(Context context) {
        a(context, c(context));
    }

    public void a(Context context, String str) {
        if (!TextUtils.equals(str, this.f) || this.c == null) {
            IAppCommandProcessor iAppCommandProcessor = this.c;
            String str2 = this.f;
            this.f = str;
            a(context);
            L.video.i("change current processor from %s to %s, package from %s to %s", iAppCommandProcessor, this.c, str2, str);
        }
    }

    public void syncProcessorByForegroundAppAsync(final Context context) {
        L.video.i("sync processor by foreground app async");
        Observable.timer(3L, TimeUnit.SECONDS).map(new Function() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$tooTOEMAatmkV0ifYXDkvOloqLs
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                String a2;
                a2 = ThirdPartyAppProxy.this.a(context, (Long) obj);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.computation()).observeOn(MicoSchedulers.mainThread()).subscribe(new Observer<String>() { // from class: com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            /* renamed from: a */
            public void onNext(String str) {
                L.video.i("syncProcessorByForegroundAppAsync : current package is %s", str);
                ThirdPartyAppProxy.this.a(context, str);
            }
        });
    }

    public /* synthetic */ String a(Context context, Long l) throws Exception {
        return c(context);
    }

    public void communicate(Context context, String str) {
        L.video.i("%s dispatch communicate", "ThirdPartyAppProxy");
        b();
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null && iAppCommandProcessor.supportCommunicate()) {
            iAppCommandProcessor.communicate(context, str);
        }
    }

    public void play(Context context) {
        L.video.i("%s dispatch play", "ThirdPartyAppProxy");
        b();
        a(context);
        if (this.c != null) {
            this.c.play(context);
        }
    }

    public void repeat(Context context) {
        L.video.i("%s dispatch repeat", "ThirdPartyAppProxy");
        b();
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportRepeat()) {
            d(context);
        } else {
            iAppCommandProcessor.repeat(context);
        }
    }

    public void next(Context context) {
        L.video.i("%s dispatch next", "ThirdPartyAppProxy");
        b();
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportNext()) {
            d(context);
        } else {
            iAppCommandProcessor.next(context);
        }
    }

    public void previous(Context context) {
        L.video.i("%s dispatch previous", "ThirdPartyAppProxy");
        a(context);
        b();
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportPrevious()) {
            d(context);
        } else {
            iAppCommandProcessor.previous(context);
        }
    }

    public void pause(Context context) {
        L.video.i("%s dispatch pause", "ThirdPartyAppProxy");
        a(context);
        b();
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null) {
            iAppCommandProcessor.pause(context);
        } else {
            d(context);
        }
    }

    public void startVideo(Context context, String str, String str2) {
        startVideo(context, str, Uri.parse(str2));
    }

    public void startVideo(Context context, String str, Uri uri) {
        L.video.i("%s dispatch startVideo", "ThirdPartyAppProxy");
        b();
        startVideo(context, StartArgs.from(str, uri));
    }

    public void startVideo(final Context context, @NonNull final StartArgs startArgs) {
        if (!Hardware.isBigScreen() || !LockSetting.getHasLock(context, startArgs.getPackage())) {
            d(context, startArgs);
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$R-f9heyJHqOsr4z-Fa377dC8cSc
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartyAppProxy.this.c(context, startArgs);
                }
            });
        }
    }

    public /* synthetic */ void c(final Context context, @NonNull final StartArgs startArgs) {
        new LockAppDialog(context, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$Ih_65NGUgdWFxxUCw7GGLH5DrnQ
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                ThirdPartyAppProxy.this.d(context, startArgs);
            }
        }).show();
    }

    /* renamed from: a */
    public void d(final Context context, @NonNull final StartArgs startArgs) {
        this.k = startArgs;
        final IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null && !TextUtils.equals(iAppCommandProcessor.getPackage(), this.f)) {
            Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$kmNs8orKrBYNZih9nhAdnztlBOc
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartyAppProxy.a(ThirdPartyAppProxy.StartArgs.this, iAppCommandProcessor, context);
                }
            });
        }
        L.video.d("startVideo begin");
        a(context, startArgs.getPackage(), new a() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$Gm2jOCnqmR5zj7yf_wXmDK3b9hY
            @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.a
            public final void onAllowed() {
                ThirdPartyAppProxy.this.b(context, startArgs);
            }
        });
    }

    public static /* synthetic */ void a(@NonNull StartArgs startArgs, IAppCommandProcessor iAppCommandProcessor, Context context) {
        L.video.d("stop last video %s", startArgs);
        iAppCommandProcessor.stop(context);
    }

    public /* synthetic */ void b(Context context, @NonNull StartArgs startArgs) {
        setTargetPackage(context, startArgs.getPackage());
        a(context);
        b();
        OnStartListener onStartListener = this.h;
        if (onStartListener != null) {
            onStartListener.onPackageAndProcessorSynced();
        }
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null) {
            c();
            g(context);
            a(startArgs.d);
            iAppCommandProcessor.startVideo(context, startArgs);
            this.d = true;
        }
        L.video.d("startVideo end");
    }

    private void c() {
        ThreadUtil.getWorkHandler().removeCallbacks(this.n);
        long cmdVideoDuration = VideoModel.getInstance().getCmdVideoDuration();
        if (cmdVideoDuration > 0) {
            ThreadUtil.getWorkHandler().postDelayed(this.n, TimeUnit.SECONDS.toMillis(cmdVideoDuration));
            VideoModel.getInstance().setCmdVideoDuration(0L);
        }
    }

    public void fastForward(Context context, long j) {
        a(context);
        L.video.i("%s dispatch fastForward %s millis", "ThirdPartyAppProxy", Long.valueOf(j));
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportForward()) {
            d(context);
        } else {
            iAppCommandProcessor.forward(context, j);
        }
    }

    private void b(Context context, String str) {
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null || !TextUtils.equals(str, iAppCommandProcessor.getPackage())) {
            if (iAppCommandProcessor != null) {
                L.video.i("target package %s, stop old processor %s", str, iAppCommandProcessor);
                iAppCommandProcessor.stop(context);
            }
            this.c = f();
        }
        MiSoundBoxCommandSdkService.getInstance().setCurrentPackage(context, this.f);
    }

    public void fastBackward(Context context, long j) {
        a(context);
        L.video.i("%s dispatch fastBackward %s millis", "ThirdPartyAppProxy", Long.valueOf(j));
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportBackward()) {
            d(context);
        } else {
            iAppCommandProcessor.backward(context, j);
        }
    }

    private void d(Context context) {
        SpeechManager.getInstance().ttsRequest(context.getString(R.string.tts_local_command_not_support));
    }

    public void stopFromUI(Context context) {
        b(context, true);
    }

    public void stop(Context context) {
        b(context, false);
    }

    private void b(Context context, boolean z) {
        if (!d()) {
            a(context);
            IAppCommandProcessor iAppCommandProcessor = this.c;
            L.video.i("%s dispatch stop to %s", "ThirdPartyAppProxy", iAppCommandProcessor);
            if (iAppCommandProcessor != null) {
                if (z) {
                    iAppCommandProcessor.stopFromUI(context);
                } else {
                    iAppCommandProcessor.stop(context);
                }
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.VIDEO);
            }
            e(context);
        }
    }

    private void e(final Context context) {
        final String str = this.f;
        Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$-yOfNBLztQeMJj3gx0z6RJbi6lw
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartyAppProxy.this.d(context, str);
            }
        });
    }

    public /* synthetic */ void d(Context context, String str) {
        StartArgs startArgs = this.k;
        if (startArgs == null || startArgs.d == null) {
            VideoStatHelper.postExposeTrack(context, str, EventType.EXIT);
        } else {
            VideoStatHelper.postExposeTrack(this.k.d, EventType.EXIT);
        }
        this.k = null;
    }

    private void f(final Context context) {
        final String str = this.f;
        Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$rD6JDWcP6wq7VxcyAA6cb3qONZI
            @Override // java.lang.Runnable
            public final void run() {
                ThirdPartyAppProxy.this.c(context, str);
            }
        });
    }

    public /* synthetic */ void c(Context context, String str) {
        StartArgs startArgs = this.k;
        if (startArgs == null || startArgs.d == null) {
            VideoStatHelper.postExposeTrack(context, str, EventType.ENTER);
        } else {
            VideoStatHelper.postExposeTrack(this.k.d, EventType.ENTER);
        }
    }

    private boolean d() {
        b bVar;
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null || (bVar = this.i) == null || !TextUtils.equals(iAppCommandProcessor.getPackage(), bVar.b) || a() - bVar.a >= b) {
            return false;
        }
        L.video.i("%s %s start duration too short ignored ", "ThirdPartyAppProxy", iAppCommandProcessor.getPackage());
        return true;
    }

    public void removeThirdPartyAppProcessor() {
        if (!d() && this.c != null && !isMicoProcessor(this.c)) {
            L.video.i("removed processor %s", this.c);
            this.c = null;
        }
    }

    private boolean e() {
        return this.c != null;
    }

    public boolean isMicoProcessor(IAppCommandProcessor iAppCommandProcessor) {
        return iAppCommandProcessor != null && b(iAppCommandProcessor.getPackage());
    }

    private boolean b(String str) {
        return TextUtils.equals(str, "com.xiaomi.micolauncher");
    }

    public boolean isCurrentProcessorIsMicoProcessor() {
        return isMicoProcessor(getCurrentProcessor());
    }

    public void quitFromUI(Context context) {
        c(context, true);
    }

    public void quit(Context context) {
        c(context, false);
    }

    private void c(Context context, boolean z) {
        DebugHelper.printStackTrace(Commands.QUIT, L.video);
        if (!d()) {
            a(context);
            L.video.i("%s dispatch quit", "ThirdPartyAppProxy");
            IAppCommandProcessor iAppCommandProcessor = this.c;
            if (iAppCommandProcessor != null) {
                if (z) {
                    iAppCommandProcessor.quitFromUI(context);
                } else {
                    iAppCommandProcessor.quit(context);
                }
                this.c = null;
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.VIDEO);
            }
        }
    }

    public void setResolution(Context context, String str) {
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
            return;
        }
        L.video.i("%s set resolution=%s,getPackage=%s", "ThirdPartyAppProxy", str, iAppCommandProcessor.getPackage());
        if (!iAppCommandProcessor.supportSetResolution()) {
            d(context);
        } else {
            iAppCommandProcessor.setResolution(context, str);
        }
    }

    public void dispatchSkipStart(Context context) {
        a(context);
        L.video.i("%s skip start", "ThirdPartyAppProxy");
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportSkipStart()) {
            d(context);
        } else {
            iAppCommandProcessor.skipStart(context);
        }
    }

    public void dispatchSeek(Context context, long j) {
        a(context);
        L.video.i("%s seek", "ThirdPartyAppProxy");
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportSeek()) {
            d(context);
        } else {
            iAppCommandProcessor.seek(context, j);
        }
    }

    public void dispatchCast(Context context, boolean z) {
        a(context);
        L.video.i("%s", MicoPassportInfoProvider.getPackageSignHex(context.getPackageName(), context));
        L.video.i("%s cast", "ThirdPartyAppProxy");
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
        } else if (!iAppCommandProcessor.supportCast()) {
            d(context);
        } else {
            iAppCommandProcessor.cast(context, z);
        }
    }

    public boolean supportLike() {
        IAppCommandProcessor iAppCommandProcessor = this.c;
        return iAppCommandProcessor != null && iAppCommandProcessor.supportLike();
    }

    public void like(Context context) {
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null) {
            d(context);
            return;
        }
        boolean supportLike = iAppCommandProcessor.supportLike();
        L.video.i("%s like, supported %s", "ThirdPartyAppProxy", Boolean.valueOf(supportLike));
        if (!supportLike) {
            d(context);
        } else {
            iAppCommandProcessor.like(context);
        }
    }

    public boolean select(Context context, int i) {
        a(context);
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor == null || !iAppCommandProcessor.supportSelect()) {
            d(context);
            return false;
        }
        L.video.i("%s select index %s", "ThirdPartyAppProxy", Integer.valueOf(i));
        return a(context, i);
    }

    private boolean a(final Context context, final int i) {
        final VideoItem currentPlayingItem = VideoMediaSession.getInstance().getCurrentPlayingItem();
        if (currentPlayingItem == null) {
            L.video.w("current playing video item is null, probably not started by brain");
        }
        if (this.c == null) {
            L.video.w("video selection protocol not implemented");
            return false;
        }
        a(context, this.c.getPackage(), new a() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$BZdWNSVRi1HdNsNX2jEF2GnBrAA
            @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.a
            public final void onAllowed() {
                ThirdPartyAppProxy.this.a(i, context, currentPlayingItem);
            }
        });
        return true;
    }

    public /* synthetic */ void a(int i, Context context, VideoItem videoItem) {
        if (this.c != null) {
            L.video.i("select video %s to play", Integer.valueOf(i));
            this.c.select(context, i);
        } else {
            L.video.w("video selection protocol not implemented");
        }
        g(context);
        a(videoItem);
    }

    private void g(Context context) {
        f(context);
        VideoMediaSession.getInstance().setPlaying();
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null && iAppCommandProcessor.shouldCountTimeOnChildMode()) {
            ChildModeManager.getManager().start(ChildModeManager.TimeType.VIDEO);
        }
        if (iAppCommandProcessor != null && iAppCommandProcessor.supportGesture() && !isMicoProcessor(iAppCommandProcessor)) {
            EventBusRegistry.getEventBus().post(new SupportGestureDialogEvent());
        }
        if (iAppCommandProcessor != null) {
            this.i = new b();
            this.i.b = iAppCommandProcessor.getPackage();
            this.i.a = a();
        }
    }

    private void a(final VideoItem videoItem) {
        if (videoItem != null) {
            VideoMediaSession.getInstance().setPlayerString(videoItem);
            L.video.d("insert videoItem %s  %s", videoItem.getTitle(), videoItem.getMediaId());
            MicoSchedulers.io().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$7Lg4WvmN0VPUc7Uo_Ei03WXVUGA
                @Override // java.lang.Runnable
                public final void run() {
                    ThirdPartyAppProxy.b(VideoItem.this);
                }
            });
        }
    }

    public static /* synthetic */ void b(VideoItem videoItem) {
        VideoRealmHelper.getInstance().insert(videoItem);
    }

    @VisibleForTesting
    public IAppCommandProcessor getProcessorInCache(String str) {
        return this.g.get(str);
    }

    private IAppCommandProcessor f() {
        return c(this.f);
    }

    private IAppCommandProcessor c(String str) {
        IAppCommandProcessor iAppCommandProcessor;
        if (str == null) {
            return new MicoAppCommandProcessor(true);
        }
        IAppCommandProcessor iAppCommandProcessor2 = this.g.get(str);
        if (iAppCommandProcessor2 != null) {
            return iAppCommandProcessor2;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2008684377:
                if (str.equals(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1740862438:
                if (str.equals(TestAppCommandProcessor.PACKAGE_NAME_TEST)) {
                    c2 = 3;
                    break;
                }
                break;
            case -911250722:
                if (str.equals(GKidAppCommandProcessor.PACKAGE_NAME_GKID_SMALLSCREEN)) {
                    c2 = 5;
                    break;
                }
                break;
            case -894756483:
                if (str.equals("com.duowan.yytv")) {
                    c2 = 7;
                    break;
                }
                break;
            case -426347972:
                if (str.equals("com.youku.iot")) {
                    c2 = 1;
                    break;
                }
                break;
            case -338862911:
                if (str.equals("com.tencent.qqlive.audiobox")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -264182514:
                if (str.equals("com.xiaomi.micolauncher")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 113775824:
                if (str.equals(KaraoketvCommandProcessor.PACKAGE_NAME_KARAOKETV)) {
                    c2 = '\f';
                    break;
                }
                break;
            case 313184810:
                if (str.equals("com.ss.android.ugc.aweme")) {
                    c2 = '\b';
                    break;
                }
                break;
            case 409393875:
                if (str.equals("com.qiyi.video.speaker")) {
                    c2 = 0;
                    break;
                }
                break;
            case 495340649:
                if (str.equals(TalkKidAppCommandProcessor.PACKAGE_NAME_51TALK)) {
                    c2 = 11;
                    break;
                }
                break;
            case 1494609354:
                if (str.equals(GKidAppCommandProcessor.PACKAGE_NAME_GKID_BIGSCREEN)) {
                    c2 = 6;
                    break;
                }
                break;
            case 1994036591:
                if (str.equals("tv.danmaku.bili")) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                iAppCommandProcessor = new QiyiAppCommandProcessor();
                break;
            case 1:
                iAppCommandProcessor = new YoukuAppCommandProcessor();
                break;
            case 2:
                iAppCommandProcessor = new MangguoAppCommandProcessor();
                break;
            case 3:
                iAppCommandProcessor = new TestAppCommandProcessor();
                break;
            case 4:
                iAppCommandProcessor = new BiliAppCommandProcessor();
                break;
            case 5:
            case 6:
                iAppCommandProcessor = new GKidAppCommandProcessor();
                break;
            case 7:
                iAppCommandProcessor = new YYAppCommandProcessor();
                break;
            case '\b':
                iAppCommandProcessor = new TiktokAppCommandProcessor();
                break;
            case '\t':
                iAppCommandProcessor = new TencentVideoAppCommandProcessor();
                break;
            case '\n':
                iAppCommandProcessor = new MicoAppCommandProcessor(true);
                break;
            case 11:
                iAppCommandProcessor = new TalkKidAppCommandProcessor();
                break;
            case '\f':
                iAppCommandProcessor = new KaraoketvCommandProcessor();
                break;
            default:
                iAppCommandProcessor = new FullFeaturedSdkBasedAppCommandProcessor(str, true);
                break;
        }
        L.video.i("created video processor %s for package %s", iAppCommandProcessor, str);
        return iAppCommandProcessor;
    }

    public IAppCommandProcessor getCurrentProcessor() {
        return this.c;
    }

    private void a(Context context, String str, a aVar) {
        L.video.i("%s createProcessorAndCheckIfAllowPlay %s", "ThirdPartyAppProxy", str);
        a(context);
        b();
        IAppCommandProcessor iAppCommandProcessor = this.c;
        if (iAppCommandProcessor != null) {
            if (iAppCommandProcessor.shouldCountTimeOnChildMode()) {
                L.video.i("%s should count time on child mode", iAppCommandProcessor);
                a(context, aVar);
                return;
            }
            L.video.i("child mode check result : allow play");
            aVar.onAllowed();
        }
    }

    private void a(final Context context, final a aVar) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$ThirdPartyAppProxy$eAnso6y12xUJOWvV95EvwYPOpQQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ThirdPartyAppProxy.a(context, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Observer<Boolean>() { // from class: com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            /* renamed from: a */
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    aVar.onAllowed();
                } else {
                    L.video.w("%s child mode, not allowed to start app", "ThirdPartyAppProxy");
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                L.video.e("%s check allow play failed", "ThirdPartyAppProxy", th);
                aVar.onAllowed();
            }
        });
    }

    public static /* synthetic */ void a(Context context, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(Boolean.valueOf(ChildModeManager.getManager().allowPlayingVideoOrShowActivity(context)));
    }

    public OnStartListener getOnStartListener() {
        return this.h;
    }

    public void setOnStartListener(OnStartListener onStartListener) {
        this.h = onStartListener;
    }

    public static List<String> getSupportedCps() {
        return a;
    }

    public boolean isCurrentAppNeedCamera() {
        return this.j;
    }

    public void setCurrentAppNeedCamera(boolean z) {
        this.j = z;
    }

    public void onLauncherResume() {
        Runnable runnable = this.m;
        if (runnable != null) {
            runnable.run();
            this.m = null;
        }
    }

    public void setOnLauncherResumeRunnable(Runnable runnable) {
        this.m = runnable;
    }

    /* loaded from: classes3.dex */
    public static class StartArgs {
        String a;
        Uri b;
        private OnCustomStartVideoCallback c;
        private VideoItem d;
        private int e;

        public static StartArgs from(@NonNull String str, @NonNull String str2) {
            return from(str, Uri.parse(str2));
        }

        public static StartArgs from(@NonNull String str, @NonNull Uri uri) {
            StartArgs startArgs = new StartArgs();
            startArgs.b = uri;
            startArgs.a = str;
            return startArgs;
        }

        public static StartArgs from(@NonNull String str, @NonNull OnCustomStartVideoCallback onCustomStartVideoCallback) {
            StartArgs startArgs = new StartArgs();
            startArgs.a = str;
            startArgs.c = onCustomStartVideoCallback;
            return startArgs;
        }

        public static StartArgs from(String str, VideoItem videoItem, int i) {
            StartArgs startArgs = new StartArgs();
            startArgs.a = str;
            startArgs.d = videoItem;
            startArgs.e = i;
            return startArgs;
        }

        public String getPackage() {
            return this.a;
        }

        public Intent getUri() {
            return new Intent("android.intent.action.VIEW", this.b);
        }

        public int getEpisodeIndex() {
            return this.e;
        }

        public void setEpisodeIndex(int i) {
            this.e = i;
        }

        public String toString() {
            return "StartArgs{targetPackage='" + this.a + "', uri=" + this.b + '}';
        }

        public OnCustomStartVideoCallback getOnCustomStartVideoCallback() {
            return this.c;
        }

        public void setVideoItem(VideoItem videoItem) {
            this.d = videoItem;
        }

        public VideoItem getVideoItem() {
            return this.d;
        }
    }

    /* loaded from: classes3.dex */
    public static class b {
        long a;
        String b;

        private b() {
        }
    }

    public static void handleAppByThirdPartySchema(Context context, IAppCommandProcessor iAppCommandProcessor, Uri uri) {
        handleAppByThirdPartySchema(context, iAppCommandProcessor, uri, true);
    }

    public static void handleAppByThirdPartySchema(Context context, IAppCommandProcessor iAppCommandProcessor, Uri uri, boolean z) {
        StringBuilder sb = new StringBuilder("thirdparty://gotoAppPage/?pkgname=");
        sb.append(iAppCommandProcessor.getPackage());
        sb.append("&action=android.intent.action.VIEW");
        if (uri != null) {
            sb.append("&data=");
            try {
                sb.append(URLEncoder.encode(uri.toString(), "UTF-8"));
            } catch (UnsupportedEncodingException unused) {
            }
        }
        if (!z) {
            sb.append("&lock=false");
        }
        SchemaManager.handleSchema(context, sb.toString());
    }
}
