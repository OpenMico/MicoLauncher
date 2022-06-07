package androidx.swiperefreshlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ListViewCompat;

/* loaded from: classes.dex */
public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingChild, NestedScrollingParent {
    private static final int[] C = {16842766};
    public static final int DEFAULT = 1;
    public static final int DEFAULT_SLINGSHOT_DISTANCE = -1;
    public static final int LARGE = 0;
    private static final String l = "SwipeRefreshLayout";
    private boolean A;
    private final DecelerateInterpolator B;
    private int D;
    private Animation E;
    private Animation F;
    private Animation G;
    private Animation H;
    private Animation I;
    private int J;
    private OnChildScrollUpCallback K;
    private Animation.AnimationListener L;
    private final Animation M;
    private final Animation N;
    OnRefreshListener a;
    boolean b;
    int c;
    boolean d;
    a e;
    float f;
    int g;
    int h;
    CircularProgressDrawable i;
    boolean j;
    boolean k;
    private View m;
    protected int mFrom;
    protected int mOriginalOffsetTop;
    private int n;
    private float o;
    private float p;
    private final NestedScrollingParentHelper q;
    private final NestedScrollingChildHelper r;
    private final int[] s;
    private final int[] t;
    private boolean u;
    private int v;
    private float w;
    private float x;
    private boolean y;
    private int z;

    /* loaded from: classes.dex */
    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view);
    }

    /* loaded from: classes.dex */
    public interface OnRefreshListener {
        void onRefresh();
    }

    void a() {
        this.e.clearAnimation();
        this.i.stop();
        this.e.setVisibility(8);
        setColorViewAlpha(255);
        if (this.d) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.c);
        }
        this.c = this.e.getTop();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (!z) {
            a();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    private void setColorViewAlpha(int i) {
        this.e.getBackground().setAlpha(i);
        this.i.setAlpha(i);
    }

    public void setProgressViewOffset(boolean z, int i, int i2) {
        this.d = z;
        this.mOriginalOffsetTop = i;
        this.g = i2;
        this.k = true;
        a();
        this.b = false;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    public int getProgressViewEndOffset() {
        return this.g;
    }

    public void setProgressViewEndTarget(boolean z, int i) {
        this.g = i;
        this.d = z;
        this.e.invalidate();
    }

    public void setSlingshotDistance(@Px int i) {
        this.h = i;
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i == 0) {
                this.J = (int) (displayMetrics.density * 56.0f);
            } else {
                this.J = (int) (displayMetrics.density * 40.0f);
            }
            this.e.setImageDrawable(null);
            this.i.setStyle(i);
            this.e.setImageDrawable(this.i);
        }
    }

    public SwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = false;
        this.o = -1.0f;
        this.s = new int[2];
        this.t = new int[2];
        this.z = -1;
        this.D = -1;
        this.L = new Animation.AnimationListener() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.b) {
                    SwipeRefreshLayout.this.i.setAlpha(255);
                    SwipeRefreshLayout.this.i.start();
                    if (SwipeRefreshLayout.this.j && SwipeRefreshLayout.this.a != null) {
                        SwipeRefreshLayout.this.a.onRefresh();
                    }
                    SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
                    swipeRefreshLayout.c = swipeRefreshLayout.e.getTop();
                    return;
                }
                SwipeRefreshLayout.this.a();
            }
        };
        this.M = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.6
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                int i;
                if (!SwipeRefreshLayout.this.k) {
                    i = SwipeRefreshLayout.this.g - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop);
                } else {
                    i = SwipeRefreshLayout.this.g;
                }
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((SwipeRefreshLayout.this.mFrom + ((int) ((i - SwipeRefreshLayout.this.mFrom) * f))) - SwipeRefreshLayout.this.e.getTop());
                SwipeRefreshLayout.this.i.setArrowScale(1.0f - f);
            }
        };
        this.N = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.7
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.a(f);
            }
        };
        this.n = ViewConfiguration.get(context).getScaledTouchSlop();
        this.v = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.B = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.J = (int) (displayMetrics.density * 40.0f);
        b();
        setChildrenDrawingOrderEnabled(true);
        this.g = (int) (displayMetrics.density * 64.0f);
        this.o = this.g;
        this.q = new NestedScrollingParentHelper(this);
        this.r = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        int i = -this.J;
        this.c = i;
        this.mOriginalOffsetTop = i;
        a(1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        int i3 = this.D;
        return i3 < 0 ? i2 : i2 == i + (-1) ? i3 : i2 >= i3 ? i2 + 1 : i2;
    }

    private void b() {
        this.e = new a(getContext(), -328966);
        this.i = new CircularProgressDrawable(getContext());
        this.i.setStyle(1);
        this.e.setImageDrawable(this.i);
        this.e.setVisibility(8);
        addView(this.e);
    }

    public void setOnRefreshListener(@Nullable OnRefreshListener onRefreshListener) {
        this.a = onRefreshListener;
    }

    public void setRefreshing(boolean z) {
        int i;
        if (!z || this.b == z) {
            a(z, false);
            return;
        }
        this.b = z;
        if (!this.k) {
            i = this.g + this.mOriginalOffsetTop;
        } else {
            i = this.g;
        }
        setTargetOffsetTopAndBottom(i - this.c);
        this.j = false;
        b(this.L);
    }

    private void b(Animation.AnimationListener animationListener) {
        this.e.setVisibility(0);
        this.i.setAlpha(255);
        this.E = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.2
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f);
            }
        };
        this.E.setDuration(this.v);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.E);
    }

    void setAnimationProgress(float f) {
        this.e.setScaleX(f);
        this.e.setScaleY(f);
    }

    private void a(boolean z, boolean z2) {
        if (this.b != z) {
            this.j = z2;
            e();
            this.b = z;
            if (this.b) {
                a(this.c, this.L);
            } else {
                a(this.L);
            }
        }
    }

    void a(Animation.AnimationListener animationListener) {
        this.F = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.3
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
            }
        };
        this.F.setDuration(150L);
        this.e.a(animationListener);
        this.e.clearAnimation();
        this.e.startAnimation(this.F);
    }

    private void c() {
        this.G = a(this.i.getAlpha(), 76);
    }

    private void d() {
        this.H = a(this.i.getAlpha(), 255);
    }

    private Animation a(final int i, final int i2) {
        Animation animation = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.4
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                CircularProgressDrawable circularProgressDrawable = SwipeRefreshLayout.this.i;
                int i3 = i;
                circularProgressDrawable.setAlpha((int) (i3 + ((i2 - i3) * f)));
            }
        };
        animation.setDuration(300L);
        this.e.a(null);
        this.e.clearAnimation();
        this.e.startAnimation(animation);
        return animation;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        setProgressBackgroundColorSchemeResource(i);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int i) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i));
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int i) {
        this.e.setBackgroundColor(i);
    }

    @Deprecated
    public void setColorScheme(@ColorRes int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeResources(@ColorRes int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = ContextCompat.getColor(context, iArr[i]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        e();
        this.i.setColorSchemeColors(iArr);
    }

    public boolean isRefreshing() {
        return this.b;
    }

    private void e() {
        if (this.m == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.e)) {
                    this.m = childAt;
                    return;
                }
            }
        }
    }

    public void setDistanceToTriggerSync(int i) {
        this.o = i;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.m == null) {
                e();
            }
            View view = this.m;
            if (view != null) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
                int measuredWidth2 = this.e.getMeasuredWidth();
                int measuredHeight2 = this.e.getMeasuredHeight();
                int i5 = measuredWidth / 2;
                int i6 = measuredWidth2 / 2;
                int i7 = this.c;
                this.e.layout(i5 - i6, i7, i5 + i6, measuredHeight2 + i7);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.m == null) {
            e();
        }
        View view = this.m;
        if (view != null) {
            view.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.e.measure(View.MeasureSpec.makeMeasureSpec(this.J, 1073741824), View.MeasureSpec.makeMeasureSpec(this.J, 1073741824));
            this.D = -1;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                if (getChildAt(i3) == this.e) {
                    this.D = i3;
                    return;
                }
            }
        }
    }

    public int getProgressCircleDiameter() {
        return this.J;
    }

    public boolean canChildScrollUp() {
        OnChildScrollUpCallback onChildScrollUpCallback = this.K;
        if (onChildScrollUpCallback != null) {
            return onChildScrollUpCallback.canChildScrollUp(this, this.m);
        }
        View view = this.m;
        if (view instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) view, -1);
        }
        return view.canScrollVertically(-1);
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback onChildScrollUpCallback) {
        this.K = onChildScrollUpCallback;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        e();
        int actionMasked = motionEvent.getActionMasked();
        if (this.A && actionMasked == 0) {
            this.A = false;
        }
        if (!isEnabled() || this.A || canChildScrollUp() || this.b || this.u) {
            return false;
        }
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.e.getTop());
                    this.z = motionEvent.getPointerId(0);
                    this.y = false;
                    int findPointerIndex = motionEvent.findPointerIndex(this.z);
                    if (findPointerIndex >= 0) {
                        this.x = motionEvent.getY(findPointerIndex);
                        break;
                    } else {
                        return false;
                    }
                case 1:
                case 3:
                    this.y = false;
                    this.z = -1;
                    break;
                case 2:
                    int i = this.z;
                    if (i != -1) {
                        int findPointerIndex2 = motionEvent.findPointerIndex(i);
                        if (findPointerIndex2 >= 0) {
                            d(motionEvent.getY(findPointerIndex2));
                            break;
                        } else {
                            return false;
                        }
                    } else {
                        Log.e(l, "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
            }
        } else {
            a(motionEvent);
        }
        return this.y;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (Build.VERSION.SDK_INT >= 21 || !(this.m instanceof AbsListView)) {
            View view = this.m;
            if (view == null || ViewCompat.isNestedScrollingEnabled(view)) {
                super.requestDisallowInterceptTouchEvent(z);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return isEnabled() && !this.A && !this.b && (i & 2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.q.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(i & 2);
        this.p = 0.0f;
        this.u = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            float f = this.p;
            if (f > 0.0f) {
                float f2 = i2;
                if (f2 > f) {
                    iArr[1] = i2 - ((int) f);
                    this.p = 0.0f;
                } else {
                    this.p = f - f2;
                    iArr[1] = i2;
                }
                b(this.p);
            }
        }
        if (this.k && i2 > 0 && this.p == 0.0f && Math.abs(i2 - iArr[1]) > 0) {
            this.e.setVisibility(8);
        }
        int[] iArr2 = this.s;
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.q.getNestedScrollAxes();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view) {
        this.q.onStopNestedScroll(view);
        this.u = false;
        float f = this.p;
        if (f > 0.0f) {
            c(f);
            this.p = 0.0f;
        }
        stopNestedScroll();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        dispatchNestedScroll(i, i2, i3, i4, this.t);
        int i5 = i4 + this.t[1];
        if (i5 < 0 && !canChildScrollUp()) {
            this.p += Math.abs(i5);
            b(this.p);
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        this.r.setNestedScrollingEnabled(z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.r.isNestedScrollingEnabled();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return this.r.startNestedScroll(i);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        this.r.stopNestedScroll();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return this.r.hasNestedScrollingParent();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.r.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.r.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.r.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.r.dispatchNestedPreFling(f, f2);
    }

    private boolean a(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    private void b(float f) {
        this.i.setArrowEnabled(true);
        float min = Math.min(1.0f, Math.abs(f / this.o));
        float max = (((float) Math.max(min - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float abs = Math.abs(f) - this.o;
        int i = this.h;
        if (i <= 0) {
            i = this.k ? this.g - this.mOriginalOffsetTop : this.g;
        }
        float f2 = i;
        double max2 = Math.max(0.0f, Math.min(abs, f2 * 2.0f) / f2) / 4.0f;
        float pow = ((float) (max2 - Math.pow(max2, 2.0d))) * 2.0f;
        int i2 = this.mOriginalOffsetTop + ((int) ((f2 * min) + (f2 * pow * 2.0f)));
        if (this.e.getVisibility() != 0) {
            this.e.setVisibility(0);
        }
        if (!this.d) {
            this.e.setScaleX(1.0f);
            this.e.setScaleY(1.0f);
        }
        if (this.d) {
            setAnimationProgress(Math.min(1.0f, f / this.o));
        }
        if (f < this.o) {
            if (this.i.getAlpha() > 76 && !a(this.G)) {
                c();
            }
        } else if (this.i.getAlpha() < 255 && !a(this.H)) {
            d();
        }
        this.i.setStartEndTrim(0.0f, Math.min(0.8f, max * 0.8f));
        this.i.setArrowScale(Math.min(1.0f, max));
        this.i.setProgressRotation((((max * 0.4f) - 0.25f) + (pow * 2.0f)) * 0.5f);
        setTargetOffsetTopAndBottom(i2 - this.c);
    }

    private void c(float f) {
        if (f > this.o) {
            a(true, true);
            return;
        }
        this.b = false;
        this.i.setStartEndTrim(0.0f, 0.0f);
        Animation.AnimationListener animationListener = null;
        if (!this.d) {
            animationListener = new Animation.AnimationListener() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.5
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    if (!SwipeRefreshLayout.this.d) {
                        SwipeRefreshLayout.this.a((Animation.AnimationListener) null);
                    }
                }
            };
        }
        b(this.c, animationListener);
        this.i.setArrowEnabled(false);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.A && actionMasked == 0) {
            this.A = false;
        }
        if (!isEnabled() || this.A || canChildScrollUp() || this.b || this.u) {
            return false;
        }
        switch (actionMasked) {
            case 0:
                this.z = motionEvent.getPointerId(0);
                this.y = false;
                return true;
            case 1:
                int findPointerIndex = motionEvent.findPointerIndex(this.z);
                if (findPointerIndex < 0) {
                    Log.e(l, "Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }
                if (this.y) {
                    this.y = false;
                    c((motionEvent.getY(findPointerIndex) - this.w) * 0.5f);
                }
                this.z = -1;
                return false;
            case 2:
                int findPointerIndex2 = motionEvent.findPointerIndex(this.z);
                if (findPointerIndex2 < 0) {
                    Log.e(l, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                float y = motionEvent.getY(findPointerIndex2);
                d(y);
                if (!this.y) {
                    return true;
                }
                float f = (y - this.w) * 0.5f;
                if (f <= 0.0f) {
                    return false;
                }
                b(f);
                return true;
            case 3:
                return false;
            case 4:
            default:
                return true;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                if (actionIndex < 0) {
                    Log.e(l, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.z = motionEvent.getPointerId(actionIndex);
                return true;
            case 6:
                a(motionEvent);
                return true;
        }
    }

    private void d(float f) {
        float f2 = this.x;
        int i = this.n;
        if (f - f2 > i && !this.y) {
            this.w = f2 + i;
            this.y = true;
            this.i.setAlpha(76);
        }
    }

    private void a(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        this.M.reset();
        this.M.setDuration(200L);
        this.M.setInterpolator(this.B);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.M);
    }

    private void b(int i, Animation.AnimationListener animationListener) {
        if (this.d) {
            c(i, animationListener);
            return;
        }
        this.mFrom = i;
        this.N.reset();
        this.N.setDuration(200L);
        this.N.setInterpolator(this.B);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.N);
    }

    void a(float f) {
        int i = this.mFrom;
        setTargetOffsetTopAndBottom((i + ((int) ((this.mOriginalOffsetTop - i) * f))) - this.e.getTop());
    }

    private void c(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        this.f = this.e.getScaleX();
        this.I = new Animation() { // from class: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.8
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.f + ((-SwipeRefreshLayout.this.f) * f));
                SwipeRefreshLayout.this.a(f);
            }
        };
        this.I.setDuration(150L);
        if (animationListener != null) {
            this.e.a(animationListener);
        }
        this.e.clearAnimation();
        this.e.startAnimation(this.I);
    }

    void setTargetOffsetTopAndBottom(int i) {
        this.e.bringToFront();
        ViewCompat.offsetTopAndBottom(this.e, i);
        this.c = this.e.getTop();
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.z) {
            this.z = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }
}
