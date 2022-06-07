package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;

/* loaded from: classes2.dex */
public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    private int f;
    private final a g;
    @NonNull
    private final f h;
    @NonNull
    private final f i;
    private final f j;
    private final f k;
    private final int l;
    private int m;
    private int n;
    @NonNull
    private final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> o;
    @NonNull
    protected ColorStateList originalTextCsl;
    private boolean p;
    private boolean q;
    private boolean r;
    private static final int e = R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
    static final Property<View, Float> a = new Property<View, Float>(Float.class, "width") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.4
        /* renamed from: a */
        public void set(@NonNull View view, @NonNull Float f) {
            view.getLayoutParams().width = f.intValue();
            view.requestLayout();
        }

        @NonNull
        /* renamed from: a */
        public Float get(@NonNull View view) {
            return Float.valueOf(view.getLayoutParams().width);
        }
    };
    static final Property<View, Float> b = new Property<View, Float>(Float.class, "height") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.5
        /* renamed from: a */
        public void set(@NonNull View view, @NonNull Float f) {
            view.getLayoutParams().height = f.intValue();
            view.requestLayout();
        }

        @NonNull
        /* renamed from: a */
        public Float get(@NonNull View view) {
            return Float.valueOf(view.getLayoutParams().height);
        }
    };
    static final Property<View, Float> c = new Property<View, Float>(Float.class, "paddingStart") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
        /* renamed from: a */
        public void set(@NonNull View view, @NonNull Float f) {
            ViewCompat.setPaddingRelative(view, f.intValue(), view.getPaddingTop(), ViewCompat.getPaddingEnd(view), view.getPaddingBottom());
        }

        @NonNull
        /* renamed from: a */
        public Float get(@NonNull View view) {
            return Float.valueOf(ViewCompat.getPaddingStart(view));
        }
    };
    static final Property<View, Float> d = new Property<View, Float>(Float.class, "paddingEnd") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.7
        /* renamed from: a */
        public void set(@NonNull View view, @NonNull Float f) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), view.getPaddingTop(), f.intValue(), view.getPaddingBottom());
        }

        @NonNull
        /* renamed from: a */
        public Float get(@NonNull View view) {
            return Float.valueOf(ViewCompat.getPaddingEnd(view));
        }
    };

    /* loaded from: classes2.dex */
    public static abstract class OnChangedCallback {
        public void onExtended(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onHidden(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onShown(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onShrunken(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface d {
        int a();

        int b();

        int c();

        int d();

        ViewGroup.LayoutParams e();
    }

    public ExtendedFloatingActionButton(@NonNull Context context) {
        this(context, null);
    }

    public ExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.extendedFloatingActionButtonStyle);
    }

    public ExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, e), attributeSet, i);
        this.f = 0;
        this.g = new a();
        this.j = new c(this.g);
        this.k = new b(this.g);
        this.p = true;
        this.q = false;
        this.r = false;
        Context context2 = getContext();
        this.o = new ExtendedFloatingActionButtonBehavior(context2, attributeSet);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.ExtendedFloatingActionButton, i, e, new int[0]);
        MotionSpec createFromAttribute = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, R.styleable.ExtendedFloatingActionButton_showMotionSpec);
        MotionSpec createFromAttribute2 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, R.styleable.ExtendedFloatingActionButton_hideMotionSpec);
        MotionSpec createFromAttribute3 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, R.styleable.ExtendedFloatingActionButton_extendMotionSpec);
        MotionSpec createFromAttribute4 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, R.styleable.ExtendedFloatingActionButton_shrinkMotionSpec);
        this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ExtendedFloatingActionButton_collapsedSize, -1);
        this.m = ViewCompat.getPaddingStart(this);
        this.n = ViewCompat.getPaddingEnd(this);
        a aVar = new a();
        this.i = new a(aVar, new d() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int a() {
                return (ExtendedFloatingActionButton.this.getMeasuredWidth() - (ExtendedFloatingActionButton.this.getCollapsedPadding() * 2)) + ExtendedFloatingActionButton.this.m + ExtendedFloatingActionButton.this.n;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int b() {
                return ExtendedFloatingActionButton.this.getMeasuredHeight();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int c() {
                return ExtendedFloatingActionButton.this.m;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int d() {
                return ExtendedFloatingActionButton.this.n;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public ViewGroup.LayoutParams e() {
                return new ViewGroup.LayoutParams(-2, -2);
            }
        }, true);
        this.h = new a(aVar, new d() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.2
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int a() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int b() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int c() {
                return ExtendedFloatingActionButton.this.getCollapsedPadding();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public int d() {
                return ExtendedFloatingActionButton.this.getCollapsedPadding();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.d
            public ViewGroup.LayoutParams e() {
                return new ViewGroup.LayoutParams(a(), b());
            }
        }, false);
        this.j.a(createFromAttribute);
        this.k.a(createFromAttribute2);
        this.i.a(createFromAttribute3);
        this.h.a(createFromAttribute4);
        obtainStyledAttributes.recycle();
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context2, attributeSet, i, e, ShapeAppearanceModel.PILL).build());
        a();
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        super.setTextColor(i);
        a();
    }

    @Override // android.widget.TextView
    public void setTextColor(@NonNull ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        a();
    }

    private void a() {
        this.originalTextCsl = getTextColors();
    }

    public void silentlyUpdateTextColor(@NonNull ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
    }

    @Override // com.google.android.material.button.MaterialButton, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.p && TextUtils.isEmpty(getText()) && getIcon() != null) {
            this.p = false;
            this.h.g();
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.o;
    }

    public void setExtended(boolean z) {
        if (this.p != z) {
            f fVar = z ? this.i : this.h;
            if (!fVar.i()) {
                fVar.g();
            }
        }
    }

    public final boolean isExtended() {
        return this.p;
    }

    public void setAnimateShowBeforeLayout(boolean z) {
        this.r = z;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        if (this.p && !this.q) {
            this.m = i;
            this.n = i3;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        if (this.p && !this.q) {
            this.m = ViewCompat.getPaddingStart(this);
            this.n = ViewCompat.getPaddingEnd(this);
        }
    }

    public void addOnShowAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.j.a(animatorListener);
    }

    public void removeOnShowAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.j.b(animatorListener);
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.k.a(animatorListener);
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.k.b(animatorListener);
    }

    public void addOnShrinkAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.h.a(animatorListener);
    }

    public void removeOnShrinkAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.h.b(animatorListener);
    }

    public void addOnExtendAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.i.a(animatorListener);
    }

    public void removeOnExtendAnimationListener(@NonNull Animator.AnimatorListener animatorListener) {
        this.i.b(animatorListener);
    }

    public void hide() {
        a(this.k, (OnChangedCallback) null);
    }

    public void hide(@NonNull OnChangedCallback onChangedCallback) {
        a(this.k, onChangedCallback);
    }

    public void show() {
        a(this.j, (OnChangedCallback) null);
    }

    public void show(@NonNull OnChangedCallback onChangedCallback) {
        a(this.j, onChangedCallback);
    }

    public void extend() {
        a(this.i, (OnChangedCallback) null);
    }

    public void extend(@NonNull OnChangedCallback onChangedCallback) {
        a(this.i, onChangedCallback);
    }

    public void shrink() {
        a(this.h, (OnChangedCallback) null);
    }

    public void shrink(@NonNull OnChangedCallback onChangedCallback) {
        a(this.h, onChangedCallback);
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        return this.j.c();
    }

    public void setShowMotionSpec(@Nullable MotionSpec motionSpec) {
        this.j.a(motionSpec);
    }

    public void setShowMotionSpecResource(@AnimatorRes int i) {
        setShowMotionSpec(MotionSpec.createFromResource(getContext(), i));
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        return this.k.c();
    }

    public void setHideMotionSpec(@Nullable MotionSpec motionSpec) {
        this.k.a(motionSpec);
    }

    public void setHideMotionSpecResource(@AnimatorRes int i) {
        setHideMotionSpec(MotionSpec.createFromResource(getContext(), i));
    }

    @Nullable
    public MotionSpec getExtendMotionSpec() {
        return this.i.c();
    }

    public void setExtendMotionSpec(@Nullable MotionSpec motionSpec) {
        this.i.a(motionSpec);
    }

    public void setExtendMotionSpecResource(@AnimatorRes int i) {
        setExtendMotionSpec(MotionSpec.createFromResource(getContext(), i));
    }

    @Nullable
    public MotionSpec getShrinkMotionSpec() {
        return this.h.c();
    }

    public void setShrinkMotionSpec(@Nullable MotionSpec motionSpec) {
        this.h.a(motionSpec);
    }

    public void setShrinkMotionSpecResource(@AnimatorRes int i) {
        setShrinkMotionSpec(MotionSpec.createFromResource(getContext(), i));
    }

    public void a(@NonNull final f fVar, @Nullable final OnChangedCallback onChangedCallback) {
        if (!fVar.i()) {
            if (!d()) {
                fVar.g();
                fVar.a(onChangedCallback);
                return;
            }
            measure(0, 0);
            AnimatorSet f = fVar.f();
            f.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.3
                private boolean d;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    fVar.a(animator);
                    this.d = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.d = true;
                    fVar.e();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    fVar.d();
                    if (!this.d) {
                        fVar.a(onChangedCallback);
                    }
                }
            });
            for (Animator.AnimatorListener animatorListener : fVar.b()) {
                f.addListener(animatorListener);
            }
            f.start();
        }
    }

    public boolean b() {
        return getVisibility() != 0 ? this.f == 2 : this.f != 1;
    }

    public boolean c() {
        return getVisibility() == 0 ? this.f == 1 : this.f != 2;
    }

    private boolean d() {
        return (ViewCompat.isLaidOut(this) || (!b() && this.r)) && !isInEditMode();
    }

    @VisibleForTesting
    int getCollapsedSize() {
        int i = this.l;
        return i < 0 ? (Math.min(ViewCompat.getPaddingStart(this), ViewCompat.getPaddingEnd(this)) * 2) + getIconSize() : i;
    }

    int getCollapsedPadding() {
        return (getCollapsedSize() - getIconSize()) / 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior<T> {
        private Rect a;
        @Nullable
        private OnChangedCallback b;
        @Nullable
        private OnChangedCallback c;
        private boolean d;
        private boolean e;

        public ExtendedFloatingActionButtonBehavior() {
            this.d = false;
            this.e = true;
        }

        public ExtendedFloatingActionButtonBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.d = obtainStyledAttributes.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
            this.e = obtainStyledAttributes.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, true);
            obtainStyledAttributes.recycle();
        }

        public void setAutoHideEnabled(boolean z) {
            this.d = z;
        }

        public boolean isAutoHideEnabled() {
            return this.d;
        }

        public void setAutoShrinkEnabled(boolean z) {
            this.e = z;
        }

        public boolean isAutoShrinkEnabled() {
            return this.e;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton, @NonNull Rect rect) {
            return super.getInsetDodgeRect(coordinatorLayout, (CoordinatorLayout) extendedFloatingActionButton, rect);
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                a(coordinatorLayout, (AppBarLayout) view, extendedFloatingActionButton);
                return false;
            } else if (!a(view)) {
                return false;
            } else {
                b(view, extendedFloatingActionButton);
                return false;
            }
        }

        private static boolean a(@NonNull View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                return ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        private boolean a(@NonNull View view, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton) {
            return (this.d || this.e) && ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams()).getAnchorId() == view.getId();
        }

        private boolean a(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!a(appBarLayout, extendedFloatingActionButton)) {
                return false;
            }
            if (this.a == null) {
                this.a = new Rect();
            }
            Rect rect = this.a;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                shrinkOrHide(extendedFloatingActionButton);
                return true;
            }
            extendOrShow(extendedFloatingActionButton);
            return true;
        }

        private boolean b(@NonNull View view, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!a(view, extendedFloatingActionButton)) {
                return false;
            }
            if (view.getTop() < (extendedFloatingActionButton.getHeight() / 2) + ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams()).topMargin) {
                shrinkOrHide(extendedFloatingActionButton);
                return true;
            }
            extendOrShow(extendedFloatingActionButton);
            return true;
        }

        protected void shrinkOrHide(@NonNull ExtendedFloatingActionButton extendedFloatingActionButton) {
            extendedFloatingActionButton.a(this.e ? extendedFloatingActionButton.h : extendedFloatingActionButton.k, this.e ? this.c : this.b);
        }

        protected void extendOrShow(@NonNull ExtendedFloatingActionButton extendedFloatingActionButton) {
            extendedFloatingActionButton.a(this.e ? extendedFloatingActionButton.i : extendedFloatingActionButton.j, this.e ? this.c : this.b);
        }

        public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ExtendedFloatingActionButton extendedFloatingActionButton, int i) {
            List<View> dependencies = coordinatorLayout.getDependencies(extendedFloatingActionButton);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = dependencies.get(i2);
                if (!(view instanceof AppBarLayout)) {
                    if (a(view) && b(view, extendedFloatingActionButton)) {
                        break;
                    }
                } else if (a(coordinatorLayout, (AppBarLayout) view, extendedFloatingActionButton)) {
                    break;
                }
            }
            coordinatorLayout.onLayoutChild(extendedFloatingActionButton, i);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class a extends b {
        private final d b;
        private final boolean c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(a aVar, d dVar, boolean z) {
            super(r1, aVar);
            ExtendedFloatingActionButton.this = r1;
            this.b = dVar;
            this.c = z;
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void g() {
            ExtendedFloatingActionButton.this.p = this.c;
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.b.e().width;
                layoutParams.height = this.b.e().height;
                ViewCompat.setPaddingRelative(ExtendedFloatingActionButton.this, this.b.c(), ExtendedFloatingActionButton.this.getPaddingTop(), this.b.d(), ExtendedFloatingActionButton.this.getPaddingBottom());
                ExtendedFloatingActionButton.this.requestLayout();
            }
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void a(@Nullable OnChangedCallback onChangedCallback) {
            if (onChangedCallback != null) {
                if (this.c) {
                    onChangedCallback.onExtended(ExtendedFloatingActionButton.this);
                } else {
                    onChangedCallback.onShrunken(ExtendedFloatingActionButton.this);
                }
            }
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public int h() {
            return this.c ? R.animator.mtrl_extended_fab_change_size_expand_motion_spec : R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        @NonNull
        public AnimatorSet f() {
            MotionSpec a = a();
            if (a.hasPropertyValues("width")) {
                PropertyValuesHolder[] propertyValues = a.getPropertyValues("width");
                propertyValues[0].setFloatValues(ExtendedFloatingActionButton.this.getWidth(), this.b.a());
                a.setPropertyValues("width", propertyValues);
            }
            if (a.hasPropertyValues("height")) {
                PropertyValuesHolder[] propertyValues2 = a.getPropertyValues("height");
                propertyValues2[0].setFloatValues(ExtendedFloatingActionButton.this.getHeight(), this.b.b());
                a.setPropertyValues("height", propertyValues2);
            }
            if (a.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] propertyValues3 = a.getPropertyValues("paddingStart");
                propertyValues3[0].setFloatValues(ViewCompat.getPaddingStart(ExtendedFloatingActionButton.this), this.b.c());
                a.setPropertyValues("paddingStart", propertyValues3);
            }
            if (a.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] propertyValues4 = a.getPropertyValues("paddingEnd");
                propertyValues4[0].setFloatValues(ViewCompat.getPaddingEnd(ExtendedFloatingActionButton.this), this.b.d());
                a.setPropertyValues("paddingEnd", propertyValues4);
            }
            if (a.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] propertyValues5 = a.getPropertyValues("labelOpacity");
                float f = 0.0f;
                float f2 = this.c ? 0.0f : 1.0f;
                if (this.c) {
                    f = 1.0f;
                }
                propertyValues5[0].setFloatValues(f2, f);
                a.setPropertyValues("labelOpacity", propertyValues5);
            }
            return super.b(a);
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void a(Animator animator) {
            super.a(animator);
            ExtendedFloatingActionButton.this.p = this.c;
            ExtendedFloatingActionButton.this.q = true;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(true);
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void d() {
            super.d();
            ExtendedFloatingActionButton.this.q = false;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.b.e().width;
                layoutParams.height = this.b.e().height;
            }
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public boolean i() {
            return this.c == ExtendedFloatingActionButton.this.p || ExtendedFloatingActionButton.this.getIcon() == null || TextUtils.isEmpty(ExtendedFloatingActionButton.this.getText());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class c extends b {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(a aVar) {
            super(r1, aVar);
            ExtendedFloatingActionButton.this = r1;
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void g() {
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.setAlpha(1.0f);
            ExtendedFloatingActionButton.this.setScaleY(1.0f);
            ExtendedFloatingActionButton.this.setScaleX(1.0f);
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void a(@Nullable OnChangedCallback onChangedCallback) {
            if (onChangedCallback != null) {
                onChangedCallback.onShown(ExtendedFloatingActionButton.this);
            }
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public int h() {
            return R.animator.mtrl_extended_fab_show_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void a(Animator animator) {
            super.a(animator);
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.f = 2;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void d() {
            super.d();
            ExtendedFloatingActionButton.this.f = 0;
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public boolean i() {
            return ExtendedFloatingActionButton.this.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class b extends b {
        private boolean b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(a aVar) {
            super(r1, aVar);
            ExtendedFloatingActionButton.this = r1;
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void g() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public void a(@Nullable OnChangedCallback onChangedCallback) {
            if (onChangedCallback != null) {
                onChangedCallback.onHidden(ExtendedFloatingActionButton.this);
            }
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public boolean i() {
            return ExtendedFloatingActionButton.this.c();
        }

        @Override // com.google.android.material.floatingactionbutton.f
        public int h() {
            return R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void a(Animator animator) {
            super.a(animator);
            this.b = false;
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.f = 1;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void e() {
            super.e();
            this.b = true;
        }

        @Override // com.google.android.material.floatingactionbutton.b, com.google.android.material.floatingactionbutton.f
        public void d() {
            super.d();
            ExtendedFloatingActionButton.this.f = 0;
            if (!this.b) {
                ExtendedFloatingActionButton.this.setVisibility(8);
            }
        }
    }
}
