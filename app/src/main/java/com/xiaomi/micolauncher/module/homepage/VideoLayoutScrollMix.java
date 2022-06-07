package com.xiaomi.micolauncher.module.homepage;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.GlideUtils;

/* loaded from: classes3.dex */
public class VideoLayoutScrollMix {
    private int a;
    private final GridLayoutManager b;
    private RecyclerView.OnScrollListener c;
    private Context d;
    private NextPageListener e;

    /* loaded from: classes3.dex */
    public interface NextPageListener {
        void autoPaging();
    }

    public VideoLayoutScrollMix(Context context, NextPageListener nextPageListener) {
        this(context, nextPageListener, false);
    }

    public VideoLayoutScrollMix(Context context, NextPageListener nextPageListener, final boolean z) {
        this.a = 2560;
        this.d = context;
        this.e = nextPageListener;
        this.b = new GridLayoutManager(context, 2, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                if (VideoLayoutScrollMix.this.a != 0) {
                    return 2560;
                }
                return super.getExtraLayoutSpace(state);
            }
        };
        this.b.setItemPrefetchEnabled(true);
        if (this.e != null) {
            this.c = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix.2
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                    if (!z) {
                        return;
                    }
                    if (i == 0 || i == 1) {
                        GlideUtils.resumeRequests(VideoLayoutScrollMix.this.d);
                    } else {
                        GlideUtils.pauseRequests(VideoLayoutScrollMix.this.d);
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                    super.onScrolled(recyclerView, i, i2);
                    VideoLayoutScrollMix.this.a = 0;
                    if (!recyclerView.canScrollHorizontally(-1)) {
                        recyclerView.stopScroll();
                    }
                    if (!recyclerView.canScrollHorizontally(1)) {
                        recyclerView.stopScroll();
                        if (VideoLayoutScrollMix.this.e != null) {
                            VideoLayoutScrollMix.this.e.autoPaging();
                        }
                    }
                }
            };
        }
    }

    public void resetExtraSpace() {
        this.a = 2560;
    }

    public void resetExtraSpaceToDefault() {
        this.a = 0;
    }

    public GridLayoutManager getLayoutManager() {
        return this.b;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return this.c;
    }

    public void release() {
        this.d = null;
        this.e = null;
    }
}
