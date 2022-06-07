package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int M = R.style.Widget_Design_BottomSheet_Modal;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private int A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private boolean F;
    private int G;
    private int H;
    private ShapeAppearanceModel I;
    private boolean J;
    private BottomSheetBehavior<V>.a K;
    @Nullable
    private ValueAnimator L;
    private boolean N;
    private boolean O;
    private boolean P;
    private int Q;
    private boolean R;
    private int S;
    @NonNull
    private final ArrayList<BottomSheetCallback> T;
    @Nullable
    private VelocityTracker U;
    private int V;
    @Nullable
    private Map<View, Integer> W;
    private int X;
    private final ViewDragHelper.Callback Y;
    int a;
    int b;
    int c;
    float d;
    int e;
    float f;
    boolean g;
    int h;
    @Nullable
    ViewDragHelper i;
    int j;
    int k;
    @Nullable
    WeakReference<V> l;
    @Nullable
    WeakReference<View> m;
    int n;
    boolean o;
    private int p;
    private boolean q;
    private boolean r;
    private float s;
    private int t;
    private boolean u;
    private int v;
    private int w;
    private boolean x;
    private MaterialShapeDrawable y;
    private int z;

    /* loaded from: classes2.dex */
    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface SaveFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface State {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, int i3, int i4, int i5, @NonNull int[] iArr) {
    }

    public BottomSheetBehavior() {
        this.p = 0;
        this.q = true;
        this.r = false;
        this.z = -1;
        this.K = null;
        this.d = 0.5f;
        this.f = -1.0f;
        this.O = true;
        this.h = 4;
        this.T = new ArrayList<>();
        this.X = -1;
        this.Y = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (BottomSheetBehavior.this.h == 1 || BottomSheetBehavior.this.o) {
                    return false;
                }
                if (BottomSheetBehavior.this.h == 3 && BottomSheetBehavior.this.n == i) {
                    View view2 = BottomSheetBehavior.this.m != null ? BottomSheetBehavior.this.m.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.l != null && BottomSheetBehavior.this.l.get() == view;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.b(i2);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && BottomSheetBehavior.this.O) {
                    BottomSheetBehavior.this.a(1);
                }
            }

            private boolean a(@NonNull View view) {
                return view.getTop() > (BottomSheetBehavior.this.k + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f, float f2) {
                int i;
                int i2 = 4;
                if (f2 < 0.0f) {
                    if (BottomSheetBehavior.this.q) {
                        i = BottomSheetBehavior.this.b;
                        i2 = 3;
                    } else if (view.getTop() > BottomSheetBehavior.this.c) {
                        i = BottomSheetBehavior.this.c;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.getExpandedOffset();
                        i2 = 3;
                    }
                } else if (!BottomSheetBehavior.this.g || !BottomSheetBehavior.this.a(view, f2)) {
                    if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                        int top = view.getTop();
                        if (BottomSheetBehavior.this.q) {
                            if (Math.abs(top - BottomSheetBehavior.this.b) < Math.abs(top - BottomSheetBehavior.this.e)) {
                                i = BottomSheetBehavior.this.b;
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.e;
                            }
                        } else if (top < BottomSheetBehavior.this.c) {
                            if (top < Math.abs(top - BottomSheetBehavior.this.e)) {
                                i = BottomSheetBehavior.this.getExpandedOffset();
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.c;
                                i2 = 6;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.c) < Math.abs(top - BottomSheetBehavior.this.e)) {
                            i = BottomSheetBehavior.this.c;
                            i2 = 6;
                        } else {
                            i = BottomSheetBehavior.this.e;
                        }
                    } else if (BottomSheetBehavior.this.q) {
                        i = BottomSheetBehavior.this.e;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - BottomSheetBehavior.this.c) < Math.abs(top2 - BottomSheetBehavior.this.e)) {
                            i = BottomSheetBehavior.this.c;
                            i2 = 6;
                        } else {
                            i = BottomSheetBehavior.this.e;
                        }
                    }
                } else if ((Math.abs(f) < Math.abs(f2) && f2 > 500.0f) || a(view)) {
                    i = BottomSheetBehavior.this.k;
                    i2 = 5;
                } else if (BottomSheetBehavior.this.q) {
                    i = BottomSheetBehavior.this.b;
                    i2 = 3;
                } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.c)) {
                    i = BottomSheetBehavior.this.getExpandedOffset();
                    i2 = 3;
                } else {
                    i = BottomSheetBehavior.this.c;
                    i2 = 6;
                }
                BottomSheetBehavior.this.a(view, i2, i, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i, int i2) {
                return MathUtils.clamp(i, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.g ? BottomSheetBehavior.this.k : BottomSheetBehavior.this.e);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                if (BottomSheetBehavior.this.g) {
                    return BottomSheetBehavior.this.k;
                }
                return BottomSheetBehavior.this.e;
            }
        };
    }

    public BottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.p = 0;
        this.q = true;
        this.r = false;
        this.z = -1;
        this.K = null;
        this.d = 0.5f;
        this.f = -1.0f;
        this.O = true;
        this.h = 4;
        this.T = new ArrayList<>();
        this.X = -1;
        this.Y = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (BottomSheetBehavior.this.h == 1 || BottomSheetBehavior.this.o) {
                    return false;
                }
                if (BottomSheetBehavior.this.h == 3 && BottomSheetBehavior.this.n == i) {
                    View view2 = BottomSheetBehavior.this.m != null ? BottomSheetBehavior.this.m.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.l != null && BottomSheetBehavior.this.l.get() == view;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.b(i2);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && BottomSheetBehavior.this.O) {
                    BottomSheetBehavior.this.a(1);
                }
            }

            private boolean a(@NonNull View view) {
                return view.getTop() > (BottomSheetBehavior.this.k + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f, float f2) {
                int i;
                int i2 = 4;
                if (f2 < 0.0f) {
                    if (BottomSheetBehavior.this.q) {
                        i = BottomSheetBehavior.this.b;
                        i2 = 3;
                    } else if (view.getTop() > BottomSheetBehavior.this.c) {
                        i = BottomSheetBehavior.this.c;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.getExpandedOffset();
                        i2 = 3;
                    }
                } else if (!BottomSheetBehavior.this.g || !BottomSheetBehavior.this.a(view, f2)) {
                    if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                        int top = view.getTop();
                        if (BottomSheetBehavior.this.q) {
                            if (Math.abs(top - BottomSheetBehavior.this.b) < Math.abs(top - BottomSheetBehavior.this.e)) {
                                i = BottomSheetBehavior.this.b;
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.e;
                            }
                        } else if (top < BottomSheetBehavior.this.c) {
                            if (top < Math.abs(top - BottomSheetBehavior.this.e)) {
                                i = BottomSheetBehavior.this.getExpandedOffset();
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.c;
                                i2 = 6;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.c) < Math.abs(top - BottomSheetBehavior.this.e)) {
                            i = BottomSheetBehavior.this.c;
                            i2 = 6;
                        } else {
                            i = BottomSheetBehavior.this.e;
                        }
                    } else if (BottomSheetBehavior.this.q) {
                        i = BottomSheetBehavior.this.e;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - BottomSheetBehavior.this.c) < Math.abs(top2 - BottomSheetBehavior.this.e)) {
                            i = BottomSheetBehavior.this.c;
                            i2 = 6;
                        } else {
                            i = BottomSheetBehavior.this.e;
                        }
                    }
                } else if ((Math.abs(f) < Math.abs(f2) && f2 > 500.0f) || a(view)) {
                    i = BottomSheetBehavior.this.k;
                    i2 = 5;
                } else if (BottomSheetBehavior.this.q) {
                    i = BottomSheetBehavior.this.b;
                    i2 = 3;
                } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.c)) {
                    i = BottomSheetBehavior.this.getExpandedOffset();
                    i2 = 3;
                } else {
                    i = BottomSheetBehavior.this.c;
                    i2 = 6;
                }
                BottomSheetBehavior.this.a(view, i2, i, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i, int i2) {
                return MathUtils.clamp(i, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.g ? BottomSheetBehavior.this.k : BottomSheetBehavior.this.e);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                if (BottomSheetBehavior.this.g) {
                    return BottomSheetBehavior.this.k;
                }
                return BottomSheetBehavior.this.e;
            }
        };
        this.w = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        this.x = obtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
        boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        if (hasValue) {
            a(context, attributeSet, hasValue, MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.BottomSheetBehavior_Layout_backgroundTint));
        } else {
            a(context, attributeSet, hasValue);
        }
        f();
        if (Build.VERSION.SDK_INT >= 21) {
            this.f = obtainStyledAttributes.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
            setMaxWidth(obtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
        }
        TypedValue peekValue = obtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (peekValue == null || peekValue.data != -1) {
            setPeekHeight(obtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            setPeekHeight(peekValue.data);
        }
        setHideable(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(obtainStyledAttributes.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(obtainStyledAttributes.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        TypedValue peekValue2 = obtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        if (peekValue2 == null || peekValue2.type != 16) {
            setExpandedOffset(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
        } else {
            setExpandedOffset(peekValue2.data);
        }
        this.C = obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.D = obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.E = obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.F = obtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        obtainStyledAttributes.recycle();
        this.s = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @NonNull
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), (BottomSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, savedState.getSuperState());
        a(savedState);
        if (savedState.a == 1 || savedState.a == 2) {
            this.h = 4;
        } else {
            this.h = savedState.a;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.l = null;
        this.i = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.l = null;
        this.i = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull final V v, int i) {
        MaterialShapeDrawable materialShapeDrawable;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            v.setFitsSystemWindows(true);
        }
        if (this.l == null) {
            this.v = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            b(v);
            this.l = new WeakReference<>(v);
            if (this.x && (materialShapeDrawable = this.y) != null) {
                ViewCompat.setBackground(v, materialShapeDrawable);
            }
            MaterialShapeDrawable materialShapeDrawable2 = this.y;
            if (materialShapeDrawable2 != null) {
                float f = this.f;
                if (f == -1.0f) {
                    f = ViewCompat.getElevation(v);
                }
                materialShapeDrawable2.setElevation(f);
                this.J = this.h == 3;
                this.y.setInterpolation(this.J ? 0.0f : 1.0f);
            }
            h();
            if (ViewCompat.getImportantForAccessibility(v) == 0) {
                ViewCompat.setImportantForAccessibility(v, 1);
            }
            int measuredWidth = v.getMeasuredWidth();
            int i2 = this.z;
            if (measuredWidth > i2 && i2 != -1) {
                final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = this.z;
                v.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
                    @Override // java.lang.Runnable
                    public void run() {
                        v.setLayoutParams(layoutParams);
                    }
                });
            }
        }
        if (this.i == null) {
            this.i = ViewDragHelper.create(coordinatorLayout, this.Y);
        }
        int top = v.getTop();
        coordinatorLayout.onLayoutChild(v, i);
        this.j = coordinatorLayout.getWidth();
        this.k = coordinatorLayout.getHeight();
        this.S = v.getHeight();
        int i3 = this.k;
        int i4 = i3 - this.S;
        int i5 = this.H;
        if (i4 < i5) {
            if (this.F) {
                this.S = i3;
            } else {
                this.S = i3 - i5;
            }
        }
        this.b = Math.max(0, this.k - this.S);
        d();
        c();
        int i6 = this.h;
        if (i6 == 3) {
            ViewCompat.offsetTopAndBottom(v, getExpandedOffset());
        } else if (i6 == 6) {
            ViewCompat.offsetTopAndBottom(v, this.c);
        } else if (!this.g || i6 != 5) {
            int i7 = this.h;
            if (i7 == 4) {
                ViewCompat.offsetTopAndBottom(v, this.e);
            } else if (i7 == 1 || i7 == 2) {
                ViewCompat.offsetTopAndBottom(v, top - v.getTop());
            }
        } else {
            ViewCompat.offsetTopAndBottom(v, this.k);
        }
        this.m = new WeakReference<>(a(v));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(@androidx.annotation.NonNull androidx.coordinatorlayout.widget.CoordinatorLayout r10, @androidx.annotation.NonNull V r11, @androidx.annotation.NonNull android.view.MotionEvent r12) {
        /*
            r9 = this;
            boolean r0 = r11.isShown()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x00d2
            boolean r0 = r9.O
            if (r0 != 0) goto L_0x000e
            goto L_0x00d2
        L_0x000e:
            int r0 = r12.getActionMasked()
            if (r0 != 0) goto L_0x0017
            r9.e()
        L_0x0017:
            android.view.VelocityTracker r3 = r9.U
            if (r3 != 0) goto L_0x0021
            android.view.VelocityTracker r3 = android.view.VelocityTracker.obtain()
            r9.U = r3
        L_0x0021:
            android.view.VelocityTracker r3 = r9.U
            r3.addMovement(r12)
            r3 = 3
            r4 = 0
            r5 = -1
            r6 = 2
            if (r0 == r3) goto L_0x0074
            switch(r0) {
                case 0: goto L_0x0030;
                case 1: goto L_0x0074;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x007f
        L_0x0030:
            float r3 = r12.getX()
            int r3 = (int) r3
            float r7 = r12.getY()
            int r7 = (int) r7
            r9.V = r7
            int r7 = r9.h
            if (r7 == r6) goto L_0x0062
            java.lang.ref.WeakReference<android.view.View> r7 = r9.m
            if (r7 == 0) goto L_0x004b
            java.lang.Object r7 = r7.get()
            android.view.View r7 = (android.view.View) r7
            goto L_0x004c
        L_0x004b:
            r7 = r4
        L_0x004c:
            if (r7 == 0) goto L_0x0062
            int r8 = r9.V
            boolean r7 = r10.isPointInChildBounds(r7, r3, r8)
            if (r7 == 0) goto L_0x0062
            int r7 = r12.getActionIndex()
            int r7 = r12.getPointerId(r7)
            r9.n = r7
            r9.o = r2
        L_0x0062:
            int r7 = r9.n
            if (r7 != r5) goto L_0x0070
            int r5 = r9.V
            boolean r11 = r10.isPointInChildBounds(r11, r3, r5)
            if (r11 != 0) goto L_0x0070
            r11 = r2
            goto L_0x0071
        L_0x0070:
            r11 = r1
        L_0x0071:
            r9.P = r11
            goto L_0x007f
        L_0x0074:
            r9.o = r1
            r9.n = r5
            boolean r11 = r9.P
            if (r11 == 0) goto L_0x007f
            r9.P = r1
            return r1
        L_0x007f:
            boolean r11 = r9.P
            if (r11 != 0) goto L_0x008e
            androidx.customview.widget.ViewDragHelper r11 = r9.i
            if (r11 == 0) goto L_0x008e
            boolean r11 = r11.shouldInterceptTouchEvent(r12)
            if (r11 == 0) goto L_0x008e
            return r2
        L_0x008e:
            java.lang.ref.WeakReference<android.view.View> r11 = r9.m
            if (r11 == 0) goto L_0x0099
            java.lang.Object r11 = r11.get()
            r4 = r11
            android.view.View r4 = (android.view.View) r4
        L_0x0099:
            if (r0 != r6) goto L_0x00d1
            if (r4 == 0) goto L_0x00d1
            boolean r11 = r9.P
            if (r11 != 0) goto L_0x00d1
            int r11 = r9.h
            if (r11 == r2) goto L_0x00d1
            float r11 = r12.getX()
            int r11 = (int) r11
            float r0 = r12.getY()
            int r0 = (int) r0
            boolean r10 = r10.isPointInChildBounds(r4, r11, r0)
            if (r10 != 0) goto L_0x00d1
            androidx.customview.widget.ViewDragHelper r10 = r9.i
            if (r10 == 0) goto L_0x00d1
            int r10 = r9.V
            float r10 = (float) r10
            float r11 = r12.getY()
            float r10 = r10 - r11
            float r10 = java.lang.Math.abs(r10)
            androidx.customview.widget.ViewDragHelper r11 = r9.i
            int r11 = r11.getTouchSlop()
            float r11 = (float) r11
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 <= 0) goto L_0x00d1
            r1 = r2
        L_0x00d1:
            return r1
        L_0x00d2:
            r9.P = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.h == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.i;
        if (viewDragHelper != null) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            e();
        }
        if (this.U == null) {
            this.U = VelocityTracker.obtain();
        }
        this.U.addMovement(motionEvent);
        if (this.i != null && actionMasked == 2 && !this.P && Math.abs(this.V - motionEvent.getY()) > this.i.getTouchSlop()) {
            this.i.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.P;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
        this.Q = 0;
        this.R = false;
        return (i & 2) != 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
        if (i3 != 1) {
            WeakReference<View> weakReference = this.m;
            if (view == (weakReference != null ? weakReference.get() : null)) {
                int top = v.getTop();
                int i4 = top - i2;
                if (i2 > 0) {
                    if (i4 < getExpandedOffset()) {
                        iArr[1] = top - getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                        a(3);
                    } else if (this.O) {
                        iArr[1] = i2;
                        ViewCompat.offsetTopAndBottom(v, -i2);
                        a(1);
                    } else {
                        return;
                    }
                } else if (i2 < 0 && !view.canScrollVertically(-1)) {
                    int i5 = this.e;
                    if (i4 > i5 && !this.g) {
                        iArr[1] = top - i5;
                        ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                        a(4);
                    } else if (this.O) {
                        iArr[1] = i2;
                        ViewCompat.offsetTopAndBottom(v, -i2);
                        a(1);
                    } else {
                        return;
                    }
                }
                b(v.getTop());
                this.Q = i2;
                this.R = true;
            }
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i) {
        int i2;
        int i3 = 3;
        if (v.getTop() == getExpandedOffset()) {
            a(3);
            return;
        }
        WeakReference<View> weakReference = this.m;
        if (weakReference != null && view == weakReference.get() && this.R) {
            if (this.Q > 0) {
                if (this.q) {
                    i2 = this.b;
                } else {
                    int top = v.getTop();
                    int i4 = this.c;
                    if (top > i4) {
                        i2 = i4;
                        i3 = 6;
                    } else {
                        i2 = getExpandedOffset();
                    }
                }
            } else if (this.g && a(v, g())) {
                i2 = this.k;
                i3 = 5;
            } else if (this.Q == 0) {
                int top2 = v.getTop();
                if (!this.q) {
                    int i5 = this.c;
                    if (top2 < i5) {
                        if (top2 < Math.abs(top2 - this.e)) {
                            i2 = getExpandedOffset();
                        } else {
                            i2 = this.c;
                            i3 = 6;
                        }
                    } else if (Math.abs(top2 - i5) < Math.abs(top2 - this.e)) {
                        i2 = this.c;
                        i3 = 6;
                    } else {
                        i2 = this.e;
                        i3 = 4;
                    }
                } else if (Math.abs(top2 - this.b) < Math.abs(top2 - this.e)) {
                    i2 = this.b;
                } else {
                    i2 = this.e;
                    i3 = 4;
                }
            } else if (this.q) {
                i2 = this.e;
                i3 = 4;
            } else {
                int top3 = v.getTop();
                if (Math.abs(top3 - this.c) < Math.abs(top3 - this.e)) {
                    i2 = this.c;
                    i3 = 6;
                } else {
                    i2 = this.e;
                    i3 = 4;
                }
            }
            a((View) v, i3, i2, false);
            this.R = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
        WeakReference<View> weakReference = this.m;
        if (weakReference == null || view != weakReference.get()) {
            return false;
        }
        return this.h != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2);
    }

    public boolean isFitToContents() {
        return this.q;
    }

    public void setFitToContents(boolean z) {
        if (this.q != z) {
            this.q = z;
            if (this.l != null) {
                c();
            }
            a((!this.q || this.h != 6) ? this.h : 3);
            h();
        }
    }

    public void setMaxWidth(@Px int i) {
        this.z = i;
    }

    @Px
    public int getMaxWidth() {
        return this.z;
    }

    public void setPeekHeight(int i) {
        setPeekHeight(i, false);
    }

    public final void setPeekHeight(int i, boolean z) {
        boolean z2 = true;
        if (i == -1) {
            if (!this.u) {
                this.u = true;
            }
            z2 = false;
        } else {
            if (this.u || this.t != i) {
                this.u = false;
                this.t = Math.max(0, i);
            }
            z2 = false;
        }
        if (z2) {
            a(z);
        }
    }

    public void a(boolean z) {
        V v;
        if (this.l != null) {
            c();
            if (this.h == 4 && (v = this.l.get()) != null) {
                if (z) {
                    c(this.h);
                } else {
                    v.requestLayout();
                }
            }
        }
    }

    public int getPeekHeight() {
        if (this.u) {
            return -1;
        }
        return this.t;
    }

    public void setHalfExpandedRatio(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.d = f;
        if (this.l != null) {
            d();
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getHalfExpandedRatio() {
        return this.d;
    }

    public void setExpandedOffset(int i) {
        if (i >= 0) {
            this.a = i;
            return;
        }
        throw new IllegalArgumentException("offset must be greater than or equal to 0");
    }

    public int getExpandedOffset() {
        if (this.q) {
            return this.b;
        }
        return Math.max(this.a, this.F ? 0 : this.H);
    }

    public void setHideable(boolean z) {
        if (this.g != z) {
            this.g = z;
            if (!z && this.h == 5) {
                setState(4);
            }
            h();
        }
    }

    public boolean isHideable() {
        return this.g;
    }

    public void setSkipCollapsed(boolean z) {
        this.N = z;
    }

    public boolean getSkipCollapsed() {
        return this.N;
    }

    public void setDraggable(boolean z) {
        this.O = z;
    }

    public boolean isDraggable() {
        return this.O;
    }

    public void setSaveFlags(int i) {
        this.p = i;
    }

    public int getSaveFlags() {
        return this.p;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        Log.w("BottomSheetBehavior", "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.T.clear();
        if (bottomSheetCallback != null) {
            this.T.add(bottomSheetCallback);
        }
    }

    public void addBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        if (!this.T.contains(bottomSheetCallback)) {
            this.T.add(bottomSheetCallback);
        }
    }

    public void removeBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        this.T.remove(bottomSheetCallback);
    }

    public void setState(int i) {
        if (i != this.h) {
            if (this.l != null) {
                c(i);
            } else if (i == 4 || i == 3 || i == 6 || (this.g && i == 5)) {
                this.h = i;
            }
        }
    }

    public void setGestureInsetBottomIgnored(boolean z) {
        this.B = z;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.B;
    }

    private void c(final int i) {
        final V v = this.l.get();
        if (v != null) {
            ViewParent parent = v.getParent();
            if (parent == null || !parent.isLayoutRequested() || !ViewCompat.isAttachedToWindow(v)) {
                a((View) v, i);
            } else {
                v.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
                    @Override // java.lang.Runnable
                    public void run() {
                        BottomSheetBehavior.this.a(v, i);
                    }
                });
            }
        }
    }

    public int getState() {
        return this.h;
    }

    void a(int i) {
        V v;
        if (this.h != i) {
            this.h = i;
            WeakReference<V> weakReference = this.l;
            if (!(weakReference == null || (v = weakReference.get()) == null)) {
                if (i == 3) {
                    b(true);
                } else if (i == 6 || i == 5 || i == 4) {
                    b(false);
                }
                d(i);
                for (int i2 = 0; i2 < this.T.size(); i2++) {
                    this.T.get(i2).onStateChanged(v, i);
                }
                h();
            }
        }
    }

    private void d(int i) {
        ValueAnimator valueAnimator;
        if (i != 2) {
            boolean z = i == 3;
            if (this.J != z) {
                this.J = z;
                if (this.y != null && (valueAnimator = this.L) != null) {
                    if (valueAnimator.isRunning()) {
                        this.L.reverse();
                        return;
                    }
                    float f = z ? 0.0f : 1.0f;
                    this.L.setFloatValues(1.0f - f, f);
                    this.L.start();
                }
            }
        }
    }

    private int b() {
        int i;
        if (this.u) {
            return Math.min(Math.max(this.v, this.k - ((this.j * 9) / 16)), this.S) + this.G;
        }
        if (this.B || this.C || (i = this.A) <= 0) {
            return this.t + this.G;
        }
        return Math.max(this.t, i + this.w);
    }

    private void c() {
        int b = b();
        if (this.q) {
            this.e = Math.max(this.k - b, this.b);
        } else {
            this.e = this.k - b;
        }
    }

    private void d() {
        this.c = (int) (this.k * (1.0f - this.d));
    }

    private void e() {
        this.n = -1;
        VelocityTracker velocityTracker = this.U;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.U = null;
        }
    }

    private void a(@NonNull SavedState savedState) {
        int i = this.p;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.t = savedState.b;
            }
            int i2 = this.p;
            if (i2 == -1 || (i2 & 2) == 2) {
                this.q = savedState.c;
            }
            int i3 = this.p;
            if (i3 == -1 || (i3 & 4) == 4) {
                this.g = savedState.d;
            }
            int i4 = this.p;
            if (i4 == -1 || (i4 & 8) == 8) {
                this.N = savedState.e;
            }
        }
    }

    boolean a(@NonNull View view, float f) {
        if (this.N) {
            return true;
        }
        if (view.getTop() < this.e) {
            return false;
        }
        return Math.abs((((float) view.getTop()) + (f * 0.1f)) - ((float) this.e)) / ((float) b()) > 0.5f;
    }

    @Nullable
    @VisibleForTesting
    View a(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View a2 = a(viewGroup.getChildAt(i));
            if (a2 != null) {
                return a2;
            }
        }
        return null;
    }

    private void a(@NonNull Context context, AttributeSet attributeSet, boolean z) {
        a(context, attributeSet, z, (ColorStateList) null);
    }

    private void a(@NonNull Context context, AttributeSet attributeSet, boolean z, @Nullable ColorStateList colorStateList) {
        if (this.x) {
            this.I = ShapeAppearanceModel.builder(context, attributeSet, R.attr.bottomSheetStyle, M).build();
            this.y = new MaterialShapeDrawable(this.I);
            this.y.initializeElevationOverlay(context);
            if (!z || colorStateList == null) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(16842801, typedValue, true);
                this.y.setTint(typedValue.data);
                return;
            }
            this.y.setFillColor(colorStateList);
        }
    }

    public MaterialShapeDrawable a() {
        return this.y;
    }

    private void f() {
        this.L = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.L.setDuration(500L);
        this.L.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.y != null) {
                    BottomSheetBehavior.this.y.setInterpolation(floatValue);
                }
            }
        });
    }

    private void b(@NonNull View view) {
        final boolean z = Build.VERSION.SDK_INT >= 29 && !isGestureInsetBottomIgnored() && !this.u;
        if (this.C || this.D || this.E || z) {
            ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
                @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                    BottomSheetBehavior.this.H = windowInsetsCompat.getSystemWindowInsetTop();
                    boolean isLayoutRtl = ViewUtils.isLayoutRtl(view2);
                    int paddingBottom = view2.getPaddingBottom();
                    int paddingLeft = view2.getPaddingLeft();
                    int paddingRight = view2.getPaddingRight();
                    if (BottomSheetBehavior.this.C) {
                        BottomSheetBehavior.this.G = windowInsetsCompat.getSystemWindowInsetBottom();
                        paddingBottom = relativePadding.bottom + BottomSheetBehavior.this.G;
                    }
                    if (BottomSheetBehavior.this.D) {
                        paddingLeft = (isLayoutRtl ? relativePadding.end : relativePadding.start) + windowInsetsCompat.getSystemWindowInsetLeft();
                    }
                    if (BottomSheetBehavior.this.E) {
                        paddingRight = (isLayoutRtl ? relativePadding.start : relativePadding.end) + windowInsetsCompat.getSystemWindowInsetRight();
                    }
                    view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
                    if (z) {
                        BottomSheetBehavior.this.A = windowInsetsCompat.getMandatorySystemGestureInsets().bottom;
                    }
                    if (BottomSheetBehavior.this.C || z) {
                        BottomSheetBehavior.this.a(false);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }

    private float g() {
        VelocityTracker velocityTracker = this.U;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.s);
        return this.U.getYVelocity(this.n);
    }

    void a(@NonNull View view, int i) {
        int i2;
        int i3;
        if (i == 4) {
            i2 = this.e;
        } else if (i == 6) {
            int i4 = this.c;
            if (!this.q || i4 > (i3 = this.b)) {
                i2 = i4;
            } else {
                i = 3;
                i2 = i3;
            }
        } else if (i == 3) {
            i2 = getExpandedOffset();
        } else if (!this.g || i != 5) {
            throw new IllegalArgumentException("Illegal state argument: " + i);
        } else {
            i2 = this.k;
        }
        a(view, i, i2, false);
    }

    void a(View view, int i, int i2, boolean z) {
        ViewDragHelper viewDragHelper = this.i;
        if (viewDragHelper != null && (!z ? viewDragHelper.smoothSlideViewTo(view, view.getLeft(), i2) : viewDragHelper.settleCapturedViewAt(view.getLeft(), i2))) {
            a(2);
            d(i);
            if (this.K == null) {
                this.K = new a(view, i);
            }
            if (!((a) this.K).d) {
                BottomSheetBehavior<V>.a aVar = this.K;
                aVar.a = i;
                ViewCompat.postOnAnimation(view, aVar);
                ((a) this.K).d = true;
                return;
            }
            this.K.a = i;
            return;
        }
        a(i);
    }

    void b(int i) {
        float f;
        V v = this.l.get();
        if (!(v == null || this.T.isEmpty())) {
            int i2 = this.e;
            if (i > i2 || i2 == getExpandedOffset()) {
                int i3 = this.e;
                f = (i3 - i) / (this.k - i3);
            } else {
                int i4 = this.e;
                f = (i4 - i) / (i4 - getExpandedOffset());
            }
            for (int i5 = 0; i5 < this.T.size(); i5++) {
                this.T.get(i5).onSlide(v, f);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void disableShapeAnimations() {
        this.L = null;
    }

    /* loaded from: classes2.dex */
    public class a implements Runnable {
        int a;
        private final View c;
        private boolean d;

        a(View view, int i) {
            BottomSheetBehavior.this = r1;
            this.c = view;
            this.a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BottomSheetBehavior.this.i == null || !BottomSheetBehavior.this.i.continueSettling(true)) {
                BottomSheetBehavior.this.a(this.a);
            } else {
                ViewCompat.postOnAnimation(this.c, this);
            }
            this.d = false;
        }
    }

    /* loaded from: classes2.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
            @NonNull
            /* renamed from: a */
            public SavedState createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Nullable
            /* renamed from: a */
            public SavedState createFromParcel(@NonNull Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            @NonNull
            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        final int a;
        int b;
        boolean c;
        boolean d;
        boolean e;

        public SavedState(@NonNull Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        public SavedState(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            boolean z = false;
            this.c = parcel.readInt() == 1;
            this.d = parcel.readInt() == 1;
            this.e = parcel.readInt() == 1 ? true : z;
        }

        public SavedState(Parcelable parcelable, @NonNull BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.a = bottomSheetBehavior.h;
            this.b = ((BottomSheetBehavior) bottomSheetBehavior).t;
            this.c = ((BottomSheetBehavior) bottomSheetBehavior).q;
            this.d = bottomSheetBehavior.g;
            this.e = ((BottomSheetBehavior) bottomSheetBehavior).N;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.a = i;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c ? 1 : 0);
            parcel.writeInt(this.d ? 1 : 0);
            parcel.writeInt(this.e ? 1 : 0);
        }
    }

    @NonNull
    public static <V extends View> BottomSheetBehavior<V> from(@NonNull V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean z) {
        this.r = z;
    }

    private void b(boolean z) {
        Map<View, Integer> map;
        WeakReference<V> weakReference = this.l;
        if (weakReference != null) {
            ViewParent parent = weakReference.get().getParent();
            if (parent instanceof CoordinatorLayout) {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
                int childCount = coordinatorLayout.getChildCount();
                if (Build.VERSION.SDK_INT >= 16 && z) {
                    if (this.W == null) {
                        this.W = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    View childAt = coordinatorLayout.getChildAt(i);
                    if (childAt != this.l.get()) {
                        if (z) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                this.W.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                            }
                            if (this.r) {
                                ViewCompat.setImportantForAccessibility(childAt, 4);
                            }
                        } else if (this.r && (map = this.W) != null && map.containsKey(childAt)) {
                            ViewCompat.setImportantForAccessibility(childAt, this.W.get(childAt).intValue());
                        }
                    }
                }
                if (!z) {
                    this.W = null;
                } else if (this.r) {
                    this.l.get().sendAccessibilityEvent(8);
                }
            }
        }
    }

    private void h() {
        V v;
        WeakReference<V> weakReference = this.l;
        if (weakReference != null && (v = weakReference.get()) != null) {
            ViewCompat.removeAccessibilityAction(v, 524288);
            ViewCompat.removeAccessibilityAction(v, 262144);
            ViewCompat.removeAccessibilityAction(v, 1048576);
            int i = this.X;
            if (i != -1) {
                ViewCompat.removeAccessibilityAction(v, i);
            }
            int i2 = 6;
            if (!this.q && this.h != 6) {
                this.X = a((BottomSheetBehavior<V>) v, R.string.bottomsheet_action_expand_halfway, 6);
            }
            if (this.g && this.h != 5) {
                a((BottomSheetBehavior<V>) v, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            int i3 = this.h;
            if (i3 != 6) {
                switch (i3) {
                    case 3:
                        if (this.q) {
                            i2 = 4;
                        }
                        a((BottomSheetBehavior<V>) v, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, i2);
                        return;
                    case 4:
                        if (this.q) {
                            i2 = 3;
                        }
                        a((BottomSheetBehavior<V>) v, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, i2);
                        return;
                    default:
                        return;
                }
            } else {
                a((BottomSheetBehavior<V>) v, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                a((BottomSheetBehavior<V>) v, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
            }
        }
    }

    private void a(V v, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, int i) {
        ViewCompat.replaceAccessibilityAction(v, accessibilityActionCompat, null, e(i));
    }

    private int a(V v, @StringRes int i, int i2) {
        return ViewCompat.addAccessibilityAction(v, v.getResources().getString(i), e(i2));
    }

    private AccessibilityViewCommand e(final int i) {
        return new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                BottomSheetBehavior.this.setState(i);
                return true;
            }
        };
    }
}
