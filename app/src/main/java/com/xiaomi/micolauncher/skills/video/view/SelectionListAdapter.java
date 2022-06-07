package com.xiaomi.micolauncher.skills.video.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.ItemSelectionListBinding;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.List;

/* loaded from: classes3.dex */
public class SelectionListAdapter extends RecyclerView.Adapter<a> {
    private final List<VideoItem> a;
    private OnItemClickListener b;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(int i, VideoItem videoItem);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull a aVar, int i, @NonNull List list) {
        onBindViewHolder2(aVar, i, (List<Object>) list);
    }

    public SelectionListAdapter(@NonNull List<VideoItem> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(ItemSelectionListBinding.inflate(LayoutInflater.from(viewGroup.getContext())));
    }

    /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(@NonNull a aVar, int i, @NonNull List<Object> list) {
        super.onBindViewHolder((SelectionListAdapter) aVar, i, list);
    }

    public void onBindViewHolder(@NonNull final a aVar, int i) {
        final VideoItem a2 = a(i);
        if (a2 != null) {
            aVar.a.setItem(a2);
            aVar.a.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$SelectionListAdapter$nce35O5VlNAaVsxjmJpn0sY54XE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SelectionListAdapter.this.a(aVar, a2, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(@NonNull a aVar, VideoItem videoItem, View view) {
        if (this.b != null && !UiUtils.isFastClick()) {
            this.b.onItemClick(aVar.getLayoutPosition(), videoItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.b = onItemClickListener;
    }

    private VideoItem a(int i) {
        if (ContainerUtil.hasData(this.a)) {
            return this.a.get(i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        private final ItemSelectionListBinding a;

        public a(@NonNull ItemSelectionListBinding itemSelectionListBinding) {
            super(itemSelectionListBinding.getRoot());
            this.a = itemSelectionListBinding;
        }
    }

    @BindingAdapter({"imgDrawable"})
    public static void loadImage(ImageView imageView, Drawable drawable) {
        GlideUtils.bindImageView(imageView, drawable);
    }
}
