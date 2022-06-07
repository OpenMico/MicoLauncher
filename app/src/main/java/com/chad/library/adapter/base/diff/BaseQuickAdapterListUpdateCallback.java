package com.chad.library.adapter.base.diff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;

/* loaded from: classes.dex */
public final class BaseQuickAdapterListUpdateCallback implements ListUpdateCallback {
    @NonNull
    private final BaseQuickAdapter mAdapter;

    public BaseQuickAdapterListUpdateCallback(@NonNull BaseQuickAdapter baseQuickAdapter) {
        this.mAdapter = baseQuickAdapter;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i, int i2) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeInserted(i + baseQuickAdapter.getHeaderLayoutCount(), i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeRemoved(i + baseQuickAdapter.getHeaderLayoutCount(), i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i, int i2) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemMoved(i + baseQuickAdapter.getHeaderLayoutCount(), i2 + this.mAdapter.getHeaderLayoutCount());
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i, int i2, @Nullable Object obj) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeChanged(i + baseQuickAdapter.getHeaderLayoutCount(), i2, obj);
    }
}
