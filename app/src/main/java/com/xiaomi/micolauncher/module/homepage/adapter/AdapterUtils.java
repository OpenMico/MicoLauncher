package com.xiaomi.micolauncher.module.homepage.adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class AdapterUtils {
    public static void onAttachedToRecyclerView(RecyclerView.Adapter adapter, RecyclerView recyclerView, final SpanSizeCallback spanSizeCallback) {
        if (adapter != null) {
            adapter.onAttachedToRecyclerView(recyclerView);
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.AdapterUtils.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    return SpanSizeCallback.this.getSpanSize(gridLayoutManager, spanSizeLookup, i);
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    private AdapterUtils() {
    }
}
