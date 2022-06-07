package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class ProcessCompat {
    private ProcessCompat() {
    }

    public static boolean isApplicationUid(int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            return c.a(i);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            return b.a(i);
        }
        if (Build.VERSION.SDK_INT == 16) {
            return a.a(i);
        }
        return true;
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    static class c {
        static boolean a(int i) {
            return Process.isApplicationUid(i);
        }
    }

    @RequiresApi(17)
    /* loaded from: classes.dex */
    static class b {
        private static final Object a = new Object();
        private static Method b;
        private static boolean c;

        @SuppressLint({"DiscouragedPrivateApi"})
        static boolean a(int i) {
            try {
                synchronized (a) {
                    if (!c) {
                        c = true;
                        b = UserHandle.class.getDeclaredMethod("isApp", Integer.TYPE);
                    }
                }
                if (b != null && ((Boolean) b.invoke(null, Integer.valueOf(i))) == null) {
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    static class a {
        private static final Object a = new Object();
        private static Method b;
        private static boolean c;

        @SuppressLint({"PrivateApi"})
        static boolean a(int i) {
            try {
                synchronized (a) {
                    if (!c) {
                        c = true;
                        b = Class.forName("android.os.UserId").getDeclaredMethod("isApp", Integer.TYPE);
                    }
                }
                if (b != null) {
                    Boolean bool = (Boolean) b.invoke(null, Integer.valueOf(i));
                    if (bool != null) {
                        return bool.booleanValue();
                    }
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
