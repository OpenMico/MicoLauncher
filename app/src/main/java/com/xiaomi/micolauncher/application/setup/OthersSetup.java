package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.provider.Settings;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.player.policy.FakeClient;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.utils.VideoMonitorHelper;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class OthersSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        OperationManager.init(context);
        NotificationHelper.init(context);
        Mic.getInstance();
        FakeClient.init(context);
        NetworkMonitor.getInstance().init(context);
        String string = Settings.Global.getString(context.getContentResolver(), ai.J);
        String str = Hardware.getDefaultName(context) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + com.xiaomi.micolauncher.application.Constants.getSnSimple();
        if (string != null && !string.equals(str)) {
            Settings.Global.putString(context.getContentResolver(), ai.J, str);
        }
        VideoMonitorHelper.getInstance().updateLowLevelCameraFunctionsStateIfNeeded();
    }
}
