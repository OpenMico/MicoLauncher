package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.R;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class WindowInsetsAnimationCompat {
    private a a;

    public WindowInsetsAnimationCompat(int i, @Nullable Interpolator interpolator, long j) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new c(i, interpolator, j);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.a = new b(i, interpolator, j);
        } else {
            this.a = new a(0, interpolator, j);
        }
    }

    public int getTypeMask() {
        return this.a.a();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getFraction() {
        return this.a.b();
    }

    public float getInterpolatedFraction() {
        return this.a.c();
    }

    @Nullable
    public Interpolator getInterpolator() {
        return this.a.d();
    }

    public long getDurationMillis() {
        return this.a.e();
    }

    public void setFraction(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.a.a(f);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAlpha() {
        return this.a.f();
    }

    public void setAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.a.b(f);
    }

    /* loaded from: classes.dex */
    public static final class BoundsCompat {
        private final Insets a;
        private final Insets b;

        public BoundsCompat(@NonNull Insets insets, @NonNull Insets insets2) {
            this.a = insets;
            this.b = insets2;
        }

        @RequiresApi(30)
        private BoundsCompat(@NonNull WindowInsetsAnimation.Bounds bounds) {
            this.a = c.a(bounds);
            this.b = c.b(bounds);
        }

        @NonNull
        public Insets getLowerBound() {
            return this.a;
        }

        @NonNull
        public Insets getUpperBound() {
            return this.b;
        }

        @NonNull
        public BoundsCompat inset(@NonNull Insets insets) {
            return new BoundsCompat(WindowInsetsCompat.a(this.a, insets.left, insets.top, insets.right, insets.bottom), WindowInsetsCompat.a(this.b, insets.left, insets.top, insets.right, insets.bottom));
        }

        public String toString() {
            return "Bounds{lower=" + this.a + " upper=" + this.b + "}";
        }

        @NonNull
        @RequiresApi(30)
        public WindowInsetsAnimation.Bounds toBounds() {
            return c.a(this);
        }

        @NonNull
        @RequiresApi(30)
        public static BoundsCompat toBoundsCompat(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return new BoundsCompat(bounds);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public static final int DISPATCH_MODE_CONTINUE_ON_SUBTREE = 1;
        public static final int DISPATCH_MODE_STOP = 0;
        WindowInsets a;
        private final int b;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public @interface DispatchMode {
        }

        public void onEnd(@NonNull WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
        }

        public void onPrepare(@NonNull WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
        }

        @NonNull
        public abstract WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull List<WindowInsetsAnimationCompat> list);

        @NonNull
        public BoundsCompat onStart(@NonNull WindowInsetsAnimationCompat windowInsetsAnimationCompat, @NonNull BoundsCompat boundsCompat) {
            return boundsCompat;
        }

        public Callback(int i) {
            this.b = i;
        }

        public final int getDispatchMode() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull View view, @Nullable Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            c.a(view, callback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            b.a(view, callback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private final int a;
        private float b;
        @Nullable
        private final Interpolator c;
        private final long d;
        private float e;

        a(int i, @Nullable Interpolator interpolator, long j) {
            this.a = i;
            this.c = interpolator;
            this.d = j;
        }

        public int a() {
            return this.a;
        }

        public float b() {
            return this.b;
        }

        public float c() {
            Interpolator interpolator = this.c;
            if (interpolator != null) {
                return interpolator.getInterpolation(this.b);
            }
            return this.b;
        }

        @Nullable
        public Interpolator d() {
            return this.c;
        }

        public long e() {
            return this.d;
        }

        public float f() {
            return this.e;
        }

        public void a(float f) {
            this.b = f;
        }

        public void b(float f) {
            this.e = f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class b extends a {
        b(int i, @Nullable Interpolator interpolator, long j) {
            super(i, interpolator, j);
        }

        static void a(@NonNull View view, @Nullable Callback callback) {
            Object tag = view.getTag(R.id.tag_on_apply_window_listener);
            if (callback == null) {
                view.setTag(R.id.tag_window_insets_animation_callback, null);
                if (tag == null) {
                    view.setOnApplyWindowInsetsListener(null);
                    return;
                }
                return;
            }
            View.OnApplyWindowInsetsListener b = b(view, callback);
            view.setTag(R.id.tag_window_insets_animation_callback, b);
            if (tag == null) {
                view.setOnApplyWindowInsetsListener(b);
            }
        }

        @NonNull
        private static View.OnApplyWindowInsetsListener b(@NonNull View view, @NonNull Callback callback) {
            return new a(view, callback);
        }

        @NonNull
        static BoundsCompat a(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsetsCompat windowInsetsCompat2, int i) {
            Insets insets = windowInsetsCompat.getInsets(i);
            Insets insets2 = windowInsetsCompat2.getInsets(i);
            return new BoundsCompat(Insets.of(Math.min(insets.left, insets2.left), Math.min(insets.top, insets2.top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom)), Insets.of(Math.max(insets.left, insets2.left), Math.max(insets.top, insets2.top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom)));
        }

        @SuppressLint({"WrongConstant"})
        static int a(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsetsCompat windowInsetsCompat2) {
            int i = 0;
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if (!windowInsetsCompat.getInsets(i2).equals(windowInsetsCompat2.getInsets(i2))) {
                    i |= i2;
                }
            }
            return i;
        }

        @SuppressLint({"WrongConstant"})
        static WindowInsetsCompat a(WindowInsetsCompat windowInsetsCompat, WindowInsetsCompat windowInsetsCompat2, float f, int i) {
            WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(windowInsetsCompat);
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) == 0) {
                    builder.setInsets(i2, windowInsetsCompat.getInsets(i2));
                } else {
                    Insets insets = windowInsetsCompat.getInsets(i2);
                    Insets insets2 = windowInsetsCompat2.getInsets(i2);
                    float f2 = 1.0f - f;
                    builder.setInsets(i2, WindowInsetsCompat.a(insets, (int) (((insets.left - insets2.left) * f2) + 0.5d), (int) (((insets.top - insets2.top) * f2) + 0.5d), (int) (((insets.right - insets2.right) * f2) + 0.5d), (int) (((insets.bottom - insets2.bottom) * f2) + 0.5d)));
                }
            }
            return builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresApi(21)
        /* loaded from: classes.dex */
        public static class a implements View.OnApplyWindowInsetsListener {
            final Callback a;
            private WindowInsetsCompat b;

            a(@NonNull View view, @NonNull Callback callback) {
                this.a = callback;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
                this.b = rootWindowInsets != null ? new WindowInsetsCompat.Builder(rootWindowInsets).build() : null;
            }

            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(final View view, WindowInsets windowInsets) {
                if (!view.isLaidOut()) {
                    this.b = WindowInsetsCompat.toWindowInsetsCompat(windowInsets, view);
                    return b.a(view, windowInsets);
                }
                final WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets, view);
                if (this.b == null) {
                    this.b = ViewCompat.getRootWindowInsets(view);
                }
                if (this.b == null) {
                    this.b = windowInsetsCompat;
                    return b.a(view, windowInsets);
                }
                Callback a = b.a(view);
                if (a != null && Objects.equals(a.a, windowInsets)) {
                    return b.a(view, windowInsets);
                }
                final int a2 = b.a(windowInsetsCompat, this.b);
                if (a2 == 0) {
                    return b.a(view, windowInsets);
                }
                final WindowInsetsCompat windowInsetsCompat2 = this.b;
                final WindowInsetsAnimationCompat windowInsetsAnimationCompat = new WindowInsetsAnimationCompat(a2, new DecelerateInterpolator(), 160L);
                windowInsetsAnimationCompat.setFraction(0.0f);
                final ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(windowInsetsAnimationCompat.getDurationMillis());
                final BoundsCompat a3 = b.a(windowInsetsCompat, windowInsetsCompat2, a2);
                b.a(view, windowInsetsAnimationCompat, windowInsets, false);
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.core.view.WindowInsetsAnimationCompat.b.a.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        windowInsetsAnimationCompat.setFraction(valueAnimator.getAnimatedFraction());
                        b.a(view, b.a(windowInsetsCompat, windowInsetsCompat2, windowInsetsAnimationCompat.getInterpolatedFraction(), a2), Collections.singletonList(windowInsetsAnimationCompat));
                    }
                });
                duration.addListener(new AnimatorListenerAdapter() { // from class: androidx.core.view.WindowInsetsAnimationCompat.b.a.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        windowInsetsAnimationCompat.setFraction(1.0f);
                        b.a(view, windowInsetsAnimationCompat);
                    }
                });
                OneShotPreDrawListener.add(view, new Runnable() { // from class: androidx.core.view.WindowInsetsAnimationCompat.b.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        b.a(view, windowInsetsAnimationCompat, a3);
                        duration.start();
                    }
                });
                this.b = windowInsetsCompat;
                return b.a(view, windowInsets);
            }
        }

        @NonNull
        static WindowInsets a(@NonNull View view, @NonNull WindowInsets windowInsets) {
            return view.getTag(R.id.tag_on_apply_window_listener) != null ? windowInsets : view.onApplyWindowInsets(windowInsets);
        }

        static void a(View view, WindowInsetsAnimationCompat windowInsetsAnimationCompat, WindowInsets windowInsets, boolean z) {
            Callback a2 = a(view);
            if (a2 != null) {
                a2.a = windowInsets;
                if (!z) {
                    a2.onPrepare(windowInsetsAnimationCompat);
                    z = a2.getDispatchMode() == 0;
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    a(viewGroup.getChildAt(i), windowInsetsAnimationCompat, windowInsets, z);
                }
            }
        }

        static void a(View view, WindowInsetsAnimationCompat windowInsetsAnimationCompat, BoundsCompat boundsCompat) {
            Callback a2 = a(view);
            if (a2 != null) {
                a2.onStart(windowInsetsAnimationCompat, boundsCompat);
                if (a2.getDispatchMode() == 0) {
                    return;
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    a(viewGroup.getChildAt(i), windowInsetsAnimationCompat, boundsCompat);
                }
            }
        }

        static void a(@NonNull View view, @NonNull WindowInsetsCompat windowInsetsCompat, @NonNull List<WindowInsetsAnimationCompat> list) {
            Callback a2 = a(view);
            if (a2 != null) {
                windowInsetsCompat = a2.onProgress(windowInsetsCompat, list);
                if (a2.getDispatchMode() == 0) {
                    return;
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    a(viewGroup.getChildAt(i), windowInsetsCompat, list);
                }
            }
        }

        static void a(@NonNull View view, @NonNull WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
            Callback a2 = a(view);
            if (a2 != null) {
                a2.onEnd(windowInsetsAnimationCompat);
                if (a2.getDispatchMode() == 0) {
                    return;
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    a(viewGroup.getChildAt(i), windowInsetsAnimationCompat);
                }
            }
        }

        @Nullable
        static Callback a(View view) {
            Object tag = view.getTag(R.id.tag_window_insets_animation_callback);
            if (tag instanceof a) {
                return ((a) tag).a;
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class c extends a {
        @NonNull
        private final WindowInsetsAnimation a;

        c(@NonNull WindowInsetsAnimation windowInsetsAnimation) {
            super(0, null, 0L);
            this.a = windowInsetsAnimation;
        }

        c(int i, Interpolator interpolator, long j) {
            this(new WindowInsetsAnimation(i, interpolator, j));
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        public int a() {
            return this.a.getTypeMask();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        @Nullable
        public Interpolator d() {
            return this.a.getInterpolator();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        public long e() {
            return this.a.getDurationMillis();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        public float b() {
            return this.a.getFraction();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        public void a(float f) {
            this.a.setFraction(f);
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.a
        public float c() {
            return this.a.getInterpolatedFraction();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @RequiresApi(30)
        /* loaded from: classes.dex */
        public static class a extends WindowInsetsAnimation.Callback {
            private final Callback a;
            private final HashMap<WindowInsetsAnimation, WindowInsetsAnimationCompat> b = new HashMap<>();

            a(@NonNull Callback callback) {
                super(callback.getDispatchMode());
                this.a = callback;
            }
        }

        public static void a(@NonNull View view, @Nullable Callback callback) {
            view.setWindowInsetsAnimationCallback(callback != null ? new a(callback) : null);
        }

        @NonNull
        public static WindowInsetsAnimation.Bounds a(@NonNull BoundsCompat boundsCompat) {
            return new WindowInsetsAnimation.Bounds(boundsCompat.getLowerBound().toPlatformInsets(), boundsCompat.getUpperBound().toPlatformInsets());
        }

        @NonNull
        public static Insets a(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getLowerBound());
        }

        @NonNull
        public static Insets b(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getUpperBound());
        }
    }
}
