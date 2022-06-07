package com.xiaomi.micolauncher.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.miot.support.MiotManager;

/* loaded from: classes3.dex */
public class AccountLoginReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        L.init.w("AccountLoginReceiver onReceive will restartMIoTService");
        SpeechManager.getInstance().forceRefreshToken();
        MiotManager.onLogon();
        MiotDeviceManager.getInstance().restartServiceAfterLogin();
        RecommendDataManager.getManager().loadData(context);
    }
}
