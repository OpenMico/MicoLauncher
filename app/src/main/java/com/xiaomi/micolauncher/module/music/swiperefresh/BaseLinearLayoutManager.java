package com.xiaomi.micolauncher.module.music.swiperefresh;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager;

/* loaded from: classes3.dex */
public class BaseLinearLayoutManager extends LinearLayoutManager implements RecyclerViewScrollManager.ScrollManagerLocationJudgement {
    private RecyclerViewScrollManager a = new RecyclerViewScrollManager();

    public BaseLinearLayoutManager(Context context) {
        super(context);
        setScrollManagerLocationJudgement(this);
    }

    public BaseLinearLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
        setScrollManagerLocationJudgement(this);
    }

    public void setScrollManagerLocationJudgement(RecyclerViewScrollManager.ScrollManagerLocationJudgement scrollManagerLocationJudgement) {
        this.a.setScrollManagerLocationJudgement(scrollManagerLocationJudgement);
    }

    public void setOnScrollLocationChangeListener(RecyclerViewScrollManager.OnScrollLocationChangeListener onScrollLocationChangeListener) {
        this.a.setOnScrollLocationChangeListener(onScrollLocationChangeListener);
    }

    public void setOnScrolledListener(RecyclerViewScrollManager.OnScrolledListener onScrolledListener) {
        this.a.setOnScrolledListener(onScrolledListener);
    }

    public RecyclerViewScrollManager getRecyclerViewScrollManager() {
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.ScrollManagerLocationJudgement
    public boolean isTop(RecyclerView recyclerView) {
        return findFirstVisibleItemPosition() == 0;
    }

    @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.ScrollManagerLocationJudgement
    public boolean isBottom(RecyclerView recyclerView, int i) {
        return findLastVisibleItemPosition() >= (recyclerView.getAdapter().getItemCount() - 1) - i;
    }
}
