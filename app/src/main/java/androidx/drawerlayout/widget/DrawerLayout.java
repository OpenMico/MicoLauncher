package androidx.drawerlayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.Openable;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DrawerLayout extends ViewGroup implements Openable {
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static boolean O = false;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    static final boolean b;
    private static final boolean d;
    private Drawable A;
    private Drawable B;
    private Drawable C;
    private CharSequence D;
    private CharSequence E;
    private Object F;
    private boolean G;
    private Drawable H;
    private Drawable I;
    private Drawable J;
    private Drawable K;
    private final ArrayList<View> L;
    private Rect M;
    private Matrix N;
    private final AccessibilityViewCommand P;
    private final b e;
    private float f;
    private int g;
    private int h;
    private float i;
    private Paint j;
    private final ViewDragHelper k;
    private final ViewDragHelper l;
    private final c m;
    private final c n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    @Nullable
    private DrawerListener w;
    private List<DrawerListener> x;
    private float y;
    private float z;
    private static final int[] c = {16843828};
    static final int[] a = {16842931};

    /* loaded from: classes.dex */
    public interface DrawerListener {
        void onDrawerClosed(@NonNull View view);

        void onDrawerOpened(@NonNull View view);

        void onDrawerSlide(@NonNull View view, float f);

        void onDrawerStateChanged(int i);
    }

    /* loaded from: classes.dex */
    public static abstract class SimpleDrawerListener implements DrawerListener {
        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerClosed(View view) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerOpened(View view) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerSlide(View view, float f) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerStateChanged(int i) {
        }
    }

    static {
        boolean z = true;
        b = Build.VERSION.SDK_INT >= 19;
        d = Build.VERSION.SDK_INT >= 21;
        if (Build.VERSION.SDK_INT < 29) {
            z = false;
        }
        O = z;
    }

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.drawerLayoutStyle);
    }

    /* JADX WARN: Finally extract failed */
    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes;
        this.e = new b();
        this.h = -1728053248;
        this.j = new Paint();
        this.q = true;
        this.r = 3;
        this.s = 3;
        this.t = 3;
        this.u = 3;
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        this.P = new AccessibilityViewCommand() { // from class: androidx.drawerlayout.widget.DrawerLayout.1
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                if (!DrawerLayout.this.isDrawerOpen(view) || DrawerLayout.this.getDrawerLockMode(view) == 2) {
                    return false;
                }
                DrawerLayout.this.closeDrawer(view);
                return true;
            }
        };
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.g = (int) ((64.0f * f) + 0.5f);
        float f2 = f * 400.0f;
        this.m = new c(3);
        this.n = new c(5);
        this.k = ViewDragHelper.create(this, 1.0f, this.m);
        this.k.setEdgeTrackingEnabled(1);
        this.k.setMinVelocity(f2);
        this.m.a(this.k);
        this.l = ViewDragHelper.create(this, 1.0f, this.n);
        this.l.setEdgeTrackingEnabled(2);
        this.l.setMinVelocity(f2);
        this.n.a(this.l);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new a());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: androidx.drawerlayout.widget.DrawerLayout.2
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        ((DrawerLayout) view).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
                        return windowInsets.consumeSystemWindowInsets();
                    }
                });
                setSystemUiVisibility(1280);
                obtainStyledAttributes = context.obtainStyledAttributes(c);
                try {
                    this.A = obtainStyledAttributes.getDrawable(0);
                    obtainStyledAttributes.recycle();
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                this.A = null;
            }
        }
        obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DrawerLayout, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(R.styleable.DrawerLayout_elevation)) {
                this.f = obtainStyledAttributes.getDimension(R.styleable.DrawerLayout_elevation, 0.0f);
            } else {
                this.f = getResources().getDimension(R.dimen.def_drawer_elevation);
            }
            obtainStyledAttributes.recycle();
            this.L = new ArrayList<>();
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setDrawerElevation(float f) {
        this.f = f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (f(childAt)) {
                ViewCompat.setElevation(childAt, this.f);
            }
        }
    }

    public float getDrawerElevation() {
        if (d) {
            return this.f;
        }
        return 0.0f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setChildInsets(Object obj, boolean z) {
        this.F = obj;
        this.G = z;
        setWillNotDraw(!z && getBackground() == null);
        requestLayout();
    }

    public void setDrawerShadow(Drawable drawable, int i) {
        if (!d) {
            if ((i & GravityCompat.START) == 8388611) {
                this.H = drawable;
            } else if ((i & GravityCompat.END) == 8388613) {
                this.I = drawable;
            } else if ((i & 3) == 3) {
                this.J = drawable;
            } else if ((i & 5) == 5) {
                this.K = drawable;
            } else {
                return;
            }
            d();
            invalidate();
        }
    }

    public void setDrawerShadow(@DrawableRes int i, int i2) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), i), i2);
    }

    public void setScrimColor(@ColorInt int i) {
        this.h = i;
        invalidate();
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        DrawerListener drawerListener2 = this.w;
        if (drawerListener2 != null) {
            removeDrawerListener(drawerListener2);
        }
        if (drawerListener != null) {
            addDrawerListener(drawerListener);
        }
        this.w = drawerListener;
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener != null) {
            if (this.x == null) {
                this.x = new ArrayList();
            }
            this.x.add(drawerListener);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        List<DrawerListener> list;
        if (drawerListener != null && (list = this.x) != null) {
            list.remove(drawerListener);
        }
    }

    public void setDrawerLockMode(int i) {
        setDrawerLockMode(i, 3);
        setDrawerLockMode(i, 5);
    }

    public void setDrawerLockMode(int i, int i2) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this));
        if (i2 == 3) {
            this.r = i;
        } else if (i2 == 5) {
            this.s = i;
        } else if (i2 == 8388611) {
            this.t = i;
        } else if (i2 == 8388613) {
            this.u = i;
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.k : this.l).cancel();
        }
        switch (i) {
            case 1:
                View a2 = a(absoluteGravity);
                if (a2 != null) {
                    closeDrawer(a2);
                    return;
                }
                return;
            case 2:
                View a3 = a(absoluteGravity);
                if (a3 != null) {
                    openDrawer(a3);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setDrawerLockMode(int i, @NonNull View view) {
        if (f(view)) {
            setDrawerLockMode(i, ((LayoutParams) view.getLayoutParams()).gravity);
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer with appropriate layout_gravity");
    }

    public int getDrawerLockMode(int i) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (i == 3) {
            int i2 = this.r;
            if (i2 != 3) {
                return i2;
            }
            int i3 = layoutDirection == 0 ? this.t : this.u;
            if (i3 != 3) {
                return i3;
            }
            return 0;
        } else if (i == 5) {
            int i4 = this.s;
            if (i4 != 3) {
                return i4;
            }
            int i5 = layoutDirection == 0 ? this.u : this.t;
            if (i5 != 3) {
                return i5;
            }
            return 0;
        } else if (i == 8388611) {
            int i6 = this.t;
            if (i6 != 3) {
                return i6;
            }
            int i7 = layoutDirection == 0 ? this.r : this.s;
            if (i7 != 3) {
                return i7;
            }
            return 0;
        } else if (i != 8388613) {
            return 0;
        } else {
            int i8 = this.u;
            if (i8 != 3) {
                return i8;
            }
            int i9 = layoutDirection == 0 ? this.s : this.r;
            if (i9 != 3) {
                return i9;
            }
            return 0;
        }
    }

    public int getDrawerLockMode(@NonNull View view) {
        if (f(view)) {
            return getDrawerLockMode(((LayoutParams) view.getLayoutParams()).gravity);
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public void setDrawerTitle(int i, @Nullable CharSequence charSequence) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            this.D = charSequence;
        } else if (absoluteGravity == 5) {
            this.E = charSequence;
        }
    }

    @Nullable
    public CharSequence getDrawerTitle(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            return this.D;
        }
        if (absoluteGravity == 5) {
            return this.E;
        }
        return null;
    }

    private boolean a(float f, float f2, View view) {
        if (this.M == null) {
            this.M = new Rect();
        }
        view.getHitRect(this.M);
        return this.M.contains((int) f, (int) f2);
    }

    private boolean a(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            MotionEvent b2 = b(motionEvent, view);
            boolean dispatchGenericMotionEvent = view.dispatchGenericMotionEvent(b2);
            b2.recycle();
            return dispatchGenericMotionEvent;
        }
        float scrollX = getScrollX() - view.getLeft();
        float scrollY = getScrollY() - view.getTop();
        motionEvent.offsetLocation(scrollX, scrollY);
        boolean dispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-scrollX, -scrollY);
        return dispatchGenericMotionEvent2;
    }

    private MotionEvent b(MotionEvent motionEvent, View view) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.offsetLocation(getScrollX() - view.getLeft(), getScrollY() - view.getTop());
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            if (this.N == null) {
                this.N = new Matrix();
            }
            matrix.invert(this.N);
            obtain.transform(this.N);
        }
        return obtain;
    }

    void a(int i, View view) {
        int viewDragState = this.k.getViewDragState();
        int viewDragState2 = this.l.getViewDragState();
        int i2 = 2;
        if (viewDragState == 1 || viewDragState2 == 1) {
            i2 = 1;
        } else if (!(viewDragState == 2 || viewDragState2 == 2)) {
            i2 = 0;
        }
        if (view != null && i == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.a == 0.0f) {
                a(view);
            } else if (layoutParams.a == 1.0f) {
                b(view);
            }
        }
        if (i2 != this.o) {
            this.o = i2;
            List<DrawerListener> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).onDrawerStateChanged(i2);
                }
            }
        }
    }

    void a(View view) {
        View rootView;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 1) {
            layoutParams.c = 0;
            List<DrawerListener> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).onDrawerClosed(view);
                }
            }
            a(view, false);
            h(view);
            if (hasWindowFocus() && (rootView = getRootView()) != null) {
                rootView.sendAccessibilityEvent(32);
            }
        }
    }

    void b(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 0) {
            layoutParams.c = 1;
            List<DrawerListener> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).onDrawerOpened(view);
                }
            }
            a(view, true);
            h(view);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    private void a(View view, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((z || f(childAt)) && (!z || childAt != view)) {
                ViewCompat.setImportantForAccessibility(childAt, 4);
            } else {
                ViewCompat.setImportantForAccessibility(childAt, 1);
            }
        }
    }

    private void h(View view) {
        ViewCompat.removeAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS.getId());
        if (isDrawerOpen(view) && getDrawerLockMode(view) != 2) {
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, this.P);
        }
    }

    void a(View view, float f) {
        List<DrawerListener> list = this.x;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.x.get(size).onDrawerSlide(view, f);
            }
        }
    }

    void b(View view, float f) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f != layoutParams.a) {
            layoutParams.a = f;
            a(view, f);
        }
    }

    float c(View view) {
        return ((LayoutParams) view.getLayoutParams()).a;
    }

    int d(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    boolean a(View view, int i) {
        return (d(view) & i) == i;
    }

    View a() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).c & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    void c(View view, float f) {
        float c2 = c(view);
        float width = view.getWidth();
        int i = ((int) (width * f)) - ((int) (c2 * width));
        if (!a(view, 3)) {
            i = -i;
        }
        view.offsetLeftAndRight(i);
        b(view, f);
    }

    View a(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((d(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    static String b(int i) {
        return (i & 3) == 3 ? "LEFT" : (i & 5) == 5 ? "RIGHT" : Integer.toHexString(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.q = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = true;
    }

    @Override // android.view.View
    @SuppressLint({"WrongConstant"})
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (isInEditMode()) {
                if (mode == 0) {
                    size = 300;
                }
                if (mode2 == 0) {
                    size2 = 300;
                }
            } else {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(size, size2);
        int i3 = 0;
        boolean z = this.F != null && ViewCompat.getFitsSystemWindows(this);
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = getChildCount();
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            WindowInsets windowInsets = (WindowInsets) this.F;
                            if (absoluteGravity == 3) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), i3, windowInsets.getSystemWindowInsetBottom());
                            } else if (absoluteGravity == 5) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(i3, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                            childAt.dispatchApplyWindowInsets(windowInsets);
                        }
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        WindowInsets windowInsets2 = (WindowInsets) this.F;
                        if (absoluteGravity == 3) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), i3, windowInsets2.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(i3, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                        }
                        layoutParams.leftMargin = windowInsets2.getSystemWindowInsetLeft();
                        layoutParams.topMargin = windowInsets2.getSystemWindowInsetTop();
                        layoutParams.rightMargin = windowInsets2.getSystemWindowInsetRight();
                        layoutParams.bottomMargin = windowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (e(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - layoutParams.leftMargin) - layoutParams.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - layoutParams.topMargin) - layoutParams.bottomMargin, 1073741824));
                } else if (f(childAt)) {
                    if (d) {
                        float elevation = ViewCompat.getElevation(childAt);
                        float f = this.f;
                        if (elevation != f) {
                            ViewCompat.setElevation(childAt, f);
                        }
                    }
                    int d2 = d(childAt) & 7;
                    int i5 = d2 == 3 ? 1 : i3;
                    if ((i5 == 0 || !z2) && (i5 != 0 || !z3)) {
                        if (i5 != 0) {
                            z2 = true;
                        } else {
                            z3 = true;
                        }
                        childAt.measure(getChildMeasureSpec(i, this.g + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), getChildMeasureSpec(i2, layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height));
                        i4++;
                        i3 = 0;
                    } else {
                        throw new IllegalStateException("Child drawer has absolute gravity " + b(d2) + " but this DrawerLayout already has a drawer view along that edge");
                    }
                } else {
                    throw new IllegalStateException("Child " + childAt + " at index " + i4 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                }
            }
            i4++;
            i3 = 0;
        }
    }

    private void d() {
        if (!d) {
            this.B = e();
            this.C = f();
        }
    }

    private Drawable e() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            Drawable drawable = this.H;
            if (drawable != null) {
                a(drawable, layoutDirection);
                return this.H;
            }
        } else {
            Drawable drawable2 = this.I;
            if (drawable2 != null) {
                a(drawable2, layoutDirection);
                return this.I;
            }
        }
        return this.J;
    }

    private Drawable f() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            Drawable drawable = this.I;
            if (drawable != null) {
                a(drawable, layoutDirection);
                return this.I;
            }
        } else {
            Drawable drawable2 = this.H;
            if (drawable2 != null) {
                a(drawable2, layoutDirection);
                return this.H;
            }
        }
        return this.K;
    }

    private void a(Drawable drawable, int i) {
        if (drawable != null && DrawableCompat.isAutoMirrored(drawable)) {
            DrawableCompat.setLayoutDirection(drawable, i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        WindowInsets rootWindowInsets;
        float f;
        int i5;
        boolean z2 = true;
        this.p = true;
        int i6 = i3 - i;
        int childCount = getChildCount();
        int i7 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (e(childAt)) {
                    childAt.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + childAt.getMeasuredWidth(), layoutParams.topMargin + childAt.getMeasuredHeight());
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f2 = measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.a * f2));
                        f = (measuredWidth + i5) / f2;
                    } else {
                        float f3 = measuredWidth;
                        int i8 = i6 - ((int) (layoutParams.a * f3));
                        f = (i6 - i8) / f3;
                        i5 = i8;
                    }
                    if (f == layoutParams.a) {
                        z2 = false;
                    }
                    int i9 = layoutParams.gravity & 112;
                    if (i9 == 16) {
                        int i10 = i4 - i2;
                        int i11 = (i10 - measuredHeight) / 2;
                        if (i11 < layoutParams.topMargin) {
                            i11 = layoutParams.topMargin;
                        } else if (i11 + measuredHeight > i10 - layoutParams.bottomMargin) {
                            i11 = (i10 - layoutParams.bottomMargin) - measuredHeight;
                        }
                        childAt.layout(i5, i11, measuredWidth + i5, measuredHeight + i11);
                    } else if (i9 != 80) {
                        childAt.layout(i5, layoutParams.topMargin, measuredWidth + i5, layoutParams.topMargin + measuredHeight);
                    } else {
                        int i12 = i4 - i2;
                        childAt.layout(i5, (i12 - layoutParams.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i12 - layoutParams.bottomMargin);
                    }
                    if (z2) {
                        b(childAt, f);
                    }
                    int i13 = layoutParams.a > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i13) {
                        childAt.setVisibility(i13);
                    }
                }
            }
            i7++;
            z2 = true;
        }
        if (O && (rootWindowInsets = getRootWindowInsets()) != null) {
            Insets systemGestureInsets = WindowInsetsCompat.toWindowInsetsCompat(rootWindowInsets).getSystemGestureInsets();
            ViewDragHelper viewDragHelper = this.k;
            viewDragHelper.setEdgeSize(Math.max(viewDragHelper.getDefaultEdgeSize(), systemGestureInsets.left));
            ViewDragHelper viewDragHelper2 = this.l;
            viewDragHelper2.setEdgeSize(Math.max(viewDragHelper2.getDefaultEdgeSize(), systemGestureInsets.right));
        }
        this.p = false;
        this.q = false;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (!this.p) {
            super.requestLayout();
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        int childCount = getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            f = Math.max(f, ((LayoutParams) getChildAt(i).getLayoutParams()).a);
        }
        this.i = f;
        boolean continueSettling = this.k.continueSettling(true);
        boolean continueSettling2 = this.l.continueSettling(true);
        if (continueSettling || continueSettling2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private static boolean i(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        this.A = drawable;
        invalidate();
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return this.A;
    }

    public void setStatusBarBackground(int i) {
        this.A = i != 0 ? ContextCompat.getDrawable(getContext(), i) : null;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int i) {
        this.A = new ColorDrawable(i);
        invalidate();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        d();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        if (this.G && this.A != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                Object obj = this.F;
                i = obj != null ? ((WindowInsets) obj).getSystemWindowInsetTop() : 0;
            } else {
                i = 0;
            }
            if (i > 0) {
                this.A.setBounds(0, 0, getWidth(), i);
                this.A.draw(canvas);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        int i;
        int height = getHeight();
        boolean e = e(view);
        int width = getWidth();
        int save = canvas.save();
        int i2 = 0;
        if (e) {
            int childCount = getChildCount();
            i = width;
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != view && childAt.getVisibility() == 0 && i(childAt) && f(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt, 3)) {
                        int right = childAt.getRight();
                        if (right > i3) {
                            i3 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < i) {
                            i = left;
                        }
                    }
                }
            }
            canvas.clipRect(i3, 0, i, getHeight());
            i2 = i3;
        } else {
            i = width;
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        float f = this.i;
        if (f > 0.0f && e) {
            int i5 = this.h;
            this.j.setColor((i5 & 16777215) | (((int) ((((-16777216) & i5) >>> 24) * f)) << 24));
            canvas.drawRect(i2, 0.0f, i, getHeight(), this.j);
        } else if (this.B != null && a(view, 3)) {
            int intrinsicWidth = this.B.getIntrinsicWidth();
            int right2 = view.getRight();
            float max = Math.max(0.0f, Math.min(right2 / this.k.getEdgeSize(), 1.0f));
            this.B.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
            this.B.setAlpha((int) (max * 255.0f));
            this.B.draw(canvas);
        } else if (this.C != null && a(view, 5)) {
            int intrinsicWidth2 = this.C.getIntrinsicWidth();
            int left2 = view.getLeft();
            float max2 = Math.max(0.0f, Math.min((getWidth() - left2) / this.l.getEdgeSize(), 1.0f));
            this.C.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
            this.C.setAlpha((int) (max2 * 255.0f));
            this.C.draw(canvas);
        }
        return drawChild;
    }

    boolean e(View view) {
        return ((LayoutParams) view.getLayoutParams()).gravity == 0;
    }

    boolean f(View view) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View findTopChildUnder;
        int actionMasked = motionEvent.getActionMasked();
        boolean shouldInterceptTouchEvent = this.k.shouldInterceptTouchEvent(motionEvent) | this.l.shouldInterceptTouchEvent(motionEvent);
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.y = x;
                this.z = y;
                z = this.i > 0.0f && (findTopChildUnder = this.k.findTopChildUnder((int) x, (int) y)) != null && e(findTopChildUnder);
                this.v = false;
                break;
            case 1:
            case 3:
                a(true);
                this.v = false;
                z = false;
                break;
            case 2:
                if (this.k.checkTouchSlop(3)) {
                    this.m.a();
                    this.n.a();
                }
                z = false;
                break;
            default:
                z = false;
                break;
        }
        return shouldInterceptTouchEvent || z || g() || this.v;
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.i <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            if (a(x, y, childAt) && !e(childAt) && a(motionEvent, childAt)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        View a2;
        this.k.processTouchEvent(motionEvent);
        this.l.processTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        boolean z = false;
        if (action != 3) {
            switch (action) {
                case 0:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    this.y = x;
                    this.z = y;
                    this.v = false;
                    break;
                case 1:
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    View findTopChildUnder = this.k.findTopChildUnder((int) x2, (int) y2);
                    if (findTopChildUnder != null && e(findTopChildUnder)) {
                        float f = x2 - this.y;
                        float f2 = y2 - this.z;
                        int touchSlop = this.k.getTouchSlop();
                        if ((f * f) + (f2 * f2) < touchSlop * touchSlop && (a2 = a()) != null) {
                            if (getDrawerLockMode(a2) == 2) {
                                z = true;
                            }
                            a(z);
                            break;
                        }
                    }
                    z = true;
                    a(z);
                    break;
            }
        } else {
            a(true);
            this.v = false;
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            a(true);
        }
    }

    public void closeDrawers() {
        a(false);
    }

    void a(boolean z) {
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (f(childAt) && (!z || layoutParams.b)) {
                int width = childAt.getWidth();
                if (a(childAt, 3)) {
                    z2 |= this.k.smoothSlideViewTo(childAt, -width, childAt.getTop());
                } else {
                    z2 |= this.l.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                }
                layoutParams.b = false;
            }
        }
        this.m.a();
        this.n.a();
        if (z2) {
            invalidate();
        }
    }

    @Override // androidx.customview.widget.Openable
    public void open() {
        openDrawer(GravityCompat.START);
    }

    public void openDrawer(@NonNull View view) {
        openDrawer(view, true);
    }

    public void openDrawer(@NonNull View view, boolean z) {
        if (f(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.q) {
                layoutParams.a = 1.0f;
                layoutParams.c = 1;
                a(view, true);
                h(view);
            } else if (z) {
                layoutParams.c |= 2;
                if (a(view, 3)) {
                    this.k.smoothSlideViewTo(view, 0, view.getTop());
                } else {
                    this.l.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
                }
            } else {
                c(view, 1.0f);
                a(0, view);
                view.setVisibility(0);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public void openDrawer(int i) {
        openDrawer(i, true);
    }

    public void openDrawer(int i, boolean z) {
        View a2 = a(i);
        if (a2 != null) {
            openDrawer(a2, z);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + b(i));
    }

    @Override // androidx.customview.widget.Openable
    public void close() {
        closeDrawer(GravityCompat.START);
    }

    public void closeDrawer(@NonNull View view) {
        closeDrawer(view, true);
    }

    public void closeDrawer(@NonNull View view, boolean z) {
        if (f(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.q) {
                layoutParams.a = 0.0f;
                layoutParams.c = 0;
            } else if (z) {
                layoutParams.c |= 4;
                if (a(view, 3)) {
                    this.k.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
                } else {
                    this.l.smoothSlideViewTo(view, getWidth(), view.getTop());
                }
            } else {
                c(view, 0.0f);
                a(0, view);
                view.setVisibility(4);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public void closeDrawer(int i) {
        closeDrawer(i, true);
    }

    public void closeDrawer(int i, boolean z) {
        View a2 = a(i);
        if (a2 != null) {
            closeDrawer(a2, z);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + b(i));
    }

    public boolean isDrawerOpen(@NonNull View view) {
        if (f(view)) {
            return (((LayoutParams) view.getLayoutParams()).c & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    @Override // androidx.customview.widget.Openable
    public boolean isOpen() {
        return isDrawerOpen(GravityCompat.START);
    }

    public boolean isDrawerOpen(int i) {
        View a2 = a(i);
        if (a2 != null) {
            return isDrawerOpen(a2);
        }
        return false;
    }

    public boolean isDrawerVisible(@NonNull View view) {
        if (f(view)) {
            return ((LayoutParams) view.getLayoutParams()).a > 0.0f;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public boolean isDrawerVisible(int i) {
        View a2 = a(i);
        if (a2 != null) {
            return isDrawerVisible(a2);
        }
        return false;
    }

    private boolean g() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).b) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean z = false;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (!f(childAt)) {
                    this.L.add(childAt);
                } else if (isDrawerOpen(childAt)) {
                    childAt.addFocusables(arrayList, i, i2);
                    z = true;
                }
            }
            if (!z) {
                int size = this.L.size();
                for (int i4 = 0; i4 < size; i4++) {
                    View view = this.L.get(i4);
                    if (view.getVisibility() == 0) {
                        view.addFocusables(arrayList, i, i2);
                    }
                }
            }
            this.L.clear();
        }
    }

    private boolean h() {
        return b() != null;
    }

    View b() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (f(childAt) && isDrawerVisible(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    void c() {
        if (!this.v) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).dispatchTouchEvent(obtain);
            }
            obtain.recycle();
            this.v = true;
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || !h()) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View b2 = b();
        if (b2 != null && getDrawerLockMode(b2) == 0) {
            closeDrawers();
        }
        return b2 != null;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View a2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (!(savedState.a == 0 || (a2 = a(savedState.a)) == null)) {
            openDrawer(a2);
        }
        if (savedState.b != 3) {
            setDrawerLockMode(savedState.b, 3);
        }
        if (savedState.c != 3) {
            setDrawerLockMode(savedState.c, 5);
        }
        if (savedState.d != 3) {
            setDrawerLockMode(savedState.d, GravityCompat.START);
        }
        if (savedState.e != 3) {
            setDrawerLockMode(savedState.e, GravityCompat.END);
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            boolean z = true;
            boolean z2 = layoutParams.c == 1;
            if (layoutParams.c != 2) {
                z = false;
            }
            if (z2 || z) {
                savedState.a = layoutParams.gravity;
                break;
            }
        }
        savedState.b = this.r;
        savedState.c = this.s;
        savedState.d = this.t;
        savedState.e = this.u;
        return savedState;
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (a() != null || f(view)) {
            ViewCompat.setImportantForAccessibility(view, 4);
        } else {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
        if (!b) {
            ViewCompat.setAccessibilityDelegate(view, this.e);
        }
    }

    static boolean g(View view) {
        return (ViewCompat.getImportantForAccessibility(view) == 4 || ViewCompat.getImportantForAccessibility(view) == 2) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.drawerlayout.widget.DrawerLayout.SavedState.1
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        int b;
        int c;
        int d;
        int e;

        public SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = 0;
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
            this.a = 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class c extends ViewDragHelper.Callback {
        private final int b;
        private ViewDragHelper c;
        private final Runnable d = new Runnable() { // from class: androidx.drawerlayout.widget.DrawerLayout.c.1
            @Override // java.lang.Runnable
            public void run() {
                c.this.b();
            }
        };

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean onEdgeLock(int i) {
            return false;
        }

        c(int i) {
            this.b = i;
        }

        public void a(ViewDragHelper viewDragHelper) {
            this.c = viewDragHelper;
        }

        public void a() {
            DrawerLayout.this.removeCallbacks(this.d);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            return DrawerLayout.this.f(view) && DrawerLayout.this.a(view, this.b) && DrawerLayout.this.getDrawerLockMode(view) == 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            DrawerLayout.this.a(i, this.c.getCapturedView());
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            int width = view.getWidth();
            float width2 = DrawerLayout.this.a(view, 3) ? (i + width) / width : (DrawerLayout.this.getWidth() - i) / width;
            DrawerLayout.this.b(view, width2);
            view.setVisibility(width2 == 0.0f ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).b = false;
            c();
        }

        private void c() {
            int i = 3;
            if (this.b == 3) {
                i = 5;
            }
            View a = DrawerLayout.this.a(i);
            if (a != null) {
                DrawerLayout.this.closeDrawer(a);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            int i;
            float c = DrawerLayout.this.c(view);
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                i = (i2 > 0 || (i2 == 0 && c > 0.5f)) ? 0 : -width;
            } else {
                i = DrawerLayout.this.getWidth();
                if (f < 0.0f || (f == 0.0f && c > 0.5f)) {
                    i -= width;
                }
            }
            this.c.settleCapturedViewAt(i, view.getTop());
            DrawerLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeTouched(int i, int i2) {
            DrawerLayout.this.postDelayed(this.d, 160L);
        }

        void b() {
            View view;
            int i;
            int edgeSize = this.c.getEdgeSize();
            int i2 = 0;
            boolean z = this.b == 3;
            if (z) {
                view = DrawerLayout.this.a(3);
                if (view != null) {
                    i2 = -view.getWidth();
                }
                i = i2 + edgeSize;
            } else {
                view = DrawerLayout.this.a(5);
                i = DrawerLayout.this.getWidth() - edgeSize;
            }
            if (view == null) {
                return;
            }
            if (((z && view.getLeft() < i) || (!z && view.getLeft() > i)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.c.smoothSlideViewTo(view, i, view.getTop());
                ((LayoutParams) view.getLayoutParams()).b = true;
                DrawerLayout.this.invalidate();
                c();
                DrawerLayout.this.c();
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            View view;
            if ((i & 1) == 1) {
                view = DrawerLayout.this.a(3);
            } else {
                view = DrawerLayout.this.a(5);
            }
            if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.c.captureChildView(view, i2);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            if (DrawerLayout.this.f(view)) {
                return view.getWidth();
            }
            return 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            if (DrawerLayout.this.a(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i, width));
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        float a;
        boolean b;
        int c;
        public int gravity;

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.a);
            this.gravity = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            this(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
        }
    }

    /* loaded from: classes.dex */
    class a extends AccessibilityDelegateCompat {
        private final Rect b = new Rect();

        a() {
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (DrawerLayout.b) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            } else {
                AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
                super.onInitializeAccessibilityNodeInfo(view, obtain);
                accessibilityNodeInfoCompat.setSource(view);
                ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(view);
                if (parentForAccessibility instanceof View) {
                    accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
                }
                a(accessibilityNodeInfoCompat, obtain);
                obtain.recycle();
                a(accessibilityNodeInfoCompat, (ViewGroup) view);
            }
            accessibilityNodeInfoCompat.setClassName("androidx.drawerlayout.widget.DrawerLayout");
            accessibilityNodeInfoCompat.setFocusable(false);
            accessibilityNodeInfoCompat.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.drawerlayout.widget.DrawerLayout");
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            CharSequence drawerTitle;
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
            List<CharSequence> text = accessibilityEvent.getText();
            View b = DrawerLayout.this.b();
            if (b == null || (drawerTitle = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.d(b))) == null) {
                return true;
            }
            text.add(drawerTitle);
            return true;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.b || DrawerLayout.g(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (DrawerLayout.g(childAt)) {
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.b;
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
        }
    }

    /* loaded from: classes.dex */
    static final class b extends AccessibilityDelegateCompat {
        b() {
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (!DrawerLayout.g(view)) {
                accessibilityNodeInfoCompat.setParent(null);
            }
        }
    }
}
