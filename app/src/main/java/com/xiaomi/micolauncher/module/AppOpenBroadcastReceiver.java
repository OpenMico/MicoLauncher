package com.xiaomi.micolauncher.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.micolauncher.application.setup.ReceiversManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;

/* loaded from: classes3.dex */
public class AppOpenBroadcastReceiver extends BroadcastReceiver implements ReceiversManager.IAppBroadcastReceiverRule {
    private final Context a;

    /* loaded from: classes3.dex */
    private enum a {
        MICO_SETTING
    }

    public AppOpenBroadcastReceiver(Context context) {
        this.a = context;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("KEY_APPLICATION_NAME");
        L.launcher.d("AppOpenBroadcastReceiver get broadcast to open application: " + stringExtra);
        if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals(a.MICO_SETTING.name())) {
            String stringExtra2 = intent.getStringExtra("KEY_MICO_SETTING_FRAGMENT_TYPE");
            BigSettings[] values = BigSettings.values();
            for (BigSettings bigSettings : values) {
                if (TextUtils.equals(stringExtra2, bigSettings.name())) {
                    SettingOpenManager.openSetting(context, bigSettings);
                    return;
                }
            }
            SettingOpenManager.openSetting(context, BigSettings.DEFAULT);
        }
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_OPEN_APPLICATION");
        this.a.registerReceiver(this, intentFilter);
    }

    @Override // com.xiaomi.micolauncher.application.setup.ReceiversManager.IAppBroadcastReceiverRule
    public void destroy() {
        this.a.unregisterReceiver(this);
    }
}
