package androidx.core.os;

import android.os.Build;
import android.os.UserHandle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(17)
/* loaded from: classes.dex */
public class UserHandleCompat {
    @Nullable
    private static Method a;
    @Nullable
    private static Constructor<UserHandle> b;

    private UserHandleCompat() {
    }

    @NonNull
    public static UserHandle getUserHandleForUid(int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            return a.a(i);
        }
        try {
            return b().newInstance((Integer) a().invoke(null, Integer.valueOf(i)));
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError();
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        } catch (InstantiationException e2) {
            InstantiationError instantiationError = new InstantiationError();
            instantiationError.initCause(e2);
            throw instantiationError;
        } catch (NoSuchMethodException e3) {
            NoSuchMethodError noSuchMethodError = new NoSuchMethodError();
            noSuchMethodError.initCause(e3);
            throw noSuchMethodError;
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static class a {
        @NonNull
        static UserHandle a(int i) {
            return UserHandle.getUserHandleForUid(i);
        }
    }

    private static Method a() throws NoSuchMethodException {
        if (a == null) {
            a = UserHandle.class.getDeclaredMethod("getUserId", Integer.TYPE);
            a.setAccessible(true);
        }
        return a;
    }

    private static Constructor<UserHandle> b() throws NoSuchMethodException {
        if (b == null) {
            b = UserHandle.class.getDeclaredConstructor(Integer.TYPE);
            b.setAccessible(true);
        }
        return b;
    }
}
