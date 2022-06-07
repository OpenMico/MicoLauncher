package androidx.core.app;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class BundleCompat {

    /* loaded from: classes.dex */
    static class a {
        private static Method a;
        private static boolean b;
        private static Method c;
        private static boolean d;

        public static IBinder a(Bundle bundle, String str) {
            if (!b) {
                try {
                    a = Bundle.class.getMethod("getIBinder", String.class);
                    a.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i("BundleCompatBaseImpl", "Failed to retrieve getIBinder method", e);
                }
                b = true;
            }
            Method method = a;
            if (method != null) {
                try {
                    return (IBinder) method.invoke(bundle, str);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i("BundleCompatBaseImpl", "Failed to invoke getIBinder via reflection", e2);
                    a = null;
                }
            }
            return null;
        }

        public static void a(Bundle bundle, String str, IBinder iBinder) {
            if (!d) {
                try {
                    c = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                    c.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i("BundleCompatBaseImpl", "Failed to retrieve putIBinder method", e);
                }
                d = true;
            }
            Method method = c;
            if (method != null) {
                try {
                    method.invoke(bundle, str, iBinder);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i("BundleCompatBaseImpl", "Failed to invoke putIBinder via reflection", e2);
                    c = null;
                }
            }
        }
    }

    private BundleCompat() {
    }

    @Nullable
    public static IBinder getBinder(@NonNull Bundle bundle, @Nullable String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            return bundle.getBinder(str);
        }
        return a.a(bundle, str);
    }

    public static void putBinder(@NonNull Bundle bundle, @Nullable String str, @Nullable IBinder iBinder) {
        if (Build.VERSION.SDK_INT >= 18) {
            bundle.putBinder(str, iBinder);
        } else {
            a.a(bundle, str, iBinder);
        }
    }
}
