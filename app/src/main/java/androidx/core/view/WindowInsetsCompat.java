package androidx.core.view;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    @NonNull
    public static final WindowInsetsCompat CONSUMED;
    private final f a;

    static {
        if (Build.VERSION.SDK_INT >= 30) {
            CONSUMED = k.e;
        } else {
            CONSUMED = f.a;
        }
    }

    @RequiresApi(20)
    private WindowInsetsCompat(@NonNull WindowInsets windowInsets) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new k(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 29) {
            this.a = new j(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 28) {
            this.a = new i(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.a = new h(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.a = new g(this, windowInsets);
        } else {
            this.a = new f(this);
        }
    }

    public WindowInsetsCompat(@Nullable WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat != null) {
            f fVar = windowInsetsCompat.a;
            if (Build.VERSION.SDK_INT >= 30 && (fVar instanceof k)) {
                this.a = new k(this, (k) fVar);
            } else if (Build.VERSION.SDK_INT >= 29 && (fVar instanceof j)) {
                this.a = new j(this, (j) fVar);
            } else if (Build.VERSION.SDK_INT >= 28 && (fVar instanceof i)) {
                this.a = new i(this, (i) fVar);
            } else if (Build.VERSION.SDK_INT >= 21 && (fVar instanceof h)) {
                this.a = new h(this, (h) fVar);
            } else if (Build.VERSION.SDK_INT < 20 || !(fVar instanceof g)) {
                this.a = new f(this);
            } else {
                this.a = new g(this, (g) fVar);
            }
            fVar.b(this);
            return;
        }
        this.a = new f(this);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets windowInsets) {
        return toWindowInsetsCompat(windowInsets, null);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets windowInsets, @Nullable View view) {
        WindowInsetsCompat windowInsetsCompat = new WindowInsetsCompat((WindowInsets) Preconditions.checkNotNull(windowInsets));
        if (view != null && view.isAttachedToWindow()) {
            windowInsetsCompat.a(ViewCompat.getRootWindowInsets(view));
            windowInsetsCompat.a(view.getRootView());
        }
        return windowInsetsCompat;
    }

    @Deprecated
    public int getSystemWindowInsetLeft() {
        return this.a.g().left;
    }

    @Deprecated
    public int getSystemWindowInsetTop() {
        return this.a.g().top;
    }

    @Deprecated
    public int getSystemWindowInsetRight() {
        return this.a.g().right;
    }

    @Deprecated
    public int getSystemWindowInsetBottom() {
        return this.a.g().bottom;
    }

    @Deprecated
    public boolean hasSystemWindowInsets() {
        return !this.a.g().equals(Insets.NONE);
    }

    public boolean hasInsets() {
        return !getInsets(Type.a()).equals(Insets.NONE) || !getInsetsIgnoringVisibility(Type.a() ^ Type.ime()).equals(Insets.NONE) || getDisplayCutout() != null;
    }

    public boolean isConsumed() {
        return this.a.b();
    }

    public boolean isRound() {
        return this.a.a();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeSystemWindowInsets() {
        return this.a.c();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int i2, int i3, int i4, int i5) {
        return new Builder(this).setSystemWindowInsets(Insets.of(i2, i3, i4, i5)).build();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(@NonNull Rect rect) {
        return new Builder(this).setSystemWindowInsets(Insets.of(rect)).build();
    }

    @Deprecated
    public int getStableInsetTop() {
        return this.a.h().top;
    }

    @Deprecated
    public int getStableInsetLeft() {
        return this.a.h().left;
    }

    @Deprecated
    public int getStableInsetRight() {
        return this.a.h().right;
    }

    @Deprecated
    public int getStableInsetBottom() {
        return this.a.h().bottom;
    }

    @Deprecated
    public boolean hasStableInsets() {
        return !this.a.h().equals(Insets.NONE);
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeStableInsets() {
        return this.a.d();
    }

    @Nullable
    public DisplayCutoutCompat getDisplayCutout() {
        return this.a.e();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeDisplayCutout() {
        return this.a.f();
    }

    @NonNull
    @Deprecated
    public Insets getSystemWindowInsets() {
        return this.a.g();
    }

    @NonNull
    @Deprecated
    public Insets getStableInsets() {
        return this.a.h();
    }

    @NonNull
    @Deprecated
    public Insets getMandatorySystemGestureInsets() {
        return this.a.j();
    }

    @NonNull
    @Deprecated
    public Insets getTappableElementInsets() {
        return this.a.k();
    }

    @NonNull
    @Deprecated
    public Insets getSystemGestureInsets() {
        return this.a.i();
    }

    @NonNull
    public WindowInsetsCompat inset(@NonNull Insets insets) {
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    @NonNull
    public WindowInsetsCompat inset(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4, @IntRange(from = 0) int i5) {
        return this.a.a(i2, i3, i4, i5);
    }

    @NonNull
    public Insets getInsets(int i2) {
        return this.a.a(i2);
    }

    @NonNull
    public Insets getInsetsIgnoringVisibility(int i2) {
        return this.a.b(i2);
    }

    public boolean isVisible(int i2) {
        return this.a.c(i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WindowInsetsCompat)) {
            return false;
        }
        return ObjectsCompat.equals(this.a, ((WindowInsetsCompat) obj).a);
    }

    public int hashCode() {
        f fVar = this.a;
        if (fVar == null) {
            return 0;
        }
        return fVar.hashCode();
    }

    @Nullable
    @RequiresApi(20)
    public WindowInsets toWindowInsets() {
        f fVar = this.a;
        if (fVar instanceof g) {
            return ((g) fVar).c;
        }
        return null;
    }

    /* loaded from: classes.dex */
    public static class f {
        @NonNull
        static final WindowInsetsCompat a = new Builder().build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
        final WindowInsetsCompat b;

        void a(@NonNull View view) {
        }

        void a(@NonNull Insets insets) {
        }

        void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
        }

        public void a(Insets[] insetsArr) {
        }

        boolean a() {
            return false;
        }

        public void b(Insets insets) {
        }

        void b(@NonNull WindowInsetsCompat windowInsetsCompat) {
        }

        boolean b() {
            return false;
        }

        boolean c(int i) {
            return true;
        }

        @Nullable
        DisplayCutoutCompat e() {
            return null;
        }

        f(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.b = windowInsetsCompat;
        }

        @NonNull
        WindowInsetsCompat c() {
            return this.b;
        }

        @NonNull
        WindowInsetsCompat d() {
            return this.b;
        }

        @NonNull
        WindowInsetsCompat f() {
            return this.b;
        }

        @NonNull
        Insets g() {
            return Insets.NONE;
        }

        @NonNull
        Insets h() {
            return Insets.NONE;
        }

        @NonNull
        Insets i() {
            return g();
        }

        @NonNull
        Insets j() {
            return g();
        }

        @NonNull
        Insets k() {
            return g();
        }

        @NonNull
        WindowInsetsCompat a(int i, int i2, int i3, int i4) {
            return a;
        }

        @NonNull
        Insets a(int i) {
            return Insets.NONE;
        }

        @NonNull
        Insets b(int i) {
            if ((i & 8) == 0) {
                return Insets.NONE;
            }
            throw new IllegalArgumentException("Unable to query the maximum insets for IME");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof f)) {
                return false;
            }
            f fVar = (f) obj;
            return a() == fVar.a() && b() == fVar.b() && ObjectsCompat.equals(g(), fVar.g()) && ObjectsCompat.equals(h(), fVar.h()) && ObjectsCompat.equals(e(), fVar.e());
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(a()), Boolean.valueOf(b()), g(), h(), e());
        }
    }

    @RequiresApi(20)
    /* loaded from: classes.dex */
    public static class g extends f {
        private static boolean e = false;
        private static Method f;
        private static Class<?> g;
        private static Class<?> h;
        private static Field i;
        private static Field j;
        @NonNull
        final WindowInsets c;
        Insets d;
        private Insets[] k;
        private Insets l;
        private WindowInsetsCompat m;

        g(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat);
            this.l = null;
            this.c = windowInsets;
        }

        g(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull g gVar) {
            this(windowInsetsCompat, new WindowInsets(gVar.c));
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        boolean a() {
            return this.c.isRound();
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        public Insets a(int i2) {
            return b(i2, false);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        public Insets b(int i2) {
            return b(i2, true);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @SuppressLint({"WrongConstant"})
        boolean c(int i2) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0 && !d(i3)) {
                    return false;
                }
            }
            return true;
        }

        @NonNull
        @SuppressLint({"WrongConstant"})
        private Insets b(int i2, boolean z) {
            Insets insets = Insets.NONE;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0) {
                    insets = Insets.max(insets, a(i3, z));
                }
            }
            return insets;
        }

        @NonNull
        protected Insets a(int i2, boolean z) {
            DisplayCutoutCompat displayCutoutCompat;
            Insets insets = null;
            if (i2 == 8) {
                Insets[] insetsArr = this.k;
                if (insetsArr != null) {
                    insets = insetsArr[Type.a(8)];
                }
                if (insets != null) {
                    return insets;
                }
                Insets g2 = g();
                Insets l = l();
                if (g2.bottom > l.bottom) {
                    return Insets.of(0, 0, 0, g2.bottom);
                }
                Insets insets2 = this.d;
                if (insets2 == null || insets2.equals(Insets.NONE) || this.d.bottom <= l.bottom) {
                    return Insets.NONE;
                }
                return Insets.of(0, 0, 0, this.d.bottom);
            } else if (i2 == 16) {
                return i();
            } else {
                if (i2 == 32) {
                    return j();
                }
                if (i2 == 64) {
                    return k();
                }
                if (i2 != 128) {
                    switch (i2) {
                        case 1:
                            if (z) {
                                return Insets.of(0, Math.max(l().top, g().top), 0, 0);
                            }
                            return Insets.of(0, g().top, 0, 0);
                        case 2:
                            if (z) {
                                Insets l2 = l();
                                Insets h2 = h();
                                return Insets.of(Math.max(l2.left, h2.left), 0, Math.max(l2.right, h2.right), Math.max(l2.bottom, h2.bottom));
                            }
                            Insets g3 = g();
                            WindowInsetsCompat windowInsetsCompat = this.m;
                            if (windowInsetsCompat != null) {
                                insets = windowInsetsCompat.getStableInsets();
                            }
                            int i3 = g3.bottom;
                            if (insets != null) {
                                i3 = Math.min(i3, insets.bottom);
                            }
                            return Insets.of(g3.left, 0, g3.right, i3);
                        default:
                            return Insets.NONE;
                    }
                } else {
                    WindowInsetsCompat windowInsetsCompat2 = this.m;
                    if (windowInsetsCompat2 != null) {
                        displayCutoutCompat = windowInsetsCompat2.getDisplayCutout();
                    } else {
                        displayCutoutCompat = e();
                    }
                    if (displayCutoutCompat != null) {
                        return Insets.of(displayCutoutCompat.getSafeInsetLeft(), displayCutoutCompat.getSafeInsetTop(), displayCutoutCompat.getSafeInsetRight(), displayCutoutCompat.getSafeInsetBottom());
                    }
                    return Insets.NONE;
                }
            }
        }

        protected boolean d(int i2) {
            if (i2 == 4) {
                return false;
            }
            if (!(i2 == 8 || i2 == 128)) {
                switch (i2) {
                    case 1:
                    case 2:
                        break;
                    default:
                        return true;
                }
            }
            return !a(i2, false).equals(Insets.NONE);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        final Insets g() {
            if (this.l == null) {
                this.l = Insets.of(this.c.getSystemWindowInsetLeft(), this.c.getSystemWindowInsetTop(), this.c.getSystemWindowInsetRight(), this.c.getSystemWindowInsetBottom());
            }
            return this.l;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        WindowInsetsCompat a(int i2, int i3, int i4, int i5) {
            Builder builder = new Builder(WindowInsetsCompat.toWindowInsetsCompat(this.c));
            builder.setSystemWindowInsets(WindowInsetsCompat.a(g(), i2, i3, i4, i5));
            builder.setStableInsets(WindowInsetsCompat.a(h(), i2, i3, i4, i5));
            return builder.build();
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        void b(@NonNull WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat.a(this.m);
            windowInsetsCompat.b(this.d);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
            this.m = windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        void a(@NonNull Insets insets) {
            this.d = insets;
        }

        private Insets l() {
            WindowInsetsCompat windowInsetsCompat = this.m;
            if (windowInsetsCompat != null) {
                return windowInsetsCompat.getStableInsets();
            }
            return Insets.NONE;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        void a(@NonNull View view) {
            Insets b = b(view);
            if (b == null) {
                b = Insets.NONE;
            }
            a(b);
        }

        @Nullable
        private Insets b(@NonNull View view) {
            if (Build.VERSION.SDK_INT < 30) {
                if (!e) {
                    m();
                }
                Method method = f;
                if (method == null || h == null || i == null) {
                    return null;
                }
                try {
                    Object invoke = method.invoke(view, new Object[0]);
                    if (invoke == null) {
                        Log.w("WindowInsetsCompat", "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                        return null;
                    }
                    Rect rect = (Rect) i.get(j.get(invoke));
                    if (rect != null) {
                        return Insets.of(rect);
                    }
                    return null;
                } catch (ReflectiveOperationException e2) {
                    Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e2.getMessage(), e2);
                    return null;
                }
            } else {
                throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        public void a(Insets[] insetsArr) {
            this.k = insetsArr;
        }

        @SuppressLint({"PrivateApi"})
        private static void m() {
            try {
                f = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]);
                g = Class.forName("android.view.ViewRootImpl");
                h = Class.forName("android.view.View$AttachInfo");
                i = h.getDeclaredField("mVisibleInsets");
                j = g.getDeclaredField("mAttachInfo");
                i.setAccessible(true);
                j.setAccessible(true);
            } catch (ReflectiveOperationException e2) {
                Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e2.getMessage(), e2);
            }
            e = true;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            return Objects.equals(this.d, ((g) obj).d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class h extends g {
        private Insets e;

        h(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
            this.e = null;
        }

        h(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull h hVar) {
            super(windowInsetsCompat, hVar);
            this.e = null;
            this.e = hVar.e;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        boolean b() {
            return this.c.isConsumed();
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        WindowInsetsCompat d() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeStableInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        WindowInsetsCompat c() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeSystemWindowInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        final Insets h() {
            if (this.e == null) {
                this.e = Insets.of(this.c.getStableInsetLeft(), this.c.getStableInsetTop(), this.c.getStableInsetRight(), this.c.getStableInsetBottom());
            }
            return this.e;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        public void b(@Nullable Insets insets) {
            this.e = insets;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static class i extends h {
        i(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        i(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull i iVar) {
            super(windowInsetsCompat, iVar);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @Nullable
        DisplayCutoutCompat e() {
            return DisplayCutoutCompat.a(this.c.getDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        WindowInsetsCompat f() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof i)) {
                return false;
            }
            i iVar = (i) obj;
            return Objects.equals(this.c, iVar.c) && Objects.equals(this.d, iVar.d);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        public int hashCode() {
            return this.c.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class j extends i {
        private Insets e = null;
        private Insets f = null;
        private Insets g = null;

        @Override // androidx.core.view.WindowInsetsCompat.h, androidx.core.view.WindowInsetsCompat.f
        public void b(@Nullable Insets insets) {
        }

        j(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        j(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull j jVar) {
            super(windowInsetsCompat, jVar);
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        Insets i() {
            if (this.e == null) {
                this.e = Insets.toCompatInsets(this.c.getSystemGestureInsets());
            }
            return this.e;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        Insets j() {
            if (this.f == null) {
                this.f = Insets.toCompatInsets(this.c.getMandatorySystemGestureInsets());
            }
            return this.f;
        }

        @Override // androidx.core.view.WindowInsetsCompat.f
        @NonNull
        Insets k() {
            if (this.g == null) {
                this.g = Insets.toCompatInsets(this.c.getTappableElementInsets());
            }
            return this.g;
        }

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        @NonNull
        WindowInsetsCompat a(int i, int i2, int i3, int i4) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.inset(i, i2, i3, i4));
        }
    }

    public static Insets a(@NonNull Insets insets, int i2, int i3, int i4, int i5) {
        int max = Math.max(0, insets.left - i2);
        int max2 = Math.max(0, insets.top - i3);
        int max3 = Math.max(0, insets.right - i4);
        int max4 = Math.max(0, insets.bottom - i5);
        return (max == i2 && max2 == i3 && max3 == i4 && max4 == i5) ? insets : Insets.of(max, max2, max3, max4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class k extends j {
        @NonNull
        static final WindowInsetsCompat e = WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED);

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        final void a(@NonNull View view) {
        }

        k(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        k(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull k kVar) {
            super(windowInsetsCompat, kVar);
        }

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        @NonNull
        public Insets a(int i) {
            return Insets.toCompatInsets(this.c.getInsets(l.a(i)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        @NonNull
        public Insets b(int i) {
            return Insets.toCompatInsets(this.c.getInsetsIgnoringVisibility(l.a(i)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.g, androidx.core.view.WindowInsetsCompat.f
        public boolean c(int i) {
            return this.c.isVisible(l.a(i));
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final b a;

        public Builder() {
            if (Build.VERSION.SDK_INT >= 30) {
                this.a = new e();
            } else if (Build.VERSION.SDK_INT >= 29) {
                this.a = new d();
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.a = new c();
            } else {
                this.a = new b();
            }
        }

        public Builder(@NonNull WindowInsetsCompat windowInsetsCompat) {
            if (Build.VERSION.SDK_INT >= 30) {
                this.a = new e(windowInsetsCompat);
            } else if (Build.VERSION.SDK_INT >= 29) {
                this.a = new d(windowInsetsCompat);
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.a = new c(windowInsetsCompat);
            } else {
                this.a = new b(windowInsetsCompat);
            }
        }

        @NonNull
        @Deprecated
        public Builder setSystemWindowInsets(@NonNull Insets insets) {
            this.a.a(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setSystemGestureInsets(@NonNull Insets insets) {
            this.a.b(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setMandatorySystemGestureInsets(@NonNull Insets insets) {
            this.a.c(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTappableElementInsets(@NonNull Insets insets) {
            this.a.d(insets);
            return this;
        }

        @NonNull
        public Builder setInsets(int i, @NonNull Insets insets) {
            this.a.a(i, insets);
            return this;
        }

        @NonNull
        public Builder setInsetsIgnoringVisibility(int i, @NonNull Insets insets) {
            this.a.b(i, insets);
            return this;
        }

        @NonNull
        public Builder setVisible(int i, boolean z) {
            this.a.a(i, z);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setStableInsets(@NonNull Insets insets) {
            this.a.e(insets);
            return this;
        }

        @NonNull
        public Builder setDisplayCutout(@Nullable DisplayCutoutCompat displayCutoutCompat) {
            this.a.a(displayCutoutCompat);
            return this;
        }

        @NonNull
        public WindowInsetsCompat build() {
            return this.a.b();
        }
    }

    /* loaded from: classes.dex */
    public static class b {
        Insets[] a;
        private final WindowInsetsCompat b;

        void a(int i, boolean z) {
        }

        void a(@NonNull Insets insets) {
        }

        void a(@Nullable DisplayCutoutCompat displayCutoutCompat) {
        }

        void b(@NonNull Insets insets) {
        }

        void c(@NonNull Insets insets) {
        }

        void d(@NonNull Insets insets) {
        }

        void e(@NonNull Insets insets) {
        }

        b() {
            this(new WindowInsetsCompat((WindowInsetsCompat) null));
        }

        b(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.b = windowInsetsCompat;
        }

        void a(int i, @NonNull Insets insets) {
            if (this.a == null) {
                this.a = new Insets[9];
            }
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.a[Type.a(i2)] = insets;
                }
            }
        }

        void b(int i, @NonNull Insets insets) {
            if (i == 8) {
                throw new IllegalArgumentException("Ignoring visibility inset not available for IME");
            }
        }

        protected final void a() {
            Insets[] insetsArr = this.a;
            if (insetsArr != null) {
                Insets insets = insetsArr[Type.a(1)];
                Insets insets2 = this.a[Type.a(2)];
                if (insets2 == null) {
                    insets2 = this.b.getInsets(2);
                }
                if (insets == null) {
                    insets = this.b.getInsets(1);
                }
                a(Insets.max(insets, insets2));
                Insets insets3 = this.a[Type.a(16)];
                if (insets3 != null) {
                    b(insets3);
                }
                Insets insets4 = this.a[Type.a(32)];
                if (insets4 != null) {
                    c(insets4);
                }
                Insets insets5 = this.a[Type.a(64)];
                if (insets5 != null) {
                    d(insets5);
                }
            }
        }

        @NonNull
        WindowInsetsCompat b() {
            a();
            return this.b;
        }
    }

    void a(Insets[] insetsArr) {
        this.a.a(insetsArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 20)
    /* loaded from: classes.dex */
    public static class c extends b {
        private static Field b = null;
        private static boolean c = false;
        private static Constructor<WindowInsets> d = null;
        private static boolean e = false;
        private WindowInsets f;
        private Insets g;

        c() {
            this.f = c();
        }

        c(@NonNull WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
            this.f = windowInsetsCompat.toWindowInsets();
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void a(@NonNull Insets insets) {
            WindowInsets windowInsets = this.f;
            if (windowInsets != null) {
                this.f = windowInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void e(@Nullable Insets insets) {
            this.g = insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        @NonNull
        WindowInsetsCompat b() {
            a();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.f);
            windowInsetsCompat.a(this.a);
            windowInsetsCompat.a(this.g);
            return windowInsetsCompat;
        }

        @Nullable
        private static WindowInsets c() {
            if (!c) {
                try {
                    b = WindowInsets.class.getDeclaredField("CONSUMED");
                } catch (ReflectiveOperationException e2) {
                    Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets.CONSUMED field", e2);
                }
                c = true;
            }
            Field field = b;
            if (field != null) {
                try {
                    WindowInsets windowInsets = (WindowInsets) field.get(null);
                    if (windowInsets != null) {
                        return new WindowInsets(windowInsets);
                    }
                } catch (ReflectiveOperationException e3) {
                    Log.i("WindowInsetsCompat", "Could not get value from WindowInsets.CONSUMED field", e3);
                }
            }
            if (!e) {
                try {
                    d = WindowInsets.class.getConstructor(Rect.class);
                } catch (ReflectiveOperationException e4) {
                    Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets(Rect) constructor", e4);
                }
                e = true;
            }
            Constructor<WindowInsets> constructor = d;
            if (constructor != null) {
                try {
                    return constructor.newInstance(new Rect());
                } catch (ReflectiveOperationException e5) {
                    Log.i("WindowInsetsCompat", "Could not invoke WindowInsets(Rect) constructor", e5);
                }
            }
            return null;
        }
    }

    void a(@Nullable Insets insets) {
        this.a.b(insets);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 29)
    /* loaded from: classes.dex */
    public static class d extends b {
        final WindowInsets.Builder b;

        d() {
            this.b = new WindowInsets.Builder();
        }

        d(@NonNull WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
            WindowInsets.Builder builder;
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            if (windowInsets != null) {
                builder = new WindowInsets.Builder(windowInsets);
            } else {
                builder = new WindowInsets.Builder();
            }
            this.b = builder;
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void a(@NonNull Insets insets) {
            this.b.setSystemWindowInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void b(@NonNull Insets insets) {
            this.b.setSystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void c(@NonNull Insets insets) {
            this.b.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void d(@NonNull Insets insets) {
            this.b.setTappableElementInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void e(@NonNull Insets insets) {
            this.b.setStableInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void a(@Nullable DisplayCutoutCompat displayCutoutCompat) {
            this.b.setDisplayCutout(displayCutoutCompat != null ? displayCutoutCompat.a() : null);
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        @NonNull
        WindowInsetsCompat b() {
            a();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.b.build());
            windowInsetsCompat.a(this.a);
            return windowInsetsCompat;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class e extends d {
        e() {
        }

        e(@NonNull WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void a(int i, @NonNull Insets insets) {
            this.b.setInsets(l.a(i), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void b(int i, @NonNull Insets insets) {
            this.b.setInsetsIgnoringVisibility(l.a(i), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.b
        void a(int i, boolean z) {
            this.b.setVisible(l.a(i), z);
        }
    }

    /* loaded from: classes.dex */
    public static final class Type {

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public @interface InsetsType {
        }

        @SuppressLint({"WrongConstant"})
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static int a() {
            return -1;
        }

        public static int captionBar() {
            return 4;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int ime() {
            return 8;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int statusBars() {
            return 1;
        }

        public static int systemBars() {
            return 7;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int tappableElement() {
            return 64;
        }

        private Type() {
        }

        static int a(int i) {
            if (i == 4) {
                return 2;
            }
            if (i == 8) {
                return 3;
            }
            if (i == 16) {
                return 4;
            }
            if (i == 32) {
                return 5;
            }
            if (i == 64) {
                return 6;
            }
            if (i == 128) {
                return 7;
            }
            if (i == 256) {
                return 8;
            }
            switch (i) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                default:
                    throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + i);
            }
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    private static final class l {
        static int a(int i) {
            int i2 = 0;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i & i3) != 0) {
                    if (i3 == 4) {
                        i2 |= WindowInsets.Type.captionBar();
                    } else if (i3 == 8) {
                        i2 |= WindowInsets.Type.ime();
                    } else if (i3 == 16) {
                        i2 |= WindowInsets.Type.systemGestures();
                    } else if (i3 == 32) {
                        i2 |= WindowInsets.Type.mandatorySystemGestures();
                    } else if (i3 == 64) {
                        i2 |= WindowInsets.Type.tappableElement();
                    } else if (i3 != 128) {
                        switch (i3) {
                            case 1:
                                i2 |= WindowInsets.Type.statusBars();
                                continue;
                            case 2:
                                i2 |= WindowInsets.Type.navigationBars();
                                continue;
                        }
                    } else {
                        i2 |= WindowInsets.Type.displayCutout();
                    }
                }
            }
            return i2;
        }
    }

    public void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
        this.a.a(windowInsetsCompat);
    }

    void b(@NonNull Insets insets) {
        this.a.a(insets);
    }

    public void a(@NonNull View view) {
        this.a.a(view);
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class a {
        private static Field a;
        private static Field b;
        private static Field c;
        private static boolean d;

        static {
            try {
                a = View.class.getDeclaredField("mAttachInfo");
                a.setAccessible(true);
                Class<?> cls = Class.forName("android.view.View$AttachInfo");
                b = cls.getDeclaredField("mStableInsets");
                b.setAccessible(true);
                c = cls.getDeclaredField("mContentInsets");
                c.setAccessible(true);
                d = true;
            } catch (ReflectiveOperationException e) {
                Log.w("WindowInsetsCompat", "Failed to get visible insets from AttachInfo " + e.getMessage(), e);
            }
        }

        @Nullable
        public static WindowInsetsCompat a(@NonNull View view) {
            if (!d || !view.isAttachedToWindow()) {
                return null;
            }
            try {
                Object obj = a.get(view.getRootView());
                if (obj != null) {
                    Rect rect = (Rect) b.get(obj);
                    Rect rect2 = (Rect) c.get(obj);
                    if (!(rect == null || rect2 == null)) {
                        WindowInsetsCompat build = new Builder().setStableInsets(Insets.of(rect)).setSystemWindowInsets(Insets.of(rect2)).build();
                        build.a(build);
                        build.a(view.getRootView());
                        return build;
                    }
                }
            } catch (IllegalAccessException e) {
                Log.w("WindowInsetsCompat", "Failed to get insets from AttachInfo. " + e.getMessage(), e);
            }
            return null;
        }
    }
}
