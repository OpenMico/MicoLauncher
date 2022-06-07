package com.xiaomi.micolauncher.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.elvishew.xlog.Logger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.skill.SpeechHandler;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.systemui.SystemUIManager;
import com.xiaomi.micolauncher.module.homepage.view.AppSkillHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHoldersCache;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBusException;

/* loaded from: classes3.dex */
public class BaseActivity extends FragmentActivity implements SpeechHandler {
    public static final long DEFAULT_CLOSE_DURATION = TimeUnit.MINUTES.toMillis(1);
    protected static final int MSG_CLOSE = 1;
    private Handler d;
    private EventBusRegisterMode f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private Context l;
    private final CompositeDisposable a = new CompositeDisposable();
    private final BehaviorSubject<ActivityEvent> b = BehaviorSubject.create();
    private Logger c = L.lifecycle;
    private long e = -1;
    private final String k = getClass().getSimpleName();

    /* loaded from: classes3.dex */
    public enum EventBusRegisterMode {
        WHOLE_LIFE,
        ON_VISIBLE,
        NO_AUTO_REGISTER
    }

    protected boolean delayPauseStat() {
        return false;
    }

    public boolean isEphemeralActivity() {
        return false;
    }

    public boolean isLockScreen() {
        return false;
    }

    protected boolean shouldFitsSystemWindows() {
        return false;
    }

    protected boolean shouldHideStatusBar() {
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.l = getApplicationContext();
        if (!TokenManager.isInited()) {
            this.c.w("login manager not inited, finish activity");
            finish();
            if (ProcessChecker.isUnknownProcess(ProcessChecker.getCurrentProcessName(this.l))) {
                ProcessChecker.quitProcessOnUnknownProcess();
                return;
            }
            return;
        }
        this.c.d("Activity onCreate %s, %s", this.k, this);
        this.b.onNext(ActivityEvent.CREATE);
        this.d = new Handler(new a());
        if (!isLockScreen() && !ChildModeManager.getManager().isScreenLock()) {
            LockScreenSendBroadcast.sendCloseLockScreenEventBroadcast(this);
        }
        this.j = false;
    }

    protected void registerToEventBusIfNot() {
        try {
            if (!EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().register(this);
            }
        } catch (EventBusException e) {
            L.base.w(e);
        }
    }

    protected void unregisterToEventBusIfNeed() {
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public Handler getHandler() {
        Handler handler = this.d;
        if (handler != null) {
            return handler;
        }
        throw new IllegalStateException("do not call this when onCreate not called");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.j = true;
        this.c.d("Activity onNewIntent %s, %s, action %s, categories %s", this.k, this, intent.getAction(), a(intent.getCategories()));
    }

    private Object a(Set<String> set) {
        if (set == null) {
            return null;
        }
        return TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, set);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.c.d("Activity onStart %s, %s", this.k, this);
        if (a() == EventBusRegisterMode.WHOLE_LIFE) {
            registerToEventBusIfNot();
        }
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.c.d("Activity onRestart %s, %s", this.k, this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        boolean z = true;
        this.g = true;
        if (a() == EventBusRegisterMode.ON_VISIBLE) {
            registerToEventBusIfNot();
        }
        this.c.d("Activity onResume %s, %s", this.k, this);
        if (!isLockScreen()) {
            a(" onResume", true);
        }
        scheduleToClose(this.e);
        L.video.i("just resumed from mico activity %s, is ephemeral activity %s", Boolean.valueOf(this.i), Boolean.valueOf(isEphemeralActivity()));
        if (!this.i && !isEphemeralActivity()) {
            z = false;
        }
        VideoProcessHelper.processOnActivityResume(this, z, this.h);
    }

    public boolean isMicoActivityResumed() {
        return this.g;
    }

    public void setDefaultScheduleDuration() {
        setScheduleDuration(DEFAULT_CLOSE_DURATION);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.g = false;
        this.c.d("Activity onStop %s, %s", this.k, this);
        ThirdPartyAppProxy.getInstance().syncProcessorByForegroundAppAsync(this.l);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.g = false;
        this.c.d("Activity onPause %s, %s", this.k, this);
        removeCloseScheduler();
        if (a() == EventBusRegisterMode.ON_VISIBLE) {
            unregisterToEventBusIfNeed();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.c.d("Activity onDestroy %s, %s", this.k, this);
        removeCloseScheduler();
        this.a.dispose();
        this.b.onNext(ActivityEvent.DESTROY);
        unregisterToEventBusIfNeed();
        VideoViewHoldersCache.getInstance().clearVideoViewHolder();
        AppSkillHolderCacheManager.getManager().clearAllAppViewHolder();
        super.onDestroy();
    }

    public void addToDisposeBag(Disposable disposable) {
        this.a.add(disposable);
    }

    public void removeFromDisposeBag(Disposable disposable) {
        this.a.remove(disposable);
    }

    public void toggleSwipeBackFeature(boolean z) {
        if (z) {
            SystemUIManager.getDefault().enableGlobalGesture(getApplication());
        } else {
            SystemUIManager.getDefault().disableGlobalGesture(getApplication());
        }
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindUntilEvent(this.b, ActivityEvent.DESTROY);
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        a(" dispatchKeyEvent");
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 3) {
            switch (action) {
                case 0:
                    a(" dispatchTouchEventDown");
                    removeCloseScheduler();
                    StatPoints.recordPointDelayed(1);
                    break;
                case 1:
                    a(" dispatchTouchEventUp");
                    long j = this.e;
                    if (j > 0) {
                        scheduleToClose(j);
                        break;
                    }
                    break;
            }
        } else {
            a(" dispatchTouchEventCancel");
        }
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    private void a(String str) {
        a(str, false);
    }

    private void a(String str, boolean z) {
        Context context = this.l;
        UserBehaviorProxy.setUserBehavior(context, this.k + str, z);
    }

    public void setScheduleDuration(long j) {
        this.e = j;
    }

    public void scheduleToClose(long j) {
        Handler handler;
        this.e = j;
        removeCloseScheduler();
        if (j > 0 && (handler = this.d) != null) {
            handler.sendEmptyMessageDelayed(1, j);
        }
    }

    public void removeCloseScheduler() {
        Handler handler = this.d;
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    @NonNull
    public EventBusRegisterMode getEventBusRegisterMode() {
        return EventBusRegisterMode.NO_AUTO_REGISTER;
    }

    private EventBusRegisterMode a() {
        if (this.f == null) {
            this.f = getEventBusRegisterMode();
        }
        return this.f;
    }

    public void setJustResumedFromMicoActivity(boolean z) {
        this.i = z;
    }

    public void setDoNotOpenCameraService(boolean z) {
        this.h = z;
    }

    /* loaded from: classes3.dex */
    public static class a implements Handler.Callback {
        private WeakReference<BaseActivity> a;

        private a(BaseActivity baseActivity) {
            this.a = new WeakReference<>(baseActivity);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            BaseActivity baseActivity;
            WeakReference<BaseActivity> weakReference = this.a;
            if (weakReference == null || (baseActivity = weakReference.get()) == null || message.what != 1) {
                return false;
            }
            baseActivity.removeCloseScheduler();
            baseActivity.finish();
            L.activity.i("finish activity %s", baseActivity);
            return true;
        }
    }

    public boolean isNightMode() {
        return (getResources().getConfiguration().uiMode & 48) == 32;
    }
}
