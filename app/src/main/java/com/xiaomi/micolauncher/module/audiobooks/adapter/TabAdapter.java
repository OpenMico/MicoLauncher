package com.xiaomi.micolauncher.module.audiobooks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.ItemAudiobookTabBinding;
import java.util.List;

/* loaded from: classes3.dex */
public class TabAdapter extends RecyclerView.Adapter<a> {
    private List<AudioDiscoveryPage.TabListBean.TabListItemBean> a;
    private OnItemClickListener b;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public TabAdapter(@NonNull List<AudioDiscoveryPage.TabListBean.TabListItemBean> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(ItemAudiobookTabBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final a aVar, int i) {
        AudioDiscoveryPage.TabListBean.TabListItemBean tabListItemBean = this.a.get(i);
        if (tabListItemBean != null) {
            aVar.a.tvTabItem.setText(tabListItemBean.getDesc());
            aVar.a.tvTabItem.setTextColor(ContextCompat.getColor(aVar.a.getRoot().getContext(), tabListItemBean.isSelected ? R.color.dark_system_primary : R.color.dark_system_secondary));
            if (this.b != null) {
                aVar.a.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.audiobooks.adapter.-$$Lambda$TabAdapter$LoHrsfTGgwAAAO-epivfFq4J9Aw
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TabAdapter.this.a(aVar, view);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(@NonNull a aVar, View view) {
        if (!UiUtils.isFastClick()) {
            this.b.onItemClick(aVar.getLayoutPosition());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.b = onItemClickListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        private ItemAudiobookTabBinding a;

        a(@NonNull ItemAudiobookTabBinding itemAudiobookTabBinding) {
            super(itemAudiobookTabBinding.getRoot());
            this.a = itemAudiobookTabBinding;
        }
    }
}
