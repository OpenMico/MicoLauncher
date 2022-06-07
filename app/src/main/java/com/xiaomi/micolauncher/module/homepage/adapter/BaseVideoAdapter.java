package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHoldersCache;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseVideoAdapter<DataType> extends BaseAdapter<BaseViewHolder> {
    protected static final int ITEM_TYPE_CATEGORY = 10005;
    protected static final int ITEM_TYPE_EMPTY = 10003;
    protected static final int ITEM_TYPE_RECOMMEND_APP = 10004;
    protected static final int ITEM_TYPE_REFRESH = 10001;
    protected static final int ITEM_TYPE_VERTICAL = 10002;
    protected static final int ITEM_TYPE_VIDEO = 10000;
    protected int count;
    protected String expId;
    protected BaseViewHolder footHolder;
    protected VideoTabData tabData;
    protected String traceId;
    protected List<DataType> videoDataList;

    public void setDataInfo(VideoTabData videoTabData, String str, String str2) {
        this.tabData = videoTabData;
        this.traceId = str;
        this.expId = str2;
    }

    public void setVideoDataList(List<DataType> list) {
        this.videoDataList = list;
    }

    public List<DataType> getVideoDataList() {
        return this.videoDataList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Logger logger = L.homepage;
        int i2 = this.count + 1;
        this.count = i2;
        logger.v("VideoAdapter onCreateViewHolder : %d", Integer.valueOf(i2));
        if (i == 10001) {
            return this.footHolder;
        }
        if (i != ITEM_TYPE_EMPTY) {
            return VideoViewHoldersCache.getInstance().fetchVideoViewHolder(viewGroup);
        }
        return VideoViewHoldersCache.getInstance().fetchEmptyHistoryHolder(viewGroup);
    }

    public void onViewRecycled(@NonNull BaseViewHolder baseViewHolder) {
        super.onViewRecycled((BaseVideoAdapter<DataType>) baseViewHolder);
        baseViewHolder.recycle();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        AdapterUtils.onAttachedToRecyclerView(null, recyclerView, new SpanSizeCallback() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$BaseVideoAdapter$g9YZlc3BCDnnvMJkixqCdvNgAig
            @Override // com.xiaomi.micolauncher.module.homepage.adapter.SpanSizeCallback
            public final int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
                int a;
                a = BaseVideoAdapter.this.a(gridLayoutManager, spanSizeLookup, i);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int a(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
        int itemViewType = getItemViewType(i);
        if (10000 == itemViewType || ITEM_TYPE_CATEGORY == itemViewType) {
            return 1;
        }
        if (10001 == itemViewType || ITEM_TYPE_EMPTY == itemViewType || ITEM_TYPE_VERTICAL == itemViewType || ITEM_TYPE_RECOMMEND_APP == itemViewType) {
            return gridLayoutManager.getSpanCount();
        }
        if (spanSizeLookup != null) {
            return spanSizeLookup.getSpanSize(i);
        }
        return 1;
    }

    public void setFootViewHolder(BaseViewHolder baseViewHolder) {
        this.footHolder = baseViewHolder;
    }

    public void hideLoadingLayout() {
        BaseViewHolder baseViewHolder = this.footHolder;
        if (baseViewHolder != null) {
            ((VideoFooterViewHolder) baseViewHolder).stop();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.footHolder == null) {
            if (ContainerUtil.hasData(this.videoDataList)) {
                return this.videoDataList.size();
            }
            return 1;
        } else if (ContainerUtil.hasData(this.videoDataList)) {
            return 1 + this.videoDataList.size();
        } else {
            return 1;
        }
    }

    protected boolean isFooterViewPosition(int i) {
        return i >= this.videoDataList.size();
    }
}
