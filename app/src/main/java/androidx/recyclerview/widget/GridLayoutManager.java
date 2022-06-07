package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {
    public static final int DEFAULT_SPAN_COUNT = -1;
    int[] c;
    View[] d;
    private boolean i;
    boolean a = false;
    int b = -1;
    final SparseIntArray e = new SparseIntArray();
    final SparseIntArray f = new SparseIntArray();
    SpanSizeLookup g = new DefaultSpanSizeLookup();
    final Rect h = new Rect();

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSpanCount(getProperties(context, attributeSet, i, i2).spanCount);
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        setSpanCount(i);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void setStackFromEnd(boolean z) {
        if (!z) {
            super.setStackFromEnd(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.b;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return a(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.b;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return a(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int a = a(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), a, 1, false, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(a, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), false, false));
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            b();
        }
        super.onLayoutChildren(recycler, state);
        a();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.a = false;
    }

    private void a() {
        this.e.clear();
        this.f.clear();
    }

    private void b() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.e.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.f.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.g.invalidateSpanIndexCache();
        this.g.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.g.invalidateSpanIndexCache();
        this.g.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.g.invalidateSpanIndexCache();
        this.g.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.g.invalidateSpanIndexCache();
        this.g.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.g.invalidateSpanIndexCache();
        this.g.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
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

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.g = spanSizeLookup;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.g;
    }

    private void c() {
        int i;
        if (getOrientation() == 1) {
            i = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            i = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        a(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.c == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            int[] iArr = this.c;
            i3 = chooseSize(i, iArr[iArr.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            int[] iArr2 = this.c;
            i4 = chooseSize(i2, iArr2[iArr2.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    private void a(int i) {
        this.c = a(this.c, this.b, i);
    }

    static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[i + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    int a(int i, int i2) {
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            int[] iArr = this.c;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.c;
        int i3 = this.b;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.a aVar, int i) {
        super.onAnchorReady(recycler, state, aVar, i);
        c();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            a(recycler, state, aVar, i);
        }
        d();
    }

    private void d() {
        View[] viewArr = this.d;
        if (viewArr == null || viewArr.length != this.b) {
            this.d = new View[this.b];
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        c();
        d();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        c();
        d();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    private void a(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.a aVar, int i) {
        boolean z = i == 1;
        int b = b(recycler, state, aVar.b);
        if (z) {
            while (b > 0 && aVar.b > 0) {
                aVar.b--;
                b = b(recycler, state, aVar.b);
            }
            return;
        }
        int itemCount = state.getItemCount() - 1;
        int i2 = aVar.b;
        while (i2 < itemCount) {
            int i3 = i2 + 1;
            int b2 = b(recycler, state, i3);
            if (b2 <= b) {
                break;
            }
            i2 = i3;
            b = b2;
        }
        aVar.b = i2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int childCount = getChildCount();
        int i2 = -1;
        int i3 = 1;
        if (z2) {
            i = getChildCount() - 1;
            i3 = -1;
        } else {
            i = 0;
            i2 = childCount;
        }
        int itemCount = state.getItemCount();
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < itemCount && b(recycler, state, position) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) >= startAfterPadding) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i3;
        }
        return view != null ? view : view2;
    }

    private int a(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.getCachedSpanGroupIndex(i, this.b);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.getCachedSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    private int b(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.getCachedSpanIndex(i, this.b);
        }
        int i2 = this.f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.getCachedSpanIndex(convertPreLayoutPositionToPostLayout, this.b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    private int c(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.getSpanSize(i);
        }
        int i2 = this.e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.getSpanSize(convertPreLayoutPositionToPostLayout);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.b bVar, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = this.b;
        for (int i2 = 0; i2 < this.b && bVar.a(state) && i > 0; i2++) {
            int i3 = bVar.d;
            layoutPrefetchRegistry.addPosition(i3, Math.max(0, bVar.g));
            i -= this.g.getSpanSize(i3);
            bVar.d += bVar.e;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a3, code lost:
        r22.mFinished = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a5, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void layoutChunk(androidx.recyclerview.widget.RecyclerView.Recycler r19, androidx.recyclerview.widget.RecyclerView.State r20, androidx.recyclerview.widget.LinearLayoutManager.b r21, androidx.recyclerview.widget.LinearLayoutManager.LayoutChunkResult r22) {
        /*
            Method dump skipped, instructions count: 576
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.layoutChunk(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, androidx.recyclerview.widget.LinearLayoutManager$b, androidx.recyclerview.widget.LinearLayoutManager$LayoutChunkResult):void");
    }

    private void a(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.d;
        int i4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int i5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int a = a(layoutParams.a, layoutParams.b);
        if (this.mOrientation == 1) {
            i2 = getChildMeasureSpec(a, i, i5, layoutParams.width, false);
            i3 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i4, layoutParams.height, true);
        } else {
            i3 = getChildMeasureSpec(a, i, i4, layoutParams.height, false);
            i2 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), i5, layoutParams.width, true);
        }
        a(view, i2, i3, z);
    }

    private void a(float f, int i) {
        a(Math.max(Math.round(f * this.b), i));
    }

    private void a(View view, int i, int i2, boolean z) {
        boolean z2;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            z2 = shouldReMeasureChild(view, i, i2, layoutParams);
        } else {
            z2 = shouldMeasureChild(view, i, i2, layoutParams);
        }
        if (z2) {
            view.measure(i, i2);
        }
    }

    private void a(RecyclerView.Recycler recycler, RecyclerView.State state, int i, boolean z) {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = -1;
        if (z) {
            i2 = 1;
            i5 = i;
            i3 = 0;
        } else {
            i3 = i - 1;
            i2 = -1;
        }
        while (i3 != i5) {
            View view = this.d[i3];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.b = c(recycler, state, getPosition(view));
            layoutParams.a = i4;
            i4 += layoutParams.b;
            i3 += i2;
        }
    }

    public int getSpanCount() {
        return this.b;
    }

    public void setSpanCount(int i) {
        if (i != this.b) {
            this.a = true;
            if (i >= 1) {
                this.b = i;
                this.g.invalidateSpanIndexCache();
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class SpanSizeLookup {
        final SparseIntArray mSpanIndexCache = new SparseIntArray();
        final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        private boolean mCacheSpanIndices = false;
        private boolean mCacheSpanGroupIndices = false;

        public abstract int getSpanSize(int i);

        public void setSpanIndexCacheEnabled(boolean z) {
            if (!z) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanIndices = z;
        }

        public void setSpanGroupIndexCacheEnabled(boolean z) {
            if (!z) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanGroupIndices = z;
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public void invalidateSpanGroupIndexCache() {
            this.mSpanGroupIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public boolean isSpanGroupIndexCacheEnabled() {
            return this.mCacheSpanGroupIndices;
        }

        int getCachedSpanIndex(int i, int i2) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(i, i2);
            }
            int i3 = this.mSpanIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = getSpanIndex(i, i2);
            this.mSpanIndexCache.put(i, spanIndex);
            return spanIndex;
        }

        int getCachedSpanGroupIndex(int i, int i2) {
            if (!this.mCacheSpanGroupIndices) {
                return getSpanGroupIndex(i, i2);
            }
            int i3 = this.mSpanGroupIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanGroupIndex = getSpanGroupIndex(i, i2);
            this.mSpanGroupIndexCache.put(i, spanGroupIndex);
            return spanGroupIndex;
        }

        public int getSpanIndex(int i, int i2) {
            int i3;
            int i4;
            int findFirstKeyLessThan;
            int spanSize = getSpanSize(i);
            if (spanSize == i2) {
                return 0;
            }
            if (!this.mCacheSpanIndices || (findFirstKeyLessThan = findFirstKeyLessThan(this.mSpanIndexCache, i)) < 0) {
                i4 = 0;
                i3 = 0;
            } else {
                i3 = this.mSpanIndexCache.get(findFirstKeyLessThan) + getSpanSize(findFirstKeyLessThan);
                i4 = findFirstKeyLessThan + 1;
            }
            while (i4 < i) {
                int spanSize2 = getSpanSize(i4);
                i3 += spanSize2;
                if (i3 == i2) {
                    i3 = 0;
                } else if (i3 > i2) {
                    i3 = spanSize2;
                }
                i4++;
            }
            if (spanSize + i3 <= i2) {
                return i3;
            }
            return 0;
        }

        static int findFirstKeyLessThan(SparseIntArray sparseIntArray, int i) {
            int size = sparseIntArray.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (sparseIntArray.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= sparseIntArray.size()) {
                return -1;
            }
            return sparseIntArray.keyAt(i4);
        }

        public int getSpanGroupIndex(int i, int i2) {
            int i3;
            int i4;
            int i5;
            int findFirstKeyLessThan;
            if (!this.mCacheSpanGroupIndices || (findFirstKeyLessThan = findFirstKeyLessThan(this.mSpanGroupIndexCache, i)) == -1) {
                i5 = 0;
                i4 = 0;
                i3 = 0;
            } else {
                i4 = this.mSpanGroupIndexCache.get(findFirstKeyLessThan);
                i3 = findFirstKeyLessThan + 1;
                i5 = getSpanSize(findFirstKeyLessThan) + getCachedSpanIndex(findFirstKeyLessThan, i2);
                if (i5 == i2) {
                    i4++;
                    i5 = 0;
                }
            }
            int spanSize = getSpanSize(i);
            while (i3 < i) {
                int spanSize2 = getSpanSize(i3);
                i5 += spanSize2;
                if (i5 == i2) {
                    i4++;
                    i5 = 0;
                } else if (i5 > i2) {
                    i4++;
                    i5 = spanSize2;
                }
                i3++;
            }
            return i5 + spanSize > i2 ? i4 + 1 : i4;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2;
        int i3;
        int i4;
        View view2;
        boolean z;
        RecyclerView.Recycler recycler2 = recycler;
        RecyclerView.State state2 = state;
        View findContainingItemView = findContainingItemView(view);
        View view3 = null;
        if (findContainingItemView == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        int i5 = layoutParams.a;
        int i6 = layoutParams.a + layoutParams.b;
        if (super.onFocusSearchFailed(view, i, recycler, state) == null) {
            return null;
        }
        if ((convertFocusDirectionToLayoutDirection(i) == 1) != this.mShouldReverseLayout) {
            i4 = getChildCount() - 1;
            i3 = -1;
            i2 = -1;
        } else {
            i3 = getChildCount();
            i2 = 1;
            i4 = 0;
        }
        boolean z2 = this.mOrientation == 1 && isLayoutRTL();
        int a = a(recycler2, state2, i4);
        int i7 = -1;
        int i8 = -1;
        int i9 = 0;
        int i10 = 0;
        View view4 = null;
        while (true) {
            if (i4 == i3) {
                view2 = view4;
                break;
            }
            int a2 = a(recycler2, state2, i4);
            View childAt = getChildAt(i4);
            if (childAt == findContainingItemView) {
                view2 = view4;
                break;
            }
            if (!childAt.hasFocusable() || a2 == a) {
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                int i11 = layoutParams2.a;
                findContainingItemView = findContainingItemView;
                i3 = i3;
                int i12 = layoutParams2.a + layoutParams2.b;
                if (childAt.hasFocusable() && i11 == i5 && i12 == i6) {
                    return childAt;
                }
                if ((!childAt.hasFocusable() || view3 != null) && (childAt.hasFocusable() || view4 != null)) {
                    int min = Math.min(i12, i6) - Math.max(i11, i5);
                    if (!childAt.hasFocusable()) {
                        if (view3 == null) {
                            i7 = i7;
                            view4 = view4;
                            boolean z3 = false;
                            if (isViewPartiallyVisible(childAt, false, true)) {
                                i10 = i10;
                                if (min > i10) {
                                    i8 = i8;
                                    z = true;
                                } else if (min == i10) {
                                    i8 = i8;
                                    if (i11 > i8) {
                                        z3 = true;
                                    }
                                    if (z2 == z3) {
                                        z = true;
                                    }
                                } else {
                                    i8 = i8;
                                }
                            } else {
                                i10 = i10;
                                i8 = i8;
                            }
                        } else {
                            i7 = i7;
                            view4 = view4;
                            i10 = i10;
                            i8 = i8;
                        }
                        z = false;
                    } else if (min > i9) {
                        i7 = i7;
                        view4 = view4;
                        i10 = i10;
                        i8 = i8;
                        z = true;
                    } else {
                        if (min == i9) {
                            if (z2 == (i11 > i7)) {
                                i7 = i7;
                                view4 = view4;
                                i10 = i10;
                                i8 = i8;
                                z = true;
                            }
                        }
                        i7 = i7;
                        view4 = view4;
                        i10 = i10;
                        i8 = i8;
                        z = false;
                    }
                } else {
                    i7 = i7;
                    view4 = view4;
                    i10 = i10;
                    i8 = i8;
                    z = true;
                }
                if (z) {
                    if (childAt.hasFocusable()) {
                        int i13 = layoutParams2.a;
                        i9 = Math.min(i12, i6) - Math.max(i11, i5);
                        view4 = view4;
                        i7 = i13;
                        i8 = i8;
                        view3 = childAt;
                    } else {
                        int i14 = layoutParams2.a;
                        i10 = Math.min(i12, i6) - Math.max(i11, i5);
                        view4 = childAt;
                        i8 = i14;
                    }
                    i4 += i2;
                    recycler2 = recycler;
                    state2 = state;
                }
            } else if (view3 != null) {
                view2 = view4;
                break;
            } else {
                findContainingItemView = findContainingItemView;
                i7 = i7;
                view4 = view4;
                i3 = i3;
                i10 = i10;
                i8 = i8;
            }
            i4 += i2;
            recycler2 = recycler;
            state2 = state;
        }
        return view3 != null ? view3 : view2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.a;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        if (this.i) {
            return a(state);
        }
        return super.computeHorizontalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        if (this.i) {
            return a(state);
        }
        return super.computeVerticalScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        if (this.i) {
            return b(state);
        }
        return super.computeHorizontalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        if (this.i) {
            return b(state);
        }
        return super.computeVerticalScrollOffset(state);
    }

    public void setUsingSpansToEstimateScrollbarDimensions(boolean z) {
        this.i = z;
    }

    public boolean isUsingSpansToEstimateScrollbarDimensions() {
        return this.i;
    }

    private int a(RecyclerView.State state) {
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!isSmoothScrollbarEnabled(), true);
        View findFirstVisibleChildClosestToEnd = findFirstVisibleChildClosestToEnd(!isSmoothScrollbarEnabled(), true);
        if (findFirstVisibleChildClosestToStart == null || findFirstVisibleChildClosestToEnd == null) {
            return 0;
        }
        if (!isSmoothScrollbarEnabled()) {
            return this.g.getCachedSpanGroupIndex(state.getItemCount() - 1, this.b) + 1;
        }
        int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(findFirstVisibleChildClosestToEnd) - this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart);
        int cachedSpanGroupIndex = this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.b);
        return (int) ((decoratedEnd / ((this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.b) - cachedSpanGroupIndex) + 1)) * (this.g.getCachedSpanGroupIndex(state.getItemCount() - 1, this.b) + 1));
    }

    private int b(RecyclerView.State state) {
        int i;
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        boolean isSmoothScrollbarEnabled = isSmoothScrollbarEnabled();
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!isSmoothScrollbarEnabled, true);
        View findFirstVisibleChildClosestToEnd = findFirstVisibleChildClosestToEnd(!isSmoothScrollbarEnabled, true);
        if (findFirstVisibleChildClosestToStart == null || findFirstVisibleChildClosestToEnd == null) {
            return 0;
        }
        int cachedSpanGroupIndex = this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.b);
        int cachedSpanGroupIndex2 = this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.b);
        int min = Math.min(cachedSpanGroupIndex, cachedSpanGroupIndex2);
        int max = Math.max(cachedSpanGroupIndex, cachedSpanGroupIndex2);
        int cachedSpanGroupIndex3 = this.g.getCachedSpanGroupIndex(state.getItemCount() - 1, this.b) + 1;
        if (this.mShouldReverseLayout) {
            i = Math.max(0, (cachedSpanGroupIndex3 - max) - 1);
        } else {
            i = Math.max(0, min);
        }
        if (!isSmoothScrollbarEnabled) {
            return i;
        }
        return Math.round((i * (Math.abs(this.mOrientationHelper.getDecoratedEnd(findFirstVisibleChildClosestToEnd) - this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart)) / ((this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.b) - this.g.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.b)) + 1))) + (this.mOrientationHelper.getStartAfterPadding() - this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart)));
    }

    /* loaded from: classes.dex */
    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanIndex(int i, int i2) {
            return i % i2;
        }
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int a = -1;
        int b = 0;

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

        public int getSpanIndex() {
            return this.a;
        }

        public int getSpanSize() {
            return this.b;
        }
    }
}
