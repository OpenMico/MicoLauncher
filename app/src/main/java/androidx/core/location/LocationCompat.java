package androidx.core.location;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class LocationCompat {
    @Nullable
    private static Method a;

    private LocationCompat() {
    }

    public static long getElapsedRealtimeNanos(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 17) {
            return a.a(location);
        }
        return TimeUnit.MILLISECONDS.toNanos(getElapsedRealtimeMillis(location));
    }

    public static long getElapsedRealtimeMillis(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TimeUnit.NANOSECONDS.toMillis(a.a(location));
        }
        long currentTimeMillis = System.currentTimeMillis() - location.getTime();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (currentTimeMillis < 0) {
            return elapsedRealtime;
        }
        if (currentTimeMillis > elapsedRealtime) {
            return 0L;
        }
        return elapsedRealtime - currentTimeMillis;
    }

    public static boolean isMock(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 18) {
            return b.a(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return false;
        }
        return extras.getBoolean("mockLocation", false);
    }

    public static void setMock(@NonNull Location location, boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                a().invoke(location, Boolean.valueOf(z));
            } catch (IllegalAccessException e) {
                IllegalAccessError illegalAccessError = new IllegalAccessError();
                illegalAccessError.initCause(e);
                throw illegalAccessError;
            } catch (NoSuchMethodException e2) {
                NoSuchMethodError noSuchMethodError = new NoSuchMethodError();
                noSuchMethodError.initCause(e2);
                throw noSuchMethodError;
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3);
            }
        } else {
            Bundle extras = location.getExtras();
            if (extras == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("mockLocation", true);
                location.setExtras(bundle);
                return;
            }
            extras.putBoolean("mockLocation", true);
        }
    }

    @RequiresApi(18)
    /* loaded from: classes.dex */
    private static class b {
        @DoNotInline
        static boolean a(Location location) {
            return location.isFromMockProvider();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(17)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static long a(Location location) {
            return location.getElapsedRealtimeNanos();
        }
    }

    private static Method a() throws NoSuchMethodException {
        if (a == null) {
            a = Location.class.getDeclaredMethod("setIsFromMockProvider", Boolean.TYPE);
            a.setAccessible(true);
        }
        return a;
    }
}
