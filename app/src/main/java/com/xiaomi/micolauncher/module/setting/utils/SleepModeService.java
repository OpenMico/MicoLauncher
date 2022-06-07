package com.xiaomi.micolauncher.module.setting.utils;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.NightModeConfig;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class SleepModeService extends IntentService {
    private NightModeConfig a;

    public SleepModeService() {
        super("SleepModeService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(@Nullable Intent intent) {
        this.a = NightModeConfig.getSleepMode(this);
        if (!this.a.isTimeSleep()) {
            return;
        }
        if (SleepMode.SLEEP_MODE_ACTION_ENTER.equals(intent.getAction())) {
            L.sleep_mode.d("[SleepModeService]: sleep_mode_action_enter");
            a(true);
        } else if (SleepMode.SLEEP_MODE_ACTION_EXIT.equals(intent.getAction())) {
            L.sleep_mode.d("[SleepModeService]: sleep_mode_action_exit");
            a(false);
            if (Hardware.isBigScreen() && this.a.isCloseScreen()) {
                ScreenUtil.turnScreenOn(this);
            }
        }
    }

    private void a(boolean z) {
        this.a.setEnable(z);
        this.a.setAuto(true);
        NightModeConfig.saveSleepMode(getApplicationContext(), this.a);
    }
}
