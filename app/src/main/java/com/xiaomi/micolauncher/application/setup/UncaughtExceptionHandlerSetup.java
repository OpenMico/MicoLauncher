package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.common.crash.AppCrashHandler;

/* loaded from: classes3.dex */
public class UncaughtExceptionHandlerSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(context));
    }
}
