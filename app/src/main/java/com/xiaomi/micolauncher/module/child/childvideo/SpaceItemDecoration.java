package com.xiaomi.micolauncher.module.child.childvideo;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int a;
    private int b;

    public SpaceItemDecoration(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i = this.a;
        int i2 = childAdapterPosition % i;
        int i3 = this.b;
        rect.left = (i2 * i3) / i;
        rect.right = i3 - (((i2 + 1) * i3) / i);
        if (childAdapterPosition >= i) {
            rect.top = i3;
        }
    }
}
