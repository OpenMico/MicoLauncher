package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {
    View a;
    float b;
    int c;
    boolean d;
    final ViewDragHelper e;
    boolean f;
    final ArrayList<b> g;
    private int h;
    private int i;
    private Drawable j;
    private Drawable k;
    private final int l;
    private boolean m;
    private float n;
    private int o;
    private float p;
    private float q;
    private PanelSlideListener r;
    private boolean s;
    private final Rect t;
    private Method u;
    private Field v;
    private boolean w;

    /* loaded from: classes.dex */
    public interface PanelSlideListener {
        void onPanelClosed(@NonNull View view);

        void onPanelOpened(@NonNull View view);

        void onPanelSlide(@NonNull View view, float f);
    }

    /* loaded from: classes.dex */
    public static class SimplePanelSlideListener implements PanelSlideListener {
        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelClosed(View view) {
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelOpened(View view) {
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.PanelSlideListener
        public void onPanelSlide(View view, float f) {
        }
    }

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.h = -858993460;
        this.s = true;
        this.t = new Rect();
        this.g = new ArrayList<>();
        float f = context.getResources().getDisplayMetrics().density;
        this.l = (int) ((32.0f * f) + 0.5f);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new a());
        ViewCompat.setImportantForAccessibility(this, 1);
        this.e = ViewDragHelper.create(this, 0.5f, new c());
        this.e.setMinVelocity(f * 400.0f);
    }

    public void setParallaxDistance(@Px int i) {
        this.o = i;
        requestLayout();
    }

    @Px
    public int getParallaxDistance() {
        return this.o;
    }

    public void setSliderFadeColor(@ColorInt int i) {
        this.h = i;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.h;
    }

    public void setCoveredFadeColor(@ColorInt int i) {
        this.i = i;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.i;
    }

    public void setPanelSlideListener(@Nullable PanelSlideListener panelSlideListener) {
        this.r = panelSlideListener;
    }

    void a(View view) {
        PanelSlideListener panelSlideListener = this.r;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelSlide(view, this.b);
        }
    }

    void b(View view) {
        PanelSlideListener panelSlideListener = this.r;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelOpened(view);
        }
        sendAccessibilityEvent(32);
    }

    void c(View view) {
        PanelSlideListener panelSlideListener = this.r;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelClosed(view);
        }
        sendAccessibilityEvent(32);
    }

    void d(View view) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        View view2 = view;
        boolean b2 = b();
        int width = b2 ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = b2 ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null || !g(view)) {
            i4 = 0;
            i3 = 0;
            i2 = 0;
            i = 0;
        } else {
            i4 = view.getLeft();
            i3 = view.getRight();
            i2 = view.getTop();
            i = view.getBottom();
        }
        int childCount = getChildCount();
        int i6 = 0;
        while (i6 < childCount) {
            View childAt = getChildAt(i6);
            if (childAt != view2) {
                if (childAt.getVisibility() == 8) {
                    b2 = b2;
                } else {
                    int max = Math.max(b2 ? paddingLeft : width, childAt.getLeft());
                    int max2 = Math.max(paddingTop, childAt.getTop());
                    if (b2) {
                        b2 = b2;
                        i5 = width;
                    } else {
                        b2 = b2;
                        i5 = paddingLeft;
                    }
                    childAt.setVisibility((max < i4 || max2 < i2 || Math.min(i5, childAt.getRight()) > i3 || Math.min(height, childAt.getBottom()) > i) ? 0 : 4);
                }
                i6++;
                view2 = view;
            } else {
                return;
            }
        }
    }

    void a() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    private static boolean g(View view) {
        Drawable background;
        if (view.isOpaque()) {
            return true;
        }
        return Build.VERSION.SDK_INT < 18 && (background = view.getBackground()) != null && background.getOpacity() == -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.s = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.s = true;
        int size = this.g.size();
        for (int i = 0; i < size; i++) {
            this.g.get(i).run();
        }
        this.g.clear();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
        } else if (mode2 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if (mode2 == 0) {
                size2 = 300;
                mode2 = Integer.MIN_VALUE;
            }
        }
        boolean z = false;
        if (mode2 == Integer.MIN_VALUE) {
            i3 = (size2 - getPaddingTop()) - getPaddingBottom();
            i4 = 0;
        } else if (mode2 != 1073741824) {
            i4 = 0;
            i3 = 0;
        } else {
            i4 = (size2 - getPaddingTop()) - getPaddingBottom();
            i3 = i4;
        }
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
        }
        this.a = null;
        boolean z2 = false;
        int i11 = paddingLeft;
        float f = 0.0f;
        int i12 = 0;
        while (true) {
            i5 = 8;
            if (i12 >= childCount) {
                break;
            }
            View childAt = getChildAt(i12);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 8) {
                layoutParams.b = z;
            } else {
                if (layoutParams.weight > 0.0f) {
                    f += layoutParams.weight;
                    if (layoutParams.width == 0) {
                    }
                }
                int i13 = layoutParams.leftMargin + layoutParams.rightMargin;
                if (layoutParams.width == -2) {
                    i9 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i13, Integer.MIN_VALUE);
                } else if (layoutParams.width == -1) {
                    i9 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i13, 1073741824);
                } else {
                    i9 = View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                }
                if (layoutParams.height == -2) {
                    i10 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
                } else if (layoutParams.height == -1) {
                    i10 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
                } else {
                    i10 = View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
                }
                childAt.measure(i9, i10);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (mode2 == Integer.MIN_VALUE && measuredHeight > i4) {
                    i4 = Math.min(measuredHeight, i3);
                }
                i11 -= measuredWidth;
                boolean z3 = i11 < 0;
                layoutParams.a = z3;
                z2 = z3 | z2;
                if (layoutParams.a) {
                    this.a = childAt;
                }
            }
            i12++;
            z = false;
        }
        if (z2 || f > 0.0f) {
            int i14 = paddingLeft - this.l;
            int i15 = 0;
            while (i15 < childCount) {
                View childAt2 = getChildAt(i15);
                if (childAt2.getVisibility() == i5) {
                    i14 = i14;
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    if (childAt2.getVisibility() == i5) {
                        i14 = i14;
                    } else {
                        boolean z4 = layoutParams2.width == 0 && layoutParams2.weight > 0.0f;
                        int measuredWidth2 = z4 ? 0 : childAt2.getMeasuredWidth();
                        if (!z2 || childAt2 == this.a) {
                            if (layoutParams2.weight > 0.0f) {
                                if (layoutParams2.width != 0) {
                                    i6 = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824);
                                } else if (layoutParams2.height == -2) {
                                    i6 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
                                } else if (layoutParams2.height == -1) {
                                    i6 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
                                } else {
                                    i6 = View.MeasureSpec.makeMeasureSpec(layoutParams2.height, 1073741824);
                                }
                                if (z2) {
                                    int i16 = paddingLeft - (layoutParams2.leftMargin + layoutParams2.rightMargin);
                                    i14 = i14;
                                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i16, 1073741824);
                                    if (measuredWidth2 != i16) {
                                        childAt2.measure(makeMeasureSpec, i6);
                                    }
                                } else {
                                    i14 = i14;
                                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2 + ((int) ((layoutParams2.weight * Math.max(0, i11)) / f)), 1073741824), i6);
                                }
                            } else {
                                i14 = i14;
                            }
                        } else if (layoutParams2.width >= 0 || (measuredWidth2 <= i14 && layoutParams2.weight <= 0.0f)) {
                            i14 = i14;
                        } else {
                            if (!z4) {
                                i7 = 1073741824;
                                i8 = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824);
                            } else if (layoutParams2.height == -2) {
                                i8 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
                                i7 = 1073741824;
                            } else if (layoutParams2.height == -1) {
                                i7 = 1073741824;
                                i8 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
                            } else {
                                i7 = 1073741824;
                                i8 = View.MeasureSpec.makeMeasureSpec(layoutParams2.height, 1073741824);
                            }
                            childAt2.measure(View.MeasureSpec.makeMeasureSpec(i14, i7), i8);
                            i14 = i14;
                        }
                    }
                }
                i15++;
                i5 = 8;
            }
        }
        setMeasuredDimension(size, i4 + getPaddingTop() + getPaddingBottom());
        this.m = z2;
        if (this.e.getViewDragState() != 0 && !z2) {
            this.e.abort();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean b2 = b();
        if (b2) {
            this.e.setEdgeTrackingEnabled(2);
        } else {
            this.e.setEdgeTrackingEnabled(1);
        }
        int i10 = i3 - i;
        int paddingRight = b2 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = b2 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.s) {
            this.b = (!this.m || !this.f) ? 0.0f : 1.0f;
        }
        int i11 = paddingRight;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.a) {
                    int i13 = i10 - paddingLeft;
                    int min = (Math.min(paddingRight, i13 - this.l) - i11) - (layoutParams.leftMargin + layoutParams.rightMargin);
                    this.c = min;
                    int i14 = b2 ? layoutParams.rightMargin : layoutParams.leftMargin;
                    layoutParams.b = ((i11 + i14) + min) + (measuredWidth / 2) > i13;
                    int i15 = (int) (min * this.b);
                    i6 = i14 + i15 + i11;
                    this.b = i15 / this.c;
                    i5 = 0;
                } else if (!this.m || (i9 = this.o) == 0) {
                    i6 = paddingRight;
                    i5 = 0;
                } else {
                    i5 = (int) ((1.0f - this.b) * i9);
                    i6 = paddingRight;
                }
                if (b2) {
                    i7 = (i10 - i6) + i5;
                    i8 = i7 - measuredWidth;
                } else {
                    i8 = i6 - i5;
                    i7 = i8 + measuredWidth;
                }
                childAt.layout(i8, paddingTop, i7, childAt.getMeasuredHeight() + paddingTop);
                paddingRight += childAt.getWidth();
                i11 = i6;
            }
        }
        if (this.s) {
            if (this.m) {
                if (this.o != 0) {
                    a(this.b);
                }
                if (((LayoutParams) this.a.getLayoutParams()).b) {
                    a(this.a, this.b, this.h);
                }
            } else {
                for (int i16 = 0; i16 < childCount; i16++) {
                    a(getChildAt(i16), 0.0f, this.h);
                }
            }
            d(this.a);
            z2 = false;
        } else {
            z2 = false;
        }
        this.s = z2;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.s = true;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!isInTouchMode() && !this.m) {
            this.f = view == this.a;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View childAt;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.m && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
            this.f = !this.e.isViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (!this.m || (this.d && actionMasked != 0)) {
            this.e.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        } else if (actionMasked == 3 || actionMasked == 1) {
            this.e.cancel();
            return false;
        } else {
            if (actionMasked == 0) {
                this.d = false;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.p = x;
                this.q = y;
                if (this.e.isViewUnder(this.a, (int) x, (int) y) && f(this.a)) {
                    z = true;
                    return this.e.shouldInterceptTouchEvent(motionEvent) || z;
                }
            } else if (actionMasked == 2) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float abs = Math.abs(x2 - this.p);
                float abs2 = Math.abs(y2 - this.q);
                if (abs > this.e.getTouchSlop() && abs2 > abs) {
                    this.e.cancel();
                    this.d = true;
                    return false;
                }
            }
            z = false;
            if (this.e.shouldInterceptTouchEvent(motionEvent)) {
                return true;
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.m) {
            return super.onTouchEvent(motionEvent);
        }
        this.e.processTouchEvent(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.p = x;
                this.q = y;
                break;
            case 1:
                if (f(this.a)) {
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    float f = x2 - this.p;
                    float f2 = y2 - this.q;
                    int touchSlop = this.e.getTouchSlop();
                    if ((f * f) + (f2 * f2) < touchSlop * touchSlop && this.e.isViewUnder(this.a, (int) x2, (int) y2)) {
                        a(this.a, 0);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private boolean a(View view, int i) {
        if (!this.s && !a(0.0f, i)) {
            return false;
        }
        this.f = false;
        return true;
    }

    private boolean b(View view, int i) {
        if (!this.s && !a(1.0f, i)) {
            return false;
        }
        this.f = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() {
        openPane();
    }

    public boolean openPane() {
        return b(this.a, 0);
    }

    @Deprecated
    public void smoothSlideClosed() {
        closePane();
    }

    public boolean closePane() {
        return a(this.a, 0);
    }

    public boolean isOpen() {
        return !this.m || this.b == 1.0f;
    }

    @Deprecated
    public boolean canSlide() {
        return this.m;
    }

    public boolean isSlideable() {
        return this.m;
    }

    void a(int i) {
        if (this.a == null) {
            this.b = 0.0f;
            return;
        }
        boolean b2 = b();
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        int width = this.a.getWidth();
        if (b2) {
            i = (getWidth() - i) - width;
        }
        this.b = (i - ((b2 ? getPaddingRight() : getPaddingLeft()) + (b2 ? layoutParams.rightMargin : layoutParams.leftMargin))) / this.c;
        if (this.o != 0) {
            a(this.b);
        }
        if (layoutParams.b) {
            a(this.a, this.b, this.h);
        }
        a(this.a);
    }

    private void a(View view, float f, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f > 0.0f && i != 0) {
            int i2 = (((int) ((((-16777216) & i) >>> 24) * f)) << 24) | (i & 16777215);
            if (layoutParams.c == null) {
                layoutParams.c = new Paint();
            }
            layoutParams.c.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER));
            if (view.getLayerType() != 2) {
                view.setLayerType(2, layoutParams.c);
            }
            e(view);
        } else if (view.getLayerType() != 0) {
            if (layoutParams.c != null) {
                layoutParams.c.setColorFilter(null);
            }
            b bVar = new b(view);
            this.g.add(bVar);
            ViewCompat.postOnAnimation(this, bVar);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int save = canvas.save();
        if (this.m && !layoutParams.a && this.a != null) {
            canvas.getClipBounds(this.t);
            if (b()) {
                Rect rect = this.t;
                rect.left = Math.max(rect.left, this.a.getRight());
            } else {
                Rect rect2 = this.t;
                rect2.right = Math.min(rect2.right, this.a.getLeft());
            }
            canvas.clipRect(this.t);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        return drawChild;
    }

    void e(View view) {
        Field field;
        if (Build.VERSION.SDK_INT >= 17) {
            ViewCompat.setLayerPaint(view, ((LayoutParams) view.getLayoutParams()).c);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (!this.w) {
                try {
                    this.u = View.class.getDeclaredMethod("getDisplayList", null);
                } catch (NoSuchMethodException e) {
                    Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", e);
                }
                try {
                    this.v = View.class.getDeclaredField("mRecreateDisplayList");
                    this.v.setAccessible(true);
                } catch (NoSuchFieldException e2) {
                    Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
                }
                this.w = true;
            }
            if (this.u == null || (field = this.v) == null) {
                view.invalidate();
                return;
            }
            try {
                field.setBoolean(view, true);
                this.u.invoke(view, null);
            } catch (Exception e3) {
                Log.e("SlidingPaneLayout", "Error refreshing display list state", e3);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    boolean a(float f, int i) {
        int i2;
        if (!this.m) {
            return false;
        }
        boolean b2 = b();
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        if (b2) {
            i2 = (int) (getWidth() - (((getPaddingRight() + layoutParams.rightMargin) + (f * this.c)) + this.a.getWidth()));
        } else {
            i2 = (int) (getPaddingLeft() + layoutParams.leftMargin + (f * this.c));
        }
        ViewDragHelper viewDragHelper = this.e;
        View view = this.a;
        if (!viewDragHelper.smoothSlideViewTo(view, i2, view.getTop())) {
            return false;
        }
        a();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.e.continueSettling(true)) {
            return;
        }
        if (!this.m) {
            this.e.abort();
        } else {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(@Nullable Drawable drawable) {
        this.j = drawable;
    }

    public void setShadowDrawableRight(@Nullable Drawable drawable) {
        this.k = drawable;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int i) {
        setShadowDrawable(getResources().getDrawable(i));
    }

    public void setShadowResourceLeft(int i) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), i));
    }

    public void setShadowResourceRight(int i) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), i));
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Drawable drawable;
        int i;
        int i2;
        super.draw(canvas);
        if (b()) {
            drawable = this.k;
        } else {
            drawable = this.j;
        }
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt != null && drawable != null) {
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (b()) {
                i2 = childAt.getRight();
                i = intrinsicWidth + i2;
            } else {
                int left = childAt.getLeft();
                i2 = left - intrinsicWidth;
                i = left;
            }
            drawable.setBounds(i2, top, i, bottom);
            drawable.draw(canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(float r10) {
        /*
            r9 = this;
            boolean r0 = r9.b()
            android.view.View r1 = r9.a
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.slidingpanelayout.widget.SlidingPaneLayout$LayoutParams r1 = (androidx.slidingpanelayout.widget.SlidingPaneLayout.LayoutParams) r1
            boolean r2 = r1.b
            r3 = 0
            if (r2 == 0) goto L_0x001c
            if (r0 == 0) goto L_0x0016
            int r1 = r1.rightMargin
            goto L_0x0018
        L_0x0016:
            int r1 = r1.leftMargin
        L_0x0018:
            if (r1 > 0) goto L_0x001c
            r1 = 1
            goto L_0x001d
        L_0x001c:
            r1 = r3
        L_0x001d:
            int r2 = r9.getChildCount()
        L_0x0021:
            if (r3 >= r2) goto L_0x0059
            android.view.View r4 = r9.getChildAt(r3)
            android.view.View r5 = r9.a
            if (r4 != r5) goto L_0x002c
            goto L_0x0056
        L_0x002c:
            float r5 = r9.n
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r6 - r5
            int r7 = r9.o
            float r8 = (float) r7
            float r5 = r5 * r8
            int r5 = (int) r5
            r9.n = r10
            float r8 = r6 - r10
            float r7 = (float) r7
            float r8 = r8 * r7
            int r7 = (int) r8
            int r5 = r5 - r7
            if (r0 == 0) goto L_0x0042
            int r5 = -r5
        L_0x0042:
            r4.offsetLeftAndRight(r5)
            if (r1 == 0) goto L_0x0056
            if (r0 == 0) goto L_0x004d
            float r5 = r9.n
            float r5 = r5 - r6
            goto L_0x0051
        L_0x004d:
            float r5 = r9.n
            float r5 = r6 - r5
        L_0x0051:
            int r6 = r9.i
            r9.a(r4, r5, r6)
        L_0x0056:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.a(float):void");
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        if (z) {
            if (view.canScrollHorizontally(b() ? i : -i)) {
                return true;
            }
        }
        return false;
    }

    boolean f(View view) {
        if (view == null) {
            return false;
        }
        return this.m && ((LayoutParams) view.getLayoutParams()).b && this.b > 0.0f;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        d dVar = new d(super.onSaveInstanceState());
        dVar.a = isSlideable() ? isOpen() : this.f;
        return dVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof d)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        d dVar = (d) parcelable;
        super.onRestoreInstanceState(dVar.getSuperState());
        if (dVar.a) {
            openPane();
        } else {
            closePane();
        }
        this.f = dVar.a;
    }

    /* loaded from: classes.dex */
    private class c extends ViewDragHelper.Callback {
        c() {
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            if (SlidingPaneLayout.this.d) {
                return false;
            }
            return ((LayoutParams) view.getLayoutParams()).a;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (SlidingPaneLayout.this.e.getViewDragState() != 0) {
                return;
            }
            if (SlidingPaneLayout.this.b == 0.0f) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                slidingPaneLayout.d(slidingPaneLayout.a);
                SlidingPaneLayout slidingPaneLayout2 = SlidingPaneLayout.this;
                slidingPaneLayout2.c(slidingPaneLayout2.a);
                SlidingPaneLayout.this.f = false;
                return;
            }
            SlidingPaneLayout slidingPaneLayout3 = SlidingPaneLayout.this;
            slidingPaneLayout3.b(slidingPaneLayout3.a);
            SlidingPaneLayout.this.f = true;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view, int i) {
            SlidingPaneLayout.this.a();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            SlidingPaneLayout.this.a(i);
            SlidingPaneLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            int i;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int paddingRight = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                if (f < 0.0f || (f == 0.0f && SlidingPaneLayout.this.b > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.c;
                }
                i = (SlidingPaneLayout.this.getWidth() - paddingRight) - SlidingPaneLayout.this.a.getWidth();
            } else {
                i = layoutParams.leftMargin + SlidingPaneLayout.this.getPaddingLeft();
                int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i2 > 0 || (i2 == 0 && SlidingPaneLayout.this.b > 0.5f)) {
                    i += SlidingPaneLayout.this.c;
                }
            }
            SlidingPaneLayout.this.e.settleCapturedViewAt(i, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.c;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) SlidingPaneLayout.this.a.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int width = SlidingPaneLayout.this.getWidth() - ((SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin) + SlidingPaneLayout.this.a.getWidth());
                return Math.max(Math.min(i, width), width - SlidingPaneLayout.this.c);
            }
            int paddingLeft = SlidingPaneLayout.this.getPaddingLeft() + layoutParams.leftMargin;
            return Math.min(Math.max(i, paddingLeft), SlidingPaneLayout.this.c + paddingLeft);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            SlidingPaneLayout.this.e.captureChildView(SlidingPaneLayout.this.a, i2);
        }
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static final int[] d = {16843137};
        boolean a;
        boolean b;
        Paint c;
        public float weight;

        public LayoutParams() {
            super(-1, -1);
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.weight = 0.0f;
            this.weight = layoutParams.weight;
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.weight = 0.0f;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, d);
            this.weight = obtainStyledAttributes.getFloat(0, 0.0f);
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d extends AbsSavedState {
        public static final Parcelable.Creator<d> CREATOR = new Parcelable.ClassLoaderCreator<d>() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout.d.1
            /* renamed from: a */
            public d createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new d(parcel, null);
            }

            /* renamed from: a */
            public d createFromParcel(Parcel parcel) {
                return new d(parcel, null);
            }

            /* renamed from: a */
            public d[] newArray(int i) {
                return new d[i];
            }
        };
        boolean a;

        d(Parcelable parcelable) {
            super(parcelable);
        }

        d(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a ? 1 : 0);
        }
    }

    /* loaded from: classes.dex */
    class a extends AccessibilityDelegateCompat {
        private final Rect b = new Rect();

        a() {
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, obtain);
            a(accessibilityNodeInfoCompat, obtain);
            obtain.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(view);
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
            }
            int childCount = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i);
                if (!b(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(childAt, 1);
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!b(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        public boolean b(View view) {
            return SlidingPaneLayout.this.f(view);
        }

        private void a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.b;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        final View a;

        b(View view) {
            this.a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.getParent() == SlidingPaneLayout.this) {
                this.a.setLayerType(0, null);
                SlidingPaneLayout.this.e(this.a);
            }
            SlidingPaneLayout.this.g.remove(this);
        }
    }

    boolean b() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
