package com.google.android.exoplayer2.util;

import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public final class BundleUtil {
    @Nullable
    private static Method a;
    @Nullable
    private static Method b;

    @Nullable
    public static IBinder getBinder(Bundle bundle, @Nullable String str) {
        if (Util.SDK_INT >= 18) {
            return bundle.getBinder(str);
        }
        return a(bundle, str);
    }

    public static void putBinder(Bundle bundle, @Nullable String str, @Nullable IBinder iBinder) {
        if (Util.SDK_INT >= 18) {
            bundle.putBinder(str, iBinder);
        } else {
            a(bundle, str, iBinder);
        }
    }

    @Nullable
    private static IBinder a(Bundle bundle, @Nullable String str) {
        Method method = a;
        if (method == null) {
            try {
                a = Bundle.class.getMethod("getIBinder", String.class);
                a.setAccessible(true);
                method = a;
            } catch (NoSuchMethodException e) {
                Log.i("BundleUtil", "Failed to retrieve getIBinder method", e);
                return null;
            }
        }
        try {
            return (IBinder) method.invoke(bundle, str);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
            Log.i("BundleUtil", "Failed to invoke getIBinder via reflection", e2);
            return null;
        }
    }

    private static void a(Bundle bundle, @Nullable String str, @Nullable IBinder iBinder) {
        Method method = b;
        if (method == null) {
            try {
                b = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                b.setAccessible(true);
                method = b;
            } catch (NoSuchMethodException e) {
                Log.i("BundleUtil", "Failed to retrieve putIBinder method", e);
                return;
            }
        }
        try {
            method.invoke(bundle, str, iBinder);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
            Log.i("BundleUtil", "Failed to invoke putIBinder via reflection", e2);
        }
    }

    private BundleUtil() {
    }
}
