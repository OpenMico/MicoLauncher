package com.xiaomi.micolauncher.module.multicp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.ItemPlaySourceBinding;
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class ContentProviderAdapter extends RecyclerView.Adapter<a> {
    private final List<VideoContentProvider> a;
    private ContentProviderBar.OnCpClickedListener b;

    public ContentProviderAdapter(@NonNull List<VideoContentProvider> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(ItemPlaySourceBinding.inflate(LayoutInflater.from(viewGroup.getContext())));
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        final VideoContentProvider a2 = a(i);
        if (a2 != null) {
            aVar.a.setCp(a2);
            aVar.a.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.multicp.adapter.-$$Lambda$ContentProviderAdapter$oYuyt-Wnu479rdo5rUQJ0ws5Swk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ContentProviderAdapter.this.a(a2, view);
                }
            });
            return;
        }
        L.multicp.e("PlaySourceAdapter onBindViewHolder item is null , pos is %s", Integer.valueOf(i));
    }

    public /* synthetic */ void a(VideoContentProvider videoContentProvider, View view) {
        L.multicp.d("ContentProviderAdapter setOnClickListener videoItem is %s", Gsons.getGson().toJson(videoContentProvider));
        if (this.b != null && !UiUtils.isFastClick() && !videoContentProvider.isSelected) {
            a(videoContentProvider);
            this.b.onCpChanged(videoContentProvider);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (ContainerUtil.hasData(this.a)) {
            return this.a.size();
        }
        return 0;
    }

    private void a(VideoContentProvider videoContentProvider) {
        Iterator<VideoContentProvider> it = this.a.iterator();
        while (it.hasNext()) {
            VideoContentProvider next = it.next();
            next.isSelected = next == videoContentProvider;
        }
        notifyDataSetChanged();
    }

    private VideoContentProvider a(int i) {
        if (!ContainerUtil.hasData(this.a) || i > this.a.size() - 1) {
            return null;
        }
        return this.a.get(i);
    }

    public void setOnCpClickedListener(ContentProviderBar.OnCpClickedListener onCpClickedListener) {
        this.b = onCpClickedListener;
    }

    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        ItemPlaySourceBinding a;

        public a(@NonNull ItemPlaySourceBinding itemPlaySourceBinding) {
            super(itemPlaySourceBinding.getRoot());
            this.a = itemPlaySourceBinding;
        }
    }

    @BindingAdapter({"resId"})
    public static void loadImage(ImageView imageView, int i) {
        if (i != 0) {
            Glide.with(imageView).load(Integer.valueOf(i)).into(imageView);
        }
    }
}
