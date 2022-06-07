package com.xiaomi.micolauncher.module.audiobooks.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.databinding.ItemBlockTitleBinding;
import com.xiaomi.micolauncher.databinding.ItemCardBinding;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import java.util.List;

/* loaded from: classes3.dex */
public class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> a;
    private LayoutInflater b;
    private int c;

    public FlowAdapter(@NonNull List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return a(i).isBlockTitle() ? 2 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (this.b == null) {
            this.b = LayoutInflater.from(viewGroup.getContext());
        }
        if (i == 1) {
            return new a(ItemCardBinding.inflate(this.b, viewGroup, false));
        }
        return new b(ItemBlockTitleBinding.inflate(this.b, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final AudioDiscoveryPage.Flow.BlocksBean.ItemsBean a2 = a(i);
        if (a2 != null) {
            final Context context = viewHolder.itemView.getContext();
            if (viewHolder instanceof a) {
                a aVar = (a) viewHolder;
                String url = a2.getImages().getPoster().getUrl();
                if (this.c == 0) {
                    this.c = context.getResources().getDimensionPixelSize(R.dimen.dp_16);
                }
                GlideUtils.bindImageViewWithRoundUseContext(context, url, aVar.a.ivCover, this.c, R.drawable.img_book_loading);
                aVar.a.tvDesc.setText(a2.getTitle());
                aVar.a.ivBadge.setVisibility(!a2.isFree() ? 0 : 8);
                aVar.a.ivCover.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.audiobooks.adapter.-$$Lambda$FlowAdapter$iDaDjVd3f10epoxIJneW2eYLCP8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FlowAdapter.a(AudioDiscoveryPage.Flow.BlocksBean.ItemsBean.this, context, view);
                    }
                });
                return;
            }
            ((b) viewHolder).a.tvTitle.setText(a2.getBlockTitle());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AudioDiscoveryPage.Flow.BlocksBean.ItemsBean itemsBean, Context context, View view) {
        if (!TextUtils.isEmpty(itemsBean.getTarget())) {
            SchemaManager.handleSchema(context, itemsBean.getTarget());
        } else {
            PlayerApi.playStation(context, itemsBean.getStationId(), itemsBean.getOrigin(), itemsBean.getTypeId());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    private AudioDiscoveryPage.Flow.BlocksBean.ItemsBean a(int i) {
        return this.a.get(i);
    }

    /* loaded from: classes3.dex */
    static class a extends RecyclerView.ViewHolder {
        ItemCardBinding a;

        a(@NonNull ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.a = itemCardBinding;
        }
    }

    /* loaded from: classes3.dex */
    static class b extends RecyclerView.ViewHolder {
        ItemBlockTitleBinding a;

        b(@NonNull ItemBlockTitleBinding itemBlockTitleBinding) {
            super(itemBlockTitleBinding.getRoot());
            this.a = itemBlockTitleBinding;
        }
    }
}
