package com.google.android.material.snackbar;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.a;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    public static final int ANIMATION_MODE_FADE = 1;
    public static final int ANIMATION_MODE_SLIDE = 0;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    @NonNull
    static final Handler a;
    private static final boolean c;
    private static final int[] d;
    private static final String e;
    @NonNull
    a.AbstractC0079a b;
    @NonNull
    private final ViewGroup f;
    private final Context g;
    @NonNull
    private final ContentViewCallback h;
    private int i;
    private boolean j;
    @Nullable
    private View k;
    private boolean l;
    private final ViewTreeObserver.OnGlobalLayoutListener m;
    @RequiresApi(29)
    private final Runnable n;
    @Nullable
    private Rect o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private List<BaseCallback<B>> u;
    private Behavior v;
    @NonNull
    protected final SnackbarBaseLayout view;
    @Nullable
    private final AccessibilityManager w;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface AnimationMode {
    }

    /* loaded from: classes2.dex */
    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes2.dex */
        public @interface DismissEvent {
        }

        public void onDismissed(B b, int i) {
        }

        public void onShown(B b) {
        }
    }

    @Deprecated
    /* loaded from: classes2.dex */
    public interface ContentViewCallback extends ContentViewCallback {
    }

    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface Duration {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    static {
        c = Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19;
        d = new int[]{R.attr.snackbarStyle};
        e = BaseTransientBottomBar.class.getSimpleName();
        a = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case 0:
                        ((BaseTransientBottomBar) message.obj).a();
                        return true;
                    case 1:
                        ((BaseTransientBottomBar) message.obj).a(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    protected BaseTransientBottomBar(@NonNull ViewGroup viewGroup, @NonNull View view, @NonNull ContentViewCallback contentViewCallback) {
        this(viewGroup.getContext(), viewGroup, view, contentViewCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseTransientBottomBar(@NonNull Context context, @NonNull ViewGroup viewGroup, @NonNull View view, @NonNull ContentViewCallback contentViewCallback) {
        this.l = false;
        this.m = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.11
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (BaseTransientBottomBar.this.l) {
                    BaseTransientBottomBar baseTransientBottomBar = BaseTransientBottomBar.this;
                    baseTransientBottomBar.t = baseTransientBottomBar.m();
                    BaseTransientBottomBar.this.g();
                }
            }
        };
        this.n = new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.12
            @Override // java.lang.Runnable
            public void run() {
                int l;
                if (BaseTransientBottomBar.this.view != null && BaseTransientBottomBar.this.g != null && (l = (BaseTransientBottomBar.this.l() - BaseTransientBottomBar.this.k()) + ((int) BaseTransientBottomBar.this.view.getTranslationY())) < BaseTransientBottomBar.this.s) {
                    ViewGroup.LayoutParams layoutParams = BaseTransientBottomBar.this.view.getLayoutParams();
                    if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                        Log.w(BaseTransientBottomBar.e, "Unable to apply gesture inset because layout params are not MarginLayoutParams");
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin += BaseTransientBottomBar.this.s - l;
                    BaseTransientBottomBar.this.view.requestLayout();
                }
            }
        };
        this.b = new a.AbstractC0079a() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.15
            @Override // com.google.android.material.snackbar.a.AbstractC0079a
            public void a() {
                BaseTransientBottomBar.a.sendMessage(BaseTransientBottomBar.a.obtainMessage(0, BaseTransientBottomBar.this));
            }

            @Override // com.google.android.material.snackbar.a.AbstractC0079a
            public void a(int i) {
                BaseTransientBottomBar.a.sendMessage(BaseTransientBottomBar.a.obtainMessage(1, i, 0, BaseTransientBottomBar.this));
            }
        };
        if (viewGroup == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (view == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback != null) {
            this.f = viewGroup;
            this.h = contentViewCallback;
            this.g = context;
            ThemeEnforcement.checkAppCompatTheme(context);
            this.view = (SnackbarBaseLayout) LayoutInflater.from(context).inflate(getSnackbarBaseLayoutResId(), this.f, false);
            if (view instanceof SnackbarContentLayout) {
                ((SnackbarContentLayout) view).a(this.view.getActionTextColorAlpha());
            }
            this.view.addView(view);
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                this.o = new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            }
            ViewCompat.setAccessibilityLiveRegion(this.view, 1);
            ViewCompat.setImportantForAccessibility(this.view, 1);
            ViewCompat.setFitsSystemWindows(this.view, true);
            ViewCompat.setOnApplyWindowInsetsListener(this.view, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.13
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                @NonNull
                public WindowInsetsCompat onApplyWindowInsets(View view2, @NonNull WindowInsetsCompat windowInsetsCompat) {
                    BaseTransientBottomBar.this.p = windowInsetsCompat.getSystemWindowInsetBottom();
                    BaseTransientBottomBar.this.q = windowInsetsCompat.getSystemWindowInsetLeft();
                    BaseTransientBottomBar.this.r = windowInsetsCompat.getSystemWindowInsetRight();
                    BaseTransientBottomBar.this.g();
                    return windowInsetsCompat;
                }
            });
            ViewCompat.setAccessibilityDelegate(this.view, new AccessibilityDelegateCompat() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.14
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(View view2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                }

                @Override // androidx.core.view.AccessibilityDelegateCompat
                public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                    if (i != 1048576) {
                        return super.performAccessibilityAction(view2, i, bundle);
                    }
                    BaseTransientBottomBar.this.dismiss();
                    return true;
                }
            });
            this.w = (AccessibilityManager) context.getSystemService("accessibility");
        } else {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams) || this.o == null) {
            Log.w(e, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.bottomMargin = this.o.bottom + (this.k != null ? this.t : this.p);
        marginLayoutParams.leftMargin = this.o.left + this.q;
        marginLayoutParams.rightMargin = this.o.right + this.r;
        this.view.requestLayout();
        if (Build.VERSION.SDK_INT >= 29 && h()) {
            this.view.removeCallbacks(this.n);
            this.view.post(this.n);
        }
    }

    private boolean h() {
        return this.s > 0 && !this.j && i();
    }

    private boolean i() {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        return (layoutParams instanceof CoordinatorLayout.LayoutParams) && (((CoordinatorLayout.LayoutParams) layoutParams).getBehavior() instanceof SwipeDismissBehavior);
    }

    @LayoutRes
    protected int getSnackbarBaseLayoutResId() {
        return hasSnackbarStyleAttr() ? R.layout.mtrl_layout_snackbar : R.layout.design_layout_snackbar;
    }

    protected boolean hasSnackbarStyleAttr() {
        TypedArray obtainStyledAttributes = this.g.obtainStyledAttributes(d);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        return resourceId != -1;
    }

    @NonNull
    public B setDuration(int i) {
        this.i = i;
        return this;
    }

    public int getDuration() {
        return this.i;
    }

    @NonNull
    public B setGestureInsetBottomIgnored(boolean z) {
        this.j = z;
        return this;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.j;
    }

    public int getAnimationMode() {
        return this.view.getAnimationMode();
    }

    @NonNull
    public B setAnimationMode(int i) {
        this.view.setAnimationMode(i);
        return this;
    }

    @Nullable
    public View getAnchorView() {
        return this.k;
    }

    @NonNull
    public B setAnchorView(@Nullable View view) {
        ViewUtils.removeOnGlobalLayoutListener(this.k, this.m);
        this.k = view;
        ViewUtils.addOnGlobalLayoutListener(this.k, this.m);
        return this;
    }

    @NonNull
    public B setAnchorView(@IdRes int i) {
        View findViewById = this.f.findViewById(i);
        if (findViewById != null) {
            return setAnchorView(findViewById);
        }
        throw new IllegalArgumentException("Unable to find anchor view with id: " + i);
    }

    public boolean isAnchorViewLayoutListenerEnabled() {
        return this.l;
    }

    public void setAnchorViewLayoutListenerEnabled(boolean z) {
        this.l = z;
    }

    @NonNull
    public B setBehavior(Behavior behavior) {
        this.v = behavior;
        return this;
    }

    public Behavior getBehavior() {
        return this.v;
    }

    @NonNull
    public Context getContext() {
        return this.g;
    }

    @NonNull
    public View getView() {
        return this.view;
    }

    public void show() {
        a.a().a(getDuration(), this.b);
    }

    public void dismiss() {
        dispatchDismiss(3);
    }

    protected void dispatchDismiss(int i) {
        a.a().a(this.b, i);
    }

    @NonNull
    public B addCallback(@Nullable BaseCallback<B> baseCallback) {
        if (baseCallback == null) {
            return this;
        }
        if (this.u == null) {
            this.u = new ArrayList();
        }
        this.u.add(baseCallback);
        return this;
    }

    @NonNull
    public B removeCallback(@Nullable BaseCallback<B> baseCallback) {
        List<BaseCallback<B>> list;
        if (baseCallback == null || (list = this.u) == null) {
            return this;
        }
        list.remove(baseCallback);
        return this;
    }

    public boolean isShown() {
        return a.a().e(this.b);
    }

    public boolean isShownOrQueued() {
        return a.a().f(this.b);
    }

    @NonNull
    protected SwipeDismissBehavior<? extends View> getNewBehavior() {
        return new Behavior();
    }

    final void a() {
        this.view.setOnAttachStateChangeListener(new OnAttachStateChangeListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.16
            @Override // com.google.android.material.snackbar.BaseTransientBottomBar.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                WindowInsets rootWindowInsets;
                if (Build.VERSION.SDK_INT >= 29 && (rootWindowInsets = BaseTransientBottomBar.this.view.getRootWindowInsets()) != null) {
                    BaseTransientBottomBar.this.s = rootWindowInsets.getMandatorySystemGestureInsets().bottom;
                    BaseTransientBottomBar.this.g();
                }
            }

            @Override // com.google.android.material.snackbar.BaseTransientBottomBar.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                if (BaseTransientBottomBar.this.isShownOrQueued()) {
                    BaseTransientBottomBar.a.post(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.16.1
                        @Override // java.lang.Runnable
                        public void run() {
                            BaseTransientBottomBar.this.b(3);
                        }
                    });
                }
            }
        });
        if (this.view.getParent() == null) {
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                a((CoordinatorLayout.LayoutParams) layoutParams);
            }
            this.t = m();
            g();
            this.view.setVisibility(4);
            this.f.addView(this.view);
        }
        if (ViewCompat.isLaidOut(this.view)) {
            j();
        } else {
            this.view.setOnLayoutChangeListener(new OnLayoutChangeListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.17
                @Override // com.google.android.material.snackbar.BaseTransientBottomBar.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4) {
                    BaseTransientBottomBar.this.view.setOnLayoutChangeListener(null);
                    BaseTransientBottomBar.this.j();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (d()) {
            b();
            return;
        }
        if (this.view.getParent() != null) {
            this.view.setVisibility(0);
        }
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int k() {
        int[] iArr = new int[2];
        this.view.getLocationOnScreen(iArr);
        return iArr[1] + this.view.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(17)
    public int l() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.g.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void a(CoordinatorLayout.LayoutParams layoutParams) {
        SwipeDismissBehavior<? extends View> swipeDismissBehavior = this.v;
        if (swipeDismissBehavior == null) {
            swipeDismissBehavior = getNewBehavior();
        }
        if (swipeDismissBehavior instanceof Behavior) {
            ((Behavior) swipeDismissBehavior).a((BaseTransientBottomBar<?>) this);
        }
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.18
            @Override // com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener
            public void onDismiss(@NonNull View view) {
                if (view.getParent() != null) {
                    view.setVisibility(8);
                }
                BaseTransientBottomBar.this.dispatchDismiss(0);
            }

            @Override // com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener
            public void onDragStateChanged(int i) {
                switch (i) {
                    case 0:
                        a.a().d(BaseTransientBottomBar.this.b);
                        return;
                    case 1:
                    case 2:
                        a.a().c(BaseTransientBottomBar.this.b);
                        return;
                    default:
                        return;
                }
            }
        });
        layoutParams.setBehavior(swipeDismissBehavior);
        if (this.k == null) {
            layoutParams.insetEdge = 80;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int m() {
        View view = this.k;
        if (view == null) {
            return 0;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[1];
        int[] iArr2 = new int[2];
        this.f.getLocationOnScreen(iArr2);
        return (iArr2[1] + this.f.getHeight()) - i;
    }

    void b() {
        this.view.post(new Runnable() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.2
            @Override // java.lang.Runnable
            public void run() {
                if (BaseTransientBottomBar.this.view != null) {
                    if (BaseTransientBottomBar.this.view.getParent() != null) {
                        BaseTransientBottomBar.this.view.setVisibility(0);
                    }
                    if (BaseTransientBottomBar.this.view.getAnimationMode() == 1) {
                        BaseTransientBottomBar.this.n();
                    } else {
                        BaseTransientBottomBar.this.o();
                    }
                }
            }
        });
    }

    private void c(int i) {
        if (this.view.getAnimationMode() == 1) {
            d(i);
        } else {
            e(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        ValueAnimator a2 = a(0.0f, 1.0f);
        ValueAnimator b = b(0.8f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(a2, b);
        animatorSet.setDuration(150L);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.c();
            }
        });
        animatorSet.start();
    }

    private void d(final int i) {
        ValueAnimator a2 = a(1.0f, 0.0f);
        a2.setDuration(75L);
        a2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.b(i);
            }
        });
        a2.start();
    }

    private ValueAnimator a(float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                BaseTransientBottomBar.this.view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return ofFloat;
    }

    private ValueAnimator b(float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                BaseTransientBottomBar.this.view.setScaleX(floatValue);
                BaseTransientBottomBar.this.view.setScaleY(floatValue);
            }
        });
        return ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o() {
        final int p = p();
        if (c) {
            ViewCompat.offsetTopAndBottom(this.view, p);
        } else {
            this.view.setTranslationY(p);
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(p, 0);
        valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        valueAnimator.setDuration(250L);
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                BaseTransientBottomBar.this.h.animateContentIn(70, Opcodes.GETFIELD);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.c();
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.8
            private int c;

            {
                this.c = p;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator2) {
                int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.c) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, intValue - this.c);
                } else {
                    BaseTransientBottomBar.this.view.setTranslationY(intValue);
                }
                this.c = intValue;
            }
        });
        valueAnimator.start();
    }

    private void e(final int i) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, p());
        valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        valueAnimator.setDuration(250L);
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                BaseTransientBottomBar.this.h.animateContentOut(0, Opcodes.GETFIELD);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseTransientBottomBar.this.b(i);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.10
            private int b = 0;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator2) {
                int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.c) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, intValue - this.b);
                } else {
                    BaseTransientBottomBar.this.view.setTranslationY(intValue);
                }
                this.b = intValue;
            }
        });
        valueAnimator.start();
    }

    private int p() {
        int height = this.view.getHeight();
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? height + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin : height;
    }

    final void a(int i) {
        if (!d() || this.view.getVisibility() != 0) {
            b(i);
        } else {
            c(i);
        }
    }

    void c() {
        a.a().b(this.b);
        List<BaseCallback<B>> list = this.u;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.u.get(size).onShown(this);
            }
        }
    }

    void b(int i) {
        a.a().a(this.b);
        List<BaseCallback<B>> list = this.u;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.u.get(size).onDismissed(this, i);
            }
        }
        ViewParent parent = this.view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.view);
        }
    }

    boolean d() {
        AccessibilityManager accessibilityManager = this.w;
        if (accessibilityManager == null) {
            return true;
        }
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(1);
        return enabledAccessibilityServiceList != null && enabledAccessibilityServiceList.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public static class SnackbarBaseLayout extends FrameLayout {
        private static final View.OnTouchListener a = new View.OnTouchListener() { // from class: com.google.android.material.snackbar.BaseTransientBottomBar.SnackbarBaseLayout.1
            @Override // android.view.View.OnTouchListener
            @SuppressLint({"ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        };
        private OnLayoutChangeListener b;
        private OnAttachStateChangeListener c;
        private int d;
        private final float e;
        private final float f;
        private ColorStateList g;
        private PorterDuff.Mode h;

        /* JADX INFO: Access modifiers changed from: protected */
        public SnackbarBaseLayout(@NonNull Context context) {
            this(context, null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public SnackbarBaseLayout(@NonNull Context context, AttributeSet attributeSet) {
            super(MaterialThemeOverlay.wrap(context, attributeSet, 0, 0), attributeSet);
            Context context2 = getContext();
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
            if (obtainStyledAttributes.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, obtainStyledAttributes.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            this.d = obtainStyledAttributes.getInt(R.styleable.SnackbarLayout_animationMode, 0);
            this.e = obtainStyledAttributes.getFloat(R.styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
            setBackgroundTintList(MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.SnackbarLayout_backgroundTint));
            setBackgroundTintMode(ViewUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
            this.f = obtainStyledAttributes.getFloat(R.styleable.SnackbarLayout_actionTextColorAlpha, 1.0f);
            obtainStyledAttributes.recycle();
            setOnTouchListener(a);
            setFocusable(true);
            if (getBackground() == null) {
                ViewCompat.setBackground(this, a());
            }
        }

        @Override // android.view.View
        public void setBackground(@Nullable Drawable drawable) {
            setBackgroundDrawable(drawable);
        }

        @Override // android.view.View
        public void setBackgroundDrawable(@Nullable Drawable drawable) {
            if (!(drawable == null || this.g == null)) {
                drawable = DrawableCompat.wrap(drawable.mutate());
                DrawableCompat.setTintList(drawable, this.g);
                DrawableCompat.setTintMode(drawable, this.h);
            }
            super.setBackgroundDrawable(drawable);
        }

        @Override // android.view.View
        public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
            this.g = colorStateList;
            if (getBackground() != null) {
                Drawable wrap = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintList(wrap, colorStateList);
                DrawableCompat.setTintMode(wrap, this.h);
                if (wrap != getBackground()) {
                    super.setBackgroundDrawable(wrap);
                }
            }
        }

        @Override // android.view.View
        public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
            this.h = mode;
            if (getBackground() != null) {
                Drawable wrap = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintMode(wrap, mode);
                if (wrap != getBackground()) {
                    super.setBackgroundDrawable(wrap);
                }
            }
        }

        @Override // android.view.View
        public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
            setOnTouchListener(onClickListener != null ? null : a);
            super.setOnClickListener(onClickListener);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            OnLayoutChangeListener onLayoutChangeListener = this.b;
            if (onLayoutChangeListener != null) {
                onLayoutChangeListener.onLayoutChange(this, i, i2, i3, i4);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            OnAttachStateChangeListener onAttachStateChangeListener = this.c;
            if (onAttachStateChangeListener != null) {
                onAttachStateChangeListener.onViewAttachedToWindow(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            OnAttachStateChangeListener onAttachStateChangeListener = this.c;
            if (onAttachStateChangeListener != null) {
                onAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.b = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
            this.c = onAttachStateChangeListener;
        }

        int getAnimationMode() {
            return this.d;
        }

        void setAnimationMode(int i) {
            this.d = i;
        }

        float getBackgroundOverlayColorAlpha() {
            return this.e;
        }

        float getActionTextColorAlpha() {
            return this.f;
        }

        @NonNull
        private Drawable a() {
            float dimension = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(dimension);
            gradientDrawable.setColor(MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha()));
            if (this.g == null) {
                return DrawableCompat.wrap(gradientDrawable);
            }
            Drawable wrap = DrawableCompat.wrap(gradientDrawable);
            DrawableCompat.setTintList(wrap, this.g);
            return wrap;
        }
    }

    /* loaded from: classes2.dex */
    public static class Behavior extends SwipeDismissBehavior<View> {
        @NonNull
        private final BehaviorDelegate g = new BehaviorDelegate(this);

        /* JADX INFO: Access modifiers changed from: private */
        public void a(@NonNull BaseTransientBottomBar<?> baseTransientBottomBar) {
            this.g.setBaseTransientBottomBar(baseTransientBottomBar);
        }

        @Override // com.google.android.material.behavior.SwipeDismissBehavior
        public boolean canSwipeDismissView(View view) {
            return this.g.canSwipeDismissView(view);
        }

        @Override // com.google.android.material.behavior.SwipeDismissBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull MotionEvent motionEvent) {
            this.g.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
            return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public static class BehaviorDelegate {
        private a.AbstractC0079a a;

        public BehaviorDelegate(@NonNull SwipeDismissBehavior<?> swipeDismissBehavior) {
            swipeDismissBehavior.setStartAlphaSwipeDistance(0.1f);
            swipeDismissBehavior.setEndAlphaSwipeDistance(0.6f);
            swipeDismissBehavior.setSwipeDirection(0);
        }

        public void setBaseTransientBottomBar(@NonNull BaseTransientBottomBar<?> baseTransientBottomBar) {
            this.a = baseTransientBottomBar.b;
        }

        public boolean canSwipeDismissView(View view) {
            return view instanceof SnackbarBaseLayout;
        }

        public void onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 3) {
                switch (actionMasked) {
                    case 0:
                        if (coordinatorLayout.isPointInChildBounds(view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                            a.a().c(this.a);
                            return;
                        }
                        return;
                    case 1:
                        break;
                    default:
                        return;
                }
            }
            a.a().d(this.a);
        }
    }
}
