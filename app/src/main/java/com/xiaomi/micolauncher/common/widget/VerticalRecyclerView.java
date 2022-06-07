package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/* loaded from: classes3.dex */
public class VerticalRecyclerView extends RecyclerView {
    public VerticalRecyclerView(Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false) { // from class: com.xiaomi.micolauncher.common.widget.VerticalRecyclerView.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return true;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                return 1280;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("onLayoutChildren exception : %s", e.toString());
                }
            }
        };
        linearLayoutManager.setAutoMeasureEnabled(false);
        linearLayoutManager.setInitialPrefetchItemCount(2);
        setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        setOverScrollMode(2);
    }
}
