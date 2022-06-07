package com.xiaomi.micolauncher.module.child.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.module.homepage.view.ChildVideoCardView;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class CourseFilterContentAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private List<ChildVideo.ItemsBean> a;
    private Context b;

    public CourseFilterContentAdapter(Context context) {
        this.b = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(LayoutInflater.from(this.b).inflate(R.layout.course_view_square, viewGroup, false));
        baseRecyclerViewHolder.setViewHolderType(i);
        return baseRecyclerViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        List<ChildVideo.ItemsBean> list = this.a;
        if (list != null && !ContainerUtil.isOutOfBound(i, list)) {
            ((ChildVideoCardView) baseRecyclerViewHolder.itemView.findViewById(R.id.card_view)).updateTitleAndImage(this.a.get(i), R.drawable.child_course_img_holder_with_bg_color_4342ff);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }

    public void setData(List<ChildVideo.ItemsBean> list) {
        this.a = list;
        notifyDataSetChanged();
    }
}
