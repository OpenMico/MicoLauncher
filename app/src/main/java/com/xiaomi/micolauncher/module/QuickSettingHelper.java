package com.xiaomi.micolauncher.module;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class QuickSettingHelper {
    public static void enable(Context context, boolean z) {
        if (!ChildModeManager.getManager().isScreenLock() || !z) {
            Intent intent = new Intent("com.xiaomi.mico.action.init");
            intent.putExtra("initialing", !z);
            L.base.i("system bar enable %s", Boolean.valueOf(z));
            context.getApplicationContext().sendStickyBroadcast(intent);
        }
    }
}
