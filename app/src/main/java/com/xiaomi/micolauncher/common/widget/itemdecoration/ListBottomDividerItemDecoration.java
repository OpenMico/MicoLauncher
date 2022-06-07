package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Canvas;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class ListBottomDividerItemDecoration extends ListDividerItemDecoration {
    public ListBottomDividerItemDecoration(int i, int i2, int i3, int i4, int i5, Ignore ignore) {
        super(i, i2, i3, i4, i5, ignore);
    }

    @Override // com.xiaomi.micolauncher.common.widget.itemdecoration.ListDividerItemDecoration
    protected void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft() + this.mLeftOrTopPadding;
        int width = (recyclerView.getWidth() - recyclerView.getPaddingRight()) - this.mRightOrBottomPadding;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View childAt = recyclerView.getChildAt(i);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
            if (this.mIgnore == null || !this.mIgnore.needIgnore(childAdapterPosition)) {
                int bottom = childAt.getBottom() + ((RecyclerView.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                int i2 = this.mDividerSize + bottom;
                if (this.mPaint != null) {
                    canvas.drawRect(paddingLeft, bottom, width, i2, this.mPaint);
                }
            }
        }
    }
}
