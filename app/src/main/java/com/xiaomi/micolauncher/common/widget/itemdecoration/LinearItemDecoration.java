package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public LinearItemDecoration(int i) {
        this.space = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.right = this.space;
    }
}
