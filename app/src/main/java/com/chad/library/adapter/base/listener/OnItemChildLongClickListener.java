package com.chad.library.adapter.base.listener;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;

/* loaded from: classes.dex */
public abstract class OnItemChildLongClickListener extends SimpleClickListener {
    @Override // com.chad.library.adapter.base.listener.SimpleClickListener
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    @Override // com.chad.library.adapter.base.listener.SimpleClickListener
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    @Override // com.chad.library.adapter.base.listener.SimpleClickListener
    public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    public abstract void onSimpleItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i);

    @Override // com.chad.library.adapter.base.listener.SimpleClickListener
    public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        onSimpleItemChildLongClick(baseQuickAdapter, view, i);
    }
}
