package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: GhostViewPlatform.java */
@RequiresApi(21)
/* loaded from: classes.dex */
class f implements d {
    private static Class<?> a;
    private static boolean b;
    private static Method c;
    private static boolean d;
    private static Method e;
    private static boolean f;
    private final View g;

    @Override // androidx.transition.d
    public void a(ViewGroup viewGroup, View view) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static d a(View view, ViewGroup viewGroup, Matrix matrix) {
        b();
        Method method = c;
        if (method != null) {
            try {
                return new f((View) method.invoke(null, view, viewGroup, matrix));
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(View view) {
        c();
        Method method = e;
        if (method != null) {
            try {
                method.invoke(null, view);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
    }

    private f(@NonNull View view) {
        this.g = view;
    }

    @Override // androidx.transition.d
    public void setVisibility(int i) {
        this.g.setVisibility(i);
    }

    private static void a() {
        if (!b) {
            try {
                a = Class.forName("android.view.GhostView");
            } catch (ClassNotFoundException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve GhostView class", e2);
            }
            b = true;
        }
    }

    private static void b() {
        if (!d) {
            try {
                a();
                c = a.getDeclaredMethod("addGhost", View.class, ViewGroup.class, Matrix.class);
                c.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve addGhost method", e2);
            }
            d = true;
        }
    }

    private static void c() {
        if (!f) {
            try {
                a();
                e = a.getDeclaredMethod("removeGhost", View.class);
                e.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("GhostViewApi21", "Failed to retrieve removeGhost method", e2);
            }
            f = true;
        }
    }
}
