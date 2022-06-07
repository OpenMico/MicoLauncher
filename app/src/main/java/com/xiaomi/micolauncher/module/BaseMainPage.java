package com.xiaomi.micolauncher.module;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.LauncherState;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.SetupManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.statemodel.HomeModel;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenOpenManager;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.alarm.model.WorkDaySyncService;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import com.xiaomi.micolauncher.skills.common.WakeupUiEvent;
import com.xiaomi.miot.support.MiotManager;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseMainPage implements LauncherState {
    private static final long c;
    private static final long d;
    private static final long e;
    protected BaseActivity activity;
    private final int f;
    private final int g;
    private Handler h;
    private final a i = new a();
    private boolean j = true;
    boolean a = false;
    boolean b = false;
    private final BroadcastReceiver k = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.BaseMainPage.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BaseMainPage.this.e()) {
                BaseMainPage.this.onTimeTick();
            }
        }
    };
    private final BroadcastReceiver l = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.BaseMainPage.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                BaseMainPage.this.b = intent.getBooleanExtra("KEY_IS_FORBID_SCHEDULE_LOCK_SCREEN", false);
                Logger logger = L.launcher;
                logger.i("BaseMainPage getBroadcast from voip, isForbidScheduleLockScreen: " + BaseMainPage.this.b);
            }
        }
    };

    protected void backOutEditStatus() {
    }

    public abstract int getLayoutId();

    public void initViews(BaseActivity baseActivity) {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public boolean needRegisterEventBus() {
        return true;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return i == 4;
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onSaveInstanceState(Bundle bundle) {
    }

    protected void onTimeTick() {
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onTrimMemory(int i) {
    }

    static {
        long j = 60;
        c = TimeUnit.SECONDS.toMillis(Hardware.isBigScreen() ? 60L : 15L);
        d = TimeUnit.SECONDS.toMillis(8L);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        if (!Hardware.isBigScreen()) {
            j = 30;
        }
        e = timeUnit.toMillis(j);
    }

    public BaseMainPage(BaseActivity baseActivity) {
        this.activity = baseActivity;
        this.f = baseActivity.getResources().getDimensionPixelSize(R.dimen.max_show_lock_screen_axis_x);
        this.g = baseActivity.getResources().getDimensionPixelSize(R.dimen.min_distance_to_show_lock_screen);
    }

    public static BaseMainPage getMainPage(BaseActivity baseActivity) {
        return new MainPageBigScreen(baseActivity);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onCreate(@Nullable Bundle bundle) {
        L.base.d("BaseMainPage setContentView entry.");
        this.activity.setContentView(getLayoutId());
        FragmentManager supportFragmentManager = this.activity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        for (Fragment fragment : supportFragmentManager.getFragments()) {
            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            FragmentTransaction beginTransaction2 = childFragmentManager.beginTransaction();
            for (Fragment fragment2 : childFragmentManager.getFragments()) {
                Logger logger = L.homepage;
                logger.d("remove old child " + fragment2);
                beginTransaction2.remove(fragment2);
            }
            beginTransaction2.commitAllowingStateLoss();
            childFragmentManager.executePendingTransactions();
            Logger logger2 = L.homepage;
            logger2.d("remove old " + fragment);
            beginTransaction.remove(fragment);
        }
        beginTransaction.commitAllowingStateLoss();
        supportFragmentManager.executePendingTransactions();
        initViews(this.activity);
        if (!Settings.canDrawOverlays(this.activity)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.parse("package:" + this.activity.getPackageName()));
            this.activity.startActivityForResult(intent, 100);
        }
        this.h = new Handler(this.i);
        this.h.sendEmptyMessageDelayed(2, e);
        HomeModel.getInstance().setInitialized();
        a(this.activity.findViewById(Hardware.isBigScreen() ? R.id.coordinator : R.id.view_pager));
        this.activity.registerReceiver(this.l, new IntentFilter("ACTION_FORBID_SCHEDULE_LOCK_SCREEN"));
    }

    protected void setVisibility(boolean z) {
        FrameLayout rootView = getRootView();
        if (rootView != null) {
            rootView.setVisibility(z ? 0 : 4);
        }
    }

    protected FrameLayout getRootView() {
        BaseActivity baseActivity = this.activity;
        if (baseActivity == null) {
            return null;
        }
        return (FrameLayout) baseActivity.findViewById(16908290);
    }

    private void a(final View view) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.xiaomi.micolauncher.module.BaseMainPage.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                L.startuplauncher.d("BaseMainPage %s onPreDraw", view);
                SetupManager.getInstance().setupAfterLoginNotEagerStage(BaseMainPage.this.activity.getApplication());
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a implements Handler.Callback {
        private a() {
            BaseMainPage.this = r1;
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (BaseMainPage.this.b) {
                L.lockscreen.i("BaseMainPage isForbidScheduleLockScreen is true");
                return false;
            }
            switch (message.what) {
                case 2:
                    if (BaseMainPage.this.e()) {
                        L.lockscreen.i("first start lock screen");
                        BaseMainPage baseMainPage = BaseMainPage.this;
                        baseMainPage.a(baseMainPage.activity);
                        break;
                    }
                    break;
                case 3:
                    if (!BaseMainPage.this.j && BaseMainPage.this.e()) {
                        L.lockscreen.i("BaseMainPage start lock screen");
                        BaseMainPage baseMainPage2 = BaseMainPage.this;
                        baseMainPage2.a(baseMainPage2.activity);
                        break;
                    }
                    break;
                case 4:
                    EventBusRegistry.getEventBus().post(new ResetScrollViewPositionEvent(1));
                    break;
            }
            return false;
        }
    }

    public void a(Activity activity) {
        backOutEditStatus();
        LockScreenOpenManager.openLockScreen(activity, true);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onDispatchTouchEvent(MotionEvent motionEvent) {
        if (e()) {
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        b();
                        d();
                        return;
                    case 1:
                        break;
                    default:
                        return;
                }
            }
            a();
            c();
        }
    }

    private void a() {
        Logger logger = L.launcher;
        StringBuilder sb = new StringBuilder();
        sb.append("BaseMainPage scheduleLockScreen delay time: ");
        sb.append(SleepMode.getInstance().getSleepStatus() ? d : c);
        logger.i(sb.toString());
        this.h.removeMessages(3);
        this.h.sendEmptyMessageDelayed(3, SleepMode.getInstance().getSleepStatus() ? d : c);
    }

    private void b() {
        L.launcher.i("BaseMainPage cancelLockScreen");
        this.j = false;
        this.h.removeMessages(2);
        this.h.removeMessages(3);
    }

    private void c() {
        L.launcher.i("BaseMainPage scheduleResetScrollViewPosition delay time: %d 秒", Long.valueOf(Constants.TimeConstants.RESET_SCROLLVIEW_POSITION_TIME_THRESHOLD));
        this.h.removeMessages(4);
        this.h.sendEmptyMessageDelayed(4, Constants.TimeConstants.RESET_SCROLLVIEW_POSITION_TIME_THRESHOLD);
    }

    private void d() {
        L.launcher.i("BaseMainPage cancelResetScrollViewPosition");
        this.h.removeMessages(4);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onResume() {
        this.a = true;
        ChildModeManager.getManager().start(ChildModeManager.TimeType.NOT_LOCKSCREEN);
        MiotManager.refreshDevices();
        WorkDaySyncService.startWorkDayAsyncTask();
        AsrTtsCard.getInstance().forbidHalfWakeupView();
        a();
        c();
        VideoMediaSession.getInstance().setStopped();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
        this.activity.registerReceiver(this.k, intentFilter);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onPause() {
        this.a = false;
        AsrTtsCard.getInstance().allowHalfWakeupView();
        this.j = false;
        b();
        this.activity.unregisterReceiver(this.k);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onDestroy() {
        this.activity.unregisterReceiver(this.l);
        this.h.removeCallbacksAndMessages(null);
    }

    public boolean e() {
        return this.activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWakeupUiEvent(WakeupUiEvent wakeupUiEvent) {
        if (!e()) {
            return;
        }
        if (wakeupUiEvent.getEvent() == 0) {
            b();
            d();
        } else if (wakeupUiEvent.getEvent() == 3) {
            a();
            c();
        }
    }
}
