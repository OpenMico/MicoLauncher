package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudioBookContentViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookBannerViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookLatestViewHolder;

/* loaded from: classes3.dex */
public class AudiobookAdapterWrap extends BaseAdapter<BaseViewHolder> {
    public static final int BASE_ITEM_TYPE_FOOTER = 200000;
    public static final int BASE_ITEM_TYPE_HEADER = 100000;
    public static final int ITEM_TYPE_CONTENT = 300000;
    private SparseArrayCompat<BaseViewHolder> a = new SparseArrayCompat<>();
    private SparseArrayCompat<BaseViewHolder> b = new SparseArrayCompat<>();
    private OprationAudiobook c;
    private OprationAudiobook d;
    private BaseAdapter<BaseViewHolder> e;

    public AudiobookAdapterWrap(BaseAdapter<BaseViewHolder> baseAdapter) {
        this.e = baseAdapter;
    }

    public void setFirstRegionData(OprationAudiobook oprationAudiobook) {
        this.c = oprationAudiobook;
    }

    public void setSecondRegionData(OprationAudiobook oprationAudiobook) {
        this.d = oprationAudiobook;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter
    public void setScrolling(boolean z) {
        this.e.setScrolling(z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseViewHolder baseViewHolder = this.a.get(i);
        if (baseViewHolder != null) {
            baseViewHolder.initInMain();
            return baseViewHolder;
        }
        BaseViewHolder baseViewHolder2 = this.b.get(i);
        if (baseViewHolder2 == null) {
            return (BaseViewHolder) this.e.createViewHolder(viewGroup, i);
        }
        baseViewHolder2.initInMain();
        return baseViewHolder2;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (!this.isScrolling) {
            if (a(i)) {
                if (i == 0) {
                    ((AudiobookLatestViewHolder) baseViewHolder).bind(this.c);
                } else {
                    ((AudiobookBannerViewHolder) baseViewHolder).bind(this.d);
                }
            } else if (!b(i)) {
                this.e.onBindViewHolder(baseViewHolder, i - getHeadersCount());
            }
        }
    }

    public void onViewAttachedToWindow(@NonNull BaseViewHolder baseViewHolder) {
        super.onViewAttachedToWindow((AudiobookAdapterWrap) baseViewHolder);
        if (baseViewHolder instanceof AudioBookContentViewHolder) {
            this.e.onViewAttachedToWindow(baseViewHolder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + a();
    }

    public void clear() {
        this.a.clear();
        this.b.clear();
        ((AudiobookContentAdapter) this.e).clear();
        notifyDataSetChanged();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (a(i)) {
            return this.a.keyAt(i);
        }
        return b(i) ? this.b.keyAt((i - getHeadersCount()) - a()) : ITEM_TYPE_CONTENT;
    }

    public void onViewRecycled(@NonNull BaseViewHolder baseViewHolder) {
        super.onViewRecycled((AudiobookAdapterWrap) baseViewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        AdapterUtils.onAttachedToRecyclerView(this.e, recyclerView, new SpanSizeCallback() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$AudiobookAdapterWrap$5V2FT4wd_NKNFihtmy6OQJ2Ijrs
            @Override // com.xiaomi.micolauncher.module.homepage.adapter.SpanSizeCallback
            public final int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
                int a;
                a = AudiobookAdapterWrap.this.a(gridLayoutManager, spanSizeLookup, i);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int a(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
        int itemViewType = getItemViewType(i);
        if (this.a.get(itemViewType) != null) {
            return gridLayoutManager.getSpanCount();
        }
        if (this.b.get(itemViewType) != null) {
            return gridLayoutManager.getSpanCount();
        }
        if (spanSizeLookup != null) {
            return spanSizeLookup.getSpanSize(i);
        }
        return 1;
    }

    private boolean a(int i) {
        return i < getHeadersCount();
    }

    private boolean b(int i) {
        return i >= getHeadersCount() + a();
    }

    public void addHeaderHolder(BaseViewHolder baseViewHolder) {
        SparseArrayCompat<BaseViewHolder> sparseArrayCompat = this.a;
        sparseArrayCompat.put(sparseArrayCompat.size() + 100000, baseViewHolder);
    }

    public void addFooterHolder(BaseViewHolder baseViewHolder) {
        SparseArrayCompat<BaseViewHolder> sparseArrayCompat = this.b;
        sparseArrayCompat.put(sparseArrayCompat.size() + 200000, baseViewHolder);
    }

    public int getHeadersCount() {
        return this.a.size();
    }

    public int getFootersCount() {
        return this.b.size();
    }

    private int a() {
        return this.e.getItemCount();
    }
}
