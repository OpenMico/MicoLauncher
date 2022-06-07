package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.os.IInterface;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public final class WindowManager {
    private final IInterface a;
    private Method b;
    private Method c;
    private Method d;
    private Method e;

    public WindowManager(IInterface iInterface) {
        this.a = iInterface;
    }

    private Method a() throws NoSuchMethodException {
        if (this.b == null) {
            Class<?> cls = this.a.getClass();
            try {
                this.b = cls.getMethod("getDefaultDisplayRotation", new Class[0]);
            } catch (NoSuchMethodException unused) {
                this.b = cls.getMethod("getRotation", new Class[0]);
            }
        }
        return this.b;
    }

    private Method b() throws NoSuchMethodException {
        if (this.c == null) {
            this.c = this.a.getClass().getMethod("freezeRotation", Integer.TYPE);
        }
        return this.c;
    }

    private Method c() throws NoSuchMethodException {
        if (this.d == null) {
            this.d = this.a.getClass().getMethod("isRotationFrozen", new Class[0]);
        }
        return this.d;
    }

    private Method d() throws NoSuchMethodException {
        if (this.e == null) {
            this.e = this.a.getClass().getMethod("thawRotation", new Class[0]);
        }
        return this.e;
    }

    public int getRotation() {
        try {
            return ((Integer) a().invoke(this.a, new Object[0])).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return 0;
        }
    }

    public void freezeRotation(int i) {
        try {
            b().invoke(this.a, Integer.valueOf(i));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }

    public boolean isRotationFrozen() {
        try {
            return ((Boolean) c().invoke(this.a, new Object[0])).booleanValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return false;
        }
    }

    public void thawRotation() {
        try {
            d().invoke(this.a, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }
}
