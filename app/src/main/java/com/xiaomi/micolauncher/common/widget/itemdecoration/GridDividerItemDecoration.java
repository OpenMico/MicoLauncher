package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* loaded from: classes3.dex */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerSize;
    private Paint mPaint = new Paint(1);

    public GridDividerItemDecoration(int i, int i2) {
        this.mDividerSize = i2;
        this.mPaint.setColor(i);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (isLastColumn(recyclerView, recyclerView.getChildAdapterPosition(view), getSpanCount(recyclerView), recyclerView.getAdapter().getItemCount())) {
            rect.set(0, 0, 0, this.mDividerSize);
            return;
        }
        int i = this.mDividerSize;
        rect.set(0, 0, i, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHorizontal(canvas, recyclerView);
        drawVertical(canvas, recyclerView);
    }

    private int getSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return -1;
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getLeft() - layoutParams.leftMargin;
            int right = childAt.getRight() + layoutParams.rightMargin + this.mDividerSize;
            int bottom = childAt.getBottom() + layoutParams.bottomMargin;
            int i2 = this.mDividerSize + bottom;
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(left, bottom, right, i2, paint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int top = childAt.getTop() - layoutParams.topMargin;
            int bottom = childAt.getBottom() + layoutParams.bottomMargin;
            int right = childAt.getRight() + layoutParams.rightMargin;
            int i2 = this.mDividerSize + right;
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(right, top, i2, bottom, paint);
            }
        }
    }

    private boolean isLastRaw(RecyclerView recyclerView, int i, int i2, int i3) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return i >= i3 - (i3 % i2);
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? i >= i3 - (i3 % i2) : (i + 1) % i2 == 0;
        }
        return false;
    }

    private boolean isLastColumn(RecyclerView recyclerView, int i, int i2, int i3) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (i + 1) % i2 == 0;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? (i + 1) % i2 == 0 : i >= i3 - (i3 % i2);
        }
        return false;
    }
}
