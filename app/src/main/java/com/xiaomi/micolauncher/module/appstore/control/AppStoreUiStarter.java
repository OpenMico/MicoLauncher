package com.xiaomi.micolauncher.module.appstore.control;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.event.AppStoreClearEvent;
import com.xiaomi.micolauncher.common.event.AppStoreFreeSpaceEvent;
import com.xiaomi.micolauncher.common.event.AppStoreInstallEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.module.appstore.NotifyMessageConfirmDialog;
import com.xiaomi.micolauncher.module.appstore.activity.AppDownloadStatusActivity;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppStoreUiStarter {
    NotifyMessageConfirmDialog a;
    private Context b;

    public AppStoreUiStarter(Context context) {
        this.b = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvFreeSpaceAlarmEvent(AppStoreFreeSpaceEvent appStoreFreeSpaceEvent) {
        if (Hardware.isBigScreen()) {
            if (appStoreFreeSpaceEvent.alarm) {
                PreferenceUtils.setSettingLong(this.b, AppStoreManager.KEY_APP_STORAGE_LAST_ALARM_TIME, System.currentTimeMillis());
                Intent intent = new Intent();
                intent.setAction(SettingOpenManager.SETTINGS_BIG_SCREEN_ACTION);
                intent.addCategory(SettingOpenManager.SETTINGS_BIG_CATEGORY);
                intent.putExtra(MicoSettings.BIG_CURRENT_FRAGMENT, BigSettings.STORAGE_MANAGER);
                PendingIntent activity = PendingIntent.getActivity(this.b, 0, intent, 134217728);
                MicoNotification.Builder builder = new MicoNotification.Builder(this.b);
                builder.setNotificationId(R.string.storage_manager_support_not_enough_space).setSmallIcon(R.drawable.alert_icon).setTitle(this.b.getString(R.string.notification_title_system)).setText(this.b.getString(R.string.storage_manager_suggest_clear)).setHowToDeal(this.b.getString(R.string.goto_clear)).setTimeoutAfter(Integer.MAX_VALUE).setPendingIntent(activity);
                builder.build().show();
                return;
            }
            NotificationHelper.cancelNotification(this.b, R.string.storage_manager_support_not_enough_space);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvClearEvent(AppStoreClearEvent appStoreClearEvent) {
        if (appStoreClearEvent.showAppDownloadStatus) {
            AppDownloadStatusActivity.dealNotEnoughSpace(this.b);
        }
        if (this.a == null) {
            this.a = new NotifyMessageConfirmDialog(this.b, R.layout.dialog_notify_message_confirm3).setMessage(R.string.storage_manager_not_enough_space).setButtonMessage(R.string.goto_clear);
        }
        this.a.setButtonClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.appstore.control.-$$Lambda$AppStoreUiStarter$5Vc7rXqKV6bx3sX1dJYUtrang5U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppStoreUiStarter.this.a(view);
            }
        });
        if (!this.a.isShowing()) {
            this.a.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        Intent intent = new Intent();
        intent.setAction(SettingOpenManager.SETTINGS_BIG_SCREEN_ACTION);
        intent.addCategory(SettingOpenManager.SETTINGS_BIG_CATEGORY);
        intent.putExtra(MicoSettings.BIG_CURRENT_FRAGMENT, BigSettings.STORAGE_MANAGER);
        this.b.startActivity(intent);
        this.a.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecvInstallEvent(AppStoreInstallEvent appStoreInstallEvent) {
        AppDownloadStatusActivity.startAppDownloadStatusActivity(this.b, appStoreInstallEvent.getAppInfo().getIconUrl(), appStoreInstallEvent.getAppInfo().getAppName(), appStoreInstallEvent.getDownloadId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChanged(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            AppStoreManager.getManager().checkIfGetAppListFromServer();
        }
    }
}
