package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ViewGroupUtils.java */
/* loaded from: classes.dex */
class w {
    private static boolean a = true;
    private static Method b;
    private static boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static v a(@NonNull ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new u(viewGroup);
        }
        return t.a(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull ViewGroup viewGroup, boolean z) {
        if (Build.VERSION.SDK_INT >= 29) {
            viewGroup.suppressLayout(z);
        } else if (Build.VERSION.SDK_INT >= 18) {
            b(viewGroup, z);
        } else {
            x.a(viewGroup, z);
        }
    }

    @RequiresApi(18)
    @SuppressLint({"NewApi"})
    private static void b(@NonNull ViewGroup viewGroup, boolean z) {
        if (a) {
            try {
                viewGroup.suppressLayout(z);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(@NonNull ViewGroup viewGroup, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            return viewGroup.getChildDrawingOrder(i);
        }
        if (!c) {
            try {
                b = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", Integer.TYPE, Integer.TYPE);
                b.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            c = true;
        }
        Method method = b;
        if (method != null) {
            try {
                return ((Integer) method.invoke(viewGroup, Integer.valueOf(viewGroup.getChildCount()), Integer.valueOf(i))).intValue();
            } catch (IllegalAccessException | InvocationTargetException unused2) {
            }
        }
        return i;
    }
}
