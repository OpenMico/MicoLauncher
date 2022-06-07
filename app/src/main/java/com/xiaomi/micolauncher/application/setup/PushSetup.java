package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

/* loaded from: classes3.dex */
public class PushSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        a(context);
    }

    private void a(Context context) {
        MiPushClient.registerPush(context, Constants.XM_APP_ID, Constants.XM_APP_KEY);
        Logger.setLogger(context, new LoggerInterface() { // from class: com.xiaomi.micolauncher.application.setup.PushSetup.1
            @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
            public void setTag(String str) {
            }

            @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
            public void log(String str) {
                L.push.i(str);
            }

            @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
            public void log(String str, Throwable th) {
                L.push.e(str, th);
            }
        });
    }
}
