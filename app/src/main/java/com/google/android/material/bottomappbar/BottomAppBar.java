package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Dimension;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    private static final int g = R.style.Widget_MaterialComponents_BottomAppBar;
    @NonNull
    AnimatorListenerAdapter e;
    @NonNull
    TransformationCallback<FloatingActionButton> f;
    private final int h;
    private final MaterialShapeDrawable i;
    @Nullable
    private Animator j;
    @Nullable
    private Animator k;
    private int l;
    private int m;
    private boolean n;
    private final boolean o;
    private final boolean p;
    private final boolean q;
    private int r;
    private ArrayList<a> s;
    @MenuRes
    private int t;
    private boolean u;
    private boolean v;
    private Behavior w;
    private int x;
    private int y;
    private int z;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FabAlignmentMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FabAnimationMode {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface a {
        void a(BottomAppBar bottomAppBar);

        void b(BottomAppBar bottomAppBar);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
    }

    public BottomAppBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public BottomAppBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomAppBarStyle);
    }

    public BottomAppBar(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, g), attributeSet, i);
        this.i = new MaterialShapeDrawable();
        this.r = 0;
        this.t = 0;
        this.u = false;
        this.v = true;
        this.e = new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (!BottomAppBar.this.u) {
                    BottomAppBar bottomAppBar = BottomAppBar.this;
                    bottomAppBar.a(bottomAppBar.l, BottomAppBar.this.v);
                }
            }
        };
        this.f = new TransformationCallback<FloatingActionButton>() { // from class: com.google.android.material.bottomappbar.BottomAppBar.2
            /* renamed from: a */
            public void onScaleChanged(@NonNull FloatingActionButton floatingActionButton) {
                BottomAppBar.this.i.setInterpolation(floatingActionButton.getVisibility() == 0 ? floatingActionButton.getScaleY() : 0.0f);
            }

            /* renamed from: b */
            public void onTranslationChanged(@NonNull FloatingActionButton floatingActionButton) {
                float translationX = floatingActionButton.getTranslationX();
                if (BottomAppBar.this.getTopEdgeTreatment().getHorizontalOffset() != translationX) {
                    BottomAppBar.this.getTopEdgeTreatment().a(translationX);
                    BottomAppBar.this.i.invalidateSelf();
                }
                float f = 0.0f;
                float max = Math.max(0.0f, -floatingActionButton.getTranslationY());
                if (BottomAppBar.this.getTopEdgeTreatment().a() != max) {
                    BottomAppBar.this.getTopEdgeTreatment().b(max);
                    BottomAppBar.this.i.invalidateSelf();
                }
                MaterialShapeDrawable materialShapeDrawable = BottomAppBar.this.i;
                if (floatingActionButton.getVisibility() == 0) {
                    f = floatingActionButton.getScaleY();
                }
                materialShapeDrawable.setInterpolation(f);
            }
        };
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.BottomAppBar, i, g, new int[0]);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.BottomAppBar_backgroundTint);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomAppBar_elevation, 0);
        this.l = obtainStyledAttributes.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.m = obtainStyledAttributes.getInt(R.styleable.BottomAppBar_fabAnimationMode, 0);
        this.n = obtainStyledAttributes.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
        this.o = obtainStyledAttributes.getBoolean(R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.p = obtainStyledAttributes.getBoolean(R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.q = obtainStyledAttributes.getBoolean(R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
        obtainStyledAttributes.recycle();
        this.h = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        this.i.setShapeAppearanceModel(ShapeAppearanceModel.builder().setTopEdge(new BottomAppBarTopEdgeTreatment(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0), obtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0), obtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0))).build());
        this.i.setShadowCompatibilityMode(2);
        this.i.setPaintStyle(Paint.Style.FILL);
        this.i.initializeElevationOverlay(context2);
        setElevation(dimensionPixelSize);
        DrawableCompat.setTintList(this.i, colorStateList);
        ViewCompat.setBackground(this, this.i);
        ViewUtils.doOnApplyWindowInsets(this, attributeSet, i, g, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.3
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            @NonNull
            public WindowInsetsCompat onApplyWindowInsets(View view, @NonNull WindowInsetsCompat windowInsetsCompat, @NonNull ViewUtils.RelativePadding relativePadding) {
                boolean z;
                if (BottomAppBar.this.o) {
                    BottomAppBar.this.x = windowInsetsCompat.getSystemWindowInsetBottom();
                }
                boolean z2 = false;
                if (BottomAppBar.this.p) {
                    z = BottomAppBar.this.z != windowInsetsCompat.getSystemWindowInsetLeft();
                    BottomAppBar.this.z = windowInsetsCompat.getSystemWindowInsetLeft();
                } else {
                    z = false;
                }
                if (BottomAppBar.this.q) {
                    if (BottomAppBar.this.y != windowInsetsCompat.getSystemWindowInsetRight()) {
                        z2 = true;
                    }
                    BottomAppBar.this.y = windowInsetsCompat.getSystemWindowInsetRight();
                }
                if (z || z2) {
                    BottomAppBar.this.i();
                    BottomAppBar.this.j();
                    BottomAppBar.this.k();
                }
                return windowInsetsCompat;
            }
        });
    }

    public int getFabAlignmentMode() {
        return this.l;
    }

    public void setFabAlignmentMode(int i) {
        setFabAlignmentModeAndReplaceMenu(i, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int i, @MenuRes int i2) {
        this.t = i2;
        this.u = true;
        a(i, this.v);
        b(i);
        this.l = i;
    }

    public int getFabAnimationMode() {
        return this.m;
    }

    public void setFabAnimationMode(int i) {
        this.m = i;
    }

    public void setBackgroundTint(@Nullable ColorStateList colorStateList) {
        DrawableCompat.setTintList(this.i, colorStateList);
    }

    @Nullable
    public ColorStateList getBackgroundTint() {
        return this.i.getTintList();
    }

    public float getFabCradleMargin() {
        return getTopEdgeTreatment().b();
    }

    public void setFabCradleMargin(@Dimension float f) {
        if (f != getFabCradleMargin()) {
            getTopEdgeTreatment().c(f);
            this.i.invalidateSelf();
        }
    }

    @Dimension
    public float getFabCradleRoundedCornerRadius() {
        return getTopEdgeTreatment().c();
    }

    public void setFabCradleRoundedCornerRadius(@Dimension float f) {
        if (f != getFabCradleRoundedCornerRadius()) {
            getTopEdgeTreatment().d(f);
            this.i.invalidateSelf();
        }
    }

    @Dimension
    public float getCradleVerticalOffset() {
        return getTopEdgeTreatment().a();
    }

    public void setCradleVerticalOffset(@Dimension float f) {
        if (f != getCradleVerticalOffset()) {
            getTopEdgeTreatment().b(f);
            this.i.invalidateSelf();
            j();
        }
    }

    public boolean getHideOnScroll() {
        return this.n;
    }

    public void setHideOnScroll(boolean z) {
        this.n = z;
    }

    public void performHide() {
        getBehavior().slideDown(this);
    }

    public void performShow() {
        getBehavior().slideUp(this);
    }

    @Override // android.view.View
    public void setElevation(float f) {
        this.i.setElevation(f);
        getBehavior().setAdditionalHiddenOffsetY(this, this.i.getShadowRadius() - this.i.getShadowOffsetY());
    }

    public void replaceMenu(@MenuRes int i) {
        if (i != 0) {
            this.t = 0;
            getMenu().clear();
            inflateMenu(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        ArrayList<a> arrayList;
        int i = this.r;
        this.r = i + 1;
        if (i == 0 && (arrayList = this.s) != null) {
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().a(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        ArrayList<a> arrayList;
        int i = this.r - 1;
        this.r = i;
        if (i == 0 && (arrayList = this.s) != null) {
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().b(this);
            }
        }
    }

    boolean a(@Px int i) {
        float f = i;
        if (f == getTopEdgeTreatment().getFabDiameter()) {
            return false;
        }
        getTopEdgeTreatment().setFabDiameter(f);
        this.i.invalidateSelf();
        return true;
    }

    void setFabCornerSize(@Dimension float f) {
        if (f != getTopEdgeTreatment().getFabCornerRadius()) {
            getTopEdgeTreatment().setFabCornerSize(f);
            this.i.invalidateSelf();
        }
    }

    private void b(int i) {
        if (this.l != i && ViewCompat.isLaidOut(this)) {
            Animator animator = this.j;
            if (animator != null) {
                animator.cancel();
            }
            ArrayList arrayList = new ArrayList();
            if (this.m == 1) {
                a(i, arrayList);
            } else {
                createFabDefaultXAnimation(i, arrayList);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            this.j = animatorSet;
            this.j.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    BottomAppBar.this.d();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    BottomAppBar.this.e();
                    BottomAppBar.this.j = null;
                }
            });
            this.j.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public FloatingActionButton f() {
        View g2 = g();
        if (g2 instanceof FloatingActionButton) {
            return (FloatingActionButton) g2;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View g() {
        /*
            r4 = this;
            android.view.ViewParent r0 = r4.getParent()
            boolean r0 = r0 instanceof androidx.coordinatorlayout.widget.CoordinatorLayout
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r0 = r4.getParent()
            androidx.coordinatorlayout.widget.CoordinatorLayout r0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) r0
            java.util.List r0 = r0.getDependents(r4)
            java.util.Iterator r0 = r0.iterator()
        L_0x0018:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x002d
            java.lang.Object r2 = r0.next()
            android.view.View r2 = (android.view.View) r2
            boolean r3 = r2 instanceof com.google.android.material.floatingactionbutton.FloatingActionButton
            if (r3 != 0) goto L_0x002c
            boolean r3 = r2 instanceof com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
            if (r3 == 0) goto L_0x0018
        L_0x002c:
            return r2
        L_0x002d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.g():android.view.View");
    }

    private boolean h() {
        FloatingActionButton f = f();
        return f != null && f.isOrWillBeShown();
    }

    protected void createFabDefaultXAnimation(final int i, List<Animator> list) {
        FloatingActionButton f = f();
        if (f != null && !f.isOrWillBeHidden()) {
            d();
            f.hide(new FloatingActionButton.OnVisibilityChangedListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.5
                @Override // com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener
                public void onHidden(@NonNull FloatingActionButton floatingActionButton) {
                    floatingActionButton.setTranslationX(BottomAppBar.this.c(i));
                    floatingActionButton.show(new FloatingActionButton.OnVisibilityChangedListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.5.1
                        @Override // com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener
                        public void onShown(FloatingActionButton floatingActionButton2) {
                            BottomAppBar.this.e();
                        }
                    });
                }
            });
        }
    }

    private void a(int i, @NonNull List<Animator> list) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(f(), "translationX", c(i));
        ofFloat.setDuration(300L);
        list.add(ofFloat);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, boolean z) {
        if (!ViewCompat.isLaidOut(this)) {
            this.u = false;
            replaceMenu(this.t);
            return;
        }
        Animator animator = this.k;
        if (animator != null) {
            animator.cancel();
        }
        ArrayList arrayList = new ArrayList();
        if (!h()) {
            i = 0;
            z = false;
        }
        a(i, z, arrayList);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        this.k = animatorSet;
        this.k.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                BottomAppBar.this.d();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                BottomAppBar.this.e();
                BottomAppBar.this.u = false;
                BottomAppBar.this.k = null;
            }
        });
        this.k.start();
    }

    private void a(final int i, final boolean z, @NonNull List<Animator> list) {
        final ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            Animator ofFloat = ObjectAnimator.ofFloat(actionMenuView, "alpha", 1.0f);
            if (Math.abs(actionMenuView.getTranslationX() - getActionMenuViewTranslationX(actionMenuView, i, z)) > 1.0f) {
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(actionMenuView, "alpha", 0.0f);
                ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.7
                    public boolean a;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        this.a = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (!this.a) {
                            boolean z2 = BottomAppBar.this.t != 0;
                            BottomAppBar bottomAppBar = BottomAppBar.this;
                            bottomAppBar.replaceMenu(bottomAppBar.t);
                            BottomAppBar.this.a(actionMenuView, i, z, z2);
                        }
                    }
                });
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(150L);
                animatorSet.playSequentially(ofFloat2, ofFloat);
                list.add(animatorSet);
            } else if (actionMenuView.getAlpha() < 1.0f) {
                list.add(ofFloat);
            }
        }
    }

    private float getFabTranslationY() {
        return -getTopEdgeTreatment().a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float c(int i) {
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i2 = 1;
        if (i != 1) {
            return 0.0f;
        }
        int measuredWidth = (getMeasuredWidth() / 2) - (this.h + (isLayoutRtl ? this.z : this.y));
        if (isLayoutRtl) {
            i2 = -1;
        }
        return measuredWidth * i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFabTranslationX() {
        return c(this.l);
    }

    @Nullable
    private ActionMenuView getActionMenuView() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                return (ActionMenuView) childAt;
            }
        }
        return null;
    }

    private void a(@NonNull ActionMenuView actionMenuView, int i, boolean z) {
        a(actionMenuView, i, z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull final ActionMenuView actionMenuView, final int i, final boolean z, boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.google.android.material.bottomappbar.BottomAppBar.8
            @Override // java.lang.Runnable
            public void run() {
                ActionMenuView actionMenuView2 = actionMenuView;
                actionMenuView2.setTranslationX(BottomAppBar.this.getActionMenuViewTranslationX(actionMenuView2, i, z));
            }
        };
        if (z2) {
            actionMenuView.post(runnable);
        } else {
            runnable.run();
        }
    }

    protected int getActionMenuViewTranslationX(@NonNull ActionMenuView actionMenuView, int i, boolean z) {
        if (i != 1 || !z) {
            return 0;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int measuredWidth = isLayoutRtl ? getMeasuredWidth() : 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if ((childAt.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) childAt.getLayoutParams()).gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 8388611) {
                if (isLayoutRtl) {
                    measuredWidth = Math.min(measuredWidth, childAt.getLeft());
                } else {
                    measuredWidth = Math.max(measuredWidth, childAt.getRight());
                }
            }
        }
        return measuredWidth - ((isLayoutRtl ? actionMenuView.getRight() : actionMenuView.getLeft()) + (isLayoutRtl ? this.y : -this.z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        Animator animator = this.k;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.j;
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            i();
            j();
        }
        k();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.i.getShapeAppearanceModel().getTopEdge();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        getTopEdgeTreatment().a(getFabTranslationX());
        View g2 = g();
        this.i.setInterpolation((!this.v || !h()) ? 0.0f : 1.0f);
        if (g2 != null) {
            g2.setTranslationY(getFabTranslationY());
            g2.setTranslationX(getFabTranslationX());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null && this.k == null) {
            actionMenuView.setAlpha(1.0f);
            if (!h()) {
                a(actionMenuView, 0, false);
            } else {
                a(actionMenuView, this.l, this.v);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull FloatingActionButton floatingActionButton) {
        floatingActionButton.addOnHideAnimationListener(this.e);
        floatingActionButton.addOnShowAnimationListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomappbar.BottomAppBar.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                BottomAppBar.this.e.onAnimationStart(animator);
                FloatingActionButton f = BottomAppBar.this.f();
                if (f != null) {
                    f.setTranslationX(BottomAppBar.this.getFabTranslationX());
                }
            }
        });
        floatingActionButton.addTransformationCallback(this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBottomInset() {
        return this.x;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRightInset() {
        return this.y;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLeftInset() {
        return this.z;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public Behavior getBehavior() {
        if (this.w == null) {
            this.w = new Behavior();
        }
        return this.w;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.i);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    /* loaded from: classes2.dex */
    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private WeakReference<BottomAppBar> b;
        private int c;
        private final View.OnLayoutChangeListener d = new View.OnLayoutChangeListener() { // from class: com.google.android.material.bottomappbar.BottomAppBar.Behavior.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                BottomAppBar bottomAppBar = (BottomAppBar) Behavior.this.b.get();
                if (bottomAppBar == null || !(view instanceof FloatingActionButton)) {
                    view.removeOnLayoutChangeListener(this);
                    return;
                }
                FloatingActionButton floatingActionButton = (FloatingActionButton) view;
                floatingActionButton.getMeasuredContentRect(Behavior.this.a);
                int height = Behavior.this.a.height();
                bottomAppBar.a(height);
                bottomAppBar.setFabCornerSize(floatingActionButton.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(Behavior.this.a)));
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                if (Behavior.this.c == 0) {
                    layoutParams.bottomMargin = bottomAppBar.getBottomInset() + (bottomAppBar.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin) - ((floatingActionButton.getMeasuredHeight() - height) / 2));
                    layoutParams.leftMargin = bottomAppBar.getLeftInset();
                    layoutParams.rightMargin = bottomAppBar.getRightInset();
                    if (ViewUtils.isLayoutRtl(floatingActionButton)) {
                        layoutParams.leftMargin += bottomAppBar.h;
                    } else {
                        layoutParams.rightMargin += bottomAppBar.h;
                    }
                }
            }
        };
        @NonNull
        private final Rect a = new Rect();

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomAppBar bottomAppBar, int i) {
            this.b = new WeakReference<>(bottomAppBar);
            View g = bottomAppBar.g();
            if (g != null && !ViewCompat.isLaidOut(g)) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) g.getLayoutParams();
                layoutParams.anchorGravity = 49;
                this.c = layoutParams.bottomMargin;
                if (g instanceof FloatingActionButton) {
                    FloatingActionButton floatingActionButton = (FloatingActionButton) g;
                    floatingActionButton.addOnLayoutChangeListener(this.d);
                    bottomAppBar.a(floatingActionButton);
                }
                bottomAppBar.j();
            }
            coordinatorLayout.onLayoutChild(bottomAppBar, i);
            return super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) bottomAppBar, i);
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomAppBar bottomAppBar, @NonNull View view, @NonNull View view2, int i, int i2) {
            return bottomAppBar.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) bottomAppBar, view, view2, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    @NonNull
    public Parcelable onSaveInstanceState() {
        b bVar = new b(super.onSaveInstanceState());
        bVar.a = this.l;
        bVar.b = this.v;
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof b)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        b bVar = (b) parcelable;
        super.onRestoreInstanceState(bVar.getSuperState());
        this.l = bVar.a;
        this.v = bVar.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class b extends AbsSavedState {
        public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator<b>() { // from class: com.google.android.material.bottomappbar.BottomAppBar.b.1
            @NonNull
            /* renamed from: a */
            public b createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new b(parcel, classLoader);
            }

            @Nullable
            /* renamed from: a */
            public b createFromParcel(@NonNull Parcel parcel) {
                return new b(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public b[] newArray(int i) {
                return new b[i];
            }
        };
        int a;
        boolean b;

        public b(Parcelable parcelable) {
            super(parcelable);
        }

        public b(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
            this.b = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b ? 1 : 0);
        }
    }
}
