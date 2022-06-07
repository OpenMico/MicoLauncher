package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.os.IInterface;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class StatusBarManager {
    private final IInterface a;
    private Method b;
    private Method c;

    public StatusBarManager(IInterface iInterface) {
        this.a = iInterface;
    }

    private Method a() throws NoSuchMethodException {
        if (this.b == null) {
            this.b = this.a.getClass().getMethod("expandNotificationsPanel", new Class[0]);
        }
        return this.b;
    }

    private Method b() throws NoSuchMethodException {
        if (this.c == null) {
            this.c = this.a.getClass().getMethod("collapsePanels", new Class[0]);
        }
        return this.c;
    }

    public void expandNotificationsPanel() {
        try {
            a().invoke(this.a, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }

    public void collapsePanels() {
        try {
            b().invoke(this.a, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }
}
