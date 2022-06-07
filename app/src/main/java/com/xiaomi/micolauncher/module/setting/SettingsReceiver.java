package com.xiaomi.micolauncher.module.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class SettingsReceiver extends BroadcastReceiver {
    public static final String SETTINGS_ACTION = "SETTINGS_ACTION";

    /* loaded from: classes3.dex */
    enum a {
        RESET
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(SETTINGS_ACTION);
        L.base.d("[SettingsReceiver]: action=%s", stringExtra);
        if (stringExtra != null && stringExtra.equals(a.RESET.name())) {
            AudioPolicyService.getInstance().requestForceStopAll(AudioSource.AUDIO_SOURCE_FACTORY_RESET);
        }
    }
}
