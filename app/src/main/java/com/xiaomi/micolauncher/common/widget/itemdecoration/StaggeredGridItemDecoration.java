package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* loaded from: classes3.dex */
public class StaggeredGridItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public StaggeredGridItemDecoration(int i) {
        this.space = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        try {
            if (((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex() % 2 != 0) {
                rect.top = this.space / 2;
            }
            rect.left = this.space;
        } catch (Exception unused) {
        }
    }
}
