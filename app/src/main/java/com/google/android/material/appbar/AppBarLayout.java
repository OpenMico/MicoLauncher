package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
    private static final int a = R.style.Widget_Design_AppBarLayout;
    private int b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private int g;
    @Nullable
    private WindowInsetsCompat h;
    private List<BaseOnOffsetChangedListener> i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    @IdRes
    private int n;
    @Nullable
    private WeakReference<View> o;
    @Nullable
    private ValueAnimator p;
    private int[] q;
    @Nullable
    private Drawable r;

    /* loaded from: classes2.dex */
    public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(T t, int i);
    }

    /* loaded from: classes2.dex */
    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener<AppBarLayout> {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    public AppBarLayout(@NonNull Context context) {
        this(context, null);
    }

    public AppBarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.appBarLayoutStyle);
    }

    public AppBarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        this.c = -1;
        this.d = -1;
        this.e = -1;
        this.g = 0;
        Context context2 = getContext();
        setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            e.a(this);
            e.a(this, attributeSet, i, a);
        }
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.AppBarLayout, i, a, new int[0]);
        ViewCompat.setBackground(this, obtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_android_background));
        if (getBackground() instanceof ColorDrawable) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) getBackground()).getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_expanded)) {
            a(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_elevation)) {
            e.a(this, obtainStyledAttributes.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.m = obtainStyledAttributes.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        this.n = obtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
        setStatusBarForeground(obtainStyledAttributes.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
        obtainStyledAttributes.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.a(windowInsetsCompat);
            }
        });
    }

    public void addOnOffsetChangedListener(@Nullable BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        if (this.i == null) {
            this.i = new ArrayList();
        }
        if (baseOnOffsetChangedListener != null && !this.i.contains(baseOnOffsetChangedListener)) {
            this.i.add(baseOnOffsetChangedListener);
        }
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        addOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    public void removeOnOffsetChangedListener(@Nullable BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        List<BaseOnOffsetChangedListener> list = this.i;
        if (list != null && baseOnOffsetChangedListener != null) {
            list.remove(baseOnOffsetChangedListener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        removeOnOffsetChangedListener((BaseOnOffsetChangedListener) onOffsetChangedListener);
    }

    public void setStatusBarForeground(@Nullable Drawable drawable) {
        Drawable drawable2 = this.r;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.r = drawable3;
            Drawable drawable4 = this.r;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.r.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.r, ViewCompat.getLayoutDirection(this));
                this.r.setVisible(getVisibility() == 0, false);
                this.r.setCallback(this);
            }
            d();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setStatusBarForegroundColor(@ColorInt int i) {
        setStatusBarForeground(new ColorDrawable(i));
    }

    public void setStatusBarForegroundResource(@DrawableRes int i) {
        setStatusBarForeground(AppCompatResources.getDrawable(getContext(), i));
    }

    @Nullable
    public Drawable getStatusBarForeground() {
        return this.r;
    }

    @Override // android.view.View
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        if (e()) {
            int save = canvas.save();
            canvas.translate(0.0f, -this.b);
            this.r.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.r;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(@NonNull Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.r;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.r;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824 && ViewCompat.getFitsSystemWindows(this) && i()) {
            int measuredHeight = getMeasuredHeight();
            if (mode == Integer.MIN_VALUE) {
                measuredHeight = MathUtils.clamp(getMeasuredHeight() + getTopInset(), 0, View.MeasureSpec.getSize(i2));
            } else if (mode == 0) {
                measuredHeight += getTopInset();
            }
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
        g();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (ViewCompat.getFitsSystemWindows(this) && i()) {
            int topInset = getTopInset();
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                ViewCompat.offsetTopAndBottom(getChildAt(childCount), topInset);
            }
        }
        g();
        boolean z2 = false;
        this.f = false;
        int childCount2 = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount2) {
                break;
            } else if (((LayoutParams) getChildAt(i5).getLayoutParams()).getScrollInterpolator() != null) {
                this.f = true;
                break;
            } else {
                i5++;
            }
        }
        Drawable drawable = this.r;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.j) {
            if (this.m || f()) {
                z2 = true;
            }
            b(z2);
        }
    }

    private void d() {
        setWillNotDraw(!e());
    }

    private boolean e() {
        return this.r != null && getTopInset() > 0;
    }

    private boolean f() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).a()) {
                return true;
            }
        }
        return false;
    }

    private void g() {
        this.c = -1;
        this.d = -1;
        this.e = -1;
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i) {
        if (i == 1) {
            super.setOrientation(i);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
        return new Behavior();
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    public void setExpanded(boolean z) {
        setExpanded(z, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean z, boolean z2) {
        a(z, z2, true);
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = (z ? 1 : 2) | (z2 ? 4 : 0);
        if (z3) {
            i = 8;
        }
        this.g = i2 | i;
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19 && (layoutParams instanceof LinearLayout.LayoutParams)) {
            return new LayoutParams((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        h();
    }

    boolean a() {
        return this.f;
    }

    public final int getTotalScrollRange() {
        int i = this.c;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.a;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;
            if (i2 == 0 && ViewCompat.getFitsSystemWindows(childAt)) {
                i3 -= getTopInset();
            }
            if ((i4 & 2) != 0) {
                i3 -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.c = max;
        return max;
    }

    boolean b() {
        return getTotalScrollRange() != 0;
    }

    int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    int getDownNestedPreScrollRange() {
        int i;
        int i2 = this.d;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.a;
            if ((i4 & 5) == 5) {
                int i5 = layoutParams.topMargin + layoutParams.bottomMargin;
                if ((i4 & 8) != 0) {
                    i = i5 + ViewCompat.getMinimumHeight(childAt);
                } else {
                    i = (i4 & 2) != 0 ? i5 + (measuredHeight - ViewCompat.getMinimumHeight(childAt)) : i5 + measuredHeight;
                }
                if (childCount == 0 && ViewCompat.getFitsSystemWindows(childAt)) {
                    i = Math.min(i, measuredHeight - getTopInset());
                }
                i3 += i;
            } else if (i3 > 0) {
                break;
            }
        }
        int max = Math.max(0, i3);
        this.d = max;
        return max;
    }

    int getDownNestedScrollRange() {
        int i = this.e;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int i4 = layoutParams.a;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight;
            if ((i4 & 2) != 0) {
                i3 -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.e = max;
        return max;
    }

    void a(int i) {
        this.b = i;
        if (!willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        List<BaseOnOffsetChangedListener> list = this.i;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                BaseOnOffsetChangedListener baseOnOffsetChangedListener = this.i.get(i2);
                if (baseOnOffsetChangedListener != null) {
                    baseOnOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight != 0) {
            return (minimumHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        int minimumHeight2 = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
        return minimumHeight2 != 0 ? (minimumHeight2 * 2) + topInset : getHeight() / 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i) {
        if (this.q == null) {
            this.q = new int[4];
        }
        int[] iArr = this.q;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        iArr[0] = this.k ? R.attr.state_liftable : -R.attr.state_liftable;
        iArr[1] = (!this.k || !this.l) ? -R.attr.state_lifted : R.attr.state_lifted;
        iArr[2] = this.k ? R.attr.state_collapsible : -R.attr.state_collapsible;
        iArr[3] = (!this.k || !this.l) ? -R.attr.state_collapsed : R.attr.state_collapsed;
        return mergeDrawableStates(onCreateDrawableState, iArr);
    }

    public boolean setLiftable(boolean z) {
        this.j = true;
        return b(z);
    }

    private boolean b(boolean z) {
        if (this.k == z) {
            return false;
        }
        this.k = z;
        refreshDrawableState();
        return true;
    }

    public boolean setLifted(boolean z) {
        return a(z);
    }

    public boolean isLifted() {
        return this.l;
    }

    boolean a(boolean z) {
        if (this.l == z) {
            return false;
        }
        this.l = z;
        refreshDrawableState();
        if (!this.m || !(getBackground() instanceof MaterialShapeDrawable)) {
            return true;
        }
        a((MaterialShapeDrawable) getBackground(), z);
        return true;
    }

    private void a(@NonNull final MaterialShapeDrawable materialShapeDrawable, boolean z) {
        float dimension = getResources().getDimension(R.dimen.design_appbar_elevation);
        float f = z ? 0.0f : dimension;
        if (!z) {
            dimension = 0.0f;
        }
        ValueAnimator valueAnimator = this.p;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.p = ValueAnimator.ofFloat(f, dimension);
        this.p.setDuration(getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
        this.p.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.p.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator2) {
                materialShapeDrawable.setElevation(((Float) valueAnimator2.getAnimatedValue()).floatValue());
            }
        });
        this.p.start();
    }

    public void setLiftOnScroll(boolean z) {
        this.m = z;
    }

    public boolean isLiftOnScroll() {
        return this.m;
    }

    public void setLiftOnScrollTargetViewId(@IdRes int i) {
        this.n = i;
        h();
    }

    @IdRes
    public int getLiftOnScrollTargetViewId() {
        return this.n;
    }

    boolean a(@Nullable View view) {
        View b = b(view);
        if (b != null) {
            view = b;
        }
        return view != null && (view.canScrollVertically(-1) || view.getScrollY() > 0);
    }

    @Nullable
    private View b(@Nullable View view) {
        int i;
        if (this.o == null && (i = this.n) != -1) {
            View findViewById = view != null ? view.findViewById(i) : null;
            if (findViewById == null && (getParent() instanceof ViewGroup)) {
                findViewById = ((ViewGroup) getParent()).findViewById(this.n);
            }
            if (findViewById != null) {
                this.o = new WeakReference<>(findViewById);
            }
        }
        WeakReference<View> weakReference = this.o;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void h() {
        WeakReference<View> weakReference = this.o;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.o = null;
    }

    @Deprecated
    public void setTargetElevation(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            e.a(this, f);
        }
    }

    int getPendingAction() {
        return this.g;
    }

    void c() {
        this.g = 0;
    }

    @VisibleForTesting
    final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.h;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    private boolean i() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        return childAt.getVisibility() != 8 && !ViewCompat.getFitsSystemWindows(childAt);
    }

    WindowInsetsCompat a(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.h, windowInsetsCompat2)) {
            this.h = windowInsetsCompat2;
            d();
            requestLayout();
        }
        return windowInsetsCompat;
    }

    /* loaded from: classes2.dex */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_NO_SCROLL = 0;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
        int a;
        Interpolator b;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes2.dex */
        public @interface ScrollFlags {
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.a = obtainStyledAttributes.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (obtainStyledAttributes.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.b = android.view.animation.AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 1;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
            this.a = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.a = 1;
            this.a = layoutParams.a;
            this.b = layoutParams.b;
        }

        public void setScrollFlags(int i) {
            this.a = i;
        }

        public int getScrollFlags() {
            return this.a;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.b = interpolator;
        }

        public Interpolator getScrollInterpolator() {
            return this.b;
        }

        boolean a() {
            int i = this.a;
            return (i & 1) == 1 && (i & 10) != 0;
        }
    }

    /* loaded from: classes2.dex */
    public static class Behavior extends BaseBehavior<AppBarLayout> {

        /* loaded from: classes2.dex */
        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, int i) {
            return super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            super.onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i, i2, iArr, i3);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            super.onNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i, i2, i3, i4, i5, iArr);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout, parcelable);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, @NonNull View view, View view2, int i, int i2) {
            return super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, view2, i, i2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i) {
            super.onStopNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void setDragCallback(@Nullable BaseBehavior.BaseDragCallback baseDragCallback) {
            super.setDragCallback(baseDragCallback);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* loaded from: classes2.dex */
    public static class BaseBehavior<T extends AppBarLayout> extends a<T> {
        private int b;
        private int c;
        private ValueAnimator d;
        private int e = -1;
        private boolean f;
        private float g;
        @Nullable
        private WeakReference<View> h;
        private BaseDragCallback i;

        /* loaded from: classes2.dex */
        public static abstract class BaseDragCallback<T extends AppBarLayout> {
            public abstract boolean canDrag(@NonNull T t);
        }

        private static boolean a(int i, int i2) {
            return (i & i2) == i2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.a
        /* synthetic */ int a(@NonNull View view) {
            return c((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.a
        /* bridge */ /* synthetic */ int a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3) {
            return a(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i, i2, i3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.a
        /* bridge */ /* synthetic */ void a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            a(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.a
        /* bridge */ /* synthetic */ int b(@NonNull View view) {
            return b((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.a
        /* synthetic */ boolean c(View view) {
            return a((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.c, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i) {
            return onLayoutChild(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3, int i4) {
            return onMeasureChild(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i, i2, i3, i4);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i, int i2, int[] iArr, int i3) {
            onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i, i2, iArr, i3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            onNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i, i2, i3, i4, i5, iArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, Parcelable parcelable) {
            onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), parcelable);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, View view3, int i, int i2) {
            return onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, view3, i, i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i) {
            onStopNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i);
        }

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, @NonNull View view, View view2, int i, int i2) {
            ValueAnimator valueAnimator;
            boolean z = (i & 2) != 0 && (t.isLiftOnScroll() || a(coordinatorLayout, (CoordinatorLayout) t, view));
            if (z && (valueAnimator = this.d) != null) {
                valueAnimator.cancel();
            }
            this.h = null;
            this.c = i2;
            return z;
        }

        private boolean a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, @NonNull View view) {
            return t.b() && coordinatorLayout.getHeight() - view.getHeight() <= t.getHeight();
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull T t, View view, int i, int i2, int[] iArr, int i3) {
            int i4;
            int i5;
            if (i2 != 0) {
                if (i2 < 0) {
                    int i6 = -t.getTotalScrollRange();
                    i4 = t.getDownNestedPreScrollRange() + i6;
                    i5 = i6;
                } else {
                    i5 = -t.getUpNestedPreScrollRange();
                    i4 = 0;
                }
                if (i5 != i4) {
                    iArr[1] = b(coordinatorLayout, t, i2, i5, i4);
                }
            }
            if (t.isLiftOnScroll()) {
                t.a(t.a(view));
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull T t, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            if (i4 < 0) {
                iArr[1] = b(coordinatorLayout, t, i4, -t.getDownNestedScrollRange(), 0);
            }
            if (i4 == 0) {
                c(coordinatorLayout, (CoordinatorLayout) t);
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull T t, View view, int i) {
            if (this.c == 0 || i == 1) {
                b(coordinatorLayout, (CoordinatorLayout) t);
                if (t.isLiftOnScroll()) {
                    t.a(t.a(view));
                }
            }
            this.h = new WeakReference<>(view);
        }

        public void setDragCallback(@Nullable BaseDragCallback baseDragCallback) {
            this.i = baseDragCallback;
        }

        private void a(CoordinatorLayout coordinatorLayout, @NonNull T t, int i, float f) {
            int i2;
            int abs = Math.abs(a() - i);
            float abs2 = Math.abs(f);
            if (abs2 > 0.0f) {
                i2 = Math.round((abs / abs2) * 1000.0f) * 3;
            } else {
                i2 = (int) (((abs / t.getHeight()) + 1.0f) * 150.0f);
            }
            a(coordinatorLayout, (CoordinatorLayout) t, i, i2);
        }

        private void a(final CoordinatorLayout coordinatorLayout, final T t, int i, int i2) {
            int a = a();
            if (a == i) {
                ValueAnimator valueAnimator = this.d;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.d.cancel();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator2 = this.d;
            if (valueAnimator2 == null) {
                this.d = new ValueAnimator();
                this.d.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.d.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator3) {
                        BaseBehavior.this.a(coordinatorLayout, (CoordinatorLayout) t, ((Integer) valueAnimator3.getAnimatedValue()).intValue());
                    }
                });
            } else {
                valueAnimator2.cancel();
            }
            this.d.setDuration(Math.min(i2, 600));
            this.d.setIntValues(a, i);
            this.d.start();
        }

        private int a(@NonNull T t, int i) {
            int childCount = t.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = t.getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (a(layoutParams.getScrollFlags(), 32)) {
                    top -= layoutParams.topMargin;
                    bottom += layoutParams.bottomMargin;
                }
                int i3 = -i;
                if (top <= i3 && bottom >= i3) {
                    return i2;
                }
            }
            return -1;
        }

        private void b(CoordinatorLayout coordinatorLayout, @NonNull T t) {
            int a = a();
            int a2 = a((BaseBehavior<T>) t, a);
            if (a2 >= 0) {
                View childAt = t.getChildAt(a2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int scrollFlags = layoutParams.getScrollFlags();
                if ((scrollFlags & 17) == 17) {
                    int i = -childAt.getTop();
                    int i2 = -childAt.getBottom();
                    if (a2 == t.getChildCount() - 1) {
                        i2 += t.getTopInset();
                    }
                    if (a(scrollFlags, 2)) {
                        i2 += ViewCompat.getMinimumHeight(childAt);
                    } else if (a(scrollFlags, 5)) {
                        int minimumHeight = ViewCompat.getMinimumHeight(childAt) + i2;
                        if (a < minimumHeight) {
                            i = minimumHeight;
                        } else {
                            i2 = minimumHeight;
                        }
                    }
                    if (a(scrollFlags, 32)) {
                        i += layoutParams.topMargin;
                        i2 -= layoutParams.bottomMargin;
                    }
                    if (a < (i2 + i) / 2) {
                        i = i2;
                    }
                    a(coordinatorLayout, (CoordinatorLayout) t, MathUtils.clamp(i, -t.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, int i, int i2, int i3, int i4) {
            if (((CoordinatorLayout.LayoutParams) t.getLayoutParams()).height != -2) {
                return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) t, i, i2, i3, i4);
            }
            coordinatorLayout.onMeasureChild(t, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0), i4);
            return true;
        }

        public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, int i) {
            int i2;
            boolean onLayoutChild = super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) t, i);
            int pendingAction = t.getPendingAction();
            int i3 = this.e;
            if (i3 >= 0 && (pendingAction & 8) == 0) {
                View childAt = t.getChildAt(i3);
                int i4 = -childAt.getBottom();
                if (this.f) {
                    i2 = i4 + ViewCompat.getMinimumHeight(childAt) + t.getTopInset();
                } else {
                    i2 = i4 + Math.round(childAt.getHeight() * this.g);
                }
                a(coordinatorLayout, (CoordinatorLayout) t, i2);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i5 = -t.getUpNestedPreScrollRange();
                    if (z) {
                        a(coordinatorLayout, (CoordinatorLayout) t, i5, 0.0f);
                    } else {
                        a(coordinatorLayout, (CoordinatorLayout) t, i5);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        a(coordinatorLayout, (CoordinatorLayout) t, 0, 0.0f);
                    } else {
                        a(coordinatorLayout, (CoordinatorLayout) t, 0);
                    }
                }
            }
            t.c();
            this.e = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -t.getTotalScrollRange(), 0));
            a(coordinatorLayout, (CoordinatorLayout) t, getTopAndBottomOffset(), 0, true);
            t.a(getTopAndBottomOffset());
            c(coordinatorLayout, (CoordinatorLayout) t);
            return onLayoutChild;
        }

        private void c(CoordinatorLayout coordinatorLayout, @NonNull T t) {
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            View a = a(coordinatorLayout);
            if (a != null && t.getTotalScrollRange() != 0 && (((CoordinatorLayout.LayoutParams) a.getLayoutParams()).getBehavior() instanceof ScrollingViewBehavior)) {
                b(coordinatorLayout, t, a);
            }
        }

        private void b(final CoordinatorLayout coordinatorLayout, @NonNull final T t, @NonNull final View view) {
            if (a() != (-t.getTotalScrollRange()) && view.canScrollVertically(1)) {
                a(coordinatorLayout, (CoordinatorLayout) t, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
            }
            if (a() == 0) {
                return;
            }
            if (view.canScrollVertically(-1)) {
                final int i = -t.getDownNestedPreScrollRange();
                if (i != 0) {
                    ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public boolean perform(@NonNull View view2, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                            BaseBehavior.this.onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) t, view, 0, i, new int[]{0, 0}, 1);
                            return true;
                        }
                    });
                    return;
                }
                return;
            }
            a(coordinatorLayout, (CoordinatorLayout) t, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
        }

        private void a(CoordinatorLayout coordinatorLayout, @NonNull final T t, @NonNull AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, final boolean z) {
            ViewCompat.replaceAccessibilityAction(coordinatorLayout, accessibilityActionCompat, null, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.3
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                    t.setExpanded(z);
                    return true;
                }
            });
        }

        boolean a(T t) {
            BaseDragCallback baseDragCallback = this.i;
            if (baseDragCallback != null) {
                return baseDragCallback.canDrag(t);
            }
            WeakReference<View> weakReference = this.h;
            if (weakReference == null) {
                return true;
            }
            View view = weakReference.get();
            return view != null && view.isShown() && !view.canScrollVertically(-1);
        }

        void a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t) {
            b(coordinatorLayout, (CoordinatorLayout) t);
            if (t.isLiftOnScroll()) {
                t.a(t.a(a(coordinatorLayout)));
            }
        }

        int b(@NonNull T t) {
            return -t.getDownNestedScrollRange();
        }

        int c(@NonNull T t) {
            return t.getTotalScrollRange();
        }

        int a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, int i, int i2, int i3) {
            int a = a();
            int i4 = 0;
            if (i2 == 0 || a < i2 || a > i3) {
                this.b = 0;
            } else {
                int clamp = MathUtils.clamp(i, i2, i3);
                if (a != clamp) {
                    int b = t.a() ? b((BaseBehavior<T>) t, clamp) : clamp;
                    boolean topAndBottomOffset = setTopAndBottomOffset(b);
                    i4 = a - clamp;
                    this.b = clamp - b;
                    if (!topAndBottomOffset && t.a()) {
                        coordinatorLayout.dispatchDependentViewsChanged(t);
                    }
                    t.a(getTopAndBottomOffset());
                    a(coordinatorLayout, (CoordinatorLayout) t, clamp, clamp < a ? -1 : 1, false);
                }
            }
            c(coordinatorLayout, (CoordinatorLayout) t);
            return i4;
        }

        private int b(@NonNull T t, int i) {
            int abs = Math.abs(i);
            int childCount = t.getChildCount();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                }
                View childAt = t.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                    i3++;
                } else if (scrollInterpolator != null) {
                    int scrollFlags = layoutParams.getScrollFlags();
                    if ((scrollFlags & 1) != 0) {
                        i2 = 0 + childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                        if ((scrollFlags & 2) != 0) {
                            i2 -= ViewCompat.getMinimumHeight(childAt);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        i2 -= t.getTopInset();
                    }
                    if (i2 > 0) {
                        float f = i2;
                        return Integer.signum(i) * (childAt.getTop() + Math.round(f * scrollInterpolator.getInterpolation((abs - childAt.getTop()) / f)));
                    }
                }
            }
            return i;
        }

        private void a(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, int i, int i2, boolean z) {
            View c = c(t, i);
            if (c != null) {
                int scrollFlags = ((LayoutParams) c.getLayoutParams()).getScrollFlags();
                boolean z2 = false;
                if ((scrollFlags & 1) != 0) {
                    int minimumHeight = ViewCompat.getMinimumHeight(c);
                    if (i2 <= 0 || (scrollFlags & 12) == 0) {
                        if ((scrollFlags & 2) != 0 && (-i) >= (c.getBottom() - minimumHeight) - t.getTopInset()) {
                            z2 = true;
                        }
                    } else if ((-i) >= (c.getBottom() - minimumHeight) - t.getTopInset()) {
                        z2 = true;
                    }
                }
                if (t.isLiftOnScroll()) {
                    z2 = t.a(a(coordinatorLayout));
                }
                boolean a = t.a(z2);
                if (z || (a && d(coordinatorLayout, t))) {
                    t.jumpDrawablesToCurrentState();
                }
            }
        }

        private boolean d(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t) {
            List<View> dependents = coordinatorLayout.getDependents(t);
            int size = dependents.size();
            for (int i = 0; i < size; i++) {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependents.get(i).getLayoutParams()).getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    return ((ScrollingViewBehavior) behavior).getOverlayTop() != 0;
                }
            }
            return false;
        }

        @Nullable
        private static View c(@NonNull AppBarLayout appBarLayout, int i) {
            int abs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                if (abs >= childAt.getTop() && abs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        @Nullable
        private View a(@NonNull CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof NestedScrollingChild) || (childAt instanceof ListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.a
        int a() {
            return getTopAndBottomOffset() + this.b;
        }

        public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t) {
            Parcelable onSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) t);
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = t.getChildCount();
            boolean z = false;
            for (int i = 0; i < childCount; i++) {
                View childAt = t.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    SavedState savedState = new SavedState(onSaveInstanceState);
                    savedState.a = i;
                    if (bottom == ViewCompat.getMinimumHeight(childAt) + t.getTopInset()) {
                        z = true;
                    }
                    savedState.c = z;
                    savedState.b = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return onSaveInstanceState;
        }

        public void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T t, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                SavedState savedState = (SavedState) parcelable;
                super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) t, savedState.getSuperState());
                this.e = savedState.a;
                this.g = savedState.b;
                this.f = savedState.c;
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) t, parcelable);
            this.e = -1;
        }

        /* loaded from: classes2.dex */
        public static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.SavedState.1
                @NonNull
                /* renamed from: a */
                public SavedState createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }

                @Nullable
                /* renamed from: a */
                public SavedState createFromParcel(@NonNull Parcel parcel) {
                    return new SavedState(parcel, null);
                }

                @NonNull
                /* renamed from: a */
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            int a;
            float b;
            boolean c;

            public SavedState(@NonNull Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.a = parcel.readInt();
                this.b = parcel.readFloat();
                this.c = parcel.readByte() != 0;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(@NonNull Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.a);
                parcel.writeFloat(this.b);
                parcel.writeByte(this.c ? (byte) 1 : (byte) 0);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class ScrollingViewBehavior extends b {
        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.c, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        @Override // com.google.android.material.appbar.b, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.c
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(obtainStyledAttributes.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
            a(view, view2);
            b(view, view2);
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onDependentViewRemoved(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
            if (view2 instanceof AppBarLayout) {
                ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
                ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onRequestChildRectangleOnScreen(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull Rect rect, boolean z) {
            AppBarLayout a = b(coordinatorLayout.getDependencies(view));
            if (a != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.a;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    a.setExpanded(false, !z);
                    return true;
                }
            }
            return false;
        }

        private void a(@NonNull View view, @NonNull View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                ViewCompat.offsetTopAndBottom(view, (((view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).b) + a()) - c(view2));
            }
        }

        @Override // com.google.android.material.appbar.b
        float a(View view) {
            int i;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                int a = a(appBarLayout);
                if ((downNestedPreScrollRange == 0 || totalScrollRange + a > downNestedPreScrollRange) && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (a / i) + 1.0f;
                }
            }
            return 0.0f;
        }

        private static int a(@NonNull AppBarLayout appBarLayout) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                return ((BaseBehavior) behavior).a();
            }
            return 0;
        }

        @Nullable
        /* renamed from: a */
        public AppBarLayout b(@NonNull List<View> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.android.material.appbar.b
        public int b(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout) view).getTotalScrollRange();
            }
            return super.b(view);
        }

        private void b(View view, View view2) {
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.a(appBarLayout.a(view));
                }
            }
        }
    }
}
