package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes.dex */
public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    c[] a;
    @NonNull
    OrientationHelper b;
    @NonNull
    OrientationHelper c;
    private int j;
    private int k;
    private BitSet m;
    private boolean o;
    private boolean p;
    private SavedState q;
    private int r;
    private int[] w;
    private int i = -1;
    boolean d = false;
    boolean e = false;
    int f = -1;
    int g = Integer.MIN_VALUE;
    b h = new b();
    private int n = 2;
    private final Rect s = new Rect();
    private final a t = new a();
    private boolean u = false;
    private boolean v = true;
    private final Runnable x = new Runnable() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.1
        @Override // java.lang.Runnable
        public void run() {
            StaggeredGridLayoutManager.this.a();
        }
    };
    @NonNull
    private final g l = new g();

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        RecyclerView.LayoutManager.Properties properties = getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        i();
    }

    public StaggeredGridLayoutManager(int i, int i2) {
        this.j = i2;
        setSpanCount(i);
        i();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return this.n != 0;
    }

    private void i() {
        this.b = OrientationHelper.createOrientationHelper(this, this.j);
        this.c = OrientationHelper.createOrientationHelper(this, 1 - this.j);
    }

    boolean a() {
        int i;
        int i2;
        if (getChildCount() == 0 || this.n == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.e) {
            i2 = g();
            i = h();
        } else {
            i2 = h();
            i = g();
        }
        if (i2 == 0 && b() != null) {
            this.h.a();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        } else if (!this.u) {
            return false;
        } else {
            int i3 = this.e ? -1 : 1;
            int i4 = i + 1;
            b.a a2 = this.h.a(i2, i4, i3, true);
            if (a2 == null) {
                this.u = false;
                this.h.a(i4);
                return false;
            }
            b.a a3 = this.h.a(i2, a2.a, i3 * (-1), true);
            if (a3 == null) {
                this.h.a(a2.a);
            } else {
                this.h.a(a3.a + 1);
            }
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        if (i == 0) {
            a();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        removeCallbacks(this.x);
        for (int i = 0; i < this.i; i++) {
            this.a[i].e();
        }
        recyclerView.requestLayout();
    }

    View b() {
        int i;
        int i2;
        boolean z;
        int childCount = getChildCount() - 1;
        BitSet bitSet = new BitSet(this.i);
        bitSet.set(0, this.i, true);
        int i3 = -1;
        char c2 = (this.j != 1 || !c()) ? (char) 65535 : (char) 1;
        if (this.e) {
            i = -1;
        } else {
            i = childCount + 1;
            childCount = 0;
        }
        if (childCount < i) {
            i3 = 1;
        }
        while (childCount != i) {
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (bitSet.get(layoutParams.a.e)) {
                if (a(layoutParams.a)) {
                    return childAt;
                }
                bitSet.clear(layoutParams.a.e);
            }
            if (!layoutParams.b && (i2 = childCount + i3) != i) {
                View childAt2 = getChildAt(i2);
                if (this.e) {
                    int decoratedEnd = this.b.getDecoratedEnd(childAt);
                    int decoratedEnd2 = this.b.getDecoratedEnd(childAt2);
                    if (decoratedEnd < decoratedEnd2) {
                        return childAt;
                    }
                    z = decoratedEnd == decoratedEnd2;
                } else {
                    int decoratedStart = this.b.getDecoratedStart(childAt);
                    int decoratedStart2 = this.b.getDecoratedStart(childAt2);
                    if (decoratedStart > decoratedStart2) {
                        return childAt;
                    }
                    z = decoratedStart == decoratedStart2;
                }
                if (!z) {
                    continue;
                } else {
                    if ((layoutParams.a.e - ((LayoutParams) childAt2.getLayoutParams()).a.e < 0) != (c2 < 0)) {
                        return childAt;
                    }
                }
            }
            childCount += i3;
        }
        return null;
    }

    private boolean a(c cVar) {
        if (this.e) {
            if (cVar.d() < this.b.getEndAfterPadding()) {
                return !cVar.c(cVar.a.get(cVar.a.size() - 1)).b;
            }
        } else if (cVar.b() > this.b.getStartAfterPadding()) {
            return !cVar.c(cVar.a.get(0)).b;
        }
        return false;
    }

    public void setSpanCount(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.i) {
            invalidateSpanAssignments();
            this.i = i;
            this.m = new BitSet(this.i);
            this.a = new c[this.i];
            for (int i2 = 0; i2 < this.i; i2++) {
                this.a[i2] = new c(i2);
            }
            requestLayout();
        }
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll(null);
            if (i != this.j) {
                this.j = i;
                OrientationHelper orientationHelper = this.b;
                this.b = this.c;
                this.c = orientationHelper;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        SavedState savedState = this.q;
        if (!(savedState == null || savedState.h == z)) {
            this.q.h = z;
        }
        this.d = z;
        requestLayout();
    }

    public int getGapStrategy() {
        return this.n;
    }

    public void setGapStrategy(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.n) {
            if (i == 0 || i == 2) {
                this.n = i;
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void assertNotInLayoutOrScroll(String str) {
        if (this.q == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public int getSpanCount() {
        return this.i;
    }

    public void invalidateSpanAssignments() {
        this.h.a();
        requestLayout();
    }

    private void j() {
        if (this.j == 1 || !c()) {
            this.e = this.d;
        } else {
            this.e = !this.d;
        }
    }

    boolean c() {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() {
        return this.d;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.j == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            i3 = chooseSize(i, (this.k * this.i) + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            i4 = chooseSize(i2, (this.k * this.i) + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        a(recycler, state, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAdapterChanged(@Nullable RecyclerView.Adapter adapter, @Nullable RecyclerView.Adapter adapter2) {
        this.h.a();
        for (int i = 0; i < this.i; i++) {
            this.a[i].e();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0161, code lost:
        if (a() != false) goto L_0x0165;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(androidx.recyclerview.widget.RecyclerView.Recycler r9, androidx.recyclerview.widget.RecyclerView.State r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.a(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, boolean):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.f = -1;
        this.g = Integer.MIN_VALUE;
        this.q = null;
        this.t.a();
    }

    private void k() {
        if (this.c.getMode() != 1073741824) {
            float f = 0.0f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                float decoratedMeasurement = this.c.getDecoratedMeasurement(childAt);
                if (decoratedMeasurement >= f) {
                    if (((LayoutParams) childAt.getLayoutParams()).isFullSpan()) {
                        decoratedMeasurement = (decoratedMeasurement * 1.0f) / this.i;
                    }
                    f = Math.max(f, decoratedMeasurement);
                }
            }
            int i2 = this.k;
            int round = Math.round(f * this.i);
            if (this.c.getMode() == Integer.MIN_VALUE) {
                round = Math.min(round, this.c.getTotalSpace());
            }
            a(round);
            if (this.k != i2) {
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt2 = getChildAt(i3);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    if (!layoutParams.b) {
                        if (!c() || this.j != 1) {
                            int i4 = layoutParams.a.e * this.k;
                            int i5 = layoutParams.a.e * i2;
                            if (this.j == 1) {
                                childAt2.offsetLeftAndRight(i4 - i5);
                            } else {
                                childAt2.offsetTopAndBottom(i4 - i5);
                            }
                        } else {
                            childAt2.offsetLeftAndRight(((-((this.i - 1) - layoutParams.a.e)) * this.k) - ((-((this.i - 1) - layoutParams.a.e)) * i2));
                        }
                    }
                }
            }
        }
    }

    private void a(a aVar) {
        if (this.q.c > 0) {
            if (this.q.c == this.i) {
                for (int i = 0; i < this.i; i++) {
                    this.a[i].e();
                    int i2 = this.q.d[i];
                    if (i2 != Integer.MIN_VALUE) {
                        if (this.q.i) {
                            i2 += this.b.getEndAfterPadding();
                        } else {
                            i2 += this.b.getStartAfterPadding();
                        }
                    }
                    this.a[i].c(i2);
                }
            } else {
                this.q.a();
                SavedState savedState = this.q;
                savedState.a = savedState.b;
            }
        }
        this.p = this.q.j;
        setReverseLayout(this.q.h);
        j();
        if (this.q.a != -1) {
            this.f = this.q.a;
            aVar.c = this.q.i;
        } else {
            aVar.c = this.e;
        }
        if (this.q.e > 1) {
            this.h.a = this.q.f;
            this.h.b = this.q.g;
        }
    }

    void a(RecyclerView.State state, a aVar) {
        if (!b(state, aVar) && !c(state, aVar)) {
            aVar.b();
            aVar.a = 0;
        }
    }

    private boolean c(RecyclerView.State state, a aVar) {
        int i;
        if (this.o) {
            i = l(state.getItemCount());
        } else {
            i = k(state.getItemCount());
        }
        aVar.a = i;
        aVar.b = Integer.MIN_VALUE;
        return true;
    }

    boolean b(RecyclerView.State state, a aVar) {
        int i;
        int i2;
        boolean z = false;
        if (state.isPreLayout() || (i = this.f) == -1) {
            return false;
        }
        if (i < 0 || i >= state.getItemCount()) {
            this.f = -1;
            this.g = Integer.MIN_VALUE;
            return false;
        }
        SavedState savedState = this.q;
        if (savedState == null || savedState.a == -1 || this.q.c < 1) {
            View findViewByPosition = findViewByPosition(this.f);
            if (findViewByPosition != null) {
                aVar.a = this.e ? g() : h();
                if (this.g != Integer.MIN_VALUE) {
                    if (aVar.c) {
                        aVar.b = (this.b.getEndAfterPadding() - this.g) - this.b.getDecoratedEnd(findViewByPosition);
                    } else {
                        aVar.b = (this.b.getStartAfterPadding() + this.g) - this.b.getDecoratedStart(findViewByPosition);
                    }
                    return true;
                } else if (this.b.getDecoratedMeasurement(findViewByPosition) > this.b.getTotalSpace()) {
                    if (aVar.c) {
                        i2 = this.b.getEndAfterPadding();
                    } else {
                        i2 = this.b.getStartAfterPadding();
                    }
                    aVar.b = i2;
                    return true;
                } else {
                    int decoratedStart = this.b.getDecoratedStart(findViewByPosition) - this.b.getStartAfterPadding();
                    if (decoratedStart < 0) {
                        aVar.b = -decoratedStart;
                        return true;
                    }
                    int endAfterPadding = this.b.getEndAfterPadding() - this.b.getDecoratedEnd(findViewByPosition);
                    if (endAfterPadding < 0) {
                        aVar.b = endAfterPadding;
                        return true;
                    }
                    aVar.b = Integer.MIN_VALUE;
                }
            } else {
                aVar.a = this.f;
                int i3 = this.g;
                if (i3 == Integer.MIN_VALUE) {
                    if (j(aVar.a) == 1) {
                        z = true;
                    }
                    aVar.c = z;
                    aVar.b();
                } else {
                    aVar.a(i3);
                }
                aVar.d = true;
            }
        } else {
            aVar.b = Integer.MIN_VALUE;
            aVar.a = this.f;
        }
        return true;
    }

    void a(int i) {
        this.k = i / this.i;
        this.r = View.MeasureSpec.makeMeasureSpec(i, this.c.getMode());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.q == null;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.i + ", array size:" + iArr.length);
        }
        for (int i = 0; i < this.i; i++) {
            iArr[i] = this.a[i].j();
        }
        return iArr;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.i + ", array size:" + iArr.length);
        }
        for (int i = 0; i < this.i; i++) {
            iArr[i] = this.a[i].l();
        }
        return iArr;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.i + ", array size:" + iArr.length);
        }
        for (int i = 0; i < this.i; i++) {
            iArr[i] = this.a[i].m();
        }
        return iArr;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.i];
        } else if (iArr.length < this.i) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.i + ", array size:" + iArr.length);
        }
        for (int i = 0; i < this.i; i++) {
            iArr[i] = this.a[i].o();
        }
        return iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return a(state);
    }

    private int a(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return k.a(state, this.b, a(!this.v), b(!this.v), this, this.v, this.e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return a(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return b(state);
    }

    private int b(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return k.a(state, this.b, a(!this.v), b(!this.v), this, this.v);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return b(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return c(state);
    }

    private int c(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return k.b(state, this.b, a(!this.v), b(!this.v), this, this.v);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return c(state);
    }

    private void a(View view, LayoutParams layoutParams, boolean z) {
        if (layoutParams.b) {
            if (this.j == 1) {
                a(view, this.r, getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), layoutParams.height, true), z);
            } else {
                a(view, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), layoutParams.width, true), this.r, z);
            }
        } else if (this.j == 1) {
            a(view, getChildMeasureSpec(this.k, getWidthMode(), 0, layoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), layoutParams.height, true), z);
        } else {
            a(view, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), layoutParams.width, true), getChildMeasureSpec(this.k, getHeightMode(), 0, layoutParams.height, false), z);
        }
    }

    private void a(View view, int i, int i2, boolean z) {
        boolean z2;
        calculateItemDecorationsForChild(view, this.s);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int a2 = a(i, layoutParams.leftMargin + this.s.left, layoutParams.rightMargin + this.s.right);
        int a3 = a(i2, layoutParams.topMargin + this.s.top, layoutParams.bottomMargin + this.s.bottom);
        if (z) {
            z2 = shouldReMeasureChild(view, a2, a3, layoutParams);
        } else {
            z2 = shouldMeasureChild(view, a2, a3, layoutParams);
        }
        if (z2) {
            view.measure(a2, a3);
        }
    }

    private int a(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.q = (SavedState) parcelable;
            if (this.f != -1) {
                this.q.b();
                this.q.a();
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        int i;
        SavedState savedState = this.q;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        savedState2.h = this.d;
        savedState2.i = this.o;
        savedState2.j = this.p;
        b bVar = this.h;
        if (bVar == null || bVar.a == null) {
            savedState2.e = 0;
        } else {
            savedState2.f = this.h.a;
            savedState2.e = savedState2.f.length;
            savedState2.g = this.h.b;
        }
        if (getChildCount() > 0) {
            savedState2.a = this.o ? g() : h();
            savedState2.b = d();
            int i2 = this.i;
            savedState2.c = i2;
            savedState2.d = new int[i2];
            for (int i3 = 0; i3 < this.i; i3++) {
                if (this.o) {
                    i = this.a[i3].b(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.b.getEndAfterPadding();
                    }
                } else {
                    i = this.a[i3].a(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.b.getStartAfterPadding();
                    }
                }
                savedState2.d[i3] = i;
            }
        } else {
            savedState2.a = -1;
            savedState2.b = -1;
            savedState2.c = 0;
        }
        return savedState2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View a2 = a(false);
            View b2 = b(false);
            if (a2 != null && b2 != null) {
                int position = getPosition(a2);
                int position2 = getPosition(b2);
                if (position < position2) {
                    accessibilityEvent.setFromIndex(position);
                    accessibilityEvent.setToIndex(position2);
                    return;
                }
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    int d() {
        View b2 = this.e ? b(true) : a(true);
        if (b2 == null) {
            return -1;
        }
        return getPosition(b2);
    }

    View a(boolean z) {
        int startAfterPadding = this.b.getStartAfterPadding();
        int endAfterPadding = this.b.getEndAfterPadding();
        int childCount = getChildCount();
        View view = null;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int decoratedStart = this.b.getDecoratedStart(childAt);
            if (this.b.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    View b(boolean z) {
        int startAfterPadding = this.b.getStartAfterPadding();
        int endAfterPadding = this.b.getEndAfterPadding();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.b.getDecoratedStart(childAt);
            int decoratedEnd = this.b.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    private void b(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int g = g(Integer.MIN_VALUE);
        if (g != Integer.MIN_VALUE && (endAfterPadding = this.b.getEndAfterPadding() - g) > 0) {
            int i = endAfterPadding - (-a(-endAfterPadding, recycler, state));
            if (z && i > 0) {
                this.b.offsetChildren(i);
            }
        }
    }

    private void c(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int f = f(Integer.MAX_VALUE);
        if (f != Integer.MAX_VALUE && (startAfterPadding = f - this.b.getStartAfterPadding()) > 0) {
            int a2 = startAfterPadding - a(startAfterPadding, recycler, state);
            if (z && a2 > 0) {
                this.b.offsetChildren(-a2);
            }
        }
    }

    private void b(int i, RecyclerView.State state) {
        int i2;
        int i3;
        int targetScrollPosition;
        g gVar = this.l;
        boolean z = false;
        gVar.b = 0;
        gVar.c = i;
        if (!isSmoothScrolling() || (targetScrollPosition = state.getTargetScrollPosition()) == -1) {
            i3 = 0;
            i2 = 0;
        } else {
            if (this.e == (targetScrollPosition < i)) {
                i3 = this.b.getTotalSpace();
                i2 = 0;
            } else {
                i2 = this.b.getTotalSpace();
                i3 = 0;
            }
        }
        if (getClipToPadding()) {
            this.l.f = this.b.getStartAfterPadding() - i2;
            this.l.g = this.b.getEndAfterPadding() + i3;
        } else {
            this.l.g = this.b.getEnd() + i3;
            this.l.f = -i2;
        }
        g gVar2 = this.l;
        gVar2.h = false;
        gVar2.a = true;
        if (this.b.getMode() == 0 && this.b.getEnd() == 0) {
            z = true;
        }
        gVar2.i = z;
    }

    private void b(int i) {
        g gVar = this.l;
        gVar.e = i;
        int i2 = 1;
        if (this.e != (i == -1)) {
            i2 = -1;
        }
        gVar.d = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void offsetChildrenHorizontal(int i) {
        super.offsetChildrenHorizontal(i);
        for (int i2 = 0; i2 < this.i; i2++) {
            this.a[i2].d(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        for (int i2 = 0; i2 < this.i; i2++) {
            this.a[i2].d(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.h.a();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        b(i, i2, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        b(i, i2, 4);
    }

    private void b(int i, int i2, int i3) {
        int i4;
        int i5;
        int g = this.e ? g() : h();
        if (i3 != 8) {
            i4 = i + i2;
            i5 = i;
        } else if (i < i2) {
            i4 = i2 + 1;
            i5 = i;
        } else {
            i4 = i + 1;
            i5 = i2;
        }
        this.h.b(i5);
        if (i3 != 8) {
            switch (i3) {
                case 1:
                    this.h.b(i, i2);
                    break;
                case 2:
                    this.h.a(i, i2);
                    break;
            }
        } else {
            this.h.a(i, 1);
            this.h.b(i2, 1);
        }
        if (i4 > g) {
            if (i5 <= (this.e ? h() : g())) {
                requestLayout();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [int] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /* JADX WARN: Unknown variable types count: 14 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(androidx.recyclerview.widget.RecyclerView.Recycler r17, androidx.recyclerview.widget.g r18, androidx.recyclerview.widget.RecyclerView.State r19) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.a(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.g, androidx.recyclerview.widget.RecyclerView$State):int");
    }

    private b.a c(int i) {
        b.a aVar = new b.a();
        aVar.c = new int[this.i];
        for (int i2 = 0; i2 < this.i; i2++) {
            aVar.c[i2] = i - this.a[i2].b(i);
        }
        return aVar;
    }

    private b.a d(int i) {
        b.a aVar = new b.a();
        aVar.c = new int[this.i];
        for (int i2 = 0; i2 < this.i; i2++) {
            aVar.c[i2] = this.a[i2].a(i) - i;
        }
        return aVar;
    }

    private void a(View view, LayoutParams layoutParams, g gVar) {
        if (gVar.e == 1) {
            if (layoutParams.b) {
                a(view);
            } else {
                layoutParams.a.b(view);
            }
        } else if (layoutParams.b) {
            b(view);
        } else {
            layoutParams.a.a(view);
        }
    }

    private void a(RecyclerView.Recycler recycler, g gVar) {
        int i;
        int i2;
        if (gVar.a && !gVar.i) {
            if (gVar.b == 0) {
                if (gVar.e == -1) {
                    b(recycler, gVar.g);
                } else {
                    a(recycler, gVar.f);
                }
            } else if (gVar.e == -1) {
                int e = gVar.f - e(gVar.f);
                if (e < 0) {
                    i2 = gVar.g;
                } else {
                    i2 = gVar.g - Math.min(e, gVar.b);
                }
                b(recycler, i2);
            } else {
                int h = h(gVar.g) - gVar.g;
                if (h < 0) {
                    i = gVar.f;
                } else {
                    i = Math.min(h, gVar.b) + gVar.f;
                }
                a(recycler, i);
            }
        }
    }

    private void a(View view) {
        for (int i = this.i - 1; i >= 0; i--) {
            this.a[i].b(view);
        }
    }

    private void b(View view) {
        for (int i = this.i - 1; i >= 0; i--) {
            this.a[i].a(view);
        }
    }

    private void a(int i, int i2) {
        for (int i3 = 0; i3 < this.i; i3++) {
            if (!this.a[i3].a.isEmpty()) {
                a(this.a[i3], i, i2);
            }
        }
    }

    private void a(c cVar, int i, int i2) {
        int i3 = cVar.i();
        if (i == -1) {
            if (cVar.b() + i3 <= i2) {
                this.m.set(cVar.e, false);
            }
        } else if (cVar.d() - i3 >= i2) {
            this.m.set(cVar.e, false);
        }
    }

    private int e(int i) {
        int a2 = this.a[0].a(i);
        for (int i2 = 1; i2 < this.i; i2++) {
            int a3 = this.a[i2].a(i);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private int f(int i) {
        int a2 = this.a[0].a(i);
        for (int i2 = 1; i2 < this.i; i2++) {
            int a3 = this.a[i2].a(i);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    boolean e() {
        int b2 = this.a[0].b(Integer.MIN_VALUE);
        for (int i = 1; i < this.i; i++) {
            if (this.a[i].b(Integer.MIN_VALUE) != b2) {
                return false;
            }
        }
        return true;
    }

    boolean f() {
        int a2 = this.a[0].a(Integer.MIN_VALUE);
        for (int i = 1; i < this.i; i++) {
            if (this.a[i].a(Integer.MIN_VALUE) != a2) {
                return false;
            }
        }
        return true;
    }

    private int g(int i) {
        int b2 = this.a[0].b(i);
        for (int i2 = 1; i2 < this.i; i2++) {
            int b3 = this.a[i2].b(i);
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private int h(int i) {
        int b2 = this.a[0].b(i);
        for (int i2 = 1; i2 < this.i; i2++) {
            int b3 = this.a[i2].b(i);
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private void a(RecyclerView.Recycler recycler, int i) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.b.getDecoratedEnd(childAt) <= i && this.b.getTransformedEndWithDecoration(childAt) <= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    for (int i2 = 0; i2 < this.i; i2++) {
                        if (this.a[i2].a.size() == 1) {
                            return;
                        }
                    }
                    for (int i3 = 0; i3 < this.i; i3++) {
                        this.a[i3].h();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.h();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
            } else {
                return;
            }
        }
    }

    private void b(RecyclerView.Recycler recycler, int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (this.b.getDecoratedStart(childAt) >= i && this.b.getTransformedStartWithDecoration(childAt) >= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    for (int i2 = 0; i2 < this.i; i2++) {
                        if (this.a[i2].a.size() == 1) {
                            return;
                        }
                    }
                    for (int i3 = 0; i3 < this.i; i3++) {
                        this.a[i3].g();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.g();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
            } else {
                return;
            }
        }
    }

    private boolean i(int i) {
        if (this.j == 0) {
            return (i == -1) != this.e;
        }
        return ((i == -1) == this.e) == c();
    }

    private c a(g gVar) {
        int i;
        int i2;
        int i3 = -1;
        if (i(gVar.e)) {
            i2 = this.i - 1;
            i = -1;
        } else {
            i2 = 0;
            i3 = this.i;
            i = 1;
        }
        c cVar = null;
        if (gVar.e == 1) {
            int i4 = Integer.MAX_VALUE;
            int startAfterPadding = this.b.getStartAfterPadding();
            while (i2 != i3) {
                c cVar2 = this.a[i2];
                int b2 = cVar2.b(startAfterPadding);
                if (b2 < i4) {
                    cVar = cVar2;
                    i4 = b2;
                }
                i2 += i;
            }
            return cVar;
        }
        int i5 = Integer.MIN_VALUE;
        int endAfterPadding = this.b.getEndAfterPadding();
        while (i2 != i3) {
            c cVar3 = this.a[i2];
            int a2 = cVar3.a(endAfterPadding);
            if (a2 > i5) {
                cVar = cVar3;
                i5 = a2;
            }
            i2 += i;
        }
        return cVar;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.j == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.j == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(i, recycler, state);
    }

    private int j(int i) {
        if (getChildCount() == 0) {
            return this.e ? 1 : -1;
        }
        return (i < h()) != this.e ? -1 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int i) {
        int j = j(i);
        PointF pointF = new PointF();
        if (j == 0) {
            return null;
        }
        if (this.j == 0) {
            pointF.x = j;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = j;
        }
        return pointF;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        SavedState savedState = this.q;
        if (!(savedState == null || savedState.a == i)) {
            this.q.b();
        }
        this.f = i;
        this.g = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        SavedState savedState = this.q;
        if (savedState != null) {
            savedState.b();
        }
        this.f = i;
        this.g = i2;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i3;
        if (this.j != 0) {
            i = i2;
        }
        if (!(getChildCount() == 0 || i == 0)) {
            a(i, state);
            int[] iArr = this.w;
            if (iArr == null || iArr.length < this.i) {
                this.w = new int[this.i];
            }
            int i4 = 0;
            for (int i5 = 0; i5 < this.i; i5++) {
                if (this.l.d == -1) {
                    i3 = this.l.f - this.a[i5].a(this.l.f);
                } else {
                    i3 = this.a[i5].b(this.l.g) - this.l.g;
                }
                if (i3 >= 0) {
                    this.w[i4] = i3;
                    i4++;
                }
            }
            Arrays.sort(this.w, 0, i4);
            for (int i6 = 0; i6 < i4 && this.l.a(state); i6++) {
                layoutPrefetchRegistry.addPosition(this.l.c, this.w[i6]);
                this.l.c += this.l.d;
            }
        }
    }

    void a(int i, RecyclerView.State state) {
        int i2;
        int i3;
        if (i > 0) {
            i3 = g();
            i2 = 1;
        } else {
            i2 = -1;
            i3 = h();
        }
        this.l.a = true;
        b(i3, state);
        b(i2);
        g gVar = this.l;
        gVar.c = i3 + gVar.d;
        this.l.b = Math.abs(i);
    }

    int a(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        a(i, state);
        int a2 = a(recycler, this.l, state);
        if (this.l.b >= a2) {
            i = i < 0 ? -a2 : a2;
        }
        this.b.offsetChildren(-i);
        this.o = this.e;
        g gVar = this.l;
        gVar.b = 0;
        a(recycler, gVar);
        return i;
    }

    int g() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    int h() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    private int k(int i) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            int position = getPosition(getChildAt(i2));
            if (position >= 0 && position < i) {
                return position;
            }
        }
        return 0;
    }

    private int l(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int position = getPosition(getChildAt(childCount));
            if (position >= 0 && position < i) {
                return position;
            }
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.j == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public int getOrientation() {
        return this.j;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    @Nullable
    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        View findContainingItemView;
        int i2;
        int i3;
        int i4;
        int i5;
        View a2;
        if (getChildCount() == 0 || (findContainingItemView = findContainingItemView(view)) == null) {
            return null;
        }
        j();
        int m = m(i);
        if (m == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        boolean z = layoutParams.b;
        c cVar = layoutParams.a;
        if (m == 1) {
            i2 = g();
        } else {
            i2 = h();
        }
        b(i2, state);
        b(m);
        g gVar = this.l;
        gVar.c = gVar.d + i2;
        this.l.b = (int) (this.b.getTotalSpace() * 0.33333334f);
        g gVar2 = this.l;
        gVar2.h = true;
        gVar2.a = false;
        a(recycler, gVar2, state);
        this.o = this.e;
        if (!(z || (a2 = cVar.a(i2, m)) == null || a2 == findContainingItemView)) {
            return a2;
        }
        if (i(m)) {
            for (int i6 = this.i - 1; i6 >= 0; i6--) {
                View a3 = this.a[i6].a(i2, m);
                if (!(a3 == null || a3 == findContainingItemView)) {
                    return a3;
                }
            }
        } else {
            for (int i7 = 0; i7 < this.i; i7++) {
                View a4 = this.a[i7].a(i2, m);
                if (!(a4 == null || a4 == findContainingItemView)) {
                    return a4;
                }
            }
        }
        boolean z2 = (this.d ^ true) == (m == -1);
        if (!z) {
            if (z2) {
                i5 = cVar.k();
            } else {
                i5 = cVar.n();
            }
            View findViewByPosition = findViewByPosition(i5);
            if (!(findViewByPosition == null || findViewByPosition == findContainingItemView)) {
                return findViewByPosition;
            }
        }
        if (i(m)) {
            for (int i8 = this.i - 1; i8 >= 0; i8--) {
                if (i8 != cVar.e) {
                    if (z2) {
                        i4 = this.a[i8].k();
                    } else {
                        i4 = this.a[i8].n();
                    }
                    View findViewByPosition2 = findViewByPosition(i4);
                    if (!(findViewByPosition2 == null || findViewByPosition2 == findContainingItemView)) {
                        return findViewByPosition2;
                    }
                }
            }
        } else {
            for (int i9 = 0; i9 < this.i; i9++) {
                if (z2) {
                    i3 = this.a[i9].k();
                } else {
                    i3 = this.a[i9].n();
                }
                View findViewByPosition3 = findViewByPosition(i3);
                if (!(findViewByPosition3 == null || findViewByPosition3 == findContainingItemView)) {
                    return findViewByPosition3;
                }
            }
        }
        return null;
    }

    private int m(int i) {
        if (i == 17) {
            return this.j == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.j == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 66) {
            return this.j == 0 ? 1 : Integer.MIN_VALUE;
        }
        if (i == 130) {
            return this.j == 1 ? 1 : Integer.MIN_VALUE;
        }
        switch (i) {
            case 1:
                return (this.j != 1 && c()) ? 1 : -1;
            case 2:
                return (this.j != 1 && c()) ? -1 : 1;
            default:
                return Integer.MIN_VALUE;
        }
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        c a;
        boolean b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setFullSpan(boolean z) {
            this.b = z;
        }

        public boolean isFullSpan() {
            return this.b;
        }

        public final int getSpanIndex() {
            c cVar = this.a;
            if (cVar == null) {
                return -1;
            }
            return cVar.e;
        }
    }

    /* loaded from: classes.dex */
    public class c {
        ArrayList<View> a = new ArrayList<>();
        int b = Integer.MIN_VALUE;
        int c = Integer.MIN_VALUE;
        int d = 0;
        final int e;

        c(int i) {
            StaggeredGridLayoutManager.this = r1;
            this.e = i;
        }

        int a(int i) {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            a();
            return this.b;
        }

        void a() {
            b.a f;
            View view = this.a.get(0);
            LayoutParams c = c(view);
            this.b = StaggeredGridLayoutManager.this.b.getDecoratedStart(view);
            if (c.b && (f = StaggeredGridLayoutManager.this.h.f(c.getViewLayoutPosition())) != null && f.b == -1) {
                this.b -= f.a(this.e);
            }
        }

        int b() {
            int i = this.b;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            a();
            return this.b;
        }

        int b(int i) {
            int i2 = this.c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            c();
            return this.c;
        }

        void c() {
            b.a f;
            ArrayList<View> arrayList = this.a;
            View view = arrayList.get(arrayList.size() - 1);
            LayoutParams c = c(view);
            this.c = StaggeredGridLayoutManager.this.b.getDecoratedEnd(view);
            if (c.b && (f = StaggeredGridLayoutManager.this.h.f(c.getViewLayoutPosition())) != null && f.b == 1) {
                this.c += f.a(this.e);
            }
        }

        int d() {
            int i = this.c;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            c();
            return this.c;
        }

        void a(View view) {
            LayoutParams c = c(view);
            c.a = this;
            this.a.add(0, view);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (c.isItemRemoved() || c.isItemChanged()) {
                this.d += StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
        }

        void b(View view) {
            LayoutParams c = c(view);
            c.a = this;
            this.a.add(view);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (c.isItemRemoved() || c.isItemChanged()) {
                this.d += StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(view);
            }
        }

        void a(boolean z, int i) {
            int i2;
            if (z) {
                i2 = b(Integer.MIN_VALUE);
            } else {
                i2 = a(Integer.MIN_VALUE);
            }
            e();
            if (i2 != Integer.MIN_VALUE) {
                if (z && i2 < StaggeredGridLayoutManager.this.b.getEndAfterPadding()) {
                    return;
                }
                if (z || i2 <= StaggeredGridLayoutManager.this.b.getStartAfterPadding()) {
                    if (i != Integer.MIN_VALUE) {
                        i2 += i;
                    }
                    this.c = i2;
                    this.b = i2;
                }
            }
        }

        void e() {
            this.a.clear();
            f();
            this.d = 0;
        }

        void f() {
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
        }

        void c(int i) {
            this.b = i;
            this.c = i;
        }

        void g() {
            int size = this.a.size();
            View remove = this.a.remove(size - 1);
            LayoutParams c = c(remove);
            c.a = null;
            if (c.isItemRemoved() || c.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(remove);
            }
            if (size == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        void h() {
            View remove = this.a.remove(0);
            LayoutParams c = c(remove);
            c.a = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (c.isItemRemoved() || c.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.b.getDecoratedMeasurement(remove);
            }
            this.b = Integer.MIN_VALUE;
        }

        public int i() {
            return this.d;
        }

        LayoutParams c(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        void d(int i) {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                this.b = i2 + i;
            }
            int i3 = this.c;
            if (i3 != Integer.MIN_VALUE) {
                this.c = i3 + i;
            }
        }

        public int j() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(this.a.size() - 1, -1, false);
            }
            return a(0, this.a.size(), false);
        }

        public int k() {
            if (StaggeredGridLayoutManager.this.d) {
                return b(this.a.size() - 1, -1, true);
            }
            return b(0, this.a.size(), true);
        }

        public int l() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(this.a.size() - 1, -1, true);
            }
            return a(0, this.a.size(), true);
        }

        public int m() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(0, this.a.size(), false);
            }
            return a(this.a.size() - 1, -1, false);
        }

        public int n() {
            if (StaggeredGridLayoutManager.this.d) {
                return b(0, this.a.size(), true);
            }
            return b(this.a.size() - 1, -1, true);
        }

        public int o() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(0, this.a.size(), true);
            }
            return a(this.a.size() - 1, -1, true);
        }

        int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int startAfterPadding = StaggeredGridLayoutManager.this.b.getStartAfterPadding();
            int endAfterPadding = StaggeredGridLayoutManager.this.b.getEndAfterPadding();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = this.a.get(i);
                int decoratedStart = StaggeredGridLayoutManager.this.b.getDecoratedStart(view);
                int decoratedEnd = StaggeredGridLayoutManager.this.b.getDecoratedEnd(view);
                boolean z4 = false;
                boolean z5 = z3 ? decoratedStart <= endAfterPadding : decoratedStart < endAfterPadding;
                if (z3) {
                    if (decoratedEnd >= startAfterPadding) {
                        z4 = true;
                    }
                } else if (decoratedEnd > startAfterPadding) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (!z || !z2) {
                        if (z2) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (decoratedStart < startAfterPadding || decoratedEnd > endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else if (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        int a(int i, int i2, boolean z) {
            return a(i, i2, z, true, false);
        }

        int b(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        public View a(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.a.size() - 1;
                while (size >= 0) {
                    View view2 = this.a.get(size);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view2) >= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.a.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = this.a.get(i3);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view3) <= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.getPosition(view3) >= i) || !view3.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }
    }

    /* loaded from: classes.dex */
    public static class b {
        int[] a;
        List<a> b;

        b() {
        }

        int a(int i) {
            List<a> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.b.get(size).a >= i) {
                        this.b.remove(size);
                    }
                }
            }
            return b(i);
        }

        int b(int i) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            int g = g(i);
            if (g == -1) {
                int[] iArr2 = this.a;
                Arrays.fill(iArr2, i, iArr2.length, -1);
                return this.a.length;
            }
            int min = Math.min(g + 1, this.a.length);
            Arrays.fill(this.a, i, min, -1);
            return min;
        }

        int c(int i) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }

        void a(int i, c cVar) {
            e(i);
            this.a[i] = cVar.e;
        }

        int d(int i) {
            int length = this.a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }

        void e(int i) {
            int[] iArr = this.a;
            if (iArr == null) {
                this.a = new int[Math.max(i, 10) + 1];
                Arrays.fill(this.a, -1);
            } else if (i >= iArr.length) {
                this.a = new int[d(i)];
                System.arraycopy(iArr, 0, this.a, 0, iArr.length);
                int[] iArr2 = this.a;
                Arrays.fill(iArr2, iArr.length, iArr2.length, -1);
            }
        }

        void a() {
            int[] iArr = this.a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.b = null;
        }

        void a(int i, int i2) {
            int[] iArr = this.a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                e(i3);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
                int[] iArr3 = this.a;
                Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
                c(i, i2);
            }
        }

        private void c(int i, int i2) {
            List<a> list = this.b;
            if (list != null) {
                int i3 = i + i2;
                for (int size = list.size() - 1; size >= 0; size--) {
                    a aVar = this.b.get(size);
                    if (aVar.a >= i) {
                        if (aVar.a < i3) {
                            this.b.remove(size);
                        } else {
                            aVar.a -= i2;
                        }
                    }
                }
            }
        }

        void b(int i, int i2) {
            int[] iArr = this.a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                e(i3);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
                Arrays.fill(this.a, i, i3, -1);
                d(i, i2);
            }
        }

        private void d(int i, int i2) {
            List<a> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    a aVar = this.b.get(size);
                    if (aVar.a >= i) {
                        aVar.a += i2;
                    }
                }
            }
        }

        private int g(int i) {
            if (this.b == null) {
                return -1;
            }
            a f = f(i);
            if (f != null) {
                this.b.remove(f);
            }
            int size = this.b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (this.b.get(i2).a >= i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return -1;
            }
            this.b.remove(i2);
            return this.b.get(i2).a;
        }

        public void a(a aVar) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                a aVar2 = this.b.get(i);
                if (aVar2.a == aVar.a) {
                    this.b.remove(i);
                }
                if (aVar2.a >= aVar.a) {
                    this.b.add(i, aVar);
                    return;
                }
            }
            this.b.add(aVar);
        }

        public a f(int i) {
            List<a> list = this.b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                a aVar = this.b.get(size);
                if (aVar.a == i) {
                    return aVar;
                }
            }
            return null;
        }

        public a a(int i, int i2, int i3, boolean z) {
            List<a> list = this.b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                a aVar = this.b.get(i4);
                if (aVar.a >= i2) {
                    return null;
                }
                if (aVar.a >= i && (i3 == 0 || aVar.b == i3 || (z && aVar.d))) {
                    return aVar;
                }
            }
            return null;
        }

        @SuppressLint({"BanParcelableUsage"})
        /* loaded from: classes.dex */
        public static class a implements Parcelable {
            public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.b.a.1
                /* renamed from: a */
                public a createFromParcel(Parcel parcel) {
                    return new a(parcel);
                }

                /* renamed from: a */
                public a[] newArray(int i) {
                    return new a[i];
                }
            };
            int a;
            int b;
            int[] c;
            boolean d;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            a(Parcel parcel) {
                this.a = parcel.readInt();
                this.b = parcel.readInt();
                this.d = parcel.readInt() != 1 ? false : true;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.c = new int[readInt];
                    parcel.readIntArray(this.c);
                }
            }

            a() {
            }

            int a(int i) {
                int[] iArr = this.c;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i];
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b);
                parcel.writeInt(this.d ? 1 : 0);
                int[] iArr = this.c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(iArr.length);
                parcel.writeIntArray(this.c);
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.a + ", mGapDir=" + this.b + ", mHasUnwantedGapAfter=" + this.d + ", mGapPerSpan=" + Arrays.toString(this.c) + '}';
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.SavedState.1
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        int b;
        int c;
        int[] d;
        int e;
        int[] f;
        List<b.a> g;
        boolean h;
        boolean i;
        boolean j;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            int i = this.c;
            if (i > 0) {
                this.d = new int[i];
                parcel.readIntArray(this.d);
            }
            this.e = parcel.readInt();
            int i2 = this.e;
            if (i2 > 0) {
                this.f = new int[i2];
                parcel.readIntArray(this.f);
            }
            boolean z = false;
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1 ? true : z;
            this.g = parcel.readArrayList(b.a.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.a = savedState.a;
            this.b = savedState.b;
            this.d = savedState.d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }

        void a() {
            this.d = null;
            this.c = 0;
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        void b() {
            this.d = null;
            this.c = 0;
            this.a = -1;
            this.b = -1;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            if (this.c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    /* loaded from: classes.dex */
    public class a {
        int a;
        int b;
        boolean c;
        boolean d;
        boolean e;
        int[] f;

        a() {
            StaggeredGridLayoutManager.this = r1;
            a();
        }

        void a() {
            this.a = -1;
            this.b = Integer.MIN_VALUE;
            this.c = false;
            this.d = false;
            this.e = false;
            int[] iArr = this.f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }

        void a(c[] cVarArr) {
            int length = cVarArr.length;
            int[] iArr = this.f;
            if (iArr == null || iArr.length < length) {
                this.f = new int[StaggeredGridLayoutManager.this.a.length];
            }
            for (int i = 0; i < length; i++) {
                this.f[i] = cVarArr[i].a(Integer.MIN_VALUE);
            }
        }

        void b() {
            this.b = this.c ? StaggeredGridLayoutManager.this.b.getEndAfterPadding() : StaggeredGridLayoutManager.this.b.getStartAfterPadding();
        }

        void a(int i) {
            if (this.c) {
                this.b = StaggeredGridLayoutManager.this.b.getEndAfterPadding() - i;
            } else {
                this.b = StaggeredGridLayoutManager.this.b.getStartAfterPadding() + i;
            }
        }
    }
}
