package com.xiaomi.micolauncher.module.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.application.setup.ReceiversManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.ScreenSaverSwitchEvent;
import com.xiaomi.micolauncher.module.SmartHomeLockControl;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;

/* loaded from: classes3.dex */
public class LockScreenLifeCycleReceiver extends BroadcastReceiver implements ReceiversManager.IAppBroadcastReceiverRule {
    private final Context a;

    public LockScreenLifeCycleReceiver(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaomi.micolauncher.module.lockscreen.LOCK_SCREEN_LIFE_CYCLE_ACTION");
        this.a.registerReceiver(this, intentFilter);
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void destroy() {
        this.a.unregisterReceiver(this);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("com.xiaomi.micolauncher.module.lockscreen.EXTRA_LIFE_CYCLE_TYPE", -1);
        Logger logger = L.lockscreen;
        logger.d("get broadcast from screen saver, type is: " + intExtra);
        switch (intExtra) {
            case 0:
                EventBusRegistry.getEventBus().post(new ScreenSaverSwitchEvent(true));
                LockScreenOpenManager.bindScreenSaverKeepAliveService(MicoApplication.getGlobalContext());
                return;
            case 1:
                Screen.getInstance().setLockScreen(true);
                ThreadUtil.postDelayedInMainThread($$Lambda$LockScreenLifeCycleReceiver$98QaK09rD8Iv6HN4EzLDW3o4lg.INSTANCE, 1000L);
                ChildModeManager.getManager().updateLockState();
                SmartHomeLockControl.getIns().setNeedShowLock(true);
                return;
            case 2:
                Screen.getInstance().setLockScreen(false);
                return;
            case 3:
                ThreadUtil.postDelayedInMainThread($$Lambda$LockScreenLifeCycleReceiver$dMoxuo3Hzj9i6cYB8ttCYGm6Eo.INSTANCE, 1000L);
                EventBusRegistry.getEventBus().post(new ScreenSaverSwitchEvent(false));
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b() {
        ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.NOT_LOCKSCREEN);
        if (LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a() {
        ChildModeManager.getManager().start(ChildModeManager.TimeType.NOT_LOCKSCREEN);
        if (LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus()) {
            ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
        }
    }
}
