package com.xiaomi.micolauncher.module.homepage.adapter;

import androidx.annotation.NonNull;
import com.xiaomi.accountsdk.account.data.ServerResultCode;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.TraceUtil;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.EmptyHistoryHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoAdapter extends BaseVideoAdapter<VideoData> {
    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseVideoAdapter
    public void setVideoDataList(List<VideoData> list) {
        super.setVideoDataList(list);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        L.homepage.v("VideoAdapter onBindViewHolder %d", Integer.valueOf(this.count));
        if (baseViewHolder instanceof EmptyHistoryHolder) {
            ((EmptyHistoryHolder) baseViewHolder).update(this.tabData != null ? this.tabData.getTabKey() : "");
        } else if (!ContainerUtil.isEmpty(this.videoDataList)) {
            if (isFooterViewPosition(i)) {
                if (baseViewHolder instanceof VideoFooterViewHolder) {
                    ((VideoFooterViewHolder) baseViewHolder).start();
                }
            } else if (!this.isScrolling && getItemViewType(i) == 10000) {
                TraceUtil.beginSection("jiangliang onbindviewholder");
                ((VideoViewHolder) baseViewHolder).bind(this.tabData.getType(), i, this.tabData.getTabKey(), (VideoData) this.videoDataList.get(i), this.traceId, this.expId);
                TraceUtil.endSection();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        long j = i;
        if (ContainerUtil.isOutOfBound(j, this.videoDataList)) {
            return 0L;
        }
        String name = ((VideoData) this.videoDataList.get(i)).getName();
        return (!ContainerUtil.isEmpty(name) && !isFooterViewPosition(i)) ? name.hashCode() : j;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (ContainerUtil.isEmpty(this.videoDataList)) {
            return 10003;
        }
        if (isFooterViewPosition(i)) {
            return ServerResultCode.RESULT_SYSTEM_SERVER;
        }
        return 10000;
    }
}
