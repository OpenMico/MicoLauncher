package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    protected int mDividerSize;
    protected Ignore mIgnore;
    protected int mLeftOrTopPadding;
    protected int mOrientation;
    protected Paint mPaint;
    protected int mRightOrBottomPadding;

    public ListDividerItemDecoration(int i, int i2, int i3, int i4, int i5, Ignore ignore) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mDividerSize = i3;
            this.mLeftOrTopPadding = i4;
            this.mRightOrBottomPadding = i5;
            this.mIgnore = ignore;
            this.mPaint = new Paint(1);
            this.mPaint.setColor(i2);
            this.mPaint.setStyle(Paint.Style.FILL);
            return;
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        Ignore ignore = this.mIgnore;
        if (ignore != null && ignore.needIgnore(childAdapterPosition)) {
            super.getItemOffsets(rect, view, recyclerView, state);
        } else if (this.mOrientation == 0) {
            rect.set(0, 0, 0, this.mDividerSize);
        } else {
            rect.set(0, 0, this.mDividerSize, 0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            drawHorizontal(canvas, recyclerView);
        } else {
            drawVertical(canvas, recyclerView);
        }
    }

    protected void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft() + this.mLeftOrTopPadding;
        int width = (recyclerView.getWidth() - recyclerView.getPaddingRight()) - this.mRightOrBottomPadding;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
            Ignore ignore = this.mIgnore;
            if (ignore == null || !ignore.needIgnore(childAdapterPosition)) {
                int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                int i2 = this.mDividerSize + bottom;
                Paint paint = this.mPaint;
                if (paint != null) {
                    canvas.drawRect(paddingLeft, bottom, width, i2, paint);
                }
            }
        }
    }

    protected void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int paddingTop = recyclerView.getPaddingTop() + this.mLeftOrTopPadding;
        int height = (recyclerView.getHeight() - recyclerView.getPaddingBottom()) - this.mRightOrBottomPadding;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
            Ignore ignore = this.mIgnore;
            if (ignore == null || !ignore.needIgnore(childAdapterPosition)) {
                int right = childAt.getRight() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).rightMargin;
                int i2 = this.mDividerSize + right;
                Paint paint = this.mPaint;
                if (paint != null) {
                    canvas.drawRect(right, paddingTop, i2, height, paint);
                }
            }
        }
    }
}
