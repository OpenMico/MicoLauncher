package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class ListPaddingItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private Ignore mIgnore;
    private int mLeftOrTopPadding;
    private int mOrientation;
    private int mRightOrBottomPadding;

    public ListPaddingItemDecoration(int i, int i2, int i3, Ignore ignore) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mLeftOrTopPadding = i2;
            this.mRightOrBottomPadding = i3;
            this.mIgnore = ignore;
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
            int i = this.mLeftOrTopPadding;
            int i2 = this.mRightOrBottomPadding;
            rect.set(i, i, i2, i2);
        } else {
            rect.set(this.mLeftOrTopPadding, 0, this.mRightOrBottomPadding, 0);
        }
    }
}
