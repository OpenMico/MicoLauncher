package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CollapsingToolbarLayout extends FrameLayout {
    public static final int TITLE_COLLAPSE_MODE_FADE = 1;
    public static final int TITLE_COLLAPSE_MODE_SCALE = 0;
    private static final int f = R.style.Widget_Design_CollapsingToolbar;
    private int A;
    private boolean B;
    private int C;
    private boolean D;
    @NonNull
    final CollapsingTextHelper a;
    @NonNull
    final ElevationOverlayProvider b;
    @Nullable
    Drawable c;
    int d;
    @Nullable
    WindowInsetsCompat e;
    private boolean g;
    private int h;
    @Nullable
    private ViewGroup i;
    @Nullable
    private View j;
    private View k;
    private int l;
    private int m;
    private int n;
    private int o;
    private final Rect p;
    private boolean q;
    private boolean r;
    @Nullable
    private Drawable s;
    private int t;
    private boolean u;
    private ValueAnimator v;
    private long w;
    private int x;
    private AppBarLayout.OnOffsetChangedListener y;
    private int z;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface TitleCollapseMode {
    }

    public CollapsingToolbarLayout(@NonNull Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.collapsingToolbarLayoutStyle);
    }

    public CollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, f), attributeSet, i);
        this.g = true;
        this.p = new Rect();
        this.x = -1;
        this.A = 0;
        this.C = 0;
        Context context2 = getContext();
        this.a = new CollapsingTextHelper(this);
        this.a.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        this.a.setRtlTextDirectionHeuristicsEnabled(false);
        this.b = new ElevationOverlayProvider(context2);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.CollapsingToolbarLayout, i, f, new int[0]);
        this.a.setExpandedTextGravity(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, BadgeDrawable.BOTTOM_START));
        this.a.setCollapsedTextGravity(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.o = dimensionPixelSize;
        this.n = dimensionPixelSize;
        this.m = dimensionPixelSize;
        this.l = dimensionPixelSize;
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.n = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.m = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.q = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        setTitle(obtainStyledAttributes.getText(R.styleable.CollapsingToolbarLayout_title));
        this.a.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.a.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.a.setExpandedTextAppearance(obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.a.setCollapsedTextAppearance(obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.x = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        if (obtainStyledAttributes.hasValue(R.styleable.CollapsingToolbarLayout_maxLines)) {
            this.a.setMaxLines(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_maxLines, 1));
        }
        this.w = obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        setContentScrim(obtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(obtainStyledAttributes.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        setTitleCollapseMode(obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_titleCollapseMode, 0));
        this.h = obtainStyledAttributes.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        this.B = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_forceApplySystemWindowInsetTop, false);
        this.D = obtainStyledAttributes.getBoolean(R.styleable.CollapsingToolbarLayout_extraMultilineHeightEnabled, false);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, @NonNull WindowInsetsCompat windowInsetsCompat) {
                return CollapsingToolbarLayout.this.a(windowInsetsCompat);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            a(appBarLayout);
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows(appBarLayout));
            if (this.y == null) {
                this.y = new a();
            }
            appBarLayout.addOnOffsetChangedListener(this.y);
            ViewCompat.requestApplyInsets(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        ViewParent parent = getParent();
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = this.y;
        if (onOffsetChangedListener != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    WindowInsetsCompat a(@NonNull WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.e, windowInsetsCompat2)) {
            this.e = windowInsetsCompat2;
            requestLayout();
        }
        return windowInsetsCompat.consumeSystemWindowInsets();
    }

    @Override // android.view.View
    public void draw(@NonNull Canvas canvas) {
        Drawable drawable;
        super.draw(canvas);
        c();
        if (this.i == null && (drawable = this.s) != null && this.t > 0) {
            drawable.mutate().setAlpha(this.t);
            this.s.draw(canvas);
        }
        if (this.q && this.r) {
            if (this.i == null || this.s == null || this.t <= 0 || !b() || this.a.getExpansionFraction() >= this.a.getFadeModeThresholdFraction()) {
                this.a.draw(canvas);
            } else {
                int save = canvas.save();
                canvas.clipRect(this.s.getBounds(), Region.Op.DIFFERENCE);
                this.a.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        if (this.c != null && this.t > 0) {
            WindowInsetsCompat windowInsetsCompat = this.e;
            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
            if (systemWindowInsetTop > 0) {
                this.c.setBounds(0, -this.d, getWidth(), systemWindowInsetTop - this.d);
                this.c.mutate().setAlpha(this.t);
                this.c.draw(canvas);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        if (this.s == null || this.t <= 0 || !d(view)) {
            z = false;
        } else {
            a(this.s, view, getWidth(), getHeight());
            this.s.mutate().setAlpha(this.t);
            this.s.draw(canvas);
            z = true;
        }
        return super.drawChild(canvas, view, j) || z;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.s;
        if (drawable != null) {
            a(drawable, i, i2);
        }
    }

    private boolean b() {
        return this.z == 1;
    }

    private void a(AppBarLayout appBarLayout) {
        if (b()) {
            appBarLayout.setLiftOnScroll(false);
        }
    }

    private void a(@NonNull Drawable drawable, int i, int i2) {
        a(drawable, this.i, i, i2);
    }

    private void a(@NonNull Drawable drawable, @Nullable View view, int i, int i2) {
        if (b() && view != null && this.q) {
            i2 = view.getBottom();
        }
        drawable.setBounds(0, 0, i, i2);
    }

    private void c() {
        if (this.g) {
            ViewGroup viewGroup = null;
            this.i = null;
            this.j = null;
            int i = this.h;
            if (i != -1) {
                this.i = (ViewGroup) findViewById(i);
                ViewGroup viewGroup2 = this.i;
                if (viewGroup2 != null) {
                    this.j = e(viewGroup2);
                }
            }
            if (this.i == null) {
                int childCount = getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    View childAt = getChildAt(i2);
                    if (c(childAt)) {
                        viewGroup = (ViewGroup) childAt;
                        break;
                    }
                    i2++;
                }
                this.i = viewGroup;
            }
            d();
            this.g = false;
        }
    }

    private static boolean c(View view) {
        return (view instanceof Toolbar) || (Build.VERSION.SDK_INT >= 21 && (view instanceof android.widget.Toolbar));
    }

    private boolean d(View view) {
        View view2 = this.j;
        if (view2 == null || view2 == this) {
            if (view == this.i) {
                return true;
            }
        } else if (view == view2) {
            return true;
        }
        return false;
    }

    @NonNull
    private View e(@NonNull View view) {
        for (ViewParent parent = view.getParent(); parent != this && parent != null; parent = parent.getParent()) {
            if (parent instanceof View) {
                view = (View) parent;
            }
        }
        return view;
    }

    private void d() {
        View view;
        if (!this.q && (view = this.k) != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.k);
            }
        }
        if (this.q && this.i != null) {
            if (this.k == null) {
                this.k = new View(getContext());
            }
            if (this.k.getParent() == null) {
                this.i.addView(this.k, -1, -1);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        c();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        WindowInsetsCompat windowInsetsCompat = this.e;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if ((mode == 0 || this.B) && systemWindowInsetTop > 0) {
            this.A = systemWindowInsetTop;
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + systemWindowInsetTop, 1073741824));
        }
        if (this.D && this.a.getMaxLines() > 1) {
            e();
            a(0, 0, getMeasuredWidth(), getMeasuredHeight(), true);
            int lineCount = this.a.getLineCount();
            if (lineCount > 1) {
                this.C = Math.round(this.a.getExpandedTextFullHeight()) * (lineCount - 1);
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + this.C, 1073741824));
            }
        }
        if (this.i != null) {
            View view = this.j;
            if (view == null || view == this) {
                setMinimumHeight(g(this.i));
            } else {
                setMinimumHeight(g(view));
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        WindowInsetsCompat windowInsetsCompat = this.e;
        if (windowInsetsCompat != null) {
            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (!ViewCompat.getFitsSystemWindows(childAt) && childAt.getTop() < systemWindowInsetTop) {
                    ViewCompat.offsetTopAndBottom(childAt, systemWindowInsetTop);
                }
            }
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            a(getChildAt(i6)).a();
        }
        a(i, i2, i3, i4, false);
        e();
        a();
        int childCount3 = getChildCount();
        for (int i7 = 0; i7 < childCount3; i7++) {
            a(getChildAt(i7)).b();
        }
    }

    private void a(int i, int i2, int i3, int i4, boolean z) {
        View view;
        if (this.q && (view = this.k) != null) {
            boolean z2 = false;
            this.r = ViewCompat.isAttachedToWindow(view) && this.k.getVisibility() == 0;
            if (this.r || z) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    z2 = true;
                }
                a(z2);
                this.a.setExpandedBounds(z2 ? this.n : this.l, this.p.top + this.m, (i3 - i) - (z2 ? this.l : this.n), (i4 - i2) - this.o);
                this.a.recalculate(z);
            }
        }
    }

    private void e() {
        if (this.i != null && this.q && TextUtils.isEmpty(this.a.getText())) {
            setTitle(f(this.i));
        }
    }

    private void a(boolean z) {
        int i;
        int i2;
        int i3;
        View view = this.j;
        if (view == null) {
            view = this.i;
        }
        int b = b(view);
        DescendantOffsetUtils.getDescendantRect(this, this.k, this.p);
        ViewGroup viewGroup = this.i;
        int i4 = 0;
        if (viewGroup instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) viewGroup;
            i4 = toolbar.getTitleMarginStart();
            i2 = toolbar.getTitleMarginEnd();
            i = toolbar.getTitleMarginTop();
            i3 = toolbar.getTitleMarginBottom();
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                ViewGroup viewGroup2 = this.i;
                if (viewGroup2 instanceof android.widget.Toolbar) {
                    android.widget.Toolbar toolbar2 = (android.widget.Toolbar) viewGroup2;
                    i4 = toolbar2.getTitleMarginStart();
                    i2 = toolbar2.getTitleMarginEnd();
                    i = toolbar2.getTitleMarginTop();
                    i3 = toolbar2.getTitleMarginBottom();
                }
            }
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        CollapsingTextHelper collapsingTextHelper = this.a;
        int i5 = this.p.left + (z ? i2 : i4);
        int i6 = this.p.top + b + i;
        int i7 = this.p.right;
        if (z) {
            i2 = i4;
        }
        collapsingTextHelper.setCollapsedBounds(i5, i6, i7 - i2, (this.p.bottom + b) - i3);
    }

    private static CharSequence f(View view) {
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getTitle();
        }
        if (Build.VERSION.SDK_INT < 21 || !(view instanceof android.widget.Toolbar)) {
            return null;
        }
        return ((android.widget.Toolbar) view).getTitle();
    }

    private static int g(@NonNull View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return view.getMeasuredHeight();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return view.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    @NonNull
    static d a(@NonNull View view) {
        d dVar = (d) view.getTag(R.id.view_offset_helper);
        if (dVar != null) {
            return dVar;
        }
        d dVar2 = new d(view);
        view.setTag(R.id.view_offset_helper, dVar2);
        return dVar2;
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        this.a.setText(charSequence);
        f();
    }

    @Nullable
    public CharSequence getTitle() {
        if (this.q) {
            return this.a.getText();
        }
        return null;
    }

    public void setTitleCollapseMode(int i) {
        this.z = i;
        boolean b = b();
        this.a.setFadeModeEnabled(b);
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            a((AppBarLayout) parent);
        }
        if (b && this.s == null) {
            setContentScrimColor(this.b.compositeOverlayWithThemeSurfaceColorIfNeeded(getResources().getDimension(R.dimen.design_appbar_elevation)));
        }
    }

    public int getTitleCollapseMode() {
        return this.z;
    }

    public void setTitleEnabled(boolean z) {
        if (z != this.q) {
            this.q = z;
            f();
            d();
            requestLayout();
        }
    }

    public boolean isTitleEnabled() {
        return this.q;
    }

    public void setScrimsShown(boolean z) {
        setScrimsShown(z, ViewCompat.isLaidOut(this) && !isInEditMode());
    }

    public void setScrimsShown(boolean z, boolean z2) {
        if (this.u != z) {
            int i = 255;
            if (z2) {
                if (!z) {
                    i = 0;
                }
                a(i);
            } else {
                if (!z) {
                    i = 0;
                }
                setScrimAlpha(i);
            }
            this.u = z;
        }
    }

    private void a(int i) {
        c();
        ValueAnimator valueAnimator = this.v;
        if (valueAnimator == null) {
            this.v = new ValueAnimator();
            this.v.setDuration(this.w);
            this.v.setInterpolator(i > this.t ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
            this.v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator2) {
                    CollapsingToolbarLayout.this.setScrimAlpha(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                }
            });
        } else if (valueAnimator.isRunning()) {
            this.v.cancel();
        }
        this.v.setIntValues(this.t, i);
        this.v.start();
    }

    void setScrimAlpha(int i) {
        ViewGroup viewGroup;
        if (i != this.t) {
            if (!(this.s == null || (viewGroup = this.i) == null)) {
                ViewCompat.postInvalidateOnAnimation(viewGroup);
            }
            this.t = i;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    int getScrimAlpha() {
        return this.t;
    }

    public void setContentScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.s;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.s = drawable3;
            Drawable drawable4 = this.s;
            if (drawable4 != null) {
                a(drawable4, getWidth(), getHeight());
                this.s.setCallback(this);
                this.s.setAlpha(this.t);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setContentScrimColor(@ColorInt int i) {
        setContentScrim(new ColorDrawable(i));
    }

    public void setContentScrimResource(@DrawableRes int i) {
        setContentScrim(ContextCompat.getDrawable(getContext(), i));
    }

    @Nullable
    public Drawable getContentScrim() {
        return this.s;
    }

    public void setStatusBarScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.c;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.c = drawable3;
            Drawable drawable4 = this.c;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.c.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.c, ViewCompat.getLayoutDirection(this));
                this.c.setVisible(getVisibility() == 0, false);
                this.c.setCallback(this);
                this.c.setAlpha(this.t);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.c;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.s;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        CollapsingTextHelper collapsingTextHelper = this.a;
        if (collapsingTextHelper != null) {
            z |= collapsingTextHelper.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(@NonNull Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.s || drawable == this.c;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.c;
        if (!(drawable == null || drawable.isVisible() == z)) {
            this.c.setVisible(z, false);
        }
        Drawable drawable2 = this.s;
        if (drawable2 != null && drawable2.isVisible() != z) {
            this.s.setVisible(z, false);
        }
    }

    public void setStatusBarScrimColor(@ColorInt int i) {
        setStatusBarScrim(new ColorDrawable(i));
    }

    public void setStatusBarScrimResource(@DrawableRes int i) {
        setStatusBarScrim(ContextCompat.getDrawable(getContext(), i));
    }

    @Nullable
    public Drawable getStatusBarScrim() {
        return this.c;
    }

    public void setCollapsedTitleTextAppearance(@StyleRes int i) {
        this.a.setCollapsedTextAppearance(i);
    }

    public void setCollapsedTitleTextColor(@ColorInt int i) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setCollapsedTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.a.setCollapsedTextColor(colorStateList);
    }

    public void setCollapsedTitleGravity(int i) {
        this.a.setCollapsedTextGravity(i);
    }

    public int getCollapsedTitleGravity() {
        return this.a.getCollapsedTextGravity();
    }

    public void setExpandedTitleTextAppearance(@StyleRes int i) {
        this.a.setExpandedTextAppearance(i);
    }

    public void setExpandedTitleColor(@ColorInt int i) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setExpandedTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.a.setExpandedTextColor(colorStateList);
    }

    public void setExpandedTitleGravity(int i) {
        this.a.setExpandedTextGravity(i);
    }

    public int getExpandedTitleGravity() {
        return this.a.getExpandedTextGravity();
    }

    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
        this.a.setCollapsedTypeface(typeface);
    }

    @NonNull
    public Typeface getCollapsedTitleTypeface() {
        return this.a.getCollapsedTypeface();
    }

    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
        this.a.setExpandedTypeface(typeface);
    }

    @NonNull
    public Typeface getExpandedTitleTypeface() {
        return this.a.getExpandedTypeface();
    }

    public void setExpandedTitleMargin(int i, int i2, int i3, int i4) {
        this.l = i;
        this.m = i2;
        this.n = i3;
        this.o = i4;
        requestLayout();
    }

    public int getExpandedTitleMarginStart() {
        return this.l;
    }

    public void setExpandedTitleMarginStart(int i) {
        this.l = i;
        requestLayout();
    }

    public int getExpandedTitleMarginTop() {
        return this.m;
    }

    public void setExpandedTitleMarginTop(int i) {
        this.m = i;
        requestLayout();
    }

    public int getExpandedTitleMarginEnd() {
        return this.n;
    }

    public void setExpandedTitleMarginEnd(int i) {
        this.n = i;
        requestLayout();
    }

    public int getExpandedTitleMarginBottom() {
        return this.o;
    }

    public void setExpandedTitleMarginBottom(int i) {
        this.o = i;
        requestLayout();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setMaxLines(int i) {
        this.a.setMaxLines(i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getMaxLines() {
        return this.a.getMaxLines();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getLineCount() {
        return this.a.getLineCount();
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setLineSpacingAdd(float f2) {
        this.a.setLineSpacingAdd(f2);
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getLineSpacingAdd() {
        return this.a.getLineSpacingAdd();
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setLineSpacingMultiplier(@FloatRange(from = 0.0d) float f2) {
        this.a.setLineSpacingMultiplier(f2);
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getLineSpacingMultiplier() {
        return this.a.getLineSpacingMultiplier();
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setHyphenationFrequency(int i) {
        this.a.setHyphenationFrequency(i);
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getHyphenationFrequency() {
        return this.a.getHyphenationFrequency();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRtlTextDirectionHeuristicsEnabled(boolean z) {
        this.a.setRtlTextDirectionHeuristicsEnabled(z);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.a.isRtlTextDirectionHeuristicsEnabled();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setForceApplySystemWindowInsetTop(boolean z) {
        this.B = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isForceApplySystemWindowInsetTop() {
        return this.B;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setExtraMultilineHeightEnabled(boolean z) {
        this.D = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isExtraMultilineHeightEnabled() {
        return this.D;
    }

    public void setScrimVisibleHeightTrigger(@IntRange(from = 0) int i) {
        if (this.x != i) {
            this.x = i;
            a();
        }
    }

    public int getScrimVisibleHeightTrigger() {
        int i = this.x;
        if (i >= 0) {
            return i + this.A + this.C;
        }
        WindowInsetsCompat windowInsetsCompat = this.e;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight > 0) {
            return Math.min((minimumHeight * 2) + systemWindowInsetTop, getHeight());
        }
        return getHeight() / 3;
    }

    public void setScrimAnimationDuration(@IntRange(from = 0) long j) {
        this.w = j;
    }

    public long getScrimAnimationDuration() {
        return this.w;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* loaded from: classes2.dex */
    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        int a;
        float b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
            this.b = 0.5f;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
            this.a = obtainStyledAttributes.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            setParallaxMultiplier(obtainStyledAttributes.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2, i3);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
            this.b = 0.5f;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 0;
            this.b = 0.5f;
        }

        @RequiresApi(19)
        public LayoutParams(@NonNull FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
            this.b = 0.5f;
        }

        public void setCollapseMode(int i) {
            this.a = i;
        }

        public int getCollapseMode() {
            return this.a;
        }

        public void setParallaxMultiplier(float f) {
            this.b = f;
        }

        public float getParallaxMultiplier() {
            return this.b;
        }
    }

    final void a() {
        if (this.s != null || this.c != null) {
            setScrimsShown(getHeight() + this.d < getScrimVisibleHeightTrigger());
        }
    }

    final int b(@NonNull View view) {
        return ((getHeight() - a(view).e()) - view.getHeight()) - ((LayoutParams) view.getLayoutParams()).bottomMargin;
    }

    private void f() {
        setContentDescription(getTitle());
    }

    /* loaded from: classes2.dex */
    private class a implements AppBarLayout.OnOffsetChangedListener {
        a() {
        }

        @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            collapsingToolbarLayout.d = i;
            int systemWindowInsetTop = collapsingToolbarLayout.e != null ? CollapsingToolbarLayout.this.e.getSystemWindowInsetTop() : 0;
            int childCount = CollapsingToolbarLayout.this.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = CollapsingToolbarLayout.this.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                d a = CollapsingToolbarLayout.a(childAt);
                switch (layoutParams.a) {
                    case 1:
                        a.a(MathUtils.clamp(-i, 0, CollapsingToolbarLayout.this.b(childAt)));
                        break;
                    case 2:
                        a.a(Math.round((-i) * layoutParams.b));
                        break;
                }
            }
            CollapsingToolbarLayout.this.a();
            if (CollapsingToolbarLayout.this.c != null && systemWindowInsetTop > 0) {
                ViewCompat.postInvalidateOnAnimation(CollapsingToolbarLayout.this);
            }
            int height = CollapsingToolbarLayout.this.getHeight();
            int minimumHeight = (height - ViewCompat.getMinimumHeight(CollapsingToolbarLayout.this)) - systemWindowInsetTop;
            float f = minimumHeight;
            CollapsingToolbarLayout.this.a.setFadeModeStartFraction(Math.min(1.0f, (height - CollapsingToolbarLayout.this.getScrimVisibleHeightTrigger()) / f));
            CollapsingToolbarLayout.this.a.setCurrentOffsetY(CollapsingToolbarLayout.this.d + minimumHeight);
            CollapsingToolbarLayout.this.a.setExpansionFraction(Math.abs(i) / f);
        }
    }
}
