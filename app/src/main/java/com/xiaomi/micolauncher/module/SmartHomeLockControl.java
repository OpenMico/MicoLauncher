package com.xiaomi.micolauncher.module;

import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class SmartHomeLockControl {
    private static final SmartHomeLockControl a = new SmartHomeLockControl();
    private boolean b = true;

    private SmartHomeLockControl() {
    }

    public static SmartHomeLockControl getIns() {
        return a;
    }

    public boolean isNeedShowLock() {
        return !ChildModeManager.getManager().isChildMode() && this.b && LockSetting.getHasLock(MicoApplication.getApp(), LockSetting.PACKAGE_SMART_HOME);
    }

    public void setNeedShowLock(boolean z) {
        this.b = z;
    }
}
