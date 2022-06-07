package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
/* loaded from: classes2.dex */
public class TabLayout extends HorizontalScrollView {
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int GRAVITY_START = 2;
    public static final int INDICATOR_ANIMATION_MODE_ELASTIC = 1;
    public static final int INDICATOR_ANIMATION_MODE_LINEAR = 0;
    public static final int INDICATOR_GRAVITY_BOTTOM = 0;
    public static final int INDICATOR_GRAVITY_CENTER = 1;
    public static final int INDICATOR_GRAVITY_STRETCH = 3;
    public static final int INDICATOR_GRAVITY_TOP = 2;
    public static final int MODE_AUTO = 2;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
    public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
    private static final int y = R.style.Widget_Design_TabLayout;
    private static final Pools.Pool<Tab> z = new Pools.SynchronizedPool(16);
    private final ArrayList<Tab> A;
    @Nullable
    private Tab B;
    private int C;
    private final int D;
    private final int E;
    private final int F;
    private int G;
    private b H;
    @Nullable
    private BaseOnTabSelectedListener I;
    private final ArrayList<BaseOnTabSelectedListener> J;
    @Nullable
    private BaseOnTabSelectedListener K;
    private ValueAnimator L;
    @Nullable
    private PagerAdapter M;
    private DataSetObserver N;
    private TabLayoutOnPageChangeListener O;
    private a P;
    private boolean Q;
    private final Pools.Pool<TabView> R;
    @NonNull
    final c a;
    int b;
    int c;
    int d;
    int e;
    int f;
    ColorStateList g;
    ColorStateList h;
    ColorStateList i;
    @NonNull
    Drawable j;
    PorterDuff.Mode k;
    float l;
    float m;
    final int n;
    int o;
    int p;
    int q;
    int r;
    int s;
    boolean t;
    boolean u;
    int v;
    boolean w;
    @Nullable
    ViewPager x;

    @Deprecated
    /* loaded from: classes2.dex */
    public interface BaseOnTabSelectedListener<T extends Tab> {
        void onTabReselected(T t);

        void onTabSelected(T t);

        void onTabUnselected(T t);
    }

    /* loaded from: classes2.dex */
    public @interface LabelVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface Mode {
    }

    /* loaded from: classes2.dex */
    public interface OnTabSelectedListener extends BaseOnTabSelectedListener<Tab> {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface TabGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface TabIndicatorAnimationMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface TabIndicatorGravity {
    }

    public TabLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tabStyle);
    }

    /* JADX WARN: Finally extract failed */
    public TabLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, y), attributeSet, i);
        this.A = new ArrayList<>();
        this.j = new GradientDrawable();
        this.C = 0;
        this.o = Integer.MAX_VALUE;
        this.J = new ArrayList<>();
        this.R = new Pools.SimplePool(12);
        Context context2 = getContext();
        setHorizontalScrollBarEnabled(false);
        this.a = new c(context2);
        super.addView(this.a, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.TabLayout, i, y, R.styleable.TabLayout_tabTextAppearance);
        if (getBackground() instanceof ColorDrawable) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) getBackground()).getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            materialShapeDrawable.setElevation(ViewCompat.getElevation(this));
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        setSelectedTabIndicator(MaterialResources.getDrawable(context2, obtainStyledAttributes, R.styleable.TabLayout_tabIndicator));
        setSelectedTabIndicatorColor(obtainStyledAttributes.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
        this.a.a(obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, -1));
        setSelectedTabIndicatorGravity(obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIndicatorGravity, 0));
        setTabIndicatorFullWidth(obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabIndicatorFullWidth, true));
        setTabIndicatorAnimationMode(obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIndicatorAnimationMode, 0));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
        this.e = dimensionPixelSize;
        this.d = dimensionPixelSize;
        this.c = dimensionPixelSize;
        this.b = dimensionPixelSize;
        this.b = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.b);
        this.c = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.c);
        this.d = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.d);
        this.e = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.e);
        this.f = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(this.f, androidx.appcompat.R.styleable.TextAppearance);
        try {
            this.l = obtainStyledAttributes2.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
            this.g = MaterialResources.getColorStateList(context2, obtainStyledAttributes2, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
            obtainStyledAttributes2.recycle();
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.g = MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.TabLayout_tabTextColor);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                this.g = a(this.g.getDefaultColor(), obtainStyledAttributes.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0));
            }
            this.h = MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.TabLayout_tabIconTint);
            this.k = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIconTintMode, -1), null);
            this.i = MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.TabLayout_tabRippleColor);
            this.q = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabIndicatorAnimationDuration, 300);
            this.D = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
            this.E = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
            this.n = obtainStyledAttributes.getResourceId(R.styleable.TabLayout_tabBackground, 0);
            this.G = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
            this.s = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabMode, 1);
            this.p = obtainStyledAttributes.getInt(R.styleable.TabLayout_tabGravity, 0);
            this.t = obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabInlineLabel, false);
            this.w = obtainStyledAttributes.getBoolean(R.styleable.TabLayout_tabUnboundedRipple, false);
            obtainStyledAttributes.recycle();
            Resources resources = getResources();
            this.m = resources.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
            this.F = resources.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
            e();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    public void setSelectedTabIndicatorColor(@ColorInt int i) {
        this.C = i;
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int i) {
        this.a.a(i);
    }

    public void setScrollPosition(int i, float f, boolean z2) {
        setScrollPosition(i, f, z2, true);
    }

    public void setScrollPosition(int i, float f, boolean z2, boolean z3) {
        int round = Math.round(i + f);
        if (round >= 0 && round < this.a.getChildCount()) {
            if (z3) {
                this.a.a(i, f);
            }
            ValueAnimator valueAnimator = this.L;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.L.cancel();
            }
            scrollTo(a(i, f), 0);
            if (z2) {
                setSelectedTabView(round);
            }
        }
    }

    public void addTab(@NonNull Tab tab) {
        addTab(tab, this.A.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int i) {
        addTab(tab, i, this.A.isEmpty());
    }

    public void addTab(@NonNull Tab tab, boolean z2) {
        addTab(tab, this.A.size(), z2);
    }

    public void addTab(@NonNull Tab tab, int i, boolean z2) {
        if (tab.parent == this) {
            a(tab, i);
            b(tab);
            if (z2) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void a(@NonNull TabItem tabItem) {
        Tab newTab = newTab();
        if (tabItem.text != null) {
            newTab.setText(tabItem.text);
        }
        if (tabItem.icon != null) {
            newTab.setIcon(tabItem.icon);
        }
        if (tabItem.customLayout != 0) {
            newTab.setCustomView(tabItem.customLayout);
        }
        if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
            newTab.setContentDescription(tabItem.getContentDescription());
        }
        addTab(newTab);
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener onTabSelectedListener) {
        setOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable BaseOnTabSelectedListener baseOnTabSelectedListener) {
        BaseOnTabSelectedListener baseOnTabSelectedListener2 = this.I;
        if (baseOnTabSelectedListener2 != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener2);
        }
        this.I = baseOnTabSelectedListener;
        if (baseOnTabSelectedListener != null) {
            addOnTabSelectedListener(baseOnTabSelectedListener);
        }
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        addOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void addOnTabSelectedListener(@Nullable BaseOnTabSelectedListener baseOnTabSelectedListener) {
        if (!this.J.contains(baseOnTabSelectedListener)) {
            this.J.add(baseOnTabSelectedListener);
        }
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        removeOnTabSelectedListener((BaseOnTabSelectedListener) onTabSelectedListener);
    }

    @Deprecated
    public void removeOnTabSelectedListener(@Nullable BaseOnTabSelectedListener baseOnTabSelectedListener) {
        this.J.remove(baseOnTabSelectedListener);
    }

    public void clearOnTabSelectedListeners() {
        this.J.clear();
    }

    @NonNull
    public Tab newTab() {
        Tab createTabFromPool = createTabFromPool();
        createTabFromPool.parent = this;
        createTabFromPool.view = a(createTabFromPool);
        if (createTabFromPool.h != -1) {
            createTabFromPool.view.setId(createTabFromPool.h);
        }
        return createTabFromPool;
    }

    protected Tab createTabFromPool() {
        Tab acquire = z.acquire();
        return acquire == null ? new Tab() : acquire;
    }

    protected boolean releaseFromTabPool(Tab tab) {
        return z.release(tab);
    }

    public int getTabCount() {
        return this.A.size();
    }

    @Nullable
    public Tab getTabAt(int i) {
        if (i < 0 || i >= getTabCount()) {
            return null;
        }
        return this.A.get(i);
    }

    public int getSelectedTabPosition() {
        Tab tab = this.B;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    public void removeTab(@NonNull Tab tab) {
        if (tab.parent == this) {
            removeTabAt(tab.getPosition());
            return;
        }
        throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
    }

    public void removeTabAt(int i) {
        Tab tab = this.B;
        int position = tab != null ? tab.getPosition() : 0;
        a(i);
        Tab remove = this.A.remove(i);
        if (remove != null) {
            remove.b();
            releaseFromTabPool(remove);
        }
        int size = this.A.size();
        for (int i2 = i; i2 < size; i2++) {
            this.A.get(i2).a(i2);
        }
        if (position == i) {
            selectTab(this.A.isEmpty() ? null : this.A.get(Math.max(0, i - 1)));
        }
    }

    public void removeAllTabs() {
        for (int childCount = this.a.getChildCount() - 1; childCount >= 0; childCount--) {
            a(childCount);
        }
        Iterator<Tab> it = this.A.iterator();
        while (it.hasNext()) {
            Tab next = it.next();
            it.remove();
            next.b();
            releaseFromTabPool(next);
        }
        this.B = null;
    }

    public void setTabMode(int i) {
        if (i != this.s) {
            this.s = i;
            e();
        }
    }

    public int getTabMode() {
        return this.s;
    }

    public void setTabGravity(int i) {
        if (this.p != i) {
            this.p = i;
            e();
        }
    }

    public int getTabGravity() {
        return this.p;
    }

    public void setSelectedTabIndicatorGravity(int i) {
        if (this.r != i) {
            this.r = i;
            ViewCompat.postInvalidateOnAnimation(this.a);
        }
    }

    public int getTabIndicatorGravity() {
        return this.r;
    }

    public void setTabIndicatorAnimationMode(int i) {
        this.v = i;
        switch (i) {
            case 0:
                this.H = new b();
                return;
            case 1:
                this.H = new a();
                return;
            default:
                throw new IllegalArgumentException(i + " is not a valid TabIndicatorAnimationMode");
        }
    }

    public int getTabIndicatorAnimationMode() {
        return this.v;
    }

    public void setTabIndicatorFullWidth(boolean z2) {
        this.u = z2;
        ViewCompat.postInvalidateOnAnimation(this.a);
    }

    public boolean isTabIndicatorFullWidth() {
        return this.u;
    }

    public void setInlineLabel(boolean z2) {
        if (this.t != z2) {
            this.t = z2;
            for (int i = 0; i < this.a.getChildCount(); i++) {
                View childAt = this.a.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).c();
                }
            }
            e();
        }
    }

    public void setInlineLabelResource(@BoolRes int i) {
        setInlineLabel(getResources().getBoolean(i));
    }

    public boolean isInlineLabel() {
        return this.t;
    }

    public void setUnboundedRipple(boolean z2) {
        if (this.w != z2) {
            this.w = z2;
            for (int i = 0; i < this.a.getChildCount(); i++) {
                View childAt = this.a.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).a(getContext());
                }
            }
        }
    }

    public void setUnboundedRippleResource(@BoolRes int i) {
        setUnboundedRipple(getResources().getBoolean(i));
    }

    public boolean hasUnboundedRipple() {
        return this.w;
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        if (this.g != colorStateList) {
            this.g = colorStateList;
            b();
        }
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.g;
    }

    public void setTabTextColors(int i, int i2) {
        setTabTextColors(a(i, i2));
    }

    public void setTabIconTint(@Nullable ColorStateList colorStateList) {
        if (this.h != colorStateList) {
            this.h = colorStateList;
            b();
        }
    }

    public void setTabIconTintResource(@ColorRes int i) {
        setTabIconTint(AppCompatResources.getColorStateList(getContext(), i));
    }

    @Nullable
    public ColorStateList getTabIconTint() {
        return this.h;
    }

    @Nullable
    public ColorStateList getTabRippleColor() {
        return this.i;
    }

    public void setTabRippleColor(@Nullable ColorStateList colorStateList) {
        if (this.i != colorStateList) {
            this.i = colorStateList;
            for (int i = 0; i < this.a.getChildCount(); i++) {
                View childAt = this.a.getChildAt(i);
                if (childAt instanceof TabView) {
                    ((TabView) childAt).a(getContext());
                }
            }
        }
    }

    public void setTabRippleColorResource(@ColorRes int i) {
        setTabRippleColor(AppCompatResources.getColorStateList(getContext(), i));
    }

    @NonNull
    public Drawable getTabSelectedIndicator() {
        return this.j;
    }

    public void setSelectedTabIndicator(@Nullable Drawable drawable) {
        if (this.j != drawable) {
            if (drawable == null) {
                drawable = new GradientDrawable();
            }
            this.j = drawable;
        }
    }

    public void setSelectedTabIndicator(@DrawableRes int i) {
        if (i != 0) {
            setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), i));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z2) {
        a(viewPager, z2, false);
    }

    private void a(@Nullable ViewPager viewPager, boolean z2, boolean z3) {
        ViewPager viewPager2 = this.x;
        if (viewPager2 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.O;
            if (tabLayoutOnPageChangeListener != null) {
                viewPager2.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
            }
            a aVar = this.P;
            if (aVar != null) {
                this.x.removeOnAdapterChangeListener(aVar);
            }
        }
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.K;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
            this.K = null;
        }
        if (viewPager != null) {
            this.x = viewPager;
            if (this.O == null) {
                this.O = new TabLayoutOnPageChangeListener(this);
            }
            this.O.a();
            viewPager.addOnPageChangeListener(this.O);
            this.K = new ViewPagerOnTabSelectedListener(viewPager);
            addOnTabSelectedListener(this.K);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                a(adapter, z2);
            }
            if (this.P == null) {
                this.P = new a();
            }
            this.P.a(z2);
            viewPager.addOnAdapterChangeListener(this.P);
            setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.x = null;
            a((PagerAdapter) null, false);
        }
        this.Q = z3;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        a(pagerAdapter, false);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (this.x == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                a((ViewPager) parent, true, true);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.Q) {
            setupWithViewPager(null);
            this.Q = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.a.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    void a(@Nullable PagerAdapter pagerAdapter, boolean z2) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter2 = this.M;
        if (!(pagerAdapter2 == null || (dataSetObserver = this.N) == null)) {
            pagerAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.M = pagerAdapter;
        if (z2 && pagerAdapter != null) {
            if (this.N == null) {
                this.N = new b();
            }
            pagerAdapter.registerDataSetObserver(this.N);
        }
        a();
    }

    void a() {
        int currentItem;
        removeAllTabs();
        PagerAdapter pagerAdapter = this.M;
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                addTab(newTab().setText(this.M.getPageTitle(i)), false);
            }
            ViewPager viewPager = this.x;
            if (viewPager != null && count > 0 && (currentItem = viewPager.getCurrentItem()) != getSelectedTabPosition() && currentItem < getTabCount()) {
                selectTab(getTabAt(currentItem));
            }
        }
    }

    private void b() {
        int size = this.A.size();
        for (int i = 0; i < size; i++) {
            this.A.get(i).a();
        }
    }

    @NonNull
    private TabView a(@NonNull Tab tab) {
        Pools.Pool<TabView> pool = this.R;
        TabView acquire = pool != null ? pool.acquire() : null;
        if (acquire == null) {
            acquire = new TabView(getContext());
        }
        acquire.setTab(tab);
        acquire.setFocusable(true);
        acquire.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(tab.d)) {
            acquire.setContentDescription(tab.c);
        } else {
            acquire.setContentDescription(tab.d);
        }
        return acquire;
    }

    private void a(@NonNull Tab tab, int i) {
        tab.a(i);
        this.A.add(i, tab);
        int size = this.A.size();
        while (true) {
            i++;
            if (i < size) {
                this.A.get(i).a(i);
            } else {
                return;
            }
        }
    }

    private void b(@NonNull Tab tab) {
        TabView tabView = tab.view;
        tabView.setSelected(false);
        tabView.setActivated(false);
        this.a.addView(tabView, tab.getPosition(), c());
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view) {
        a(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i) {
        a(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    private void a(View view) {
        if (view instanceof TabItem) {
            a((TabItem) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    @NonNull
    private LinearLayout.LayoutParams c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        a(layoutParams);
        return layoutParams;
    }

    private void a(@NonNull LinearLayout.LayoutParams layoutParams) {
        if (this.s == 1 && this.p == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getTabCount(), false, 1));
    }

    @Override // android.view.View
    protected void onDraw(@NonNull Canvas canvas) {
        for (int i = 0; i < this.a.getChildCount(); i++) {
            View childAt = this.a.getChildAt(i);
            if (childAt instanceof TabView) {
                ((TabView) childAt).a(canvas);
            }
        }
        super.onDraw(canvas);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int round = Math.round(ViewUtils.dpToPx(getContext(), getDefaultHeight()));
        int mode = View.MeasureSpec.getMode(i2);
        boolean z2 = false;
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                i2 = View.MeasureSpec.makeMeasureSpec(round + getPaddingTop() + getPaddingBottom(), 1073741824);
            }
        } else if (getChildCount() == 1 && View.MeasureSpec.getSize(i2) >= round) {
            getChildAt(0).setMinimumHeight(round);
        }
        int size = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i) != 0) {
            int i3 = this.E;
            if (i3 <= 0) {
                i3 = (int) (size - ViewUtils.dpToPx(getContext(), 56));
            }
            this.o = i3;
        }
        super.onMeasure(i, i2);
        if (getChildCount() == 1) {
            View childAt = getChildAt(0);
            switch (this.s) {
                case 0:
                case 2:
                    if (childAt.getMeasuredWidth() < getMeasuredWidth()) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1:
                    if (childAt.getMeasuredWidth() != getMeasuredWidth()) {
                        z2 = true;
                        break;
                    }
                    break;
            }
            if (z2) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), childAt.getLayoutParams().height));
            }
        }
    }

    private void a(int i) {
        TabView tabView = (TabView) this.a.getChildAt(i);
        this.a.removeViewAt(i);
        if (tabView != null) {
            tabView.a();
            this.R.release(tabView);
        }
        requestLayout();
    }

    private void b(int i) {
        if (i != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.a.a()) {
                setScrollPosition(i, 0.0f, true);
                return;
            }
            int scrollX = getScrollX();
            int a2 = a(i, 0.0f);
            if (scrollX != a2) {
                d();
                this.L.setIntValues(scrollX, a2);
                this.L.start();
            }
            this.a.a(i, this.q);
        }
    }

    private void d() {
        if (this.L == null) {
            this.L = new ValueAnimator();
            this.L.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.L.setDuration(this.q);
            this.L.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.tabs.TabLayout.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                    TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        d();
        this.L.addListener(animatorListener);
    }

    private void setSelectedTabView(int i) {
        int childCount = this.a.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (i2 < childCount) {
                View childAt = this.a.getChildAt(i2);
                boolean z2 = true;
                childAt.setSelected(i2 == i);
                if (i2 != i) {
                    z2 = false;
                }
                childAt.setActivated(z2);
                i2++;
            }
        }
    }

    public void selectTab(@Nullable Tab tab) {
        selectTab(tab, true);
    }

    public void selectTab(@Nullable Tab tab, boolean z2) {
        Tab tab2 = this.B;
        if (tab2 != tab) {
            int position = tab != null ? tab.getPosition() : -1;
            if (z2) {
                if ((tab2 == null || tab2.getPosition() == -1) && position != -1) {
                    setScrollPosition(position, 0.0f, true);
                } else {
                    b(position);
                }
                if (position != -1) {
                    setSelectedTabView(position);
                }
            }
            this.B = tab;
            if (tab2 != null) {
                d(tab2);
            }
            if (tab != null) {
                c(tab);
            }
        } else if (tab2 != null) {
            e(tab);
            b(tab.getPosition());
        }
    }

    private void c(@NonNull Tab tab) {
        for (int size = this.J.size() - 1; size >= 0; size--) {
            this.J.get(size).onTabSelected(tab);
        }
    }

    private void d(@NonNull Tab tab) {
        for (int size = this.J.size() - 1; size >= 0; size--) {
            this.J.get(size).onTabUnselected(tab);
        }
    }

    private void e(@NonNull Tab tab) {
        for (int size = this.J.size() - 1; size >= 0; size--) {
            this.J.get(size).onTabReselected(tab);
        }
    }

    private int a(int i, float f) {
        int i2 = this.s;
        int i3 = 0;
        if (i2 != 0 && i2 != 2) {
            return 0;
        }
        View childAt = this.a.getChildAt(i);
        int i4 = i + 1;
        View childAt2 = i4 < this.a.getChildCount() ? this.a.getChildAt(i4) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i3 = childAt2.getWidth();
        }
        int left = (childAt.getLeft() + (width / 2)) - (getWidth() / 2);
        int i5 = (int) ((width + i3) * 0.5f * f);
        return ViewCompat.getLayoutDirection(this) == 0 ? left + i5 : left - i5;
    }

    private void e() {
        int i = this.s;
        ViewCompat.setPaddingRelative(this.a, (i == 0 || i == 2) ? Math.max(0, this.G - this.b) : 0, 0, 0, 0);
        switch (this.s) {
            case 0:
                c(this.p);
                break;
            case 1:
            case 2:
                if (this.p == 2) {
                    Log.w("TabLayout", "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
                }
                this.a.setGravity(1);
                break;
        }
        a(true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void c(int i) {
        switch (i) {
            case 0:
                Log.w("TabLayout", "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
                break;
            case 1:
                this.a.setGravity(1);
                return;
            case 2:
                break;
            default:
                return;
        }
        this.a.setGravity(GravityCompat.START);
    }

    void a(boolean z2) {
        for (int i = 0; i < this.a.getChildCount(); i++) {
            View childAt = this.a.getChildAt(i);
            childAt.setMinimumWidth(getTabMinWidth());
            a((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z2) {
                childAt.requestLayout();
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class Tab {
        public static final int INVALID_POSITION = -1;
        @Nullable
        private Object a;
        @Nullable
        private Drawable b;
        @Nullable
        private CharSequence c;
        @Nullable
        private CharSequence d;
        @Nullable
        private View f;
        @Nullable
        public TabLayout parent;
        @NonNull
        public TabView view;
        private int e = -1;
        @LabelVisibility
        private int g = 1;
        private int h = -1;

        @Nullable
        public Object getTag() {
            return this.a;
        }

        @NonNull
        public Tab setTag(@Nullable Object obj) {
            this.a = obj;
            return this;
        }

        @NonNull
        public Tab setId(int i) {
            this.h = i;
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.setId(i);
            }
            return this;
        }

        public int getId() {
            return this.h;
        }

        @Nullable
        public View getCustomView() {
            return this.f;
        }

        @NonNull
        public Tab setCustomView(@Nullable View view) {
            this.f = view;
            a();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int i) {
            return setCustomView(LayoutInflater.from(this.view.getContext()).inflate(i, (ViewGroup) this.view, false));
        }

        @Nullable
        public Drawable getIcon() {
            return this.b;
        }

        public int getPosition() {
            return this.e;
        }

        void a(int i) {
            this.e = i;
        }

        @Nullable
        public CharSequence getText() {
            return this.c;
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable) {
            this.b = drawable;
            if (this.parent.p == 1 || this.parent.s == 2) {
                this.parent.a(true);
            }
            a();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.j() && this.view.f.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        @NonNull
        public Tab setIcon(@DrawableRes int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            if (TextUtils.isEmpty(this.d) && !TextUtils.isEmpty(charSequence)) {
                this.view.setContentDescription(charSequence);
            }
            this.c = charSequence;
            a();
            return this;
        }

        @NonNull
        public Tab setText(@StringRes int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setText(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public BadgeDrawable getOrCreateBadge() {
            return this.view.getOrCreateBadge();
        }

        public void removeBadge() {
            this.view.g();
        }

        @Nullable
        public BadgeDrawable getBadge() {
            return this.view.getBadge();
        }

        @NonNull
        public Tab setTabLabelVisibility(@LabelVisibility int i) {
            this.g = i;
            if (this.parent.p == 1 || this.parent.s == 2) {
                this.parent.a(true);
            }
            a();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.j() && this.view.f.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        @LabelVisibility
        public int getTabLabelVisibility() {
            return this.g;
        }

        public void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                tabLayout.selectTab(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public boolean isSelected() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return tabLayout.getSelectedTabPosition() == this.e;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@StringRes int i) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setContentDescription(tabLayout.getResources().getText(i));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            a();
            return this;
        }

        @Nullable
        public CharSequence getContentDescription() {
            TabView tabView = this.view;
            if (tabView == null) {
                return null;
            }
            return tabView.getContentDescription();
        }

        void a() {
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.b();
            }
        }

        void b() {
            this.parent = null;
            this.view = null;
            this.a = null;
            this.b = null;
            this.h = -1;
            this.c = null;
            this.d = null;
            this.e = -1;
            this.f = null;
        }
    }

    /* loaded from: classes2.dex */
    public final class TabView extends LinearLayout {
        private Tab b;
        private TextView c;
        private ImageView d;
        @Nullable
        private View e;
        @Nullable
        private BadgeDrawable f;
        @Nullable
        private View g;
        @Nullable
        private TextView h;
        @Nullable
        private ImageView i;
        @Nullable
        private Drawable j;
        private int k = 2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TabView(Context context) {
            super(context);
            TabLayout.this = r4;
            a(context);
            ViewCompat.setPaddingRelative(this, r4.b, r4.c, r4.d, r4.e);
            setGravity(17);
            setOrientation(!r4.t ? 1 : 0);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        }

        public void a(Context context) {
            if (TabLayout.this.n != 0) {
                this.j = AppCompatResources.getDrawable(context, TabLayout.this.n);
                Drawable drawable = this.j;
                if (drawable != null && drawable.isStateful()) {
                    this.j.setState(getDrawableState());
                }
            } else {
                this.j = null;
            }
            Drawable gradientDrawable = new GradientDrawable();
            ((GradientDrawable) gradientDrawable).setColor(0);
            if (TabLayout.this.i != null) {
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setCornerRadius(1.0E-5f);
                gradientDrawable2.setColor(-1);
                ColorStateList convertToRippleDrawableColor = RippleUtils.convertToRippleDrawableColor(TabLayout.this.i);
                if (Build.VERSION.SDK_INT >= 21) {
                    if (TabLayout.this.w) {
                        gradientDrawable = null;
                    }
                    if (TabLayout.this.w) {
                        gradientDrawable2 = null;
                    }
                    gradientDrawable = new RippleDrawable(convertToRippleDrawableColor, gradientDrawable, gradientDrawable2);
                } else {
                    Drawable wrap = DrawableCompat.wrap(gradientDrawable2);
                    DrawableCompat.setTintList(wrap, convertToRippleDrawableColor);
                    gradientDrawable = new LayerDrawable(new Drawable[]{gradientDrawable, wrap});
                }
            }
            ViewCompat.setBackground(this, gradientDrawable);
            TabLayout.this.invalidate();
        }

        public void a(@NonNull Canvas canvas) {
            Drawable drawable = this.j;
            if (drawable != null) {
                drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                this.j.draw(canvas);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void drawableStateChanged() {
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            Drawable drawable = this.j;
            boolean z = false;
            if (drawable != null && drawable.isStateful()) {
                z = false | this.j.setState(drawableState);
            }
            if (z) {
                invalidate();
                TabLayout.this.invalidate();
            }
        }

        @Override // android.view.View
        public boolean performClick() {
            boolean performClick = super.performClick();
            if (this.b == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.b.select();
            return true;
        }

        @Override // android.view.View
        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z && Build.VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            TextView textView = this.c;
            if (textView != null) {
                textView.setSelected(z);
            }
            ImageView imageView = this.d;
            if (imageView != null) {
                imageView.setSelected(z);
            }
            View view = this.g;
            if (view != null) {
                view.setSelected(z);
            }
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            BadgeDrawable badgeDrawable = this.f;
            if (badgeDrawable != null && badgeDrawable.isVisible()) {
                CharSequence contentDescription = getContentDescription();
                accessibilityNodeInfo.setContentDescription(((Object) contentDescription) + ", " + ((Object) this.f.getContentDescription()));
            }
            AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
            wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.b.getPosition(), 1, false, isSelected()));
            if (isSelected()) {
                wrap.setClickable(false);
                wrap.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }
            wrap.setRoleDescription(getResources().getString(R.string.item_view_role_description));
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            Layout layout;
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            int tabMaxWidth = TabLayout.this.getTabMaxWidth();
            if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
                i = View.MeasureSpec.makeMeasureSpec(TabLayout.this.o, Integer.MIN_VALUE);
            }
            super.onMeasure(i, i2);
            if (this.c != null) {
                float f = TabLayout.this.l;
                int i3 = this.k;
                ImageView imageView = this.d;
                boolean z = true;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView = this.c;
                    if (textView != null && textView.getLineCount() > 1) {
                        f = TabLayout.this.m;
                    }
                } else {
                    i3 = 1;
                }
                float textSize = this.c.getTextSize();
                int lineCount = this.c.getLineCount();
                int maxLines = TextViewCompat.getMaxLines(this.c);
                int i4 = (f > textSize ? 1 : (f == textSize ? 0 : -1));
                if (i4 != 0 || (maxLines >= 0 && i3 != maxLines)) {
                    if (TabLayout.this.s == 1 && i4 > 0 && lineCount == 1 && ((layout = this.c.getLayout()) == null || a(layout, 0, f) > (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) {
                        z = false;
                    }
                    if (z) {
                        this.c.setTextSize(0, f);
                        this.c.setMaxLines(i3);
                        super.onMeasure(i, i2);
                    }
                }
            }
        }

        void setTab(@Nullable Tab tab) {
            if (tab != this.b) {
                this.b = tab;
                b();
            }
        }

        void a() {
            setTab(null);
            setSelected(false);
        }

        final void b() {
            Tab tab = this.b;
            Drawable drawable = null;
            View customView = tab != null ? tab.getCustomView() : null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.g = customView;
                TextView textView = this.c;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.d;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.d.setImageDrawable(null);
                }
                this.h = (TextView) customView.findViewById(16908308);
                TextView textView2 = this.h;
                if (textView2 != null) {
                    this.k = TextViewCompat.getMaxLines(textView2);
                }
                this.i = (ImageView) customView.findViewById(16908294);
            } else {
                View view = this.g;
                if (view != null) {
                    removeView(view);
                    this.g = null;
                }
                this.h = null;
                this.i = null;
            }
            if (this.g == null) {
                if (this.d == null) {
                    d();
                }
                if (!(tab == null || tab.getIcon() == null)) {
                    drawable = DrawableCompat.wrap(tab.getIcon()).mutate();
                }
                if (drawable != null) {
                    DrawableCompat.setTintList(drawable, TabLayout.this.h);
                    if (TabLayout.this.k != null) {
                        DrawableCompat.setTintMode(drawable, TabLayout.this.k);
                    }
                }
                if (this.c == null) {
                    e();
                    this.k = TextViewCompat.getMaxLines(this.c);
                }
                TextViewCompat.setTextAppearance(this.c, TabLayout.this.f);
                if (TabLayout.this.g != null) {
                    this.c.setTextColor(TabLayout.this.g);
                }
                a(this.c, this.d);
                h();
                a(this.d);
                a(this.c);
            } else if (!(this.h == null && this.i == null)) {
                a(this.h, this.i);
            }
            if (tab != null && !TextUtils.isEmpty(tab.d)) {
                setContentDescription(tab.d);
            }
            setSelected(tab != null && tab.isSelected());
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void d() {
            FrameLayout frameLayout;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                frameLayout = f();
                addView(frameLayout, 0);
            } else {
                frameLayout = this;
            }
            this.d = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup) frameLayout, false);
            frameLayout.addView(this.d, 0);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void e() {
            FrameLayout frameLayout;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                frameLayout = f();
                addView(frameLayout);
            } else {
                frameLayout = this;
            }
            this.c = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup) frameLayout, false);
            frameLayout.addView(this.c);
        }

        @NonNull
        private FrameLayout f() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            return frameLayout;
        }

        @NonNull
        public BadgeDrawable getOrCreateBadge() {
            if (this.f == null) {
                this.f = BadgeDrawable.create(getContext());
            }
            h();
            BadgeDrawable badgeDrawable = this.f;
            if (badgeDrawable != null) {
                return badgeDrawable;
            }
            throw new IllegalStateException("Unable to create badge");
        }

        @Nullable
        public BadgeDrawable getBadge() {
            return this.f;
        }

        public void g() {
            if (this.e != null) {
                i();
            }
            this.f = null;
        }

        private void a(@Nullable final View view) {
            if (view != null) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        if (view.getVisibility() == 0) {
                            TabView.this.c(view);
                        }
                    }
                });
            }
        }

        private void h() {
            Tab tab;
            Tab tab2;
            if (j()) {
                if (this.g != null) {
                    i();
                } else if (this.d != null && (tab2 = this.b) != null && tab2.getIcon() != null) {
                    View view = this.e;
                    ImageView imageView = this.d;
                    if (view != imageView) {
                        i();
                        b(this.d);
                        return;
                    }
                    c(imageView);
                } else if (this.c == null || (tab = this.b) == null || tab.getTabLabelVisibility() != 1) {
                    i();
                } else {
                    View view2 = this.e;
                    TextView textView = this.c;
                    if (view2 != textView) {
                        i();
                        b(this.c);
                        return;
                    }
                    c(textView);
                }
            }
        }

        private void b(@Nullable View view) {
            if (j() && view != null) {
                a(false);
                BadgeUtils.attachBadgeDrawable(this.f, view, d(view));
                this.e = view;
            }
        }

        private void i() {
            if (j()) {
                a(true);
                View view = this.e;
                if (view != null) {
                    BadgeUtils.detachBadgeDrawable(this.f, view);
                    this.e = null;
                }
            }
        }

        private void a(boolean z) {
            setClipChildren(z);
            setClipToPadding(z);
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup != null) {
                viewGroup.setClipChildren(z);
                viewGroup.setClipToPadding(z);
            }
        }

        final void c() {
            setOrientation(!TabLayout.this.t ? 1 : 0);
            if (this.h == null && this.i == null) {
                a(this.c, this.d);
            } else {
                a(this.h, this.i);
            }
        }

        private void a(@Nullable TextView textView, @Nullable ImageView imageView) {
            Tab tab = this.b;
            CharSequence charSequence = null;
            Drawable mutate = (tab == null || tab.getIcon() == null) ? null : DrawableCompat.wrap(this.b.getIcon()).mutate();
            Tab tab2 = this.b;
            CharSequence text = tab2 != null ? tab2.getText() : null;
            if (imageView != null) {
                if (mutate != null) {
                    imageView.setImageDrawable(mutate);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
            }
            boolean z = !TextUtils.isEmpty(text);
            if (textView != null) {
                if (z) {
                    textView.setText(text);
                    if (this.b.g == 1) {
                        textView.setVisibility(0);
                    } else {
                        textView.setVisibility(8);
                    }
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                int dpToPx = (!z || imageView.getVisibility() != 0) ? 0 : (int) ViewUtils.dpToPx(getContext(), 8);
                if (TabLayout.this.t) {
                    if (dpToPx != MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams)) {
                        MarginLayoutParamsCompat.setMarginEnd(marginLayoutParams, dpToPx);
                        marginLayoutParams.bottomMargin = 0;
                        imageView.setLayoutParams(marginLayoutParams);
                        imageView.requestLayout();
                    }
                } else if (dpToPx != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = dpToPx;
                    MarginLayoutParamsCompat.setMarginEnd(marginLayoutParams, 0);
                    imageView.setLayoutParams(marginLayoutParams);
                    imageView.requestLayout();
                }
            }
            Tab tab3 = this.b;
            if (tab3 != null) {
                charSequence = tab3.d;
            }
            if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
                if (z) {
                    charSequence = text;
                }
                TooltipCompat.setTooltipText(this, charSequence);
            }
        }

        public void c(@NonNull View view) {
            if (j() && view == this.e) {
                BadgeUtils.setBadgeDrawableBounds(this.f, view, d(view));
            }
        }

        public boolean j() {
            return this.f != null;
        }

        @Nullable
        private FrameLayout d(@NonNull View view) {
            if ((view == this.d || view == this.c) && BadgeUtils.USE_COMPAT_PARENT) {
                return (FrameLayout) view.getParent();
            }
            return null;
        }

        public int getContentWidth() {
            View[] viewArr = {this.c, this.d, this.g};
            int i = 0;
            int i2 = 0;
            boolean z = false;
            for (View view : viewArr) {
                if (view != null && view.getVisibility() == 0) {
                    i2 = z ? Math.min(i2, view.getLeft()) : view.getLeft();
                    i = z ? Math.max(i, view.getRight()) : view.getRight();
                    z = true;
                }
            }
            return i - i2;
        }

        public int getContentHeight() {
            View[] viewArr = {this.c, this.d, this.g};
            int i = 0;
            int i2 = 0;
            boolean z = false;
            for (View view : viewArr) {
                if (view != null && view.getVisibility() == 0) {
                    i2 = z ? Math.min(i2, view.getTop()) : view.getTop();
                    i = z ? Math.max(i, view.getBottom()) : view.getBottom();
                    z = true;
                }
            }
            return i - i2;
        }

        @Nullable
        public Tab getTab() {
            return this.b;
        }

        private float a(@NonNull Layout layout, int i, float f) {
            return layout.getLineWidth(i) * (f / layout.getPaint().getTextSize());
        }
    }

    /* loaded from: classes2.dex */
    public class c extends LinearLayout {
        ValueAnimator a;
        float c;
        int b = -1;
        private int e = -1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(Context context) {
            super(context);
            TabLayout.this = r1;
            setWillNotDraw(false);
        }

        void a(int i) {
            Rect bounds = TabLayout.this.j.getBounds();
            TabLayout.this.j.setBounds(bounds.left, 0, bounds.right, i);
            requestLayout();
        }

        boolean a() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        void a(int i, float f) {
            ValueAnimator valueAnimator = this.a;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.a.cancel();
            }
            this.b = i;
            this.c = f;
            a(getChildAt(this.b), getChildAt(this.b + 1), this.c);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onRtlPropertiesChanged(int i) {
            super.onRtlPropertiesChanged(i);
            if (Build.VERSION.SDK_INT < 23 && this.e != i) {
                requestLayout();
                this.e = i;
            }
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            boolean z;
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) == 1073741824) {
                if (TabLayout.this.p == 1 || TabLayout.this.s == 2) {
                    int childCount = getChildCount();
                    int i3 = 0;
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = getChildAt(i4);
                        if (childAt.getVisibility() == 0) {
                            i3 = Math.max(i3, childAt.getMeasuredWidth());
                        }
                    }
                    if (i3 > 0) {
                        if (i3 * childCount <= getMeasuredWidth() - (((int) ViewUtils.dpToPx(getContext(), 16)) * 2)) {
                            z = false;
                            for (int i5 = 0; i5 < childCount; i5++) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i5).getLayoutParams();
                                if (layoutParams.width != i3 || layoutParams.weight != 0.0f) {
                                    layoutParams.width = i3;
                                    layoutParams.weight = 0.0f;
                                    z = true;
                                }
                            }
                        } else {
                            TabLayout tabLayout = TabLayout.this;
                            tabLayout.p = 0;
                            tabLayout.a(false);
                            z = true;
                        }
                        if (z) {
                            super.onMeasure(i, i2);
                        }
                    }
                }
            }
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ValueAnimator valueAnimator = this.a;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                b();
            } else {
                a(false, this.b, -1);
            }
        }

        private void b() {
            View childAt = getChildAt(this.b);
            b bVar = TabLayout.this.H;
            TabLayout tabLayout = TabLayout.this;
            bVar.a(tabLayout, childAt, tabLayout.j);
        }

        public void a(View view, View view2, float f) {
            if (view != null && view.getWidth() > 0) {
                b bVar = TabLayout.this.H;
                TabLayout tabLayout = TabLayout.this;
                bVar.a(tabLayout, view, view2, f, tabLayout.j);
            } else {
                TabLayout.this.j.setBounds(-1, TabLayout.this.j.getBounds().top, -1, TabLayout.this.j.getBounds().bottom);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }

        void a(int i, int i2) {
            ValueAnimator valueAnimator = this.a;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.a.cancel();
            }
            a(true, i, i2);
        }

        private void a(boolean z, final int i, int i2) {
            final View childAt = getChildAt(this.b);
            final View childAt2 = getChildAt(i);
            if (childAt2 == null) {
                b();
                return;
            }
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.tabs.TabLayout.c.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                    c.this.a(childAt, childAt2, valueAnimator.getAnimatedFraction());
                }
            };
            if (z) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.a = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                valueAnimator.setDuration(i2);
                valueAnimator.setFloatValues(0.0f, 1.0f);
                valueAnimator.addUpdateListener(animatorUpdateListener);
                valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.tabs.TabLayout.c.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        c.this.b = i;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        c.this.b = i;
                    }
                });
                valueAnimator.start();
                return;
            }
            this.a.removeAllUpdateListeners();
            this.a.addUpdateListener(animatorUpdateListener);
        }

        @Override // android.view.View
        public void draw(@NonNull Canvas canvas) {
            int height = TabLayout.this.j.getBounds().height();
            if (height < 0) {
                height = TabLayout.this.j.getIntrinsicHeight();
            }
            int i = 0;
            switch (TabLayout.this.r) {
                case 0:
                    i = getHeight() - height;
                    height = getHeight();
                    break;
                case 1:
                    i = (getHeight() - height) / 2;
                    height = (getHeight() + height) / 2;
                    break;
                case 2:
                    break;
                case 3:
                    height = getHeight();
                    break;
                default:
                    height = 0;
                    break;
            }
            if (TabLayout.this.j.getBounds().width() > 0) {
                Rect bounds = TabLayout.this.j.getBounds();
                TabLayout.this.j.setBounds(bounds.left, i, bounds.right, height);
                Drawable drawable = TabLayout.this.j;
                if (TabLayout.this.C != 0) {
                    drawable = DrawableCompat.wrap(drawable);
                    if (Build.VERSION.SDK_INT == 21) {
                        drawable.setColorFilter(TabLayout.this.C, PorterDuff.Mode.SRC_IN);
                    } else {
                        DrawableCompat.setTint(drawable, TabLayout.this.C);
                    }
                }
                drawable.draw(canvas);
            }
            super.draw(canvas);
        }
    }

    @NonNull
    private static ColorStateList a(int i, int i2) {
        return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{i2, i});
    }

    @Dimension(unit = 0)
    private int getDefaultHeight() {
        int size = this.A.size();
        boolean z2 = false;
        int i = 0;
        while (true) {
            if (i < size) {
                Tab tab = this.A.get(i);
                if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                    z2 = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return (!z2 || this.t) ? 48 : 72;
    }

    private int getTabMinWidth() {
        int i = this.D;
        if (i != -1) {
            return i;
        }
        int i2 = this.s;
        if (i2 == 0 || i2 == 2) {
            return this.F;
        }
        return 0;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    int getTabMaxWidth() {
        return this.o;
    }

    /* loaded from: classes2.dex */
    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @NonNull
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            this.b = this.c;
            this.c = i;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.c != 2 || this.b == 1;
                if (!(this.c == 2 && this.b == 0)) {
                    z = true;
                }
                tabLayout.setScrollPosition(i, f, z2, z);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i && i < tabLayout.getTabCount()) {
                int i2 = this.c;
                tabLayout.selectTab(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.b == 0));
            }
        }

        void a() {
            this.c = 0;
            this.b = 0;
        }
    }

    /* loaded from: classes2.dex */
    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager a;

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabReselected(Tab tab) {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabUnselected(Tab tab) {
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.a = viewPager;
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabSelected(@NonNull Tab tab) {
            this.a.setCurrentItem(tab.getPosition());
        }
    }

    /* loaded from: classes2.dex */
    public class b extends DataSetObserver {
        b() {
            TabLayout.this = r1;
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            TabLayout.this.a();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            TabLayout.this.a();
        }
    }

    /* loaded from: classes2.dex */
    public class a implements ViewPager.OnAdapterChangeListener {
        private boolean b;

        a() {
            TabLayout.this = r1;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
            if (TabLayout.this.x == viewPager) {
                TabLayout.this.a(pagerAdapter2, this.b);
            }
        }

        void a(boolean z) {
            this.b = z;
        }
    }
}
