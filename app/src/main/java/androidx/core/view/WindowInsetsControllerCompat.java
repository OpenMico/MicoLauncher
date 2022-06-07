package androidx.core.view;

import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;

/* loaded from: classes.dex */
public final class WindowInsetsControllerCompat {
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
    private final a a;

    /* loaded from: classes.dex */
    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(@NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, int i);
    }

    @RequiresApi(30)
    private WindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new e(windowInsetsController, this);
        } else {
            this.a = new a();
        }
    }

    public WindowInsetsControllerCompat(@NonNull Window window, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new e(window, this);
        } else if (Build.VERSION.SDK_INT >= 26) {
            this.a = new d(window, view);
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.a = new c(window, view);
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.a = new b(window, view);
        } else {
            this.a = new a();
        }
    }

    @NonNull
    @RequiresApi(30)
    public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        return new WindowInsetsControllerCompat(windowInsetsController);
    }

    public void show(int i) {
        this.a.a(i);
    }

    public void hide(int i) {
        this.a.b(i);
    }

    public boolean isAppearanceLightStatusBars() {
        return this.a.b();
    }

    public void setAppearanceLightStatusBars(boolean z) {
        this.a.a(z);
    }

    public boolean isAppearanceLightNavigationBars() {
        return this.a.c();
    }

    public void setAppearanceLightNavigationBars(boolean z) {
        this.a.b(z);
    }

    public void controlWindowInsetsAnimation(int i, long j, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        this.a.a(i, j, interpolator, cancellationSignal, windowInsetsAnimationControlListenerCompat);
    }

    public void setSystemBarsBehavior(int i) {
        this.a.c(i);
    }

    public int getSystemBarsBehavior() {
        return this.a.a();
    }

    public void addOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.a.a(onControllableInsetsChangedListener);
    }

    public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.a.b(onControllableInsetsChangedListener);
    }

    /* loaded from: classes.dex */
    private static class a {
        int a() {
            return 0;
        }

        void a(int i) {
        }

        void a(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        public void a(boolean z) {
        }

        void b(int i) {
        }

        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        public void b(boolean z) {
        }

        public boolean b() {
            return false;
        }

        void c(int i) {
        }

        public boolean c() {
            return false;
        }

        a() {
        }
    }

    @RequiresApi(20)
    /* loaded from: classes.dex */
    private static class b extends a {
        @NonNull
        protected final Window a;
        @Nullable
        private final View b;

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        int a() {
            return 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        b(@NonNull Window window, @Nullable View view) {
            this.a = window;
            this.b = view;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(int i) {
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    h(i2);
                }
            }
        }

        private void h(int i) {
            if (i != 8) {
                switch (i) {
                    case 1:
                        e(4);
                        g(1024);
                        return;
                    case 2:
                        e(2);
                        return;
                    default:
                        return;
                }
            } else {
                final View view = this.b;
                if (view == null || (!view.isInEditMode() && !view.onCheckIsTextEditor())) {
                    view = this.a.getCurrentFocus();
                } else {
                    view.requestFocus();
                }
                if (view == null) {
                    view = this.a.findViewById(16908290);
                }
                if (view != null && view.hasWindowFocus()) {
                    view.post(new Runnable() { // from class: androidx.core.view.WindowInsetsControllerCompat.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
                        }
                    });
                }
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void b(int i) {
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    i(i2);
                }
            }
        }

        private void i(int i) {
            if (i != 8) {
                switch (i) {
                    case 1:
                        d(4);
                        return;
                    case 2:
                        d(2);
                        return;
                    default:
                        return;
                }
            } else {
                ((InputMethodManager) this.a.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.a.getDecorView().getWindowToken(), 0);
            }
        }

        protected void d(int i) {
            View decorView = this.a.getDecorView();
            decorView.setSystemUiVisibility(i | decorView.getSystemUiVisibility());
        }

        protected void e(int i) {
            View decorView = this.a.getDecorView();
            decorView.setSystemUiVisibility((~i) & decorView.getSystemUiVisibility());
        }

        protected void f(int i) {
            this.a.addFlags(i);
        }

        protected void g(int i) {
            this.a.clearFlags(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void c(int i) {
            switch (i) {
                case 0:
                    e(6144);
                    return;
                case 1:
                    e(4096);
                    d(2048);
                    return;
                case 2:
                    e(2048);
                    d(4096);
                    return;
                default:
                    return;
            }
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    private static class c extends b {
        c(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public boolean b() {
            return (this.a.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public void a(boolean z) {
            if (z) {
                g(67108864);
                f(Integer.MIN_VALUE);
                d(8192);
                return;
            }
            e(8192);
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    private static class d extends c {
        d(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public boolean c() {
            return (this.a.getDecorView().getSystemUiVisibility() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public void b(boolean z) {
            if (z) {
                g(134217728);
                f(Integer.MIN_VALUE);
                d(16);
                return;
            }
            e(16);
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    private static class e extends a {
        final WindowInsetsControllerCompat a;
        final WindowInsetsController b;
        private final SimpleArrayMap<OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> c;

        e(@NonNull Window window, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat) {
            this(window.getInsetsController(), windowInsetsControllerCompat);
        }

        e(@NonNull WindowInsetsController windowInsetsController, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat) {
            this.c = new SimpleArrayMap<>();
            this.b = windowInsetsController;
            this.a = windowInsetsControllerCompat;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(int i) {
            this.b.show(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void b(int i) {
            this.b.hide(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public boolean b() {
            return (this.b.getSystemBarsAppearance() & 8) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public void a(boolean z) {
            if (z) {
                this.b.setSystemBarsAppearance(8, 8);
            } else {
                this.b.setSystemBarsAppearance(0, 8);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public boolean c() {
            return (this.b.getSystemBarsAppearance() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        public void b(boolean z) {
            if (z) {
                this.b.setSystemBarsAppearance(16, 16);
            } else {
                this.b.setSystemBarsAppearance(0, 16);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(int i, long j, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull final WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
            this.b.controlWindowInsetsAnimation(i, j, interpolator, cancellationSignal, new WindowInsetsAnimationControlListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.e.1
                private WindowInsetsAnimationControllerCompat c = null;
            });
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void c(int i) {
            this.b.setSystemBarsBehavior(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        int a() {
            return this.b.getSystemBarsBehavior();
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void a(@NonNull final OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            if (!this.c.containsKey(onControllableInsetsChangedListener)) {
                WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener2 = new WindowInsetsController.OnControllableInsetsChangedListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.e.2
                };
                this.c.put(onControllableInsetsChangedListener, onControllableInsetsChangedListener2);
                this.b.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener2);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.a
        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            WindowInsetsController.OnControllableInsetsChangedListener remove = this.c.remove(onControllableInsetsChangedListener);
            if (remove != null) {
                this.b.removeOnControllableInsetsChangedListener(remove);
            }
        }
    }
}
