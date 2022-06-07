package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class GridPaddingItemDecoration extends RecyclerView.ItemDecoration {
    private int mHorizontalPadding;
    private boolean mNeedLeftSpacing = false;
    private int mVerticalPadding;

    private GridPaddingItemDecoration(int i, int i2) {
        this.mHorizontalPadding = i;
        this.mVerticalPadding = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (!(recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            super.getItemOffsets(rect, view, recyclerView, state);
            return;
        }
        int spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        int width = (recyclerView.getWidth() / spanCount) - ((int) ((recyclerView.getWidth() - (this.mHorizontalPadding * (spanCount - 1))) / spanCount));
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition % spanCount == 0) {
            rect.left = 0;
            rect.right = width;
            this.mNeedLeftSpacing = true;
        } else if ((childAdapterPosition + 1) % spanCount == 0) {
            this.mNeedLeftSpacing = false;
            rect.right = 0;
            rect.left = width;
        } else if (this.mNeedLeftSpacing) {
            this.mNeedLeftSpacing = false;
            int i = this.mHorizontalPadding;
            rect.left = i - width;
            if ((childAdapterPosition + 2) % spanCount == 0) {
                rect.right = i - width;
            } else {
                rect.right = i / 2;
            }
        } else if ((childAdapterPosition + 2) % spanCount == 0) {
            this.mNeedLeftSpacing = false;
            int i2 = this.mHorizontalPadding;
            rect.left = i2 / 2;
            rect.right = i2 - width;
        } else {
            this.mNeedLeftSpacing = false;
            int i3 = this.mHorizontalPadding;
            rect.left = i3 / 2;
            rect.right = i3 / 2;
        }
        rect.top = 0;
        rect.bottom = this.mVerticalPadding;
    }
}
