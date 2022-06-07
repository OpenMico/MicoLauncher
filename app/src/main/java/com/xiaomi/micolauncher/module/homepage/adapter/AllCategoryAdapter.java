package com.xiaomi.micolauncher.module.homepage.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity;
import com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity;
import com.xiaomi.micolauncher.module.homepage.adapter.AllCategoryAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookCategory;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryViewHolder> {
    private List<AudiobookCategory> a;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AllCategoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audiobook_all_category_item, viewGroup));
    }

    public void onBindViewHolder(@NonNull AllCategoryViewHolder allCategoryViewHolder, int i) {
        allCategoryViewHolder.bindData(this.a.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    /* loaded from: classes3.dex */
    public class AllCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView a;
        private AudiobookCategory c;

        public AllCategoryViewHolder(final View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.category_title);
            RxViewHelp.debounceClicksWithOneSeconds(view).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$AllCategoryAdapter$AllCategoryViewHolder$zLJo--h1nqOG0QHmfioVP8NNjXI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AllCategoryAdapter.AllCategoryViewHolder.this.a(view, obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(@NonNull View view, Object obj) throws Exception {
            if (CommonUtil.checkHasNetworkAndShowToast(view.getContext())) {
                Intent intent = new Intent(view.getContext(), AudiobookCommonActivity.class);
                intent.putExtra(BaseAudiobookActivity.KEY_TITLE, this.c.getTitle());
                ActivityLifeCycleManager.startActivityQuietly(intent);
            }
        }

        public void bindData(AudiobookCategory audiobookCategory) {
            this.c = audiobookCategory;
            this.a.setText(audiobookCategory.getTitle());
        }
    }
}
