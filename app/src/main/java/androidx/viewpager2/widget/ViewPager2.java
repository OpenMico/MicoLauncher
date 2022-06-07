package androidx.viewpager2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R;
import androidx.viewpager2.adapter.StatefulAdapter;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ViewPager2 extends ViewGroup {
    public static final int OFFSCREEN_PAGE_LIMIT_DEFAULT = -1;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static boolean a = true;
    int b;
    RecyclerView d;
    e e;
    a f;
    private LinearLayoutManager k;
    private Parcelable m;
    private PagerSnapHelper n;
    private b o;
    private c p;
    private d q;
    private final Rect g = new Rect();
    private final Rect h = new Rect();
    private b i = new b(3);
    boolean c = false;
    private RecyclerView.AdapterDataObserver j = new c() { // from class: androidx.viewpager2.widget.ViewPager2.1
        @Override // androidx.viewpager2.widget.ViewPager2.c, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            ViewPager2 viewPager2 = ViewPager2.this;
            viewPager2.c = true;
            viewPager2.e.a();
        }
    };
    private int l = -1;
    private RecyclerView.ItemAnimator r = null;
    private boolean s = false;
    private boolean t = true;
    private int u = -1;

    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface OffscreenPageLimit {
    }

    /* loaded from: classes.dex */
    public static abstract class OnPageChangeCallback {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, @Px int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Orientation {
    }

    /* loaded from: classes.dex */
    public interface PageTransformer {
        void transformPage(@NonNull View view, float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface ScrollState {
    }

    public ViewPager2(@NonNull Context context) {
        super(context);
        a(context, (AttributeSet) null);
    }

    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context, attributeSet);
    }

    @RequiresApi(21)
    public ViewPager2(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        this.f = a ? new e() : new b();
        this.d = new g(context);
        this.d.setId(ViewCompat.generateViewId());
        this.d.setDescendantFocusability(131072);
        this.k = new d(context);
        this.d.setLayoutManager(this.k);
        this.d.setScrollingTouchSlop(1);
        b(context, attributeSet);
        this.d.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.d.addOnChildAttachStateChangeListener(d());
        this.e = new e(this);
        this.p = new c(this, this.e, this.d);
        this.n = new f();
        this.n.attachToRecyclerView(this.d);
        this.d.addOnScrollListener(this.e);
        this.o = new b(3);
        this.e.a(this.o);
        OnPageChangeCallback onPageChangeCallback = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                if (ViewPager2.this.b != i2) {
                    ViewPager2 viewPager2 = ViewPager2.this;
                    viewPager2.b = i2;
                    viewPager2.f.e();
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i2) {
                if (i2 == 0) {
                    ViewPager2.this.a();
                }
            }
        };
        OnPageChangeCallback onPageChangeCallback2 = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i2) {
                ViewPager2.this.clearFocus();
                if (ViewPager2.this.hasFocus()) {
                    ViewPager2.this.d.requestFocus(2);
                }
            }
        };
        this.o.a(onPageChangeCallback);
        this.o.a(onPageChangeCallback2);
        this.f.a(this.o, this.d);
        this.o.a(this.i);
        this.q = new d(this.k);
        this.o.a(this.q);
        RecyclerView recyclerView = this.d;
        attachViewToParent(recyclerView, 0, recyclerView.getLayoutParams());
    }

    private RecyclerView.OnChildAttachStateChangeListener d() {
        return new RecyclerView.OnChildAttachStateChangeListener() { // from class: androidx.viewpager2.widget.ViewPager2.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewAttachedToWindow(@NonNull View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (layoutParams.width != -1 || layoutParams.height != -1) {
                    throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
                }
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    @RequiresApi(23)
    public CharSequence getAccessibilityClassName() {
        if (this.f.a()) {
            return this.f.b();
        }
        return super.getAccessibilityClassName();
    }

    private void b(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewPager2);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, R.styleable.ViewPager2, attributeSet, obtainStyledAttributes, 0, 0);
        }
        try {
            setOrientation(obtainStyledAttributes.getInt(R.styleable.ViewPager2_android_orientation, 0));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    @Nullable
    protected Parcelable onSaveInstanceState() {
        h hVar = new h(super.onSaveInstanceState());
        hVar.a = this.d.getId();
        int i2 = this.l;
        if (i2 == -1) {
            i2 = this.b;
        }
        hVar.b = i2;
        Parcelable parcelable = this.m;
        if (parcelable != null) {
            hVar.c = parcelable;
        } else {
            RecyclerView.Adapter adapter = this.d.getAdapter();
            if (adapter instanceof StatefulAdapter) {
                hVar.c = ((StatefulAdapter) adapter).saveState();
            }
        }
        return hVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof h)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        h hVar = (h) parcelable;
        super.onRestoreInstanceState(hVar.getSuperState());
        this.l = hVar.b;
        this.m = hVar.c;
    }

    private void e() {
        RecyclerView.Adapter adapter;
        if (this.l != -1 && (adapter = getAdapter()) != null) {
            Parcelable parcelable = this.m;
            if (parcelable != null) {
                if (adapter instanceof StatefulAdapter) {
                    ((StatefulAdapter) adapter).restoreState(parcelable);
                }
                this.m = null;
            }
            this.b = Math.max(0, Math.min(this.l, adapter.getItemCount() - 1));
            this.l = -1;
            this.d.scrollToPosition(this.b);
            this.f.c();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        Parcelable parcelable = sparseArray.get(getId());
        if (parcelable instanceof h) {
            int i2 = ((h) parcelable).a;
            sparseArray.put(this.d.getId(), sparseArray.get(i2));
            sparseArray.remove(i2);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        e();
    }

    /* loaded from: classes.dex */
    public static class h extends View.BaseSavedState {
        public static final Parcelable.Creator<h> CREATOR = new Parcelable.ClassLoaderCreator<h>() { // from class: androidx.viewpager2.widget.ViewPager2.h.1
            /* renamed from: a */
            public h createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return Build.VERSION.SDK_INT >= 24 ? new h(parcel, classLoader) : new h(parcel);
            }

            /* renamed from: a */
            public h createFromParcel(Parcel parcel) {
                return createFromParcel(parcel, null);
            }

            /* renamed from: a */
            public h[] newArray(int i) {
                return new h[i];
            }
        };
        int a;
        int b;
        Parcelable c;

        @RequiresApi(24)
        h(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel, classLoader);
        }

        h(Parcel parcel) {
            super(parcel);
            a(parcel, null);
        }

        h(Parcelable parcelable) {
            super(parcelable);
        }

        private void a(Parcel parcel, ClassLoader classLoader) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readParcelable(classLoader);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeParcelable(this.c, i);
        }
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        RecyclerView.Adapter adapter2 = this.d.getAdapter();
        this.f.b(adapter2);
        b(adapter2);
        this.d.setAdapter(adapter);
        this.b = 0;
        e();
        this.f.a(adapter);
        a(adapter);
    }

    private void a(@Nullable RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.j);
        }
    }

    private void b(@Nullable RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.j);
        }
    }

    @Nullable
    public RecyclerView.Adapter getAdapter() {
        return this.d.getAdapter();
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        throw new IllegalStateException(getClass().getSimpleName() + " does not support direct child views");
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        measureChild(this.d, i2, i3);
        int measuredWidth = this.d.getMeasuredWidth();
        int measuredHeight = this.d.getMeasuredHeight();
        int measuredState = this.d.getMeasuredState();
        int paddingLeft = measuredWidth + getPaddingLeft() + getPaddingRight();
        int paddingTop = measuredHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i2, measuredState), resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i3, measuredState << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int measuredWidth = this.d.getMeasuredWidth();
        int measuredHeight = this.d.getMeasuredHeight();
        this.g.left = getPaddingLeft();
        this.g.right = (i4 - i2) - getPaddingRight();
        this.g.top = getPaddingTop();
        this.g.bottom = (i5 - i3) - getPaddingBottom();
        Gravity.apply(BadgeDrawable.TOP_START, measuredWidth, measuredHeight, this.g, this.h);
        this.d.layout(this.h.left, this.h.top, this.h.right, this.h.bottom);
        if (this.c) {
            a();
        }
    }

    void a() {
        PagerSnapHelper pagerSnapHelper = this.n;
        if (pagerSnapHelper != null) {
            View findSnapView = pagerSnapHelper.findSnapView(this.k);
            if (findSnapView != null) {
                int position = this.k.getPosition(findSnapView);
                if (position != this.b && getScrollState() == 0) {
                    this.o.onPageSelected(position);
                }
                this.c = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    int getPageSize() {
        RecyclerView recyclerView = this.d;
        if (getOrientation() == 0) {
            return (recyclerView.getWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight();
        }
        return (recyclerView.getHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom();
    }

    public void setOrientation(int i2) {
        this.k.setOrientation(i2);
        this.f.d();
    }

    public int getOrientation() {
        return this.k.getOrientation();
    }

    public boolean b() {
        return this.k.getLayoutDirection() == 1;
    }

    public void setCurrentItem(int i2) {
        setCurrentItem(i2, true);
    }

    public void setCurrentItem(int i2, boolean z) {
        if (!isFakeDragging()) {
            a(i2, z);
            return;
        }
        throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
    }

    void a(int i2, boolean z) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            if (this.l != -1) {
                this.l = Math.max(i2, 0);
            }
        } else if (adapter.getItemCount() > 0) {
            int min = Math.min(Math.max(i2, 0), adapter.getItemCount() - 1);
            if (min == this.b && this.e.e()) {
                return;
            }
            if (min != this.b || !z) {
                double d2 = this.b;
                this.b = min;
                this.f.e();
                if (!this.e.e()) {
                    d2 = this.e.h();
                }
                this.e.a(min, z);
                if (!z) {
                    this.d.scrollToPosition(min);
                    return;
                }
                double d3 = min;
                if (Math.abs(d3 - d2) > 3.0d) {
                    this.d.scrollToPosition(d3 > d2 ? min - 3 : min + 3);
                    RecyclerView recyclerView = this.d;
                    recyclerView.post(new i(min, recyclerView));
                    return;
                }
                this.d.smoothScrollToPosition(min);
            }
        }
    }

    public int getCurrentItem() {
        return this.b;
    }

    public int getScrollState() {
        return this.e.d();
    }

    public boolean beginFakeDrag() {
        return this.p.b();
    }

    public boolean fakeDragBy(@Px @SuppressLint({"SupportAnnotationUsage"}) float f2) {
        return this.p.a(f2);
    }

    public boolean endFakeDrag() {
        return this.p.c();
    }

    public boolean isFakeDragging() {
        return this.p.a();
    }

    public void c() {
        View findSnapView = this.n.findSnapView(this.k);
        if (findSnapView != null) {
            int[] calculateDistanceToFinalSnap = this.n.calculateDistanceToFinalSnap(this.k, findSnapView);
            if (calculateDistanceToFinalSnap[0] != 0 || calculateDistanceToFinalSnap[1] != 0) {
                this.d.smoothScrollBy(calculateDistanceToFinalSnap[0], calculateDistanceToFinalSnap[1]);
            }
        }
    }

    public void setUserInputEnabled(boolean z) {
        this.t = z;
        this.f.f();
    }

    public boolean isUserInputEnabled() {
        return this.t;
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 >= 1 || i2 == -1) {
            this.u = i2;
            this.d.requestLayout();
            return;
        }
        throw new IllegalArgumentException("Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
    }

    public int getOffscreenPageLimit() {
        return this.u;
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i2) {
        return this.d.canScrollHorizontally(i2);
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i2) {
        return this.d.canScrollVertically(i2);
    }

    public void registerOnPageChangeCallback(@NonNull OnPageChangeCallback onPageChangeCallback) {
        this.i.a(onPageChangeCallback);
    }

    public void unregisterOnPageChangeCallback(@NonNull OnPageChangeCallback onPageChangeCallback) {
        this.i.b(onPageChangeCallback);
    }

    public void setPageTransformer(@Nullable PageTransformer pageTransformer) {
        if (pageTransformer != null) {
            if (!this.s) {
                this.r = this.d.getItemAnimator();
                this.s = true;
            }
            this.d.setItemAnimator(null);
        } else if (this.s) {
            this.d.setItemAnimator(this.r);
            this.r = null;
            this.s = false;
        }
        if (pageTransformer != this.q.a()) {
            this.q.a(pageTransformer);
            requestTransform();
        }
    }

    public void requestTransform() {
        if (this.q.a() != null) {
            double h2 = this.e.h();
            int i2 = (int) h2;
            float f2 = (float) (h2 - i2);
            this.q.onPageScrolled(i2, f2, Math.round(getPageSize() * f2));
        }
    }

    @Override // android.view.View
    @RequiresApi(17)
    public void setLayoutDirection(int i2) {
        super.setLayoutDirection(i2);
        this.f.g();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.f.a(accessibilityNodeInfo);
    }

    @Override // android.view.View
    @RequiresApi(16)
    public boolean performAccessibilityAction(int i2, Bundle bundle) {
        if (this.f.a(i2, bundle)) {
            return this.f.b(i2, bundle);
        }
        return super.performAccessibilityAction(i2, bundle);
    }

    /* loaded from: classes.dex */
    public class g extends RecyclerView {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(Context context) {
            super(context);
            ViewPager2.this = r1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        @RequiresApi(23)
        public CharSequence getAccessibilityClassName() {
            if (ViewPager2.this.f.h()) {
                return ViewPager2.this.f.i();
            }
            return super.getAccessibilityClassName();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setFromIndex(ViewPager2.this.b);
            accessibilityEvent.setToIndex(ViewPager2.this.b);
            ViewPager2.this.f.a(accessibilityEvent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.isUserInputEnabled() && super.onTouchEvent(motionEvent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.isUserInputEnabled() && super.onInterceptTouchEvent(motionEvent);
        }
    }

    /* loaded from: classes.dex */
    public class d extends LinearLayoutManager {
        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean requestChildRectangleOnScreen(@NonNull RecyclerView recyclerView, @NonNull View view, @NonNull Rect rect, boolean z, boolean z2) {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(Context context) {
            super(context);
            ViewPager2.this = r1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean performAccessibilityAction(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int i, @Nullable Bundle bundle) {
            if (ViewPager2.this.f.a(i)) {
                return ViewPager2.this.f.b(i);
            }
            return super.performAccessibilityAction(recycler, state, i, bundle);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onInitializeAccessibilityNodeInfo(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
            ViewPager2.this.f.a(accessibilityNodeInfoCompat);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] iArr) {
            int offscreenPageLimit = ViewPager2.this.getOffscreenPageLimit();
            if (offscreenPageLimit == -1) {
                super.calculateExtraLayoutSpace(state, iArr);
                return;
            }
            int pageSize = ViewPager2.this.getPageSize() * offscreenPageLimit;
            iArr[0] = pageSize;
            iArr[1] = pageSize;
        }
    }

    /* loaded from: classes.dex */
    public class f extends PagerSnapHelper {
        f() {
            ViewPager2.this = r1;
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        @Nullable
        public View findSnapView(RecyclerView.LayoutManager layoutManager) {
            if (ViewPager2.this.isFakeDragging()) {
                return null;
            }
            return super.findSnapView(layoutManager);
        }
    }

    /* loaded from: classes.dex */
    public static class i implements Runnable {
        private final int a;
        private final RecyclerView b;

        i(int i, RecyclerView recyclerView) {
            this.a = i;
            this.b = recyclerView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.b.smoothScrollToPosition(this.a);
        }
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.d.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration, int i2) {
        this.d.addItemDecoration(itemDecoration, i2);
    }

    @NonNull
    public RecyclerView.ItemDecoration getItemDecorationAt(int i2) {
        return this.d.getItemDecorationAt(i2);
    }

    public int getItemDecorationCount() {
        return this.d.getItemDecorationCount();
    }

    public void invalidateItemDecorations() {
        this.d.invalidateItemDecorations();
    }

    public void removeItemDecorationAt(int i2) {
        this.d.removeItemDecorationAt(i2);
    }

    public void removeItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.d.removeItemDecoration(itemDecoration);
    }

    /* loaded from: classes.dex */
    public abstract class a {
        void a(@NonNull AccessibilityEvent accessibilityEvent) {
        }

        void a(AccessibilityNodeInfo accessibilityNodeInfo) {
        }

        void a(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        void a(@Nullable RecyclerView.Adapter<?> adapter) {
        }

        void a(@NonNull b bVar, @NonNull RecyclerView recyclerView) {
        }

        boolean a() {
            return false;
        }

        boolean a(int i) {
            return false;
        }

        boolean a(int i, Bundle bundle) {
            return false;
        }

        void b(@Nullable RecyclerView.Adapter<?> adapter) {
        }

        void c() {
        }

        void d() {
        }

        void e() {
        }

        void f() {
        }

        void g() {
        }

        boolean h() {
            return false;
        }

        private a() {
            ViewPager2.this = r1;
        }

        String b() {
            throw new IllegalStateException("Not implemented.");
        }

        boolean b(int i, Bundle bundle) {
            throw new IllegalStateException("Not implemented.");
        }

        boolean b(int i) {
            throw new IllegalStateException("Not implemented.");
        }

        CharSequence i() {
            throw new IllegalStateException("Not implemented.");
        }
    }

    /* loaded from: classes.dex */
    public class b extends a {
        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean h() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b() {
            super();
            ViewPager2.this = r2;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean a(int i) {
            return (i == 8192 || i == 4096) && !ViewPager2.this.isUserInputEnabled();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean b(int i) {
            if (a(i)) {
                return false;
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void a(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (!ViewPager2.this.isUserInputEnabled()) {
                accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfoCompat.setScrollable(false);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public CharSequence i() {
            if (h()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes.dex */
    public class e extends a {
        private final AccessibilityViewCommand c = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.e.1
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                e.this.c(((ViewPager2) view).getCurrentItem() + 1);
                return true;
            }
        };
        private final AccessibilityViewCommand d = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.e.2
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                e.this.c(((ViewPager2) view).getCurrentItem() - 1);
                return true;
            }
        };
        private RecyclerView.AdapterDataObserver e;

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean a() {
            return true;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean a(int i, Bundle bundle) {
            return i == 8192 || i == 4096;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e() {
            super();
            ViewPager2.this = r2;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void a(@NonNull b bVar, @NonNull RecyclerView recyclerView) {
            ViewCompat.setImportantForAccessibility(recyclerView, 2);
            this.e = new c() { // from class: androidx.viewpager2.widget.ViewPager2.e.3
                @Override // androidx.viewpager2.widget.ViewPager2.c, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    e.this.j();
                }
            };
            if (ViewCompat.getImportantForAccessibility(ViewPager2.this) == 0) {
                ViewCompat.setImportantForAccessibility(ViewPager2.this, 1);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public String b() {
            if (a()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void c() {
            j();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void a(@Nullable RecyclerView.Adapter<?> adapter) {
            j();
            if (adapter != null) {
                adapter.registerAdapterDataObserver(this.e);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void b(@Nullable RecyclerView.Adapter<?> adapter) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(this.e);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void d() {
            j();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void e() {
            j();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void f() {
            j();
            if (Build.VERSION.SDK_INT < 21) {
                ViewPager2.this.sendAccessibilityEvent(2048);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void g() {
            j();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void a(AccessibilityNodeInfo accessibilityNodeInfo) {
            b(accessibilityNodeInfo);
            if (Build.VERSION.SDK_INT >= 16) {
                c(accessibilityNodeInfo);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public boolean b(int i, Bundle bundle) {
            int i2;
            if (a(i, bundle)) {
                if (i == 8192) {
                    i2 = ViewPager2.this.getCurrentItem() - 1;
                } else {
                    i2 = ViewPager2.this.getCurrentItem() + 1;
                }
                c(i2);
                return true;
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.a
        public void a(@NonNull AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setSource(ViewPager2.this);
            accessibilityEvent.setClassName(b());
        }

        void c(int i) {
            if (ViewPager2.this.isUserInputEnabled()) {
                ViewPager2.this.a(i, true);
            }
        }

        void j() {
            int itemCount;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i = 16908360;
            ViewCompat.removeAccessibilityAction(viewPager2, 16908360);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908361);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908358);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908359);
            if (ViewPager2.this.getAdapter() != null && (itemCount = ViewPager2.this.getAdapter().getItemCount()) != 0 && ViewPager2.this.isUserInputEnabled()) {
                if (ViewPager2.this.getOrientation() == 0) {
                    boolean b = ViewPager2.this.b();
                    int i2 = b ? 16908360 : 16908361;
                    if (b) {
                        i = 16908361;
                    }
                    if (ViewPager2.this.b < itemCount - 1) {
                        ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i2, null), null, this.c);
                    }
                    if (ViewPager2.this.b > 0) {
                        ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, null), null, this.d);
                        return;
                    }
                    return;
                }
                if (ViewPager2.this.b < itemCount - 1) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908359, null), null, this.c);
                }
                if (ViewPager2.this.b > 0) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908358, null), null, this.d);
                }
            }
        }

        private void b(AccessibilityNodeInfo accessibilityNodeInfo) {
            int i;
            int i2;
            if (ViewPager2.this.getAdapter() == null) {
                i2 = 0;
                i = 0;
            } else if (ViewPager2.this.getOrientation() == 1) {
                i2 = ViewPager2.this.getAdapter().getItemCount();
                i = 0;
            } else {
                i = ViewPager2.this.getAdapter().getItemCount();
                i2 = 0;
            }
            AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(i2, i, false, 0));
        }

        private void c(AccessibilityNodeInfo accessibilityNodeInfo) {
            int itemCount;
            RecyclerView.Adapter adapter = ViewPager2.this.getAdapter();
            if (adapter != null && (itemCount = adapter.getItemCount()) != 0 && ViewPager2.this.isUserInputEnabled()) {
                if (ViewPager2.this.b > 0) {
                    accessibilityNodeInfo.addAction(8192);
                }
                if (ViewPager2.this.b < itemCount - 1) {
                    accessibilityNodeInfo.addAction(4096);
                }
                accessibilityNodeInfo.setScrollable(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static abstract class c extends RecyclerView.AdapterDataObserver {
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        private c() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2, int i3) {
            onChanged();
        }
    }
}
