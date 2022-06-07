package com.xiaomi.micolauncher.application.controllers;

import android.content.Context;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.VolumeLimitInPowerSaveModeEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class StatusBarController extends ControllerBase {
    private static Context b;
    private static final long a = Constants.TimeConstants.SECONDS_10S_TO_MILLIS;
    private static Runnable c = $$Lambda$StatusBarController$03Oa_QDS1rYl_e9VnofqKyoU60.INSTANCE;

    public StatusBarController(Context context) {
        super(context);
        b = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            a(getContext());
        } else if (wifiConnectivityChangedEvent.isNotConnectedStablely()) {
            showNetworkLostNotification(getContext());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVolumeLimitInPowerSaveMode(VolumeLimitInPowerSaveModeEvent volumeLimitInPowerSaveModeEvent) {
        ToastUtil.showToast(getContext().getResources().getString(R.string.low_battery_level_volume_max_toast_warning, 65));
    }

    public static void showNetworkLostNotification(Context context) {
        if (SystemSetting.isInitializedNoCheckAccount(context)) {
            ThreadUtil.removeCallbacksInMainThread(c);
            ThreadUtil.postDelayedInMainThread(c, a);
        }
    }

    private static void a(Context context) {
        ThreadUtil.removeCallbacksInMainThread(c);
        NotificationHelper.cancelNotification(context, R.string.notification_network_lost);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a() {
        L.wlan.i("post to showNetworkLost Notification");
        NotificationHelper.notify(b, 0);
    }
}
