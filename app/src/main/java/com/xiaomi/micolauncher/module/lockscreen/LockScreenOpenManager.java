package com.xiaomi.micolauncher.module.lockscreen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.common.statemodel.HomeModel;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class LockScreenOpenManager {
    public static final String EXTRA_LOCK_SCREEN_ADVERTISER = "EXTRA_LOCK_SCREEN_ADVERTISER";
    public static final String EXTRA_LOCK_SCREEN_HAS_LOCK = "EXTRA_LOCK_SCREEN_HAS_LOCK";
    public static final String EXTRA_LOCK_SCREEN_PREVIEW = "EXTRA_LOCK_SCREEN_PREVIEW";
    public static final String EXTRA_LOCK_SCREEN_TYPE_ID = "EXTRA_LOCK_SCREEN_TYPE_ID";
    public static final String EXTRA_NEED_ANIMATION = "EXTRA_NEED_ANIMATION";
    public static final String EXTRA_USE_ALPHA_ENTER_ANIMATION = "EXTRA_USE_ALPHA_ENTER_ANIMATION";
    public static final String LOCK_SCREEN_ACTION = "com.xiaomi.mico.screensaver.lock";
    public static final String LOCK_SCREEN_CATEGORY = "com.xiaomi.mico.category.LOCKSCREEN";
    private static boolean a = false;
    private static final ServiceConnection b = new ServiceConnection() { // from class: com.xiaomi.micolauncher.module.lockscreen.LockScreenOpenManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("bindService", "bind screensaver service success");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("bindService", "bind screensaver service fail");
        }
    };

    public static void openLockScreen(Context context, boolean z) {
        if (Hardware.isBigScreen()) {
            a(context, LOCK_SCREEN_ACTION, LOCK_SCREEN_CATEGORY, z);
        }
    }

    private static void a(final Context context, final String str, final String str2, final boolean z) {
        if (ThreadUtil.isMainThread()) {
            handleStartLockScreenActivity(context, str, str2, z, false, -1);
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.lockscreen.-$$Lambda$LockScreenOpenManager$RKOPyuq5EabHhpUlG4AQlRBKkLE
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenOpenManager.handleStartLockScreenActivity(context, str, str2, z, false, -1);
                }
            });
        }
    }

    public static void handleStartLockScreenActivity(Context context, String str, String str2, boolean z, boolean z2, int i) {
        if (a(z2)) {
            Intent intent = new Intent();
            intent.setAction(str);
            intent.addCategory(str2);
            intent.putExtra(EXTRA_LOCK_SCREEN_PREVIEW, z2);
            intent.putExtra(EXTRA_NEED_ANIMATION, z);
            intent.putExtra(EXTRA_USE_ALPHA_ENTER_ANIMATION, true);
            intent.putExtra(EXTRA_LOCK_SCREEN_TYPE_ID, i);
            intent.putExtra(EXTRA_LOCK_SCREEN_HAS_LOCK, z2 ? false : ChildModeManager.getManager().isScreenLock());
            context.startActivity(intent);
        }
        a(context);
    }

    private static void a(Context context) {
        if (context != null) {
            SystemSetting.LockScreen lockScreen = SystemSetting.getLockScreen();
            long settingLong = PreferenceUtils.getSettingLong(context, "key_record_lock_Screen_type", 0L);
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(settingLong);
            if (!TimeUtils.checkSameDay(Calendar.getInstance(), instance)) {
                String string = context.getString(lockScreen.getNameRes());
                StatPoints.recordPoint(StatPoints.Event.micolog_lock_screen, StatPoints.LockScreenKey.type, string);
                StatUtils.recordCountEvent("lock_screen", "micolog_lock_screen_type", "screen_saver_type", string);
                PreferenceUtils.setSettingLong(context, "key_record_lock_Screen_type", System.currentTimeMillis());
            }
        }
    }

    private static boolean a(boolean z) {
        if (z) {
            return true;
        }
        if (!HomeModel.getInstance().isInitialized()) {
            return false;
        }
        boolean z2 = SleepMode.getInstance().getSleepStatus() || ((SystemSetting.getLockScreenAdvertiser() > 0L ? 1 : (SystemSetting.getLockScreenAdvertiser() == 0L ? 0 : -1)) > 0) || (SystemSetting.getLockScreen() != SystemSetting.LockScreen.EMPTY);
        L.lockscreen.v("can open lock screen %s", Boolean.valueOf(z2));
        return z2;
    }

    public static void bindScreenSaverKeepAliveService(Context context) {
        Intent intent = new Intent();
        intent.setPackage("com.xiaomi.mico.screensaver");
        intent.setComponent(new ComponentName("com.xiaomi.mico.screensaver", "com.xiaomi.mico.screensaver.ScreenSaverKeepAliveService"));
        context.bindService(intent, b, 1);
        a = true;
    }

    public static void unbindScreenSaverKeepAliveService(Context context) {
        if (a) {
            a = false;
            context.unbindService(b);
        }
    }
}
