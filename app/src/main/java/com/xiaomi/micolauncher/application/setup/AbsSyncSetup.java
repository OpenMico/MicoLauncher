package com.xiaomi.micolauncher.application.setup;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;

/* loaded from: classes3.dex */
public abstract class AbsSyncSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return true;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    protected Application getApp(Context context) {
        if (context instanceof Application) {
            return (Application) context;
        }
        if (context instanceof Activity) {
            return ((Activity) context).getApplication();
        }
        if (context instanceof Service) {
            return ((Service) context).getApplication();
        }
        throw new IllegalArgumentException("context is not an application : " + context);
    }
}
