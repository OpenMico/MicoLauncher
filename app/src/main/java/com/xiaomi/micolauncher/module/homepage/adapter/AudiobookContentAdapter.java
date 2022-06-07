package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.view.EntertainmentHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudioBookContentViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AudiobookContentAdapter extends BaseAdapter<BaseViewHolder> {
    static final /* synthetic */ boolean a = !AudiobookContentAdapter.class.desiredAssertionStatus();
    private List<AudiobookContent> b;
    private List<Station.Item> c;
    private List<Order.OrderInfo> d;
    private AudioBookContentViewHolder e;
    private TrackWidget f;
    private SparseArrayCompat<BaseViewHolder> g = new SparseArrayCompat<>();
    private List<String> h;
    private boolean i;

    public void setContents(List<AudiobookContent> list) {
        this.b = list;
    }

    public void setContents(List<AudiobookContent> list, TrackWidget trackWidget) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        this.b.clear();
        this.b.addAll(list);
        this.f = trackWidget;
    }

    public void clearContents() {
        if (ContainerUtil.hasData(this.b)) {
            this.b.clear();
            notifyDataSetChanged();
        }
    }

    public void setItems(List<Station.Item> list, TrackWidget trackWidget) {
        this.c = list;
        this.f = trackWidget;
    }

    public void setOrderInfos(List<Order.OrderInfo> list) {
        this.d = list;
    }

    public void setBlackList(List<String> list) {
        this.h = list;
    }

    public void setForMainPage(boolean z) {
        this.i = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 300000) {
            return getAudioBookContentViewHolder(viewGroup);
        }
        BaseViewHolder baseViewHolder = this.g.get(i);
        if (a || baseViewHolder != null) {
            baseViewHolder.initInMain();
            return baseViewHolder;
        }
        throw new AssertionError();
    }

    protected AudioBookContentViewHolder getAudioBookContentViewHolder(ViewGroup viewGroup) {
        return EntertainmentHolderCacheManager.getManager().fetchAudiobookContentViewHolder(this.i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i != getItemCount() - 1 || this.g.size() <= 0) {
            return AudiobookAdapterWrap.ITEM_TYPE_CONTENT;
        }
        return 1000;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (!this.isScrolling && !(baseViewHolder instanceof AudiobookRefreshViewHolder)) {
            this.e = (AudioBookContentViewHolder) baseViewHolder;
            this.e.setGroudResource(i);
            if (ContainerUtil.hasData(this.b)) {
                this.e.bind(this.b.get(i), this.f);
            } else if (ContainerUtil.hasData(this.c)) {
                this.e.setBlackList(this.h);
                this.e.bind(this.c.get(i), this.f);
            } else if (ContainerUtil.hasData(this.d)) {
                this.e.setBlackList(this.h);
                this.e.bind(this.d.get(i));
            }
        }
    }

    public void onViewAttachedToWindow(@NonNull BaseViewHolder baseViewHolder) {
        super.onViewAttachedToWindow((AudiobookContentAdapter) baseViewHolder);
        L.homepage.i("Audiobook onViewAttachedToWindow : %s", baseViewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = this.g.size();
        if (ContainerUtil.hasData(this.b)) {
            return this.b.size() + size;
        }
        if (ContainerUtil.hasData(this.d)) {
            return this.d.size() + size;
        }
        if (ContainerUtil.hasData(this.c)) {
            return this.c.size() + size;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.AudiobookContentAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    if (AudiobookContentAdapter.this.getItemViewType(i) == 1000) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (ContainerUtil.hasData(this.b)) {
            return Long.parseLong(this.b.get(i).getId());
        }
        if (ContainerUtil.hasData(this.d)) {
            return Long.parseLong(this.d.get(i).orderId);
        }
        if (ContainerUtil.hasData(this.c)) {
            return Long.parseLong(this.c.get(i).getId());
        }
        return super.getItemId(i);
    }

    public void clear() {
        if (ContainerUtil.hasData(this.b)) {
            this.b.clear();
        }
        if (ContainerUtil.hasData(this.d)) {
            this.d.clear();
        }
        if (ContainerUtil.hasData(this.c)) {
            this.c.clear();
        }
        this.g.clear();
    }

    public void addFooterHolder(BaseViewHolder baseViewHolder) {
        this.g.put(1000, baseViewHolder);
    }
}
