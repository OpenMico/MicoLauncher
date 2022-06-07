package com.xiaomi.micolauncher.module.child.childvideo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoAgeHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoBannerHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoCommonHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoTypeHolder;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoAdapter extends BaseAdapter<BaseChildVideoHolder> {
    public static final int TYPE_BANNER = 0;
    private boolean d;
    private boolean e;
    private Context f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private ChildVideo.BlocksBean b = new ChildVideo.BlocksBean();
    private ChildVideo.BlocksBean c = new ChildVideo.BlocksBean();
    private List<ChildVideo.BlocksBean> a = new ArrayList();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        onBindViewHolder((BaseChildVideoHolder) viewHolder, i, (List<Object>) list);
    }

    public ChildVideoAdapter(Context context, boolean z) {
        this.f = context;
        ChildVideo.BlocksBean.UiTypeBeanX uiTypeBeanX = new ChildVideo.BlocksBean.UiTypeBeanX();
        uiTypeBeanX.setName(ChildVideo.BLOCK_AGE);
        this.b.setUi_type(uiTypeBeanX);
        ChildVideo.BlocksBean.UiTypeBeanX uiTypeBeanX2 = new ChildVideo.BlocksBean.UiTypeBeanX();
        uiTypeBeanX2.setName(ChildVideo.BLOCK_TYPE);
        this.c.setUi_type(uiTypeBeanX2);
        ChildVideo.BlocksBean blocksBean = new ChildVideo.BlocksBean(ChildVideo.BLOCK_BANNER);
        ChildVideo.BlocksBean blocksBean2 = new ChildVideo.BlocksBean("block_grid_button");
        this.a.add(blocksBean);
        this.a.add(blocksBean2);
        this.a.add(this.b);
        this.a.add(this.c);
        this.g = UiUtils.getSize(context, R.dimen.child_video_small_card_width);
        this.h = UiUtils.getSize(context, R.dimen.child_video_small_card_height);
        this.i = z ? R.layout.item_child_video_header_from_home : R.layout.item_child_video_header;
        this.k = z ? R.layout.item_child_video_age_from_home : R.layout.item_child_video_age;
        this.l = z ? R.layout.item_child_video_type_from_home : R.layout.item_child_video_type;
        this.j = z ? R.layout.item_child_video_common_from_home : R.layout.item_child_video_common;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseChildVideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new ChildVideoBannerHolder(this.f, LayoutInflater.from(viewGroup.getContext()).inflate(this.i, viewGroup, false));
        }
        switch (i) {
            case 2:
                return new ChildVideoAgeHolder(this.f, LayoutInflater.from(viewGroup.getContext()).inflate(this.k, viewGroup, false));
            case 3:
                return new ChildVideoTypeHolder(this.f, LayoutInflater.from(viewGroup.getContext()).inflate(this.l, viewGroup, false));
            default:
                ChildVideoCommonHolder childVideoCommonHolder = new ChildVideoCommonHolder(this.f, LayoutInflater.from(viewGroup.getContext()).inflate(this.j, viewGroup, false), this.g, this.h, false);
                L.childContent.i("common holder");
                return childVideoCommonHolder;
        }
    }

    public void onBindViewHolder(@NonNull BaseChildVideoHolder baseChildVideoHolder, int i) {
        List<ChildVideo.BlocksBean> list = this.a;
        if (list != null && !ContainerUtil.isOutOfBound(i, list)) {
            if (baseChildVideoHolder instanceof ChildVideoCommonHolder) {
                ((ChildVideoCommonHolder) baseChildVideoHolder).setBackground(i);
            }
            baseChildVideoHolder.setBlocksBean(this.a.get(i));
        }
    }

    public void onBindViewHolder(@NonNull BaseChildVideoHolder baseChildVideoHolder, int i, @NonNull List<Object> list) {
        if (ContainerUtil.isEmpty(list)) {
            onBindViewHolder(baseChildVideoHolder, i);
        } else if (baseChildVideoHolder instanceof ChildVideoBannerHolder) {
            Bundle bundle = (Bundle) list.get(0);
            ChildVideoBannerHolder childVideoBannerHolder = (ChildVideoBannerHolder) baseChildVideoHolder;
            childVideoBannerHolder.bindVideoHistoryInner(bundle.getString("cover_payloads"));
            if (bundle.get(ChildVideoBannerHolder.VIP_TITLE_PAYLOADS) != null) {
                childVideoBannerHolder.bindVipTitle((ChildVideo.ItemsBean) bundle.get(ChildVideoBannerHolder.VIP_TITLE_PAYLOADS));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return Math.max(4, ContainerUtil.getSize(this.a));
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.a == null && i < 4) {
            return i;
        }
        List<ChildVideo.BlocksBean> list = this.a;
        if (list == null || ContainerUtil.isOutOfBound(i, list)) {
            return 0;
        }
        String name = this.a.get(i).getUi_type().getName();
        char c = 65535;
        int hashCode = name.hashCode();
        if (hashCode != 872767053) {
            if (hashCode != 1228551577) {
                if (hashCode != 1286558636) {
                    if (hashCode == 1933258553 && name.equals(ChildVideo.BLOCK_BANNER)) {
                        c = 0;
                    }
                } else if (name.equals(ChildVideo.BLOCK_TYPE)) {
                    c = 2;
                }
            } else if (name.equals("block_grid_button")) {
                c = 3;
            }
        } else if (name.equals(ChildVideo.BLOCK_AGE)) {
            c = 1;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 1;
        }
    }

    public void setData(List<ChildVideo.BlocksBean> list) {
        if (ContainerUtil.hasData(list)) {
            this.a.clear();
            notifyDataSetChanged();
            this.a.addAll(list);
            a();
            notifyDataSetChanged();
        }
    }

    public void setHasAddAgeBlock(boolean z) {
        this.d = z;
    }

    public void setHasAddTypeBlock(boolean z) {
        this.e = z;
    }

    private void a() {
        if (!this.d && !ContainerUtil.isOutOfBound(3L, this.a)) {
            this.a.add(3, this.b);
            this.d = true;
        }
        if (!this.e && !ContainerUtil.isOutOfBound(6L, this.a)) {
            this.a.add(6, this.c);
            this.e = true;
        }
    }

    public void onViewDetachedFromWindow(@NonNull BaseChildVideoHolder baseChildVideoHolder) {
        this.holders.remove(baseChildVideoHolder);
    }

    public void onViewAttachedToWindow(@NonNull BaseChildVideoHolder baseChildVideoHolder) {
        this.holders.add(baseChildVideoHolder);
    }

    public void removeMessages() {
        for (BaseChildVideoHolder baseChildVideoHolder : this.holders) {
            baseChildVideoHolder.removeAllMessages();
        }
    }

    public void startMessages() {
        for (BaseChildVideoHolder baseChildVideoHolder : this.holders) {
            baseChildVideoHolder.startAllMessages();
        }
    }
}
