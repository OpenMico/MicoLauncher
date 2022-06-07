package com.dinuscxj.itemdecoration;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* loaded from: classes.dex */
public class GridOffsetsItemDecoration extends RecyclerView.ItemDecoration {
    public static final int GRID_OFFSETS_HORIZONTAL = 0;
    public static final int GRID_OFFSETS_VERTICAL = 1;
    private final SparseArray<OffsetsCreator> a = new SparseArray<>();
    private int b;
    private int c;
    private int d;

    /* loaded from: classes.dex */
    public interface OffsetsCreator {
        int createHorizontal(RecyclerView recyclerView, int i);

        int createVertical(RecyclerView recyclerView, int i);
    }

    public GridOffsetsItemDecoration(int i) {
        setOrientation(i);
    }

    public void setOrientation(int i) {
        this.b = i;
    }

    public void setVerticalItemOffsets(int i) {
        this.c = i;
    }

    public void setHorizontalItemOffsets(int i) {
        this.d = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int a = a(recyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        rect.set(0, 0, a(recyclerView, view), b(recyclerView, view));
        if (b(childAdapterPosition, a, itemCount)) {
            rect.bottom = 0;
        }
        if (a(childAdapterPosition, a, itemCount)) {
            rect.right = 0;
        }
    }

    private int a(RecyclerView recyclerView, View view) {
        if (this.a.size() == 0) {
            return this.d;
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        OffsetsCreator offsetsCreator = this.a.get(recyclerView.getAdapter().getItemViewType(childAdapterPosition));
        if (offsetsCreator != null) {
            return offsetsCreator.createHorizontal(recyclerView, childAdapterPosition);
        }
        return 0;
    }

    private int b(RecyclerView recyclerView, View view) {
        if (this.a.size() == 0) {
            return this.c;
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        OffsetsCreator offsetsCreator = this.a.get(recyclerView.getAdapter().getItemViewType(childAdapterPosition));
        if (offsetsCreator != null) {
            return offsetsCreator.createVertical(recyclerView, childAdapterPosition);
        }
        return 0;
    }

    private boolean a(int i, int i2, int i3) {
        if (this.b == 1) {
            return (i + 1) % i2 == 0;
        }
        int i4 = i3 % i2;
        if (i4 != 0) {
            i2 = i4;
        }
        return i >= i3 - i2;
    }

    private boolean b(int i, int i2, int i3) {
        if (this.b != 1) {
            return (i + 1) % i2 == 0;
        }
        int i4 = i3 % i2;
        if (i4 != 0) {
            i2 = i4;
        }
        return i >= i3 - i2;
    }

    private int a(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        throw new UnsupportedOperationException("the GridDividerItemDecoration can only be used in the RecyclerView which use a GridLayoutManager or StaggeredGridLayoutManager");
    }

    public void registerTypeDrawable(int i, OffsetsCreator offsetsCreator) {
        this.a.put(i, offsetsCreator);
    }
}
