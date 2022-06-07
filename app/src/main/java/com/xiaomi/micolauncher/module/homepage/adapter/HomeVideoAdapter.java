package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.accountsdk.account.data.ServerResultCode;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.utils.TraceUtil;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoCategoryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoRecommendAppViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHoldersCache;

/* loaded from: classes3.dex */
public class HomeVideoAdapter extends BaseVideoAdapter<Object> {
    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseVideoAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseViewHolder baseViewHolder;
        switch (i) {
            case 10004:
                baseViewHolder = VideoViewHoldersCache.getInstance().fetchRecommendAppHolder(viewGroup);
                break;
            case 10005:
                baseViewHolder = VideoViewHoldersCache.getInstance().fetchCategoryHolder(viewGroup);
                break;
            default:
                baseViewHolder = null;
                break;
        }
        return baseViewHolder == null ? super.onCreateViewHolder(viewGroup, i) : baseViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (!ContainerUtil.isEmpty(this.videoDataList)) {
            if (isFooterViewPosition(i)) {
                if (baseViewHolder instanceof VideoFooterViewHolder) {
                    ((VideoFooterViewHolder) baseViewHolder).start();
                }
            } else if (!this.isScrolling) {
                int itemViewType = getItemViewType(i);
                Object obj = this.videoDataList.get(i);
                if (itemViewType != 10000) {
                    switch (itemViewType) {
                        case 10004:
                            ((VideoRecommendAppViewHolder) baseViewHolder).bind((VideoTabInfo.RecommendAppCard) obj);
                            return;
                        case 10005:
                            ((VideoCategoryViewHolder) baseViewHolder).bind((VideoTabInfo.RecommendCategory) obj);
                            return;
                        default:
                            return;
                    }
                } else {
                    TraceUtil.beginSection("jiangliang onbindviewholder");
                    ((VideoViewHolder) baseViewHolder).bind(this.tabData.getType(), i, this.tabData.getTabKey(), (VideoData) obj, this.traceId, this.expId);
                    TraceUtil.endSection();
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        long j = i;
        if (ContainerUtil.isOutOfBound(j, this.videoDataList)) {
            return 0L;
        }
        Object obj = this.videoDataList.get(i);
        if (!(obj instanceof VideoData)) {
            return isFooterViewPosition(i) ? j : j;
        }
        String name = ((VideoData) obj).getName();
        return ContainerUtil.isEmpty(name) ? j : name.hashCode();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (ContainerUtil.isEmpty(this.videoDataList)) {
            return 10003;
        }
        if (isFooterViewPosition(i)) {
            return ServerResultCode.RESULT_SYSTEM_SERVER;
        }
        Object obj = this.videoDataList.get(i);
        if (obj instanceof VideoData) {
            return 10000;
        }
        if (obj instanceof VideoTabInfo.RecommendAppCard) {
            return 10004;
        }
        return obj instanceof VideoTabInfo.RecommendCategory ? 10005 : 10000;
    }
}
