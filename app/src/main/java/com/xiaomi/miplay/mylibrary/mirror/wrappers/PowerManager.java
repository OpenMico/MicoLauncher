package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.os.Build;
import android.os.IInterface;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public final class PowerManager {
    private final IInterface a;
    private Method b;

    public PowerManager(IInterface iInterface) {
        this.a = iInterface;
    }

    private Method a() throws NoSuchMethodException {
        if (this.b == null) {
            this.b = this.a.getClass().getMethod(Build.VERSION.SDK_INT >= 20 ? "isInteractive" : "isScreenOn", new Class[0]);
        }
        return this.b;
    }

    public boolean isScreenOn() {
        try {
            return ((Boolean) a().invoke(this.a, new Object[0])).booleanValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return false;
        }
    }
}
