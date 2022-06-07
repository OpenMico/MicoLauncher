package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;
import com.xiaomi.miplay.mylibrary.mirror.Ln;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressLint({"PrivateApi"})
/* loaded from: classes4.dex */
public final class SurfaceControl {
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    private static final Class<?> a;
    private static Method b;
    private static Method c;

    static {
        try {
            a = Class.forName("android.view.SurfaceControl");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    private SurfaceControl() {
    }

    public static void openTransaction() {
        try {
            a.getMethod("openTransaction", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void closeTransaction() {
        try {
            a.getMethod("closeTransaction", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void setDisplayProjection(IBinder iBinder, int i, Rect rect, Rect rect2) {
        try {
            a.getMethod("setDisplayProjection", IBinder.class, Integer.TYPE, Rect.class, Rect.class).invoke(null, iBinder, Integer.valueOf(i), rect, rect2);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void setDisplayLayerStack(IBinder iBinder, int i) {
        try {
            a.getMethod("setDisplayLayerStack", IBinder.class, Integer.TYPE).invoke(null, iBinder, Integer.valueOf(i));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void setDisplaySurface(IBinder iBinder, Surface surface) {
        try {
            Log.e("MiPlayQuickSurface", "class" + surface + Constants.ACCEPT_TIME_SEPARATOR_SP + iBinder);
            a.getMethod("setDisplaySurface", IBinder.class, Surface.class).invoke(null, iBinder, surface);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static IBinder createDisplay(String str, boolean z) {
        try {
            Log.e("MiPlayQuickSurface", "class" + a);
            return (IBinder) a.getMethod("createDisplay", String.class, Boolean.TYPE).invoke(null, str, Boolean.valueOf(z));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private static Method a() throws NoSuchMethodException {
        if (b == null) {
            if (Build.VERSION.SDK_INT < 29) {
                b = a.getMethod("getBuiltInDisplay", Integer.TYPE);
            } else {
                b = a.getMethod("getInternalDisplayToken", new Class[0]);
            }
        }
        return b;
    }

    public static IBinder getBuiltInDisplay() {
        try {
            Method a2 = a();
            return Build.VERSION.SDK_INT < 29 ? (IBinder) a2.invoke(null, 0) : (IBinder) a2.invoke(null, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
            return null;
        }
    }

    private static Method b() throws NoSuchMethodException {
        if (c == null) {
            c = a.getMethod("setDisplayPowerMode", IBinder.class, Integer.TYPE);
        }
        return c;
    }

    public static void setDisplayPowerMode(IBinder iBinder, int i) {
        try {
            b().invoke(null, iBinder, Integer.valueOf(i));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Ln.e("Could not invoke method", e);
        }
    }

    public static void destroyDisplay(IBinder iBinder) {
        try {
            a.getMethod("destroyDisplay", IBinder.class).invoke(null, iBinder);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
