package androidx.core.view;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class WindowCompat {
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;

    private WindowCompat() {
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull Window window, @IdRes int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (T) window.requireViewById(i);
        }
        T t = (T) window.findViewById(i);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Window");
    }

    public static void setDecorFitsSystemWindows(@NonNull Window window, boolean z) {
        if (Build.VERSION.SDK_INT >= 30) {
            b.a(window, z);
        } else if (Build.VERSION.SDK_INT >= 16) {
            a.a(window, z);
        }
    }

    @Nullable
    public static WindowInsetsControllerCompat getInsetsController(@NonNull Window window, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            return b.a(window);
        }
        return new WindowInsetsControllerCompat(window, view);
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    private static class a {
        static void a(@NonNull Window window, boolean z) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z ? systemUiVisibility & (-1793) : systemUiVisibility | 1792);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class b {
        static void a(@NonNull Window window, boolean z) {
            window.setDecorFitsSystemWindows(z);
        }

        static WindowInsetsControllerCompat a(@NonNull Window window) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                return WindowInsetsControllerCompat.toWindowInsetsControllerCompat(insetsController);
            }
            return null;
        }
    }
}
