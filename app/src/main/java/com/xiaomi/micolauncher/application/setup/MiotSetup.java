package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.services.job.AlarmRunStateCheckJobService;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.MiotProviderImp;
import com.xiaomi.micolauncher.module.miot.OtInvokers;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotLogger;
import com.xiaomi.miot.support.MiotManager;

/* loaded from: classes3.dex */
public class MiotSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(final Context context) {
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature(MiotDeviceManager.SE_HARDWARE_FEATURE);
        L.base.d("MiotDeviceManager.init supportMiotSe=%s", Boolean.valueOf(hasSystemFeature));
        MiotHostManager.getInstance().setMijiaSe(context, hasSystemFeature);
        MiotDeviceManager.init(context, new MiotDeviceManager.OnBindListener() { // from class: com.xiaomi.micolauncher.application.setup.MiotSetup.1
            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onBindSuccess() {
                OtInvokers.getInstance().registerMethodsToReceive(context);
            }

            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onBindFailed(int i) {
                L.ot.e("MiotSetup bind OT onFailed=%s", Integer.valueOf(i));
            }

            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onStartDevice(boolean z) {
                L.ot.d("MiotSetup bind OT onStartDevice=%s", Boolean.valueOf(z));
                if (!z) {
                    return;
                }
                if (!SystemSetting.isInitializedNoCheckAccount(context)) {
                    OtInvokers.getInstance().sendWifiConfStatus(context, 3);
                } else {
                    OtInvokers.getInstance().sendWifiConfStatus(context, 1);
                }
            }
        });
        String userId = LoginManager.get().getUserId();
        Log.i(MicoSupConstants.TAG_LAU, "Info: MiotManager.init, userId: " + userId);
        MiotManager.init(context, new MiotProviderImp(), false, new MiotLogger() { // from class: com.xiaomi.micolauncher.application.setup.MiotSetup.2
            @Override // com.xiaomi.miot.support.MiotLogger
            public void d(String str) {
                L.miot.d(str);
            }

            @Override // com.xiaomi.miot.support.MiotLogger
            public void i(String str) {
                L.miot.i(str);
            }

            @Override // com.xiaomi.miot.support.MiotLogger
            public void w(String str) {
                L.miot.w(str);
            }

            @Override // com.xiaomi.miot.support.MiotLogger
            public void e(String str) {
                L.miot.e(str);
            }
        }, false);
        if (!TextUtils.isEmpty(userId) && SystemSetting.isInitialized(context)) {
            MiotManager.onLogon();
        }
        MiotManager.onDeviceIdUpdated(SystemSetting.getMiotDeviceId());
        AlarmRunStateCheckJobService.JobStarter.start(context);
    }
}
