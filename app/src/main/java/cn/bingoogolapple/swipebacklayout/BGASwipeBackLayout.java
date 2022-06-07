package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BGASwipeBackLayout extends ViewGroup {
    static final e h;
    private float A;
    private boolean B;
    private boolean C;
    View a;
    float b;
    int c;
    boolean d;
    final ViewDragHelper e;
    boolean f;
    final ArrayList<b> g;
    private int i;
    private int j;
    private Drawable k;
    private Drawable l;
    private final int m;
    private boolean n;
    private float o;
    private int p;
    private float q;
    private float r;
    private PanelSlideListener s;
    private boolean t;
    private final Rect u;
    private boolean v;
    private boolean w;
    private b x;
    private View y;
    private Activity z;

    /* loaded from: classes.dex */
    public interface PanelSlideListener {
        void onPanelClosed(View view);

        void onPanelOpened(View view);

        void onPanelSlide(View view, float f);
    }

    /* loaded from: classes.dex */
    public static class SimplePanelSlideListener implements PanelSlideListener {
        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
        public void onPanelClosed(View view) {
        }

        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
        public void onPanelOpened(View view) {
        }

        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
        public void onPanelSlide(View view, float f) {
        }
    }

    /* loaded from: classes.dex */
    public interface e {
        void a(BGASwipeBackLayout bGASwipeBackLayout, View view);
    }

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 17) {
            h = new h();
        } else if (i >= 16) {
            h = new g();
        } else {
            h = new f();
        }
    }

    public void a(Activity activity) {
        this.z = activity;
        setSliderFadeColor(0);
        this.x = new b(activity);
        addView(this.x, 0, new LayoutParams(-1, -1));
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        this.y = viewGroup.getChildAt(0);
        viewGroup.removeView(this.y);
        viewGroup.addView(this);
        addView(this.y, 1, new LayoutParams(-1, -1));
    }

    public void setSwipeBackEnable(boolean z) {
        this.v = z;
    }

    public void setIsOnlyTrackingLeftEdge(boolean z) {
        this.w = z;
    }

    public void setIsWeChatStyle(boolean z) {
        this.x.c(z);
    }

    public void setSwipeBackThreshold(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.A = f2;
    }

    public void setIsNavigationBarOverlap(boolean z) {
        this.B = z;
    }

    public boolean a() {
        return this.C;
    }

    public boolean d() {
        return this.v && a.a().b();
    }

    public void setShadowResId(@DrawableRes int i) {
        this.x.a(i);
    }

    public void setIsNeedShowShadow(boolean z) {
        this.x.a(z);
    }

    public void setIsShadowAlphaGradient(boolean z) {
        this.x.b(z);
    }

    public BGASwipeBackLayout(Context context) {
        this(context, null);
    }

    public BGASwipeBackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BGASwipeBackLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.i = -858993460;
        this.t = true;
        this.u = new Rect();
        this.g = new ArrayList<>();
        this.v = true;
        this.w = true;
        this.A = 0.3f;
        this.B = false;
        float f2 = context.getResources().getDisplayMetrics().density;
        this.m = 0;
        ViewConfiguration.get(context);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new a());
        ViewCompat.setImportantForAccessibility(this, 1);
        this.e = ViewDragHelper.create(this, 0.5f, new c());
        this.e.setMinVelocity(f2 * 400.0f);
    }

    public void setParallaxDistance(int i) {
        this.p = i;
        requestLayout();
    }

    public int getParallaxDistance() {
        return this.p;
    }

    public void setSliderFadeColor(@ColorInt int i) {
        this.i = i;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.i;
    }

    public void setCoveredFadeColor(@ColorInt int i) {
        this.j = i;
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.j;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.s = panelSlideListener;
    }

    void a(View view) {
        this.x.a(1.0f - this.b);
        this.x.b(this.b);
        PanelSlideListener panelSlideListener = this.s;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelSlide(view, this.b);
        }
    }

    void b(View view) {
        PanelSlideListener panelSlideListener = this.s;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelOpened(view);
        }
        sendAccessibilityEvent(32);
        this.x.d(true);
    }

    void c(View view) {
        this.x.b();
        PanelSlideListener panelSlideListener = this.s;
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
        boolean c2 = c();
        int width = c2 ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = c2 ? getPaddingLeft() : getWidth() - getPaddingRight();
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
                    c2 = c2;
                } else {
                    int max = Math.max(c2 ? paddingLeft : width, childAt.getLeft());
                    int max2 = Math.max(paddingTop, childAt.getTop());
                    if (c2) {
                        c2 = c2;
                        i5 = width;
                    } else {
                        c2 = c2;
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

    void b() {
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
        this.t = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.t = true;
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
        int mode2 = View.MeasureSpec.getMode(i2);
        int d2 = c.d(this.z);
        int c2 = c.c(this.z);
        if (mode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if (mode != Integer.MIN_VALUE && mode == 0) {
                d2 = 300;
            }
        } else if (mode2 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if (mode2 == 0) {
                c2 = 300;
                mode2 = Integer.MIN_VALUE;
            }
        }
        boolean z = false;
        if (mode2 == Integer.MIN_VALUE) {
            i3 = (c2 - getPaddingTop()) - getPaddingBottom();
            i4 = 0;
        } else if (mode2 != 1073741824) {
            i4 = 0;
            i3 = -1;
        } else {
            i3 = (c2 - getPaddingTop()) - getPaddingBottom();
            i4 = i3;
        }
        if (!this.B && c.b(this.z)) {
            i3 -= c.a(this.z);
        }
        if (this.B && !c.b(this.z)) {
            d2 += c.a(this.z);
        }
        int paddingLeft = (d2 - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
        }
        this.a = null;
        boolean z2 = false;
        int i11 = paddingLeft;
        float f2 = 0.0f;
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
                    f2 += layoutParams.weight;
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
        if (z2 || f2 > 0.0f) {
            int i14 = paddingLeft - this.m;
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
                                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2 + ((int) ((layoutParams2.weight * Math.max(0, i11)) / f2)), 1073741824), i6);
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
        setMeasuredDimension(d2, i4 + getPaddingTop() + getPaddingBottom());
        this.n = z2;
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
        boolean c2 = c();
        if (c2) {
            this.e.setEdgeTrackingEnabled(2);
        } else {
            this.e.setEdgeTrackingEnabled(1);
        }
        int i10 = i3 - i;
        int paddingRight = c2 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = c2 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.t) {
            this.b = (!this.n || !this.f) ? 0.0f : 1.0f;
        }
        int i11 = paddingRight;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.a) {
                    int i13 = i10 - paddingLeft;
                    int min = (Math.min(paddingRight, i13 - this.m) - i11) - (layoutParams.leftMargin + layoutParams.rightMargin);
                    this.c = min;
                    int i14 = c2 ? layoutParams.rightMargin : layoutParams.leftMargin;
                    layoutParams.b = ((i11 + i14) + min) + (measuredWidth / 2) > i13;
                    int i15 = (int) (min * this.b);
                    i6 = i14 + i15 + i11;
                    this.b = i15 / this.c;
                    i5 = 0;
                } else if (!this.n || (i9 = this.p) == 0) {
                    i6 = paddingRight;
                    i5 = 0;
                } else {
                    i5 = (int) ((1.0f - this.b) * i9);
                    i6 = paddingRight;
                }
                if (c2) {
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
        if (this.t) {
            if (this.n) {
                if (this.p != 0) {
                    a(this.b);
                }
                if (((LayoutParams) this.a.getLayoutParams()).b) {
                    a(this.a, this.b, this.i);
                }
            } else {
                for (int i16 = 0; i16 < childCount; i16++) {
                    a(getChildAt(i16), 0.0f, this.i);
                }
            }
            d(this.a);
            z2 = false;
        } else {
            z2 = false;
        }
        this.t = z2;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.t = true;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!isInTouchMode() && !this.n) {
            this.f = view == this.a;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View childAt;
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (!this.n && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
            this.f = !this.e.isViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (!d()) {
            this.e.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        } else if (!this.n || (this.d && actionMasked != 0)) {
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
                this.q = x;
                this.r = y;
                if (this.e.isViewUnder(this.a, (int) x, (int) y) && f(this.a)) {
                    z = true;
                    return this.e.shouldInterceptTouchEvent(motionEvent) || z;
                }
            } else if (actionMasked == 2) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float abs = Math.abs(x2 - this.q);
                float abs2 = Math.abs(y2 - this.r);
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
        if (!d()) {
            return super.onTouchEvent(motionEvent);
        }
        if (!this.n) {
            return super.onTouchEvent(motionEvent);
        }
        this.e.processTouchEvent(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.q = x;
                this.r = y;
                break;
            case 1:
                if (f(this.a)) {
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    float f2 = x2 - this.q;
                    float f3 = y2 - this.r;
                    int touchSlop = this.e.getTouchSlop();
                    if ((f2 * f2) + (f3 * f3) < touchSlop * touchSlop && this.e.isViewUnder(this.a, (int) x2, (int) y2)) {
                        a(this.a, 0);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private boolean a(View view, int i) {
        if (!this.t && !a(0.0f, i)) {
            return false;
        }
        this.f = false;
        return true;
    }

    private boolean b(View view, int i) {
        if (!this.t && !a(1.0f, i)) {
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
        return !this.n || this.b == 1.0f;
    }

    @Deprecated
    public boolean canSlide() {
        return this.n;
    }

    public boolean isSlideable() {
        return this.n;
    }

    void a(int i) {
        if (this.a == null) {
            this.b = 0.0f;
            return;
        }
        boolean c2 = c();
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        this.b = ((c2 ? (getWidth() - i) - this.a.getWidth() : i) - ((c2 ? getPaddingRight() : getPaddingLeft()) + (c2 ? layoutParams.rightMargin : layoutParams.leftMargin))) / this.c;
        if (this.p != 0) {
            a(this.b);
        }
        if (layoutParams.b) {
            a(this.a, this.b, this.i);
        }
        b bVar = this.x;
        ViewCompat.setTranslationX(bVar, (-bVar.getMeasuredWidth()) + i);
        a(this.a);
    }

    private void a(View view, float f2, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f2 > 0.0f && i != 0) {
            int i2 = (((int) ((((-16777216) & i) >>> 24) * f2)) << 24) | (i & 16777215);
            if (layoutParams.c == null) {
                layoutParams.c = new Paint();
            }
            layoutParams.c.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER));
            if (ViewCompat.getLayerType(view) != 2) {
                ViewCompat.setLayerType(view, 2, layoutParams.c);
            }
            e(view);
        } else if (ViewCompat.getLayerType(view) != 0) {
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
        boolean z;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int save = canvas.save();
        if (this.n && !layoutParams.a && this.a != null) {
            canvas.getClipBounds(this.u);
            if (c()) {
                Rect rect = this.u;
                rect.left = Math.max(rect.left, this.a.getRight());
            } else {
                Rect rect2 = this.u;
                rect2.right = Math.min(rect2.right, this.a.getLeft());
            }
            canvas.clipRect(this.u);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            z = super.drawChild(canvas, view, j);
        } else if (!layoutParams.b || this.b <= 0.0f) {
            if (view.isDrawingCacheEnabled()) {
                view.setDrawingCacheEnabled(false);
            }
            z = super.drawChild(canvas, view, j);
        } else {
            if (!view.isDrawingCacheEnabled()) {
                view.setDrawingCacheEnabled(true);
            }
            Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null) {
                canvas.drawBitmap(drawingCache, view.getLeft(), view.getTop(), layoutParams.c);
                z = false;
            } else {
                Log.e("SlidingPaneLayout", "drawChild: child view " + view + " returned null drawing cache");
                z = super.drawChild(canvas, view, j);
            }
        }
        canvas.restoreToCount(save);
        return z;
    }

    void e(View view) {
        h.a(this, view);
    }

    boolean a(float f2, int i) {
        int i2;
        if (!this.n) {
            return false;
        }
        boolean c2 = c();
        LayoutParams layoutParams = (LayoutParams) this.a.getLayoutParams();
        if (c2) {
            i2 = (int) (getWidth() - (((getPaddingRight() + layoutParams.rightMargin) + (f2 * this.c)) + this.a.getWidth()));
        } else {
            i2 = (int) (getPaddingLeft() + layoutParams.leftMargin + (f2 * this.c));
        }
        ViewDragHelper viewDragHelper = this.e;
        View view = this.a;
        if (!viewDragHelper.smoothSlideViewTo(view, i2, view.getTop())) {
            return false;
        }
        b();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.e.continueSettling(true)) {
            return;
        }
        if (!this.n) {
            this.e.abort();
        } else {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(Drawable drawable) {
        this.k = drawable;
    }

    public void setShadowDrawableRight(Drawable drawable) {
        this.l = drawable;
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
        if (c()) {
            drawable = this.l;
        } else {
            drawable = this.k;
        }
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt != null && drawable != null) {
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (c()) {
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
            boolean r0 = r9.c()
            android.view.View r1 = r9.a
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout$LayoutParams r1 = (cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.LayoutParams) r1
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
            float r5 = r9.o
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r6 - r5
            int r7 = r9.p
            float r8 = (float) r7
            float r5 = r5 * r8
            int r5 = (int) r5
            r9.o = r10
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
            float r5 = r9.o
            float r5 = r5 - r6
            goto L_0x0051
        L_0x004d:
            float r5 = r9.o
            float r5 = r6 - r5
        L_0x0051:
            int r6 = r9.j
            r9.a(r4, r5, r6)
        L_0x0056:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.a(float):void");
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
            if (ViewCompat.canScrollHorizontally(view, c() ? i : -i)) {
                return true;
            }
        }
        return false;
    }

    boolean f(View view) {
        if (view == null) {
            return false;
        }
        return this.n && ((LayoutParams) view.getLayoutParams()).b && this.b > 0.0f;
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class c extends ViewDragHelper.Callback {
        c() {
            BGASwipeBackLayout.this = r1;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            return !BGASwipeBackLayout.this.d && !BGASwipeBackLayout.this.w && BGASwipeBackLayout.this.d() && ((LayoutParams) view.getLayoutParams()).a;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (BGASwipeBackLayout.this.e.getViewDragState() == 0) {
                if (BGASwipeBackLayout.this.b == 0.0f) {
                    BGASwipeBackLayout bGASwipeBackLayout = BGASwipeBackLayout.this;
                    bGASwipeBackLayout.d(bGASwipeBackLayout.a);
                    BGASwipeBackLayout bGASwipeBackLayout2 = BGASwipeBackLayout.this;
                    bGASwipeBackLayout2.c(bGASwipeBackLayout2.a);
                    BGASwipeBackLayout.this.f = false;
                } else {
                    BGASwipeBackLayout bGASwipeBackLayout3 = BGASwipeBackLayout.this;
                    bGASwipeBackLayout3.b(bGASwipeBackLayout3.a);
                    BGASwipeBackLayout.this.f = true;
                }
                BGASwipeBackLayout.this.C = false;
                return;
            }
            BGASwipeBackLayout.this.C = true;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view, int i) {
            if (BGASwipeBackLayout.this.d()) {
                BGASwipeBackLayout.this.x.a();
            }
            BGASwipeBackLayout.this.b();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            BGASwipeBackLayout.this.a(i);
            BGASwipeBackLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            int i;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (BGASwipeBackLayout.this.c()) {
                int paddingRight = BGASwipeBackLayout.this.getPaddingRight() + layoutParams.rightMargin;
                if (f < 0.0f || (f == 0.0f && BGASwipeBackLayout.this.b > BGASwipeBackLayout.this.A)) {
                    paddingRight += BGASwipeBackLayout.this.c;
                }
                i = (BGASwipeBackLayout.this.getWidth() - paddingRight) - BGASwipeBackLayout.this.a.getWidth();
            } else {
                i = layoutParams.leftMargin + BGASwipeBackLayout.this.getPaddingLeft();
                int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i2 > 0 || (i2 == 0 && BGASwipeBackLayout.this.b > BGASwipeBackLayout.this.A)) {
                    i += BGASwipeBackLayout.this.c;
                }
            }
            BGASwipeBackLayout.this.e.settleCapturedViewAt(i, view.getTop());
            BGASwipeBackLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            return BGASwipeBackLayout.this.c;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) BGASwipeBackLayout.this.a.getLayoutParams();
            if (BGASwipeBackLayout.this.c()) {
                int width = BGASwipeBackLayout.this.getWidth() - ((BGASwipeBackLayout.this.getPaddingRight() + layoutParams.rightMargin) + BGASwipeBackLayout.this.a.getWidth());
                return Math.max(Math.min(i, width), width - BGASwipeBackLayout.this.c);
            }
            int paddingLeft = BGASwipeBackLayout.this.getPaddingLeft() + layoutParams.leftMargin;
            return Math.min(Math.max(i, paddingLeft), BGASwipeBackLayout.this.c + paddingLeft);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            if (BGASwipeBackLayout.this.d()) {
                BGASwipeBackLayout.this.e.captureChildView(BGASwipeBackLayout.this.a, i2);
            }
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

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.weight = 0.0f;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.weight = 0.0f;
            this.weight = layoutParams.weight;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.weight = 0.0f;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, d);
            this.weight = obtainStyledAttributes.getFloat(0, 0.0f);
            obtainStyledAttributes.recycle();
        }
    }

    /* loaded from: classes.dex */
    public static class d extends AbsSavedState {
        public static final Parcelable.Creator<d> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<d>() { // from class: cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.d.1
            /* renamed from: a */
            public d createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new d(parcel, classLoader);
            }

            /* renamed from: a */
            public d[] newArray(int i) {
                return new d[i];
            }
        });
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class f implements e {
        f() {
        }

        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.e
        public void a(BGASwipeBackLayout bGASwipeBackLayout, View view) {
            ViewCompat.postInvalidateOnAnimation(bGASwipeBackLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    /* loaded from: classes.dex */
    static class g extends f {
        private Method a;
        private Field b;

        g() {
            try {
                this.a = View.class.getDeclaredMethod("getDisplayList", null);
            } catch (NoSuchMethodException e) {
                Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", e);
            }
            try {
                this.b = View.class.getDeclaredField("mRecreateDisplayList");
                this.b.setAccessible(true);
            } catch (NoSuchFieldException e2) {
                Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2);
            }
        }

        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.f, cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.e
        public void a(BGASwipeBackLayout bGASwipeBackLayout, View view) {
            Field field;
            if (this.a == null || (field = this.b) == null) {
                view.invalidate();
                return;
            }
            try {
                field.setBoolean(view, true);
                this.a.invoke(view, null);
            } catch (Exception e) {
                Log.e("SlidingPaneLayout", "Error refreshing display list state", e);
            }
            super.a(bGASwipeBackLayout, view);
        }
    }

    /* loaded from: classes.dex */
    static class h extends f {
        h() {
        }

        @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.f, cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.e
        public void a(BGASwipeBackLayout bGASwipeBackLayout, View view) {
            ViewCompat.setLayerPaint(view, ((LayoutParams) view.getLayoutParams()).c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a extends AccessibilityDelegateCompat {
        private final Rect b = new Rect();

        a() {
            BGASwipeBackLayout.this = r1;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, obtain);
            a(accessibilityNodeInfoCompat, obtain);
            obtain.recycle();
            accessibilityNodeInfoCompat.setClassName(BGASwipeBackLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            ViewParent parentForAccessibility = ViewCompat.getParentForAccessibility(view);
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
            }
            int childCount = BGASwipeBackLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = BGASwipeBackLayout.this.getChildAt(i);
                if (!b(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(childAt, 1);
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(BGASwipeBackLayout.class.getName());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!b(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        public boolean b(View view) {
            return BGASwipeBackLayout.this.f(view);
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

    /* loaded from: classes.dex */
    public class b implements Runnable {
        final View a;

        b(View view) {
            BGASwipeBackLayout.this = r1;
            this.a = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.getParent() == this) {
                ViewCompat.setLayerType(this.a, 0, null);
                BGASwipeBackLayout.this.e(this.a);
            }
            BGASwipeBackLayout.this.g.remove(this);
        }
    }

    boolean c() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
