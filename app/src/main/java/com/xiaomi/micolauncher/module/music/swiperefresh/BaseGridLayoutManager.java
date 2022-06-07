package com.xiaomi.micolauncher.module.music.swiperefresh;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager;

/* loaded from: classes3.dex */
public class BaseGridLayoutManager extends GridLayoutManager implements RecyclerViewScrollManager.ScrollManagerLocationJudgement {
    private final RecyclerViewScrollManager i = new RecyclerViewScrollManager();

    public BaseGridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        setScrollManagerLocationJudgement(this);
    }

    public RecyclerViewScrollManager getRecyclerViewScrollManager() {
        return this.i;
    }

    public void setScrollManagerLocationJudgement(RecyclerViewScrollManager.ScrollManagerLocationJudgement scrollManagerLocationJudgement) {
        this.i.setScrollManagerLocationJudgement(scrollManagerLocationJudgement);
    }

    public void setOnScrollLocationChangeListener(RecyclerViewScrollManager.OnScrollLocationChangeListener onScrollLocationChangeListener) {
        this.i.setOnScrollLocationChangeListener(onScrollLocationChangeListener);
    }

    @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.ScrollManagerLocationJudgement
    public boolean isTop(RecyclerView recyclerView) {
        return findFirstVisibleItemPosition() == 0;
    }

    @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.ScrollManagerLocationJudgement
    public boolean isBottom(RecyclerView recyclerView, int i) {
        return findLastCompletelyVisibleItemPosition() >= (recyclerView.getAdapter().getItemCount() - 1) - i;
    }
}
