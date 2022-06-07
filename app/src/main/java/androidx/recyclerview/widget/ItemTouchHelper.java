package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int START = 16;
    public static final int UP = 1;
    private a A;
    private Rect C;
    private long D;
    float c;
    float d;
    float e;
    float f;
    @NonNull
    Callback h;
    int i;
    RecyclerView k;
    VelocityTracker m;
    GestureDetectorCompat p;
    private float r;
    private float s;
    private float t;
    private float u;
    private int w;
    private List<RecyclerView.ViewHolder> x;
    private List<Integer> y;
    final List<View> a = new ArrayList();
    private final float[] q = new float[2];
    RecyclerView.ViewHolder b = null;
    int g = -1;
    private int v = 0;
    @VisibleForTesting
    List<b> j = new ArrayList();
    final Runnable l = new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.1
        @Override // java.lang.Runnable
        public void run() {
            if (ItemTouchHelper.this.b != null && ItemTouchHelper.this.b()) {
                if (ItemTouchHelper.this.b != null) {
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    itemTouchHelper.a(itemTouchHelper.b);
                }
                ItemTouchHelper.this.k.removeCallbacks(ItemTouchHelper.this.l);
                ViewCompat.postOnAnimation(ItemTouchHelper.this.k, this);
            }
        }
    };
    private RecyclerView.ChildDrawingOrderCallback z = null;
    View n = null;
    int o = -1;
    private final RecyclerView.OnItemTouchListener B = new RecyclerView.OnItemTouchListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            int findPointerIndex;
            b b2;
            ItemTouchHelper.this.p.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                ItemTouchHelper.this.g = motionEvent.getPointerId(0);
                ItemTouchHelper.this.c = motionEvent.getX();
                ItemTouchHelper.this.d = motionEvent.getY();
                ItemTouchHelper.this.c();
                if (ItemTouchHelper.this.b == null && (b2 = ItemTouchHelper.this.b(motionEvent)) != null) {
                    ItemTouchHelper.this.c -= b2.m;
                    ItemTouchHelper.this.d -= b2.n;
                    ItemTouchHelper.this.a(b2.h, true);
                    if (ItemTouchHelper.this.a.remove(b2.h.itemView)) {
                        ItemTouchHelper.this.h.clearView(ItemTouchHelper.this.k, b2.h);
                    }
                    ItemTouchHelper.this.a(b2.h, b2.i);
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    itemTouchHelper.a(motionEvent, itemTouchHelper.i, 0);
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                itemTouchHelper2.g = -1;
                itemTouchHelper2.a((RecyclerView.ViewHolder) null, 0);
            } else if (ItemTouchHelper.this.g != -1 && (findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.g)) >= 0) {
                ItemTouchHelper.this.a(actionMasked, motionEvent, findPointerIndex);
            }
            if (ItemTouchHelper.this.m != null) {
                ItemTouchHelper.this.m.addMovement(motionEvent);
            }
            return ItemTouchHelper.this.b != null;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            ItemTouchHelper.this.p.onTouchEvent(motionEvent);
            if (ItemTouchHelper.this.m != null) {
                ItemTouchHelper.this.m.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.g != -1) {
                int actionMasked = motionEvent.getActionMasked();
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.g);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.a(actionMasked, motionEvent, findPointerIndex);
                }
                RecyclerView.ViewHolder viewHolder = ItemTouchHelper.this.b;
                if (viewHolder != null) {
                    int i = 0;
                    if (actionMasked != 6) {
                        switch (actionMasked) {
                            case 1:
                                break;
                            case 2:
                                if (findPointerIndex >= 0) {
                                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                                    itemTouchHelper.a(motionEvent, itemTouchHelper.i, findPointerIndex);
                                    ItemTouchHelper.this.a(viewHolder);
                                    ItemTouchHelper.this.k.removeCallbacks(ItemTouchHelper.this.l);
                                    ItemTouchHelper.this.l.run();
                                    ItemTouchHelper.this.k.invalidate();
                                    return;
                                }
                                return;
                            case 3:
                                if (ItemTouchHelper.this.m != null) {
                                    ItemTouchHelper.this.m.clear();
                                    break;
                                }
                                break;
                            default:
                                return;
                        }
                        ItemTouchHelper.this.a((RecyclerView.ViewHolder) null, 0);
                        ItemTouchHelper.this.g = -1;
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == ItemTouchHelper.this.g) {
                        if (actionIndex == 0) {
                            i = 1;
                        }
                        ItemTouchHelper.this.g = motionEvent.getPointerId(i);
                        ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                        itemTouchHelper2.a(motionEvent, itemTouchHelper2.i, actionIndex);
                    }
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.a((RecyclerView.ViewHolder) null, 0);
            }
        }
    };

    /* loaded from: classes.dex */
    public interface ViewDropHandler {
        void prepareForDrop(@NonNull View view, @NonNull View view2, int i, int i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewAttachedToWindow(@NonNull View view) {
    }

    public ItemTouchHelper(@NonNull Callback callback) {
        this.h = callback;
    }

    private static boolean a(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view.getHeight());
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.k;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                e();
            }
            this.k = recyclerView;
            if (recyclerView != null) {
                Resources resources = recyclerView.getResources();
                this.r = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
                this.s = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
                d();
            }
        }
    }

    private void d() {
        this.w = ViewConfiguration.get(this.k.getContext()).getScaledTouchSlop();
        this.k.addItemDecoration(this);
        this.k.addOnItemTouchListener(this.B);
        this.k.addOnChildAttachStateChangeListener(this);
        f();
    }

    private void e() {
        this.k.removeItemDecoration(this);
        this.k.removeOnItemTouchListener(this.B);
        this.k.removeOnChildAttachStateChangeListener(this);
        for (int size = this.j.size() - 1; size >= 0; size--) {
            b bVar = this.j.get(0);
            bVar.b();
            this.h.clearView(this.k, bVar.h);
        }
        this.j.clear();
        this.n = null;
        this.o = -1;
        h();
        g();
    }

    private void f() {
        this.A = new a();
        this.p = new GestureDetectorCompat(this.k.getContext(), this.A);
    }

    private void g() {
        a aVar = this.A;
        if (aVar != null) {
            aVar.a();
            this.A = null;
        }
        if (this.p != null) {
            this.p = null;
        }
    }

    private void a(float[] fArr) {
        if ((this.i & 12) != 0) {
            fArr[0] = (this.t + this.e) - this.b.itemView.getLeft();
        } else {
            fArr[0] = this.b.itemView.getTranslationX();
        }
        if ((this.i & 3) != 0) {
            fArr[1] = (this.u + this.f) - this.b.itemView.getTop();
        } else {
            fArr[1] = this.b.itemView.getTranslationY();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2 = 0.0f;
        if (this.b != null) {
            a(this.q);
            float[] fArr = this.q;
            f2 = fArr[0];
            f = fArr[1];
        } else {
            f = 0.0f;
        }
        this.h.onDrawOver(canvas, recyclerView, this.b, this.j, this.v, f2, f);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        this.o = -1;
        float f2 = 0.0f;
        if (this.b != null) {
            a(this.q);
            float[] fArr = this.q;
            f2 = fArr[0];
            f = fArr[1];
        } else {
            f = 0.0f;
        }
        this.h.onDraw(canvas, recyclerView, this.b, this.j, this.v, f2, f);
    }

    void a(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        boolean z2;
        float f;
        float f2;
        if (viewHolder != this.b || i != this.v) {
            this.D = Long.MIN_VALUE;
            int i2 = this.v;
            a(viewHolder, true);
            this.v = i;
            if (i == 2) {
                if (viewHolder != null) {
                    this.n = viewHolder.itemView;
                    i();
                } else {
                    throw new IllegalArgumentException("Must pass a ViewHolder when dragging");
                }
            }
            int i3 = (1 << ((i * 8) + 8)) - 1;
            final RecyclerView.ViewHolder viewHolder2 = this.b;
            if (viewHolder2 != null) {
                if (viewHolder2.itemView.getParent() != null) {
                    final int c = i2 == 2 ? 0 : c(viewHolder2);
                    h();
                    if (c != 4 && c != 8 && c != 16 && c != 32) {
                        switch (c) {
                            case 1:
                            case 2:
                                f = Math.signum(this.f) * this.k.getHeight();
                                f2 = 0.0f;
                                break;
                            default:
                                f2 = 0.0f;
                                f = 0.0f;
                                break;
                        }
                    } else {
                        f2 = Math.signum(this.e) * this.k.getWidth();
                        f = 0.0f;
                    }
                    int i4 = i2 == 2 ? 8 : c > 0 ? 2 : 4;
                    a(this.q);
                    float[] fArr = this.q;
                    float f3 = fArr[0];
                    float f4 = fArr[1];
                    b bVar = new b(viewHolder2, i4, i2, f3, f4, f2, f) { // from class: androidx.recyclerview.widget.ItemTouchHelper.3
                        @Override // androidx.recyclerview.widget.ItemTouchHelper.b, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            if (!this.o) {
                                if (c <= 0) {
                                    ItemTouchHelper.this.h.clearView(ItemTouchHelper.this.k, viewHolder2);
                                } else {
                                    ItemTouchHelper.this.a.add(viewHolder2.itemView);
                                    this.l = true;
                                    int i5 = c;
                                    if (i5 > 0) {
                                        ItemTouchHelper.this.a(this, i5);
                                    }
                                }
                                if (ItemTouchHelper.this.n == viewHolder2.itemView) {
                                    ItemTouchHelper.this.a(viewHolder2.itemView);
                                }
                            }
                        }
                    };
                    bVar.a(this.h.getAnimationDuration(this.k, i4, f2 - f3, f - f4));
                    this.j.add(bVar);
                    bVar.a();
                    z = true;
                } else {
                    a(viewHolder2.itemView);
                    this.h.clearView(this.k, viewHolder2);
                    z = false;
                }
                this.b = null;
            } else {
                z = false;
            }
            if (viewHolder != null) {
                this.i = (this.h.getAbsoluteMovementFlags(this.k, viewHolder) & i3) >> (this.v * 8);
                this.t = viewHolder.itemView.getLeft();
                this.u = viewHolder.itemView.getTop();
                this.b = viewHolder;
                if (i == 2) {
                    z2 = false;
                    this.b.itemView.performHapticFeedback(0);
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            ViewParent parent = this.k.getParent();
            if (parent != null) {
                if (this.b != null) {
                    z2 = true;
                }
                parent.requestDisallowInterceptTouchEvent(z2);
            }
            if (!z) {
                this.k.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.h.onSelectedChanged(this.b, this.v);
            this.k.invalidate();
        }
    }

    void a(final b bVar, final int i) {
        this.k.post(new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.4
            @Override // java.lang.Runnable
            public void run() {
                if (ItemTouchHelper.this.k != null && ItemTouchHelper.this.k.isAttachedToWindow() && !bVar.o && bVar.h.getAbsoluteAdapterPosition() != -1) {
                    RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.k.getItemAnimator();
                    if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !ItemTouchHelper.this.a()) {
                        ItemTouchHelper.this.h.onSwiped(bVar.h, i);
                    } else {
                        ItemTouchHelper.this.k.post(this);
                    }
                }
            }
        });
    }

    boolean a() {
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            if (!this.j.get(i).p) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c5, code lost:
        if (r1 > 0) goto L_0x00c9;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0106 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0112  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean b() {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.b():boolean");
    }

    private List<RecyclerView.ViewHolder> b(RecyclerView.ViewHolder viewHolder) {
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        List<RecyclerView.ViewHolder> list = this.x;
        if (list == null) {
            this.x = new ArrayList();
            this.y = new ArrayList();
        } else {
            list.clear();
            this.y.clear();
        }
        int boundingBoxMargin = this.h.getBoundingBoxMargin();
        int round = Math.round(this.t + this.e) - boundingBoxMargin;
        int round2 = Math.round(this.u + this.f) - boundingBoxMargin;
        int i = boundingBoxMargin * 2;
        int width = viewHolder2.itemView.getWidth() + round + i;
        int height = viewHolder2.itemView.getHeight() + round2 + i;
        int i2 = (round + width) / 2;
        int i3 = (round2 + height) / 2;
        RecyclerView.LayoutManager layoutManager = this.k.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int i4 = 0;
        while (i4 < childCount) {
            View childAt = layoutManager.getChildAt(i4);
            if (childAt != viewHolder2.itemView && childAt.getBottom() >= round2 && childAt.getTop() <= height && childAt.getRight() >= round && childAt.getLeft() <= width) {
                RecyclerView.ViewHolder childViewHolder = this.k.getChildViewHolder(childAt);
                if (this.h.canDropOver(this.k, this.b, childViewHolder)) {
                    int abs = Math.abs(i2 - ((childAt.getLeft() + childAt.getRight()) / 2));
                    int abs2 = Math.abs(i3 - ((childAt.getTop() + childAt.getBottom()) / 2));
                    int i5 = (abs * abs) + (abs2 * abs2);
                    int size = this.x.size();
                    int i6 = 0;
                    for (int i7 = 0; i7 < size && i5 > this.y.get(i7).intValue(); i7++) {
                        i6++;
                    }
                    this.x.add(i6, childViewHolder);
                    this.y.add(i6, Integer.valueOf(i5));
                }
            }
            i4++;
            viewHolder2 = viewHolder;
        }
        return this.x;
    }

    void a(RecyclerView.ViewHolder viewHolder) {
        if (!this.k.isLayoutRequested() && this.v == 2) {
            float moveThreshold = this.h.getMoveThreshold(viewHolder);
            int i = (int) (this.t + this.e);
            int i2 = (int) (this.u + this.f);
            if (Math.abs(i2 - viewHolder.itemView.getTop()) >= viewHolder.itemView.getHeight() * moveThreshold || Math.abs(i - viewHolder.itemView.getLeft()) >= viewHolder.itemView.getWidth() * moveThreshold) {
                List<RecyclerView.ViewHolder> b2 = b(viewHolder);
                if (b2.size() != 0) {
                    RecyclerView.ViewHolder chooseDropTarget = this.h.chooseDropTarget(viewHolder, b2, i, i2);
                    if (chooseDropTarget == null) {
                        this.x.clear();
                        this.y.clear();
                        return;
                    }
                    int absoluteAdapterPosition = chooseDropTarget.getAbsoluteAdapterPosition();
                    int absoluteAdapterPosition2 = viewHolder.getAbsoluteAdapterPosition();
                    if (this.h.onMove(this.k, viewHolder, chooseDropTarget)) {
                        this.h.onMoved(this.k, viewHolder, absoluteAdapterPosition2, chooseDropTarget, absoluteAdapterPosition, i, i2);
                    }
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        a(view);
        RecyclerView.ViewHolder childViewHolder = this.k.getChildViewHolder(view);
        if (childViewHolder != null) {
            RecyclerView.ViewHolder viewHolder = this.b;
            if (viewHolder == null || childViewHolder != viewHolder) {
                a(childViewHolder, false);
                if (this.a.remove(childViewHolder.itemView)) {
                    this.h.clearView(this.k, childViewHolder);
                    return;
                }
                return;
            }
            a((RecyclerView.ViewHolder) null, 0);
        }
    }

    void a(RecyclerView.ViewHolder viewHolder, boolean z) {
        for (int size = this.j.size() - 1; size >= 0; size--) {
            b bVar = this.j.get(size);
            if (bVar.h == viewHolder) {
                bVar.o |= z;
                if (!bVar.p) {
                    bVar.b();
                }
                this.j.remove(size);
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    void c() {
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.m = VelocityTracker.obtain();
    }

    private void h() {
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.m = null;
        }
    }

    private RecyclerView.ViewHolder c(MotionEvent motionEvent) {
        View a2;
        RecyclerView.LayoutManager layoutManager = this.k.getLayoutManager();
        int i = this.g;
        if (i == -1) {
            return null;
        }
        int findPointerIndex = motionEvent.findPointerIndex(i);
        float abs = Math.abs(motionEvent.getX(findPointerIndex) - this.c);
        float abs2 = Math.abs(motionEvent.getY(findPointerIndex) - this.d);
        int i2 = this.w;
        if (abs < i2 && abs2 < i2) {
            return null;
        }
        if (abs > abs2 && layoutManager.canScrollHorizontally()) {
            return null;
        }
        if ((abs2 <= abs || !layoutManager.canScrollVertically()) && (a2 = a(motionEvent)) != null) {
            return this.k.getChildViewHolder(a2);
        }
        return null;
    }

    void a(int i, MotionEvent motionEvent, int i2) {
        RecyclerView.ViewHolder c;
        int absoluteMovementFlags;
        if (this.b == null && i == 2 && this.v != 2 && this.h.isItemViewSwipeEnabled() && this.k.getScrollState() != 1 && (c = c(motionEvent)) != null && (absoluteMovementFlags = (this.h.getAbsoluteMovementFlags(this.k, c) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) != 0) {
            float x = motionEvent.getX(i2);
            float y = motionEvent.getY(i2);
            float f = x - this.c;
            float f2 = y - this.d;
            float abs = Math.abs(f);
            float abs2 = Math.abs(f2);
            int i3 = this.w;
            if (abs >= i3 || abs2 >= i3) {
                if (abs > abs2) {
                    if (f < 0.0f && (absoluteMovementFlags & 4) == 0) {
                        return;
                    }
                    if (f > 0.0f && (absoluteMovementFlags & 8) == 0) {
                        return;
                    }
                } else if (f2 < 0.0f && (absoluteMovementFlags & 1) == 0) {
                    return;
                } else {
                    if (f2 > 0.0f && (absoluteMovementFlags & 2) == 0) {
                        return;
                    }
                }
                this.f = 0.0f;
                this.e = 0.0f;
                this.g = motionEvent.getPointerId(0);
                a(c, 1);
            }
        }
    }

    View a(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        RecyclerView.ViewHolder viewHolder = this.b;
        if (viewHolder != null) {
            View view = viewHolder.itemView;
            if (a(view, x, y, this.t + this.e, this.u + this.f)) {
                return view;
            }
        }
        for (int size = this.j.size() - 1; size >= 0; size--) {
            b bVar = this.j.get(size);
            View view2 = bVar.h.itemView;
            if (a(view2, x, y, bVar.m, bVar.n)) {
                return view2;
            }
        }
        return this.k.findChildViewUnder(x, y);
    }

    public void startDrag(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.h.hasDragFlag(this.k, viewHolder)) {
            Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
        } else if (viewHolder.itemView.getParent() != this.k) {
            Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            c();
            this.f = 0.0f;
            this.e = 0.0f;
            a(viewHolder, 2);
        }
    }

    public void startSwipe(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!this.h.hasSwipeFlag(this.k, viewHolder)) {
            Log.e("ItemTouchHelper", "Start swipe has been called but swiping is not enabled");
        } else if (viewHolder.itemView.getParent() != this.k) {
            Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            c();
            this.f = 0.0f;
            this.e = 0.0f;
            a(viewHolder, 1);
        }
    }

    b b(MotionEvent motionEvent) {
        if (this.j.isEmpty()) {
            return null;
        }
        View a2 = a(motionEvent);
        for (int size = this.j.size() - 1; size >= 0; size--) {
            b bVar = this.j.get(size);
            if (bVar.h.itemView == a2) {
                return bVar;
            }
        }
        return null;
    }

    void a(MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        this.e = x - this.c;
        this.f = y - this.d;
        if ((i & 4) == 0) {
            this.e = Math.max(0.0f, this.e);
        }
        if ((i & 8) == 0) {
            this.e = Math.min(0.0f, this.e);
        }
        if ((i & 1) == 0) {
            this.f = Math.max(0.0f, this.f);
        }
        if ((i & 2) == 0) {
            this.f = Math.min(0.0f, this.f);
        }
    }

    private int c(RecyclerView.ViewHolder viewHolder) {
        if (this.v == 2) {
            return 0;
        }
        int movementFlags = this.h.getMovementFlags(this.k, viewHolder);
        int convertToAbsoluteDirection = (this.h.convertToAbsoluteDirection(movementFlags, ViewCompat.getLayoutDirection(this.k)) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (convertToAbsoluteDirection == 0) {
            return 0;
        }
        int i = (movementFlags & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (Math.abs(this.e) > Math.abs(this.f)) {
            int b2 = b(viewHolder, convertToAbsoluteDirection);
            if (b2 > 0) {
                return (i & b2) == 0 ? Callback.convertToRelativeDirection(b2, ViewCompat.getLayoutDirection(this.k)) : b2;
            }
            int c = c(viewHolder, convertToAbsoluteDirection);
            if (c > 0) {
                return c;
            }
        } else {
            int c2 = c(viewHolder, convertToAbsoluteDirection);
            if (c2 > 0) {
                return c2;
            }
            int b3 = b(viewHolder, convertToAbsoluteDirection);
            if (b3 > 0) {
                return (i & b3) == 0 ? Callback.convertToRelativeDirection(b3, ViewCompat.getLayoutDirection(this.k)) : b3;
            }
        }
        return 0;
    }

    private int b(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 12) == 0) {
            return 0;
        }
        int i2 = 8;
        int i3 = this.e > 0.0f ? 8 : 4;
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null && this.g > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.h.getSwipeVelocityThreshold(this.s));
            float xVelocity = this.m.getXVelocity(this.g);
            float yVelocity = this.m.getYVelocity(this.g);
            if (xVelocity <= 0.0f) {
                i2 = 4;
            }
            float abs = Math.abs(xVelocity);
            if ((i2 & i) != 0 && i3 == i2 && abs >= this.h.getSwipeEscapeVelocity(this.r) && abs > Math.abs(yVelocity)) {
                return i2;
            }
        }
        float width = this.k.getWidth() * this.h.getSwipeThreshold(viewHolder);
        if ((i & i3) == 0 || Math.abs(this.e) <= width) {
            return 0;
        }
        return i3;
    }

    private int c(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 3) == 0) {
            return 0;
        }
        int i2 = 2;
        int i3 = this.f > 0.0f ? 2 : 1;
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null && this.g > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.h.getSwipeVelocityThreshold(this.s));
            float xVelocity = this.m.getXVelocity(this.g);
            float yVelocity = this.m.getYVelocity(this.g);
            if (yVelocity <= 0.0f) {
                i2 = 1;
            }
            float abs = Math.abs(yVelocity);
            if ((i2 & i) != 0 && i2 == i3 && abs >= this.h.getSwipeEscapeVelocity(this.r) && abs > Math.abs(xVelocity)) {
                return i2;
            }
        }
        float height = this.k.getHeight() * this.h.getSwipeThreshold(viewHolder);
        if ((i & i3) == 0 || Math.abs(this.f) <= height) {
            return 0;
        }
        return i3;
    }

    private void i() {
        if (Build.VERSION.SDK_INT < 21) {
            if (this.z == null) {
                this.z = new RecyclerView.ChildDrawingOrderCallback() { // from class: androidx.recyclerview.widget.ItemTouchHelper.5
                    @Override // androidx.recyclerview.widget.RecyclerView.ChildDrawingOrderCallback
                    public int onGetChildDrawingOrder(int i, int i2) {
                        if (ItemTouchHelper.this.n == null) {
                            return i2;
                        }
                        int i3 = ItemTouchHelper.this.o;
                        if (i3 == -1) {
                            i3 = ItemTouchHelper.this.k.indexOfChild(ItemTouchHelper.this.n);
                            ItemTouchHelper.this.o = i3;
                        }
                        return i2 == i + (-1) ? i3 : i2 < i3 ? i2 : i2 + 1;
                    }
                };
            }
            this.k.setChildDrawingOrderCallback(this.z);
        }
    }

    void a(View view) {
        if (view == this.n) {
            this.n = null;
            if (this.z != null) {
                this.k.setChildDrawingOrderCallback(null);
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.ItemTouchHelper.Callback.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.ItemTouchHelper.Callback.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        private int mCachedMaxScrollSpeed = -1;

        public static int convertToRelativeDirection(int i, int i2) {
            int i3 = i & ABS_HORIZONTAL_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 << 2);
            }
            int i5 = i3 << 1;
            return i4 | ((-789517) & i5) | ((i5 & ABS_HORIZONTAL_DIR_FLAGS) << 2);
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3 = i & RELATIVE_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = i & (~i3);
            if (i2 == 0) {
                return i4 | (i3 >> 2);
            }
            int i5 = i3 >> 1;
            return i4 | ((-3158065) & i5) | ((i5 & RELATIVE_DIR_FLAGS) >> 2);
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public abstract boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2);

        public abstract void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i);

        @NonNull
        public static ItemTouchUIUtil getDefaultUIUtil() {
            return f.a;
        }

        public static int makeMovementFlags(int i, int i2) {
            int makeFlag = makeFlag(0, i2 | i);
            return makeFlag(2, i) | makeFlag(1, i2) | makeFlag;
        }

        final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & 16711680) != 0;
        }

        boolean hasSwipeFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) != 0;
        }

        public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<RecyclerView.ViewHolder> list, int i, int i2) {
            int i3;
            int bottom;
            int top;
            int abs;
            int left;
            int abs2;
            int right;
            int width = i + viewHolder.itemView.getWidth();
            int height = i2 + viewHolder.itemView.getHeight();
            int left2 = i - viewHolder.itemView.getLeft();
            int top2 = i2 - viewHolder.itemView.getTop();
            int size = list.size();
            RecyclerView.ViewHolder viewHolder2 = null;
            int i4 = -1;
            for (int i5 = 0; i5 < size; i5++) {
                RecyclerView.ViewHolder viewHolder3 = list.get(i5);
                if (left2 <= 0 || (right = viewHolder3.itemView.getRight() - width) >= 0 || viewHolder3.itemView.getRight() <= viewHolder.itemView.getRight() || (i3 = Math.abs(right)) <= i4) {
                    i3 = i4;
                } else {
                    viewHolder2 = viewHolder3;
                }
                if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (abs2 = Math.abs(left)) > i3) {
                    i3 = abs2;
                    viewHolder2 = viewHolder3;
                }
                if (top2 < 0 && (top = viewHolder3.itemView.getTop() - i2) > 0 && viewHolder3.itemView.getTop() < viewHolder.itemView.getTop() && (abs = Math.abs(top)) > i3) {
                    i3 = abs;
                    viewHolder2 = viewHolder3;
                }
                if (top2 <= 0 || (bottom = viewHolder3.itemView.getBottom() - height) >= 0 || viewHolder3.itemView.getBottom() <= viewHolder.itemView.getBottom() || (i4 = Math.abs(bottom)) <= i3) {
                    i4 = i3;
                } else {
                    viewHolder2 = viewHolder3;
                }
            }
            return viewHolder2;
        }

        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                f.a.onSelected(viewHolder.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<b> list, int i, float f, float f2) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                b bVar = list.get(i2);
                bVar.c();
                int save = canvas.save();
                onChildDraw(canvas, recyclerView, bVar.h, bVar.m, bVar.n, bVar.i, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas.save();
                onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(save2);
            }
        }

        void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<b> list, int i, float f, float f2) {
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                b bVar = list.get(i2);
                int save = canvas.save();
                onChildDrawOver(canvas, recyclerView, bVar.h, bVar.m, bVar.n, bVar.i, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                int save2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(save2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                b bVar2 = list.get(i3);
                if (bVar2.p && !bVar2.l) {
                    list.remove(i3);
                } else if (!bVar2.p) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            f.a.clearView(viewHolder.itemView);
        }

        public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            f.a.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            f.a.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public long getAnimationDuration(@NonNull RecyclerView recyclerView, int i, float f, float f2) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator == null) {
                return i == 8 ? 200L : 250L;
            }
            if (i == 8) {
                return itemAnimator.getMoveDuration();
            }
            return itemAnimator.getRemoveDuration();
        }

        public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float f = 1.0f;
            int signum = (int) (((int) Math.signum(i2)) * getMaxDragScroll(recyclerView) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (Math.abs(i2) * 1.0f) / i)));
            if (j <= 2000) {
                f = ((float) j) / 2000.0f;
            }
            int interpolation = (int) (signum * sDragScrollInterpolator.getInterpolation(f));
            return interpolation == 0 ? i2 > 0 ? 1 : -1 : interpolation;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class SimpleCallback extends Callback {
        private int a;
        private int b;

        public SimpleCallback(int i, int i2) {
            this.a = i2;
            this.b = i;
        }

        public void setDefaultSwipeDirs(int i) {
            this.a = i;
        }

        public void setDefaultDragDirs(int i) {
            this.b = i;
        }

        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.a;
        }

        public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return this.b;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
    }

    /* loaded from: classes.dex */
    public class a extends GestureDetector.SimpleOnGestureListener {
        private boolean b = true;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        a() {
            ItemTouchHelper.this = r1;
        }

        void a() {
            this.b = false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            View a;
            RecyclerView.ViewHolder childViewHolder;
            if (this.b && (a = ItemTouchHelper.this.a(motionEvent)) != null && (childViewHolder = ItemTouchHelper.this.k.getChildViewHolder(a)) != null && ItemTouchHelper.this.h.hasDragFlag(ItemTouchHelper.this.k, childViewHolder) && motionEvent.getPointerId(0) == ItemTouchHelper.this.g) {
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.g);
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                itemTouchHelper.c = x;
                itemTouchHelper.d = y;
                itemTouchHelper.f = 0.0f;
                itemTouchHelper.e = 0.0f;
                if (itemTouchHelper.h.isLongPressDragEnabled()) {
                    ItemTouchHelper.this.a(childViewHolder, 2);
                }
            }
        }
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class b implements Animator.AnimatorListener {
        private float a;
        final float d;
        final float e;
        final float f;
        final float g;
        final RecyclerView.ViewHolder h;
        final int i;
        final int k;
        boolean l;
        float m;
        float n;
        boolean o = false;
        boolean p = false;
        @VisibleForTesting
        final ValueAnimator j = ValueAnimator.ofFloat(0.0f, 1.0f);

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        b(RecyclerView.ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            this.i = i2;
            this.k = i;
            this.h = viewHolder;
            this.d = f;
            this.e = f2;
            this.f = f3;
            this.g = f4;
            this.j.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.b.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    b.this.a(valueAnimator.getAnimatedFraction());
                }
            });
            this.j.setTarget(viewHolder.itemView);
            this.j.addListener(this);
            a(0.0f);
        }

        public void a(long j) {
            this.j.setDuration(j);
        }

        public void a() {
            this.h.setIsRecyclable(false);
            this.j.start();
        }

        public void b() {
            this.j.cancel();
        }

        public void a(float f) {
            this.a = f;
        }

        public void c() {
            float f = this.d;
            float f2 = this.f;
            if (f == f2) {
                this.m = this.h.itemView.getTranslationX();
            } else {
                this.m = f + (this.a * (f2 - f));
            }
            float f3 = this.e;
            float f4 = this.g;
            if (f3 == f4) {
                this.n = this.h.itemView.getTranslationY();
            } else {
                this.n = f3 + (this.a * (f4 - f3));
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.p) {
                this.h.setIsRecyclable(true);
            }
            this.p = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            a(1.0f);
        }
    }
}
