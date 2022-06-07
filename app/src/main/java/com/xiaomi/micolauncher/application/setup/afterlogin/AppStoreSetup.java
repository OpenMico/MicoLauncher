package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.module.appstore.manager.AppDownloadManager;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;

/* loaded from: classes3.dex */
public class AppStoreSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (Hardware.isBigScreen()) {
            AppStoreManager.getManager().init(context);
            AppDownloadManager.getAppDownloadManager().init(context);
        }
    }
}
