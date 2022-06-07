package androidx.recyclerview.widget;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public abstract class SnapHelper extends RecyclerView.OnFlingListener {
    RecyclerView a;
    private Scroller b;
    private final RecyclerView.OnScrollListener c = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.SnapHelper.1
        boolean a = false;

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0 && this.a) {
                this.a = false;
                SnapHelper.this.a();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (i != 0 || i2 != 0) {
                this.a = true;
            }
        }
    };

    @Nullable
    public abstract int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view);

    @Nullable
    public abstract View findSnapView(RecyclerView.LayoutManager layoutManager);

    public abstract int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2);

    @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
    public boolean onFling(int i, int i2) {
        RecyclerView.LayoutManager layoutManager = this.a.getLayoutManager();
        if (layoutManager == null || this.a.getAdapter() == null) {
            return false;
        }
        int minFlingVelocity = this.a.getMinFlingVelocity();
        return (Math.abs(i2) > minFlingVelocity || Math.abs(i) > minFlingVelocity) && a(layoutManager, i, i2);
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        RecyclerView recyclerView2 = this.a;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                c();
            }
            this.a = recyclerView;
            if (this.a != null) {
                b();
                this.b = new Scroller(this.a.getContext(), new DecelerateInterpolator());
                a();
            }
        }
    }

    private void b() throws IllegalStateException {
        if (this.a.getOnFlingListener() == null) {
            this.a.addOnScrollListener(this.c);
            this.a.setOnFlingListener(this);
            return;
        }
        throw new IllegalStateException("An instance of OnFlingListener already set.");
    }

    private void c() {
        this.a.removeOnScrollListener(this.c);
        this.a.setOnFlingListener(null);
    }

    public int[] calculateScrollDistance(int i, int i2) {
        this.b.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[]{this.b.getFinalX(), this.b.getFinalY()};
    }

    private boolean a(@NonNull RecyclerView.LayoutManager layoutManager, int i, int i2) {
        RecyclerView.SmoothScroller createScroller;
        int findTargetSnapPosition;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (createScroller = createScroller(layoutManager)) == null || (findTargetSnapPosition = findTargetSnapPosition(layoutManager, i, i2)) == -1) {
            return false;
        }
        createScroller.setTargetPosition(findTargetSnapPosition);
        layoutManager.startSmoothScroll(createScroller);
        return true;
    }

    void a() {
        RecyclerView.LayoutManager layoutManager;
        View findSnapView;
        RecyclerView recyclerView = this.a;
        if (recyclerView != null && (layoutManager = recyclerView.getLayoutManager()) != null && (findSnapView = findSnapView(layoutManager)) != null) {
            int[] calculateDistanceToFinalSnap = calculateDistanceToFinalSnap(layoutManager, findSnapView);
            if (calculateDistanceToFinalSnap[0] != 0 || calculateDistanceToFinalSnap[1] != 0) {
                this.a.smoothScrollBy(calculateDistanceToFinalSnap[0], calculateDistanceToFinalSnap[1]);
            }
        }
    }

    @Nullable
    protected RecyclerView.SmoothScroller createScroller(@NonNull RecyclerView.LayoutManager layoutManager) {
        return createSnapScroller(layoutManager);
    }

    @Nullable
    @Deprecated
    protected LinearSmoothScroller createSnapScroller(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(this.a.getContext()) { // from class: androidx.recyclerview.widget.SnapHelper.2
            @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
            protected void onTargetFound(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                if (SnapHelper.this.a != null) {
                    SnapHelper snapHelper = SnapHelper.this;
                    int[] calculateDistanceToFinalSnap = snapHelper.calculateDistanceToFinalSnap(snapHelper.a.getLayoutManager(), view);
                    int i = calculateDistanceToFinalSnap[0];
                    int i2 = calculateDistanceToFinalSnap[1];
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                    if (calculateTimeForDeceleration > 0) {
                        action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / displayMetrics.densityDpi;
            }
        };
    }
}
