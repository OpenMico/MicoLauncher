package com.xiaomi.micolauncher.common.systemui;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class SystemUIManager {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final SystemUIManager a = new SystemUIManager();
    }

    public static SystemUIManager getDefault() {
        return a.a;
    }

    private SystemUIManager() {
    }

    public void enableGlobalGesture(Context context) {
        a(context, true);
    }

    public void disableGlobalGesture(Context context) {
        a(context, false);
    }

    private void a(Context context, boolean z) {
        L.base.i("toggle global gesture %b", Boolean.valueOf(z));
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.mico.action.toggle_globalgesture");
        intent.setPackage(Constants.PKG_SYSTEM_UI);
        intent.putExtra("enable", z);
        context.sendBroadcast(intent);
    }
}
