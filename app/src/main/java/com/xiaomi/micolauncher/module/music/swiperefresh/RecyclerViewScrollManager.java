package com.xiaomi.micolauncher.module.music.swiperefresh;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class RecyclerViewScrollManager {
    private ScrollManagerLocationJudgement b;
    private OnScrollLocationChangeListener c;
    private OnScrolledListener d;
    private final List<RecyclerView.OnScrollListener> a = new ArrayList();
    private boolean e = false;
    private boolean f = false;

    /* loaded from: classes3.dex */
    public interface OnScrollLocationChangeListener {
        int getEarlyToBottomItemCount();

        void onBottomWhenScrollDragging(RecyclerView recyclerView);

        void onBottomWhenScrollIdle(RecyclerView recyclerView);

        void onTopWhenScrollIdle(RecyclerView recyclerView);
    }

    /* loaded from: classes3.dex */
    public interface OnScrolledListener {
        void onScrolled(RecyclerView recyclerView, int i, int i2);
    }

    /* loaded from: classes3.dex */
    public interface ScrollManagerLocationJudgement {
        boolean isBottom(RecyclerView recyclerView, int i);

        boolean isTop(RecyclerView recyclerView);
    }

    public void setScrollManagerLocationJudgement(ScrollManagerLocationJudgement scrollManagerLocationJudgement) {
        this.b = scrollManagerLocationJudgement;
    }

    public void setOnScrollLocationChangeListener(OnScrollLocationChangeListener onScrollLocationChangeListener) {
        this.c = onScrollLocationChangeListener;
    }

    public void setOnScrolledListener(OnScrolledListener onScrolledListener) {
        this.d = onScrolledListener;
    }

    public boolean isScrolling() {
        return this.f;
    }

    private void a(RecyclerView recyclerView) {
        if (!this.e) {
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView2, int i) {
                    super.onScrollStateChanged(recyclerView2, i);
                    for (RecyclerView.OnScrollListener onScrollListener : RecyclerViewScrollManager.this.a) {
                        onScrollListener.onScrollStateChanged(recyclerView2, i);
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                    super.onScrolled(recyclerView2, i, i2);
                    for (RecyclerView.OnScrollListener onScrollListener : RecyclerViewScrollManager.this.a) {
                        onScrollListener.onScrolled(recyclerView2, i, i2);
                    }
                }
            });
        }
    }

    public void addScrollListener(RecyclerView recyclerView, RecyclerView.OnScrollListener onScrollListener) {
        if (onScrollListener != null) {
            this.a.add(onScrollListener);
            a(recyclerView);
        }
    }

    public void registerScrollListener(RecyclerView recyclerView) {
        addScrollListener(recyclerView, new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView2, int i) {
                if (i == 0) {
                    RecyclerViewScrollManager.this.f = false;
                    a(recyclerView2);
                    return;
                }
                RecyclerViewScrollManager.this.f = true;
                if (i == 1) {
                    b(recyclerView2);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                if (RecyclerViewScrollManager.this.d != null) {
                    RecyclerViewScrollManager.this.d.onScrolled(recyclerView2, i, i2);
                }
            }

            private void a(RecyclerView recyclerView2) {
                if (RecyclerViewScrollManager.this.b != null && RecyclerViewScrollManager.this.c != null) {
                    if (RecyclerViewScrollManager.this.b.isBottom(recyclerView2, RecyclerViewScrollManager.this.c.getEarlyToBottomItemCount())) {
                        RecyclerViewScrollManager.this.c.onBottomWhenScrollIdle(recyclerView2);
                    } else if (RecyclerViewScrollManager.this.b.isTop(recyclerView2)) {
                        RecyclerViewScrollManager.this.c.onTopWhenScrollIdle(recyclerView2);
                    }
                }
            }

            private void b(RecyclerView recyclerView2) {
                if (RecyclerViewScrollManager.this.b != null && RecyclerViewScrollManager.this.c != null && RecyclerViewScrollManager.this.b.isBottom(recyclerView2, RecyclerViewScrollManager.this.c.getEarlyToBottomItemCount())) {
                    RecyclerViewScrollManager.this.c.onBottomWhenScrollDragging(recyclerView2);
                }
            }
        });
    }
}
