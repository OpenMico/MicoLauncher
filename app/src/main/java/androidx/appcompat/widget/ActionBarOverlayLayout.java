package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.graphics.Insets;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

@SuppressLint({"UnknownNullness"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent, NestedScrollingParent, NestedScrollingParent2, NestedScrollingParent3 {
    static final int[] e = {R.attr.actionBarSize, 16842841};
    @NonNull
    private WindowInsetsCompat A;
    private ActionBarVisibilityCallback B;
    private OverScroller C;
    private final Runnable D;
    private final Runnable E;
    private final NestedScrollingParentHelper F;
    ActionBarContainer a;
    boolean b;
    ViewPropertyAnimator c;
    final AnimatorListenerAdapter d;
    private int f;
    private int g;
    private ContentFrameLayout h;
    private DecorToolbar i;
    private Drawable j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private final Rect q;
    private final Rect r;
    private final Rect s;
    private final Rect t;
    private final Rect u;
    private final Rect v;
    private final Rect w;
    @NonNull
    private WindowInsetsCompat x;
    @NonNull
    private WindowInsetsCompat y;
    @NonNull
    private WindowInsetsCompat z;

    /* loaded from: classes.dex */
    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z);

        void hideForSystem();

        void onContentScrollStarted();

        void onContentScrollStopped();

        void onWindowVisibilityChanged(int i);

        void showForSystem();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    public void setShowingForActionMode(boolean z) {
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setUiOptions(int i) {
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarOverlayLayout(@NonNull Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = 0;
        this.q = new Rect();
        this.r = new Rect();
        this.s = new Rect();
        this.t = new Rect();
        this.u = new Rect();
        this.v = new Rect();
        this.w = new Rect();
        this.x = WindowInsetsCompat.CONSUMED;
        this.y = WindowInsetsCompat.CONSUMED;
        this.z = WindowInsetsCompat.CONSUMED;
        this.A = WindowInsetsCompat.CONSUMED;
        this.d = new AnimatorListenerAdapter() { // from class: androidx.appcompat.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.c = null;
                actionBarOverlayLayout.b = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.c = null;
                actionBarOverlayLayout.b = false;
            }
        };
        this.D = new Runnable() { // from class: androidx.appcompat.widget.ActionBarOverlayLayout.2
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.b();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.c = actionBarOverlayLayout.a.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.d);
            }
        };
        this.E = new Runnable() { // from class: androidx.appcompat.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.b();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.c = actionBarOverlayLayout.a.animate().translationY(-ActionBarOverlayLayout.this.a.getHeight()).setListener(ActionBarOverlayLayout.this.d);
            }
        };
        a(context);
        this.F = new NestedScrollingParentHelper(this);
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(e);
        boolean z = false;
        this.f = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.j = obtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.j == null);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z = true;
        }
        this.k = z;
        this.C = new OverScroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.B = actionBarVisibilityCallback;
        if (getWindowToken() != null) {
            this.B.onWindowVisibilityChanged(this.g);
            int i = this.p;
            if (i != 0) {
                onWindowSystemUiVisibilityChanged(i);
                ViewCompat.requestApplyInsets(this);
            }
        }
    }

    public void setOverlayMode(boolean z) {
        this.l = z;
        this.k = z && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.l;
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.m = z;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a(getContext());
        ViewCompat.requestApplyInsets(this);
    }

    @Override // android.view.View
    @Deprecated
    public void onWindowSystemUiVisibilityChanged(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i);
        }
        a();
        int i2 = this.p ^ i;
        this.p = i;
        boolean z = false;
        boolean z2 = (i & 4) == 0;
        if ((i & 256) != 0) {
            z = true;
        }
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.B;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.enableContentAnimations(!z);
            if (z2 || !z) {
                this.B.showForSystem();
            } else {
                this.B.hideForSystem();
            }
        }
        if ((i2 & 256) != 0 && this.B != null) {
            ViewCompat.requestApplyInsets(this);
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.g = i;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.B;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onWindowVisibilityChanged(i);
        }
    }

    private boolean a(@NonNull View view, @NonNull Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!z || layoutParams.leftMargin == rect.left) {
            z5 = false;
        } else {
            layoutParams.leftMargin = rect.left;
            z5 = true;
        }
        if (z2 && layoutParams.topMargin != rect.top) {
            layoutParams.topMargin = rect.top;
            z5 = true;
        }
        if (z4 && layoutParams.rightMargin != rect.right) {
            layoutParams.rightMargin = rect.right;
            z5 = true;
        }
        if (!z3 || layoutParams.bottomMargin == rect.bottom) {
            return z5;
        }
        layoutParams.bottomMargin = rect.bottom;
        return true;
    }

    @Override // android.view.View
    protected boolean fitSystemWindows(Rect rect) {
        if (Build.VERSION.SDK_INT >= 21) {
            return super.fitSystemWindows(rect);
        }
        a();
        boolean a = a(this.a, rect, true, true, false, true);
        this.t.set(rect);
        ViewUtils.computeFitSystemWindows(this, this.t, this.q);
        if (!this.u.equals(this.t)) {
            this.u.set(this.t);
            a = true;
        }
        if (!this.r.equals(this.q)) {
            this.r.set(this.q);
            a = true;
        }
        if (a) {
            requestLayout();
        }
        return true;
    }

    @Override // android.view.View
    @RequiresApi(21)
    public WindowInsets onApplyWindowInsets(@NonNull WindowInsets windowInsets) {
        a();
        WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets, this);
        boolean a = a(this.a, new Rect(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom()), true, true, false, true);
        ViewCompat.computeSystemWindowInsets(this, windowInsetsCompat, this.q);
        this.x = windowInsetsCompat.inset(this.q.left, this.q.top, this.q.right, this.q.bottom);
        if (!this.y.equals(this.x)) {
            this.y = this.x;
            a = true;
        }
        if (!this.r.equals(this.q)) {
            this.r.set(this.q);
            a = true;
        }
        if (a) {
            requestLayout();
        }
        return windowInsetsCompat.consumeDisplayCutout().consumeSystemWindowInsets().consumeStableInsets().toWindowInsets();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        a();
        measureChildWithMargins(this.a, i, 0, i2, 0);
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        int max = Math.max(0, this.a.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int max2 = Math.max(0, this.a.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int combineMeasuredStates = View.combineMeasuredStates(0, this.a.getMeasuredState());
        boolean z = (ViewCompat.getWindowSystemUiVisibility(this) & 256) != 0;
        if (z) {
            i3 = this.f;
            if (this.m && this.a.getTabContainer() != null) {
                i3 += this.f;
            }
        } else {
            i3 = this.a.getVisibility() != 8 ? this.a.getMeasuredHeight() : 0;
        }
        this.s.set(this.q);
        if (Build.VERSION.SDK_INT >= 21) {
            this.z = this.x;
        } else {
            this.v.set(this.t);
        }
        if (!this.l && !z) {
            this.s.top += i3;
            this.s.bottom += 0;
            if (Build.VERSION.SDK_INT >= 21) {
                this.z = this.z.inset(0, i3, 0, 0);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.z = new WindowInsetsCompat.Builder(this.z).setSystemWindowInsets(Insets.of(this.z.getSystemWindowInsetLeft(), this.z.getSystemWindowInsetTop() + i3, this.z.getSystemWindowInsetRight(), this.z.getSystemWindowInsetBottom() + 0)).build();
        } else {
            this.v.top += i3;
            this.v.bottom += 0;
        }
        a(this.h, this.s, true, true, true, true);
        if (Build.VERSION.SDK_INT >= 21 && !this.A.equals(this.z)) {
            WindowInsetsCompat windowInsetsCompat = this.z;
            this.A = windowInsetsCompat;
            ViewCompat.dispatchApplyWindowInsets(this.h, windowInsetsCompat);
        } else if (Build.VERSION.SDK_INT < 21 && !this.w.equals(this.v)) {
            this.w.set(this.v);
            this.h.dispatchFitSystemWindows(this.v);
        }
        measureChildWithMargins(this.h, i, 0, i2, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.h.getLayoutParams();
        int max3 = Math.max(max, this.h.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
        int max4 = Math.max(max2, this.h.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
        int combineMeasuredStates2 = View.combineMeasuredStates(combineMeasuredStates, this.h.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, combineMeasuredStates2), View.resolveSizeAndState(Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, combineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = layoutParams.leftMargin + paddingLeft;
                int i7 = layoutParams.topMargin + paddingTop;
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7);
            }
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.j != null && !this.k) {
            int bottom = this.a.getVisibility() == 0 ? (int) (this.a.getBottom() + this.a.getTranslationY() + 0.5f) : 0;
            this.j.setBounds(0, bottom, getWidth(), this.j.getIntrinsicHeight() + bottom);
            this.j.draw(canvas);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        onNestedScroll(view, i, i2, i3, i4, i5);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return i2 == 0 && onStartNestedScroll(view, view2, i);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        if (i2 == 0) {
            onNestedScrollAccepted(view, view2, i);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(View view, int i) {
        if (i == 0) {
            onStopNestedScroll(view);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            onNestedScroll(view, i, i2, i3, i4);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        if (i3 == 0) {
            onNestedPreScroll(view, i, i2, iArr);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.a.getVisibility() != 0) {
            return false;
        }
        return this.n;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.F.onNestedScrollAccepted(view, view2, i);
        this.o = getActionBarHideOffset();
        b();
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.B;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        this.o += i2;
        setActionBarHideOffset(this.o);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view) {
        if (this.n && !this.b) {
            if (this.o <= this.a.getHeight()) {
                c();
            } else {
                d();
            }
        }
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.B;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.n || !z) {
            return false;
        }
        if (a(f2)) {
            f();
        } else {
            e();
        }
        this.b = true;
        return true;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.F.getNestedScrollAxes();
    }

    void a() {
        if (this.h == null) {
            this.h = (ContentFrameLayout) findViewById(R.id.action_bar_activity_content);
            this.a = (ActionBarContainer) findViewById(R.id.action_bar_container);
            this.i = a(findViewById(R.id.action_bar));
        }
    }

    private DecorToolbar a(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.n) {
            this.n = z;
            if (!z) {
                b();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.n;
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.a;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    public void setActionBarHideOffset(int i) {
        b();
        this.a.setTranslationY(-Math.max(0, Math.min(i, this.a.getHeight())));
    }

    void b() {
        removeCallbacks(this.D);
        removeCallbacks(this.E);
        ViewPropertyAnimator viewPropertyAnimator = this.c;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    private void c() {
        b();
        postDelayed(this.D, 600L);
    }

    private void d() {
        b();
        postDelayed(this.E, 600L);
    }

    private void e() {
        b();
        this.D.run();
    }

    private void f() {
        b();
        this.E.run();
    }

    private boolean a(float f) {
        this.C.fling(0, 0, 0, (int) f, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.C.getFinalY() > this.a.getHeight();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setWindowCallback(Window.Callback callback) {
        a();
        this.i.setWindowCallback(callback);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setWindowTitle(CharSequence charSequence) {
        a();
        this.i.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public CharSequence getTitle() {
        a();
        return this.i.getTitle();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void initFeature(int i) {
        a();
        if (i == 2) {
            this.i.initProgress();
        } else if (i == 5) {
            this.i.initIndeterminateProgress();
        } else if (i == 109) {
            setOverlayMode(true);
        }
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean hasIcon() {
        a();
        return this.i.hasIcon();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean hasLogo() {
        a();
        return this.i.hasLogo();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setIcon(int i) {
        a();
        this.i.setIcon(i);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setIcon(Drawable drawable) {
        a();
        this.i.setIcon(drawable);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setLogo(int i) {
        a();
        this.i.setLogo(i);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean canShowOverflowMenu() {
        a();
        return this.i.canShowOverflowMenu();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean isOverflowMenuShowing() {
        a();
        return this.i.isOverflowMenuShowing();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean isOverflowMenuShowPending() {
        a();
        return this.i.isOverflowMenuShowPending();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean showOverflowMenu() {
        a();
        return this.i.showOverflowMenu();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public boolean hideOverflowMenu() {
        a();
        return this.i.hideOverflowMenu();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setMenuPrepared() {
        a();
        this.i.setMenuPrepared();
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        a();
        this.i.setMenu(menu, callback);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        a();
        this.i.saveHierarchyState(sparseArray);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        a();
        this.i.restoreHierarchyState(sparseArray);
    }

    @Override // androidx.appcompat.widget.DecorContentParent
    public void dismissPopups() {
        a();
        this.i.dismissPopupMenus();
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }
}
