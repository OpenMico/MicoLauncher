package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Build;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: CanvasUtils.java */
/* loaded from: classes.dex */
class b {
    private static Method a;
    private static Method b;
    private static boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"SoonBlockedPrivateApi"})
    public static void a(@NonNull Canvas canvas, boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= 29) {
                if (z) {
                    canvas.enableZ();
                } else {
                    canvas.disableZ();
                }
            } else if (Build.VERSION.SDK_INT != 28) {
                if (!c) {
                    try {
                        a = Canvas.class.getDeclaredMethod("insertReorderBarrier", new Class[0]);
                        a.setAccessible(true);
                        b = Canvas.class.getDeclaredMethod("insertInorderBarrier", new Class[0]);
                        b.setAccessible(true);
                    } catch (NoSuchMethodException unused) {
                    }
                    c = true;
                }
                if (z) {
                    try {
                        if (a != null) {
                            a.invoke(canvas, new Object[0]);
                        }
                    } catch (IllegalAccessException unused2) {
                        return;
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    }
                }
                if (!z && b != null) {
                    b.invoke(canvas, new Object[0]);
                }
            } else {
                throw new IllegalStateException("This method doesn't work on Pie!");
            }
        }
    }
}
