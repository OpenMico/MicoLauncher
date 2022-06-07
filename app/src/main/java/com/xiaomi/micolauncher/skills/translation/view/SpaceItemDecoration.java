package com.xiaomi.micolauncher.skills.translation.view;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.L;

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
        L.homepage.i("child count is : %d", Integer.valueOf(this.b));
        if (this.b == 2) {
            if (childAdapterPosition == 0) {
                int i = this.a;
                rect.left = i * 2;
                rect.right = i;
                return;
            }
            rect.right = this.a;
        } else if (childAdapterPosition == 0) {
            int i2 = this.a;
            rect.left = i2;
            rect.right = i2;
        } else {
            rect.right = this.a;
        }
    }
}
