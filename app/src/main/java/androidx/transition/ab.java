package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/* compiled from: ViewUtils.java */
/* loaded from: classes.dex */
public class ab {
    static final Property<View, Float> a;
    static final Property<View, Rect> b;
    private static final ah c;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            c = new ag();
        } else if (Build.VERSION.SDK_INT >= 23) {
            c = new af();
        } else if (Build.VERSION.SDK_INT >= 22) {
            c = new ae();
        } else if (Build.VERSION.SDK_INT >= 21) {
            c = new ad();
        } else if (Build.VERSION.SDK_INT >= 19) {
            c = new ac();
        } else {
            c = new ah();
        }
        a = new Property<View, Float>(Float.class, "translationAlpha") { // from class: androidx.transition.ab.1
            /* renamed from: a */
            public Float get(View view) {
                return Float.valueOf(ab.c(view));
            }

            /* renamed from: a */
            public void set(View view, Float f) {
                ab.a(view, f.floatValue());
            }
        };
        b = new Property<View, Rect>(Rect.class, "clipBounds") { // from class: androidx.transition.ab.2
            /* renamed from: a */
            public Rect get(View view) {
                return ViewCompat.getClipBounds(view);
            }

            /* renamed from: a */
            public void set(View view, Rect rect) {
                ViewCompat.setClipBounds(view, rect);
            }
        };
    }

    public static aa a(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new z(view);
        }
        return y.d(view);
    }

    public static ak b(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new aj(view);
        }
        return new ai(view.getWindowToken());
    }

    public static void a(@NonNull View view, float f) {
        c.a(view, f);
    }

    public static float c(@NonNull View view) {
        return c.a(view);
    }

    public static void d(@NonNull View view) {
        c.b(view);
    }

    public static void e(@NonNull View view) {
        c.c(view);
    }

    public static void a(@NonNull View view, int i) {
        c.a(view, i);
    }

    public static void a(@NonNull View view, @NonNull Matrix matrix) {
        c.a(view, matrix);
    }

    public static void b(@NonNull View view, @NonNull Matrix matrix) {
        c.b(view, matrix);
    }

    public static void c(@NonNull View view, @Nullable Matrix matrix) {
        c.c(view, matrix);
    }

    public static void a(@NonNull View view, int i, int i2, int i3, int i4) {
        c.a(view, i, i2, i3, i4);
    }
}
