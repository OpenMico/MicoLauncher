package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.os.IInterface;
import com.xiaomi.miplay.mylibrary.mirror.DisplayInfo;
import com.xiaomi.miplay.mylibrary.mirror.Size;

/* loaded from: classes4.dex */
public final class DisplayManager {
    private final IInterface a;

    public DisplayManager(IInterface iInterface) {
        this.a = iInterface;
    }

    public DisplayInfo getDisplayInfo() {
        try {
            Object invoke = this.a.getClass().getMethod("getDisplayInfo", Integer.TYPE).invoke(this.a, 0);
            Class<?> cls = invoke.getClass();
            return new DisplayInfo(new Size(cls.getDeclaredField("logicalWidth").getInt(invoke), cls.getDeclaredField("logicalHeight").getInt(invoke)), cls.getDeclaredField("rotation").getInt(invoke));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
