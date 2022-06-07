package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;

/* loaded from: classes3.dex */
public class AppStoreSetupBeforeLogin implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return true;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        AppStoreManager.getManager().initBeforeLogin(context);
    }
}
