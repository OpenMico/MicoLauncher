package com.xiaomi.micolauncher.module.lockscreen;

import android.content.Context;
import android.content.Intent;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class LockScreenSendBroadcast {
    public static void sendCloseLockScreenEventBroadcast(Context context) {
        Intent intent = new Intent("com.xiaomi.mico.screensaver.CLOSE_LOCK_SCREEN_ACTION");
        L.lockscreen.d("send launcher broadcast CLOSE_LOCK_SCREEN_EVENT_ACTION");
        context.sendBroadcast(intent);
    }

    public static void sendWakeupUiEventBroadcast(Context context, int i) {
        Intent intent = new Intent("com.xiaomi.mico.screensaver.WAKEUP_UI_EVENT_ACTION");
        intent.putExtra("com.xiaomi.mico.screensaver.EXTRA_WAKEUP_UI_EVENT_TYPE", i);
        L.lockscreen.d("send launcher broadcast WAKEUP_UI_EVENT_ACTION");
        context.sendBroadcast(intent);
    }

    public static void sendUpdateLockStateEvent(Context context) {
        Intent intent = new Intent("com.xiaomi.mico.screensaver.UPDATE_LOCK_STATE_EVENT_ACTION");
        boolean isScreenLock = ChildModeManager.getManager().isScreenLock();
        Logger logger = L.lockscreen;
        logger.d("start send update broadcast lock state is: " + String.valueOf(isScreenLock));
        intent.putExtra("com.xiaomi.mico.screensaver.EXTRA_UPDATE_LOCK_STATE_EVENT_STATUS", isScreenLock);
        context.sendBroadcast(intent);
    }
}
