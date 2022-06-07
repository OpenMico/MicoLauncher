package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class BannerAdapter extends RecyclerView.Adapter<BannerViewHolder> {
    private Context a;
    private List<IListItem> b;
    private a c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface a {
        void onItemClick(int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public BannerAdapter(Context context, List<IListItem> list) {
        this.a = context;
        this.b = list;
        UiUtils.getSize(context, R.dimen.banner_width);
        UiUtils.getSize(context, R.dimen.banner_height);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        L.childContent.i("BannerAdapter onCreateViewHolder");
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_banner_layout, (ViewGroup) null);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new BannerViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull BannerViewHolder bannerViewHolder, final int i) {
        if (!ContainerUtil.isEmpty(this.b) && !ContainerUtil.isOutOfBound(i % this.b.size(), this.b)) {
            List<IListItem> list = this.b;
            L.childContent.i("BannerAdapter onBindViewHolder  %s", Integer.valueOf(i));
            GlideUtils.bindImageViewWithRoundCornersNoCrop(this.a, list.get(i % list.size()).getItemImageUrl(), bannerViewHolder.imageView, UiUtils.getSize(this.a, R.dimen.child_story_banner_radius), R.drawable.child_story_banner_place_bg);
            RxViewHelp.debounceClicksWithOneSeconds(bannerViewHolder.imageView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.view.-$$Lambda$BannerAdapter$kyIt4WKixIrxiPiHSCR1UdV-TWQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BannerAdapter.this.a(i, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        a aVar = this.c;
        if (aVar != null) {
            aVar.onItemClick(i % this.b.size());
        }
    }

    /* loaded from: classes3.dex */
    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public BannerViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.banner_image);
        }
    }

    public void setOnItemClickListener(a aVar) {
        this.c = aVar;
    }
}
