package com.xiaomi.micolauncher.module.video.childmode;

/* loaded from: classes3.dex */
public class ChildModeRefreshHelper {
    private boolean a = ChildModeManager.getManager().isChildMode();

    public boolean needRefresh() {
        boolean isChildMode = ChildModeManager.getManager().isChildMode();
        boolean z = isChildMode != this.a;
        this.a = isChildMode;
        return z;
    }
}
