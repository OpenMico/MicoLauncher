package com.xiaomi.micolauncher.common.adapter;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int a;
    private final boolean b;

    public GridSpacingItemDecoration(int i, boolean z) {
        this.a = i;
        this.b = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (gridLayoutManager != null) {
            int spanCount = gridLayoutManager.getSpanCount();
            int childLayoutPosition = recyclerView.getChildLayoutPosition(view);
            int i = childLayoutPosition % spanCount;
            if (this.b) {
                int i2 = this.a;
                rect.left = i2 - ((i * i2) / spanCount);
                rect.right = ((i + 1) * i2) / spanCount;
                if (childLayoutPosition < spanCount) {
                    rect.top = i2;
                }
                rect.bottom = this.a;
                return;
            }
            int i3 = this.a;
            rect.left = (i * i3) / spanCount;
            rect.right = i3 - (((i + 1) * i3) / spanCount);
            if (childLayoutPosition >= spanCount) {
                rect.top = i3;
            }
        }
    }
}
