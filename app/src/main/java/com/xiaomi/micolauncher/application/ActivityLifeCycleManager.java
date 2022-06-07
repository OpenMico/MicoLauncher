package com.xiaomi.micolauncher.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.quickapp.QuickAppHelper;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeForbidVideoActivity;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.player.VideoPlayerActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ActivityLifeCycleManager {
    private WeakReference<Activity> d;
    private WeakReference<Activity> e;
    private WeakReference<Activity> f;
    private long i;
    private static final ActivityLifeCycleManager a = new ActivityLifeCycleManager();
    private static final a b = $$Lambda$_411_I7KiHkTns3gqKHpxMTh5U.INSTANCE;
    public static final long RESUME_FROM_WHEN_CALLED_DURATION_MAX_SECS = TimeUnit.SECONDS.toMillis(2);
    private List<WeakReference<Activity>> c = new ArrayList();
    private final List<Application.ActivityLifecycleCallbacks> g = new LinkedList();
    private final Handler h = new Handler(Looper.getMainLooper());

    /* loaded from: classes3.dex */
    public interface a {
        boolean shouldFinish(Activity activity);
    }

    /* loaded from: classes3.dex */
    public interface c {
        void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks);
    }

    public static ActivityLifeCycleManager getInstance() {
        return a;
    }

    public Activity getTopActivity() {
        Activity a2 = a(this.d);
        return a2 != null ? a2 : a(this.e);
    }

    private Activity a(WeakReference<Activity> weakReference) {
        Activity activity = weakReference == null ? null : weakReference.get();
        if (activity != null) {
            return activity;
        }
        return null;
    }

    public Activity getTopActivityOrLauncher() {
        Activity topActivity = getTopActivity();
        if (isValidActivity(topActivity)) {
            return topActivity;
        }
        WeakReference<Activity> weakReference = this.f;
        Activity activity = weakReference == null ? null : weakReference.get();
        if (isValidActivity(activity)) {
            return activity;
        }
        return null;
    }

    @Deprecated
    public <T extends Activity> boolean hasActivityOnStack(Class<T> cls) {
        for (WeakReference<Activity> weakReference : c()) {
            if (cls.isInstance(weakReference.get())) {
                return true;
            }
        }
        return false;
    }

    public void a(Application application) {
        application.registerActivityLifecycleCallbacks(new b());
    }

    public void destroyActivities() {
        if (Threads.isMainThread()) {
            a();
        } else {
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$0MjhL2lly9WYsCOPOBJeAgKY4j4
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLifeCycleManager.this.a();
                }
            });
        }
    }

    public void a() {
        a(b);
    }

    private void b() {
        a($$Lambda$ActivityLifeCycleManager$ZU2WJu6n5Ncmf_5qNddKHp2UDtQ.INSTANCE);
    }

    public static /* synthetic */ boolean b(Activity activity) {
        return activity != null && !(activity instanceof Launcher);
    }

    private void a(a aVar) {
        ThreadUtil.verifyMainThread();
        List<WeakReference<Activity>> c2 = c();
        for (int size = c2.size() - 1; size >= 0; size--) {
            Activity activity = c2.get(size).get();
            if (aVar != null && aVar.shouldFinish(activity)) {
                a(activity);
            }
        }
        this.c.clear();
    }

    private void a(Activity activity) {
        DebugHelper.printStackTrace("finish " + activity);
        activity.finish();
        L.activity.i("%s finish activity %s", "ActivityLifeCycleManager", activity);
    }

    public void gotoMainActivity(final Context context) {
        L.base.i("go to main page, start launcher activity");
        ThirdPartyAppProxy.getInstance().stop(context);
        ThirdPartyAppProxy.getInstance().quit(context);
        if (ThreadUtil.isMainThread()) {
            b(context);
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$_AzPDyU7Nz53tKCyXE5vBdu5Les
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLifeCycleManager.this.b(context);
                }
            });
        }
    }

    /* renamed from: a */
    public void b(Context context) {
        b();
        startActivityQuietly(new Intent(context, Launcher.class).setFlags(268435456).putExtra(Launcher.KEY_ENTER_HOMEPAGE, true));
    }

    public void gotoMusicActivity(Context context) {
        PlayerApi.playRecommendOrShowPlayerView(context);
    }

    public void finishVideoPlayerActivity() {
        ThreadUtil.verifyMainThread();
        List<WeakReference<Activity>> c2 = c();
        for (int size = c2.size() - 1; size >= 0; size--) {
            Activity activity = c2.get(size).get();
            if (activity instanceof VideoPlayerActivity) {
                a(activity);
                VideoMediaSession.getInstance().setStopped();
            }
        }
    }

    @NonNull
    public List<WeakReference<Activity>> c() {
        return this.c;
    }

    public boolean isVideoInForeground() {
        Activity topActivity = getTopActivity();
        if (topActivity != null) {
            return topActivity instanceof VideoPlayerActivity;
        }
        return false;
    }

    public boolean isChildModeForbidVideoInForeground() {
        Activity topActivity = getTopActivity();
        if (topActivity != null) {
            return topActivity instanceof ChildModeForbidVideoActivity;
        }
        return false;
    }

    public boolean isHomeInForeground() {
        Activity topActivity = getTopActivity();
        if (topActivity != null) {
            return topActivity instanceof Launcher;
        }
        return false;
    }

    public static boolean startActivityWithThrowException(Intent intent) throws ActivityNotFoundException {
        return startActivity(getInstance().getTopActivity(), intent, -1, true);
    }

    public static boolean startActivityQuietly(Intent intent) {
        return startActivityQuietly(getInstance().getTopActivity(), intent);
    }

    public static boolean startActivityQuietly(Context context, Intent intent) {
        return startActivity(context, intent, -1, false);
    }

    public static boolean startActivity(final Context context, final Intent intent, final int i, final boolean z) {
        if (ThreadUtil.isMainThread()) {
            try {
                return getInstance().a(context, intent, i, z);
            } catch (ActivityNotFoundException e) {
                if (!z) {
                    e.printStackTrace();
                    return false;
                }
                throw e;
            }
        } else {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            getInstance().h.post(new Runnable() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$yZe0NBXKQaLZrWKXiYZbVm4S6qM
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLifeCycleManager.a(context, intent, i, z, atomicBoolean2, atomicBoolean, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
                if (z && atomicBoolean2.get()) {
                    throw new ActivityNotFoundException();
                }
                return atomicBoolean.get();
            } catch (InterruptedException e2) {
                L.base.e("startActivity from thread error", e2);
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$YaV9rRK9-meVBLOkPzGRCstDxPQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityLifeCycleManager.b(context, intent, i, z);
                    }
                });
                return true;
            }
        }
    }

    public static /* synthetic */ void a(Context context, Intent intent, int i, boolean z, AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2, CountDownLatch countDownLatch) {
        try {
            L.base.d("startActivity from thread come in, countDownLatch.countDown");
            try {
                atomicBoolean2.set(getInstance().a(context, intent, i, z));
            } catch (ActivityNotFoundException unused) {
                if (z) {
                    L.base.e("%s caught ActivityNotFoundException", "ActivityLifeCycleManager");
                    atomicBoolean.set(true);
                }
                atomicBoolean2.set(false);
            }
            countDownLatch.countDown();
        } catch (Throwable th) {
            atomicBoolean2.set(false);
            countDownLatch.countDown();
            throw th;
        }
    }

    public static /* synthetic */ void b(Context context, Intent intent, int i, boolean z) {
        getInstance().a(context, intent, i, z);
    }

    private boolean a(Context context, Intent intent, int i, boolean z) {
        WeakReference<Activity> weakReference;
        if (intent == null) {
            L.init.e("lifecycle start intent is null");
            return false;
        }
        ThreadUtil.verifyMainThread();
        Activity topActivity = getInstance().getTopActivity();
        L.init.i("lifecycle start %s", intent);
        DebugHelper.printStackTrace("startActivityInMainThread", L.init);
        if (isInvalidActivity(topActivity)) {
            topActivity = context;
        }
        if (topActivity == null && (weakReference = this.f) != null) {
            Activity activity = weakReference.get();
            if (isValidActivity(activity)) {
                L.base.w("apply launcher as start context");
                topActivity = activity;
            } else if (MicoApplication.getGlobalContext() != null) {
                topActivity = MicoApplication.getGlobalContext();
                L.base.w("apply global context as start context");
            }
        }
        if (topActivity == null) {
            topActivity = MicoApplication.getApp();
        }
        if (topActivity == null) {
            L.base.e("srcCtx is null, intent action %s, component %s", intent.getAction(), intent.getComponent());
            return false;
        }
        try {
            if (i == -1) {
                topActivity.startActivity(intent);
            } else if (topActivity instanceof Activity) {
                ((Activity) topActivity).startActivityForResult(intent, i);
            } else {
                L.base.e("failed to start activity , request code %s, context %s, srcCtx %s", Integer.valueOf(i), context, topActivity);
                return false;
            }
            return true;
        } catch (ActivityNotFoundException e) {
            if (!z) {
                return false;
            }
            throw e;
        }
    }

    public static boolean isInvalidActivity(Context context) {
        if (context == null) {
            return true;
        }
        if (!(context instanceof Activity)) {
            return false;
        }
        return !isValidActivity((Activity) context);
    }

    public static boolean isValidActivity(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public void addLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        checkIsOnMainThread();
        this.g.add(activityLifecycleCallbacks);
    }

    public static void checkIsOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("Not on main thread!");
        }
    }

    public void removeLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        checkIsOnMainThread();
        this.g.remove(activityLifecycleCallbacks);
    }

    public static boolean hasActivityOnMicoTask(Context context, Class<? extends Activity> cls) {
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningTasks(Integer.MAX_VALUE)) {
            if (cls.getCanonicalName().equalsIgnoreCase(runningTaskInfo.baseActivity.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void dumpActivityReferences(Logger logger) {
        ThreadUtil.verifyMainThread();
        logger.i("begin dump activity references");
        a(logger, "topActivity", this.d);
        a(logger, "backupTopActivity", this.e);
        a(logger, "launcher", this.f);
        for (WeakReference<Activity> weakReference : this.c) {
            a(logger, "element", weakReference);
        }
        logger.i("end dump activity references");
    }

    private void a(Logger logger, String str, WeakReference<Activity> weakReference) {
        if (weakReference != null) {
            logger.i("%s activity is %s", str, weakReference.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class b implements Application.ActivityLifecycleCallbacks {
        private b() {
            ActivityLifeCycleManager.this = r1;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(final Activity activity, final Bundle bundle) {
            a(activity);
            ActivityLifeCycleManager.this.c.add(new WeakReference(activity));
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$URE33oyjsYcQgYtWERbuaCCAOH8
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityCreated(activity, bundle);
                }
            });
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(final Activity activity) {
            a(activity);
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$cVV-Dz3t5nmYPTDANl_dQqYML0o
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityStarted(activity);
                }
            });
        }

        private void a(Activity activity) {
            if (activity instanceof Launcher) {
                ActivityLifeCycleManager.this.f = new WeakReference(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(final Activity activity) {
            boolean z = true;
            L.activity.d("onActivityResumed %s", activity.getClass().getName());
            if (activity instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) activity;
                if (SystemClock.uptimeMillis() - ActivityLifeCycleManager.this.i >= ActivityLifeCycleManager.RESUME_FROM_WHEN_CALLED_DURATION_MAX_SECS) {
                    z = false;
                }
                baseActivity.setJustResumedFromMicoActivity(z);
            }
            ActivityLifeCycleManager.this.d = new WeakReference(activity);
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$ZZQNuHiwYUxkkieiiQIDOfxqh5U
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityResumed(activity);
                }
            });
            if (QuickAppHelper.isDicChannelOpened()) {
                QuickAppHelper.notifyExit();
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(final Activity activity) {
            ActivityLifeCycleManager.this.i = SystemClock.uptimeMillis();
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$cK-iVMa4O5SHdJB5L9W0lBW2jVE
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityPaused(activity);
                }
            });
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(final Activity activity) {
            a(activity, false);
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$KWx5A-15znNpibseotbwwz9lIUU
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityStopped(activity);
                }
            });
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(final Activity activity, final Bundle bundle) {
            a(activity, false);
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$GhCAUXg2PRR7dXFW3YWh6JCVRBg
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivitySaveInstanceState(activity, bundle);
                }
            });
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(final Activity activity) {
            a(activity, true);
            b(activity);
            a(new c() { // from class: com.xiaomi.micolauncher.application.-$$Lambda$ActivityLifeCycleManager$b$w3f_jYphBii-juiYXaHyg3z50Qg
                @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.c
                public final void notify(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
                    activityLifecycleCallbacks.onActivityDestroyed(activity);
                }
            });
        }

        private void b(Activity activity) {
            List c = ActivityLifeCycleManager.this.c();
            for (int size = c.size() - 1; size >= 0; size--) {
                Activity activity2 = (Activity) ((WeakReference) c.get(size)).get();
                if (activity2 == null || activity2.equals(activity)) {
                    c.remove(size);
                }
            }
        }

        private void a(Activity activity, boolean z) {
            Activity activity2;
            if (ActivityLifeCycleManager.this.d != null && (activity2 = (Activity) ActivityLifeCycleManager.this.d.get()) == activity) {
                if (z || activity2.isDestroyed() || activity2.isFinishing()) {
                    L.debug.d("%s clear top %s", "ActivityLifeCycleManager", ActivityLifeCycleManager.this.d.get());
                    ActivityLifeCycleManager.this.e = null;
                } else {
                    ActivityLifeCycleManager activityLifeCycleManager = ActivityLifeCycleManager.this;
                    activityLifeCycleManager.e = activityLifeCycleManager.d;
                    L.debug.d("%s clear top %s and backup", "ActivityLifeCycleManager", ActivityLifeCycleManager.this.d.get());
                }
                ActivityLifeCycleManager.this.d = null;
            }
        }

        private void a(c cVar) {
            for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : ActivityLifeCycleManager.this.g) {
                if (activityLifecycleCallbacks != null) {
                    cVar.notify(activityLifecycleCallbacks);
                }
            }
        }
    }
}
