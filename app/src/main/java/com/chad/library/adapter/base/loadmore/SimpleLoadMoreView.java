package com.chad.library.adapter.base.loadmore;

import com.chad.library.R;

/* loaded from: classes.dex */
public final class SimpleLoadMoreView extends LoadMoreView {
    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    public int getLayoutId() {
        return R.layout.brvah_quick_view_load_more;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override // com.chad.library.adapter.base.loadmore.LoadMoreView
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
