package com.xiaomi.smarthome.ui.widgets;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int a;

    public GridSpacingItemDecoration(int i) {
        this.a = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (gridLayoutManager != null) {
            int itemCount = gridLayoutManager.getItemCount();
            boolean z = (itemCount & 1) != 1;
            int spanCount = gridLayoutManager.getSpanCount();
            int childLayoutPosition = recyclerView.getChildLayoutPosition(view);
            int i = childLayoutPosition % spanCount;
            if (z && childLayoutPosition < itemCount - 2) {
                rect.right = this.a;
            }
            if (!z && childLayoutPosition < itemCount - 1) {
                rect.right = this.a;
            }
            if (i != 0) {
                rect.top = this.a;
            }
        }
    }
}
