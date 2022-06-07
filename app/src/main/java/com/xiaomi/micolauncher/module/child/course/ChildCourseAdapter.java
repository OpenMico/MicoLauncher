package com.xiaomi.micolauncher.module.child.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoCommonHolder;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildCourseAdapter extends BaseAdapter<BaseChildVideoHolder> {
    public static final int TYPE_BANNER = 0;
    private List<ChildVideo.BlocksBean> a = new ArrayList();
    private Context b;
    private int c;
    private int d;

    public ChildCourseAdapter(Context context) {
        this.b = context;
        ChildVideo.BlocksBean blocksBean = new ChildVideo.BlocksBean(ChildVideo.BLOCK_BANNER);
        ChildVideo.BlocksBean blocksBean2 = new ChildVideo.BlocksBean("block_grid_button");
        this.a.add(blocksBean);
        this.a.add(blocksBean2);
        this.c = UiUtils.getSize(context, R.dimen.child_course_small_card_width);
        this.d = UiUtils.getSize(context, R.dimen.child_course_small_card_height);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseChildVideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new CourseBannerHolder(this.b, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_banner, viewGroup, false));
        }
        ChildVideoCommonHolder childVideoCommonHolder = new ChildVideoCommonHolder(this.b, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_course_common, viewGroup, false), this.c, this.d, false);
        L.course.i("common holder");
        return childVideoCommonHolder;
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return Math.max(2, ContainerUtil.getSize(this.a));
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.a == null && i < 2) {
            return i;
        }
        if (ContainerUtil.isOutOfBound(i, this.a)) {
            return 1;
        }
        String name = this.a.get(i).getUi_type().getName();
        char c = 65535;
        int hashCode = name.hashCode();
        if (hashCode != 1228551577) {
            if (hashCode == 1933258553 && name.equals(ChildVideo.BLOCK_BANNER)) {
                c = 0;
            }
        } else if (name.equals("block_grid_button")) {
            c = 1;
        }
        return c != 0 ? 1 : 0;
    }

    public void setData(List<ChildVideo.BlocksBean> list) {
        if (ContainerUtil.hasData(list)) {
            this.a = list;
            notifyDataSetChanged();
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
