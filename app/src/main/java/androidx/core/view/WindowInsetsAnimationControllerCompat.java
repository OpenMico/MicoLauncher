package androidx.core.view;

import android.os.Build;
import android.view.WindowInsetsAnimationController;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;

/* loaded from: classes.dex */
public final class WindowInsetsAnimationControllerCompat {
    private final a a;

    WindowInsetsAnimationControllerCompat() {
        if (Build.VERSION.SDK_INT < 30) {
            this.a = new a();
            return;
        }
        throw new UnsupportedOperationException("On API 30+, the constructor taking a " + WindowInsetsAnimationController.class.getSimpleName() + " as parameter");
    }

    @NonNull
    public Insets getHiddenStateInsets() {
        return this.a.a();
    }

    @NonNull
    public Insets getShownStateInsets() {
        return this.a.b();
    }

    @NonNull
    public Insets getCurrentInsets() {
        return this.a.c();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getCurrentFraction() {
        return this.a.d();
    }

    public float getCurrentAlpha() {
        return this.a.e();
    }

    public int getTypes() {
        return this.a.f();
    }

    public void setInsetsAndAlpha(@Nullable Insets insets, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.a.a(insets, f, f2);
    }

    public void finish(boolean z) {
        this.a.a(z);
    }

    public boolean isReady() {
        return !isFinished() && !isCancelled();
    }

    public boolean isFinished() {
        return this.a.g();
    }

    public boolean isCancelled() {
        return this.a.h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        public void a(@Nullable Insets insets, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        }

        void a(boolean z) {
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float d() {
            return 0.0f;
        }

        public float e() {
            return 0.0f;
        }

        public int f() {
            return 0;
        }

        boolean g() {
            return false;
        }

        boolean h() {
            return true;
        }

        a() {
        }

        @NonNull
        public Insets a() {
            return Insets.NONE;
        }

        @NonNull
        public Insets b() {
            return Insets.NONE;
        }

        @NonNull
        public Insets c() {
            return Insets.NONE;
        }
    }
}
